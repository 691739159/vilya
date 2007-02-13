//
// $Id$
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

package com.threerings.parlor.game.data;

import java.util.ArrayList;
import java.util.List;

import com.threerings.crowd.data.PlaceConfig;
import com.threerings.util.Name;

import com.threerings.parlor.client.TableConfigurator;
import com.threerings.parlor.game.client.GameConfigurator;

/**
 * The game config class encapsulates the configuration information for a
 * particular type of game. The hierarchy of game config objects mimics the
 * hierarchy of game managers and controllers. Both the game manager and game
 * controller are provided with the game config object when the game is
 * created.
 *
 * <p> The game config object is also the mechanism used to instantiate the
 * appropriate game manager and controller. Every game must have an associated
 * game config derived class that overrides {@link #createController} and
 * {@link #getManagerClassName}, returning the appropriate game controller and
 * manager class for that game. Thus the entire chain of events that causes a
 * particular game to be created is the construction of the appropriate game
 * config instance which is provided to the server as part of an invitation or
 * via some other matchmaking mechanism.
 */
public abstract class GameConfig extends PlaceConfig implements Cloneable
{
    /** Game type constant: a game that is started with a list of players,
     * and those are the only players that may play. */
    public static final byte SEATED_GAME = 0;

    /** Game type constant: a game that starts immediately, but only has
     * a certain number of player slots. Users enter the game room, and
     * then choose where to sit. */
    public static final byte SEATED_CONTINUOUS = 1;

    /** Game type constant: a game that starts immediately, and every
     * user that enters is a player. */
    public static final byte PARTY = 2;

    /** The usernames of the players involved in this game, or an empty
     * array if such information is not needed by this particular game. */
    public Name[] players = new Name[0];

    /** Indicates whether or not this game is rated. */
    public boolean rated = true;

    /** Configurations for AIs to be used in this game. Slots with real
     * players should be null and slots with AIs should contain
     * configuration for those AIs. A null array indicates no use of AIs
     * at all. */
    public GameAI[] ais = new GameAI[0];

    /**
     * Get the type of game.
     */
    public byte getGameType ()
    {
        return SEATED_GAME;
    }

    /**
     * Returns the message bundle identifier for the bundle that should be
     * used to translate the translatable strings used to describe the
     * game config parameters.
     */
    public abstract String getBundleName ();

    /**
     * Creates a configurator that can be used to create a user interface
     * for configuring this instance prior to starting the game. If no
     * configuration is necessary, this method should return null.
     */
    public abstract GameConfigurator createConfigurator ();

    /**
     * Creates a table configurator for initializing 'table' properties
     * of the game. The default implementation returns null.
     */
    public TableConfigurator createTableConfigurator ()
    {
        return null;
    }

    /**
     * Returns a translatable label describing this game.
     */
    public String getGameName ()
    {
        // the whole getRatingTypeId(), getGameName(), getBundleName()
        // business should be cleaned up. we should have getGameIdent()
        // and everything should have a default implementation using that
        return "m." + getBundleName();
    }

    /**
     * Returns the game rating type, if the system uses such things.
     */
    public byte getRatingTypeId ()
    {
        return (byte)-1;
    }

    /**
     * Returns a List of strings that describe the configuration of this
     * game. Default implementation returns an empty list.
     */
    public List<String> getDescription ()
    {
        return new ArrayList<String>(); // nothing by default
    }

    /**
     * Returns true if this game config object is equal to the supplied
     * object (meaning it is also a game config object and its
     * configuration settings are the same as ours).
     */
    public boolean equals (Object other)
    {
        // make sure they're of the same class
        if (other.getClass() == this.getClass()) {
            GameConfig that = (GameConfig) other;
            return this.rated == that.rated;

        } else {
            return false;
        }
    }

    /**
     * Computes a hashcode for this game config object that supports our
     * {@link #equals} implementation. Objects that are equal should have
     * the same hashcode.
     */
    public int hashCode ()
    {
        // look ma, it's so sophisticated!
        return getClass().hashCode() + (rated ? 1 : 0);
    }

    // documentation inherited
    public Object clone ()
    {
        try {
            return super.clone();
        } catch (CloneNotSupportedException cnse) {
            throw new RuntimeException(cnse);
        }
    }
}
