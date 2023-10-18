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
 * Matcher for DICOM C-FIND query
 **/
public class FindMatcher {
    private long self;

    /**
     * Construct a Java object wrapping a C object that is managed by Orthanc.
     * @param self Pointer to the C object.
     **/
    protected FindMatcher(long self) {
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

    @Override
    protected void finalize() throws Throwable {
        dispose();
        super.finalize();
    }

    /**
     * Manually deallocate the C object that is associated with this Java wrapper.
     *
     * This method can be used to immediately deallocate the C object,
     * instead of waiting for the garbage collector to dispose the Java wrapper.
     **/
    public void dispose() {
        if (self != 0) {
            NativeSDK.OrthancPluginFreeFindMatcher(self);
            self = 0;
        }
    }

    /**
     * Create a C-Find matcher.
     * 
     * This function creates a "matcher" object that can be used to check whether a
     * DICOM instance matches a C-Find query. The C-Find query must be expressed as a
     * DICOM buffer.
     * 
     * @param query The C-Find DICOM query.
     * @return The newly constructed object.
     **/
    public static FindMatcher createFindMatcher(
        byte[] query) {
        return new FindMatcher(NativeSDK.OrthancPluginCreateFindMatcher(query));
    }


    /**
     * Test whether a DICOM instance matches a C-Find query.
     * 
     * This function checks whether one DICOM instance matches C-Find matcher that was
     * previously allocated using OrthancPluginCreateFindMatcher().
     * 
     * @param dicom The DICOM instance to be matched.
     * @return 1 if the DICOM instance matches the query, 0 otherwise.
     **/
    public int findMatcherIsMatch(
        byte[] dicom) {
        return NativeSDK.OrthancPluginFindMatcherIsMatch(self, dicom);
    }

}
