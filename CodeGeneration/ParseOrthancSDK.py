#!/usr/bin/env python3

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


import argparse
import clang.cindex
import json
import os
import pprint
import pystache
import re
import sys


ROOT = os.path.dirname(os.path.realpath(sys.argv[0]))


parser = argparse.ArgumentParser(description = 'Parse the Orthanc SDK.')
parser.add_argument('--libclang',
                    default = 'libclang-6.0.so.1',
                    help = 'manually provides the path to the libclang shared library')
parser.add_argument('--source',
                    default = os.path.join(ROOT, '../Resources/Orthanc/Sdk-1.10.0/orthanc/OrthancCPlugin.h'),
                    help = 'input path to the Orthanc SDK header')
parser.add_argument('--target',
                    default = os.path.join(ROOT, 'CodeModel.json'),
                    help = 'target path to store the JSON code model')

args = parser.parse_args()

if len(args.libclang) != 0:
    clang.cindex.Config.set_library_file(args.libclang)

index = clang.cindex.Index.create()

tu = index.parse(args.source, [ ])

TARGET = os.path.realpath(args.target)



SPECIAL_FUNCTIONS = [
    'OrthancPluginCreateMemoryBuffer',
    'OrthancPluginCreateMemoryBuffer64',
    'OrthancPluginFreeMemoryBuffer',
    'OrthancPluginFreeMemoryBuffer64',
    'OrthancPluginFreeString',
    ]



# First, discover the classes and enumerations
classes = {}
enumerations = {}

def ParseDocumentationLines(comment):
    s = re.sub('^[ ]*/', '', comment)
    s = re.sub('/[ ]*$', '', s)
    s = re.sub('<tt>', '"', s)
    s = re.sub('</tt>', '"', s)
    return list(map(lambda x: re.sub('[ ]*\*+', '', x).strip(), s.splitlines()))

def ParseEnumerationDocumentation(comment):
    result = ''
    for line in ParseDocumentationLines(comment):
        if len(line) > 0 and not line.startswith('@'):
            if len(result) == 0:
                result = line
            else:
                result = result + ' ' + line
    return result

def ParseEnumValueDocumentation(comment):
    m = re.match(r'/\*!<\s*(.*?)\s*\*/$', comment, re.MULTILINE)
    if m != None:
        return m.group(1)
    else:
        result = ''
        for line in ParseDocumentationLines(comment):
            if len(line) > 0:
                if len(result) == 0:
                    result = line
                else:
                    result = result + ' ' + line
        return result.replace('@brief ', '')

for node in tu.cursor.get_children():
    # Only consider the Orthanc SDK
    path = node.location.file.name
    if os.path.split(path) [-1] != 'OrthancCPlugin.h':
        continue

    if node.kind == clang.cindex.CursorKind.ENUM_DECL:
        if node.type.spelling.startswith('OrthancPlugin'):
            name = node.type.spelling

            if name in enumerations:
                raise Exception('Enumeration declared twice: %s' % name)

            if node.raw_comment == None:
                raise Exception('Enumeration without documentation: %s' % name)

            values = []
            for item in node.get_children():
                if (item.kind == clang.cindex.CursorKind.ENUM_CONSTANT_DECL and
                    item.spelling.startswith(name + '_')):

                    if item.raw_comment == None:
                        raise Exception('Enumeration value without documentation: %s' % item.spelling)

                    key = item.spelling[len(name + '_'):]
                    values.append({
                        'key' : key,
                        'value' : item.enum_value,
                        'documentation' : ParseEnumValueDocumentation(item.raw_comment),
                    })

                elif (item.kind == clang.cindex.CursorKind.ENUM_CONSTANT_DECL and
                      item.spelling == '_%s_INTERNAL' % name):
                    pass

                else:
                    raise Exception('Ignoring unknown enumeration item: %s' % item.spelling)

            enumerations[name] = {
                'values' : values,
                'documentation' : ParseEnumerationDocumentation(node.raw_comment),
            }

        elif node.spelling == '':  # Unnamed enumeration (presumbaly "_OrthancPluginService")
            pass

        else:
            raise Exception('Ignoring unknown enumeration: %s' % node.spelling)

    elif node.kind == clang.cindex.CursorKind.STRUCT_DECL:
        if (node.spelling.startswith('_OrthancPlugin') and
            node.spelling.endswith('_t') and
            node.spelling != '_OrthancPluginContext_t'):

            name = node.spelling[len('_') : -len('_t')]
            classes[name] = {
                'name' : name,
                'methods' : [ ],
            }

        elif node.spelling in [ '',  # This is an internal structure to call Orthanc SDK
                                '_OrthancPluginContext_t' ]:
            pass

        else:
            raise Exception('Ignoring unknown structure: %s' % node.spelling)


