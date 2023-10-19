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
 * Orthanc peer
 **/
public class Peers {
    private long self;

    /**
     * Construct a Java object wrapping a C object that is managed by Orthanc.
     * @param self Pointer to the C object.
     **/
    protected Peers(long self) {
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
            NativeSDK.OrthancPluginFreePeers(self);
            self = 0;
        }
    }

    /**
     * Return the list of available Orthanc peers.
     * 
     * This function returns the parameters of the Orthanc peers that are known to the
     * Orthanc server hosting the plugin.
     * 
     * @return The newly constructed object.
     **/
    public static Peers getPeers() {
        return new Peers(NativeSDK.OrthancPluginGetPeers());
    }


    /**
     * Get the number of Orthanc peers.
     * 
     * This function returns the number of Orthanc peers.
     * 
     * This function is thread-safe: Several threads sharing the same
     * OrthancPluginPeers object can simultaneously call this function.
     * 
     * @return The number of peers.
     **/
    public int getPeersCount() {
        return NativeSDK.OrthancPluginGetPeersCount(self);
    }

    /**
     * Get the symbolic name of an Orthanc peer.
     * 
     * This function returns the symbolic name of the Orthanc peer, which corresponds
     * to the key of the "OrthancPeers" configuration option of Orthanc.
     * 
     * This function is thread-safe: Several threads sharing the same
     * OrthancPluginPeers object can simultaneously call this function.
     * 
     * @param peerIndex The index of the peer of interest. This value must be lower
     * than OrthancPluginGetPeersCount().
     * @return The resulting string.
     **/
    public String getPeerName(
        int peerIndex) {
        return NativeSDK.OrthancPluginGetPeerName(self, peerIndex);
    }

    /**
     * Get the base URL of an Orthanc peer.
     * 
     * This function returns the base URL to the REST API of some Orthanc peer.
     * 
     * This function is thread-safe: Several threads sharing the same
     * OrthancPluginPeers object can simultaneously call this function.
     * 
     * @param peerIndex The index of the peer of interest. This value must be lower
     * than OrthancPluginGetPeersCount().
     * @return The resulting string.
     **/
    public String getPeerUrl(
        int peerIndex) {
        return NativeSDK.OrthancPluginGetPeerUrl(self, peerIndex);
    }

    /**
     * Get some user-defined property of an Orthanc peer.
     * 
     * This function returns some user-defined property of some Orthanc peer. An
     * user-defined property is a property that is associated with the peer in the
     * Orthanc configuration file, but that is not recognized by the Orthanc core.
     * 
     * This function is thread-safe: Several threads sharing the same
     * OrthancPluginPeers object can simultaneously call this function.
     * 
     * @param peerIndex The index of the peer of interest. This value must be lower
     * than OrthancPluginGetPeersCount().
     * @param userProperty The user property of interest.
     * @return The resulting string.
     **/
    public String getPeerUserProperty(
        int peerIndex,
        String userProperty) {
        return NativeSDK.OrthancPluginGetPeerUserProperty(self, peerIndex, userProperty);
    }

}
