# SPDX-FileCopyrightText: 2023-2025 Sebastien Jodogne, ICTEAM UCLouvain, Belgium
# SPDX-License-Identifier: GPL-3.0-or-later

# Java plugin for Orthanc
# Copyright (C) 2023-2025 Sebastien Jodogne, ICTEAM UCLouvain, Belgium
#
# This program is free software: you can redistribute it and/or
# modify it under the terms of the GNU General Public License as
# published by the Free Software Foundation, either version 3 of the
# License, or (at your option) any later version.
#
# This program is distributed in the hope that it will be useful, but
# WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
# General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program. If not, see <http://www.gnu.org/licenses/>.



#####################################################################
## General information
#####################################################################

set(ORTHANC_SDK_DEFAULT_VERSION "1.12.9")

# This list must correspond to the content of "./Resources/SyncOrthancFolder.py"
set(ORTHANC_SDK_AVAILABLE_VERSIONS "1.10.0" "1.12.6" "1.12.9")


#####################################################################
## Define the CMake parameters to select the SDK to be used. This
## information is shared by the C++ native code and by the Java
## wrapper.
#####################################################################

# Generate the documentation about the "ORTHANC_SDK_VERSION" option
set(tmp "Version of the Orthanc plugin SDK to use, if not using the system version (can be")
foreach(version IN LISTS ORTHANC_SDK_AVAILABLE_VERSIONS)
  set(tmp "${tmp} ${version},")
endforeach()
set(tmp "${tmp} or \"path\")")

set(USE_SYSTEM_ORTHANC_SDK ON CACHE BOOL "Use the system-wide version of the Orthanc plugin SDK")
set(ORTHANC_SDK_VERSION "${ORTHANC_SDK_DEFAULT_VERSION}" CACHE STRING "${tmp}")
set(ORTHANC_SDK_PATH "" CACHE STRING "Path to the orthanc/OrthancCPlugin.h file, if ORTHANC_SDK_VERSION is set to \"path\"")


#####################################################################
## Find the Orthanc SDK
#####################################################################

if (STATIC_BUILD OR NOT USE_SYSTEM_ORTHANC_SDK)
  if (ORTHANC_SDK_VERSION STREQUAL "path")
    set(ORTHANC_SDK ${ORTHANC_SDK_PATH}/orthanc/OrthancCPlugin.h)

  else()
    set(ORTHANC_SDK ${CMAKE_SOURCE_DIR}/../Resources/Orthanc/Sdk-${ORTHANC_SDK_VERSION}/orthanc/OrthancCPlugin.h)
  endif()

else()
  find_path(ORTHANC_SDK_SYSTEM_DIR OrthancCPlugin.h
    /usr/
    /usr/local/
    PATH_SUFFIXES include/orthanc
    )

  if (${ORTHANC_SDK_SYSTEM_DIR} STREQUAL "ORTHANC_SDK_SYSTEM_DIR-NOTFOUND")
    message(FATAL_ERROR "Cannot locate the orthanc/OrthancCPlugin.h header")
  endif()

  set(ORTHANC_SDK ${ORTHANC_SDK_SYSTEM_DIR}/OrthancCPlugin.h)
endif()
