/*
 * iAddingToolPanel.java
 *
 * Created on Четвртак, 2006, Мај 11, 18.29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.gui.interfaces;

import org.VIVE.gui.tabbedPanel;


/**
 *
 * @author nikola
 */
public interface iAddingToolPanel {
    //public void setActiveToolList (tabbedPanel yourTabbedPanel);
    
    public int getToolcount (int i);
    
    public void setToolCount (int i, int value);
    
    public void incrementToolCount(int i);
    
    public boolean decrementToolCount(int i);
    
}
