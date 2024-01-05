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


import be.uclouvain.orthanc.Callbacks;
import be.uclouvain.orthanc.Functions;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.server.HardcodedServerAddressStrategy;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.RestfulServer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.mock.web.MockServletConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;


public class Main extends RestfulServer {
    Main() throws ServletException {
        setFhirContext(FhirContext.forR5());

        FhirConfiguration configuration = new FhirConfiguration();
        setServerAddressStrategy(new HardcodedServerAddressStrategy(configuration.getServerBaseUrl()));

        configuration.getDicomWebBaseUrl();

        IOrthancConnection connection = new OrthancPluginConnection();

        List<IResourceProvider> resourceProviders = new ArrayList<IResourceProvider>();
        resourceProviders.add(new PatientProvider(connection));
        resourceProviders.add(new ImagingStudyProvider(connection));
        resourceProviders.add(new EndpointProvider(connection));
        setResourceProviders(resourceProviders);
    }

    static {
        Main main;

        try {
            main = new Main();
            main.init(new MockServletConfig());
        } catch (ServletException e) {
            throw new RuntimeException("Cannot start the HAPI FHIR server");
        }

        Callbacks.register("/fhir(/.*)", new FhirRequestHandler(main));
        Callbacks.register("/fhir", new FhirRequestHandler(main));
    }
}
