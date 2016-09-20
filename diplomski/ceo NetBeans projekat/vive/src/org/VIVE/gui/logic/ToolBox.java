/*
 * ToolBox.java
 *
 * Created on Четвртак, 2006, Мај 25, 13.31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.gui.logic;

import javax.swing.JPanel;
import org.VIVE.gui.addingToolPanel;
import org.VIVE.gui.tabbedPanel;
import org.VIVE.gui.logic.interfaces.iToolBox;
import org.VIVE.gui.logic.interfaces.iToolPanel;
import org.sgJoe.tools.interfaces.VirTool;

/**
 *
 * @author nikola
 */
public class ToolBox implements iToolBox{
    
    private addingToolPanel toolPanel;
    private tabbedPanel ActiveToolList;
    private tabbedPanel ActiveOptionList;
    
    /** Creates a new instance of ToolBox */
    public ToolBox(addingToolPanel ATP,tabbedPanel ATL,tabbedPanel AOL) 
    {
        toolPanel = ATP;
        ActiveToolList = ATL;
        ActiveOptionList = AOL;
    }

    public void createTool(VirTool Tool) 
    {
        toolPanel.createTool(Tool);
    }

    public void createMain(VirTool Tool) 
    {
        JPanel tmp = Tool.getToolPanel();
        ActiveToolList.addTab("Main",tmp);
        for (int i = 0;i<Tool.getToolOperatorPanelList().length;i++)
            ActiveOptionList.addTab("Visibility",Tool.getToolOperatorPanelList()[i]);
    }

    public void setToolRegistry(Object toolRegistry) 
    {
        
    }

    public void setToolPanel(Object toolPanel) 
    {
        this.toolPanel = (addingToolPanel) toolPanel;
    }

    public Object getToolRegistry() {
        return null;
    }

    public Object getToolPanel() {
         return toolPanel;
    }

    public Object getSGPanel() {
        return toolPanel;
    }

    public void setSGPanel(Object sgPanel) {
        toolPanel = (addingToolPanel) sgPanel;
    }

    public void setActiveToolList(iToolPanel yourTabbedPanel) {
    }

    public int getToolcount(int i) {
        return 0;
    }

    public void setToolCount(int i, int value) {
        
    }

    public void incrementToolCount(int i) {
    }

    public boolean decrementToolCount(int i) {
        return false;
    }

    public void setToolPanel(addingToolPanel toolPanel) {
        this.toolPanel = toolPanel;
    }
    
}
