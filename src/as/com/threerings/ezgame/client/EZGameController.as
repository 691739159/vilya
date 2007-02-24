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

package com.threerings.ezgame.client {

import flash.events.Event;

import com.threerings.util.Name;

import com.threerings.crowd.client.PlaceView;
import com.threerings.crowd.data.PlaceConfig;
import com.threerings.crowd.data.PlaceObject;
import com.threerings.crowd.util.CrowdContext;

import com.threerings.parlor.game.client.GameController;

import com.threerings.parlor.turn.client.TurnGameController;
import com.threerings.parlor.turn.client.TurnGameControllerDelegate;

import com.threerings.ezgame.data.EZGameObject;

/**
 * A controller for flash games.
 */
public class EZGameController extends GameController
    implements TurnGameController
{
    /**
     */
    public function EZGameController ()
    {
        addDelegate(_turnDelegate = new TurnGameControllerDelegate(this));
    }

    override public function willEnterPlace (plobj :PlaceObject) :void
    {
        _ezObj = (plobj as EZGameObject);

        super.willEnterPlace(plobj);
    }

    override public function didLeavePlace (plobj :PlaceObject) :void
    {
        super.didLeavePlace(plobj);

        _ezObj = null;
    }

    // from TurnGameController
    public function turnDidChange (turnHolder :Name) :void
    {
        _panel.backend.turnDidChange();
    }

    override protected function gameDidStart () :void
    {
        super.gameDidStart();

        _panel.backend.gameDidStart();
    }

    override protected function gameDidEnd () :void
    {
        super.gameDidEnd();

        _panel.backend.gameDidEnd();
    }

    override protected function createPlaceView (ctx :CrowdContext) :PlaceView
    {
        return new EZGamePanel(ctx, this);
    }

    override protected function didInit () :void
    {
        super.didInit();
        // retain a casted reference to our panel
        _panel = (_view as EZGamePanel);
    }

    protected var _ezObj :EZGameObject;

    protected var _turnDelegate :TurnGameControllerDelegate;

    protected var _panel :EZGamePanel;
}
}