# Secondly, loop over the C functions and categorize them either as
# method, or as global functions


def RemovePrefix(prefix, s):
    if not s.startswith(prefix):
        raise Exception('String "%s" does not start with prefix "%s"' % (s, prefix))
    else:
        return s[len(prefix):]


def IsClassType(t):
    return (t.kind == clang.cindex.TypeKind.POINTER and
            not t.get_pointee().is_const_qualified() and
            t.get_pointee().spelling in classes)


def IsConstClassType(t):
    return (t.kind == clang.cindex.TypeKind.POINTER and
            t.get_pointee().is_const_qualified() and
            t.get_pointee().spelling.startswith('const ') and
            t.get_pointee().spelling[len('const '):] in classes)


def EncodeArguments(target, args):
    assert(type(target) is dict)
    result = []

    i = 0
    while i < len(args):
        arg = {
            'name' : 'arg%d' % i,
            'sdk_name' : args[i].spelling,
            'sdk_type' : args[i].type.spelling,
            }

        if (i + 1 < len(args) and
            args[i].type.spelling == 'const void *' and
            args[i + 1].type.spelling == 'uint32_t'):

            arg['sdk_type'] = 'const_void_pointer_with_size'

            # Skip the size argument
            i += 1

        elif arg['sdk_type'] in [ 'float',
                                  'int32_t',
                                  'uint8_t',
                                  'uint16_t',
                                  'uint32_t',
                                  'uint64_t',
                                  'const char *',
                                  'const void *' ]:
            pass

        elif arg['sdk_type'] in enumerations:
            arg['sdk_type'] = 'enumeration'
            arg['sdk_enumeration'] = args[i].type.spelling

        elif IsClassType(args[i].type):
            arg['sdk_type'] = 'object'
            arg['sdk_class'] = args[i].type.get_pointee().spelling

        elif IsConstClassType(args[i].type):
            arg['sdk_type'] = 'const_object'
            arg['sdk_class'] = RemovePrefix('const ', args[i].type.get_pointee().spelling)

        else:
            print('[WARNING] Unsupported argument type in a method (%s), cannot wrap: %s' % (
                args[i].type.spelling, node.spelling))
            return False

        result.append(arg)
        i += 1

    target['args'] = result
    return True


def EncodeResultType(target, returnBufferType, t):
    assert(type(target) is dict)
    assert('args' in target)

    target['return_sdk_type'] = t.spelling

    if returnBufferType != None:
        target['return_sdk_type'] = returnBufferType
        return True

    elif target['return_sdk_type'] in [ 'void',
                                        'int32_t',
                                        'uint32_t',
                                        'int64_t',
                                        'char *',
                                        'const char *' ]:
        return True

    elif target['return_sdk_type'] in enumerations:
        target['return_sdk_type'] = 'enumeration'
        target['return_sdk_enumeration'] = t.spelling
        return True

    elif IsClassType(t):
        target['return_sdk_type'] = 'object'
        target['return_sdk_class'] = t.get_pointee().spelling
        return True

    else:
        return False


