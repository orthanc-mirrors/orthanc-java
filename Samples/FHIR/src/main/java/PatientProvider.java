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


import be.uclouvain.orthanc.ResourceType;
import ca.uhn.fhir.rest.annotation.*;
import ca.uhn.fhir.rest.param.DateParam;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import org.hl7.fhir.r5.model.IdType;
import org.hl7.fhir.r5.model.Patient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientProvider implements IResourceProvider {
    private IOrthancConnection orthanc;

    PatientProvider(IOrthancConnection orthanc) {
        this.orthanc = orthanc;
    }

    @Override
    public Class<Patient> getResourceType() {
        return Patient.class;
    }

    @Read()
    public Patient getResourceById(@IdParam IdType theId) {
        Map<String, String> tags = new HashMap<>();
        tags.put(Toolbox.TAG_PATIENT_ID, theId.getIdPart());

        List<OrthancResource> resources = OrthancResource.find(orthanc, ResourceType.PATIENT, tags, true);
        if (resources.size() == 0) {
            return null;
        } else if (resources.size() > 1) {
            throw new RuntimeException("Too many matching resources");
        } else {
            return resources.get(0).getFhirPatient();
        }
    }

    @Search()
    public List<Patient> getPatients(@OptionalParam(name = Patient.SP_FAMILY) StringParam theFamilyName,
                                     @OptionalParam(name = Patient.SP_GIVEN) StringParam theGivenName,
                                     @OptionalParam(name = Patient.SP_IDENTIFIER) StringParam theIdentifier,
                                     @OptionalParam(name = Patient.SP_BIRTHDATE) DateParam theBirthDate,
                                     @Offset Integer theOffset,
                                     @Count Integer theCount) {
        Map<String, String> tags = new HashMap<>();

        if (theFamilyName != null &&
                theGivenName != null) {
            tags.put(Toolbox.TAG_PATIENT_NAME, "*" + theFamilyName.getValue() + "*" + theGivenName.getValue() + "*");
        } else if (theFamilyName != null) {
            tags.put(Toolbox.TAG_PATIENT_NAME, "*" + theFamilyName.getValue() + "*");
        } else if (theGivenName != null) {
            tags.put(Toolbox.TAG_PATIENT_NAME, "*" + theGivenName.getValue() + "*");
        }

        if (theIdentifier != null) {
            tags.put(Toolbox.TAG_PATIENT_ID, theIdentifier.getValue());
        }

        if (theBirthDate != null) {
            tags.put(Toolbox.TAG_PATIENT_BIRTH_DATE, Toolbox.formatDicomDate(theBirthDate.getValue()));
        }

        final boolean caseSensitive = false;

        List<OrthancResource> resources;

        if (theOffset != null &&
                theCount != null) {
            resources = OrthancResource.find(orthanc, ResourceType.PATIENT, tags, caseSensitive, theOffset.intValue(), theCount.intValue());
        } else if (theCount != null) {
            resources = OrthancResource.find(orthanc, ResourceType.PATIENT, tags, caseSensitive, 0, theCount.intValue());
        } else {
            resources = OrthancResource.find(orthanc, ResourceType.PATIENT, tags, caseSensitive);
        }

        List<Patient> patients = new ArrayList<>();
        for (OrthancResource resource : resources) {
            Patient patient = resource.getFhirPatient();
            if (patient.hasId()) {
                patients.add(patient);
            }
        }

        return patients;
    }
}
