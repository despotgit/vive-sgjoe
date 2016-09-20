package org.sgJoe.plugin;

import org.sgJoe.logic.*;

import org.apache.log4j.Logger;

/**
 * This is the form for the draw primitives plugin.
 * Possible primitives are: Sphere, Cube, Cylinder, and Cone.
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:            0.1         $
 * @date     $ Date:  December 4, 2005 8:52 PM  $
 */
public class DrawPrimitiveForm extends Form {
    
    private static Logger logger = Logger.getLogger(DrawPrimitiveForm.class);
    
    public final static int PRIMITIVE_SPHERE = 0;
    public final static int PRIMITIVE_CUBE = 1;
    public final static int PRIMITIVE_PLANE = 7;
    
    public final static int PRIMITIVE_LINE3D = 101;
      
    private int type = 0;
    private double x = 0, y = 0, z = 0; // the initial coordinates
  
    public void setPrimitive(int type) {
        this.type = type;
    }
  
    public int getPrimitive() {
        return type;
    }
  
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
        this.type = 0;
        this.x = 0;
        this.y = 0;
        this.z = 0;
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
