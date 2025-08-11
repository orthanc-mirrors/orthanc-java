
Introduction
============

This folder contains the Python script that generates the Java wrapper
from the code model of the Orthanc SDK.

The Java wrapper is written to:   ../JavaSDK/be/uclouvain/orthanc/

The C++ interface is written to:  ../Plugin/NativeSDK.cpp


Usage on Ubuntu 22.04
=====================

$ sudo apt-get install python3-clang-14 python3-pystache
$ python3 ./CodeGeneration.py --source ../Resources/CodeModel-1.10.0.json
