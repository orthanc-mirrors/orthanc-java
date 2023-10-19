/**
 * SPDX-FileCopyrightText: 2023 Sebastien Jodogne, UCLouvain, Belgium
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

/**
 * Java plugin for Orthanc
 * Copyright (C) 2023 Sebastien Jodogne, UCLouvain, Belgium
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

static OrthancPluginContext* context_ = NULL;



static const char* JAVA_EXCEPTION_CLASS = "be/uclouvain/orthanc/OrthancException";  

class JavaVirtualMachine : public NonCopyable
{
private:
  JavaVM *jvm_;

public:
  JavaVirtualMachine(const std::string& classPath)
  {
    std::string classPathOption = "-Djava.class.path=" + classPath;

    std::vector<JavaVMOption> options;
    options.resize(2);
    options[0].optionString = const_cast<char*>(classPathOption.c_str());
    options[0].extraInfo = NULL;
    options[1].optionString = const_cast<char*>("-Xcheck:jni");
    options[1].extraInfo = NULL;

    JavaVMInitArgs vm_args;
    vm_args.version  = JNI_VERSION_1_6;
    vm_args.nOptions = options.size();
    vm_args.options  = (options.empty() ? NULL : &options[0]);
    vm_args.ignoreUnrecognized = false;

    JNIEnv* env = NULL;
    jint res = JNI_CreateJavaVM(&jvm_, (void **) &env, &vm_args);
    if (res != JNI_OK ||
        jvm_ == NULL ||
        env == NULL)
    {
      throw std::runtime_error("Cannot create the JVM");
    }
  }

  ~JavaVirtualMachine()
  {
    jvm_->DestroyJavaVM();
  }

  JavaVM& GetValue()
  {
    assert(jvm_ != NULL);
    return *jvm_;
  }
};


class JavaEnvironment : public NonCopyable
{
private:
  JavaVM *jvm_;
  JNIEnv *env_;

public:
  JavaEnvironment(JNIEnv* env) :
    jvm_(NULL),
    env_(env)
  {
    if (env_ == NULL)
    {
      throw std::runtime_error("Null pointer");
    }
  }
  
  JavaEnvironment(JavaVirtualMachine& jvm) :
    jvm_(&jvm.GetValue())
  {
    jint status = jvm_->GetEnv((void **) &env_, JNI_VERSION_1_6);

    switch (status)
    {
      case JNI_OK:
        break;

      case JNI_EDETACHED:
      {
        jint code = jvm_->AttachCurrentThread((void **) &env_, NULL);
        if (code != JNI_OK)
        {
          throw std::runtime_error("Cannot attach thread");
        }
        break;
      }

      case JNI_EVERSION:
        throw std::runtime_error("JNI version not supported");

      default:
        throw std::runtime_error("Not implemented");
    }

    if (env_ == NULL)
    {
      throw std::runtime_error("Error inside JNI");
    }
  }

  ~JavaEnvironment()
  {
    if (jvm_ != NULL)
    {
      jvm_->DetachCurrentThread();
    }
  }

  void CheckException()
  {
    if (env_->ExceptionCheck() == JNI_TRUE)
    {
      env_->ExceptionClear();
      throw std::runtime_error("An exception has occurred in Java");
    }
  }

  JNIEnv& GetValue()
  {
    assert(env_ != NULL);
    return *env_;
  }

  void RunGarbageCollector()
  {
    assert(env_ != NULL);

    jclass system = FindClass("java/lang/System");

    jmethodID runFinalization = env_->GetStaticMethodID(system, "gc", "()V");
    if (runFinalization != NULL)
    {
      env_->CallStaticVoidMethod(system, runFinalization);
      CheckException();
    }
    else
    {
      throw std::runtime_error("Cannot run garbage collector");
    }
  }

  jclass FindClass(const std::string& fqn)
  {
    jclass c = GetValue().FindClass(fqn.c_str());

    if (c == NULL)
    {
      throw std::runtime_error("Unable to find class: " + fqn);
    }
    else
    {
      return c;
    }
  }

  jclass GetObjectClass(jobject obj)
  {
    jclass c = GetValue().GetObjectClass(obj);

    if (c == NULL)
    {
      throw std::runtime_error("Unable to get class of object");
    }
    else
    {
      return c;
    }
  }

  jmethodID GetMethodID(jclass c,
                        const std::string& method,
                        const std::string& signature)
  {
    jmethodID m = GetValue().GetMethodID(c, method.c_str(), signature.c_str());

    if (m == NULL)
    {
      throw std::runtime_error("Unable to locate method in class");
    }
    else
    {
      return m;
    }
  }

  jobject ConstructJavaWrapper(const std::string& fqn,
                               void* nativeObject)
  {
    jclass cls = FindClass(fqn);
    jmethodID constructor = GetMethodID(cls, "<init>", "(J)V");
    jobject obj = env_->NewObject(cls, constructor, reinterpret_cast<intptr_t>(nativeObject));
    
    if (obj == NULL)
    {
      throw std::runtime_error("Cannot create Java wrapper around C/C++ object: " + fqn);
    }
    else
    {
      return obj;
    }
  }

  jbyteArray ConstructByteArray(const size_t size,
                                const void* data)
  {
    assert(env_ != NULL);
    jbyteArray obj = env_->NewByteArray(size);
    if (obj == NULL)
    {
      throw std::runtime_error("Cannot create a byte array");
    }
    else
    {
      if (size > 0)
      {
        env_->SetByteArrayRegion(obj, 0, size, reinterpret_cast<const jbyte*>(data));
      }

      return obj;
    }
  }

  jbyteArray ConstructByteArray(const std::string& data)
  {
    return ConstructByteArray(data.size(), data.c_str());
  }

  void RegisterNatives(const std::string& fqn,
                       const std::vector<JNINativeMethod>& methods)
  {
    if (!methods.empty())
    {
      if (env_->RegisterNatives(FindClass(fqn), &methods[0], methods.size()) < 0)
      {
        throw std::runtime_error("Unable to register the native methods");
      }
    }
  }

  void ThrowException(const std::string& fqn,
                      const std::string& message)
  {
    if (GetValue().ThrowNew(FindClass(fqn), message.c_str()) != 0)
    {
      std::string message = "Cannot throw exception " + fqn;
      OrthancPluginLogError(context_, message.c_str());
    }
  }

  void ThrowException(const std::string& message)
  {
    ThrowException(JAVA_EXCEPTION_CLASS, message);
  }

  void ThrowException(OrthancPluginErrorCode code)
  {
    ThrowException(JAVA_EXCEPTION_CLASS, OrthancPluginGetErrorDescription(context_, code));
  }

  jobject ConstructEnumValue(const std::string& fqn,
                             int value)
  {
    assert(env_ != NULL);
    jclass cls = FindClass(fqn);

    std::string signature = "(I)L" + fqn + ";";
    jmethodID constructor = env_->GetStaticMethodID(cls, "getInstance", signature.c_str());
    if (constructor != NULL)
    {
      jobject obj = env_->CallStaticObjectMethod(cls, constructor, static_cast<jint>(value));
      CheckException();
      return obj;
    }
    else
    {
      char buf[16];
      sprintf(buf, "%d", value);
      throw std::runtime_error("Cannot create enumeration value: " + fqn + " " + buf);
    }
  }

  
  static void ThrowException(JNIEnv* env,
                             const std::string& fqn,
                             const std::string& message)
  {
    JavaEnvironment e(env);
    e.ThrowException(fqn, message);
  }
  
  static void ThrowException(JNIEnv* env,
                             const std::string& message)
  {
    JavaEnvironment e(env);
    e.ThrowException(message);
  }

  static void ThrowException(JNIEnv* env,
                             OrthancPluginErrorCode code)
  {
    JavaEnvironment e(env);
    e.ThrowException(code);
  }
};


static std::unique_ptr<JavaVirtualMachine> java_;



class JavaString : public NonCopyable
{
private:
  JNIEnv*      env_;
  jstring      javaStr_;
  const char*  cStr_;
  jboolean     isCopy_;
  
public:
  JavaString(JNIEnv* env,
             jstring javaStr) :
    env_(env),
    javaStr_(javaStr)
  {
    if (env == NULL ||
        javaStr == NULL)
    {
      throw std::runtime_error("Null pointer");
    }

    cStr_ = env_->GetStringUTFChars(javaStr_, &isCopy_);
    if (cStr_ == NULL)
    {
      throw std::runtime_error("Cannot read string");
    }
  }

  ~JavaString()
  {
    /**
     * "The ReleaseString-Chars call is necessary whether
     * GetStringChars has set isCopy to JNI_TRUE or JNI_FALSE."
     * https://stackoverflow.com/a/5863081
     **/
    env_->ReleaseStringUTFChars(javaStr_, cStr_);
  }

  const char* GetValue() const
  {
    return cStr_;
  }
};


