package org.sgJoe.tools.sample;

import com.sun.j3d.utils.geometry.ColorCube;

import javax.vecmath.Vector3d;
import org.apache.log4j.Logger;
import org.sgJoe.tools.*;
import org.sgJoe.tools.decorators.*;
import org.sgJoe.tools.interfaces.*;
import javax.media.j3d.*;

/*
 * Descritpion for SampleTranslateHandle.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: April 25, 2006  6:22 PM    $
 */

public class SampleTranslateHandle extends VToolHandle {
    
    private static Logger logger = Logger.getLogger(SampleTranslateHandle.class);
    
    public SampleTranslateHandle(VirTool virToolRef, int action) {
        super(virToolRef, action);
        
        BranchGroup bg = new BranchGroup();
        VToolFactory.setBGCapabilities(bg);
        
        // some geometry for handle
        ColorCube cube = new ColorCube();
        
        bg.addChild(cube);
        
        this.addChild(bg);
        
        Transform3D currTrans = new Transform3D();
        Transform3D newTrans = new Transform3D();
        Vector3d scaleVector = new Vector3d(0.1d, 1.0d, 0.1d);
      
        this.getTransform(currTrans);
        
        newTrans.setScale(scaleVector);
        
        currTrans.mul(newTrans);
        
        this.setTransform(currTrans);
    }

    // nothing actions are always the same...
    public void onMousePressed() {
        this.action = VToolForm.ACT_MOUSE_PRESSED;
    }

    public void onMouseClicked() {
        this.action = VToolForm.ACT_MOUSE_CLICKED;
    }
    
    public void onMouseDragged() {
        this.action = VToolForm.ACT_MOUSE_TRANSLATE_Y;
    }

    public void onMouseReleased() {
        this.action = VToolForm.ACT_MOUSE_RELEASED;
    }
    
}
