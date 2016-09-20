
package org.sgJoe.graphics.behaviors;

import com.sun.j3d.utils.behaviors.mouse.MouseBehavior;
import java.util.ArrayList;
import java.util.Enumeration;

import java.awt.*;
import java.awt.event.*;
import javax.media.j3d.*;
import javax.vecmath.*;

import org.apache.log4j.Logger;
import org.sgJoe.graphics.event.EventDispatcher;
import org.sgJoe.graphics.event.SGEvent;
import org.sgJoe.graphics.event.view.SGEventView;
import org.sgJoe.graphics.event.view.ViewEventPublisher;


/*
 * Descritpion for NavigateStudyRollBehavior.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: May 15, 2006  11:27 AM  $
 */

public class NavigateStudyPlaneBehavior extends SGMouseBehaviour implements ViewEventPublisher {
    
    private static Logger logger = Logger.getLogger(NavigateStudyPlaneBehavior.class);
    
    private static long counter = 0;
  
    private double x_angle = 0;
    private double y_angle = 0;
    private double z_angle = 0;
    private double x_factor = .01;
    private double y_factor = .01;
    private double z_factor = .04;
    
    private int dx = 0;
    private int dy = 0;
  
    private Vector3f focusPoint = new Vector3f(0f, 0f, 0f);
    private double focusDistance = 20;
    
    private Matrix3f mat = new Matrix3f();
    private Transform3D temp1 = new Transform3D();
    private Transform3D temp2 = new Transform3D();
    private Vector3d translation = new Vector3d();
        
    private Vector3f directionVector = new Vector3f();    
      
    private double zoomMul = 0.25;
    private double moveMul = 0.025;
  
    private long myID = -1;
    
    // eventpublisher
    private EventDispatcher dispatcher;
    private View viewRef;
    /**
     * Creates a custom orbit behavior given the transform group.
     * @param transformGroup The transformGroup to operate on.
     */
    public NavigateStudyPlaneBehavior(Component c) {
        super(c, 0);
        this.myID = ++counter;
        this.setPDispatcher(EventDispatcher.getDispatcher());
        // --> System.out.println("[myID=" + myID + "]" + " CustomOrbitBehaviour constructor ");
    }
    
    /**
     * Initialize this behavior.
     */
    public void initialize() {
        super.initialize();
        x_angle = 0;
        y_angle = 0;
        if ((flags & INVERT_INPUT) == INVERT_INPUT) {
            invert = true;
            x_factor *= -1;
            y_factor *= -1;
            z_factor *= -1;
        }
        // --> System.out.println("[myID=" + myID +"]" + "initialize");
    }
  
    /**
     * Return the x-axis movement multipler.
     **/
    public double getXFactor() {
        return x_factor;
    }
  
    /**
     * Return the y-axis movement multipler.
     **/
    public double getYFactor() {
        return y_factor;
    }
  
  
    /**
     * Set the x-axis amd y-axis movement multipler with factor.
     **/
    public void setFactor( double factor) {
        x_factor = y_factor = factor;
    }
  
    /**
     * Set the x-axis amd y-axis movement multipler with xFactor and yFactor
     * respectively.
     **/
    public void setFactor( double xFactor, double yFactor) {
        x_factor = xFactor;
        y_factor = yFactor;
    }
  
    public void setXAngle(double angle) {
        x_angle = angle;
    }
  
    public void setYAngle(double angle) {
        y_angle = angle;
    }
  
    public void setZAngle(double angle) {
        z_angle = angle;
    }    
    public void setFocusPoint(Vector3f point) {
        focusPoint = point;
    }
  
    public void setFocusDistance(double distance) {
        focusDistance = distance;
    }
 
 
    public void processStimulus(Enumeration criteria) {
        WakeupCriterion wakeup;
        AWTEvent[] events;
        MouseEvent evt;
        
        // --> System.out.println("processStimulus " + myID);
        while (criteria.hasMoreElements()) {
            wakeup = (WakeupCriterion) criteria.nextElement();
            if (wakeup instanceof WakeupOnAWTEvent) {
                events = ((WakeupOnAWTEvent)wakeup).getAWTEvent();
                if (events.length > 0) {
                    evt = (MouseEvent) events[events.length-1];
                    // --> System.out.println("[myID=" + myID + "]" + "wakeup instanceof WakeupOnAWTEvent");
                    doProcess(evt);
                }
            } else if (wakeup instanceof WakeupOnBehaviorPost) {
                while (true) { 
                    // access to the queue must be synchronized
                    synchronized (mouseq) {
                        if (mouseq.isEmpty()) {
                            break;
                        }
                        evt = (MouseEvent)mouseq.remove(0);
                        // consolidate MOUSE_DRAG events
                        while ((evt.getID() == MouseEvent.MOUSE_DRAGGED) && 
                                !mouseq.isEmpty() && 
                                (((MouseEvent)mouseq.get(0)).getID() == MouseEvent.MOUSE_DRAGGED)) {evt = (MouseEvent)mouseq.remove(0);
                        }
                    }
                    // --> System.out.println("--------------------------------------------------------------");
                    // --> System.out.println("[myID=" + myID + "]" +"[BEFORE][doProcess(evt)]" +"[wakeup instanceof WakeupOnBehaviorPost]");
                    // --> stdPrintInternalState();
                    doProcess(evt);
                    // --> System.out.println("[myID=" + myID + "]" +"[AFTER][doProcess(evt)]" +"[wakeup instanceof WakeupOnBehaviorPost]");
                    // --> stdPrintInternalState();
                    // --> System.out.println("--------------------------------------------------------------");
                }
            }
      
        }
        wakeupOn(mouseCriterion);
    }
  
