package org.sgJoe.graphics.behaviors;

import com.sun.j3d.utils.picking.*;
import java.awt.AWTEvent;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import javax.media.j3d.*;
import javax.vecmath.Point3d;
import org.apache.log4j.Logger;
import org.sgJoe.exception.*;
import org.sgJoe.graphics.*;
import org.sgJoe.graphics.behaviors.*;
import org.sgJoe.graphics.event.SGEvent;
import org.sgJoe.graphics.event.ToolSelectedPublisher;
import org.sgJoe.graphics.event.mouse.MouseDraggedPublisher;
import org.sgJoe.logic.*;
import org.sgJoe.tools.*;
import org.sgJoe.tools.decorators.*;
import org.sgJoe.tools.interfaces.*;

/*
 * Descritpion for CustomPickSensorBehavior.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: April 3, 2006  8:39 PM  $
 */

public class CustomPickSensorBehavior extends CustomBehavior implements GraphicsConstants 
{
    
    private static Logger logger = Logger.getLogger(CustomPickSensorBehavior.class);
    
    private WakeupCondition condition = null;
    private VTool vTool = null;
    private VToolHandle vToolHandle = null; 
    private int x, y, x_last, y_last;
   
    private BaseSceneGraph graph = null;
    private boolean reset = false;
    
    private TransformGroup lastTG = null;
    
    // adendum 
    PickResult[] pickResultArray = null;    
    
    public CustomPickSensorBehavior(BaseSceneGraph graph) 
    {
      this.graph = graph;
//      WakeupCriterion[] criteria = new WakeupCriterion[6];
      WakeupCriterion[] criteria = new WakeupCriterion[4];
      criteria[0] = new WakeupOnAWTEvent(MouseEvent.MOUSE_DRAGGED);
      criteria[1] = new WakeupOnAWTEvent(MouseEvent.MOUSE_PRESSED);
      criteria[2] = new WakeupOnAWTEvent(MouseEvent.MOUSE_RELEASED);
      criteria[3] = new WakeupOnAWTEvent(MouseEvent.MOUSE_CLICKED);
//      criteria[3] = new WakeupOnAWTEvent(MouseEvent.MOUSE_ENTERED);
//      criteria[4] = new WakeupOnAWTEvent(MouseEvent.MOUSE_EXITED);
//      criteria[5] = new WakeupOnAWTEvent(MouseEvent.MOUSE_MOVED);
      condition = new WakeupOr(criteria); 
    }    

    public void reset() 
    {
        x = x_last;
        y = y_last;
        this.vTool = null;
        this.vToolHandle = null;
        reset = true;
    }
    
    public void initialize() {
        wakeupOn(condition);    
    }

    public void setNavigationPlane(int plane) {

    }
    public void setDirection(int direction) {

    }    
    
