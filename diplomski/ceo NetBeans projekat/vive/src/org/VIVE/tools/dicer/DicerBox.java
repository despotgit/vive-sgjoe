/*
 * kvadar.java
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

public class DicerBox extends Box implements iDicerConstants
{
    private float xDim;
    private float yDim;
    private float zDim;  
        
    /** Creates a new instance of kvadar */
    public DicerBox(float aa , float bb , float cc ) 
    {
        super(aa,bb,cc, Primitive.GEOMETRY_NOT_SHARED | Primitive.GENERATE_TEXTURE_COORDS,new Appearance()); 
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
        Material material = new Material();
        app.setColoringAttributes(ca);
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
