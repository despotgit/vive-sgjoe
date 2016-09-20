package org.sgJoe.tools.lights.point;

import org.apache.log4j.Logger;

import javax.media.j3d.*;
import org.sgJoe.tools.lights.*;


/*
 * Descritpion for PointLightVirTool.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: June 3, 2006  10:00 PM  $
 */

public class PointLightVirTool extends LightVirTool {
    
    private static Logger logger = Logger.getLogger(PointLightVirTool.class);
    
    private static long counter = 0;
    
    public PointLightVirTool() {
        super();
        setToolDescription("Directional Light Tool - You can rotate, translate, change color");
    }

    public void createToolInstance(String string) {
        this.setVToolRef(new PointLightVTool(this));
        BranchGroup bg = new BranchGroup();
        bg.addChild(this.getVToolRef());
        this.getToolBaseSWG().addChild(bg);
        this.toolBaseTG.addChild(getToolBaseSWG());        
    }
    
    public void createForm() {
        vToolFormRef = new PointLightVToolForm(this);
    }

    public void createVUIForm() {
        vuiToolFormRef = new PointLightVUIToolForm(this);
        vuiToolFormRef.setup();
    }

    public void createPlugin() {
        vToolPluginRef = new PointLightVToolPlugin(this);        
    }

    public void createOperatorsForm() {
        vToolOperatorsFormRef = new LightVToolOperatorsForm(this);
    }    

    public long generateUID() {
        return ++counter;
    }
    
}
