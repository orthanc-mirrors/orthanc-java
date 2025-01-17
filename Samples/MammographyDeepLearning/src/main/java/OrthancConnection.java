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


import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

public abstract class OrthancConnection {
    private static final String PIXEL_REPRESENTATION = "0028,0103";
    private static final String BITS_STORED = "0028,0101";
    private static final String SAMPLES_PER_PIXEL = "0028,0002";

    public static OrthancConnection createHttpClient(ExecutorService executor,
                                                     String baseUrl) {
        return new OrthancConnection() {
            private HttpClient client = HttpClient.newBuilder().executor(executor).build();

            @Override
            public byte[] doGetAsByteArray(String uri) throws IOException, InterruptedException {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(baseUrl + uri))
                        .build();
                HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
                if (response.statusCode() != 200) {
                    throw new RuntimeException();
                } else {
                    return response.body();
                }
            }

            @Override
            public byte[] doPostAsByteArray(String uri, byte[] body) throws IOException, InterruptedException {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(baseUrl + uri))
                        .POST(HttpRequest.BodyPublishers.ofByteArray(body))
                        .build();
                HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
                if (response.statusCode() != 200) {
                    throw new RuntimeException();
                } else {
                    return response.body();
                }
            }
        };
    }

    public static OrthancConnection createForPlugin() {
        return new OrthancConnection() {
            @Override
            public byte[] doGetAsByteArray(String uri) throws IOException, InterruptedException {
                return be.uclouvain.orthanc.Functions.restApiGet(uri);
            }

            @Override
            public byte[] doPostAsByteArray(String uri, byte[] body) throws IOException, InterruptedException {
                return be.uclouvain.orthanc.Functions.restApiPost(uri, body);
            }
        };
    }

    public abstract byte[] doGetAsByteArray(String uri) throws IOException, InterruptedException;

    public abstract byte[] doPostAsByteArray(String uri,
                                             byte[] body) throws IOException, InterruptedException;

    public String doGetAsString(String uri) throws IOException, InterruptedException {
        return new String(doGetAsByteArray(uri), StandardCharsets.UTF_8);
    }

    public JSONObject doGetAsJsonObject(String uri) throws IOException, InterruptedException {
        return new JSONObject(doGetAsString(uri));
    }

    public String doPostAsString(String uri,
                                 String body) throws IOException, InterruptedException {
        byte[] answer = doPostAsByteArray(uri, body.getBytes(StandardCharsets.UTF_8));
        return new String(answer, StandardCharsets.UTF_8);
    }

    public JSONObject doPostAsJsonObject(String uri,
                                         String body) throws IOException, InterruptedException {
        return new JSONObject(doPostAsString(uri, body));
    }

    public BufferedImage getGrayscaleFrame(String instance,
                                           int frame) throws IOException, InterruptedException {
        if (frame < 0) {
            throw new IllegalArgumentException();
        }

        JSONObject tags = doGetAsJsonObject("/instances/" + instance + "/tags?short");

        if (!tags.has(PIXEL_REPRESENTATION) ||
                tags.getInt(PIXEL_REPRESENTATION) == 1) {
            throw new IllegalArgumentException("Negative pixels not supported");
        }

        if (!tags.has(BITS_STORED) ||
                (tags.getInt(BITS_STORED) != 8 &&
                        tags.getInt(BITS_STORED) != 16)) {
            throw new IllegalArgumentException("Pixel depth not supported");
        }

        if (!tags.has(SAMPLES_PER_PIXEL) ||
                tags.getInt(SAMPLES_PER_PIXEL) != 1) {
            throw new IllegalArgumentException("Color images not implemented");
        }

        byte[] png = doGetAsByteArray("/instances/" + instance + "/frames/" + frame + "/image-uint16");
        try (ByteArrayInputStream stream = new ByteArrayInputStream(png)) {
            return ImageIO.read(stream);
        }
    }

    boolean isOrthancVersionAbove(int major,
                                  int minor,
                                  int revision) throws IOException, InterruptedException {
        JSONObject system = doGetAsJsonObject("/system");
        String version = system.getString("Version");
        if (version == null) {
            throw new RuntimeException("Not an Orthanc server");
        }

        if (version.equals("mainline")) {
            return true;
        } else {
            String[] items = version.split("\\.");
            if (items.length != 3) {
                throw new RuntimeException("Cannot parse Orthanc version: " + version);
            }

            int thisMajor = Integer.valueOf(items[0]);
            int thisMinor = Integer.valueOf(items[1]);
            int thisRevision = Integer.valueOf(items[2]);

            return (thisMajor > major ||
                    (thisMajor == major && thisMinor > minor) ||
                    (thisMajor == major && thisMinor == minor && thisRevision >= revision));
        }
    }


    public static void download(String targetPath,
                                ExecutorService executor,
                                String url,
                                long expectedSize,
                                String expectedMd5) throws IOException, NoSuchAlgorithmException, InterruptedException {
        class FileDownloader implements HttpResponse.BodyHandler<Void>, Consumer<Optional<byte[]>> {
            private FileOutputStream target;
            private String url;
            private boolean success;
            private long contentLength;
            private long size;

            public FileDownloader(final FileOutputStream target,
                                  final String url) throws FileNotFoundException {
                this.target = target;
                this.success = false;
            }

            @Override
            public HttpResponse.BodySubscriber<Void> apply(final HttpResponse.ResponseInfo responseInfo) {
                if (responseInfo.statusCode() != 200) {
                    throw new RuntimeException("URL does not exist: " + url);
                }

                final OptionalLong contentLength = responseInfo.headers().firstValueAsLong("Content-Length");
                if (contentLength.isEmpty()) {
                    throw new RuntimeException("Server does not provide a content length: " + url);
                }
                this.contentLength = contentLength.getAsLong();

                return HttpResponse.BodySubscribers.ofByteArrayConsumer(this);
            }

            @Override
            public void accept(final Optional<byte[]> bytes) {
                if (bytes.isEmpty()) {
                    System.out.println();
                    System.out.flush();
                    if (this.success) {
                        throw new IllegalStateException("File already closed");
                    }
                    if (this.size != this.contentLength) {
                        throw new RuntimeException("Server has not answered with the proper content length");
                    }
                    this.success = true;
                } else {
                    try {
                        this.target.write(bytes.get());
                    } catch (IOException e) {
                        System.out.println();
                        throw new RuntimeException("Cannot write to file");
                    }
                    this.size += bytes.get().length;
                    final int BAR_WIDTH = 30;
                    final int a = Math.min(30, Math.round(this.size / (float) this.contentLength * 30.0f));
                    System.out.print("\r  Progress: [" + "=".repeat(a) + " ".repeat(30 - a) + "]");
                    System.out.flush();
                }
            }

            boolean isSuccess() {
                return this.success;
            }
        }


        System.out.println("Downloading: " + url);

        if (Files.exists(Paths.get(targetPath))) {
            System.out.println("  File already downloaded");
        } else {
            FileOutputStream target = new FileOutputStream(targetPath);
            HttpClient client = HttpClient.newBuilder().executor(executor).followRedirects(HttpClient.Redirect.NORMAL).build();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

            FileDownloader consumer = new FileDownloader(target, url);
            HttpResponse<Void> response = client.send(request, (HttpResponse.BodyHandler<Void>) consumer);
            target.close();

            if (!consumer.isSuccess()) {
                throw new IOException("Could not download: " + url);
            }
        }

        byte[] content = Files.readAllBytes(Paths.get(targetPath));
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(content);

        final String actualMd5 = new BigInteger(1, md.digest()).toString(16);
        if (content.length != expectedSize ||
                !actualMd5.equals(expectedMd5)) {
            throw new IOException("Incorrect content in a download file, please remove it and retry: " + targetPath);
        }
    }
}
