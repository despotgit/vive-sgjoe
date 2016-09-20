package org.sgJoe.exception;

import org.apache.log4j.Logger;

/**
 * Handles exceptions thrown by plugins
 *
 *
 * @author   $ Author: Aleksandar Babic             $
 * @version  $ Revision:            0.1             $
 * @date     $ Date:  November 29, 2005 10:36 PM    $
 */
public class SGPluginException extends java.lang.Exception {
    
    private static Logger logger = Logger.getLogger(SGPluginException.class);

    /**
     * Creates a new instance of <code>SGPluginException</code> without detail message.
     */
    public SGPluginException() { }

    /**
     * Constructs an instance of <code>SGPluginException</code> with the specified detail message.
     * 
     * 
     * @param msg the detail message.
     */
    public SGPluginException(java.lang.String exMsg) {
        super(exMsg);
    }
}
