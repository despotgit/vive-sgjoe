/*
 * DicerWireframe.java
 *
 * Created on April 20, 2007, 12:30 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.tools.dicer;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.image.ImageException;
import javax.media.j3d.*;
import javax.vecmath.*;

import org.VIVE.gui.toolPanels.toolPanelDicer;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.*;
import org.sgJoe.tools.interfaces.iSpatialField;

/**
 *
 * @author Vladimir
 */

public class DicerWireframe extends Shape3D implements iDicerConstants
{
        
    /** Creates a new instance of kvadar */
    public DicerWireframe(float aa , float bb , float cc ) 
    {
        setCaps();
          
        this.setGeometry(createGeometry(aa,bb,cc));    
        this.setAppearance(createAppearance(wireframeColor));             
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
    
    private Appearance createAppearance (Color3f color) 
    {
        Appearance app = new Appearance ();
        ColoringAttributes ca = new ColoringAttributes ();
        ca.setColor(color);
        PolygonAttributes polyAttr = new PolygonAttributes(1 , 1 , 0.0f);   // (cull_back,polygon_line...)
        Material material = new Material();
        app.setColoringAttributes(ca);
        app.setPolygonAttributes(polyAttr);
        app.setMaterial(material);
        return app;
    }   
    
    private Geometry createGeometry(float aa,float bb,float cc)
    {
        float a = aa;        
        float b = bb;
        float c = cc;
        
        /* Helping Point3f variables */
        Point3f p1 = new Point3f( -a ,  b ,  c);                      // vertex at position 13
        Point3f p2 = new Point3f( -a , -b ,  c);                      //        -II-        14          
        Point3f p3 = new Point3f(  a , -b ,  c);                      //        -II-        15
        Point3f p4 = new Point3f(  a ,  b ,  c);                      //        -II-        16 
        Point3f p5 = new Point3f( -a ,  b , -c);                      //        -II-        17
        Point3f p6 = new Point3f( -a , -b , -c);                      //        -II-        18
        Point3f p7 = new Point3f(  a , -b , -c);                      //        -II-        19
        Point3f p8 = new Point3f(  a ,  b , -c);                      //        -II-        20
                
        Point3f[] vertex_queue = new Point3f[]
        {
           p1,p4,    p2,p3,    p1,p2,    p3,p4,    
           p5,p8,    p6,p7,    p5,p6,    p7,p8,                    
           p1,p5,    p2,p6,    p3,p7,    p4,p8                                          
           
        };        
        
        LineArray wireframeG = new LineArray(24,QuadArray.COORDINATES | QuadArray.COLOR_3);
        wireframeG.setCoordinates(0,vertex_queue);
               
        for(int i=0 ; i<24 ; i++)
        {
            wireframeG.setColor(i , wireframeColor);         //coloring all the vertices with the same color
        }
        
        return wireframeG;        
        
    }
    
    
}
