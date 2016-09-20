package org.sgJoe.graphics.behaviors;

import com.sun.j3d.utils.picking.PickResult;
import java.awt.AWTEvent;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import javax.vecmath.Vector3d;

import org.sgJoe.graphics.*;
import javax.media.j3d.*;

import org.apache.log4j.Logger;
import org.sgJoe.logic.*;

/**
 * This class is similar to PickTranslateBehavior, but it features a more
 * precise picking method.
 *
 * @author   $ Author: Aleksandar Babic $
 * @version  $ Revision:            0.1 $
 * @date     $ Date:  December 8, 2005 8:00 PM $
 */
public class CustomPickTranslateBehavior extends CustomBehavior implements GraphicsConstants{
    
    private static Logger logger = Logger.getLogger(CustomPickTranslateBehavior.class);
 
    private double x_factor = .02;
    private double y_factor = .02;
    private Vector3d translation = new Vector3d();
    private int navigationPlane = 1; // default plane
    private int x, y, x_last, y_last; // for mouse movement
    private boolean reset = false;
    private Transform3D currXform = new Transform3D();
    private Transform3D transformX = new Transform3D();
    private BaseSceneGraph graph = null;
    private WakeupCondition condition = null;
    private TransformGroup transformGroup = null;    
    
    /**
     * Creates a new CustomPickTranslateBehavior. A scene graph is needed
     * as paramtere.
     * 
     * 
     * 
     * @param graph The baseSceneGraph.
     */
    public CustomPickTranslateBehavior(BaseSceneGraph graph) {
      this.graph = graph;
      reset = true;
      WakeupCriterion[] criteria = new WakeupCriterion[3];
      criteria[0] = new WakeupOnAWTEvent(MouseEvent.MOUSE_DRAGGED);
      criteria[1] = new WakeupOnAWTEvent(MouseEvent.MOUSE_PRESSED);
      criteria[2] = new WakeupOnAWTEvent(MouseEvent.MOUSE_RELEASED);
      condition = new WakeupOr(criteria);
      
      this.navigationPlane = 0;
    }

    /**
     * Initialize this behavior.
     */
    public void initialize() {
        wakeupOn(condition);    
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
    public void setFactor(double factor) {
        x_factor = y_factor = factor;
    }

    /**
     * Set the x-axis amd y-axis movement multipler with xFactor and yFactor
     * respectively.
     **/
    public void setFactor(double xFactor, double yFactor) {
        x_factor = xFactor;
        y_factor = yFactor;    
    }
  
    /**
     * Set the behavior's navigation plane
     * This alters the way the mouse movement is handled, thus we need to reset
     * the mouse movement info (x_last, y_last), too.
     *
     * @param plane the behavior's plane
     */
    public void setNavigationPlane(int plane) {
        navigationPlane = plane;
        reset = true;
    }
    
    public void setDirection(int direction) {
        translation = new Vector3d();
        setNavigationPlane(direction);
        reset();
    }
  
    public void reset() {
        this.reset = true;
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
        System.out.println("ps CustomPickTranslateBehavior-a.");
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
                if ((!evt.isMetaDown()) && (!evt.isAltDown())) {
        
                // mouse pressed, get the selected shape's transform group
                if (eventId == MouseEvent.MOUSE_PRESSED) {
                    x = evt.getX();
                    y = evt.getY();
                    x_last = x;
                    y_last = y;
            
                    // use the BaseSceneGraph's picking method
                    PickResult pickResult = graph.getClosestIntersection(x, y);
                    
                    transformGroup = null;
                    if (pickResult != null) {
                        //transformGroup = (TransformGroup)pickResult.getNode(PickResult.TRANSFORM_GROUP); 
                        
                        // based on association type, and direction resolve to correct TGs
                        transformGroup = getLastTransformGroup(pickResult);
                        
                        SGObjectInfo info = (SGObjectInfo) transformGroup.getUserData();
                        if(info != null) {
                            if(graph.peekUID() != info.getSGUID()) {
                                graph.pushUID(info.getSGUID());
                            }
                            TGAssociation association = info.getTGAssociation();
                            if(association != null) {
                                transformGroup = association.get2ndTG();
                            }
                        }
                    }

                }
                // mouse dragged, use the mouse movement to translate the picked shape accordingly
                else if (eventId == MouseEvent.MOUSE_DRAGGED && transformGroup != null) {
                    int dx, dy;
                    x = evt.getX();
                    y = evt.getY();
                    dx = x - x_last;
                    dy = y - y_last;
            
                    if ((!reset) && ((Math.abs(dy) < 10) && (Math.abs(dx) < 10))) {
                        
                        transformGroup.getTransform(currXform);
              
                        // depending on the view plane, translation works differently
                        switch (navigationPlane) {
                        
                            case PLANE_PERSPECTIVE:
                                translation.x = dx*x_factor;
                                translation.y = -dy*y_factor;
                                break;
                
                            case PLANE_XY:
                                translation.x = dx*x_factor; 
                                translation.y = -dy*y_factor;
                                break;
                
                            case PLANE_XZ:
                                translation.x = dx*x_factor; 
                                translation.z = dy*y_factor;
                                break;
                
                            case PLANE_YZ:
                                translation.y = -dy*y_factor;
                                translation.z = dx*x_factor; 
                                break;
                                
                            case DIRECTION_X:
                                translation.x = dx*x_factor; 
                                break;                                
                                
                            case DIRECTION_Y:
                                translation.y = -dy*y_factor;
                                //translation.y = dx*x_factor; 
                                break;                                
                                
                            case DIRECTION_Z:
                                translation.z = -dy*y_factor;
                                translation.z = dx*x_factor; 
                                break;                                
                        }   

                        transformX.set(translation);
                        currXform.mul(transformX, currXform);
                        transformGroup.setTransform(currXform);
                    } else {
                        reset = false;
                    }

                    x_last = x;
                    y_last = y;
                }
                // when the mouse gets released, update context menues
                else if(eventId == MouseEvent.MOUSE_RELEASED) {
                    // if we have some observer set, apply the update method
                    if (behaviorObserver != null) {
                        // get translation values
                        Vector3d translation = new Vector3d();
                        currXform.get(translation);
                        // get scale values
                        Vector3d scale = new Vector3d();
                        currXform.getScale(scale);
                        // get rotation angles
                        Vector3d rotation = Object3DFactory.getRotationAngle(currXform);
                        // call update fromobserver
                        behaviorObserver.update(translation, scale, rotation);
                        // reset currXform, if we click no object, o.o values are set
                        currXform = new Transform3D();
                    }
                 }
               }
            }
        }
        wakeupOn(condition);
    }
}
