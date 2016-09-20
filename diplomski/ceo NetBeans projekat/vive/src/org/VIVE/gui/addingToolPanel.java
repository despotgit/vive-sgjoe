package org.VIVE.gui;
/*
 * addingToolPanel.java
 *
 * Created on ��������, 2006, �� 4, 13.01
 *
 *Ovaj panel bi trebalo da iz neke konfiguracije ucita sve postojece alate
 *(koji mogu da se instanciraju). Takodje ucita ikonice i tool tip text.
 *Treba da zna kako se pravi nova instanca alata(recimo salje zahtev FW).
 *Vodi evidenciju o broju kreiranih alata, pri svakom sledecem kreiranju
 *uz ime stavlja broj (alat1, alat2, alat3...)
 *
 */



import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import javax.swing.*;
import javax.swing.border.Border;
import org.sgJoe.graphics.SceneGraphEditor;
import org.sgJoe.gui.*;
import org.VIVE.gui.interfaces.iAddingToolPanel;
import org.VIVE.gui.logic.LogicGuiManager;
import org.sgJoe.logic.Tool;
import org.sgJoe.logic.ToolRegistry;
import org.sgJoe.plugin.*;

import org.sgJoe.logic.Session;
import org.sgJoe.tools.interfaces.VTool;
import org.sgJoe.tools.interfaces.VirTool;

/**
 *
 * @author  nikola
 */


public class addingToolPanel extends BasePluginPanel implements iAddingToolPanel {
    
    /** Creates new form addingToolPanel */
    private ToolRegistry toolRegistryRef = null;
    private LogicGuiManager GuiManager;
    private SceneGraphEditor editor = null;
    
    public addingToolPanel(ToolRegistry tRR, tabbedPanel TP, LogicGuiManager GM) {
        super();
        initComponents();
        toolRegistryRef = tRR;
        GuiManager = GM;
        
        //Deo koda koji ucitava iz XML-a
        //JPanel toolBoxPanel = new JPanel();
        //toolBoxPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 2));
        //toolBoxPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        //this.setLayout(new FlowLayout(FlowLayout.LEFT));
        //this.setPreferredSize(new Dimension(42, 42));
        //this.setSize(new Dimension(42, 42));
        //this.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        
        // creating and populating toolbox with tool
        if (toolRegistryRef != null)
        {
            ArrayList buttonList = new ArrayList();

            Iterator toolIt = toolRegistryRef.getIterator();
            while(toolIt.hasNext()) {
                Tool tool = (Tool) toolIt.next();     
                VTool vTool = tool.getVTool();
                String icon = tool.getIcon();
                
                // --- Nikola --- pri ubacivanju alata na panel registujem ime sa ID-om

                JButton toolButton = createButton(icon, tool.getName());
                toolButton.setActionCommand(tool.getName());
                toolButton.addActionListener(new ActionListener() 
                {
                    public void actionPerformed(ActionEvent evt) 
                    {
                        createToolActionPerformed(evt);
                    }
                });
                buttonList.add(toolButton);
            }
                    
        // create the submenu
        JPanel navTransMenu = this.createSubMenu("Base Functions", buttonList);
    
        // add the menu to the base plugin panel (this)
        add(navTransMenu);
        }
    }
    
//    public addingToolPanel(tabbedPanel TP) {
//        initComponents();
//        ActiveToolList = TP;
//        //Hardcode-ovano da ima 6 alata
//        toolCount = new int[6];
//    }
    
    
//    public void setActiveToolList (tabbedPanel yourTabbedPanel)
//    {
//        ActiveToolList = yourTabbedPanel;
//    }
    
    
    public int getToolcount (int i)
    {
        return 0;
    }
    
    public void setToolCount (int i, int value)
    {
        return;
    }
    
    public void incrementToolCount(int i) {

    }
    
    public boolean decrementToolCount(int i){
        return false;

    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 275, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 100, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    
    /**Ovde treba razdvojiti kreiranje alata od guia, da bude samo poziv jedne metode
     * iz managera...
     */
    private void createToolActionPerformed(ActionEvent evt) {
        CreateToolForm form = (CreateToolForm)useForm("createTool");
        form.setToolName(evt.getActionCommand());

        //treba da nadjem koji je alat kliknut da se kreira, pa da inkrementiram 
        //njegov counter
        System.out.println(evt.getActionCommand());
        
        //Ovde ubacujem panel u ActiveToolList
        /**
         * Trebalo bi da se prosledi poruka manageru, a on onda dalje da izvrsi
         * azuriranja...
         */
        String name = evt.getActionCommand();
        Tool tool = toolRegistryRef.getTool(name);
        VirTool instance = tool.getVirTool();
        instance.setToolName(name);
        instance.setInstanceName(name + "#" + instance.generateUID());
        instance.setup(editor, editor.getBaseSceneGraph().getBehaviorObserver());
        instance.getVToolRef().setInstanceName(null);
        editor.getVirToolMap().put(instance);   
        editor.setCapabilities(instance.getToolBaseBG());
        editor.addChild(instance.getToolBaseBG());        
        this.createTool(instance);
    }
    
    public void createTool (VirTool vTool){
        String name = vTool.getToolName();
        if (name.equalsIgnoreCase("main")){
            GuiManager.addTool(vTool);
            return;
        }
        //treba da nadjem koji je alat kliknut da se kreira, pa da inkrementiram 
        //njegov counter
        System.out.println(vTool.getInstanceName());

        
        //Ovde ubacujem panel u ActiveToolList
        /**
         * Trebalo bi da se prosledi poruka manageru, a on onda dalje da izvrsi
         * azuriranja...
         */
        GuiManager.addTool(vTool);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    protected void setup() {
    }

    protected void update(Session session) {
    }

    public void setSGEditor(SceneGraphEditor editor) {
        this.editor = editor;
    }


}