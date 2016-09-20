/*
 * DicerVirTool.java
 *
 * Created on april 23rd 2007
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.tools.dicer;

import java.io.Serializable;
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

import org.VIVE.gui.toolPanels.toolPanelDicer;
import org.apache.log4j.Logger;
import org.sgJoe.graphics.SceneGraphEditor;
import org.sgJoe.graphics.event.*;
import org.sgJoe.graphics.behaviors.BehaviorObserver;
import org.sgJoe.tools.VToolFactory;
import org.sgJoe.tools.interfaces.VToolForm;
import org.sgJoe.tools.interfaces.VirTool;

/**
 *
 * @author Vladimir
 */

public class DicerVirTool extends VirTool implements iDicerConstants,Serializable,
                                                     iVisibility, iNavigation, iBrightness
{
    private static Logger logger = Logger.getLogger(DicerVirTool.class);
    //Counts class instances
    private static long counter = 0;
    //GUI
    private toolPanelDicer dicerToolPanel;
    private JPanel[] dicerOperatorsPanel;
    //Move step
    private static double moveStep = 1d;
    //Visibility levels
    public static final int VIS_HIDDEN = 0,                 //Nothing is displayed
                            VIS_SIMPLE = 1,                 //Only Box is displayed
                            VIS_FRAMED = 2,                 //Box and all scale handles
                            VIS_HANDLES_ON = 3,             //Box and all handles
                            VIS_ROT_SPHERE_ON = 4,          //Box and rotation sphere
                                    
                            VIS_TRANSLATING0 = 5,
                            VIS_TRANSLATING1 = 6,
                            VIS_TRANSLATING2 = 7, 
                            VIS_TRANSLATING3 = 8,
                            VIS_TRANSLATING4 = 9,
                            VIS_TRANSLATING5 = 10,                            
            
                            VIS_SCALING0 = 11,
                            VIS_SCALING1 = 12,
                            VIS_SCALING2 = 13,
                            VIS_SCALING3 = 14,
                            VIS_SCALING4 = 15,
                            VIS_SCALING5 = 16,           
                            
                            VIS_ROTATING = 17,               // To implement this feature.
                            VIS_ALL = 18,                    // For testing purpose only       
                            
                            VIS_WIREFRAME=19,                //When hidden surface is checked
            
                            VIS_SIMPLE0 = 20,                //simple + slider0
                            VIS_SIMPLE1 = 21,                //simple + slider1
                            VIS_SIMPLE2 = 22,                //simple + slider2
                            VIS_SIMPLE3 = 23,                //simple + slider3
                            VIS_SIMPLE4 = 24,                //simple + slider4
                            VIS_SIMPLE5 = 25,                //simple + slider5
                            
                            VIS_MESH = 26;
    
    
    // Memorize visibility so it can be revived at some point
    private int oldVis; 
    
    // Current visibility level. Visibility level defines which handles and guiders are visible
    // See more detail on setVisibility()    
    private int visibility;
    private boolean locked = false;
    public int mode = MODE_NONE;                 //for defining the mode
    public int submode = MODE_NONE;              //for further defining the mode
    
    public boolean dragSimple = false;           //indicator whether drag is simple or not(currently not)
    
    //Touch handles 
    //Scale touch handles (together form frame)
    private DicerScaleHandle[] DicerScaleHandles = new DicerScaleHandle[21];
    private DicerScaleHandle[] DSHWireFrames = new DicerScaleHandle[21];
    
    private SliderVToolHandle[] SliderHandles = new SliderVToolHandle[6];  
    
    //Dicer's wireframe
    private DicerWireframe dWireframe;
    
    //Rotation sphere handle
    private RotationSphereHandle rotSphereHandle = null;
    
    //Switch rotation sphere handle On/Off
    private SwitchHandle rotationSwitchHandle = null;
    private SwitchHandle rotationSwitchHandle2 = null;
    
    //Guide handles are used as guiders during certain operations over specific surfaces   
   
    private float xDim;
    private float yDim;
    private float zDim;
    
    //TO determine which scale handle is pressed
    private int scaleHandleId = DicerScaleHandle.NONE_ID;
    
    //Used while positioning handles (initial position)
    private Vector3d posVector;
    private Vector3d oldPosVector;
    private Transform3D posTr;
    private Transform3D posTr2;
    
    //Class in charge for applying textures to dicer
    private DicerTexturer DTexturer;
        
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
    
    
    /**
     * Creates a new instance of DicerVirTool
     */
    public DicerVirTool() {
        super();        
        DTexturer = new DicerTexturer(this);            //initializing the texturer
        
        dicerToolPanel = new toolPanelDicer(this);        // Dicer specific tool panel
        
        dicerOperatorsPanel = new JPanel[3];
        VisibilityComponent[] visComps = new VisibilityComponent [5];
        visComps[0] = new VisibilityComponent("Hidden", VIS_HIDDEN);
        visComps[1] = new VisibilityComponent("Simple", VIS_SIMPLE);
        visComps[2] = new VisibilityComponent("Framed", VIS_FRAMED);
        visComps[3] = new VisibilityComponent("Handles on", VIS_HANDLES_ON);
        visComps[4] = new VisibilityComponent("Rotation sphere on",VIS_ROT_SPHERE_ON);    
           
        this.setVUIToolFormRef(dicerToolPanel);        
        
        Visibility tmpVis = new Visibility(visComps, this);
        tmpVis.setVisibilityLevel(DicerVirTool.VIS_ALL);
        dicerOperatorsPanel[0] = tmpVis;
        this.setInstanceName("Dicer#" + generateUID());
        this.setToolDescription("Dicer tool");
        dicerOperatorsPanel[1] = new Navigation(Navigation.KRETANJE|Navigation.AKCIJE|Navigation.FIT,this);
        dicerOperatorsPanel[2] = new BrightnessContrast(this);
    }
    
    //Constructing subgraph for a specific dicer
    public void createToolInstance(String string) 
    {
        this.setVToolRef(new DicerVTool(this));
        System.out.println("Create Tool Instance metoda.");
        
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
        setPosTr2(new Transform3D());
        
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
        
    }
    
    //Adding actualy visible objects to both left and right end of the dicer's scene graph's chain
    public void setup (SceneGraphEditor editor, BehaviorObserver behaviorObserver) 
    {
        System.out.println("Setup metoda DicerVirTool-a.");
        super.setup(editor, behaviorObserver);
        this.getVToolRef().setup();
        setXdim(((DicerVTool)this.getVToolRef()).getShp().getXdim());
        setYdim(((DicerVTool)this.getVToolRef()).getShp().getYdim());
        setZdim(((DicerVTool)this.getVToolRef()).getShp().getZdim());
  
        // Putting handles
        
        // Each of these DSH sections does the following:
        // Creates handles, modifies Transform3D component of these newly created handles,and then
        // attaches handles to VToolSWG     
        // First we define the appropriate x,y,z offsets from the coordinate center(as helping variables)
        
        float x=getXdim();
        float y=getYdim();
        float z=getZdim();
        DicerScaleHandle DSH;
        DicerScaleHandle DSH2;        // This one is for sides distinction(its polygon attributes is LINE)
        SliderVToolHandle SVTH;       // This one is for setting up the Slider handles              
        DicerWireframe DWF;           // This is for setting up the dicer's wireframe
        
        // Setting and attaching to the SceneGraph DicerScaleHandle 1
        setScaleHandle(1,new DicerScaleHandle(this,vToolFormRef.ACT_NONE,DicerScaleHandle.FRONT_UP_ID,"FrontUp" , INIT_EDGE_LENGTH , INIT_EDGE_WIDTH));
        getPosVector().set(0,y,z);
        getPosTr().setTranslation(getPosVector());
        getScaleHandle(1).setTransform(getPosTr());
        this.getVToolSWG().addChild(getScaleHandle(1));   
        
       
        //Setting and attaching DSH2
        DSH=new DicerScaleHandle(this,vToolFormRef.ACT_NONE,DicerScaleHandle.FRONT_DOWN_ID,"FrontDown" , INIT_EDGE_LENGTH , INIT_EDGE_WIDTH);
        getPosVector().set(0,-y,z);
        getPosTr().rotZ(Math.PI);
        getPosTr().setTranslation(getPosVector());        
        DSH.setTransform(getPosTr());
        setScaleHandle(2,DSH);
        this.getVToolSWG().addChild(getScaleHandle(2));        
        
        //DSH3...
        DSH=new DicerScaleHandle(this,vToolFormRef.ACT_NONE,DicerScaleHandle.FRONT_LEFT_ID,"FrontLeft" , INIT_EDGE_LENGTH , INIT_EDGE_WIDTH);
        getPosVector().set(-x,0,z);
        getPosTr().rotZ(Math.PI/2);
        getPosTr().setTranslation(getPosVector());        
        DSH.setTransform(getPosTr());
        setScaleHandle(3,DSH);
        this.getVToolSWG().addChild(getScaleHandle(3));        
        
        //DSH4
        DSH=new DicerScaleHandle(this,vToolFormRef.ACT_NONE,DicerScaleHandle.FRONT_RIGHT_ID,"FrontRight",INIT_EDGE_LENGTH,INIT_EDGE_WIDTH);
        getPosVector().set(x,0,z);
        getPosTr().rotZ(-Math.PI/2);
        getPosTr().setTranslation(getPosVector());        
        DSH.setTransform(getPosTr());
        setScaleHandle(4,DSH);
        this.getVToolSWG().addChild(getScaleHandle(4));       
        
        //DSH5
        DSH=new DicerScaleHandle(this,vToolFormRef.ACT_NONE,DicerScaleHandle.BACK_UP_ID , "BackUp" , INIT_EDGE_LENGTH , INIT_EDGE_WIDTH);
        getPosVector().set(0,y,-z);
        getPosTr().rotX(-Math.PI/2);        //It's on the back side
        getPosTr().setTranslation(getPosVector());        
        DSH.setTransform(getPosTr());
        setScaleHandle(5,DSH);
        this.getVToolSWG().addChild(getScaleHandle(5));  
        
        //DSH6
        DSH=new DicerScaleHandle(this,vToolFormRef.ACT_NONE,DicerScaleHandle.BACK_DOWN_ID,"BackDown",INIT_EDGE_LENGTH,INIT_EDGE_WIDTH);
        getPosVector().set(0,-y,-z);
        getPosTr().rotX(Math.PI);                             //it's orientation here on back side
        getPosTr().setTranslation(getPosVector());        
        DSH.setTransform(getPosTr());
        setScaleHandle(6,DSH);
        this.getVToolSWG().addChild(getScaleHandle(6));  
        
        //DSH7
        DSH=new DicerScaleHandle(this,vToolFormRef.ACT_NONE,DicerScaleHandle.BACK_LEFT_ID,"BackLeft",INIT_EDGE_LENGTH,INIT_EDGE_WIDTH);
        getPosVector().set(-x,0,-z);
        getPosTr().rotZ(Math.PI/2);                    //turn it to fit the back side
        getPosTr2().rotX(-Math.PI/2);                     //rotates it to fit the left edge on the back side
        getPosTr().mul(getPosTr2());                     //combining the two
        getPosTr().setTranslation(getPosVector());        
        DSH.setTransform(getPosTr());
        setScaleHandle(7,DSH);
        this.getVToolSWG().addChild(getScaleHandle(7));   
        
        //DSH8
        DSH=new DicerScaleHandle(this,vToolFormRef.ACT_NONE,DicerScaleHandle.BACK_RIGHT_ID,"BackRight",INIT_EDGE_LENGTH,INIT_EDGE_WIDTH);
        getPosVector().set(x,0,-z);
        getPosTr().rotZ(Math.PI/2);
        getPosTr2().rotX(-Math.PI);
        getPosTr().mul(getPosTr2());
        getPosTr().setTranslation(getPosVector());        
        DSH.setTransform(getPosTr());
        setScaleHandle(8,DSH);
        this.getVToolSWG().addChild(getScaleHandle(8));                
        
        //DSH9
        DSH=new DicerScaleHandle(this,vToolFormRef.ACT_NONE,DicerScaleHandle.SIDE_UPPER_LEFT_ID,"SideUpperLeft",INIT_EDGE_LENGTH,INIT_EDGE_WIDTH);
        getPosVector().set(-x,y,0);
        getPosTr().rotY(-Math.PI/2);
        getPosTr().setTranslation(getPosVector());        
        DSH.setTransform(getPosTr());
        setScaleHandle(9,DSH);
        this.getVToolSWG().addChild(getScaleHandle(9));     
        
        //DSH10
        DSH=new DicerScaleHandle(this,vToolFormRef.ACT_NONE,DicerScaleHandle.SIDE_LOWER_LEFT_ID,"SideLowerLeft",INIT_EDGE_LENGTH,INIT_EDGE_WIDTH);
        getPosVector().set(-x,-y,0);
        getPosTr().rotY(-Math.PI/2);
        getPosTr2().rotX(Math.PI/2);
        getPosTr().mul(getPosTr2());
        getPosTr().setTranslation(getPosVector());        
        DSH.setTransform(getPosTr());
        setScaleHandle(10,DSH);
        this.getVToolSWG().addChild(getScaleHandle(10));        
        
        //DSH11
        DSH=new DicerScaleHandle(this,vToolFormRef.ACT_NONE,DicerScaleHandle.SIDE_LOWER_RIGHT_ID,"SideLowerRight",INIT_EDGE_LENGTH,INIT_EDGE_WIDTH);
        getPosVector().set(x,-y,0);
        getPosTr().rotY(-Math.PI/2);
        getPosTr2().rotX(Math.PI);
        getPosTr().mul(getPosTr2());
        getPosTr().setTranslation(getPosVector());       
        DSH.setTransform(getPosTr());
        setScaleHandle(11,DSH);
        this.getVToolSWG().addChild(getScaleHandle(11));      
        
        //DSH12
        DSH=new DicerScaleHandle(this,vToolFormRef.ACT_NONE,DicerScaleHandle.SIDE_UPPER_RIGHT_ID,"SideUpperRight",INIT_EDGE_LENGTH,INIT_EDGE_WIDTH);
        getPosVector().set(x,y,0);
        getPosTr().rotY(Math.PI/2);
        getPosTr().setTranslation(getPosVector());
        DSH.setTransform(getPosTr());
        setScaleHandle(12,DSH);
        this.getVToolSWG().addChild(getScaleHandle(12));
           
        
        //Edge handles done,continuing to corner handles
        
        //DSH13
        DSH=new DicerScaleHandle(this,vToolFormRef.ACT_NONE,DicerScaleHandle.FRONT_UPPER_LEFT_ID,"FrontUpperLeft",INIT_CORNER_DIM);
        getPosVector().set(-x,y,z);
        getPosTr().rotX(-Math.PI);
        getPosTr().setTranslation(getPosVector());        
        DSH.setTransform(getPosTr());
        setScaleHandle(13,DSH);
        this.getVToolSWG().addChild(getScaleHandle(13));        
        
        //DSH14
        DSH=new DicerScaleHandle(this,vToolFormRef.ACT_NONE,DicerScaleHandle.FRONT_LOWER_LEFT_ID,"FrontLowerLeft",INIT_CORNER_DIM);
        getPosVector().set(-x,-y,z);
        getPosTr().rotY(Math.PI/2);
        getPosTr().setTranslation(getPosVector());        
        DSH.setTransform(getPosTr());
        setScaleHandle(14,DSH);
        this.getVToolSWG().addChild(getScaleHandle(14));           
        
        //DSH15
        DSH=new DicerScaleHandle(this,vToolFormRef.ACT_NONE,DicerScaleHandle.FRONT_LOWER_RIGHT_ID,"FrontLowerRight",INIT_CORNER_DIM);
        getPosVector().set(x,-y,z);
        getPosTr().rotY(Math.PI);
        getPosTr().setTranslation(getPosVector());        
        DSH.setTransform(getPosTr());
        setScaleHandle(15,DSH);
        this.getVToolSWG().addChild(getScaleHandle(15));        
        
        //DSH16
        DSH=new DicerScaleHandle(this,vToolFormRef.ACT_NONE,DicerScaleHandle.FRONT_UPPER_RIGHT_ID,"FrontUpperRight",INIT_CORNER_DIM);
        getPosVector().set(x,y,z);
        getPosTr().rotY(Math.PI);
        getPosTr2().rotX(Math.PI/2);
        getPosTr().mul(getPosTr2());
        getPosTr().setTranslation(getPosVector());       
        DSH.setTransform(getPosTr());
        setScaleHandle(16,DSH);
        this.getVToolSWG().addChild(getScaleHandle(16));        
        
        //DSH17
        DSH=new DicerScaleHandle(this,vToolFormRef.ACT_NONE,DicerScaleHandle.BACK_UPPER_LEFT_ID,"BackUpperLeft",INIT_CORNER_DIM);
        getPosVector().set(-x,y,-z);
        getPosTr().rotX(Math.PI/2);
        getPosTr().setTranslation(getPosVector());        
        DSH.setTransform(getPosTr());
        setScaleHandle(17,DSH);
        this.getVToolSWG().addChild(getScaleHandle(17));           
        
        //DSH18
        DSH=new DicerScaleHandle(this,vToolFormRef.ACT_NONE,DicerScaleHandle.BACK_LOWER_LEFT_ID,"BackLowerLeft",INIT_CORNER_DIM);
        getPosVector().set(-x,-y,-z);
        setPosTr(new Transform3D());
        getPosTr().setTranslation(getPosVector());
        DSH.setTransform(getPosTr());
        setScaleHandle(18,DSH);
        this.getVToolSWG().addChild(getScaleHandle(18));
        
        //DSH19
        DSH=new DicerScaleHandle(this,vToolFormRef.ACT_NONE,DicerScaleHandle.BACK_LOWER_RIGHT_ID,"BackLowerRight",INIT_CORNER_DIM);
        getPosVector().set(x,-y,-z);
        getPosTr().rotY(-Math.PI/2);
        getPosTr().setTranslation(getPosVector());        
        DSH.setTransform(getPosTr());
        setScaleHandle(19,DSH);
        this.getVToolSWG().addChild(getScaleHandle(19));      

        //DSH20 
        DSH=new DicerScaleHandle(this,vToolFormRef.ACT_NONE,DicerScaleHandle.BACK_UPPER_RIGHT_ID,"BackUpperRight",INIT_CORNER_DIM);
        getPosVector().set(x,y,-z);
        getPosTr().rotZ(Math.PI);
        getPosTr().setTranslation(getPosVector());        
        DSH.setTransform(getPosTr());
        setScaleHandle(20,DSH);
        this.getVToolSWG().addChild(getScaleHandle(20));     //VToolSWG's child number 20
        
        
        //Plane guiders initialization(GuideHandles):
        //Indices are set according to Box sides indices(TOP,FRONT......)
        
        //SVTH0    (front slider)
        SVTH = new SliderVToolHandle(this,vToolFormRef.ACT_NONE);
        getPosVector().set(0,0,z+INIT_CORNER_DIM/2);
        getPosTr().setIdentity();
        getPosTr().setTranslation(getPosVector());
        SVTH.setTransform(getPosTr());
        setSliderHandle(0,SVTH);
        this.getVToolSWG().addChild(getSliderHandle(0));        //VToolSWG's child number 21
        
        //SVTH1    (back slider)
        SVTH = new SliderVToolHandle(this,vToolFormRef.ACT_NONE);
        getPosVector().set(0,0,-z-INIT_CORNER_DIM/2);
        getPosTr().setIdentity();
        getPosTr().setTranslation(getPosVector());
        SVTH.setTransform(getPosTr());
        setSliderHandle(1,SVTH);
        this.getVToolSWG().addChild(getSliderHandle(1));          //VToolSWG's child number 22
        
        //SVTH2    (right slider)
        SVTH = new SliderVToolHandle(this,vToolFormRef.ACT_NONE);
        getPosVector().set(x+INIT_CORNER_DIM/2,0,0);
        getPosTr().setIdentity();
        getPosTr().rotY(Math.PI/2);
        getPosTr().setTranslation(getPosVector());
        SVTH.setTransform(getPosTr());
        setSliderHandle(2,SVTH);
        this.getVToolSWG().addChild(getSliderHandle(2));         //VToolSWG's child number 23  
        
        //SVTH3    (left slider)
        SVTH = new SliderVToolHandle(this,vToolFormRef.ACT_NONE);
        getPosVector().set(-x-INIT_CORNER_DIM/2,0,0);
        getPosTr().setIdentity();
        getPosTr().rotY(-Math.PI/2);
        getPosTr().setTranslation(getPosVector());
        SVTH.setTransform(getPosTr());
        setSliderHandle(3,SVTH);
        this.getVToolSWG().addChild(getSliderHandle(3));       //VToolSWG's child number 24
        
        //SVTH4    (top slider)
        SVTH = new SliderVToolHandle(this,vToolFormRef.ACT_NONE);
        getPosVector().set(0,y+INIT_CORNER_DIM/2,0);
        getPosTr().setIdentity();
        getPosTr().rotX(-Math.PI/2);
        getPosTr().setTranslation(getPosVector());
        SVTH.setTransform(getPosTr());
        setSliderHandle(4,SVTH);
        this.getVToolSWG().addChild(getSliderHandle(4));       //VToolSWG's child number 25
        
        //SVTH5    (bottom slider)
        SVTH = new SliderVToolHandle(this,vToolFormRef.ACT_NONE);
        getPosVector().set(0,-y-INIT_CORNER_DIM/2,0);
        getPosTr().setIdentity();
        getPosTr().rotX(Math.PI/2);
        getPosTr().setTranslation(getPosVector());
        SVTH.setTransform(getPosTr());                          
        setSliderHandle(5,SVTH);
        this.getVToolSWG().addChild(getSliderHandle(5));        //child number 26
        
        //Wireframe
        DWF = new DicerWireframe(INIT_DICER_LENGTH , INIT_DICER_HEIGHT , INIT_DICER_WIDTH);
        setWireframe(DWF);
        this.getVToolSWG().addChild(getWireframe());            //attach wireframe to graph as child 27       
        
        /* Attaching rotation elements to the scene graph...  */
        
        //Rotation switch (0)
        setRotationSwitchHandle(new SwitchHandle(this, VToolForm.ACT_NONE, SwitchHandle.MOD_SWITCH_ROTSPHERE_ON, 0.1f, 0.7f));
        getPosVector().set(getDims().x + this.getRotationSwitchHandle().getSize(), getDims().y + this.getRotationSwitchHandle().getSize(), getDims().z + this.getRotationSwitchHandle().getSize() );
        getPosTr().setTranslation(getPosVector());
        getRotationSwitchHandle().setTransform(getPosTr());
        getNotVToolSWG().addChild(getRotationSwitchHandle());
       
        //Rotation switch2 (1)
        setRotationSwitchHandle2(new SwitchHandle(this, VToolForm.ACT_NONE, SwitchHandle.MOD_SWITCH_ROTSPHERE_ON, 0.1f, 0.7f));
        getPosVector().negate(getPosVector());
        //getPosVector().set(getDims().x + this.getRotationSwitchHandle().getSize(), getDims().y + this.getRotationSwitchHandle().getSize(), getDims().z + this.getRotationSwitchHandle().getSize() );
        getPosTr().setTranslation(getPosVector());
        getRotationSwitchHandle2().setTransform(getPosTr());
        getNotVToolSWG().addChild(getRotationSwitchHandle2());
                
        //Rotation Sphere (2)
        setRotSphereHandle(new RotationSphereHandle(this, VToolForm.ACT_NONE, (float) Math.sqrt(Math.pow(getDims().x, 2)  + Math.pow(getDims().y, 2) +Math.pow(getDims().z,2) )   , 0.7f));
        getNotVToolSWG().addChild(getRotSphereHandle());
            
        //Dummy Mesh attachment as child 28
        this.getVToolSWG().addChild(getDummyMesh());
        
        
//       Switching leaves to be rendered...
       this.getToolBaseSWG().setWhichChild(Switch.CHILD_ALL);
       setLocalVisibleNodes(new java.util.BitSet(getLocalSWG().numChildren()));
       setVToolVisibleNodes(new java.util.BitSet(getVToolSWG().numChildren()));
       setNotVToolVisibleNodes(new java.util.BitSet(getNotVToolSWG().numChildren()));
       setVisibility (VIS_FRAMED);
       
    }

    public void createForm() {
        vToolFormRef = new DicerVToolForm(this);
    }

    public void createVUIForm() {
        vuiToolFormRef = new DicerVUIToolForm(this);
        vuiToolFormRef.setup();
    }

    public void createPlugin() {
        vToolPluginRef = new DicerVToolPlugin(this);
    }

    public void createOperatorsForm() {
        vToolOperatorsFormRef = new DicerVToolOperatorsForm(this);
    }

    //Sets which leaves to render depending on visibility level passed
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
        
        if (visParm == VIS_MESH) 
        { //Only mesh is visible
            this.getToolBaseSWG().setWhichChild(Switch.CHILD_ALL);
            getLocalVisibleNodes().set(0);
            getLocalSWG().setWhichChild(Switch.CHILD_MASK);
            getLocalSWG().setChildMask(getLocalVisibleNodes());
            getVToolVisibleNodes().set(28);                             //turning on child 28                           
                                           
            getVToolSWG().setWhichChild(Switch.CHILD_MASK);
            getVToolSWG().setChildMask(getVToolVisibleNodes());
           
            getNotVToolSWG().setWhichChild(Switch.CHILD_NONE);
        } 
        
        else if (visParm == VIS_SIMPLE) 
        {             //Only dicer's box
                      
            this.getToolBaseSWG().setWhichChild(Switch.CHILD_ALL);
            getLocalVisibleNodes().set(0);
            getLocalSWG().setWhichChild(Switch.CHILD_MASK);
            getLocalSWG().setChildMask(getLocalVisibleNodes());
            getVToolVisibleNodes().set(0);
            getVToolSWG().setWhichChild(Switch.CHILD_MASK);
            getVToolSWG().setChildMask(getVToolVisibleNodes());
            
            getNotVToolSWG().setWhichChild(Switch.CHILD_NONE);
        } 
        
        else if(visParm == VIS_WIREFRAME)              //just dicer's wireframe
        {
            this.getToolBaseSWG().setWhichChild(Switch.CHILD_ALL);
            getLocalVisibleNodes().set(0);
            getLocalSWG().setWhichChild(Switch.CHILD_MASK);
            getLocalSWG().setChildMask(getLocalVisibleNodes());
            getVToolVisibleNodes().set(27);                                   //turning on child 27                         
                                           
            getVToolSWG().setWhichChild(Switch.CHILD_MASK);
            getVToolSWG().setChildMask(getVToolVisibleNodes());
           
            getNotVToolSWG().setWhichChild(Switch.CHILD_NONE);
            
        }
        
        else if(visParm == VIS_SIMPLE0)
        {
            this.getToolBaseSWG().setWhichChild(Switch.CHILD_ALL);
            getLocalVisibleNodes().set(0);
            getLocalSWG().setWhichChild(Switch.CHILD_MASK);
            getLocalSWG().setChildMask(getLocalVisibleNodes());
            getVToolVisibleNodes().set(0);                                   //turning on box and child 21                         
            getVToolVisibleNodes().set(21);                               
            getVToolSWG().setWhichChild(Switch.CHILD_MASK);
            getVToolSWG().setChildMask(getVToolVisibleNodes());
           
            getNotVToolSWG().setWhichChild(Switch.CHILD_NONE);
        }
        
        else if(visParm == VIS_SIMPLE1)
        {
            this.getToolBaseSWG().setWhichChild(Switch.CHILD_ALL);
            getLocalVisibleNodes().set(0);
            getLocalSWG().setWhichChild(Switch.CHILD_MASK);
            getLocalSWG().setChildMask(getLocalVisibleNodes());
            getVToolVisibleNodes().set(0);                                     //turning on box and child 22
            getVToolVisibleNodes().set(22);
            getVToolSWG().setWhichChild(Switch.CHILD_MASK);
            getVToolSWG().setChildMask(getVToolVisibleNodes());
           
            getNotVToolSWG().setWhichChild(Switch.CHILD_NONE);
        }
        
        else if(visParm == VIS_SIMPLE2)
        {
            this.getToolBaseSWG().setWhichChild(Switch.CHILD_ALL);
            getLocalVisibleNodes().set(0);
            getLocalSWG().setWhichChild(Switch.CHILD_MASK);
            getLocalSWG().setChildMask(getLocalVisibleNodes());
            getVToolVisibleNodes().set(0);                                //turning on box and child 23                        
            getVToolVisibleNodes().set(23);
            getVToolSWG().setWhichChild(Switch.CHILD_MASK);
            getVToolSWG().setChildMask(getVToolVisibleNodes());
           
            getNotVToolSWG().setWhichChild(Switch.CHILD_NONE);
        }
        
        else if(visParm == VIS_SIMPLE3)
        {
            this.getToolBaseSWG().setWhichChild(Switch.CHILD_ALL);
            getLocalVisibleNodes().set(0);
            getLocalSWG().setWhichChild(Switch.CHILD_MASK);
            getLocalSWG().setChildMask(getLocalVisibleNodes());
            getVToolVisibleNodes().set(0);                               //turning on box and child 24                        
            getVToolVisibleNodes().set(24);
            getVToolSWG().setWhichChild(Switch.CHILD_MASK);
            getVToolSWG().setChildMask(getVToolVisibleNodes());
           
            getNotVToolSWG().setWhichChild(Switch.CHILD_NONE);
        }
        
        else if(visParm == VIS_SIMPLE4)
        {
            this.getToolBaseSWG().setWhichChild(Switch.CHILD_ALL);
            getLocalVisibleNodes().set(0);
            getLocalSWG().setWhichChild(Switch.CHILD_MASK);
            getLocalSWG().setChildMask(getLocalVisibleNodes());
            getVToolVisibleNodes().set(0);                              //turning on box and child 25                        
            getVToolVisibleNodes().set(25);
            getVToolSWG().setWhichChild(Switch.CHILD_MASK);
            getVToolSWG().setChildMask(getVToolVisibleNodes());
           
            getNotVToolSWG().setWhichChild(Switch.CHILD_NONE);
        }
        
        else if(visParm == VIS_SIMPLE5)
        {
            this.getToolBaseSWG().setWhichChild(Switch.CHILD_ALL);
            getLocalVisibleNodes().set(0);
            getLocalSWG().setWhichChild(Switch.CHILD_MASK);
            getLocalSWG().setChildMask(getLocalVisibleNodes());
            getVToolVisibleNodes().set(0);                             //turning on box and child 26                        
            getVToolVisibleNodes().set(26);
            getVToolSWG().setWhichChild(Switch.CHILD_MASK);
            getVToolSWG().setChildMask(getVToolVisibleNodes());
           
            getNotVToolSWG().setWhichChild(Switch.CHILD_NONE);
        }
        
        else if (visParm == VIS_FRAMED) 
        {                          // Dicer's box and whole frame (scale handles and wireframe) are visible
                                   // Operations: this is a visibility after instantiation
            this.getToolBaseSWG().setWhichChild(Switch.CHILD_ALL);
            getLocalVisibleNodes().set(0);
            getLocalSWG().setWhichChild(Switch.CHILD_MASK);
            getLocalSWG().setChildMask(getLocalVisibleNodes());
            getVToolVisibleNodes().set(0,21);
            getVToolSWG().setWhichChild(Switch.CHILD_MASK);
            getVToolSWG().setChildMask(getVToolVisibleNodes());
            
            getNotVToolSWG().setWhichChild(Switch.CHILD_NONE);
        } 
        else if (visParm == VIS_HANDLES_ON) 
        {                            // Dicers box, frame, and rot sphere switchers are visible
                                     // Operations:Dicer is ready for manipulation
            //reposition and resize rotation switch handles
            getPosVector().set(getDims().x + this.getRotationSwitchHandle().getSize(), getDims().y + this.getRotationSwitchHandle().getSize(), getDims().z + this.getRotationSwitchHandle().getSize() );
            getPosTr().setTranslation(getPosVector());
            getPosTr().setScale(getSurrSphereRad());
            getRotationSwitchHandle().setTransform(getPosTr()); 
            
            getPosVector().negate(getPosVector() );  //The other one is on the opposite side
            getPosTr().setTranslation(getPosVector());
            getPosTr().setScale(getSurrSphereRad());            
            getRotationSwitchHandle2().setTransform(getPosTr());
            
            
            this.getToolBaseSWG().setWhichChild(Switch.CHILD_ALL);
            getLocalSWG().setWhichChild(Switch.CHILD_ALL);
            getVToolVisibleNodes().set(0,21);
            getVToolSWG().setWhichChild(Switch.CHILD_MASK);
            getVToolSWG().setChildMask(getVToolVisibleNodes());
            
            getNotVToolVisibleNodes().set(0,2);
            getNotVToolSWG().setWhichChild(Switch.CHILD_MASK);
            getNotVToolSWG().setChildMask(getNotVToolVisibleNodes());
        } 
        else if (visParm == VIS_ROT_SPHERE_ON) 
        {                          //Dicer's box, rot switch handles & rot sphere are visible
                                   //Operations: Rotating & rot sphere turn off            
            //set rotation sphere radius
            double newrad = Math.sqrt(Math.pow(getDims().x, 2)  + Math.pow(getDims().y, 2) +Math.pow(getDims().z,2) );
            getRotSphereHandle().setScale(newrad);   
            
            //reposition and resize rotation switch handles
            getPosVector().set(getDims().x + this.getRotationSwitchHandle().getSize(), getDims().y + this.getRotationSwitchHandle().getSize(), getDims().z + this.getRotationSwitchHandle().getSize() );
            getPosTr().setTranslation(getPosVector());
            getPosTr().setScale(getSurrSphereRad());
            getRotationSwitchHandle().setTransform(getPosTr()); 
            
            getPosVector().negate(getPosVector() );
            getPosTr().setTranslation(getPosVector());
            getPosTr().setScale(getSurrSphereRad());            
            getRotationSwitchHandle2().setTransform(getPosTr());
            
            //proceed with setting the visible nodes
            this.getToolBaseSWG().setWhichChild(Switch.CHILD_ALL);
            getLocalSWG().setWhichChild(Switch.CHILD_ALL);
            
            getVToolVisibleNodes().set(0);
            getVToolSWG().setWhichChild(Switch.CHILD_MASK);
            getVToolSWG().setChildMask(getVToolVisibleNodes());
            
            getNotVToolSWG().setWhichChild(Switch.CHILD_ALL);
        } 
        
        else if (visParm == VIS_ROTATING) 
        {    //Box & Rotating sphere
             //Operations: rotation of dicer
            this.getToolBaseSWG().setWhichChild(Switch.CHILD_ALL);
            getLocalSWG().setWhichChild(Switch.CHILD_ALL);
            
            getVToolVisibleNodes().set(0);
            getVToolSWG().setWhichChild(Switch.CHILD_MASK);
            getVToolSWG().setChildMask(getVToolVisibleNodes());
            
            getNotVToolVisibleNodes().set(2);
            getNotVToolSWG().setWhichChild(Switch.CHILD_MASK);
            getNotVToolSWG().setChildMask(getNotVToolVisibleNodes());
        } 
        
        else if(visParm == VIS_TRANSLATING0 || visParm == VIS_SCALING0)
        {        //Framed dicer and a Slider handle 0
                 //Operations: used during scaling or translation
            this.getToolBaseSWG().setWhichChild(Switch.CHILD_ALL);
            getLocalVisibleNodes().set(0);
            getLocalSWG().setWhichChild(Switch.CHILD_MASK);
            getLocalSWG().setChildMask(getLocalVisibleNodes());
            getVToolVisibleNodes().set(0,21);                       //turning on box and frame                       
            getVToolVisibleNodes().set(21);                         //turning on slider0
            getVToolSWG().setWhichChild(Switch.CHILD_MASK);
            getVToolSWG().setChildMask(getVToolVisibleNodes());
           
            getNotVToolSWG().setWhichChild(Switch.CHILD_NONE);
        }
        else if(visParm == VIS_TRANSLATING1 || visParm == VIS_SCALING1)         
        {       //Framed dicer and a Slider handle 1
                //Operations: used during scaling or translation
            this.getToolBaseSWG().setWhichChild(Switch.CHILD_ALL);
            getLocalVisibleNodes().set(0);
            getLocalSWG().setWhichChild(Switch.CHILD_MASK);
            getLocalSWG().setChildMask(getLocalVisibleNodes());
            getVToolVisibleNodes().set(0,21);                        //turning on box and frame 
            getVToolVisibleNodes().set(22);                          //turn on slider1
            getVToolSWG().setWhichChild(Switch.CHILD_MASK);
            getVToolSWG().setChildMask(getVToolVisibleNodes());
            
            getNotVToolSWG().setWhichChild(Switch.CHILD_NONE);
            
        }
        else if(visParm == VIS_TRANSLATING2 || visParm == VIS_SCALING2)         
        {       //Framed dicer and a Slider handle 2
                //Operations: used during scaling or translation
            this.getToolBaseSWG().setWhichChild(Switch.CHILD_ALL);
            getLocalVisibleNodes().set(0);
            getLocalSWG().setWhichChild(Switch.CHILD_MASK);
            getLocalSWG().setChildMask(getLocalVisibleNodes());
            getVToolVisibleNodes().set(0,21);                         //turning on box and frame           
            getVToolVisibleNodes().set(23);                           //turn on slider2
            getVToolSWG().setWhichChild(Switch.CHILD_MASK);
            getVToolSWG().setChildMask(getVToolVisibleNodes());
            
            getNotVToolSWG().setWhichChild(Switch.CHILD_NONE);
            
        }
        else if(visParm == VIS_TRANSLATING3 || visParm == VIS_SCALING3)         
        {       //Framed dicer and a Slider handle 3
                //Operations: used during scaling or translation
            this.getToolBaseSWG().setWhichChild(Switch.CHILD_ALL);
            getLocalVisibleNodes().set(0);
            getLocalSWG().setWhichChild(Switch.CHILD_MASK);
            getLocalSWG().setChildMask(getLocalVisibleNodes());
            getVToolVisibleNodes().set(0,21);                       //turning on box and frame 
            getVToolVisibleNodes().set(24);                         //turn on slider3
            getVToolSWG().setWhichChild(Switch.CHILD_MASK);
            getVToolSWG().setChildMask(getVToolVisibleNodes());
            
            getNotVToolSWG().setWhichChild(Switch.CHILD_NONE);
            
        }
        else if(visParm == VIS_TRANSLATING4 || visParm == VIS_SCALING4)         
        {       //Framed dicer and a Slider handle 4
                //Operations: used during scaling or translation
            this.getToolBaseSWG().setWhichChild(Switch.CHILD_ALL);
            getLocalVisibleNodes().set(0);
            getLocalSWG().setWhichChild(Switch.CHILD_MASK);
            getLocalSWG().setChildMask(getLocalVisibleNodes());
            getVToolVisibleNodes().set(0,21);                        //turning on box and frame 
            getVToolVisibleNodes().set(25);                          //turn on slider4
            getVToolSWG().setWhichChild(Switch.CHILD_MASK);
            getVToolSWG().setChildMask(getVToolVisibleNodes());
            
            getNotVToolSWG().setWhichChild(Switch.CHILD_NONE);
            
        }
        else if(visParm == VIS_TRANSLATING5 || visParm == VIS_SCALING5)         
        {       //Framed dicer and a Slider handle 5
                //Operations: used during scaling or translation
            this.getToolBaseSWG().setWhichChild(Switch.CHILD_ALL);
            getLocalVisibleNodes().set(0);
            getLocalSWG().setWhichChild(Switch.CHILD_MASK);
            getLocalSWG().setChildMask(getLocalVisibleNodes());
            getVToolVisibleNodes().set(0,21);             //turning on box and frame 
            getVToolVisibleNodes().set(26);               //turn on slider5
            getVToolSWG().setWhichChild(Switch.CHILD_MASK);
            getVToolSWG().setChildMask(getVToolVisibleNodes());
            
            getNotVToolSWG().setWhichChild(Switch.CHILD_NONE);
            
        }        
        
        else if (visParm == VIS_ALL) 
        { //for testing purpose only
            this.getToolBaseSWG().setWhichChild(Switch.CHILD_ALL);
            getLocalSWG().setWhichChild(Switch.CHILD_ALL);
            getVToolVisibleNodes().set(5,7);
            getVToolSWG().setWhichChild(Switch.CHILD_MASK);
            getVToolSWG().setChildMask(getVToolVisibleNodes());
            
            getNotVToolSWG().setWhichChild(Switch.CHILD_ALL);
            
        }
        
    }
       //Getters and setters:
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

    //Logger setters and getters
    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger aLogger) {
        logger = aLogger;
    }

    //Scale handles setters and getters
    public DicerScaleHandle getScaleHandle(int i)
    {
        return DicerScaleHandles[i];
    }
    
    public void setScaleHandle(int i,DicerScaleHandle DSH)
    {
        DicerScaleHandles[i]=DSH;
    }
    
    //Slider handles setters and getters
    public SliderVToolHandle getSliderHandle(int i)
    {
       return SliderHandles[i];
    }
    
    public void setSliderHandle(int i,SliderVToolHandle SVTH)
    {        
        SliderHandles[i]=SVTH;
    }
    
    public DicerWireframe getWireframe()
    {
        return dWireframe;
    }
    
    public void setWireframe(DicerWireframe dwf)
    {
        dWireframe = dwf;
    }
    
    
    public SwitchHandle getRotationSwitchHandle() {
        return rotationSwitchHandle;
    }

    public void setRotationSwitchHandle(SwitchHandle rotationSwitchHandle) {
        this.rotationSwitchHandle = rotationSwitchHandle;
    }
    
    public SwitchHandle getRotationSwitchHandle2() {
        return rotationSwitchHandle2;
    }

    public void setRotationSwitchHandle2(SwitchHandle rotationSwitchHandle) {
        this.rotationSwitchHandle2 = rotationSwitchHandle;
    }

    public RotationSphereHandle getRotSphereHandle() {
        return rotSphereHandle;
    }

    public void setRotSphereHandle(RotationSphereHandle rotSphereHandle) {
        this.rotSphereHandle = rotSphereHandle;
    }
    
    public float getXdim() 
    {
        return xDim;
    }

    public void setXdim(float a) 
    {
        this.xDim = a;
    }

    public float getYdim() 
    {
        return yDim;
    }

    public void setYdim(float b) 
    {
        this.yDim = b;
    }
    
    public float getZdim()
    {
        return zDim;
    }
    
    public void setZdim(float c)
    {
        zDim=c;
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

    public void setPosTr(Transform3D Tr) {
        this.posTr = Tr;
    }
    
    public Transform3D getPosTr2()
    {
        return posTr2;
    }
    
    public void setPosTr2(Transform3D Tr)
    {
        this.posTr2=Tr;
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

    public int getHandleId() 
    {
        return scaleHandleId;
    }

    public void setHandleId(int handleId) 
    {
        this.scaleHandleId = handleId;
    }


    public long generateUID() {
        return ++counter;
    }


    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void setVisibilityLevel(int level) 
    {
       setVisibility(level);
    }
    

    public void moveTool(int constMove) 
    {
        publishStartTransform();
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
        posTr.setTranslation(oldPosVector);
        getTransTG().setTransform(posTr);
        
        publishTransforming();
        publishEndTransform();
    }

    public void setPositionSizeRotation(int PSR) 
    {
        
    }
    public void setInitSize()
    {
        Transform3D hlp = new Transform3D();
        getScaleTG().getTransform(hlp);
        hlp.setScale(new Vector3d(1,1,1));
        getScaleTG().setTransform(hlp);
        setVisibility(getCurrVis());
        
    }
    public void setInitRot()
    {
        //postaviti orijentaciju uzimajuci u obzir trenutni viewpoint
    }
    public void setInitTrans()
    {
        //postaviti translaciju uzimajuci u obzir trenutni viewpoint   
    }

    public void setScale(int scale) {
    }

    public void setBrightnes(int level) {
    }

    public void setContrast(int level) {
    }
    public JPanel[] getToolOperatorPanelList() {
        return dicerOperatorsPanel;
    }

    public Vector3d getOldPosVector() {
        return oldPosVector;
    }

    public void setOldPosVector(Vector3d oldPosVector) {
        this.oldPosVector = oldPosVector;
    }
    public Vector3d getDims()
    {
        Vector3d dimvec = new Vector3d();
        Transform3D scalTr = new Transform3D();
        this.getScaleTG().getTransform(scalTr);
        scalTr.getScale(dimvec);
        return dimvec;       
        
    }
    public double getSurrSphereRad()
    {
        return Math.sqrt(Math.pow(getDims().x, 2)  + Math.pow(getDims().y, 2) +Math.pow(getDims().z,2) );
    }
    
    private void publishStartTransform () 
    {
        ((DicerVTool)getVToolRef()).getPDispatcher()
                     .publish((DicerVTool)getVToolRef(),SGEvent.EVT_START_MOVE_TOOL);
    }
    private void publishTransforming() 
    {
       ((DicerVTool)getVToolRef()).getPDispatcher()
                     .publish((DicerVTool)getVToolRef(),SGEvent.EVT_MOVE_TOOL);
    }
    
    private void publishEndTransform () 
    {
               ((DicerVTool)getVToolRef()).getPDispatcher()
                     .publish((DicerVTool)getVToolRef(),SGEvent.EVT_STOP_MOVE_TOOL);
    }
    
    public DicerTexturer getActiveDicerTexturer()
    {
        return DTexturer;
    }
    
    public void cancelCurrentTexRequest()
    {
        DTexturer.interrupt();                   
        DTexturer = new DicerTexturer(this);
    }
    
    public void memOldVis()
    {
        oldVis = visibility;      //memorize current visibility
    }
    
    public void revOldVis()
    {
        /* revive old visibility */
        setVisibility(oldVis);    
    } 
    
    public int getCurrVis()
    {
        return visibility;
    }
       
    private BranchGroup getDummyMesh()
    {
        DummyMeshGetter dmg = new DummyMeshGetter();
        return dmg.getDummyMesh();
    }
     
}