    /**
     * Process the specific mouse event. This is just an internal method.
     *
     * @param evt The mouse event.
     */
    void doProcess(MouseEvent evt) {
        int id;
       
        processMouseEvent(evt);
    
        if (((buttonPress)&&((flags & MANUAL_WAKEUP) == 0)) ||
            ((wakeUp)&&((flags & MANUAL_WAKEUP) != 0))) {
            id = evt.getID();
            
            if ((id == MouseEvent.MOUSE_DRAGGED) && !evt.isMetaDown() && !evt.isAltDown()) {
                x = evt.getX();
                y = evt.getY();
        
                dx = x - x_last;
                dy = y - y_last;
        
                if (!reset) {
                    // adjust angles and control if their values are valid

                    if(Math.abs(dx) > 0) {
                        y_angle = y_angle - dx * x_factor;
                        if (y_angle >= 2 * Math.PI) {
                            y_angle = (y_angle - 2 * Math.PI);
                        }
                        if (y_angle < 0) {
                            y_angle = (y_angle + 2 * Math.PI);
                        }                        
                    } else {
                        dx = 0;
                    }
                    
                    focusDistance += (zoomMul * dy * (focusDistance / 100.0));
          
                    if (focusDistance < 0) {
                        focusDistance = 0;
                    }
  
                    this.calcViewPosition(false, false);
                } else {
                    reset = false;
                }
        
                x_last = x;
                y_last = y;
            }
           // mouse translate
           else if ((id == MouseEvent.MOUSE_DRAGGED) && !evt.isAltDown() && evt.isMetaDown()) {
                x = evt.getX();
                y = evt.getY();
        
                dx = x - x_last;
                dy = y - y_last;
        
                if ((!reset) && ((Math.abs(dy) < 50) && (Math.abs(dx) < 50))) {
                    this.calcViewPosition(false, true);
                    
                } else {
                    reset = false;
                }
                
                x_last = x;
                y_last = y;
        
           } else if (id == MouseEvent.MOUSE_PRESSED) {
                // --> System.out.println("[myID=" + myID + "][MOUSE_PRESSED]");
                x_last = evt.getX();
                y_last = evt.getY();
            } else if(id == MouseEvent.MOUSE_RELEASED) {
                // --> System.out.println("MouseEvent.MOUSE_RELEASED");
            }
        } else {
            // --> System.out.println("[myId=" + myID + "]" + "[inside doProcess][NOTHING]"); 
        }
        
        // --> printInternalState();
    }
 
    public void transformChanged( Transform3D transform ) { 
        // --> System.out.println("transform changed " + transform.toString());
    }
  
