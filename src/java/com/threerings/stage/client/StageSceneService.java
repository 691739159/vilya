//
// $Id$

package com.threerings.stage.client;

import com.threerings.presents.client.Client;
import com.threerings.presents.client.InvocationService;

import com.threerings.miso.data.ObjectInfo;

/**
 * Provides services relating to Stage scenes.
 */
public interface StageSceneService extends InvocationService
{
    /**
     * Requests to add the supplied object to the current scene.
     */
    public void addObject (Client client, ObjectInfo info,
                           ConfirmListener listener);
    
    /**
     * Requests to remove the supplied objects from the current scene.
     */
    public void removeObjects (Client client, ObjectInfo[] info,
                               ConfirmListener listener);
}
