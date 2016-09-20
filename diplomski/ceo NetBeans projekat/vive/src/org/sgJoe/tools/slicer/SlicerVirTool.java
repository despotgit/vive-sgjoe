/*
 * SlicerVirTool.java
 *
 * Created on Sreda, 2006, Maj 31, 22.30
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.sgJoe.tools.slicer;

import java.util.BitSet;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Switch;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JPanel;
import javax.vecmath.Vector3d;
import org.VIVE.gui.toolOperators.BrightnessContrast;
import org.VIVE.gui.toolOperators.Navigation;
import org.VIVE.gui.toolOperators.Visibility;
import org.VIVE.gui.toolOperators.VisibilityComponent;
import org.VIVE.gui.toolOperators.interfaces.NavigationConstants;
import org.VIVE.gui.toolOperators.interfaces.iBrightness;
import org.VIVE.gui.toolOperators.interfaces.iNavigation;
import org.VIVE.gui.toolOperators.interfaces.iVisibility;
import org.VIVE.gui.toolPanels.toolPanelSlicer;
import org.apache.log4j.Logger;
import org.sgJoe.graphics.SceneGraphEditor;
import org.sgJoe.graphics.behaviors.BehaviorObserver;
import org.sgJoe.tools.VToolFactory;
import org.sgJoe.tools.interfaces.VToolForm;
import org.sgJoe.tools.interfaces.VirTool;

/**
 *
 * @author Igor
 */
public class SlicerVirTool extends VirTool implements iVisibility, iNavigation, iBrightness{
    private static Logger logger = Logger.getLogger(SlicerVirTool.class);
    //Counts class instances
    private static long counter = 0;
    //GUI
    private JPanel slicerToolPanel;
    private JPanel[] slicerOperatorsPanel;
    //Move step
    private static double moveStep = 1d;
    //Visibility levels
    public static final int VIS_HIDDEN = 0,
                            VIS_SIMPLE = 1,
                            VIS_FRAMED = 2,
                            VIS_HANDLES_ON = 3,
                            VIS_ROT_SPHERE_ON = 4,
                            VIS_SCALING = 5,
                            VIS_ROTATING = 6,
                            VIS_TRANSLATING_XY = 7,
                            VIS_TRANSLATING_Z = 8,
                            VIS_ALL = 9; //for testin purpose only
    //Current vilibility level. Visibility level defines witch handles and guider are visible
    //See more detail on setVisibility()
    private int visibility;
    private boolean locked = false;
    
    //Touche handles 
    //Scale touch handles (together form frame)
    private ScaleTouchHandle uppRight = null;
    private ScaleTouchHandle uppMid = null;
    private ScaleTouchHandle uppLeft = null;
    private ScaleTouchHandle leftMid = null;
    private ScaleTouchHandle dwnLeft = null;
    private ScaleTouchHandle dwnMid = null;
    private ScaleTouchHandle dwnRight = null;
    private ScaleTouchHandle rightMid = null;
    
    //Translate Z touch handle (For XY translation VTool is used as a touch handle)
    private TranslateTouchHandle transZTouchHandle = null;
    
    //Rotation sphere handle
    private RotationSphereHandle rotSphereHandle = null;
    
    //Switch rotation sphere handle On/Off
    private SwitchHandle rotationSwitchHandle = null;
    
    //Guide handles are used as guiders during certain operations over specific surfaces
    //xyGuider is used for XY translation and scaling
    private GuideHandle xyGuider = null;
    private GuideHandle transZGuider = null;
    
    //Defines exactly which scale handle is pressed
    private int scaleHandleId = ScaleTouchHandle.NONE_ID;
    
    //Just in case if there is more than one Translate touch handle
    private int translateHandleMod = TranslateTouchHandle.MOD_NONE;
    
    //Defines with which guider to work with
    private int guiderMod = GuideHandle.MOD_NONE;
    
