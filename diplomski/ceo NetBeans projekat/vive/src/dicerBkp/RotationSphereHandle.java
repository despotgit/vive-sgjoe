/*
 * RotationSphereHandle.java
 *
 * Created on Subota, 2006, Juni 17, 19.14
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package dicerBkp;

import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Color3f;
import org.apache.log4j.Logger;
import org.sgJoe.tools.VToolFactory;
import org.sgJoe.tools.decorators.VToolHandle;
import org.sgJoe.tools.interfaces.VToolForm;
import org.sgJoe.tools.interfaces.VirTool;

/**
 *
 * @author Igor
 */
public class RotationSphereHandle extends VToolHandle {
    private static Logger logger = Logger.getLogger(RotationSphereHandle.class);
    
    private float radius;
    private static final Color3f color = new Color3f (0f, 1f, 1f);
    private Transform3D newTrans = new Transform3D();
    /** Creates a new instance of RotationSphereHandle */
    public RotationSphereHandle(VirTool virToolRef, int action, float radius, float transp) {
        super (virToolRef, action);
        this.radius = radius;
        BranchGroup bg = new BranchGroup();
        VToolFactory.setBGCapabilities(bg);
        VToolFactory.setTGCapabilities(this);
        Sphere shp = new Sphere(radius, Primitive.GEOMETRY_NOT_SHARED | Primitive.GENERATE_NORMALS, 100, createAppearance(transp));
        bg.addChild(shp);
        this.addChild(bg);
    }
    private Appearance createAppearance (float transp) {
        Appearance app = new Appearance ();
        
        PolygonAttributes polyAttr = new PolygonAttributes ();
        polyAttr.setCullFace(PolygonAttributes.CULL_NONE);
        ColoringAttributes ca = new ColoringAttributes ();
        ca.setColor(color);
        TransparencyAttributes tratt = new TransparencyAttributes(TransparencyAttributes.FASTEST, transp);
        app.setColoringAttributes(ca);
        app.setPolygonAttributes(polyAttr);
        app.setTransparencyAttributes(tratt);
        
        return app;
    }

    public void onMousePressed() 
    {
        this.action = VToolForm.ACT_MOUSE_ROTATE_HANDLE_PRESSED;
    }

    public void onMouseClicked() 
    {
        this.action = VToolForm.ACT_MOUSE_ROTATE_HANDLE_CLICKED;
    }
    
    public void onMouseDragged() 
    {
        this.action = VToolForm.ACT_MOUSE_ROTATE;
    }

    public void onMouseReleased() 
    {
        this.action = VToolForm.ACT_MOUSE_ROTATE_HANDLE_RELEASED;
    }
    
    public float getRadius() 
    {
        return radius;
    }
    
    public void setScale (double newRadius) 
    {
        newTrans.setIdentity();
        newTrans.setScale(newRadius/this.radius);
        this.setTransform(newTrans);
        
    } 

    
    
}
