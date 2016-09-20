package org.sgJoe.plugin;

import com.sun.j3d.utils.picking.PickResult;
import javax.vecmath.Matrix3d;
import org.sgJoe.exception.SGPluginException;
import org.sgJoe.graphics.SceneGraphEditor;
import org.sgJoe.logic.Session;

import javax.media.j3d.*;

import org.apache.log4j.Logger;
/**
 * Description for RotateObjPlugin class:
 *
 *
 * @author   $ Author: Aleksandar Babic $
 * @version  $ Revision:            0.1 $
 * @date     $ Date:  December 22, 2005 12:38 PM $
 */
public class RotateObjPlugin extends Plugin {
    
    private static Logger logger = Logger.getLogger(RotateObjPlugin.class);
    
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
        PickResult pickResult = (PickResult)session.get(PICK_RESULT);
        if (pickResult == null) {
            session.setContextDialog("First select a object.");
            return;
        }
    
        TransformGroup group = (TransformGroup)pickResult.getNode(PickResult.TRANSFORM_GROUP);
    
        if (group != null) {
            double x = ((RotateObjForm)form).getX();
            Matrix3d rotX = new Matrix3d();
            rotX.rotX(Math.toRadians(x));

            double y = ((RotateObjForm)form).getY();
            Matrix3d rotY = new Matrix3d();
            rotY.rotY(Math.toRadians(y));

            double z = ((RotateObjForm)form).getZ();
            Matrix3d rotZ = new Matrix3d();
            rotZ.rotZ(Math.toRadians(z));
    
            Transform3D currTrans = new Transform3D();
            group.getTransform(currTrans);
            rotZ.mul(rotX);
            rotZ.mul(rotY);
            currTrans.setRotation(rotZ);
            group.setTransform(currTrans);
      
            session.put("ROT_X", new Double(x));
            session.put("ROT_Y", new Double(y));
            session.put("ROT_Z", new Double(z));
            session.setContextDialog("Object rotated x:" + x + " y:" + y + " z:" + z);
        }
    }  
}
