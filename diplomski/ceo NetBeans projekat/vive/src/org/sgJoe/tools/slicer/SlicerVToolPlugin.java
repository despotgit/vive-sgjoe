/*
 * SlicerVToolPlugin.java
 *
 * Created on Sreda, 2006, Maj 31, 22.47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.sgJoe.tools.slicer;

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
import org.sgJoe.tools.interfaces.VToolForm;
import org.sgJoe.tools.interfaces.VToolPlugin;
import org.sgJoe.tools.interfaces.VirTool;

/**
 *
 * @author Igor
 */
public class SlicerVToolPlugin extends VToolPlugin 
{
    
    private static Logger logger = Logger.getLogger(SlicerVToolPlugin.class);
    
    //Minimal dimension of slicer
    private static final double MIN_DIM = 0.5d;
    
    //To get, combine, and set transforms to TGs
    private Transform3D currTr = new Transform3D();
    private Transform3D tempTr = new Transform3D();
    
    //To put rotaton transform, and invert rotation transform
    private Transform3D rotTr = new Transform3D();
    private Transform3D invertRotTr = new Transform3D();
    
    //To put translation transform, and invert translation transform
    private Transform3D transTr = new Transform3D();
    private Transform3D invertTransTr = new Transform3D();
    
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
    
    //Defines unmovable point during scaling
    private Vector3d repVec = new Vector3d();
    
    //Vector and angle of rotation
    private Vector3d rotVec = new Vector3d();
    private double rotAngle;
    private AxisAngle4d newRotAxisAngle = new AxisAngle4d();
    
    //If mouse is over rotation sphere this boolean is true.
    private boolean onTheSphere = false;
    
    //Start and current points during dragging
    private Point3d startPt;
    private Point3d currPt;
    
    //Start and current vectors from the center of the object to the start and current points
    //Used for rotation
    private Vector3d startVec;
    private Vector3d currVec;
    
    //References to VirTool and VTool
    private SlicerVirTool sVirTool;
    private SlicerVTool sVTool;
    
    //Dimensions of VTools plate
    private float a;
    //new scale for X and Y. 
    private double scaleX;
    private double scaleY;
    
    //Action is used to get received message (different reactions for different actions)
    private int action;
    //Memorize visibility so it can be resetted after the action has taken place
    private int oldVis;
    //Report error if current or start points are null
    private boolean nullError = false;
    //THIS IS JUST 4 NOW! TRAING TO FIX A BUG...
    private boolean mouseOverOtherTool = false;
    
    /**
     * Creates a new instance of SlicerVToolPlugin
     */
    public SlicerVToolPlugin(VirTool virToolRef) 
    {
        super(virToolRef);
        startPt = new Point3d ();
        currPt = new Point3d ();
        Vector3d startVec = new Vector3d();
        Vector3d currVec = new Vector3d();
    }

