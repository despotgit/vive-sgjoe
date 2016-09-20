package org.sgJoe.graphics.behaviors;

import java.util.HashMap;
import org.apache.log4j.Logger;


/*
 * Descritpion for sgJBehaivorState.java
 *
 *
 * @author   $ Author: Aleksandar Babic             $
 * @version  $ Revision:             0.1            $
 * @date     $ Date: February 20, 2006  11:28 AM    $
 */

public abstract class SGBehaviorState {
    
    private static Logger logger = Logger.getLogger(SGBehaviorState.class);
    
    private HashMap propertyMap = null;
    
    public SGBehaviorState() {
        propertyMap = new HashMap(0);
    }
    
    public SGBehaviorState(int capacity) {
        propertyMap = new HashMap(capacity);
    }    
    
    public void addProperty(Object key, Object value) {
        propertyMap.put(key, value);
    }
    
    public Object getProperty(Object key) {
        if(propertyMap.containsKey(key)) {
            return propertyMap.get(key);
        } else {
            return null;
        }
    }
    
    public void removeProperty(Object key) {
        if(propertyMap.containsKey(key)) {
            propertyMap.remove(key);
        }  
    }
    
    public boolean containsProperty(Object key) {
        return propertyMap.containsKey(key);
    }    
}
