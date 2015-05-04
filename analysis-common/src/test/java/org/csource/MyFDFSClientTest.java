/** Copyright (C) 2012 Happy Fish / YuQing
 *  My FastDFS Java Client may be copied only under the terms of the GNU Lesser
 * General Public License (LGPL). 
 * Please visit the FastDFS Home Page http://www.csource.org/ for more detail.
 */

package org.csource;

/**
 * My FastDFS test
 * 
 * @author Happy Fish / YuQing
 * @version Version 1.00
 */
public class MyFDFSClientTest {
        public MyFDFSClientTest() {
        }
       

        public static void main(String[] args) {
                final String fdfsConfigFilename = Thread.currentThread().getContextClassLoader().getResource("fdfs_client.conf").getPath();
                final String fdhtConfigFilename = Thread.currentThread().getContextClassLoader().getResource("fdht_client.conf").getPath();
                final String fdhtNamespace = "fdfs";
                String local_filename = "/Users/crazyy/Downloads/vertica.png";

                System.out.println(fdfsConfigFilename);
                System.out.println(fdhtConfigFilename);

                try {
                        DFSCustomIdClient.init(fdfsConfigFilename, fdhtConfigFilename);
                        DFSCustomIdClient myFDFSClient = new DFSCustomIdClient(fdhtNamespace);
                        final String my_file_id = "/201503/vertica";
                        final String file_ext_name = "png";
                        int result;
                        if ((result = myFDFSClient.uploadFile(my_file_id, local_filename, file_ext_name)) != 0) {
                                System.err.println("upload_file fail, errno: " + result);
                                return;
                        }

//                        byte[] bytes = myFDFSClient.downloadFile("/201502/vertica");

                        myFDFSClient.close();
                        System.out.println("fdfs_file_id: " + myFDFSClient.getFdfsFileId(my_file_id));
                        System.out.println(myFDFSClient.downloadFile(my_file_id));
                        
//                        System.out.println("delete_file result: " + myFDFSClient.delete_file(my_file_id));
                } catch (Exception ex) {
                        ex.printStackTrace();
                }
        }
}
