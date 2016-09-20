package org.sgJoe.tools.lights;

import javax.media.j3d.*;
import javax.vecmath.Vector3d;
import org.apache.log4j.Logger;
import org.sgJoe.graphics.SceneGraphEditor;
import org.sgJoe.graphics.behaviors.BehaviorObserver;
import org.sgJoe.tools.VToolFactory;
import org.sgJoe.tools.decorators.*;
import org.sgJoe.tools.interfaces.*;
import org.sgJoe.tools.sphere.*;

/*
 * Descritpion for LightVirTool.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: June 3, 2006  8:47 AM  $
 */

public abstract class LightVirTool extends VirTool {
    
    private static Logger logger = Logger.getLogger(LightVirTool.class);

    public void setup(SceneGraphEditor editor, BehaviorObserver behaviorObserver) {
        super.setup(editor, behaviorObserver);
        this.getVToolRef().setup();
          
        VToolHandle rotHandle = new LightRotateHandle(this, VToolForm.ACT_MOUSE_ROTATE);
        rotHandle.setInstanceName("ROTATE");
        addToolHandle("ROTATE", rotHandle);
        
        VToolHandle planeHandle = new LightPlaneHandle(this, VToolForm.ACT_MOUSE_TRANSLATE_XY);
        planeHandle.setInstanceName("PLANE");
        addToolHandle("PLANE", planeHandle);
        
        VToolHandle frontHandle = new LightPositionHandle(this, VToolForm.ACT_NONE);
        frontHandle.setInstanceName("POSITION");
        addToolHandle("POSITION", frontHandle);        
       
        BranchGroup rotBg = new BranchGroup();
        VToolFactory.setBGCapabilities(rotBg);
        rotBg.addChild(getRotHandle());
        getToolBaseSWG().addChild(rotBg);        
        
        BranchGroup posBg = new BranchGroup();
        VToolFactory.setBGCapabilities(posBg);
        posBg.addChild(getFrontHandle());
        getToolBaseSWG().addChild(posBg);
        
        BranchGroup sliderBg = new BranchGroup();
        VToolFactory.setBGCapabilities(sliderBg);
        sliderBg.addChild(getSliderHandle());
        getToolBaseSWG().addChild(sliderBg);        
               
        java.util.BitSet visibleNodes = new java.util.BitSet(getToolBaseSWG().numChildren());
        visibleNodes.set(0);
        getToolBaseSWG().setChildMask(visibleNodes);        
    }    

    public LightRotateHandle getRotHandle() {
        return (LightRotateHandle) getToolHandle("ROTATE");
    }
    
    public LightPositionHandle getFrontHandle() {
        return (LightPositionHandle) getToolHandle("POSITION");
    }

    public LightPlaneHandle getSliderHandle() {
        return (LightPlaneHandle) getToolHandle("PLANE");
    }

    public void setFocus() {
        System.out.println("Trying to externaly set focus on AmbLightVirTool");
    }
}
