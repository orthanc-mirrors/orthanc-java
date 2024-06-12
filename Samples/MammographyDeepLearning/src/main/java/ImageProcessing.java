/**
 * SPDX-FileCopyrightText: 2023-2024 Sebastien Jodogne, UCLouvain, Belgium
 * SPDX-License-Identifier: GPL-3.0-or-later
 **/

/**
 * Java plugin for Orthanc
 * Copyright (C) 2023-2024 Sebastien Jodogne, UCLouvain, Belgium
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
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 **/


import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.types.DataType;
import ai.djl.ndarray.types.Shape;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;

public class ImageProcessing {
    static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, originalImage.getType());
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        return resizedImage;
    }

    static NDArray imageToTensor(NDManager manager,
                                 BufferedImage image) {
        if (image.getType() != image.TYPE_USHORT_GRAY /* 16 bpp */ &&
                image.getType() != image.TYPE_BYTE_GRAY /* 8 bpp */) {
            throw new IllegalArgumentException();
        }

        float pixels[] = new float[image.getHeight() * image.getWidth()];

        DataBuffer db = image.getData().getDataBuffer();

        int pos = 0;
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++, pos++) {
                pixels[pos] = db.getElemFloat(pos);
            }
        }

        return manager.create(pixels, new Shape(1, image.getHeight(), image.getWidth()));
    }

    static NDArray standardize(NDArray image) {
        if (image.getDataType() != DataType.FLOAT32 ||
                image.getShape().dimension() != 3 ||
                (image.getShape().get(0) != 1 &&
                        image.getShape().get(0) != 3)) {
            throw new IllegalArgumentException();
        }

        // Standardize the image to zero mean and 1 standard deviation
        NDArray doubleImage = image.toType(DataType.FLOAT64, false);
        NDArray squared = doubleImage.mul(doubleImage);

        double asum = doubleImage.sum().getDouble();
        double asumOfSquares = squared.sum().getDouble();
        double n = doubleImage.getShape().size();
        double amean = asum / n;
        double astd = Math.sqrt((asumOfSquares - asum * asum / n) / n);

        return image.add(-amean).div(astd);
    }
}
