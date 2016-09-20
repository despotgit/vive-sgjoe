package org.sgJoe.graphics.behaviors;

import com.sun.j3d.utils.picking.PickResult;
import java.awt.AWTEvent;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import javax.vecmath.Vector3d;

import javax.media.j3d.*;
import org.sgJoe.graphics.*;

import org.apache.log4j.Logger;

/**
 * This class allows scaling of objects with the mouse
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:            0.1         $
 * @date     $ Date:  December 7, 2005 8:09 PM  $
 */
public class CustomPickScaleBehavior extends CustomBehavior implements GraphicsConstants {
    
    private static Logger logger = Logger.getLogger(CustomPickScaleBehavior.class);
    
    private int navigationPlane = 1; // default plane
    private int x, y, x_last, y_last; // for mouse movement
    private boolean reset = false;
    private Transform3D currXform = new Transform3D();
    private Transform3D transformX = new Transform3D();
    private Transform3D transformY = new Transform3D();
    private BaseSceneGraph graph = null;
    private WakeupCondition condition = null;
    private TransformGroup transformGroup = null;
  
    /**
     * Creates a new CustomPickScaleBehavior. A scene graph is needed
     * as paramtere.
     * 
     * 
     * 
     * @param graph The baseSceneGraph.
     */
    public CustomPickScaleBehavior(BaseSceneGraph graph) 
    {
        this.graph = graph;
        reset = true;
        WakeupCriterion[] criteria = new WakeupCriterion[3];
        criteria[0] = new WakeupOnAWTEvent(MouseEvent.MOUSE_DRAGGED);
        criteria[1] = new WakeupOnAWTEvent(MouseEvent.MOUSE_PRESSED);
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
        setNavigationPlane(direction);
        reset();
    }    
  
    public void reset() {
        reset = true;
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
        System.out.println("ps CustomPickScaleBehavior-a.");
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

                    // mouse pressed, get the selected shape's transform group
                    if (eventId == MouseEvent.MOUSE_PRESSED) 
                    {
                        x = evt.getX();
                        y = evt.getY();
                        x_last = x;
                        y_last = y;

                        // use the BaseSceneGraph's picking method
                        PickResult pickResult = graph.getClosestIntersection(x, y);

                        transformGroup = null;
                        if (pickResult != null) 
                        {
                            transformGroup = this.getLastTransformGroup(pickResult);
                        }
                    }
                    // mouse dragged, use the mouse movement to translate the picked shape accordingly
                    else if (eventId == MouseEvent.MOUSE_DRAGGED && transformGroup != null) 
                    {
                        int dx, dy;
                        x = evt.getX();
                        y = evt.getY();
                        dx = x - x_last;
                        dy = y - y_last;

                        if ((!reset) && ((Math.abs(dy) < 50) && (Math.abs(dx) < 50))) 
                        {
                            transformGroup.getTransform(currXform);

                            // The neutral scale
                            double neutralScale = 1.0;
                            double scaleDiff = 0.025;    // Skalirni inkrement prim V.D.?

                            double scaleX = 1.0;
                            double scaleY = 1.0;
                            double scaleZ = 1.0;
                            // Make object bigger when moving mouse to the right
                            // and smaller when moving mouse to the left

                            if(dx < 0) 
                            {
                                scaleX -= scaleDiff;
                            }
                            else 
                            {
                                scaleX += scaleDiff;
                            }
                            
                            if(dy < 0)
                            {
                                scaleY -= scaleDiff;
                            } 
                            else 
                            {
                                scaleY += scaleDiff; 
                            }
                            
                            scaleZ = (scaleX + scaleY) / 2;

                            // Set the scaling
                            Vector3d scaleVec = null;
                                                        
                            // depending on the view plane, scaling works differently
                            switch (navigationPlane) 
                            {

                                case PLANE_PERSPECTIVE:
                                    // like uniform scaling
                                    scaleVec = new Vector3d(scaleX, scaleX, scaleX);
                                    break;

                                case PLANE_XY:
                                    scaleVec = new Vector3d(scaleX, scaleX, neutralScale);
                                    break;

                                case PLANE_XZ:
                                    scaleVec = new Vector3d(scaleX, neutralScale, scaleX);
                                    break;

                                case PLANE_YZ:
                                    scaleVec = new Vector3d(neutralScale, scaleX, scaleX);
                                    break;

                                case DIRECTION_X:
                                    scaleVec = new Vector3d(scaleX, neutralScale, neutralScale);
                                    break;                                

                                case DIRECTION_Y:
                                    scaleVec = new Vector3d(neutralScale, scaleX, neutralScale);
                                    break;                                

                                case DIRECTION_Z:
                                    scaleVec = new Vector3d(neutralScale, neutralScale, scaleX);
                                    break;                                

                                default:
                                    scaleVec = new Vector3d(neutralScale, neutralScale, neutralScale);
                            }  
                            
                            transformX.setScale(scaleVec);
                            
                            // Combine the two transforms
                            currXform.mul(transformX);

                            // Update xform
                            transformGroup.setTransform(currXform);

                            x_last = x;
                        } else {
                            reset = false;
                        }

                        x_last = x;
                    } else if (eventId == MouseEvent.MOUSE_PRESSED) 
                    {
                        x_last = evt.getX();
                    }
                    // when the mouse gets released, update context menues
                    else if(eventId == MouseEvent.MOUSE_RELEASED) 
                    {
                        // if we have some observer set, apply the update method
                        if (behaviorObserver != null) 
                        {
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
