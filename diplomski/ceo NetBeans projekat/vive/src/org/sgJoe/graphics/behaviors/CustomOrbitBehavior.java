package org.sgJoe.graphics.behaviors;

import com.sun.j3d.utils.behaviors.mouse.MouseBehavior;
import java.util.Enumeration;

import java.awt.*;
import java.awt.event.*;
import javax.media.j3d.*;
import javax.vecmath.*;

import org.apache.log4j.Logger;


/*
 * CustomOrbitBehaviour is a customized version of Java3D's standard OrbitBehavior.
 * It is more intuitive and similar to navigation modes found in common 3D editors.
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: December 9, 2005  1:26 PM  $
 */

public class CustomOrbitBehavior extends SGMouseBehaviour {
    
    private static Logger logger = Logger.getLogger(CustomOrbitBehavior.class);
    
    private static long counter = 0;
  
    private double x_angle = 0;
    private double y_angle = 0;
    private double x_factor = .01;
    private double y_factor = .01;
    private double z_factor = .04;
  
    private Vector3f focusPoint = new Vector3f(0f, 0f, 0f);
    private double focusDistance = 20;
    
    private Matrix3f mat = new Matrix3f();
    private Transform3D temp1 = new Transform3D();
    private Vector3d translation = new Vector3d();
        
    private Vector3f directionVector = new Vector3f();    
      
    private double zoomMul = 0.25;
    private double moveMul = 0.025;
  
    private long myID = -1;
    /**
     * Creates a custom orbit behavior given the transform group.
     * @param transformGroup The transformGroup to operate on.
     */
//    public CustomOrbitBehaviour(TransformGroup transformGroup) {
//        super(transformGroup);
//        myID = ++counter;
//        //System.out.println("CustomOrbitBehaviour constructor" + myID);
//    }
  
    /**
     * Creates a custom orbit behavior that uses AWT listeners and behavior
     * posts rather than WakeupOnAWTEvent.  The behavior is added to the
     * specified Component. A null component can be passed to specify
     * the behavior should use listeners.  Components can then be added
     * to the behavior with the addListener(Component c) method.
     * @param c The Component to add the MouseListener
     * and MouseMotionListener to.
     * @since Java 3D 1.2.1
     */
    public CustomOrbitBehavior(Component c) {
        super(c, 0);
        myID = ++counter;
        // --> System.out.println("[myID=" + myID + "]" + " CustomOrbitBehaviour constructor ");
    }
    
    public static class CustomOrbitBehaviorState extends SGMouseBehaviour.sgJMouseBehaviorState {
        
        private final static String X_ANGLE = "x_angle";
        private final static String Y_ANGLE = "y_angle";
        private final static String X_FACTOR = "x_factor";
        private final static String Y_FACTOR = "y_factor";
        private final static String Z_FACTOR = "z_factor";
        private final static String FOCUSDISTANCE = "focusDistance";
        private final static String ZOOMMUL = "zoomMul";
        private final static String MOVEMUL = "moveMul";
        
        private final static String MAT = "mat";
        private final static String FOCUSPOINT = "focusPoint";
        private final static String TEMP1 = "temp1";
        
        private final static String TRANSLATION = "translation";
        private final static String DIRECTIONVECTOR = "directionVector";    
        
        public CustomOrbitBehaviorState(CustomOrbitBehavior bhv) {
            super(bhv);
            
            addProperty(X_ANGLE, new Double(bhv.x_angle));
            addProperty(Y_ANGLE, new Double(bhv.y_angle));
            addProperty(X_FACTOR, new Double(bhv.x_factor));
            addProperty(Y_FACTOR, new Double(bhv.y_factor));
            addProperty(Z_FACTOR, new Double(bhv.z_factor));      
            addProperty(ZOOMMUL, new Double(bhv.zoomMul));
            addProperty(MOVEMUL, new Double(bhv.moveMul));
            addProperty(FOCUSDISTANCE, new Double(bhv.focusDistance));
            
            addProperty(MAT, new Matrix3f(bhv.mat));   
            addProperty(TEMP1, new Transform3D(bhv.temp1));
            addProperty(FOCUSPOINT, new Vector3f(bhv.focusPoint));
            addProperty(DIRECTIONVECTOR, new Vector3f(bhv.directionVector));                        
            
            addProperty(TRANSLATION, new Vector3d(bhv.translation));
        }
    }
 
