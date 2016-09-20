package org.sgJoe.plugin;

import org.apache.log4j.Logger;
import org.sgJoe.logic.ActionErrors;
import org.sgJoe.logic.Session;

/**
 * This is the form to the corresponding SelectObjectPlugin. Setter and
 * Getter are not commented. The name says what they do.
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:            0.1         $
 * @date     $ Date:  December 4, 2005 4:19 PM  $
 */
public class SelectObjectForm extends Form {
    
    private static Logger logger = Logger.getLogger(SelectObjectForm.class);
    
    private int x, y;
  
    private String contextMenu;
  
    public void setContextMenu(String menu) {
        this.contextMenu = menu;
    }
  
    public String getContextMenu() {
        return contextMenu;
    }
    
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
  
    public void reset() {
    }
 
    /**
     * Reset all bean properties to their default state. This method is 
     * called before the properties are repopulated by the controller.
     *
     * @param session Validate arguments from the session if needed.
     */
    public void reset(Session session) {
        x = 0;
        y = 0;
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