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
 * Answers to a DICOM C-FIND query
 **/
public class FindAnswers {
    private long self;

    /**
     * Construct a Java object wrapping a C object that is managed by Orthanc.
     * @param self Pointer to the C object.
     **/
    protected FindAnswers(long self) {
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
     * Add one answer to some C-Find request.
     * 
     * This function adds one answer (encoded as a DICOM file) to the set of answers
     * corresponding to some C-Find SCP request that is not related to modality
     * worklists.
     * 
     * @param dicom The answer to be added, encoded as a DICOM file.
     **/
    public void findAddAnswer(
        byte[] dicom) {
        NativeSDK.OrthancPluginFindAddAnswer(self, dicom);
    }

    /**
     * Mark the set of C-Find answers as incomplete.
     * 
     * This function marks as incomplete the set of answers corresponding to some
     * C-Find SCP request that is not related to modality worklists. This must be used
     * if canceling the handling of a request when too many answers are to be returned.
     **/
    public void findMarkIncomplete() {
        NativeSDK.OrthancPluginFindMarkIncomplete(self);
    }

}
