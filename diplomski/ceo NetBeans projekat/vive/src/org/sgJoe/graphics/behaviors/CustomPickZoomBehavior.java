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
 * This class is similar to PickZoomBehavior, but it features a more
 * precise picking method.
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:            0.1         $
 * @date     $ Date:  December 8, 2005 6:21 PM  $
 */
public class CustomPickZoomBehavior extends CustomBehavior implements GraphicsConstants {
    
    private static Logger logger = Logger.getLogger(CustomPickZoomBehavior.class);
    
    private double z_factor = .04;
    private Vector3d translation = new Vector3d();
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
     * @param graph The baseSceneGraph.
     */
    public CustomPickZoomBehavior(BaseSceneGraph graph) {
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
     * Return the z-axis movement multipler.
     **/
    public double getZFactor() {
        return z_factor;
    }
  
    /**
     * Set the z-axis movement multipler with factor.
     **/
    public void setFactor(double factor) {
        z_factor = factor;
    }
  
    /**
     * Reset the former mouse movement (useful when focusing on other viewplanes)
     * Otherwise, mouse movement info (x_last, y_last) from former planes might
     * be stored and used
     */
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
    public void processStimulus(Enumeration criteria) {
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
        
                // make sure the middle mouse button is pressed and nothing else
                // for now, zoom behavior is for the perspective view only, since
                // it doesn't make much sense in the orthogonal views
                if ((!evt.isMetaDown()) && (evt.isAltDown())) {
          
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
                        }
            
                    }
                    // mouse dragged, use the mouse movement to translate the picked shape accordingly
                    else if (eventId == MouseEvent.MOUSE_DRAGGED && transformGroup != null) {
                        int dx, dy;
                        x = evt.getX();
                        y = evt.getY();
                        dx = x - x_last;
                        dy = y - y_last;
            
                        if (!reset) {
                            transformGroup.getTransform(currXform);
                            translation.z = dy*z_factor;
                            transformX.set(translation);
                            currXform.mul(transformX, currXform);
                            transformGroup.setTransform(currXform);
                        } else {
                            reset = false;
                        }
            
                        x_last = x;
                        y_last = y;
            
                    } else if(eventId == MouseEvent.MOUSE_RELEASED) {
                        // if we have some observer set, apply the update method
//                        if (behaviorObserver != null) {
//                            // get translation values
//                            Vector3d translation = new Vector3d();
//                            currXform.get(translation);
//                            // get scale values
//                            Vector3d scale = new Vector3d();
//                            currXform.getScale(scale);
//                            // get rotation angles
//                            Vector3d rotation = Object3DFactory.getRotationAngle(currXform);
//                            // call update fromobserver
//                            behaviorObserver.update(translation, scale, rotation);
//                            // reset currXform, if we click no object, o.o values are set
//                            currXform = new Transform3D();
//                        }
                    }
                }
            }
        }
        wakeupOn(condition);
    }    
}
