package org.sgJoe.plugin;

import org.sgJoe.graphics.*;
import javax.media.j3d.*;

import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import java.util.Enumeration;
import org.sgJoe.exception.SGPluginException;
import org.sgJoe.logic.Session;

import org.apache.log4j.Logger;

/**
 * This plugin loads an obj file makes it visible in the current scene. The
 * scene will not be deleted like in the case of the LoadScenegraphPlugin.
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:            0.1         $
 * @date     $ Date:  December 22, 2005 1:30 PM $
 */
public class OBJLoadPlugin extends Plugin {
    
    private static Logger logger = Logger.getLogger(OBJLoadPlugin.class);
    
    protected boolean showUserMessage() {
        return true;
    }
  
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
        long start = System.currentTimeMillis();
        String filename = ((OBJLoadForm)form).getFilename();

        int flags = ObjectFile.RESIZE;
       
        Scene scene = Utility.loadObjFile(filename, flags, 60.0);
        BranchGroup loadedScene = scene.getSceneGroup();
        editor.setCapabilities(loadedScene);
    
        TransformGroup tg = new TransformGroup();
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tg.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
        tg.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
    
        Enumeration enumeration = loadedScene.getAllChildren();
        while (enumeration.hasMoreElements()) {
            Node child = (Node)enumeration.nextElement();
            loadedScene.removeChild(child);
            tg.addChild(child);
        }
    
        editor.addChild(tg);    
        long stop = System.currentTimeMillis();
        session.setContextDialog("OBJ '" + filename + "' successfully loaded in " + (stop-start) + " msec.");
    }
    
}
