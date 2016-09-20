package org.sgJoe.tools.scale;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Switch;
import javax.media.j3d.Transform3D;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import org.apache.log4j.Logger;
import org.sgJoe.graphics.SceneGraphEditor;
import org.sgJoe.graphics.behaviors.BehaviorObserver;
import org.sgJoe.tools.VToolFactory;
import org.sgJoe.tools.decorators.*;
import org.sgJoe.tools.interfaces.*;
import org.sgJoe.tools.planar.PlanarTranslateHandle;

/*
 * Descritpion for ScaleVirTool.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: May 4, 2006  11:51 PM  $
 */

public class ScaleVirTool extends VirTool {
    
    private static Logger logger = Logger.getLogger(ScaleVirTool.class);
    
    private static long counter = 0;

    private PlanarScaleHandle hFront = null;
    private PlanarScaleHandle hBack = null;
    
    private ScaleTouchHandle uppright = null;
    private ScaleTouchHandle midright = null;
    private ScaleTouchHandle dwnRight = null;
    private ScaleTouchHandle midUp = null;
   
    private int scaleModus = ScaleTouchHandle.MOD_SCALE_XY;
    private Point3d anchorPt = new Point3d(-1, -1, 1);
    
    public ScaleVirTool() {
        super();
        this.setToolDescription("Scale Tool - demonstrates scaling with center/anchor point paradigm");
    }
    
    public void createToolInstance(String string) {
        this.setVToolRef(new ScaleVTool(this));
        //trTG = new VToolTG();
        //VToolFactory.setTGCapabilities(trTG);        
        super.trTG.addChild(this.getVToolRef());
        
        
        BranchGroup bg = new BranchGroup();
        VToolFactory.setBGCapabilities(bg);
        // --> bg.addChild(this.getVToolRef());
        bg.addChild(super.trTG);
        this.getToolBaseSWG().addChild(bg);
        this.toolBaseTG.addChild(getToolBaseSWG());        
    }

