package org.sgJoe.tools.lights.directional;

import org.apache.log4j.Logger;

import javax.media.j3d.*;
import org.sgJoe.tools.lights.*;


/*
 * Descritpion for DirLightVirTool.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: June 3, 2006  9:07 AM  $
 */

public class DirLightVirTool extends LightVirTool {
    
    private static Logger logger = Logger.getLogger(DirLightVirTool.class);
    
    private static long counter = 0;
    
    public DirLightVirTool() {
        super();
        setToolDescription("Directional Light Tool - You can rotate, translate, change color");
    }

    public void createToolInstance(String string) {
        this.setVToolRef(new DirLightVTool(this));
        BranchGroup bg = new BranchGroup();
        bg.addChild(this.getVToolRef());
        this.getToolBaseSWG().addChild(bg);
        this.toolBaseTG.addChild(getToolBaseSWG());        
    }
    
    public void createForm() {
        vToolFormRef = new DirLightVToolForm(this);
    }

    public void createVUIForm() {
        vuiToolFormRef = new DirLightVUIToolForm(this);
        vuiToolFormRef.setup();
    }

    public void createPlugin() {
        vToolPluginRef = new DirLightVToolPlugin(this);        
    }

    public void createOperatorsForm() {
        vToolOperatorsFormRef = new LightVToolOperatorsForm(this);
    }    

    public long generateUID() {
        return ++counter;
    }
    
}
