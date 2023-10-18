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
 * The available values for the Failure Reason (0008,1197) during storage
 * commitment.
 * http://dicom.nema.org/medical/dicom/2019e/output/chtml/part03/sect_C.14.html#sect_C.14.1.1
 **/
public enum StorageCommitmentFailureReason {
    /**
     * Success: The DICOM instance is properly stored in the SCP
     **/
    SUCCESS(0),
    /**
     * 0110H: A general failure in processing the operation was encountered
     **/
    PROCESSING_FAILURE(1),
    /**
     * 0112H: One or more of the elements in the Referenced SOP Instance Sequence was
     * not available
     **/
    NO_SUCH_OBJECT_INSTANCE(2),
    /**
     * 0213H: The SCP does not currently have enough resources to store the requested
     * SOP Instance(s)
     **/
    RESOURCE_LIMITATION(3),
    /**
     * 0122H: Storage Commitment has been requested for a SOP Instance with a SOP Class
     * that is not supported by the SCP
     **/
    REFERENCED_SOPCLASS_NOT_SUPPORTED(4),
    /**
     * 0119H: The SOP Class of an element in the Referenced SOP Instance Sequence did
     * not correspond to the SOP class registered for this SOP Instance at the SCP
     **/
    CLASS_INSTANCE_CONFLICT(5),
    /**
     * 0131H: The Transaction UID of the Storage Commitment Request is already in use
     **/
    DUPLICATE_TRANSACTION_UID(6);

    private int value;

    private StorageCommitmentFailureReason(int value) {
        this.value = value;
    }

    /**
     * Return the enumeration value that corresponds to an integer value of interest.
     * @param value The integer value.
     * @return The enumeration value.
     **/
    protected static StorageCommitmentFailureReason getInstance(int value) {
        if (value == 0) {
            return SUCCESS;
        }
        if (value == 1) {
            return PROCESSING_FAILURE;
        }
        if (value == 2) {
            return NO_SUCH_OBJECT_INSTANCE;
        }
        if (value == 3) {
            return RESOURCE_LIMITATION;
        }
        if (value == 4) {
            return REFERENCED_SOPCLASS_NOT_SUPPORTED;
        }
        if (value == 5) {
            return CLASS_INSTANCE_CONFLICT;
        }
        if (value == 6) {
            return DUPLICATE_TRANSACTION_UID;
        }

        throw new IllegalArgumentException("Value out of range for enumeration StorageCommitmentFailureReason: " + value);
    }

    /**
     * Get the integer value corresponding to this enumeration value.
     * @return The integer value.
     **/
    public int getValue() {
        return value;
    }
}
