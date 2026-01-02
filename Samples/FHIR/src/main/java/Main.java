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


import be.uclouvain.orthanc.Callbacks;
import be.uclouvain.orthanc.ChangeType;
import be.uclouvain.orthanc.ResourceType;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.server.HardcodedServerAddressStrategy;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.RestfulServer;
import org.springframework.mock.web.MockServletConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.servlet.ServletException;


public class Main extends RestfulServer {
    Main() {
        setFhirContext(FhirContext.forR5());

        setServerName("Orthanc FHIR server");

        try (InputStream is = Main.class.getResourceAsStream("app.properties")) {
            Properties properties = new Properties();
            properties.load(is);
            setServerVersion(properties.getProperty("orthanc_java.version"));
        } catch (IOException e) {
            // Ignore error
        }

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

    private static Main main = new Main();

    static {
        try {
            main.init(new MockServletConfig());
        } catch (ServletException e) {
            throw new RuntimeException("Cannot start the HAPI FHIR server");
        }

        Callbacks.register(new Callbacks.OnChange() {
            @Override
            public void call(ChangeType changeType, ResourceType resourceType, String resourceId) {
                if (changeType == ChangeType.ORTHANC_STOPPED) {
                    main.destroy();
                }
            }
        });

        Callbacks.register("/fhir(/.*)", new FhirRequestHandler(main));
        Callbacks.register("/fhir", new FhirRequestHandler(main));
    }
}
