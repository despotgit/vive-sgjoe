package org.sgJoe.tools.templates.translate.cube;

import com.sun.j3d.utils.geometry.ColorCube;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import org.apache.log4j.Logger;
import org.sgJoe.tools.VToolFactory;

import org.sgJoe.tools.interfaces.*;
import javax.media.j3d.*;
import org.sgJoe.tools.templates.*;
/*
 * Descritpion for PlaneTool.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: June 17, 2006  12:09 PM  $
 */

public class PlaneCubeTool extends VTool implements PlaneAdapter {
    
    private static Logger logger = Logger.getLogger(PlaneCubeTool.class);
    
    private VToolTG rotTG = null;
    
    // sample geometry
    private ColorCube cube = null;
    
    public PlaneCubeTool(VirTool virToolRef) {
        super(virToolRef);
        rotTG = new VToolTG(this);
        VToolFactory.setTGCapabilities(getRotTG());
        addChild(getRotTG());
    }
    
    public void setup() {
        
        BranchGroup bgCube = new BranchGroup();
        
        VToolFactory.setBGCapabilities(bgCube);        
        
        cube = new ColorCube(1.76);
        
        bgCube.addChild(cube);
        
        this.addChild(bgCube);                
    }
    
    public Bounds getBoundingBox() {
        BranchGroup bg =(BranchGroup) getChild(0);
        Node node = bg.getChild(0);
        return node.getBounds();
    }            

    public VToolTG getRotTG() {
        return rotTG;
    }

    public Vector3f getPosition4Plane() {
        
        float z = 0f;
        
        if(cube.getBounds() instanceof BoundingBox){
            BoundingBox bbox = (BoundingBox) cube.getBounds();
            Point3d pt = new Point3d();
            bbox.getUpper(pt);
            z = (float)pt.z;
        } else if (cube.getBounds() instanceof BoundingSphere) {
            BoundingSphere bsphere = (BoundingSphere) cube.getBounds();
            z = (float)bsphere.getRadius();
        } 
        
        return new Vector3f(0, 0, z);
    }
}


