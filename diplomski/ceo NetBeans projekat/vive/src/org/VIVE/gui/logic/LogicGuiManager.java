/*
 * logicGuiManager.java
 *
 * Created on Четвртак, 2006, Мај 11, 17.52
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.gui.logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.VIVE.gui.logic.interfaces.iLogicalCamera;
import org.VIVE.gui.logic.interfaces.iToolBox;
import org.VIVE.gui.logic.interfaces.iToolOperators;
import org.VIVE.gui.logic.interfaces.iToolPanel;
import org.VIVE.gui.logic.interfaces.iViewPort;
import org.VIVE.gui.addingToolPanel;
import org.VIVE.gui.tabbedPanel;
import org.sgJoe.graphics.SceneGraphEditor;
import org.sgJoe.logic.*;
import org.sgJoe.tools.interfaces.*;
import org.sgJoe.tools.main.*;

/**
 *Ova klasa sluzi za upravljanje GUI-em. Ona govori koji pane kad i sta radi, sa izuzetkom povezanosti panela,
 *recimo ako se dodaje main alat onda (ovaj) manager samo pozove metodu iz iToolBox-a a sam iToolBox dalje
 *odradi pozive po tabPanelima...
 * @author nikola
 */
public class LogicGuiManager implements ActionListener {
    
    //ova polja su interfejsi (koji predstavljaju logiku, konkretne implementacije sadrze jedno polje tipa panel
    //koje predstavlja prikaz panela) za sada izplaniranih gui komponenata
    //skoro sve sam implementirao
    private iToolBox toolBox;
    private iToolPanel toolpanel;
    private iToolOperators toolOperator;
    private iLogicalCamera logicalCamera;
    private iViewPort viewPort;
    private ToolRegistry toolRegistryRef = null;
    private tabbedPanel ActiveToolList;
    private tabbedPanel ActiveOptionList;
    
    private addingToolPanel atp = null;
    
    /**
     * Creates a new instance of LogicGuiManager
     */
    public LogicGuiManager(ToolRegistry TRR) {
        //TODO: dodati inicijalizacije svih ostalih polja
        toolRegistryRef = TRR;
        MainVirTool MainTool = new MainVirTool();
        MainTool.setToolName("Main");
        
        setActiveToolList(new tabbedPanel());
        ActiveToolList.getTab().addActionListener(this);
        setActiveOptionList(new tabbedPanel());
        toolpanel = new ToolPanel(ActiveToolList);
        
        toolOperator = new ToolOperatorPanel(ActiveOptionList);
        
        atp = new addingToolPanel(TRR, getActiveToolList(), this);
        toolBox = new ToolBox(atp, getActiveToolList(), getActiveOptionList());
        if(getToolBox() != null) 
        {
            getToolBox().createMain(MainTool);            
        }
    }
    
    public void setSGEditor(SceneGraphEditor editor) {
        this.atp.setSGEditor(editor);
    }
    //Umesto ovih Object parametara treba da stoji VirTool il neka klasa koja najbolje opisuje alat. zaboravio sam koja
    //Ovu metodu bi zvao kada se promeni fokus na alatu
    public void setFocusOnTool (VirTool virTool)
    {
        System.out.println("[LogicGuiManager.setFocusOnTool][VitTool ID=" + virTool.getToolUID() + "]");
        if(getToolPanel() != null) 
        {
            getToolPanel().setFocusOnTool(virTool);
        }
        if(getToolOperator() != null) 
        {
            getToolOperator().setOperators(virTool);
        }
        
    }
    
    //Ovu zoves kada se izbacuje alat sa scene
    public void toolRemoved (VirTool virTool) 
    {
        if(getToolOperator() != null) 
        {
            getToolPanel().removeTool(virTool);
            if (getToolOperator().getActiveOperatorName() == virTool.toString()) 
                getToolOperator().setOperators(this.getTool(getToolPanel().getActiveToolName()));            
        }
    }
    //i nema vise :) sve u svemu dve metode. to bi mozda mogle da se upakuje
    //u setup/update model, samo ne znam kako...

    private VirTool getTool(String toolName) {
        return null;
    }
    
    public void addTool(VirTool tool){
        toolpanel.addTool(tool);
        toolOperator.setOperators(tool);
    }
 
    public ToolRegistry getToolRegistryRef() {
        return toolRegistryRef;
    }

    public void setToolRegistryRef(ToolRegistry toolRegistryRef) {
        this.toolRegistryRef = toolRegistryRef;
    }

    public tabbedPanel getActiveToolList() {
        return ActiveToolList;
    }

    public void setActiveToolList(tabbedPanel ActiveToolList) {
        this.ActiveToolList = ActiveToolList;
    }

    public tabbedPanel getActiveOptionList() {
        return ActiveOptionList;
    }

    public void setActiveOptionList(tabbedPanel ActiveOptionList) {
        this.ActiveOptionList = ActiveOptionList;
    }

    public iToolBox getToolBox() {
        return toolBox;
    }

    public iToolPanel getToolPanel() {
        return toolpanel;
    }

    public iToolOperators getToolOperator() {
        return toolOperator;
    }

    public iLogicalCamera getLogicalCamera() {
        return logicalCamera;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().compareToIgnoreCase("close")==0){
            System.out.println("Ugasen je neki tab, treba informisati FMW");
        } else
        if (e.getActionCommand().compareToIgnoreCase("select")==0){
            System.out.println("Selektovan je neki tab, treba azurirati Operatore");
        }
    }


    
}