def ParseFunctionDocumentation(comment):
    lines = ParseDocumentationLines(comment)

    sections = []
    currentType = None
    currentSection = None

    for i in range(len(lines)):
        if lines[i].find('@') > 0:
            raise Exception('Character "@" not occurring at the beggining of a documentation paragraph')

        if (len(lines[i]) == 0 and
            currentType == None):
            continue

        m = re.match(r'^@([a-z]+)\s*', lines[i])

        if m == None:
            if currentType == None:
                print(comment)
                raise Exception('Documentation does not begin with a "@"')

            assert(currentSection != None)
            currentSection.append(lines[i])
        else:
            if currentType != None:
                sections.append({
                    'type' : currentType,
                    'lines' : currentSection,
                    })

            currentType = m.group(1)
            currentSection = [ lines[i][m.span() [1] : ] ]

    if currentType == None:
        raise Exception('Empty documentation')

    sections.append({
        'type' : currentType,
        'lines' : currentSection,
    })

    for i in range(len(sections)):
        paragraphs = []
        lines = sections[i]['lines']
        currentParagraph = ''
        for j in range(len(lines)):
            if len(lines[j]) == 0:
                if currentParagraph != '':
                    paragraphs.append(currentParagraph)
                currentParagraph = ''
            else:
                if currentParagraph == '':
                    currentParagraph = lines[j]
                else:
                    currentParagraph = '%s %s' % (currentParagraph, lines[j])
        if currentParagraph != '':
            paragraphs.append(currentParagraph)

        sections[i]['paragraphs'] = paragraphs

    documentation = {
        'args' : {}
    }

    for i in range(len(sections)):
        t = sections[i]['type']
        paragraphs = sections[i]['paragraphs']

        if t == 'brief':
            if len(paragraphs) < 1:
                raise Exception('Bad @brief')

            documentation['summary'] = paragraphs[0]
            documentation['description'] = paragraphs[1:]

        elif t in [ 'return', 'result' ]:
            if len(paragraphs) != 1:
                raise Exception('Bad @return')

            documentation['return'] = paragraphs[0]

        elif t == 'param':
            if len(paragraphs) != 1:
                raise Exception('Bad @param')

            m = re.match(r'^([a-zA-Z0-9]+)\s+(.+)', paragraphs[0])
            if m == None:
                raise Exception('Bad @param')

            key = m.group(1)
            value = m.group(2)
            if (len(key) == 0 or
                len(value) == 0):
                raise Exception('Bad @param')

            if key in documentation['args']:
                raise Exception('Twice the same parameter: %s' % key)

            documentation['args'][key] = value

        elif t == 'warning':
            if not 'description' in documentation:
                raise Exception('@warning before @summary')

            if len(paragraphs) == 0:
                raise Exception('Bad @warning')

            for j in range(len(paragraphs)):
                if j == 0:
                    documentation['description'].append('Warning: %s' % paragraphs[j])
                else:
                    documentation['description'].append(paragraphs[j])

        elif t == 'note':
            if not 'description' in documentation:
                raise Exception('@note before @summary')

            if len(paragraphs) == 0:
                raise Exception('Bad @note')

            for j in range(len(paragraphs)):
                if j == 0:
                    documentation['description'].append('Remark: %s' % paragraphs[j])
                else:
                    documentation['description'].append(paragraphs[j])

        elif t in [
                'deprecated',
                'ingroup',
                'see',
        ]:
            pass

        else:
            raise Exception('Unsupported documentation token: @%s' % t)

    return documentation


globalFunctions = []
countWrappedFunctions = 0
countAllFunctions = 0

