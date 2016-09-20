package org.sgJoe.plugin;

import javax.media.j3d.*;
import org.sgJoe.logic.SGObjectInfo;
import org.sgJoe.logic.*;

import javax.vecmath.Vector3d;
import org.sgJoe.exception.SGPluginException;
import org.sgJoe.graphics.SceneGraphEditor;
import org.sgJoe.gui.GuiConstants;
import com.sun.j3d.utils.picking.PickResult;

import org.apache.log4j.Logger;

/**
 * This plugin selects a node from the screen and stores it in the session.
 * So it can be accessed in other plugins. To mark a selected object on the
 * screen, it will change the appearance. For instance it will change to
 * wireframe.
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:            0.1         $
 * @date     $ Date:  December 22, 2005 4:08 PM $
 */
public class SelectObjectPlugin extends Plugin implements GuiConstants {
    
    private static Logger logger = Logger.getLogger(SelectObjectPlugin.class);
    
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
//        logger.debug("select an object from the scene graph.");
//    
//        Node lastNode = (Node)session.get(LAST_SELECTED_OBJECT);
//    
//        // first we have to check if the capability is set to pick objects
//        if (!editor.isEditable()) {
//            logger.debug("plugin not execute, scene graph not editable.");
//            session.setContextDialog("Select object not executed, scene graph is not editable.");
//        }
//    
//        int x = ((SelectObjectForm)form).getX();
//        int y = ((SelectObjectForm)form).getY();
//    
//        // pick closest object
//        PickResult pickResult = editor.getClosestIntersection(x, y);
//    
//        // double check, just to be sure.
//        if (pickResult != null && pickResult.getObject() != null) {
//            Node node = pickResult.getObject();
//            //Node node = pickResult.getNode(PickResult.TRANSFORM_GROUP);
//            // --> 
//      
////        // we change the current node's appearance.
////        if (!node.equals(lastNode)) {
////            Appearance app = ((Shape3D)node).getAppearance();
////            PolygonAttributes pa = app.getPolygonAttributes();
////            if(pa == null) {
////                // be carefull, you have to set the capabilities, else the object
////                // will not be pickable again.
////                pa = new PolygonAttributes(PolygonAttributes.POLYGON_LINE, PolygonAttributes.CULL_BACK, 0.0f);
////                pa.setCapability(PolygonAttributes.ALLOW_MODE_WRITE);
////                pa.setCapability(PolygonAttributes.ALLOW_MODE_READ);
////                app.setPolygonAttributes(pa);
////            } else {
////                pa.setPolygonMode(PolygonAttributes.POLYGON_LINE);
////            }
////        
////            // we change the previous selected node to the previous appearance.
////            if (lastNode != null) {
////                app = ((Shape3D)lastNode).getAppearance();
////                PolygonAttributes polyAttr = app.getPolygonAttributes();
////                // Here the PolygonAttributes cannot be null because node became lastNode
////                app.getPolygonAttributes().setPolygonMode(PolygonAttributes.POLYGON_FILL);
////            }
////        }
//         
//        // NEW: Take LAST TransformGroup when going up from the picked node to the top
//        TransformGroup transGrp = null;
//        SceneGraphPath sgp = pickResult.getSceneGraphPath();
//        for (int i = sgp.nodeCount() - 1; i >= 0; i--) {
//            Node pNode = sgp.getNode(i); 
//            logger.debug("looking at node " + pNode);
//
//            if (pNode instanceof TransformGroup) {
//                transGrp = (TransformGroup)pNode;
//            }
//        }
//      
//        if (transGrp != null) {
//            // push object UID in stack of selected objects
//            SGObjectInfo info = (SGObjectInfo) transGrp.getUserData();
//            if(info != null) {
//                if(editor.peekUID() != info.getSGUID()) {
//                    editor.pushUID(info.getSGUID());    
//                }                
//            }
//
//                     
//            Vector3d transVec = new Vector3d();
//            Vector3d rotatVec = new Vector3d();
//            Vector3d scaleVec = new Vector3d();
//        
//            editor.calcObjInfo(transGrp, transVec, scaleVec, rotatVec);
//        
//            session.put("TRANS_X", new Double(transVec.x));
//            session.put("TRANS_Y", new Double(transVec.y));
//            session.put("TRANS_Z", new Double(transVec.z));
//        
//            session.put("ROT_X", new Double(rotatVec.x));
//            session.put("ROT_Y", new Double(rotatVec.y));
//            session.put("ROT_Z", new Double(rotatVec.z));
//        
//            session.put("SCALE_X", new Double(scaleVec.x));
//            session.put("SCALE_Y", new Double(scaleVec.y));
//            session.put("SCALE_Z", new Double(scaleVec.z));
//        } else {
//            logger.debug("return since transformGrp was null");
//            return;
//        }
//      
//        // we save the node in the session, so we can access it in other plugins
//        session.put(LAST_SELECTED_OBJECT, node);
//      
//        // better to store the pick result
//        session.put(PICK_RESULT, pickResult);
//      
//        // set a message for the user.
//        String nodeName = resources.getResource(node.getClass().getName());
//        session.put(PLUGIN_MESSAGE, "pick "+nodeName+" at ["+x+", "+y+"]");
//      
//        String contextMenu = ((SelectObjectForm)form).getContextMenu();
//        String contextPanelKey = session.getContextPanelKey();
//        
//        logger.debug("contextMenu: " + contextMenu + " contextPanelKey: " + contextPanelKey);
//        
//        // So that after selecting a primitive, the primitveContext Menu is shown
//        if(contextMenu != null)
//        {
//          session.setContextPanelKey(contextMenu);
//        }
//        else if(contextPanelKey == null)
//        {
//          session.setContextPanelKey("primitiveContext");
//        }
//        else if(contextPanelKey.equals("lightContext"))
//        {
//          session.setContextPanelKey("primitiveContext");
//        }
//
//        session.setContextDialog("pick " + nodeName + " at [" + x + ", " + y + "]");
//    } else {
//        // case if we did not pick an object. if we have a previous node,
//        // set his previous appearance.
//        if (lastNode != null) {
////            // we need to detach the top from the root to change the appearance
////            // of the 3d object.
////            Appearance app = ((Shape3D)lastNode).getAppearance();
////            // Here the PolygonAttributes cannot be null because node became lastNode
////            app.getPolygonAttributes().setPolygonMode(PolygonAttributes.POLYGON_FILL);
//        
//            session.remove(LAST_SELECTED_OBJECT);
//        }
//      
//        session.remove("TRANS_X");
//        session.remove("TRANS_Y");
//        session.remove("TRANS_Z");
//        session.remove("ROT_X");
//        session.remove("ROT_Y");
//        session.remove("ROT_Z");
//        session.remove("TRANS_X");
//        session.remove("TRANS_Y");
//        session.remove("TRANS_Z");
//        session.setContextDialog("no objects to pick at ["+x+", "+y+"]");
//    }
  }
}
