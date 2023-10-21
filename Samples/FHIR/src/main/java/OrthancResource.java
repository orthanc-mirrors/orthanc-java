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


import be.uclouvain.orthanc.ResourceType;
import org.hl7.fhir.r5.model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrthancResource {
    private ResourceType type;
    private String id;
    private String lastUpdate;
    private Map<String, String> tags;
    private List<String> children;

    public OrthancResource(JSONObject info) {
        String s = info.getString("Type");
        if (s.equals("Patient")) {
            type = ResourceType.PATIENT;
        } else if (s.equals("Study")) {
            type = ResourceType.STUDY;
        } else if (s.equals("Series")) {
            type = ResourceType.SERIES;
        } else if (s.equals("Instance")) {
            type = ResourceType.INSTANCE;
        } else {
            throw new RuntimeException("Unknown resource type");
        }

        id = info.getString("ID");
        lastUpdate = info.optString("LastUpdate");
        tags = new HashMap<>();
        addToDictionary(tags, info.getJSONObject("MainDicomTags"));

        if (type != ResourceType.INSTANCE) {
            String childKey;
            switch (type) {
                case PATIENT:
                    childKey = "Studies";
                    break;
                case STUDY:
                    childKey = "Series";
                    addToDictionary(tags, info.getJSONObject("PatientMainDicomTags"));
                    break;
                case SERIES:
                    childKey = "Instances";
                    break;
                default:
                    throw new RuntimeException();
            }

            children = new ArrayList<>();
            addToListOfStrings(children, info.getJSONArray(childKey));
        }
    }

    static public void addToDictionary(Map<String, String> target,
                                       JSONObject source) {
        for (String key : source.keySet()) {
            target.put(key, source.getString(key));
        }
    }

    static public void addToListOfStrings(List<String> target,
                                          JSONArray source) {
        for (int i = 0; i < source.length(); i++) {
            target.add(source.getString(i));
        }
    }

    public ResourceType getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public List<String> getChildren() {
        if (type == ResourceType.INSTANCE) {
            throw new RuntimeException("A DICOM instance has no child");
        } else {
            return children;
        }
    }

    public static List<OrthancResource> find(IOrthancConnection connection,
                                             ResourceType type,
                                             Map<String, String> tags,
                                             boolean caseSensitive) {
        JSONObject query = new JSONObject();
        for (Map.Entry<String, String> entry : tags.entrySet()) {
            query.put(entry.getKey(), entry.getValue());
        }

        JSONObject request = new JSONObject();
        request.put("Expand", true);
        request.put("Query", query);
        request.put("Short", true);
        request.put("CaseSensitive", caseSensitive);

        switch (type) {
            case PATIENT:
                request.put("Level", "Patient");
                break;
            case STUDY:
                request.put("Level", "Study");
                break;
            case SERIES:
                request.put("Level", "Series");
                break;
            case INSTANCE:
                request.put("Level", "Instance");
                break;
            default:
                throw new RuntimeException();
        }

        byte[] response = connection.doPost("/tools/find", request.toString().getBytes(StandardCharsets.UTF_8));

        JSONArray arr = new JSONArray(new String(response, StandardCharsets.UTF_8));

        List<OrthancResource> result = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            result.add(new OrthancResource(arr.getJSONObject(i)));
        }

        return result;
    }


    public Patient getFhirPatient() {
        if (type != ResourceType.PATIENT) {
            throw new RuntimeException("Not a patient");
        }

        Patient patient = new Patient();
        patient.setId(getTags().getOrDefault(Toolbox.TAG_PATIENT_ID, ""));

        String birthDate = getTags().getOrDefault(Toolbox.TAG_PATIENT_BIRTH_DATE, "");
        if (birthDate != null) {
            patient.setBirthDate(Toolbox.parseDicomDate(birthDate));
        }

        String patientName = getTags().getOrDefault(Toolbox.TAG_PATIENT_NAME, "");
        if (!patientName.isEmpty()) {
            patient.addName();
            HumanName name = patient.getName().get(0);

            String[] parts = patientName.split("\\^");

            // https://dicom.nema.org/medical/dicom/current/output/chtml/part19/sect_10.2.html
            // https://www.hl7.org/fhir/datatypes.html#HumanName
            if (parts.length > 0) {
                name.setFamily(parts[0]);
            }
            for (int i = 1; i < parts.length; i++) {
                name.addGiven(parts[i]);
            }
        }

        String sex = getTags().getOrDefault(Toolbox.TAG_PATIENT_SEX, "");
        if (sex.equals("M")) {
            patient.setGender(Enumerations.AdministrativeGender.MALE);
        } else if (sex.equals("F")) {
            patient.setGender(Enumerations.AdministrativeGender.FEMALE);
        }

        return patient;
    }

    public ImagingStudy getFhirStudy(IOrthancConnection orthanc) {
        if (type != ResourceType.STUDY) {
            throw new RuntimeException("Not a study");
        }

        boolean hasDicomWeb = IOrthancConnection.hasPluginInstalled(orthanc, "dicom-web");

        // https://build.fhir.org/imagingstudy-example.json.html

        ImagingStudy study = new ImagingStudy();
        study.setId(getTags().getOrDefault(Toolbox.TAG_STUDY_INSTANCE_UID, ""));
        study.setStatus(ImagingStudy.ImagingStudyStatus.AVAILABLE);

        if (hasDicomWeb) {
            study.addEndpoint(Toolbox.createLocalReference("Endpoint", EndpointProvider.ID));
        }

        study.setSubject(Toolbox.createLocalReference("Patient", getTags().getOrDefault(Toolbox.TAG_PATIENT_ID, "")));

        String studyDate = getTags().getOrDefault(Toolbox.TAG_STUDY_DATE, "");
        if (!studyDate.isEmpty()) {
            study.setStarted(Toolbox.parseDicomDate(studyDate));
        }

        study.addIdentifier();
        study.getIdentifier().get(0).setSystem("urn:dicom:uid");
        study.getIdentifier().get(0).setValue("urn:oid:" + study.getId());

        study.setNumberOfSeries(getChildren().size());

        int countInstances = 0;

        Map<String, String> shortTags = new HashMap<>();
        shortTags.put("short", "");

        Map<String, String> expand = new HashMap<>();
        expand.put("expand", "");

        for (int i = 0; i < getChildren().size(); i++) {
            String seriesUri = "/series/" + getChildren().get(i);
            OrthancResource orthancSeries = new OrthancResource(IOrthancConnection.getJSONObject(orthanc, seriesUri, shortTags));

            ImagingStudy.ImagingStudySeriesComponent fhirSeries = study.addSeries();
            fhirSeries.setUid(orthancSeries.getTags().getOrDefault(Toolbox.TAG_SERIES_INSTANCE_UID, ""));

            String modality = orthancSeries.getTags().getOrDefault(Toolbox.TAG_MODALITY, "");
            if (!modality.isEmpty()) {
                fhirSeries.setModality(Toolbox.createDicomCodeableConcept(modality));
            }

            String seriesDescription = orthancSeries.getTags().getOrDefault(Toolbox.TAG_SERIES_DESCRIPTION, "");
            if (!seriesDescription.isEmpty()) {
                fhirSeries.setDescription(seriesDescription);
            }

            String seriesNumber = orthancSeries.getTags().getOrDefault(Toolbox.TAG_SERIES_NUMBER, "");
            if (!seriesNumber.isEmpty()) {
                fhirSeries.setNumber(Integer.parseInt(seriesNumber));
            }

            fhirSeries.setNumberOfInstances(orthancSeries.getChildren().size());

            for (int j = 0; j < orthancSeries.getChildren().size(); j++) {
                String instanceUri = "/instances/" + orthancSeries.getChildren().get(j);
                OrthancResource orthancInstance = new OrthancResource(IOrthancConnection.getJSONObject(orthanc, instanceUri, shortTags));

                JSONObject instanceMetadata = IOrthancConnection.getJSONObject(orthanc, instanceUri + "/metadata", expand);

                ImagingStudy.ImagingStudySeriesInstanceComponent fhirInstance = fhirSeries.addInstance();
                fhirInstance.setUid(orthancInstance.getTags().getOrDefault(Toolbox.TAG_SOP_INSTANCE_UID, ""));

                String instanceNumber = orthancInstance.getTags().getOrDefault(Toolbox.TAG_INSTANCE_NUMBER, "");
                if (!instanceNumber.isEmpty()) {
                    fhirInstance.setNumber(Integer.parseInt(instanceNumber));
                }

                String sopClassUid = instanceMetadata.optString("SopClassUid", "");
                if (!sopClassUid.isEmpty()) {
                    fhirInstance.setSopClass(new Coding("urn:ietf:rfc:3986", "urn:oid:" + sopClassUid, ""));
                }
            }

            countInstances += orthancSeries.getChildren().size();
        }

        study.setNumberOfInstances(countInstances);

        return study;
    }
}
