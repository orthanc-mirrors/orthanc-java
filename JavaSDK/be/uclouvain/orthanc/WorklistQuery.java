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
 * DICOM C-FIND worklist query
 **/
public class WorklistQuery {
    private long self;

    /**
     * Construct a Java object wrapping a C object that is managed by Orthanc.
     * @param self Pointer to the C object.
     **/
    protected WorklistQuery(long self) {
        if (self == 0) {
            throw new IllegalArgumentException("Null pointer");
        } else {
            this.self = self;
        }
    }

    /**
     * Return the C object that is associated with this Java wrapper.
     * @return Pointer to the C object.
     **/
    protected long getSelf() {
        return self;
    }



    /**
     * Test whether a worklist matches the query.
     * 
     * This function checks whether one worklist (encoded as a DICOM file) matches the
     * C-Find SCP query against modality worklists. This function must be called before
     * adding the worklist as an answer through OrthancPluginWorklistAddAnswer().
     * 
     * @param dicom The worklist to answer, encoded as a DICOM file.
     * @return 1 if the worklist matches the query, 0 otherwise.
     **/
    public int worklistIsMatch(
        byte[] dicom) {
        return NativeSDK.OrthancPluginWorklistIsMatch(self, dicom);
    }

    /**
     * Retrieve the worklist query as a DICOM file.
     * 
     * This function retrieves the DICOM file that underlies a C-Find SCP query against
     * modality worklists.
     * 
     * @return The resulting memory buffer.
     **/
    public byte[] worklistGetDicomQuery() {
        return NativeSDK.OrthancPluginWorklistGetDicomQuery(self);
    }

}