    //Plates dimensions. Since plate is a square only a dimension is enough
    private float a;
    private float b;
    //Used while positioning handles (initial position)
    private Vector3d posVector;
    private Vector3d oldPosVector;
    private Transform3D posTr;
    
    //nodes
    private TransformGroup rotTG;
    private TransformGroup transTG;
    private Switch localSWG;
    private BranchGroup vToolScaledBG;
    private BranchGroup notVToolScaledBG;
    private TransformGroup scaleTG;
    private Switch vToolSWG;
    private Switch notVToolSWG;
    
    //visible nodes on each SWG
    private java.util.BitSet localVisibleNodes;
    private java.util.BitSet vToolVisibleNodes;
    private java.util.BitSet notVToolVisibleNodes;
    
    
    private TransformGroup ZTg;
    /**
     * Creates a new instance of SlicerVirTool
     */
    public SlicerVirTool() {        
        super();
        System.out.println("Slicer constructor beginning.");
        slicerToolPanel = new toolPanelSlicer();
        slicerOperatorsPanel = new JPanel[1];
        VisibilityComponent[] visComps = new VisibilityComponent [5];
        visComps[0] = new VisibilityComponent("Hidden", VIS_HIDDEN);
        visComps[1] = new VisibilityComponent("Simple", VIS_SIMPLE);
        visComps[2] = new VisibilityComponent("Framed", VIS_FRAMED);
        visComps[3] = new VisibilityComponent("Handles on", VIS_HANDLES_ON);
        visComps[4] = new VisibilityComponent("Rotation sphere on", VIS_ROT_SPHERE_ON);
       
        Visibility tmpVis = new Visibility(visComps, this);
        tmpVis.setVisibilityLevel(SlicerVirTool.VIS_ALL);
        slicerOperatorsPanel[0] = tmpVis;
        this.setInstanceName("Slicer#" + generateUID());
        this.setToolDescription("Slicer tool");
//        slicerOperatorsPanel[1] = new Navigation(Navigation.KRETANJE|Navigation.AKCIJE|Navigation.FIT,this);
//        slicerOperatorsPanel[2] = new BrightnessContrast(this);
    }

    public void createToolInstance(String string) {
        this.setVToolRef(new SlicerVTool(this));
        System.out.println("Create Tool Instance SlicerVirTool-a");
        
        //Create nodes and set proper capabilities
        setRotTG(new TransformGroup());
        VToolFactory.setTGCapabilities(getRotTG());
        setTransTG(new TransformGroup());
        VToolFactory.setTGCapabilities(getTransTG());
        setLocalSWG(new Switch(Switch.CHILD_MASK));
        VToolFactory.setSWGCapabilities(getLocalSWG());
        setVToolScaledBG(new BranchGroup());
        VToolFactory.setBGCapabilities(getVToolScaledBG());
        setNotVToolScaledBG(new BranchGroup());;
        VToolFactory.setBGCapabilities(getNotVToolScaledBG());
        setScaleTG(new TransformGroup());
        VToolFactory.setTGCapabilities(getScaleTG());
        setVToolSWG(new Switch(Switch.CHILD_MASK));
        VToolFactory.setSWGCapabilities(getVToolSWG());
        setNotVToolSWG(new Switch(Switch.CHILD_MASK));   
        VToolFactory.setSWGCapabilities(getNotVToolSWG());
        
        //Used for positioning certain handles
        setPosVector(new Vector3d(0d, 0d, 0d));
        setOldPosVector(new Vector3d(0d, 0d, 0d));
        setPosTr(new Transform3D());
        
        //Tree structure. Order is important!
        BranchGroup bg = new BranchGroup ();
        VToolFactory.setBGCapabilities(bg);
        this.toolBaseSWG.addChild(bg);
        this.toolBaseTG.addChild(this.toolBaseSWG);
        
        bg.addChild(getTransTG());
        getTransTG().addChild(getRotTG());
        getRotTG().addChild(getLocalSWG());
        
        getLocalSWG().addChild(getVToolScaledBG());
        getLocalSWG().addChild(getNotVToolScaledBG());
        getVToolScaledBG().addChild(getScaleTG());
        getScaleTG().addChild(getVToolSWG());
        getNotVToolScaledBG().addChild(getNotVToolSWG());
        
        getVToolSWG().addChild(getVToolRef());
        
        ZTg = new TransformGroup();
        VToolFactory.setTGCapabilities(getZTg());
    }
    
