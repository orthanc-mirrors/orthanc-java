package be.uclouvain.orthanc;

/**
 * SPDX-FileCopyrightText: 2023-2024 Sebastien Jodogne, UCLouvain, Belgium
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

/**
 * Java plugin for Orthanc
 * Copyright (C) 2023-2024 Sebastien Jodogne, UCLouvain, Belgium
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


import java.util.Map;

/**
 * Wrapper around the callbacks provided by the Orthanc SDK.
 **/
public class Callbacks {
    /**
     * Callback to react to changes.
     **/
    public interface OnChange {
        /**
         * Signature of a callback function that is triggered when a
         * change happens to some DICOM resource.
         * @param changeType The type of change.
         * @param resourceType The type of resource affected by this change.
         * @param resourceId The identifier of the affected resource, if any.
         **/
        public void call(ChangeType changeType,
                         ResourceType resourceType,
                         String resourceId);
    }

    /**
     * Callback to serve a resource in the REST API.
     **/
    public interface OnRestRequest {
        /**
         * Signature of a callback function that answers a REST request.
         * @param output Output containing the answer that is sent to the REST client.
         * @param method The HTTP method.
         * @param uri The URI, starting from the root of the Orthanc server.
         * @param regularExpressionGroups The actual values of the groups matched by the regular expression.
         * @param headers The HTTP headers of the request.
         * @param getParameters The parameters of a GET request.
         * @param body The body of the request (only applicable to POST and PUT methods).
         **/
        public void call(RestOutput output,
                         HttpMethod method,
                         String uri,
                         String[] regularExpressionGroups,
                         Map<String, String> headers,
                         Map<String, String> getParameters,
                         byte[] body);
    }

    /**
     * Register a callback to monitor changes.
     * @param callback The callback to handle the change.
     **/
    public static native void register(OnChange callback);

    /**
     * Register a REST callback. Note that the callback will NOT be
     * invoked in mutual exclusion.
     * @param pathRegularExpression Regular expression for the URI. May contain groups.
     * @param callback The callback to handle the REST call.
     **/
    public static native void register(String pathRegularExpression,
                                       OnRestRequest callback);
}
