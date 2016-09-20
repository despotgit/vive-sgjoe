/*
 * CaliperEventPublisher.java
 *
 * Created on June 17, 2006, 10:29 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.graphics.event.caliper;

import javax.vecmath.Point3d;
import org.sgJoe.graphics.event.EventPublisher;

/**
 *
 * @author Milos Lakicevic
 */
public interface CaliperEventPublisher extends EventPublisher{
    
    public void setWPt(Point3d lPt);
    
}
