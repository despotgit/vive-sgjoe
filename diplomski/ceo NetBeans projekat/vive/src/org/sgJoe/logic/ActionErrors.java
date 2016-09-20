package org.sgJoe.logic;

import org.sgJoe.logic.ApplicationResources;

import org.apache.log4j.Logger;

/**
 * An encapsulation of an individual error message returned by the 
 * <code>validate()</code> method of an Form, consisting of a message 
 * key (to be used to look up message text in an appropriate message 
 * resources database).
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: 29/11/2005 21:31:00 $
 */
public final class ActionErrors {
    
    private static Logger logger = Logger.getLogger(SGThreadPool.class);
    
    // indicates whether an error key is set or not. This indicator is used
    // by the controller.
    public boolean isSet = false;
  
    // should not set to null, else the lookup
    // in the application resources leeds to an
    // null-pointer-exception!!!
    private String message = new String();
    private String key = new String();
  
    /**
     * Sets the key for this action error.
     *
     * @param key The key to set.
     */
    public void setKey(String key) {
        isSet = true;
        this.key = key;
    }
  
    /**
     * Returns the key.
     *
     * @return The key for this action error.
   */
    public String getKey() {
        return key;
    }
  
    /**
     * Returns the message for this ActionError. The error message is lookup
     * in the ApplicationResources. If the associated message is not found,
     * <code>null</code> will be returned.
     *
     * @return String The corresponding error message from the ApplicationResources.
     */
    public String getMessage() {
        return ApplicationResources.instance().getResource(key);
    }
    
}