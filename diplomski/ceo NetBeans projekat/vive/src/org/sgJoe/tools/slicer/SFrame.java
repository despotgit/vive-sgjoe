/*
 * SFrame.java
 *
 * Created on Sreda, 2006, Maj 24, 10.08
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.sgJoe.tools.slicer;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Geometry;
import javax.media.j3d.LineAttributes;
import javax.media.j3d.Material;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.vecmath.Color3f;

/**
 *
 * @author Igor
 */
public class SFrame extends Shape3D{
    
    public static final int EDGE = 1;
    public static final int CORNER = 2;
    
    private float a;
    private float b;
    private float c;
    
    private float[] cords;
    private float[] colors;
    private static final Color3f colorEdge = new Color3f (0.95f, 0.95f, 0.95f);
    private static final Color3f colorCorner = new Color3f (0.85f, 0.85f, 0.85f);
    
    QuadArray frame;
    
    public SFrame(float aa,float bb, float cc, int midang) 
    {
        if (midang == EDGE) 
        {
	   this.setGeometry(createGeometryMid(aa, bb, cc));
           this.setAppearance(createAppearance(colorEdge));
        }
        
        if (midang == CORNER) 
        {
            this.setGeometry(createGeometryAng(aa,bb,cc));
            this.setAppearance(createAppearance(colorCorner));
        }
        setCaps();      
    }
    
    public SFrame(float aa,float bb, int midang) 
    {
        if (midang == EDGE) 
        {
	   this.setGeometry(createGeometryMid(aa, bb, 0.1f));
           this.setAppearance(createAppearance(colorEdge));
        }
        
        if (midang == CORNER) 
        {
            this.setGeometry(createGeometryAng(aa,bb,0.1f));
            this.setAppearance(createAppearance(colorCorner));
        }
        setCaps();      
    }
    
    public SFrame(float aa, int midang) 
    {
        if (midang == EDGE) 
        {
	   this.setGeometry(createGeometryMid(aa, 0.1f, 0.1f));
           this.setAppearance(createAppearance(colorEdge));
        }
        
        if (midang == CORNER) 
        {
            this.setGeometry(createGeometryAng(aa, 0.1f,0.1f));
            this.setAppearance(createAppearance(colorCorner));
        }
        setCaps();      
    }
    
    private Geometry createGeometryMid (float aa,float bb, float cc) 
    {
        a = 0.7f*aa;
        b=bb;
        c=cc;

        cords = new float [] {

            // front face
            -a,  0.0f,  0.0f, //0
             a,  0.0f,  0.0f, //1
             a,  b,  c, //2
            -a,  b,  c, //3

            // back face
            -a,  0.0f,  0.0f, //0
            -a,  b, -c, //5
             a,  b, -c, //4
             a,  0.0f,  0.0f, //1

            // top face
            -a,  b,  c, //3
             a,  b,  c, //2
             a,  b, -c, //4
            -a,  b, -c, //5
        };

       frame = new QuadArray(12, QuadArray.COORDINATES);
       frame.setCoordinates(0, cords);
       return frame;
    }
    
    private Geometry createGeometryAng (float aa, float bb, float cc) {

        a = 0.3f*aa;
        b=bb;
        c=cc;

        cords = new float [] {

            // front face 1
            -a,  0.0f,  0.0f, //0
             0.0f,  0.0f,  0.0f, //1
             b,  b,  c, //2
            -a,  b,  c, //3

            // back face 1
            -a,  0.0f,  0.0f, //0
            -a,  b, -c, //5
             b,  b, -c, //4
             0.0f,  0.0f,  0.0f, //1

            // top face 1
            -a,  b,  c, //3
             b,  b,  c, //2
             b,  b, -c, //4
            -a,  b, -c, //5

            // front face 2
             0.0f,  0.0f,  0.0f, //1
             0.0f,    -a,  0.0f, //6
             b, -a,  c, //7
             b,  b,  c, //2

            // back face 2
             0.0f,    -a,  0.0f, //6
             0.0f,  0.0f,  0.0f, //1
             b,  b, -c, //4
             b,  -a, -c, //8

            // top face 2
             b, -a,  c, //7
             b,  -a, -c, //8
             b,  b, -c, //4
             b,  b,  c, //2
       };

        frame = new QuadArray(24, QuadArray.COORDINATES);
        frame.setCoordinates(0, cords);
        return frame;   
    }
    
    private void setCaps() {
        this.setCapability(Shape3D.ALLOW_APPEARANCE_OVERRIDE_READ);
        this.setCapability(Shape3D.ALLOW_APPEARANCE_OVERRIDE_WRITE);
        this.setCapability(Shape3D.ALLOW_APPEARANCE_READ);
        this.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
        this.setCapability(Shape3D.ALLOW_BOUNDS_READ);
        this.setCapability(Shape3D.ALLOW_BOUNDS_WRITE);
        this.setCapability(Shape3D.ALLOW_GEOMETRY_READ);
        this.setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
    }
    
    private Appearance createAppearance (Color3f coleur) {
        Appearance app = new Appearance ();
        ColoringAttributes ca = new ColoringAttributes ();
        ca.setColor(coleur);
        LineAttributes lineAttr = new LineAttributes();
        lineAttr.setLineAntialiasingEnable(true);
        Material material = new Material();
        app.setColoringAttributes(ca);
        app.setLineAttributes(lineAttr);
        app.setMaterial(material);
        return app;
    }
    
    /** Creates a new instance of SFrame */
    
    
}
