/*
 * CaliperLineVTool.java
 *
 * Created on Ponedeljak, 2006, Juni 19, 14.46
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.tools.caliper;

import com.sun.j3d.utils.geometry.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import org.apache.log4j.Logger;
import org.sgJoe.tools.*;
import org.sgJoe.tools.interfaces.*;

/**
 *
 * @author Lakicevic Milos
 */
public class CaliperLineVTool extends VTool{
    
    private static Logger logger = Logger.getLogger(CaliperStickVTool.class);
    
    private Appearance ap = new Appearance();
    private LineArray lineGeom=new LineArray(2,LineArray.COORDINATES);
    private Shape3D line;
    
     private Color3f color =  new Color3f();
    
    /** Creates a new instance of CaliperLineVTool */
    public CaliperLineVTool(VirTool virToolRef) {
        super(virToolRef);
    }

    public void setup() {
        
        BranchGroup bg = new BranchGroup();
        VToolFactory.setBGCapabilities(bg);    
        
        ColoringAttributes ca = new ColoringAttributes(new Color3f(1.0f,0.0f,0.0f),ColoringAttributes.SHADE_FLAT);
        ap.setColoringAttributes(ca);
        
        ap.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_READ |
                         Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);
        
        LineAttributes la = new LineAttributes();
        la.setLineAntialiasingEnable(true);
        ap.setLineAttributes(la);
        
        lineGeom.setCapability(GeometryArray.ALLOW_COORDINATE_READ | 
                               GeometryArray.ALLOW_COORDINATE_WRITE);
        
        line = new Shape3D(lineGeom, ap);
        
        bg.addChild(line);
        this.modifyGeometryForAB();
        this.addChild(bg);
        
    }

    public void modifyGeometryForAB() {
        CaliperVirTool toolRef = (CaliperVirTool)this.getVirToolRef();
        lineGeom.setCoordinate(0,new Point3d(toolRef.getPointA()));
        lineGeom.setCoordinate(1,new Point3d(toolRef.getPointB()));
    }
    
     public Color3f getColor() {
        return color;
    }

    public void setColor(Color3f color) {
        this.color = color;
        ap.getColoringAttributes().setColor(this.color);
    }
    
}
