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
import be.uclouvain.orthanc.Callbacks;
import be.uclouvain.orthanc.ChangeType;
import be.uclouvain.orthanc.Functions;
import be.uclouvain.orthanc.HttpMethod;
import be.uclouvain.orthanc.ResourceType;
import be.uclouvain.orthanc.RestOutput;
import org.apache.commons.compress.utils.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Main {
    private static final String MODEL_PATH = "2024-03-08-retina_res50_trained_08_03.torchscript";
    private static final String STONE_VERSION = "2024-08-31-StoneWebViewer-DICOM-SR";
    private static final String STONE_PATH = "2024-08-31-StoneWebViewer-DICOM-SR.zip";

    private static ZipFile stone;
    private static NDManager manager;
    private static RetinaNet retinaNet;
    private static Map<String, String> mimeTypes = new HashMap<>();

    static {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            OrthancConnection.download(MODEL_PATH, executor,
                    "https://orthanc.uclouvain.be/downloads/cross-platform/orthanc-mammography/models/" + MODEL_PATH,
                    146029397L, "b3de8f562de683bc3515fe93ae102fd4");
            OrthancConnection.download(STONE_PATH, executor,
                    "https://github.com/jodogne/orthanc-mammography/raw/master/viewer/" + STONE_PATH,
                    4815178L, "86b52a17f86e4769d12e9ae680c4a99f");
        } catch (IOException | NoSuchAlgorithmException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            executor.shutdown();
        }

        try {
            stone = new ZipFile(STONE_PATH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Functions.logWarning("Initializing Deep Java Library");
        manager = NDManager.newBaseManager();

        Functions.logWarning("Loading RetinaNet model");
        try {
            retinaNet = new RetinaNet(MODEL_PATH);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Functions.logWarning("RetinaNet model is ready");

        mimeTypes.put("css", "text/css");
        mimeTypes.put("gif", "image/gif");
        mimeTypes.put("html", "text/html");
        mimeTypes.put("jpeg", "image/jpeg");
        mimeTypes.put("js", "text/javascript");
        mimeTypes.put("png", "image/png");

        try (InputStream stream = Main.class.getResourceAsStream("OrthancExplorer.js")) {
            byte[] content = IOUtils.toByteArray(stream);
            Functions.extendOrthancExplorer(new String(content, StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Callbacks.register(new Callbacks.OnChange() {
            @Override
            public void call(ChangeType changeType, ResourceType resourceType, String resourceId) {
                switch (changeType) {
                    case ORTHANC_STARTED:
                        OrthancConnection connection = OrthancConnection.createForPlugin();
                        try {
                            if (!connection.isOrthancVersionAbove(1, 12, 5)) {
                                throw new RuntimeException("Your version of Orthanc must be >= 1.12.5 to run this plugin");
                            }

                            JSONArray plugins = new JSONArray(connection.doGetAsString("/plugins"));

                            boolean hasDicomWeb = false;
                            for (int i = 0; i < plugins.length() && !hasDicomWeb; i++) {
                                if (plugins.getString(i).equals("dicom-web")) {
                                    hasDicomWeb = true;
                                }
                            }

                            if (!hasDicomWeb) {
                                throw new RuntimeException("The DICOMweb plugin is required, but is not installed");
                            }
                        } catch (IOException | InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        break;

                    case ORTHANC_STOPPED:
                        Functions.logWarning("Finalizing Deep Java Library");
                        if (retinaNet != null) {
                            retinaNet.close();
                        }

                        if (manager != null) {
                            manager.close();
                        }

                        System.gc();
                        System.runFinalization();
                        break;

                    default:
                        break;
                }
            }
        });

        Callbacks.register("/java-mammography-apply", new Callbacks.OnRestRequest() {
            @Override
            public void call(RestOutput output,
                             HttpMethod method,
                             String uri,
                             String[] regularExpressionGroups,
                             Map<String, String> headers,
                             Map<String, String> getParameters,
                             byte[] body) {
                if (method != HttpMethod.POST) {
                    output.sendMethodNotAllowed("POST");
                    return;
                }

                JSONObject request = new JSONObject(new String(body, StandardCharsets.UTF_8));

                String instanceId = request.getString("instance");
                if (instanceId == null) {
                    throw new RuntimeException("Missing instance identifier");
                }

                Functions.logWarning("Applying RetinaNet to instance: " + instanceId);
                OrthancConnection connection = OrthancConnection.createForPlugin();

                try {
                    BufferedImage image = connection.getGrayscaleFrame(instanceId, 0);

                    double largestSide = Math.max(image.getWidth(), image.getHeight());
                    BufferedImage resized = ImageProcessing.resizeImage(image,
                            (int) Math.round((double) image.getWidth() * 2048.0 / largestSide),
                            (int) Math.round((double) image.getHeight() * 2048.0 / largestSide));

                    double aspectRatio = (double) image.getWidth() / (double) resized.getWidth();

                    NDArray grayscale = ImageProcessing.imageToTensor(manager, resized);
                    grayscale = ImageProcessing.standardize(grayscale);

                    NDArray rgb = grayscale.concat(grayscale).concat(grayscale);  // Create a "RGB" image from grayscale pixel values
                    List<Detection> detections = retinaNet.apply(rgb);

                    JSONObject created = DicomToolbox.createDicomSR(connection, instanceId, detections, aspectRatio);

                    String id = created.getString("ID");
                    Functions.logWarning("Detection results stored in DICOM-SR instance: " + id);

                    output.answerBuffer(created.toString().getBytes(StandardCharsets.UTF_8), "application/json");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Callbacks.register("/java-mammography-viewer/(.*)", new Callbacks.OnRestRequest() {
            @Override
            public void call(RestOutput output,
                             HttpMethod method,
                             String uri,
                             String[] regularExpressionGroups,
                             Map<String, String> headers,
                             Map<String, String> getParameters,
                             byte[] body) {
                if (method != HttpMethod.GET) {
                    output.sendMethodNotAllowed("GET");
                    return;
                }

                String path = regularExpressionGroups[0];
                int dot = path.lastIndexOf(".");
                if (dot < 0) {
                    output.sendHttpStatus((short) 404, new byte[0]);
                } else {
                    String extension = path.substring(dot + 1);
                    String mime = mimeTypes.getOrDefault(extension, "application/octet-stream");

                    ZipEntry entry = stone.getEntry(STONE_VERSION + "/" + path);
                    if (entry == null) {
                        output.sendHttpStatus((short) 404, new byte[0]);
                    } else {
                        try (InputStream stream = stone.getInputStream(entry)) {
                            output.answerBuffer(IOUtils.toByteArray(stream), mime);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });
    }
}
