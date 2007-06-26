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

package com.threerings.whirled;

/**
 * A placeholder class that contains a reference to the log object used by
 * the Whirled services.
 */
public class Log
{
    public static com.samskivert.util.Log log =
        new com.samskivert.util.Log("whirled");

    /** Convenience function. */
    public static boolean debug ()
    {
        return (com.samskivert.util.Log.getLevel() ==
                com.samskivert.util.Log.DEBUG);
    }

    /** Convenience function. */
    public static void debug (String message)
    {
        log.debug(message);
    }

    /** Convenience function. */
    public static void info (String message)
    {
        log.info(message);
    }

    /** Convenience function. */
    public static void warning (String message)
    {
        log.warning(message);
    }

    /** Convenience function. */
    public static void logStackTrace (Throwable t)
    {
        log.logStackTrace(com.samskivert.util.Log.WARNING, t);
    }
}
