#!/usr/bin/python3

# SPDX-FileCopyrightText: 2023-2024 Sebastien Jodogne, UCLouvain, Belgium
# SPDX-License-Identifier: GPL-3.0-or-later

# Java plugin for Orthanc
# Copyright (C) 2023-2024 Sebastien Jodogne, UCLouvain, Belgium
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


#
# This maintenance script updates the content of the "Orthanc" folder
# to match the latest version of the Orthanc source code.
#

import multiprocessing
import os
import stat
import urllib.request
import subprocess

TARGET = os.path.join(os.path.dirname(__file__), 'Orthanc')
PLUGIN_SDK_VERSION = '1.10.0'
REPOSITORY = 'https://orthanc.uclouvain.be/hg/orthanc/raw-file'

FILES = [
    ('OrthancFramework/Resources/CMake/AutoGeneratedCode.cmake', 'CMake'),
    ('OrthancFramework/Resources/CMake/Compiler.cmake', 'CMake'),
    ('OrthancFramework/Resources/CMake/DownloadPackage.cmake', 'CMake'),
    ('OrthancFramework/Resources/CMake/JsonCppConfiguration.cmake', 'CMake'),
    ('OrthancFramework/Resources/WindowsResources.py', 'CMake'),
    ('OrthancFramework/Resources/WindowsResources.rc', 'CMake'),

    ('OrthancFramework/Resources/Toolchains/LinuxStandardBaseToolchain.cmake', 'Toolchains'),
    ('OrthancFramework/Resources/Toolchains/MinGW-W64-Toolchain32.cmake', 'Toolchains'),
    ('OrthancFramework/Resources/Toolchains/MinGW-W64-Toolchain64.cmake', 'Toolchains'),

    ('OrthancServer/Plugins/Samples/Common/ExportedSymbolsPlugins.list', 'Plugins'),
    ('OrthancServer/Plugins/Samples/Common/OrthancPluginsExports.cmake', 'Plugins'),
    ('OrthancServer/Plugins/Samples/Common/VersionScriptPlugins.map', 'Plugins'),
]

SDK = [
    'orthanc/OrthancCPlugin.h',
]


def Download(x):
    branch = x[0]
    source = x[1]
    target = os.path.join(TARGET, x[2])
    print(target)

    try:
        os.makedirs(os.path.dirname(target))
    except:
        pass

    url = '%s/%s/%s' % (REPOSITORY, branch, source)

    with open(target, 'wb') as f:
        try:
            f.write(urllib.request.urlopen(url).read())
        except:
            print('ERROR %s' % url)
            raise


commands = []

for f in FILES:
    commands.append([ 'default',
                      f[0],
                      os.path.join(f[1], os.path.basename(f[0])) ])

for f in SDK:
    commands.append([
        'Orthanc-%s' % PLUGIN_SDK_VERSION, 
        'OrthancServer/Plugins/Include/%s' % f,
        'Sdk-%s/%s' % (PLUGIN_SDK_VERSION, f) 
    ])


pool = multiprocessing.Pool(10)  # simultaneous downloads
pool.map(Download, commands)


# Patch the SDK, if need be
patch = os.path.join(os.path.abspath(os.path.dirname(__file__)),
                     'OrthancCPlugin-%s.patch' % PLUGIN_SDK_VERSION)
if os.path.exists(patch):
    subprocess.check_call([ 'patch', '-p0', '-i', patch ],
                          cwd = os.path.join(os.path.dirname(__file__),
                                             'Orthanc',
                                             'Sdk-%s' % PLUGIN_SDK_VERSION, 'orthanc'))
