/*
 * TranslateTouchHandle.java
 *
 * Created on Utorak, 2006, Juni 20, 0.35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.sgJoe.tools.slicer;

import javax.media.j3d.BranchGroup;
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
public class TranslateTouchHandle extends VToolHandle {
    private static Logger logger = Logger.getLogger(TranslateTouchHandle.class);
    
    public static final int MOD_NONE = 0,
                            MOD_XY = 1,
                            MOD_Z = 2;
    
    private static final Color3f color = new Color3f (1f, 0.5f, 0f); 
    private int mod = MOD_NONE;
    /**
     * Creates a new instance of TranslateTouchHandle
     */
    public TranslateTouchHandle(VirTool virToolRef, int action, float a, int mod, boolean visible) 
    {
        super (virToolRef, action);
        this.setMod(mod);
        BranchGroup bg = new BranchGroup();
        VToolFactory.setBGCapabilities(bg);
        VToolFactory.setTGCapabilities(this);
        Plate shp = new Plate (visible, a, color, 0.3f);
        bg.addChild(shp);
        this.addChild(bg);
    }

    public void onMousePressed() 
    {
        action = VToolForm.ACT_MOUSE_TRANSLATE_TOUCHHANDLE_PRESSED;
        SlicerVirTool svt = (SlicerVirTool) virToolRef;
        svt.setTranslateHandleMod(this.mod);
    }

    public void onMouseDragged() 
    {
        action = VToolForm.ACT_MOUSE_TRANSLATE_TOUCHHANDLE_DRAGGED;
    }

    public void onMouseReleased() 
    {
        action = VToolForm.ACT_MOUSE_TRANSLATE_TOUCHHANDLE_RELEASED;
    }

    public void onMouseClicked() 
    {
    }

    public int getMod() 
    {
        return mod;
    }

    public void setMod(int mod) 
    {
        this.mod = mod;
    }
    
}
