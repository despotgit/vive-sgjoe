package org.sgJoe.tools.scale;

import javax.vecmath.Point3d;
import org.apache.log4j.Logger;

import org.sgJoe.graphics.event.EventDispatcher;
import org.sgJoe.graphics.event.EventPublisher;

import org.sgJoe.tools.*;
import org.sgJoe.tools.decorators.*;
import org.sgJoe.tools.interfaces.*;
import com.sun.j3d.utils.geometry.*;
import javax.media.j3d.*;

/*
 * Descritpion for PlanarScaleHandle.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: May 4, 2006  11:38 PM  $
 */


public class PlanarScaleHandle extends VToolHandle 
{
    
    private static Logger logger = Logger.getLogger(PlanarScaleHandle.class);
    
    private Point3d anchorPt = new Point3d(-1.0d, -1.0d, -1.0d);
    
    public PlanarScaleHandle(VirTool virToolRef, int action) 
    {
        super(virToolRef, action);

        BranchGroup bg = new BranchGroup();
        VToolFactory.setBGCapabilities(bg);
        
        // some geometry for handle
        Appearance app = new Appearance();
        PolygonAttributes polyAttr = new PolygonAttributes();
        polyAttr.setPolygonMode(PolygonAttributes.POLYGON_LINE);
        app.setPolygonAttributes(polyAttr);
        // TransparencyAttributes transAttr = new TransparencyAttributes(TransparencyAttributes.FASTEST,1.0f); 
        // app.setTransparencyAttributes(transAttr);
                
        Box box = new Box(10.0f, 10.0f, 0.01f, Primitive.GEOMETRY_NOT_SHARED, app);
        
        bg.addChild(box);        
        
        this.addChild(bg);        
    }

    public void onMousePressed() {
        this.action = VToolForm.ACT_MOUSE_SCALEHANDLE_PRESSED;
    }

    public void onMouseClicked() {
        this.action = VToolForm.ACT_NONE;
    }
    
    public void onMouseDragged() {
        this.action = VToolForm.ACT_MOUSE_SCALEHANDLE_DRAGGED;
    }

    public void onMouseReleased() {
        this.action = VToolForm.ACT_MOUSE_SCALEHANDLE_RELEASED;
    }

    public Point3d getAnchorPt() {
        return anchorPt;
    }

    public void setAnchorPt(Point3d anchorPt) {
        this.anchorPt = anchorPt;
    }
    
}
