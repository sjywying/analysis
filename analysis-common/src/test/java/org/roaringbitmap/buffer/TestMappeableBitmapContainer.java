/*
 * (c) the authors
 * Licensed under the Apache License, Version 2.0.
 */

package org.roaringbitmap.buffer;

import org.junit.Test;
import org.roaringbitmap.buffer.MappeableBitmapContainer;

import java.nio.LongBuffer;

public class TestMappeableBitmapContainer {

    @Test(expected = IndexOutOfBoundsException.class)
    public void testPreviousTooSmall() {
        emptyContainer().prevSetBit(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testPreviousTooLarge() {
        emptyContainer().prevSetBit(Short.MAX_VALUE + 1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testNextTooSmall() {
        emptyContainer().nextSetBit(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testNextTooLarge() {
        emptyContainer().nextSetBit(Short.MAX_VALUE + 1);
    }
    private static MappeableBitmapContainer emptyContainer() {
        return new MappeableBitmapContainer(0, LongBuffer.allocate(1));
    }
}
