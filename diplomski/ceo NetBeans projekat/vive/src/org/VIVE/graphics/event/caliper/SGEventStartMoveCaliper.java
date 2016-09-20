/*
 * SGEventStartMoveCaliper.java
 *
 * Created on Èetvrtak, 2006, Juni 1, 14.51
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.graphics.event.caliper;

import javax.vecmath.Point3d;
import org.apache.log4j.Logger;
import org.sgJoe.graphics.event.*;



/**
 *
 * @author Lakicevic Milos
 */
public class SGEventStartMoveCaliper extends SGEvent{
    
     private static Logger logger = Logger.getLogger(SGEventStartMoveCaliper.class); 
    
     private Point3d worldPt;    
    
     /** Creates a new instance of SGEventStartMoveCaliper */
     public SGEventStartMoveCaliper(Long evtUID, EventPublisher publisher,Point3d wPt) {
        super(publisher, evtUID);
        this.worldPt = wPt;        
    }  
      
     public Point3d getWorldPt() {
        return worldPt;
    }
    
}
