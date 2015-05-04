/**
 * Copyright (C) 2008 Happy Fish / YuQing
 *
 * FastDFS Java Client may be copied only under the terms of the GNU Lesser
 * General Public License (LGPL).
 * Please visit the FastDFS Home Page http://www.csource.org/ for more detail.
 **/

package org.csource.fastdfs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.csource.common.IniFileReader;
import org.csource.common.MyException;

/**
 * Global variables
 * 
 * @author Happy Fish / YuQing
 * @version Version 1.11
 */
public class ClientGlobal {
        public static int connectTimeout; // millisecond
        public static int networkTimeout; // millisecond
        public static String charset;
        public static int trackerHttpPort;
        public static boolean antiStealToken; // if anti-steal token
        public static String secretKey; // generage token secret key
        public static TrackerGroup trackerGroup;

        public static final int DEFAULT_CONNECT_TIMEOUT = 5; // second
        public static final int DEFAULT_NETWORK_TIMEOUT = 30; // second

        private ClientGlobal() {
        }

        /**
         * load global variables
         * 
         * @param conf_filename
         *                config filename
         */
        public static void init(String conf_filename) throws FileNotFoundException, IOException, MyException {
                IniFileReader iniReader;
                String[] szTrackerServers;
                String[] parts;

                iniReader = new IniFileReader(conf_filename);

                connectTimeout = iniReader.getIntValue("connect_timeout", DEFAULT_CONNECT_TIMEOUT);
                if (connectTimeout < 0) {
                        connectTimeout = DEFAULT_CONNECT_TIMEOUT;
                }
                connectTimeout *= 1000; // millisecond

                networkTimeout = iniReader.getIntValue("network_timeout", DEFAULT_NETWORK_TIMEOUT);
                if (networkTimeout < 0) {
                        networkTimeout = DEFAULT_NETWORK_TIMEOUT;
                }
                networkTimeout *= 1000; // millisecond

                charset = iniReader.getStrValue("charset");
                if (charset == null || charset.length() == 0) {
                        charset = "UTF-8";
                }

                szTrackerServers = iniReader.getValues("tracker_server");
                if (szTrackerServers == null) {
                        throw new MyException("item \"tracker_server\" in " + conf_filename + " not found");
                }

                InetSocketAddress[] tracker_servers = new InetSocketAddress[szTrackerServers.length];
                for (int i = 0; i < szTrackerServers.length; i++) {
                        parts = szTrackerServers[i].split("\\:", 2);
                        if (parts.length != 2) {
                                throw new MyException(
                                                "the value of item \"tracker_server\" is invalid, the correct format is host:port");
                        }

                        tracker_servers[i] = new InetSocketAddress(parts[0].trim(), Integer.parseInt(parts[1].trim()));
                }
                trackerGroup = new TrackerGroup(tracker_servers);

                trackerHttpPort = iniReader.getIntValue("http.tracker_http_port", 80);
                antiStealToken = iniReader.getBoolValue("http.anti_steal_token", false);
                if (antiStealToken) {
                        secretKey = iniReader.getStrValue("http.secret_key");
                }
        }

        /**
         * construct Socket object
         * 
         * @param ip_addr
         *                ip address or hostname
         * @param port
         *                port number
         * @return connected Socket object
         */
        public static Socket getSocket(String ip_addr, int port) throws IOException {
                Socket sock = new Socket();
                sock.setSoTimeout(ClientGlobal.networkTimeout);
                sock.connect(new InetSocketAddress(ip_addr, port), ClientGlobal.connectTimeout);
                return sock;
        }

        /**
         * construct Socket object
         * 
         * @param addr
         *                InetSocketAddress object, including ip address and
         *                port
         * @return connected Socket object
         */
        public static Socket getSocket(InetSocketAddress addr) throws IOException {
                Socket sock = new Socket();
                sock.setSoTimeout(ClientGlobal.networkTimeout);
                sock.connect(addr, ClientGlobal.connectTimeout);
                return sock;
        }

        public static int getConnectTimeout() {
                return connectTimeout;
        }

        public static void setConnectTimeout(int connectTimeout) {
                ClientGlobal.connectTimeout = connectTimeout;
        }

        public static int getNetworkTimeout() {
                return networkTimeout;
        }

        public static void setNetworkTimeout(int networkTimeout) {
                ClientGlobal.networkTimeout = networkTimeout;
        }

        public static String getCharset() {
                return charset;
        }

        public static void setCharset(String charset) {
                ClientGlobal.charset = charset;
        }

        public static int getTrackerHttpPort() {
                return trackerHttpPort;
        }

        public static void setTrackerHttpPort(int trackerHttpPort) {
                ClientGlobal.trackerHttpPort = trackerHttpPort;
        }

        public static boolean isAntiStealToken() {
                return antiStealToken;
        }

        public static void setAntiStealToken(boolean antiStealToken) {
                ClientGlobal.antiStealToken = antiStealToken;
        }

        public static String getSecretKey() {
                return secretKey;
        }

        public static void setSecretKey(String secretKey) {
                ClientGlobal.secretKey = secretKey;
        }

        public static TrackerGroup getTrackerGroup() {
                return trackerGroup;
        }

        public static void setTrackerGroup(TrackerGroup trackerGroup) {
                ClientGlobal.trackerGroup = trackerGroup;
        }

        public static int getDefaultConnectTimeout() {
                return DEFAULT_CONNECT_TIMEOUT;
        }

        public static int getDefaultNetworkTimeout() {
                return DEFAULT_NETWORK_TIMEOUT;
        }

     
}
