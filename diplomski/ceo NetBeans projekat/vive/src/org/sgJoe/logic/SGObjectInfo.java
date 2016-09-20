package org.sgJoe.logic;

import java.util.Hashtable;
import org.apache.log4j.Logger;
import org.sgJoe.graphics.*;


/*
 * Descritpion for sgObjectInfo.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: March 21, 2006  11:37 AM   $
 */

public class SGObjectInfo {
    
    private static Logger logger = Logger.getLogger(SGObjectInfo.class);
    public final static int ACT_NONE = 0,
                            ACT_TRANSLATE = 1,
                            ACT_ROTATE = 2,
                            ACT_SCALE = 3;
    
    private Long sgUID;
    private String sgName;
    private TGAssociation association = null;
    
    private boolean bActuator = false;
    private int actuatorType = ACT_NONE;
    
    //private TGAssociationRegsitry associations;
    
    public SGObjectInfo(long UID) {
        sgUID = new Long(UID);
        //associations = new TGAssociationRegsitry();
    }
    
//    public Hashtable getTGAssociations() {
//        return associations.getTGAssociations();
//    }

    public Long getSGUID() {
        return sgUID;
    }
    
    public String getSGName() {
        return sgName;
    }

    public void setSGName(String sgName) {
        this.sgName = sgName;
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        
        if(sgUID != null) {
            buffer.append("[SGUID=" + sgUID + "]");
        }

        if(sgName != null) {
            buffer.append("[SGNAME=" + sgName + "]");
        }        
        
        return buffer.toString();
    }
    
    public void registerTGAssociation(TGAssociation assoc) {
        //associations.putTGAssociation(assoc);
        association = assoc;
    }
    
    public TGAssociation getTGAssociation() {
        return association;
    }

    public boolean isActuator() {
        return bActuator;
    }

    public void setActuator(boolean bActuator) {
        this.bActuator = bActuator;
    }

    public int getActuatorType() {
        return actuatorType;
    }

    public void setActuatorType(int actuatorType) {
        this.actuatorType = actuatorType;
    }
    
}