class JavaBytes : public NonCopyable
{
private:
  JNIEnv*      env_;
  jbyteArray   bytes_;
  jbyte*       data_;
  jsize        size_;
  jboolean     isCopy_;
  
public:
  JavaBytes(JNIEnv* env,
            jbyteArray bytes) :
    env_(env),
    bytes_(bytes)
  {
    if (env == NULL ||
        bytes == NULL)
    {
      throw std::runtime_error("Null pointer");
    }

    size_ = env->GetArrayLength(bytes);

    if (size_ == 0)
    {
      data_ = NULL;
    }
    else
    {
      data_ = env->GetByteArrayElements(bytes_, &isCopy_);
      if (data_ == NULL)
      {
        throw std::runtime_error("Cannot read array of bytes");
      }
    }
  }

  ~JavaBytes()
  {
    if (size_ > 0)
    {
      env_->ReleaseByteArrayElements(bytes_, data_, 0);
    }
  }

  const void* GetData() const
  {
    return data_;
  }

  size_t GetSize() const
  {
    return size_;
  }
};


class OrthancString : public NonCopyable
{
private:
  char*  str_;

public:
  OrthancString(char* str) :
    str_(str)
  {
  }

  ~OrthancString()
  {
    if (str_ != NULL)
    {
      OrthancPluginFreeString(context_, str_);
    }
  }

