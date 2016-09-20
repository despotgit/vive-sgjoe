package org.sgJoe.graphics.event.view;

import javax.media.j3d.View;
import org.apache.log4j.Logger;
import org.sgJoe.graphics.event.EventPublisher;
import org.sgJoe.graphics.event.SGEvent;


/*
 * Descritpion for SGEventViewDistanceChanged.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: June 13, 2006  9:08 PM  $
 */

public class SGEventView extends SGEvent {
    
    private static Logger logger = Logger.getLogger(SGEventView.class);
    
    private View viewRef;    
    
    public SGEventView(Long evtUID, EventPublisher publisher, View viewRef) {
        super(publisher, evtUID);
        this.viewRef = viewRef;
    }  
    
    public SGEventView(EventPublisher publisher, View viewRef) {
        super(publisher, SGEvent.EVT_VIEW);
        this.viewRef = viewRef;
    }  
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(this.getClass().getName() + " [id = " + super.getUID() + "]");
        buffer.append("Publisher [id = " + super.getPublisherRef().getPublisherUID() + "]");
        buffer.append("Publisher [name = " + super.getPublisherRef().getPublisherName() + "]");
        return  buffer.toString();
    }

    public View getViewRef() {
        return viewRef;
    }

}

