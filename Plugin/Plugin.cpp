/**
 * SPDX-FileCopyrightText: 2023-2024 Sebastien Jodogne, UCLouvain, Belgium
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

/**
 * Java plugin for Orthanc
 * Copyright (C) 2023-2024 Sebastien Jodogne, UCLouvain, Belgium
 *
 * This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 **/


#include "JavaLocalObject.h"
#include "JavaGlobalReference.h"
#include "OrthancBytes.h"
#include "OrthancString.h"
#include "JavaBytes.h"
#include "JavaString.h"
#include "JavaEnvironment.h"
#include "JavaVirtualMachine.h"

#include <orthanc/OrthancCPlugin.h>

#include <cassert>
#include <iostream>
#include <jni.h>
#include <list>
#include <map>
#include <memory>
#include <set>
#include <stdexcept>
#include <vector>

#include <json/reader.h>

#include "Mutex.h"

#define ORTHANC_PLUGIN_NAME  "java"


OrthancPluginContext* context_ = NULL;

static std::unique_ptr<JavaVirtualMachine> java_;


// This function is implemented in the "NativeSDK.cpp" auto-generated file
extern void JNI_LoadNatives(std::vector<JNINativeMethod>& methods);


#define MAX_REST_CALLBACKS  10

class CallbacksConfiguration : public NonCopyable
{
private:
  Mutex                              mutex_;
  std::list<JavaGlobalReference*>    onChangeCallbacks_;
  std::vector<JavaGlobalReference*>  onRestRequestCallbacks_;

  static void DestructCallbacks(std::list<JavaGlobalReference*>& lst)
  {
    for (std::list<JavaGlobalReference*>::iterator it = lst.begin(); it != lst.end(); ++it)
    {
      assert(*it != NULL);
      delete *it;
    }
  }

  static void DestructCallbacks(std::vector<JavaGlobalReference*>& v)
  {
    for (size_t i = 0; i < v.size(); i++)
    {
      assert(v[i] != NULL);
      delete v[i];
    }
  }

  void CopyCallbacks(std::list<jobject>& target,
                     const std::list<JavaGlobalReference*>& lst)
  {
    Mutex::Locker locker(mutex_);
    target.clear();

    for (std::list<JavaGlobalReference*>::const_iterator it = lst.begin(); it != lst.end(); ++it)
    {
      assert(*it != NULL);
      target.push_back((*it)->GetValue());
    }
  }

  void AddCallback(std::list<JavaGlobalReference*>& lst,
                   JavaVirtualMachine& jvm,
                   jobject callback)
  {
    if (callback == NULL)
    {
      throw std::runtime_error("Null pointer");
    }
    else
    {
      Mutex::Locker locker(mutex_);
      lst.push_back(new JavaGlobalReference(jvm, callback));
    }
  }

public:
  CallbacksConfiguration()
  {
    onRestRequestCallbacks_.reserve(MAX_REST_CALLBACKS);
  }

  ~CallbacksConfiguration()
  {
    DestructCallbacks(onChangeCallbacks_);
    DestructCallbacks(onRestRequestCallbacks_);
  }

  void AddOnChangeCallback(JavaVirtualMachine& jvm,
                           jobject callback)
  {
    AddCallback(onChangeCallbacks_, jvm, callback);
  }

  void GetOnChangeCallbacks(std::list<jobject>& target)
  {
    CopyCallbacks(target, onChangeCallbacks_);
  }

  size_t AddOnRestRequestCallback(JavaVirtualMachine& jvm,
                                  jobject callback)
  {
    if (callback == NULL)
    {
      throw std::runtime_error("Null pointer");
    }
    else
    {
      Mutex::Locker locker(mutex_);
      
      if (onRestRequestCallbacks_.size() >= MAX_REST_CALLBACKS)
      {
        char buf[16];
        sprintf(buf, "%d", MAX_REST_CALLBACKS);
        throw std::runtime_error("The Java plugin for Orthanc has been compiled for a maximum of " +
                                 std::string(buf) + " REST callbacks");
      }
      else
      {
        size_t result = onRestRequestCallbacks_.size();
        onRestRequestCallbacks_.push_back(new JavaGlobalReference(jvm, callback));
        return result;
      }
    }
  }

