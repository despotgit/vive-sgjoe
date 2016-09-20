package org.sgJoe.plugin;

import javax.media.j3d.*;

import com.sun.j3d.utils.picking.PickResult;
import javax.vecmath.Vector3f;
import org.sgJoe.exception.SGPluginException;
import org.sgJoe.graphics.SceneGraphEditor;
import org.sgJoe.logic.Session;

import org.apache.log4j.Logger;

/**
 * This plugin translates an object in x-, y- and z-direction.
 *
 *
 * @author   $ Author: Aleksandar Babic             $
 * @version  $ Revision:            0.1             $
 * @date     $ Date:  December 22, 2005 12:03 PM    $
 */
public class TranslateObjPlugin extends Plugin {
    
    private static Logger logger = Logger.getLogger(TranslateObjPlugin.class);
    
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
    public void performAction(Form form, SceneGraphEditor editor, Session session)  throws SGPluginException {
        PickResult pickResult = (PickResult)session.get(PICK_RESULT);
        if (pickResult == null) {
            session.setContextDialog("First select a object.");
            return;
        }
    
        TransformGroup group = (TransformGroup)pickResult.getNode(PickResult.TRANSFORM_GROUP);
    
        if (group != null) {
            float x = ((TranslateObjForm)form).getX();
            float y = ((TranslateObjForm)form).getY();
            float z = ((TranslateObjForm)form).getZ();

            Vector3f translateVec = new Vector3f(x, y, z);

            Transform3D currTrans = new Transform3D();
            group.getTransform(currTrans);
            currTrans.setTranslation(translateVec);
            group.setTransform(currTrans);
      
            session.put("TRANS_X", new Double(translateVec.x));
            session.put("TRANS_Y", new Double(translateVec.y));
            session.put("TRANS_Z", new Double(translateVec.z));
            session.setContextDialog("Node translated to: " + translateVec);
        }
    }
}
