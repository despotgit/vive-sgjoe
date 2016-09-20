/*
 * VToolPanelSuplyer.java
 *
 * Created on Среда, 2006, Мај 24, 13.30
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.sgJoe.tools.interfaces;

import javax.swing.JPanel;

/**
 *Snabdeva GUI manager sa potrebnim panelima za alate
 * @author nikola
 */
public interface VToolPanelSuplyer {
   /**
    *Vreca jedan panel koji predstavlja ToolPanel, kontrole koje se odnose na
    *samo taj alat.
    */
    public JPanel getToolPanel();
   
    /**
    *Vraca niz panela, koji prestavljaju OptionPanele, odnosno raznovrsne kontrole 
    *za taj alat
    */
    public JPanel [ ] getToolOperatorPanelList();
    
    
}
