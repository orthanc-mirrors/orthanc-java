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
 * Explains why the job should stop and release the resources it has allocated.
 * This is especially important to disambiguate between the "paused" condition and
 * the "final" conditions (success, failure, or canceled).
 **/
public enum JobStopReason {
    /**
     * The job has succeeded
     **/
    SUCCESS(1),
    /**
     * The job was paused, and will be resumed later
     **/
    PAUSED(2),
    /**
     * The job has failed, and might be resubmitted later
     **/
    FAILURE(3),
    /**
     * The job was canceled, and might be resubmitted later
     **/
    CANCELED(4);

    private int value;

    private JobStopReason(int value) {
        this.value = value;
    }

    /**
     * Return the enumeration value that corresponds to an integer value of interest.
     * @param value The integer value.
     * @return The enumeration value.
     **/
    protected static JobStopReason getInstance(int value) {
        if (value == 1) {
            return SUCCESS;
        }
        if (value == 2) {
            return PAUSED;
        }
        if (value == 3) {
            return FAILURE;
        }
        if (value == 4) {
            return CANCELED;
        }

        throw new IllegalArgumentException("Value out of range for enumeration JobStopReason: " + value);
    }

    /**
     * Get the integer value corresponding to this enumeration value.
     * @return The integer value.
     **/
    public int getValue() {
        return value;
    }
}
