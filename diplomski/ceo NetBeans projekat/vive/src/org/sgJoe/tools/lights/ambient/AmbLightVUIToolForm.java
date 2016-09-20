package org.sgJoe.tools.lights.ambient;

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
import org.sgJoe.tools.lights.LightVUIToolForm;



/*
 * Descritpion for AmbLightVUIToolForm.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: May 19, 2006  9:43 PM  $
 */

public class AmbLightVUIToolForm extends LightVUIToolForm {//extends VUIToolForm implements ChangeListener {
    
    private static Logger logger = Logger.getLogger(AmbLightVUIToolForm.class);

//    private JLabel name = null;
//  
//    private Color3f color = new Color3f();
//  
//    // gui elements with user interaction
//    private JCheckBox jCheckBoxLightEnabled;
//    //  private JColorChooser jColorChooser;
//    private JSpinner jSpinnerConcentration;
//    private JSpinner jSpinnerSpreadAngle;
//    private JTextField jTextFieldAttenuationC;
//    private JTextField jTextFieldAttenuationL;
//    private JTextField jTextFieldAttenuationQ;
//    private JTextField jTextFieldDirectionX;
//    private JTextField jTextFieldDirectionY;
//    private JTextField jTextFieldDirectionZ;
//    private JTextField jTextFieldPositionX;
//    private JTextField jTextFieldPositionY;
//    private JTextField jTextFieldPositionZ;
//  
//    private JButton colorButton = null;
//  
//    // gui elements that cannot be directly changed by user
//    private JLabel jLabelAttenuation;
//    private JLabel jLabelAttenuationC;
//    private JLabel jLabelAttenuationL;
//    private JLabel jLabelAttenuationQ;
//    private JLabel jLabelConcentration;
//    private JLabel jLabelDirection;
//    private JLabel jLabelDirectionX;
//    private JLabel jLabelDirectionY;
//    private JLabel jLabelDirectionZ;
//    private JLabel jLabelLightEnabled;
//    private JLabel jLabelLightTypeTitel;
//    private JLabel jLabelLightType;
//    private JLabel jLabelPosition;
//    private JLabel jLabelPositionX;
//    private JLabel jLabelPositionY;
//    private JLabel jLabelPositionZ;
//    private JLabel jLabelSpreadAngle;
//    private JPanel jPanelLightType;
//    private JPanel jPanelAttenuation;
//    private JPanel jPanelConcentration;
//    private JPanel jPanelDirection;
//    private JPanel jPanelLightEnabled;
//    private JPanel jPanelPosition;
//    private JPanel jPanelSettingsBottom;
//    private JPanel jPanelSettingsTop;
//    private JPanel jPanelSpreadAngle;
//    private JPanel jPanelColor;
//    private JLabel jLabelColor;
  
    public AmbLightVUIToolForm(VirTool virToolRef) {
        super(virToolRef);
    }