    public void setup (SceneGraphEditor editor, BehaviorObserver behaviorObserver) 
    {
        System.out.println("SlicerVirTool setup method called.");
        super.setup(editor, behaviorObserver);
        this.getVToolRef().setup();
        setA(((SlicerVTool)this.getVToolRef()).getShp().getA());
        setB(((SlicerVTool)this.getVToolRef()).getShp().getB());
        
    //Putting handles
        //UppRight scale touch handle (1)  
       setUppRight(new ScaleTouchHandle(this, VToolForm.ACT_NONE, ScaleTouchHandle.UPP_RIGHT_ID, "UppRight", SFrame.CORNER, getA()));
       getPosVector().set(getA(), getB(), 0d);
       getPosTr().setTranslation(getPosVector());
       getUppRight().setTransform(getPosTr());
       this.getVToolSWG().addChild(getUppRight());
       
       //UppMid scale touch handle (2)
       setUppMid(new ScaleTouchHandle(this, VToolForm.ACT_NONE, ScaleTouchHandle.UPP_MID_ID, "UppMid", SFrame.EDGE, getA()));
       getPosVector().set(0d, getB(), 0d);
       getPosTr().setTranslation(getPosVector());
       getUppMid().setTransform(getPosTr());
       this.getVToolSWG().addChild(getUppMid());
       
       //UppLeft scale touch handle (3)
       setUppLeft(new ScaleTouchHandle(this, VToolForm.ACT_NONE,ScaleTouchHandle.UPP_LEFT_ID, "UppLeft", SFrame.CORNER, getA()));
       getPosVector().set(-getA(), getB(), 0d);
       getPosTr().rotZ(Math.PI/2d);
       getPosTr().setTranslation(getPosVector());
       getUppLeft().setTransform(getPosTr());
       this.getVToolSWG().addChild(getUppLeft());
       
       //leftMid scale touch handle (4)
       setLeftMid(new ScaleTouchHandle(this, VToolForm.ACT_NONE, ScaleTouchHandle.LEFT_MID_ID, "LeftMid", SFrame.EDGE, getA()));
       getPosVector().set(-getA(), 0d, 0d);
       getPosTr().rotZ(Math.PI/2d);
       getPosTr().setTranslation(getPosVector());
       getLeftMid().setTransform(getPosTr());
       this.getVToolSWG().addChild(getLeftMid());
       
       //dwnLeft scale touch handle (5)
       setDwnLeft(new ScaleTouchHandle(this, VToolForm.ACT_NONE, ScaleTouchHandle.DWN_LEFT_ID, "DwnLeft", SFrame.CORNER, getA()));
       getPosVector().set(-getA(), -getB(), 0d);
       getPosTr().rotZ(Math.PI);
       getPosTr().setTranslation(getPosVector());
       getDwnLeft().setTransform(getPosTr());
       this.getVToolSWG().addChild(getDwnLeft());
       
       //dwnMid scale touch handle (6)
       setDwnMid(new ScaleTouchHandle(this, VToolForm.ACT_NONE, ScaleTouchHandle.DWN_MID_ID, "DwnMid", SFrame.EDGE, getA()));
       getPosVector().set(0d, -getB(), 0d);
       getPosTr().rotZ(Math.PI);
       getPosTr().setTranslation(getPosVector());
       getDwnMid().setTransform(getPosTr());
       this.getVToolSWG().addChild(getDwnMid());
       
       //dwnRight scale touch handle (7)
       setDwnRight(new ScaleTouchHandle(this, VToolForm.ACT_NONE, ScaleTouchHandle.DWN_RIGHT_ID, "DwnRight", SFrame.CORNER, getA()));
       getPosVector().set(getA(), -getB(), 0d);
       getPosTr().rotZ(-Math.PI/2d);
       getPosTr().setTranslation(getPosVector());
       getDwnRight().setTransform(getPosTr());
       this.getVToolSWG().addChild(getDwnRight());
       
       //rightMid scale touch handle (8)
       setRightMid(new ScaleTouchHandle(this, VToolForm.ACT_NONE, ScaleTouchHandle.RIGHT_MID_ID, "RightMid", SFrame.EDGE, getA()));
       getPosVector().set(getA(), 0d, 0d);
       getPosTr().rotZ(-Math.PI/2d);
       getPosTr().setTranslation(getPosVector());
       getRightMid().setTransform(getPosTr());
       this.getVToolSWG().addChild(getRightMid());
       
       //XYplate Guider (9)
       xyGuider = new GuideHandle(this, VToolForm.ACT_NONE, 100*getA(), 100*getB(), GuideHandle.MOD_NONE);
       getPosVector().set(0d, 0d, 0d);
       getPosTr().setTranslation(getPosVector());
       getXyGuider().setTransform(getPosTr());
       getVToolSWG().addChild(getXyGuider());
       
       //transZTouchHandle (10)
       setTransZTouchHandle(new TranslateTouchHandle(this, VToolForm.ACT_NONE, getA() / (float)(Math.sqrt(2)), TranslateTouchHandle.MOD_Z, true));
       Transform3D tempTrans = new Transform3D();
       Transform3D tempTrans2 = new Transform3D();
       tempTrans.rotY(Math.PI/2);
       tempTrans.setTranslation(new Vector3d(-getA(), 0d, 0d));
       tempTrans2.rotZ(Math.PI/4);
       tempTrans.mul(tempTrans2);
       getTransZTouchHandle().setTransform(tempTrans);
       getZTg().addChild(getTransZTouchHandle());
       getVToolSWG().addChild(getZTg());
//       getVToolSWG().addChild(getTransZTouchHandle());
       
       //transZGuider (11)
       setTransZGuider(new GuideHandle(this, VToolForm.ACT_NONE, 10* getA(), 10* getB(), GuideHandle.MOD_TRANSLATE_Z));
       tempTrans.setIdentity();
       tempTrans.rotY(Math.PI/2);
       tempTrans.setTranslation(new Vector3d(-getA(), 0d, 0d));
       getTransZGuider().setTransform(tempTrans);
       getVToolSWG().addChild(getTransZGuider());
             
       //Rotation switch (0)
       setRotationSwitchHandle(new SwitchHandle(this, VToolForm.ACT_NONE, SwitchHandle.MOD_SWITCH_ROTSPHERE_ON, getA() / 8, 0.7f));
       getPosVector().set(getA() + this.getRotationSwitchHandle().getSize(), getB() + this.getRotationSwitchHandle().getSize(), 0d);
       getPosTr().setTranslation(getPosVector());
       getRotationSwitchHandle().setTransform(getPosTr());
       getNotVToolSWG().addChild(getRotationSwitchHandle());
       
       //Rotation Sphere (1)
       setRotSphereHandle(new RotationSphereHandle(this, VToolForm.ACT_NONE, (float) (Math.sqrt(Math.pow(getA(), 2)  + Math.pow(getB(), 2))), 0.7f));
       getNotVToolSWG().addChild(getRotSphereHandle());
       
       //Switching leaves to be rendered...
       this.getToolBaseSWG().setWhichChild(Switch.CHILD_ALL);
       setLocalVisibleNodes(new java.util.BitSet(getLocalSWG().numChildren()));
       setVToolVisibleNodes(new java.util.BitSet(getVToolSWG().numChildren()));
       setNotVToolVisibleNodes(new java.util.BitSet(getNotVToolSWG().numChildren()));
       setVisibility (VIS_HANDLES_ON);
       
    }

