/**
 * SPDX-FileCopyrightText: 2023-2026 Sebastien Jodogne, ICTEAM UCLouvain, Belgium
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

/**
 * Java plugin for Orthanc
 * Copyright (C) 2023-2026 Sebastien Jodogne, ICTEAM UCLouvain, Belgium
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


#include "JavaEnvironment.h"
#include "JavaString.h"

#include <cassert>
#include <stdexcept>

extern OrthancPluginContext* context_;


static const char* ORTHANC_EXCEPTION_JAVA_CLASS = "be/uclouvain/orthanc/OrthancException";


JavaEnvironment::JavaEnvironment(JNIEnv* env) :
  jvm_(NULL),
  env_(env)
{
  if (env_ == NULL)
  {
    throw std::runtime_error("Null pointer");
  }
}


JavaEnvironment::JavaEnvironment(JavaVirtualMachine& jvm) :
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


JavaEnvironment::~JavaEnvironment()
{
  if (jvm_ != NULL)
  {
    jvm_->DetachCurrentThread();
  }
}


JNIEnv& JavaEnvironment::GetValue()
{
  assert(env_ != NULL);
  return *env_;
}


void JavaEnvironment::CheckException()
{
  if (env_->ExceptionCheck() == JNI_TRUE)
  {
    jthrowable e = env_->ExceptionOccurred();
    env_->ExceptionClear();

    jclass clazz = env_->GetObjectClass(e);
    if (clazz != NULL)
    {
      jmethodID getMessage = env_->GetMethodID(clazz, "getMessage", "()Ljava/lang/String;");
      if (getMessage != NULL)
      {
        JavaString message(env_, (jstring) env_->CallObjectMethod(e, getMessage));
        throw std::runtime_error("An exception has occurred in Java: " + std::string(message.GetValue()));
      }
    }

    throw std::runtime_error("An exception has occurred in Java");
  }
}


void JavaEnvironment::ThrowException(const std::string& fqn,
                                     const std::string& message)
{
  if (GetValue().ThrowNew(FindClass(fqn), message.c_str()) != 0)
  {
    std::string tmp = "Cannot throw exception " + fqn;
    OrthancPluginLogError(context_, tmp.c_str());
  }
}


void JavaEnvironment::ThrowOrthancException(const std::string& message)
{
  ThrowException(ORTHANC_EXCEPTION_JAVA_CLASS, message);
}


void JavaEnvironment::ThrowOrthancException(OrthancPluginErrorCode code)
{
  ThrowException(ORTHANC_EXCEPTION_JAVA_CLASS, OrthancPluginGetErrorDescription(context_, code));
}


void JavaEnvironment::ThrowOrthancException(JNIEnv* env,
                                            const std::string& message)
{
  JavaEnvironment e(env);
  e.ThrowOrthancException(message);
}


void JavaEnvironment::ThrowOrthancException(JNIEnv* env,
                                            OrthancPluginErrorCode code)
{
  JavaEnvironment e(env);
  e.ThrowOrthancException(code);
}


void JavaEnvironment::RegisterNatives(const std::string& fqn,
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


void JavaEnvironment::RunGarbageCollector()
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


jclass JavaEnvironment::FindClass(const std::string& fqn)
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


jclass JavaEnvironment::GetObjectClass(jobject obj)
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


jmethodID JavaEnvironment::GetMethodID(jclass c,
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


jobject JavaEnvironment::ConstructJavaWrapper(const std::string& fqn,
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


jbyteArray JavaEnvironment::ConstructByteArray(const size_t size,
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


jobject JavaEnvironment::ConstructEnumValue(const std::string& fqn,
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


std::string JavaEnvironment::GetRuntimeErrorMessage(OrthancPluginContext* context,
                                                    OrthancPluginErrorCode code)
{
  return "An exception has occurred in Java: " + std::string(OrthancPluginGetErrorDescription(context, code));
}