  jobject GetOnRestCallback(size_t i)
  {
    Mutex::Locker locker(mutex_);

    if (i >= onRestRequestCallbacks_.size())
    {
      throw std::runtime_error("Unknown REST callback");
    }
    else
    {
      assert(onRestRequestCallbacks_[i] != NULL);
      return onRestRequestCallbacks_[i]->GetValue();
    }
  }    
};

static std::unique_ptr<CallbacksConfiguration> callbacksConfiguration_;




template<size_t Index>
class RestCallbacksPool
{
private:
  RestCallbacksPool<Index - 1>  next_;

  static OrthancPluginErrorCode Callback(OrthancPluginRestOutput* output,
                                         const char* uri,
                                         const OrthancPluginHttpRequest* request)
  {
    try
    {
      jobject callback = callbacksConfiguration_->GetOnRestCallback(MAX_REST_CALLBACKS - Index);
      if (callback == NULL)
      {
        throw std::runtime_error("Missing callback");
      }

      std::vector<std::string> groups;
      groups.resize(request->groupsCount);
      for (uint32_t i = 0; i < request->groupsCount; i++)
      {
        groups[i].assign(request->groups[i]);
      }

      std::map<std::string, std::string> headers;
      for (uint32_t i = 0; i < request->headersCount; i++)
      {
        headers[request->headersKeys[i]] = request->headersValues[i];
      }

      std::map<std::string, std::string> getParameters;
      for (uint32_t i = 0; i < request->getCount; i++)
      {
        getParameters[request->getKeys[i]] = request->getValues[i];
      }

      JavaEnvironment env(*java_);

      JavaLocalObject joutput(env, env.ConstructJavaWrapper("be/uclouvain/orthanc/RestOutput", output));
      JavaLocalObject jmethod(env, env.ConstructEnumValue("be/uclouvain/orthanc/HttpMethod", request->method));
      JavaLocalObject juri(env, env.GetValue().NewStringUTF(uri == NULL ? "" : uri));
      std::unique_ptr<JavaLocalObject> jgroups(JavaLocalObject::CreateArrayOfStrings(env, groups));
      std::unique_ptr<JavaLocalObject> jheaders(JavaLocalObject::CreateDictionary(env, headers));
      std::unique_ptr<JavaLocalObject> jgetParameters(JavaLocalObject::CreateDictionary(env, getParameters));
      JavaLocalObject jbody(env, env.ConstructByteArray(request->bodySize, request->body));

      jmethodID call = env.GetMethodID(
        env.GetObjectClass(callback), "call",
        "(Lbe/uclouvain/orthanc/RestOutput;Lbe/uclouvain/orthanc/HttpMethod;Ljava/lang/String;"
        "[Ljava/lang/String;Ljava/util/Map;Ljava/util/Map;[B)V");

      env.GetValue().CallVoidMethod(callback, call, joutput.GetValue(), jmethod.GetValue(), juri.GetValue(),
                                    jgroups->GetValue(), jheaders->GetValue(), jgetParameters->GetValue(), jbody.GetValue());
      env.CheckException();

      return OrthancPluginErrorCode_Success;
    }
    catch (std::runtime_error& e)
    {
      OrthancPluginLogError(context_, e.what());
      return OrthancPluginErrorCode_Plugin;
    }
    catch (...)
    {
      OrthancPluginLogError(context_, "Caught native exception");
      return OrthancPluginErrorCode_Plugin;
    }
  }

public:
  OrthancPluginRestCallback GetCallback(size_t i)
  {
    if (i == 0)
    {
      return Callback;
    }
    else
    {
      return next_.GetCallback(i - 1);
    }
  }
};

template<>
class RestCallbacksPool<0>
{
public:
  OrthancPluginRestCallback& GetCallback(size_t i)
  {
    throw std::runtime_error("Out of tuple");
  }
};


static RestCallbacksPool<MAX_REST_CALLBACKS>  restCallbacksPool_;



