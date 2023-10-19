package be.uclouvain.orthanc;

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
 * along with this program. If not, see http://www.gnu.org/licenses/.
 **/


// WARNING: Auto-generated file. Do not modify it by hand.

/**
 * Flags to customize a DICOM-to-JSON conversion. By default, binary tags are
 * formatted using Data URI scheme.
 **/
public enum DicomToJsonFlags {
    /**
     * Default formatting
     **/
    NONE(0),
    /**
     * Include the binary tags
     **/
    INCLUDE_BINARY(1),
    /**
     * Include the private tags
     **/
    INCLUDE_PRIVATE_TAGS(2),
    /**
     * Include the tags unknown by the dictionary
     **/
    INCLUDE_UNKNOWN_TAGS(4),
    /**
     * Include the pixel data
     **/
    INCLUDE_PIXEL_DATA(8),
    /**
     * Output binary tags as-is, dropping non-ASCII
     **/
    CONVERT_BINARY_TO_ASCII(16),
    /**
     * Signal binary tags as null values
     **/
    CONVERT_BINARY_TO_NULL(32),
    /**
     * Stop processing after pixel data (new in 1.9.1)
     **/
    STOP_AFTER_PIXEL_DATA(64),
    /**
     * Skip tags whose element is zero (new in 1.9.1)
     **/
    SKIP_GROUP_LENGTHS(128);

    private int value;

    private DicomToJsonFlags(int value) {
        this.value = value;
    }

    /**
     * Return the enumeration value that corresponds to an integer value of interest.
     * @param value The integer value.
     * @return The enumeration value.
     **/
    protected static DicomToJsonFlags getInstance(int value) {
        if (value == 0) {
            return NONE;
        }
        if (value == 1) {
            return INCLUDE_BINARY;
        }
        if (value == 2) {
            return INCLUDE_PRIVATE_TAGS;
        }
        if (value == 4) {
            return INCLUDE_UNKNOWN_TAGS;
        }
        if (value == 8) {
            return INCLUDE_PIXEL_DATA;
        }
        if (value == 16) {
            return CONVERT_BINARY_TO_ASCII;
        }
        if (value == 32) {
            return CONVERT_BINARY_TO_NULL;
        }
        if (value == 64) {
            return STOP_AFTER_PIXEL_DATA;
        }
        if (value == 128) {
            return SKIP_GROUP_LENGTHS;
        }

        throw new IllegalArgumentException("Value out of range for enumeration DicomToJsonFlags: " + value);
    }

    /**
     * Get the integer value corresponding to this enumeration value.
     * @return The integer value.
     **/
    public int getValue() {
        return value;
    }
}
