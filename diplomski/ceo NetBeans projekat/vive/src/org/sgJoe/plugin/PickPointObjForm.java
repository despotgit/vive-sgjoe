package org.sgJoe.plugin;

import org.sgJoe.logic.*;

import org.apache.log4j.Logger;


/*
 * Descritpion for PickPointObjForm.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: March 4, 2006  2:28 PM     $
 */
public class PickPointObjForm extends Form {
    
    private static Logger logger = Logger.getLogger(TranslateObjForm.class);
    
    private float x, y, z = 0;
  
    public void setX(float x) {
        this.x = x;
    }
  
    public float getX() {
        return x;
    }
  
    public void setY(float y) {
        this.y = y;
    }
  
    public float getY() {
        return y;
    }
  
    public void setZ(float z) {
        this.z = z;
    }
  
    public float getZ() {
        return z;
    }
  
  /**
   * Reset all bean properties to their default state. This method is 
   * called before the properties are repopulated by the controller.
   *
   * @param session Validate arguments from the session if needed.
   */
    public void reset(Session session) {
        x = y = z = 0;
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
