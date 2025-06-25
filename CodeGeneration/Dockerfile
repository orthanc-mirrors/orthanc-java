#
# SPDX-FileCopyrightText: 2023-2025 Sebastien Jodogne, ICTEAM UCLouvain, Belgium
# SPDX-License-Identifier: GPL-3.0-or-later
#

#### sample usage to build the image:
# cd CodeGeneration
# docker build -t code-model-generator .

### sample usage to generate a CodeModel.json in /tmp on your machine (note, it assumes you have an 'orthanc' folder at the same level as 'orthanc-java' folder)
# docker run --rm -v /tmp:/out -v $(pwd):/app -v $(pwd)/../OrthancSDKVersion.cmake:/OrthancSDKVersion.cmake -v $(pwd)/../../orthanc/OrthancServer/Plugins/Include/orthanc/:/sdk code-model-generator python3 ParseOrthancSDK.py --libclang /usr/lib/x86_64-linux-gnu/libclang-6.0.so.1 --source /sdk/OrthancCPlugin.h --target /out/CodeModel.json

FROM ubuntu:18.04

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update && apt-get install -y \
    python3 \
    python3-pip
    

RUN apt-get install -y clang-6.0

RUN pip3 install pystache
RUN pip3 install clang==6.0.0

WORKDIR /app
