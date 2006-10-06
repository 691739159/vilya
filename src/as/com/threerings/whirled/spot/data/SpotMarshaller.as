//
// $Id$
//
// Vilya library - tools for developing networked games
// Copyright (C) 2002-2006 Three Rings Design, Inc., All Rights Reserved
// http://www.threerings.net/code/vilya/
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

import com.threerings.util.*; // for Float, Integer, etc.

import com.threerings.presents.client.Client;
import com.threerings.presents.client.InvocationService_ConfirmListener;
import com.threerings.presents.data.InvocationMarshaller;
import com.threerings.presents.data.InvocationMarshaller_ConfirmMarshaller;
import com.threerings.presents.data.InvocationMarshaller_ListenerMarshaller;
import com.threerings.whirled.client.SceneService_SceneMoveListener;
import com.threerings.whirled.data.SceneMarshaller_SceneMoveMarshaller;
import com.threerings.whirled.spot.client.SpotService;
import com.threerings.whirled.spot.data.Location;

/**
 * Provides the implementation of the {@link SpotService} interface
 * that marshalls the arguments and delivers the request to the provider
 * on the server. Also provides an implementation of the response listener
 * interfaces that marshall the response arguments and deliver them back
 * to the requesting client.
 */
public class SpotMarshaller extends InvocationMarshaller
    implements SpotService
{
    /** The method id used to dispatch {@link #changeLocation} requests. */
    public static const CHANGE_LOCATION :int = 1;

    // from interface SpotService
    public function changeLocation (arg1 :Client, arg2 :int, arg3 :Location, arg4 :InvocationService_ConfirmListener) :void
    {
        var listener4 :InvocationMarshaller_ConfirmMarshaller = new InvocationMarshaller_ConfirmMarshaller();
        listener4.listener = arg4;
        sendRequest(arg1, CHANGE_LOCATION, [
            Integer.valueOf(arg2), arg3, listener4
        ]);
    }

    /** The method id used to dispatch {@link #clusterSpeak} requests. */
    public static const CLUSTER_SPEAK :int = 2;

    // from interface SpotService
    public function clusterSpeak (arg1 :Client, arg2 :String, arg3 :int) :void
    {
        sendRequest(arg1, CLUSTER_SPEAK, [
            arg2, Byte.valueOf(arg3)
        ]);
    }

    /** The method id used to dispatch {@link #joinCluster} requests. */
    public static const JOIN_CLUSTER :int = 3;

    // from interface SpotService
    public function joinCluster (arg1 :Client, arg2 :int, arg3 :InvocationService_ConfirmListener) :void
    {
        var listener3 :InvocationMarshaller_ConfirmMarshaller = new InvocationMarshaller_ConfirmMarshaller();
        listener3.listener = arg3;
        sendRequest(arg1, JOIN_CLUSTER, [
            Integer.valueOf(arg2), listener3
        ]);
    }

    /** The method id used to dispatch {@link #traversePortal} requests. */
    public static const TRAVERSE_PORTAL :int = 4;

    // from interface SpotService
    public function traversePortal (arg1 :Client, arg2 :int, arg3 :int, arg4 :int, arg5 :SceneService_SceneMoveListener) :void
    {
        var listener5 :SceneMarshaller_SceneMoveMarshaller = new SceneMarshaller_SceneMoveMarshaller();
        listener5.listener = arg5;
        sendRequest(arg1, TRAVERSE_PORTAL, [
            Integer.valueOf(arg2), Integer.valueOf(arg3), Integer.valueOf(arg4), listener5
        ]);
    }
}
}
