package org.sgJoe.plugin;

import org.apache.log4j.Logger;
import org.sgJoe.exception.SGPluginException;
import org.sgJoe.graphics.SceneGraphEditor;
import org.sgJoe.logic.Session;


/*
 * Descritpion for ChangeFunctionDirectionPlugin.java
 *
 *
 * @author   $ Author: Aleksandar Babic             $
 * @version  $ Revision:             0.1            $
 * @date     $ Date: February 23, 2006  11:09 AM    $
 */

public class ChangeFunctionDirectionPlugin extends Plugin {
    
    private static Logger logger = Logger.getLogger(ChangeFunctionDirectionPlugin.class);
    
    /**
     * Process a specific user action. The form contains the necessary
     * arguments which comes from the GUI. The scene graph editor directly
     * take influence to the current scene graph. The session stores
     * additional information, which should ne shown in a specific GUI
     * component.
     * 
     * 
     * 
     * @param form The form with the necessary arguments.
     * @param editor A scene graph editor.
     * @param session Stores additional information for the GUI.
     * @throws SGPluginException If an exception occures.
     */
    public void performAction(Form form, SceneGraphEditor editor, Session session) throws SGPluginException {
        logger.debug("change the direction of functions [translate, rotate, scale]");
        
        ChangeFunctionDirectionForm changeFunctionDirectionForm = ((ChangeFunctionDirectionForm)form);
        int newDirection = changeFunctionDirectionForm.getDirection();
    
        editor.changeFunctionDirection(newDirection);
      
        /*
         * setup all data in session bean if neccessary
         */
  }     
}
