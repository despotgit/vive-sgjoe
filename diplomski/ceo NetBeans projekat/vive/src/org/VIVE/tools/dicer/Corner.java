/*
 * Corner.java
 *
 * Created on April 26, 2007, 1:30 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.tools.dicer;

import javax.media.j3d.*;
import javax.vecmath.*;

/**
 *
 * @author Vladimir Despotovic
 */
public class Corner extends Shape3D implements iDicerConstants
{    
    /**
     * Creates a new instance of Corner
     */
   
    // Corner is defined by its dimension.One eighth of it is "cut".
    // this object is positioned(by default) like handle 18 according to frame illustration
    
    private float Dimension;      
    
    //constructor for filled quadarray Corner
    public Corner(float dimension)    
    {
        setCaps();
        Dimension = dimension;        
        
        this.setGeometry(createCorner());
        this.setAppearance(createAppearance(cornerColor,false));               
    }    
    
    //constructor for wireframed/filled quadarray Corner
    public Corner(float dimension,boolean justLines)
    {
        setCaps();
        Dimension=dimension;
        
        this.setGeometry(createCorner());
        this.setAppearance(createAppearance(cornerColor,justLines));
        
    }
    
    private Geometry createCorner()
    {
        float a=Dimension/2;        
        
        /* Helping Point3f variables */
        Point3f p0 = new Point3f(0,0,0);                       // 0 -center
        Point3f p1 = new Point3f(-a,a,a);                      // 1
        Point3f p2 = new Point3f(-a,-a,a);                     // 2
        Point3f p3 = new Point3f(a,-a,a);                      // 3
        Point3f p4 = new Point3f(a,0,a);                       // Point 4
        Point3f p5 = new Point3f(0,0,a);                       // Point 5
        Point3f p6 = new Point3f(0,a,a);                       // 6
        Point3f p7 = new Point3f(0,a,0);                       // 7
        Point3f p8 = new Point3f(0,0,0);                       // 8
        Point3f p9 = new Point3f(a,0,0);                       // 9
        Point3f p10 = new Point3f(a,a,0);                      // 10
        Point3f p11 = new Point3f(-a,a,-a);                    // 11
        Point3f p12 = new Point3f(-a,-a,-a);                   // 12
        Point3f p13 = new Point3f(a,-a,-a);                    // 13
        Point3f p14 = new Point3f(a,a,-a);                     // 14       
        
        Point3f[] vertex_queue = new Point3f[]
        {
            p1,p2,p5,p6,             //side 1
            p5,p2,p3,p4,             //side 2
            p4,p3,p13,p9,            //side 3
            p10,p9,p13,p14,          //side 4
            p11,p7,p10,p14,          //side 5
            p11,p1,p6,p7,            //side 6
            
            //we don't need sides 7,8 and 9
            
            p11,p12,p2,p1,           //side 10      has to have normals generated(because of distinction requirement)
            p2,p12,p13,p3,           //side 11      has to have normals generated
            p14,p13,p12,p11          //side 12      has to have normals generated              
            
        };        
        
        QuadArray cornerG = new QuadArray(36,QuadArray.COORDINATES | QuadArray.NORMALS | QuadArray.COLOR_3);
        cornerG.setCoordinates(0,vertex_queue);
        
        Vector3f posX = new Vector3f(  1 ,  0 ,  0 );
        Vector3f negX = new Vector3f( -1 ,  0 ,  0 );
        Vector3f posY = new Vector3f(  0 ,  1 ,  0 );
        Vector3f negY = new Vector3f(  0 , -1 ,  0 );
        Vector3f posZ = new Vector3f(  0 ,  0 ,  1 );
        Vector3f negZ = new Vector3f(  0 ,  0 , -1 );
        
        
        for(int i=0 ; i<8 ; i++)
        {
            cornerG.setNormal(i , posZ);              //for vertices on sides 1 and 2
        }
        
        for(int i=8 ; i<16 ; i++)
        {
            cornerG.setNormal(i , posX);             //for vertices on sides  3 and 4
        }
        
        for(int i=16 ; i<24 ; i++)
        {
            cornerG.setNormal(i , posY);              //for vertices on sides  5 and 6
        }
        
        for(int i=24 ; i<28 ; i++)
        {
            cornerG.setNormal(i , negX);        //for vertices on side 10
        }
        
        for(int i=28 ; i<32 ; i++)
        {
            cornerG.setNormal(i , negY);         //for vertices on side 11
        }
        
        for(int i=32 ; i<36 ; i++)
        {
            cornerG.setNormal(i , negZ);        //for vertices on side 12
        }   
        
        for(int i=0 ; i<36 ; i++)
        {
            cornerG.setColor(i , cornerColor);         //coloring all the vertices with the same color
        }
        
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
        ca.setShadeModel(ColoringAttributes.SHADE_GOURAUD);       //setting GOURAUD shading
        
        ColoringAttributes blackca = new ColoringAttributes();
        blackca.setColor(distinctionColor);
        
        LineAttributes lineAttr = new LineAttributes();
        lineAttr.setLineAntialiasingEnable(true);
        
        PolygonAttributes polyAttr=new PolygonAttributes();
        polyAttr.setPolygonMode(PolygonAttributes.POLYGON_FILL);
        
        if(justLines)
        {        
            polyAttr.setPolygonMode(PolygonAttributes.POLYGON_LINE);   //line for wireframing the quadarray
        }
        Material material = new Material();
        material.setDiffuseColor(cornerColor);
        
        material.setEmissiveColor(cornerColor);
        
        if(justLines)
        {
            app.setColoringAttributes(blackca);
        }
        else
        {
           // app.setColoringAttributes(ca);    // no need for this since colors are defined 4 each vertex
        }
        
        if(justLines)
        {
            app.setLineAttributes(lineAttr);
        }
        app.setPolygonAttributes(polyAttr);
        app.setMaterial(material);
        return app;
    }
     
    
}
