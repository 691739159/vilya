//
// $Id$
//
// Vilya library - tools for developing networked games
// Copyright (C) 2002-2007 Three Rings Design, Inc., All Rights Reserved
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

package com.threerings.whirled.zone.server;

import com.threerings.presents.data.ClientObject;
import com.threerings.presents.data.InvocationMarshaller;
import com.threerings.presents.server.InvocationDispatcher;
import com.threerings.presents.server.InvocationException;
import com.threerings.whirled.zone.client.ZoneService;
import com.threerings.whirled.zone.data.ZoneMarshaller;

/**
 * Dispatches requests to the {@link ZoneProvider}.
 */
public class ZoneDispatcher extends InvocationDispatcher<ZoneMarshaller>
{
    /**
     * Creates a dispatcher that may be registered to dispatch invocation
     * service requests for the specified provider.
     */
    public ZoneDispatcher (ZoneProvider provider)
    {
        this.provider = provider;
    }

    @Override // documentation inherited
    public ZoneMarshaller createMarshaller ()
    {
        return new ZoneMarshaller();
    }

    @SuppressWarnings("unchecked")
    @Override // documentation inherited
    public void dispatchRequest (
        ClientObject source, int methodId, Object[] args)
        throws InvocationException
    {
        switch (methodId) {
        case ZoneMarshaller.MOVE_TO:
            ((ZoneProvider)provider).moveTo(
                source,
                ((Integer)args[0]).intValue(), ((Integer)args[1]).intValue(), ((Integer)args[2]).intValue(), (ZoneService.ZoneMoveListener)args[3]
            );
            return;

        default:
            super.dispatchRequest(source, methodId, args);
            return;
        }
    }
}
