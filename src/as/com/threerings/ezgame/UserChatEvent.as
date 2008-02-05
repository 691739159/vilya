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

package com.threerings.ezgame {

import flash.events.Event;

/**
 * Dispatched when a player speaks.
 */
public class UserChatEvent extends Event
{
    /**
     * The type of a property change event.
     * @eventType UserChat
     */
    public static const USER_CHAT :String = "UserChat";

    /**
     * Get the name of the user who spoke.
     */
    public function get speaker () :int
    {
        return _speaker;
    }

    /**
     * Get the content of the chat.
     */
    public function get message () :Object
    {
        return _message;
    }

    public function UserChatEvent (speaker :int, message :String)
    {
        super(USER_CHAT);
        _speaker = speaker;
        _message = message;
    }

    override public function toString () :String
    {
        return "[UserChatEvent speaker=" + _speaker + ", message=" + _message + "]";
    }

    override public function clone () :Event
    {
        return new UserChatEvent(_speaker, _message);
    }

    /** @private */
    protected var _speaker: int;

    /** @private */
    protected var _message :String;
}
}
