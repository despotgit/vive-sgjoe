package org.sgJoe.logic;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import org.apache.log4j.Logger;


/*
 * Descritpion for ToolRegistry.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: April 2, 2006  4:15 PM     $
 */

public class ToolRegistry 
{
    
    private static Logger logger = Logger.getLogger(ToolRegistry.class);
    
    private Hashtable registry = new Hashtable();
    
    public void registerTool(Tool tool) throws IllegalArgumentException 
    {
        if (tool == null) 
        {
            throw new IllegalArgumentException("A module can not be 'null'.");
        }
        registry.put(tool.getName(), tool);
    }

    public Tool getTool(String name) 
    {
        return (Tool)registry.get(name);
    }

    public Enumeration getTools() {
        return registry.elements();
    }
  
    /**
     * Return the number of loaded modules.
     *
     * @return int The number of loaded modules.
     */
    public int size() {
        return registry.size();
    }    
    
    public Iterator getIterator() {
        return registry.values().iterator();
    }
}
