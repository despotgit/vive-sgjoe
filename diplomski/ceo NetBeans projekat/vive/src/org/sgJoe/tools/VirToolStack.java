package org.sgJoe.tools;

import java.util.Iterator;
import java.util.LinkedList;
import javax.media.j3d.TransformGroup;
import org.apache.log4j.Logger;
import org.sgJoe.tools.interfaces.VirTool;
/*
 * Descritpion for VirToolStack.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: May 29, 2006  9:40 PM  $
 */

public class VirToolStack {
    
    private static Logger logger = Logger.getLogger(VirToolStack.class);
    
    protected LinkedList list = null;
    protected int size;
    
    public VirToolStack(int stackSize) {
        if(stackSize < 1) {
            this.size = 1;
        } else {
            this.size = stackSize;
        }
        
        this.list = new LinkedList();
    }
    
    public void push(VirTool vt) {
        if(list.size() == size) {
            list.removeLast();
        }

        list.addFirst(vt);      
    }
    
    public VirTool peek() {
        if(list.size() != 0) {
            return (VirTool) list.getFirst();
        } else {
            return null;
        }
        
    }
    
    public VirTool peek(int index) {
        if(index < 0 || index >= list.size()) {
            return null;
        }
        return (VirTool) list.get(index);
    }
    
    public VirTool pop() {
        return (VirTool) list.removeFirst();
    } 
    
    public void remove(VirTool vt) {
        list.remove(vt);
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
    
    public void removeAll(VirTool vt) {
        while(list.contains(vt)) {
            list.remove(vt);
        }
    }
}

