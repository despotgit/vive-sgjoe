package org.sgJoe.graphics.event;

import org.apache.log4j.Logger;

import java.util.*;

/*
 * Descritpion for SGEventSubscriberMatrix.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: April 27, 2006  9:49 AM    $
 */

public class SGEventSubscriberMatrix {
    
    private static Logger logger = Logger.getLogger(SGEventSubscriberMatrix.class);
    
    protected Hashtable e2s = new Hashtable();
    protected Hashtable s2e = new Hashtable();
    
    public SGEventSubscriberMatrix() {
    }
    
    public boolean containsSubscriber(EventSubscriber subscriber) {
        return s2e.containsKey(subscriber);
    }
    
    public boolean containsSubscriber(EventSubscriber subscriber, Long evtUID) {
        if(containsSubscriber(subscriber)) {
            SGSubscriber2EventMap s2emap = (SGSubscriber2EventMap) s2e.get(subscriber);
            return s2emap.containsEvent(evtUID);
        } else {
            return false;
        }
    }
    
    public boolean containsEvent(Long evtUID) {
        return e2s.containsKey(evtUID);
    }
    
    public boolean containsEvent(Long evtUID, EventSubscriber subscriber) {
        if(containsEvent(evtUID)) {
            SGEvent2SubscriberMap e2smap = (SGEvent2SubscriberMap) e2s.get(evtUID);
            return e2smap.containsSubscriber(subscriber);
        } else {
            return false;
        }
    } 
    
    public void add(EventSubscriber subscriber, Long evtUID) {
        SGEvent2SubscriberMap e2smap = (SGEvent2SubscriberMap) e2s.get(evtUID);
        SGSubscriber2EventMap s2emap = (SGSubscriber2EventMap) s2e.get(subscriber);
        
        if(e2smap == null && s2emap == null) {
            e2smap = new SGEvent2SubscriberMap();
            e2smap.add(subscriber);
            e2s.put(evtUID, e2smap);
            
            s2emap = new SGSubscriber2EventMap();
            s2emap.add(evtUID);
            s2e.put(subscriber, s2emap);
        } else if(e2smap == null && s2emap != null) {
            e2smap = new SGEvent2SubscriberMap();
            e2smap.add(subscriber);
            e2s.put(evtUID, e2smap);
            if(!s2emap.containsEvent(evtUID)) {
                s2emap.add(evtUID);
            }
        } else if(e2smap != null && s2emap == null) {
            s2emap = new SGSubscriber2EventMap();
            s2emap.add(evtUID);
            s2e.put(subscriber, s2emap);            
            if(!e2smap.containsSubscriber(subscriber)) {
                e2smap.add(subscriber);
            }
        } else {
            if(!e2smap.containsSubscriber(subscriber)) {
                e2smap.add(subscriber);
            }
            if(!s2emap.containsEvent(evtUID)) {
                s2emap.add(evtUID);
            }            
        }
    }
    
    public SGEvent2SubscriberMap getSubscribers4Event(Long evtUID) {
        return (SGEvent2SubscriberMap) e2s.get(evtUID);
    }
    
    public SGSubscriber2EventMap getEvents4Subscriber(EventSubscriber subscriber) {
        return (SGSubscriber2EventMap) s2e.get(subscriber);
    }    
    
    public void remove(Long evtUID) {
        if(containsEvent(evtUID)) {
            SGEvent2SubscriberMap e2smap = (SGEvent2SubscriberMap) e2s.get(evtUID);
            // remove event from subscribers lists
            Iterator sIt = e2smap.iterator();
            while(sIt.hasNext()) {
                EventSubscriber subscriber = (EventSubscriber) sIt.next();
                SGSubscriber2EventMap s2emap = (SGSubscriber2EventMap) s2e.get(subscriber);
                s2emap.remove(evtUID);
            }
            e2s.remove(evtUID);
        }
    }
    
    public void remove(EventSubscriber subscriber) {
        if(containsSubscriber(subscriber)) {
            SGSubscriber2EventMap s2emap = (SGSubscriber2EventMap) s2e.get(subscriber);
            // remove subscriber from events lists
            Iterator eIt = s2emap.iterator();
            while(eIt.hasNext()) {
                Long evtUID = (Long) eIt.next();
                SGEvent2SubscriberMap e2smap = (SGEvent2SubscriberMap) e2s.get(evtUID);
                e2smap.remove(subscriber);
            }
            s2e.remove(subscriber);
        }
    }  
    
    public void remove(Long evtUID, EventSubscriber subscriber) {
        SGSubscriber2EventMap s2emap = (SGSubscriber2EventMap) s2e.get(subscriber);
        SGEvent2SubscriberMap e2smap = (SGEvent2SubscriberMap) e2s.get(evtUID);
        s2emap.remove(evtUID);
        e2smap.remove(subscriber);
        if(s2emap.size() == 0) {
            s2e.remove(subscriber);
        }
        if(e2smap.size() == 0) {
            e2s.remove(evtUID);
        }        
    }
    
    public void publish(EventPublisher publisher, Long evtUID) {
        if(containsEvent(evtUID)) {
            SGEvent2SubscriberMap e2smap = (SGEvent2SubscriberMap) e2s.get(evtUID);
            e2smap.publish(publisher, evtUID);
        } 
    }
}
