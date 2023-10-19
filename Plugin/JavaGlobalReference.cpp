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


#include "JavaGlobalReference.h"

#include "JavaEnvironment.h"

#include <cassert>
#include <stdexcept>

extern OrthancPluginContext* context_;


JavaGlobalReference::JavaGlobalReference(JavaVirtualMachine& jvm,
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


JavaGlobalReference::~JavaGlobalReference()
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


jobject JavaGlobalReference::GetValue()
{
  assert(obj_ != NULL);
  return obj_;
}