    private void calcViewPosition(boolean isAltDown, boolean isMetaDown) {
        
         if(!isAltDown && !isMetaDown) {

            if(Math.abs(dx) > 0) {
                super.getCurrXForm().getRotationScale(mat);
                Vector3f axisYVector = new Vector3f();
                axisYVector = mulVec(mat, new Vector3f(0, 1, 0));
        
                if(dx > 0) {
                    y_angle *= -1;
                } 
           
                AxisAngle4f yy = new AxisAngle4f(axisYVector, (float)(y_angle*0.01));

                temp1.setIdentity();
                temp1.set(yy);

                super.getCurrXForm().mul(temp1, super.getCurrXForm());         
            }
                       
            translation = new Vector3d(0.0, 0.0, 0.0);

            super.getCurrXForm().setTranslation(focusPoint); // translate to focus point
            
            // get the rotation matrix
            super.getCurrXForm().getRotationScale(mat);

            // determine the direction
            directionVector = mulVec(mat, new Vector3f(0, 0, -1));
            
            // set translation into the opposite direction where user is looking
            translation.x  = -focusDistance*directionVector.x;
            translation.y  = -focusDistance*directionVector.y;
            translation.z  = -focusDistance*directionVector.z;        

            temp1.set(translation);
            super.getCurrXForm().mul(temp1, super.getCurrXForm());

            transformGroup.setTransform(super.getCurrXForm());             

            getPDispatcher().publish(this, SGEvent.EVT_VIEW);

        } else if(!isAltDown && isMetaDown) {
             
            super.getCurrXForm().setTranslation(focusPoint); // translate to focus point
            
            // get the rotation matrix
            super.getCurrXForm().getRotationScale(mat);
        
            // determine the direction
            directionVector = mulVec(mat, new Vector3f(0, 0, -1));
            
            // set translation into the opposite direction where user is looking
            translation.x  = -focusDistance*directionVector.x;
            translation.y  = -focusDistance*directionVector.y;
            translation.z  = -focusDistance*directionVector.z;        

            temp1.set(translation);
            super.getCurrXForm().mul(temp1, super.getCurrXForm());

  
            transformGroup.setTransform(super.getCurrXForm());         
            
            translation = new Vector3d(0.0, 0.0, 0.0);

            //super.getCurrXForm().setTranslation(focusPoint); // translate to focus point
            super.getCurrXForm().getRotationScale(mat);
            directionVector = mulVec(mat, new Vector3f(0, 0, -1));
            Vector3f dirX = mulVec(mat, new Vector3f(1.0f, 0.0f, 0.0f));
            Vector3f dirY = mulVec(mat, new Vector3f(0.0f, 1.0f, 0.0f));
            
            if(Math.abs(dx) > 0) {
                translation.x  += dirX.x*dx * moveMul;
                translation.y  += dirX.y*dx * moveMul;
                translation.z  += dirX.z*dx * moveMul;                    
            }
            if(Math.abs(dy) > 0) {
                translation.x  += dirY.x*dy * moveMul;
                translation.y  += dirY.y*dy * moveMul;
                translation.z  += dirY.z*dy * moveMul;                                  
            }                        
            
                temp1.set(translation);
                
                focusPoint.x += translation.x;
                focusPoint.y += translation.y;
                focusPoint.z += translation.z;
            
                // apply translation away from focus point
    
                super.getCurrXForm().mul(temp1, super.getCurrXForm());
                
                transformGroup.setTransform(super.getCurrXForm());    
              
              getPDispatcher().publish(this, SGEvent.EVT_VIEW);
            return;
        }
          
 
    }
  
    /**
     * Multiply the given matrix with the vector and returns the result
     * in a new vector.
     *
     * @param matrix The matrix to multiply.
     * @param vector The vector to multiply to the matrix.
     * @return A vector with the multiplication result.
     */
    private Vector3f mulVec(Matrix3f matrix, Vector3f vector) {
        float x = (matrix.m00 * vector.x) + (matrix.m01 * vector.y) + (matrix.m02 * vector.z);
        float y = (matrix.m10 * vector.x) + (matrix.m11 * vector.y) + (matrix.m12 * vector.z);
        float z = (matrix.m20 * vector.x) + (matrix.m21 * vector.y) + (matrix.m22 * vector.z);
        return new Vector3f(x, y, z);
    }

    public void copyStateFrom (SGMouseBehaviour fromBhv) {
        super.copyStateFrom(fromBhv);
        NavigateStudyPlaneBehavior nspb = (NavigateStudyPlaneBehavior) fromBhv;
        this.focusDistance = nspb.focusDistance;
        this.focusPoint = nspb.focusPoint;
        
        this.x_angle = nspb.x_angle;
        this.y_angle = nspb.y_angle;
        this.z_angle = nspb.z_angle;
        this.x_factor = nspb.x_factor;
        this.y_factor = nspb.y_factor;
        this.z_factor = nspb.z_factor;   
        
        this.zoomMul = nspb.zoomMul;
        this.moveMul = nspb.moveMul;    
        
        this.setPDispatcher(EventDispatcher.getDispatcher());
        
    }    

    public void setView(View view) {
        this.viewRef = view;
    }

    public void setPDispatcher(EventDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public EventDispatcher getPDispatcher() {
        return dispatcher;
    }

    public void onPRegister(Long evtUID) { }
    
    public void onPublish(SGEvent event) { }    

    public ArrayList getEventUIDs4Publish() {
        ArrayList evtList = new ArrayList();
        evtList.add(SGEvent.EVT_VIEW);
        return evtList;        
    }

    public SGEvent createEvent(Long evtUID) {
        if(evtUID == SGEvent.EVT_VIEW) {
            return new SGEventView(this, viewRef);
        } else {
            return null;
        }        
    }

    public Long getPublisherUID() {
        return new Long(myID);
    }

    public String getPublisherName() {
        return "NavigateStudyPlaneBehavior";
    }

    public boolean isPTransparent(Long evtUID) {
        return false;
    }

    public void setPTransparent(boolean bTransparent, Long evtUID) {
    }

}