    public SGBehaviorState getBehaviorState() {
        return new CustomOrbitBehaviorState(this);
    }    
    
    public void setBehaviorSate(SGBehaviorState bhvSta) {
        super.setBehaviorSate(bhvSta);
        
        CustomOrbitBehaviorState coBhvSta = (CustomOrbitBehaviorState)bhvSta;
        
        x_angle = ((Double)coBhvSta.getProperty(coBhvSta.X_ANGLE)).doubleValue();
        y_angle = ((Double)coBhvSta.getProperty(coBhvSta.Y_ANGLE)).doubleValue();
        x_factor = ((Double)coBhvSta.getProperty(coBhvSta.X_FACTOR)).doubleValue();
        y_factor = ((Double)coBhvSta.getProperty(coBhvSta.Y_FACTOR)).doubleValue();
        z_factor = ((Double)coBhvSta.getProperty(coBhvSta.Z_FACTOR)).doubleValue();
        zoomMul = ((Double)coBhvSta.getProperty(coBhvSta.ZOOMMUL)).doubleValue();
        moveMul = ((Double)coBhvSta.getProperty(coBhvSta.MOVEMUL)).doubleValue();
        focusDistance = ((Double)coBhvSta.getProperty(coBhvSta.FOCUSDISTANCE)).doubleValue();
        
        mat = new Matrix3f((Matrix3f)coBhvSta.getProperty(coBhvSta.MAT));
        temp1 = new Transform3D((Transform3D)coBhvSta.getProperty(coBhvSta.TEMP1));
        focusPoint = new Vector3f((Vector3f)coBhvSta.getProperty(coBhvSta.FOCUSPOINT));
        directionVector = new Vector3f((Vector3f)coBhvSta.getProperty(coBhvSta.DIRECTIONVECTOR));
        
        translation = new Vector3d((Vector3d)coBhvSta.getProperty(coBhvSta.TRANSLATION));
    }
    
    public void copyStateTo (CustomOrbitBehavior toBhv) {
        
        super.copyStateTo(toBhv);
        
        toBhv.x_angle = this.x_angle;
        toBhv.y_angle = this.y_angle;
        toBhv.x_factor = this.x_factor;
        toBhv.y_factor = this.y_factor;
        toBhv.z_factor = this.z_factor;

        toBhv.zoomMul = this.zoomMul;
        toBhv.moveMul = this.moveMul;
        
        toBhv.focusDistance = this.focusDistance;
      
        toBhv.mat = new Matrix3f(this.mat);
        toBhv.focusPoint = new Vector3f(this.focusPoint);
        toBhv.temp1 = new Transform3D(this.temp1);
        toBhv.translation = new Vector3d(this.translation);
        toBhv.directionVector = new Vector3f(this.directionVector);            
    }
    
