/*
 * DicerVToolPlugin.java
 *
 * Created on April 15, 2007, 11:15 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.tools.dicer;

import org.VIVE.gui.toolOperators.Visibility;
import javax.media.j3d.Transform3D;
import javax.media.j3d.View;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import org.apache.log4j.Logger;
import org.sgJoe.exception.SGPluginException;
import org.sgJoe.graphics.SceneGraphEditor;
import org.sgJoe.graphics.behaviors.BehaviorObserver;
import org.sgJoe.graphics.event.SGEvent;
import org.sgJoe.logic.Session;
import org.sgJoe.plugin.Form;
import org.sgJoe.tools.decorators.SliderVToolHandle;
import org.sgJoe.tools.interfaces.VToolForm;
import org.sgJoe.tools.interfaces.VToolPlugin;
import org.sgJoe.tools.interfaces.VirTool;
import org.VIVE.tools.dicer.*;
/**
 *
 * @author Vladimir
 */
public class DicerVToolPlugin extends VToolPlugin implements iDicerConstants
{
     private static Logger logger = Logger.getLogger(DicerVToolPlugin.class);
    
    //Minimal dimension of slicer
    private static final double MIN_DIM = 0.5d;
    
    //Handle referent value - will be set to either CRV or ERV(uporedna velicina )
    private double HRV;
    
    //boolean to indicate the first drag:
    private boolean done;
    
    //boolean to indicate whether drag is simple:
    private boolean dragSimple = false;
    
    
    //To get, combine, and set transforms to TGs
    private Transform3D currTr = new Transform3D();
    private Transform3D tempTr = new Transform3D();
    
    //Rotaton transform and invert rotation transform
    private Transform3D rotTr = new Transform3D();
    private Transform3D currRotTr =new Transform3D();
    private Transform3D invertRotTr = new Transform3D();
    
    //Translation transform and invert translation transform
    private Transform3D transTr = new Transform3D();
    private Transform3D currTransTr = new Transform3D();
    private Transform3D invertTransTr = new Transform3D();
    
    //Scale transform and invert scaling transform
    private Transform3D scaleTr = new Transform3D();
    private Transform3D currScaleTr = new Transform3D();
    private Transform3D invertScaleTr = new Transform3D();
       
    
    //To get and set translation and scaling parameters
    //Current pars (during the action)
    private Vector3d currTransVec = new Vector3d();
    private Vector3d currScaleVec = new Vector3d();
    
    //Old parms (before the action)
    private Vector3d oldTransVec = new Vector3d();
    private Vector3d oldScaleVec = new Vector3d();
    
    //New parms (to set to)
    private Vector3d newScaleVec = new Vector3d();
    private Vector3d newTransVec = new Vector3d();
    
    
    //Defines unscaling factor for the handles
    private Vector3d unscaleVec=new Vector3d();
    
    //helper Transform3D for the above
    private Transform3D helperT3D=new Transform3D();
    
    //Vector and angle of rotation
    private Vector3d rotVec = new Vector3d();
    private double rotAngle;
    private AxisAngle4d newRotAxisAngle = new AxisAngle4d();
    
    //If mouse is over rotation sphere this boolean is true.
    private boolean onTheSphere = false;
    
    //Start and current points during dragging
    private Point3d startPt;                           //VW start point
    private Point3d lStartPt;                          //local start point
    private Point3d relStartPt;                        //relocated starting point
    
    private Point3d currPt;                            //VW current point
    private Point3d lCurrPt;                           //local current point
    private Point3d relCurrPt;                         //relocated current point
    
    //Start and current vectors from the center of the object to the start and current points
    //Used for rotation
    private Vector3d startVec;
    private Vector3d currVec;
    
    //References to VirTool and VTool
    private DicerVirTool dVirTool;
    private DicerVTool dVTool;
    
    //Dimensions of VTool's box
    private float a;
    private float b;
    private float c;
    
    //new scale for X,Y and Z. 
    private double scaleX;
    private double scaleY;
    private double scaleZ;
    
    //Action is used to get received message (different reactions for different actions)
    private int action;
    //Memorize visibility so it can be reset after the action has taken place
    private int oldVis;  
    //Report error if current or start points are null
    private boolean nullError = false;
    //THIS IS JUST 4 NOW! TRAING TO FIX A BUG...
    private boolean mouseOverOtherTool = false;  
    private SliderVToolHandle slider;
    private int texQuality = 1;
    
    
    /** Creates a new instance of DicerVToolPlugin */
    public DicerVToolPlugin(VirTool virToolRef) 
    {
        super(virToolRef);
        startPt = new Point3d();      //VW coordinates
        currPt = new Point3d ();       
        
        lStartPt = new Point3d();      //Local coordinates
        lCurrPt = new Point3d();
        
        relStartPt = new Point3d();      //relocated coordinates
        relCurrPt = new Point3d();
        
        Vector3d startVec = new Vector3d();
        Vector3d currVec = new Vector3d();
    } 
     