  const char* GetValue() const
  {
    return str_;
  }
};


class OrthancBytes : public NonCopyable
{
private:
  OrthancPluginMemoryBuffer  buffer_;

public:
  OrthancBytes()
  {
    buffer_.data = NULL;
    buffer_.size = 0;
  }

  ~OrthancBytes()
  {
    OrthancPluginFreeMemoryBuffer(context_, &buffer_);
  }

  OrthancPluginMemoryBuffer* GetMemoryBuffer()
  {
    return &buffer_;
  }

  const void* GetData() const
  {
    return buffer_.data;
  }

  size_t GetSize() const
  {
    return buffer_.size;
  }
};


class JavaGlobalReference : public NonCopyable
{
private:
  JavaVirtualMachine&  jvm_;
  jobject obj_;

public:
  JavaGlobalReference(JavaVirtualMachine& jvm,
                      jobject obj) :
    jvm_(jvm),
    obj_(NULL)
  {
    if (obj == NULL)
    {
      throw std::runtime_error("Null pointer");
    }

    JavaEnvironment env(jvm);

    obj_ = env.GetValue().NewGlobalRef(obj);
    if (obj_ == NULL)
    {
      throw std::runtime_error("Cannot create global reference");
    }
  }

  ~JavaGlobalReference()
  {
    assert(obj_ != NULL);

    try
    {
      JavaEnvironment env(jvm_);
      env.GetValue().DeleteGlobalRef(obj_);
    }
    catch (std::runtime_error& e)
    {
      OrthancPluginLogError(context_, e.what());
    }
  }

  jobject GetValue()
  {
    assert(obj_ != NULL);
    return obj_;
  }
};


class LocalJavaObject : public NonCopyable
{
private:
  JNIEnv*  env_;
  jobject  obj_;

public:
  LocalJavaObject(JavaEnvironment& env,
                  jobject obj,
                  bool objCanBeNull = false) :
    env_(&env.GetValue()),
    obj_(obj)
  {
    if (!objCanBeNull && obj == NULL)
    {
      throw std::runtime_error("Null pointer");
    }
  }

