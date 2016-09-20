package org.sgJoe.graphics.event.mouse;

import javax.media.j3d.View;
import javax.vecmath.Point3d;
import org.apache.log4j.Logger;
import org.sgJoe.graphics.event.*;


/*
 * Descritpion for SGEventMouse.java
 *
 *
 * @author   $ Author: Aleksandar Babic     $
 * @version  $ Revision:             0.1    $
 * @date     $ Date: May 18, 2006  9:51 AM  $
 */

public class SGEventMouse extends SGEvent {
    
    private static Logger logger = Logger.getLogger(SGEventMouse.class);
    
    private int X;
    private int Y;
    private Point3d localPt;
    private Point3d worldPt;
    private double distance;
    private View viewRef;    
    
    public SGEventMouse(Long evtUID, EventPublisher publisher, int x, int y, Point3d lPt, Point3d wPt, double dst, View viewRef) {
        super(publisher, evtUID);
        this.X = x;
        this.Y = y;
        this.localPt = lPt;
        this.worldPt = wPt;
        this.distance = dst;
        this.viewRef = viewRef;
    }  
    
        
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(this.getClass().getName() + " [id = " + super.getUID() + "]");
        buffer.append("Publisher [id = " + super.getPublisherRef().getPublisherUID() + "]");
        buffer.append("Publisher [name = " + super.getPublisherRef().getPublisherName() + "]");
        buffer.append("Screen [" + X + " , " + Y + "]");
        buffer.append("Local [" + localPt +"]");
        buffer.append("World [" + worldPt +"]");
        buffer.append("Distance [" + distance +"]");
        return  buffer.toString();
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public Point3d getLocalPt() {
        return localPt;
    }

    public Point3d getWorldPt() {
        return worldPt;
    }

    public double getDistance() {
        return distance;
    }

    public View getViewRef() {
        return viewRef;
    }

}
