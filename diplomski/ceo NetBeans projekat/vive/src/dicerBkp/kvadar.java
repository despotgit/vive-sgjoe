/*
 * kvadar.java
 *
 * Created on April 20, 2007, 12:30 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package dicerBkp;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import javax.media.j3d.*;
import javax.vecmath.*;

/**
 *
 * @author Vladimir Despotovic
 */

public class kvadar extends Box implements iDicerConstants
{
    private float xDim;
    private float yDim;
    private float zDim;
        
    /** Creates a new instance of kvadar */
    public kvadar(float aa , float bb , float cc) 
    {
        super(aa,bb,cc, Primitive.GEOMETRY_NOT_SHARED,new Appearance()); 
        xDim=aa;
        yDim=bb;
        zDim=cc;
        
       setAppearance(createAppearance(faceColor));            
        
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
        LineAttributes lineAttr = new LineAttributes();
        lineAttr.setLinePattern(LineAttributes.PATTERN_DASH);
        lineAttr.setLineAntialiasingEnable(true);
        Material material = new Material();
        app.setColoringAttributes(ca);
        app.setLineAttributes(lineAttr);
        app.setMaterial(material);
        return app;
    }
    
    public float getXdim()
    {
        return xDim;
    }
    
    public void setXdim(float aa)
    {
        xDim=aa;
    }
    
    public float getYdim()
    {
        return yDim;
    }
    
    public void setYdim(float bb)
    {
        yDim=bb;
    }
    
    public float getZdim()
    {
        return zDim;
    }    
    
    public void setZdim(float cc)
    {
        zDim=cc;
    }
    
}
