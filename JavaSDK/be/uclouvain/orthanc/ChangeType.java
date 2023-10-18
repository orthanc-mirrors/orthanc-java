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
 * The supported types of changes that can be signaled to the change callback.
 **/
public enum ChangeType {
    /**
     * Series is now complete
     **/
    COMPLETED_SERIES(0),
    /**
     * Deleted resource
     **/
    DELETED(1),
    /**
     * A new instance was added to this resource
     **/
    NEW_CHILD_INSTANCE(2),
    /**
     * New instance received
     **/
    NEW_INSTANCE(3),
    /**
     * New patient created
     **/
    NEW_PATIENT(4),
    /**
     * New series created
     **/
    NEW_SERIES(5),
    /**
     * New study created
     **/
    NEW_STUDY(6),
    /**
     * Timeout: No new instance in this patient
     **/
    STABLE_PATIENT(7),
    /**
     * Timeout: No new instance in this series
     **/
    STABLE_SERIES(8),
    /**
     * Timeout: No new instance in this study
     **/
    STABLE_STUDY(9),
    /**
     * Orthanc has started
     **/
    ORTHANC_STARTED(10),
    /**
     * Orthanc is stopping
     **/
    ORTHANC_STOPPED(11),
    /**
     * Some user-defined attachment has changed for this resource
     **/
    UPDATED_ATTACHMENT(12),
    /**
     * Some user-defined metadata has changed for this resource
     **/
    UPDATED_METADATA(13),
    /**
     * The list of Orthanc peers has changed
     **/
    UPDATED_PEERS(14),
    /**
     * The list of DICOM modalities has changed
     **/
    UPDATED_MODALITIES(15),
    /**
     * New Job submitted
     **/
    JOB_SUBMITTED(16),
    /**
     * A Job has completed successfully
     **/
    JOB_SUCCESS(17),
    /**
     * A Job has failed
     **/
    JOB_FAILURE(18);

    private int value;

    private ChangeType(int value) {
        this.value = value;
    }

    /**
     * Return the enumeration value that corresponds to an integer value of interest.
     * @param value The integer value.
     * @return The enumeration value.
     **/
    protected static ChangeType getInstance(int value) {
        if (value == 0) {
            return COMPLETED_SERIES;
        }
        if (value == 1) {
            return DELETED;
        }
        if (value == 2) {
            return NEW_CHILD_INSTANCE;
        }
        if (value == 3) {
            return NEW_INSTANCE;
        }
        if (value == 4) {
            return NEW_PATIENT;
        }
        if (value == 5) {
            return NEW_SERIES;
        }
        if (value == 6) {
            return NEW_STUDY;
        }
        if (value == 7) {
            return STABLE_PATIENT;
        }
        if (value == 8) {
            return STABLE_SERIES;
        }
        if (value == 9) {
            return STABLE_STUDY;
        }
        if (value == 10) {
            return ORTHANC_STARTED;
        }
        if (value == 11) {
            return ORTHANC_STOPPED;
        }
        if (value == 12) {
            return UPDATED_ATTACHMENT;
        }
        if (value == 13) {
            return UPDATED_METADATA;
        }
        if (value == 14) {
            return UPDATED_PEERS;
        }
        if (value == 15) {
            return UPDATED_MODALITIES;
        }
        if (value == 16) {
            return JOB_SUBMITTED;
        }
        if (value == 17) {
            return JOB_SUCCESS;
        }
        if (value == 18) {
            return JOB_FAILURE;
        }

        throw new IllegalArgumentException("Value out of range for enumeration ChangeType: " + value);
    }

    /**
     * Get the integer value corresponding to this enumeration value.
     * @return The integer value.
     **/
    public int getValue() {
        return value;
    }
}
