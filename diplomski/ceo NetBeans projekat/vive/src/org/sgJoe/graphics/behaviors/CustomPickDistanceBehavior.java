package org.sgJoe.graphics.behaviors;

import com.sun.j3d.utils.picking.PickIntersection;
import com.sun.j3d.utils.picking.PickResult;
import java.awt.AWTEvent;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupCondition;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.media.j3d.WakeupOr;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import org.apache.log4j.Logger;
import org.sgJoe.graphics.BaseSceneGraph;
import org.sgJoe.graphics.GraphicsConstants;

import org.apache.log4j.Logger;


/*
 * Descritpion for CustomPickDistanceBehavior.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: March 5, 2006  3:10 PM     $
 */
public class CustomPickDistanceBehavior extends CustomBehavior implements GraphicsConstants{
    
    private static Logger logger = Logger.getLogger(CustomPickDistanceBehavior.class);
    
    private int ray_x, ray_y;
    private int ray_x_last, ray_y_last;
   
    private double x, y, z;
    private double x_last, y_last, z_last;
    
    private Point3d currPt = null;
    private Point3d prevPt = null;
    private double ptDistance = 0.0;
    
    private BaseSceneGraph graph = null;
    private WakeupCondition condition = null;
    private TransformGroup transformGroup = null;
    
    private boolean reset = false;
    
    private int navigationPlane = 1;

    public CustomPickDistanceBehavior(BaseSceneGraph graph) {
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
    currPt = null;
    prevPt = null;
    ptDistance = 0.0;
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
    System.out.println("ps CustomPickDistanceBehavior-a.");
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
                    Point3d intPt = pickInter.getPointCoordinatesVW();
                    if(intPt != null) {
                        // --> System.out.println(intPt);
                        
                        x_last = x;
                        y_last = y;
                        z_last = z;
                        
                        x = intPt.x;
                        y = intPt.y;
                        z = intPt.z;
                        
                        if(eventId == MouseEvent.MOUSE_RELEASED) {
                            prevPt = currPt;
                            currPt = new Point3d(x, y, z);
                        }
                    }
                }
              }              
              
              ray_x_last = ray_x;
              ray_y_last = ray_y;
            
              // if we have some observer set, apply the update method
              if (behaviorObserver != null) {
                  // calculate distance - if aplicable
                  if(currPt != null && prevPt != null) {
                      ptDistance = currPt.distance(prevPt);
                      
                      // call update fromobserver
                      behaviorObserver.update(currPt, prevPt);
                      
                      //graph.createLine3d(prevPt, currPt, new Vector3d(0.0, 0.0, 0.0));
                      
                      graph.createCylinderLine3D(prevPt, currPt, 0.1, new Vector3d(0.0, 0.0, 0.0));
                                            
                      // reset previous point val;ues
                      prevPt = null;
                      currPt = null;
                  }
              
              }
              
            }
          
        }
      }
    }
    wakeupOn(condition);
  }  
  
    public void setEnable(boolean enable) {
        super.setEnable(enable);
        this.prevPt = null;
        this.currPt = null;
        this.ptDistance = 0.0;
    }  
}

