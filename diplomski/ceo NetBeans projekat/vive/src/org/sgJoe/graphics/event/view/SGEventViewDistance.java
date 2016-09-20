package org.sgJoe.graphics.event.view;

import javax.media.j3d.View;
import org.apache.log4j.Logger;
import org.sgJoe.graphics.event.EventPublisher;
import org.sgJoe.graphics.event.SGEvent;


/*
 * Descritpion for SGEventViewDistance.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: June 13, 2006  9:14 PM  $
 */


public class SGEventViewDistance extends SGEventView {
    
    private static Logger logger = Logger.getLogger(SGEventViewDistance.class);
    
    public SGEventViewDistance(EventPublisher publisher, View viewRef) {
        super(SGEvent.EVT_VIEW_DISTANCE, publisher, viewRef);
    }  
    
}
