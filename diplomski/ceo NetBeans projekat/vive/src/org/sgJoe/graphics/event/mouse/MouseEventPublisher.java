/*
 * MouseEventPublisher.java
 *
 * Created on May 17, 2006, 5:50 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.sgJoe.graphics.event.mouse;

import javax.media.j3d.View;
import javax.vecmath.Point3d;
import org.sgJoe.graphics.event.*;

/**
 *
 * @author ealebab
 */
public interface MouseEventPublisher extends EventPublisher {
    
    public void setX(int X);
    public void setY(int Y);
    
    public void setLPt(Point3d lPt);
    public void setWPt(Point3d lPt);
  
    public void setDistance(double distance);
    public void setView(View view);    
    
}
