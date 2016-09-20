package org.sgJoe.graphics.event;

import javax.media.j3d.View;
import javax.vecmath.Point3d;
import org.apache.log4j.Logger;


/*
 * Descritpion for SGEvent.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: April 27, 2006  9:58 AM  $
 */

public class SGEvent {
    
    private static Logger logger = Logger.getLogger(SGEvent.class);
    
    public final static Long EVT_MOUSE_OVER = new Long(1),
                             EVT_MOUSE_PRESSED = new Long(2),
                             EVT_MOUSE_CLICKED = new Long(3),
                             EVT_MOUSE_DRAGGED = new Long(4),
                             EVT_MOUSE_RELEASED = new Long(5),
                             EVT_TOOL_SELECTED = new Long(6),
                             
                             EVT_VIEW = new Long(7),
                             EVT_VIEW_DISTANCE = new Long(8),
                             
                             //caliper announce start of movement to ather calipers  
                             EVT_START_MOVE_CALIPER = new Long(101),                          
                             //tool(not caliper) announces start of movement to calipers
                             EVT_START_MOVE_TOOL = new Long(102),
                             //tool(not caliper) announces movement to calipers
                             EVT_MOVE_TOOL = new Long(103),
                             //tool(not caliper) announces finalization of movement to calipers
                             EVT_STOP_MOVE_TOOL = new Long(104),
                             //caliper announces finalization of movement to calipers
                             EVT_CALIPER_MOVING_MOUSE_PRESSED = new Long(105);
    
    protected Long UID = null;
    protected EventPublisher publisherRef = null;
    
    public SGEvent(EventPublisher publisher, long uid) {
        publisherRef = publisher;
        UID = new Long(uid);
    }

    public SGEvent(EventPublisher publisher, Long evtUID) {
        publisherRef = publisher;
        UID = evtUID;
    }
    
    public EventPublisher getPublisherRef() {
        return publisherRef;
    }

    public Long getUID() {
        return UID;
    }
    
    public String toString() {
        return "SGEvent";
    }
    
    
}
