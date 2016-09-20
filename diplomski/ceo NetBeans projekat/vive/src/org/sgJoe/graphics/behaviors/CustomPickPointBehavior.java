package org.sgJoe.graphics.behaviors;

import com.sun.j3d.utils.picking.*;
import java.awt.AWTEvent;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import javax.media.j3d.*;
import javax.vecmath.*;
import org.apache.log4j.Logger;
import org.sgJoe.graphics.*;


/*
 * Descritpion for CustomPickPointBehavior.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: March 2, 2006  9:06 PM     $
 */
public class CustomPickPointBehavior extends CustomBehavior implements GraphicsConstants{
    
    private static Logger logger = Logger.getLogger(CustomPickPointBehavior.class);
    
    private int ray_x, ray_y;
    private int ray_x_last, ray_y_last;
   
    private double x, y, z;
    private double x_last, y_last, z_last;
    
    
    private BaseSceneGraph graph = null;
    private WakeupCondition condition = null;
    private TransformGroup transformGroup = null;
    
    private boolean reset = false;
    
    private int navigationPlane = 1;

    public CustomPickPointBehavior(BaseSceneGraph graph) {
        this.graph = graph;
        this.reset = true;
        x = y = z = x_last = y_last = z_last = 0.0;
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
      ray_x = 0; ray_y = 0;
      ray_x_last = 0; ray_y_last = 0;
      
      x = y = z = 0.0;
      x_last = y_last = z_last = 0.0;
      
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
    System.out.println("ps CustomPickPointBehavior-a.");  
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
          if (eventId == MouseEvent.MOUSE_PRESSED || 
                  eventId == MouseEvent.MOUSE_DRAGGED ||
                    eventId == MouseEvent.MOUSE_RELEASED) {
              int ray_dx, ray_dy;
              ray_x = evt.getX();
              ray_y = evt.getY();
              ray_dx = ray_x - ray_x_last;
              ray_dy = ray_y - ray_y_last;
              
              // use the BaseSceneGraph's picking method
              PickResult pickResult = graph.getClosestIntersection(ray_x, ray_y);

              transformGroup = null;
              if (pickResult != null) {
                transformGroup = this.getLastTransformGroup(pickResult);

                int interNum = pickResult.numIntersections();
                // for(int i = 0; i < interNum; i++) {
                for(int i = 0; i < 1; i++) {
                    PickIntersection pickInter = pickResult.getIntersection(i);
                    // --> System.out.println("distance from eye" + pickInter.getDistance());
                    Point3d intPt = pickInter.getPointCoordinatesVW();
                    
                    if(intPt != null) {
                        // --> System.out.println(intPt);
                        
                        x_last = x;
                        y_last = y;
                        z_last = z;
                        
                        x = intPt.x;
                        y = intPt.y;
                        z = intPt.z;
                    }
                }
              }              
              
              ray_x_last = ray_x;
              ray_y_last = ray_y;
            
              // if we have some observer set, apply the update method
              if (behaviorObserver != null) {
                  // get current values
                  Point3d point3d = new Point3d(x, y, z);

                  // call update from observer
                  behaviorObserver.update(point3d);
              
              }
              
            }
          
        }
      }
    }
    wakeupOn(condition);
  }  
}

