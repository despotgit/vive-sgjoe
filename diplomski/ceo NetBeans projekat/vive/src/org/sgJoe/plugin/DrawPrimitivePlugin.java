package org.sgJoe.plugin;

import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import org.sgJoe.exception.SGPluginException;
import org.sgJoe.graphics.SceneGraphEditor;
import org.sgJoe.gui.GuiConstants;
import org.sgJoe.logic.Session;

import org.apache.log4j.Logger;

/**
 * This plugin renders a new primitive and position it near the
 * origin. For the position coordinates we choose some random values.
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:            0.1         $
 * @date     $ Date:  December 22, 2005 2:28 PM $
 */
public class DrawPrimitivePlugin extends Plugin {
    
    private static Logger logger = Logger.getLogger(DrawPrimitivePlugin.class);
    
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
        logger.debug("draw new primitive");
        DrawPrimitiveForm drawPrimitiveForm = ((DrawPrimitiveForm)form);
        int primitive = drawPrimitiveForm.getPrimitive();
        double x = drawPrimitiveForm.getX();
        double y = drawPrimitiveForm.getY();
        double z = drawPrimitiveForm.getZ();
    
        TransformGroup objTransGrp = null;
        // create primitive and add it to the graph
        switch (primitive) {
            case DrawPrimitiveForm.PRIMITIVE_CUBE:
                objTransGrp = editor.createBox(new Vector3d(x, y, z));
                session.setContextDialog("Draw new cube at ["+x+","+y+","+z+"]");
                break;
                
            case DrawPrimitiveForm.PRIMITIVE_SPHERE:
                objTransGrp = editor.createSphere(new Vector3d(x, y, z));
                session.setContextDialog("Draw new sphere at ["+x+","+y+","+z+"]");
                break;
            
            case DrawPrimitiveForm.PRIMITIVE_PLANE:
                objTransGrp = editor.createPlane(new Vector3d(x, y, z));
                session.setContextDialog("Draw new plane at ["+x+","+y+","+z+"]");
                break;
                
            case DrawPrimitiveForm.PRIMITIVE_LINE3D:
                // first get points for line
                Point3d pt1st = (Point3d)session.get("POINT3D_1ST");
                Point3d pt2nd = (Point3d)session.get("POINT3D_2nd");                
                objTransGrp = editor.createLine3d(pt1st, pt2nd, new Vector3d(x, y, z));
                session.setContextDialog("Draw new Line at ["+x+","+y+","+z+"]");
                break;                
                
            default:
                objTransGrp = editor.createBox(new Vector3d(x, y, z));
                session.setContextDialog("Draw new cube at ["+x+","+y+","+z+"]");
                break;
        }
    
        // calc values for primitive context panel
        Vector3d translation = new Vector3d();
        Vector3d rotation = new Vector3d();
        Vector3d scale = new Vector3d();
    
        editor.calcObjInfo(objTransGrp, translation, scale, rotation);
      
        session.put("TRANS_X", new Double(translation.x));
        session.put("TRANS_Y", new Double(translation.y));
        session.put("TRANS_Z", new Double(translation.z));

        session.put("ROT_X", new Double(rotation.x));
        session.put("ROT_Y", new Double(rotation.y));
        session.put("ROT_Z", new Double(rotation.z));

        session.put("SCALE_X", new Double(scale.x));
        session.put("SCALE_Y", new Double(scale.y));
        session.put("SCALE_Z", new Double(scale.z));
   
        // add primitive to the scene.
        editor.setCapabilities(objTransGrp);
        editor.addChild(objTransGrp);
    
        session.setContextPanelKey(GuiConstants.PRIMITIVE_CONTEXT);        
  } 
}
