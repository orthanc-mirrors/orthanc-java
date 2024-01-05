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
 * The origin of a DICOM instance that has been received by Orthanc.
 **/
public enum InstanceOrigin {
    /**
     * Unknown origin
     **/
    UNKNOWN(1),
    /**
     * Instance received through DICOM protocol
     **/
    DICOM_PROTOCOL(2),
    /**
     * Instance received through REST API of Orthanc
     **/
    REST_API(3),
    /**
     * Instance added to Orthanc by a plugin
     **/
    PLUGIN(4),
    /**
     * Instance added to Orthanc by a Lua script
     **/
    LUA(5),
    /**
     * Instance received through WebDAV (new in 1.8.0)
     **/
    WEB_DAV(6);

    private int value;

    private InstanceOrigin(int value) {
        this.value = value;
    }

    /**
     * Return the enumeration value that corresponds to an integer value of interest.
     * @param value The integer value.
     * @return The enumeration value.
     **/
    protected static InstanceOrigin getInstance(int value) {
        if (value == 1) {
            return UNKNOWN;
        }
        if (value == 2) {
            return DICOM_PROTOCOL;
        }
        if (value == 3) {
            return REST_API;
        }
        if (value == 4) {
            return PLUGIN;
        }
        if (value == 5) {
            return LUA;
        }
        if (value == 6) {
            return WEB_DAV;
        }

        throw new IllegalArgumentException("Value out of range for enumeration InstanceOrigin: " + value);
    }

    /**
     * Get the integer value corresponding to this enumeration value.
     * @return The integer value.
     **/
    public int getValue() {
        return value;
    }
}