OrthancPluginErrorCode OnChangeCallback(OrthancPluginChangeType changeType,
                                        OrthancPluginResourceType resourceType,
                                        const char* resourceId)
{
  try
  {
    std::list<jobject> callbacks;
    callbacksConfiguration_->GetOnChangeCallbacks(callbacks);

    if (!callbacks.empty())
    {
      JavaEnvironment env(*java_);

      JavaLocalObject c(env, env.ConstructEnumValue("be/uclouvain/orthanc/ChangeType", changeType));
      JavaLocalObject r(env, env.ConstructEnumValue("be/uclouvain/orthanc/ResourceType", resourceType));
      JavaLocalObject s(env, env.GetValue().NewStringUTF(resourceId == NULL ? "" : resourceId));

      for (std::list<jobject>::const_iterator
             callback = callbacks.begin(); callback != callbacks.end(); ++callback)
      {
        assert(*callback != NULL);

        jmethodID call = env.GetMethodID(
          env.GetObjectClass(*callback), "call",
          "(Lbe/uclouvain/orthanc/ChangeType;Lbe/uclouvain/orthanc/ResourceType;Ljava/lang/String;)V");

        env.GetValue().CallVoidMethod(*callback, call, c.GetValue(), r.GetValue(), s.GetValue());
        env.CheckException();
      }
    }

    return OrthancPluginErrorCode_Success;
  }
  catch (std::runtime_error& e)
  {
    OrthancPluginLogError(context_, e.what());
    return OrthancPluginErrorCode_Plugin;
  }
  catch (...)
  {
    OrthancPluginLogError(context_, "Caught native exception");
    return OrthancPluginErrorCode_Plugin;
  }
}


JNIEXPORT void RegisterOnChangeCallback(JNIEnv* env, jobject sdkObject, jobject callback)
{
  try
  {
    callbacksConfiguration_->AddOnChangeCallback(*java_, callback);
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
  }
}


JNIEXPORT void RegisterOnRestRequestCallback(JNIEnv* env, jobject sdkObject, jstring regex, jobject callback)
{
  try
  {
    JavaString cregex(env, regex);
    size_t index = callbacksConfiguration_->AddOnRestRequestCallback(*java_, callback);
    OrthancPluginRegisterRestCallbackNoLock(context_, cregex.GetValue(), restCallbacksPool_.GetCallback(index));
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
  }
}


static void ParseJson(Json::Value& target,
                      const std::string& source)
{
  Json::CharReaderBuilder builder;
  builder.settings_["collectComments"] = false;

  const std::unique_ptr<Json::CharReader> reader(builder.newCharReader());
  assert(reader.get() != NULL);

  JSONCPP_STRING err;
  if (!reader->parse(source.c_str(), source.c_str() + source.size(), &target, &err))
  {
    throw std::runtime_error("Cannot parse JSON: " + err);
  }
}


static bool HasOption(const Json::Value& json,
                      const std::string& key,
                      Json::ValueType type,
                      bool isMandatory)
{
  if (!json.isMember(key))
  {
    if (isMandatory)
    {
      throw std::runtime_error("Missing configuration option for the Java plugin: \"" + key + "\"");
    }
    else
    {
      return false;
    }
  }
  else if (json[key].type() == type)
  {
    return true;
  }
  else
  {
    throw std::runtime_error("The configuration option \"" + key + "\" for the Java plugin has not the proper type");
  }
}


static std::string GetMandatoryString(const Json::Value& json,
                                      const std::string& key)
{
  HasOption(json, key, Json::stringValue, true);
  assert(json.isMember(key) &&
         json[key].type() == Json::stringValue);
  return json[key].asString();
}


static void SetPluginDescription(const std::string& description)
{
#if ORTHANC_PLUGINS_VERSION_IS_ABOVE(1, 12, 4)
  OrthancPluginSetDescription2(context_, ORTHANC_PLUGIN_NAME, description.c_str());
#else
  _OrthancPluginSetPluginProperty params;
  params.plugin = ORTHANC_PLUGIN_NAME;
  params.property = _OrthancPluginProperty_Description;
  params.value = description.c_str();

  context_->InvokeService(context_, _OrthancPluginService_SetPluginProperty, &params);
#endif
}


