package be.uclouvain.orthanc;

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
 * along with this program. If not, see http://www.gnu.org/licenses/.
 **/


// WARNING: Auto-generated file. Do not modify it by hand.

/**
 * Wrapper around the global functions provided by the Orthanc SDK.
 **/
public class Functions {

    /**
     * Check that the version of the hosting Orthanc is above a given version.
     * 
     * This function checks whether the version of the Orthanc server running this
     * plugin, is above the given version. Contrarily to OrthancPluginCheckVersion(),
     * it is up to the developer of the plugin to make sure that all the Orthanc SDK
     * services called by the plugin are actually implemented in the given version of
     * Orthanc.
     * 
     * @param expectedMajor Expected major version.
     * @param expectedMinor Expected minor version.
     * @param expectedRevision Expected revision.
     * @return 1 if and only if the versions are compatible. If the result is 0, the
     * initialization of the plugin should fail.
     **/
    public static int checkVersionAdvanced(
        int expectedMajor,
        int expectedMinor,
        int expectedRevision) {
        return NativeSDK.OrthancPluginCheckVersionAdvanced(expectedMajor, expectedMinor, expectedRevision);
    }

    /**
     * Check the compatibility of the plugin wrt. the version of its hosting Orthanc.
     * 
     * This function checks whether the version of the Orthanc server running this
     * plugin, is above the version of the current Orthanc SDK header. This guarantees
     * that the plugin is compatible with the hosting Orthanc (i.e. it will not call
     * unavailable services). The result of this function should always be checked in
     * the OrthancPluginInitialize() entry point of the plugin.
     * 
     * @return 1 if and only if the versions are compatible. If the result is 0, the
     * initialization of the plugin should fail.
     **/
    public static int checkVersion() {
        return NativeSDK.OrthancPluginCheckVersion();
    }

    /**
     * Log an error.
     * 
     * Log an error message using the Orthanc logging system.
     * 
     * @param message The message to be logged.
     **/
    public static void logError(
        String message) {
        NativeSDK.OrthancPluginLogError(message);
    }

    /**
     * Log a warning.
     * 
     * Log a warning message using the Orthanc logging system.
     * 
     * @param message The message to be logged.
     **/
    public static void logWarning(
        String message) {
        NativeSDK.OrthancPluginLogWarning(message);
    }

    /**
     * Log an information.
     * 
     * Log an information message using the Orthanc logging system.
     * 
     * @param message The message to be logged.
     **/
    public static void logInfo(
        String message) {
        NativeSDK.OrthancPluginLogInfo(message);
    }

    /**
     * Retrieve a DICOM instance using its Orthanc identifier.
     * 
     * Retrieve a DICOM instance using its Orthanc identifier. The DICOM file is stored
     * into a newly allocated memory buffer.
     * 
     * @param instanceId The Orthanc identifier of the DICOM instance of interest.
     * @return The resulting memory buffer.
     **/
    public static byte[] getDicomForInstance(
        String instanceId) {
        return NativeSDK.OrthancPluginGetDicomForInstance(instanceId);
    }

    /**
     * Make a GET call to the built-in Orthanc REST API.
     * 
     * Make a GET call to the built-in Orthanc REST API. The result to the query is
     * stored into a newly allocated memory buffer.
     * 
     * Remark: If the resource is not existing (error 404), the error code will be
     * OrthancPluginErrorCode_UnknownResource.
     * 
     * @param uri The URI in the built-in Orthanc API.
     * @return The resulting memory buffer.
     **/
    public static byte[] restApiGet(
        String uri) {
        return NativeSDK.OrthancPluginRestApiGet(uri);
    }

    /**
     * Make a GET call to the REST API, as tainted by the plugins.
     * 
     * Make a GET call to the Orthanc REST API, after all the plugins are applied. In
     * other words, if some plugin overrides or adds the called URI to the built-in
     * Orthanc REST API, this call will return the result provided by this plugin. The
     * result to the query is stored into a newly allocated memory buffer.
     * 
     * Remark: If the resource is not existing (error 404), the error code will be
     * OrthancPluginErrorCode_UnknownResource.
     * 
     * @param uri The URI in the built-in Orthanc API.
     * @return The resulting memory buffer.
     **/
    public static byte[] restApiGetAfterPlugins(
        String uri) {
        return NativeSDK.OrthancPluginRestApiGetAfterPlugins(uri);
    }

