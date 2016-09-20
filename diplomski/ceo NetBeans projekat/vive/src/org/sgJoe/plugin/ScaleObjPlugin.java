package org.sgJoe.plugin;

import com.sun.j3d.utils.picking.PickResult;
import javax.vecmath.Vector3d;
import org.sgJoe.exception.SGPluginException;
import org.sgJoe.graphics.SceneGraphEditor;
import org.sgJoe.logic.Session;

import javax.media.j3d.*;

import org.apache.log4j.Logger;
/**
 * Description for ScaleObjPlugin class:
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:            0.1         $
 * @date     $ Date:  December 22, 2005 4:42 PM $
 */
public class ScaleObjPlugin extends Plugin {
    
    private static Logger logger = Logger.getLogger(ScaleObjPlugin.class);
    
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
        PickResult pickResult = (PickResult)session.get(PICK_RESULT);
        if (pickResult == null) {
            session.setContextDialog("First select a object.");
            return;
        }
    
        TransformGroup group = (TransformGroup)pickResult.getNode(PickResult.TRANSFORM_GROUP);
    
        if (group != null) {
            Transform3D scale = new Transform3D();
    
            double x = ((ScaleObjForm)form).getX();
            double y = ((ScaleObjForm)form).getY();
            double z = ((ScaleObjForm)form).getZ();
    
            Vector3d scaleVec = new Vector3d(x, y, z);
            scale.setScale(scaleVec);
    
            Transform3D currTrans = new Transform3D();
            group.getTransform(currTrans);
            currTrans.setScale(scaleVec);
            group.setTransform(currTrans);
      
            session.put("SCALE_X", new Double(scaleVec.x));
            session.put("SCALE_Y", new Double(scaleVec.y));
            session.put("SCALE_Z", new Double(scaleVec.z));
        }
    }      
}
