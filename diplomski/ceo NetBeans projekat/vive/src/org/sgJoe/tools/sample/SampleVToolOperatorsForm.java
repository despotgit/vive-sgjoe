package org.sgJoe.tools.sample;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import org.apache.log4j.Logger;
import org.sgJoe.exception.SGPluginException;
import org.sgJoe.gui.SGButtonGroup;
import org.sgJoe.tools.*;
import org.sgJoe.tools.interfaces.*;

/*
 * Descritpion for SampleVToolOperatorsForm.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: April 13, 2006  7:04 PM    $
 */

public class SampleVToolOperatorsForm extends VToolOperatorsForm {
    
    private static Logger logger = Logger.getLogger(SampleVToolOperatorsForm.class);
    
    private SGButtonGroup buttonGroup;
    
    private JRadioButton rbViewSystem;
    private JRadioButton rbWorldSystem;

    public SampleVToolOperatorsForm(VirTool virToolRef) {
        super(virToolRef);
    }

    public void setCoordinateSystem(int system) {
        switch (system) {
            case SampleVTool.COORD_SYS_VIEW:
                rbViewSystem.setSelected(true);
                break;
            case SampleVTool.COORD_SYS_WORLD:
                rbWorldSystem.setSelected(true);
        }
    }
    
    public void setup() {
        super.setup();
        ArrayList radioButtonList = new ArrayList();
        
        rbViewSystem = this.createRadioButton("VIEW", false);
        rbViewSystem.setActionCommand("VIEW");
        rbViewSystem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                changeCoordinateSystem(event);
            }
        });
        
        rbWorldSystem = this.createRadioButton("WORLD", false);
        rbWorldSystem.setActionCommand("WORLD");
        rbWorldSystem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                changeCoordinateSystem(event);
            }
        });    
        
        radioButtonList.add(rbViewSystem);
        radioButtonList.add(rbWorldSystem);        
        
        buttonGroup = new SGButtonGroup();
        
        AbstractButton[] absButtonArray =  new AbstractButton[radioButtonList.size()]; 
        
        for(int i = 0; i < radioButtonList.size(); i++) {
            absButtonArray[i] = (AbstractButton) radioButtonList.get(i);
        } 
        
        buttonGroup.add(absButtonArray);

        JPanel radioMenu = createButtons(radioButtonList);
        add(radioMenu);  
        
        this.setCoordinateSystem(((SampleVTool)virToolRef.getVToolRef()).getCoordinateSystem());
        
    }
    
    private JPanel createButtons(ArrayList buttons) {
        JPanel subMenuPanel = new JPanel();
        subMenuPanel.setLayout(new BorderLayout());
        subMenuPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
    
        // create the panel holding the buttons
        JPanel subMenuButtonPanel = new JPanel();
        subMenuButtonPanel.setLayout(new BoxLayout(subMenuButtonPanel, BoxLayout.Y_AXIS));
    
        Iterator it = buttons.iterator();
        while(it.hasNext()) {
            Object item = it.next();
            JRadioButton radioButton = (JRadioButton)item;
            subMenuButtonPanel.add(radioButton);                
        }
    
        // add everything to the menu panel
        subMenuPanel.add(subMenuButtonPanel, BorderLayout.CENTER);
    
        return subMenuPanel;
    }  

     private void changeCoordinateSystem(ActionEvent evt) {

        SampleVToolForm vToolForm = (SampleVToolForm) virToolRef.getVToolFormRef();
        
        if(rbViewSystem.isSelected()) {
            vToolForm.setCoordinateSystem(SampleVTool.COORD_SYS_VIEW);
        } else if (rbWorldSystem.isSelected()) {
            vToolForm.setCoordinateSystem(SampleVTool.COORD_SYS_WORLD);
        }
        
        vToolForm.setAction(SampleVToolForm.ACT_CHANGE_COORDINATE_SYSTEM);
        
        try {
            VToolPlugin vToolPlugin = virToolRef.getVToolPluginRef();
            vToolPlugin.performAction(vToolForm, sgEditor, null);
        } catch (SGPluginException ex) {
            ex.printStackTrace();
        }        
        
    }
     
}