    /**
     * Make a POST call to the built-in Orthanc REST API.
     * 
     * Make a POST call to the built-in Orthanc REST API. The result to the query is
     * stored into a newly allocated memory buffer.
     * 
     * Remark: If the resource is not existing (error 404), the error code will be
     * OrthancPluginErrorCode_UnknownResource.
     * 
     * @param uri The URI in the built-in Orthanc API.
     * @param body The body of the POST request.
     * @return The resulting memory buffer.
     **/
    public static byte[] restApiPost(
        String uri,
        byte[] body) {
        return NativeSDK.OrthancPluginRestApiPost(uri, body);
    }

    /**
     * Make a POST call to the REST API, as tainted by the plugins.
     * 
     * Make a POST call to the Orthanc REST API, after all the plugins are applied. In
     * other words, if some plugin overrides or adds the called URI to the built-in
     * Orthanc REST API, this call will return the result provided by this plugin. The
     * result to the query is stored into a newly allocated memory buffer.
     * 
     * Remark: If the resource is not existing (error 404), the error code will be
     * OrthancPluginErrorCode_UnknownResource.
     * 
     * @param uri The URI in the built-in Orthanc API.
     * @param body The body of the POST request.
     * @return The resulting memory buffer.
     **/
    public static byte[] restApiPostAfterPlugins(
        String uri,
        byte[] body) {
        return NativeSDK.OrthancPluginRestApiPostAfterPlugins(uri, body);
    }

    /**
     * Make a DELETE call to the built-in Orthanc REST API.
     * 
     * Make a DELETE call to the built-in Orthanc REST API.
     * 
     * Remark: If the resource is not existing (error 404), the error code will be
     * OrthancPluginErrorCode_UnknownResource.
     * 
     * @param uri The URI to delete in the built-in Orthanc API.
     **/
    public static void restApiDelete(
        String uri) {
        NativeSDK.OrthancPluginRestApiDelete(uri);
    }

    /**
     * Make a DELETE call to the REST API, as tainted by the plugins.
     * 
     * Make a DELETE call to the Orthanc REST API, after all the plugins are applied.
     * In other words, if some plugin overrides or adds the called URI to the built-in
     * Orthanc REST API, this call will return the result provided by this plugin.
     * 
     * Remark: If the resource is not existing (error 404), the error code will be
     * OrthancPluginErrorCode_UnknownResource.
     * 
     * @param uri The URI to delete in the built-in Orthanc API.
     **/
    public static void restApiDeleteAfterPlugins(
        String uri) {
        NativeSDK.OrthancPluginRestApiDeleteAfterPlugins(uri);
    }

    /**
     * Make a PUT call to the built-in Orthanc REST API.
     * 
     * Make a PUT call to the built-in Orthanc REST API. The result to the query is
     * stored into a newly allocated memory buffer.
     * 
     * Remark: If the resource is not existing (error 404), the error code will be
     * OrthancPluginErrorCode_UnknownResource.
     * 
     * @param uri The URI in the built-in Orthanc API.
     * @param body The body of the PUT request.
     * @return The resulting memory buffer.
     **/
    public static byte[] restApiPut(
        String uri,
        byte[] body) {
        return NativeSDK.OrthancPluginRestApiPut(uri, body);
    }

    /**
     * Make a PUT call to the REST API, as tainted by the plugins.
     * 
     * Make a PUT call to the Orthanc REST API, after all the plugins are applied. In
     * other words, if some plugin overrides or adds the called URI to the built-in
     * Orthanc REST API, this call will return the result provided by this plugin. The
     * result to the query is stored into a newly allocated memory buffer.
     * 
     * Remark: If the resource is not existing (error 404), the error code will be
     * OrthancPluginErrorCode_UnknownResource.
     * 
     * @param uri The URI in the built-in Orthanc API.
     * @param body The body of the PUT request.
     * @return The resulting memory buffer.
     **/
    public static byte[] restApiPutAfterPlugins(
        String uri,
        byte[] body) {
        return NativeSDK.OrthancPluginRestApiPutAfterPlugins(uri, body);
    }

