package org.sgJoe.tools.templates.translate.sphere;

import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import org.apache.log4j.Logger;
import org.sgJoe.tools.VToolFactory;

import org.sgJoe.tools.interfaces.*;
import javax.media.j3d.*;
import org.sgJoe.tools.templates.*;

/*
 * Descritpion for PlaneSphereTool.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: June 18, 2006  10:45 PM  $
 */

public class PlaneSphereTool extends VTool implements PlaneAdapter {
    
    private static Logger logger = Logger.getLogger(PlaneSphereTool.class);
    
    private VToolTG rotTG = null;
    
    // sample geometry
    private Sphere sphere = null;
    
    public PlaneSphereTool(VirTool virToolRef) {
        super(virToolRef);
        rotTG = new VToolTG(this);
        VToolFactory.setTGCapabilities(getRotTG());
        addChild(getRotTG());
    }
    
    public void setup() {
        
        BranchGroup bg = new BranchGroup();
        
        VToolFactory.setBGCapabilities(bg);        
        
        Appearance app = new Appearance();
        PolygonAttributes polyAttr = new PolygonAttributes();
        polyAttr.setPolygonMode(PolygonAttributes.POLYGON_LINE);
        app.setPolygonAttributes(polyAttr);
        
        sphere = new Sphere(1.0f, Primitive.GEOMETRY_NOT_SHARED, 100);
        sphere.setAppearance(app);
        
        bg.addChild(sphere);
        
        this.addChild(bg);                
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
        
        float z = sphere.getRadius();
        
        return new Vector3f(0, 0, z);
    }
}


