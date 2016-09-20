package org.sgJoe.tools.lights;


import org.sgJoe.exception.SGPluginException;
import org.sgJoe.tools.interfaces.*;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;

import java.util.*;
import org.apache.log4j.Logger;

import javax.swing.border.*;
import javax.swing.tree.*;
import javax.swing.*;

import java.math.BigDecimal;

import org.sgJoe.plugin.*;
import org.sgJoe.exception.SGException;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import javax.vecmath.*;
import javax.media.j3d.*;

import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.colorchooser.DefaultColorSelectionModel;
import org.sgJoe.tools.lights.directional.DirLightVToolForm;



/*
 * Descritpion for AmbLightVUIToolForm.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: May 19, 2006  9:43 PM  $
 */

public class LightVUIToolForm extends VUIToolForm implements ChangeListener {
    
    private static Logger logger = Logger.getLogger(LightVUIToolForm.class);

    private JLabel name = null;
  
    private Color3f color = new Color3f();
  
    // gui elements with user interaction
    private JCheckBox jCheckBoxLightEnabled;

    private JTextField jTextFieldPositionX;
    private JTextField jTextFieldPositionY;
    private JTextField jTextFieldPositionZ;
  
    private JButton colorButton = null;
  
    // gui elements that cannot be directly changed by user
    private JLabel jLabelLightEnabled;
    private JLabel jLabelLightTypeTitel;
    private JLabel jLabelLightType;
    private JLabel jLabelPosition;
    private JLabel jLabelPositionX;
    private JLabel jLabelPositionY;
    private JLabel jLabelPositionZ;
    private JPanel jPanelLightType;
    private JPanel jPanelLightEnabled;
    private JPanel jPanelPosition;
    private JPanel jPanelSettingsBottom;
    protected JPanel jPanelSettingsTop;
    private JPanel jPanelColor;
    private JLabel jLabelColor;
  
    public LightVUIToolForm(VirTool virToolRef) {
        super(virToolRef);
    }

