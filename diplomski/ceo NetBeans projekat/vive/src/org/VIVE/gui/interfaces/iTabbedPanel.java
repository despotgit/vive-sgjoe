/*
 * iTabbedPanel.java
 *
 * Created on Четвртак, 2006, Мај 11, 18.24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.gui.interfaces;

import javax.swing.JPanel;

/**
 *
 * @author nikola
 */
public interface iTabbedPanel {
    public void addTab (String name, JPanel panel);
    public void removeTab(String name);

    public void setToolActive (boolean b);
    public void setTab (String name, JPanel panel);
    public void setActiveTab(String name);
    
    public boolean getToolActive ();
    public JPanel getTabPanel (String name);
}
