package org.sgJoe.tools.lights.ambient;

import javax.media.j3d.*;
import javax.vecmath.Vector3d;
import org.apache.log4j.Logger;
import org.sgJoe.graphics.SceneGraphEditor;
import org.sgJoe.graphics.behaviors.BehaviorObserver;
import org.sgJoe.tools.VToolFactory;
import org.sgJoe.tools.decorators.*;
import org.sgJoe.tools.interfaces.*;
import org.sgJoe.tools.lights.*;
import org.sgJoe.tools.sphere.*;

/*
 * Descritpion for AmbLightVirTool.java
 *
 *
 * @author   $ Author: Aleksandar Babic     $
 * @version  $ Revision:             0.1    $
 * @date     $ Date: May 19, 2006  9:35 PM  $
 */

public class AmbLightVirTool extends LightVirTool {//VirTool {
    
    private static Logger logger = Logger.getLogger(AmbLightVirTool.class);
    
    private static long counter = 0;
    
    public AmbLightVirTool() {
        super();
        this.setToolDescription("Ambient Light Tool - You can rotate, translate, change color");
    }

    public void createToolInstance(String string) {
        this.setVToolRef(new AmbLightVTool(this));
        BranchGroup bg = new BranchGroup();
        bg.addChild(this.getVToolRef());
        this.getToolBaseSWG().addChild(bg);
        this.toolBaseTG.addChild(getToolBaseSWG());        
    }

    public void createForm() {
        vToolFormRef = new LightVToolForm(this);
    }

    public void createVUIForm() {
        vuiToolFormRef = new LightVUIToolForm(this);
        vuiToolFormRef.setup();
    }

    public void createPlugin() {
        vToolPluginRef = new LightVToolPlugin(this);        
    }

    public void createOperatorsForm() {
        vToolOperatorsFormRef = new LightVToolOperatorsForm(this);
    }    

    public void setFocus() {
        System.out.println("Trying to externaly set focus on AmbLightVirTool");
    }
    
    public long generateUID() {
        return ++counter;
    }    
}
