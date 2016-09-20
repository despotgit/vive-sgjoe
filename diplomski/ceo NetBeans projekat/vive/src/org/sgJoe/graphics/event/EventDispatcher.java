package org.sgJoe.graphics.event;

import org.apache.log4j.Logger;
import java.util.*;

/*
 * Descritpion for SGEventDispatcher.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: April 27, 2006  9:20 AM  $
 */

public class EventDispatcher {
    
    private static Logger logger = Logger.getLogger(EventDispatcher.class);
   
    protected static EventDispatcher instance = new EventDispatcher();
    
    protected SGEventSubscriberMatrix es = new SGEventSubscriberMatrix();
//    protected SGEventPublisherMatrix ep = new SGEventPublisherMatrix();
    
    private EventDispatcher() { }
    
    public static EventDispatcher getDispatcher() {
        return instance;
    }
    
//    private SGEventPublisherMatrix getEPMatrix() {
//        return ep;
//    }
    
    private SGEventSubscriberMatrix getESMatrix() {
        return es;
    }
    
//    public void register(EventPublisher publisher) {
//        ArrayList events = publisher.getEvents();
//        Iterator iT = events.iterator();
//        while(iT.hasNext()) {
//            SGEvent event = (SGEvent) iT.next();
//            this.register4publish(publisher, event);
//        }
//    }
    
    public void register(EventSubscriber subscriber) {
        ArrayList events = subscriber.getEvents();
        Iterator iT = events.iterator();
        while(iT.hasNext()) {
            Long evtUID = (Long) iT.next();
            this.register4subscribe(subscriber, evtUID);
        }
    }
    
//    private void register4publish(EventPublisher publisher, SGEvent event) {
//        getDispatcher().getEPMatrix().add(publisher, event);
//        publisher.onRegister(event);
//    }
    
    public void register4subscribe(EventSubscriber subscriber, Long evtUID) {
        getDispatcher().getESMatrix().add(subscriber, evtUID);
        subscriber.onSRegister(null);
    }
    
//    public void unregister(EventPublisher publisher) {
//        getDispatcher().getEPMatrix().remove(publisher);
//    }
    
//    public void unregister(SGEvent event, EventPublisher publisher) {
//        getDispatcher().getEPMatrix().remove(event, publisher);
//    }
    
    public void unregister(EventSubscriber subscriber) {
        getDispatcher().getESMatrix().remove(subscriber);
    }
    
    public void unregister(Long evtUID, EventSubscriber subscriber) {
        getDispatcher().getESMatrix().remove(evtUID, subscriber);
    }    
    
    public void publish(EventPublisher publisher, Long evtUID) {
        getDispatcher().getESMatrix().publish(publisher, evtUID);
    }
}
