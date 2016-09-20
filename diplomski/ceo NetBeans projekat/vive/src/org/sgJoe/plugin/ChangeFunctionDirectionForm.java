package org.sgJoe.plugin;

import org.apache.log4j.Logger;
import org.sgJoe.logic.ActionErrors;
import org.sgJoe.logic.Session;


/*
 * Descritpion for ChangeFunctionDirectionForm.java
 *
 *
 * @author   $ Author: Aleksandar Babic             $
 * @version  $ Revision:             0.1            $
 * @date     $ Date: February 23, 2006  10:36 AM    $
 */

public class ChangeFunctionDirectionForm extends Form {
    
    private static Logger logger = Logger.getLogger(ChangeFunctionDirectionForm.class);
    
    // these constants are used to identify currently enabled direction for chosen action
    public static final int DIRECTION_PERSPECTIVE = 0,
                            DIRECTION_XY = 1,
                            DIRECTION_XZ = 2,
                            DIRECTION_YZ = 3,
                            DIRECTION_X = 4,
                            DIRECTION_Y = 5,
                            DIRECTION_Z = 6;
    
    private int direction = -1;

    public void setDirection(int direction) {
        this.direction = direction;
    }
  
    public int getDirection() {
        return direction;
    }    
    /**
     * Reset all bean properties to their default state. This method is 
     * called before the properties are repopulated by the controller.
     *
     * @param session Validate arguments from the session if needed.
     */
    public void reset(Session session) {
        // we keep the state save -> no need to reset.
    }
  
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
        // no validation needed
        return null;
    }    
}
