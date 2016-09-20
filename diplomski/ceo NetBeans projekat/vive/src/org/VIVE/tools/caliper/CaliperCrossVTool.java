/*
 * CaliperCrossVTool.java
 *
 * Created on Ponedeljak, 2006, Juni 19, 15.47
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
 * @author admin
 */
public class CaliperCrossVTool extends VTool{
    
    private static Logger logger = Logger.getLogger(CaliperStickVTool.class);
    
    private int whichEndpoint;
    
    private Appearance ap = new Appearance();
    private LineArray lineGeom=new LineArray(6,LineArray.COORDINATES);
    private Shape3D line;
    
     private Color3f color =  new Color3f();
    /** Creates a new instance of CaliperCrossVTool */
    public CaliperCrossVTool(VirTool virToolRef, int whichEndpoint) {
        super(virToolRef);
        this.whichEndpoint = whichEndpoint;
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
        
        lineGeom.setCoordinate(0,new Point3d(-1,0,0));
        lineGeom.setCoordinate(1,new Point3d( 1,0,0));
        lineGeom.setCoordinate(2,new Point3d(0,-1,0));
        lineGeom.setCoordinate(3,new Point3d(0, 1,0));
        lineGeom.setCoordinate(4,new Point3d(0,0,-1));
        lineGeom.setCoordinate(5,new Point3d(0,0, 1));

        
        
        line = new Shape3D(lineGeom, ap);
        
        bg.addChild(line);
        this.modifyTransform3DForAB();
        this.addChild(bg);
    }

    public void modifyTransform3DForAB() 
    {
        Vector3d centar;
        Transform3D translate = new Transform3D();
        double radius = 4*((CaliperVirTool) getVirToolRef()).getCaliperWidth();
        
        if (whichEndpoint == ((CaliperVirTool)this.getVirToolRef()).POINT_A)
               centar = ((CaliperVirTool)this.getVirToolRef()).getPointA();
        else
               centar = ((CaliperVirTool)this.getVirToolRef()).getPointB();
        translate.setTranslation(centar);
        translate.setScale(radius);
        this.setTransform(translate);
    }
    
      public Color3f getColor() {
        return color;
    }

    public void setColor(Color3f color) {
        this.color = color;
        ap.getColoringAttributes().setColor(this.color);
    }
}
