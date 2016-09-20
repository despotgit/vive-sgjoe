package org.sgJoe.tools.planar;


import javax.vecmath.Vector3d;
import org.apache.log4j.Logger;
import org.sgJoe.graphics.SceneGraphEditor;
import org.sgJoe.graphics.behaviors.BehaviorObserver;
import org.sgJoe.tools.VToolFactory;
import org.sgJoe.tools.decorators.SliderVToolHandle;
import org.sgJoe.tools.interfaces.*;
import javax.media.j3d.*;


/*
 * Descritpion for PlanarVirTool.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: April 26, 2006  9:08 AM    $
 */

public class PlanarVirTool extends VirTool {
    
    private static Logger logger = Logger.getLogger(PlanarVirTool.class);
    
    private static long counter = 0;
    
    private SliderVToolHandle sliderHandle = null;
    
    public PlanarVirTool() {
        super();
        this.setToolDescription("Planar Tool - demonstrates planar translation paradigm");
    }

    public void setup(SceneGraphEditor editor, BehaviorObserver behaviorObserver) 
    {
        System.out.println("setup PlanarVirToola");
        super.setup(editor, behaviorObserver);
        this.getVToolRef().setup();
        
        PlanarTranslateHandle handle = new PlanarTranslateHandle(this, PlanarVToolForm.ACT_NONE);
        Transform3D tr = new Transform3D();
        handle.getTransform(tr);
        tr.setTranslation(new Vector3d(0.0, 1.2, .9));
        handle.setTransform(tr);
        BranchGroup bg = new BranchGroup();
        VToolFactory.setBGCapabilities(bg);
        bg.addChild(handle);
        getVToolRef().addChild(bg);
    }
    
    public void createToolInstance(String string) 
    {
        System.out.println("createToolInstance PlanarVirTool-a.");
        this.setVToolRef(new PlanarVTool(this));
        BranchGroup bg = new BranchGroup();
        bg.addChild(this.getVToolRef());
        this.toolBaseTG.addChild(bg);        
    }

    public void createForm() {
        vToolFormRef = new PlanarVToolForm(this);
    }

    public void createVUIForm() {
        vuiToolFormRef = new PlanarVUIToolForm(this);
        vuiToolFormRef.setup();
    }

    public void createPlugin() {
        vToolPluginRef = new PlanarVToolPlugin(this);        
    }

    public void createOperatorsForm() {
        vToolOperatorsFormRef = new PlanarVToolOperatorsForm(this);
    }

    public SliderVToolHandle getSliderHandle() {
        return sliderHandle;
    }

    public void setSliderHandle(SliderVToolHandle sliderHandle) {
        this.sliderHandle = sliderHandle;
    }
    
    public void setFocus() {
        System.out.println("Trying to externaly set focus on AmbLightVirTool");
    }    
    
    public long generateUID() {
        return ++counter;
    }    
}
    