  /**
   * Creates the GUI for this panel.
   */
//  public void setup()
//  {
    //    setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));
    //
//    setBorder(new TitledBorder(null, "Light Settings", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION));
//    
//    jPanelSettingsTop = new javax.swing.JPanel();
//    jPanelSettingsTop.setLayout(new javax.swing.BoxLayout(jPanelSettingsTop, javax.swing.BoxLayout.Y_AXIS));
    
//    jPanelLightType = new javax.swing.JPanel();
//    jLabelLightTypeTitel = new javax.swing.JLabel();
//    jLabelLightTypeTitel.setFont(DEFAULT_FONT);
//    jLabelLightType = new javax.swing.JLabel();
//    jLabelLightType.setFont(DEFAULT_FONT);
//    
//    jPanelPosition = new javax.swing.JPanel();
//    jLabelPosition = new javax.swing.JLabel();
//    jLabelPosition.setFont(DEFAULT_FONT);
//    jLabelPositionX = new javax.swing.JLabel();
//    jLabelPositionX.setFont(DEFAULT_FONT);
//    jTextFieldPositionX = new javax.swing.JTextField();
//    jTextFieldPositionX.addActionListener(new ActionListener()
//    {
//      public void actionPerformed(ActionEvent evt)
//      {
//        positionActionPerformed(evt);
//      }
//    });
//    jLabelPositionY = new javax.swing.JLabel();
//    jLabelPositionY.setFont(DEFAULT_FONT);
//    jTextFieldPositionY = new javax.swing.JTextField();
//    jTextFieldPositionY.addActionListener(new ActionListener()
//    {
//      public void actionPerformed(ActionEvent evt)
//      {
//        positionActionPerformed(evt);
//      }
//    });
//    jLabelPositionZ = new javax.swing.JLabel();
//    jLabelPositionZ.setFont(DEFAULT_FONT);
//    jTextFieldPositionZ = new javax.swing.JTextField();
//    jTextFieldPositionZ.addActionListener(new ActionListener()
//    {
//      public void actionPerformed(ActionEvent evt)
//      {
//        positionActionPerformed(evt);
//      }
//    });
//    
//    jPanelDirection = new javax.swing.JPanel();
//    jLabelDirection = new javax.swing.JLabel();
//    jLabelDirection.setFont(DEFAULT_FONT);
//    jLabelDirectionX = new javax.swing.JLabel();
//    jLabelDirectionX.setFont(DEFAULT_FONT);
//    jTextFieldDirectionX = new javax.swing.JTextField();
//    jTextFieldDirectionX.addActionListener(new ActionListener()
//    {
//      public void actionPerformed(ActionEvent evt)
//      {
//        directionActionPerformed(evt);
//      }
//    });
//    jLabelDirectionY = new javax.swing.JLabel();
//    jLabelDirectionY.setFont(DEFAULT_FONT);
//    jTextFieldDirectionY = new javax.swing.JTextField();
//    jTextFieldDirectionY.addActionListener(new ActionListener()
//    {
//      public void actionPerformed(ActionEvent evt)
//      {
//        directionActionPerformed(evt);
//      }
//    });
//    jLabelDirectionZ = new javax.swing.JLabel();
//    jLabelDirectionZ.setFont(DEFAULT_FONT);
//    jTextFieldDirectionZ = new javax.swing.JTextField();
//    jTextFieldDirectionZ.addActionListener(new ActionListener()
//    {
//      public void actionPerformed(ActionEvent evt)
//      {
//        directionActionPerformed(evt);
//      }
//    });
//    
//    jPanelAttenuation = new javax.swing.JPanel();
//    jLabelAttenuation = new javax.swing.JLabel();
//    jLabelAttenuation.setFont(DEFAULT_FONT);
//    jLabelAttenuationC = new javax.swing.JLabel();
//    jLabelAttenuationC.setFont(DEFAULT_FONT);
//    jTextFieldAttenuationC = new javax.swing.JTextField();
//    jTextFieldAttenuationC.addActionListener(new ActionListener()
//    {
//      public void actionPerformed(ActionEvent evt)
//      {
//        attenuationActionPerformed(evt);
//      }
//    });
//    jLabelAttenuationL = new javax.swing.JLabel();
//    jLabelAttenuationL.setFont(DEFAULT_FONT);
//    jTextFieldAttenuationL = new javax.swing.JTextField();
//    jTextFieldAttenuationL.addActionListener(new ActionListener()
//    {
//      public void actionPerformed(ActionEvent evt)
//      {
//        attenuationActionPerformed(evt);
//      }
//    });
//    jLabelAttenuationQ = new javax.swing.JLabel();
//    jLabelAttenuationQ.setFont(DEFAULT_FONT);
//    jTextFieldAttenuationQ = new javax.swing.JTextField();
//    jTextFieldAttenuationQ.addActionListener(new ActionListener()
//    {
//      public void actionPerformed(ActionEvent evt)
//      {
//        attenuationActionPerformed(evt);
//      }
//    });
//    
//    jPanelSpreadAngle = new javax.swing.JPanel();
//    jLabelSpreadAngle = new javax.swing.JLabel();
//    jLabelSpreadAngle.setFont(DEFAULT_FONT);
//    
//    Double value = new Double(60.0);
//    Double min = new Double(0.0);
//    Double max = new Double(90.0);
//    Double step = new Double(1.0);
//    SpinnerNumberModel model = new SpinnerNumberModel(value, min, max, step);
//    jSpinnerSpreadAngle = new javax.swing.JSpinner();
//    jSpinnerSpreadAngle.setModel(model);
//    jSpinnerSpreadAngle.addChangeListener(this);
//    
//    jPanelConcentration = new javax.swing.JPanel();
//    jLabelConcentration = new javax.swing.JLabel();
//    jLabelConcentration.setFont(DEFAULT_FONT);
//    
//    value = new Double(0.0);
//    min = new Double(0.0);
//    max = new Double(128.0);
//    step = new Double(1.0);
//    SpinnerNumberModel model2 = new SpinnerNumberModel(value, min, max, step);
//    jSpinnerConcentration = new javax.swing.JSpinner();
//    jSpinnerConcentration.setModel(model2);
//    jSpinnerConcentration.addChangeListener(this);
//    
//    jPanelLightEnabled = new javax.swing.JPanel();
//    jLabelLightEnabled = new javax.swing.JLabel();
//    jLabelLightEnabled.setFont(DEFAULT_FONT);
//    jCheckBoxLightEnabled = new javax.swing.JCheckBox();
//    jCheckBoxLightEnabled.addItemListener(new ItemListener()
//    {
//      public void itemStateChanged(ItemEvent evt)
//      {
//        lightEnabledActionPerformed(evt);
//      }
//    });
//    
//    
//    
//    jPanelSettingsBottom = new javax.swing.JPanel();
//    //    jColorChooser = new javax.swing.JColorChooser();
//    
//    jPanelLightType.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
//    jPanelLightType.setBorder(new javax.swing.border.EtchedBorder());
//    jLabelLightTypeTitel.setText("LightType");
//    jLabelLightTypeTitel.setPreferredSize(new java.awt.Dimension(70, 20));
//    jLabelLightType.setText("-");
//    jPanelLightType.add(jLabelLightTypeTitel);
//    jPanelLightType.add(jLabelLightType);
//    
//    jPanelSettingsTop.add(jPanelLightType);
//    
//    jPanelPosition.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
//    
//    jPanelPosition.setBorder(new javax.swing.border.EtchedBorder());
//    jLabelPosition.setText("Position");
//    jLabelPosition.setPreferredSize(new java.awt.Dimension(70, 20));
//    jPanelPosition.add(jLabelPosition);
//    
//    jLabelPositionX.setText("X");
//    jPanelPosition.add(jLabelPositionX);
//    
//    jTextFieldPositionX.setPreferredSize(new java.awt.Dimension(40, 20));
//    jPanelPosition.add(jTextFieldPositionX);
//    
//    jLabelPositionY.setText("Y");
//    jPanelPosition.add(jLabelPositionY);
//    
//    jTextFieldPositionY.setPreferredSize(new java.awt.Dimension(40, 20));
//    jPanelPosition.add(jTextFieldPositionY);
//    
//    jLabelPositionZ.setText("Z");
//    jPanelPosition.add(jLabelPositionZ);
//    
//    jTextFieldPositionZ.setPreferredSize(new java.awt.Dimension(40, 20));
//    jPanelPosition.add(jTextFieldPositionZ);
//    
//    jPanelSettingsTop.add(jPanelPosition);
//    
//    jPanelDirection.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
//    
//    jPanelDirection.setBorder(new javax.swing.border.EtchedBorder());
//    jLabelDirection.setText("Direction");
//    jLabelDirection.setPreferredSize(new java.awt.Dimension(70, 20));
//    jPanelDirection.add(jLabelDirection);
//    
//    jLabelDirectionX.setText("X");
//    jPanelDirection.add(jLabelDirectionX);
//    
//    jTextFieldDirectionX.setPreferredSize(new java.awt.Dimension(40, 20));
//    jPanelDirection.add(jTextFieldDirectionX);
//    
//    jLabelDirectionY.setText("Y");
//    jPanelDirection.add(jLabelDirectionY);
//    
//    jTextFieldDirectionY.setPreferredSize(new java.awt.Dimension(40, 20));
//    jPanelDirection.add(jTextFieldDirectionY);
//    
//    jLabelDirectionZ.setText("Z");
//    jPanelDirection.add(jLabelDirectionZ);
//    
//    jTextFieldDirectionZ.setPreferredSize(new java.awt.Dimension(40, 20));
//    jPanelDirection.add(jTextFieldDirectionZ);
//    
//    jPanelSettingsTop.add(jPanelDirection);
//    
//    jPanelAttenuation.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
//    
//    jPanelAttenuation.setBorder(new javax.swing.border.EtchedBorder());
//    jLabelAttenuation.setText("Attenuation");
//    jLabelAttenuation.setPreferredSize(new java.awt.Dimension(70, 20));
//    jPanelAttenuation.add(jLabelAttenuation);
//    
//    jLabelAttenuationC.setText("C");
//    jPanelAttenuation.add(jLabelAttenuationC);
//    
//    jTextFieldAttenuationC.setPreferredSize(new java.awt.Dimension(40, 20));
//    jPanelAttenuation.add(jTextFieldAttenuationC);
//    
//    jLabelAttenuationL.setText("L");
//    jPanelAttenuation.add(jLabelAttenuationL);
//    
//    jTextFieldAttenuationL.setPreferredSize(new java.awt.Dimension(40, 20));
//    jPanelAttenuation.add(jTextFieldAttenuationL);
//    
//    jLabelAttenuationQ.setText("Q");
//    jPanelAttenuation.add(jLabelAttenuationQ);
//    
//    jTextFieldAttenuationQ.setPreferredSize(new java.awt.Dimension(40, 20));
//    jPanelAttenuation.add(jTextFieldAttenuationQ);
//    
//    jPanelSettingsTop.add(jPanelAttenuation);
//    
//    jPanelSpreadAngle.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
//    
//    jPanelSpreadAngle.setBorder(new javax.swing.border.EtchedBorder());
//    jLabelSpreadAngle.setText("SpreadAngle");
//    jLabelSpreadAngle.setMaximumSize(new java.awt.Dimension(70, 20));
//    jLabelSpreadAngle.setMinimumSize(new java.awt.Dimension(70, 20));
//    jLabelSpreadAngle.setPreferredSize(new java.awt.Dimension(70, 20));
//    jPanelSpreadAngle.add(jLabelSpreadAngle);
//    
//    jSpinnerSpreadAngle.setPreferredSize(new java.awt.Dimension(40, 20));
//    jPanelSpreadAngle.add(jSpinnerSpreadAngle);
//    
//    jPanelSettingsTop.add(jPanelSpreadAngle);
//    
//    jPanelConcentration.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
//    
//    jPanelConcentration.setBorder(new javax.swing.border.EtchedBorder());
//    jLabelConcentration.setText("Concentration");
//    jLabelConcentration.setPreferredSize(new java.awt.Dimension(70, 20));
//    jPanelConcentration.add(jLabelConcentration);
//    
//    jSpinnerConcentration.setPreferredSize(new java.awt.Dimension(40, 20));
//    jPanelConcentration.add(jSpinnerConcentration);
//    
//    jPanelSettingsTop.add(jPanelConcentration);
//    
//    jPanelLightEnabled.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
//    
//    jPanelLightEnabled.setBorder(new javax.swing.border.EtchedBorder());
//    jLabelLightEnabled.setText("Enabled");
//    jLabelLightEnabled.setMaximumSize(new java.awt.Dimension(70, 20));
//    jLabelLightEnabled.setMinimumSize(new java.awt.Dimension(70, 20));
//    jLabelLightEnabled.setPreferredSize(new java.awt.Dimension(70, 20));
//    jPanelLightEnabled.add(jLabelLightEnabled);
//    
//    jPanelLightEnabled.add(jCheckBoxLightEnabled);
//    
//    jPanelSettingsTop.add(jPanelLightEnabled);
//    
//    
//    colorButton = new JButton();
//    colorButton.setBackground(color.get());
//    //    colorButton.setHorizontalAlignment(SwingConstants.RIGHT);
//    colorButton.addActionListener(new ActionListener()
//    {
//      public void actionPerformed(ActionEvent evt)
//      {
//        colorActionPerformed(evt, color);
//      }
//    });
//    
//    jPanelColor = new JPanel();
//    jPanelColor.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
//    jPanelColor.setBorder(new javax.swing.border.EtchedBorder());
//    
//    jLabelColor = new JLabel();
//    jLabelColor.setText("Color");
//    jLabelColor.setFont(DEFAULT_FONT);
//    jLabelColor.setMaximumSize(new java.awt.Dimension(70, 16));
//    jLabelColor.setMinimumSize(new java.awt.Dimension(70, 16));
//    jLabelColor.setPreferredSize(new java.awt.Dimension(70, 16));
//    
//    jPanelColor.add(jLabelColor);
//    jPanelColor.add(colorButton);
//    colorButton.setMaximumSize(new java.awt.Dimension(16, 16));
//    colorButton.setMinimumSize(new java.awt.Dimension(16, 16));
//    colorButton.setPreferredSize(new java.awt.Dimension(16, 16));
//    jPanelSettingsTop.add(jPanelColor);
//    
//    add(jPanelSettingsTop);
//  }
  
