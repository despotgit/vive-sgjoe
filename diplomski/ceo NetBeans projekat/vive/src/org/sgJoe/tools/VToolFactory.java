package org.sgJoe.tools;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Switch;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;
import org.apache.log4j.Logger;
import org.sgJoe.tools.decorators.VToolHandle;
import org.sgJoe.tools.interfaces.VTool;
import org.sgJoe.tools.interfaces.VToolDecorator;
import org.sgJoe.tools.interfaces.VToolTG;


/*
 * Descritpion for VToolFactory.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: April 3, 2006  3:40 PM     $
 */

public class VToolFactory {
    
    private static Logger logger = Logger.getLogger(VToolFactory.class);
    
    private static long toolUID = 0;
    private static long toolTGUID = 0;
    private static long virToolUID = 0;
    private static long toolHandleUID = 0;
    
    public VToolFactory() { }
    
    public static VTool createVTool(BranchGroup bg, Vector3d vector) {
        VTool trans = null ;
        Transform3D t3d = new Transform3D();
        t3d.setTranslation(vector);
        trans.setTransform(t3d);
        trans.addChild(bg);
        return trans;
    }    
  
    public static VTool setTGCapabilities(VTool vTool) {
        
        vTool.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        vTool.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        vTool.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
        vTool.setCapability(TransformGroup.ALLOW_LOCAL_TO_VWORLD_READ);
        
        // need for scene graph traversal
        vTool.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
        vTool.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
        vTool.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);

        vTool.setCapability(TransformGroup.ALLOW_BOUNDS_READ);
        vTool.setCapability(TransformGroup.ALLOW_BOUNDS_READ);
        
        // --> vTool.setToolUID(generateToolUID());
        
        return vTool;
    }    
    public static TransformGroup setTGCapabilities(TransformGroup tg) {
        
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tg.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
        tg.setCapability(TransformGroup.ALLOW_LOCAL_TO_VWORLD_READ);
        
        // need for scene graph traversal
        tg.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
        tg.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
        tg.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);

        tg.setCapability(TransformGroup.ALLOW_BOUNDS_READ);
        tg.setCapability(TransformGroup.ALLOW_BOUNDS_READ);
        
        // --> vTool.setToolUID(generateToolUID());
        
        return tg;
    }
   
    public static void setTGCapabilities(VToolDecorator vToolDecorator) {
        
        vToolDecorator.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        vToolDecorator.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        vToolDecorator.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
        vToolDecorator.setCapability(TransformGroup.ALLOW_LOCAL_TO_VWORLD_READ);
        
        vToolDecorator.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
        vToolDecorator.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
        vToolDecorator.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);

        vToolDecorator.setCapability(TransformGroup.ALLOW_BOUNDS_READ);
        vToolDecorator.setCapability(TransformGroup.ALLOW_BOUNDS_READ);

    }
    
    public static void setTGCapabilities(VToolTG vToolTG) {
        
        vToolTG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        vToolTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        vToolTG.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
        vToolTG.setCapability(TransformGroup.ALLOW_LOCAL_TO_VWORLD_READ);
        
        vToolTG.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
        vToolTG.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
        vToolTG.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);

        vToolTG.setCapability(TransformGroup.ALLOW_BOUNDS_READ);
        vToolTG.setCapability(TransformGroup.ALLOW_BOUNDS_READ);

    }
    
    public static void setBGCapabilities(BranchGroup vbg) {
        
        vbg.setCapability(BranchGroup.ALLOW_DETACH);
        
        vbg.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        vbg.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
        vbg.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        
        vbg.setCapability(BranchGroup.ALLOW_LOCAL_TO_VWORLD_READ);
        vbg.setCapability(BranchGroup.ALLOW_PICKABLE_READ);
        vbg.setCapability(BranchGroup.ALLOW_PICKABLE_WRITE);
        
        vbg.setCapability(BranchGroup.ENABLE_PICK_REPORTING);
        
        vbg.setCapability(BranchGroup.ALLOW_BOUNDS_READ);
        vbg.setCapability(BranchGroup.ALLOW_BOUNDS_WRITE);
    } 
    
    public static void setSWGCapabilities(Switch swg) {
        
        swg.setCapability(Switch.ALLOW_SWITCH_READ);
        swg.setCapability(Switch.ALLOW_SWITCH_WRITE);
                
        swg.setCapability(Switch.ALLOW_CHILDREN_EXTEND);
        swg.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
        swg.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        
        swg.setCapability(BranchGroup.ALLOW_LOCAL_TO_VWORLD_READ);
        swg.setCapability(BranchGroup.ALLOW_PICKABLE_READ);
        swg.setCapability(BranchGroup.ALLOW_PICKABLE_WRITE);
        
        swg.setCapability(BranchGroup.ENABLE_PICK_REPORTING);
        
        swg.setCapability(BranchGroup.ALLOW_BOUNDS_READ);
        swg.setCapability(BranchGroup.ALLOW_BOUNDS_WRITE);
    }
    
    
    public static VToolTG createVToolTG() {
        VToolTG vtg = new VToolTG();
      
        Transform3D t3d = new Transform3D();
        
        vtg.setTransform(t3d);        
        
        setTGCapabilities(vtg);
        
        return vtg;
    }
    
    public static BranchGroup createVToolBG() {
        BranchGroup vbg = new BranchGroup();
              
        VToolFactory.setBGCapabilities(vbg);
        
        return vbg;
    } 
    
    public static Long generateToolTGUID() {
        return new Long(toolTGUID++);
    }
    public static Long generateToolUID() {
        return new Long(toolUID++);
    }
    
    public static Long generateVirToolUID() {
        return new Long(virToolUID++);
    }    
    
    public static Long generateToolHandleUID() {
        return new Long(toolHandleUID++);
    }    
}
