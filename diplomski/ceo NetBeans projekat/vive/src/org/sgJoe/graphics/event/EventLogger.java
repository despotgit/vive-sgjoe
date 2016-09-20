package org.sgJoe.graphics.event;

import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.sgJoe.graphics.event.mouse.SGEventMouseOver;


/*
 * Descritpion for EventLogger.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: May 2, 2006  12:08 PM  $
 */

public class EventLogger implements EventSubscriber {
    
    private static Logger logger = Logger.getLogger(EventLogger.class);

    private EventDispatcher dispatcher;
    
    public EventLogger() {
        this.dispatcher = EventDispatcher.getDispatcher();
        this.dispatcher.register(this);
    }

    public void onSRegister(Long evtUID) {
    }

    public ArrayList getEvents() {
        // add all possible events .... 
        ArrayList evtList = new ArrayList();
        evtList.add(SGEventMouseOver.EVT_MOUSE_OVER);
        evtList.add(SGEventMouseOver.EVT_MOUSE_DRAGGED);
        evtList.add(SGEvent.EVT_VIEW);
        return evtList;        
    }

    public void onEvent(SGEvent event) {
        System.out.println(event);
    }

    public void setSDispatcher(EventDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }
    
}
