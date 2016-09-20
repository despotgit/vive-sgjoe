package org.sgJoe.utils;

import java.util.Hashtable;
import java.util.Iterator;
import javax.media.j3d.TransformGroup;
import org.apache.log4j.Logger;


/*
 * Descritpion for TGHash.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: May 29, 2006  9:20 PM  $
 */

public class TGHash {
    
    private static Logger logger = Logger.getLogger(TGHash.class);
    
    protected Hashtable hashtable = null;
    
    protected TGHash() {
        hashtable = new Hashtable();
    }
    
    public int size() {
        return hashtable.size();
    }
    
    public boolean isEmpty() {
        return hashtable.isEmpty();
    }
    
    protected void add(String name, TransformGroup tg) {
        hashtable.put(name, tg);
    }
    
    protected TransformGroup remove(String name) {
        return (TransformGroup) hashtable.remove(name);
    }
    
    protected TransformGroup get(String name) {
        return (TransformGroup) hashtable.get(name);
    }
    
    protected boolean contains(TransformGroup tg) {
        return hashtable.contains(tg);
    }
    
    protected boolean containsKey(String name) {
        return hashtable.containsKey(name);
    }
    
    protected Iterator iterator() {
        return hashtable.values().iterator();
    }    
    
}