    /**
     * Look for a patient.
     * 
     * Look for a patient stored in Orthanc, using its Patient ID tag (0x0010, 0x0020).
     * This function uses the database index to run as fast as possible (it does not
     * loop over all the stored patients).
     * 
     * @param patientID The Patient ID of interest.
     * @return The resulting string.
     **/
    public static String lookupPatient(
        String patientID) {
        return NativeSDK.OrthancPluginLookupPatient(patientID);
    }

    /**
     * Look for a study.
     * 
     * Look for a study stored in Orthanc, using its Study Instance UID tag (0x0020,
     * 0x000d). This function uses the database index to run as fast as possible (it
     * does not loop over all the stored studies).
     * 
     * @param studyUID The Study Instance UID of interest.
     * @return The resulting string.
     **/
    public static String lookupStudy(
        String studyUID) {
        return NativeSDK.OrthancPluginLookupStudy(studyUID);
    }

    /**
     * Look for a study, using the accession number.
     * 
     * Look for a study stored in Orthanc, using its Accession Number tag (0x0008,
     * 0x0050). This function uses the database index to run as fast as possible (it
     * does not loop over all the stored studies).
     * 
     * @param accessionNumber The Accession Number of interest.
     * @return The resulting string.
     **/
    public static String lookupStudyWithAccessionNumber(
        String accessionNumber) {
        return NativeSDK.OrthancPluginLookupStudyWithAccessionNumber(accessionNumber);
    }

    /**
     * Look for a series.
     * 
     * Look for a series stored in Orthanc, using its Series Instance UID tag (0x0020,
     * 0x000e). This function uses the database index to run as fast as possible (it
     * does not loop over all the stored series).
     * 
     * @param seriesUID The Series Instance UID of interest.
     * @return The resulting string.
     **/
    public static String lookupSeries(
        String seriesUID) {
        return NativeSDK.OrthancPluginLookupSeries(seriesUID);
    }

    /**
     * Look for an instance.
     * 
     * Look for an instance stored in Orthanc, using its SOP Instance UID tag (0x0008,
     * 0x0018). This function uses the database index to run as fast as possible (it
     * does not loop over all the stored instances).
     * 
     * @param sopInstanceUID The SOP Instance UID of interest.
     * @return The resulting string.
     **/
    public static String lookupInstance(
        String sopInstanceUID) {
        return NativeSDK.OrthancPluginLookupInstance(sopInstanceUID);
    }

    /**
     * Return the path to the Orthanc executable.
     * 
     * This function returns the path to the Orthanc executable.
     * 
     * @return The resulting string.
     **/
    public static String getOrthancPath() {
        return NativeSDK.OrthancPluginGetOrthancPath();
    }

    /**
     * Return the directory containing the Orthanc.
     * 
     * This function returns the path to the directory containing the Orthanc
     * executable.
     * 
     * @return The resulting string.
     **/
    public static String getOrthancDirectory() {
        return NativeSDK.OrthancPluginGetOrthancDirectory();
    }

    /**
     * Return the path to the configuration file(s).
     * 
     * This function returns the path to the configuration file(s) that was specified
     * when starting Orthanc. Since version 0.9.1, this path can refer to a folder that
     * stores a set of configuration files. This function is deprecated in favor of
     * OrthancPluginGetConfiguration().
     * 
     * @return The resulting string.
     **/
    public static String getConfigurationPath() {
        return NativeSDK.OrthancPluginGetConfigurationPath();
    }

