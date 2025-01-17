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
 * 2D image managed by the Orthanc core
 **/
public class Image {
    private long self;

    /**
     * Construct a Java object wrapping a C object that is managed by Orthanc.
     * @param self Pointer to the C object.
     **/
    protected Image(long self) {
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
            NativeSDK.OrthancPluginFreeImage(self);
            self = 0;
        }
    }

    /**
     * Decode a compressed image.
     * 
     * This function decodes a compressed image from a memory buffer.
     * 
     * @param data Pointer to a memory buffer containing the compressed image.
     * @param format The file format of the compressed image.
     * @return The newly constructed object.
     **/
    public static Image uncompressImage(
        byte[] data,
        ImageFormat format) {
        return new Image(NativeSDK.OrthancPluginUncompressImage(data, format.getValue()));
    }

    /**
     * Create an image.
     * 
     * This function creates an image of given size and format.
     * 
     * @param format The format of the pixels.
     * @param width The width of the image.
     * @param height The height of the image.
     * @return The newly constructed object.
     **/
    public static Image createImage(
        PixelFormat format,
        int width,
        int height) {
        return new Image(NativeSDK.OrthancPluginCreateImage(format.getValue(), width, height));
    }

    /**
     * Decode one frame from a DICOM instance.
     * 
     * This function decodes one frame of a DICOM image that is stored in a memory
     * buffer. This function will give the same result as
     * OrthancPluginUncompressImage() for single-frame DICOM images.
     * 
     * @param buffer Pointer to a memory buffer containing the DICOM image.
     * @param frameIndex The index of the frame of interest in a multi-frame image.
     * @return The newly constructed object.
     **/
    public static Image decodeDicomImage(
        byte[] buffer,
        int frameIndex) {
        return new Image(NativeSDK.OrthancPluginDecodeDicomImage(buffer, frameIndex));
    }


    /**
     * Return the pixel format of an image.
     * 
     * This function returns the type of memory layout for the pixels of the given
     * image.
     * 
     * @return The pixel format.
     **/
    public PixelFormat getImagePixelFormat() {
        return PixelFormat.getInstance(NativeSDK.OrthancPluginGetImagePixelFormat(self));
    }

    /**
     * Return the width of an image.
     * 
     * This function returns the width of the given image.
     * 
     * @return The width.
     **/
    public int getImageWidth() {
        return NativeSDK.OrthancPluginGetImageWidth(self);
    }

    /**
     * Return the height of an image.
     * 
     * This function returns the height of the given image.
     * 
     * @return The height.
     **/
    public int getImageHeight() {
        return NativeSDK.OrthancPluginGetImageHeight(self);
    }

    /**
     * Return the pitch of an image.
     * 
     * This function returns the pitch of the given image. The pitch is defined as the
     * number of bytes between 2 successive lines of the image in the memory buffer.
     * 
     * @return The pitch.
     **/
    public int getImagePitch() {
        return NativeSDK.OrthancPluginGetImagePitch(self);
    }

    /**
     * Change the pixel format of an image.
     * 
     * This function creates a new image, changing the memory layout of the pixels.
     * 
     * @param targetFormat The target pixel format.
     * @return The newly constructed object.
     **/
    public Image convertPixelFormat(
        PixelFormat targetFormat) {
        return new Image(NativeSDK.OrthancPluginConvertPixelFormat(self, targetFormat.getValue()));
    }

    /**
     * Draw text on an image.
     * 
     * This function draws some text on some image.
     * 
     * @param fontIndex The index of the font. This value must be less than
     * OrthancPluginGetFontsCount().
     * @param utf8Text The text to be drawn, encoded as an UTF-8 zero-terminated
     * string.
     * @param x The X position of the text over the image.
     * @param y The Y position of the text over the image.
     * @param r The value of the red color channel of the text.
     * @param g The value of the green color channel of the text.
     * @param b The value of the blue color channel of the text.
     **/
    public void drawText(
        int fontIndex,
        String utf8Text,
        int x,
        int y,
        byte r,
        byte g,
        byte b) {
        NativeSDK.OrthancPluginDrawText(self, fontIndex, utf8Text, x, y, r, g, b);
    }

}
