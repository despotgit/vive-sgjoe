package org.sgJoe.tools.templates;

import com.sun.j3d.utils.geometry.*;
import javax.media.j3d.*;
import javax.vecmath.Matrix3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import org.apache.log4j.Logger;
import org.sgJoe.tools.*;
import org.sgJoe.tools.decorators.*;
import org.sgJoe.tools.interfaces.*;
/*
 * Descritpion for PlaneHandle.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: June 17, 2006  9:48 AM  $
 */

public class PlaneHandle extends VToolHandle {
    
    private static Logger logger = Logger.getLogger(PlaneHandle.class);
  
    /**
     * The order of translation and rotation are not invariant.
     * Their TG's have been separated.
     * This is will ease 'moving' the plane together with 'real' tool
     */
    
    private VToolHandleTG rotTG = null;
    private VToolHandleTG trTG = null;
    
    float height = 5.0f;
    float width  = 5.0f;
    float depth  = 0.001f;
    
    public PlaneHandle(VirTool virToolRef, int action) {     
        super(virToolRef, action);
        rotTG = new VToolHandleTG(this);
        VToolFactory.setTGCapabilities(getRotTG());
        trTG = new VToolHandleTG(this);
        VToolFactory.setTGCapabilities(trTG);        
        
        BranchGroup bg = new BranchGroup();
        VToolFactory.setBGCapabilities(bg);
        
        // some geometry for handle
        Appearance app = new Appearance();
        PolygonAttributes polyAttr = new PolygonAttributes();
        polyAttr.setPolygonMode(PolygonAttributes.POLYGON_LINE);
        app.setPolygonAttributes(polyAttr);
        
        Box box = new Box(height, width, depth, Primitive.GEOMETRY_NOT_SHARED, app);
        
        Transform3D curr = new Transform3D();
        trTG.getTransform(curr);
        curr.setTranslation(new Vector3d(0.0, 0.0, 0.6));
        trTG.setTransform(curr);        
         
        trTG.addChild(box);
        
        getRotTG().addChild(trTG);
        
        bg.addChild(getRotTG());
        
        this.addChild(bg);        
    }

    public void onMousePressed() {
        this.action = VToolForm.ACT_MOUSE_PLANAR_HANDLE_PRESSED;
    }

    public void onMouseClicked() {
        this.action = VToolForm.ACT_MOUSE_PLANAR_HANDLE_CLICKED;
    }
    
    public void onMouseDragged() {
        this.action = VToolForm.ACT_MOUSE_TRANSLATE_XY;
    }

    public void onMouseReleased() {
        this.action = VToolForm.ACT_MOUSE_PLANAR_HANDLE_RELEASED;
    }

    public VToolHandleTG getRotTG() {
        return rotTG;
    }
    
    /**
    * Sets the PlaneHandle position - to align with tools translation part.
    *
    * @param position The new position
    */
    public void alignPosition() {
        Transform3D toolT3D = new Transform3D();
        this.getVirToolRef().getVToolRef().getTransform(toolT3D);
                
        setTransform(toolT3D);
    }    
    
    public void setRelativePosition(Vector3f position) {
        Transform3D transform = new Transform3D();
        
        trTG.getTransform(transform);
        
        transform.setTranslation(new Vector3f(0.0f, 0.0f, 0.0f));
        // go back
        trTG.setTransform(transform);
        
        // move relatrivly
        transform.setTranslation(position);
        trTG.setTransform(transform);
    }
    
    public void alignDirection() {
        Transform3D toolRotT3D = new Transform3D();
        if(getVirToolRef().getVToolRef() != null) {
            PlaneAdapter plane = (PlaneAdapter) getVirToolRef().getVToolRef();
            if(plane.getRotTG() != null) {
                plane.getRotTG().getTransform(toolRotT3D);
                rotTG.setTransform(toolRotT3D);        
            } else {
                return;
            }
        } else {
            return;
        }
    }
}


