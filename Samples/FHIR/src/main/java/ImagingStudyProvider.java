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
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 **/


import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.OptionalParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.param.ReferenceParam;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import org.hl7.fhir.r5.model.IdType;
import org.hl7.fhir.r5.model.ImagingStudy;
import org.hl7.fhir.r5.model.Patient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImagingStudyProvider implements IResourceProvider {
    private IOrthancConnection orthanc;

    ImagingStudyProvider(IOrthancConnection orthanc) {
        this.orthanc = orthanc;
    }

    @Override
    public Class<ImagingStudy> getResourceType() {
        return ImagingStudy.class;
    }

    @Read
    public ImagingStudy getResourceById(@IdParam IdType theId) {
        Map<String, String> tags = new HashMap<>();
        tags.put(Toolbox.TAG_STUDY_INSTANCE_UID, theId.getIdPart());

        List<OrthancResource> resources = OrthancResource.find(orthanc, be.uclouvain.orthanc.ResourceType.STUDY, tags, true);
        if (resources.size() == 0) {
            return null;
        } else if (resources.size() > 1) {
            throw new RuntimeException("Too many matching resources");
        } else {
            return resources.get(0).getFhirStudy(orthanc);
        }
    }

    @Search()
    public List<ImagingStudy> getImagingStudies(@OptionalParam(name = ImagingStudy.SP_IDENTIFIER) StringParam theIdentifier,
                                                @OptionalParam(name = ImagingStudy.SP_SUBJECT) ReferenceParam theSubject) {
        Map<String, String> tags = new HashMap<>();

        if (theIdentifier != null) {
            tags.put(Toolbox.TAG_STUDY_INSTANCE_UID, theIdentifier.getValue());
        }

        if (theSubject != null) {
            // https://hapifhir.io/hapi-fhir/docs/server_plain/rest_operations_search.html#search-parameters-resource-reference
            tags.put(Toolbox.TAG_PATIENT_ID, theSubject.getValue());
        }

        List<OrthancResource> resources = OrthancResource.find(orthanc, be.uclouvain.orthanc.ResourceType.STUDY, tags, false);

        List<ImagingStudy> studies = new ArrayList<>();
        for (OrthancResource resource : resources) {
            ImagingStudy study = resource.getFhirStudy(orthanc);
            if (study.hasId()) {
                studies.add(study);
            }
        }

        return studies;
    }
}
