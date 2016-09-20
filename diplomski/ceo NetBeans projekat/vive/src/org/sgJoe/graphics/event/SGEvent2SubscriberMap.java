package org.sgJoe.graphics.event;

import org.apache.log4j.Logger;

import java.util.*;

/*
 * Descritpion for SGEvent2SubsciberMap.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: April 27, 2006  9:39 AM    $
 */

public class SGEvent2SubscriberMap {
    
    private static Logger logger = Logger.getLogger(SGEvent2SubscriberMap.class);
    
    protected LinkedList event2subscriber = new LinkedList();
    
    public SGEvent2SubscriberMap() { }
    
    public int size() {
        return event2subscriber.size();
    }    
    
    public boolean containsSubscriber(EventSubscriber subscriber) {
        return event2subscriber.contains(subscriber);
    }
    
    public void add(EventSubscriber subscriber) {
        event2subscriber.add(subscriber);
    }
    
    public void remove(EventSubscriber subscriber) {
        event2subscriber.remove(subscriber);
    } 

    public EventSubscriber get(int index) {
        return (EventSubscriber) event2subscriber.get(index);
    }
    
    public int indexOf(EventSubscriber subscriber) {
        return event2subscriber.indexOf(subscriber);
    }
    
    public Iterator iterator() {
        return event2subscriber.iterator();
    } 
    
    public void publish(EventPublisher publisher, Long evtUID) 
    {
        // work with list copy so that in onEvents You can manipulate with original one
        LinkedList tmpList = new LinkedList(event2subscriber);
        SGEvent event = publisher.createEvent(evtUID);
        if(event == null) 
        {
            return;
        }
        Iterator sIt = tmpList.iterator();
        while(sIt.hasNext()) 
        {
            EventSubscriber subscriber = (EventSubscriber) sIt.next();
                subscriber.onEvent(event);
        }
    }
}
