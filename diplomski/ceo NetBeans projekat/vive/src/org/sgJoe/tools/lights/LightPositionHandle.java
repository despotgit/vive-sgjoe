package org.sgJoe.tools.lights;

import javax.vecmath.Vector3d;
import org.apache.log4j.Logger;
import org.sgJoe.tools.VToolFactory;
import org.sgJoe.tools.decorators.*;
import org.sgJoe.tools.interfaces.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.geometry.*;

/*
 * Descritpion for LightPositionHandle.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: June 1, 2006  6:03 PM  $
 */

public class LightPositionHandle extends VToolHandle {
    
    private static Logger logger = Logger.getLogger(LightPositionHandle.class);
    
    private VToolHandleTG rotTG = null;
    private VToolHandleTG trTG = null;
    
    public LightPositionHandle(VirTool virToolRef, int action) {
        super(virToolRef, action);
        //rotTG = new VToolTG(virToolRef.getVToolRef());
        rotTG = new VToolHandleTG(this);
        VToolFactory.setTGCapabilities(getRotTG());
        //trTG = new VToolTG(virToolRef.getVToolRef());
        trTG = new VToolHandleTG(this);
        VToolFactory.setTGCapabilities(trTG);
        
        BranchGroup bg = new BranchGroup();
        VToolFactory.setBGCapabilities(bg);
        
        // some geometry for handle
        Appearance app = new Appearance();
        PolygonAttributes polyAttr = new PolygonAttributes();
        polyAttr.setPolygonMode(PolygonAttributes.POLYGON_LINE);
        app.setPolygonAttributes(polyAttr);
        
        Transform3D curr = new Transform3D();
        trTG.getTransform(curr);
        curr.setTranslation(new Vector3d(0.0, 0.0, 0.6));
        trTG.setTransform(curr);
        
        Box box = new Box(.7f, .5f, 0.0001f, Primitive.GEOMETRY_NOT_SHARED, app);
        trTG.addChild(box);
        
        getRotTG().addChild(trTG);
        
        bg.addChild(getRotTG());
        
        this.addChild(bg);        
    }   

    public void onMousePressed() {
        this.action = VToolForm.ACT_MOUSE_POSITION_HANDLE_PRESSED;
    }

    public void onMouseClicked() {
        this.action = VToolForm.ACT_MOUSE_POSITION_HANDLE_CLICKED;
    }
   
    public void onMouseDragged() {
        this.action = VToolForm.ACT_NONE;
    }

    public void onMouseReleased() {
        this.action = VToolForm.ACT_MOUSE_POSITION_HANDLE_RELEASED;
    }    

    public VToolHandleTG getRotTG() {
        return rotTG;
    }

}
