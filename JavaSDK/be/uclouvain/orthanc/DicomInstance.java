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
 * DICOM instance managed by the Orthanc core
 **/
public class DicomInstance {
    private long self;

    /**
     * Construct a Java object wrapping a C object that is managed by Orthanc.
     * @param self Pointer to the C object.
     **/
    protected DicomInstance(long self) {
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
            NativeSDK.OrthancPluginFreeDicomInstance(self);
            self = 0;
        }
    }

    /**
     * Parse a DICOM instance.
     * 
     * This function parses a memory buffer that contains a DICOM file. The function
     * returns a new pointer to a data structure that is managed by the Orthanc core.
     * 
     * @param buffer The memory buffer containing the DICOM instance.
     * @return The newly constructed object.
     **/
    public static DicomInstance createDicomInstance(
        byte[] buffer) {
        return new DicomInstance(NativeSDK.OrthancPluginCreateDicomInstance(buffer));
    }

    /**
     * Parse and transcode a DICOM instance.
     * 
     * This function parses a memory buffer that contains a DICOM file, then transcodes
     * it to the given transfer syntax. The function returns a new pointer to a data
     * structure that is managed by the Orthanc core.
     * 
     * @param buffer The memory buffer containing the DICOM instance.
     * @param transferSyntax The transfer syntax UID for the transcoding.
     * @return The newly constructed object.
     **/
    public static DicomInstance transcodeDicomInstance(
        byte[] buffer,
        String transferSyntax) {
        return new DicomInstance(NativeSDK.OrthancPluginTranscodeDicomInstance(buffer, transferSyntax));
    }


    /**
     * Get the AET of a DICOM instance.
     * 
     * This function returns the Application Entity Title (AET) of the DICOM modality
     * from which a DICOM instance originates.
     * 
     * @return The resulting string.
     **/
    public String getInstanceRemoteAet() {
        return NativeSDK.OrthancPluginGetInstanceRemoteAet(self);
    }

    /**
     * Get the size of a DICOM file.
     * 
     * This function returns the number of bytes of the given DICOM instance.
     * 
     * @return The size of the file, -1 in case of error.
     **/
    public long getInstanceSize() {
        return NativeSDK.OrthancPluginGetInstanceSize(self);
    }

    /**
     * Get the DICOM tag hierarchy as a JSON file.
     * 
     * This function returns a pointer to a newly created string containing a JSON
     * file. This JSON file encodes the tag hierarchy of the given DICOM instance.
     * 
     * @return The resulting string.
     **/
    public String getInstanceJson() {
        return NativeSDK.OrthancPluginGetInstanceJson(self);
    }

    /**
     * Get the DICOM tag hierarchy as a JSON file (with simplification).
     * 
     * This function returns a pointer to a newly created string containing a JSON
     * file. This JSON file encodes the tag hierarchy of the given DICOM instance. In
     * contrast with ::OrthancPluginGetInstanceJson(), the returned JSON file is in its
     * simplified version.
     * 
     * @return The resulting string.
     **/
    public String getInstanceSimplifiedJson() {
        return NativeSDK.OrthancPluginGetInstanceSimplifiedJson(self);
    }

    /**
     * Check whether a DICOM instance is associated with some metadata.
     * 
     * This function checks whether the DICOM instance of interest is associated with
     * some metadata. As of Orthanc 0.8.1, in the callbacks registered by
     * ::OrthancPluginRegisterOnStoredInstanceCallback(), the only possibly available
     * metadata are "ReceptionDate", "RemoteAET" and "IndexInSeries".
     * 
     * @param metadata The metadata of interest.
     * @return 1 if the metadata is present, 0 if it is absent, -1 in case of error.
     **/
    public int hasInstanceMetadata(
        String metadata) {
        return NativeSDK.OrthancPluginHasInstanceMetadata(self, metadata);
    }

    /**
     * Get the value of some metadata associated with a given DICOM instance.
     * 
     * This functions returns the value of some metadata that is associated with the
     * DICOM instance of interest. Before calling this function, the existence of the
     * metadata must have been checked with ::OrthancPluginHasInstanceMetadata().
     * 
     * @param metadata The metadata of interest.
     * @return The resulting string.
     **/
    public String getInstanceMetadata(
        String metadata) {
        return NativeSDK.OrthancPluginGetInstanceMetadata(self, metadata);
    }

    /**
     * Get the origin of a DICOM file.
     * 
     * This function returns the origin of a DICOM instance that has been received by
     * Orthanc.
     * 
     * @return The origin of the instance.
     **/
    public InstanceOrigin getInstanceOrigin() {
        return InstanceOrigin.getInstance(NativeSDK.OrthancPluginGetInstanceOrigin(self));
    }

    /**
     * Get the transfer syntax of a DICOM file.
     * 
     * This function returns a pointer to a newly created string that contains the
     * transfer syntax UID of the DICOM instance. The empty string might be returned if
     * this information is unknown.
     * 
     * @return The resulting string.
     **/
    public String getInstanceTransferSyntaxUid() {
        return NativeSDK.OrthancPluginGetInstanceTransferSyntaxUid(self);
    }

    /**
     * Check whether the DICOM file has pixel data.
     * 
     * This function returns a Boolean value indicating whether the DICOM instance
     * contains the pixel data (7FE0,0010) tag.
     * 
     * @return "1" if the DICOM instance contains pixel data, or "0" if the tag is
     * missing, or "-1" in the case of an error.
     **/
    public int hasInstancePixelData() {
        return NativeSDK.OrthancPluginHasInstancePixelData(self);
    }

    /**
     * Get the number of frames in a DICOM instance.
     * 
     * This function returns the number of frames that are part of a DICOM image
     * managed by the Orthanc core.
     * 
     * @return The number of frames (will be zero in the case of an error).
     **/
    public int getInstanceFramesCount() {
        return NativeSDK.OrthancPluginGetInstanceFramesCount(self);
    }

    /**
     * Get the raw content of a frame in a DICOM instance.
     * 
     * This function returns a memory buffer containing the raw content of a frame in a
     * DICOM instance that is managed by the Orthanc core. This is notably useful for
     * compressed transfer syntaxes, as it gives access to the embedded files (such as
     * JPEG, JPEG-LS or JPEG2k). The Orthanc core transparently reassembles the
     * fragments to extract the raw frame.
     * 
     * @param frameIndex The index of the frame of interest.
     * @return The resulting memory buffer.
     **/
    public byte[] getInstanceRawFrame(
        int frameIndex) {
        return NativeSDK.OrthancPluginGetInstanceRawFrame(self, frameIndex);
    }

    /**
     * Decode one frame from a DICOM instance.
     * 
     * This function decodes one frame of a DICOM image that is managed by the Orthanc
     * core.
     * 
     * @param frameIndex The index of the frame of interest.
     * @return The newly constructed object.
     **/
    public Image getInstanceDecodedFrame(
        int frameIndex) {
        return new Image(NativeSDK.OrthancPluginGetInstanceDecodedFrame(self, frameIndex));
    }

    /**
     * Writes a DICOM instance to a memory buffer.
     * 
     * This function returns a memory buffer containing the serialization of a DICOM
     * instance that is managed by the Orthanc core.
     * 
     * @return The resulting memory buffer.
     **/
    public byte[] serializeDicomInstance() {
        return NativeSDK.OrthancPluginSerializeDicomInstance(self);
    }

    /**
     * Format a DICOM memory buffer as a JSON string.
     * 
     * This function takes as DICOM instance managed by the Orthanc core, and outputs a
     * JSON string representing the tags of this DICOM file.
     * 
     * @param format The output format.
     * @param flags Flags governing the output.
     * @param maxStringLength The maximum length of a field. Too long fields will be
     * output as "null". The 0 value means no maximum length.
     * @return The resulting string.
     **/
    public String getInstanceAdvancedJson(
        DicomToJsonFormat format,
        DicomToJsonFlags flags,
        int maxStringLength) {
        return NativeSDK.OrthancPluginGetInstanceAdvancedJson(self, format.getValue(), flags.getValue(), maxStringLength);
    }

}
