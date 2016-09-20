package org.sgJoe.graphics.behaviors;

import org.apache.log4j.Logger;

import com.sun.j3d.utils.picking.PickResult;
import java.awt.AWTEvent;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import javax.media.j3d.*;
import org.sgJoe.exception.SGPluginException;
import org.sgJoe.graphics.*;
import org.sgJoe.tools.*;
import org.sgJoe.tools.decorators.*;
import org.sgJoe.tools.interfaces.*;


/*
 * Descritpion for CustomPickEventBasedBehavior.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: April 17, 2006  10:16 PM   $
 */

public class CustomPickEventBasedBehavior extends CustomBehavior implements GraphicsConstants {
    
    private static Logger logger = Logger.getLogger(CustomPickEventBasedBehavior.class);

    private WakeupCondition condition = null;
    private VirTool currVirToolRef = null;
    private VTool currVToolRef = null;
    private VToolHandle currVToolHandleRef = null;
    private int x, y, x_last, y_last;
    private BaseSceneGraph graph = null;
    private boolean reset = false;    
    
    public void reset() {
        reset = true;
    }
    
    public void initialize() {
        wakeupOn(condition);    
    }

   
    public CustomPickEventBasedBehavior(BaseSceneGraph graph) {
      this.graph = graph;
      WakeupCriterion[] criteria = new WakeupCriterion[3];
      criteria[0] = new WakeupOnAWTEvent(MouseEvent.MOUSE_DRAGGED);
      criteria[1] = new WakeupOnAWTEvent(MouseEvent.MOUSE_PRESSED);
      criteria[2] = new WakeupOnAWTEvent(MouseEvent.MOUSE_RELEASED);
      condition = new WakeupOr(criteria); 
    }        
    
