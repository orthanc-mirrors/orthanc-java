/**
 * SPDX-FileCopyrightText: 2023-2025 Sebastien Jodogne, ICTEAM UCLouvain, Belgium
 * SPDX-License-Identifier: GPL-3.0-or-later
 **/

/**
 * Java plugin for Orthanc
 * Copyright (C) 2023-2025 Sebastien Jodogne, ICTEAM UCLouvain, Belgium
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

import java.io.IOException;
import java.util.List;

public class DicomToolbox {
    private static final String MAPPING_RESOURCE = "0008,0105";
    private static final String REFERENCED_SOP_CLASS_UID = "0008,1150";
    private static final String REFERENCED_SOP_INSTANCE_UID = "0008,1155";
    private static final String REFERENCED_SOP_SEQUENCE = "0008,1199";

    private static final String CONTENT_TEMPLATE_SEQUENCE = "0040,a504";
    private static final String TEMPLATE_IDENTIFIER = "0040,db00";
    private static final String RELATIONSHIP_TYPE = "0040,a010";
    private static final String CONCEPT_NAME_CODE_SEQUENCE = "0040,a043";
    private static final String VALUE_TYPE = "0040,a040";
    private static final String CONCEPT_CODE_SEQUENCE = "0040,a168";
    private static final String CONTINUITY_OF_CONTENT = "0040,a050";
    private static final String CONTENT_SEQUENCE = "0040,a730";
    private static final String TEXT_VALUE_ATTRIBUTE = "0040,a160";
    private static final String UID_ATTRIBUTE = "0040,a124";
    private static final String MEASURED_VALUE_SEQUENCE = "0040,a300";
    private static final String MEASUREMENT_UNITS_CODE_SEQUENCE = "0040,08ea";
    private static final String FLOATING_POINT_VALUE = "0040,a161";
    private static final String NUMERIC_VALUE = "0040,a30a";
    private static final String GRAPHIC_DATA = "0070,0022";
    private static final String GRAPHIC_TYPE = "0070,0023";

    static JSONObject createDicomSR(OrthancConnection client,
                                    String instance,
                                    List<Detection> detections,
                                    double aspectRatio) throws IOException, InterruptedException {
        final JSONObject instanceTags = client.doGetAsJsonObject("/instances/" + instance + "/tags?short");
        final String trackingUid = client.doGetAsString("/tools/generate-uid?level=instance");

        String instanceStudy = client.doGetAsJsonObject("/instances/" + instance + "/study").getString("ID");

        // https://dicom.nema.org/medical/dicom/2024a/output/chtml/part16/chapter_A.html#sect_TID_1500
        // Sample TID 1500: https://dicom.nema.org/medical/dicom/current/output/chtml/part21/sect_a.7.2.html


        JSONObject json = new JSONObject();
        json.put("Parent", instanceStudy);

        JSONObject tags = new JSONObject();
        tags.put("0008,0016", "1.2.840.10008.5.1.4.1.1.88.33");  // SOP Class UID
        tags.put("0008,0060", "SR"); // Modality
        tags.put("0008,0070", "");   // Manufacturer
        tags.put("0008,1111", new JSONArray());  // Referenced Performed Procedure Step Sequence is type 2 (required but can be empty)
        tags.put("0020,0011", "1");  // Series number
        tags.put("0020,0013", "1");  // Instance number
        tags.put("0040,a040", "CONTAINER");  // Value type for SR: https://dicom.nema.org/medical/dicom/2024a/output/chtml/part03/sect_C.17.3.html

        // CID 7021 Measurement Report Document Title: https://dicom.nema.org/medical/dicom/2024a/output/chtml/part16/sect_CID_7021.html
        tags.append("0040,a043", new DicomCode("126000", "DCM", "Imaging Measurement Report").toJson());

        tags.put("0040,a050", "CONTINUOUS");  // Continuity of content
        tags.put("0040,a372", new JSONArray());  // Performed procedure code sequence

        // Current Requested Procedure Evidence: References to the StudyInstanceUID, SeriesInstanceUID, SOPInstanceUID, and SOPClassUID
        tags.append("0040,a375", new JSONObject().
                put("0020,000d", instanceTags.getString("0020,000d")).
                append("0008,1115", new JSONObject().
                        put("0020,000e", instanceTags.getString("0020,000e")).
                        append("0008,1199", new JSONObject().
                                put("0008,1150", instanceTags.getString("0008,0016")).
                                put("0008,1155", instanceTags.getString("0008,0018")))));

        tags.put("0040,a491", "PARTIAL");      // Completion flag
        tags.put("0040,a493", "UNVERIFIED");   // Verification flag
        tags.put("0040,a496", "PRELIMINARY");  // Preliminary flag

        // Content template sequence, which indicates TID 1500
        setTemplateId(tags, "1500");

        JSONArray contentSequence = new JSONArray();
        contentSequence.put(createCodeRelationship(
                new DicomCode("121049", "DCM", "Language of Content Item and Descendants"),
                new DicomCode("en-US", "RFC5646", "English (United States)")));

        // This is "procedure_reported" in "highdicom.sr.MeasurementReport()"
        contentSequence.put(createCodeRelationship(
                new DicomCode("121058", "DCM", "Procedure reported"),
                new DicomCode("43468-8", "LN", "XR unspecified body region")));

        JSONArray measurementGroups = new JSONArray();

        for (Detection detection : detections) {
            JSONArray measurements = new JSONArray();
            measurements.put(createTextContext(
                    new DicomCode("112039", "DCM", "Tracking Identifier"),
                    "Orthanc Deep Learning for Mammography"));

            measurements.put(createUidReference(
                    new DicomCode("112040", "DCM", "Tracking Unique Identifier"),
                    trackingUid));

            measurements.put(createNumericValue(
                    new DicomCode("111047", "DCM", "Probability of cancer"),
                    new DicomCode("%", "UCUM", "Percent"),
                    detection.getScore() * 100.0));

            measurements.put(createRectangle(
                    new DicomCode("111030", "DCM", "Image Region"),
                    instanceTags.getString("0008,0016"), instanceTags.getString("0008,0018"),
                    detection.getRectangle(), aspectRatio));

            JSONObject measurementGroup = createContainer(new DicomCode("125007", "DCM", "Measurement Group"), measurements);
            setTemplateId(measurementGroup, "1410");

            measurementGroups.put(measurementGroup);
        }

        JSONObject imagingMeasurements = createContainer(new DicomCode("126010", "DCM", "Imaging Measurements"), measurementGroups);
        contentSequence.put(imagingMeasurements);

        tags.put("0040,a730", contentSequence);

        json.put("Tags", tags);

        return client.doPostAsJsonObject("/tools/create-dicom", json.toString());
    }

    static public class Point2D {
        private double x;
        private double y;

        public Point2D(double x,
                       double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }

    public static JSONObject setTemplateId(JSONObject target,
                                           String templateId) {
        if (target.has(CONTENT_TEMPLATE_SEQUENCE)) {
            throw new IllegalStateException();
        } else {
            target.append(CONTENT_TEMPLATE_SEQUENCE, new JSONObject().
                    put(MAPPING_RESOURCE, "DCMR").
                    put(TEMPLATE_IDENTIFIER, templateId));
            return target;
        }
    }

    public static JSONObject createCodeRelationship(DicomCode concept,
                                                    DicomCode referencedCode) {
        JSONObject result = new JSONObject();
        result.put(RELATIONSHIP_TYPE, "HAS CONCEPT MOD");
        result.append(CONCEPT_NAME_CODE_SEQUENCE, concept.toJson());
        result.put(VALUE_TYPE, "CODE");
        result.append(CONCEPT_CODE_SEQUENCE, referencedCode.toJson());
        return result;
    }

    public static JSONObject createContainer(DicomCode concept,
                                             JSONArray content) {
        JSONObject result = new JSONObject();
        result.put(RELATIONSHIP_TYPE, "CONTAINS");
        result.put(VALUE_TYPE, "CONTAINER");
        result.append(CONCEPT_NAME_CODE_SEQUENCE, concept.toJson());
        result.put(CONTINUITY_OF_CONTENT, "CONTINUOUS");
        result.put(CONTENT_SEQUENCE, content);
        return result;
    }

    public static JSONObject createTextContext(DicomCode concept,
                                               String value) {
        JSONObject result = new JSONObject();
        result.put(RELATIONSHIP_TYPE, "HAS OBS CONTEXT");
        result.put(VALUE_TYPE, "TEXT");
        result.append(CONCEPT_NAME_CODE_SEQUENCE, concept.toJson());
        result.put(TEXT_VALUE_ATTRIBUTE, value);
        return result;
    }

    public static JSONObject createUidReference(DicomCode concept,
                                                String uid) {
        JSONObject result = new JSONObject();
        result.put(RELATIONSHIP_TYPE, "HAS OBS CONTEXT");
        result.put(VALUE_TYPE, "UIDREF");
        result.append(CONCEPT_NAME_CODE_SEQUENCE, concept.toJson());
        result.put(UID_ATTRIBUTE, uid);
        return result;
    }

    public static JSONObject createNumericValue(DicomCode concept,
                                                DicomCode unit,
                                                double value) {
        String ds = String.valueOf(value);
        if (ds.length() > 16) {
            ds = ds.substring(0, 16);  // The DS VR must have less than 16 characters
        }

        JSONObject result = new JSONObject();
        result.put(RELATIONSHIP_TYPE, "CONTAINS");
        result.put(VALUE_TYPE, "NUM");
        result.append(CONCEPT_NAME_CODE_SEQUENCE, concept.toJson());
        result.append(MEASURED_VALUE_SEQUENCE, new JSONObject().
                append(MEASUREMENT_UNITS_CODE_SEQUENCE, unit.toJson()).
                put(FLOATING_POINT_VALUE, String.valueOf(value)).
                put(NUMERIC_VALUE, ds));
        return result;
    }

    public static JSONObject createSelectedFromImage(String sopClassUid,
                                                     String sopInstanceUid) {
        JSONObject result = new JSONObject();
        result.put(RELATIONSHIP_TYPE, "SELECTED FROM");
        result.put(VALUE_TYPE, "IMAGE");
        result.append(REFERENCED_SOP_SEQUENCE, new JSONObject().
                put(REFERENCED_SOP_CLASS_UID, sopClassUid).
                put(REFERENCED_SOP_INSTANCE_UID, sopInstanceUid));
        result.append(CONCEPT_NAME_CODE_SEQUENCE, new DicomCode("111040", "DCM", "Original Source").toJson());
        return result;
    }

    public static JSONObject createPolyline2D(DicomCode concept,
                                              String sopClassUid,
                                              String sopInstanceUid,
                                              Point2D vertices[]) {
        String polyline = new String();
        for (int i = 0; i < vertices.length; i++) {
            if (!polyline.isEmpty()) {
                polyline = polyline + "\\";
            }
            polyline += String.valueOf(vertices[i].getX()) + "\\" + String.valueOf(vertices[i].getY());
        }

        JSONObject result = new JSONObject();
        result.put(RELATIONSHIP_TYPE, "CONTAINS");
        result.put(VALUE_TYPE, "SCOORD");
        result.append(CONCEPT_NAME_CODE_SEQUENCE, concept.toJson());
        result.append(CONTENT_SEQUENCE, createSelectedFromImage(sopClassUid, sopInstanceUid));
        result.put(GRAPHIC_DATA, polyline);  // WARNING: This necessitates Orthanc > 1.12.4: https://orthanc.uclouvain.be/hg/orthanc/rev/dedbf019a707
        result.put(GRAPHIC_TYPE, "POLYLINE");
        return result;
    }

    public static JSONObject createRectangle(DicomCode concept,
                                             String sopClassUid,
                                             String sopInstanceUid,
                                             Rectangle rectangle,
                                             double aspectRatio) {
        Point2D vertices[] = new Point2D[5];
        vertices[0] = new Point2D(rectangle.getX1() * aspectRatio, rectangle.getY1() * aspectRatio);
        vertices[1] = new Point2D(rectangle.getX2() * aspectRatio, rectangle.getY1() * aspectRatio);
        vertices[2] = new Point2D(rectangle.getX2() * aspectRatio, rectangle.getY2() * aspectRatio);
        vertices[3] = new Point2D(rectangle.getX1() * aspectRatio, rectangle.getY2() * aspectRatio);
        vertices[4] = new Point2D(rectangle.getX1() * aspectRatio, rectangle.getY1() * aspectRatio);
        return createPolyline2D(concept, sopClassUid, sopInstanceUid, vertices);
    }
}
