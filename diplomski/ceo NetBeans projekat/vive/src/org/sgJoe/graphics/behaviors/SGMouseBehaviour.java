package org.sgJoe.graphics.behaviors;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.internal.J3dUtilsI18N;

import org.apache.log4j.Logger;

/**
 * Description for sgJMouseBehaviour class:
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:            0.1         $
 * @date     $ Date:  February 11, 2006 9:28 PM $
 */

/**
 * Base class for all mouse manipulators (see MouseRotate, MouseZoom
 * and MouseTranslate for
 * examples of how to extend this base class). 
 */

public abstract class SGMouseBehaviour extends Behavior
     implements MouseListener, MouseMotionListener, MouseWheelListener {
    
    private static Logger logger = Logger.getLogger(SGMouseBehaviour.class);

    private boolean listener = false;
    
    protected WakeupCriterion[] mouseEvents;
    protected WakeupOr mouseCriterion;
    protected int x, y;
    protected int x_last, y_last;
    protected TransformGroup transformGroup;
    protected Transform3D transformX;
    protected Transform3D transformY;
    private Transform3D currXform;
    protected boolean buttonPress = false;
    protected boolean reset = false;
    protected boolean invert = false;
    protected boolean wakeUp = false;
    protected int flags = 0;

    // to queue the mouse events
    protected LinkedList mouseq;

    // true if this behavior is enable
    protected boolean enable = true;

   /**
    * Set this flag if you want to manually wakeup the behavior.
    */
    public static final int MANUAL_WAKEUP = 0x1;

    /** 
     * Set this flag if you want to invert the inputs.  This is useful when
     * the transform for the view platform is being changed instead of the 
     * transform for the object.
     */
    public static final int INVERT_INPUT = 0x2;

    /**
     * Creates a mouse behavior object with a given transform group.
     * @param transformGroup The transform group to be manipulated.
     */
//    public sgJMouseBehaviour(TransformGroup transformGroup) {
//	super();
//	// need to remove old behavior from group 
//	this.transformGroup = transformGroup;
//	currXform = new Transform3D();
//	transformX = new Transform3D();
//	transformY = new Transform3D();
//	reset = true;
//    }

    /**
     * Initializes standard fields. Note that this behavior still
     * needs a transform group to work on (use setTransformGroup(tg)) and
     * the transform group must add this behavior.
     * @param format flags
     */
    public SGMouseBehaviour(int format) {
	super();
	flags = format;
	currXform = new Transform3D();
	transformX = new Transform3D();
	transformY = new Transform3D();
	reset = true;
    }

    /**
     * Creates a mouse behavior that uses AWT listeners and behavior
     * posts rather than WakeupOnAWTEvent.  The behaviors is added to
     * the specified Component and works on the given TransformGroup.
     * A null component can be passed to specify the behaviors should use
     * listeners.  Components can then be added to the behavior with the
     * addListener(Component c) method.
     * @param c The Component to add the MouseListener and
     * MouseMotionListener to.
     * @param transformGroup The TransformGroup to operate on.
     * @since Java 3D 1.2.1
     */
//    public sgJMouseBehaviour(Component c, TransformGroup transformGroup) {
//	this(transformGroup);
//	if (c != null) {
//	    c.addMouseListener(this);
//	    c.addMouseMotionListener(this);
//	    c.addMouseWheelListener(this);
//	}
//	listener = true;
//    }

    /**
     * Creates a mouse behavior that uses AWT listeners and behavior
     * posts rather than WakeupOnAWTEvent.  The behavior is added to the
     * specified Component.  A null component can be passed to specify
     * the behavior should use listeners.  Components can then be added
     * to the behavior with the addListener(Component c) method.
     * Note that this behavior still needs a transform
     * group to work on (use setTransformGroup(tg)) and the transform
     * group must add this behavior.
     * @param format interesting flags (wakeup conditions).
     * @since Java 3D 1.2.1
     */
    public SGMouseBehaviour(Component c, int format) {
	this(format);
	if (c != null) {
	    c.addMouseListener(this);
	    c.addMouseMotionListener(this);
	    c.addMouseWheelListener(this);
	}
	listener = true;
    }
    
    public void copyStateTo (SGMouseBehaviour toBhv) {
        // mouseEvents;
        // mouseCriterion;
        // transformGroup;
        // mouseq;        
        toBhv.listener = this.listener;
        toBhv.x = this.x;
        toBhv.y = this.y;
        toBhv.x_last = this.x_last;
        toBhv.y_last = this.y_last;
       
        toBhv.transformX = new Transform3D(this.transformX);
        toBhv.transformY = new Transform3D(this.transformY);
        toBhv.currXform = new Transform3D(this.currXform);
        
        toBhv.buttonPress = this.buttonPress;
        toBhv.reset = this.reset;
        toBhv.invert = this.invert;
        toBhv.wakeUp = this.wakeUp;
        toBhv.flags = this.flags;

        toBhv.enable = this.enable;        
    }
    
    public void copyStateFrom (SGMouseBehaviour fromBhv) {
        // mouseEvents;
        // mouseCriterion;
        // transformGroup;
        // mouseq;        
        this.listener = fromBhv.listener;
        this.x = fromBhv.x;
        this.y = fromBhv.y;
        this.x_last = fromBhv.x_last;
        this.y_last = fromBhv.y_last;
       
        this.transformX = new Transform3D(fromBhv.transformX);
        this.transformY = new Transform3D(fromBhv.transformY);
        this.currXform = new Transform3D(fromBhv.currXform);
        
        this.buttonPress = fromBhv.buttonPress;
        this.reset = fromBhv.reset;
        this.invert = fromBhv.invert;
        this.wakeUp = fromBhv.wakeUp;
        this.flags = fromBhv.flags;

        this.enable = fromBhv.enable;
    }
    
    public static class sgJMouseBehaviorState extends SGBehaviorState {
        
        public final static String LISTENER = "listener";
        public final static String X = "x";
        public final static String Y = "y";
        
        public final static String X_LAST = "x_last";
        public final static String Y_LAST = "y_last";
               
        public final static String TRANSFORMX = "transformX";
        public final static String TRANSFORMY = "transformY";
        public final static String CURRXFORM = "currXform";
        
        public final static String BUTTONPRESS = "buttonPress";
        public final static String RESET = "reset";
        public final static String INVERT = "invert";
        public final static String WAKEUP = "wakeUp";
        public final static String FLAGS = "flags";

        public final static String ENABLE = "enable";        
        
        public sgJMouseBehaviorState(SGMouseBehaviour bhv) {
            super(14);
            
            addProperty(LISTENER, new Boolean(bhv.listener));
            
            addProperty(X, new Integer(bhv.x));
            addProperty(Y, new Integer(bhv.y));
            addProperty(X_LAST, new Integer(bhv.x_last));
            addProperty(Y_LAST, new Integer(bhv.y_last));          
                    
            addProperty(FLAGS, new Integer(bhv.flags));            
            
            addProperty(TRANSFORMX, new Transform3D(bhv.transformX));
            addProperty(TRANSFORMY, new Transform3D(bhv.transformY));
            addProperty(CURRXFORM, new Transform3D(bhv.currXform));
        
            addProperty(BUTTONPRESS, new Boolean(bhv.buttonPress));
            addProperty(RESET, new Boolean(bhv.reset));
            addProperty(INVERT, new Boolean(bhv.invert));
            addProperty(WAKEUP, new Boolean(bhv.wakeUp));
            addProperty(ENABLE, new Boolean(bhv.enable));
        }
    }
    
    public SGBehaviorState getBehaviorState() {
        return new sgJMouseBehaviorState(this);
    }
    
    public void setBehaviorSate(SGBehaviorState bhvSta) {
        sgJMouseBehaviorState mBhvSta = (sgJMouseBehaviorState)bhvSta;
        
        listener = ((Boolean)mBhvSta.getProperty(mBhvSta.LISTENER)).booleanValue();
        buttonPress = ((Boolean)mBhvSta.getProperty(mBhvSta.BUTTONPRESS)).booleanValue();
        reset = ((Boolean)mBhvSta.getProperty(mBhvSta.RESET)).booleanValue();
        invert = ((Boolean)mBhvSta.getProperty(mBhvSta.INVERT)).booleanValue();
        wakeUp = ((Boolean)mBhvSta.getProperty(mBhvSta.WAKEUP)).booleanValue();            
        enable = ((Boolean)mBhvSta.getProperty(mBhvSta.ENABLE)).booleanValue();
            
        x = ((Integer)mBhvSta.getProperty(mBhvSta.X)).intValue();
        y = ((Integer)mBhvSta.getProperty(mBhvSta.Y)).intValue();
        x_last = ((Integer)mBhvSta.getProperty(mBhvSta.X_LAST)).intValue();
        y_last = ((Integer)mBhvSta.getProperty(mBhvSta.Y_LAST)).intValue();

        flags = ((Integer)mBhvSta.getProperty(mBhvSta.FLAGS)).intValue();
                 
        transformX = new Transform3D(((Transform3D)mBhvSta.getProperty(mBhvSta.TRANSFORMX)));
        transformY = new Transform3D(((Transform3D)mBhvSta.getProperty(mBhvSta.TRANSFORMY)));
        currXform = new Transform3D(((Transform3D)mBhvSta.getProperty(mBhvSta.CURRXFORM)));
    }
 
  /** 
   * Swap a new transformGroup replacing the old one. This allows 
   * manipulators to operate on different nodes.
   * 
   * @param transformGroup The *new* transform group to be manipulated.
   */
  public void setTransformGroup(TransformGroup transformGroup){
    // need to remove old behavior from group 
    this.transformGroup = transformGroup;
    currXform = new Transform3D();
    transformX = new Transform3D();
    transformY = new Transform3D();
    reset = true;
  }

  public void importTransformGroup(TransformGroup transformGroup){
    // need to remove old behavior from group 
    this.transformGroup = transformGroup;
    reset = false;
  }
  /**
   * Return the transformGroup on which this node is operating
   */
  public TransformGroup getTransformGroup() {
    return this.transformGroup;
  }

  /** Initializes the behavior.
   */

  public void initialize() {
    mouseEvents = new WakeupCriterion[4];

    if (!listener) {
	mouseEvents[0] = new WakeupOnAWTEvent(MouseEvent.MOUSE_DRAGGED);
	mouseEvents[1] = new WakeupOnAWTEvent(MouseEvent.MOUSE_PRESSED);
	mouseEvents[2] = new WakeupOnAWTEvent(MouseEvent.MOUSE_RELEASED);
	mouseEvents[3] = new WakeupOnAWTEvent(MouseEvent.MOUSE_WHEEL);
    }
    else {
	mouseEvents[0] = new WakeupOnBehaviorPost(this,
						  MouseEvent.MOUSE_DRAGGED);
	mouseEvents[1] = new WakeupOnBehaviorPost(this,
						  MouseEvent.MOUSE_PRESSED);
	mouseEvents[2] = new WakeupOnBehaviorPost(this,
						  MouseEvent.MOUSE_RELEASED);
	mouseEvents[3] = new WakeupOnBehaviorPost(this,
						  MouseEvent.MOUSE_WHEEL);
	mouseq = new LinkedList();
    }
    mouseCriterion = new WakeupOr(mouseEvents);
    wakeupOn (mouseCriterion);
    x = 0;
    y = 0;
    x_last = 0;
    y_last = 0;
  }
  
  /** 
   * Manually wake up the behavior. If MANUAL_WAKEUP flag was set upon 
   * creation, you must wake up this behavior each time it is handled.
   */

  public void wakeup()
  {
    wakeUp = true;
  }

  /**
   * Handles mouse events
   */
  public void processMouseEvent(MouseEvent evt) {
    if (evt.getID()==MouseEvent.MOUSE_PRESSED) {
      buttonPress = true;
      // --> System.out.println("[buttonPress=true]");
      return;
    }
    else if (evt.getID()==MouseEvent.MOUSE_RELEASED){
      buttonPress = false;
      wakeUp = false;
      // --> System.out.println("[buttonPress=false]");
      // --> System.out.println("[wakeUp=false]");
    } else if (evt.getID() == MouseEvent.MOUSE_WHEEL) {
        buttonPress = true;
       // Process mouse wheel event
    } 
    /* 
       else if (evt.getID() == MouseEvent.MOUSE_MOVED) {
       // Process mouse move event
       }
       else if (evt.getID() == MouseEvent.MOUSE_WHEEL) {
       // Process mouse wheel event
       }
    */
  }
  
  /**
   * All mouse manipulators must implement this.
   */
  public abstract void processStimulus (Enumeration criteria);

    /**
     * Adds this behavior as a MouseListener, mouseWheelListener and MouseMotionListener to
     * the specified component.  This method can only be called if
     * the behavior was created with one of the constructors that takes
     * a Component as a parameter.
     * @param c The component to add the MouseListener, MouseWheelListener and
     * MouseMotionListener to.
     * @exception IllegalStateException if the behavior was not created
     * as a listener
     * @since Java 3D 1.2.1
     */
    public void addListener(Component c) {
	if (!listener) {
	   throw new IllegalStateException(J3dUtilsI18N.getString("Behavior0"));
	}
	c.addMouseListener(this);
	c.addMouseMotionListener(this);
	c.addMouseWheelListener(this);
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {
// 	System.out.println("mousePressed");

	// add new event to the queue
	// must be MT safe
	if (enable) {
	    synchronized (mouseq) {
		mouseq.add(e);
		// only need to post if this is the only event in the queue
		if (mouseq.size() == 1) 
		    postId(MouseEvent.MOUSE_PRESSED);
	    }
	}
    }

    public void mouseReleased(MouseEvent e) {
	// --> System.out.println("mouseReleased");

	// add new event to the queue
	// must be MT safe
	if (enable) {
	    synchronized (mouseq) {
		mouseq.add(e);
		// only need to post if this is the only event in the queue
		if (mouseq.size() == 1) {
                    // --> System.out.println("mouseReleased");
		    postId(MouseEvent.MOUSE_RELEASED);
                }
	    }
	}
    }

    public void mouseDragged(MouseEvent e) {
// 	System.out.println("mouseDragged");

	// add new event to the to the queue
	// must be MT safe.
	if (enable) {
	    synchronized (mouseq) {
		mouseq.add(e);
		// only need to post if this is the only event in the queue
		if (mouseq.size() == 1) 
		    postId(MouseEvent.MOUSE_DRAGGED);
	    }
	}
    }

    public void mouseMoved(MouseEvent e) {}

    public void setEnable(boolean state) {
	super.setEnable(state);
        this.enable = state;
	if (!enable && (mouseq != null)) {
	    mouseq.clear();
	}
    }

    public void mouseWheelMoved(MouseWheelEvent e){
	// --> System.out.println("MouseBehavior : mouseWheel enable = " + enable );
	
	// add new event to the to the queue
	// must be MT safe.
	if (enable) {
	    synchronized (mouseq) {
		mouseq.add(e);
		// only need to post if this is the only event in the queue
		if (mouseq.size() == 1) 
		    postId(MouseEvent.MOUSE_WHEEL);
	    }
	}
    }
    
    public void stdPrintInternalState() {
        
        System.out.println("[BEGIN][buttonPress][enable][flags][invert][listener][reset][wakeUp]");
        System.out.println("[" + buttonPress + "][" + enable + "][" +flags+ "][" + invert+"]["+listener+"]["+reset+"]["+wakeUp+"]");
        System.out.println("[END][buttonPress][enable][flags][invert][listener][reset][wakeUp]");
        //this.mouseCriterion;
        //this.mouseEvents;
        //this.mouseq;
        System.out.println("[BEGIN][currXform]");
        System.out.println(currXform.toString());
        System.out.println("[END][currXform]");
        
        Transform3D t3d =  new Transform3D();
        transformGroup.getTransform(t3d);
        System.out.println("[BEGIN][transformGroup.getTransform]");
        System.out.println(t3d.toString());
        System.out.println("[END][transformGroup.getTransform]");
        
//        System.out.println("[BEGIN][transformX]");
//        System.out.println(transformX.toString());
//        System.out.println("[END][transformX]");
//        
//        System.out.println("[BEGIN][transformY]");
//        System.out.println(transformY.toString());
//        System.out.println("[END][transformY]");
        
        System.out.println("[BEGIN][x][y][x_last][y_last]");
        System.out.println("["+x+"]["+y+"]["+x_last+"]["+y_last+"]");
        System.out.println("[END][x][y][x_last][y_last]");
    }
    
    public Transform3D getCurrXForm() {
        return this.currXform;
    }
    
    public void currXFormSet(Transform3D tr3d) {
        this.currXform = new Transform3D(tr3d);
    }    
}