    public void performAction(Form form, SceneGraphEditor editor, Session session) throws SGPluginException 
    {
        //System.out.println("Ulazak u performAction Slicera.");
        SlicerVToolForm toolForm = (SlicerVToolForm) form;
        View viewSource = toolForm.getViewSource();
        //get action
        setAction(toolForm.getAction());
        System.out.println("Action: [ " + action + " ] ");
        sVirTool = (SlicerVirTool) toolForm.getVirToolRef();
        sVTool = (SlicerVTool) sVirTool.getVToolRef();
        a = sVTool.getShp().getA();
        
        if(action == toolForm.ACT_MOUSE_VTOOL_CLICKED) 
        {           

           //START XY TRANSLATION            
        } 
        else if (!sVirTool.isLocked() && action == VToolForm.ACT_MOUSE_VTOOL_PRESSED) 
        { 
             setMouseOverOtherTool(false);
             sVirTool.setVisibility(SlicerVirTool.VIS_HANDLES_ON);
             if (toolForm.getVWGripPt()!=null)
             {
                startPt.set(toolForm.getVWGripPt()); //get startPt
                startToXYTranslate(); // Prepare start parms
                
                //Publish start translation
                    publishStartTransform();
                nullError = false;
             }else nullError = true;
                         
//XY TRANSLATING             
        } 
        else if (!sVirTool.isLocked() && !isMouseOverOtherTool()
                    &&(action == VToolForm.ACT_MOUSE_GUIDEHANDLE_DRAGGED
                        && sVirTool.getGuiderMod() == GuideHandle.MOD_TRANSLATE_XY))
        {
                            //Every guider ends the same action VToolForm.ACT_MOUSE_GUIDEHANDLE_DRAGGED
                            //To identify which guider sends the action, guider mod is used
             if (toolForm.getVWGripPt()!= null && !nullError) 
             {
                currPt.set(toolForm.getVWGripPt());
                prepareXYTranslateParms();
                translate(newTransVec);
                //Publish translation (TG change)
                    publishTransforming();
                //... and not a null point
                nullError = false;
            }
            else 
            {
                 System.out.println("helloooo!");
                 System.out.println("NEW POINT IS NULL");
                 System.out.println("Did U knew that?!");
                 nullError = true;
                 action = VToolForm.ACT_MOUSE_GUIDEHANDLE_RELEASED;
            }
             
//Z TRANSLATE START
        }
        else if(!sVirTool.isLocked() 
                    && action == VToolForm.ACT_MOUSE_TRANSLATE_TOUCHHANDLE_PRESSED
                        && sVirTool.getTranslateHandleMod() == TranslateTouchHandle.MOD_Z) 
        {
            setMouseOverOtherTool(false);
             if (toolForm.getVWGripPt()!=null)
             {
                startPt.set(toolForm.getVWGripPt());
                startToZTranslate();
                //Publish start translation
                    publishStartTransform();
                nullError = false;
            } else nullError = true;
             
//Z TRANSLATING             
        } else if (!sVirTool.isLocked() && !isMouseOverOtherTool()
                    &&((action == VToolForm.ACT_MOUSE_GUIDEHANDLE_DRAGGED 
                            && sVirTool.getGuiderMod() == GuideHandle.MOD_TRANSLATE_Z)
                        || (action == VToolForm.ACT_MOUSE_TRANSLATE_TOUCHHANDLE_DRAGGED
                            && sVirTool.getTranslateHandleMod() == TranslateTouchHandle.MOD_Z)))
        {
             if (toolForm.getVWGripPt()!= null && !nullError) 
             {
                currPt.set(toolForm.getVWGripPt());
                prepareZTranslateParms();
                translate(newTransVec);
                //Publish translation (TG change)
                    publishTransforming();
                //... and not a null point
                nullError = false;
            }else nullError = true;
             
//START SCALING
        } 
        else if (!sVirTool.isLocked() && action == VToolForm.ACT_MOUSE_SCALETOUCHHANDLE_PRESSED) 
        {
            setMouseOverOtherTool(false);
            //setting start point (world coordinates)
            if (toolForm.getVWGripPt()!=null)
            {
                startPt.set(toolForm.getVWGripPt());
                startToScale();
                //Publish start scaling
                    publishStartTransform();
                nullError = false;
            } else nullError = true;
//SCALING
        } else if (!sVirTool.isLocked() && !isMouseOverOtherTool() &&
                  (    (action == toolForm.ACT_MOUSE_GUIDEHANDLE_DRAGGED || 
                        action == VToolForm.ACT_MOUSE_SCALETOUCHHANDLE_DRAGGED)
                            && sVirTool.getGuiderMod() == GuideHandle.MOD_SCALE_XY)   ) 
        {
            //get new point
            if (toolForm.getVWGripPt()!= null && !nullError) 
            {
                currPt.set(toolForm.getVWGripPt());
                prepareScaleParms ();
                //accept new scale
                    scale(newScaleVec);
                //..and new translate (this special scaling is actually combination of scaling and translation)
                    translate (newTransVec);
                //Publish scaling (TG change)
                    publishTransforming();
                //... and scale and rotate Rotate switch handle
                    newTransVec.set(newScaleVec);
                    newTransVec.x*=a + sVirTool.getRotationSwitchHandle().getSize();
                    newTransVec.y*=a + sVirTool.getRotationSwitchHandle().getSize();
                    newTransVec.z=0;
                    tempTr.setIdentity();
                    tempTr.set(newTransVec);
                    double scale = Math.sqrt(Math.pow(newScaleVec.x, 2) + Math.pow(newScaleVec.y, 2));
                    tempTr.setScale(scale);
                    sVirTool.getRotationSwitchHandle().setTransform(tempTr);
                //...and scale Ztranslate touch handle...
                    newScaleVec.z=0.7*newScaleVec.y*a;
                    newScaleVec.x=1;
                    newScaleVec.y=1;
                    tempTr.setIdentity();
                    tempTr.setScale(newScaleVec);
                    sVirTool.getZTg().setTransform(tempTr);
                //... and not a null point
                    nullError = false;
            }else nullError = true;
             
//TURN ROT SPHERE ON 
        }else if (!sVirTool.isLocked() && action == VToolForm.ACT_MOUSE_SWITCH_SPHERE_ON_PRESSED) 
        {
            sVirTool.setVisibility(SlicerVirTool.VIS_ROT_SPHERE_ON);
            //Dulke
            Visibility v =(Visibility)sVirTool.getToolOperatorPanelList()[0];            
            if (v!=null) v.setVisibilityLevel(SlicerVirTool.VIS_ROT_SPHERE_ON);
//TURN ROT SPHERE OFF            
        } else if (!sVirTool.isLocked() && action == VToolForm.ACT_MOUSE_SWITCH_SPHERE_OFF_PRESSED) 
        {
            sVirTool.setVisibility(SlicerVirTool.VIS_HANDLES_ON);
            //Dulke
            Visibility v =(Visibility)sVirTool.getToolOperatorPanelList()[0];            
            if (v!=null) v.setVisibilityLevel(SlicerVirTool.VIS_HANDLES_ON);
        
//START ROTATING
        } else if (!sVirTool.isLocked() && action == VToolForm.ACT_MOUSE_ROTATE_HANDLE_PRESSED) 
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
            } else if (!sVirTool.isLocked() && !isMouseOverOtherTool()
                        &&action == VToolForm.ACT_MOUSE_ROTATE) 
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
                sVirTool.getRotTG().getTransform(currTr);
                currTr.mul(tempTr);
                tempTr.setIdentity();
                tempTr.rotY(dx * 0.01);
                currTr.mul(tempTr);                
                sVirTool.getRotTG().setTransform(currTr);
                sVirTool.getRotTG().getTransform(rotTr);
                //Publish rotation (TG change)
                    publishTransforming();
                setOnTheSphere(false);
            }else nullError = true;

