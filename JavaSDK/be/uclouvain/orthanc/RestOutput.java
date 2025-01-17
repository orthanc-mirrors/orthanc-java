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
 * Output for a call to the REST API of Orthanc
 **/
public class RestOutput {
    private long self;

    /**
     * Construct a Java object wrapping a C object that is managed by Orthanc.
     * @param self Pointer to the C object.
     **/
    protected RestOutput(long self) {
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
     * Answer to a REST request.
     * 
     * This function answers to a REST request with the content of a memory buffer.
     * 
     * @param answer Pointer to the memory buffer containing the answer.
     * @param mimeType The MIME type of the answer.
     **/
    public void answerBuffer(
        byte[] answer,
        String mimeType) {
        NativeSDK.OrthancPluginAnswerBuffer(self, answer, mimeType);
    }

    /**
     * Answer to a REST request with a PNG image.
     * 
     * This function answers to a REST request with a PNG image. The parameters of this
     * function describe a memory buffer that contains an uncompressed image. The image
     * will be automatically compressed as a PNG image by the core system of Orthanc.
     * 
     * @param format The memory layout of the uncompressed image.
     * @param width The width of the image.
     * @param height The height of the image.
     * @param pitch The pitch of the image (i.e. the number of bytes between 2
     * successive lines of the image in the memory buffer).
     * @param buffer The memory buffer containing the uncompressed image.
     **/
    public void compressAndAnswerPngImage(
        PixelFormat format,
        int width,
        int height,
        int pitch,
        byte[] buffer) {
        NativeSDK.OrthancPluginCompressAndAnswerPngImage(self, format.getValue(), width, height, pitch, buffer);
    }

    /**
     * Redirect a REST request.
     * 
     * This function answers to a REST request by redirecting the user to another URI
     * using HTTP status 301.
     * 
     * @param redirection Where to redirect.
     **/
    public void redirect(
        String redirection) {
        NativeSDK.OrthancPluginRedirect(self, redirection);
    }

    /**
     * Send a HTTP status code.
     * 
     * This function answers to a REST request by sending a HTTP status code (such as
     * "400 - Bad Request"). Note that: - Successful requests (status 200) must use
     * ::OrthancPluginAnswerBuffer(). - Redirections (status 301) must use
     * ::OrthancPluginRedirect(). - Unauthorized access (status 401) must use
     * ::OrthancPluginSendUnauthorized(). - Methods not allowed (status 405) must use
     * ::OrthancPluginSendMethodNotAllowed().
     * 
     * @param status The HTTP status code to be sent.
     **/
    public void sendHttpStatusCode(
        short status) {
        NativeSDK.OrthancPluginSendHttpStatusCode(self, status);
    }

    /**
     * Signal that a REST request is not authorized.
     * 
     * This function answers to a REST request by signaling that it is not authorized.
     * 
     * @param realm The realm for the authorization process.
     **/
    public void sendUnauthorized(
        String realm) {
        NativeSDK.OrthancPluginSendUnauthorized(self, realm);
    }

    /**
     * Signal that this URI does not support this HTTP method.
     * 
     * This function answers to a REST request by signaling that the queried URI does
     * not support this method.
     * 
     * @param allowedMethods The allowed methods for this URI (e.g. "GET,POST" after a
     * PUT or a POST request).
     **/
    public void sendMethodNotAllowed(
        String allowedMethods) {
        NativeSDK.OrthancPluginSendMethodNotAllowed(self, allowedMethods);
    }

    /**
     * Set a cookie.
     * 
     * This function sets a cookie in the HTTP client.
     * 
     * @param cookie The cookie to be set.
     * @param value The value of the cookie.
     **/
    public void setCookie(
        String cookie,
        String value) {
        NativeSDK.OrthancPluginSetCookie(self, cookie, value);
    }

    /**
     * Set some HTTP header.
     * 
     * This function sets a HTTP header in the HTTP answer.
     * 
     * @param key The HTTP header to be set.
     * @param value The value of the HTTP header.
     **/
    public void setHttpHeader(
        String key,
        String value) {
        NativeSDK.OrthancPluginSetHttpHeader(self, key, value);
    }

    /**
     * Start an HTTP multipart answer.
     * 
     * Initiates a HTTP multipart answer, as the result of a REST request.
     * 
     * @param subType The sub-type of the multipart answer ("mixed" or "related").
     * @param contentType The MIME type of the items in the multipart answer.
     **/
    public void startMultipartAnswer(
        String subType,
        String contentType) {
        NativeSDK.OrthancPluginStartMultipartAnswer(self, subType, contentType);
    }

    /**
     * Send an item as a part of some HTTP multipart answer.
     * 
     * This function sends an item as a part of some HTTP multipart answer that was
     * initiated by OrthancPluginStartMultipartAnswer().
     * 
     * @param answer Pointer to the memory buffer containing the item.
     **/
    public void sendMultipartItem(
        byte[] answer) {
        NativeSDK.OrthancPluginSendMultipartItem(self, answer);
    }

    /**
     * Send a HTTP status, with a custom body.
     * 
     * This function answers to a HTTP request by sending a HTTP status code (such as
     * "400 - Bad Request"), together with a body describing the error. The body will
     * only be returned if the configuration option "HttpDescribeErrors" of Orthanc is
     * set to "true".
     * 
     * Note that: - Successful requests (status 200) must use
     * ::OrthancPluginAnswerBuffer(). - Redirections (status 301) must use
     * ::OrthancPluginRedirect(). - Unauthorized access (status 401) must use
     * ::OrthancPluginSendUnauthorized(). - Methods not allowed (status 405) must use
     * ::OrthancPluginSendMethodNotAllowed().
     * 
     * @param status The HTTP status code to be sent.
     * @param body The body of the answer.
     **/
    public void sendHttpStatus(
        short status,
        byte[] body) {
        NativeSDK.OrthancPluginSendHttpStatus(self, status, body);
    }

    /**
     * Answer to a REST request with a JPEG image.
     * 
     * This function answers to a REST request with a JPEG image. The parameters of
     * this function describe a memory buffer that contains an uncompressed image. The
     * image will be automatically compressed as a JPEG image by the core system of
     * Orthanc.
     * 
     * @param format The memory layout of the uncompressed image.
     * @param width The width of the image.
     * @param height The height of the image.
     * @param pitch The pitch of the image (i.e. the number of bytes between 2
     * successive lines of the image in the memory buffer).
     * @param buffer The memory buffer containing the uncompressed image.
     * @param quality The quality of the JPEG encoding, between 1 (worst quality, best
     * compression) and 100 (best quality, worst compression).
     **/
    public void compressAndAnswerJpegImage(
        PixelFormat format,
        int width,
        int height,
        int pitch,
        byte[] buffer,
        byte quality) {
        NativeSDK.OrthancPluginCompressAndAnswerJpegImage(self, format.getValue(), width, height, pitch, buffer, quality);
    }

    /**
     * Provide a detailed description for an HTTP error.
     * 
     * This function sets the detailed description associated with an HTTP error. This
     * description will be displayed in the "Details" field of the JSON body of the
     * HTTP answer. It is only taken into consideration if the REST callback returns an
     * error code that is different from "OrthancPluginErrorCode_Success", and if the
     * "HttpDescribeErrors" configuration option of Orthanc is set to "true".
     * 
     * @param details The details of the error message.
     * @param log Whether to also write the detailed error to the Orthanc logs.
     **/
    public void setHttpErrorDetails(
        String details,
        byte log) {
        NativeSDK.OrthancPluginSetHttpErrorDetails(self, details, log);
    }

}
