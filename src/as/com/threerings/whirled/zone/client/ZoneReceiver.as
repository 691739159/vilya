package com.threerings.whirled.zone.client {

import com.threerings.presents.client.InvocationReceiver;

/**
 * Defines, for the zone services, a set of notifications delivered asynchronously by the server
 * to the client.
 */
public interface ZoneReceiver extends InvocationReceiver
{
    /**
     * Used to communicate a required move notification to the client. The server will have
     * removed the client from their existing scene and the client is then responsible for
     * generating a {@link ZoneService#moveTo} request to move to the new scene in the specified
     * zone.
     */
    function forcedMove (zoneId :int, sceneId :int) :void;
}
}
