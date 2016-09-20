package org.sgJoe.tools.templates.translate;

import javax.media.j3d.BranchGroup;
import org.apache.log4j.Logger;
import org.sgJoe.graphics.SceneGraphEditor;
import org.sgJoe.graphics.behaviors.BehaviorObserver;
import org.sgJoe.tools.VToolFactory;
import org.sgJoe.tools.decorators.*;
import org.sgJoe.tools.interfaces.*;
import org.sgJoe.tools.templates.*;
import org.sgJoe.tools.templates.translate.cube.PlaneCubeTool;
import org.sgJoe.tools.templates.translate.sphere.PlaneSphereTool;


/*
 * Descritpion for VirPlaneTool.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: June 17, 2006  8:54 AM  $
 */

public abstract class VirPlaneTool extends VirTool {
    
    private static Logger logger = Logger.getLogger(VirPlaneTool.class);
    
    
    public void setup(SceneGraphEditor editor, BehaviorObserver behaviorObserver) {
        super.setup(editor, behaviorObserver);
        this.getVToolRef().setup();
          
        VToolHandle planeHandle = new PlaneHandle(this, VToolForm.ACT_MOUSE_TRANSLATE_XY);
        planeHandle.setInstanceName("PLANE");
        addToolHandle("PLANE", planeHandle);
 
        BranchGroup planeBg = new BranchGroup();
        VToolFactory.setBGCapabilities(planeBg);
        planeBg.addChild(getPlaneHandle());
        getToolBaseSWG().addChild(planeBg);        
               
        java.util.BitSet visibleNodes = new java.util.BitSet(getToolBaseSWG().numChildren());
        visibleNodes.set(0);
        getToolBaseSWG().setChildMask(visibleNodes);        
    }
    
    

    public void createForm() {
        vToolFormRef = new PlaneForm(this);        
    }

    public void createVUIForm() {
        vuiToolFormRef = new PlaneGUIForm(this);
        vuiToolFormRef.setup();        
    }

    public void createPlugin() {
        vToolPluginRef = new PlanePlugin(this);        
    }

    public void createOperatorsForm() {
        vToolOperatorsFormRef = new PlaneOperatorsForm(this);
    }

    public PlaneHandle getPlaneHandle() {
        return (PlaneHandle) getToolHandle("PLANE");
    }    
    
    public void setFocus() {
        System.out.println("Trying to externaly set focus on template VirPlaneTool");
    }        
}