    public void createForm() {
        vToolFormRef = new SlicerVToolForm(this);
    }

    public void createVUIForm() {
        vuiToolFormRef = new SlicerVUIToolForm(this);
        vuiToolFormRef.setup();
    }

    public void createPlugin() {
        vToolPluginRef = new SlicerVToolPlugin(this);
    }

    public void createOperatorsForm() {
        vToolOperatorsFormRef = new SlicerVToolOperatorsForm(this);
    }

    //Sets witch leafs are render depending on visibility level passed
    public void setVisibility (int visParm) 
    {        
        this.visibility = visParm;
        getLocalVisibleNodes().clear();
        getVToolVisibleNodes().clear();
        getNotVToolVisibleNodes().clear();
        if (visParm == VIS_HIDDEN) 
        { //Nothing is visible
            this.getToolBaseSWG().setWhichChild(Switch.CHILD_NONE);
        } 
        else if (visParm == VIS_SIMPLE) 
        {                                   //Only slicers plate is visible
                                            //Operations: Translate XY
            this.getToolBaseSWG().setWhichChild(Switch.CHILD_ALL);
            getLocalVisibleNodes().set(0);
            getLocalSWG().setWhichChild(Switch.CHILD_MASK);
            getLocalSWG().setChildMask(getLocalVisibleNodes());
            getVToolVisibleNodes().set(0);
            getVToolSWG().setWhichChild(Switch.CHILD_MASK);
            getVToolSWG().setChildMask(getVToolVisibleNodes());
            getNotVToolSWG().setWhichChild(Switch.CHILD_NONE);
        } 
        else if (visParm == VIS_FRAMED) 
        {                                   // Slicers plate & frame (scale handles) are visible
                                            //Operations: slace & translateXY
            this.getToolBaseSWG().setWhichChild(Switch.CHILD_ALL);
            getLocalVisibleNodes().set(0);
            getLocalSWG().setWhichChild(Switch.CHILD_MASK);
            getLocalSWG().setChildMask(getLocalVisibleNodes());
            getVToolVisibleNodes().set(0,9);
            getVToolSWG().setWhichChild(Switch.CHILD_MASK);
            getVToolSWG().setChildMask(getVToolVisibleNodes());
            getNotVToolSWG().setWhichChild(Switch.CHILD_NONE);
        } 
        else if (visParm == VIS_HANDLES_ON) 
        {                                      //Slicers plate, frame, translateZ touch handle, and rot sphere switcher are visible
                                               //Operations: translate XY, translateZ, scale & turn rot sphere on
            this.getToolBaseSWG().setWhichChild(Switch.CHILD_ALL);
            getLocalSWG().setWhichChild(Switch.CHILD_ALL);
            getVToolVisibleNodes().set(0,9);
            getVToolVisibleNodes().set(10);
            getVToolSWG().setWhichChild(Switch.CHILD_MASK);
            getVToolSWG().setChildMask(getVToolVisibleNodes());
            getNotVToolVisibleNodes().set(0);
            getNotVToolSWG().setWhichChild(Switch.CHILD_MASK);
            getNotVToolSWG().setChildMask(getNotVToolVisibleNodes());
        } 
        else if (visParm == VIS_ROT_SPHERE_ON) 
        {                                  //Slicers plate, rot switch handle & rot sphere are visible
                                            //Operations: Rotating & rot sphere turn off
            getScaleTG().getTransform(posTr);
            posTr.getScale(posVector);
            //Rotation sphere should always enclose the VTool plate
            double newRadius = Math.sqrt(Math.pow(a*posVector.x, 2) + Math.pow(a*posVector.y, 2));
            getRotSphereHandle().setScale(newRadius);
            this.getToolBaseSWG().setWhichChild(Switch.CHILD_ALL);
            getLocalSWG().setWhichChild(Switch.CHILD_ALL);
            getVToolVisibleNodes().set(0);
            getVToolSWG().setWhichChild(Switch.CHILD_MASK);
            getVToolSWG().setChildMask(getVToolVisibleNodes());
            getNotVToolSWG().setWhichChild(Switch.CHILD_ALL);
        } 
        else if (visParm == VIS_SCALING) 
        {              //Plate, frame, XY guider(not actually visible)
            this.getToolBaseSWG().setWhichChild(Switch.CHILD_ALL);
            getLocalVisibleNodes().set(0);
            getLocalSWG().setWhichChild(Switch.CHILD_MASK);
            getLocalSWG().setChildMask(getLocalVisibleNodes());
            getVToolVisibleNodes().set(0, 10);
            getVToolSWG().setWhichChild(Switch.CHILD_MASK);
            getVToolSWG().setChildMask(getVToolVisibleNodes());
            getNotVToolSWG().setWhichChild(Switch.CHILD_NONE);
        } 
        else if (visParm == VIS_ROTATING) 
        {         //Plate & Rotating sphere
            this.getToolBaseSWG().setWhichChild(Switch.CHILD_ALL);
            getLocalSWG().setWhichChild(Switch.CHILD_ALL);
            getVToolVisibleNodes().set(0);
            getVToolSWG().setWhichChild(Switch.CHILD_MASK);
            getVToolSWG().setChildMask(getVToolVisibleNodes());
            getNotVToolVisibleNodes().set(1);
            getNotVToolSWG().setWhichChild(Switch.CHILD_MASK);
            getNotVToolSWG().setChildMask(getNotVToolVisibleNodes());
        } 
        else if (visParm == VIS_TRANSLATING_XY) 
        {  //VTool plate and xy guider
            this.getToolBaseSWG().setWhichChild(Switch.CHILD_ALL);
            getLocalVisibleNodes().set(0);
            getLocalSWG().setWhichChild(Switch.CHILD_MASK);
            getLocalSWG().setChildMask(getLocalVisibleNodes());
            getVToolVisibleNodes().set(0);
            getVToolVisibleNodes().set(9);
            getVToolSWG().setWhichChild(Switch.CHILD_MASK);
            getVToolSWG().setChildMask(getVToolVisibleNodes());
            getNotVToolSWG().setWhichChild(Switch.CHILD_NONE);
        }
        else if (visParm == VIS_TRANSLATING_Z) 
        { //Vtool plate, trans Z touch handle and transZ guider
            this.getToolBaseSWG().setWhichChild(Switch.CHILD_ALL);
            getLocalVisibleNodes().set(0);
            getLocalSWG().setWhichChild(Switch.CHILD_MASK);
            getLocalSWG().setChildMask(getLocalVisibleNodes());
            getVToolVisibleNodes().set(0);
            getVToolVisibleNodes().set(10);
            getVToolVisibleNodes().set(11);
            getVToolSWG().setWhichChild(Switch.CHILD_MASK);
            getVToolSWG().setChildMask(getVToolVisibleNodes());
            getNotVToolSWG().setWhichChild(Switch.CHILD_NONE);
        }
    }
//Geters and setter (mostly automatic genrated)
    public int getVisibility() {
        return visibility;
    }

