/*
 * iToolOperators.java
 *
 * Created on Четвртак, 2006, Мај 11, 17.34
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.gui.logic.interfaces;

import org.sgJoe.tools.interfaces.*;

/**
 *
 * @author nikola
 */
public interface iToolOperators {
    public void setOperators (VirTool Tool);
    public void setActiveOperator ();
    public String getActiveOperatorName ();
    public String[] getOperatorsNames ();
    
    public Object getSGPanel ();    
    public void setSGPanel (Object sgPanel);
}
