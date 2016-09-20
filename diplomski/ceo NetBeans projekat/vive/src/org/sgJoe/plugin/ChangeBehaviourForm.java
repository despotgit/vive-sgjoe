package org.sgJoe.plugin;

import org.sgJoe.logic.*;

import org.apache.log4j.Logger;

/**
 * This class represents the form when a specific behaviour
 * get changed.
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: 29/11/2005 21:31:00 $
 */
 public class ChangeBehaviourForm extends Form {
    
    public final static int ENABLE_BEHAVIOUR_MOUSE_PICK_ROTATE = 0;
    public final static int DISABLE_BEHAVIOUR_MOUSE_PICK_ROTATE = 1;
  
    public final static int ENABLE_BEHAVIOUR_MOUSE_PICK_ZOOM = 2;
    public final static int DISABLE_BEHAVIOUR_MOUSE_PICK_ZOOM = 3;
  
    public final static int ENABLE_BEHAVIOUR_MOUSE_PICK_TRANSLATE = 4;
    public final static int DISABLE_BEHAVIOUR_MOUSE_PICK_TRANSLATE = 5;
  
    public final static int ENABLE_BEHAVIOUR_MOUSE_PICK_SCALE = 6;
    public final static int DISABLE_BEHAVIOUR_MOUSE_PICK_SCALE = 7;
  
    public final static int ENABLE_BEHAVIOUR_NAVIGATION_MODE = 8;
    public final static int DISABLE_BEHAVIOUR_NAVIGATION_MODE = 9;
    
    public final static int ENABLE_BEHAVIOUR_MOUSE_PICK_POINT = 10;
    public final static int DISABLE_BEHAVIOUR_MOUSE_PICK_POINT = 11;    
    
    public final static int ENABLE_BEHAVIOUR_DISTANCE_METER = 12;
    public final static int DISABLE_BEHAVIOUR_DISTANCE_METER = 13;    
  
    private int behaviourType = -1;
    private boolean orbitMode = true;
    private int operation;
  
    public void setOperation(int operation) {
        this.operation = operation;
    }
  
    public int getOperation() {
        return operation;
    }
  
    public void setBehaviourType(int behaviourType) {
        this.behaviourType = behaviourType;
    }
  
    public int getBehaviourType() {
        return behaviourType;
    }
  
    public void setOrbitMode(boolean orbitMode) {
        this.orbitMode = orbitMode;
    }
  
    public boolean getOrbitMode() {
        return orbitMode;
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
