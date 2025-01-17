#!/usr/bin/env python3

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


import argparse
import json
import os
import pystache


SOURCE = os.path.abspath(os.path.dirname(__file__))

parser = argparse.ArgumentParser(description = 'Generate Java wrapper from code model.')
parser.add_argument('--source',
                    default = os.path.join(SOURCE, 'CodeModel.json'),
                    help = 'location of the JSON code model')

args = parser.parse_args()



TARGET = os.path.join(SOURCE, '..', 'JavaSDK', 'be', 'uclouvain', 'orthanc')

with open(args.source, 'r') as f:
    model = json.loads(f.read())

with open(os.path.join(SOURCE, 'ClassDocumentation.json'), 'r') as f:
    classDocumentation = json.loads(f.read())

    
renderer = pystache.Renderer(
    escape = lambda u: u,  # No escaping
)


def ToUpperCase(name):
    s = ''
    for i in range(len(name)):
        if name[i].isupper():
            if len(s) == 0:
                s += name[i]
            elif name[i - 1].islower():
                s += '_' + name[i]
            elif (i + 1 < len(name) and
                  name[i - 1].islower() and
                  name[i + 1].isupper()):
                s += '_' + name[i]
            else:
                s += name[i]
        else:
            s += name[i].upper()
    return s


def RemoveOrthancPluginPrefix(s, isCamelCase):
    PREFIX = 'OrthancPlugin'
    if s.startswith(PREFIX):
        t = s[len(PREFIX):]
        if isCamelCase:
            t = t[0].lower() + t[1:]
        return t
    else:
        raise Exception('Incorrect prefix: %s' % s)


def ConvertReturnType(f):
    result = None

    if f['return_sdk_type'] == 'void':
        result = {
            'c_type' : 'void',
            'is_void' : True,
            'java_signature' : 'V',
            'java_type' : 'void',
            }
    elif f['return_sdk_type'] in [ 'int', 'int32_t', 'uint32_t' ]:
        result = {
            'c_type' : 'jint',
            'default_value' : '0',
            'is_number' : True,
            'java_signature' : 'I',
            'java_type' : 'int',
            }
    elif f['return_sdk_type'] in [ 'int64_t' ]:
        result = {
            'c_type' : 'jlong',
            'default_value' : '0',
            'is_number' : True,
            'java_signature' : 'J',
            'java_type' : 'long',
            }
    elif f['return_sdk_type'] == 'OrthancPluginMemoryBuffer *':
        result = {
            'c_type' : 'jbyteArray',
            'default_value' : 'NULL',
            'is_bytes' : True,
            'java_signature' : '[B',
            'java_type' : 'byte[]',
            }
    elif f['return_sdk_type'] == 'enumeration':
        if f['return_sdk_enumeration'] == 'OrthancPluginErrorCode':
            result = {
                'c_type' : 'void',
                'is_exception' : True,
                'java_signature' : 'V',
                'java_type' : 'void',
            }
        else:
            result = {
                'c_type' : 'jint',
                'default_value' : '0',
                'is_enumeration' : True,
                'java_wrapper_type' : RemoveOrthancPluginPrefix(f['return_sdk_enumeration'], False),
                'java_signature' : 'I',
                'java_type' : 'int',
            }
    elif f['return_sdk_type'] == 'object':
        result = {
            'c_type' : 'jlong',
            'class_name' : f['return_sdk_class'],
            'default_value' : '0',
            'is_object' : True,
            'java_wrapper_type' : RemoveOrthancPluginPrefix(f['return_sdk_class'], False),
            'java_signature' : 'J',
            'java_type' : 'long',
            }
    elif f['return_sdk_type'] == 'char *':
        result = {
            'c_type' : 'jstring',
            'default_value' : 'NULL',
            'is_dynamic_string' : True,
            'java_signature' : 'Ljava/lang/String;',
            'java_type' : 'String',
            }
    elif f['return_sdk_type'] == 'const char *':
        result = {
            'c_type' : 'jstring',
            'default_value' : 'NULL',
            'is_static_string' : True,
            'java_signature' : 'Ljava/lang/String;',
            'java_type' : 'String',
            }
    else:
        raise Exception('Unsupported return type: %s' % json.dumps(f, indent=4))

    if not 'java_wrapper_type' in result:
        result['java_wrapper_type'] = result['java_type']

    return result

