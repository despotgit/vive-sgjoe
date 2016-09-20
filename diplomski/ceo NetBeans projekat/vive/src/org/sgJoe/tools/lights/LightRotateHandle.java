package org.sgJoe.tools.lights;

import org.apache.log4j.Logger;

import org.sgJoe.tools.*;
import org.sgJoe.tools.decorators.*;
import org.sgJoe.tools.interfaces.*;
import com.sun.j3d.utils.geometry.*;
import javax.media.j3d.*;

/*
 * Descritpion for LightRotateHandle.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: June 1, 2006  6:03 PM  $
 */

public class LightRotateHandle extends VToolHandle {
    
    private static Logger logger = Logger.getLogger(LightRotateHandle.class);
        
    public LightRotateHandle(VirTool virToolRef, int action) {
        super(virToolRef, action);

        BranchGroup bg = new BranchGroup();
        VToolFactory.setBGCapabilities(bg);
        
        // some geometry for handle
        Appearance app = new Appearance();
        PolygonAttributes polyAttr = new PolygonAttributes();
        polyAttr.setPolygonMode(PolygonAttributes.POLYGON_POINT);
        app.setPolygonAttributes(polyAttr);
        // TransparencyAttributes transAttr = new TransparencyAttributes(TransparencyAttributes.FASTEST,1.0f); 
        // app.setTransparencyAttributes(transAttr);
        
        Sphere sphere = new Sphere(1.0f, Primitive.GEOMETRY_NOT_SHARED, 50);
        sphere.setAppearance(app);
        
        bg.addChild(sphere);

        this.addChild(bg); 
  
    }

    public void onMousePressed() {
        this.action = VToolForm.ACT_MOUSE_ROTATE_HANDLE_PRESSED;
    }

    public void onMouseClicked() {
        this.action = VToolForm.ACT_MOUSE_ROTATE_HANDLE_CLICKED;
    }
    
    public void onMouseDragged() {
        this.action = VToolForm.ACT_MOUSE_ROTATE;
    }

    public void onMouseReleased() {
        this.action = VToolForm.ACT_MOUSE_ROTATE_HANDLE_RELEASED;
    }   
}

