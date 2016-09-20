package org.sgJoe.graphics.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import org.apache.log4j.Logger;


/*
 * Descritpion for SGEvent2PublisherMap.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: April 30, 2006  9:34 AM    $
 */

public class SGEvent2PublisherMap {
    
    private static Logger logger = Logger.getLogger(SGEvent2PublisherMap.class);
  
    protected LinkedList event2publisher = new LinkedList();
      
    public SGEvent2PublisherMap() { }

    public boolean containsPublisher(EventPublisher publisher) {
        return event2publisher.contains(publisher);
    }
    
    public void add(EventPublisher publisher) {
        event2publisher.add(publisher);
    }    
    
    public void remove(EventPublisher publisher) {
        event2publisher.remove(publisher);
    }
    
    public int size() {
        return event2publisher.size();
    }

    public EventPublisher get(int index) {
        return (EventPublisher) event2publisher.get(index);
    }
    
    public int indexOf(EventPublisher publisher) {
        return event2publisher.indexOf(publisher);
    }
    
    public Iterator iterator() {
        return event2publisher.iterator();
    }    
}