  ~LocalJavaObject()
  {
    env_->DeleteLocalRef(obj_);
  }

  jobject GetValue()
  {
    return obj_;
  }

  static LocalJavaObject* CreateArrayOfStrings(JavaEnvironment& env,
                                               const std::vector<std::string>& items)
  {
    LocalJavaObject emptyString(env, env.GetValue().NewStringUTF(""));

    jobjectArray obj = env.GetValue().NewObjectArray(
      items.size(), env.GetValue().FindClass("java/lang/String"),
      emptyString.GetValue());

    if (obj == NULL)
    {
      throw std::runtime_error("Cannot create an array of Java strings");
    }
    else
    {
      std::unique_ptr<LocalJavaObject> result(new LocalJavaObject(env, obj));

      for (size_t i = 0; i < items.size(); i++)
      {
        LocalJavaObject item(env, env.GetValue().NewStringUTF(items[i].c_str()));
        env.GetValue().SetObjectArrayElement(obj, i, item.GetValue());
      }

      return result.release();
    }
  }

  static LocalJavaObject* CreateDictionary(JavaEnvironment& env,
                                           const std::map<std::string, std::string>& items)
  {
    // NB: In JNI, there are no generics. All the templated arguments
    // are taken as instances of the "Object" base class.

    jclass cls = env.FindClass("java/util/HashMap");
    jmethodID constructor = env.GetMethodID(cls, "<init>", "()V");
    jmethodID setter = env.GetMethodID(cls, "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
    jobject obj = env.GetValue().NewObject(cls, constructor);

    if (obj == NULL)
    {
      throw std::runtime_error("Cannot create a Java dictionary");
    }
    else
    {
      std::unique_ptr<LocalJavaObject> result(new LocalJavaObject(env, obj));

      for (std::map<std::string, std::string>::const_iterator it = items.begin(); it != items.end(); ++it)
      {
        LocalJavaObject key(env, env.GetValue().NewStringUTF(it->first.c_str()));
        LocalJavaObject value(env, env.GetValue().NewStringUTF(it->second.c_str()));
        LocalJavaObject previousValue(env, env.GetValue().CallObjectMethod(obj, setter, key.GetValue(), value.GetValue()), true);
        env.CheckException();
      }

      return result.release();
    }
  }
};



#include "NativeSDK.cpp"



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

      LocalJavaObject joutput(env, env.ConstructJavaWrapper("be/uclouvain/orthanc/RestOutput", output));
      LocalJavaObject jmethod(env, env.ConstructEnumValue("be/uclouvain/orthanc/HttpMethod", request->method));
      LocalJavaObject juri(env, env.GetValue().NewStringUTF(uri == NULL ? "" : uri));
      std::unique_ptr<LocalJavaObject> jgroups(LocalJavaObject::CreateArrayOfStrings(env, groups));
      std::unique_ptr<LocalJavaObject> jheaders(LocalJavaObject::CreateDictionary(env, headers));
      std::unique_ptr<LocalJavaObject> jgetParameters(LocalJavaObject::CreateDictionary(env, getParameters));
      LocalJavaObject jbody(env, env.ConstructByteArray(request->bodySize, request->body));

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

      LocalJavaObject c(env, env.ConstructEnumValue("be/uclouvain/orthanc/ChangeType", changeType));
      LocalJavaObject r(env, env.ConstructEnumValue("be/uclouvain/orthanc/ResourceType", resourceType));
      LocalJavaObject s(env, env.GetValue().NewStringUTF(resourceId == NULL ? "" : resourceId));

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
    JavaEnvironment::ThrowException(env, e.what());
  }
  catch (...)
  {
    JavaEnvironment::ThrowException(env, OrthancPluginErrorCode_Plugin);
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
    JavaEnvironment::ThrowException(env, e.what());
  }
  catch (...)
  {
    JavaEnvironment::ThrowException(env, OrthancPluginErrorCode_Plugin);
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

    OrthancPluginSetDescription(context, "Java plugin for Orthanc");

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
    return "java";
  }


  ORTHANC_PLUGINS_API const char* OrthancPluginGetVersion()
  {
    return PLUGIN_VERSION;
  }
}