 /**
   * Action when one of the two JSpinner fire an event
   *
   * @param the event that triggered the method
   */
//  public void stateChanged(ChangeEvent evt)
//  {
//    ModifyLightForm form = (ModifyLightForm)useForm("modifyLight");
//    
//    if(evt.getSource() == jSpinnerSpreadAngle)
//    {
//      double spreadAngle = ((Double)jSpinnerSpreadAngle.getValue()).doubleValue();
//      form.setSpreadAngle(spreadAngle);
//      form.setOperation(OP_SPREAD_ANGLE);
//    }
//    else if(evt.getSource() == jSpinnerConcentration)
//    {
//      double concentration = ((Double)jSpinnerConcentration.getValue()).doubleValue();
//      form.setConcentration(concentration);
//      form.setOperation(OP_CONCENTRATION);
//    }
//    this.performAction("modifyLight", form);
//  }
 
  
  /**
   * The direction has been modified and will be passed to the form
   * The ModifyLightPlugin is executed
   *
   *@param the event
   */
//  private void directionActionPerformed(ActionEvent evt)
//  {
//    try
//    {
//      float x = Float.parseFloat(jTextFieldDirectionX.getText());
//      float y = Float.parseFloat(jTextFieldDirectionY.getText());
//      float z = Float.parseFloat(jTextFieldDirectionZ.getText());
//      
//      ModifyLightForm form = (ModifyLightForm)useForm("modifyLight");
//      form.setDirX(x);
//      form.setDirY(y);
//      form.setDirZ(z);
//      form.setOperation(OP_DIRECTION);
//      
//      this.performAction("modifyLight", form);
//    }
//    catch (NumberFormatException e)
//    {
//      // log message and continue...
//      logger.error("only numbers allowed");
//    }
//  }
//  
  /**
   * The attenuation has been modified and will be passed to the form
   * The ModifyLightPlugin is executed
   *
   *@param the event
   */
//  private void attenuationActionPerformed(ActionEvent evt)
//  {
//    try
//    {
//      float c = Float.parseFloat(jTextFieldAttenuationC.getText());
//      float l = Float.parseFloat(jTextFieldAttenuationL.getText());
//      float q = Float.parseFloat(jTextFieldAttenuationQ.getText());
//      
//      ModifyLightForm form = (ModifyLightForm)useForm("modifyLight");
//      form.setAttC(c);
//      form.setAttL(l);
//      form.setAttQ(q);
//      form.setOperation(OP_ATTENUATION);
//      
//      this.performAction("modifyLight", form);
//    }
//    catch (NumberFormatException e)
//    {
//      // log message and continue...
//      logger.error("only numbers allowed");
//    }
//  }
    

  
  /**
   * Updates some components with specific data from
   * the session object. The data is normaly added in
   * a plugin.
   *
   * @param the session containing specific data.
   */
//  protected void update() {
//    //LightGroup lg = (LightGroup) session.get(Constants.LIGHT_GROUP);
//        AmbLightVTool lg = (AmbLightVTool) virToolRef.getVToolRef();
//        
//        resetGuiElements();
//        disableGuiElements();
//      
//        Light light = lg.getLight();
//        
//        String lightTypeText = lg.getLightTypeText();
//      
//        Vector3f position = lg.getPosition();
//      
//        Vector3f direction = lg.getDirection();
//      
//        light.getColor(color);
//        boolean lightEnabled = light.getEnable();
//      
//        colorButton.setBackground(new Color(color.x, color.y, color.z));
//      
//        jCheckBoxLightEnabled.setSelected(lightEnabled);
//      
//        jTextFieldPositionX.setText(String.valueOf(round(position.x)));
//        jTextFieldPositionY.setText(String.valueOf(round(position.y)));
//        jTextFieldPositionZ.setText(String.valueOf(round(position.z)));
//      
//        jLabelLightType.setText(lightTypeText);
//
//  }
  