    public void performAction(Form form, SceneGraphEditor editor, Session session) throws SGPluginException 
    {
        //System.out.println("Ulazak u performAction Dicera.");
        DicerVToolForm toolForm = (DicerVToolForm) form;
        View viewSource = toolForm.getViewSource();
        //get action
        setAction(toolForm.getAction());
        System.out.println("Action: [ " + action + " ] ");
        dVirTool = (DicerVirTool) toolForm.getVirToolRef();
        dVTool = (DicerVTool) dVirTool.getVToolRef();
        a = dVTool.getShp().getXdim();
        b = dVTool.getShp().getYdim();
        c = dVTool.getShp().getZdim();        
               
        if(action == toolForm.ACT_MOUSE_VTOOL_CLICKED) 
        {           

           //START XY TRANSLATION            
        } 
        else if (action == VToolForm.ACT_MOUSE_VTOOL_PRESSED) 
        {   //initiating the translation
            dVirTool.mode = DICER_TRANSLATION;    //mode set
            
            System.out.println("action == vtool_pressed");
            setMouseOverOtherTool(false);
            try
            {
                lStartPt.set(toolForm.getLGripPt());
                startPt.set(toolForm.getVWGripPt());                
                relStartPt.set(toolForm.getVWGripPt());
            }
            catch(Exception e)
            {
                System.out.println("startPt error."+e.toString());
            }
            prepareForTranslation();
        }      
        
        else if(action == VToolForm.ACT_MOUSE_SCALETOUCHHANDLE_PRESSED)
        {     //initiating the scaling
            dVirTool.mode = DICER_SCALING;
            
            //System.out.println("action == ACT_MOUSE_SCALETOUCHHANDLE_PRESSED");
            setMouseOverOtherTool(false);
            try 
            {
                lStartPt.set(toolForm.getLGripPt());
                startPt.set(toolForm.getVWGripPt());
                relStartPt.set(toolForm.getVWGripPt());
            } catch(Exception e) 
            {
                System.out.println("startPt,relStartPt ili lStartPt error-greska. "+e.toString());
                
            }
            System.out.println("startPt: "+startPt.x+" "+startPt.y+" "+startPt.z);
            //System.out.println("lStartPt: "+lStartPt.x+" "+lStartPt.y+" "+lStartPt.z);
            //System.out.println("relStartPt: "+relStartPt.x+" "+relStartPt.y+" "+relStartPt.z);
            
            prepareForScaling();
        }
                
        else if (action == VToolForm.ACT_MOUSE_PLANARHANDLE_DRAGGED && dVirTool.mode == DICER_TRANSLATION)
        {           
            //We need to change VW gripPt before performing any action
            
            System.out.println("Action == slider_dragged , translation occurs.");
            try
            {
                lCurrPt.set(toolForm.getLGripPt());
                currPt.set(toolForm.getVWGripPt());                
                relCurrPt.set(toolForm.getVWGripPt());
            }
            catch(Exception e)
            {
                System.out.println("startPt,relStartPt ili lStartPt error-greska.");
            }
            translate();                  
            
        }
        else if(action == VToolForm.ACT_MOUSE_PLANARHANDLE_DRAGGED && dVirTool.mode == DICER_SCALING)
        {
            try
            {
                lCurrPt.set(toolForm.getLGripPt()); 
                currPt.set(toolForm.getVWGripPt());
                relCurrPt.set(toolForm.getVWGripPt());
            }
            catch(Exception e)
            {
                System.out.println("startPt,relStartPt ili lStartPt error-greska.");
            }
            transcale();
        }
        
        else if (action == VToolForm.ACT_MOUSE_SWITCH_SPHERE_ON_PRESSED) 
        {
            dVirTool.setVisibility(DicerVirTool.VIS_ROT_SPHERE_ON);
            //Dulke
            Visibility v =(Visibility)dVirTool.getToolOperatorPanelList()[0];            
            if (v!=null) v.setVisibilityLevel(DicerVirTool.VIS_ROT_SPHERE_ON);
//TURN ROT SPHERE OFF            
        } 
        
        else if ( action == VToolForm.ACT_MOUSE_SWITCH_SPHERE_OFF_PRESSED) 
        {
            dVirTool.setVisibility(DicerVirTool.VIS_HANDLES_ON);
            //Dulke
            Visibility v =(Visibility)dVirTool.getToolOperatorPanelList()[0];            
            if (v!=null) v.setVisibilityLevel(DicerVirTool.VIS_HANDLES_ON);
        
//START ROTATING
        } 
        
        else if ( action == VToolForm.ACT_MOUSE_ROTATE_HANDLE_PRESSED) 
        {
            setMouseOverOtherTool(false);
            if (toolForm.getVWGripPt()!=null){
                startVec = new Vector3d();
                currVec = new Vector3d();
                startVec.set(toolForm.getVWGripPt());
                if(!onTheSphere)
                    startToRotate();
                //Publish start rotation
                    publishStartTransform();
                nullError = false;
            } else nullError = true;

//ROTATING    
        } 
        
        else if ( action == VToolForm.ACT_MOUSE_ROTATE) 
        {
            if (toolForm.getVWGripPt()!= null && !nullError) 
            {
                if(!onTheSphere)
                {
                    startVec = new Vector3d(toolForm.getVWGripPt());
                    startToRotate();
                }
                currVec = new Vector3d();
                currVec.set(toolForm.getVWGripPt());
                currVec.sub(oldTransVec);
                rotVec.cross(startVec, currVec);
                rotAngle = startVec.angle(currVec);
                newRotAxisAngle.set (rotVec, rotAngle);
                rotate(newRotAxisAngle);
                //Publish rotation (TG change)
                publishTransforming();
                applyNewTextures();               //apply textures for new orientation
                //... and not a null point
                nullError = false;
            }
            else if (toolForm.getVWGripPt()==null)
            {
               //What if mouse pointer gets out sphere.
                //This works fine, but could be recoded...
                int dx, dy;
                int prevX = toolForm.getPrevX();
                int prevY = toolForm.getPrevY();
                int currX = toolForm.getCurrX();
                int currY = toolForm.getCurrY();
                dx = currX - prevX;
                dy = currY - prevY;              
                tempTr.setIdentity();
                tempTr.rotX(dy * 0.01);
                dVirTool.getRotTG().getTransform(currTr);
                currTr.mul(tempTr);
                tempTr.setIdentity();
                tempTr.rotY(dx * 0.01);
                currTr.mul(tempTr);                
                dVirTool.getRotTG().setTransform(currTr);
                dVirTool.getRotTG().getTransform(rotTr);
                //Publish rotation (TG change)
                publishTransforming();
                
                setOnTheSphere(false);
            }else nullError = true;


        }
                
        else if( action == VToolForm.ACT_MOUSE_RELEASED ||
                 action == VToolForm.ACT_MOUSE_SCALETOUCHHANDLE_RELEASED ||
                 action == VToolForm.ACT_MOUSE_ROTATE_HANDLE_RELEASED ||
                 action == VToolForm.ACT_MOUSE_PLANARHANDLE_RELEASED ||
                 action == VToolForm.ACT_MOUSE_VTOOL_RELEASED )
        {           
            dVirTool.setVisibility(oldVis);
            setOnTheSphere(false);//to start a new rotation
            //Publish end transform
            publishEndTransform();
            dVirTool.setLocked(false);                   
        
        }    
        
        else if(action == toolForm.ACT_ON_INSTANCE_DELETE) 
        {
            super.OnInstanceDelete(toolForm, editor);    
        }
    }  
    
