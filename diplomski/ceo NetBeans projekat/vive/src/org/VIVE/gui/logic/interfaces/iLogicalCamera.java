/*
 * iLogicalCamera.java
 *
 * Created on Четвртак, 2006, Мај 11, 17.30
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.gui.logic.interfaces;

/**
 *
 * @author nikola
 */
public interface iLogicalCamera {
    public Object[] getRegisteredViews();
    public String   getRegisteredToolName();
    public Object getSGPanel ();    
    public void setSGPanel (Object sgPanel);
    public void setRegisteredTool (Object tool);
}
