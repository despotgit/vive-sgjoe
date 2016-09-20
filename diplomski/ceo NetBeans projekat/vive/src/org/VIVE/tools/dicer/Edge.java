/*
 * Edge.java
 *
 * Created on April 26, 2007, 12:14 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.tools.dicer;

import javax.media.j3d.*;
import javax.vecmath.*;

/**
 *
 * @author Vladimir
 */
public class Edge extends Shape3D implements iDicerConstants
{
    
    /**
     * Creates a new instance of Edge
     */
    
    // Edge is defined by its length and width.Quarter of it is "cut".
    // this object is positioned(by default) like Edge 1 according to frame illustration
    
    private float Length;
    private float Width;
    
    // filled polygon constructor
    public Edge(float length,float width) 
    {
        setCaps();
        Length=length;
        Width=width;        
        
        this.setGeometry(createEdge());
        this.setAppearance(createAppearance(edgeColor,false));               
    }    
    
    
    // filled/wireframed polygon constructor
    public Edge(float length,float width,boolean justLines)
    {
        setCaps();
        Length=length;
        Width=width;
        this.setGeometry(createEdge());
        this.setAppearance(createAppearance(edgeColor,justLines));
    }
    
    
    private Geometry createEdge()
    {
        float a=Length/2;
        float b=Width/2;
        
        Point3f p0 = new Point3f(0,0,0);                  //0 -center
        Point3f p1 = new Point3f(a,b,b);                   //1
        Point3f p2 = new Point3f(a,-b,b);                 //2
        Point3f p3 = new Point3f(a,-b,0);                 //3
        Point3f p4 = new Point3f(a,0,0);                  //Point 4
        Point3f p5 = new Point3f(a,0,-b);                 //Point 5
        Point3f p6 = new Point3f(a,b,-b);                 //6
        Point3f p7 = new Point3f(-a,b,b);                  //7
        Point3f p8 = new Point3f(-a,-b,b);                //8
        Point3f p9 = new Point3f(-a,-b,0);                //9
        Point3f p10 = new Point3f(-a,0,0);                 //10
        Point3f p11 = new Point3f(-a,0,-b);               //11
        Point3f p12 = new Point3f(-a,b,-b);               //12
       
        Point3f[] vertex_queue=new Point3f[]
        {
            p7,p8,p2,p1,         //forming side 1
            //sides 2 and 3 not needed            
            p12,p7,p1,p6,          //side 4
            //sides 5 and 6 not needed            
            p3,p2,p8,p9,          //side 7
            //sides 8 and 9 not needed 
            p6,p5,p11,p12        //side 10    
            
        };        
        
        QuadArray edgeG=new QuadArray(16,QuadArray.COORDINATES | QuadArray.NORMALS | QuadArray.COLOR_3);
        edgeG.setCoordinates(0,vertex_queue);
        
        Vector3f posZ = new Vector3f( 0  ,  0 ,  1);
        Vector3f negZ = new Vector3f( 0  ,  0 ,  -1);
        Vector3f posY = new Vector3f( 0  ,  1 ,  0);
        Vector3f negY = new Vector3f( 0  ,  -1,  0);
        
        for(int i=0;i<4;i++)
        {
            edgeG.setNormal(i,posZ);              //for vertices on side 1
        }
        for(int i=4 ; i<8 ; i++)
        {
            edgeG.setNormal(i,posY);              //for vertices on side 4
        }        
        for(int i=8 ; i<12 ; i++)
        {
            edgeG.setNormal(i,negY);              //for vertices on side 7
        }        
        for(int i=12 ; i<16 ; i++)
        {
            edgeG.setNormal(i,negZ);              //for vertices on side 10
        }
        
        for(int i=0 ; i<16 ;i++)
        {
            edgeG.setColor(i , edgeColor);      //setting color
        }
        return edgeG;
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
    
    private Appearance createAppearance (Color3f color,boolean justLines) 
    {
        Appearance app = new Appearance ();
        ColoringAttributes ca = new ColoringAttributes ();
        ca.setColor(color);
        ca.setShadeModel(ColoringAttributes.SHADE_GOURAUD);
        
        ColoringAttributes blackca = new ColoringAttributes();
        blackca.setColor(distinctionColor);
        
        LineAttributes lineAttr = new LineAttributes();
        lineAttr.setLineAntialiasingEnable(true);
        PolygonAttributes polyAttr=new PolygonAttributes();
        if(justLines)
        {        
            polyAttr.setPolygonMode(PolygonAttributes.POLYGON_LINE);   //line for wireframing the quadarray
        }
        Material material = new Material();
        material.setDiffuseColor(edgeColor);
        
        material.setEmissiveColor(edgeColor);
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
    

