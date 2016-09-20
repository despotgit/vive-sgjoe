package org.sgJoe.plugin;

import org.apache.log4j.Logger;
import org.sgJoe.logic.ActionErrors;
import org.sgJoe.logic.Session;


/*
 * Descritpion for CreateToolForm.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: April 2, 2006  2:48 PM     $
 */

public class CreateToolForm extends Form{
    
    private static Logger logger = Logger.getLogger(CreateToolForm.class);
    
    private String toolName = null;
    private String toolInstanceName = null;
    
    public void reset(Session session) { 
        this.toolName = null;
        this.toolInstanceName = null;
    }
  
    public ActionErrors validate(Session session) {
        return null;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public String getToolInstanceName() {
        return toolInstanceName;
    }

    public void setToolInstanceName(String toolInstanceName) {
        this.toolInstanceName = toolInstanceName;
    }
    
}
