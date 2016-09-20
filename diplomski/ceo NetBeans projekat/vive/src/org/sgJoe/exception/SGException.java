package org.sgJoe.exception;

import org.apache.log4j.Logger;

/**
 * This is sgJoe's exception handler. Depending on the "weight"
 * of the exception, it is shown differently to the user.
 * Light: Message is put into the log
 * Medium: Shows a popup dialog to the user
 * Heavy: Puts exception into log and terminates program
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision: 0.1             $
 * @date     $ Date: 29/11/2005 14:03:50 $
 */
public class SGException extends java.lang.Exception {
    
    private Logger logger = Logger.getLogger(SGException.class);
  
    // this exception causes the program to terminate and log the exception's message.
    public final static int HEAVY_EXCEPTION = 0;
    // this exception causes to show a popup to the user and log the exception's message.
    public final static int MEDIUM_EXCEPTION = 1;
    // this exception causes to log the exception's message.
    public final static int LIGHT_EXCEPTION = 2;
  
    // the execption level is set to the default level.
    private int exceptionLevel = LIGHT_EXCEPTION;
  
    /**
     * Creates a new instance of <code>SGException</code> without detail 
     * message.
     */
    public SGException() { }

    /**
     * Constructs an instance of <code>SGException</code> with the 
     * specified detail message.
     * 
     * 
     * @param msg the detail message.
     */
    public SGException(String exMsg) { 
        super(exMsg);
    }
  
    /**
     * Constructs an instance of <code>SGException</code> with the 
     * specified detail message.
     * 
     * 
     * @param msg the detail message.
     */
    public SGException(String exMsg, int exceptionLevel) {
        super(exMsg);
        this.exceptionLevel = exceptionLevel;
    }
  
    public int getExecptionLevel() {
        return this.exceptionLevel;
    }
}
