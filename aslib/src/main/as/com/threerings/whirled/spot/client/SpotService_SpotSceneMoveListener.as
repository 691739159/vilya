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

package com.threerings.whirled.spot.client {

import com.threerings.io.TypedArray;

import com.threerings.presents.client.InvocationService_InvocationListener;

import com.threerings.crowd.data.PlaceConfig;

import com.threerings.whirled.data.SceneModel;

/**
 * An ActionScript version of the Java SpotService_SpotSceneMoveListener interface.
 */
public interface SpotService_SpotSceneMoveListener
    extends InvocationService_InvocationListener
{
    // from Java SpotService_SpotSceneMoveListener
    function moveRequiresServerSwitch (arg1 :String, arg2 :TypedArray /* of int */) :void

    // from Java SpotService_SpotSceneMoveListener
    function moveSucceeded (arg1 :int, arg2 :PlaceConfig) :void

    // from Java SpotService_SpotSceneMoveListener
    function moveSucceededWithScene (arg1 :int, arg2 :PlaceConfig, arg3 :SceneModel) :void

    // from Java SpotService_SpotSceneMoveListener
    function moveSucceededWithUpdates (arg1 :int, arg2 :PlaceConfig, arg3 :TypedArray /* of class com.threerings.whirled.data.SceneUpdate */) :void

    // from Java SpotService_SpotSceneMoveListener
    function requestCancelled () :void
}
}