    /**
     * Set the URI where the plugin provides its Web interface.
     * 
     * For plugins that come with a Web interface, this function declares the entry
     * path where to find this interface. This information is notably used in the
     * "Plugins" page of Orthanc Explorer.
     * 
     * @param uri The root URI for this plugin.
     **/
    public static void setRootUri(
        String uri) {
        NativeSDK.OrthancPluginSetRootUri(uri);
    }

    /**
     * Set a description for this plugin.
     * 
     * Set a description for this plugin. It is displayed in the "Plugins" page of
     * Orthanc Explorer.
     * 
     * @param description The description.
     **/
    public static void setDescription(
        String description) {
        NativeSDK.OrthancPluginSetDescription(description);
    }

    /**
     * Extend the JavaScript code of Orthanc Explorer.
     * 
     * Add JavaScript code to customize the default behavior of Orthanc Explorer. This
     * can for instance be used to add new buttons.
     * 
     * @param javascript The custom JavaScript code.
     **/
    public static void extendOrthancExplorer(
        String javascript) {
        NativeSDK.OrthancPluginExtendOrthancExplorer(javascript);
    }

    /**
     * Get the value of a global property.
     * 
     * Get the value of a global property that is stored in the Orthanc database.
     * Global properties whose index is below 1024 are reserved by Orthanc.
     * 
     * @param property The global property of interest.
     * @param defaultValue The value to return, if the global property is unset.
     * @return The resulting string.
     **/
    public static String getGlobalProperty(
        int property,
        String defaultValue) {
        return NativeSDK.OrthancPluginGetGlobalProperty(property, defaultValue);
    }

    /**
     * Set the value of a global property.
     * 
     * Set the value of a global property into the Orthanc database. Setting a global
     * property can be used by plugins to save their internal parameters. Plugins are
     * only allowed to set properties whose index are above or equal to 1024
     * (properties below 1024 are read-only and reserved by Orthanc).
     * 
     * @param property The global property of interest.
     * @param value The value to be set in the global property.
     **/
    public static void setGlobalProperty(
        int property,
        String value) {
        NativeSDK.OrthancPluginSetGlobalProperty(property, value);
    }

    /**
     * Get the number of command-line arguments.
     * 
     * Retrieve the number of command-line arguments that were used to launch Orthanc.
     * 
     * @return The number of arguments.
     **/
    public static int getCommandLineArgumentsCount() {
        return NativeSDK.OrthancPluginGetCommandLineArgumentsCount();
    }

    /**
     * Get the value of a command-line argument.
     * 
     * Get the value of one of the command-line arguments that were used to launch
     * Orthanc. The number of available arguments can be retrieved by
     * OrthancPluginGetCommandLineArgumentsCount().
     * 
     * @param argument The index of the argument.
     * @return The resulting string.
     **/
    public static String getCommandLineArgument(
        int argument) {
        return NativeSDK.OrthancPluginGetCommandLineArgument(argument);
    }

    /**
     * Get the expected version of the database schema.
     * 
     * Retrieve the expected version of the database schema.
     * 
     * @return The version.
     **/
    public static int getExpectedDatabaseVersion() {
        return NativeSDK.OrthancPluginGetExpectedDatabaseVersion();
    }

    /**
     * Return the content of the configuration file(s).
     * 
     * This function returns the content of the configuration that is used by Orthanc,
     * formatted as a JSON string.
     * 
     * @return The resulting string.
     **/
    public static String getConfiguration() {
        return NativeSDK.OrthancPluginGetConfiguration();
    }

    /**
     * Compress or decompress a buffer.
     * 
     * This function compresses or decompresses a buffer, using the version of the zlib
     * library that is used by the Orthanc core.
     * 
     * @param source The source buffer.
     * @param compression The compression algorithm.
     * @param uncompress If set to "0", the buffer must be compressed. If set to "1",
     * the buffer must be uncompressed.
     * @return The resulting memory buffer.
     **/
    public static byte[] bufferCompression(
        byte[] source,
        CompressionType compression,
        byte uncompress) {
        return NativeSDK.OrthancPluginBufferCompression(source, compression.getValue(), uncompress);
    }

