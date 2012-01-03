//
// $Id$
//
// Vilya library - tools for developing networked games
// Copyright (C) 2002-2012 Three Rings Design, Inc., All Rights Reserved
// http://code.google.com/p/vilya/
//
// This library is free software; you can redistribute it and/or modify it
// under the terms of the GNU Lesser General Public License as published
// by the Free Software Foundation; either version 2.1 of the License, or
// (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

package com.threerings.whirled.spot.data {

import com.threerings.crowd.chat.data.ChatCodes;

import com.threerings.whirled.data.SceneCodes;

/**
 * Contains codes used by the Spot invocation services.
 */
public class SpotCodes extends SceneCodes /*, ChatCodes */
{
    /** An error code indicating that the portal specified in a
     * traversePortal request does not exist. */
    public static const NO_SUCH_PORTAL :String = "m.no_such_portal";

    /** An error code indicating that a location is occupied. Usually
     * generated by a failed changeLoc request. */
    public static const LOCATION_OCCUPIED :String = "m.location_occupied";

    /** An error code indicating that a location is not valid. Usually
     * generated by a failed changeLoc request. */
    public static const INVALID_LOCATION :String = "m.invalid_location";

    /** An error code indicating that a cluster is not valid. Usually
     * generated by a failed joinCluster request. */
    public static const NO_SUCH_CLUSTER :String = "m.no_such_cluster";

    /** An error code indicating that a cluster is full. Usually generated
     * by a failed joinCluster request. */
    public static const CLUSTER_FULL :String = "m.cluster_full";

    /** The chat type code with which we register our cluster auxiliary
     * chat objects. Chat display implementations should interpret chat
     * messages with this type accordingly. */
    public static const CLUSTER_CHAT_TYPE :String = "clusterChat";
}
}
