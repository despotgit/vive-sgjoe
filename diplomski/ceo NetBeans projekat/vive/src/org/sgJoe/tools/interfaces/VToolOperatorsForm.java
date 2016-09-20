package org.sgJoe.tools.interfaces;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import org.sgJoe.exception.SGPluginException;
import org.sgJoe.graphics.SceneGraphEditor;
import org.sgJoe.gui.SGPanel;
import org.sgJoe.logic.Session;


/*
 * Descritpion for VToolOperatorForm.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: April 13, 2006  6:46 PM  $
 */

public abstract class VToolOperatorsForm extends SGPanel{
    
    protected SceneGraphEditor  sgEditor = null;
    protected VirTool           virToolRef = null;
    private boolean           dirty = true;   

    private JButton   deleteButton = null;

    protected String toolName;    
        
    public VToolOperatorsForm(VirTool virToolRef) {
        super();
        this.virToolRef = virToolRef;
    }
        
    /**
     * Creates the GUI for this panel.
     */
    public void setup() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        deleteButton = new JButton("delete");
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteButton.setMaximumSize(new Dimension(80, 30));
        deleteButton.setMinimumSize(new Dimension(80, 30));
        deleteButton.setPreferredSize(new Dimension(80, 30));
        
        deleteButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent event) 
            {
                deletePressed(event);
            }
        });
        
        add(deleteButton);        
    }
    

    protected void update(Session session) { }

    public SceneGraphEditor getSGEditor() {
        return sgEditor;
    }

    public void setSGEditor(SceneGraphEditor sgEditor) {
        this.sgEditor = sgEditor;
    }

    public void onVToolDelete(VTool vTool) {
//        if(vToolRef != null && vToolRef.equals(vTool)) {
//            _onVToolDelete();
//        }        
    }

//    public VTool getVToolRef() {
//        return vToolRef;
//    }
//
//    public void setVToolRef(VTool vToolRef) {
//        this.vToolRef = vToolRef;
//    }

    protected void _onVToolDelete() {
//        this.vToolRef = null;
    }

    public void setToolName(String string) {
        this.toolName = string;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }
    
    public void deletePressed(ActionEvent event) 
    {
        VToolForm vToolForm = virToolRef.getVToolFormRef();
        vToolForm.reset(null);
        vToolForm.setAction(VToolForm.ACT_ON_INSTANCE_DELETE);
        
        try 
        {
            VToolPlugin vToolPlugin = virToolRef.getVToolPluginRef();
            vToolPlugin.performAction(vToolForm, virToolRef.getSGEditor(), null);                                
        } 
        catch (SGPluginException ex) 
        {
          ex.printStackTrace();
        }        
    }     
    
}
