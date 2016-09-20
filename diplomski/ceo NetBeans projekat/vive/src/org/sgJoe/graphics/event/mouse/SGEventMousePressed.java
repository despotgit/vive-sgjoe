package org.sgJoe.graphics.event.mouse;

import javax.media.j3d.View;
import javax.vecmath.Point3d;
import org.apache.log4j.Logger;
import org.sgJoe.graphics.event.EventPublisher;
import org.sgJoe.graphics.event.SGEvent;
import org.sgJoe.graphics.event.mouse.SGEventMouse;


/*
 * Descritpion for SGEventMousePressed.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: May 18, 2006  10:12 AM  $
 */

public class SGEventMousePressed extends SGEventMouse {
    
    private static Logger logger = Logger.getLogger(SGEventMousePressed.class);
    
    public SGEventMousePressed(EventPublisher publisher, int x, int y, Point3d lPt, Point3d wPt, double dst, View viewRef) {
        super(SGEvent.EVT_MOUSE_PRESSED, publisher, x, y, lPt, wPt, dst, viewRef);
    }  
    
}

