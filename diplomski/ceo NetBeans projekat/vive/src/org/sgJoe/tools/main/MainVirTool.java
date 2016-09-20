package org.sgJoe.tools.main;

import org.VIVE.gui.toolOperators.Visibility;
import javax.media.j3d.BranchGroup;
import javax.swing.JPanel;
import org.VIVE.gui.toolOperators.interfaces.iVisibility;
import org.VIVE.gui.toolPanels.toolViewMain;
import org.apache.log4j.Logger;

import org.sgJoe.tools.interfaces.*;

/*
 * Descritpion for MainVirTool.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: May 22, 2006  5:23 PM  $
 */

public class MainVirTool extends VirTool implements iVisibility {
    
    private static Logger logger = Logger.getLogger(MainVirTool.class);
    private JPanel mainToolPanel;
    private JPanel[] mainOptionPanel;
    
    private static long counter = 0;
    
    public MainVirTool() {
        super();
        // --> this.setInstanceName("MainTool#" + VToolFactory.generateVirToolUID());
        this.setInstanceName("MainTool#" + generateUID());
        this.setToolDescription("MainVirTool - don't ask me");
        mainToolPanel = new toolViewMain();
        mainOptionPanel = new JPanel[1];
        String[] aStr = new String[2];
        aStr[0] = "Frame on";
        aStr[1] = "Frame off";
        mainOptionPanel[0] = new Visibility(aStr, this);
    }

    public void createToolInstance(String string) {
        this.setVToolRef(new MainVTool(this));
        BranchGroup bg = new BranchGroup();
        bg.addChild(this.getVToolRef());
        this.toolBaseTG.addChild(bg);
    }
    
    public void createForm() {
    }

    public void createVUIForm() {
    }

    public void createPlugin() {
    }

    public void createOperatorsForm() {
    }

    public JPanel getMainToolPanel() {
        return mainToolPanel;
    }

    public void setMainToolPanel(JPanel mainToolPanel) {
        this.mainToolPanel = mainToolPanel;
    }

    public JPanel[] getMainOptionPanel() {
        return mainOptionPanel;
    }

    public void setMainOptionPanel(JPanel[] mainOptionPanel) {
        this.mainOptionPanel = mainOptionPanel;
    }
    
    public JPanel getToolPanel(){
        return mainToolPanel;
    }

    public JPanel[] getToolOperatorPanelList(){
        return mainOptionPanel;
    }

    public void setFocus() {
    }

    public long generateUID() {
        return 0;
    }

    public void setVisibilityLevel(int level) {
        //TODO: set visibility za Main alat
    }
}
