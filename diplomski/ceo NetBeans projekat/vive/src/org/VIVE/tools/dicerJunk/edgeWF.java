/*
 * edge.java
 *
 * Created on April 26, 2007, 12:14 AM
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
public class edgeWF extends Shape3D implements iDicerConstants
{
    
    /** Creates a new instance of edge */
    
    // edge is defined by its length and width.Quarter of it is "cut".
    // this object is positioned(by default) like edge 1 according to frame illustration
    
    private float Length;
    private float Width;
    
    // filled polygon constructor
    public edgeWF(float length,float width) 
    {
        setCaps();
        Length=length;
        Width=width;        
        
        this.setGeometry(createEdge());
        this.setAppearance(createAppearance(edgeColor,false));               
    }    
    
    
    // filled/wireframed polygon constructor
    public edgeWF(float length,float width,boolean justLines)
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
            
            p12,p7,p1,p6        //side 4
            
            
        };        
        
        QuadArray edgeG=new QuadArray(8,QuadArray.COORDINATES);
        edgeG.setCoordinates(0,vertex_queue);
        
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
    
