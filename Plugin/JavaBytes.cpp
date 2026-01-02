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


#include "JavaBytes.h"

#include <stdexcept>


JavaBytes::JavaBytes(JNIEnv* env,
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


JavaBytes::~JavaBytes()
{
  if (size_ > 0)
  {
    env_->ReleaseByteArrayElements(bytes_, data_, 0);
  }
}
