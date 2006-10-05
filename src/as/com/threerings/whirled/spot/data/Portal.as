//
// $Id: Portal.java 4072 2006-04-28 01:34:02Z ray $
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

package com.threerings.whirled.spot.data {

import com.threerings.util.ClassUtil;
import com.threerings.util.Cloneable;
import com.threerings.util.Hashable;
import com.threerings.util.StringBuilder;

import com.threerings.io.ObjectInputStream;
import com.threerings.io.ObjectOutputStream;
import com.threerings.io.SimpleStreamableObject;

import com.threerings.util.Hashable;

/**
 * Represents an exit to another scene. A body sprite would walk over to a
 * portal's coordinates and then either proceed off of the edge of the
 * display, or open a door and walk through it, or fizzle away in a Star
 * Trekkian transporter style or whatever is appropriate for the game in
 * question. It contains information on the scene to which the body exits
 * when using this portal and the location at which the body sprite should
 * appear in that target scene.
 */
public class Portal extends SimpleStreamableObject
    implements Cloneable, Hashable
{
    /** This portal's unique identifier. */
    public var portalId :int;

    /** The location of the portal.
     * This field is present on client and server, it is streamed specially. */
    public var loc :Location;

    /** The scene identifier of the scene to which a body will exit when
     * they "use" this portal. */
    public var targetSceneId :int;

    /** The portal identifier of the portal at which a body will enter
     * the target scene when they "use" this portal, or -1 to specify
     * that the body enters on the default portal, whatever id it is.  */
    public var targetPortalId :int;

    public function Portal ()
    {
        // nothing needed
    }

    /**
     * Returns a location instance configured with the location and
     * opposite orientation of this portal. This is useful for when a body
     * is entering a scene at a portal and we want them to face the
     * opposite direction (as they are entering via the portal rather than
     * leaving, which is the natural "orientation" of a portal).
     */
    public function getOppLocation () :Location
    {
        return loc.getOpposite();
    }

    // documentation inherited from interface Hashable
    public function hashCode () :int
    {
        return portalId;
    }

    // documentation inherited from interface Cloneable
    public function clone () :Object
    {
        var p :Portal = (ClassUtil.newInstance(this) as Portal);
        p.portalId = portalId;
        p.loc = loc;
        p.targetSceneId = targetSceneId;
        p.targetPortalId = targetPortalId;
        return p;
    }

    // documentation inherited from interface Hashable
    public function equals (other :Object) :Boolean
    {
        return (other is Portal) &&
            ((other as Portal).portalId == portalId);
    }

    /**
     * Returns a location instance configured with the location and
     * orientation of this portal.
     */
    public function getLocation () :Location
    {
        return (loc.clone() as Location);
    }

    /**
     * Returns true if the portal has a potentially valid target scene and
     * portal id (they are not guaranteed to exist, but they are at least
     * potentially valid values rather than 0).
     */
    public function isValid () :Boolean
    {
        return (targetSceneId > 0) &&
            // the target portal must be positive, or -1
            ((targetPortalId > 0) || (targetPortalId == -1));
    }

    // from interface Streamable
    override public function readObject (ins :ObjectInputStream) :void
    {
        super.readObject(ins);
        portalId = ins.readShort();
        loc = (ins.readObject() as Location);
        targetSceneId = ins.readInt();
        targetPortalId = ins.readShort();
    }

    // from interface Streamable
    override public function writeObject (out :ObjectOutputStream) :void
    {
        super.writeObject(out);
        out.writeShort(portalId);
        out.writeObject(loc);
        out.writeInt(targetSceneId);
        out.writeShort(targetPortalId);
    }

    // from SimpleStreamableObject
    override protected function toStringBuilder (buf :StringBuilder): void
    {
        buf.append("id=").append(portalId);
        buf.append(", destScene=").append(targetSceneId);
        buf.append(", loc=").append(loc);
    }
}
}
