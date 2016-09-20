/*
 * SGEventMoveTool.java
 *
 * Created on Ponedeljak, 2006, Juni 5, 15.35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.graphics.event.caliper;

import org.apache.log4j.Logger;
import org.sgJoe.graphics.event.*;

/**
 *
 * @author admin
 */
public class SGEventMoveTool_1 extends SGEvent{
    
      
    private static Logger logger = Logger.getLogger(SGEventMoveTool.class);
    
    /** Creates a new instance of SGEventMoveTool */
    public SGEventMoveTool_1(EventPublisher publisher, Long evtUID) {
        super(publisher,evtUID);
    }
}
