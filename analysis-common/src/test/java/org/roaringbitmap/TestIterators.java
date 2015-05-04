/*
 * (c) the authors
 * Licensed under the Apache License, Version 2.0.
 */


package org.roaringbitmap;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import org.junit.Assert;
import org.junit.Test;
import org.roaringbitmap.BitmapContainer;
import org.roaringbitmap.IntIterator;
import org.roaringbitmap.RoaringBitmap;
import org.roaringbitmap.ShortIterator;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;

public class TestIterators {
    @Test
    public void testEmptyIteration() {
        Assert.assertFalse(RoaringBitmap.bitmapOf().iterator().hasNext());
        Assert.assertFalse(RoaringBitmap.bitmapOf().getIntIterator().hasNext());
        Assert.assertFalse(RoaringBitmap.bitmapOf().getReverseIntIterator().hasNext());
    }

    @Test
    public void testSmallIteration() {
        RoaringBitmap bitmap = RoaringBitmap.bitmapOf(1, 2, 3);

        final List<Integer> iteratorCopy = ImmutableList.copyOf(bitmap.iterator());
        final List<Integer> intIteratorCopy = asList(bitmap.getIntIterator());
        final List<Integer> reverseIntIteratorCopy = asList(bitmap.getReverseIntIterator());

        Assert.assertEquals(ImmutableList.of(1, 2, 3), iteratorCopy);
        Assert.assertEquals(ImmutableList.of(1, 2, 3), intIteratorCopy);
        Assert.assertEquals(ImmutableList.of(3, 2, 1), reverseIntIteratorCopy);
    }

    @Test
    public void testBitmapIteration() {
        final BitmapContainer bits = new BitmapContainer(new long[]{0x1l, 1l << 63}, 2);

        Assert.assertEquals(asList(bits.getShortIterator()), ImmutableList.of(0, 127));
        Assert.assertEquals(asList(bits.getReverseShortIterator()), ImmutableList.of(127, 0));
    }

    @Test
    public void testIteration() {
        final Random source = new Random(0xcb000a2b9b5bdfb6l);
        final int[] data = takeSortedAndDistinct(source, 450000);
        RoaringBitmap bitmap = RoaringBitmap.bitmapOf(data);

        final List<Integer> iteratorCopy = ImmutableList.copyOf(bitmap.iterator());
        final List<Integer> intIteratorCopy = asList(bitmap.getIntIterator());
        final List<Integer> reverseIntIteratorCopy = asList(bitmap.getReverseIntIterator());

        Assert.assertEquals(bitmap.getCardinality(), iteratorCopy.size());
        Assert.assertEquals(bitmap.getCardinality(), intIteratorCopy.size());
        Assert.assertEquals(bitmap.getCardinality(), reverseIntIteratorCopy.size());
        Assert.assertEquals(Ints.asList(data), iteratorCopy);
        Assert.assertEquals(Ints.asList(data), intIteratorCopy);
        Assert.assertEquals(Lists.reverse(Ints.asList(data)), reverseIntIteratorCopy);
    }

    private static int[] takeSortedAndDistinct(Random source, int count) {
        LinkedHashSet<Integer> ints = new LinkedHashSet<Integer>(count);
        for (int size = 0; size < count; size++) {
            int next;
            do {
                next = Math.abs(source.nextInt());
            } while (!ints.add(next));
        }
        int[] unboxed = Ints.toArray(ints);
        Arrays.sort(unboxed);
        return unboxed;
    }

    private static List<Integer> asList(IntIterator ints) {
        int[] values = new int[10];
        int size = 0;
        while (ints.hasNext()) {
            if (!(size < values.length)) {
                values = Arrays.copyOf(values, values.length * 2);
            }
            values[size++] = ints.next();
        }
        return Ints.asList(Arrays.copyOf(values, size));
    }

    private static List<Integer> asList(final ShortIterator shorts) {
        return asList(new IntIterator() {
            @Override
            public boolean hasNext() {
                return shorts.hasNext();
            }

            @Override
            public int next() {
                return shorts.next();
            }

            @SuppressWarnings("CloneDoesntCallSuperClone")
            @Override
            public IntIterator clone() {
                throw new UnsupportedOperationException();
            }
        });
    }
}
