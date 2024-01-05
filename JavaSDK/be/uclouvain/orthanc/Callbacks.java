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
    public interface OnChange {
        public void call(ChangeType changeType,
                         ResourceType resourceType,
                         String resourceId);
    }

    public interface OnRestRequest {
        public void call(RestOutput output,
                         HttpMethod method,
                         String uri,
                         String[] regularExpressionGroups,
                         Map<String, String> headers,
                         Map<String, String> getParameters,
                         byte[] body);
    }

    public static native void register(OnChange callback);

    public static native void register(String pathRegularExpression,
                                       OnRestRequest callback);
}
