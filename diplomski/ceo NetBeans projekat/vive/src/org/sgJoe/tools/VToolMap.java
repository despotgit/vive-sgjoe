package org.sgJoe.tools;

import java.util.Iterator;
import org.apache.log4j.Logger;
import org.sgJoe.tools.interfaces.VTool;
import org.sgJoe.utils.TGHash;


/*
 * Descritpion for VToolMap.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: May 30, 2006  9:37 PM  $
 */

public class VToolMap extends TGHash {
    
    private static Logger logger = Logger.getLogger(VToolMap.class);
    
    
    protected VToolMap() {
        super();
        }
        
    public void addTool(String name, VTool tool) {
        hashtable.put(name, tool);
    }
  
    public VTool removeTool(String name) {
        return (VTool) super.remove(name);
    }

    protected VTool getTool(String name) {
        return (VTool) super.get(name);
        }
        
    public boolean containsTool(VTool tool) {
        return super.contains(tool);
    }    

    public boolean containsTool(String name) {
        return super.containsKey(name);
    }

}

