package org.sgJoe.tools;

import org.apache.log4j.Logger;
import org.sgJoe.tools.decorators.VToolHandle;
import org.sgJoe.utils.TGHash;


/*
 * Descritpion for VToolHandleMap.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: May 30, 2006  9:54 PM  $
 */

    
public class VToolHandleMap extends TGHash {
    
    private static Logger logger = Logger.getLogger(VToolHandleMap.class);
        

    public VToolHandleMap() {
        super();
        }
    
    public void addToolHandle(String name, VToolHandle handle) {
        super.add(name, handle);
    }

    public VToolHandle removeToolHandle(String name) {
        return (VToolHandle) super.remove(name);
        }
        
    public VToolHandle getToolHandle(String name) {
        return (VToolHandle) super.get(name);
    }    

    public boolean containsTooHandle(VToolHandle handle) {
        return super.contains(handle);
    }

    public boolean containsToolHandle(String name) {
        return super.containsKey(name);
    }    
        
}
