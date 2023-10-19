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
 * DICOM C-FIND query
 **/
public class FindQuery {
    private long self;

    /**
     * Construct a Java object wrapping a C object that is managed by Orthanc.
     * @param self Pointer to the C object.
     **/
    protected FindQuery(long self) {
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
     * Get the number of tags in a C-Find query.
     * 
     * This function returns the number of tags that are contained in the given C-Find
     * query.
     * 
     * @return The number of tags.
     **/
    public int getFindQuerySize() {
        return NativeSDK.OrthancPluginGetFindQuerySize(self);
    }

    /**
     * Get the symbolic name of one tag in a C-Find query.
     * 
     * This function returns the symbolic name of one DICOM tag in the given C-Find
     * query.
     * 
     * @param index The index of the tag of interest.
     * @return The resulting string.
     **/
    public String getFindQueryTagName(
        int index) {
        return NativeSDK.OrthancPluginGetFindQueryTagName(self, index);
    }

    /**
     * Get the value associated with one tag in a C-Find query.
     * 
     * This function returns the value associated with one tag in the given C-Find
     * query.
     * 
     * @param index The index of the tag of interest.
     * @return The resulting string.
     **/
    public String getFindQueryValue(
        int index) {
        return NativeSDK.OrthancPluginGetFindQueryValue(self, index);
    }

}