extern "C"
{
  ORTHANC_PLUGINS_API int32_t OrthancPluginInitialize(OrthancPluginContext* context)
  {
    context_ = context;

    /* Check the version of the Orthanc core */
    if (OrthancPluginCheckVersion(context) == 0)
    {
      char info[1024];
      sprintf(info, "Your version of Orthanc (%s) must be above %d.%d.%d to run this plugin",
              context->orthancVersion,
              ORTHANC_PLUGINS_MINIMAL_MAJOR_NUMBER,
              ORTHANC_PLUGINS_MINIMAL_MINOR_NUMBER,
              ORTHANC_PLUGINS_MINIMAL_REVISION_NUMBER);
      OrthancPluginLogError(context, info);
      return -1;
    }

    SetPluginDescription("Java plugin for Orthanc");

    try
    {
      {
        // Sanity check to ensure that the compiler has created different callback functions
        std::set<intptr_t> c;
        for (unsigned int i = 0; i < MAX_REST_CALLBACKS; i++)
        {
          c.insert(reinterpret_cast<intptr_t>(restCallbacksPool_.GetCallback(i)));
        }

        if (c.size() != MAX_REST_CALLBACKS)
        {
          throw std::runtime_error("The Java plugin has not been properly compiled");
        }
      }

      Json::Value globalConfiguration;

      {
        OrthancString tmp(OrthancPluginGetConfiguration(context));
        ParseJson(globalConfiguration, tmp.GetValue());
      }

      static const std::string KEY_JAVA = "Java";
      static const std::string KEY_ENABLED = "Enabled";
      static const std::string KEY_CLASSPATH = "Classpath";
      static const std::string KEY_INITIALIZATION_CLASS = "InitializationClass";

      if (!HasOption(globalConfiguration, KEY_JAVA, Json::objectValue, false))
      {
        OrthancPluginLogInfo(context, "Java plugin is disabled");
        return 0;
      }

      Json::Value javaConfiguration = globalConfiguration[KEY_JAVA];
      assert(javaConfiguration.isObject());

      if (HasOption(javaConfiguration, KEY_ENABLED, Json::booleanValue, false) &&
          !javaConfiguration[KEY_ENABLED].asBool())
      {
        OrthancPluginLogInfo(context, "Java plugin is disabled");
        return 0;
      }

      java_.reset(new JavaVirtualMachine(GetMandatoryString(javaConfiguration, KEY_CLASSPATH)));

      callbacksConfiguration_.reset(new CallbacksConfiguration);
      OrthancPluginRegisterOnChangeCallback(context_, OnChangeCallback);

      JavaEnvironment env(*java_);
      
      {
        std::vector<JNINativeMethod> methods;
        JNI_LoadNatives(methods);
        env.RegisterNatives("be/uclouvain/orthanc/NativeSDK", methods);
      }

      {
        std::vector<JNINativeMethod> methods;
        methods.push_back((JNINativeMethod) {
            const_cast<char*>("register"),
              const_cast<char*>("(Lbe/uclouvain/orthanc/Callbacks$OnChange;)V"),
              (void*) RegisterOnChangeCallback });
        methods.push_back((JNINativeMethod) {
            const_cast<char*>("register"),
              const_cast<char*>("(Ljava/lang/String;Lbe/uclouvain/orthanc/Callbacks$OnRestRequest;)V"),
              (void*) RegisterOnRestRequestCallback });
        env.RegisterNatives("be/uclouvain/orthanc/Callbacks", methods);
      }

      if (HasOption(javaConfiguration, KEY_INITIALIZATION_CLASS, Json::stringValue, false))
      {
        env.FindClass(javaConfiguration[KEY_INITIALIZATION_CLASS].asString());
      }
    }
    catch (std::runtime_error& e)
    {
      OrthancPluginLogError(context, e.what());
      return -1;
    }
    
    return 0;
  }


  ORTHANC_PLUGINS_API void OrthancPluginFinalize()
  {
    if (java_.get() != NULL)
    {
      callbacksConfiguration_.reset(NULL);
      
      try
      {
        JavaEnvironment env(*java_);
        env.RunGarbageCollector();
      }
      catch (std::runtime_error& e)
      {
        OrthancPluginLogError(context_, e.what());
      }

      java_.reset(NULL);
    }
  }


  ORTHANC_PLUGINS_API const char* OrthancPluginGetName()
  {
    return ORTHANC_PLUGIN_NAME;
  }


  ORTHANC_PLUGINS_API const char* OrthancPluginGetVersion()
  {
    return PLUGIN_VERSION;
  }
}