    private void prepareForTranslation()
    {          
            dragSimple = dVirTool.dragSimple;             //take the value from DicerVirTool
            done = false;
            System.out.println("Start point: "+startPt.x +"   "+ startPt.y +"   "+ startPt.z);
            
            dVirTool.getTransTG().getTransform(currTransTr);
            dVirTool.getRotTG().getTransform(currRotTr);
                       
            invertTransTr.invert(currTransTr);
            invertRotTr.invert(currRotTr);
                                  
            invertTransTr.transform(relStartPt);
            invertRotTr.transform(relStartPt);
              
            
            currTransTr.get(oldTransVec);                       
            //now we have to set the submode
            
            oldVis = dVirTool.getVisibility();                              // Remember the old visibility
            
            if (lStartPt.z == dVTool.getShp().getZdim())                    // Front side touched
            {
                if(!dragSimple)   dVirTool.setVisibility(DicerVirTool.VIS_TRANSLATING0);
                else dVirTool.setVisibility(DicerVirTool.VIS_SIMPLE0);
                dVirTool.submode = DICER_TRANSLATING0;
            }
            else if (lStartPt.z == -dVTool.getShp().getZdim())              //  Back side touched
            {
                if(!dragSimple)  dVirTool.setVisibility(DicerVirTool.VIS_TRANSLATING1);
                else dVirTool.setVisibility(DicerVirTool.VIS_SIMPLE1);
                dVirTool.submode = DICER_TRANSLATING1;
              
            }     
            else if(lStartPt.x == dVTool.getShp().getXdim())                 //  Right side touched
            {
                if(!dragSimple)  dVirTool.setVisibility(DicerVirTool.VIS_TRANSLATING2);
                else  dVirTool.setVisibility(DicerVirTool.VIS_SIMPLE2);
                dVirTool.submode = DICER_TRANSLATING2;
            }
            else if (lStartPt.x == -dVTool.getShp().getXdim())      //Left side touched
            {
                if(!dragSimple) dVirTool.setVisibility(DicerVirTool.VIS_TRANSLATING3);
                else    dVirTool.setVisibility(DicerVirTool.VIS_SIMPLE3);
                dVirTool.submode = DICER_TRANSLATING3;
            }
            else if(lStartPt.y == dVTool.getShp().getYdim())        //Top side touched
            {
                if(!dragSimple) dVirTool.setVisibility(DicerVirTool.VIS_TRANSLATING4);
                else    dVirTool.setVisibility(DicerVirTool.VIS_SIMPLE4);
                dVirTool.submode = DICER_TRANSLATING4;
                
            }
            else if(lStartPt.y == -dVTool.getShp().getYdim())        //Bottom side touched
            {
                if(!dragSimple) dVirTool.setVisibility(DicerVirTool.VIS_TRANSLATING5);
                else    dVirTool.setVisibility(DicerVirTool.VIS_SIMPLE5);
                dVirTool.submode = DICER_TRANSLATING5;
                
            }            
            publishStartTransform();
            
    }
    
