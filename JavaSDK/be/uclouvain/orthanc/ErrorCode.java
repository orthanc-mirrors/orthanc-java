package be.uclouvain.orthanc;

/**
 * SPDX-FileCopyrightText: 2023 Sebastien Jodogne, UCLouvain, Belgium
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

/**
 * Java plugin for Orthanc
 * Copyright (C) 2023 Sebastien Jodogne, UCLouvain, Belgium
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


/**
 * The various error codes that can be returned by the Orthanc core.
 **/
public enum ErrorCode {
    /**
     * Internal error
     **/
    INTERNAL_ERROR(-1),
    /**
     * Success
     **/
    SUCCESS(0),
    /**
     * Error encountered within the plugin engine
     **/
    PLUGIN(1),
    /**
     * Not implemented yet
     **/
    NOT_IMPLEMENTED(2),
    /**
     * Parameter out of range
     **/
    PARAMETER_OUT_OF_RANGE(3),
    /**
     * The server hosting Orthanc is running out of memory
     **/
    NOT_ENOUGH_MEMORY(4),
    /**
     * Bad type for a parameter
     **/
    BAD_PARAMETER_TYPE(5),
    /**
     * Bad sequence of calls
     **/
    BAD_SEQUENCE_OF_CALLS(6),
    /**
     * Accessing an inexistent item
     **/
    INEXISTENT_ITEM(7),
    /**
     * Bad request
     **/
    BAD_REQUEST(8),
    /**
     * Error in the network protocol
     **/
    NETWORK_PROTOCOL(9),
    /**
     * Error while calling a system command
     **/
    SYSTEM_COMMAND(10),
    /**
     * Error with the database engine
     **/
    DATABASE(11),
    /**
     * Badly formatted URI
     **/
    URI_SYNTAX(12),
    /**
     * Inexistent file
     **/
    INEXISTENT_FILE(13),
    /**
     * Cannot write to file
     **/
    CANNOT_WRITE_FILE(14),
    /**
     * Bad file format
     **/
    BAD_FILE_FORMAT(15),
    /**
     * Timeout
     **/
    TIMEOUT(16),
    /**
     * Unknown resource
     **/
    UNKNOWN_RESOURCE(17),
    /**
     * Incompatible version of the database
     **/
    INCOMPATIBLE_DATABASE_VERSION(18),
    /**
     * The file storage is full
     **/
    FULL_STORAGE(19),
    /**
     * Corrupted file (e.g. inconsistent MD5 hash)
     **/
    CORRUPTED_FILE(20),
    /**
     * Inexistent tag
     **/
    INEXISTENT_TAG(21),
    /**
     * Cannot modify a read-only data structure
     **/
    READ_ONLY(22),
    /**
     * Incompatible format of the images
     **/
    INCOMPATIBLE_IMAGE_FORMAT(23),
    /**
     * Incompatible size of the images
     **/
    INCOMPATIBLE_IMAGE_SIZE(24),
    /**
     * Error while using a shared library (plugin)
     **/
    SHARED_LIBRARY(25),
    /**
     * Plugin invoking an unknown service
     **/
    UNKNOWN_PLUGIN_SERVICE(26),
    /**
     * Unknown DICOM tag
     **/
    UNKNOWN_DICOM_TAG(27),
    /**
     * Cannot parse a JSON document
     **/
    BAD_JSON(28),
    /**
     * Bad credentials were provided to an HTTP request
     **/
    UNAUTHORIZED(29),
    /**
     * Badly formatted font file
     **/
    BAD_FONT(30),
    /**
     * The plugin implementing a custom database back-end does not fulfill the proper
     * interface
     **/
    DATABASE_PLUGIN(31),
    /**
     * Error in the plugin implementing a custom storage area
     **/
    STORAGE_AREA_PLUGIN(32),
    /**
     * The request is empty
     **/
    EMPTY_REQUEST(33),
    /**
     * Cannot send a response which is acceptable according to the Accept HTTP header
     **/
    NOT_ACCEPTABLE(34),
    /**
     * Cannot handle a NULL pointer
     **/
    NULL_POINTER(35),
    /**
     * The database is currently not available (probably a transient situation)
     **/
    DATABASE_UNAVAILABLE(36),
    /**
     * This job was canceled
     **/
    CANCELED_JOB(37),
    /**
     * Geometry error encountered in Stone
     **/
    BAD_GEOMETRY(38),
    /**
     * Cannot initialize SSL encryption, check out your certificates
     **/
    SSL_INITIALIZATION(39),
    /**
     * Calling a function that has been removed from the Orthanc Framework
     **/
    DISCONTINUED_ABI(40),
    /**
     * Incorrect range request
     **/
    BAD_RANGE(41),
    /**
     * Database could not serialize access due to concurrent update, the transaction
     * should be retried
     **/
    DATABASE_CANNOT_SERIALIZE(42),
    /**
     * A bad revision number was provided, which might indicate conflict between
     * multiple writers
     **/
    REVISION(43),
    /**
     * SQLite: The database is not opened
     **/
    SQLITE_NOT_OPENED(1000),
    /**
     * SQLite: Connection is already open
     **/
    SQLITE_ALREADY_OPENED(1001),
    /**
     * SQLite: Unable to open the database
     **/
    SQLITE_CANNOT_OPEN(1002),
    /**
     * SQLite: This cached statement is already being referred to
     **/
    SQLITE_STATEMENT_ALREADY_USED(1003),
    /**
     * SQLite: Cannot execute a command
     **/
    SQLITE_EXECUTE(1004),
    /**
     * SQLite: Rolling back a nonexistent transaction (have you called Begin()?)
     **/
    SQLITE_ROLLBACK_WITHOUT_TRANSACTION(1005),
    /**
     * SQLite: Committing a nonexistent transaction
     **/
    SQLITE_COMMIT_WITHOUT_TRANSACTION(1006),
    /**
     * SQLite: Unable to register a function
     **/
    SQLITE_REGISTER_FUNCTION(1007),
    /**
     * SQLite: Unable to flush the database
     **/
    SQLITE_FLUSH(1008),
    /**
     * SQLite: Cannot run a cached statement
     **/
    SQLITE_CANNOT_RUN(1009),
    /**
     * SQLite: Cannot step over a cached statement
     **/
    SQLITE_CANNOT_STEP(1010),
    /**
     * SQLite: Bing a value while out of range (serious error)
     **/
    SQLITE_BIND_OUT_OF_RANGE(1011),
    /**
     * SQLite: Cannot prepare a cached statement
     **/
    SQLITE_PREPARE_STATEMENT(1012),
    /**
     * SQLite: Beginning the same transaction twice
     **/
    SQLITE_TRANSACTION_ALREADY_STARTED(1013),
    /**
     * SQLite: Failure when committing the transaction
     **/
    SQLITE_TRANSACTION_COMMIT(1014),
    /**
     * SQLite: Cannot start a transaction
     **/
    SQLITE_TRANSACTION_BEGIN(1015),
    /**
     * The directory to be created is already occupied by a regular file
     **/
    DIRECTORY_OVER_FILE(2000),
    /**
     * Unable to create a subdirectory or a file in the file storage
     **/
    FILE_STORAGE_CANNOT_WRITE(2001),
    /**
     * The specified path does not point to a directory
     **/
    DIRECTORY_EXPECTED(2002),
    /**
     * The TCP port of the HTTP server is privileged or already in use
     **/
    HTTP_PORT_IN_USE(2003),
    /**
     * The TCP port of the DICOM server is privileged or already in use
     **/
    DICOM_PORT_IN_USE(2004),
    /**
     * This HTTP status is not allowed in a REST API
     **/
    BAD_HTTP_STATUS_IN_REST(2005),
    /**
     * The specified path does not point to a regular file
     **/
    REGULAR_FILE_EXPECTED(2006),
    /**
     * Unable to get the path to the executable
     **/
    PATH_TO_EXECUTABLE(2007),
    /**
     * Cannot create a directory
     **/
    MAKE_DIRECTORY(2008),
    /**
     * An application entity title (AET) cannot be empty or be longer than 16
     * characters
     **/
    BAD_APPLICATION_ENTITY_TITLE(2009),
    /**
     * No request handler factory for DICOM C-FIND SCP
     **/
    NO_CFIND_HANDLER(2010),
    /**
     * No request handler factory for DICOM C-MOVE SCP
     **/
    NO_CMOVE_HANDLER(2011),
    /**
     * No request handler factory for DICOM C-STORE SCP
     **/
    NO_CSTORE_HANDLER(2012),
    /**
     * No application entity filter
     **/
    NO_APPLICATION_ENTITY_FILTER(2013),
    /**
     * DicomUserConnection: Unable to find the SOP class and instance
     **/
    NO_SOP_CLASS_OR_INSTANCE(2014),
    /**
     * DicomUserConnection: No acceptable presentation context for modality
     **/
    NO_PRESENTATION_CONTEXT(2015),
    /**
     * DicomUserConnection: The C-FIND command is not supported by the remote SCP
     **/
    DICOM_FIND_UNAVAILABLE(2016),
    /**
     * DicomUserConnection: The C-MOVE command is not supported by the remote SCP
     **/
    DICOM_MOVE_UNAVAILABLE(2017),
    /**
     * Cannot store an instance
     **/
    CANNOT_STORE_INSTANCE(2018),
    /**
     * Only string values are supported when creating DICOM instances
     **/
    CREATE_DICOM_NOT_STRING(2019),
    /**
     * Trying to override a value inherited from a parent module
     **/
    CREATE_DICOM_OVERRIDE_TAG(2020),
    /**
     * Use \"Content\" to inject an image into a new DICOM instance
     **/
    CREATE_DICOM_USE_CONTENT(2021),
    /**
     * No payload is present for one instance in the series
     **/
    CREATE_DICOM_NO_PAYLOAD(2022),
    /**
     * The payload of the DICOM instance must be specified according to Data URI scheme
     **/
    CREATE_DICOM_USE_DATA_URI_SCHEME(2023),
    /**
     * Trying to attach a new DICOM instance to an inexistent resource
     **/
    CREATE_DICOM_BAD_PARENT(2024),
    /**
     * Trying to attach a new DICOM instance to an instance (must be a series, study or
     * patient)
     **/
    CREATE_DICOM_PARENT_IS_INSTANCE(2025),
    /**
     * Unable to get the encoding of the parent resource
     **/
    CREATE_DICOM_PARENT_ENCODING(2026),
    /**
     * Unknown modality
     **/
    UNKNOWN_MODALITY(2027),
    /**
     * Bad ordering of filters in a job
     **/
    BAD_JOB_ORDERING(2028),
    /**
     * Cannot convert the given JSON object to a Lua table
     **/
    JSON_TO_LUA_TABLE(2029),
    /**
     * Cannot create the Lua context
     **/
    CANNOT_CREATE_LUA(2030),
    /**
     * Cannot execute a Lua command
     **/
    CANNOT_EXECUTE_LUA(2031),
    /**
     * Arguments cannot be pushed after the Lua function is executed
     **/
    LUA_ALREADY_EXECUTED(2032),
    /**
     * The Lua function does not give the expected number of outputs
     **/
    LUA_BAD_OUTPUT(2033),
    /**
     * The Lua function is not a predicate (only true/false outputs allowed)
     **/
    NOT_LUA_PREDICATE(2034),
    /**
     * The Lua function does not return a string
     **/
    LUA_RETURNS_NO_STRING(2035),
    /**
     * Another plugin has already registered a custom storage area
     **/
    STORAGE_AREA_ALREADY_REGISTERED(2036),
    /**
     * Another plugin has already registered a custom database back-end
     **/
    DATABASE_BACKEND_ALREADY_REGISTERED(2037),
    /**
     * Plugin trying to call the database during its initialization
     **/
    DATABASE_NOT_INITIALIZED(2038),
    /**
     * Orthanc has been built without SSL support
     **/
    SSL_DISABLED(2039),
    /**
     * Unable to order the slices of the series
     **/
    CANNOT_ORDER_SLICES(2040),
    /**
     * No request handler factory for DICOM C-Find Modality SCP
     **/
    NO_WORKLIST_HANDLER(2041),
    /**
     * Cannot override the value of a tag that already exists
     **/
    ALREADY_EXISTING_TAG(2042),
    /**
     * No request handler factory for DICOM N-ACTION SCP (storage commitment)
     **/
    NO_STORAGE_COMMITMENT_HANDLER(2043),
    /**
     * No request handler factory for DICOM C-GET SCP
     **/
    NO_CGET_HANDLER(2044),
    /**
     * Unsupported media type
     **/
    UNSUPPORTED_MEDIA_TYPE(3000);