    /**
     * Process a stimulus meant for this behavior. This method is invoked 
     * if the Behavior's wakeup criteria are satisfied and an active 
     * ViewPlatform's activation volume intersects with the Behavior's 
     * scheduling region.
     * <br><br>
     * NOTE: Applications should not call this method. It is called by the 
     * Java 3D behavior scheduler.
     *
     * @param criteria - an enumeration of triggered wakeup criteria for 
     *                   this behavior.
     */
    public void processStimulus(Enumeration criteria) 
    {
        
        WakeupCriterion wakeup;
        AWTEvent[] event;
        MouseEvent evt;
    
        int eventId;
        while (criteria.hasMoreElements()) 
        {
            wakeup = (WakeupCriterion) criteria.nextElement();
            if (wakeup instanceof WakeupOnAWTEvent) 
            {
                event = ((WakeupOnAWTEvent)wakeup).getAWTEvent();
                evt = (MouseEvent)event[event.length - 1]; // get most recent event
                eventId = evt.getID();
        
                // make sure the left mouse button is pressed and nothing else
                if ((!evt.isMetaDown()) && (!evt.isAltDown())) 
                {
                    
                    if(eventId == MouseEvent.MOUSE_ENTERED) 
                    {
                        // not subscriber at this moment
                        System.out.println("MOUSE_ENTERED");
                    } else if (eventId == MouseEvent.MOUSE_EXITED) 
                    {
                        // not subscriber at this moment
                        System.out.println("MOUSE_EXITED");
                    } else if (eventId == MouseEvent.MOUSE_MOVED) 
                    {
                        // not subscriber at this moment
                        System.out.println("MOUSE_MOVED");
                    } 
                    else if(eventId == MouseEvent.MOUSE_CLICKED) 
                    {
                         //System.out.println("CustomPickSensorBehavior -> eventId == MouseEvent.MOUSE_PRESSED");
                        x = evt.getX();
                        y = evt.getY();
                        x_last = x;
                        y_last = y;
            
                        // use the BaseSceneGraph's picking method
                        PickResult pickResult = graph.getClosestIntersection(x, y);
                                            
                        
                        if (pickResult != null) 
                        {                   
                            
                            TransformGroup tg = getLastTransformGroup(pickResult, false); 
                            
                            ///////////////////////////
                            PickIntersection pickInter = pickResult.getIntersection(0);                                                   
                            ///////////////////////////
                            if(tg instanceof VToolTG) 
                            {
                                VToolTG vtTG = (VToolTG)tg;
                                vTool = vtTG.getVToolRef();
                                vTool.setAction4OnMouseClicked();
                                vToolHandle = null;
                            } 
                            else if(tg instanceof VTool) 
                            {
                                vTool = (VTool) tg;
                                vTool.setAction4OnMouseClicked();
                                vToolHandle = null;
                            } 
                            else if(tg instanceof VToolHandle) 
                            {
                                vToolHandle = (VToolHandle) tg;
                                vToolHandle.onMouseClicked();
                                vTool = vToolHandle.getVirToolRef().getVToolRef();
                            }
                            
                                                        
                            VirTool instance = vTool.getVirToolRef();
                                    // --> graph.getParentEditor().getVirToolMap().get(vTool.getToolUID());                                                           
                                                            
                            VToolForm vToolForm = instance.getVToolFormRef();
                            VToolPlugin vToolPlugin = instance.getVToolPluginRef();
                            VUIToolForm vuiToolForm = instance.getVUIToolFormRef();
                            VToolOperatorsForm vToolOperatorsForm = instance.getVToolOperatorsFormRef();
                            
                            vToolForm.reset(null);
                            
                            vToolForm.setLGripPt(pickInter.getPointCoordinates());
                            vToolForm.setVWGripPt(pickInter.getPointCoordinatesVW());
                                                        
                            vToolForm.setViewSource(graph.getViewInFocus());
                            vToolForm.setCurrX(x);
                            vToolForm.setCurrY(y);
                            vToolForm.setPrevX(x_last);
                            vToolForm.setPrevY(y_last);
                            
                            if(vToolHandle != null) 
                            {
                                vToolForm.setAction(vToolHandle.getAction());
                            } 
                            else 
                            {
                                vToolForm.setAction(vTool.getAction());
                            }
                                
                                           
                            try 
                            {

                                vToolPlugin.performAction(vToolForm, graph.getParentEditor(), null);
                                
                            } catch (SGPluginException ex) {
                                ex.printStackTrace();
                            }
                            
                        }
                    }
                    // mouse pressed, get the selected shape's transform group
                    else if (eventId == MouseEvent.MOUSE_PRESSED) 
                    {
                        //System.out.print("Mouse pressed");
                        // --> System.out.println("CustomPickSensorBehavior -> eventId == MouseEvent.MOUSE_PRESSED");
                        x = evt.getX();
                        y = evt.getY();
                        x_last = x;
                        y_last = y;
            
                        // use the BaseSceneGraph's picking method
                        PickResult pickResult = graph.getClosestIntersection(x, y);
                                            
                        
                        if (pickResult != null) 
                        {                          
                            TransformGroup tg = getLastTransformGroup(pickResult, false); 

//                            if(tg instanceof ToolSelectedPublisher) {
//                                ToolSelectedPublisher publisher = (ToolSelectedPublisher) tg;
//                                publisher.getPDispatcher().publish(publisher, SGEvent.EVT_TOOL_SELECTED);
//                            }
                            
                            ///////////////////////////
                            PickIntersection pickInter = pickResult.getIntersection(0);                                                   
                            ///////////////////////////
                            if(tg instanceof VToolTG) 
                            {
                                VToolTG vtTG = (VToolTG)tg;
                                vTool = vtTG.getVToolRef();
                                vTool.setAction4OnMousePressed();
                                vToolHandle = null;
                            } 
                            else if(tg instanceof VTool) 
                            {
                                vTool = (VTool) tg;
                                vTool.setAction4OnMousePressed();
                                vToolHandle = null;
                            } 
                            else if(tg instanceof VToolHandle) 
                            {
                                vToolHandle = (VToolHandle) tg;
                                vToolHandle.onMousePressed();
                                vTool = vToolHandle.getVirToolRef().getVToolRef();
                            }
                            
                                                        
                            VirTool instance = vTool.getVirToolRef();
                                    // --> graph.getParentEditor().getVirToolMap().get(vTool.getToolUID());
                                                           
                            instance.getBhvObserver().setFocusOnTool(instance);
                            VirTool stackTool = graph.getParentEditor().getVirToolStack().peek();
                            if(stackTool != null) 
                            {
                                if(!stackTool.equals(instance)) 
                                {
                                    graph.getParentEditor().getVirToolStack().push(instance);
                                }
                            }
                            else 
                            {
                               graph.getParentEditor().getVirToolStack().push(instance);
                            }
                            
                                                            
                            VToolForm vToolForm = instance.getVToolFormRef();
                            VToolPlugin vToolPlugin = instance.getVToolPluginRef();
                            VUIToolForm vuiToolForm = instance.getVUIToolFormRef();
                            VToolOperatorsForm vToolOperatorsForm = instance.getVToolOperatorsFormRef();
                            
                            vToolForm.reset(null);
                            
                            vToolForm.setLGripPt(pickInter.getPointCoordinates());
                            vToolForm.setVWGripPt(pickInter.getPointCoordinatesVW());
                                                        
                            vToolForm.setViewSource(graph.getViewInFocus());
                            vToolForm.setCurrX(x);
                            vToolForm.setCurrY(y);
                            vToolForm.setPrevX(x_last);
                            vToolForm.setPrevY(y_last);
                            
                            if(vToolHandle != null) 
                            {
                                vToolForm.setAction(vToolHandle.getAction());
                            }
                            else 
                            {
                                vToolForm.setAction(vTool.getAction());
                            }                                
                                           
                            try {

                                vToolPlugin.performAction(vToolForm, graph.getParentEditor(), null);
                                
                            } catch (SGPluginException ex) {
                                ex.printStackTrace();
                            }
                            
                        }
                    }
                    // mouse dragged, use the mouse movement to translate the picked shape accordingly
                    else if (eventId == MouseEvent.MOUSE_DRAGGED) 
                    {
                        //System.out.println("Mouse dragged.");
                        int dx, dy;
                        x = evt.getX();
                        y = evt.getY();
                        dx = x - x_last;
                        dy = y - y_last;
                                                
                        if((vTool != null || vToolHandle != null)) 
                        {
                            // direct tool or vtoolhandle handling
                            if ((!reset) && (Math.abs(dy) < 50 || Math.abs(dx) < 50 )) 
                            {
                                // use the BaseSceneGraph's picking method
                                PickResult pickResult = graph.getClosestIntersection(x, y);

                                VirTool instance = vTool.getVirToolRef();
                                
                                VToolForm vToolForm = instance.getVToolFormRef();
                                VToolPlugin vToolPlugin = instance.getVToolPluginRef();
                                VUIToolForm vuiToolForm = instance.getVUIToolFormRef();

                                vToolForm.reset(null);

                                vToolForm.setViewSource(graph.getViewInFocus());
                                vToolForm.setCurrX(x);
                                vToolForm.setCurrY(y);
                                vToolForm.setPrevX(x_last);
                                vToolForm.setPrevY(y_last);                            

                                if(pickResult != null) 
                                {

                                    TransformGroup tg = getLastTransformGroup(pickResult, false); 
                                
                                
                                    if(tg instanceof VTool) 
                                    {
                                        VTool tmpVTool = (VTool) tg;
                                        if(!vTool.equals(tmpVTool)) 
                                        {
                                            vToolForm.setAction(vToolForm.ACT_MOUSE_DRAGGED_HIT_OTHER_TOOL);
                                            //------------------------------------------
                                            try 
                                            {

                                                vToolPlugin.performAction(vToolForm, graph.getParentEditor(), null);

                                            } 
                                            catch (SGPluginException ex) 
                                            {
                                            
                                                ex.printStackTrace();
                                            
                                            }                                        
                                            //------------------------------------------
                                            wakeupOn(condition);
                                            return;
                                        }
                                    } 
                                    else if(tg instanceof VToolHandle) 
                                    {
                                        VToolHandle tmpVToolHandle = (VToolHandle) tg; 
                                        if(!vTool.equals(tmpVToolHandle.getVirToolRef().getVToolRef())) 
                                        {
                                            vToolForm.setAction(vToolForm.ACT_MOUSE_DRAGGED_HIT_OTHER_TOOL);
                                            //------------------------------------------
                                            try 
                                            {

                                                vToolPlugin.performAction(vToolForm, graph.getParentEditor(), null);

                                            } catch (SGPluginException ex) 
                                            {
                                            
                                                ex.printStackTrace();
                                            
                                            }                                        
                                            //------------------------------------------                                        
                                            wakeupOn(condition);
                                            return;
                                        }
                                    }

                                    ///////////////////////////
                                    PickIntersection pickInter = pickResult.getIntersection(0);                                                   
                                    ///////////////////////////

                                    if(tg instanceof VToolHandle) 
                                    {
                                        if(vToolHandle == null) 
                                        {
                                            vToolHandle = (VToolHandle) tg;
                                            vTool = vToolHandle.getVirToolRef().getVToolRef();                                    
                                        } else if(!vToolHandle.equals(tg)) 
                                        {
                                            vToolHandle = (VToolHandle) tg;
                                        }
                                    } 
                                    else if(tg instanceof VTool) 
                                    {
                                        VTool tmpVTool = (VTool) tg;
                                        if(vTool.equals(tmpVTool)) 
                                        {
                                            vTool.setAction4OnMouseDragged();
                                        }
                                    }
                      
                                    VirTool stackTool = graph.getParentEditor().getVirToolStack().peek();
                                    if(stackTool != null) 
                                    {
                                        if(!stackTool.equals(vTool.getVirToolRef())) 
                                        {
                                            //graph.getParentEditor().getVirToolStack().push(instance);
                                            return;
                                        }
                                    }                                
                                    
                                    vToolForm.setViewingDistance(pickInter.getDistance());
                                    vToolForm.setLGripPt(pickInter.getPointCoordinates());
                                    vToolForm.setVWGripPt(pickInter.getPointCoordinatesVW());                                
                                }
                                else 
                                {
                                    vToolForm.setLGripPt(null);
                                    vToolForm.setVWGripPt(null);                                                                
                                }

                                if(vToolHandle != null) 
                                {
                                    vToolHandle.onMouseDragged();
                                    vToolForm.setAction(vToolHandle.getAction());
                                } 
                                else 
                                {
                                    vToolForm.setAction(vTool.getAction());
                                }

                                try 
                                {
                                    vToolPlugin.performAction(vToolForm, graph.getParentEditor(), null);

                                } 
                                catch (SGPluginException ex) 
                                {
                                    ex.printStackTrace();
                                }

                            } 
                            else 
                            {
                                reset = false;
                            }
                        }
                                    
                        x_last = x;
                        y_last = y;
                    }
                    // when the mouse gets released, update context menues
                    else if(!reset && eventId == MouseEvent.MOUSE_RELEASED && (vTool != null || vToolHandle !=null )) 
                    {
                        // --> System.out.println("MouseEvent.MOUSE_RELEASED");
                        x = evt.getX();
                        y = evt.getY();

                        // this is some bug found in during normal run time execution
                        if(vToolHandle != null) 
                        {
                            vToolHandle.onMouseReleased();
                            vTool = vToolHandle.getVirToolRef().getVToolRef();
                        } 
                        else if(vTool != null) 
                        {
                            vTool.setAction4OnMouseReleased();
                        }
                            
                        VirTool instance = vTool.getVirToolRef();
                                // --> graph.getParentEditor().getVirToolMap().get(vTool.getToolUID());                        
         
                        VToolForm vToolForm = instance.getVToolFormRef();
                        VToolPlugin vToolPlugin = instance.getVToolPluginRef();
                        VUIToolForm vuiToolForm = instance.getVUIToolFormRef();
                            
                        vToolForm.reset(null);
                            
                        vToolForm.setViewSource(graph.getViewInFocus());
                        vToolForm.setCurrX(x);
                        vToolForm.setCurrY(y);
                        vToolForm.setPrevX(x_last);
                        vToolForm.setPrevY(y_last);     
                        if(vToolHandle != null) 
                        {
                            vToolForm.setAction(vToolHandle.getAction());
                        } 
                        else 
                        {
                            vToolForm.setAction(vTool.getAction());
                        }
                        
                           
                        try {

                            vToolPlugin.performAction(vToolForm, graph.getParentEditor(), null);

                        } catch (SGPluginException ex) {
                            ex.printStackTrace();
                        }
                        vTool = null;
                        vToolHandle = null;
                    }
                    
                    // -> vTool = null;
                    // --> vToolHandle = null;
                }
            }
        }
        wakeupOn(condition);
    }       
}