    /**
     * Resets the Direction and Attenuation TextFields
     */
//     private void resetGuiElements() {
//         jTextFieldDirectionX.setText("");
//         jTextFieldDirectionY.setText("");
//         jTextFieldDirectionZ.setText("");
//    
//         jTextFieldAttenuationC.setText("");
//         jTextFieldAttenuationL.setText("");
//         jTextFieldAttenuationQ.setText("");
//     }
  
    /**
     * Disables all the GUI elements of the lightContext menu.
     */
//     private void disableGuiElements() {
//         jLabelDirection.setEnabled(false);
//         jLabelDirectionX.setEnabled(false);
//         jLabelDirectionY.setEnabled(false);
//         jLabelDirectionZ.setEnabled(false);
//    
//         jLabelAttenuation.setEnabled(false);
//         jLabelAttenuationC.setEnabled(false);
//         jLabelAttenuationL.setEnabled(false);
//         jLabelAttenuationQ.setEnabled(false);
//    
//         jTextFieldDirectionX.setEnabled(false);
//         jTextFieldDirectionY.setEnabled(false);
//         jTextFieldDirectionZ.setEnabled(false);
//    
//         jTextFieldAttenuationC.setEnabled(false);
//         jTextFieldAttenuationL.setEnabled(false);
//         jTextFieldAttenuationQ.setEnabled(false);
//
//         jLabelConcentration.setEnabled(false);
//         jSpinnerConcentration.setEnabled(false);
//
//         jLabelSpreadAngle.setEnabled(false);
//         jSpinnerSpreadAngle.setEnabled(false);
//     }
  
