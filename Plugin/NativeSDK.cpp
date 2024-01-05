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


// WARNING: Auto-generated file. Do not modify it by hand.

#include "JavaBytes.h"
#include "JavaEnvironment.h"
#include "JavaString.h"
#include "OrthancBytes.h"
#include "OrthancString.h"

#include <orthanc/OrthancCPlugin.h>

#include <stdexcept>

extern OrthancPluginContext* context_;


JNIEXPORT jint JNI_OrthancPluginCheckVersionAdvanced(JNIEnv* env, jobject sdkObject, jint arg0, jint arg1, jint arg2)
{
  try
  {
    return OrthancPluginCheckVersionAdvanced(context_
      , arg0, arg1, arg2);
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return 0;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return 0;
  }
}


JNIEXPORT jint JNI_OrthancPluginCheckVersion(JNIEnv* env, jobject sdkObject)
{
  try
  {
    return OrthancPluginCheckVersion(context_
      );
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return 0;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return 0;
  }
}


JNIEXPORT void JNI_OrthancPluginLogError(JNIEnv* env, jobject sdkObject, jstring arg0)
{
  try
  {
    JavaString c_arg0(env, arg0);
    OrthancPluginLogError(context_
      , c_arg0.GetValue());
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


JNIEXPORT void JNI_OrthancPluginLogWarning(JNIEnv* env, jobject sdkObject, jstring arg0)
{
  try
  {
    JavaString c_arg0(env, arg0);
    OrthancPluginLogWarning(context_
      , c_arg0.GetValue());
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


JNIEXPORT void JNI_OrthancPluginLogInfo(JNIEnv* env, jobject sdkObject, jstring arg0)
{
  try
  {
    JavaString c_arg0(env, arg0);
    OrthancPluginLogInfo(context_
      , c_arg0.GetValue());
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


JNIEXPORT jbyteArray JNI_OrthancPluginGetDicomForInstance(JNIEnv* env, jobject sdkObject, jstring arg0)
{
  try
  {
    JavaString c_arg0(env, arg0);
    OrthancBytes b;
    OrthancPluginErrorCode code = OrthancPluginGetDicomForInstance(context_, b.GetMemoryBuffer()
      , c_arg0.GetValue());
    if (code == OrthancPluginErrorCode_Success)
    {
      jbyteArray answer = env->NewByteArray(b.GetSize());
      if (answer == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        env->SetByteArrayRegion(answer, 0, b.GetSize(), reinterpret_cast<const jbyte*>(b.GetData()));
        return answer;
      }
    }
    else
    {
      JavaEnvironment::ThrowOrthancException(env, code);
      return NULL;
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jbyteArray JNI_OrthancPluginRestApiGet(JNIEnv* env, jobject sdkObject, jstring arg0)
{
  try
  {
    JavaString c_arg0(env, arg0);
    OrthancBytes b;
    OrthancPluginErrorCode code = OrthancPluginRestApiGet(context_, b.GetMemoryBuffer()
      , c_arg0.GetValue());
    if (code == OrthancPluginErrorCode_Success)
    {
      jbyteArray answer = env->NewByteArray(b.GetSize());
      if (answer == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        env->SetByteArrayRegion(answer, 0, b.GetSize(), reinterpret_cast<const jbyte*>(b.GetData()));
        return answer;
      }
    }
    else
    {
      JavaEnvironment::ThrowOrthancException(env, code);
      return NULL;
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jbyteArray JNI_OrthancPluginRestApiGetAfterPlugins(JNIEnv* env, jobject sdkObject, jstring arg0)
{
  try
  {
    JavaString c_arg0(env, arg0);
    OrthancBytes b;
    OrthancPluginErrorCode code = OrthancPluginRestApiGetAfterPlugins(context_, b.GetMemoryBuffer()
      , c_arg0.GetValue());
    if (code == OrthancPluginErrorCode_Success)
    {
      jbyteArray answer = env->NewByteArray(b.GetSize());
      if (answer == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        env->SetByteArrayRegion(answer, 0, b.GetSize(), reinterpret_cast<const jbyte*>(b.GetData()));
        return answer;
      }
    }
    else
    {
      JavaEnvironment::ThrowOrthancException(env, code);
      return NULL;
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jbyteArray JNI_OrthancPluginRestApiPost(JNIEnv* env, jobject sdkObject, jstring arg0, jbyteArray arg1)
{
  try
  {
    JavaString c_arg0(env, arg0);
    JavaBytes c_arg1(env, arg1);
    OrthancBytes b;
    OrthancPluginErrorCode code = OrthancPluginRestApiPost(context_, b.GetMemoryBuffer()
      , c_arg0.GetValue(), c_arg1.GetData(), c_arg1.GetSize());
    if (code == OrthancPluginErrorCode_Success)
    {
      jbyteArray answer = env->NewByteArray(b.GetSize());
      if (answer == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        env->SetByteArrayRegion(answer, 0, b.GetSize(), reinterpret_cast<const jbyte*>(b.GetData()));
        return answer;
      }
    }
    else
    {
      JavaEnvironment::ThrowOrthancException(env, code);
      return NULL;
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jbyteArray JNI_OrthancPluginRestApiPostAfterPlugins(JNIEnv* env, jobject sdkObject, jstring arg0, jbyteArray arg1)
{
  try
  {
    JavaString c_arg0(env, arg0);
    JavaBytes c_arg1(env, arg1);
    OrthancBytes b;
    OrthancPluginErrorCode code = OrthancPluginRestApiPostAfterPlugins(context_, b.GetMemoryBuffer()
      , c_arg0.GetValue(), c_arg1.GetData(), c_arg1.GetSize());
    if (code == OrthancPluginErrorCode_Success)
    {
      jbyteArray answer = env->NewByteArray(b.GetSize());
      if (answer == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        env->SetByteArrayRegion(answer, 0, b.GetSize(), reinterpret_cast<const jbyte*>(b.GetData()));
        return answer;
      }
    }
    else
    {
      JavaEnvironment::ThrowOrthancException(env, code);
      return NULL;
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT void JNI_OrthancPluginRestApiDelete(JNIEnv* env, jobject sdkObject, jstring arg0)
{
  try
  {
    JavaString c_arg0(env, arg0);
    OrthancPluginErrorCode code = OrthancPluginRestApiDelete(context_
      , c_arg0.GetValue());
    if (code != OrthancPluginErrorCode_Success)
    {
      JavaEnvironment::ThrowOrthancException(env, code);
    }
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


JNIEXPORT void JNI_OrthancPluginRestApiDeleteAfterPlugins(JNIEnv* env, jobject sdkObject, jstring arg0)
{
  try
  {
    JavaString c_arg0(env, arg0);
    OrthancPluginErrorCode code = OrthancPluginRestApiDeleteAfterPlugins(context_
      , c_arg0.GetValue());
    if (code != OrthancPluginErrorCode_Success)
    {
      JavaEnvironment::ThrowOrthancException(env, code);
    }
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


JNIEXPORT jbyteArray JNI_OrthancPluginRestApiPut(JNIEnv* env, jobject sdkObject, jstring arg0, jbyteArray arg1)
{
  try
  {
    JavaString c_arg0(env, arg0);
    JavaBytes c_arg1(env, arg1);
    OrthancBytes b;
    OrthancPluginErrorCode code = OrthancPluginRestApiPut(context_, b.GetMemoryBuffer()
      , c_arg0.GetValue(), c_arg1.GetData(), c_arg1.GetSize());
    if (code == OrthancPluginErrorCode_Success)
    {
      jbyteArray answer = env->NewByteArray(b.GetSize());
      if (answer == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        env->SetByteArrayRegion(answer, 0, b.GetSize(), reinterpret_cast<const jbyte*>(b.GetData()));
        return answer;
      }
    }
    else
    {
      JavaEnvironment::ThrowOrthancException(env, code);
      return NULL;
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jbyteArray JNI_OrthancPluginRestApiPutAfterPlugins(JNIEnv* env, jobject sdkObject, jstring arg0, jbyteArray arg1)
{
  try
  {
    JavaString c_arg0(env, arg0);
    JavaBytes c_arg1(env, arg1);
    OrthancBytes b;
    OrthancPluginErrorCode code = OrthancPluginRestApiPutAfterPlugins(context_, b.GetMemoryBuffer()
      , c_arg0.GetValue(), c_arg1.GetData(), c_arg1.GetSize());
    if (code == OrthancPluginErrorCode_Success)
    {
      jbyteArray answer = env->NewByteArray(b.GetSize());
      if (answer == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        env->SetByteArrayRegion(answer, 0, b.GetSize(), reinterpret_cast<const jbyte*>(b.GetData()));
        return answer;
      }
    }
    else
    {
      JavaEnvironment::ThrowOrthancException(env, code);
      return NULL;
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jstring JNI_OrthancPluginLookupPatient(JNIEnv* env, jobject sdkObject, jstring arg0)
{
  try
  {
    JavaString c_arg0(env, arg0);
    OrthancString s(OrthancPluginLookupPatient(context_
      , c_arg0.GetValue()));
    if (s.GetValue() == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return NULL;
    }
    else
    {
      jstring t = env->NewStringUTF(s.GetValue());
      if (t == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        return t;
      }
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jstring JNI_OrthancPluginLookupStudy(JNIEnv* env, jobject sdkObject, jstring arg0)
{
  try
  {
    JavaString c_arg0(env, arg0);
    OrthancString s(OrthancPluginLookupStudy(context_
      , c_arg0.GetValue()));
    if (s.GetValue() == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return NULL;
    }
    else
    {
      jstring t = env->NewStringUTF(s.GetValue());
      if (t == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        return t;
      }
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jstring JNI_OrthancPluginLookupStudyWithAccessionNumber(JNIEnv* env, jobject sdkObject, jstring arg0)
{
  try
  {
    JavaString c_arg0(env, arg0);
    OrthancString s(OrthancPluginLookupStudyWithAccessionNumber(context_
      , c_arg0.GetValue()));
    if (s.GetValue() == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return NULL;
    }
    else
    {
      jstring t = env->NewStringUTF(s.GetValue());
      if (t == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        return t;
      }
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jstring JNI_OrthancPluginLookupSeries(JNIEnv* env, jobject sdkObject, jstring arg0)
{
  try
  {
    JavaString c_arg0(env, arg0);
    OrthancString s(OrthancPluginLookupSeries(context_
      , c_arg0.GetValue()));
    if (s.GetValue() == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return NULL;
    }
    else
    {
      jstring t = env->NewStringUTF(s.GetValue());
      if (t == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        return t;
      }
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jstring JNI_OrthancPluginLookupInstance(JNIEnv* env, jobject sdkObject, jstring arg0)
{
  try
  {
    JavaString c_arg0(env, arg0);
    OrthancString s(OrthancPluginLookupInstance(context_
      , c_arg0.GetValue()));
    if (s.GetValue() == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return NULL;
    }
    else
    {
      jstring t = env->NewStringUTF(s.GetValue());
      if (t == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        return t;
      }
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jstring JNI_OrthancPluginGetOrthancPath(JNIEnv* env, jobject sdkObject)
{
  try
  {
    OrthancString s(OrthancPluginGetOrthancPath(context_
      ));
    if (s.GetValue() == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return NULL;
    }
    else
    {
      jstring t = env->NewStringUTF(s.GetValue());
      if (t == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        return t;
      }
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jstring JNI_OrthancPluginGetOrthancDirectory(JNIEnv* env, jobject sdkObject)
{
  try
  {
    OrthancString s(OrthancPluginGetOrthancDirectory(context_
      ));
    if (s.GetValue() == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return NULL;
    }
    else
    {
      jstring t = env->NewStringUTF(s.GetValue());
      if (t == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        return t;
      }
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jstring JNI_OrthancPluginGetConfigurationPath(JNIEnv* env, jobject sdkObject)
{
  try
  {
    OrthancString s(OrthancPluginGetConfigurationPath(context_
      ));
    if (s.GetValue() == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return NULL;
    }
    else
    {
      jstring t = env->NewStringUTF(s.GetValue());
      if (t == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        return t;
      }
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT void JNI_OrthancPluginSetRootUri(JNIEnv* env, jobject sdkObject, jstring arg0)
{
  try
  {
    JavaString c_arg0(env, arg0);
    OrthancPluginSetRootUri(context_
      , c_arg0.GetValue());
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


JNIEXPORT void JNI_OrthancPluginSetDescription(JNIEnv* env, jobject sdkObject, jstring arg0)
{
  try
  {
    JavaString c_arg0(env, arg0);
    OrthancPluginSetDescription(context_
      , c_arg0.GetValue());
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


JNIEXPORT void JNI_OrthancPluginExtendOrthancExplorer(JNIEnv* env, jobject sdkObject, jstring arg0)
{
  try
  {
    JavaString c_arg0(env, arg0);
    OrthancPluginExtendOrthancExplorer(context_
      , c_arg0.GetValue());
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


JNIEXPORT jstring JNI_OrthancPluginGetGlobalProperty(JNIEnv* env, jobject sdkObject, jint arg0, jstring arg1)
{
  try
  {
    JavaString c_arg1(env, arg1);
    OrthancString s(OrthancPluginGetGlobalProperty(context_
      , arg0, c_arg1.GetValue()));
    if (s.GetValue() == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return NULL;
    }
    else
    {
      jstring t = env->NewStringUTF(s.GetValue());
      if (t == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        return t;
      }
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT void JNI_OrthancPluginSetGlobalProperty(JNIEnv* env, jobject sdkObject, jint arg0, jstring arg1)
{
  try
  {
    JavaString c_arg1(env, arg1);
    OrthancPluginErrorCode code = OrthancPluginSetGlobalProperty(context_
      , arg0, c_arg1.GetValue());
    if (code != OrthancPluginErrorCode_Success)
    {
      JavaEnvironment::ThrowOrthancException(env, code);
    }
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


JNIEXPORT jint JNI_OrthancPluginGetCommandLineArgumentsCount(JNIEnv* env, jobject sdkObject)
{
  try
  {
    return OrthancPluginGetCommandLineArgumentsCount(context_
      );
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return 0;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return 0;
  }
}


JNIEXPORT jstring JNI_OrthancPluginGetCommandLineArgument(JNIEnv* env, jobject sdkObject, jint arg0)
{
  try
  {
    OrthancString s(OrthancPluginGetCommandLineArgument(context_
      , arg0));
    if (s.GetValue() == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return NULL;
    }
    else
    {
      jstring t = env->NewStringUTF(s.GetValue());
      if (t == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        return t;
      }
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jint JNI_OrthancPluginGetExpectedDatabaseVersion(JNIEnv* env, jobject sdkObject)
{
  try
  {
    return OrthancPluginGetExpectedDatabaseVersion(context_
      );
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return 0;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return 0;
  }
}


JNIEXPORT jstring JNI_OrthancPluginGetConfiguration(JNIEnv* env, jobject sdkObject)
{
  try
  {
    OrthancString s(OrthancPluginGetConfiguration(context_
      ));
    if (s.GetValue() == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return NULL;
    }
    else
    {
      jstring t = env->NewStringUTF(s.GetValue());
      if (t == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        return t;
      }
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jbyteArray JNI_OrthancPluginBufferCompression(JNIEnv* env, jobject sdkObject, jbyteArray arg0, jint arg2, jbyte arg3)
{
  try
  {
    JavaBytes c_arg0(env, arg0);
    OrthancBytes b;
    OrthancPluginErrorCode code = OrthancPluginBufferCompression(context_, b.GetMemoryBuffer()
      , c_arg0.GetData(), c_arg0.GetSize(), static_cast<OrthancPluginCompressionType>(arg2), arg3);
    if (code == OrthancPluginErrorCode_Success)
    {
      jbyteArray answer = env->NewByteArray(b.GetSize());
      if (answer == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        env->SetByteArrayRegion(answer, 0, b.GetSize(), reinterpret_cast<const jbyte*>(b.GetData()));
        return answer;
      }
    }
    else
    {
      JavaEnvironment::ThrowOrthancException(env, code);
      return NULL;
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jbyteArray JNI_OrthancPluginReadFile(JNIEnv* env, jobject sdkObject, jstring arg0)
{
  try
  {
    JavaString c_arg0(env, arg0);
    OrthancBytes b;
    OrthancPluginErrorCode code = OrthancPluginReadFile(context_, b.GetMemoryBuffer()
      , c_arg0.GetValue());
    if (code == OrthancPluginErrorCode_Success)
    {
      jbyteArray answer = env->NewByteArray(b.GetSize());
      if (answer == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        env->SetByteArrayRegion(answer, 0, b.GetSize(), reinterpret_cast<const jbyte*>(b.GetData()));
        return answer;
      }
    }
    else
    {
      JavaEnvironment::ThrowOrthancException(env, code);
      return NULL;
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT void JNI_OrthancPluginWriteFile(JNIEnv* env, jobject sdkObject, jstring arg0, jbyteArray arg1)
{
  try
  {
    JavaString c_arg0(env, arg0);
    JavaBytes c_arg1(env, arg1);
    OrthancPluginErrorCode code = OrthancPluginWriteFile(context_
      , c_arg0.GetValue(), c_arg1.GetData(), c_arg1.GetSize());
    if (code != OrthancPluginErrorCode_Success)
    {
      JavaEnvironment::ThrowOrthancException(env, code);
    }
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


JNIEXPORT jstring JNI_OrthancPluginGetErrorDescription(JNIEnv* env, jobject sdkObject, jint arg0)
{
  try
  {
    const char* s = OrthancPluginGetErrorDescription(context_
      , static_cast<OrthancPluginErrorCode>(arg0));
    if (s == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return NULL;
    }
    else
    {
      return env->NewStringUTF(s);
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jlong JNI_OrthancPluginUncompressImage(JNIEnv* env, jobject sdkObject, jbyteArray arg0, jint arg2)
{
  try
  {
    JavaBytes c_arg0(env, arg0);
    OrthancPluginImage* answer = OrthancPluginUncompressImage(context_
      , c_arg0.GetData(), c_arg0.GetSize(), static_cast<OrthancPluginImageFormat>(arg2));
    if (answer == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return 0;
    }
    else
    {
      return reinterpret_cast<intptr_t>(answer);
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return 0;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return 0;
  }
}


JNIEXPORT jbyteArray JNI_OrthancPluginCompressPngImage(JNIEnv* env, jobject sdkObject, jint arg0, jint arg1, jint arg2, jint arg3, jbyteArray arg4)
{
  try
  {
    JavaBytes c_arg4(env, arg4);
    OrthancBytes b;
    OrthancPluginErrorCode code = OrthancPluginCompressPngImage(context_, b.GetMemoryBuffer()
      , static_cast<OrthancPluginPixelFormat>(arg0), arg1, arg2, arg3, c_arg4.GetData());
    if (code == OrthancPluginErrorCode_Success)
    {
      jbyteArray answer = env->NewByteArray(b.GetSize());
      if (answer == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        env->SetByteArrayRegion(answer, 0, b.GetSize(), reinterpret_cast<const jbyte*>(b.GetData()));
        return answer;
      }
    }
    else
    {
      JavaEnvironment::ThrowOrthancException(env, code);
      return NULL;
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jbyteArray JNI_OrthancPluginCompressJpegImage(JNIEnv* env, jobject sdkObject, jint arg0, jint arg1, jint arg2, jint arg3, jbyteArray arg4, jbyte arg5)
{
  try
  {
    JavaBytes c_arg4(env, arg4);
    OrthancBytes b;
    OrthancPluginErrorCode code = OrthancPluginCompressJpegImage(context_, b.GetMemoryBuffer()
      , static_cast<OrthancPluginPixelFormat>(arg0), arg1, arg2, arg3, c_arg4.GetData(), arg5);
    if (code == OrthancPluginErrorCode_Success)
    {
      jbyteArray answer = env->NewByteArray(b.GetSize());
      if (answer == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        env->SetByteArrayRegion(answer, 0, b.GetSize(), reinterpret_cast<const jbyte*>(b.GetData()));
        return answer;
      }
    }
    else
    {
      JavaEnvironment::ThrowOrthancException(env, code);
      return NULL;
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jbyteArray JNI_OrthancPluginHttpGet(JNIEnv* env, jobject sdkObject, jstring arg0, jstring arg1, jstring arg2)
{
  try
  {
    JavaString c_arg0(env, arg0);
    JavaString c_arg1(env, arg1);
    JavaString c_arg2(env, arg2);
    OrthancBytes b;
    OrthancPluginErrorCode code = OrthancPluginHttpGet(context_, b.GetMemoryBuffer()
      , c_arg0.GetValue(), c_arg1.GetValue(), c_arg2.GetValue());
    if (code == OrthancPluginErrorCode_Success)
    {
      jbyteArray answer = env->NewByteArray(b.GetSize());
      if (answer == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        env->SetByteArrayRegion(answer, 0, b.GetSize(), reinterpret_cast<const jbyte*>(b.GetData()));
        return answer;
      }
    }
    else
    {
      JavaEnvironment::ThrowOrthancException(env, code);
      return NULL;
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jbyteArray JNI_OrthancPluginHttpPost(JNIEnv* env, jobject sdkObject, jstring arg0, jbyteArray arg1, jstring arg3, jstring arg4)
{
  try
  {
    JavaString c_arg0(env, arg0);
    JavaBytes c_arg1(env, arg1);
    JavaString c_arg3(env, arg3);
    JavaString c_arg4(env, arg4);
    OrthancBytes b;
    OrthancPluginErrorCode code = OrthancPluginHttpPost(context_, b.GetMemoryBuffer()
      , c_arg0.GetValue(), c_arg1.GetData(), c_arg1.GetSize(), c_arg3.GetValue(), c_arg4.GetValue());
    if (code == OrthancPluginErrorCode_Success)
    {
      jbyteArray answer = env->NewByteArray(b.GetSize());
      if (answer == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        env->SetByteArrayRegion(answer, 0, b.GetSize(), reinterpret_cast<const jbyte*>(b.GetData()));
        return answer;
      }
    }
    else
    {
      JavaEnvironment::ThrowOrthancException(env, code);
      return NULL;
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jbyteArray JNI_OrthancPluginHttpPut(JNIEnv* env, jobject sdkObject, jstring arg0, jbyteArray arg1, jstring arg3, jstring arg4)
{
  try
  {
    JavaString c_arg0(env, arg0);
    JavaBytes c_arg1(env, arg1);
    JavaString c_arg3(env, arg3);
    JavaString c_arg4(env, arg4);
    OrthancBytes b;
    OrthancPluginErrorCode code = OrthancPluginHttpPut(context_, b.GetMemoryBuffer()
      , c_arg0.GetValue(), c_arg1.GetData(), c_arg1.GetSize(), c_arg3.GetValue(), c_arg4.GetValue());
    if (code == OrthancPluginErrorCode_Success)
    {
      jbyteArray answer = env->NewByteArray(b.GetSize());
      if (answer == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        env->SetByteArrayRegion(answer, 0, b.GetSize(), reinterpret_cast<const jbyte*>(b.GetData()));
        return answer;
      }
    }
    else
    {
      JavaEnvironment::ThrowOrthancException(env, code);
      return NULL;
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT void JNI_OrthancPluginHttpDelete(JNIEnv* env, jobject sdkObject, jstring arg0, jstring arg1, jstring arg2)
{
  try
  {
    JavaString c_arg0(env, arg0);
    JavaString c_arg1(env, arg1);
    JavaString c_arg2(env, arg2);
    OrthancPluginErrorCode code = OrthancPluginHttpDelete(context_
      , c_arg0.GetValue(), c_arg1.GetValue(), c_arg2.GetValue());
    if (code != OrthancPluginErrorCode_Success)
    {
      JavaEnvironment::ThrowOrthancException(env, code);
    }
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


JNIEXPORT jint JNI_OrthancPluginGetFontsCount(JNIEnv* env, jobject sdkObject)
{
  try
  {
    return OrthancPluginGetFontsCount(context_
      );
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return 0;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return 0;
  }
}


JNIEXPORT jstring JNI_OrthancPluginGetFontName(JNIEnv* env, jobject sdkObject, jint arg0)
{
  try
  {
    const char* s = OrthancPluginGetFontName(context_
      , arg0);
    if (s == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return NULL;
    }
    else
    {
      return env->NewStringUTF(s);
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jint JNI_OrthancPluginGetFontSize(JNIEnv* env, jobject sdkObject, jint arg0)
{
  try
  {
    return OrthancPluginGetFontSize(context_
      , arg0);
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return 0;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return 0;
  }
}


JNIEXPORT void JNI_OrthancPluginRegisterErrorCode(JNIEnv* env, jobject sdkObject, jint arg0, jshort arg1, jstring arg2)
{
  try
  {
    JavaString c_arg2(env, arg2);
    OrthancPluginErrorCode code = OrthancPluginRegisterErrorCode(context_
      , arg0, arg1, c_arg2.GetValue());
    if (code != OrthancPluginErrorCode_Success)
    {
      JavaEnvironment::ThrowOrthancException(env, code);
    }
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


JNIEXPORT void JNI_OrthancPluginRegisterDictionaryTag(JNIEnv* env, jobject sdkObject, jshort arg0, jshort arg1, jint arg2, jstring arg3, jint arg4, jint arg5)
{
  try
  {
    JavaString c_arg3(env, arg3);
    OrthancPluginErrorCode code = OrthancPluginRegisterDictionaryTag(context_
      , arg0, arg1, static_cast<OrthancPluginValueRepresentation>(arg2), c_arg3.GetValue(), arg4, arg5);
    if (code != OrthancPluginErrorCode_Success)
    {
      JavaEnvironment::ThrowOrthancException(env, code);
    }
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


JNIEXPORT void JNI_OrthancPluginRegisterPrivateDictionaryTag(JNIEnv* env, jobject sdkObject, jshort arg0, jshort arg1, jint arg2, jstring arg3, jint arg4, jint arg5, jstring arg6)
{
  try
  {
    JavaString c_arg3(env, arg3);
    JavaString c_arg6(env, arg6);
    OrthancPluginErrorCode code = OrthancPluginRegisterPrivateDictionaryTag(context_
      , arg0, arg1, static_cast<OrthancPluginValueRepresentation>(arg2), c_arg3.GetValue(), arg4, arg5, c_arg6.GetValue());
    if (code != OrthancPluginErrorCode_Success)
    {
      JavaEnvironment::ThrowOrthancException(env, code);
    }
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


JNIEXPORT jstring JNI_OrthancPluginDicomBufferToJson(JNIEnv* env, jobject sdkObject, jbyteArray arg0, jint arg2, jint arg3, jint arg4)
{
  try
  {
    JavaBytes c_arg0(env, arg0);
    OrthancString s(OrthancPluginDicomBufferToJson(context_
      , c_arg0.GetData(), c_arg0.GetSize(), static_cast<OrthancPluginDicomToJsonFormat>(arg2), static_cast<OrthancPluginDicomToJsonFlags>(arg3), arg4));
    if (s.GetValue() == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return NULL;
    }
    else
    {
      jstring t = env->NewStringUTF(s.GetValue());
      if (t == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        return t;
      }
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jstring JNI_OrthancPluginDicomInstanceToJson(JNIEnv* env, jobject sdkObject, jstring arg0, jint arg1, jint arg2, jint arg3)
{
  try
  {
    JavaString c_arg0(env, arg0);
    OrthancString s(OrthancPluginDicomInstanceToJson(context_
      , c_arg0.GetValue(), static_cast<OrthancPluginDicomToJsonFormat>(arg1), static_cast<OrthancPluginDicomToJsonFlags>(arg2), arg3));
    if (s.GetValue() == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return NULL;
    }
    else
    {
      jstring t = env->NewStringUTF(s.GetValue());
      if (t == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        return t;
      }
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jbyteArray JNI_OrthancPluginCreateDicom(JNIEnv* env, jobject sdkObject, jstring arg0, jlong arg1, jint arg2)
{
  try
  {
    JavaString c_arg0(env, arg0);
    OrthancBytes b;
    OrthancPluginErrorCode code = OrthancPluginCreateDicom(context_, b.GetMemoryBuffer()
      , c_arg0.GetValue(), reinterpret_cast<OrthancPluginImage*>(static_cast<intptr_t>(arg1)), static_cast<OrthancPluginCreateDicomFlags>(arg2));
    if (code == OrthancPluginErrorCode_Success)
    {
      jbyteArray answer = env->NewByteArray(b.GetSize());
      if (answer == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        env->SetByteArrayRegion(answer, 0, b.GetSize(), reinterpret_cast<const jbyte*>(b.GetData()));
        return answer;
      }
    }
    else
    {
      JavaEnvironment::ThrowOrthancException(env, code);
      return NULL;
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jlong JNI_OrthancPluginCreateImage(JNIEnv* env, jobject sdkObject, jint arg0, jint arg1, jint arg2)
{
  try
  {
    OrthancPluginImage* answer = OrthancPluginCreateImage(context_
      , static_cast<OrthancPluginPixelFormat>(arg0), arg1, arg2);
    if (answer == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return 0;
    }
    else
    {
      return reinterpret_cast<intptr_t>(answer);
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return 0;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return 0;
  }
}


JNIEXPORT jlong JNI_OrthancPluginDecodeDicomImage(JNIEnv* env, jobject sdkObject, jbyteArray arg0, jint arg2)
{
  try
  {
    JavaBytes c_arg0(env, arg0);
    OrthancPluginImage* answer = OrthancPluginDecodeDicomImage(context_
      , c_arg0.GetData(), c_arg0.GetSize(), arg2);
    if (answer == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return 0;
    }
    else
    {
      return reinterpret_cast<intptr_t>(answer);
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return 0;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return 0;
  }
}


JNIEXPORT jstring JNI_OrthancPluginComputeMd5(JNIEnv* env, jobject sdkObject, jbyteArray arg0)
{
  try
  {
    JavaBytes c_arg0(env, arg0);
    OrthancString s(OrthancPluginComputeMd5(context_
      , c_arg0.GetData(), c_arg0.GetSize()));
    if (s.GetValue() == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return NULL;
    }
    else
    {
      jstring t = env->NewStringUTF(s.GetValue());
      if (t == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        return t;
      }
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jstring JNI_OrthancPluginComputeSha1(JNIEnv* env, jobject sdkObject, jbyteArray arg0)
{
  try
  {
    JavaBytes c_arg0(env, arg0);
    OrthancString s(OrthancPluginComputeSha1(context_
      , c_arg0.GetData(), c_arg0.GetSize()));
    if (s.GetValue() == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return NULL;
    }
    else
    {
      jstring t = env->NewStringUTF(s.GetValue());
      if (t == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        return t;
      }
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jstring JNI_OrthancPluginGenerateUuid(JNIEnv* env, jobject sdkObject)
{
  try
  {
    OrthancString s(OrthancPluginGenerateUuid(context_
      ));
    if (s.GetValue() == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return NULL;
    }
    else
    {
      jstring t = env->NewStringUTF(s.GetValue());
      if (t == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        return t;
      }
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jlong JNI_OrthancPluginCreateFindMatcher(JNIEnv* env, jobject sdkObject, jbyteArray arg0)
{
  try
  {
    JavaBytes c_arg0(env, arg0);
    OrthancPluginFindMatcher* answer = OrthancPluginCreateFindMatcher(context_
      , c_arg0.GetData(), c_arg0.GetSize());
    if (answer == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return 0;
    }
    else
    {
      return reinterpret_cast<intptr_t>(answer);
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return 0;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return 0;
  }
}


JNIEXPORT jlong JNI_OrthancPluginGetPeers(JNIEnv* env, jobject sdkObject)
{
  try
  {
    OrthancPluginPeers* answer = OrthancPluginGetPeers(context_
      );
    if (answer == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return 0;
    }
    else
    {
      return reinterpret_cast<intptr_t>(answer);
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return 0;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return 0;
  }
}


JNIEXPORT jstring JNI_OrthancPluginAutodetectMimeType(JNIEnv* env, jobject sdkObject, jstring arg0)
{
  try
  {
    JavaString c_arg0(env, arg0);
    const char* s = OrthancPluginAutodetectMimeType(context_
      , c_arg0.GetValue());
    if (s == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return NULL;
    }
    else
    {
      return env->NewStringUTF(s);
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT void JNI_OrthancPluginSetMetricsValue(JNIEnv* env, jobject sdkObject, jstring arg0, jfloat arg1, jint arg2)
{
  try
  {
    JavaString c_arg0(env, arg0);
    OrthancPluginSetMetricsValue(context_
      , c_arg0.GetValue(), arg1, static_cast<OrthancPluginMetricsType>(arg2));
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


JNIEXPORT jstring JNI_OrthancPluginGetTagName(JNIEnv* env, jobject sdkObject, jshort arg0, jshort arg1, jstring arg2)
{
  try
  {
    JavaString c_arg2(env, arg2);
    OrthancString s(OrthancPluginGetTagName(context_
      , arg0, arg1, c_arg2.GetValue()));
    if (s.GetValue() == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return NULL;
    }
    else
    {
      jstring t = env->NewStringUTF(s.GetValue());
      if (t == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        return t;
      }
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jlong JNI_OrthancPluginCreateDicomInstance(JNIEnv* env, jobject sdkObject, jbyteArray arg0)
{
  try
  {
    JavaBytes c_arg0(env, arg0);
    OrthancPluginDicomInstance* answer = OrthancPluginCreateDicomInstance(context_
      , c_arg0.GetData(), c_arg0.GetSize());
    if (answer == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return 0;
    }
    else
    {
      return reinterpret_cast<intptr_t>(answer);
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return 0;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return 0;
  }
}


JNIEXPORT jlong JNI_OrthancPluginTranscodeDicomInstance(JNIEnv* env, jobject sdkObject, jbyteArray arg0, jstring arg2)
{
  try
  {
    JavaBytes c_arg0(env, arg0);
    JavaString c_arg2(env, arg2);
    OrthancPluginDicomInstance* answer = OrthancPluginTranscodeDicomInstance(context_
      , c_arg0.GetData(), c_arg0.GetSize(), c_arg2.GetValue());
    if (answer == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return 0;
    }
    else
    {
      return reinterpret_cast<intptr_t>(answer);
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return 0;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return 0;
  }
}


JNIEXPORT jstring JNI_OrthancPluginGenerateRestApiAuthorizationToken(JNIEnv* env, jobject sdkObject)
{
  try
  {
    OrthancString s(OrthancPluginGenerateRestApiAuthorizationToken(context_
      ));
    if (s.GetValue() == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return NULL;
    }
    else
    {
      jstring t = env->NewStringUTF(s.GetValue());
      if (t == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        return t;
      }
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jbyteArray JNI_OrthancPluginCreateDicom2(JNIEnv* env, jobject sdkObject, jstring arg0, jlong arg1, jint arg2, jstring arg3)
{
  try
  {
    JavaString c_arg0(env, arg0);
    JavaString c_arg3(env, arg3);
    OrthancBytes b;
    OrthancPluginErrorCode code = OrthancPluginCreateDicom2(context_, b.GetMemoryBuffer()
      , c_arg0.GetValue(), reinterpret_cast<OrthancPluginImage*>(static_cast<intptr_t>(arg1)), static_cast<OrthancPluginCreateDicomFlags>(arg2), c_arg3.GetValue());
    if (code == OrthancPluginErrorCode_Success)
    {
      jbyteArray answer = env->NewByteArray(b.GetSize());
      if (answer == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        env->SetByteArrayRegion(answer, 0, b.GetSize(), reinterpret_cast<const jbyte*>(b.GetData()));
        return answer;
      }
    }
    else
    {
      JavaEnvironment::ThrowOrthancException(env, code);
      return NULL;
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT void JNI_OrthancPluginFreeDicomInstance(JNIEnv* env, jobject sdkObject, jlong self)
{
  try
  {
    OrthancPluginFreeDicomInstance(context_
      , reinterpret_cast<OrthancPluginDicomInstance*>(static_cast<intptr_t>(self))
      );
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


JNIEXPORT jstring JNI_OrthancPluginGetInstanceRemoteAet(JNIEnv* env, jobject sdkObject, jlong self)
{
  try
  {
    const char* s = OrthancPluginGetInstanceRemoteAet(context_
      , reinterpret_cast<OrthancPluginDicomInstance*>(static_cast<intptr_t>(self))
      );
    if (s == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return NULL;
    }
    else
    {
      return env->NewStringUTF(s);
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jlong JNI_OrthancPluginGetInstanceSize(JNIEnv* env, jobject sdkObject, jlong self)
{
  try
  {
    return OrthancPluginGetInstanceSize(context_
      , reinterpret_cast<OrthancPluginDicomInstance*>(static_cast<intptr_t>(self))
      );
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return 0;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return 0;
  }
}


JNIEXPORT jstring JNI_OrthancPluginGetInstanceJson(JNIEnv* env, jobject sdkObject, jlong self)
{
  try
  {
    OrthancString s(OrthancPluginGetInstanceJson(context_
      , reinterpret_cast<OrthancPluginDicomInstance*>(static_cast<intptr_t>(self))
      ));
    if (s.GetValue() == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return NULL;
    }
    else
    {
      jstring t = env->NewStringUTF(s.GetValue());
      if (t == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        return t;
      }
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jstring JNI_OrthancPluginGetInstanceSimplifiedJson(JNIEnv* env, jobject sdkObject, jlong self)
{
  try
  {
    OrthancString s(OrthancPluginGetInstanceSimplifiedJson(context_
      , reinterpret_cast<OrthancPluginDicomInstance*>(static_cast<intptr_t>(self))
      ));
    if (s.GetValue() == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return NULL;
    }
    else
    {
      jstring t = env->NewStringUTF(s.GetValue());
      if (t == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        return t;
      }
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jint JNI_OrthancPluginHasInstanceMetadata(JNIEnv* env, jobject sdkObject, jlong self, jstring arg0)
{
  try
  {
    JavaString c_arg0(env, arg0);
    return OrthancPluginHasInstanceMetadata(context_
      , reinterpret_cast<OrthancPluginDicomInstance*>(static_cast<intptr_t>(self))
      , c_arg0.GetValue());
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return 0;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return 0;
  }
}


JNIEXPORT jstring JNI_OrthancPluginGetInstanceMetadata(JNIEnv* env, jobject sdkObject, jlong self, jstring arg0)
{
  try
  {
    JavaString c_arg0(env, arg0);
    const char* s = OrthancPluginGetInstanceMetadata(context_
      , reinterpret_cast<OrthancPluginDicomInstance*>(static_cast<intptr_t>(self))
      , c_arg0.GetValue());
    if (s == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return NULL;
    }
    else
    {
      return env->NewStringUTF(s);
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jint JNI_OrthancPluginGetInstanceOrigin(JNIEnv* env, jobject sdkObject, jlong self)
{
  try
  {
    return OrthancPluginGetInstanceOrigin(context_
      , reinterpret_cast<OrthancPluginDicomInstance*>(static_cast<intptr_t>(self))
      );
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return 0;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return 0;
  }
}


JNIEXPORT jstring JNI_OrthancPluginGetInstanceTransferSyntaxUid(JNIEnv* env, jobject sdkObject, jlong self)
{
  try
  {
    OrthancString s(OrthancPluginGetInstanceTransferSyntaxUid(context_
      , reinterpret_cast<OrthancPluginDicomInstance*>(static_cast<intptr_t>(self))
      ));
    if (s.GetValue() == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return NULL;
    }
    else
    {
      jstring t = env->NewStringUTF(s.GetValue());
      if (t == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        return t;
      }
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jint JNI_OrthancPluginHasInstancePixelData(JNIEnv* env, jobject sdkObject, jlong self)
{
  try
  {
    return OrthancPluginHasInstancePixelData(context_
      , reinterpret_cast<OrthancPluginDicomInstance*>(static_cast<intptr_t>(self))
      );
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return 0;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return 0;
  }
}


JNIEXPORT jint JNI_OrthancPluginGetInstanceFramesCount(JNIEnv* env, jobject sdkObject, jlong self)
{
  try
  {
    return OrthancPluginGetInstanceFramesCount(context_
      , reinterpret_cast<OrthancPluginDicomInstance*>(static_cast<intptr_t>(self))
      );
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return 0;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return 0;
  }
}


JNIEXPORT jbyteArray JNI_OrthancPluginGetInstanceRawFrame(JNIEnv* env, jobject sdkObject, jlong self, jint arg0)
{
  try
  {
    OrthancBytes b;
    OrthancPluginErrorCode code = OrthancPluginGetInstanceRawFrame(context_, b.GetMemoryBuffer()
      , reinterpret_cast<OrthancPluginDicomInstance*>(static_cast<intptr_t>(self))
      , arg0);
    if (code == OrthancPluginErrorCode_Success)
    {
      jbyteArray answer = env->NewByteArray(b.GetSize());
      if (answer == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        env->SetByteArrayRegion(answer, 0, b.GetSize(), reinterpret_cast<const jbyte*>(b.GetData()));
        return answer;
      }
    }
    else
    {
      JavaEnvironment::ThrowOrthancException(env, code);
      return NULL;
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jlong JNI_OrthancPluginGetInstanceDecodedFrame(JNIEnv* env, jobject sdkObject, jlong self, jint arg0)
{
  try
  {
    OrthancPluginImage* answer = OrthancPluginGetInstanceDecodedFrame(context_
      , reinterpret_cast<OrthancPluginDicomInstance*>(static_cast<intptr_t>(self))
      , arg0);
    if (answer == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return 0;
    }
    else
    {
      return reinterpret_cast<intptr_t>(answer);
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return 0;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return 0;
  }
}


JNIEXPORT jbyteArray JNI_OrthancPluginSerializeDicomInstance(JNIEnv* env, jobject sdkObject, jlong self)
{
  try
  {
    OrthancBytes b;
    OrthancPluginErrorCode code = OrthancPluginSerializeDicomInstance(context_, b.GetMemoryBuffer()
      , reinterpret_cast<OrthancPluginDicomInstance*>(static_cast<intptr_t>(self))
      );
    if (code == OrthancPluginErrorCode_Success)
    {
      jbyteArray answer = env->NewByteArray(b.GetSize());
      if (answer == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        env->SetByteArrayRegion(answer, 0, b.GetSize(), reinterpret_cast<const jbyte*>(b.GetData()));
        return answer;
      }
    }
    else
    {
      JavaEnvironment::ThrowOrthancException(env, code);
      return NULL;
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jstring JNI_OrthancPluginGetInstanceAdvancedJson(JNIEnv* env, jobject sdkObject, jlong self, jint arg0, jint arg1, jint arg2)
{
  try
  {
    OrthancString s(OrthancPluginGetInstanceAdvancedJson(context_
      , reinterpret_cast<OrthancPluginDicomInstance*>(static_cast<intptr_t>(self))
      , static_cast<OrthancPluginDicomToJsonFormat>(arg0), static_cast<OrthancPluginDicomToJsonFlags>(arg1), arg2));
    if (s.GetValue() == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return NULL;
    }
    else
    {
      jstring t = env->NewStringUTF(s.GetValue());
      if (t == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        return t;
      }
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT void JNI_OrthancPluginFindAddAnswer(JNIEnv* env, jobject sdkObject, jlong self, jbyteArray arg0)
{
  try
  {
    JavaBytes c_arg0(env, arg0);
    OrthancPluginErrorCode code = OrthancPluginFindAddAnswer(context_
      , reinterpret_cast<OrthancPluginFindAnswers*>(static_cast<intptr_t>(self))
      , c_arg0.GetData(), c_arg0.GetSize());
    if (code != OrthancPluginErrorCode_Success)
    {
      JavaEnvironment::ThrowOrthancException(env, code);
    }
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


JNIEXPORT void JNI_OrthancPluginFindMarkIncomplete(JNIEnv* env, jobject sdkObject, jlong self)
{
  try
  {
    OrthancPluginErrorCode code = OrthancPluginFindMarkIncomplete(context_
      , reinterpret_cast<OrthancPluginFindAnswers*>(static_cast<intptr_t>(self))
      );
    if (code != OrthancPluginErrorCode_Success)
    {
      JavaEnvironment::ThrowOrthancException(env, code);
    }
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


JNIEXPORT void JNI_OrthancPluginFreeFindMatcher(JNIEnv* env, jobject sdkObject, jlong self)
{
  try
  {
    OrthancPluginFreeFindMatcher(context_
      , reinterpret_cast<OrthancPluginFindMatcher*>(static_cast<intptr_t>(self))
      );
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


JNIEXPORT jint JNI_OrthancPluginFindMatcherIsMatch(JNIEnv* env, jobject sdkObject, jlong self, jbyteArray arg0)
{
  try
  {
    JavaBytes c_arg0(env, arg0);
    return OrthancPluginFindMatcherIsMatch(context_
      , reinterpret_cast<OrthancPluginFindMatcher*>(static_cast<intptr_t>(self))
      , c_arg0.GetData(), c_arg0.GetSize());
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return 0;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return 0;
  }
}


JNIEXPORT jint JNI_OrthancPluginGetFindQuerySize(JNIEnv* env, jobject sdkObject, jlong self)
{
  try
  {
    return OrthancPluginGetFindQuerySize(context_
      , reinterpret_cast<OrthancPluginFindQuery*>(static_cast<intptr_t>(self))
      );
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return 0;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return 0;
  }
}


JNIEXPORT jstring JNI_OrthancPluginGetFindQueryTagName(JNIEnv* env, jobject sdkObject, jlong self, jint arg0)
{
  try
  {
    OrthancString s(OrthancPluginGetFindQueryTagName(context_
      , reinterpret_cast<OrthancPluginFindQuery*>(static_cast<intptr_t>(self))
      , arg0));
    if (s.GetValue() == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return NULL;
    }
    else
    {
      jstring t = env->NewStringUTF(s.GetValue());
      if (t == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        return t;
      }
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jstring JNI_OrthancPluginGetFindQueryValue(JNIEnv* env, jobject sdkObject, jlong self, jint arg0)
{
  try
  {
    OrthancString s(OrthancPluginGetFindQueryValue(context_
      , reinterpret_cast<OrthancPluginFindQuery*>(static_cast<intptr_t>(self))
      , arg0));
    if (s.GetValue() == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return NULL;
    }
    else
    {
      jstring t = env->NewStringUTF(s.GetValue());
      if (t == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        return t;
      }
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT void JNI_OrthancPluginFreeImage(JNIEnv* env, jobject sdkObject, jlong self)
{
  try
  {
    OrthancPluginFreeImage(context_
      , reinterpret_cast<OrthancPluginImage*>(static_cast<intptr_t>(self))
      );
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


JNIEXPORT jint JNI_OrthancPluginGetImagePixelFormat(JNIEnv* env, jobject sdkObject, jlong self)
{
  try
  {
    return OrthancPluginGetImagePixelFormat(context_
      , reinterpret_cast<OrthancPluginImage*>(static_cast<intptr_t>(self))
      );
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return 0;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return 0;
  }
}


JNIEXPORT jint JNI_OrthancPluginGetImageWidth(JNIEnv* env, jobject sdkObject, jlong self)
{
  try
  {
    return OrthancPluginGetImageWidth(context_
      , reinterpret_cast<OrthancPluginImage*>(static_cast<intptr_t>(self))
      );
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return 0;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return 0;
  }
}


JNIEXPORT jint JNI_OrthancPluginGetImageHeight(JNIEnv* env, jobject sdkObject, jlong self)
{
  try
  {
    return OrthancPluginGetImageHeight(context_
      , reinterpret_cast<OrthancPluginImage*>(static_cast<intptr_t>(self))
      );
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return 0;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return 0;
  }
}


JNIEXPORT jint JNI_OrthancPluginGetImagePitch(JNIEnv* env, jobject sdkObject, jlong self)
{
  try
  {
    return OrthancPluginGetImagePitch(context_
      , reinterpret_cast<OrthancPluginImage*>(static_cast<intptr_t>(self))
      );
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return 0;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return 0;
  }
}


JNIEXPORT jlong JNI_OrthancPluginConvertPixelFormat(JNIEnv* env, jobject sdkObject, jlong self, jint arg0)
{
  try
  {
    OrthancPluginImage* answer = OrthancPluginConvertPixelFormat(context_
      , reinterpret_cast<OrthancPluginImage*>(static_cast<intptr_t>(self))
      , static_cast<OrthancPluginPixelFormat>(arg0));
    if (answer == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return 0;
    }
    else
    {
      return reinterpret_cast<intptr_t>(answer);
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return 0;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return 0;
  }
}


JNIEXPORT void JNI_OrthancPluginDrawText(JNIEnv* env, jobject sdkObject, jlong self, jint arg0, jstring arg1, jint arg2, jint arg3, jbyte arg4, jbyte arg5, jbyte arg6)
{
  try
  {
    JavaString c_arg1(env, arg1);
    OrthancPluginErrorCode code = OrthancPluginDrawText(context_
      , reinterpret_cast<OrthancPluginImage*>(static_cast<intptr_t>(self))
      , arg0, c_arg1.GetValue(), arg2, arg3, arg4, arg5, arg6);
    if (code != OrthancPluginErrorCode_Success)
    {
      JavaEnvironment::ThrowOrthancException(env, code);
    }
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


JNIEXPORT void JNI_OrthancPluginFreeJob(JNIEnv* env, jobject sdkObject, jlong self)
{
  try
  {
    OrthancPluginFreeJob(context_
      , reinterpret_cast<OrthancPluginJob*>(static_cast<intptr_t>(self))
      );
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


JNIEXPORT jstring JNI_OrthancPluginSubmitJob(JNIEnv* env, jobject sdkObject, jlong self, jint arg0)
{
  try
  {
    OrthancString s(OrthancPluginSubmitJob(context_
      , reinterpret_cast<OrthancPluginJob*>(static_cast<intptr_t>(self))
      , arg0));
    if (s.GetValue() == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return NULL;
    }
    else
    {
      jstring t = env->NewStringUTF(s.GetValue());
      if (t == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        return t;
      }
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT void JNI_OrthancPluginFreePeers(JNIEnv* env, jobject sdkObject, jlong self)
{
  try
  {
    OrthancPluginFreePeers(context_
      , reinterpret_cast<OrthancPluginPeers*>(static_cast<intptr_t>(self))
      );
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


JNIEXPORT jint JNI_OrthancPluginGetPeersCount(JNIEnv* env, jobject sdkObject, jlong self)
{
  try
  {
    return OrthancPluginGetPeersCount(context_
      , reinterpret_cast<OrthancPluginPeers*>(static_cast<intptr_t>(self))
      );
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return 0;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return 0;
  }
}


JNIEXPORT jstring JNI_OrthancPluginGetPeerName(JNIEnv* env, jobject sdkObject, jlong self, jint arg0)
{
  try
  {
    const char* s = OrthancPluginGetPeerName(context_
      , reinterpret_cast<OrthancPluginPeers*>(static_cast<intptr_t>(self))
      , arg0);
    if (s == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return NULL;
    }
    else
    {
      return env->NewStringUTF(s);
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jstring JNI_OrthancPluginGetPeerUrl(JNIEnv* env, jobject sdkObject, jlong self, jint arg0)
{
  try
  {
    const char* s = OrthancPluginGetPeerUrl(context_
      , reinterpret_cast<OrthancPluginPeers*>(static_cast<intptr_t>(self))
      , arg0);
    if (s == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return NULL;
    }
    else
    {
      return env->NewStringUTF(s);
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT jstring JNI_OrthancPluginGetPeerUserProperty(JNIEnv* env, jobject sdkObject, jlong self, jint arg0, jstring arg1)
{
  try
  {
    JavaString c_arg1(env, arg1);
    const char* s = OrthancPluginGetPeerUserProperty(context_
      , reinterpret_cast<OrthancPluginPeers*>(static_cast<intptr_t>(self))
      , arg0, c_arg1.GetValue());
    if (s == NULL)
    {
      JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
      return NULL;
    }
    else
    {
      return env->NewStringUTF(s);
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT void JNI_OrthancPluginAnswerBuffer(JNIEnv* env, jobject sdkObject, jlong self, jbyteArray arg0, jstring arg2)
{
  try
  {
    JavaBytes c_arg0(env, arg0);
    JavaString c_arg2(env, arg2);
    OrthancPluginAnswerBuffer(context_
      , reinterpret_cast<OrthancPluginRestOutput*>(static_cast<intptr_t>(self))
      , c_arg0.GetData(), c_arg0.GetSize(), c_arg2.GetValue());
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


JNIEXPORT void JNI_OrthancPluginCompressAndAnswerPngImage(JNIEnv* env, jobject sdkObject, jlong self, jint arg0, jint arg1, jint arg2, jint arg3, jbyteArray arg4)
{
  try
  {
    JavaBytes c_arg4(env, arg4);
    OrthancPluginCompressAndAnswerPngImage(context_
      , reinterpret_cast<OrthancPluginRestOutput*>(static_cast<intptr_t>(self))
      , static_cast<OrthancPluginPixelFormat>(arg0), arg1, arg2, arg3, c_arg4.GetData());
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


JNIEXPORT void JNI_OrthancPluginRedirect(JNIEnv* env, jobject sdkObject, jlong self, jstring arg0)
{
  try
  {
    JavaString c_arg0(env, arg0);
    OrthancPluginRedirect(context_
      , reinterpret_cast<OrthancPluginRestOutput*>(static_cast<intptr_t>(self))
      , c_arg0.GetValue());
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


JNIEXPORT void JNI_OrthancPluginSendHttpStatusCode(JNIEnv* env, jobject sdkObject, jlong self, jshort arg0)
{
  try
  {
    OrthancPluginSendHttpStatusCode(context_
      , reinterpret_cast<OrthancPluginRestOutput*>(static_cast<intptr_t>(self))
      , arg0);
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


JNIEXPORT void JNI_OrthancPluginSendUnauthorized(JNIEnv* env, jobject sdkObject, jlong self, jstring arg0)
{
  try
  {
    JavaString c_arg0(env, arg0);
    OrthancPluginSendUnauthorized(context_
      , reinterpret_cast<OrthancPluginRestOutput*>(static_cast<intptr_t>(self))
      , c_arg0.GetValue());
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


JNIEXPORT void JNI_OrthancPluginSendMethodNotAllowed(JNIEnv* env, jobject sdkObject, jlong self, jstring arg0)
{
  try
  {
    JavaString c_arg0(env, arg0);
    OrthancPluginSendMethodNotAllowed(context_
      , reinterpret_cast<OrthancPluginRestOutput*>(static_cast<intptr_t>(self))
      , c_arg0.GetValue());
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


JNIEXPORT void JNI_OrthancPluginSetCookie(JNIEnv* env, jobject sdkObject, jlong self, jstring arg0, jstring arg1)
{
  try
  {
    JavaString c_arg0(env, arg0);
    JavaString c_arg1(env, arg1);
    OrthancPluginSetCookie(context_
      , reinterpret_cast<OrthancPluginRestOutput*>(static_cast<intptr_t>(self))
      , c_arg0.GetValue(), c_arg1.GetValue());
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


JNIEXPORT void JNI_OrthancPluginSetHttpHeader(JNIEnv* env, jobject sdkObject, jlong self, jstring arg0, jstring arg1)
{
  try
  {
    JavaString c_arg0(env, arg0);
    JavaString c_arg1(env, arg1);
    OrthancPluginSetHttpHeader(context_
      , reinterpret_cast<OrthancPluginRestOutput*>(static_cast<intptr_t>(self))
      , c_arg0.GetValue(), c_arg1.GetValue());
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


JNIEXPORT void JNI_OrthancPluginStartMultipartAnswer(JNIEnv* env, jobject sdkObject, jlong self, jstring arg0, jstring arg1)
{
  try
  {
    JavaString c_arg0(env, arg0);
    JavaString c_arg1(env, arg1);
    OrthancPluginErrorCode code = OrthancPluginStartMultipartAnswer(context_
      , reinterpret_cast<OrthancPluginRestOutput*>(static_cast<intptr_t>(self))
      , c_arg0.GetValue(), c_arg1.GetValue());
    if (code != OrthancPluginErrorCode_Success)
    {
      JavaEnvironment::ThrowOrthancException(env, code);
    }
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


JNIEXPORT void JNI_OrthancPluginSendMultipartItem(JNIEnv* env, jobject sdkObject, jlong self, jbyteArray arg0)
{
  try
  {
    JavaBytes c_arg0(env, arg0);
    OrthancPluginErrorCode code = OrthancPluginSendMultipartItem(context_
      , reinterpret_cast<OrthancPluginRestOutput*>(static_cast<intptr_t>(self))
      , c_arg0.GetData(), c_arg0.GetSize());
    if (code != OrthancPluginErrorCode_Success)
    {
      JavaEnvironment::ThrowOrthancException(env, code);
    }
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


JNIEXPORT void JNI_OrthancPluginSendHttpStatus(JNIEnv* env, jobject sdkObject, jlong self, jshort arg0, jbyteArray arg1)
{
  try
  {
    JavaBytes c_arg1(env, arg1);
    OrthancPluginSendHttpStatus(context_
      , reinterpret_cast<OrthancPluginRestOutput*>(static_cast<intptr_t>(self))
      , arg0, c_arg1.GetData(), c_arg1.GetSize());
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


JNIEXPORT void JNI_OrthancPluginCompressAndAnswerJpegImage(JNIEnv* env, jobject sdkObject, jlong self, jint arg0, jint arg1, jint arg2, jint arg3, jbyteArray arg4, jbyte arg5)
{
  try
  {
    JavaBytes c_arg4(env, arg4);
    OrthancPluginCompressAndAnswerJpegImage(context_
      , reinterpret_cast<OrthancPluginRestOutput*>(static_cast<intptr_t>(self))
      , static_cast<OrthancPluginPixelFormat>(arg0), arg1, arg2, arg3, c_arg4.GetData(), arg5);
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


JNIEXPORT void JNI_OrthancPluginSetHttpErrorDetails(JNIEnv* env, jobject sdkObject, jlong self, jstring arg0, jbyte arg1)
{
  try
  {
    JavaString c_arg0(env, arg0);
    OrthancPluginSetHttpErrorDetails(context_
      , reinterpret_cast<OrthancPluginRestOutput*>(static_cast<intptr_t>(self))
      , c_arg0.GetValue(), arg1);
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


JNIEXPORT void JNI_OrthancPluginStorageAreaCreate(JNIEnv* env, jobject sdkObject, jlong self, jstring arg0, jbyteArray arg1, jlong arg2, jint arg3)
{
  try
  {
    JavaString c_arg0(env, arg0);
    JavaBytes c_arg1(env, arg1);
    OrthancPluginErrorCode code = OrthancPluginStorageAreaCreate(context_
      , reinterpret_cast<OrthancPluginStorageArea*>(static_cast<intptr_t>(self))
      , c_arg0.GetValue(), c_arg1.GetData(), arg2, static_cast<OrthancPluginContentType>(arg3));
    if (code != OrthancPluginErrorCode_Success)
    {
      JavaEnvironment::ThrowOrthancException(env, code);
    }
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


JNIEXPORT jbyteArray JNI_OrthancPluginStorageAreaRead(JNIEnv* env, jobject sdkObject, jlong self, jstring arg0, jint arg1)
{
  try
  {
    JavaString c_arg0(env, arg0);
    OrthancBytes b;
    OrthancPluginErrorCode code = OrthancPluginStorageAreaRead(context_, b.GetMemoryBuffer()
      , reinterpret_cast<OrthancPluginStorageArea*>(static_cast<intptr_t>(self))
      , c_arg0.GetValue(), static_cast<OrthancPluginContentType>(arg1));
    if (code == OrthancPluginErrorCode_Success)
    {
      jbyteArray answer = env->NewByteArray(b.GetSize());
      if (answer == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        env->SetByteArrayRegion(answer, 0, b.GetSize(), reinterpret_cast<const jbyte*>(b.GetData()));
        return answer;
      }
    }
    else
    {
      JavaEnvironment::ThrowOrthancException(env, code);
      return NULL;
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


JNIEXPORT void JNI_OrthancPluginStorageAreaRemove(JNIEnv* env, jobject sdkObject, jlong self, jstring arg0, jint arg1)
{
  try
  {
    JavaString c_arg0(env, arg0);
    OrthancPluginErrorCode code = OrthancPluginStorageAreaRemove(context_
      , reinterpret_cast<OrthancPluginStorageArea*>(static_cast<intptr_t>(self))
      , c_arg0.GetValue(), static_cast<OrthancPluginContentType>(arg1));
    if (code != OrthancPluginErrorCode_Success)
    {
      JavaEnvironment::ThrowOrthancException(env, code);
    }
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


JNIEXPORT void JNI_OrthancPluginReconstructMainDicomTags(JNIEnv* env, jobject sdkObject, jlong self, jint arg0)
{
  try
  {
    OrthancPluginErrorCode code = OrthancPluginReconstructMainDicomTags(context_
      , reinterpret_cast<OrthancPluginStorageArea*>(static_cast<intptr_t>(self))
      , static_cast<OrthancPluginResourceType>(arg0));
    if (code != OrthancPluginErrorCode_Success)
    {
      JavaEnvironment::ThrowOrthancException(env, code);
    }
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


JNIEXPORT void JNI_OrthancPluginWorklistAddAnswer(JNIEnv* env, jobject sdkObject, jlong self, jlong arg0, jbyteArray arg1)
{
  try
  {
    JavaBytes c_arg1(env, arg1);
    OrthancPluginErrorCode code = OrthancPluginWorklistAddAnswer(context_
      , reinterpret_cast<OrthancPluginWorklistAnswers*>(static_cast<intptr_t>(self))
      , reinterpret_cast<OrthancPluginWorklistQuery*>(static_cast<intptr_t>(arg0)), c_arg1.GetData(), c_arg1.GetSize());
    if (code != OrthancPluginErrorCode_Success)
    {
      JavaEnvironment::ThrowOrthancException(env, code);
    }
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


JNIEXPORT void JNI_OrthancPluginWorklistMarkIncomplete(JNIEnv* env, jobject sdkObject, jlong self)
{
  try
  {
    OrthancPluginErrorCode code = OrthancPluginWorklistMarkIncomplete(context_
      , reinterpret_cast<OrthancPluginWorklistAnswers*>(static_cast<intptr_t>(self))
      );
    if (code != OrthancPluginErrorCode_Success)
    {
      JavaEnvironment::ThrowOrthancException(env, code);
    }
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


JNIEXPORT jint JNI_OrthancPluginWorklistIsMatch(JNIEnv* env, jobject sdkObject, jlong self, jbyteArray arg0)
{
  try
  {
    JavaBytes c_arg0(env, arg0);
    return OrthancPluginWorklistIsMatch(context_
      , reinterpret_cast<OrthancPluginWorklistQuery*>(static_cast<intptr_t>(self))
      , c_arg0.GetData(), c_arg0.GetSize());
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return 0;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return 0;
  }
}


JNIEXPORT jbyteArray JNI_OrthancPluginWorklistGetDicomQuery(JNIEnv* env, jobject sdkObject, jlong self)
{
  try
  {
    OrthancBytes b;
    OrthancPluginErrorCode code = OrthancPluginWorklistGetDicomQuery(context_, b.GetMemoryBuffer()
      , reinterpret_cast<OrthancPluginWorklistQuery*>(static_cast<intptr_t>(self))
      );
    if (code == OrthancPluginErrorCode_Success)
    {
      jbyteArray answer = env->NewByteArray(b.GetSize());
      if (answer == NULL)
      {
        JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_NotEnoughMemory);
        return NULL;
      }
      else
      {
        env->SetByteArrayRegion(answer, 0, b.GetSize(), reinterpret_cast<const jbyte*>(b.GetData()));
        return answer;
      }
    }
    else
    {
      JavaEnvironment::ThrowOrthancException(env, code);
      return NULL;
    }
  }
  catch (std::runtime_error& e)
  {
    JavaEnvironment::ThrowOrthancException(env, e.what());
    return NULL;
  }
  catch (...)
  {
    JavaEnvironment::ThrowOrthancException(env, OrthancPluginErrorCode_Plugin);
    return NULL;
  }
}


void JNI_LoadNatives(std::vector<JNINativeMethod>& methods)
{
  methods.clear();

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginCheckVersionAdvanced"),
    const_cast<char*>("(III)I"),
    (void*) JNI_OrthancPluginCheckVersionAdvanced
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginCheckVersion"),
    const_cast<char*>("()I"),
    (void*) JNI_OrthancPluginCheckVersion
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginLogError"),
    const_cast<char*>("(Ljava/lang/String;)V"),
    (void*) JNI_OrthancPluginLogError
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginLogWarning"),
    const_cast<char*>("(Ljava/lang/String;)V"),
    (void*) JNI_OrthancPluginLogWarning
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginLogInfo"),
    const_cast<char*>("(Ljava/lang/String;)V"),
    (void*) JNI_OrthancPluginLogInfo
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginGetDicomForInstance"),
    const_cast<char*>("(Ljava/lang/String;)[B"),
    (void*) JNI_OrthancPluginGetDicomForInstance
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginRestApiGet"),
    const_cast<char*>("(Ljava/lang/String;)[B"),
    (void*) JNI_OrthancPluginRestApiGet
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginRestApiGetAfterPlugins"),
    const_cast<char*>("(Ljava/lang/String;)[B"),
    (void*) JNI_OrthancPluginRestApiGetAfterPlugins
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginRestApiPost"),
    const_cast<char*>("(Ljava/lang/String;[B)[B"),
    (void*) JNI_OrthancPluginRestApiPost
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginRestApiPostAfterPlugins"),
    const_cast<char*>("(Ljava/lang/String;[B)[B"),
    (void*) JNI_OrthancPluginRestApiPostAfterPlugins
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginRestApiDelete"),
    const_cast<char*>("(Ljava/lang/String;)V"),
    (void*) JNI_OrthancPluginRestApiDelete
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginRestApiDeleteAfterPlugins"),
    const_cast<char*>("(Ljava/lang/String;)V"),
    (void*) JNI_OrthancPluginRestApiDeleteAfterPlugins
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginRestApiPut"),
    const_cast<char*>("(Ljava/lang/String;[B)[B"),
    (void*) JNI_OrthancPluginRestApiPut
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginRestApiPutAfterPlugins"),
    const_cast<char*>("(Ljava/lang/String;[B)[B"),
    (void*) JNI_OrthancPluginRestApiPutAfterPlugins
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginLookupPatient"),
    const_cast<char*>("(Ljava/lang/String;)Ljava/lang/String;"),
    (void*) JNI_OrthancPluginLookupPatient
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginLookupStudy"),
    const_cast<char*>("(Ljava/lang/String;)Ljava/lang/String;"),
    (void*) JNI_OrthancPluginLookupStudy
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginLookupStudyWithAccessionNumber"),
    const_cast<char*>("(Ljava/lang/String;)Ljava/lang/String;"),
    (void*) JNI_OrthancPluginLookupStudyWithAccessionNumber
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginLookupSeries"),
    const_cast<char*>("(Ljava/lang/String;)Ljava/lang/String;"),
    (void*) JNI_OrthancPluginLookupSeries
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginLookupInstance"),
    const_cast<char*>("(Ljava/lang/String;)Ljava/lang/String;"),
    (void*) JNI_OrthancPluginLookupInstance
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginGetOrthancPath"),
    const_cast<char*>("()Ljava/lang/String;"),
    (void*) JNI_OrthancPluginGetOrthancPath
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginGetOrthancDirectory"),
    const_cast<char*>("()Ljava/lang/String;"),
    (void*) JNI_OrthancPluginGetOrthancDirectory
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginGetConfigurationPath"),
    const_cast<char*>("()Ljava/lang/String;"),
    (void*) JNI_OrthancPluginGetConfigurationPath
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginSetRootUri"),
    const_cast<char*>("(Ljava/lang/String;)V"),
    (void*) JNI_OrthancPluginSetRootUri
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginSetDescription"),
    const_cast<char*>("(Ljava/lang/String;)V"),
    (void*) JNI_OrthancPluginSetDescription
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginExtendOrthancExplorer"),
    const_cast<char*>("(Ljava/lang/String;)V"),
    (void*) JNI_OrthancPluginExtendOrthancExplorer
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginGetGlobalProperty"),
    const_cast<char*>("(ILjava/lang/String;)Ljava/lang/String;"),
    (void*) JNI_OrthancPluginGetGlobalProperty
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginSetGlobalProperty"),
    const_cast<char*>("(ILjava/lang/String;)V"),
    (void*) JNI_OrthancPluginSetGlobalProperty
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginGetCommandLineArgumentsCount"),
    const_cast<char*>("()I"),
    (void*) JNI_OrthancPluginGetCommandLineArgumentsCount
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginGetCommandLineArgument"),
    const_cast<char*>("(I)Ljava/lang/String;"),
    (void*) JNI_OrthancPluginGetCommandLineArgument
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginGetExpectedDatabaseVersion"),
    const_cast<char*>("()I"),
    (void*) JNI_OrthancPluginGetExpectedDatabaseVersion
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginGetConfiguration"),
    const_cast<char*>("()Ljava/lang/String;"),
    (void*) JNI_OrthancPluginGetConfiguration
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginBufferCompression"),
    const_cast<char*>("([BIB)[B"),
    (void*) JNI_OrthancPluginBufferCompression
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginReadFile"),
    const_cast<char*>("(Ljava/lang/String;)[B"),
    (void*) JNI_OrthancPluginReadFile
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginWriteFile"),
    const_cast<char*>("(Ljava/lang/String;[B)V"),
    (void*) JNI_OrthancPluginWriteFile
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginGetErrorDescription"),
    const_cast<char*>("(I)Ljava/lang/String;"),
    (void*) JNI_OrthancPluginGetErrorDescription
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginUncompressImage"),
    const_cast<char*>("([BI)J"),
    (void*) JNI_OrthancPluginUncompressImage
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginCompressPngImage"),
    const_cast<char*>("(IIII[B)[B"),
    (void*) JNI_OrthancPluginCompressPngImage
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginCompressJpegImage"),
    const_cast<char*>("(IIII[BB)[B"),
    (void*) JNI_OrthancPluginCompressJpegImage
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginHttpGet"),
    const_cast<char*>("(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)[B"),
    (void*) JNI_OrthancPluginHttpGet
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginHttpPost"),
    const_cast<char*>("(Ljava/lang/String;[BLjava/lang/String;Ljava/lang/String;)[B"),
    (void*) JNI_OrthancPluginHttpPost
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginHttpPut"),
    const_cast<char*>("(Ljava/lang/String;[BLjava/lang/String;Ljava/lang/String;)[B"),
    (void*) JNI_OrthancPluginHttpPut
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginHttpDelete"),
    const_cast<char*>("(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V"),
    (void*) JNI_OrthancPluginHttpDelete
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginGetFontsCount"),
    const_cast<char*>("()I"),
    (void*) JNI_OrthancPluginGetFontsCount
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginGetFontName"),
    const_cast<char*>("(I)Ljava/lang/String;"),
    (void*) JNI_OrthancPluginGetFontName
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginGetFontSize"),
    const_cast<char*>("(I)I"),
    (void*) JNI_OrthancPluginGetFontSize
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginRegisterErrorCode"),
    const_cast<char*>("(ISLjava/lang/String;)V"),
    (void*) JNI_OrthancPluginRegisterErrorCode
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginRegisterDictionaryTag"),
    const_cast<char*>("(SSILjava/lang/String;II)V"),
    (void*) JNI_OrthancPluginRegisterDictionaryTag
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginRegisterPrivateDictionaryTag"),
    const_cast<char*>("(SSILjava/lang/String;IILjava/lang/String;)V"),
    (void*) JNI_OrthancPluginRegisterPrivateDictionaryTag
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginDicomBufferToJson"),
    const_cast<char*>("([BIII)Ljava/lang/String;"),
    (void*) JNI_OrthancPluginDicomBufferToJson
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginDicomInstanceToJson"),
    const_cast<char*>("(Ljava/lang/String;III)Ljava/lang/String;"),
    (void*) JNI_OrthancPluginDicomInstanceToJson
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginCreateDicom"),
    const_cast<char*>("(Ljava/lang/String;JI)[B"),
    (void*) JNI_OrthancPluginCreateDicom
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginCreateImage"),
    const_cast<char*>("(III)J"),
    (void*) JNI_OrthancPluginCreateImage
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginDecodeDicomImage"),
    const_cast<char*>("([BI)J"),
    (void*) JNI_OrthancPluginDecodeDicomImage
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginComputeMd5"),
    const_cast<char*>("([B)Ljava/lang/String;"),
    (void*) JNI_OrthancPluginComputeMd5
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginComputeSha1"),
    const_cast<char*>("([B)Ljava/lang/String;"),
    (void*) JNI_OrthancPluginComputeSha1
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginGenerateUuid"),
    const_cast<char*>("()Ljava/lang/String;"),
    (void*) JNI_OrthancPluginGenerateUuid
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginCreateFindMatcher"),
    const_cast<char*>("([B)J"),
    (void*) JNI_OrthancPluginCreateFindMatcher
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginGetPeers"),
    const_cast<char*>("()J"),
    (void*) JNI_OrthancPluginGetPeers
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginAutodetectMimeType"),
    const_cast<char*>("(Ljava/lang/String;)Ljava/lang/String;"),
    (void*) JNI_OrthancPluginAutodetectMimeType
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginSetMetricsValue"),
    const_cast<char*>("(Ljava/lang/String;FI)V"),
    (void*) JNI_OrthancPluginSetMetricsValue
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginGetTagName"),
    const_cast<char*>("(SSLjava/lang/String;)Ljava/lang/String;"),
    (void*) JNI_OrthancPluginGetTagName
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginCreateDicomInstance"),
    const_cast<char*>("([B)J"),
    (void*) JNI_OrthancPluginCreateDicomInstance
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginTranscodeDicomInstance"),
    const_cast<char*>("([BLjava/lang/String;)J"),
    (void*) JNI_OrthancPluginTranscodeDicomInstance
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginGenerateRestApiAuthorizationToken"),
    const_cast<char*>("()Ljava/lang/String;"),
    (void*) JNI_OrthancPluginGenerateRestApiAuthorizationToken
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginCreateDicom2"),
    const_cast<char*>("(Ljava/lang/String;JILjava/lang/String;)[B"),
    (void*) JNI_OrthancPluginCreateDicom2
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginFreeDicomInstance"),
    const_cast<char*>("(J)V"),
    (void*) JNI_OrthancPluginFreeDicomInstance
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginGetInstanceRemoteAet"),
    const_cast<char*>("(J)Ljava/lang/String;"),
    (void*) JNI_OrthancPluginGetInstanceRemoteAet
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginGetInstanceSize"),
    const_cast<char*>("(J)J"),
    (void*) JNI_OrthancPluginGetInstanceSize
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginGetInstanceJson"),
    const_cast<char*>("(J)Ljava/lang/String;"),
    (void*) JNI_OrthancPluginGetInstanceJson
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginGetInstanceSimplifiedJson"),
    const_cast<char*>("(J)Ljava/lang/String;"),
    (void*) JNI_OrthancPluginGetInstanceSimplifiedJson
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginHasInstanceMetadata"),
    const_cast<char*>("(JLjava/lang/String;)I"),
    (void*) JNI_OrthancPluginHasInstanceMetadata
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginGetInstanceMetadata"),
    const_cast<char*>("(JLjava/lang/String;)Ljava/lang/String;"),
    (void*) JNI_OrthancPluginGetInstanceMetadata
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginGetInstanceOrigin"),
    const_cast<char*>("(J)I"),
    (void*) JNI_OrthancPluginGetInstanceOrigin
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginGetInstanceTransferSyntaxUid"),
    const_cast<char*>("(J)Ljava/lang/String;"),
    (void*) JNI_OrthancPluginGetInstanceTransferSyntaxUid
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginHasInstancePixelData"),
    const_cast<char*>("(J)I"),
    (void*) JNI_OrthancPluginHasInstancePixelData
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginGetInstanceFramesCount"),
    const_cast<char*>("(J)I"),
    (void*) JNI_OrthancPluginGetInstanceFramesCount
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginGetInstanceRawFrame"),
    const_cast<char*>("(JI)[B"),
    (void*) JNI_OrthancPluginGetInstanceRawFrame
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginGetInstanceDecodedFrame"),
    const_cast<char*>("(JI)J"),
    (void*) JNI_OrthancPluginGetInstanceDecodedFrame
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginSerializeDicomInstance"),
    const_cast<char*>("(J)[B"),
    (void*) JNI_OrthancPluginSerializeDicomInstance
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginGetInstanceAdvancedJson"),
    const_cast<char*>("(JIII)Ljava/lang/String;"),
    (void*) JNI_OrthancPluginGetInstanceAdvancedJson
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginFindAddAnswer"),
    const_cast<char*>("(J[B)V"),
    (void*) JNI_OrthancPluginFindAddAnswer
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginFindMarkIncomplete"),
    const_cast<char*>("(J)V"),
    (void*) JNI_OrthancPluginFindMarkIncomplete
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginFreeFindMatcher"),
    const_cast<char*>("(J)V"),
    (void*) JNI_OrthancPluginFreeFindMatcher
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginFindMatcherIsMatch"),
    const_cast<char*>("(J[B)I"),
    (void*) JNI_OrthancPluginFindMatcherIsMatch
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginGetFindQuerySize"),
    const_cast<char*>("(J)I"),
    (void*) JNI_OrthancPluginGetFindQuerySize
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginGetFindQueryTagName"),
    const_cast<char*>("(JI)Ljava/lang/String;"),
    (void*) JNI_OrthancPluginGetFindQueryTagName
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginGetFindQueryValue"),
    const_cast<char*>("(JI)Ljava/lang/String;"),
    (void*) JNI_OrthancPluginGetFindQueryValue
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginFreeImage"),
    const_cast<char*>("(J)V"),
    (void*) JNI_OrthancPluginFreeImage
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginGetImagePixelFormat"),
    const_cast<char*>("(J)I"),
    (void*) JNI_OrthancPluginGetImagePixelFormat
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginGetImageWidth"),
    const_cast<char*>("(J)I"),
    (void*) JNI_OrthancPluginGetImageWidth
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginGetImageHeight"),
    const_cast<char*>("(J)I"),
    (void*) JNI_OrthancPluginGetImageHeight
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginGetImagePitch"),
    const_cast<char*>("(J)I"),
    (void*) JNI_OrthancPluginGetImagePitch
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginConvertPixelFormat"),
    const_cast<char*>("(JI)J"),
    (void*) JNI_OrthancPluginConvertPixelFormat
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginDrawText"),
    const_cast<char*>("(JILjava/lang/String;IIBBB)V"),
    (void*) JNI_OrthancPluginDrawText
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginFreeJob"),
    const_cast<char*>("(J)V"),
    (void*) JNI_OrthancPluginFreeJob
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginSubmitJob"),
    const_cast<char*>("(JI)Ljava/lang/String;"),
    (void*) JNI_OrthancPluginSubmitJob
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginFreePeers"),
    const_cast<char*>("(J)V"),
    (void*) JNI_OrthancPluginFreePeers
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginGetPeersCount"),
    const_cast<char*>("(J)I"),
    (void*) JNI_OrthancPluginGetPeersCount
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginGetPeerName"),
    const_cast<char*>("(JI)Ljava/lang/String;"),
    (void*) JNI_OrthancPluginGetPeerName
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginGetPeerUrl"),
    const_cast<char*>("(JI)Ljava/lang/String;"),
    (void*) JNI_OrthancPluginGetPeerUrl
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginGetPeerUserProperty"),
    const_cast<char*>("(JILjava/lang/String;)Ljava/lang/String;"),
    (void*) JNI_OrthancPluginGetPeerUserProperty
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginAnswerBuffer"),
    const_cast<char*>("(J[BLjava/lang/String;)V"),
    (void*) JNI_OrthancPluginAnswerBuffer
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginCompressAndAnswerPngImage"),
    const_cast<char*>("(JIIII[B)V"),
    (void*) JNI_OrthancPluginCompressAndAnswerPngImage
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginRedirect"),
    const_cast<char*>("(JLjava/lang/String;)V"),
    (void*) JNI_OrthancPluginRedirect
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginSendHttpStatusCode"),
    const_cast<char*>("(JS)V"),
    (void*) JNI_OrthancPluginSendHttpStatusCode
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginSendUnauthorized"),
    const_cast<char*>("(JLjava/lang/String;)V"),
    (void*) JNI_OrthancPluginSendUnauthorized
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginSendMethodNotAllowed"),
    const_cast<char*>("(JLjava/lang/String;)V"),
    (void*) JNI_OrthancPluginSendMethodNotAllowed
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginSetCookie"),
    const_cast<char*>("(JLjava/lang/String;Ljava/lang/String;)V"),
    (void*) JNI_OrthancPluginSetCookie
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginSetHttpHeader"),
    const_cast<char*>("(JLjava/lang/String;Ljava/lang/String;)V"),
    (void*) JNI_OrthancPluginSetHttpHeader
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginStartMultipartAnswer"),
    const_cast<char*>("(JLjava/lang/String;Ljava/lang/String;)V"),
    (void*) JNI_OrthancPluginStartMultipartAnswer
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginSendMultipartItem"),
    const_cast<char*>("(J[B)V"),
    (void*) JNI_OrthancPluginSendMultipartItem
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginSendHttpStatus"),
    const_cast<char*>("(JS[B)V"),
    (void*) JNI_OrthancPluginSendHttpStatus
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginCompressAndAnswerJpegImage"),
    const_cast<char*>("(JIIII[BB)V"),
    (void*) JNI_OrthancPluginCompressAndAnswerJpegImage
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginSetHttpErrorDetails"),
    const_cast<char*>("(JLjava/lang/String;B)V"),
    (void*) JNI_OrthancPluginSetHttpErrorDetails
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginStorageAreaCreate"),
    const_cast<char*>("(JLjava/lang/String;[BJI)V"),
    (void*) JNI_OrthancPluginStorageAreaCreate
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginStorageAreaRead"),
    const_cast<char*>("(JLjava/lang/String;I)[B"),
    (void*) JNI_OrthancPluginStorageAreaRead
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginStorageAreaRemove"),
    const_cast<char*>("(JLjava/lang/String;I)V"),
    (void*) JNI_OrthancPluginStorageAreaRemove
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginReconstructMainDicomTags"),
    const_cast<char*>("(JI)V"),
    (void*) JNI_OrthancPluginReconstructMainDicomTags
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginWorklistAddAnswer"),
    const_cast<char*>("(JJ[B)V"),
    (void*) JNI_OrthancPluginWorklistAddAnswer
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginWorklistMarkIncomplete"),
    const_cast<char*>("(J)V"),
    (void*) JNI_OrthancPluginWorklistMarkIncomplete
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginWorklistIsMatch"),
    const_cast<char*>("(J[B)I"),
    (void*) JNI_OrthancPluginWorklistIsMatch
  });

  methods.push_back((JNINativeMethod) {
    const_cast<char*>("OrthancPluginWorklistGetDicomQuery"),
    const_cast<char*>("(J)[B"),
    (void*) JNI_OrthancPluginWorklistGetDicomQuery
  });
}