def ConvertArgument(arg):
    result = None

    if arg['sdk_type'] in [ 'int', 'int32_t', 'uint32_t' ]:
        result = {
            'c_type' : 'jint',
            'java_signature' : 'I',
            'java_type' : 'int',
        }
    elif arg['sdk_type'] == 'uint8_t':
        result = {
            'c_type' : 'jbyte',
            'java_signature' : 'B',
            'java_type' : 'byte',
        }
    elif arg['sdk_type'] == 'uint16_t':
        result = {
            'c_type' : 'jshort',
            'java_signature' : 'S',
            'java_type' : 'short',
        }
    elif arg['sdk_type'] == 'uint64_t':
        result = {
            'c_type' : 'jlong',
            'java_signature' : 'J',
            'java_type' : 'long',
        }
    elif arg['sdk_type'] == 'float':
        result = {
            'c_type' : 'jfloat',
            'java_signature' : 'F',
            'java_type' : 'float',
        }
    elif arg['sdk_type'] == 'const char *':
        result = {
            'c_accessor' : 'c_%s.GetValue()' % arg['name'],
            'c_type' : 'jstring',
            'convert_string' : True,
            'java_signature' : 'Ljava/lang/String;',
            'java_type' : 'String',
        }
    elif arg['sdk_type'] == 'const_void_pointer_with_size':
        result = {
            'c_accessor' : 'c_%s.GetData(), c_%s.GetSize()' % (arg['name'], arg['name']),
            'c_type' : 'jbyteArray',
            'convert_bytes' : True,
            'java_signature' : '[B',
            'java_type' : 'byte[]',
        }
    elif arg['sdk_type'] == 'enumeration':
        result = {
            'c_accessor' : 'static_cast<%s>(%s)' % (arg['sdk_enumeration'], arg['name']),
            'c_type' : 'jint',
            'java_wrapper_accessor' : '%s.getValue()' % arg['sdk_name'],
            'java_wrapper_type' : RemoveOrthancPluginPrefix(arg['sdk_enumeration'], False),
            'java_signature' : 'I',
            'java_type' : 'int',
            }
    elif arg['sdk_type'] == 'const void *':
        result = {
            'c_accessor' : 'c_%s.GetData()' % arg['name'],
            'c_type' : 'jbyteArray',
            'convert_bytes' : True,
            'java_signature' : '[B',
            'java_type' : 'byte[]',
        }
    elif arg['sdk_type'] in [ 'object', 'const_object' ]:
        result = {
            'c_accessor' : 'reinterpret_cast<%s*>(static_cast<intptr_t>(%s))' % (arg['sdk_class'], arg['name']),
            'c_type' : 'jlong',
            'java_signature' : 'J',
            'java_type' : 'long',
            'java_wrapper_accessor' : '%s.getSelf()' % arg['sdk_name'],
            'java_wrapper_type' : RemoveOrthancPluginPrefix(arg['sdk_class'], False),
        }
    else:
        raise Exception('Unsupported argument type: %s' % json.dumps(arg, indent=4))

    result['name'] = arg['name']
    result['sdk_name'] = arg['sdk_name']

    if not 'java_wrapper_type' in result:
        result['java_wrapper_type'] = result['java_type']

    if not 'java_wrapper_accessor' in result:
        result['java_wrapper_accessor'] = arg['sdk_name']

    if not 'c_accessor' in result:
        result['c_accessor'] = arg['name']

    return result


def FixLinesWidth(source):
    target = []

    for line in source:
        for word in line.split(' '):
            if len(target) == 0:
                target.append(word)
            elif len(target[-1]) == 0:
                target[-1] = word
            elif len(target[-1] + ' ' + word) <= 80:
                target[-1] = target[-1] + ' ' + word
            else:
                target.append(word)
        target.append('')

    while len(target) > 0:
        if target[-1] == '':
            target = target[:-1]
        else:
            break

    return target


def EncodeFunctionDocumentation(f):
    documentation = f['documentation']
    
    paragraphs = [ ]
    if 'summary' in documentation:
        paragraphs.append(documentation['summary'])
        paragraphs.append('')

    if 'description' in documentation:
        for line in documentation['description']:
            paragraphs.append(line)
            paragraphs.append('')

    if 'args' in documentation:
        for arg in f['args']:
            name = arg['sdk_name']
            if name in documentation['args']:
                doc = documentation['args'][name]
                paragraphs.append('@param %s %s' % (name, doc))

    if 'return' in documentation:
        if (f['return_sdk_type'] == 'enumeration' and
            f['return_sdk_enumeration'] == 'OrthancPluginErrorCode'):
            pass
        elif f['return_sdk_type'] == 'object':
            paragraphs.append('@return The newly constructed object.')
        elif f['return_sdk_type'] in [ 'char *', 'const char *' ]:
            paragraphs.append('@return The resulting string.')
        elif f['return_sdk_type'] == 'OrthancPluginMemoryBuffer *':
            paragraphs.append('@return The resulting memory buffer.')
        else:
            paragraphs.append('@return ' + documentation['return'])

    lines = FixLinesWidth(paragraphs)

    return list(map(lambda x: { 'line' : x }, lines))


