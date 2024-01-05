package be.uclouvain.orthanc;

/**
 * SPDX-FileCopyrightText: 2023-2024 Sebastien Jodogne, UCLouvain, Belgium
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

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
 * along with this program. If not, see http://www.gnu.org/licenses/.
 **/


// WARNING: Auto-generated file. Do not modify it by hand.

/**
 * The available modes to export a binary DICOM tag into a DICOMweb JSON or XML
 * document.
 **/
public enum DicomWebBinaryMode {
    /**
     * Don't include binary tags
     **/
    IGNORE(0),
    /**
     * Inline encoding using Base64
     **/
    INLINE_BINARY(1),
    /**
     * Use a bulk data URI field
     **/
    BULK_DATA_URI(2);

    private int value;

    private DicomWebBinaryMode(int value) {
        this.value = value;
    }

    /**
     * Return the enumeration value that corresponds to an integer value of interest.
     * @param value The integer value.
     * @return The enumeration value.
     **/
    protected static DicomWebBinaryMode getInstance(int value) {
        if (value == 0) {
            return IGNORE;
        }
        if (value == 1) {
            return INLINE_BINARY;
        }
        if (value == 2) {
            return BULK_DATA_URI;
        }

        throw new IllegalArgumentException("Value out of range for enumeration DicomWebBinaryMode: " + value);
    }

    /**
     * Get the integer value corresponding to this enumeration value.
     * @return The integer value.
     **/
    public int getValue() {
        return value;
    }
}
