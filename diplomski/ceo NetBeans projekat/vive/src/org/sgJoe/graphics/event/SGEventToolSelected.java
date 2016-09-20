package org.sgJoe.graphics.event;

import org.apache.log4j.Logger;
import org.sgJoe.tools.interfaces.*;


/*
 * Descritpion for SGEventToolSelected.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: May 10, 2006  1:02 PM  $
 */

public class SGEventToolSelected extends SGEvent {
    
    private static Logger logger = Logger.getLogger(SGEventToolSelected.class);
    
    private VirTool virToolRef = null;
    
    public SGEventToolSelected(EventPublisher publisher, VirTool virToolRef) {
        super(publisher, SGEvent.EVT_TOOL_SELECTED);
        this.virToolRef = virToolRef;
    } 
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SGEventToolSelected [id = " + this.getUID() + "]");
        return  buffer.toString();
    }    
}