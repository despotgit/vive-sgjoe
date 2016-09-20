package org.sgJoe.plugin;

import org.sgJoe.logic.*;

import org.apache.log4j.Logger;

/**
 * This class represents the form to delete an specific object.
 * The x, y position from the screen is given.
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:            0.1         $
 * @date     $ Date:  December 4, 2005 3:54 PM  $
 */
public class DeleteObjectForm extends Form{
    
    private static Logger logger = Logger.getLogger(DeleteObjectForm.class);
    
    private int x = -1;
    private int y = -1;
  
    public void setX(int x) {
        this.x = x;
    }
  
    public int getX() {
        return x;
    }
  
    public void setY(int y) {
        this.y = y;
    }
  
    public int getY() {
        return y;
    }
  
    /**
     * Reset all bean properties to their default state. This method is
     * called before the properties are repopulated by the controller.
     *
     * @param session Validate arguments from the session if needed.
     */
    public void reset(Session session) {
        x = -1;
        y = -1;
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
        return null;
    }
      
}
