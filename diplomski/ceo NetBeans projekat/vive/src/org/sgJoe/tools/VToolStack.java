package org.sgJoe.tools;

import org.apache.log4j.Logger;
import org.sgJoe.tools.interfaces.VTool;
import org.sgJoe.utils.*;


/*
 * Descritpion for VToolStack.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: May 29, 2006  10:06 PM  $
 */

public class VToolStack extends TGQueue {
    
    private static Logger logger = Logger.getLogger(VToolStack.class);
    
    private TGQueue stack = null;
    
    public VToolStack(int stackSize) {
        super(stackSize);
    }
    
    public void push(VTool vt) {
        super.add(vt);
    }
    
    public VTool pop() {
        return (VTool) super.removeFirst();
    }
    
    public VTool peek() {
        return (VTool) super.get();
    }
    
    protected VTool peek(int index) {
        return (VTool) super.get(index);
    }
    
    public void remove(VTool vt) {
        super.remove(vt);
    }
    
    public void removeAll(VTool vt) {
        super.removeAll(vt);
    }
    
}
