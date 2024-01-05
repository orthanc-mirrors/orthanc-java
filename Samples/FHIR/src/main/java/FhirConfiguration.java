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


import be.uclouvain.orthanc.Functions;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class FhirConfiguration {
    static private final String KEY_FHIR = "FHIR";
    static private final String KEY_DICOM_WEB = "DicomWeb";
    static private final String KEY_ROOT = "Root";
    static private final String KEY_BASE_URL = "BaseUrl";

    private String serverBaseUrl = "http://localhost:8042/fhir";
    private String dicomWebRelativeUri = "../dicom-web";

    FhirConfiguration() {
        JSONObject configuration = new JSONObject(Functions.getConfiguration());

        if (configuration.has(KEY_FHIR)) {
            JSONObject fhir = configuration.getJSONObject(KEY_FHIR);
            if (fhir.has(KEY_BASE_URL)) {
                serverBaseUrl = fhir.getString(KEY_BASE_URL);
            }
        }

        if (configuration.has(KEY_DICOM_WEB)) {
            JSONObject dicomWeb = configuration.getJSONObject(KEY_DICOM_WEB);
            if (dicomWeb.has(KEY_ROOT)) {
                dicomWebRelativeUri = "../" + dicomWeb.getString(KEY_ROOT);
            }
        }

        if (!serverBaseUrl.endsWith("/")) {
            serverBaseUrl = serverBaseUrl + "/";
        }
    }

    public String getServerBaseUrl() {
        return serverBaseUrl;
    }

    public String getDicomWebBaseUrl() {
        /**
         * From URL: <scheme>://<authority><path>?<query>#<fragment>
         */

        try {
            URL url = new URL(serverBaseUrl);
            URI uri = new URI(url.getPath());
            return url.getProtocol() + "://" + url.getAuthority() + uri.resolve(dicomWebRelativeUri);
        } catch (MalformedURLException | URISyntaxException e) {
            throw new IllegalArgumentException("Bad URL for DICOMweb: " + serverBaseUrl + dicomWebRelativeUri);
        }
    }
}
