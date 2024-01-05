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


#pragma once

#include "JavaVirtualMachine.h"

#include <orthanc/OrthancCPlugin.h>

#include <vector>


class JavaEnvironment : public NonCopyable
{
private:
  JavaVM *jvm_;
  JNIEnv *env_;

public:
  JavaEnvironment(JNIEnv* env);

  JavaEnvironment(JavaVirtualMachine& jvm);

  ~JavaEnvironment();

  JNIEnv& GetValue();

  void CheckException();

  void ThrowException(const std::string& fqn,
                      const std::string& message);

  void ThrowOrthancException(const std::string& message);

  void ThrowOrthancException(OrthancPluginErrorCode code);

  static void ThrowOrthancException(JNIEnv* env,
                                    const std::string& message);

  static void ThrowOrthancException(JNIEnv* env,
                                    OrthancPluginErrorCode code);

  void RegisterNatives(const std::string& fqn,
                       const std::vector<JNINativeMethod>& methods);

  void RunGarbageCollector();

  jclass FindClass(const std::string& fqn);

  jclass GetObjectClass(jobject obj);

  jmethodID GetMethodID(jclass c,
                        const std::string& method,
                        const std::string& signature);

  jobject ConstructJavaWrapper(const std::string& fqn,
                               void* nativeObject);

  jbyteArray ConstructByteArray(const size_t size,
                                const void* data);

  jbyteArray ConstructByteArray(const std::string& data)
  {
    return ConstructByteArray(data.size(), data.c_str());
  }

  jobject ConstructEnumValue(const std::string& fqn,
                             int value);
};
