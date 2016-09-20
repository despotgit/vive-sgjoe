package org.sgJoe.plugin;

import org.apache.log4j.Logger;
import org.sgJoe.logic.ActionErrors;
import org.sgJoe.logic.Session;


/*
 * Descritpion for CopyViewForm.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: February 8, 2006  5:54 PM  $
 */

public class CopyViewForm extends Form {
    
    private static Logger logger = Logger.getLogger(CopyViewForm.class);
    
    /**
     * Reset all bean properties to their default state. This method is 
     * called before the properties are repopulated by the controller.
     *
     * @param session Validate arguments from the session if needed.
     */
    public void reset(Session session) { }
  
    /**
     * Validate the properties that have been set for this request, and 
     * return an ActionErrors object that encapsulates any validation 
     * errors that have been found. If no errors are found, return null 
     * or an ActionErrors object with no recorded error messages.
     * Any validation error is shown in a popup.
     *
     * @param session Validate arguments from the session if needed.
     * @return ActionErrors The ActionErrors containing the error messages.
     */
    public ActionErrors validate(Session session) {
        return null;
    }        
}