    private int value;

    private ErrorCode(int value) {
        this.value = value;
    }

    /**
     * Return the enumeration value that corresponds to an integer value of interest.
     * @param value The integer value.
     * @return The enumeration value.
     **/
    protected static ErrorCode getInstance(int value) {
        if (value == -1) {
            return INTERNAL_ERROR;
        }
        if (value == 0) {
            return SUCCESS;
        }
        if (value == 1) {
            return PLUGIN;
        }
        if (value == 2) {
            return NOT_IMPLEMENTED;
        }
        if (value == 3) {
            return PARAMETER_OUT_OF_RANGE;
        }
        if (value == 4) {
            return NOT_ENOUGH_MEMORY;
        }
        if (value == 5) {
            return BAD_PARAMETER_TYPE;
        }
        if (value == 6) {
            return BAD_SEQUENCE_OF_CALLS;
        }
        if (value == 7) {
            return INEXISTENT_ITEM;
        }
        if (value == 8) {
            return BAD_REQUEST;
        }
        if (value == 9) {
            return NETWORK_PROTOCOL;
        }
        if (value == 10) {
            return SYSTEM_COMMAND;
        }
        if (value == 11) {
            return DATABASE;
        }
        if (value == 12) {
            return URI_SYNTAX;
        }
        if (value == 13) {
            return INEXISTENT_FILE;
        }
        if (value == 14) {
            return CANNOT_WRITE_FILE;
        }
        if (value == 15) {
            return BAD_FILE_FORMAT;
        }
        if (value == 16) {
            return TIMEOUT;
        }
        if (value == 17) {
            return UNKNOWN_RESOURCE;
        }
        if (value == 18) {
            return INCOMPATIBLE_DATABASE_VERSION;
        }
        if (value == 19) {
            return FULL_STORAGE;
        }
        if (value == 20) {
            return CORRUPTED_FILE;
        }
        if (value == 21) {
            return INEXISTENT_TAG;
        }
        if (value == 22) {
            return READ_ONLY;
        }
        if (value == 23) {
            return INCOMPATIBLE_IMAGE_FORMAT;
        }
        if (value == 24) {
            return INCOMPATIBLE_IMAGE_SIZE;
        }
        if (value == 25) {
            return SHARED_LIBRARY;
        }
        if (value == 26) {
            return UNKNOWN_PLUGIN_SERVICE;
        }
        if (value == 27) {
            return UNKNOWN_DICOM_TAG;
        }
        if (value == 28) {
            return BAD_JSON;
        }
        if (value == 29) {
            return UNAUTHORIZED;
        }
        if (value == 30) {
            return BAD_FONT;
        }
        if (value == 31) {
            return DATABASE_PLUGIN;
        }
        if (value == 32) {
            return STORAGE_AREA_PLUGIN;
        }
        if (value == 33) {
            return EMPTY_REQUEST;
        }
        if (value == 34) {
            return NOT_ACCEPTABLE;
        }
        if (value == 35) {
            return NULL_POINTER;
        }
        if (value == 36) {
            return DATABASE_UNAVAILABLE;
        }
        if (value == 37) {
            return CANCELED_JOB;
        }
        if (value == 38) {
            return BAD_GEOMETRY;
        }
        if (value == 39) {
            return SSL_INITIALIZATION;
        }
        if (value == 40) {
            return DISCONTINUED_ABI;
        }
        if (value == 41) {
            return BAD_RANGE;
        }
        if (value == 42) {
            return DATABASE_CANNOT_SERIALIZE;
        }
        if (value == 43) {
            return REVISION;
        }
        if (value == 1000) {
            return SQLITE_NOT_OPENED;
        }
        if (value == 1001) {
            return SQLITE_ALREADY_OPENED;
        }
        if (value == 1002) {
            return SQLITE_CANNOT_OPEN;
        }
        if (value == 1003) {
            return SQLITE_STATEMENT_ALREADY_USED;
        }
        if (value == 1004) {
            return SQLITE_EXECUTE;
        }
        if (value == 1005) {
            return SQLITE_ROLLBACK_WITHOUT_TRANSACTION;
        }
        if (value == 1006) {
            return SQLITE_COMMIT_WITHOUT_TRANSACTION;
        }
        if (value == 1007) {
            return SQLITE_REGISTER_FUNCTION;
        }
        if (value == 1008) {
            return SQLITE_FLUSH;
        }
        if (value == 1009) {
            return SQLITE_CANNOT_RUN;
        }
        if (value == 1010) {
            return SQLITE_CANNOT_STEP;
        }
        if (value == 1011) {
            return SQLITE_BIND_OUT_OF_RANGE;
        }
        if (value == 1012) {
            return SQLITE_PREPARE_STATEMENT;
        }
        if (value == 1013) {
            return SQLITE_TRANSACTION_ALREADY_STARTED;
        }
        if (value == 1014) {
            return SQLITE_TRANSACTION_COMMIT;
        }
        if (value == 1015) {
            return SQLITE_TRANSACTION_BEGIN;
        }
        if (value == 2000) {
            return DIRECTORY_OVER_FILE;
        }
        if (value == 2001) {
            return FILE_STORAGE_CANNOT_WRITE;
        }
        if (value == 2002) {
            return DIRECTORY_EXPECTED;
        }
        if (value == 2003) {
            return HTTP_PORT_IN_USE;
        }
        if (value == 2004) {
            return DICOM_PORT_IN_USE;
        }
        if (value == 2005) {
            return BAD_HTTP_STATUS_IN_REST;
        }
        if (value == 2006) {
            return REGULAR_FILE_EXPECTED;
        }
        if (value == 2007) {
            return PATH_TO_EXECUTABLE;
        }
        if (value == 2008) {
            return MAKE_DIRECTORY;
        }
        if (value == 2009) {
            return BAD_APPLICATION_ENTITY_TITLE;
        }
        if (value == 2010) {
            return NO_CFIND_HANDLER;
        }
        if (value == 2011) {
            return NO_CMOVE_HANDLER;
        }
        if (value == 2012) {
            return NO_CSTORE_HANDLER;
        }
        if (value == 2013) {
            return NO_APPLICATION_ENTITY_FILTER;
        }
        if (value == 2014) {
            return NO_SOP_CLASS_OR_INSTANCE;
        }
        if (value == 2015) {
            return NO_PRESENTATION_CONTEXT;
        }
        if (value == 2016) {
            return DICOM_FIND_UNAVAILABLE;
        }
        if (value == 2017) {
            return DICOM_MOVE_UNAVAILABLE;
        }
        if (value == 2018) {
            return CANNOT_STORE_INSTANCE;
        }
        if (value == 2019) {
            return CREATE_DICOM_NOT_STRING;
        }
        if (value == 2020) {
            return CREATE_DICOM_OVERRIDE_TAG;
        }
        if (value == 2021) {
            return CREATE_DICOM_USE_CONTENT;
        }
        if (value == 2022) {
            return CREATE_DICOM_NO_PAYLOAD;
        }
        if (value == 2023) {
            return CREATE_DICOM_USE_DATA_URI_SCHEME;
        }
        if (value == 2024) {
            return CREATE_DICOM_BAD_PARENT;
        }
        if (value == 2025) {
            return CREATE_DICOM_PARENT_IS_INSTANCE;
        }
        if (value == 2026) {
            return CREATE_DICOM_PARENT_ENCODING;
        }
        if (value == 2027) {
            return UNKNOWN_MODALITY;
        }
        if (value == 2028) {
            return BAD_JOB_ORDERING;
        }
        if (value == 2029) {
            return JSON_TO_LUA_TABLE;
        }
        if (value == 2030) {
            return CANNOT_CREATE_LUA;
        }
        if (value == 2031) {
            return CANNOT_EXECUTE_LUA;
        }
        if (value == 2032) {
            return LUA_ALREADY_EXECUTED;
        }
        if (value == 2033) {
            return LUA_BAD_OUTPUT;
        }
        if (value == 2034) {
            return NOT_LUA_PREDICATE;
        }
        if (value == 2035) {
            return LUA_RETURNS_NO_STRING;
        }
        if (value == 2036) {
            return STORAGE_AREA_ALREADY_REGISTERED;
        }
        if (value == 2037) {
            return DATABASE_BACKEND_ALREADY_REGISTERED;
        }
        if (value == 2038) {
            return DATABASE_NOT_INITIALIZED;
        }
        if (value == 2039) {
            return SSL_DISABLED;
        }
        if (value == 2040) {
            return CANNOT_ORDER_SLICES;
        }
        if (value == 2041) {
            return NO_WORKLIST_HANDLER;
        }
        if (value == 2042) {
            return ALREADY_EXISTING_TAG;
        }
        if (value == 2043) {
            return NO_STORAGE_COMMITMENT_HANDLER;
        }
        if (value == 2044) {
            return NO_CGET_HANDLER;
        }
        if (value == 3000) {
            return UNSUPPORTED_MEDIA_TYPE;
        }

        throw new IllegalArgumentException("Value out of range for enumeration ErrorCode: " + value);
    }

    /**
     * Get the integer value corresponding to this enumeration value.
     * @return The integer value.
     **/
    public int getValue() {
        return value;
    }
}