    public void setup(SceneGraphEditor editor, BehaviorObserver behaviorObserver) {
        super.setup(editor, behaviorObserver);
        this.getVToolRef().setup();
        
        BoundingSphere toolBounds = (BoundingSphere) this.getVToolRef().getBounds();
        double Z = Math.sqrt(Math.pow(toolBounds.getRadius(), 2) / 3.0);
        
        /////////////////////////////////////////////////////////////////////////////
        
        uppright = new ScaleTouchHandle(this, VToolForm.ACT_NONE);
        uppright.setModus(ScaleTouchHandle.MOD_SCALE_XY);
        uppright.setAnchorPt(new Point3d(-1.0, -1.0, 1.0));
        
        Transform3D ruCrTR = new Transform3D();
        ruCrTR.setTranslation(new Vector3d(Z - .05, Z -.05, Z + 0.001d));
        getUppright().setTransform(ruCrTR);
        getTrTG().addChild(getUppright());
          
        /////////////////////////////////////////////////////////////////////////////
        
        /////////////////////////////////////////////////////////////////////////////
        
        setMidright(new ScaleTouchHandle(this, VToolForm.ACT_NONE));
        midright.setModus(ScaleTouchHandle.MOD_SCALE_X);
        getMidright().setAnchorPt(new Point3d(-1.0, 0.0, 1.0));
        
        Transform3D midCrTR = new Transform3D();
        midCrTR.setTranslation(new Vector3d(Z - .05, 0.0, Z + 0.001d));
        getMidright().setTransform(midCrTR);
        getTrTG().addChild(getMidright());
          
        /////////////////////////////////////////////////////////////////////////////
        
        /////////////////////////////////////////////////////////////////////////////
        
        setDwnRight(new ScaleTouchHandle(this, VToolForm.ACT_NONE));
        getDwnRight().setModus(ScaleTouchHandle.MOD_SCALE_XY);
        getDwnRight().setAnchorPt(new Point3d(-1.0, 1.0, 1.0));
        
        Transform3D dwnCrTR = new Transform3D();
        dwnCrTR.setTranslation(new Vector3d(Z - .05, -Z + 0.05, Z + 0.001d));
        getDwnRight().setTransform(dwnCrTR);
        getTrTG().addChild(getDwnRight());
          
        /////////////////////////////////////////////////////////////////////////////
        
        /////////////////////////////////////////////////////////////////////////////
        
        setMidUp(new ScaleTouchHandle(this, VToolForm.ACT_NONE));
        getMidUp().setModus(ScaleTouchHandle.MOD_SCALE_Y);
        getMidUp().setAnchorPt(new Point3d(0.0, -1.0, 1.0));
        
        Transform3D midUpTR = new Transform3D();
        midUpTR.setTranslation(new Vector3d(0.0, Z - 0.05, Z + 0.001d));
        getMidUp().setTransform(midUpTR);
        getTrTG().addChild(getMidUp());
          
        /////////////////////////////////////////////////////////////////////////////
        
        hFront = new PlanarScaleHandle(this, VToolForm.ACT_NONE);
        Transform3D curr = new Transform3D();
        getHFront().getTransform(curr);
        
        Transform3D temp = new Transform3D();
        temp.setTranslation(new Vector3d(0.0, 0.0, Z + 0.001d));
        curr.mul(temp);
        getHFront().setTransform(curr);
        
        BranchGroup bgFront = new BranchGroup();
        VToolFactory.setBGCapabilities(bgFront);
        bgFront.addChild(getHFront());
        getToolBaseSWG().addChild(bgFront);
                
        hBack = new PlanarScaleHandle(this, VToolForm.ACT_NONE);
        curr = new Transform3D();
        getHBack().getTransform(curr);
        
        temp = new Transform3D();
        temp.setTranslation(new Vector3d(0.0, 0.0, - Z - 0.001));
        curr.mul(temp);
        getHBack().setTransform(curr);
        
        BranchGroup bgBack = new BranchGroup();
        VToolFactory.setBGCapabilities(bgBack);
        bgBack.addChild(getHBack());
        getToolBaseSWG().addChild(bgBack);
        
        java.util.BitSet visibleNodes = new java.util.BitSet(getToolBaseSWG().numChildren());
        visibleNodes.set(0);
        getToolBaseSWG().setChildMask(visibleNodes);        
    }
    
    public void createForm() {
        vToolFormRef = new ScaleVToolForm(this);
    }

    public void createVUIForm() {
        vuiToolFormRef = new ScaleVUIToolForm(this);
        vuiToolFormRef.setup();
    }

    public void createPlugin() {
        vToolPluginRef = new ScaleVToolPlugin(this);        
    }

    public void createOperatorsForm() {
        vToolOperatorsFormRef = new ScaleVToolOperatorsForm(this);
    }    

    public PlanarScaleHandle getHFront() {
        return hFront;
    }

    public PlanarScaleHandle getHBack() {
        return hBack;
    }

    public ScaleTouchHandle getUppright() {
        return uppright;
    }

    public ScaleTouchHandle getMidright() {
        return midright;
    }

    public void setMidright(ScaleTouchHandle midright) {
        this.midright = midright;
    }

    public int getScaleModus() {
        return scaleModus;
    }

    public void setScaleModus(int scaleModus) {
        this.scaleModus = scaleModus;
    }

    public ScaleTouchHandle getDwnRight() {
        return dwnRight;
    }

    public void setDwnRight(ScaleTouchHandle dwnRight) {
        this.dwnRight = dwnRight;
    }

    public Point3d getAnchorPt() {
        return anchorPt;
    }

    public void setAnchorPt(Point3d anchorPt) {
        this.anchorPt = anchorPt;
    }

    public ScaleTouchHandle getMidUp() {
        return midUp;
    }

    public void setMidUp(ScaleTouchHandle midUp) {
        this.midUp = midUp;
    }

    public void setFocus() {
        System.out.println("Trying to externaly set focus on AmbLightVirTool");
    }

    public long generateUID() {
        return ++counter;
    }    

}

    

