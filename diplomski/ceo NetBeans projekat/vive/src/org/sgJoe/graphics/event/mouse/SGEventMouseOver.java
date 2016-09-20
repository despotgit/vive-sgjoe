package org.sgJoe.graphics.event.mouse;

import javax.media.j3d.View;
import javax.vecmath.Point3d;
import org.apache.log4j.Logger;
import org.sgJoe.graphics.event.*;


/*
 * Descritpion for SGEventMouseOver.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: May 1, 2006  6:47 PM  $
 */

public class SGEventMouseOver extends SGEventMouse {
    
    private static Logger logger = Logger.getLogger(SGEventMouseOver.class);
    
    public SGEventMouseOver(EventPublisher publisher, int x, int y, Point3d lPt, Point3d wPt, double dst, View viewRef) {
        super(SGEvent.EVT_MOUSE_OVER, publisher, x, y, lPt, wPt, dst, viewRef);
    }  
    
}
