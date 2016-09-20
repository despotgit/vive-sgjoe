package org.sgJoe.tools;

import java.util.Collection;
import java.util.Hashtable;
import org.sgJoe.tools.interfaces.VirTool;

import org.apache.log4j.Logger;

/*
 * Descritpion for VirToolMap.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: April 23, 2006  7:04 PM    $
 */

public class VirToolMap {
    
    private static Logger logger = Logger.getLogger(VirToolMap.class);

    private Hashtable mapName2VRT = new Hashtable();
    
    public VirToolMap() { }
    
    public void put(VirTool virTool) throws IllegalArgumentException 
    {
        if (virTool == null) 
        {
            throw new IllegalArgumentException("A virTool can not be 'null'.");
        }
        
        mapName2VRT.put(virTool.getInstanceName(), virTool);
    }
  
    public VirTool get (String name) 
    {
        if(name == null) 
        {
            throw new IllegalArgumentException("A name can not be 'null'.");
        }
        return (VirTool)mapName2VRT.get(name);
    }

    public VirTool remove (String name) 
    {
        if(name == null) 
        {
            throw new IllegalArgumentException("A name can not be 'null'.");
        }
        VirTool virTool = (VirTool)mapName2VRT.remove(name);
        
        return virTool;
    }    

    public Collection getValues() 
    {
        return mapName2VRT.values();
    }
    
    public void clear() 
    {
        mapName2VRT.clear();
    }

    public int size() {
        return mapName2VRT.size();
    }        
    
}
