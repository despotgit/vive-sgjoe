package org.sgJoe.tools.lights.spot;

import org.apache.log4j.Logger;

import javax.media.j3d.*;
import org.sgJoe.tools.lights.*;

/*
 * Descritpion for SpotLightVirTool.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: June 4, 2006  10:00 AM  $
 */

public class SpotLightVirTool extends LightVirTool {
    
    private static Logger logger = Logger.getLogger(SpotLightVirTool.class);
    
    private static long counter = 0;
    
    public SpotLightVirTool() {
        super();
        setToolDescription("Spot Light Tool - You can rotate, translate, change color");
    }

    public void createToolInstance(String string) {
        this.setVToolRef(new SpotLightVTool(this));
        BranchGroup bg = new BranchGroup();
        bg.addChild(this.getVToolRef());
        this.getToolBaseSWG().addChild(bg);
        this.toolBaseTG.addChild(getToolBaseSWG());        
    }
    
    public void createForm() {
        vToolFormRef = new SpotLightVToolForm(this);
    }

    public void createVUIForm() {
        vuiToolFormRef = new SpotLightVUIToolForm(this);
        vuiToolFormRef.setup();
    }

    public void createPlugin() {
        vToolPluginRef = new SpotLightVToolPlugin(this);        
    }

    public void createOperatorsForm() {
        vToolOperatorsFormRef = new LightVToolOperatorsForm(this);
    } 
    
    public long generateUID() {
        return ++counter;
    }    
}
