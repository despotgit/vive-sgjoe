package org.sgJoe.graphics.primitives.utils;

import javax.media.j3d.*;
import javax.vecmath.*;

import org.apache.log4j.Logger;


/*
 * Descritpion for Line3d.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: March 16, 2006  9:01 PM    $
 */

public class SGLine3D extends Shape3D {
    
    private static Logger logger = Logger.getLogger(SGLine3D.class);
    
    public SGLine3D (Point3d A, Point3d B) {
        LineArray LineGeo = new LineArray (2, LineArray.COORDINATES);
	this.addGeometry (LineGeo);
		
	LineGeo.setCoordinate (0, A);
	LineGeo.setCoordinate (1, B);
		
	ColoringAttributes  ca= new ColoringAttributes();
        ca.setColor (1,1,1);
        Appearance app = new Appearance();
        app.setColoringAttributes(ca);
        LineAttributes la = new LineAttributes (20,LineAttributes.PATTERN_SOLID,true);
        app.setLineAttributes (la);

        this.setAppearance (app);
    }
	
    public SGLine3D (Point3d A, Point3d B, Color3f coleur, float lineWidth, int pattern, boolean smooth) {
        LineArray LineGeo = new LineArray (2, LineArray.COORDINATES);
	this.addGeometry (LineGeo);
		
	LineGeo.setCoordinate (0, A);
	LineGeo.setCoordinate (1, B);
		
	ColoringAttributes  ca= new ColoringAttributes();
        ca.setColor (coleur);
        Appearance app = new Appearance();
        app.setColoringAttributes(ca);
        LineAttributes la = new LineAttributes (lineWidth,pattern,smooth);
        app.setLineAttributes (la);

        this.setAppearance (app);
    }    
}
