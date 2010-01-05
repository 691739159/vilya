//
// $Id$
//
// Vilya library - tools for developing networked games
// Copyright (C) 2002-2010 Three Rings Design, Inc., All Rights Reserved
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

package com.threerings.whirled.zone.data;

import com.threerings.presents.client.Client;
import com.threerings.presents.data.InvocationMarshaller;
import com.threerings.presents.dobj.InvocationResponseEvent;

import com.threerings.crowd.data.PlaceConfig;

import com.threerings.whirled.data.SceneModel;
import com.threerings.whirled.data.SceneUpdate;
import com.threerings.whirled.zone.client.ZoneService;

/**
 * Provides the implementation of the {@link ZoneService} interface
 * that marshalls the arguments and delivers the request to the provider
 * on the server. Also provides an implementation of the response listener
 * interfaces that marshall the response arguments and deliver them back
 * to the requesting client.
 */
public class ZoneMarshaller extends InvocationMarshaller
    implements ZoneService
{
    /**
     * Marshalls results to implementations of {@link ZoneService.ZoneMoveListener}.
     */
    public static class ZoneMoveMarshaller extends ListenerMarshaller
        implements ZoneMoveListener
    {
        /** The method id used to dispatch {@link #moveSucceeded}
         * responses. */
        public static final int MOVE_SUCCEEDED = 1;

        // from interface ZoneMoveMarshaller
        public void moveSucceeded (int arg1, PlaceConfig arg2, ZoneSummary arg3)
        {
            _invId = null;
            omgr.postEvent(new InvocationResponseEvent(
                               callerOid, requestId, MOVE_SUCCEEDED,
                               new Object[] { Integer.valueOf(arg1), arg2, arg3 }, transport));
        }

        /** The method id used to dispatch {@link #moveSucceededWithScene}
         * responses. */
        public static final int MOVE_SUCCEEDED_WITH_SCENE = 2;

        // from interface ZoneMoveMarshaller
        public void moveSucceededWithScene (int arg1, PlaceConfig arg2, ZoneSummary arg3, SceneModel arg4)
        {
            _invId = null;
            omgr.postEvent(new InvocationResponseEvent(
                               callerOid, requestId, MOVE_SUCCEEDED_WITH_SCENE,
                               new Object[] { Integer.valueOf(arg1), arg2, arg3, arg4 }, transport));
        }

        /** The method id used to dispatch {@link #moveSucceededWithUpdates}
         * responses. */
        public static final int MOVE_SUCCEEDED_WITH_UPDATES = 3;

        // from interface ZoneMoveMarshaller
        public void moveSucceededWithUpdates (int arg1, PlaceConfig arg2, ZoneSummary arg3, SceneUpdate[] arg4)
        {
            _invId = null;
            omgr.postEvent(new InvocationResponseEvent(
                               callerOid, requestId, MOVE_SUCCEEDED_WITH_UPDATES,
                               new Object[] { Integer.valueOf(arg1), arg2, arg3, arg4 }, transport));
        }

        @Override // from InvocationMarshaller
        public void dispatchResponse (int methodId, Object[] args)
        {
            switch (methodId) {
            case MOVE_SUCCEEDED:
                ((ZoneMoveListener)listener).moveSucceeded(
                    ((Integer)args[0]).intValue(), (PlaceConfig)args[1], (ZoneSummary)args[2]);
                return;

            case MOVE_SUCCEEDED_WITH_SCENE:
                ((ZoneMoveListener)listener).moveSucceededWithScene(
                    ((Integer)args[0]).intValue(), (PlaceConfig)args[1], (ZoneSummary)args[2], (SceneModel)args[3]);
                return;

            case MOVE_SUCCEEDED_WITH_UPDATES:
                ((ZoneMoveListener)listener).moveSucceededWithUpdates(
                    ((Integer)args[0]).intValue(), (PlaceConfig)args[1], (ZoneSummary)args[2], (SceneUpdate[])args[3]);
                return;

            default:
                super.dispatchResponse(methodId, args);
                return;
            }
        }
    }

    /** The method id used to dispatch {@link #moveTo} requests. */
    public static final int MOVE_TO = 1;

    // from interface ZoneService
    public void moveTo (Client arg1, int arg2, int arg3, int arg4, ZoneService.ZoneMoveListener arg5)
    {
        ZoneMarshaller.ZoneMoveMarshaller listener5 = new ZoneMarshaller.ZoneMoveMarshaller();
        listener5.listener = arg5;
        sendRequest(arg1, MOVE_TO, new Object[] {
            Integer.valueOf(arg2), Integer.valueOf(arg3), Integer.valueOf(arg4), listener5
        });
    }
}
