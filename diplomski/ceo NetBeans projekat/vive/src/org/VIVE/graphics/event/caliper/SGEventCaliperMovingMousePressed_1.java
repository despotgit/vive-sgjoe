/*
 * SGeventStopMoveCaliper.java
 *
 * Created on June 4, 2006, 6:18 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.graphics.event.caliper;

import javax.media.j3d.View;
import javax.vecmath.Point3d;
import org.apache.log4j.Logger;
import org.sgJoe.graphics.event.*;
import org.sgJoe.graphics.event.mouse.*;

/**
 *
 * @author Milos Lakicevic
 */
public class SGEventCaliperMovingMousePressed_1  extends SGEventMouse {
    
   private static Logger logger = Logger.getLogger(SGEventCaliperMovingMousePressed.class);
    
    public SGEventCaliperMovingMousePressed_1(EventPublisher publisher, int x, int y, Point3d lPt, Point3d wPt, double dst, View viewRef) {
        super(SGEvent.EVT_CALIPER_MOVING_MOUSE_PRESSED, publisher, x, y, lPt, wPt, dst, viewRef);
    } 
}
