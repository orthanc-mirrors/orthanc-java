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


#include "JavaString.h"

#include <stdexcept>


JavaString::JavaString(JNIEnv* env,
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


JavaString::~JavaString()
{
  /**
   * "The ReleaseString-Chars call is necessary whether
   * GetStringChars has set isCopy to JNI_TRUE or JNI_FALSE."
   * https://stackoverflow.com/a/5863081
   **/
  env_->ReleaseStringUTFChars(javaStr_, cStr_);
}
