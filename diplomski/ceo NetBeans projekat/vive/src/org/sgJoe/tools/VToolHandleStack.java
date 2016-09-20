package org.sgJoe.tools;

import org.apache.log4j.Logger;
import org.sgJoe.tools.decorators.VToolHandle;
import org.sgJoe.utils.*;


/*
 * Descritpion for VToolHandleStack.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: May 29, 2006  10:18 PM  $
 */

public class VToolHandleStack extends TGQueue {
    
    private static Logger logger = Logger.getLogger(VToolHandleStack.class);
    
    private TGQueue stack = null;
    
    public VToolHandleStack(int stackSize) {
        super(stackSize);
    }
    
    public void push(VToolHandle vtHandle) {
        super.add(vtHandle);
    }
    
    public VToolHandle pop() {
        return (VToolHandle) super.removeFirst();
    }
    
    public VToolHandle peek() {
        return (VToolHandle) super.get();
    }

    protected VToolHandle peek(int index) {
        return (VToolHandle) super.get(index);
    }
    
    public void remove(VToolHandle vtHandle) {
        super.remove(vtHandle);
    }
    
    public void removeAll(VToolHandle vtHandle) {
        super.removeAll(vtHandle);
    }
    
}

