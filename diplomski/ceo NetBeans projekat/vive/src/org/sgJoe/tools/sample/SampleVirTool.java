package org.sgJoe.tools.sample;

import com.sun.j3d.utils.geometry.ColorCube;

import javax.vecmath.Vector3d;
import org.apache.log4j.Logger;
import org.sgJoe.graphics.SceneGraphEditor;
import org.sgJoe.graphics.behaviors.BehaviorObserver;
import org.sgJoe.tools.VToolFactory;
import org.sgJoe.tools.interfaces.*;
import javax.media.j3d.*;


/*
 * Descritpion for SampleVirTool.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: April 23, 2006  4:48 PM    $
 */

public class SampleVirTool extends VirTool {
    
    private static Logger logger = Logger.getLogger(SampleVirTool.class);
    
    private static long counter = 0;
    
    public SampleVirTool() {
        super();
        this.setToolDescription("Sample Tool - first tool that demonstrates virtual tool pradigm");
    }
    
    public void setup(SceneGraphEditor editor, BehaviorObserver behaviorObserver) {
        super.setup(editor, behaviorObserver);
        this.getVToolRef().setup();
        
        SampleTranslateHandle handle = new SampleTranslateHandle(this, SampleVToolForm.ACT_MOUSE_TRANSLATE_Y);
        Transform3D tr = new Transform3D();
        handle.getTransform(tr);
        tr.setTranslation(new Vector3d(0.0, 1.0, 0.0));
        handle.setTransform(tr);
        BranchGroup bg = new BranchGroup();
        VToolFactory.setBGCapabilities(bg);
        bg.addChild(handle);
        this.toolBaseTG.addChild(bg);
        
    }
    public void createToolInstance(String string) {
        this.setVToolRef(new SampleVTool(this));
        BranchGroup bg = new BranchGroup();
        bg.addChild(this.getVToolRef());
        this.toolBaseTG.addChild(bg);
    }

    public void createForm() {
        vToolFormRef = new SampleVToolForm(this);
    }

    public void createVUIForm() {
        vuiToolFormRef = new SampleVUIToolForm(this);
        vuiToolFormRef.setup();
    }

    public void createPlugin() {
        vToolPluginRef = new SampleVToolPlugin(this);
    }

    public void createOperatorsForm() {
        vToolOperatorsFormRef = new SampleVToolOperatorsForm(this);
    }
    
    public void setFocus() {
        System.out.println("Trying to externaly set focus on AmbLightVirTool");
    }    
    
    public long generateUID() {
        return ++counter;
    }    
}