    public void setFocus() {
    }

    public java.util.BitSet getLocalVisibleNodes() {
        return localVisibleNodes;
    }

    public java.util.BitSet getVToolVisibleNodes() {
        return vToolVisibleNodes;
    }

    public java.util.BitSet getNotVToolVisibleNodes() {
        return notVToolVisibleNodes;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger aLogger) {
        logger = aLogger;
    }

    public ScaleTouchHandle getUppRight() {
        return uppRight;
    }

    public void setUppRight(ScaleTouchHandle uppRight) {
        this.uppRight = uppRight;
    }

    public ScaleTouchHandle getUppMid() {
        return uppMid;
    }

    public void setUppMid(ScaleTouchHandle uppMid) {
        this.uppMid = uppMid;
    }

    public ScaleTouchHandle getUppLeft() {
        return uppLeft;
    }

    public void setUppLeft(ScaleTouchHandle uppLeft) {
        this.uppLeft = uppLeft;
    }

    public ScaleTouchHandle getLeftMid() {
        return leftMid;
    }

    public void setLeftMid(ScaleTouchHandle leftMid) {
        this.leftMid = leftMid;
    }

    public ScaleTouchHandle getDwnLeft() {
        return dwnLeft;
    }

    public void setDwnLeft(ScaleTouchHandle dwnLeft) {
        this.dwnLeft = dwnLeft;
    }

