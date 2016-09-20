package org.sgJoe.graphics.event;

import java.util.Hashtable;
import java.util.Iterator;
import org.apache.log4j.Logger;


/*
 * Descritpion for SGEvent2PublisherMatrix.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: April 30, 2006  9:37 AM    $
 */

public class SGEventPublisherMatrix {
    
    private static Logger logger = Logger.getLogger(SGEventPublisherMatrix.class);

    protected Hashtable e2p = new Hashtable();
    protected Hashtable p2e = new Hashtable();    
    
    public SGEventPublisherMatrix() { }

    public boolean containsPublisher(EventPublisher publisher) {
        return p2e.containsKey(publisher);
    }
    
    public boolean containsPublisher(EventPublisher publisher, SGEvent event) {
        if(containsPublisher(publisher)) {
            SGPublisher2EventMap p2emap = (SGPublisher2EventMap) p2e.get(publisher);
            return p2emap.containsEvent(event);
        } else {
            return false;
        }
    }    
    
    public boolean containsEvent(SGEvent event) {
        return e2p.containsKey(event);
    }
    
    public boolean containsEvent(SGEvent event, EventPublisher publisher) {
        if(containsEvent(event)) {
            SGEvent2PublisherMap e2pmap = (SGEvent2PublisherMap) e2p.get(event);
            return e2pmap.containsPublisher(publisher);
        } else {
            return false;
        }
    } 
    public void add(EventPublisher publisher, SGEvent event) {
        SGEvent2PublisherMap e2pmap = (SGEvent2PublisherMap) e2p.get(event);
        SGPublisher2EventMap p2emap = (SGPublisher2EventMap) p2e.get(publisher);
        
        if(e2pmap == null && p2emap == null) {
            
            e2pmap = new SGEvent2PublisherMap();
            e2pmap.add(publisher);
            e2p.put(event, e2pmap);
            
            p2emap = new SGPublisher2EventMap();
            p2emap.add(event);
            p2e.put(publisher, p2emap);
            
        } else if(e2pmap == null && p2emap != null) {
            
            e2pmap = new SGEvent2PublisherMap();
            e2pmap.add(publisher);
            e2p.put(event, e2pmap);
            if(!p2emap.containsEvent(event)) {
                p2emap.add(event);
            }
            
        } else if(e2pmap != null && p2emap == null) {
            
            p2emap = new SGPublisher2EventMap();
            p2emap.add(event);
            p2e.put(publisher, p2emap);            
            if(!e2pmap.containsPublisher(publisher)) {
                e2pmap.add(publisher);
            }
        } else {
            
            if(!e2pmap.containsPublisher(publisher)) {
                e2pmap.add(publisher);
            }
            if(!p2emap.containsEvent(event)) {
                p2emap.add(event);
            }        
            
        }
    }    
    
    public SGEvent2PublisherMap getPublisher4Event(SGEvent event) {
        return (SGEvent2PublisherMap) e2p.get(event);
    }
    
    public SGPublisher2EventMap getEvents4Publisher(EventPublisher publisher) {
        return (SGPublisher2EventMap) p2e.get(publisher);
    }
    
    public void remove(SGEvent event) {
        if(containsEvent(event)) {
            SGEvent2PublisherMap e2pmap = (SGEvent2PublisherMap) e2p.get(event);
            // remove event from publishers lists
            Iterator pIt = e2pmap.iterator();
            while(pIt.hasNext()) {
                EventPublisher publisher = (EventPublisher) pIt.next();
                SGPublisher2EventMap p2emap = (SGPublisher2EventMap) p2e.get(publisher);
                p2emap.remove(event);
            }
            e2p.remove(event);
        }
    }
    
    public void remove(EventPublisher publisher) {
        if(containsPublisher(publisher)) {
            SGPublisher2EventMap p2emap = (SGPublisher2EventMap) p2e.get(publisher);
            // remove publisher from events lists
            Iterator pIt = p2emap.iterator();
            while(pIt.hasNext()) {
                SGEvent event = (SGEvent) pIt.next();
                SGEvent2PublisherMap e2pmap = (SGEvent2PublisherMap) e2p.get(event);
                e2pmap.remove(publisher);
            }
            p2e.remove(publisher);
        }
    }  
    
    public void remove(SGEvent event, EventPublisher publisher) {
        SGPublisher2EventMap p2emap = (SGPublisher2EventMap) p2e.get(publisher);
        SGEvent2PublisherMap e2pmap = (SGEvent2PublisherMap) e2p.get(event);
        p2emap.remove(event);
        e2pmap.remove(publisher);
        if(p2emap.size() == 0) {
            p2e.remove(publisher);
        }
        if(e2pmap.size() == 0) {
            e2p.remove(event);
        }        
    }    
}
