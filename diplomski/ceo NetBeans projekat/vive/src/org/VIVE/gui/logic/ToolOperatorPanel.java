/*
 * ToolOperatorPanel.java
 *
 * Created on Понедељак, 2006, Јуни 19, 17.56
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.gui.logic;

import java.util.Hashtable;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.VIVE.gui.logic.interfaces.iToolOperators;
import org.VIVE.gui.tabbedPanel;
import org.sgJoe.tools.interfaces.VirTool;

/**
 * Pasivna klasa, samo predstavlja prostor za prikazivanje tabova. Ne radi nista
 * pametno.
 * @author nikola
 */
public class ToolOperatorPanel implements iToolOperators {
    
    private tabbedPanel tPanel;
    /** Creates a new instance of ToolOperatorPanel */
    public ToolOperatorPanel(tabbedPanel TP) {
        tPanel = TP;
    }

    public void setOperators(VirTool tool) {
        JPanel[] tmpOption = tool.getToolOperatorPanelList();
        String[] imena = new String[1];
        if (tmpOption == null) 
        {
            tmpOption = new JPanel[1];
            tmpOption[0]= new JPanel();
            tmpOption[0].add(new JLabel("Option Panels not supported for this tool"));
            imena[0] = "noLabel";
        }
        
        //Treba da pogasim sve tabove koji nisu podrzani
        //Pogasim sve pa ih ponovo upalim
        //tPanel.hideAllTabs();
        tPanel.closeAllTabs();
        for (int i = 0; i< tmpOption.length;i++)
        {
                tPanel.addTab(tmpOption[i].getClass().getName(),tmpOption[i]);
        }
    }

    public void setActiveOperator() {
        
    }

    public String getActiveOperatorName() {
        return null;
    }

    public String[] getOperatorsNames() {
        return null;
    }

    public Object getSGPanel() {
        return null;
    }

    public void setSGPanel(Object sgPanel) {
    }

    
}
