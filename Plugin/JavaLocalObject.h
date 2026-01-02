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


#pragma once

#include "JavaEnvironment.h"

#include <map>


class JavaLocalObject : public NonCopyable
{
private:
  JNIEnv*  env_;
  jobject  obj_;

public:
  JavaLocalObject(JavaEnvironment& env,
                  jobject obj,
                  bool objCanBeNull = false);

  ~JavaLocalObject();

  jobject GetValue()
  {
    return obj_;
  }

  static JavaLocalObject* CreateArrayOfStrings(JavaEnvironment& env,
                                               const std::vector<std::string>& items);

  static JavaLocalObject* CreateDictionary(JavaEnvironment& env,
                                           const std::map<std::string, std::string>& items);
};