  /**
   * Rounds the float number after 2 digits after the comma
   * @param the float number to be rounded
   * @return the rounded float number
   */
//  private float round(float r)
//  {
//    int decimalPlace = 2;
//    BigDecimal bd = new BigDecimal(r);
//    bd = bd.setScale(decimalPlace,BigDecimal.ROUND_HALF_UP);
//    r = bd.floatValue();
//    return r;
//  } 

  // --------------------------------------------------------------------------------- //
  /**
   * Actions for user input
   */
  // --------------------------------------------------------------------------------- //
  
    /**
     * The light state has been modified and will be passed to the form
     * The ModifyLightPlugin is executed
     *
     *@param the event
     */
//    private void lightEnabledActionPerformed(ItemEvent evt) {
//        boolean enabled = (evt.getStateChange() == evt.SELECTED);
//    
//        ModifyLightForm form = (ModifyLightForm)useForm("modifyLight");
//        
//        form.setOperation(OP_ENABLE);
//        
//        AmbLightVToolForm vToolForm = (AmbLightVToolForm)virToolRef.getVToolFormRef();
//        form.setEnabled(enabled);
//        vToolForm.setAction(VToolForm.ACT_LIGHT_ENABLE);      
//        
//        try {
//            VToolPlugin vToolPlugin = virToolRef.getVToolPluginRef();
//            vToolPlugin.performAction(vToolForm, virToolRef.getSGEditor(), null);                                
//        } catch (SGPluginException ex) {
//          ex.printStackTrace();
//        }
//    }    
    
