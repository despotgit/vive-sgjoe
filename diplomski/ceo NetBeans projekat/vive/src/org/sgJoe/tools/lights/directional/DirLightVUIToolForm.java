package org.sgJoe.tools.lights.directional;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.vecmath.Vector3f;
import org.apache.log4j.Logger;
import org.sgJoe.exception.SGPluginException;
import org.sgJoe.tools.interfaces.*;
import org.sgJoe.tools.lights.*;


/*
 * Descritpion for DirLightVUIToolForm.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: June 3, 2006  12:58 PM  $
 */

public class DirLightVUIToolForm extends LightVUIToolForm {
    
    private static Logger logger = Logger.getLogger(DirLightVUIToolForm.class);
    
    private JTextField jTextFieldDirectionX;
    private JTextField jTextFieldDirectionY;
    private JTextField jTextFieldDirectionZ;
    
    private JLabel jLabelDirection;
    private JLabel jLabelDirectionX;
    private JLabel jLabelDirectionY;
    private JLabel jLabelDirectionZ;    
    
    private JPanel jPanelDirection;
    
    public DirLightVUIToolForm(VirTool virToolRef) {
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
    }    
    
   private void directionActionPerformed(ActionEvent evt)
  {
    try
    {
      float x = Float.parseFloat(jTextFieldDirectionX.getText());
      float y = Float.parseFloat(jTextFieldDirectionY.getText());
      float z = Float.parseFloat(jTextFieldDirectionZ.getText());
      
      DirLightVToolForm vToolForm = (DirLightVToolForm)virToolRef.getVToolFormRef();
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
   
   public void update() {
       
       super.update();
       
       LightVTool lg = (LightVTool) virToolRef.getVToolRef();
       
       Vector3f direction = lg.getDirection();
       
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

     } 
     
     protected void resetGuiElements() {
         super.resetGuiElements();
         jTextFieldDirectionX.setText("");
         jTextFieldDirectionY.setText("");
         jTextFieldDirectionZ.setText("");
     }     
}