    public ScaleTouchHandle getDwnMid() {
        return dwnMid;
    }

    public void setDwnMid(ScaleTouchHandle dwnMid) {
        this.dwnMid = dwnMid;
    }

    public ScaleTouchHandle getDwnRight() {
        return dwnRight;
    }

    public void setDwnRight(ScaleTouchHandle dwnRight) {
        this.dwnRight = dwnRight;
    }

    public ScaleTouchHandle getRightMid() {
        return rightMid;
    }

    public void setRightMid(ScaleTouchHandle rightMid) {
        this.rightMid = rightMid;
    }

    public GuideHandle getXyGuider() {
        return xyGuider;
    }

    public void setXyGuider(GuideHandle xyGuider) {
        this.xyGuider = xyGuider;
    }

    public TranslateTouchHandle getTransZTouchHandle() {
        return transZTouchHandle;
    }

    public void setTransZTouchHandle(TranslateTouchHandle transZTouchHandle) {
        this.transZTouchHandle = transZTouchHandle;
    }

    public GuideHandle getTransZGuider() {
        return transZGuider;
    }

    public void setTransZGuider(GuideHandle transZGuider) {
        this.transZGuider = transZGuider;
    }

    public SwitchHandle getRotationSwitchHandle() {
        return rotationSwitchHandle;
    }

