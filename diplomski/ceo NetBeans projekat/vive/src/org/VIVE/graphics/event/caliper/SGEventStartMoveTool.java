/*
 * SGEventStartMoveTool.java
 *
 * Created on Ponedeljak, 2006, Juni 5, 15.32
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.graphics.event.caliper;

import org.apache.log4j.Logger;
import org.sgJoe.graphics.event.*;

/**
 *
 * @author Lakicevic Milos
 */
public class SGEventStartMoveTool extends SGEvent{
    
    private static Logger logger = Logger.getLogger(SGEventStartMoveTool.class);
    
    /** Creates a new instance of SGEventStartMoveTool */
    public SGEventStartMoveTool(EventPublisher publisher, Long evtUID) {
        super(publisher,evtUID);
    }
    
}
