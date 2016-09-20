package org.sgJoe.tools.sample;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.apache.log4j.Logger;
import org.sgJoe.exception.SGPluginException;
import org.sgJoe.graphics.SceneGraphEditor;
import org.sgJoe.gui.SGPanel;
import org.sgJoe.logic.*;
import org.sgJoe.tools.*;
import org.sgJoe.tools.interfaces.*;

/*
 * Descritpion for SampleVUIToolForm.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: April 2, 2006  9:35 PM  $
 */

public class SampleVUIToolForm extends VUIToolForm {
    
    private static Logger logger = Logger.getLogger(SampleVUIToolForm.class);
            
    private JButton   actionButton = null;
    private JTextArea textArea = null;
    
    public SampleVUIToolForm(VirTool virToolRef) {
        super(virToolRef);
    }
    
    /**
     * Creates the GUI for this panel.
     */
    public void setup() {
        logger.debug("init GUIToolForm");
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        textArea = new JTextArea();
        textArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        textArea.setMaximumSize(new Dimension(250, 500));
        textArea.setMinimumSize(new Dimension(250, 500));
        textArea.setPreferredSize(new Dimension(250, 500));   
    
        actionButton = new JButton("action");
        actionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        actionButton.setMaximumSize(new Dimension(80, 30));
        actionButton.setMinimumSize(new Dimension(80, 30));
        actionButton.setPreferredSize(new Dimension(80, 30));
        
        actionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                buttonPressed(event);
            }
        });
        
        this.add(actionButton);
        this.add(textArea);
    }
    
    public void setText(String text) {
        textArea.setText(text);
    }
    
    public void buttonPressed(ActionEvent event) {
        VToolForm vToolForm = virToolRef.getVToolFormRef();
        vToolForm.reset(null);
        vToolForm.setAction(VToolForm.ACT_ACTIONBUTTON_PRESSED);
        
        try {
            VToolPlugin vToolPlugin = virToolRef.getVToolPluginRef();
            vToolPlugin.performAction(vToolForm, virToolRef.getSGEditor(), null);                                
        } catch (SGPluginException ex) {
          ex.printStackTrace();
        }        
    }
    
    protected void _onVToolDelete() {
        textArea.setText("");
        VToolForm vToolForm = virToolRef.getVToolFormRef();
        vToolForm.reset(null);
        vToolForm.setAction(vToolForm.ACT_ON_INSTANCE_DELETE);
        
        try {
            VToolPlugin vToolPlugin = virToolRef.getVToolPluginRef();
            vToolPlugin.performAction(vToolForm, virToolRef.getSGEditor(), null);
        } catch (SGPluginException ex) {
            ex.printStackTrace();
        }
        
        super._onVToolDelete();
    }
        
}
