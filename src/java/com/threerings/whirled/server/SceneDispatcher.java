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

package com.threerings.whirled.server;

import javax.annotation.Generated;

import com.threerings.presents.data.ClientObject;
import com.threerings.presents.server.InvocationDispatcher;
import com.threerings.presents.server.InvocationException;
import com.threerings.whirled.client.SceneService;
import com.threerings.whirled.data.SceneMarshaller;

/**
 * Dispatches requests to the {@link SceneProvider}.
 */
@Generated(value={"com.threerings.presents.tools.GenServiceTask"},
           comments="Derived from SceneService.java.")
public class SceneDispatcher extends InvocationDispatcher<SceneMarshaller>
{
    /**
     * Creates a dispatcher that may be registered to dispatch invocation
     * service requests for the specified provider.
     */
    public SceneDispatcher (SceneProvider provider)
    {
        this.provider = provider;
    }

    @Override
    public SceneMarshaller createMarshaller ()
    {
        return new SceneMarshaller();
    }

    @Override
    public void dispatchRequest (
        ClientObject source, int methodId, Object[] args)
        throws InvocationException
    {
        switch (methodId) {
        case SceneMarshaller.MOVE_TO:
            ((SceneProvider)provider).moveTo(
                source, ((Integer)args[0]).intValue(), ((Integer)args[1]).intValue(), (SceneService.SceneMoveListener)args[2]
            );
            return;

        default:
            super.dispatchRequest(source, methodId, args);
            return;
        }
    }
}
