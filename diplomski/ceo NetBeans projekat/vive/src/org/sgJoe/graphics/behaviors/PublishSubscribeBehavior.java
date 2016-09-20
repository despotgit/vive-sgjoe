package org.sgJoe.graphics.behaviors;

import org.apache.log4j.Logger;

import com.sun.j3d.utils.picking.*;
import java.awt.AWTEvent;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import javax.media.j3d.*;
import javax.vecmath.*;
import org.sgJoe.graphics.*;
import org.sgJoe.graphics.event.*;
import org.sgJoe.graphics.event.mouse.*;
import org.sgJoe.tools.decorators.*;


/*
 * Descritpion for PublishSubscribeBehavior.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: April 30, 2006  6:26 PM    $
 */

public class PublishSubscribeBehavior extends CustomBehavior {
    
    private static Logger logger = Logger.getLogger(PublishSubscribeBehavior.class);
    int X, Y, prevX, prevY;
    private WakeupCondition condition = null;
    private BaseSceneGraph graph = null;
    private TransformGroup transformGroup = null;    
    private PickResult pickResult = null;
    private PickIntersection pickIntersection = null;
    
    // adendum 
    PickResult[] pickResultArray = null;
    
    public PublishSubscribeBehavior(BaseSceneGraph graph) {
        this.graph = graph;
        WakeupCriterion[] criteria = new WakeupCriterion[5];
        criteria[0] = new WakeupOnAWTEvent(MouseEvent.MOUSE_MOVED);
        criteria[1] = new WakeupOnAWTEvent(MouseEvent.MOUSE_PRESSED);
        criteria[2] = new WakeupOnAWTEvent(MouseEvent.MOUSE_CLICKED);        
        criteria[3] = new WakeupOnAWTEvent(MouseEvent.MOUSE_DRAGGED);
        criteria[4] = new WakeupOnAWTEvent(MouseEvent.MOUSE_RELEASED);
      
        condition = new WakeupOr(criteria);        
    }
    
    public void initialize() {
       wakeupOn(condition);    
    }

    public void setNavigationPlane(int plane) { }

    public void setDirection(int direction) { }
    
    public void reset() { 
        X = Y = prevX = prevY = 0;
    }
  
  public void processStimulus(Enumeration criteria) 
  {
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
          
          
          X = evt.getX();
          Y = evt.getY();
          
          int dx = prevX - X;
          int dy = prevY - Y;
                       
          switch(eventId) 
          {
              case MouseEvent.MOUSE_MOVED:
                  if (((Math.abs(dx) > 1) || (Math.abs(dy) > 1) )) 
                  {
                      this.publishEvent(MouseEvent.MOUSE_MOVED, SGEvent.EVT_MOUSE_OVER);
                  }
                  break;
              case MouseEvent.MOUSE_PRESSED:
                  this.publishEvent(MouseEvent.MOUSE_PRESSED, SGEvent.EVT_MOUSE_PRESSED);
                  break;
              case MouseEvent.MOUSE_CLICKED:
                  this.publishEvent(MouseEvent.MOUSE_CLICKED, SGEvent.EVT_MOUSE_CLICKED);
                  break;
              case MouseEvent.MOUSE_DRAGGED:
                  this.publishEvent(MouseEvent.MOUSE_DRAGGED, SGEvent.EVT_MOUSE_DRAGGED);
                  break;
              case MouseEvent.MOUSE_RELEASED:
                  this.publishEvent(MouseEvent.MOUSE_RELEASED, SGEvent.EVT_MOUSE_RELEASED);
                  break;
          }
          
          prevX = X;
          prevY = Y;
      }
    }
    wakeupOn(condition);
  }
  
  public boolean isPublisher4Event(TransformGroup publisher, int eventId) 
  {
      
      if( !(publisher instanceof MouseEventPublisher) ) 
      {
          return false;
      }
        
      switch(eventId) 
      {
          case MouseEvent.MOUSE_MOVED:
              return transformGroup instanceof MouseOverPublisher;
          case MouseEvent.MOUSE_PRESSED:
              return publisher instanceof MousePressedPublisher;
          case MouseEvent.MOUSE_CLICKED:
              return publisher instanceof MouseClickedPublisher;
          case MouseEvent.MOUSE_DRAGGED:
              return publisher instanceof MouseDraggedPublisher;
          case MouseEvent.MOUSE_RELEASED:
              return publisher instanceof MouseReleasedPublisher;
          default:
              return false;
      }
  }
  
  public void publishEvent(int eventID, Long messageID) {
      
      // use the BaseSceneGraph's picking method that reports all picked objects
              // --> pickResult = graph.getClosestIntersection(X, Y);
              
              pickResultArray = graph.getAllSorted(X, Y);
              
              if(pickResultArray != null) 
              {
                  for(int i = 0; i < pickResultArray.length; i++) 
                  {
                      
                      pickResult = pickResultArray[i];
                      
                      //transformGroup = null;
                      pickIntersection = pickResult.getIntersection(0);
                      transformGroup = this.getLastTransformGroup(pickResult, false);
                  
                      if(transformGroup != null ) 
                      {//&& transformGroup instanceof EventPublisher) {
                  //if(transformGroup instanceof MouseOverPublisher) {
                  if( isPublisher4Event(transformGroup, eventID) ) 
                  {
                              
                      MouseEventPublisher publisher = (MouseEventPublisher) transformGroup;
                          
                              publisher.setX(X);
                              publisher.setY(Y);
                              publisher.setDistance(pickIntersection.getDistance());
                              publisher.setLPt(pickIntersection.getPointCoordinates());
                              publisher.setWPt(pickIntersection.getPointCoordinatesVW());
                              publisher.setView(graph.getViewInFocus());

                      publisher.getPDispatcher().publish(publisher, messageID);
                              
                      if(!publisher.isPTransparent(messageID)) 
                      {
                                  break;
                      }
                  } 
                  else 
                  {
                              // when it is not of publisher type.. step out it blocks
                              break;
                  }                     
                      }                                    
      }
    }
  }      
}
