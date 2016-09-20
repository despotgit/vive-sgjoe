package org.sgJoe.graphics.event;

import org.apache.log4j.Logger;

import java.util.*;


/*
 * Descritpion for SGPublisher2EventMap.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: April 30, 2006  9:37 AM    $
 */

public class SGPublisher2EventMap {
    
    private static Logger logger = Logger.getLogger(SGPublisher2EventMap.class);

    protected ArrayList publisher2event = new ArrayList();
    
    public SGPublisher2EventMap() { }
    
    public boolean containsEvent(SGEvent event) {
        return publisher2event.contains(event);
    }
        
    public void add(SGEvent event) {
        publisher2event.add(event);
    }        
    
    public void remove(SGEvent event) {
        publisher2event.remove(event);
    }

    public int size() {
        return publisher2event.size();
    }
    
    public SGEvent get(int index) {
        return (SGEvent) publisher2event.get(index);
    }
    
    public int indexOf(SGEvent event) {
        return publisher2event.indexOf(event);
    }
    
    public Iterator iterator() {
        return publisher2event.iterator();
    }    
}