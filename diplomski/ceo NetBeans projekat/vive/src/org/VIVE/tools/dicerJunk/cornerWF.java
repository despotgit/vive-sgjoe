/*
 * corner.java
 *
 * Created on April 26, 2007, 1:30 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.tools.dicerJunk;

import javax.media.j3d.*;
import javax.vecmath.*;
import org.VIVE.tools.dicer.*;

/**
 *
 * @author Vladimir Despotovic
 */
public class cornerWF extends Shape3D implements iDicerConstants
{
    
    /** Creates a new instance of corner */
   
    // corner is defined by its dimension.One eighth of it is "cut".
    // this object is positioned(by default) like handle 18 according to frame illustration
    
    private float Dimension;      
    
    //constructor for filled quadarray corner
    public cornerWF(float dimension)    
    {
        setCaps();
        Dimension=dimension;        
        
        this.setGeometry(createCorner());
        this.setAppearance(createAppearance(cornerColor,false));               
    }    
    
    //constructor for wireframed/filled quadarray corner
    public cornerWF(float dimension,boolean justLines)
    {
        setCaps();
        Dimension=dimension;
        
        this.setGeometry(createCorner());
        this.setAppearance(createAppearance(cornerColor,justLines));
        
    }
    
    private Geometry createCorner()
    {
        float a=Dimension/2;        
        
        Point3f p0 = new Point3f(0,0,0);                   //0 -center
        Point3f p1 = new Point3f(-a,a,a);                   //1
        Point3f p2 = new Point3f(-a,-a,a);                 //2
        Point3f p3 = new Point3f(a,-a,a);                 //3
        Point3f p4 = new Point3f(a,0,a);                  //Point 4
        Point3f p5 = new Point3f(0,0,a);                 //Point 5
        Point3f p6 = new Point3f(0,a,a);                     //6
        Point3f p7 = new Point3f(0,a,0);                      //7
        Point3f p8 = new Point3f(0,0,0);                     //8
        Point3f p9 = new Point3f(a,0,0);                     //9
        Point3f p10 = new Point3f(a,a,0);                     //10
        Point3f p11 = new Point3f(-a,a,-a);                     //11
        Point3f p12 = new Point3f(-a,-a,-a);                    //12
        Point3f p13 = new Point3f(a,-a,-a);                      //13
        Point3f p14 = new Point3f(a,a,-a);                     //14
        
        
        
        Point3f[] vertex_queue=new Point3f[]
        {
           
            p11,p12,p2,p1,                //side 10    
            p2,p12,p13,p3,                //side 11
            p14,p13,p12,p11               //side 12                    
            
        };        
        
        QuadArray cornerG=new QuadArray(12,QuadArray.COORDINATES);
        cornerG.setCoordinates(0,vertex_queue);
        
        return cornerG;
    }   
    
    private void setCaps() 
    {
        this.setCapability(Shape3D.ALLOW_APPEARANCE_OVERRIDE_READ);
        this.setCapability(Shape3D.ALLOW_APPEARANCE_OVERRIDE_WRITE);
        this.setCapability(Shape3D.ALLOW_APPEARANCE_READ);
        this.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
        this.setCapability(Shape3D.ALLOW_BOUNDS_READ);
        this.setCapability(Shape3D.ALLOW_BOUNDS_WRITE);
        this.setCapability(Shape3D.ALLOW_GEOMETRY_READ);
        this.setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
    }
  
    
    private Appearance createAppearance (Color3f color,boolean justLines)
    {
        Appearance app = new Appearance ();
        ColoringAttributes ca = new ColoringAttributes ();
        ca.setColor(color);
        ColoringAttributes blackca=new ColoringAttributes();
        blackca.setColor(distinctionColor);
        LineAttributes lineAttr = new LineAttributes();
        lineAttr.setLineAntialiasingEnable(true);
        PolygonAttributes polyAttr=new PolygonAttributes();
        if(justLines)
        {        
            polyAttr.setPolygonMode(PolygonAttributes.POLYGON_LINE);   //line for wireframing the quadarray
        }
        Material material = new Material();
        if(justLines)
        {
            app.setColoringAttributes(blackca);
        }
        else
        {
            app.setColoringAttributes(ca);
        }
        app.setLineAttributes(lineAttr);
        app.setPolygonAttributes(polyAttr);
        app.setMaterial(material);
        return app;
    }
     
    
}
