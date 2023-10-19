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
 * The memory layout of the pixels of an image.
 **/
public enum PixelFormat {
    /**
     * Graylevel 8bpp image. The image is graylevel. Each pixel is unsigned and stored
     * in one byte.
     **/
    GRAYSCALE8(1),
    /**
     * Graylevel, unsigned 16bpp image. The image is graylevel. Each pixel is unsigned
     * and stored in two bytes.
     **/
    GRAYSCALE16(2),
    /**
     * Graylevel, signed 16bpp image. The image is graylevel. Each pixel is signed and
     * stored in two bytes.
     **/
    SIGNED_GRAYSCALE16(3),
    /**
     * Color image in RGB24 format. This format describes a color image. The pixels are
     * stored in 3 consecutive bytes. The memory layout is RGB.
     **/
    RGB24(4),
    /**
     * Color image in RGBA32 format. This format describes a color image. The pixels
     * are stored in 4 consecutive bytes. The memory layout is RGBA.
     **/
    RGBA32(5),
    /**
     * Unknown pixel format
     **/
    UNKNOWN(6),
    /**
     * Color image in RGB48 format. This format describes a color image. The pixels are
     * stored in 6 consecutive bytes. The memory layout is RRGGBB.
     **/
    RGB48(7),
    /**
     * Graylevel, unsigned 32bpp image. The image is graylevel. Each pixel is unsigned
     * and stored in four bytes.
     **/
    GRAYSCALE32(8),
    /**
     * Graylevel, floating-point 32bpp image. The image is graylevel. Each pixel is
     * floating-point and stored in four bytes.
     **/
    FLOAT32(9),
    /**
     * Color image in BGRA32 format. This format describes a color image. The pixels
     * are stored in 4 consecutive bytes. The memory layout is BGRA.
     **/
    BGRA32(10),
    /**
     * Graylevel, unsigned 64bpp image. The image is graylevel. Each pixel is unsigned
     * and stored in eight bytes.
     **/
    GRAYSCALE64(11);

    private int value;

    private PixelFormat(int value) {
        this.value = value;
    }

    /**
     * Return the enumeration value that corresponds to an integer value of interest.
     * @param value The integer value.
     * @return The enumeration value.
     **/
    protected static PixelFormat getInstance(int value) {
        if (value == 1) {
            return GRAYSCALE8;
        }
        if (value == 2) {
            return GRAYSCALE16;
        }
        if (value == 3) {
            return SIGNED_GRAYSCALE16;
        }
        if (value == 4) {
            return RGB24;
        }
        if (value == 5) {
            return RGBA32;
        }
        if (value == 6) {
            return UNKNOWN;
        }
        if (value == 7) {
            return RGB48;
        }
        if (value == 8) {
            return GRAYSCALE32;
        }
        if (value == 9) {
            return FLOAT32;
        }
        if (value == 10) {
            return BGRA32;
        }
        if (value == 11) {
            return GRAYSCALE64;
        }

        throw new IllegalArgumentException("Value out of range for enumeration PixelFormat: " + value);
    }

    /**
     * Get the integer value corresponding to this enumeration value.
     * @return The integer value.
     **/
    public int getValue() {
        return value;
    }
}
