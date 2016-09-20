package org.sgJoe.plugin;

import org.sgJoe.logic.*;

import org.apache.log4j.Logger;

/**
 * Description for ScaleObjForm class:
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:            0.1         $
 * @date     $ Date:  December 22, 2005 4:34 PM $
 */
public class ScaleObjForm extends Form {
    
    private static Logger logger = Logger.getLogger(ScaleObjForm.class);
    
    private double x, y, z = 1;
  
    public void setX(double x) {
        this.x = x;
    }
  
    public double getX() {
        return x;
    }
  
    public void setY(double y) {
        this.y = y;
    }
  
    public double getY() {
        return y;
    }
  
    public void setZ(double z) {
        this.z = z;
    }
  
    public double getZ() {
        return z;
    }
  
    /**
     * Reset all bean properties to their default state. This method is
     * called before the properties are repopulated by the controller.
     *
     * @param session Validate arguments from the session if needed.
     */
    public void reset(Session session) {
        x = y = z = 1;
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