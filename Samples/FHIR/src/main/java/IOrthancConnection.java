/**
 * SPDX-FileCopyrightText: 2023-2024 Sebastien Jodogne, UCLouvain, Belgium
 * SPDX-License-Identifier: GPL-3.0-or-later
 **/

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
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 **/


import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public interface IOrthancConnection {
    public byte[] doGet(String uri,
                        Map<String, String> parameters);

    public byte[] doPost(String uri,
                         byte[] body);

    public static String formatGetParameters(Map<String, String> parameters) {
        String result = "";

        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String item;
            if (entry.getValue().isEmpty()) {
                item = entry.getKey();
            } else {
                item = entry.getKey() + "=" + entry.getValue();
            }

            if (result.isEmpty()) {
                result = "?" + item;
            } else {
                result = result + "&" + item;
            }
        }

        return result;
    }

    public static JSONObject getJSONObject(IOrthancConnection orthanc,
                                           String uri,
                                           Map<String, String> parameters) {
        byte[] a = orthanc.doGet(uri, parameters);
        return new JSONObject(new String(a, StandardCharsets.UTF_8));
    }

    public static JSONArray getJSONArray(IOrthancConnection orthanc,
                                         String uri,
                                         Map<String, String> parameters) {
        byte[] a = orthanc.doGet(uri, parameters);
        return new JSONArray(new String(a, StandardCharsets.UTF_8));
    }

    public static boolean hasPluginInstalled(IOrthancConnection orthanc,
                                             String plugin) {
        Map<String, String> empty = new HashMap<>();
        JSONArray plugins = IOrthancConnection.getJSONArray(orthanc, "/plugins", empty);

        for (int i = 0; i < plugins.length(); i++) {
            if (plugins.getString(i).equals(plugin)) {
                return true;
            }
        }

        return false;
    }
}