  /**
   * Creates the GUI for this panel.
   */
  public void setup()
  {
      System.out.println("LightVUIToolForm - setup() metoda");
    //    setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));
    //
    setBorder(new TitledBorder(null, "Light Settings", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION));
    
    jPanelSettingsTop = new javax.swing.JPanel();
    jPanelSettingsTop.setLayout(new javax.swing.BoxLayout(jPanelSettingsTop, javax.swing.BoxLayout.Y_AXIS));
    
    jPanelLightType = new javax.swing.JPanel();
    jLabelLightTypeTitel = new javax.swing.JLabel();
    jLabelLightTypeTitel.setFont(DEFAULT_FONT);
    jLabelLightType = new javax.swing.JLabel();
    jLabelLightType.setFont(DEFAULT_FONT);
    
    jPanelPosition = new javax.swing.JPanel();
    jLabelPosition = new javax.swing.JLabel();
    jLabelPosition.setFont(DEFAULT_FONT);
    jLabelPositionX = new javax.swing.JLabel();
    jLabelPositionX.setFont(DEFAULT_FONT);
    jTextFieldPositionX = new javax.swing.JTextField();
    jTextFieldPositionX.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        positionActionPerformed(evt);
      }
    });
    jLabelPositionY = new javax.swing.JLabel();
    jLabelPositionY.setFont(DEFAULT_FONT);
    jTextFieldPositionY = new javax.swing.JTextField();
    jTextFieldPositionY.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        positionActionPerformed(evt);
      }
    });
    jLabelPositionZ = new javax.swing.JLabel();
    jLabelPositionZ.setFont(DEFAULT_FONT);
    jTextFieldPositionZ = new javax.swing.JTextField();
    jTextFieldPositionZ.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        positionActionPerformed(evt);
      }
    });
        
    jPanelLightEnabled = new javax.swing.JPanel();
    jLabelLightEnabled = new javax.swing.JLabel();
    jLabelLightEnabled.setFont(DEFAULT_FONT);
    jCheckBoxLightEnabled = new javax.swing.JCheckBox();
    jCheckBoxLightEnabled.addItemListener(new ItemListener()
    {
      public void itemStateChanged(ItemEvent evt)
      {
        lightEnabledActionPerformed(evt);
      }
    });
    
    
    
    jPanelSettingsBottom = new javax.swing.JPanel();
    
    jPanelLightType.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
    jPanelLightType.setBorder(new javax.swing.border.EtchedBorder());
    jLabelLightTypeTitel.setText("LightType");
    jLabelLightTypeTitel.setPreferredSize(new java.awt.Dimension(70, 20));
    jLabelLightType.setText("-");
    jPanelLightType.add(jLabelLightTypeTitel);
    jPanelLightType.add(jLabelLightType);
    
    jPanelSettingsTop.add(jPanelLightType);

    jPanelLightEnabled.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
    
    jPanelLightEnabled.setBorder(new javax.swing.border.EtchedBorder());
    jLabelLightEnabled.setText("Enabled");
    jLabelLightEnabled.setMaximumSize(new java.awt.Dimension(70, 20));
    jLabelLightEnabled.setMinimumSize(new java.awt.Dimension(70, 20));
    jLabelLightEnabled.setPreferredSize(new java.awt.Dimension(70, 20));
    jPanelLightEnabled.add(jLabelLightEnabled);
    
    jPanelLightEnabled.add(jCheckBoxLightEnabled);
    
    jPanelSettingsTop.add(jPanelLightEnabled);    

    //---------------------------------------------------------------------------
    
    colorButton = new JButton();
    colorButton.setBackground(color.get());
    colorButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        colorActionPerformed(evt, color);
      }
    });
    
    jPanelColor = new JPanel();
    jPanelColor.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
    jPanelColor.setBorder(new javax.swing.border.EtchedBorder());
    
    jLabelColor = new JLabel();
    jLabelColor.setText("Color");
    jLabelColor.setFont(DEFAULT_FONT);
    jLabelColor.setMaximumSize(new java.awt.Dimension(70, 16));
    jLabelColor.setMinimumSize(new java.awt.Dimension(70, 16));
    jLabelColor.setPreferredSize(new java.awt.Dimension(70, 16));
    
    jPanelColor.add(jLabelColor);
    jPanelColor.add(colorButton);
    colorButton.setMaximumSize(new java.awt.Dimension(16, 16));
    colorButton.setMinimumSize(new java.awt.Dimension(16, 16));
    colorButton.setPreferredSize(new java.awt.Dimension(16, 16));
    jPanelSettingsTop.add(jPanelColor);
    
    //----------------------------------------------------------------
    
    jPanelPosition.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
    
    jPanelPosition.setBorder(new javax.swing.border.EtchedBorder());
    jLabelPosition.setText("Position");
    jLabelPosition.setPreferredSize(new java.awt.Dimension(70, 20));
    jPanelPosition.add(jLabelPosition);
    
    jLabelPositionX.setText("X");
    jPanelPosition.add(jLabelPositionX);
    
    jTextFieldPositionX.setPreferredSize(new java.awt.Dimension(40, 20));
    jPanelPosition.add(jTextFieldPositionX);
    
    jLabelPositionY.setText("Y");
    jPanelPosition.add(jLabelPositionY);
    
    jTextFieldPositionY.setPreferredSize(new java.awt.Dimension(40, 20));
    jPanelPosition.add(jTextFieldPositionY);
    
    jLabelPositionZ.setText("Z");
    jPanelPosition.add(jLabelPositionZ);
    
    jTextFieldPositionZ.setPreferredSize(new java.awt.Dimension(40, 20));
    jPanelPosition.add(jTextFieldPositionZ);
    
    jPanelSettingsTop.add(jPanelPosition);

    add(jPanelSettingsTop);
  }
  
 /**
   * Action when one of the two JSpinner fire an event
   *
   * @param the event that triggered the method
   */
  public void stateChanged(ChangeEvent evt)  {  }

  
  /**
   * Updates some components with specific data from
   * the session object. The data is normaly added in
   * a plugin.
   *
   * @param the session containing specific data.
   */
  public void update() {
        LightVTool lg = (LightVTool) virToolRef.getVToolRef();
        
        resetGuiElements();
        disableGuiElements();
      
        Light light = lg.getLight();
        
        String lightTypeText = lg.getLightTypeText();
      
        Vector3f position = lg.getPosition();
      
        Vector3f direction = lg.getDirection();
      
        light.getColor(color);
        boolean lightEnabled = light.getEnable();
      
        colorButton.setBackground(new Color(color.x, color.y, color.z));
      
        jCheckBoxLightEnabled.setSelected(lightEnabled);
      
        jTextFieldPositionX.setText(String.valueOf(round(position.x)));
        jTextFieldPositionY.setText(String.valueOf(round(position.y)));
        jTextFieldPositionZ.setText(String.valueOf(round(position.z)));
      
          jLabelLightType.setText(lightTypeText);

  }
  
     protected void resetGuiElements() { }
  

     protected void disableGuiElements() { }
  
  /**
   * Rounds the float number after 2 digits after the comma
   * @param the float number to be rounded
   * @return the rounded float number
   */
  protected float round(float r)
  {
    int decimalPlace = 2;
    BigDecimal bd = new BigDecimal(r);
    bd = bd.setScale(decimalPlace,BigDecimal.ROUND_HALF_UP);
    r = bd.floatValue();
    return r;
  } 

  // --------------------------------------------------------------------------------- //
  /**
   * Actions for user input
   */
  // --------------------------------------------------------------------------------- //
  
    /**
     * The light state has been modified and will be passed to the form
     * The LightVToolPlugin is executed
     *
     *@param the event
     */
    private void lightEnabledActionPerformed(ItemEvent evt) {
        boolean enabled = (evt.getStateChange() == evt.SELECTED);
    
        LightVToolForm vToolForm = (LightVToolForm)virToolRef.getVToolFormRef();
        vToolForm.reset(null);
        vToolForm.setAction(VToolForm.ACT_LIGHT_ENABLE);            
        vToolForm.setLightEnabled(enabled);

        try {
            VToolPlugin vToolPlugin = virToolRef.getVToolPluginRef();
            vToolPlugin.performAction(vToolForm, virToolRef.getSGEditor(), null);                                
        } catch (SGPluginException ex) {
          ex.printStackTrace();
        }
    }    
    
  /**
   * The color has been modified and will be passed to the form
   * The LightVToolPlugin is executed
   *
   * @param the event
   * @param the newly set color
   */
  private void colorActionPerformed(ActionEvent evt, Color3f changeColor)
  {
    // show colorChooser
    Color newColor = JColorChooser.showDialog(this, "Select Color", changeColor.get());
    // apply new color
    if (newColor != null)
    {
      changeColor.set(newColor);
      ((JButton)evt.getSource()).setBackground(newColor);
      
        LightVToolForm vToolForm = (LightVToolForm)virToolRef.getVToolFormRef();
        vToolForm.reset(null);
        vToolForm.setAction(VToolForm.ACT_LIGHT_COLOR_CHANGE);            
        vToolForm.setColor(changeColor);
        
        try {
            VToolPlugin vToolPlugin = virToolRef.getVToolPluginRef();
            vToolPlugin.performAction(vToolForm, virToolRef.getSGEditor(), null);                                
        } catch (SGPluginException ex) {
          ex.printStackTrace();
        }
    }
  }   
  
    /**
     * The position has been modified and will be passed to the form
     * The LightVToolPlugin is executed
     *
     *@param the event
     */
   
    private void positionActionPerformed(ActionEvent evt) {
        try {
            float x = Float.parseFloat(jTextFieldPositionX.getText());
            float y = Float.parseFloat(jTextFieldPositionY.getText());
            float z = Float.parseFloat(jTextFieldPositionZ.getText());
            
            LightVToolForm vToolForm = (LightVToolForm)virToolRef.getVToolFormRef();
            vToolForm.setPosX(x);
            vToolForm.setPosY(y);
            vToolForm.setPosZ(z);
            vToolForm.setAction(VToolForm.ACT_LIGHT_POSITION_CHANGE);            
      
            try {
                VToolPlugin vToolPlugin = virToolRef.getVToolPluginRef();
                vToolPlugin.performAction(vToolForm, virToolRef.getSGEditor(), null);                                
            } catch (SGPluginException ex) {
                ex.printStackTrace();
            }
            
        } catch (NumberFormatException e) {
            // log message and continue...
            logger.error("only numbers allowed");
        }
    }  

}
