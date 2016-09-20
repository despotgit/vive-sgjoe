package org.sgJoe.logic;

import java.util.Enumeration;
import java.util.Hashtable;
import org.apache.log4j.Logger;


/*
 * Descritpion for TGAssociationRegsitry.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: March 27, 2006  9:55 AM  $
 */

public class TGAssociationRegsitry {
    
    private static Logger logger = Logger.getLogger(TGAssociationRegsitry.class);
    
    private Hashtable idToAssociation = new Hashtable();
    
    public void putTGAssociation(TGAssociation association) throws IllegalArgumentException {
        if (association == null) {
            throw new IllegalArgumentException("A association can not be 'null'.");
        }
              
        idToAssociation.put(association.getIdentifier(), association);
    }
  

    public TGAssociation getTGAssicoation(Object identifier) {
        if(identifier == null) {
            throw new IllegalArgumentException("A identifier can not be 'null'.");
        }
        return (TGAssociation)idToAssociation.get(identifier);
    }

    public TGAssociation removeTGAssociation(Object identifier) {
        if(identifier == null) {
            throw new IllegalArgumentException("A identifier can not be 'null'.");
        }
        TGAssociation TGassoc = (TGAssociation)idToAssociation.remove(identifier);

        return TGassoc;
    }    

    public void clear() {
        idToAssociation.clear();
    }
    
//    public Enumeration getTGAssociations() {
//        return idToAssociation.elements();
//    }
  
    public int size() {
        return idToAssociation.size();
    }
           
    public Hashtable getTGAssociations() {
        return idToAssociation;
    }
    
}
