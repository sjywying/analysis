/*
* Copyright (C) 2008 Happy Fish / YuQing
*
* FastDFS Java Client may be copied only under the terms of the GNU Lesser
* General Public License (LGPL).
* Please visit the FastDFS Home Page http://www.csource.org/ for more detail.
*/

package org.csource.common;

/**
 * My Exception
 *
 * @author Happy Fish / YuQing
 * @version Version 1.0
 */
public class FastDFSException extends Exception {
    /**
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
     */
    private static final long serialVersionUID = 1L;

    public FastDFSException() {
    }

    public FastDFSException(String message) {
        super(message);
    }
}