    private void translate()
    {        
        if(!done)          // this means we do this just the first drag:
        {            
            // We have to raise startPt according to mode in order to eliminate unwanted offset
          
            invertTransTr.transform(relCurrPt);
            invertRotTr.transform(relCurrPt);         
                                 
            if(dVirTool.submode == 0)   
            {
                relStartPt.set(relStartPt.x , relStartPt.y , relCurrPt.z);
            }
            
            else if(dVirTool.submode == 1)   
            {
                relStartPt.set(relStartPt.x , relStartPt.y , relCurrPt.z);
            } 
            
            else if(dVirTool.submode == 2)   
            {
                relStartPt.set(relCurrPt.x , relStartPt.y , relStartPt.z);
            }
            
            else if(dVirTool.submode == 3)   
            {
            
                 relStartPt.set(relCurrPt.x , relStartPt.y , relStartPt.z);
            }
            
            else if(dVirTool.submode == 4)   
            {
                relStartPt.set(relStartPt.x , relCurrPt.y , relStartPt.z);
            }
            
            else if(dVirTool.submode == 5)   
            {
                relStartPt.set(relStartPt.x , relCurrPt.y , relStartPt.z);
            }            
            System.out.println("Current point: "+currPt.x +"   "+ currPt.y +"   "+ currPt.z);
            
            currRotTr.transform(relStartPt);   //(return to  real coordinates)
            currRotTr.transform(relCurrPt);
            
            currTransTr.transform(relStartPt);
            currTransTr.transform(relCurrPt);
            
            done=true;
        } 
        
        newTransVec.set(relCurrPt);    
        newTransVec.sub(relStartPt);            
              
        System.out.println("Vektor translacije je: "+ newTransVec.x + " " + newTransVec.y + " " + newTransVec.z);
        newTransVec.add(oldTransVec);
        Transform3D tempTr = new Transform3D();
        tempTr.setTranslation(newTransVec);
        dVirTool.getTransTG().setTransform(tempTr);
        publishTransforming();                       //to inform calipers of transformation change
        applyNewTextures();              
        
    }
    
