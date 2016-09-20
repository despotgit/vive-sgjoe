package org.sgJoe.plugin;

import org.sgJoe.logic.*;

import com.sun.j3d.utils.picking.PickResult;
import javax.media.j3d.Node;
import org.sgJoe.exception.SGPluginException;
import org.sgJoe.graphics.SceneGraphEditor;

import org.apache.log4j.Logger;

/**
 * Deletes a node from the scene graph. This can only be done, if we have
 * selected a node before.
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:            0.1         $
 * @date     $ Date:  December 22, 2005 5:29 PM $
 */
public class DeleteObjectPlugin extends Plugin {
    
    private static Logger logger = Logger.getLogger(DeleteObjectPlugin.class);
    private ApplicationResources resources = ApplicationResources.instance();
    
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
        Node node = null;
    
        DeleteObjectForm delForm = (DeleteObjectForm) form;
    
        int x = delForm.getX();
        int y = delForm.getY();

        // pick closest object
        PickResult pickResult = editor.getClosestIntersection(x, y);
        
        
        // double check, just to be shure.
        if (pickResult != null && pickResult.getObject() != null) {
            //node = pickResult.getObject();
            node = pickResult.getNode(PickResult.TRANSFORM_GROUP);
        }
    
        // if we didn't pick a node take the selected one
        if(node == null) {
            node = (Node)session.remove(LAST_SELECTED_OBJECT);
        }
    
        // if we have a node, delete it
        if(node != null) {
            editor.removeNode(node);
      
        // convert the class name in a nicer and shorter name.
        String nodeName = resources.getResource(node.getClass().getName());
        session.setContextDialog("Node " + nodeName + " successfully deleted.");
      
        session.remove("TRANS_X");
        session.remove("TRANS_Y");
        session.remove("TRANS_Z");
      
        session.remove("ROT_X");
        session.remove("ROT_Y");
        session.remove("ROT_Z");
      
        session.remove("SCALE_X");
        session.remove("SCALE_Y");
        session.remove("SCALE_Z");
        }
    }
}
