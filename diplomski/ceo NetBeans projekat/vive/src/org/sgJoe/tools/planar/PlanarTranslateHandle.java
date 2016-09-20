package org.sgJoe.tools.planar;


import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import org.apache.log4j.Logger;

import com.sun.j3d.utils.geometry.ColorCube;
import javax.vecmath.Vector3d;

import org.sgJoe.tools.*;
import org.sgJoe.tools.decorators.*;
import org.sgJoe.tools.interfaces.*;
import javax.media.j3d.*;

/*
 * Descritpion for PlanarTranslateHandle.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: April 26, 2006  10:36 AM   $
 */

public class PlanarTranslateHandle extends VToolHandle {
    
    private static Logger logger = Logger.getLogger(PlanarTranslateHandle.class);
    
    protected float dimX = 30.0f;
    protected float dimY = 30.0f;
    protected float dimZ = 0.0001f;
    
    
    public PlanarTranslateHandle(VirTool virToolRef, int action) 
    {
        super(virToolRef, action);
        
        BranchGroup bg = new BranchGroup();
        VToolFactory.setBGCapabilities(bg);
        
        // some geometry for handle
        Appearance app = new Appearance();
        PolygonAttributes polyAttr = new PolygonAttributes();
        polyAttr.setPolygonMode(PolygonAttributes.POLYGON_LINE);
        app.setPolygonAttributes(polyAttr);
        
        Box box = new Box(.5f, .2f, 0.0001f, Primitive.GEOMETRY_NOT_SHARED, app);
        
        bg.addChild(box);
        
        this.addChild(bg);        
    }   

    public void onMousePressed() {
        this.action = VToolForm.ACT_MOUSE_PLANARHANDLE_PRESSED;
    }

    public void onMouseClicked() {
        this.action = VToolForm.ACT_MOUSE_PLANARHANDLE_CLICKED;
    }    

    public void onMouseDragged() {
        this.action = VToolForm.ACT_NONE;
    }

    public void onMouseReleased() {
        this.action = VToolForm.ACT_MOUSE_PLANARHANDLE_RELEASED;
    }
           
}
