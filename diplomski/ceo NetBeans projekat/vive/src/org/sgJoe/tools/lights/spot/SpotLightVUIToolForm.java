package org.sgJoe.tools.lights.spot;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.media.j3d.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import org.apache.log4j.Logger;
import org.sgJoe.exception.SGPluginException;
import org.sgJoe.tools.interfaces.*;
import org.sgJoe.tools.lights.*;

/*
 * Descritpion for SpotLightVUIToolForm.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: June 4, 2006  10:12 AM  $
 */

public class SpotLightVUIToolForm extends LightVUIToolForm {
    
    private static Logger logger = Logger.getLogger(SpotLightVUIToolForm.class);
    
// ----------------------------------------------------------------------------    
    
    private JTextField jTextFieldDirectionX;
    private JTextField jTextFieldDirectionY;
    private JTextField jTextFieldDirectionZ;
    
    private JLabel jLabelDirection;
    private JLabel jLabelDirectionX;
    private JLabel jLabelDirectionY;
    private JLabel jLabelDirectionZ;    
    
    private JPanel jPanelDirection;
    
// ----------------------------------------------------------------------------
    
    private JTextField jTextFieldAttenuationC;
    private JTextField jTextFieldAttenuationL;
    private JTextField jTextFieldAttenuationQ;
    
    private JLabel jLabelAttenuation;
    private JLabel jLabelAttenuationC;
    private JLabel jLabelAttenuationL;
    private JLabel jLabelAttenuationQ;
    
    private JPanel jPanelAttenuation;    
    
// ----------------------------------------------------------------------------
    private JSpinner jSpinnerConcentration;
    private JSpinner jSpinnerSpreadAngle;
  
    private JLabel jLabelConcentration;
    private JLabel jLabelSpreadAngle;
    
    private JPanel jPanelConcentration;
    private JPanel jPanelSpreadAngle;    
    
    public SpotLightVUIToolForm(VirTool virToolRef) {
        super(virToolRef);     
    }
    
