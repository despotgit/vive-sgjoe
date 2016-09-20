package org.sgJoe.plugin;


import org.sgJoe.exception.SGPluginException;
import org.sgJoe.graphics.SceneGraphEditor;
import org.sgJoe.logic.Session;

import org.apache.log4j.Logger;

/**
 * This class is used to set the corresponding context menu.
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:            0.1         $
 * @date     $ Date:  December 22, 2005 6:01 PM $
 */
public class ContextSwitchPlugin extends Plugin {
    
    private static Logger logger = Logger.getLogger(ContextSwitchPlugin.class);
  
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
        
        ContextSwitchForm contextSwitchForm = (ContextSwitchForm) form;
        String contextMenu = contextSwitchForm.getContextMenu();
        if(contextMenu != null) {
            session.setContextPanelKey(contextMenu);
        }
  }
    
}
