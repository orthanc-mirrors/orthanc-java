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


#include "JavaVirtualMachine.h"

#include <cassert>
#include <stdexcept>
#include <vector>


JavaVirtualMachine::JavaVirtualMachine(const std::string& classPath)
{
  std::string classPathOption = "-Djava.class.path=" + classPath;

  std::vector<JavaVMOption> options;

  options.resize(1);
  options[0].optionString = const_cast<char*>(classPathOption.c_str());
  options[0].extraInfo = NULL;

#if !defined(NDEBUG)
  // Debug mode
  {
    JavaVMOption option;
    option.optionString = const_cast<char*>("-Xcheck:jni");
    option.extraInfo = NULL;
    options.push_back(option);
  }
#endif

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


JavaVirtualMachine::~JavaVirtualMachine()
{
  jvm_->DestroyJavaVM();
}


JavaVM& JavaVirtualMachine::GetValue()
{
  assert(jvm_ != NULL);
  return *jvm_;
}
