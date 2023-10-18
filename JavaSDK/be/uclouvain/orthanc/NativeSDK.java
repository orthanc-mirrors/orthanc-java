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
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 **/


class NativeSDK {
    public static native int OrthancPluginCheckVersionAdvanced(int arg0, int arg1, int arg2);
    public static native int OrthancPluginCheckVersion();
    public static native void OrthancPluginLogError(String arg0);
    public static native void OrthancPluginLogWarning(String arg0);
    public static native void OrthancPluginLogInfo(String arg0);
    public static native byte[] OrthancPluginGetDicomForInstance(String arg0);
    public static native byte[] OrthancPluginRestApiGet(String arg0);
    public static native byte[] OrthancPluginRestApiGetAfterPlugins(String arg0);
    public static native byte[] OrthancPluginRestApiPost(String arg0, byte[] arg1);
    public static native byte[] OrthancPluginRestApiPostAfterPlugins(String arg0, byte[] arg1);
    public static native void OrthancPluginRestApiDelete(String arg0);
    public static native void OrthancPluginRestApiDeleteAfterPlugins(String arg0);
    public static native byte[] OrthancPluginRestApiPut(String arg0, byte[] arg1);
    public static native byte[] OrthancPluginRestApiPutAfterPlugins(String arg0, byte[] arg1);
    public static native String OrthancPluginLookupPatient(String arg0);
    public static native String OrthancPluginLookupStudy(String arg0);
    public static native String OrthancPluginLookupStudyWithAccessionNumber(String arg0);
    public static native String OrthancPluginLookupSeries(String arg0);
    public static native String OrthancPluginLookupInstance(String arg0);
    public static native String OrthancPluginGetOrthancPath();
    public static native String OrthancPluginGetOrthancDirectory();
    public static native String OrthancPluginGetConfigurationPath();
    public static native void OrthancPluginSetRootUri(String arg0);
    public static native void OrthancPluginSetDescription(String arg0);
    public static native void OrthancPluginExtendOrthancExplorer(String arg0);
    public static native String OrthancPluginGetGlobalProperty(int arg0, String arg1);
    public static native void OrthancPluginSetGlobalProperty(int arg0, String arg1);
    public static native int OrthancPluginGetCommandLineArgumentsCount();
    public static native String OrthancPluginGetCommandLineArgument(int arg0);
    public static native int OrthancPluginGetExpectedDatabaseVersion();
    public static native String OrthancPluginGetConfiguration();
    public static native byte[] OrthancPluginBufferCompression(byte[] arg0, int arg2, byte arg3);
    public static native byte[] OrthancPluginReadFile(String arg0);
    public static native void OrthancPluginWriteFile(String arg0, byte[] arg1);
    public static native String OrthancPluginGetErrorDescription(int arg0);
    public static native long OrthancPluginUncompressImage(byte[] arg0, int arg2);
    public static native byte[] OrthancPluginCompressPngImage(int arg0, int arg1, int arg2, int arg3, byte[] arg4);
    public static native byte[] OrthancPluginCompressJpegImage(int arg0, int arg1, int arg2, int arg3, byte[] arg4, byte arg5);
    public static native byte[] OrthancPluginHttpGet(String arg0, String arg1, String arg2);
    public static native byte[] OrthancPluginHttpPost(String arg0, byte[] arg1, String arg3, String arg4);
    public static native byte[] OrthancPluginHttpPut(String arg0, byte[] arg1, String arg3, String arg4);
    public static native void OrthancPluginHttpDelete(String arg0, String arg1, String arg2);
    public static native int OrthancPluginGetFontsCount();
    public static native String OrthancPluginGetFontName(int arg0);
    public static native int OrthancPluginGetFontSize(int arg0);
    public static native void OrthancPluginRegisterErrorCode(int arg0, short arg1, String arg2);
    public static native void OrthancPluginRegisterDictionaryTag(short arg0, short arg1, int arg2, String arg3, int arg4, int arg5);
    public static native void OrthancPluginRegisterPrivateDictionaryTag(short arg0, short arg1, int arg2, String arg3, int arg4, int arg5, String arg6);
    public static native String OrthancPluginDicomBufferToJson(byte[] arg0, int arg2, int arg3, int arg4);
    public static native String OrthancPluginDicomInstanceToJson(String arg0, int arg1, int arg2, int arg3);
    public static native byte[] OrthancPluginCreateDicom(String arg0, long arg1, int arg2);
    public static native long OrthancPluginCreateImage(int arg0, int arg1, int arg2);
    public static native long OrthancPluginDecodeDicomImage(byte[] arg0, int arg2);
    public static native String OrthancPluginComputeMd5(byte[] arg0);
    public static native String OrthancPluginComputeSha1(byte[] arg0);
    public static native String OrthancPluginGenerateUuid();
    public static native long OrthancPluginCreateFindMatcher(byte[] arg0);
    public static native long OrthancPluginGetPeers();
    public static native String OrthancPluginAutodetectMimeType(String arg0);
    public static native void OrthancPluginSetMetricsValue(String arg0, float arg1, int arg2);
    public static native String OrthancPluginGetTagName(short arg0, short arg1, String arg2);
    public static native long OrthancPluginCreateDicomInstance(byte[] arg0);
    public static native long OrthancPluginTranscodeDicomInstance(byte[] arg0, String arg2);
    public static native String OrthancPluginGenerateRestApiAuthorizationToken();
    public static native byte[] OrthancPluginCreateDicom2(String arg0, long arg1, int arg2, String arg3);
    public static native void OrthancPluginFreeDicomInstance(long self );
    public static native String OrthancPluginGetInstanceRemoteAet(long self );
    public static native long OrthancPluginGetInstanceSize(long self );
    public static native String OrthancPluginGetInstanceJson(long self );
    public static native String OrthancPluginGetInstanceSimplifiedJson(long self );
    public static native int OrthancPluginHasInstanceMetadata(long self, String arg0);
    public static native String OrthancPluginGetInstanceMetadata(long self, String arg0);
    public static native int OrthancPluginGetInstanceOrigin(long self );
    public static native String OrthancPluginGetInstanceTransferSyntaxUid(long self );
    public static native int OrthancPluginHasInstancePixelData(long self );
    public static native int OrthancPluginGetInstanceFramesCount(long self );
    public static native byte[] OrthancPluginGetInstanceRawFrame(long self, int arg0);
    public static native long OrthancPluginGetInstanceDecodedFrame(long self, int arg0);
    public static native byte[] OrthancPluginSerializeDicomInstance(long self );
    public static native String OrthancPluginGetInstanceAdvancedJson(long self, int arg0, int arg1, int arg2);
    public static native void OrthancPluginFindAddAnswer(long self, byte[] arg0);
    public static native void OrthancPluginFindMarkIncomplete(long self );
    public static native void OrthancPluginFreeFindMatcher(long self );
    public static native int OrthancPluginFindMatcherIsMatch(long self, byte[] arg0);
    public static native int OrthancPluginGetFindQuerySize(long self );
    public static native String OrthancPluginGetFindQueryTagName(long self, int arg0);
    public static native String OrthancPluginGetFindQueryValue(long self, int arg0);
    public static native void OrthancPluginFreeImage(long self );
    public static native int OrthancPluginGetImagePixelFormat(long self );
    public static native int OrthancPluginGetImageWidth(long self );
    public static native int OrthancPluginGetImageHeight(long self );
    public static native int OrthancPluginGetImagePitch(long self );
    public static native long OrthancPluginConvertPixelFormat(long self, int arg0);
    public static native void OrthancPluginDrawText(long self, int arg0, String arg1, int arg2, int arg3, byte arg4, byte arg5, byte arg6);
    public static native void OrthancPluginFreeJob(long self );
    public static native String OrthancPluginSubmitJob(long self, int arg0);
    public static native void OrthancPluginFreePeers(long self );
    public static native int OrthancPluginGetPeersCount(long self );
    public static native String OrthancPluginGetPeerName(long self, int arg0);
    public static native String OrthancPluginGetPeerUrl(long self, int arg0);
    public static native String OrthancPluginGetPeerUserProperty(long self, int arg0, String arg1);
    public static native void OrthancPluginAnswerBuffer(long self, byte[] arg0, String arg2);
    public static native void OrthancPluginCompressAndAnswerPngImage(long self, int arg0, int arg1, int arg2, int arg3, byte[] arg4);
    public static native void OrthancPluginRedirect(long self, String arg0);
    public static native void OrthancPluginSendHttpStatusCode(long self, short arg0);
    public static native void OrthancPluginSendUnauthorized(long self, String arg0);
    public static native void OrthancPluginSendMethodNotAllowed(long self, String arg0);
    public static native void OrthancPluginSetCookie(long self, String arg0, String arg1);
    public static native void OrthancPluginSetHttpHeader(long self, String arg0, String arg1);
    public static native void OrthancPluginStartMultipartAnswer(long self, String arg0, String arg1);
    public static native void OrthancPluginSendMultipartItem(long self, byte[] arg0);
    public static native void OrthancPluginSendHttpStatus(long self, short arg0, byte[] arg1);
    public static native void OrthancPluginCompressAndAnswerJpegImage(long self, int arg0, int arg1, int arg2, int arg3, byte[] arg4, byte arg5);
    public static native void OrthancPluginSetHttpErrorDetails(long self, String arg0, byte arg1);
    public static native void OrthancPluginStorageAreaCreate(long self, String arg0, byte[] arg1, long arg2, int arg3);
    public static native byte[] OrthancPluginStorageAreaRead(long self, String arg0, int arg1);
    public static native void OrthancPluginStorageAreaRemove(long self, String arg0, int arg1);
    public static native void OrthancPluginReconstructMainDicomTags(long self, int arg0);
    public static native void OrthancPluginWorklistAddAnswer(long self, long arg0, byte[] arg1);
    public static native void OrthancPluginWorklistMarkIncomplete(long self );
    public static native int OrthancPluginWorklistIsMatch(long self, byte[] arg0);
    public static native byte[] OrthancPluginWorklistGetDicomQuery(long self );
}