    public void setRotationSwitchHandle(SwitchHandle rotationSwitchHandle) {
        this.rotationSwitchHandle = rotationSwitchHandle;
    }

    public RotationSphereHandle getRotSphereHandle() {
        return rotSphereHandle;
    }

    public void setRotSphereHandle(RotationSphereHandle rotSphereHandle) {
        this.rotSphereHandle = rotSphereHandle;
    }

    public int getScaleHandleId() {
        return scaleHandleId;
    }

    public void setScaleHandleId(int scaleHandleId) {
        this.scaleHandleId = scaleHandleId;
    }

    public float getA() {
        return a;
    }

    public void setA(float a) {
        this.a = a;
    }

    public float getB() {
        return b;
    }

    public void setB(float b) {
        this.b = b;
    }

    public Vector3d getPosVector() {
        return posVector;
    }

    public void setPosVector(Vector3d posVector) {
        this.posVector = posVector;
    }

    public Transform3D getPosTr() {
        return posTr;
    }

    public void setPosTr(Transform3D posTr) {
        this.posTr = posTr;
    }

    public TransformGroup getRotTG() {
        return rotTG;
    }

    public void setRotTG(TransformGroup rotTG) {
        this.rotTG = rotTG;
    }

    public TransformGroup getTransTG() {
        return transTG;
    }

    public void setTransTG(TransformGroup transTG) {
        this.transTG = transTG;
    }

    public Switch getLocalSWG() {
        return localSWG;
    }

    public void setLocalSWG(Switch localSWG) {
        this.localSWG = localSWG;
    }

    public BranchGroup getVToolScaledBG() {
        return vToolScaledBG;
    }

    public void setVToolScaledBG(BranchGroup vToolScaledBG) {
        this.vToolScaledBG = vToolScaledBG;
    }