    private void prepareForScaling()
    {        
      //  prepareScalingParameters();
      //  prepareScaleTranslatePars();
        
        done = false;    
        dVirTool.getTransTG().getTransform(currTransTr);
        dVirTool.getRotTG().getTransform(currRotTr);
        dVirTool.getScaleTG().getTransform(currScaleTr);
                    
        currScaleTr.getScale(oldScaleVec);     
        currTransTr.get(oldTransVec);   
            
        //now we have to set the submode
        DicerScaleHandle test=(DicerScaleHandle)(dVirTool.getScaleHandle(dVirTool.getHandleId()));
        int hid=test.getId();                           // handle id as a separate variable
        int side;
        System.out.println("Id hendla je " + hid);                   
         
        invertTransTr.invert(currTransTr);              //first invert translational transformation
        invertRotTr.invert(currRotTr);                  //then invert rotational transformation
        invertScaleTr.invert(currScaleTr);              //then invert scaling
        
        invertTransTr.transform(relStartPt);            //needed   
        invertRotTr.transform(relStartPt);              //for  
        invertScaleTr.transform(relStartPt);            //scaling 
            
            
        if ((hid>0) && (hid<13)) HRV = ERV;             //setting HRV to one of the two values
        else HRV=CRV;
            
        oldVis = dVirTool.getVisibility();
        if(Math.abs(relStartPt.z-HRV)<0.00000001)
        {
            dVirTool.setVisibility(DicerVirTool.VIS_SCALING0);
            dVirTool.submode = DICER_SCALING0;                 
        }
        if(Math.abs(relStartPt.z+HRV)<0.00000001)
        {
            dVirTool.setVisibility(DicerVirTool.VIS_SCALING1);
            dVirTool.submode = DICER_SCALING1;          
            
        }
        if(Math.abs(relStartPt.x-HRV)<0.00000001)
        {
            dVirTool.setVisibility(DicerVirTool.VIS_SCALING2);
            dVirTool.submode = DICER_SCALING2;            
        }
        if(Math.abs(relStartPt.x+HRV)<0.00000001)
        {
            dVirTool.setVisibility(DicerVirTool.VIS_SCALING3);
            dVirTool.submode = DICER_SCALING3;             
        }
        if(Math.abs(relStartPt.y-HRV)<0.00000001)
        {
            dVirTool.setVisibility(DicerVirTool.VIS_SCALING4);
            dVirTool.submode = DICER_SCALING4;                
        }
        if(Math.abs(relStartPt.y+HRV)<0.00000001)
        {
            dVirTool.setVisibility(DicerVirTool.VIS_SCALING5);
            dVirTool.submode = DICER_SCALING5;                
        }
        publishStartTransform(); 
            
    }
    
