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


/**
 * The possible status for one single step of a job.
 **/
public enum JobStepStatus {
    /**
     * The job has successfully executed all its steps
     **/
    SUCCESS(1),
    /**
     * The job has failed while executing this step
     **/
    FAILURE(2),
    /**
     * The job has still data to process after this step
     **/
    CONTINUE(3);

    private int value;

    private JobStepStatus(int value) {
        this.value = value;
    }

    /**
     * Return the enumeration value that corresponds to an integer value of interest.
     * @param value The integer value.
     * @return The enumeration value.
     **/
    protected static JobStepStatus getInstance(int value) {
        if (value == 1) {
            return SUCCESS;
        }
        if (value == 2) {
            return FAILURE;
        }
        if (value == 3) {
            return CONTINUE;
        }

        throw new IllegalArgumentException("Value out of range for enumeration JobStepStatus: " + value);
    }

    /**
     * Get the integer value corresponding to this enumeration value.
     * @return The integer value.
     **/
    public int getValue() {
        return value;
    }
}
