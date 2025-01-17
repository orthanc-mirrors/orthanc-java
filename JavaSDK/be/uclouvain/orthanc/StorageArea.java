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
 * Storage area plugin
 **/
public class StorageArea {
    private long self;

    /**
     * Construct a Java object wrapping a C object that is managed by Orthanc.
     * @param self Pointer to the C object.
     **/
    protected StorageArea(long self) {
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
     * Create a file inside the storage area.
     * 
     * This function creates a new file inside the storage area that is currently used
     * by Orthanc.
     * 
     * @param uuid The identifier of the file to be created.
     * @param content The content to store in the newly created file.
     * @param size The size of the content.
     * @param type The type of the file content.
     **/
    public void storageAreaCreate(
        String uuid,
        byte[] content,
        long size,
        ContentType type) {
        NativeSDK.OrthancPluginStorageAreaCreate(self, uuid, content, size, type.getValue());
    }

    /**
     * Read a file from the storage area.
     * 
     * This function reads the content of a given file from the storage area that is
     * currently used by Orthanc.
     * 
     * @param uuid The identifier of the file to be read.
     * @param type The type of the file content.
     * @return The resulting memory buffer.
     **/
    public byte[] storageAreaRead(
        String uuid,
        ContentType type) {
        return NativeSDK.OrthancPluginStorageAreaRead(self, uuid, type.getValue());
    }

    /**
     * Remove a file from the storage area.
     * 
     * This function removes a given file from the storage area that is currently used
     * by Orthanc.
     * 
     * @param uuid The identifier of the file to be removed.
     * @param type The type of the file content.
     **/
    public void storageAreaRemove(
        String uuid,
        ContentType type) {
        NativeSDK.OrthancPluginStorageAreaRemove(self, uuid, type.getValue());
    }

    /**
     * Reconstruct the main DICOM tags.
     * 
     * This function requests the Orthanc core to reconstruct the main DICOM tags of
     * all the resources of the given type. This function can only be used as a part of
     * the upgrade of a custom database back-end. A database transaction will be
     * automatically setup.
     * 
     * @param level The type of the resources of interest.
     **/
    public void reconstructMainDicomTags(
        ResourceType level) {
        NativeSDK.OrthancPluginReconstructMainDicomTags(self, level.getValue());
    }

}
