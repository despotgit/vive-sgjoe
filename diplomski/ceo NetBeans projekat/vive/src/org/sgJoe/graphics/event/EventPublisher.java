package org.sgJoe.graphics.event;

import java.util.ArrayList;
import org.apache.log4j.Logger;


/*
 * Descritpion for EventPublisher.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: April 27, 2006  9:20 AM  $
 */

public interface EventPublisher {
    
    public void setPDispatcher(EventDispatcher dispatcher);
    
    public EventDispatcher getPDispatcher();
    
    public void onPRegister(Long evtUID);
    
    public ArrayList getEventUIDs4Publish();
    
    public void onPublish(SGEvent event);
    
    public SGEvent createEvent(Long evtUID);
    
    public Long getPublisherUID(); 
    public String getPublisherName();     
    
    public boolean isPTransparent(Long evtUID);
    
    public void setPTransparent(boolean bTransparent, Long evtUID);
}
