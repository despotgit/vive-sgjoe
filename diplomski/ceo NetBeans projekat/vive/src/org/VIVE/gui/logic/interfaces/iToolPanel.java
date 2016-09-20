/*
 * iToolPanel.java
 *
 * Created on Четвртак, 2006, Мај 11, 17.29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.gui.logic.interfaces;

import javax.swing.JPanel;
import org.sgJoe.tools.interfaces.*;

/**
 * 
 * @author nikola
 */
public interface iToolPanel {
    
    public void addTool (VirTool tool);
    public void addNonClosableTool (VirTool tool);
    public void removeTool (VirTool tool);
    public void setFocusOnTool (VirTool tool);
    public String getActiveToolName ();
    
    public Object getSGPanel ();    
    public void setSGPanel (Object sgPanel);
    
    //Preuzeto iz tabbedPanela, da bi se izdvoila logika od GUI-a
    //public void addTab (String name, JPanel panel);
    //public void removeTab(String name);

    public void setToolActive (boolean b);
    public void setTab (String name, JPanel panel);
    //public void setActiveTab(String name);
    
    public boolean getToolActive ();
    public JPanel getTabPanel (String name);
}
