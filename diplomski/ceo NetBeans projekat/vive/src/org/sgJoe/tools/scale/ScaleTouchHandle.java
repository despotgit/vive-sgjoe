package org.sgJoe.tools.scale;

import org.apache.log4j.Logger;

import javax.vecmath.Point3d;

import org.sgJoe.graphics.event.EventDispatcher;
import org.sgJoe.graphics.event.EventPublisher;

import org.sgJoe.tools.*;
import org.sgJoe.tools.decorators.*;
import org.sgJoe.tools.interfaces.*;
import com.sun.j3d.utils.geometry.*;
import javax.media.j3d.*;


/*
 * Descritpion for ScaleTouchHandle.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: May 7, 2006  6:20 PM  $
 */

public class ScaleTouchHandle extends VToolHandle {
    
    private static Logger logger = Logger.getLogger(ScaleTouchHandle.class);
    
    public static final int MOD_SCALE_X = 1,
                            MOD_SCALE_Y = 2,
                            MOD_SCALE_Z = 3,
                            MOD_SCALE_XY = 4,
                            MOD_SCALE_XZ = 5,
                            MOD_SCALE_YZ = 6;
    
    
    private Point3d anchorPt = null;
    
    private int modus = MOD_SCALE_XY;
    
    
    public ScaleTouchHandle(VirTool virToolRef, int action) {
        super(virToolRef, action);

        BranchGroup bg = new BranchGroup();
        VToolFactory.setBGCapabilities(bg);
        
        Box box = new Box(.2f, 0.2f, .01f, Primitive.GEOMETRY_NOT_SHARED, null);
        
        bg.addChild(box);        
        
        this.addChild(bg);        
    }

    public void onMousePressed() {
        this.action = VToolForm.ACT_MOUSE_SCALETOUCHHANDLE_PRESSED;
        ScaleVirTool svt = (ScaleVirTool) virToolRef;
        svt.setScaleModus(modus);
        svt.setAnchorPt(anchorPt);
    }

    public void onMouseClicked() {
        this.action = VToolForm.ACT_NONE;
    }    
    
    public void onMouseDragged() {
        this.action = VToolForm.ACT_MOUSE_SCALETOUCHHANDLE_DRAGGED;
    }

    public void onMouseReleased() {
        this.action = VToolForm.ACT_MOUSE_SCALETOUCHHANDLE_RELEASED;
    }

    public Point3d getAnchorPt() {
        return anchorPt;
    }

    public void setAnchorPt(Point3d anchorPt) {
        this.anchorPt = anchorPt;
    }    

    public int getModus() {
        return modus;
    }

    public void setModus(int modus) {
        this.modus = modus;
    }
}


