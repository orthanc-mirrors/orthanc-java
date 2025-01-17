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
 * The constraints on the DICOM identifiers that must be supported by the database
 * plugins.
 **/
public enum IdentifierConstraint {
    /**
     * Equal
     **/
    EQUAL(1),
    /**
     * Less or equal
     **/
    SMALLER_OR_EQUAL(2),
    /**
     * More or equal
     **/
    GREATER_OR_EQUAL(3),
    /**
     * Case-sensitive wildcard matching (with * and ?)
     **/
    WILDCARD(4);

    private int value;

    private IdentifierConstraint(int value) {
        this.value = value;
    }

    /**
     * Return the enumeration value that corresponds to an integer value of interest.
     * @param value The integer value.
     * @return The enumeration value.
     **/
    protected static IdentifierConstraint getInstance(int value) {
        if (value == 1) {
            return EQUAL;
        }
        if (value == 2) {
            return SMALLER_OR_EQUAL;
        }
        if (value == 3) {
            return GREATER_OR_EQUAL;
        }
        if (value == 4) {
            return WILDCARD;
        }

        throw new IllegalArgumentException("Value out of range for enumeration IdentifierConstraint: " + value);
    }

    /**
     * Get the integer value corresponding to this enumeration value.
     * @return The integer value.
     **/
    public int getValue() {
        return value;
    }
}