    private void transcale()
    {        
        invertTransTr.transform(relCurrPt);      //apply inverts to currPt(relCurrPT)
        invertRotTr.transform(relCurrPt);        //we use rel points to 
        invertScaleTr.transform(relCurrPt);      //perform scaling     
        
        
        invertTransTr.transform(startPt);              //needed for translation 
        invertRotTr.transform(startPt);                //that takes place during scaling
        
        invertTransTr.transform(currPt);
        invertRotTr.transform(currPt);
                 
        
        //setting scaling for corners on sides 0 and 1
        if( ( (dVirTool.submode==DICER_SCALING0) && (dVirTool.getHandleId()==16) ) ||
            ( (dVirTool.submode==DICER_SCALING1) && (dVirTool.getHandleId()==20) ) )      
        {             
            newScaleVec.set(1+(relCurrPt.x-relStartPt.x)/2 , 1+(relCurrPt.y-relStartPt.y)/2 , 1.0);
            startPt.set(startPt.x , startPt.y , currPt.z);          //for later translation                     
        }  
        
        else if( ( (dVirTool.submode==DICER_SCALING0) && (dVirTool.getHandleId()==15) ) ||
            ( (dVirTool.submode==DICER_SCALING1) && (dVirTool.getHandleId()==19) ) )
        {              
            newScaleVec.set(1+(relCurrPt.x-relStartPt.x)/2 , 1-(relCurrPt.y-relStartPt.y)/2 , 1.0);
            startPt.set(startPt.x , startPt.y , currPt.z);          //for later translation         
        }
        
        else if( ( (dVirTool.submode==DICER_SCALING0) && (dVirTool.getHandleId()==14) ) ||
            ( (dVirTool.submode==DICER_SCALING1) && (dVirTool.getHandleId()==18) ) )
        {            
            newScaleVec.set(1-(relCurrPt.x-relStartPt.x)/2 , 1-(relCurrPt.y-relStartPt.y)/2 , 1.0);
            startPt.set(startPt.x , startPt.y , currPt.z);          //for later translation         
        }
        
        else if( ( (dVirTool.submode==DICER_SCALING0) && (dVirTool.getHandleId()==13) ) ||
            ( (dVirTool.submode==DICER_SCALING1) && (dVirTool.getHandleId()==17) ) )
        {            
            newScaleVec.set(1-(relCurrPt.x-relStartPt.x)/2 , 1+(relCurrPt.y-relStartPt.y)/2 , 1.0);
            startPt.set(startPt.x , startPt.y , currPt.z);          //for later translation        
        }
        
        
        
        //setting scaling for corners on sides 2 and 3
        else if( ( (dVirTool.submode==DICER_SCALING2) && (dVirTool.getHandleId()==16) ) ||
            ( (dVirTool.submode==DICER_SCALING3) && (dVirTool.getHandleId()==13) ) )
        {            
            newScaleVec.set(1.0, 1+(relCurrPt.y-relStartPt.y)/2 , 1+(relCurrPt.z-relStartPt.z)/2);
            startPt.set(currPt.x , startPt.y , startPt.z);          //for later translation     
        }
        
        else if( ( (dVirTool.submode==DICER_SCALING2) && (dVirTool.getHandleId()==15) ) ||
            ( (dVirTool.submode==DICER_SCALING3) && (dVirTool.getHandleId()==14) ) )
        {             
            newScaleVec.set(1.0, 1-(relCurrPt.y-relStartPt.y)/2 , 1+(relCurrPt.z-relStartPt.z)/2);
            startPt.set(currPt.x , startPt.y , startPt.z);          //for later translation       
        }
        
        else if( ( (dVirTool.submode==DICER_SCALING2) && (dVirTool.getHandleId()==19) ) ||
            ( (dVirTool.submode==DICER_SCALING3) && (dVirTool.getHandleId()==18) ) )
        {            
            newScaleVec.set(1.0, 1-(relCurrPt.y-relStartPt.y)/2 , 1-(relCurrPt.z-relStartPt.z)/2);
            startPt.set(currPt.x , startPt.y , startPt.z);          //for later translation     
        }
        
        else if( ( (dVirTool.submode==DICER_SCALING2) && (dVirTool.getHandleId()==20) ) ||
            ( (dVirTool.submode==DICER_SCALING3) && (dVirTool.getHandleId()==17) ) )
        {            
            newScaleVec.set(1.0, 1+(relCurrPt.y-relStartPt.y)/2 , 1-(relCurrPt.z-relStartPt.z)/2);
            startPt.set(currPt.x , startPt.y , startPt.z);          //for later translation     
        }        
        
        //setting scaling for corners on sides 4 and 5
        else if( ( (dVirTool.submode==DICER_SCALING4) && (dVirTool.getHandleId()==17) ) ||
            ( (dVirTool.submode==DICER_SCALING5) && (dVirTool.getHandleId()==18) ) )
        {             
            newScaleVec.set(1-(relCurrPt.x-relStartPt.x)/2, 1.0 , 1-(relCurrPt.z-relStartPt.z)/2);
            startPt.set(startPt.x , currPt.y , startPt.z);          //for later translation        
        }
        
        else if( ( (dVirTool.submode==DICER_SCALING4) && (dVirTool.getHandleId()==13) ) ||
            ( (dVirTool.submode==DICER_SCALING5) && (dVirTool.getHandleId()==14) ) )
        {
            newScaleVec.set(1-(relCurrPt.x-relStartPt.x)/2, 1.0 , 1+(relCurrPt.z-relStartPt.z)/2);
            startPt.set(startPt.x , currPt.y , startPt.z);          //for later translation      
        }
        
        else if( ( (dVirTool.submode==DICER_SCALING4) && (dVirTool.getHandleId()==16) ) ||
            ( (dVirTool.submode==DICER_SCALING5) && (dVirTool.getHandleId()==15) ) )
        { 
            newScaleVec.set(1+(relCurrPt.x-relStartPt.x)/2, 1.0 , 1+(relCurrPt.z-relStartPt.z)/2);  
            startPt.set(startPt.x , currPt.y , startPt.z);          //for later translation        
        }
        
        else if( ( (dVirTool.submode==DICER_SCALING4) && (dVirTool.getHandleId()==20) ) ||
            ( (dVirTool.submode==DICER_SCALING5) && (dVirTool.getHandleId()==19) ) )
        {
            newScaleVec.set(1+(relCurrPt.x-relStartPt.x)/2, 1.0 , 1-(relCurrPt.z-relStartPt.z)/2);
            startPt.set(startPt.x , currPt.y , startPt.z);          //for later translation  
        }
        
        //corner scaling setting up finished(if needed)
        
        //to do: edges scaling setting up  
               
        //setting scaling for edge sides on sides 0 and 1
        else if( ( (dVirTool.submode==DICER_SCALING0) && (dVirTool.getHandleId()==1) ) ||
            ( (dVirTool.submode==DICER_SCALING1) && (dVirTool.getHandleId()==5) ) )      
        {  
            newScaleVec.set(1.0 , 1+(relCurrPt.y-relStartPt.y)/2 , 1.0);
            startPt.set(currPt.x , startPt.y , currPt.z);          //for later translation                    
        }  
        
        else if( ( (dVirTool.submode==DICER_SCALING0) && (dVirTool.getHandleId()==2) ) ||
            ( (dVirTool.submode==DICER_SCALING1) && (dVirTool.getHandleId()==6) ) )      
        {
            newScaleVec.set(1.0 , 1-(relCurrPt.y-relStartPt.y)/2 , 1.0);
            startPt.set(currPt.x , startPt.y , currPt.z);          //for later translation                    
        }  
        
        else if( ( (dVirTool.submode==DICER_SCALING0) && (dVirTool.getHandleId()==3) ) ||
            ( (dVirTool.submode==DICER_SCALING1) && (dVirTool.getHandleId()==7) ) )      
        {
            newScaleVec.set(1-(relCurrPt.x-relStartPt.x)/2 , 1.0 , 1.0);
            startPt.set(startPt.x , currPt.y , currPt.z);          //for later translation                       
        }
        
        else if( ( (dVirTool.submode==DICER_SCALING0) && (dVirTool.getHandleId()==4) ) ||
            ( (dVirTool.submode==DICER_SCALING1) && (dVirTool.getHandleId()==8) ) )      
        {
            newScaleVec.set(1+(relCurrPt.x-relStartPt.x)/2 , 1.0 , 1.0);
            startPt.set(startPt.x , currPt.y , currPt.z);          //for later translation                 
        }  
        
        //setting scaling for edge sides on sides 2 and 3
        else if( ( (dVirTool.submode==DICER_SCALING2) && (dVirTool.getHandleId()==4) ) ||
            ( (dVirTool.submode==DICER_SCALING3) && (dVirTool.getHandleId()==3) ) )      
        {
            newScaleVec.set(1.0 , 1.0 , 1+(relCurrPt.z-relStartPt.z)/2);
            startPt.set(currPt.x , currPt.y , startPt.z);          //for later translation                   
        }  
        
        else if( ( (dVirTool.submode==DICER_SCALING2) && (dVirTool.getHandleId()==11) ) ||
            ( (dVirTool.submode==DICER_SCALING3) && (dVirTool.getHandleId()==10) ) )      
        {
            newScaleVec.set(1.0 , 1-(relCurrPt.y-relStartPt.y)/2 , 1.0);
            startPt.set(currPt.x , startPt.y, currPt.z);                  
        }  
        
        else if( ( (dVirTool.submode==DICER_SCALING2) && (dVirTool.getHandleId()==8) ) ||
            ( (dVirTool.submode==DICER_SCALING3) && (dVirTool.getHandleId()==7) ) )      
        {
            newScaleVec.set(1.0 , 1.0 , 1-(relCurrPt.z-relStartPt.z)/2);
            startPt.set(currPt.x , currPt.y , startPt.z);          //for later translation                  
        }  
        
        else if( ( (dVirTool.submode==DICER_SCALING2) && (dVirTool.getHandleId()==12) ) ||
            ( (dVirTool.submode==DICER_SCALING3) && (dVirTool.getHandleId()==9) ) )      
        {
            newScaleVec.set(1.0 , 1+(relCurrPt.y-relStartPt.y)/2 , 1.0);
            startPt.set(currPt.x , startPt.y , currPt.z);          //for later translation     
        }  
        
        //setting scaling for edge sides on sides 4 and 5
        else if( ( (dVirTool.submode==DICER_SCALING4) && (dVirTool.getHandleId()==9) ) ||
            ( (dVirTool.submode==DICER_SCALING5) && (dVirTool.getHandleId()==10) ) )      
        {
            newScaleVec.set(1-(relCurrPt.x-relStartPt.x)/2 , 1.0 , 1.0);
            startPt.set(startPt.x , currPt.y , currPt.z);          //for later translation                  
        }  
        
        else if( ( (dVirTool.submode==DICER_SCALING4) && (dVirTool.getHandleId()==1) ) ||
            ( (dVirTool.submode==DICER_SCALING5) && (dVirTool.getHandleId()==2) ) )      
        {
            newScaleVec.set(1.0 , 1.0 , 1+(relCurrPt.z-relStartPt.z)/2);
            startPt.set(currPt.x , currPt.y , startPt.z);          //for later translation                        
        }  
        
        else if( ( (dVirTool.submode==DICER_SCALING4) && (dVirTool.getHandleId()==12) ) ||
            ( (dVirTool.submode==DICER_SCALING5) && (dVirTool.getHandleId()==11) ) )      
        {
            newScaleVec.set(1+(relCurrPt.x-relStartPt.x)/2 , 1.0 , 1.0);
            startPt.set(startPt.x , currPt.y , currPt.z);          //for later translation                    
        }  
        
        else if( ( (dVirTool.submode==DICER_SCALING4) && (dVirTool.getHandleId()==5) ) ||
            ( (dVirTool.submode==DICER_SCALING5) && (dVirTool.getHandleId()==6) ) )      
        {
            newScaleVec.set(1.0 , 1.0 , 1-(relCurrPt.z-relStartPt.z)/2);
            startPt.set(currPt.x , currPt.y , startPt.z);          //for later translation                   
        }     
        
        System.out.println("reld startPt: " + startPt.x + " " + startPt.y+" " + startPt.z);
        System.out.println("reld currPt : " + currPt.x +  " " + currPt.y+ " " + currPt.z);
        
        currRotTr.transform(startPt);               // apply rotation
        currTransTr.transform(startPt);             // and then translation
        
        currRotTr.transform(currPt);                // apply same transformations to 
        currTransTr.transform(currPt);              // current point
              
        System.out.println("final startPt: "+startPt.x+" "+startPt.y+" "+startPt.z);
        System.out.println("final currPt : "+currPt.x+ " "+currPt.y+ " "+currPt.z);
        
       
        newScaleVec.set(newScaleVec.x*oldScaleVec.x , newScaleVec.y*oldScaleVec.y , newScaleVec.z*oldScaleVec.z);
                
        if( (newScaleVec.x<0.5d) || (newScaleVec.y<0.5d) || (newScaleVec.z<0.5d) )
        {
            System.out.println("Dimensions too small.");
            return;
        }
        
        //scaling takes place
        currScaleTr.setScale(newScaleVec);
                             
        //translation follows                        
            
        newTransVec.set(currPt);
        newTransVec.sub(startPt);
        newTransVec.scale(0.5);
        
        newTransVec.add(oldTransVec);
         
        Transform3D tempTr = new Transform3D();
        tempTr.setTranslation(newTransVec);
                      
         
        dVirTool.getTransTG().setTransform(tempTr);
        dVirTool.getScaleTG().setTransform(currScaleTr);     
        
        Transform3D why=new Transform3D();
        Vector3d v=new Vector3d();
        dVirTool.getTransTG().getTransform(why);
        why.get(v);
        
        publishTransforming(); 
        applyNewTextures();
    }
    
