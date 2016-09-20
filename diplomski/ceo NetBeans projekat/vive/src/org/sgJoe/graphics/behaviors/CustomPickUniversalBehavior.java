package org.sgJoe.graphics.behaviors;


import com.sun.j3d.utils.picking.PickResult;
import java.awt.AWTEvent;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.vecmath.Matrix4d;
import javax.vecmath.Vector3d;

import org.sgJoe.graphics.*;
import javax.media.j3d.*;

import org.apache.log4j.Logger;
import org.sgJoe.logic.SGObjectInfo;
import org.sgJoe.logic.TGAssociation;


/*
 * This class will act as translate, scale and rotate behavior
 * depending on the transformgroup - that is object selected
 * uses more precise picking method
 * 
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: March 27, 2006  10:45 PM   $
 */

public class CustomPickUniversalBehavior 
    extends CustomBehavior implements GraphicsConstants {
    
    private static Logger logger = Logger.getLogger(CustomPickUniversalBehavior.class);
    
    public static final int TRANSLATING = 0,
                            ROTATING = 1,
                            SCALING = 3;
    // translate specific fields
    private Vector3d translation = new Vector3d();
    
    // rotate specific fields
    private double x_angle, y_angle;
    private Matrix4d mat = new Matrix4d();
        
    // scale specific fields
      
    // common fields
    private int navigationPlane = 1; // default plane
    private boolean reset = false;
    private double x_factor = .02;
    private double y_factor = .02;
    private int x, y, x_last, y_last; // for mouse movement
    private BaseSceneGraph graph = null;
    private WakeupCondition condition = null;
    private TransformGroup transformGroup = null;
    private Transform3D currXform = new Transform3D();
    private Transform3D transformX = new Transform3D();    
    private Transform3D transformY = new Transform3D();    
    
    private int state = 0;
    /**
     * Creates a new CustomPickUniversalBehaviour. 
     * A scene graph is needed as paramtere.
     * 
     * 
     * @param graph The baseSceneGraph.
     */
    public CustomPickUniversalBehavior(BaseSceneGraph graph) {
      this.graph = graph;
      this.reset = true;
      this.navigationPlane = 0;
      WakeupCriterion[] criteria = new WakeupCriterion[3];
      criteria[0] = new WakeupOnAWTEvent(MouseEvent.MOUSE_DRAGGED);
      criteria[1] = new WakeupOnAWTEvent(MouseEvent.MOUSE_PRESSED);
      criteria[2] = new WakeupOnAWTEvent(MouseEvent.MOUSE_RELEASED);
      condition = new WakeupOr(criteria); 
    }    
        
    public void initialize() {
        wakeupOn(condition);    
    }

    public double getXFactor() {
        return x_factor;
    }

    public double getYFactor() {
        return y_factor;
    }

    public void setFactor(double factor) {
        x_factor = y_factor = factor;
    }

    public void setFactor(double xFactor, double yFactor) {
        x_factor = xFactor;
        y_factor = yFactor;    
    }
    
    public void reset() {
        reset = true;
    }    
      
    public void setNavigationPlane(int plane) {
        navigationPlane = plane;
        reset = true;
    }
    public void setDirection(int direction) {
        x_angle = 0;
        y_angle = 0;
        translation = new Vector3d();
        setNavigationPlane(direction);
        reset();
    }
    
    private void internalReset() {
        translation = new Vector3d();
        currXform = new Transform3D();
        transformX = new Transform3D();    
        transformY = new Transform3D();            
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
                        // based on association type, and direction 
                        // resolve to correct TGs
                        // and correct state
                        transformGroup = getLastTransformGroup(pickResult);
                        
                        SGObjectInfo info = (SGObjectInfo) transformGroup.getUserData();
                        if(graph.peekUID() != info.getSGUID()) {
                            graph.pushUID(info.getSGUID());
                            //if new object selected check if it is actuator
                            if(info.isActuator()) {
                                // handle actuator
                                // switch to new state .. reset previous
                                switchInternalState(info.getActuatorType());
                            } else {
                                switchInternalState(SGObjectInfo.ACT_NONE);
                            }                           
                        }                        
                    
                        // special case ... only one association
                        // in future there will be list of associations
                        TGAssociation association = info.getTGAssociation();
                        if(association != null) {
                            transformGroup = association.get2ndTG();
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
                        
                        // must be correct TG due to possible associations !!!
                        transformGroup.getTransform(currXform);
              
                        // depending on behavior state different actions will take place
                        switch(state) {
                            case SGObjectInfo.ACT_TRANSLATE:
                                performTranslate(dx, dy);
                                break;
                                
                            case SGObjectInfo.ACT_SCALE:
                                performScale(dx, dy);
                                break;
                                
                            case SGObjectInfo.ACT_ROTATE:
                                performRotate(dx, dy);
                                break;
                                
                            case SGObjectInfo.ACT_NONE:
                            default:
                                continue;
                        }

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
    
    private void switchInternalState(int _state) {
        this.state = _state;
        internalReset();
    }
    
    private void performTranslate(int dx, int dy) {
        // depending on the view plane, translation works differently
        switch (navigationPlane) {
            case PLANE_XY:
            case PLANE_XZ:
            case PLANE_YZ:
            case DIRECTION_X:
            case DIRECTION_Y:
            case DIRECTION_Z:
            case PLANE_PERSPECTIVE:
                translation.x = dx*x_factor;
                translation.y = -dy*y_factor;
                break;                
        }   

        transformX.set(translation);
        currXform.mul(transformX, currXform);
        transformGroup.setTransform(currXform);        
    }
    
    private void performScale(int dx, int dy) {
        // The neutral scale
        double neutralScale = 1.0;
        double scaleDiff = 0.025;

        double scaleX = 1.0;
        double scaleY = 1.0;
        double scaleZ = 1.0;
        // Make object bigger when moving mouse to the right
        // and smaller when moving mouse to the left

        if(dx < 0) {
            scaleX -= scaleDiff;
        } else {
            scaleX += scaleDiff;
        }

        if(dy < 0){
            scaleY -= scaleDiff;
        } else {
            scaleY += scaleDiff; 
        }

        scaleZ = (scaleX + scaleY) / 2;

        // Set the scaling
        Vector3d scaleVec = null;

        // depending on the view plane, scaling works differently
        switch (navigationPlane) {
            case PLANE_XY:
            case PLANE_XZ:
            case PLANE_YZ:
            case DIRECTION_X:
            case DIRECTION_Y:
            case DIRECTION_Z:
            case PLANE_PERSPECTIVE:
                // uniform scaling
                scaleVec = new Vector3d(scaleX, scaleX, scaleX);
                break;
        }  

        transformX.setScale(scaleVec);

        // Combine the two transforms
        currXform.mul(transformX);

        // Update xform
        transformGroup.setTransform(currXform);
    }
    
    private void performRotate(int dx, int dy) {
        x_angle = dy * y_factor;
        y_angle = dx * x_factor;

        // depending on the view plane, navigation works differently
        switch (navigationPlane) {

            case PLANE_XY:
            case DIRECTION_Z:
            case PLANE_XZ:
            case DIRECTION_Y:
            case PLANE_YZ:
            case DIRECTION_X:
            case PLANE_PERSPECTIVE:
                transformX.rotX(x_angle);
                transformY.rotY(y_angle);
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
    }
}

