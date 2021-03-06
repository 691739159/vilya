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

package com.threerings.parlor.rating.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Random;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the {@link Percentiler} class.
 */
public class PercentilerTest
{
    @Test public void testTiler ()
    {
        Percentiler tiler = createGaussian();

        // check some basic stuff
        assertTrue(tiler.getMinScore() < tiler.getMaxScore());

        // dump the tiler
        StringWriter out = new StringWriter();
        tiler.dump(new PrintWriter(out));
        String run1 = out.toString();

        // serialize, unserialize and dump again
        Percentiler t2 = new Percentiler(tiler.toBytes());
        out = new StringWriter();
        t2.dump(new PrintWriter(out));
        String run2 = out.toString();

        // should be equal
        assertEquals(run1, run2);
    }

    @Test public void testGetPercentile ()
    {
        Percentiler tiler = createGaussian();

        assertEquals(0, tiler.getPercentile(tiler.getMinScore()));
        assertEquals(0, tiler.getPercentile(tiler.getMinScore()-1));
        assertEquals(100, tiler.getPercentile(tiler.getMaxScore()));
        assertEquals(100, tiler.getPercentile(tiler.getMaxScore()+1));
    }

    protected Percentiler createGaussian ()
    {
        // create a percentiler
        Percentiler tiler = new Percentiler();
        Random rando = new Random();

        // add some random values
        for (int ii = 0; ii < 500; ii++) {
            tiler.recordValue((float)rando.nextGaussian() + 5.0f, false);
        }
        tiler.recomputePercentiles();
        return tiler;
    }
}
