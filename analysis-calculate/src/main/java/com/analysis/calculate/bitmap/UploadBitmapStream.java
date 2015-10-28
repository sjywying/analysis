package com.analysis.calculate.bitmap;

import org.csource.fastdfs.UploadCallback;
import org.roaringbitmap.RoaringBitmap;

import java.io.*;

/**
 * Upload file by stream
 *
 * @author zhouzezhong & Happy Fish / YuQing
 * @version Version 1.11
 */
public class UploadBitmapStream implements UploadCallback {
    RoaringBitmap bitmap;

    /**
     * constructor
     *
     * @param bitmap
     */
    public UploadBitmapStream(RoaringBitmap bitmap) {
        super();
        this.bitmap = bitmap;
    }

    /**
     * send file content callback function, be called only once when the file uploaded
     *
     * @param out output stream for writing file content
     * @return 0 success, return none zero(errno) if fail
     */
    public int send(OutputStream out) throws IOException {
        DataOutput output = new DataOutputStream(out);

        bitmap.serialize(output);

        return 0;
    }
}
