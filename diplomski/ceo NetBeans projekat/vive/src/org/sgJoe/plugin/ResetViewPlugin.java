package org.sgJoe.plugin;

import org.sgJoe.exception.SGPluginException;
import org.sgJoe.graphics.SceneGraphEditor;
import org.sgJoe.logic.Session;

import org.apache.log4j.Logger;

/**
 * Plugin to reset the view to its original position.
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:            0.1         $
 * @date     $ Date:  December 22, 2005 2:48 PM $
 */
public class ResetViewPlugin extends Plugin {
    
    private static Logger logger = Logger.getLogger(ResetViewPlugin.class);
    
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
        editor.resetViewPlatform();
        session.setContextDialog("View reset.");
    }
}
