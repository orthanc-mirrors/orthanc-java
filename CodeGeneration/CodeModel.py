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


import json
import re


def ReadOrthancSdkDefaultVersion(path):
    with open(path, 'r') as f:
        m = re.findall(r'^set\(ORTHANC_SDK_DEFAULT_VERSION "([^"]+)"\)$', f.read(), re.MULTILINE)
        assert(len(m) == 1)
        return m[0]


def ReadOrthancSdkVersion(path):
    with open(path, 'r') as f:
        content = f.read()

        major = re.findall(r'#\s*define\s+ORTHANC_PLUGINS_MINIMAL_MAJOR_NUMBER\s+([0-9.]+)$', content, re.MULTILINE)
        minor = re.findall(r'#\s*define\s+ORTHANC_PLUGINS_MINIMAL_MINOR_NUMBER\s+([0-9.]+)$', content, re.MULTILINE)
        revision = re.findall(r'#\s*define\s+ORTHANC_PLUGINS_MINIMAL_REVISION_NUMBER\s+([0-9.]+)$', content, re.MULTILINE)
        assert(len(major) == 1)
        assert(len(minor) == 1)
        assert(len(revision) == 1)

        return [ int(major[0]), int(minor[0]), int(revision[0]) ]


def IsPrimitiveAvailable(sdk_version, primitive, key_prefix = ''):
    since_sdk = primitive.get('since_sdk')
    if since_sdk != None:
        assert(len(since_sdk) == 3)
        assert(len(sdk_version) == 3)
        if since_sdk[0] < sdk_version[0]:
            available = True
        elif since_sdk[0] > sdk_version[0]:
            available = False
        elif since_sdk[1] < sdk_version[1]:
            available = True
        elif since_sdk[1] > sdk_version[1]:
            available = False
        else:
            available = since_sdk[2] <= sdk_version[2]

        if not available:
            name = primitive.get('name')
            if name == None:
                name = primitive.get('c_function')
            if name == None:
                name = primitive.get('short_name')
            if name == None:
                # For enumerations
                key = primitive.get('key')
                if key != None:
                    name = '%s_%s' % (key_prefix, key)
            assert(name != None)
            print('Primitive unavailable in SDK: %s (only available since %s)' % (name, '.'.join(map(str, since_sdk))))

        return available
    else:
        return True


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


def RemovePrefix(prefix, s, isCamelCase):
    assert(s.startswith(prefix))
    t = s[len(prefix):]
    if isCamelCase:
        return t[0].lower() + t[1:]
    else:
        return t


def RemoveOrthancPluginPrefix(s, isCamelCase):
    CUSTOM_PREFIX = 'OrthancPluginCustom_'
    if s.startswith(CUSTOM_PREFIX):
        return RemovePrefix(CUSTOM_PREFIX, s, isCamelCase)

    PREFIX = 'OrthancPlugin'
    if s.startswith(PREFIX):
        return RemovePrefix(PREFIX, s, isCamelCase)

    raise Exception('Incorrect prefix: %s' % s)


def GetJavaMethodName(className, f):
    if 'short_name' in f:
        return f['short_name']
    elif (className != None and
          f['c_function'].startswith(className)):
        return RemovePrefix(className, f['c_function'], True)
    else:
        return RemoveOrthancPluginPrefix(f['c_function'], True)


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
            'is_basic_type' : True,
            'java_signature' : 'I',
            'java_type' : 'int',
            }
    elif f['return_sdk_type'] in [ 'int64_t', 'uint64_t' ]:
        result = {
            'c_type' : 'jlong',
            'default_value' : '0',
            'is_basic_type' : True,
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
    elif f['return_sdk_type'] == 'bool':
        result = {
            'c_type' : 'jboolean',
            'default_value' : 'false',
            'is_basic_type' : True,
            'java_signature' : 'Z',
            'java_type' : 'boolean',
            }
    else:
        raise Exception('Unsupported return type: %s' % json.dumps(f, indent=4))

    if not 'java_wrapper_type' in result:
        result['java_wrapper_type'] = result['java_type']

    return result

def ConvertArgument(arg):
    result = None

    if 'name' in arg:
        name = arg['name']
    else:
        name = arg['sdk_name']

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
    elif arg['sdk_type'] in [ 'int64_t', 'uint64_t' ]:
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
            'c_accessor' : 'c_%s.GetValue()' % name,
            'c_type' : 'jstring',
            'convert_string' : True,
            'java_signature' : 'Ljava/lang/String;',
            'java_type' : 'String',
        }
    elif arg['sdk_type'] == 'const_void_pointer_with_size':
        # NB: The cast to "const char*" allows compatibility with functions whose
        # signatures were incorrect at the time they were introduced, notably:
        #   - argument "body" of "OrthancPluginSendHttpStatus()" in 1.11.1

        result = {
            'c_accessor' : 'reinterpret_cast<const char*>(c_%s.GetData()), c_%s.GetSize()' % (name, name),
            'c_type' : 'jbyteArray',
            'convert_bytes' : True,
            'java_signature' : '[B',
            'java_type' : 'byte[]',
        }
    elif arg['sdk_type'] == 'enumeration':
        result = {
            'c_accessor' : 'static_cast<%s>(%s)' % (arg['sdk_enumeration'], name),
            'c_type' : 'jint',
            'java_wrapper_accessor' : '%s.getValue()' % arg['sdk_name'],
            'java_wrapper_type' : RemoveOrthancPluginPrefix(arg['sdk_enumeration'], False),
            'java_signature' : 'I',
            'java_type' : 'int',
            }
    elif arg['sdk_type'] == 'const void *':
        result = {
            'c_accessor' : 'c_%s.GetData()' % name,
            'c_type' : 'jbyteArray',
            'convert_bytes' : True,
            'java_signature' : '[B',
            'java_type' : 'byte[]',
        }
    elif arg['sdk_type'] in [ 'object', 'const_object' ]:
        result = {
            'c_accessor' : 'reinterpret_cast<%s*>(static_cast<intptr_t>(%s))' % (arg['sdk_class'], name),
            'c_type' : 'jlong',
            'java_signature' : 'J',
            'java_type' : 'long',
            'java_wrapper_accessor' : '%s.getSelf()' % arg['sdk_name'],
            'java_wrapper_type' : RemoveOrthancPluginPrefix(arg['sdk_class'], False),
        }
    else:
        raise Exception('Unsupported argument type: %s' % json.dumps(arg, indent=4))

    result['name'] = name
    result['sdk_name'] = arg['sdk_name']
    result['sdk_type'] = arg['sdk_type']

    if not 'java_wrapper_type' in result:
        result['java_wrapper_type'] = result['java_type']

    if not 'java_wrapper_accessor' in result:
        result['java_wrapper_accessor'] = arg['sdk_name']

    if not 'c_accessor' in result:
        result['c_accessor'] = name

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


