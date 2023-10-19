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
 * Orthanc job
 **/
public class Job {
    private long self;

    /**
     * Construct a Java object wrapping a C object that is managed by Orthanc.
     * @param self Pointer to the C object.
     **/
    protected Job(long self) {
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
            NativeSDK.OrthancPluginFreeJob(self);
            self = 0;
        }
    }


    /**
     * Submit a new job to the jobs engine of Orthanc.
     * 
     * This function adds the given job to the pending jobs of Orthanc. Orthanc will
     * take take of freeing it by invoking the finalization callback provided to
     * OrthancPluginCreateJob().
     * 
     * @param priority The priority of the job.
     * @return The resulting string.
     **/
    public String submitJob(
        int priority) {
        return NativeSDK.OrthancPluginSubmitJob(self, priority);
    }

}
