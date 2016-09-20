package org.sgJoe.plugin;

import org.sgJoe.logic.*;

import org.apache.log4j.Logger;

/**
 * This is the form to the corresponding ResetViewPlugin. Setter and
 * Getter are not commented. The name says what they do.
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:            0.1         $
 * @date     $ Date:  December 4, 2005 4:09 PM  $
 */
public class ResetViewForm extends Form {
    
    private static Logger logger = Logger.getLogger(ResetViewForm.class);
    
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
