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

package com.threerings.micasa.simulator.server;

import com.threerings.micasa.simulator.data.SimulatorMarshaller;
import com.threerings.parlor.game.data.GameConfig;
import com.threerings.presents.data.ClientObject;
import com.threerings.presents.server.InvocationDispatcher;
import com.threerings.presents.server.InvocationException;

/**
 * Dispatches requests to the {@link SimulatorProvider}.
 */
public class SimulatorDispatcher extends InvocationDispatcher<SimulatorMarshaller>
{
    /**
     * Creates a dispatcher that may be registered to dispatch invocation
     * service requests for the specified provider.
     */
    public SimulatorDispatcher (SimulatorProvider provider)
    {
        this.provider = provider;
    }

    @Override // documentation inherited
    public SimulatorMarshaller createMarshaller ()
    {
        return new SimulatorMarshaller();
    }

    @Override // documentation inherited
    public void dispatchRequest (
        ClientObject source, int methodId, Object[] args)
        throws InvocationException
    {
        switch (methodId) {
        case SimulatorMarshaller.CREATE_GAME:
            ((SimulatorProvider)provider).createGame(
                source, (GameConfig)args[0], (String)args[1], ((Integer)args[2]).intValue()
            );
            return;

        default:
            super.dispatchRequest(source, methodId, args);
            return;
        }
    }
}
