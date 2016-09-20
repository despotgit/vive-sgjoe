package org.sgJoe.graphics.event;

import java.util.ArrayList;
import org.apache.log4j.Logger;


/*
 * Descritpion for EventSubscriber.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: April 27, 2006  9:20 AM  $
 */

public interface EventSubscriber {
    
    public void setSDispatcher(EventDispatcher dispatcher);
    
    public void onSRegister(Long evtUID);
    
    public ArrayList getEvents();
    
    public void onEvent(SGEvent event);
    
}
