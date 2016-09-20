package org.sgJoe.tools.lights.point;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.media.j3d.Light;
import javax.media.j3d.PointLight;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import org.apache.log4j.Logger;
import org.sgJoe.exception.SGPluginException;
import org.sgJoe.tools.interfaces.*;
import org.sgJoe.tools.lights.*;

/*
 * Descritpion for PointLightVUIToolForm.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: June 3, 2006  10:14 PM  $
 */

public class PointLightVUIToolForm extends LightVUIToolForm {
    
    private static Logger logger = Logger.getLogger(PointLightVUIToolForm.class);
    
    private JTextField jTextFieldAttenuationC;
    private JTextField jTextFieldAttenuationL;
    private JTextField jTextFieldAttenuationQ;
    
    private JLabel jLabelAttenuation;
    private JLabel jLabelAttenuationC;
    private JLabel jLabelAttenuationL;
    private JLabel jLabelAttenuationQ;
    
    private JPanel jPanelAttenuation;
    
    public PointLightVUIToolForm(VirTool virToolRef) {
        super(virToolRef);     
    }
    
    public void setup() {
        super.setup();
        
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
    
    }    
    
    
   public void update() {
       
       super.update();
       
       LightVTool lg = (LightVTool) virToolRef.getVToolRef();
       
       Vector3f direction = lg.getDirection();
       
        Light light = lg.getLight();
            
          
          jLabelAttenuation.setEnabled(true);
          jLabelAttenuationC.setEnabled(true);
          jLabelAttenuationL.setEnabled(true);
          jLabelAttenuationQ.setEnabled(true);
          jTextFieldAttenuationC.setEnabled(true);
          jTextFieldAttenuationL.setEnabled(true);
          jTextFieldAttenuationQ.setEnabled(true);
          
          PointLight pointLight = (PointLight) light;
          
          Point3f attenuation = new Point3f();
          pointLight.getAttenuation(attenuation);
          jTextFieldAttenuationC.setText(String.valueOf(round(attenuation.x)));
          jTextFieldAttenuationL.setText(String.valueOf(round(attenuation.y)));
          jTextFieldAttenuationQ.setText(String.valueOf(round(attenuation.z)));      
   }
 
  /**
   * The attenuation has been modified and will be passed to the form
   * The ModifyLightPlugin is executed
   *
   *@param the event
   */
  private void attenuationActionPerformed(ActionEvent evt)
  {
    try
    {
      float c = Float.parseFloat(jTextFieldAttenuationC.getText());
      float l = Float.parseFloat(jTextFieldAttenuationL.getText());
      float q = Float.parseFloat(jTextFieldAttenuationQ.getText());
      
      PointLightVToolForm vToolForm = (PointLightVToolForm)virToolRef.getVToolFormRef();
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
  
     protected void disableGuiElements() {
         super.disableGuiElements();
         jLabelAttenuation.setEnabled(false);
         jLabelAttenuationC.setEnabled(false);
         jLabelAttenuationL.setEnabled(false);
         jLabelAttenuationQ.setEnabled(false);
    
         jTextFieldAttenuationC.setEnabled(false);
         jTextFieldAttenuationL.setEnabled(false);
         jTextFieldAttenuationQ.setEnabled(false);
     } 
     
     protected void resetGuiElements() {
         super.resetGuiElements();
         jTextFieldAttenuationC.setText("");
         jTextFieldAttenuationL.setText("");
         jTextFieldAttenuationQ.setText("");
     }     
}
