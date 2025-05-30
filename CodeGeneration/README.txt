
Introduction
============

This folder contains the two Python scripts that analyze the header of
the Orthanc Plugin SDK using clang, then extract the code model of the
SDK as a JSON file, and finally create the Java wrapper.

The code model is written to:     ../CodeGeneration/CodeModel.json

The Java wrapper is written to:   ../JavaSDK/be/uclouvain/orthanc/

The C++ interface is written to:  ../Plugin/NativeSDK.cpp

Note that the generated code model is also used in the orthanc-python
project, starting with its release 4.3.


Usage on Ubuntu 22.04
=====================

$ sudo apt-get install python3-clang-14 python3-pystache
$ python3 ./ParseOrthancSDK.py --libclang=libclang-14.so.1 \
          --source ../Resources/Orthanc/Sdk-1.10.0/orthanc/OrthancCPlugin.h \
          --target ../Resources/CodeModel-1.10.0.json
$ python3 ./CodeGeneration.py --source ../Resources/CodeModel-1.10.0.json
