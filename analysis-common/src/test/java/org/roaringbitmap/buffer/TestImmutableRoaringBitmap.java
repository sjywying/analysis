/*
 * (c) the authors
 * Licensed under the Apache License, Version 2.0.
 */

package org.roaringbitmap.buffer;

import org.junit.Assert;
import org.junit.Test;
import org.roaringbitmap.buffer.ImmutableRoaringBitmap;
import org.roaringbitmap.buffer.MappeableArrayContainer;
import org.roaringbitmap.buffer.MappeableBitmapContainer;
import org.roaringbitmap.buffer.MutableRoaringBitmap;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Random;

/**
 * Generic testing of the roaring bitmaps
 */
@SuppressWarnings({"static-method", "javadoc"})
public class TestImmutableRoaringBitmap {
    
    @Test
    public void testSerializeImmutable() throws IOException {
        MutableRoaringBitmap mr = new MutableRoaringBitmap();
        mr.add(5);
        ByteBuffer buffer = serializeRoaring(mr);
        
        buffer.rewind();
        buffer = serializeRoaring(new ImmutableRoaringBitmap(buffer));

        buffer.rewind();
        ImmutableRoaringBitmap ir = new ImmutableRoaringBitmap(buffer);
        Assert.assertTrue(ir.contains(5));
    }

    @Test
    public void testProperSerialization() throws IOException {
        final int SIZE = 500;
        final Random rand = new Random(0);
        for (int i = 0; i < SIZE; ++i) {
            MutableRoaringBitmap r = new MutableRoaringBitmap();
            for (int k = 0; k < 500000; ++k) {
                if (rand.nextDouble() < .5) {
                    r.add(k);
                }
            }
            ByteBuffer b = ByteBuffer.allocate(r.serializedSizeInBytes());
            r.serialize(new DataOutputStream(new OutputStream() {
                ByteBuffer mBB;

                OutputStream init(final ByteBuffer mbb) {
                    mBB = mbb;
                    return this;
                }

                public void close() {
                }

                public void flush() {
                }

                public void write(final int b) {
                    mBB.put((byte) b);
                }

                public void write(final byte[] b) {
                    mBB.put(b);
                }

                public void write(final byte[] b, final int off, final int l) {
                    mBB.put(b, off, l);
                }
            }.init(b)));
            b.flip();
            ImmutableRoaringBitmap irb = new ImmutableRoaringBitmap(b);
            Assert.assertTrue(irb.equals(r));
            Assert.assertTrue(irb.getCardinality() == r.getCardinality());
        }
    }
	
    @Test
    public void testContains() throws IOException {
        System.out.println("test contains");
        MutableRoaringBitmap mr = new MutableRoaringBitmap();
        for(int k = 0; k<1000;++k) {
            mr.add(17*k);
        }
        ByteBuffer buffer = serializeRoaring(mr);
        buffer.rewind();
        ImmutableRoaringBitmap ir = new ImmutableRoaringBitmap(buffer);
        for(int k = 0; k<17*1000;++k) {
            Assert.assertTrue(ir.contains(k) == (k/17*17==k));
        }
    }
    
    @SuppressWarnings("resource")
	static ByteBuffer serializeRoaring(ImmutableRoaringBitmap mrb) throws IOException {
            byte[] backingArray = new byte[mrb.serializedSizeInBytes()+1024];
			ByteBuffer outbb = ByteBuffer.wrap(backingArray, 1024, mrb.serializedSizeInBytes()).slice();
			DataOutputStream dos = new DataOutputStream(new OutputStream(){
	        ByteBuffer mBB;
	        OutputStream init(ByteBuffer mbb) {mBB=mbb; return this;}
	            public void write(int b) {mBB.put((byte) b);}
	            public void write(byte[] b) {}
	            public void write(byte[] b, int off, int l) { mBB.put(b,off,l); }
	        }.init(outbb));
			mrb.serialize(dos);
			dos.close();

			return outbb;
	}

	@Test
	public void testHash() {
		MutableRoaringBitmap rbm1 = new MutableRoaringBitmap();
		rbm1.add(17);
		MutableRoaringBitmap rbm2 = new MutableRoaringBitmap();
		rbm2.add(17);
		Assert.assertTrue(rbm1.hashCode() == rbm2.hashCode());
		rbm2 = rbm1.clone();
		Assert.assertTrue(rbm1.hashCode() == rbm2.hashCode());
	}
	
