package org.sgJoe.tools.sphere;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Switch;
import javax.media.j3d.Transform3D;
import javax.vecmath.Vector3d;
import org.apache.log4j.Logger;
import org.sgJoe.graphics.SceneGraphEditor;
import org.sgJoe.graphics.behaviors.BehaviorObserver;
import org.sgJoe.tools.VToolFactory;
import org.sgJoe.tools.interfaces.*;
import org.sgJoe.tools.planar.PlanarTranslateHandle;


/*
 * Descritpion for SphereVirTool.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: April 28, 2006  9:26 PM  $
 */

public class SphereVirTool extends VirTool {
    
    private static Logger logger = Logger.getLogger(SphereVirTool.class);
    
    private static long counter = 0;
    
    public SphereVirTool() {
        super();
        this.setToolDescription("Sphere Tool - demonstrates sphere rotation pradigm");
    }
    
    public void createToolInstance(String string) {
        this.setVToolRef(new SphereVTool(this));
        BranchGroup bg = new BranchGroup();
        bg.addChild(this.getVToolRef());
        this.getToolBaseSWG().addChild(bg);
        this.toolBaseTG.addChild(getToolBaseSWG());        
    }

    public void setup(SceneGraphEditor editor, BehaviorObserver behaviorObserver) {
        super.setup(editor, behaviorObserver);
        this.getVToolRef().setup();
          
        SphereRotationHandle handle = new SphereRotationHandle(this, VToolForm.ACT_MOUSE_ROTATE);
        //SphereRotationHandle handle = new SphereRotationHandle(this, VToolForm.ACT_NONE);
        Transform3D curr = new Transform3D();
        handle.getTransform(curr);
        BoundingSphere toolBounds = (BoundingSphere) this.getVToolRef().getBounds();
        
        Transform3D temp = new Transform3D();
        temp.setScale( (toolBounds.getRadius() + 0.05) );
        curr.mul(temp);
        handle.setTransform(curr);
        
        BranchGroup bg = new BranchGroup();
        VToolFactory.setBGCapabilities(bg);
        bg.addChild(handle);
        getToolBaseSWG().addChild(bg);
                
        java.util.BitSet visibleNodes = new java.util.BitSet(getToolBaseSWG().numChildren());
        visibleNodes.set(0);
        getToolBaseSWG().setChildMask(visibleNodes);        
    }
    
    public void createForm() {
        vToolFormRef = new SphereVToolForm(this);
    }

    public void createVUIForm() {
        vuiToolFormRef = new SphereVUIToolForm(this);
        vuiToolFormRef.setup();
    }

    public void createPlugin() {
        vToolPluginRef = new SphereVToolPlugin(this);        
    }

    public void createOperatorsForm() {
        vToolOperatorsFormRef = new SphereVToolOperatorsForm(this);
    }    

    public void setFocus() {
        System.out.println("Trying to externaly set focus on SphereVirTool");
    }
    
    public long generateUID() {
        return ++counter;
    }    

}

    