def WrapFunction(cls, f, is_custom = False):
    if cls == None:
        className = None
    else:
        className = cls['name']

    args = []
    for a in f['args']:
        args.append(ConvertArgument(a))

    if len(args) > 0:
        args[-1]['last'] = True

    returnType = ConvertReturnType(f)
    signature = '(%s%s)%s' % ('J' if cls != None else '',
                              ''.join(map(lambda x: x['java_signature'], args)),
                              returnType['java_signature'])

    since_sdk = f.get('since_sdk')
    if (since_sdk == None and
        cls != None):
        since_sdk = cls.get('since_sdk')

    result = {
        'args' : args,
        'c_function' : f['c_function'],
        'class_name' : className,
        'has_args' : len(args) > 0,
        'java_signature' : signature,
        'return' : returnType,
        'java_name' : GetJavaMethodName(className, f),
        'since_sdk' : since_sdk,
        'is_custom' : is_custom,
        'return_sdk_type' : f['return_sdk_type'],
    }

    if 'documentation' in f:
        result['has_documentation'] = True
        result['documentation'] = EncodeFunctionDocumentation(f)

    if (returnType.get('is_basic_type') == True or
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


def Load(path, customFunctionsPath = None, customMethodsPath = None):
    with open(path, 'r') as f:
        model = json.loads(f.read())

    if customMethodsPath == None:
        customMethods = {}
    else:
        with open(customMethodsPath, 'r') as f:
            customMethods = json.loads(f.read())

    if customFunctionsPath == None:
        customFunctions = {}
    else:
        with open(customFunctionsPath, 'r') as f:
            customFunctions = json.loads(f.read())

    for enum in model['enumerations']:
        if not enum['name'].startswith('OrthancPlugin'):
            raise Exception()

        enum['short_name'] = enum['name'][len('OrthancPlugin'):]

        for i in range(len(enum['values'])):
            enum['values'][i]['key'] = ToUpperCase(enum['values'][i]['key'])

            if 'documentation' in enum['values'][i]:
                enum['values'][i]['has_documentation'] = True
                enum['values'][i]['documentation'] = list(map(lambda x: { 'line' : x },
                                                              FixLinesWidth([ enum['values'][i]['documentation'] ])))

        if 'documentation' in enum:
            enum['has_documentation'] = True
            enum['documentation'] = list(map(lambda x: { 'line' : x },
                                             FixLinesWidth([ enum['documentation'] ])))

    nativeFunctions = []

    for f in model['global_functions']:
        nativeFunctions.append(WrapFunction(None, f))

    for f in customFunctions:
        nativeFunctions.append(WrapFunction(None, f, is_custom = True))

    for c in model['classes']:
        c['short_name'] = RemoveOrthancPluginPrefix(c['name'], False)

        if 'destructor' in c:
            nativeFunctions.append(WrapFunction(c, {
                'args' : [],
                'c_function' : c['destructor'],
                'return_sdk_type' : 'void',
                'since_sdk' : c.get('since_sdk'),
            }))

        wrappedMethods = []

        for m in c['methods']:
            nativeFunctions.append(WrapFunction(c, m))
            wrappedMethods.append(WrapFunction(c, m))

        for customMethod in customMethods.get(c['name'], []):
            nativeFunctions.append(WrapFunction(c, customMethod, is_custom = True))
            wrappedMethods.append(WrapFunction(c, customMethod, is_custom = True))

        c['wrapped_methods'] = wrappedMethods

    model['native_functions'] = nativeFunctions

    return model
