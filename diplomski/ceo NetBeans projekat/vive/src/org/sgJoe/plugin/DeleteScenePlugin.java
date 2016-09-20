package org.sgJoe.plugin;


import org.sgJoe.exception.SGPluginException;
import org.sgJoe.graphics.SceneGraphEditor;
import org.sgJoe.logic.Session;

import org.apache.log4j.Logger;

/**
 * This plugin deletes the scene and moves the camera view in it's
 * original position.
 *
 *
 * @author   $ Author: Aleksandar Babic             $
 * @version  $ Revision:            0.1             $
 * @date     $ Date:  December 22, 2005 12:24 PM    $
 */
public class DeleteScenePlugin extends Plugin {
    
    private static Logger logger = Logger.getLogger(DeleteScenePlugin.class);
    
  /**
     * Process a specific user action. The form contains the necessary
     * arguments which comes from the GUI. The scene graph editor directly
     * take influence to the current scene graph. The session stores
     * additional information, which should be shown in a specific GUI
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
        editor.deleteScene();
        editor.resetViewPlatform();
        session.setContextDialog("Scene sucessfully deleted.");
    }    
}