    private void unscaleHandles()
    {        
        for(int x=1;x<21;x++)
        {
            Vector3d curr=new Vector3d();
            DicerScaleHandle dsh=dVirTool.getScaleHandle(x);
            dsh.getTransform(helperT3D);
            helperT3D.getScale(curr);
            curr.set(curr.x/unscaleVec.x , curr.y/unscaleVec.y , curr.z/unscaleVec.z);
            helperT3D.setScale(curr);
            dsh.setTransform(helperT3D);
            
        }
    
    } 
    
    private void startToRotate () 
    {
        dVirTool.getRotTG().getTransform(currRotTr);
        dVirTool.getTransTG().getTransform(currTransTr);
        currTransTr.get(oldTransVec);
        startVec.sub(oldTransVec);
        if(dVirTool.getVisibility()!=DicerVirTool.VIS_ROTATING)
            oldVis = dVirTool.getVisibility();
            //setting visible nodes (turn Guider ON)
        dVirTool.setVisibility(DicerVirTool.VIS_ROTATING);
        startPt.sub(oldTransVec);
        setOnTheSphere(true);
    }
    private void rotate (AxisAngle4d newRotAxisAngle)
    {
        currTr.setIdentity();
        currTr.setRotation(newRotAxisAngle);
        invertRotTr.invert(currTr);                // this isn't necessary
        currTr.mul(currRotTr);
        dVirTool.getRotTG().setTransform(currTr);
    }
    