  /**
   * The color has been modified and will be passed to the form
   * The ModifyLightPlugin is executed
   *
   * @param the event
   * @param the newly set color
   */
//  private void colorActionPerformed(ActionEvent evt, Color3f changeColor)
//  {
//    // show colorChooser
//    Color newColor = JColorChooser.showDialog(this, "Select Color", changeColor.get());
//    // apply new color
//    if (newColor != null)
//    {
//      changeColor.set(newColor);
//      ((JButton)evt.getSource()).setBackground(newColor);
//      
//        AmbLightVToolForm vToolForm = (AmbLightVToolForm)virToolRef.getVToolFormRef();
//        vToolForm.reset(null);
//        vToolForm.setAction(VToolForm.ACT_LIGHT_COLOR_CHANGE);      
//      
//        vToolForm.setColor(changeColor);
//        
//        try {
//            VToolPlugin vToolPlugin = virToolRef.getVToolPluginRef();
//            vToolPlugin.performAction(vToolForm, virToolRef.getSGEditor(), null);                                
//        } catch (SGPluginException ex) {
//          ex.printStackTrace();
//        }
//    }
//  }   
  
    /**
     * The position has been modified and will be passed to the form
     * The ModifyLightPlugin is executed
     *
     *@param the event
     */
   
////    private void positionActionPerformed(ActionEvent evt) {
////        try {
////            float x = Float.parseFloat(jTextFieldPositionX.getText());
////            float y = Float.parseFloat(jTextFieldPositionY.getText());
////            float z = Float.parseFloat(jTextFieldPositionZ.getText());
////            
////            AmbLightVToolForm vToolForm = (AmbLightVToolForm)virToolRef.getVToolFormRef();
////            vToolForm.setPosX(x);
////            vToolForm.setPosY(y);
////            vToolForm.setPosZ(z);
////            vToolForm.setAction(VToolForm.ACT_LIGHT_POSITION_CHANGE);            
////      
////            try {
////                VToolPlugin vToolPlugin = virToolRef.getVToolPluginRef();
////                vToolPlugin.performAction(vToolForm, virToolRef.getSGEditor(), null);                                
////            } catch (SGPluginException ex) {
////                ex.printStackTrace();
////            }
////            
////        } catch (NumberFormatException e) {
////            // log message and continue...
////            logger.error("only numbers allowed");
////        }
////    }  
}
