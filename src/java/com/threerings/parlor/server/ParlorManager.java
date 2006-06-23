//
// $Id: ParlorManager.java 3758 2005-11-10 23:18:58Z mdb $
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

package com.threerings.parlor.server;

import com.samskivert.util.HashIntMap;
import com.threerings.util.Name;

import com.threerings.presents.server.InvocationException;
import com.threerings.presents.server.InvocationManager;

import com.threerings.crowd.data.BodyObject;
import com.threerings.crowd.server.PlaceRegistry;

import com.threerings.parlor.Log;
import com.threerings.parlor.data.ParlorCodes;
import com.threerings.parlor.game.data.GameConfig;
import com.threerings.parlor.game.server.GameManager;

/**
 * The parlor manager is responsible for the parlor services in
 * aggregate. This includes maintaining the registry of active games,
 * handling the necessary coordination for the matchmaking services and
 * anything else that falls outside the scope of an actual in-progress
 * game.
 */
public class ParlorManager
    implements ParlorCodes
{
    /** Provides the server-side implementation of the parlor services. */
    public ParlorProvider parprov;

    /**
     * Initializes the parlor manager. This should be called by the server
     * that is making use of the parlor services on the single instance of
     * parlor manager that it has created.
     *
     * @param invmgr a reference to the invocation manager in use by this
     * server.
     * @param plreg a reference to the place registry to be used by the
     * parlor manager when creating game places.
     */
    public void init (InvocationManager invmgr, PlaceRegistry plreg)
    {
        // create and register our invocation provider
        parprov = new ParlorProvider(this);
        invmgr.registerDispatcher(new ParlorDispatcher(parprov), true);

        // keep this for later
        _plreg = plreg;
    }

    /**
     * Issues an invitation from the <code>inviter</code> to the
     * <code>invitee</code> for a game as described by the supplied config
     * object.
     *
     * @param inviter the player initiating the invitation.
     * @param invitee the player being invited.
     * @param config the configuration of the game being proposed.
     *
     * @return the invitation identifier for the newly created invitation
     * record.
     *
     * @exception InvocationException thrown if the invitation was not
     * able to be processed for some reason (like the invited player has
     * requested not to be disturbed). The explanation will be provided in
     * the message data of the exception.
     */
    public int invite (BodyObject inviter, BodyObject invitee,
                       GameConfig config)
        throws InvocationException
    {
//          Log.info("Received invitation request [inviter=" + inviter +
//                   ", invitee=" + invitee + ", config=" + config + "].");

        // here we should check to make sure the invitee hasn't muted the
        // inviter, and that the inviter isn't shunned and all that other
        // access control type stuff

        // create a new invitation record for this invitation
        Invitation invite = new Invitation(inviter, invitee, config);

        // stick it in the pending invites table
        _invites.put(invite.inviteId, invite);

        // deliver an invite notification to the invitee
        ParlorSender.sendInvite(
            invitee, invite.inviteId, inviter.getVisibleName(), config);

        // and let the caller know the invite id we assigned
        return invite.inviteId;
    }

    /**
     * Effects a response to an invitation (accept, refuse or counter),
     * made by the specified source user with the specified arguments.
     *
     * @param source the body object of the user that is issuing this
     * response.
     * @param inviteId the identifier of the invitation to which we are
     * responding.
     * @param code the response code (either {@link
     * #INVITATION_ACCEPTED}, {@link #INVITATION_REFUSED} or {@link
     * #INVITATION_COUNTERED}).
     * @param arg the argument that goes along with the response: an
     * explanatory message in the case of a refusal (the empty string, not
     * null, if no message was provided) or the new game configuration in
     * the case of a counter-invitation.
     */
    public void respondToInvite (BodyObject source, int inviteId, int code,
                                 Object arg)
    {
        // look up the invitation
        Invitation invite = (Invitation)_invites.get(inviteId);
        if (invite == null) {
            Log.warning("Requested to respond to non-existent invitation " +
                        "[source=" + source + ", inviteId=" + inviteId +
                        ", code=" + code + ", arg=" + arg + "].");
            return;
        }

        // make sure this response came from the proper person
        if (source != invite.invitee) {
            Log.warning("Got response from non-invitee [source=" + source +
                        ", invite=" + invite + ", code=" + code +
                        ", arg=" + arg + "].");
            return;
        }

        // let the other user know that a response was made to this
        // invitation
        ParlorSender.sendInviteResponse(
            invite.inviter, invite.inviteId, code, arg);

        switch (code) {
        case INVITATION_ACCEPTED:
            // the invitation was accepted, so we'll need to start up the
            // game and get the necessary balls rolling
            processAcceptedInvitation(invite);
            // and remove the invitation from the pending table
            _invites.remove(inviteId);
            break;

        case INVITATION_REFUSED:
            // remove the invitation record from the pending table as it
            // is no longer pending
            _invites.remove(inviteId);
            break;

        case INVITATION_COUNTERED:
            // swap control of the invitation to the invitee
            invite.swapControl();
            break;

        default:
            Log.warning("Requested to respond to invitation with " +
                        "unknown response code [source=" + source +
                        ", invite=" + invite + ", code=" + code +
                        ", arg=" + arg + "].");
            break;
        }
    }

    /**
     * Requests that an outstanding invitation be cancelled.
     *
     * @param source the body object of the user that is making the
     * request.
     * @param inviteId the unique id of the invitation to be cancelled.
     */
    public void cancelInvite (BodyObject source, int inviteId)
    {
        // TBD
    }

    /**
     * Starts up and configures the game manager for an accepted
     * invitation.
     */
    protected void processAcceptedInvitation (Invitation invite)
    {
        try {
            Log.info("Creating game manager [invite=" + invite + "].");

            // configure the game config with the player info
            invite.config.players = new Name[] {
                invite.invitee.username, invite.inviter.username };

            // create the game manager and begin it's initialization
            // process. the game manager will take care of notifying the
            // players that the game has been created once it has been
            // started up (which is done by the place registry once the
            // game object creation has completed)
            GameManager gmgr = (GameManager)
                _plreg.createPlace(invite.config, null);

        } catch (Exception e) {
            Log.warning("Unable to create game manager [invite=" + invite +
                        ", error=" + e + "].");
            Log.logStackTrace(e);
        }
    }

    /**
     * The invitation record is used by the parlor manager to keep track
     * of pending invitations.
     */
    protected static class Invitation
    {
        /** The unique identifier for this invitation. */
        public int inviteId = _nextInviteId++;

        /** The person proposing the invitation. */
        public BodyObject inviter;

        /** The person to whom the invitation is proposed. */
        public BodyObject invitee;

        /** The configuration of the game being proposed. */
        public GameConfig config;

        /**
         * Constructs a new invitation with the specified participants and
         * configuration.
         */
        public Invitation (BodyObject inviter, BodyObject invitee,
                           GameConfig config)
        {
            this.inviter = inviter;
            this.invitee = invitee;
            this.config = config;
        }

        /**
         * Swaps the inviter and invitee which is necessary when the
         * invitee responds with a counter-invitation.
         */
        public void swapControl ()
        {
            BodyObject tmp = inviter;
            inviter = invitee;
            invitee = tmp;
        }
    }

    /** The place registry with which we operate. */
    protected PlaceRegistry _plreg;

    /** The table of pending invitations. */
    protected HashIntMap _invites = new HashIntMap();

    /** A counter used to generate unique identifiers for invitation
     * records. */
    protected static int _nextInviteId = 0;
}
