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

package com.threerings.stage.server;

import com.threerings.miso.data.ObjectInfo;
import com.threerings.presents.client.InvocationService;
import com.threerings.presents.data.ClientObject;
import com.threerings.presents.server.InvocationDispatcher;
import com.threerings.presents.server.InvocationException;
import com.threerings.stage.data.StageSceneMarshaller;

/**
 * Dispatches requests to the {@link StageSceneProvider}.
 */
public class StageSceneDispatcher extends InvocationDispatcher<StageSceneMarshaller>
{
    /**
     * Creates a dispatcher that may be registered to dispatch invocation
     * service requests for the specified provider.
     */
    public StageSceneDispatcher (StageSceneProvider provider)
    {
        this.provider = provider;
    }

    @Override // documentation inherited
    public StageSceneMarshaller createMarshaller ()
    {
        return new StageSceneMarshaller();
    }

    @SuppressWarnings("unchecked")
    @Override // documentation inherited
    public void dispatchRequest (
        ClientObject source, int methodId, Object[] args)
        throws InvocationException
    {
        switch (methodId) {
        case StageSceneMarshaller.ADD_OBJECT:
            ((StageSceneProvider)provider).addObject(
                source, (ObjectInfo)args[0], (InvocationService.ConfirmListener)args[1]
            );
            return;

        case StageSceneMarshaller.REMOVE_OBJECTS:
            ((StageSceneProvider)provider).removeObjects(
                source, (ObjectInfo[])args[0], (InvocationService.ConfirmListener)args[1]
            );
            return;

        default:
            super.dispatchRequest(source, methodId, args);
            return;
        }
    }
}