    @Test
    public void ANDNOTtest() {
        final MutableRoaringBitmap rr = new MutableRoaringBitmap();
        for (int k = 4000; k < 4256; ++k)
            rr.add(k);
        for (int k = 65536; k < 65536 + 4000; ++k)
            rr.add(k);
        for (int k = 3 * 65536; k < 3 * 65536 + 9000; ++k)
            rr.add(k);
        for (int k = 4 * 65535; k < 4 * 65535 + 7000; ++k)
            rr.add(k);
        for (int k = 6 * 65535; k < 6 * 65535 + 10000; ++k)
            rr.add(k);
        for (int k = 8 * 65535; k < 8 * 65535 + 1000; ++k)
            rr.add(k);
        for (int k = 9 * 65535; k < 9 * 65535 + 30000; ++k)
            rr.add(k);

        final MutableRoaringBitmap rr2 = new MutableRoaringBitmap();
        for (int k = 4000; k < 4256; ++k) {
            rr2.add(k);
        }
        for (int k = 65536; k < 65536 + 4000; ++k) {
            rr2.add(k);
        }
        for (int k = 3 * 65536 + 2000; k < 3 * 65536 + 6000; ++k) {
            rr2.add(k);
        }
        for (int k = 6 * 65535; k < 6 * 65535 + 1000; ++k) {
            rr2.add(k);
        }
        for (int k = 7 * 65535; k < 7 * 65535 + 1000; ++k) {
            rr2.add(k);
        }
        for (int k = 10 * 65535; k < 10 * 65535 + 5000; ++k) {
            rr2.add(k);
        }
        final MutableRoaringBitmap correct = MutableRoaringBitmap.andNot(rr, rr2);
        rr.andNot(rr2);
        Assert.assertTrue(correct.equals(rr));
    }
    
