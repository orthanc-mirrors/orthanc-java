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

class Mutex : public NonCopyable
{
private:
  struct PImpl;
  PImpl *pimpl_;

  void Lock();

  void Unlock();

public:
  Mutex();

  ~Mutex();

  class Locker : public NonCopyable
  {
  private:
    Mutex&  mutex_;

  public:
    explicit Locker(Mutex& mutex) :
      mutex_(mutex)
    {
      mutex_.Lock();
    }

    ~Locker()
    {
      mutex_.Unlock();
    }
  };
};