    public void processStimulus(Enumeration criteria) {
        System.out.println("ps CustomPickEventBasedBehaviora");
        WakeupCriterion wakeup;
        AWTEvent[] event;
        MouseEvent evt;
    
        int eventId;
        while (criteria.hasMoreElements()) {
            wakeup = (WakeupCriterion) criteria.nextElement();
            if (wakeup instanceof WakeupOnAWTEvent) {
                event = ((WakeupOnAWTEvent)wakeup).getAWTEvent();
                evt = (MouseEvent)event[event.length - 1]; // get most recent event
                eventId = evt.getID();
        
                // make sure the left mouse button is pressed and nothing else
                if ((!evt.isMetaDown()) && (!evt.isAltDown())) 
                {
        
                    // mouse pressed, get the selected shape's transform group
                    if (eventId == MouseEvent.MOUSE_PRESSED) 
                    {
                        x = evt.getX();
                        y = evt.getY();
                        x_last = x;
                        y_last = y;
            
                        // use the BaseSceneGraph's picking method
                        PickResult pickResult = graph.getClosestIntersection(x, y);
                    
                         if (pickResult != null) {                   
                            
                            TransformGroup tg = getLastTransformGroup(pickResult, false); 
                        
                            // Only two types may be selected.....
                            if(tg instanceof VTool) {
                                currVToolRef = (VTool) tg;
                                currVirToolRef = currVToolRef.getVirToolRef();
                            } else if(tg instanceof VToolHandle) {
                                currVToolHandleRef = (VToolHandle) tg;
                                currVirToolRef = currVToolHandleRef.getVirToolRef();
                            }
                                                                                                      
                            VToolForm vToolForm = currVirToolRef.getVToolFormRef();
                            VToolPlugin vToolPlugin = currVirToolRef.getVToolPluginRef();
                            VUIToolForm vuiToolForm = currVirToolRef.getVUIToolFormRef();
                            VToolOperatorsForm vToolOperatorsForm = currVirToolRef.getVToolOperatorsFormRef();
                            
                            vToolForm.reset(null);
                            
                            vToolForm.setViewSource(graph.getViewInFocus());
                            // --> vToolForm.setVToolRef(currVToolRef);
                            vToolForm.setCurrX(x);
                            vToolForm.setCurrY(y);
                            vToolForm.setPrevX(x_last);
                            vToolForm.setPrevY(y_last);
                            vToolForm.setAction(vToolForm.ACT_MOUSE_PRESSED);
                                                            
                            // --> vuiToolForm.setVToolRef(currVToolRef);
                            // --> vToolOperatorsForm.setVToolRef(currVToolRef);
                            
//                            if(vToolForm.getBehaviorObserverRef() == null) {
//                                vToolForm.setBehaviorObserverRef(behaviorObserver);
//                            }
//                            if(vToolForm.getVUIToolFormRef() == null) {
//                                vToolForm.setVUIToolFormRef(vuiToolForm);
//                            }
//                            if(vToolForm.getVToolOperatorsFormRef() == null) {
//                                vToolForm.setVToolOperatorsFormRef(vToolOperatorsForm);
//                            }                            
//                            if(vuiToolForm.getSGEditor() == null) {
//                                vuiToolForm.setSGEditor(graph.getParentEditor());
//                            }
                                           
                            try {

                                vToolPlugin.performAction(vToolForm, graph.getParentEditor(), null);
                                
                            } catch (SGPluginException ex) {
                                ex.printStackTrace();
                            }
                            
                        }
                    }
                    // mouse dragged, use the mouse movement to translate the picked shape accordingly
                    else if (eventId == MouseEvent.MOUSE_DRAGGED && currVirToolRef != null) {
                        int dx, dy;
                        x = evt.getX();
                        y = evt.getY();
                        dx = x - x_last;
                        dy = y - y_last;
            
                        if ((!reset) && ((Math.abs(dy) < 50) && (Math.abs(dx) < 50))) {
                        
                            VToolForm vToolForm = currVirToolRef.getVToolFormRef();
                            VToolPlugin vToolPlugin = currVirToolRef.getVToolPluginRef();
                            VUIToolForm vuiToolForm = currVirToolRef.getVUIToolFormRef();

                            vToolForm.reset(null);

                            vToolForm.setViewSource(graph.getViewInFocus());
                            // --> vToolForm.setVToolRef(currVToolRef);
                            vToolForm.setCurrX(x);
                            vToolForm.setCurrY(y);
                            vToolForm.setPrevX(x_last);
                            vToolForm.setPrevY(y_last);                            
                            
                            if(currVToolHandleRef != null) {
                                vToolForm.setAction(currVToolHandleRef.getAction());
                            } else {
                                vToolForm.setAction(vToolForm.ACT_MOUSE_DRAGGED);
                            }
                            
                            // --> vuiToolForm.setVToolRef(currVToolRef);

//                            if(vToolForm.getBehaviorObserverRef() == null) {
//                                vToolForm.setBehaviorObserverRef(behaviorObserver);
//                            }
//                            if(vToolForm.getVUIToolFormRef() == null) {
//                                vToolForm.setVUIToolFormRef(vuiToolForm);
//                            }
//                            if(vuiToolForm.getSGEditor() == null) {
//                                vuiToolForm.setSGEditor(graph.getParentEditor());
//                            }

                            try {

                                vToolPlugin.performAction(vToolForm, graph.getParentEditor(), null);

                            } catch (SGPluginException ex) {
                                ex.printStackTrace();
                            }
              
                        } else {
                            reset = false;
                        }

                        x_last = x;
                        y_last = y;
                    }
                    // when the mouse gets released, update context menues
                    else if(eventId == MouseEvent.MOUSE_RELEASED) {
                        x = evt.getX();
                        y = evt.getY();

                        VToolForm vToolForm = currVirToolRef.getVToolFormRef();
                        VToolPlugin vToolPlugin = currVirToolRef.getVToolPluginRef();
                        VUIToolForm vuiToolForm = currVirToolRef.getVUIToolFormRef();
                            
                        vToolForm.reset(null);
                            
                        vToolForm.setViewSource(graph.getViewInFocus());
                        // --> vToolForm.setVToolRef(currVToolRef);
                        vToolForm.setCurrX(x);
                        vToolForm.setCurrY(y);
                        vToolForm.setPrevX(x_last);
                        vToolForm.setPrevY(y_last);                        
                        vToolForm.setAction(vToolForm.ACT_MOUSE_RELEASED);
                           
                        // --> vuiToolForm.setVToolRef(currVToolRef);
                            
//                        if(vToolForm.getBehaviorObserverRef() == null) {
//                            vToolForm.setBehaviorObserverRef(behaviorObserver);
//                        }
//                        if(vToolForm.getVUIToolFormRef() == null) {
//                            vToolForm.setVUIToolFormRef(vuiToolForm);
//                        }
//                        if(vuiToolForm.getSGEditor() == null) {
//                            vuiToolForm.setSGEditor(graph.getParentEditor());
//                        }
                                           
                        try {

                            vToolPlugin.performAction(vToolForm, graph.getParentEditor(), null);

                        } catch (SGPluginException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
        wakeupOn(condition);
    }           
}