package org.sgJoe.logic;

import java.util.LinkedList;
import org.apache.log4j.Logger;


/*
 * Description for SGObjectStack.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: March 24, 2006  6:09 PM  $
 */

public class SGObjectStack {
    
    private static Logger logger = Logger.getLogger(SGObjectStack.class);

    private LinkedList uidList = new LinkedList();
    
    public void pushUID(Long sgUID) throws IllegalArgumentException {
        if (sgUID == null) {
            throw new IllegalArgumentException("A sgUID can not be 'null'.");
        }
        
        uidList.addFirst(sgUID);
        
        // System.out.println("[PUSH]" + sgUID);
    }
  

    public Long popUID() 
    {
        if(uidList.size() != 0) 
        {
            Long sgUID = (Long)uidList.removeFirst(); 
            // System.out.println("[POP]" + sgUID);
            return sgUID;
        } 
        else  
        {
            return null;
        }
    }

    public void removeUID(Long sgUID) 
    {
        if(sgUID == null) 
        {
            throw new IllegalArgumentException("A sgUID can not be 'null'.");
        }
        int count = 0;
        while(uidList.contains(sgUID))
        {
            uidList.remove(sgUID);
            // -> System.out.println("[REMOVE][" + (++count) + "]" + sgUID);
        }
        
    }    
  
    public Long peekUID() 
    {
        if(uidList.size() != 0) 
        {
            return (Long)uidList.getFirst();
        } 
        else  {
            return null;
        }        
    }
    
    public int size() {
        return uidList.size();
    }    

    public void clear() {
        uidList.clear();
    }
}
