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

package com.threerings.whirled.zone.data {

import com.threerings.util.Name;

import com.threerings.io.ObjectInputStream;
import com.threerings.io.ObjectOutputStream;
import com.threerings.io.SimpleStreamableObject;
import com.threerings.io.TypedArray;

/**
 * The zone summary contains information on a zone, including its name and
 * summary info on all of the scenes in this zone (which can be used to
 * generate a map of the zone on the client).
 */
public class ZoneSummary extends SimpleStreamableObject
{
    /** The zone's fully qualified unique identifier. */
    public var zoneId :int;

    /** The name of the zone. */
    public var name :Name;

    /** The summary information for all of the scenes in the zone. */
    public var scenes :TypedArray;

    public function ZoneSummary ()
    {
        // nothing needed
    }

    /**
     * Generates a string representation of this instance.
     */
    override public function toString () :String
    {
        return "[zoneId=" + zoneId + ", name=" + name + "]";
    }

    // from interface Streamable
    override public function readObject (ins :ObjectInputStream) :void
    {
        super.readObject(ins);
        zoneId = ins.readInt();
        name = Name(ins.readObject());
        scenes = TypedArray(ins.readObject());
    }

    // from interface Streamable
    override public function writeObject (out :ObjectOutputStream) :void
    {
        super.writeObject(out);
        out.writeInt(zoneId);
        out.writeObject(name);
        out.writeObject(scenes);
    }
}
}