    /**
     * Read a file.
     * 
     * Read the content of a file on the filesystem, and returns it into a newly
     * allocated memory buffer.
     * 
     * @param path The path of the file to be read.
     * @return The resulting memory buffer.
     **/
    public static byte[] readFile(
        String path) {
        return NativeSDK.OrthancPluginReadFile(path);
    }

    /**
     * Write a file.
     * 
     * Write the content of a memory buffer to the filesystem.
     * 
     * @param path The path of the file to be written.
     * @param data The content of the memory buffer.
     **/
    public static void writeFile(
        String path,
        byte[] data) {
        NativeSDK.OrthancPluginWriteFile(path, data);
    }

    /**
     * Get the description of a given error code.
     * 
     * This function returns the description of a given error code.
     * 
     * @param error The error code of interest.
     * @return The resulting string.
     **/
    public static String getErrorDescription(
        ErrorCode error) {
        return NativeSDK.OrthancPluginGetErrorDescription(error.getValue());
    }

    /**
     * Encode a PNG image.
     * 
     * This function compresses the given memory buffer containing an image using the
     * PNG specification, and stores the result of the compression into a newly
     * allocated memory buffer.
     * 
     * @param format The memory layout of the uncompressed image.
     * @param width The width of the image.
     * @param height The height of the image.
     * @param pitch The pitch of the image (i.e. the number of bytes between 2
     * successive lines of the image in the memory buffer).
     * @param buffer The memory buffer containing the uncompressed image.
     * @return The resulting memory buffer.
     **/
    public static byte[] compressPngImage(
        PixelFormat format,
        int width,
        int height,
        int pitch,
        byte[] buffer) {
        return NativeSDK.OrthancPluginCompressPngImage(format.getValue(), width, height, pitch, buffer);
    }

    /**
     * Encode a JPEG image.
     * 
     * This function compresses the given memory buffer containing an image using the
     * JPEG specification, and stores the result of the compression into a newly
     * allocated memory buffer.
     * 
     * @param format The memory layout of the uncompressed image.
     * @param width The width of the image.
     * @param height The height of the image.
     * @param pitch The pitch of the image (i.e. the number of bytes between 2
     * successive lines of the image in the memory buffer).
     * @param buffer The memory buffer containing the uncompressed image.
     * @param quality The quality of the JPEG encoding, between 1 (worst quality, best
     * compression) and 100 (best quality, worst compression).
     * @return The resulting memory buffer.
     **/
    public static byte[] compressJpegImage(
        PixelFormat format,
        int width,
        int height,
        int pitch,
        byte[] buffer,
        byte quality) {
        return NativeSDK.OrthancPluginCompressJpegImage(format.getValue(), width, height, pitch, buffer, quality);
    }

    /**
     * Issue a HTTP GET call.
     * 
     * Make a HTTP GET call to the given URL. The result to the query is stored into a
     * newly allocated memory buffer. Favor OrthancPluginRestApiGet() if calling the
     * built-in REST API of the Orthanc instance that hosts this plugin.
     * 
     * @param url The URL of interest.
     * @param username The username (can be "NULL" if no password protection).
     * @param password The password (can be "NULL" if no password protection).
     * @return The resulting memory buffer.
     **/
    public static byte[] httpGet(
        String url,
        String username,
        String password) {
        return NativeSDK.OrthancPluginHttpGet(url, username, password);
    }

    /**
     * Issue a HTTP POST call.
     * 
     * Make a HTTP POST call to the given URL. The result to the query is stored into a
     * newly allocated memory buffer. Favor OrthancPluginRestApiPost() if calling the
     * built-in REST API of the Orthanc instance that hosts this plugin.
     * 
     * @param url The URL of interest.
     * @param body The content of the body of the request.
     * @param username The username (can be "NULL" if no password protection).
     * @param password The password (can be "NULL" if no password protection).
     * @return The resulting memory buffer.
     **/
    public static byte[] httpPost(
        String url,
        byte[] body,
        String username,
        String password) {
        return NativeSDK.OrthancPluginHttpPost(url, body, username, password);
    }