    public void copyStateFrom (CustomOrbitBehavior fromBhv) {
        
        super.copyStateTo(fromBhv);
        
        this.x_angle = fromBhv.x_angle;
        this.y_angle = fromBhv.y_angle;
        this.x_factor = fromBhv.x_factor;
        this.y_factor = fromBhv.y_factor;
        this.z_factor = fromBhv.z_factor;

        this.zoomMul = fromBhv.zoomMul;
        this.moveMul = fromBhv.moveMul;
        
        this.focusDistance = fromBhv.focusDistance;
      
        this.mat = new Matrix3f(fromBhv.mat);
        this.focusPoint = new Vector3f(fromBhv.focusPoint);
        this.temp1 = new Transform3D(fromBhv.temp1);
        this.translation = new Vector3d(fromBhv.translation);
        this.directionVector = new Vector3f(fromBhv.directionVector);    
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
  
    public void setFocusPoint(Vector3f point) {
        focusPoint = point;
    }
  
    public void setFocusDistance(double distance) {
        focusDistance = distance;
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
        AWTEvent[] events;
        MouseEvent evt;
        
        // --> System.out.println("processStimulus " + myID);
        while (criteria.hasMoreElements()) 
        {
            wakeup = (WakeupCriterion) criteria.nextElement();
            if (wakeup instanceof WakeupOnAWTEvent) 
            {
                events = ((WakeupOnAWTEvent)wakeup).getAWTEvent();
                if (events.length > 0) 
                {
                    evt = (MouseEvent) events[events.length-1];
                    // --> System.out.println("[myID=" + myID + "]" + "wakeup instanceof WakeupOnAWTEvent");
                    doProcess(evt);
                }
            } else if (wakeup instanceof WakeupOnBehaviorPost) 
            {
                while (true) 
                {                    
                    // access to the queue must be synchronized
                    synchronized (mouseq) 
                    {
                        if (mouseq.isEmpty()) 
                        {
                            break;
                        }
                        evt = (MouseEvent)mouseq.remove(0);
                        // consolidate MOUSE_DRAG events
                        while ((evt.getID() == MouseEvent.MOUSE_DRAGGED) && 
                                !mouseq.isEmpty() && 
                                (((MouseEvent)mouseq.get(0)).getID() == MouseEvent.MOUSE_DRAGGED)) 
                        {evt = (MouseEvent)mouseq.remove(0);
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
        int dx, dy;
        
        // --> System.out.println("[myID=" + myID + "]" +"[BEFORE][processMouseEvent(evt)]");
        processMouseEvent(evt);
        // --> System.out.println("[myID=" + myID + "]" +"[AFTER][processMouseEvent(evt)]");
    
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
                    x_angle = x_angle - dy * y_factor;
                    if (x_angle >= 2 * Math.PI) {
                        x_angle = x_angle - 2 * Math.PI;
                    }
                    if (x_angle < 0) {
                        x_angle = x_angle + 2 * Math.PI;
                    }
          
                    y_angle = y_angle - dx * x_factor;
                    if (y_angle >= 2 * Math.PI) {
                        y_angle = y_angle - 2 * Math.PI;
                    }
                    if (y_angle < 0) {
                        y_angle = y_angle + 2 * Math.PI;
                    }
                    // --> System.out.println("[myID =" +myID+ "]" + "[!reset] calcViewPosition");
                    this.calcViewPosition(1);
                } else {
                    reset = false;
                }
        
                x_last = x;
                y_last = y;
            }
            // zoom in and out
            else if ((id == MouseEvent.MOUSE_DRAGGED) &&  evt.isAltDown() && !evt.isMetaDown()) {
                x = evt.getX();
                y = evt.getY();
        
                dx = x - x_last;
                dy = y - y_last;
        
                if (!reset) {
                    // focusDistance -= dy*z_factor;
                    // proportional zoom
                    focusDistance -= (zoomMul * dy * (focusDistance / 100.0));
          
                    if (focusDistance < 0) {
                        focusDistance = 0;
                    }
                    // --> System.out.println("zoom calcViewPosition");
                    this.calcViewPosition(2);
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
                    // determine the direction (orthogonal to the user's view direction)
                    // for x/z movement (x axis mouse direction)
                    // --> currXform.getRotationScale(mat);
                    super.getCurrXForm().getRotationScale(mat);
                    // determine the direction
                    directionVector = mulVec(mat, new Vector3f(0, 0, -1));
          
                    // move focus point accordingly (orthogonal to view direction)
                    // increase speed if user is further away from focus point
                    focusPoint.x = (float)(focusPoint.x + (dx * moveMul * directionVector.z * (focusDistance / 100.0)));
                    focusPoint.z = (float)(focusPoint.z - (dx * moveMul * directionVector.x * (focusDistance / 100.0)));
                    focusPoint.y = (float)(focusPoint.y + (dy * moveMul * (focusDistance / 100.0)));
                    this.calcViewPosition(3);
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
  
    /**
     * Users can overload this method  which is called every time
     * the Behavior updates the transform
     *
     * Default implementation does nothing
     */
    public void transformChanged( Transform3D transform ) { 
        // --> System.out.println("transform changed " + transform.toString());
    }
  
    private void calcViewPosition(int callPos) {
               
        // --> System.out.println("[calcViewPosition][position=" + callPos + "]");
        
        super.getCurrXForm().setIdentity();
        super.getCurrXForm().rotX(x_angle); // elevation

        temp1.setIdentity();
        temp1.rotY(y_angle); // horizontal view

        super.getCurrXForm().mul(temp1, super.getCurrXForm()); // rotate!

        /////////////////////////////////////////////////////////////////////////////////////
        //translation part
        
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
        super.getCurrXForm().mul(temp1, super.getCurrXForm()); // apply translation away from focus point

        transformGroup.setTransform(super.getCurrXForm());
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
  
    private void printInternalState() {
        System.out.println(mat.toString());
        System.out.println(focusPoint.toString());
        System.out.println(focusDistance);
        System.out.println(temp1.toString());
        System.out.println(translation.toString());
    }
    
    public CustomOrbitBehavior createNewBehaviourObjectAndCopyInternalState(Component c) {
        // --> System.out.println("[myID=" + myID +"][createNewBehaviourObjectAndCopyInternalState]");
        CustomOrbitBehavior orbit = new CustomOrbitBehavior(c);

        orbit.x = this.x;
        orbit.y = this.y;
        orbit.x_last = this.x_last;
        orbit.y_last = this.y_last;
        orbit.enable = this.enable;
        orbit.flags = this.flags;
        orbit.invert = this.invert;
        orbit.reset = this.reset;
        
        orbit.x_angle = this.x_angle;
        orbit.y_angle = this.y_angle;
        orbit.x_factor = this.x_factor;
        orbit.y_factor = this.y_factor;        
        orbit.z_factor = this.z_factor;        
    
        orbit.mat = new Matrix3f(this.mat);
        orbit.focusPoint = new Vector3f(this.focusPoint);
        orbit.focusDistance = this.focusDistance;
        orbit.temp1 = new Transform3D(this.temp1);
        orbit.translation = new Vector3d(this.translation);
        
        orbit.directionVector = new Vector3f(this.directionVector);    
      
        orbit.zoomMul = this.zoomMul;
        orbit.moveMul = this.moveMul;        
        
        orbit.currXFormSet(this.getCurrXForm());
        
        // --> System.out.println("[myID="+ orbit.myID+"]" + "createNewBehaviourObjectAndCopyInternalState");
        
        return orbit;
    }
   
    public void stdPrintInternalState() {
        super.stdPrintInternalState();
        
//        System.out.println("[BEGIN][counter][myID]");
//        System.out.println("[" + counter + "][" + myID + "]");
//        System.out.println("[END][counter][myID]"); 
        
//        System.out.println("[BEGIN][zoomMul][moveMul][focusDistance]");
//        System.out.println("[" + zoomMul + "][" + moveMul + "][" + focusDistance + "]");
//        System.out.println("[END][zoomMul][moveMul][focusDistance]");
    
        System.out.println("[BEGIN][x_angle][y_angle]");
        System.out.println("[" + x_angle + "][" + y_angle + "]");
        System.out.println("[END][x_angle][y_angle]");
  
//        System.out.println("[BEGIN][x_factor][y_factor][z_factor]");
//        System.out.println("[" + x_factor + "][" + y_factor + "][" + z_factor + "]");
//        System.out.println("[END][x_factor][y_factor][z_factor]");
                
//        System.out.println("[BEGIN][focusPoint]");
//        System.out.println(focusPoint.toString());
//        System.out.println("[END][focusPoint]");        
  
        System.out.println("[BEGIN][mat]");
        System.out.println(mat.toString());
        System.out.println("[END][mat]");

        System.out.println("[BEGIN][temp1]");
        System.out.println(temp1.toString());
        System.out.println("[END][temp1]");
        
        System.out.println("[BEGIN][translation]");
        System.out.println(translation.toString());
        System.out.println("[END][translation]");
    
        System.out.println("[BEGIN][directionVector]");
        System.out.println(directionVector.toString());
        System.out.println("[END][directionVector]");        
    }
}
