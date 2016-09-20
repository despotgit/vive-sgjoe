/*
 * GuideHandle.java
 *
 * Created on Sreda, 2006, Maj 31, 22.45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.sgJoe.tools.slicer;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.LineAttributes;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.TransparencyAttributes;
import org.apache.log4j.Logger;
import org.sgJoe.tools.VToolFactory;
import org.sgJoe.tools.decorators.VToolHandle;
import org.sgJoe.tools.interfaces.VToolForm;
import org.sgJoe.tools.interfaces.VirTool;

/**
 *
 * @author Igor
 */
public class GuideHandle extends VToolHandle 
{    
    private static Logger logger = Logger.getLogger(GuideHandle.class);
    
    public static final int MOD_NONE = 0,
                            MOD_SCALE_XY = 1,
                            MOD_TRANSLATE_XY = 2,
                            MOD_TRANSLATE_Z = 3;
    private int mod = MOD_NONE;
    
    /**
     * Creates a new instance of GuideHandle
     */
    public GuideHandle(VirTool virToolRef, int action, float a, float b, int mod) 
    {
        super (virToolRef, action);
        
        this.setMod(mod);
        BranchGroup bg = new BranchGroup();
        VToolFactory.setBGCapabilities(bg);
        VToolFactory.setTGCapabilities(this);
        
        Appearance app = new Appearance ();
        PolygonAttributes polyAttrs = new PolygonAttributes();
        polyAttrs.setPolygonMode(PolygonAttributes.POLYGON_LINE);
        LineAttributes lineAttr = new LineAttributes ();
        TransparencyAttributes transAttrs = new TransparencyAttributes(TransparencyAttributes.FASTEST, 1f);
        
//        lineAttr.setPatternMask(0);
//        lineAttr.setLinePattern(LineAttributes.PATTERN_USER_DEFINED);
//        lineAttr.
        app.setPolygonAttributes(polyAttrs);
        app.setLineAttributes(lineAttr);
        app.setTransparencyAttributes(transAttrs);
        
        Box box = new Box (a, b, 0.01f, Primitive.GEOMETRY_NOT_SHARED, app);
        box.setAppearance(app);
        
        bg.addChild(box);
        this.addChild(bg);
    }

    public void onMousePressed() 
    {
        action = VToolForm.ACT_MOUSE_GUIDEHANDLE_PRESSED;
    }

    public void onMouseDragged() 
    {
        action = VToolForm.ACT_MOUSE_GUIDEHANDLE_DRAGGED;
    }

    public void onMouseReleased() 
    {
        action = VToolForm.ACT_MOUSE_GUIDEHANDLE_RELEASED;
    }

    public int getMod() 
    {
        return mod;
    }

    public void setMod(int mod) 
    {
        this.mod = mod;
    }

    public void onMouseClicked() 
    {
    }
    
}
