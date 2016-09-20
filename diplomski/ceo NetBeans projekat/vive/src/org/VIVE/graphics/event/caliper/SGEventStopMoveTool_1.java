/*
 * SGEventStopMoveTool.java
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
public class SGEventStopMoveTool_1 extends SGEvent{
    
    private static Logger logger = Logger.getLogger(SGEventStopMoveTool.class);
    
    /** Creates a new instance of SGEventStopMoveTool */
    public SGEventStopMoveTool_1(EventPublisher publisher, Long evtUID) {
        super(publisher,evtUID);
    }
    
}