for node in tu.cursor.get_children():
    # Only consider the Orthanc SDK
    path = node.location.file.name
    if os.path.split(path) [-1] != 'OrthancCPlugin.h':
        continue

    if (node.kind == clang.cindex.CursorKind.FUNCTION_DECL and
        node.spelling.startswith('OrthancPlugin')):

        if node.spelling in SPECIAL_FUNCTIONS:
            countAllFunctions += 1
            continue

        args = list(filter(lambda x: x.kind == clang.cindex.CursorKind.PARM_DECL,
                           node.get_children()))

        # Check that the first argument is the Orthanc context
        if (len(args) == 0 or
            args[0].type.kind != clang.cindex.TypeKind.POINTER or
            args[0].type.get_pointee().spelling != 'OrthancPluginContext'):
            print('[INFO] Not in the Orthanc SDK: %s()' % node.spelling)
            continue

        countAllFunctions += 1

        contextName = args[0].spelling
        args = args[1:]  # Skip the Orthanc context

        if (len(args) >= 1 and
            args[0].type.spelling in [ 'OrthancPluginMemoryBuffer *',
                                       'OrthancPluginMemoryBuffer64 *' ]):
            # The method/function returns a byte array
            returnBufferType = args[0].type.spelling
            args = args[1:]
        else:
            returnBufferType = None

        if (len(args) >= 1 and
            (IsClassType(args[0].type) or
             IsConstClassType(args[0].type))):

            # This is a class method
            cls = args[0].type.get_pointee().spelling
            if IsConstClassType(args[0].type):
                cls = RemovePrefix('const ', cls)

            # Special case of destructors
            if (len(args) == 1 and
                not args[0].type.get_pointee().is_const_qualified() and
                node.spelling.startswith('OrthancPluginFree')):
                classes[cls]['destructor'] = node.spelling
                countWrappedFunctions += 1

            else:
                if node.raw_comment == None:
                    raise Exception('Method without documentation: %s' % node.spelling)

                doc = ParseFunctionDocumentation(node.raw_comment)
                del doc['args'][contextName]      # Remove OrthancPluginContext from documentation
                del doc['args'][args[0].spelling] # Remove self from documentation

                method = {
                    'c_function' : node.spelling,
                    'const' : args[0].type.get_pointee().is_const_qualified(),
                    'documentation' : doc,
                    }

                if not EncodeArguments(method, args[1:]):
                    pass
                elif EncodeResultType(method, returnBufferType, node.result_type):
                    classes[cls]['methods'].append(method)
                    countWrappedFunctions += 1
                else:
                    print('[WARNING] Unsupported return type in a method (%s), cannot wrap: %s' % (
                        node.result_type.spelling, node.spelling))

        else:
            # This is a global function
            if node.raw_comment == None:
                raise Exception('Global function without documentation: %s' % node.spelling)

            doc = ParseFunctionDocumentation(node.raw_comment)
            del doc['args'][contextName]  # Remove OrthancPluginContext from documentation

            f = {
                'c_function' : node.spelling,
                'documentation' : doc,
            }

            if not EncodeArguments(f, args):
                pass
            elif EncodeResultType(f, returnBufferType, node.result_type):
                globalFunctions.append(f)
                countWrappedFunctions += 1
            else:
                print('[WARNING] Unsupported return type in a global function (%s), cannot wrap: %s' % (
                    node.result_type.spelling, node.spelling))



# Thirdly, export the code model

def FlattenEnumerations():
    result = []
    for (name, content) in enumerations.items():
        result.append({
            'name' : name,
            'values' : content['values'],
            'documentation' : content['documentation'],
            })
    return result

def FlattenDictionary(source):
    result = []
    for (name, value) in source.items():
        result.append(value)
    return result

codeModel = {
    'classes' : sorted(FlattenDictionary(classes), key = lambda x: x['name']),
    'enumerations' : sorted(FlattenEnumerations(), key = lambda x: x['name']),
    'global_functions' : globalFunctions,  # Global functions are ordered in the same order as in the C header
    }


with open(TARGET, 'w') as f:
    f.write(json.dumps(codeModel, sort_keys = True, indent = 4))

print('\nTotal functions in the SDK: %d' % countAllFunctions)
print('Total wrapped functions (including destructors): %d' % countWrappedFunctions)
print('Coverage: %.0f%%' % (float(countWrappedFunctions) /
                            float(countAllFunctions) * 100.0))
