package com.analysis.calculate.bitmap;

import org.csource.common.DFSCustomIdClient;
import org.roaringbitmap.RoaringBitmap;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.util.concurrent.ConcurrentLinkedQueue;

public class UpLoadBitmap {
    public static final String FDHT_NAMESPACE = "bitmap";

    public static ConcurrentLinkedQueue<BitmapBean> bitmapQueue = new ConcurrentLinkedQueue<BitmapBean>();

    public static class Uploader {
        DFSCustomIdClient dfsClient;

        private BitmapBean bitmapBean;

        public Uploader(BitmapBean bitmapBean, String fdhtNamespace) throws Exception {
            dfsClient = new DFSCustomIdClient(fdhtNamespace);

            this.bitmapBean = bitmapBean;
        }

        public int uploadFile() throws Exception {
            long fileSize = bitmapBean.getBitmap().serializedSizeInBytes();
            UploadBitmapStream uploadStream = new UploadBitmapStream(bitmapBean.getBitmap());
            String filename = "/" + bitmapBean.getModule() + "/" + bitmapBean.getData() + "_" + bitmapBean.getModuleName();

            int result = dfsClient.uploadFile(filename, fileSize, uploadStream, null);

            return result;
        }
    }


    public static class DownloadThread extends Thread {
        private int thread_index;

        public DownloadThread(int index) {
            this.thread_index = index;
        }

        public void run() {

//            TODO 输出目前队列情况
            BitmapBean bitmapBean;

            System.out.println("download thread " + this.thread_index + " start");

            while (true) {
                bitmapBean = UpLoadBitmap.bitmapQueue.poll();
                if (bitmapBean == null) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }

                int result = 0;
                try {
                    Uploader uploader = new Uploader(bitmapBean, UpLoadBitmap.FDHT_NAMESPACE);
                    result = uploader.uploadFile();
                } catch (Exception e) {
//                        TODO 处理异常情况
                    e.printStackTrace();
                }

                if (result != 0) {
//                      TODO 上传文件失败
                    System.out.println("finish " + this.thread_index + " end....");
                }
            }

        }
    }

    private UpLoadBitmap() {
    }

    public void init() throws Exception {
        final String fdfsConfigFilename = "classpath:fdfs _client.conf";
        final String fdhtConfigFilename = "classpath:fdht_client.conf";
        final String fdhtNamespace = "fdfs";
        final int queuethreadnum = 3;

//      先初始化 然后进行文件上传
        DFSCustomIdClient.init(new Configuration(fdfsConfigFilename), new Configuration(fdhtConfigFilename));

//        for (int i = 0; i < queuethreadnum; i++) {
        new DownloadThread(0).start();
//        }

    }

    public static final UpLoadBitmap getInstance() {
        return UpLoadBitmapHolder.INSTANCE;
    }

    private static class UpLoadBitmapHolder {
        private static final UpLoadBitmap INSTANCE = new UpLoadBitmap();

    }


    /**
     * entry point
     *
     * @param args comand arguments
     *             <ul><li>args[0]: config filename</li></ul>
     */
    public static void main(String args[]) {

//        bitmapQueue = new ConcurrentLinkedQueue<BitmapBean>();
//        for (int i = 0; i < 10; i++) {
//            RoaringBitmap bitmap = new RoaringBitmap();
//            bitmap.add(10000000);
//            BitmapBean bitmapBean = new BitmapBean(String.valueOf(20150429 + i), i+"111channle" + i, "101702" + i, bitmap);
//            UpLoadBitmap.bitmapQueue.offer(bitmapBean);
//        }
//
//        try {
//            UpLoadBitmap.getInstance().init();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        final String fdfsConfigFilename = "classpath:fdfs_client.conf";
        final String fdhtConfigFilename = "classpath:fdht_client.conf";
        final String fdhtNamespace = "fdfs";
        final int queuethreadnum = 3;

//      先初始化 然后进行文件上传
        try {
            DFSCustomIdClient.init(new Configuration(fdfsConfigFilename), new Configuration(fdhtConfigFilename));
            DFSCustomIdClient myFDFSClient = new DFSCustomIdClient(fdhtNamespace);

            RoaringBitmap inbitmap = new RoaringBitmap();
            inbitmap.add(54236);

            UploadBitmapStream uploadStream = new UploadBitmapStream(inbitmap);
            myFDFSClient.uploadFile("/test/bitmap1111122", inbitmap.serializedSizeInBytes(), uploadStream, null);



            byte[] bytes = myFDFSClient.downloadFile("/test/bitmap1111122");

            DataInput in = new DataInputStream(new ByteArrayInputStream(bytes));
            RoaringBitmap bitmap = new RoaringBitmap();
            bitmap.deserialize(in);

            System.out.println(bitmap.toArray().length);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        /6111channle6/20150435_1017026
    }
}