    /**
     * Issue a HTTP PUT call.
     * 
     * Make a HTTP PUT call to the given URL. The result to the query is stored into a
     * newly allocated memory buffer. Favor OrthancPluginRestApiPut() if calling the
     * built-in REST API of the Orthanc instance that hosts this plugin.
     * 
     * @param url The URL of interest.
     * @param body The content of the body of the request.
     * @param username The username (can be "NULL" if no password protection).
     * @param password The password (can be "NULL" if no password protection).
     * @return The resulting memory buffer.
     **/
    public static byte[] httpPut(
        String url,
        byte[] body,
        String username,
        String password) {
        return NativeSDK.OrthancPluginHttpPut(url, body, username, password);
    }

    /**
     * Issue a HTTP DELETE call.
     * 
     * Make a HTTP DELETE call to the given URL. Favor OrthancPluginRestApiDelete() if
     * calling the built-in REST API of the Orthanc instance that hosts this plugin.
     * 
     * @param url The URL of interest.
     * @param username The username (can be "NULL" if no password protection).
     * @param password The password (can be "NULL" if no password protection).
     **/
    public static void httpDelete(
        String url,
        String username,
        String password) {
        NativeSDK.OrthancPluginHttpDelete(url, username, password);
    }

    /**
     * Return the number of available fonts.
     * 
     * This function returns the number of fonts that are built in the Orthanc core.
     * These fonts can be used to draw texts on images through OrthancPluginDrawText().
     * 
     * @return The number of fonts.
     **/
    public static int getFontsCount() {
        return NativeSDK.OrthancPluginGetFontsCount();
    }

    /**
     * Return the name of a font.
     * 
     * This function returns the name of a font that is built in the Orthanc core.
     * 
     * @param fontIndex The index of the font. This value must be less than
     * OrthancPluginGetFontsCount().
     * @return The resulting string.
     **/
    public static String getFontName(
        int fontIndex) {
        return NativeSDK.OrthancPluginGetFontName(fontIndex);
    }

    /**
     * Return the size of a font.
     * 
     * This function returns the size of a font that is built in the Orthanc core.
     * 
     * @param fontIndex The index of the font. This value must be less than
     * OrthancPluginGetFontsCount().
     * @return The font size.
     **/
    public static int getFontSize(
        int fontIndex) {
        return NativeSDK.OrthancPluginGetFontSize(fontIndex);
    }

    /**
     * Declare a custom error code for this plugin.
     * 
     * This function declares a custom error code that can be generated by this plugin.
     * This declaration is used to enrich the body of the HTTP answer in the case of an
     * error, and to set the proper HTTP status code.
     * 
     * @param code The error code that is internal to this plugin.
     * @param httpStatus The HTTP status corresponding to this error.
     * @param message The description of the error.
     **/
    public static void registerErrorCode(
        int code,
        short httpStatus,
        String message) {
        NativeSDK.OrthancPluginRegisterErrorCode(code, httpStatus, message);
    }

    /**
     * Register a new tag into the DICOM dictionary.
     * 
     * This function declares a new public tag in the dictionary of DICOM tags that are
     * known to Orthanc. This function should be used in the OrthancPluginInitialize()
     * callback.
     * 
     * @param group The group of the tag.
     * @param element The element of the tag.
     * @param vr The value representation of the tag.
     * @param name The nickname of the tag.
     * @param minMultiplicity The minimum multiplicity of the tag (must be above 0).
     * @param maxMultiplicity The maximum multiplicity of the tag. A value of 0 means
     * an arbitrary multiplicity (""n"").
     **/
    public static void registerDictionaryTag(
        short group,
        short element,
        ValueRepresentation vr,
        String name,
        int minMultiplicity,
        int maxMultiplicity) {
        NativeSDK.OrthancPluginRegisterDictionaryTag(group, element, vr.getValue(), name, minMultiplicity, maxMultiplicity);
    }

