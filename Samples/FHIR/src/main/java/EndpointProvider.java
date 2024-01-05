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


import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.server.IResourceProvider;
import org.hl7.fhir.r5.model.Endpoint;
import org.hl7.fhir.r5.model.IdType;

import java.util.ArrayList;
import java.util.List;

public class EndpointProvider implements IResourceProvider {
    public static final String ID = "wado-rs";
    private IOrthancConnection orthanc;
    private FhirConfiguration configuration;

    EndpointProvider(IOrthancConnection orthanc) {
        this.orthanc = orthanc;
        this.configuration = new FhirConfiguration();
    }

    private Endpoint createEndpoint() {
        Endpoint endpoint = new Endpoint();

        // https://build.fhir.org/endpoint-example-wadors.json.html

        endpoint.setAddress(configuration.getDicomWebBaseUrl());
        endpoint.setId(ID);
        endpoint.setStatus(Endpoint.EndpointStatus.ACTIVE);
        endpoint.addConnectionType(Toolbox.createCodeableConcept("http://terminology.hl7.org/CodeSystem/endpoint-connection-type", "dicom-wado-rs"));
        endpoint.setName("Orthanc DICOMweb server");

        return endpoint;
    }

    @Override
    public Class<Endpoint> getResourceType() {
        return Endpoint.class;
    }

    @Read()
    public Endpoint getResourceById(@IdParam IdType theId) {
        if (theId.getIdPart().equals(ID) &&
                IOrthancConnection.hasPluginInstalled(orthanc, "dicom-web")) {
            return createEndpoint();
        } else {
            return null;
        }
    }

    @Search()
    public List<Endpoint> getEndpoints() {
        List<Endpoint> endpoints = new ArrayList<>();

        if (IOrthancConnection.hasPluginInstalled(orthanc, "dicom-web")) {
            endpoints.add(createEndpoint());
        }

        return endpoints;
    }
}
