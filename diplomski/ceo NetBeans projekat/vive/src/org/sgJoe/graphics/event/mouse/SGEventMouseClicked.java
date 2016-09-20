package org.sgJoe.graphics.event.mouse;

import javax.media.j3d.View;
import javax.vecmath.Point3d;
import org.apache.log4j.Logger;
import org.sgJoe.graphics.event.EventPublisher;
import org.sgJoe.graphics.event.SGEvent;
import org.sgJoe.graphics.event.mouse.SGEventMouse;

/*
 * Descritpion for SGEventMouseClicked.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: May 18, 2006  10:58 AM  $
 */

public class SGEventMouseClicked extends SGEventMouse {
    
    private static Logger logger = Logger.getLogger(SGEventMouseClicked.class);

    public SGEventMouseClicked(EventPublisher publisher, int x, int y, Point3d lPt, Point3d wPt, double dst, View viewRef) {
        super(SGEvent.EVT_MOUSE_CLICKED, publisher, x, y, lPt, wPt, dst, viewRef);
    }      
    
}
