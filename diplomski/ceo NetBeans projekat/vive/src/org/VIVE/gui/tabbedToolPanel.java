/*
 * tabbedToolPanel.java
 *
 * Created on Уторак, 2006, Мај 9, 15.14
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.gui;

import org.sgJoe.gui.*;
import org.sgJoe.logic.ToolRegistry;

/**
 *Ovaj panel treba da prikazuje instance alata, prilikom promene fokusa treba
 *da azurira izgled toolOptionPanela
 * @author nikola
 */
public class tabbedToolPanel extends tabbedPanel {
    
    private tabbedPanel toolOptionPanel;
    private ToolRegistry toolRegistryRef;
    /** Creates a new instance of tabbedToolPanel */
    public tabbedToolPanel(tabbedPanel TP, ToolRegistry TRR) {
        super();
        toolOptionPanel = TP;
        toolRegistryRef = TRR;
    }
    
    public void ActivateTool (String toolName)
    {
        
    }
    
    protected void setToolOptionPanels (String toolName){
        ((tabbedOptionPanel)toolOptionPanel).setActiveToolTabs(toolName);
    }    
}
