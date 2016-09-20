/*
 * ToolPanel.java
 *
 * Created on Петак, 2006, Мај 26, 12.12
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.gui.logic;


import java.awt.event.ActionEvent;
import java.util.Hashtable;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.VIVE.gui.logic.interfaces.iTabbChange;
import org.VIVE.gui.tabbedPanel;
import org.VIVE.gui.logic.interfaces.iToolPanel;
import org.netbeans.swing.tabcontrol.TabbedContainer;
import org.sgJoe.tools.interfaces.VirTool;

/**
 *
 * @author nikola
 */
public class ToolPanel implements iToolPanel{
    
    private tabbedPanel tPanel;
    private Hashtable tabela;
    private String activeToolName;
    
    
    /**
     * Creates a new instance of ToolPanel
     */
    public ToolPanel(tabbedPanel TP) {
        setTPanel(TP);
        //tPanel.setTabChange(this);
    }

    public void addTool(VirTool tool) {
        JPanel tmpPanel = tool.getToolPanel();
        if (tmpPanel == null) {
            tmpPanel = new JPanel();
            tmpPanel.add(new JLabel("Tool Panel not supported for this tool"));
        }
        tPanel.addTab(tool.getInstanceName(),tmpPanel);
    }

    public void addNonClosableTool(VirTool tool) {
        
    }

    public void removeTool(VirTool tool) {
        
    }

    public void setFocusOnTool(VirTool tool) {
        System.out.println("Treba da se promeni tab");
        String imeAlata = tool.getInstanceName();
        tPanel.setActiveTab(imeAlata);
        activeToolName = imeAlata;
    }

    public String getActiveToolName() {
        return null;
    }

    public Object getSGPanel() {
        return null;
    }

    public void setSGPanel(Object sgPanel) {
    }

    public void setToolActive(boolean b) {
    }

    public void setTab(String name, JPanel panel) {
    }

    public boolean getToolActive() {
        return false;
    }

    public JPanel getTabPanel(String name) {
        return getTPanel().getTabPanel(name);
    }

    public tabbedPanel getTPanel() {
        return tPanel;
    }

    public void setTPanel(tabbedPanel tPanel) {
        this.tPanel = tPanel;
    }

    public Hashtable getTabela() {
        return tabela;
    }

    public void setTabela(Hashtable tabela) {
        this.tabela = tabela;
    }

    public String getActivetoolName() {
        return activeToolName;
    }

    public void setActivetoolName(String activetoolName) {
        this.activeToolName = activetoolName;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().compareToIgnoreCase("close")==0){
            System.out.println("Ugasen je neki tab, treba informisati FMW");
        } else
        if (e.getActionCommand().compareToIgnoreCase("select")==0){
            System.out.println("Selektovan je neki tab, treba azurirati Operatore");
        }
    }

    /*public void Tabbchange(ActionEvent e) {
        if (e.getActionCommand().compareToIgnoreCase("close")==0){
            System.out.println("Ugasen je neki tab, treba informisati FMW");
        } else
        if (e.getActionCommand().compareToIgnoreCase("select")==0){
            System.out.println("Selektovan je neki tab, treba azurirati Operatore");
        }
    }*/
}
