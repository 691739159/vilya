//
// $Id: PuzzleConfig.java 3381 2005-03-03 19:36:34Z mdb $
//
// Narya library - tools for developing networked games
// Copyright (C) 2002-2004 Three Rings Design, Inc., All Rights Reserved
// http://www.threerings.net/code/narya/
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

package com.threerings.puzzle.data;

import com.threerings.parlor.game.data.GameConfig;

/**
 * Encapsulates the basic configuration information for a puzzle game.
 */
public abstract class PuzzleConfig extends GameConfig
    implements Cloneable
{
    /**
     * Constructs a blank puzzle config.
     */
    public PuzzleConfig ()
    {
    }

    /**
     * Returns the message bundle identifier for the bundle that should be
     * used to translate the translatable strings used to describe the
     * puzzle config parameters.  The default implementation returns the
     * base puzzle message bundle, but puzzles that have their own message
     * bundle should override this method and return their puzzle-specific
     * bundle identifier.
     */
    public String getBundleName ()
    {
        return PuzzleCodes.PUZZLE_MESSAGE_BUNDLE;
    }
}
