/*
 * SwitchHandle.java
 *
 * Created on Utorak, 2006, Juni 20, 21.23
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
import javax.media.j3d.Shape3D;
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
public class SwitchHandle extends VToolHandle {
    
    private static Logger logger = Logger.getLogger(SwitchHandle.class);
    
    public static final int MOD_NONE = 0,
                            MOD_SWITCH_ROTSPHERE_ON = 1,
                            MOD_SWITCH_ROTSPHERE_OFF = 2;
    private static final Color3f color = new Color3f(1f, 0f, 0f);
    
    private float size =0;
    private int mod = MOD_NONE;
    
    /** Creates a new instance of SwitchHandle */
    public SwitchHandle(VirTool virToolRef, int action, int mod, float radius, float transpLevel) 
    {
        super (virToolRef, action);
        BranchGroup bg = new BranchGroup();
        VToolFactory.setBGCapabilities(bg);
        VToolFactory.setTGCapabilities(this);
        this.mod = mod;
        if (mod == MOD_SWITCH_ROTSPHERE_ON || mod == MOD_SWITCH_ROTSPHERE_OFF) 
        {
            Sphere shp = new Sphere (radius, Primitive.GEOMETRY_NOT_SHARED | Primitive.GENERATE_NORMALS, 50, createAppearance(transpLevel));
            this.size = radius;
            bg.addChild(shp);
            this.addChild(bg);
        }
        
    }
        private Appearance createAppearance (float transpLevel) 
        {
        Appearance app = new Appearance ();
        PolygonAttributes polyAttr = new PolygonAttributes ();
        polyAttr.setCullFace(PolygonAttributes.CULL_NONE);
//        LineAttributes lineAttr = new LineAttributes();
//        lineAttr.setLineAntialiasingEnable(true);
        ColoringAttributes ca = new ColoringAttributes ();
        ca.setColor(color);
        TransparencyAttributes tratt = new TransparencyAttributes(TransparencyAttributes.FASTEST, transpLevel);
        app.setColoringAttributes(ca);
        app.setPolygonAttributes(polyAttr);
        app.setTransparencyAttributes(tratt);
//        app.setLineAttributes(lineAttr);
        
        return app;
    }
    public void onMousePressed() 
    {   
        if (this.mod == MOD_SWITCH_ROTSPHERE_ON) 
        {
            this.mod = MOD_SWITCH_ROTSPHERE_OFF;
            action = VToolForm.ACT_MOUSE_SWITCH_SPHERE_ON_PRESSED;
        } 
        else if (this.mod == MOD_SWITCH_ROTSPHERE_OFF) 
        {
            this.mod = MOD_SWITCH_ROTSPHERE_ON;
            action = VToolForm.ACT_MOUSE_SWITCH_SPHERE_OFF_PRESSED;
        }
        
    }

    public void onMouseDragged() {
    }

    public void onMouseReleased() {
    }

    public float getSize() {
        return size;
    }

    public void onMouseClicked() {
    }
}
