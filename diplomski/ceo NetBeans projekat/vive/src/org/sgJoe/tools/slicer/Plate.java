/*
 * Plate.java
 *
 * Created on Sreda, 2006, Maj 24, 16.17
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.sgJoe.tools.slicer;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Geometry;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Color3f;
import javax.media.j3d.*;
/**
 *
 * @author Igor
 */
public class Plate extends Shape3D {
    private float a;
    private float b;
    private static float[] cords;
    private QuadArray plt;
    private static final Color3f co = new Color3f (0.8f, 1f, 1f);
    private float transp;
    
    /** Creates a new instance of Plate */
    public Plate(boolean visible, Color3f color, float transp) 
    {
       setA(1f); setB(1f);
       this.setGeometry(createGeometry (getA(), getB()));
       this.setAppearance(createAppearance(visible,color, transp));
     
    }
    
    public Plate(boolean visible, float a, Color3f color, float transp) 
    {
       setA(a); setB(a);
       this.setGeometry(createGeometry (getA(), getB()));
       this.setAppearance(createAppearance(visible,color, transp));
    }
    public Plate(boolean visible) 
    {
       setA(1f); setB(1f);
       this.setGeometry(createGeometry (getA(), getB()));
       this.setAppearance(createAppearance(visible, co, 0f));
     
    }
    
    public Plate(boolean visible, float a) 
    {
       setA(a); setB(a);
       this.setGeometry(createGeometry (getA(), getB()));
       this.setAppearance(createAppearance(visible, co, 0f));
    }
    
    
//    public Plate(float aa, float bb) {
//       a=aa; b=bb;
//       this.setGeometry(createGeometry (a,b));
//       this.setAppearance(createAppearance());
//    }
    
   
    private Appearance createAppearance (boolean visible, Color3f color, float transp) 
    {
        this.transp = transp;
        Appearance app = new Appearance ();   
        PolygonAttributes polyAttr = new PolygonAttributes ();
        polyAttr.setCullFace(PolygonAttributes.CULL_NONE);
        ColoringAttributes ca = new ColoringAttributes ();
        ca.setColor(color);
        if (!visible) this.transp =1f;
        TransparencyAttributes transAttr = new TransparencyAttributes(TransparencyAttributes.FASTEST, this.transp);
        app.setColoringAttributes(ca);
        app.setPolygonAttributes(polyAttr);
        if (transp!=0f) app.setTransparencyAttributes(transAttr);
        
        return app;
    }
    
    private Geometry createGeometry (float a, float b) 
    {        
        cords = new float [] 
        {
            -a, -b, 0f,
             a, -b, 0f,
             a,  b, 0f,
            -a,  b, 0f,
        };
        
        plt = new QuadArray (4, QuadArray.COORDINATES);
        plt.setCoordinates(0, cords);
//        for (int i=0; i<4; i++)
//              plt.setColor(i, co);
        return plt;
    }
    

    public float getA() 
    {
        return a;
    }

    public void setA(float a) 
    {
        this.a = a;
    }

    public float getB() 
    {
        return b;
    }

    public void setB(float b) 
    {
        this.b = b;
    }
}
