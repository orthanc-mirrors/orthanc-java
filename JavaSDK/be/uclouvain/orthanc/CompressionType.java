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
 * The compression algorithms that are supported by the Orthanc core.
 **/
public enum CompressionType {
    /**
     * Standard zlib compression
     **/
    ZLIB(0),
    /**
     * zlib, prefixed with uncompressed size (uint64_t)
     **/
    ZLIB_WITH_SIZE(1),
    /**
     * Standard gzip compression
     **/
    GZIP(2),
    /**
     * gzip, prefixed with uncompressed size (uint64_t)
     **/
    GZIP_WITH_SIZE(3);

    private int value;

    private CompressionType(int value) {
        this.value = value;
    }

    /**
     * Return the enumeration value that corresponds to an integer value of interest.
     * @param value The integer value.
     * @return The enumeration value.
     **/
    protected static CompressionType getInstance(int value) {
        if (value == 0) {
            return ZLIB;
        }
        if (value == 1) {
            return ZLIB_WITH_SIZE;
        }
        if (value == 2) {
            return GZIP;
        }
        if (value == 3) {
            return GZIP_WITH_SIZE;
        }

        throw new IllegalArgumentException("Value out of range for enumeration CompressionType: " + value);
    }

    /**
     * Get the integer value corresponding to this enumeration value.
     * @return The integer value.
     **/
    public int getValue() {
        return value;
    }
}
