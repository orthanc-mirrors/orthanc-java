# SPDX-FileCopyrightText: 2023 Sebastien Jodogne, UCLouvain, Belgium
# SPDX-License-Identifier: GPL-3.0-or-later

# Java plugin for Orthanc
# Copyright (C) 2023 Sebastien Jodogne, UCLouvain, Belgium
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


cmake_minimum_required(VERSION 3.0)

project(OrthancJavaSDK)

find_package(Java REQUIRED)
include(UseJava)

set(CMAKE_JAVA_COMPILE_FLAGS "-source" "1.8" "-target" "1.8" "-Xdoclint:all/protected")

file(GLOB JAVA_SOURCES
  ${CMAKE_SOURCE_DIR}/be/uclouvain/orthanc/*.java
  )

set(JAVADOC_OUTPUT_DIR "${CMAKE_CURRENT_BINARY_DIR}/OrthancJavaSDKDocumentation")
file(MAKE_DIRECTORY ${JAVADOC_OUTPUT_DIR})

add_jar(OrthancJavaSDK
  ${JAVA_SOURCES}
  DEPENDS CreateJavadocDirectory
  )

add_custom_command(TARGET OrthancJavaSDK
  POST_BUILD
  COMMAND ${Java_JAVADOC_EXECUTABLE} -Xdoclint:all ${JAVA_SOURCES}
  WORKING_DIRECTORY ${JAVADOC_OUTPUT_DIR}
  COMMENT "Generating SDK documentation with javadoc" VERBATIM
  )