//ON MOUSE RELEASE
        } else if (action == VToolForm.ACT_MOUSE_RELEASED ||
                    action == VToolForm.ACT_MOUSE_SCALETOUCHHANDLE_RELEASED ||
                    action == VToolForm.ACT_MOUSE_GUIDEHANDLE_RELEASED ||
                    action == VToolForm.ACT_MOUSE_TRANSLATE_TOUCHHANDLE_RELEASED ||
                    action == VToolForm.ACT_MOUSE_ROTATE_HANDLE_RELEASED 
                //Hm... 
           //     ||action == VToolForm.ACT_MOUSE_DRAGGED_HIT_OTHER_TOOL
                ) 
        {
            if (action == VToolForm.ACT_MOUSE_DRAGGED_HIT_OTHER_TOOL)
            {
                System.out.println("PRIJAVIO JE OTHER TOOL!!!");
                setMouseOverOtherTool(true);
            }
            sVirTool.setVisibility(oldVis);
            setOnTheSphere(false);//to start a new rotation
            //Publish end transform
            publishEndTransform();
            sVirTool.setLocked(false); 
        } 
        else if(action == toolForm.ACT_ON_INSTANCE_DELETE) 
        {
            super.OnInstanceDelete(toolForm, editor);    
        }
        else 
        {
            System.out.println("[ " + action + " ] no such action!");
            
        }
    }
    private void startToXYTranslate() 
    {
        if(sVirTool.getVisibility()!=SlicerVirTool.VIS_TRANSLATING_XY)
            //memorize old visibility so it could be revived after operation of translating is done
            //same thing is on transZ, scale, rotate
            oldVis = sVirTool.getVisibility();
        //set proper guider mod
        sVirTool.setGuiderMod(GuideHandle.MOD_TRANSLATE_XY);
            //setting visible nodes (turn Guider ON)
        sVirTool.setVisibility(SlicerVirTool.VIS_TRANSLATING_XY);
                                                                     //FIRST KILL TRANSLATION!
            sVirTool.getTransTG().getTransform(transTr);
            invertTransTr.invert(transTr);
            invertTransTr.transform(startPt);
                                                                     // THEN kill rotation
            sVirTool.getRotTG().getTransform(rotTr);
            invertRotTr.invert(rotTr);
            invertRotTr.transform(startPt);
            
        //It seems that it is not necessary to invert (kill) translation here, because if both start an current
        //points are translated, vector between them will still be the same
        //but, since we need to kill rotation to get proper XY coordinates it is actually necessary
        //to kill translation first to get proper coordinate system to invert rotation
        //Methode node.getLocalToWorld could be used here too, but be aware of the TranslateTG
        //and RotateTG order
        //Same thing is done during scaling and Z translation!
            
        sVirTool.getTransTG().getTransform(currTr);
        currTr.get(oldTransVec);
    }
    
    private void prepareXYTranslateParms() 
    {
        invertTransTr.transform(currPt);    // first kill translation
        invertRotTr.transform(currPt);      //kill rotation
        newTransVec.set(currPt);
        newTransVec.sub(startPt);
        //... new translate
        tempTr.setIdentity();
        newTransVec.z=0;
        rotTr.transform(newTransVec);     //revive rotation
    }
    
    private void translate (Vector3d newTransVec) 
    {
        tempTr.setIdentity(); 
        newTransVec.add(oldTransVec);
        tempTr.setTranslation(newTransVec);
        sVirTool.getTransTG().setTransform(tempTr);
    }
    
    private void startToZTranslate () 
    {
        if(sVirTool.getVisibility()!=SlicerVirTool.VIS_TRANSLATING_Z)
                oldVis = sVirTool.getVisibility();
        
        sVirTool.setGuiderMod(GuideHandle.MOD_TRANSLATE_Z);        //set proper guider mod            
        sVirTool.setVisibility(SlicerVirTool.VIS_TRANSLATING_Z);   //setting visible nodes (turn Guider ON)        
        sVirTool.getTransTG().getTransform(transTr);               //FIRST KILL TRANSLATION!
        invertTransTr.invert(transTr);
        invertTransTr.transform(startPt);                                                                 
        sVirTool.getRotTG().getTransform(rotTr);                   // THEN kill rotation
        invertRotTr.invert(rotTr);
        invertRotTr.transform(startPt);
        sVirTool.getTransTG().getTransform(currTr);
        currTr.get(oldTransVec);
    }
    
    private void prepareZTranslateParms()
    {
        invertTransTr.transform(currPt);   //invert old translation(kill translation)
        invertRotTr.transform(currPt);   //kill rotation
        newTransVec.set(currPt);
        newTransVec.sub(startPt);
        //... new translate
        tempTr.setIdentity();
        newTransVec.x=0;
        newTransVec.y=0;
        rotTr.transform(newTransVec);//revive rotation
    }
    
    private void startToScale() 
    {
                if(sVirTool.getVisibility()!=SlicerVirTool.VIS_SCALING)
                    oldVis = sVirTool.getVisibility();
            //setting visible nodes (turn Guider ON)
        sVirTool.setVisibility(SlicerVirTool.VIS_SCALING);
            //set proper guider mod
        sVirTool.setGuiderMod(GuideHandle.MOD_SCALE_XY);
     //STILL NO ROTATION!!! WATCH OUT!    
        //Rottation added 060617
                                                                 //FIRST KILL TRANSLATION!
        sVirTool.getTransTG().getTransform(transTr);
        invertTransTr.invert(transTr);
        invertTransTr.transform(startPt);
                                                                // THEN kill rotation
        sVirTool.getRotTG().getTransform(rotTr);
        invertRotTr.invert(rotTr);
        invertRotTr.transform(startPt);
        //END
        //DO THE SAME THING WITH currPt, BUT WATCH OUT!
        sVirTool.getTransTG().getTransform(currTr);
        currTr.get(currTransVec);
        sVirTool.getScaleTG().getTransform(currTr);
        currTr.getScale(currScaleVec);
        
        //RepVec is actually Point which should not be moved
        if (sVirTool.getHandleId() == ScaleTouchHandle.UPP_RIGHT_ID 
                || sVirTool.getHandleId() == ScaleTouchHandle.UPP_MID_ID) 
        {
            currScaleVec.scale(a);
            repVec.negate(currScaleVec);
        } 
        else if (sVirTool.getHandleId() == ScaleTouchHandle.UPP_LEFT_ID || 
                sVirTool.getHandleId() == ScaleTouchHandle.LEFT_MID_ID) 
        {
            currScaleVec.scale(a);
            repVec.x = currScaleVec.x;
            repVec.y = -currScaleVec.y;
//                System.out.println("reper: " + repVec);
        } 
        else if (sVirTool.getHandleId() == ScaleTouchHandle.DWN_LEFT_ID ||
                sVirTool.getHandleId() == ScaleTouchHandle.DWN_MID_ID) 
        {
            currScaleVec.scale(a);
            repVec.set(currScaleVec);
        } 
        else if (sVirTool.getHandleId() == ScaleTouchHandle.DWN_RIGHT_ID ||
                sVirTool.getHandleId() == ScaleTouchHandle.RIGHT_MID_ID) 
        {
            currScaleVec.scale(a);
            repVec.x = - currScaleVec.x;
            repVec.y = currScaleVec.y;
        } 
        repVec.z=0;
        //Getting prev translation rotation and scale
            sVirTool.getScaleTG().getTransform(currTr);
            currTr.getScale(oldScaleVec);
            sVirTool.getTransTG().getTransform(currTr);
            currTr.get(oldTransVec);
    }
    
    private void prepareScaleParms () 
    {
        //FIRST KILL TRANSLATION!
                invertTransTr.transform(currPt);
                invertRotTr.transform(currPt); //kill rotation
                //set "potential" scaling and seting new translation and scale to the old one
                //I say "potential" because it will be or will not be accepted depending on which scale hande is grabbed
                scaleX = (currPt.x - repVec.x)/ (startPt.x - repVec.x);            
                scaleY = (currPt.y - repVec.y)/ (startPt.y - repVec.y);
                
                newScaleVec.set(oldScaleVec);
                newTransVec.set(0d, 0d, 0d);
                
                if (sVirTool.getHandleId() == ScaleTouchHandle.UPP_RIGHT_ID) 
                {
                    //setting new scale pars
                    newScaleVec.x = (oldScaleVec.x * scaleX*a)>0.5?
                        (oldScaleVec.x * scaleX) : MIN_DIM/a; //Slicers minumum dimension is MIN_DIM
                    newScaleVec.y = (oldScaleVec.y * scaleY*a)>0.5?
                        (oldScaleVec.y * scaleY*a) : MIN_DIM/a;
                    //...and new translate pars
                    newTransVec.x = (newScaleVec.x - oldScaleVec.x)*a;
                    newTransVec.y = (newScaleVec.y - oldScaleVec.y)*a;
                } else if (sVirTool.getHandleId() == ScaleTouchHandle.UPP_MID_ID) 
                {
                    //setting new scale pars
                    newScaleVec.y = (oldScaleVec.y * scaleY*a)>0.5?
                        (oldScaleVec.y * scaleY*a) : MIN_DIM/a;
                    //...and new translate pars
                    newTransVec.y = (newScaleVec.y - oldScaleVec.y)*a;
                } else if (sVirTool.getHandleId() == ScaleTouchHandle.UPP_LEFT_ID) {
                    //setting new scale pars
                    newScaleVec.x = (oldScaleVec.x * scaleX*a)>0.5?
                        (oldScaleVec.x * scaleX) : MIN_DIM/a;
                    newScaleVec.y = (oldScaleVec.y * scaleY*a)>0.5?
                        (oldScaleVec.y * scaleY*a) : MIN_DIM/a;
                    //...and new translate pars
                    newTransVec.x = -(newScaleVec.x - oldScaleVec.x)*a;
                    newTransVec.y = (newScaleVec.y - oldScaleVec.y)*a;
                } else if (sVirTool.getHandleId() == ScaleTouchHandle.LEFT_MID_ID) {  
                    //setting new scale pars
                    newScaleVec.x = (oldScaleVec.x * scaleX*a)>0.5?
                        (oldScaleVec.x * scaleX) : MIN_DIM/a;
                    //...and new translate pars
                    newTransVec.x = -(newScaleVec.x - oldScaleVec.x)*a;
                    newTransVec.y = (newScaleVec.y - oldScaleVec.y)*a;
                } else if (sVirTool.getHandleId() == ScaleTouchHandle.DWN_LEFT_ID) {
                    //setting new scale pars
                    newScaleVec.x = (oldScaleVec.x * scaleX*a)>0.5?
                        (oldScaleVec.x * scaleX) : MIN_DIM/a;
                    newScaleVec.y = (oldScaleVec.y * scaleY*a)>0.5?
                        (oldScaleVec.y * scaleY*a) : MIN_DIM/a;
                    //...and new translate pars
                    newTransVec.x = -(newScaleVec.x - oldScaleVec.x)*a;
                    newTransVec.y = -(newScaleVec.y - oldScaleVec.y)*a;
                } else if (sVirTool.getHandleId() == ScaleTouchHandle.DWN_MID_ID) {
                    //setting new scale pars
                    newScaleVec.y = (oldScaleVec.y * scaleY*a)>0.5?
                        (oldScaleVec.y * scaleY*a) : MIN_DIM/a;
                    //...and new translate pars
                    newTransVec.y = -(newScaleVec.y - oldScaleVec.y)*a;
                } else if (sVirTool.getHandleId() == ScaleTouchHandle.DWN_RIGHT_ID) {
                    //setting new scale pars
                    newScaleVec.x = (oldScaleVec.x * scaleX*a)>0.5?
                        (oldScaleVec.x * scaleX) : MIN_DIM/a;
                    newScaleVec.y = (oldScaleVec.y * scaleY*a)>0.5?
                        (oldScaleVec.y * scaleY*a) : MIN_DIM/a;
                    //...and new translate pars
                    newTransVec.x = (newScaleVec.x - oldScaleVec.x)*a;
                    newTransVec.y = -(newScaleVec.y - oldScaleVec.y)*a;
                } else if (sVirTool.getHandleId() == ScaleTouchHandle.RIGHT_MID_ID) {
                    //setting new scale pars
                    newScaleVec.x = (oldScaleVec.x * scaleX*a)>0.5?
                        (oldScaleVec.x * scaleX) : MIN_DIM/a;
                    //...and new translate pars
                    newTransVec.x = (newScaleVec.x - oldScaleVec.x)*a;
                }
                rotTr.transform(newTransVec);// revive rotation
    }
    
    private void scale (Vector3d scaleVec) 
    {
        tempTr.setIdentity();
        tempTr.setScale(scaleVec);
        sVirTool.getScaleTG().setTransform(tempTr);   
    }
    
    private void startToRotate () 
    {
        sVirTool.getRotTG().getTransform(rotTr);
        sVirTool.getTransTG().getTransform(currTr);
        currTr.get(oldTransVec);
        startVec.sub(oldTransVec);
        if(sVirTool.getVisibility()!=SlicerVirTool.VIS_ROTATING)
            oldVis = sVirTool.getVisibility();
            //setting visible nodes (turn Guider ON)
        sVirTool.setVisibility(SlicerVirTool.VIS_ROTATING);
        startPt.sub(oldTransVec);
        setOnTheSphere(true);
    }
    private void rotate (AxisAngle4d newRotAxisAngle)
    {
        currTr.setIdentity();
        currTr.setRotation(newRotAxisAngle);
        invertRotTr.invert(currTr);
        currTr.mul(rotTr);
        sVirTool.getRotTG().setTransform(currTr);
    }

    private void refresh()
    {
        BehaviorObserver observer = sVTool.getVirToolRef().getBhvObserver();                   
        observer.update(sVTool.getVirToolRef().getVToolOperatorsFormRef());            
        observer.update(sVTool.getVirToolRef().getVUIToolFormRef());
    }
    
    public int getAction() {
        return action;
    }

    private void setAction(int action) {
        this.action = action;
    }

    public Point3d getCurrPt() {
        return currPt;
    }

    private void setOnTheSphere(boolean b) 
    {
        onTheSphere = b;
    }
   private void publishStartTransform () 
   {
       ((SlicerVTool)getVirToolRef().getVToolRef()).getPDispatcher()
                     .publish((SlicerVTool)getVirToolRef().getVToolRef(),SGEvent.EVT_START_MOVE_TOOL);
   }
   private void publishTransforming() 
   {
       ((SlicerVTool)getVirToolRef().getVToolRef()).getPDispatcher()
                     .publish((SlicerVTool)getVirToolRef().getVToolRef(),SGEvent.EVT_MOVE_TOOL);
   }
   private void publishEndTransform () 
   {
               ((SlicerVTool)getVirToolRef().getVToolRef()).getPDispatcher()
                     .publish((SlicerVTool)getVirToolRef().getVToolRef(),SGEvent.EVT_STOP_MOVE_TOOL);
   }

    private boolean isMouseOverOtherTool() {
        return mouseOverOtherTool;
    }

    private void setMouseOverOtherTool(boolean mouseOverOtherTool) {
        this.mouseOverOtherTool = mouseOverOtherTool;
    }
}