    public BranchGroup getNotVToolScaledBG() {
        return notVToolScaledBG;
    }

    public void setNotVToolScaledBG(BranchGroup notVToolScaledBG) {
        this.notVToolScaledBG = notVToolScaledBG;
    }

    public TransformGroup getScaleTG() {
        return scaleTG;
    }

    public void setScaleTG(TransformGroup scaleTG) {
        this.scaleTG = scaleTG;
    }

    public Switch getVToolSWG() {
        return vToolSWG;
    }

    public void setVToolSWG(Switch vToolSWG) {
        this.vToolSWG = vToolSWG;
    }

    public Switch getNotVToolSWG() {
        return notVToolSWG;
    }

    public void setNotVToolSWG(Switch notVToolSWG) {
        this.notVToolSWG = notVToolSWG;
    }

    public void setLocalVisibleNodes(java.util.BitSet localVisibleNodes) {
        this.localVisibleNodes = localVisibleNodes;
    }

    public void setVToolVisibleNodes(java.util.BitSet vToolVisibleNodes) {
        this.vToolVisibleNodes = vToolVisibleNodes;
    }

    public void setNotVToolVisibleNodes(java.util.BitSet notVToolVisibleNodes) {
        this.notVToolVisibleNodes = notVToolVisibleNodes;
    }

    public int getHandleId() {
        return scaleHandleId;
    }

    public void setHandleId(int handleId) {
        this.scaleHandleId = handleId;
        
    }

    public int getTranslateHandleMod() {
        return translateHandleMod;
    }

    public void setTranslateHandleMod(int translateHandleMod) {
        this.translateHandleMod = translateHandleMod;
    }

    public int getGuiderMod() {
        return guiderMod;
    }

    public void setGuiderMod(int guiderMod) {
        this.guiderMod = guiderMod;
    }

    public long generateUID() {
        return ++counter;
    }

    public TransformGroup getZTg() {
        return ZTg;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void setVisibilityLevel(int level) {
        setVisibility(level);
    }

    public void moveTool(int constMove) 
    {
        posVector.set(0d, 0d, 0d);
        switch (constMove) 
        {
            case NavigationConstants.UP: 
            {
                posVector.set(0d, moveStep, 0d);
                break;
            }
            case NavigationConstants.DOWN: {
                posVector.set(0d, -moveStep, 0d);
                break;
            }
            case NavigationConstants.LEFT: {
                posVector.set(-moveStep, 0d, 0d);
                break;
            }
            case NavigationConstants.RIGHT: {
                posVector.set(moveStep, 0d, 0d);
                break;
            }
            case NavigationConstants.BACKWARD: {
                posVector.set(0d, 0d, -moveStep);
                break;
            }
            case NavigationConstants.FORWARD: {
                posVector.set(0d, 0d, moveStep);
                break;
            }   
        }
        getTransTG().getTransform(posTr);
        posTr.get(oldPosVector);
        oldPosVector.add(posVector);
        posTr.setTranslation(posVector);
        getTransTG().setTransform(posTr);
        
    }

    public void setPositionSizeRotation(int PSR) {
    }

    public void setScale(int scale) {
    }

    public void setBrightnes(int level) {
    }

    public void setContrast(int level) {
    }
    public JPanel[] getToolOperatorPanelList() {
        return slicerOperatorsPanel;
    }

    public Vector3d getOldPosVector() {
        return oldPosVector;
    }

    public void setOldPosVector(Vector3d oldPosVector) {
        this.oldPosVector = oldPosVector;
    }
    
    public void setInitRot()
    {
        //postaviti orijentaciju uzimajuci u obzir trenutni viewpoint
    }
    
    public void setInitTrans()
    {
        //postaviti translaciju uzimajuci u obzir trenutni viewpoint   
    }
    
    public void setInitSize()
    {
        //uzimajuci u obzir trenutni VP
    }
}