    public int getAction() {
        return action;
    }

    private void setAction(int action) {
        this.action = action;
    }

    public Point3d getCurrPt() 
    {
        return currPt;
    }
    
    private void setOnTheSphere(boolean b) 
    {
        onTheSphere = b;
    }
    
    private void publishStartTransform () 
    {
        ((DicerVTool)getVirToolRef().getVToolRef()).getPDispatcher()
                     .publish((DicerVTool)getVirToolRef().getVToolRef(),SGEvent.EVT_START_MOVE_TOOL);
    }
    private void publishTransforming() 
    {
       ((DicerVTool)getVirToolRef().getVToolRef()).getPDispatcher()
                     .publish((DicerVTool)getVirToolRef().getVToolRef(),SGEvent.EVT_MOVE_TOOL);
    }
    
    private void publishEndTransform () 
    {
               ((DicerVTool)getVirToolRef().getVToolRef()).getPDispatcher()
                     .publish((DicerVTool)getVirToolRef().getVToolRef(),SGEvent.EVT_STOP_MOVE_TOOL);
    }

    private boolean isMouseOverOtherTool() 
    {
        return mouseOverOtherTool;
    }

    private void setMouseOverOtherTool(boolean mouseOverOtherTool) 
    {
        this.mouseOverOtherTool = mouseOverOtherTool;
    }
    
    private void applyNewTextures()
    {
        cancelCurrentTextureRequest();
        
        Thread th = getActiveDicerTexturer();
        th.start();
                
    }
    
    private DicerTexturer getActiveDicerTexturer()
    {        
        return dVirTool.getActiveDicerTexturer();
    }

    private void cancelCurrentTextureRequest()
    {
        dVirTool.cancelCurrentTexRequest();
    }
    
    public void insertDummyMesh()
    {
       dVirTool.setVisibility(DicerVirTool.VIS_MESH);
    }
    
    
}