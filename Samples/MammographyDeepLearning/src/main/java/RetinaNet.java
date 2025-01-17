/**
 * SPDX-FileCopyrightText: 2023-2025 Sebastien Jodogne, ICTEAM UCLouvain, Belgium
 * SPDX-License-Identifier: GPL-3.0-or-later
 **/

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
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 **/


import ai.djl.MalformedModelException;
import ai.djl.inference.Predictor;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import ai.djl.translate.TranslateException;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class RetinaNet {
    private ZooModel model;

    private static double sigmoid(double x) {
        // This corresponds to "torch.sigmoid()", aka. "torch.expit()"
        return 1.0f / (1.0f + Math.exp(-x));
    }

    private static float clamp(float value,
                               float min,
                               float max) {
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        } else {
            return value;
        }
    }

    /**
     * This function corresponds to method "decode_single()" in class "BoxCoder" in:
     * https://github.com/pytorch/vision/blob/main/torchvision/models/detection/_utils.py
     */
    public static Rectangle decodeSingle(float[] rel_codes,
                                         float[] boxes,
                                         int image_shape_width,
                                         int image_shape_height) {
        /**
         * The following constant come from the constructor of "BoxCoder" in:
         * https://github.com/pytorch/vision/blob/main/torchvision/models/detection/_utils.py
         *
         * self, weights: Tuple[float, float, float, float], bbox_xform_clip: float = math.log(1000.0 / 16)
         */
        final float BBOX_XFORM_CLIP = (float) Math.log(1000.0 / 16.0);

        /**
         * The following constants come from the following line in
         * https://github.com/pytorch/vision/blob/main/torchvision/models/detection/retinanet.py
         *
         * self.box_coder = det_utils.BoxCoder(weights=(1.0, 1.0, 1.0, 1.0))
         */
        final float WX = 1.0f;
        final float WY = 1.0f;
        final float WW = 1.0f;
        final float WH = 1.0f;

        final float widths = boxes[2] - boxes[0];
        final float heights = boxes[3] - boxes[1];
        final float ctr_x = boxes[0] + 0.5f * widths;
        final float ctr_y = boxes[1] + 0.5f * heights;

        final float dx = rel_codes[0] / WX;
        final float dy = rel_codes[1] / WY;
        final float dw = Math.min(rel_codes[2] / WW, BBOX_XFORM_CLIP);  // This corresponds to "torch.clamp()"
        final float dh = Math.min(rel_codes[3] / WH, BBOX_XFORM_CLIP);

        final float pred_ctr_x = dx * widths + ctr_x;
        final float pred_ctr_y = dy * heights + ctr_y;
        final float pred_w = (float) (Math.exp(dw) * widths);
        final float pred_h = (float) (Math.exp(dh) * heights);

        final float c_to_c_h = 0.5f * pred_h;
        final float c_to_c_w = 0.5f * pred_w;

        final float pred_boxes1 = pred_ctr_x - c_to_c_w;
        final float pred_boxes2 = pred_ctr_y - c_to_c_h;
        final float pred_boxes3 = pred_ctr_x + c_to_c_w;
        final float pred_boxes4 = pred_ctr_y + c_to_c_h;

        // The calls to clamp() correspond to function "box_ops.clip_boxes_to_image()"
        return new Rectangle(
                clamp(pred_boxes1, 0, image_shape_width),
                clamp(pred_boxes2, 0, image_shape_height),
                clamp(pred_boxes3, 0, image_shape_width),
                clamp(pred_boxes4, 0, image_shape_height));
    }

    public RetinaNet(String path) throws ModelNotFoundException, MalformedModelException, IOException {
        Criteria criteria = Criteria.builder()
                .setTypes(NDList.class, NDList.class)
                .optModelPath(Paths.get(path))
                //.optOption("mapLocation", "true") // this model requires mapLocation for GPU
                //.optTranslator(translator)
                .optProgress(new ProgressBar()).build();

        model = criteria.loadModel();
    }

    public ZooModel getModel() {
        return model;
    }

    public void close() {
        model.close();
    }

    public List<Detection> apply(NDArray image) throws TranslateException {
        if (image.getShape().dimension() != 3 ||
                image.getShape().get(0) != 3) {
            throw new RuntimeException();
        }

        final int imageWidth = (int) image.getShape().get(2);
        final int imageHeight = (int) image.getShape().get(1);

        Predictor<NDList, NDList> predictor = model.newPredictor();

        NDList output = predictor.predict(new NDList(image));

        if (output.size() <= 3) {
            throw new IllegalArgumentException();
        }

        NDArray logits_per_image = output.get(0);
        NDArray box_regression_per_image = output.get(1);
        NDArray anchors_per_image = output.get(2);

        final int numberOfClasses = (int) logits_per_image.getShape().get(2);

        if (logits_per_image.getShape().dimension() != 3 ||
                logits_per_image.getShape().get(0) != 1 ||
                numberOfClasses != 2 /* "2" corresponds to a binary classification task */ ||
                box_regression_per_image.getShape().dimension() != 3 ||
                box_regression_per_image.getShape().get(0) != 1 ||
                box_regression_per_image.getShape().get(2) != 4 ||
                anchors_per_image.getShape().dimension() != 2 ||
                anchors_per_image.getShape().get(1) != 4) {
            throw new RuntimeException();
        }


        final int numberOfLevels = output.size() - 3;  // This corresponds to "features" in Python export

        int[] num_anchors_per_level = new int[numberOfLevels];

        {
            int[] sizes = new int[numberOfLevels];
            int HW = 0;

            for (int i = 0; i < numberOfLevels; i++) {
                NDArray feature = output.get(i + 3);
                if (feature.getShape().dimension() != 4 ||
                        feature.getShape().get(0) != 1) {
                    throw new RuntimeException();
                }

                sizes[i] = (int) (feature.getShape().get(2) * feature.getShape().get(3));
                HW += sizes[i];
            }

            final int HWA = (int) logits_per_image.getShape().get(1);

            if (HWA % HW != 0) {
                throw new RuntimeException();
            }

            final int A = (int) (HWA / HW);

            for (int i = 0; i < numberOfLevels; i++) {
                num_anchors_per_level[i] = sizes[i] * A;
            }
        }


        int anchorsIndex[] = new int[numberOfLevels];
        int countAnchors = 0;
        for (int i = 0; i < numberOfLevels; i++) {
            anchorsIndex[i] = countAnchors;
            countAnchors += num_anchors_per_level[i];
        }

        if (logits_per_image.getShape().get(1) != countAnchors ||
                box_regression_per_image.getShape().get(1) != countAnchors ||
                anchors_per_image.getShape().get(0) != countAnchors) {
            throw new RuntimeException();
        }

        final float SCORE_THRESHOLD = 0.05f;
        final int TOP_K_CANDIDATES = 1000;

        // Convert as float array, because direct access to NDArray is terribly slow
        float logits_per_image_as_float[] = logits_per_image.toFloatArray();
        float box_regression_per_image_as_float[] = box_regression_per_image.toFloatArray();
        float anchors_per_image_as_float[] = anchors_per_image.toFloatArray();

        List<Detection> detections = new LinkedList<>();

        for (int level = 0; level < anchorsIndex.length; level++) {
            float box_regression_per_level[][] = new float[num_anchors_per_level[level]][4];
            float logits_per_level[][] = new float[num_anchors_per_level[level]][2];
            float anchors_per_level[][] = new float[num_anchors_per_level[level]][4];

            for (int i = 0; i < num_anchors_per_level[level]; i++) {
                int index = anchorsIndex[level] + i;
                for (int j = 0; j < 4; j++) {
                    box_regression_per_level[i][j] = box_regression_per_image_as_float[4 * index + j];
                }
                for (int j = 0; j < 2; j++) {
                    logits_per_level[i][j] = logits_per_image_as_float[2 * index + j];
                }
                for (int j = 0; j < 4; j++) {
                    anchors_per_level[i][j] = anchors_per_image_as_float[4 * index + j];
                }
            }

            List<Detection> candidates = new LinkedList<>();
            for (int i = 0; i < num_anchors_per_level[level]; i++) {
                for (int label = 0; label < numberOfClasses /* This is actually "2" */; label++) {
                    double score = sigmoid(logits_per_level[i][label]);
                    if (score > SCORE_THRESHOLD) {
                        Rectangle rectangle = decodeSingle(
                                new float[]{box_regression_per_level[i][0], box_regression_per_level[i][1], box_regression_per_level[i][2], box_regression_per_level[i][3]},
                                new float[]{anchors_per_level[i][0], anchors_per_level[i][1], anchors_per_level[i][2], anchors_per_level[i][3]},
                                imageWidth,
                                imageHeight);

                        // This is an entry in "topk_idxs" in Python
                        candidates.add(new Detection(rectangle, label, score));
                    }
                }
            }

            candidates.stream().sorted().limit(TOP_K_CANDIDATES).forEach(detection -> detections.add(detection));
        }


        /**
         * This is non-maximal suppression, which corresponds to
         * function "batched_nms()", then "_batched_nms_vanilla()" in:
         * https://github.com/pytorch/vision/blob/main/torchvision/ops/boxes.py
         *
         * Note that "iou_threshold" equals "self.nms_thresh" of the RetinaNet.
         **/

        final float NMS_THRESH = 0.3f;  // Note that by default, "retinanet.py" uses 0.5

        List<Detection> toKeep = new LinkedList<>();

        for (int label = 0; label < numberOfClasses; label++) {
            /**
             * This corresponds to "torch.ops.torchvision.nms(boxes,
             * scores, iou_threshold)", which is the native function
             * "nms_kernel_impl()" implemented in C++:
             * https://github.com/pytorch/vision/blob/main/torchvision/csrc/ops/cpu/nms_kernel.cpp
             **/

            List<Detection> tmp = new ArrayList<>();

            for (Detection detection : detections) {
                if (detection.getLabel() == label) {
                    tmp.add(detection);
                }
            }

            if (tmp.size() > 0) {
                /**
                 * Performs non-maximum suppression (NMS) on the boxes according
                 * to their intersection-over-union (IoU).
                 * NMS iteratively removes lower scoring boxes which have an
                 * IoU greater than iou_threshold with another (higher scoring)
                 * box.
                 */
                Collections.sort(tmp);  // Sort by decreasing scores
                final Detection[] dets = tmp.toArray(new Detection[0]);
                final int ndets = dets.length;

                boolean[] suppressed = new boolean[ndets];

                for (int i = 0; i < ndets; i++) {
                    if (!suppressed[i]) {
                        toKeep.add(dets[i]);

                        final float iarea = dets[i].getRectangle().getArea();

                        for (int j = i + 1; j < ndets; j++) {
                            if (!suppressed[j]) {
                                final float xx1 = Math.max(dets[i].getRectangle().getX1(), dets[j].getRectangle().getX1());
                                final float yy1 = Math.max(dets[i].getRectangle().getY1(), dets[j].getRectangle().getY1());
                                final float xx2 = Math.min(dets[i].getRectangle().getX2(), dets[j].getRectangle().getX2());
                                final float yy2 = Math.min(dets[i].getRectangle().getY2(), dets[j].getRectangle().getY2());
                                final float w = Math.max(0, xx2 - xx1);
                                final float h = Math.max(0, yy2 - yy1);
                                final float inter = w * h;
                                final float ovr = inter / (iarea + dets[j].getRectangle().getArea() - inter);
                                if (ovr > NMS_THRESH) {
                                    suppressed[j] = true;
                                }
                            }
                        }
                    }
                }
            }
        }

        List<Detection> toSerialize = new LinkedList<>();

        for (Detection detection : toKeep) {
            if (detection.getLabel() == 1 /* "1" is the "mass" label */ &&
                    detection.getScore() >= 0.2f /* This is the "minimum_score=0.2" in "dicom_sr.py" */) {
                toSerialize.add(detection);
            }
        }

        return toSerialize;
    }
}
