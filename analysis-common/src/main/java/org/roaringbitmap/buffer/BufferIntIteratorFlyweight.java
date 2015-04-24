/*
 * (c) the authors
 * Licensed under the Apache License, Version 2.0.
 */

package org.roaringbitmap.buffer;

import org.roaringbitmap.IntIterator;
import org.roaringbitmap.ShortIterator;

/**
 * Fast iterator minimizing the stress on the garbage collector.
 * You can create one reusable instance of this class and then
 * {@link #wrap(ImmutableRoaringBitmap)}
 * 
 * @author Borislav Ivanov
 **/
public class BufferIntIteratorFlyweight implements IntIterator {

    private int hs;

    private ShortIterator iter;

    private MappeableArrayContainerShortIterator arrIter = new MappeableArrayContainerShortIterator();
    
    private MappeableBitmapContainerShortIterator bitmapIter = new MappeableBitmapContainerShortIterator();

    private int pos;

    private ImmutableRoaringBitmap roaringBitmap = null;

    /**
     * Creates an instance that is not ready for iteration. You must first call
     * {@link #wrap(ImmutableRoaringBitmap)}.
     */
    public BufferIntIteratorFlyweight() {

    }

    /**
     * Creates an instance that is ready for iteration.
     * 
     * @param r
     *            bitmap to be iterated over
     */
    public BufferIntIteratorFlyweight(ImmutableRoaringBitmap r) {

    }

    /**
     * Prepares a bitmap for iteration
     * 
     * @param r
     *            bitmap to be iterated over
     */
    public void wrap(ImmutableRoaringBitmap r) {
        this.hs = 0;
        this.pos = 0;
        this.roaringBitmap = r;
        this.nextContainer();
    }

    @Override
    public boolean hasNext() {
        return pos < this.roaringBitmap.highLowContainer.size();
    }

    private void nextContainer() {
        if (pos < this.roaringBitmap.highLowContainer.size()) {

            MappeableContainer container = this.roaringBitmap.highLowContainer
                    .getContainerAtIndex(pos);

            if (container instanceof MappeableBitmapContainer) {
                bitmapIter.wrap((MappeableBitmapContainer) container);
                iter = bitmapIter;
            } else {
                arrIter.wrap((MappeableArrayContainer) container);
                iter = arrIter;
            }

            hs = BufferUtil.toIntUnsigned(this.roaringBitmap.highLowContainer
                    .getKeyAtIndex(pos)) << 16;
        }
    }

    @Override
    public int next() {
        int x = BufferUtil.toIntUnsigned(iter.next()) | hs;
        if (!iter.hasNext()) {
            ++pos;
            nextContainer();
        }
        return x;
    }

    @Override
    public IntIterator clone() {
        try {
            BufferIntIteratorFlyweight x = (BufferIntIteratorFlyweight) super.clone();
            x.iter = this.iter.clone();
            return x;
        } catch (CloneNotSupportedException e) {
            return null;// will not happen
        }
    }

}