    /**
     * Register a new private tag into the DICOM dictionary.
     * 
     * This function declares a new private tag in the dictionary of DICOM tags that
     * are known to Orthanc. This function should be used in the
     * OrthancPluginInitialize() callback.
     * 
     * @param group The group of the tag.
     * @param element The element of the tag.
     * @param vr The value representation of the tag.
     * @param name The nickname of the tag.
     * @param minMultiplicity The minimum multiplicity of the tag (must be above 0).
     * @param maxMultiplicity The maximum multiplicity of the tag. A value of 0 means
     * an arbitrary multiplicity (""n"").
     * @param privateCreator The private creator of this private tag.
     **/
    public static void registerPrivateDictionaryTag(
        short group,
        short element,
        ValueRepresentation vr,
        String name,
        int minMultiplicity,
        int maxMultiplicity,
        String privateCreator) {
        NativeSDK.OrthancPluginRegisterPrivateDictionaryTag(group, element, vr.getValue(), name, minMultiplicity, maxMultiplicity, privateCreator);
    }

    /**
     * Format a DICOM memory buffer as a JSON string.
     * 
     * This function takes as input a memory buffer containing a DICOM file, and
     * outputs a JSON string representing the tags of this DICOM file.
     * 
     * @param buffer The memory buffer containing the DICOM file.
     * @param format The output format.
     * @param flags Flags governing the output.
     * @param maxStringLength The maximum length of a field. Too long fields will be
     * output as "null". The 0 value means no maximum length.
     * @return The resulting string.
     **/
    public static String dicomBufferToJson(
        byte[] buffer,
        DicomToJsonFormat format,
        DicomToJsonFlags flags,
        int maxStringLength) {
        return NativeSDK.OrthancPluginDicomBufferToJson(buffer, format.getValue(), flags.getValue(), maxStringLength);
    }

    /**
     * Format a DICOM instance as a JSON string.
     * 
     * This function formats a DICOM instance that is stored in Orthanc, and outputs a
     * JSON string representing the tags of this DICOM instance.
     * 
     * @param instanceId The Orthanc identifier of the instance.
     * @param format The output format.
     * @param flags Flags governing the output.
     * @param maxStringLength The maximum length of a field. Too long fields will be
     * output as "null". The 0 value means no maximum length.
     * @return The resulting string.
     **/
    public static String dicomInstanceToJson(
        String instanceId,
        DicomToJsonFormat format,
        DicomToJsonFlags flags,
        int maxStringLength) {
        return NativeSDK.OrthancPluginDicomInstanceToJson(instanceId, format.getValue(), flags.getValue(), maxStringLength);
    }

    /**
     * Create a DICOM instance from a JSON string and an image.
     * 
     * This function takes as input a string containing a JSON file describing the
     * content of a DICOM instance. As an output, it writes the corresponding DICOM
     * instance to a newly allocated memory buffer. Additionally, an image to be
     * encoded within the DICOM instance can also be provided.
     * 
     * Private tags will be associated with the private creator whose value is
     * specified in the "DefaultPrivateCreator" configuration option of Orthanc. The
     * function OrthancPluginCreateDicom2() can be used if another private creator must
     * be used to create this instance.
     * 
     * @param json The input JSON file.
     * @param pixelData The image. Can be NULL, if the pixel data is encoded inside the
     * JSON with the data URI scheme.
     * @param flags Flags governing the output.
     * @return The resulting memory buffer.
     **/
    public static byte[] createDicom(
        String json,
        Image pixelData,
        CreateDicomFlags flags) {
        return NativeSDK.OrthancPluginCreateDicom(json, pixelData.getSelf(), flags.getValue());
    }

    /**
     * Compute an MD5 hash.
     * 
     * This functions computes the MD5 cryptographic hash of the given memory buffer.
     * 
     * @param buffer The source memory buffer.
     * @return The resulting string.
     **/
    public static String computeMd5(
        byte[] buffer) {
        return NativeSDK.OrthancPluginComputeMd5(buffer);
    }

