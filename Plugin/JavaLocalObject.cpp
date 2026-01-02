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


#include "JavaLocalObject.h"

#include <memory>
#include <stdexcept>


JavaLocalObject::JavaLocalObject(JavaEnvironment& env,
                                 jobject obj,
                                 bool objCanBeNull) :
  env_(&env.GetValue()),
  obj_(obj)
{
  if (!objCanBeNull && obj == NULL)
  {
    throw std::runtime_error("Null pointer");
  }
}


JavaLocalObject::~JavaLocalObject()
{
  env_->DeleteLocalRef(obj_);
}


JavaLocalObject* JavaLocalObject::CreateArrayOfStrings(JavaEnvironment& env,
                                                       const std::vector<std::string>& items)
{
  JavaLocalObject emptyString(env, env.GetValue().NewStringUTF(""));

  jobjectArray obj = env.GetValue().NewObjectArray(
    items.size(), env.GetValue().FindClass("java/lang/String"),
    emptyString.GetValue());

  if (obj == NULL)
  {
    throw std::runtime_error("Cannot create an array of Java strings");
  }
  else
  {
    std::unique_ptr<JavaLocalObject> result(new JavaLocalObject(env, obj));

    for (size_t i = 0; i < items.size(); i++)
    {
      JavaLocalObject item(env, env.GetValue().NewStringUTF(items[i].c_str()));
      env.GetValue().SetObjectArrayElement(obj, i, item.GetValue());
    }

    return result.release();
  }
}


JavaLocalObject* JavaLocalObject::CreateDictionary(JavaEnvironment& env,
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
    std::unique_ptr<JavaLocalObject> result(new JavaLocalObject(env, obj));

    for (std::map<std::string, std::string>::const_iterator it = items.begin(); it != items.end(); ++it)
    {
      JavaLocalObject key(env, env.GetValue().NewStringUTF(it->first.c_str()));
      JavaLocalObject value(env, env.GetValue().NewStringUTF(it->second.c_str()));
      JavaLocalObject previousValue(env, env.GetValue().CallObjectMethod(obj, setter, key.GetValue(), value.GetValue()), true);
      env.CheckException();
    }

    return result.release();
  }
}