def EncodeFunction(className, f):
    args = []
    for a in f['args']:
        args.append(ConvertArgument(a))

    if len(args) > 0:
        args[-1]['last'] = True

    returnType = ConvertReturnType(f)
    signature = '(%s%s)%s' % ('J' if className != None else '',
                              ''.join(map(lambda x: x['java_signature'], args)),
                              returnType['java_signature'])

    result = {
        'args' : args,
        'c_function' : f['c_function'],
        'class_name' : className,
        'has_args' : len(args) > 0,
        'java_signature' : signature,
        'return' : returnType,
        'java_name' : RemoveOrthancPluginPrefix(f['c_function'], True),
    }

    if 'documentation' in f:
        result['has_documentation'] = True
        result['documentation'] = EncodeFunctionDocumentation(f)

    if (returnType.get('is_number') == True or
        returnType.get('is_bytes') == True or
        returnType.get('is_dynamic_string') == True or
        returnType.get('is_static_string') == True):
        result['java_return_start'] = 'return '

    elif returnType.get('is_enumeration') == True:
        result['java_return_start'] = 'return %s.getInstance(' % returnType['java_wrapper_type']
        result['java_return_end'] = ')'

    elif returnType.get('is_object') == True:
        result['java_return_start'] = 'return new %s(' % returnType['java_wrapper_type']
        result['java_return_end'] = ')'

    return result


nativeFunctions = []

for f in model['global_functions']:
    nativeFunctions.append(EncodeFunction(None, f))


for c in model['classes']:
    if 'destructor' in c:
        nativeFunctions.append(EncodeFunction(c['name'], {
            'args' : [],
            'c_function' : c['destructor'],
            'return_sdk_type' : 'void',
        }))

    for m in c['methods']:
        nativeFunctions.append(EncodeFunction(c['name'], m))
        

with open(os.path.join(SOURCE, 'JavaNativeSDK.mustache'), 'r') as f:
    template = f.read()

    with open(os.path.join(TARGET, 'NativeSDK.java'), 'w') as g:
        g.write(renderer.render(template, {
            'functions' : nativeFunctions
        }))


with open(os.path.join(SOURCE, 'JavaFunctions.mustache'), 'r') as f:
    template = f.read()

    with open(os.path.join(TARGET, 'Functions.java'), 'w') as g:
        g.write(renderer.render(template, {
            'functions' : filter(lambda x: (x['class_name'] == None and
                                            x['return'].get('is_object') != True), nativeFunctions),
        }))


with open(os.path.join(SOURCE, 'CppNativeSDK.mustache'), 'r') as f:
    template = f.read()

    with open(os.path.join(SOURCE, '..', 'Plugin', 'NativeSDK.cpp'), 'w') as g:
        s = renderer.render(template, {
            'functions' : nativeFunctions
        })

        s = s.splitlines()
        s = filter(lambda l: not l.isspace() or len(l) == 0, s)
        
        g.write('\n'.join(s))

        

for enum in model['enumerations']:
    if not enum['name'].startswith('OrthancPlugin'):
        raise Exception()

    enum['short_name'] = enum['name'][len('OrthancPlugin'):]

    for i in range(len(enum['values'])):
        enum['values'][i]['key'] = ToUpperCase(enum['values'][i]['key'])

        if 'documentation' in enum['values'][i]:
            enum['values'][i]['has_documentation'] = True
            enum['values'][i]['documentation'] = list(map(lambda x: { 'line' : x }, FixLinesWidth([ enum['values'][i]['documentation'] ])))

    enum['values'][-1]['last'] = True

    if 'documentation' in enum:
        enum['has_documentation'] = True
        enum['documentation'] = list(map(lambda x: { 'line' : x }, FixLinesWidth([ enum['documentation'] ])))

    with open(os.path.join(SOURCE, 'JavaEnumeration.mustache'), 'r') as f:
        template = f.read()
        
        with open(os.path.join(TARGET, '%s.java' % enum['short_name']), 'w') as g:
            g.write(renderer.render(template, enum))



for cls in model['classes']:
    shortName = RemoveOrthancPluginPrefix(cls['name'], False)

    with open(os.path.join(SOURCE, 'JavaClass.mustache'), 'r') as f:
        template = f.read()

        methods = []
        for m in cls['methods']:
            methods.append(EncodeFunction(shortName, m))

        constructors = []
        for f in nativeFunctions:
            if (f['class_name'] == None and
                f['return'].get('is_object') == True and
                f['return']['class_name'] == cls['name']):
                constructors.append(f)

        with open(os.path.join(TARGET, '%s.java' % shortName), 'w') as g:
            if not cls['name'] in classDocumentation:
                raise Exception('No global documentation for class: %s' % cls['name'])

            g.write(renderer.render(template, {
                'destructor' : cls.get('destructor'),
                'class_name' : shortName,
                'methods' : methods,
                'constructors' : constructors,
                'has_documentation' : True,
                'documentation' : classDocumentation[cls['name']],
            }))