    @Test
    public void MappeableContainersAccessTest() throws IOException {
        MutableRoaringBitmap mr = new MutableRoaringBitmap();
        for (int k = 4000; k < 4256; ++k)
            mr.add(k);
        for (int k = (1 << 16); k < (1 << 16) + 4000; ++k)
            mr.add(k);
        for (int k = (1 << 18); k < (1 << 18) + 9000; ++k)
            mr.add(k);
        for (int k = (1 << 19); k < (1 << 19) + 7000; ++k)
            mr.add(k);
        for (int k = (1 << 20); k < (1 << 20) + 10000; ++k)
            mr.add(k);
        for (int k = (1 << 23); k < (1 << 23) + 1000; ++k)
            mr.add(k);
        for (int k = (1 << 24); k < (1 << 24) + 30000; ++k)
            mr.add(k);
        ByteBuffer buffer = serializeRoaring(mr);
        buffer.rewind();
        ImmutableRoaringBitmap ir = new ImmutableRoaringBitmap(buffer);
        mr = ir.toMutableRoaringBitmap();
        Assert.assertTrue((Object)(mr.getMappeableRoaringArray().getContainerAtIndex(0)) instanceof MappeableArrayContainer);
        Assert.assertTrue((Object)(mr.getMappeableRoaringArray().getContainerAtIndex(1)) instanceof MappeableArrayContainer);
        Assert.assertTrue((Object)(mr.getMappeableRoaringArray().getContainerAtIndex(2)) instanceof MappeableBitmapContainer);
        Assert.assertTrue((Object)(mr.getMappeableRoaringArray().getContainerAtIndex(3)) instanceof MappeableBitmapContainer);
        Assert.assertTrue((Object)(mr.getMappeableRoaringArray().getContainerAtIndex(4)) instanceof MappeableBitmapContainer);
        Assert.assertTrue((Object)(mr.getMappeableRoaringArray().getContainerAtIndex(5)) instanceof MappeableArrayContainer);
        Assert.assertTrue((Object)(mr.getMappeableRoaringArray().getContainerAtIndex(6)) instanceof MappeableBitmapContainer);
        
        Assert.assertTrue(mr.getMappeableRoaringArray().getContainerAtIndex(0).getCardinality()==256);
        Assert.assertTrue(mr.getMappeableRoaringArray().getContainerAtIndex(1).getCardinality()==4000);
        Assert.assertTrue(mr.getMappeableRoaringArray().getContainerAtIndex(2).getCardinality()==9000);
        Assert.assertTrue(mr.getMappeableRoaringArray().getContainerAtIndex(3).getCardinality()==7000);
        Assert.assertTrue(mr.getMappeableRoaringArray().getContainerAtIndex(4).getCardinality()==10000);
        Assert.assertTrue(mr.getMappeableRoaringArray().getContainerAtIndex(5).getCardinality()==1000);
        Assert.assertTrue(mr.getMappeableRoaringArray().getContainerAtIndex(6).getCardinality()==30000);
        
        MutableRoaringBitmap mr2 = new MutableRoaringBitmap();
        for (int k = 4000; k < 4256; ++k)
            mr2.add(k);
        for (int k = (1 << 16); k < (1 << 16) + 4000; ++k) 
            mr2.add(k);
        for (int k = (1 << 18) + 2000; k < (1 << 18) + 8000; ++k) 
            mr2.add(k);
        for (int k = (1 << 21); k < (1 << 21) + 1000; ++k) 
            mr2.add(k);
        for (int k = (1 << 22); k < (1 << 22) + 2000; ++k)
            mr2.add(k);
        for (int k = (1 << 25); k < (1 << 25) + 5000; ++k)
            mr2.add(k);        
        buffer = serializeRoaring(mr2);
        buffer.rewind();
        ImmutableRoaringBitmap ir2 = new ImmutableRoaringBitmap(buffer);
        mr2 = ir2.toMutableRoaringBitmap();
        Assert.assertTrue((Object)(mr2.getMappeableRoaringArray().getContainerAtIndex(0)) instanceof MappeableArrayContainer);
        Assert.assertTrue((Object)(mr2.getMappeableRoaringArray().getContainerAtIndex(1)) instanceof MappeableArrayContainer);
        Assert.assertTrue((Object)(mr2.getMappeableRoaringArray().getContainerAtIndex(2)) instanceof MappeableBitmapContainer);
        Assert.assertTrue((Object)(mr2.getMappeableRoaringArray().getContainerAtIndex(3)) instanceof MappeableArrayContainer);
        Assert.assertTrue((Object)(mr2.getMappeableRoaringArray().getContainerAtIndex(4)) instanceof MappeableArrayContainer);
        Assert.assertTrue((Object)(mr2.getMappeableRoaringArray().getContainerAtIndex(5)) instanceof MappeableBitmapContainer);
        
        Assert.assertTrue(mr2.getMappeableRoaringArray().getContainerAtIndex(0).getCardinality()==256);
        Assert.assertTrue(mr2.getMappeableRoaringArray().getContainerAtIndex(1).getCardinality()==4000);
        Assert.assertTrue(mr2.getMappeableRoaringArray().getContainerAtIndex(2).getCardinality()==6000);
        Assert.assertTrue(mr2.getMappeableRoaringArray().getContainerAtIndex(3).getCardinality()==1000);
        Assert.assertTrue(mr2.getMappeableRoaringArray().getContainerAtIndex(4).getCardinality()==2000);
        Assert.assertTrue(mr2.getMappeableRoaringArray().getContainerAtIndex(5).getCardinality()==5000);
        
        MutableRoaringBitmap mr3 = new MutableRoaringBitmap();
        buffer = serializeRoaring(mr3);
        buffer.rewind();
        System.out.println("buffer capacity = "+buffer.capacity());
        ImmutableRoaringBitmap ir3 = new ImmutableRoaringBitmap(buffer);
        mr3 = ir3.toMutableRoaringBitmap();
        
        Assert.assertTrue(mr3.getCardinality()==0);
    }

    @Test
    public void andnottest4() {
        final MutableRoaringBitmap rb = new MutableRoaringBitmap();
        final MutableRoaringBitmap rb2 = new MutableRoaringBitmap();

        for (int i = 0; i < 200000; i += 4)
            rb2.add(i);
        for (int i = 200000; i < 400000; i += 14)
            rb2.add(i);
        rb2.getCardinality();

        // check or against an empty bitmap
        final MutableRoaringBitmap andNotresult = MutableRoaringBitmap
                .andNot(rb, rb2);
        final MutableRoaringBitmap off = MutableRoaringBitmap.andNot(rb2, rb);

        Assert.assertEquals(rb, andNotresult);
        Assert.assertEquals(rb2, off);
        rb2.andNot(rb);
        Assert.assertEquals(rb2, off);
    }
}