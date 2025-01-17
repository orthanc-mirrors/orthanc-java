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


import org.hl7.fhir.r5.model.CodeableConcept;
import org.hl7.fhir.r5.model.Reference;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Toolbox {
    public static String TAG_PATIENT_NAME = "0010,0010";
    public static String TAG_PATIENT_ID = "0010,0020";
    public static String TAG_PATIENT_BIRTH_DATE = "0010,0030";
    public static String TAG_PATIENT_SEX = "0010,0040";
    public static String TAG_STUDY_INSTANCE_UID = "0020,000d";
    public static String TAG_STUDY_DATE = "0008,0020";
    public static String TAG_SERIES_DESCRIPTION = "0008,103e";
    public static String TAG_SERIES_INSTANCE_UID = "0020,000e";
    public static String TAG_MODALITY = "0008,0060";
    public static String TAG_SERIES_NUMBER = "0020,0011";
    public static String TAG_SOP_INSTANCE_UID = "0008,0018";
    public static String TAG_INSTANCE_NUMBER = "0020,0013";
    public static String TAG_STUDY_DESCRIPTION = "0008,1030";

    public static Date parseDicomDate(String date) {
        Pattern pattern = Pattern.compile("^([0-9]{4})([0-9]{2})([0-9]{2})$");
        Matcher matcher = pattern.matcher(date);
        if (matcher.matches()) {
            GregorianCalendar calendar = new GregorianCalendar(
                    Integer.parseInt(matcher.group(1)),
                    Integer.parseInt(matcher.group(2)) - 1,  // Month is 1-based in DICOM
                    Integer.parseInt(matcher.group(3)));
            return calendar.getTime();
        } else {
            throw new IllegalArgumentException("Badly formatted DICOM date: " + date);
        }
    }

    public static Date parseFhirDate(String date) {
        Pattern pattern = Pattern.compile("^([0-9]{4})-([0-9]{2})-([0-9]{2})$");
        Matcher matcher = pattern.matcher(date);
        if (matcher.matches()) {
            Calendar c = Calendar.getInstance();
            c.set(Integer.parseInt(matcher.group(1)),
                    Integer.parseInt(matcher.group(2)) - 1,  // Month is 1-based in FHIR
                    Integer.parseInt(matcher.group(3)));
            return c.getTime();
        } else {
            throw new IllegalArgumentException("Badly formatted FHIR date: " + date);
        }
    }

    public static String formatDicomDate(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return String.format("%04d%02d%02d", calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,  // Month is 1-based in DICOM
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    public static String formatFhirDate(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return String.format("%04d-%02d-%02d", calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,  // Month is 1-based in FHIR
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    public static CodeableConcept createCodeableConcept(String system,
                                                        String code) {
        CodeableConcept codeable = new CodeableConcept();
        codeable.addCoding();
        codeable.getCoding().get(0).setSystem(system);
        codeable.getCoding().get(0).setCode(code);
        return codeable;
    }

    public static CodeableConcept createDicomCodeableConcept(String code) {
        return createCodeableConcept("http://dicom.nema.org/resources/ontology/DCM", code);
    }

    public static Reference createLocalReference(String type,
                                                 String id) {
        Reference reference = new Reference();
        reference.setReference(type + "/" + id);
        return reference;
    }
}
