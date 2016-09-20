package org.sgJoe.graphics.behaviors;

import com.sun.j3d.utils.picking.PickIntersection;
import com.sun.j3d.utils.picking.PickResult;

import java.awt.AWTEvent;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.media.j3d.*;
import javax.vecmath.*;
import org.sgJoe.graphics.*;

import org.apache.log4j.Logger;

/**
 * This class is similar to PickRotateBehavior, but it features a more
 * precise picking method.
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:            0.1         $
 * @date     $ Date:  December 8, 2005 10:13 PM $
 */
public class CustomPickRotateBehavior extends CustomBehavior implements GraphicsConstants {
    
    private static Logger logger = Logger.getLogger(CustomPickRotateBehavior.class);
   
    private double x_factor = .03;
    private double y_factor = .03;
    private double x_angle, y_angle;
    private int x, y, x_last, y_last;
    
    private Transform3D currXform = new Transform3D();
    private Transform3D transformX = new Transform3D();
    private Transform3D transformY = new Transform3D();
    
    private Matrix4d mat = new Matrix4d();
   
    private BaseSceneGraph graph = null;
    private WakeupCondition condition = null;
    private TransformGroup transformGroup = null;
    
    private boolean reset = false;
    
    private int navigationPlane = 1;

    public CustomPickRotateBehavior(BaseSceneGraph graph) {
        this.graph = graph;
        this.reset = true;
        WakeupCriterion[] criteria = new WakeupCriterion[3];
        criteria[0] = new WakeupOnAWTEvent(MouseEvent.MOUSE_PRESSED);
        criteria[1] = new WakeupOnAWTEvent(MouseEvent.MOUSE_DRAGGED);
        criteria[2] = new WakeupOnAWTEvent(MouseEvent.MOUSE_RELEASED);
        condition = new WakeupOr(criteria);
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
        x_angle = 0;
        y_angle = 0;
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
        System.out.println("ps CustomPickRotateBehavior-a.");
        
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
                            transformGroup = this.getLastTransformGroup(pickResult);
                            
                            int interNum = pickResult.numIntersections();
                            for(int i = 0; i < interNum; i++) {
                                PickIntersection pickInter = pickResult.getIntersection(i);
                                System.out.println("distance" + pickInter.getDistance());
                                Point3d intPt = pickInter.getPointCoordinatesVW();
                                if(intPt != null) {
                                    System.out.println(intPt);
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
            
                        if ((!reset) && ((Math.abs(dy) < 50) && (Math.abs(dx) < 50))) {
                            transformGroup.getTransform(currXform);
              
                            x_angle = dy * y_factor;
                            y_angle = dx * x_factor;
              
                            // depending on the view plane, navigation works differently
                            switch (navigationPlane) {
                
                                case PLANE_PERSPECTIVE:
                                    transformX.rotX(x_angle);
                                    transformY.rotY(y_angle);
                                    break;
                                    
                                case PLANE_XY:
                                case DIRECTION_Z:
                                    transformX.rotZ(-y_angle);
                                    break;
                
                                case PLANE_XZ:
                                case DIRECTION_Y:
                                    transformX.rotY(-y_angle);
                                    break;
                                
                                case PLANE_YZ:
                                case DIRECTION_X:
                                    transformX.rotX(y_angle);
                                    break;
                            }
              
                            // Remember old matrix
                            currXform.get(mat);
              
                            // Translate to origin
                            currXform.setTranslation(new Vector3d(0.0,0.0,0.0));
              
                            currXform.mul(transformX, currXform);
                            currXform.mul(transformY, currXform);
              
                            // Set old translation back
                            Vector3d translation = new Vector3d(mat.m03, mat.m13, mat.m23);
                            currXform.setTranslation(translation);

                            // Update xform
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
