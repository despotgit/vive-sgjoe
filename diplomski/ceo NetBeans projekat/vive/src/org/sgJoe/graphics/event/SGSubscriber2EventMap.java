package org.sgJoe.graphics.event;

import org.apache.log4j.Logger;

import java.util.*;

/*
 * Descritpion for SGSubscriber2EventMap.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: April 27, 2006  9:46 AM    $
 */

public class SGSubscriber2EventMap {
    
    private static Logger logger = Logger.getLogger(SGSubscriber2EventMap.class);
    
    protected ArrayList subscriber2event = new ArrayList();
    
    public SGSubscriber2EventMap() { }
    
    public int size() {
        return subscriber2event.size();
    }
    
    public boolean containsEvent(Long evtUID) {
        return subscriber2event.contains(evtUID);
    }
        
    public void add(Long evtUID) {
        subscriber2event.add(evtUID);
    }
    
     public void remove(Long evtUID) {
        subscriber2event.remove(evtUID);
    }

    public Long get(int index) {
        return (Long) subscriber2event.get(index);
    }
    
    public int indexOf(Long evtUID) {
        return subscriber2event.indexOf(evtUID);
    }
    
    public Iterator iterator() {
        return subscriber2event.iterator();
    }
}