    public void setup() {
        super.setup();
        jPanelDirection = new javax.swing.JPanel();
        jLabelDirection = new javax.swing.JLabel();
        jLabelDirection.setFont(DEFAULT_FONT);
        jLabelDirectionX = new javax.swing.JLabel();
        jLabelDirectionX.setFont(DEFAULT_FONT);
        jTextFieldDirectionX = new javax.swing.JTextField();
        jTextFieldDirectionX.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent evt)
          {
            directionActionPerformed(evt);
          }
        });
        jLabelDirectionY = new javax.swing.JLabel();
        jLabelDirectionY.setFont(DEFAULT_FONT);
        jTextFieldDirectionY = new javax.swing.JTextField();
        jTextFieldDirectionY.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent evt)
          {
            directionActionPerformed(evt);
          }
        });
        jLabelDirectionZ = new javax.swing.JLabel();
        jLabelDirectionZ.setFont(DEFAULT_FONT);
        jTextFieldDirectionZ = new javax.swing.JTextField();
        jTextFieldDirectionZ.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent evt)
          {
            directionActionPerformed(evt);
          }
        });        
    
        jPanelDirection.setBorder(new javax.swing.border.EtchedBorder());
        jLabelDirection.setText("Direction");
        jLabelDirection.setPreferredSize(new java.awt.Dimension(70, 20));
        jPanelDirection.add(jLabelDirection);

        jLabelDirectionX.setText("X");
        jPanelDirection.add(jLabelDirectionX);

        jTextFieldDirectionX.setPreferredSize(new java.awt.Dimension(40, 20));
        jPanelDirection.add(jTextFieldDirectionX);

        jLabelDirectionY.setText("Y");
        jPanelDirection.add(jLabelDirectionY);

        jTextFieldDirectionY.setPreferredSize(new java.awt.Dimension(40, 20));
        jPanelDirection.add(jTextFieldDirectionY);

        jLabelDirectionZ.setText("Z");
        jPanelDirection.add(jLabelDirectionZ);

        jTextFieldDirectionZ.setPreferredSize(new java.awt.Dimension(40, 20));
        jPanelDirection.add(jTextFieldDirectionZ);

        jPanelSettingsTop.add(jPanelDirection);        
        
        jPanelAttenuation = new javax.swing.JPanel();
        jLabelAttenuation = new javax.swing.JLabel();
        jLabelAttenuation.setFont(DEFAULT_FONT);
        jLabelAttenuationC = new javax.swing.JLabel();
        jLabelAttenuationC.setFont(DEFAULT_FONT);
        jTextFieldAttenuationC = new javax.swing.JTextField();
        jTextFieldAttenuationC.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent evt)
          {
            attenuationActionPerformed(evt);
          }
        });
        jLabelAttenuationL = new javax.swing.JLabel();
        jLabelAttenuationL.setFont(DEFAULT_FONT);
        jTextFieldAttenuationL = new javax.swing.JTextField();
        jTextFieldAttenuationL.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent evt)
          {
            attenuationActionPerformed(evt);
          }
        });
        jLabelAttenuationQ = new javax.swing.JLabel();
        jLabelAttenuationQ.setFont(DEFAULT_FONT);
        jTextFieldAttenuationQ = new javax.swing.JTextField();
        jTextFieldAttenuationQ.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent evt)
          {
            attenuationActionPerformed(evt);
          }
        });   
    
        jPanelAttenuation.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jPanelAttenuation.setBorder(new javax.swing.border.EtchedBorder());
        jLabelAttenuation.setText("Attenuation");
        jLabelAttenuation.setPreferredSize(new java.awt.Dimension(70, 20));
        jPanelAttenuation.add(jLabelAttenuation);

        jLabelAttenuationC.setText("C");
        jPanelAttenuation.add(jLabelAttenuationC);

        jTextFieldAttenuationC.setPreferredSize(new java.awt.Dimension(40, 20));
        jPanelAttenuation.add(jTextFieldAttenuationC);
    
        jLabelAttenuationL.setText("L");
        jPanelAttenuation.add(jLabelAttenuationL);

        jTextFieldAttenuationL.setPreferredSize(new java.awt.Dimension(40, 20));
        jPanelAttenuation.add(jTextFieldAttenuationL);

        jLabelAttenuationQ.setText("Q");
        jPanelAttenuation.add(jLabelAttenuationQ);

        jTextFieldAttenuationQ.setPreferredSize(new java.awt.Dimension(40, 20));
        jPanelAttenuation.add(jTextFieldAttenuationQ);

        jPanelSettingsTop.add(jPanelAttenuation);
        
    jPanelSpreadAngle = new javax.swing.JPanel();
    jLabelSpreadAngle = new javax.swing.JLabel();
    jLabelSpreadAngle.setFont(DEFAULT_FONT);
    
    Double value = new Double(60.0);
    Double min = new Double(0.0);
    Double max = new Double(90.0);
    Double step = new Double(1.0);
    SpinnerNumberModel model = new SpinnerNumberModel(value, min, max, step);
    jSpinnerSpreadAngle = new javax.swing.JSpinner();
    jSpinnerSpreadAngle.setModel(model);
    jSpinnerSpreadAngle.addChangeListener(this);
    
    jPanelConcentration = new javax.swing.JPanel();
    jLabelConcentration = new javax.swing.JLabel();
    jLabelConcentration.setFont(DEFAULT_FONT);
    
    value = new Double(0.0);
    min = new Double(0.0);
    max = new Double(128.0);
    step = new Double(1.0);
    SpinnerNumberModel model2 = new SpinnerNumberModel(value, min, max, step);
    jSpinnerConcentration = new javax.swing.JSpinner();
    jSpinnerConcentration.setModel(model2);
    jSpinnerConcentration.addChangeListener(this);    
    
    jPanelSpreadAngle.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
    
    jPanelSpreadAngle.setBorder(new javax.swing.border.EtchedBorder());
    jLabelSpreadAngle.setText("SpreadAngle");
    jLabelSpreadAngle.setMaximumSize(new java.awt.Dimension(70, 20));
    jLabelSpreadAngle.setMinimumSize(new java.awt.Dimension(70, 20));
    jLabelSpreadAngle.setPreferredSize(new java.awt.Dimension(70, 20));
    jPanelSpreadAngle.add(jLabelSpreadAngle);
    
    jSpinnerSpreadAngle.setPreferredSize(new java.awt.Dimension(40, 20));
    jPanelSpreadAngle.add(jSpinnerSpreadAngle);
    
    jPanelSettingsTop.add(jPanelSpreadAngle);
    
    jPanelConcentration.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
    
    jPanelConcentration.setBorder(new javax.swing.border.EtchedBorder());
    jLabelConcentration.setText("Concentration");
    jLabelConcentration.setPreferredSize(new java.awt.Dimension(70, 20));
    jPanelConcentration.add(jLabelConcentration);
    
    jSpinnerConcentration.setPreferredSize(new java.awt.Dimension(40, 20));
    jPanelConcentration.add(jSpinnerConcentration);
    
    jPanelSettingsTop.add(jPanelConcentration);    
    
    }    
    
   private void directionActionPerformed(ActionEvent evt)
  {
    try
    {
      float x = Float.parseFloat(jTextFieldDirectionX.getText());
      float y = Float.parseFloat(jTextFieldDirectionY.getText());
      float z = Float.parseFloat(jTextFieldDirectionZ.getText());
      
      SpotLightVToolForm vToolForm = (SpotLightVToolForm)virToolRef.getVToolFormRef();
      vToolForm.setDirX(x);
      vToolForm.setDirY(y);
      vToolForm.setDirZ(z);
      vToolForm.setAction(VToolForm.ACT_LIGHT_DIRECTION_CHANGE);
      
            try {
                VToolPlugin vToolPlugin = virToolRef.getVToolPluginRef();
                vToolPlugin.performAction(vToolForm, virToolRef.getSGEditor(), null);                                
            } catch (SGPluginException ex) {
                ex.printStackTrace();
            }
    }
    catch (NumberFormatException e)
    {
      // log message and continue...
      logger.error("only numbers allowed");
    }
           
  }
   
  private void attenuationActionPerformed(ActionEvent evt)
  {
    try
    {
      float c = Float.parseFloat(jTextFieldAttenuationC.getText());
      float l = Float.parseFloat(jTextFieldAttenuationL.getText());
      float q = Float.parseFloat(jTextFieldAttenuationQ.getText());
      
      SpotLightVToolForm vToolForm = (SpotLightVToolForm)virToolRef.getVToolFormRef();
      vToolForm.setAttC(c);
      vToolForm.setAttL(l);
      vToolForm.setAttQ(q);
      vToolForm.setAction(VToolForm.ACT_LIGHT_ATTENUATION_CHANGE);
      
              try {
                VToolPlugin vToolPlugin = virToolRef.getVToolPluginRef();
                vToolPlugin.performAction(vToolForm, virToolRef.getSGEditor(), null);                                
            } catch (SGPluginException ex) {
                ex.printStackTrace();
            }
    }
    catch (NumberFormatException e)
    {
      // log message and continue...
      logger.error("only numbers allowed");
    }
  }   
   
   public void update() {
       
       super.update();
       
       LightVTool lg = (LightVTool) virToolRef.getVToolRef();
       
       Vector3f direction = lg.getDirection();
       Light light = lg.getLight();
       
          jLabelDirection.setEnabled(true);
          jLabelDirectionX.setEnabled(true);
          jLabelDirectionY.setEnabled(true);
          jLabelDirectionZ.setEnabled(true);
          jTextFieldDirectionX.setEnabled(true);
          jTextFieldDirectionY.setEnabled(true);
          jTextFieldDirectionZ.setEnabled(true);
          
          jLabelDirection.setEnabled(true);
          jLabelDirectionX.setEnabled(true);
          jLabelDirectionY.setEnabled(true);
          jLabelDirectionZ.setEnabled(true);
          jTextFieldDirectionX.setText(String.valueOf(round(direction.x)));
          jTextFieldDirectionY.setText(String.valueOf(round(direction.y)));
          jTextFieldDirectionZ.setText(String.valueOf(round(direction.z)));       
          
          jLabelAttenuation.setEnabled(true);
          jLabelAttenuationC.setEnabled(true);
          jLabelAttenuationL.setEnabled(true);
          jLabelAttenuationQ.setEnabled(true);
          jTextFieldAttenuationC.setEnabled(true);
          jTextFieldAttenuationL.setEnabled(true);
          jTextFieldAttenuationQ.setEnabled(true);
          
          SpotLight spotLight = (SpotLight) light;
          
          Point3f attenuation = new Point3f();
          spotLight.getAttenuation(attenuation);
          jTextFieldAttenuationC.setText(String.valueOf(round(attenuation.x)));
          jTextFieldAttenuationL.setText(String.valueOf(round(attenuation.y)));
          jTextFieldAttenuationQ.setText(String.valueOf(round(attenuation.z)));  
          
          jLabelConcentration.setEnabled(true);
          jSpinnerConcentration.setEnabled(true);
          
          jLabelSpreadAngle.setEnabled(true);
          jSpinnerSpreadAngle.setEnabled(true);
          
          jTextFieldDirectionX.setText(String.valueOf(round(direction.x)));
          jTextFieldDirectionY.setText(String.valueOf(round(direction.y)));
          jTextFieldDirectionZ.setText(String.valueOf(round(direction.z)));
          
          Double concentration = new Double(spotLight.getConcentration());
          jSpinnerConcentration.setValue(concentration);
          
          attenuation = new Point3f();
          spotLight.getAttenuation(attenuation);
          jTextFieldAttenuationC.setText(String.valueOf(round(attenuation.x)));
          jTextFieldAttenuationL.setText(String.valueOf(round(attenuation.y)));
          jTextFieldAttenuationQ.setText(String.valueOf(round(attenuation.z)));
          
          Double spreadAngle = new Double(Math.round(Math.toDegrees(spotLight.getSpreadAngle())));
          jSpinnerSpreadAngle.setValue(spreadAngle);
          
   }
   
     protected void disableGuiElements() {
         super.disableGuiElements();
         jLabelDirection.setEnabled(false);
         jLabelDirectionX.setEnabled(false);
         jLabelDirectionY.setEnabled(false);
         jLabelDirectionZ.setEnabled(false);
    
         jTextFieldDirectionX.setEnabled(false);
         jTextFieldDirectionY.setEnabled(false);
         jTextFieldDirectionZ.setEnabled(false);
         
    
        jLabelAttenuation.setEnabled(false);
        jLabelAttenuationC.setEnabled(false);
        jLabelAttenuationL.setEnabled(false);
        jLabelAttenuationQ.setEnabled(false);

        jTextFieldAttenuationC.setEnabled(false);
        jTextFieldAttenuationL.setEnabled(false);
        jTextFieldAttenuationQ.setEnabled(false);
    
        jLabelConcentration.setEnabled(false);
        jSpinnerConcentration.setEnabled(false);
    
        jLabelSpreadAngle.setEnabled(false);
        jSpinnerSpreadAngle.setEnabled(false);         

     } 
     
     protected void resetGuiElements() {
         super.resetGuiElements();
         jTextFieldDirectionX.setText("");
         jTextFieldDirectionY.setText("");
         jTextFieldDirectionZ.setText("");
    
         jTextFieldAttenuationC.setText("");
         jTextFieldAttenuationL.setText("");
         jTextFieldAttenuationQ.setText("");
     }  
     
     public void stateChanged(ChangeEvent evt) {
        
         SpotLightVToolForm vToolForm = (SpotLightVToolForm)virToolRef.getVToolFormRef();
    
        if(evt.getSource() == jSpinnerSpreadAngle) {
            double spreadAngle = ((Double)jSpinnerSpreadAngle.getValue()).doubleValue();
            vToolForm.setSpreadAngle(spreadAngle);
            vToolForm.setAction(VToolForm.ACT_LIGHT_SPREAD_ANGLE_CHANGE);
        } else if(evt.getSource() == jSpinnerConcentration) {
            double concentration = ((Double)jSpinnerConcentration.getValue()).doubleValue();
            vToolForm.setConcentration(concentration);
            vToolForm.setAction(VToolForm.ACT_LIGHT_CONCENTRATION_CHANGE);
        }
        try {
            VToolPlugin vToolPlugin = virToolRef.getVToolPluginRef();
            vToolPlugin.performAction(vToolForm, virToolRef.getSGEditor(), null);                                
        } catch (SGPluginException ex) {
            ex.printStackTrace();
        }
     }     
}

