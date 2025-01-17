package be.uclouvain.orthanc;

/**
 * SPDX-FileCopyrightText: 2023-2025 Sebastien Jodogne, ICTEAM UCLouvain, Belgium
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

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
 * along with this program. If not, see http://www.gnu.org/licenses/.
 **/


// WARNING: Auto-generated file. Do not modify it by hand.

/**
 * Flags to the creation of a DICOM file.
 **/
public enum CreateDicomFlags {
    /**
     * Default mode
     **/
    NONE(0),
    /**
     * Decode fields encoded using data URI scheme
     **/
    DECODE_DATA_URI_SCHEME(1),
    /**
     * Automatically generate DICOM identifiers
     **/
    GENERATE_IDENTIFIERS(2);

    private int value;

    private CreateDicomFlags(int value) {
        this.value = value;
    }

    /**
     * Return the enumeration value that corresponds to an integer value of interest.
     * @param value The integer value.
     * @return The enumeration value.
     **/
    protected static CreateDicomFlags getInstance(int value) {
        if (value == 0) {
            return NONE;
        }
        if (value == 1) {
            return DECODE_DATA_URI_SCHEME;
        }
        if (value == 2) {
            return GENERATE_IDENTIFIERS;
        }

        throw new IllegalArgumentException("Value out of range for enumeration CreateDicomFlags: " + value);
    }

    /**
     * Get the integer value corresponding to this enumeration value.
     * @return The integer value.
     **/
    public int getValue() {
        return value;
    }
}