    /**
     * Compute a SHA-1 hash.
     * 
     * This functions computes the SHA-1 cryptographic hash of the given memory buffer.
     * 
     * @param buffer The source memory buffer.
     * @return The resulting string.
     **/
    public static String computeSha1(
        byte[] buffer) {
        return NativeSDK.OrthancPluginComputeSha1(buffer);
    }

    /**
     * Generate an UUID.
     * 
     * Generate a random GUID/UUID (globally unique identifier).
     * 
     * @return The resulting string.
     **/
    public static String generateUuid() {
        return NativeSDK.OrthancPluginGenerateUuid();
    }

    /**
     * Detect the MIME type of a file.
     * 
     * This function returns the MIME type of a file by inspecting its extension.
     * 
     * @param path Path to the file.
     * @return The resulting string.
     **/
    public static String autodetectMimeType(
        String path) {
        return NativeSDK.OrthancPluginAutodetectMimeType(path);
    }

    /**
     * Set the value of a metrics.
     * 
     * This function sets the value of a metrics to monitor the behavior of the plugin
     * through tools such as Prometheus. The values of all the metrics are stored
     * within the Orthanc context.
     * 
     * @param name The name of the metrics to be set.
     * @param value The value of the metrics.
     * @param type The type of the metrics. This parameter is only taken into
     * consideration the first time this metrics is set.
     **/
    public static void setMetricsValue(
        String name,
        float value,
        MetricsType type) {
        NativeSDK.OrthancPluginSetMetricsValue(name, value, type.getValue());
    }

    /**
     * Returns the symbolic name of a DICOM tag.
     * 
     * This function makes a lookup to the dictionary of DICOM tags that are known to
     * Orthanc, and returns the symbolic name of a DICOM tag.
     * 
     * @param group The group of the tag.
     * @param element The element of the tag.
     * @param privateCreator For private tags, the name of the private creator (can be
     * NULL).
     * @return The resulting string.
     **/
    public static String getTagName(
        short group,
        short element,
        String privateCreator) {
        return NativeSDK.OrthancPluginGetTagName(group, element, privateCreator);
    }

    /**
     * Generate a token to grant full access to the REST API of Orthanc
     * 
     * This function generates a token that can be set in the HTTP header
     * "Authorization" so as to grant full access to the REST API of Orthanc using an
     * external HTTP client. Using this function avoids the need of adding a separate
     * user in the "RegisteredUsers" configuration of Orthanc, which eases deployments.
     * 
     * This feature is notably useful in multiprocess scenarios, where a subprocess
     * created by a plugin has no access to the "OrthancPluginContext", and thus cannot
     * call "OrthancPluginRestApi[Get|Post|Put|Delete]()".
     * 
     * This situation is frequently encountered in Python plugins, where the
     * "multiprocessing" package can be used to bypass the Global Interpreter Lock
     * (GIL) and thus to improve performance and concurrency.
     * 
     * @return The resulting string.
     **/
    public static String generateRestApiAuthorizationToken() {
        return NativeSDK.OrthancPluginGenerateRestApiAuthorizationToken();
    }

    /**
     * Create a DICOM instance from a JSON string and an image, with a private creator.
     * 
     * This function takes as input a string containing a JSON file describing the
     * content of a DICOM instance. As an output, it writes the corresponding DICOM
     * instance to a newly allocated memory buffer. Additionally, an image to be
     * encoded within the DICOM instance can also be provided.
     * 
     * Contrarily to the function OrthancPluginCreateDicom(), this function can be
     * explicitly provided with a private creator.
     * 
     * @param json The input JSON file.
     * @param pixelData The image. Can be NULL, if the pixel data is encoded inside the
     * JSON with the data URI scheme.
     * @param flags Flags governing the output.
     * @param privateCreator The private creator to be used for the private DICOM tags.
     * Check out the global configuration option "Dictionary" of Orthanc.
     * @return The resulting memory buffer.
     **/
    public static byte[] createDicom2(
        String json,
        Image pixelData,
        CreateDicomFlags flags,
        String privateCreator) {
        return NativeSDK.OrthancPluginCreateDicom2(json, pixelData.getSelf(), flags.getValue(), privateCreator);
    }

}
