/**
 * SPDX-FileCopyrightText: 2023-2025 Sebastien Jodogne, ICTEAM UCLouvain, Belgium
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

/**
 * Java plugin for Orthanc
 * Copyright (C) 2023-2025 Sebastien Jodogne, ICTEAM UCLouvain, Belgium
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

#include "NonCopyable.h"

#include <jni.h>


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
            jbyteArray bytes);

  ~JavaBytes();

  const void* GetData() const
  {
    return data_;
  }

  size_t GetSize() const
  {
    return size_;
  }
};
