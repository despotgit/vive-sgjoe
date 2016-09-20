package org.sgJoe.utils;

import java.util.Iterator;
import java.util.LinkedList;
import javax.media.j3d.TransformGroup;
import org.apache.log4j.Logger;


/*
 * Descritpion for Queue.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: May 29, 2006  3:10 PM  $
 */

public class TGQueue {
    
    private static Logger logger = Logger.getLogger(TGQueue.class);
        
    protected LinkedList list = null;
    protected int size;
    
    protected TGQueue(int max) {
        if(max < 1) {
            this.size = 1;
        } else {
            this.size = max;
        }
        
        list = new LinkedList();
    }
    
    protected void add(TransformGroup tg) {
        if(list.size() == size) {
            list.removeFirst();
        }
        list.addFirst(tg);
    }
    
    protected TransformGroup get() {
        return (TransformGroup) list.getFirst();
    }

    protected TransformGroup get(int index) {
        if(index < 0 || index >= list.size()) {
            return null;
        }
        return (TransformGroup) list.get(index);
    }
    protected void remove(TransformGroup tg) {
        list.remove(tg);
    }
    
    protected TransformGroup removeFirst() {
        return (TransformGroup) list.removeFirst();
    }
    
    public Iterator iterator() {
        return list.iterator();
    }
    
    public boolean isEmpty() {
        return list.isEmpty();
    }
    
    public int size() {
        return list.size();
    }    
    
    protected void removeAll(TransformGroup tg) {
        while(list.contains(tg)) {
            list.remove(tg);
        }
    }
}
