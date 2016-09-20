package org.sgJoe.logic;

import java.util.Enumeration;
import java.util.Hashtable;
import javax.media.j3d.TransformGroup;
import org.apache.log4j.Logger;
import org.sgJoe.logic.SGObjectInfo;


/*
 * Descritpion for SGObjectRegistry.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: March 24, 2006  5:46 PM  $
 */

public class SGObjectRegistry {
    
    private static Logger logger = Logger.getLogger(SGObjectRegistry.class);
    
    private Hashtable uidToTG = new Hashtable();
    
    public void putObjectTG(TransformGroup objectTG) throws IllegalArgumentException 
    {
        if (objectTG == null) 
        {
            throw new IllegalArgumentException("A objectTG can not be 'null'.");
        }
        
        SGObjectInfo info = (SGObjectInfo) objectTG.getUserData();
        if (info == null) 
        {
            throw new IllegalArgumentException("A object info can not be 'null'.");
        }        
        
        uidToTG.put(info.getSGUID(), objectTG);
        
        System.out.println("[ADDED]" + info);
    }
  

    public TransformGroup getObjectTG(Long sgUID) 
    {
        if(sgUID == null) 
        {
            throw new IllegalArgumentException("A sgUID can not be 'null'.");
        }
        return (TransformGroup)uidToTG.get(sgUID);
    }

    public TransformGroup removeObjectTG(Long sgUID) 
    {
        if(sgUID == null) 
        {
            throw new IllegalArgumentException("A sgUID can not be 'null'.");
        }
        TransformGroup objTG = (TransformGroup)uidToTG.remove(sgUID);
        
        // --> SGObjectInfo info = (SGObjectInfo) objTG.getUserData();
        // --> System.out.println("[REMOVED]" + info);
        
        return objTG;
    }    

    public void clear() 
    {
        uidToTG.clear();
    }
    
    public Enumeration getObjectTGs() {
        return uidToTG.elements();
    }
  
    public int size() {
        return uidToTG.size();
    }
        
}
