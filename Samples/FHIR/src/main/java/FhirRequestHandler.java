/**
 * SPDX-FileCopyrightText: 2023-2026 Sebastien Jodogne, ICTEAM UCLouvain, Belgium
 * SPDX-License-Identifier: GPL-3.0-or-later
 **/

/**
 * Java plugin for Orthanc
 * Copyright (C) 2023-2026 Sebastien Jodogne, ICTEAM UCLouvain, Belgium
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


import ca.uhn.fhir.rest.server.RestfulServer;
import be.uclouvain.orthanc.Callbacks;
import be.uclouvain.orthanc.RestOutput;
import be.uclouvain.orthanc.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

public class FhirRequestHandler implements Callbacks.OnRestRequest {
    private static RestfulServer server;

    FhirRequestHandler(RestfulServer server) {
        this.server = server;
    }

    @Override
    public void call(RestOutput output,
                     HttpMethod method,
                     String uri,
                     String[] regularExpressionGroups,
                     Map<String, String> headers,
                     Map<String, String> getParameters, byte[] body) {
        MockHttpServletRequest request = new MockHttpServletRequest();

        if (regularExpressionGroups.length == 0) {
            request.setRequestURI("/");
        } else {
            request.setRequestURI(regularExpressionGroups[0]);
        }
        request.setContent(body);

        switch (method) {
            case GET:
                request.setMethod("GET");
                break;
            case POST:
                request.setMethod("POST");
                break;
            case PUT:
                request.setMethod("PUT");
                break;
            case DELETE:
                request.setMethod("DELETE");
                break;
            default:
                throw new IllegalArgumentException("Unknown HTTP method");
        }

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            request.addHeader(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<String, String> entry : getParameters.entrySet()) {
            request.setParameter(entry.getKey(), entry.getValue());
        }

        try {
            MockHttpServletResponse response = new MockHttpServletResponse();
            server.service(request, response);

            for (String header : response.getHeaderNames()) {
                if (header != "Content-Type") {
                    output.setHttpHeader(header, response.getHeader(header));
                }
            }

            output.answerBuffer(response.getContentAsByteArray(), response.getContentType());
        } catch (IOException e) {
            output.sendHttpStatusCode((short) 500);
        } catch (ServletException e) {
            output.sendHttpStatusCode((short) 500);
        }
    }
}
