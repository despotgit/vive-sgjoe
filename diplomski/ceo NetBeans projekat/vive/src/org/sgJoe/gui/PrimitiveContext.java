package org.sgJoe.gui;

import java.math.BigDecimal;
import javax.swing.border.TitledBorder;
import javax.vecmath.Point3d;
import org.sgJoe.logic.Session;
import org.sgJoe.plugin.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


import org.apache.log4j.Logger;



/**
 * This GUI class shows information about the selected object
 * and gives the posibility to manipulate the selected 3D object.
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:            0.1         $
 * @date     $ Date:  December 22, 2005 7:08 PM $
 */
public class PrimitiveContext extends SGPanel {
    
    private static Logger logger = Logger.getLogger(PrimitiveContext.class);
 
    private JPanel topPanel;
    // Position
    private JPanel jPanelPosition;
  
    private JLabel jLabelPosition;
    private JLabel jLabelPositionX;
    private JLabel jLabelPositionY;
    private JLabel jLabelPositionZ;
  
    private JTextField jTextFieldPositionX;
    private JTextField jTextFieldPositionY;
    private JTextField jTextFieldPositionZ;
    
//    // Pick Point
//    private JPanel jPanelPoint;
//  
//    private JLabel jLabelPoint;
//    private JLabel jLabelPointX;
//    private JLabel jLabelPointY;
//    private JLabel jLabelPointZ;
//  
//    private JTextField jTextFieldPointX;
//    private JTextField jTextFieldPointY;
//    private JTextField jTextFieldPointZ;    
  
    // Scaling
    private JPanel jPanelScaling;
  
    private JLabel jLabelScaling;
    private JLabel jLabelScalingX;
    private JLabel jLabelScalingY;
    private JLabel jLabelScalingZ;
  
    private JTextField jTextFieldScalingX;
    private JTextField jTextFieldScalingY;
    private JTextField jTextFieldScalingZ;
  
    // Rotation
    private JPanel jPanelRotation;
  
    private JLabel jLabelRotation;
    private JLabel jLabelRotationX;
    private JLabel jLabelRotationY;
    private JLabel jLabelRotationZ;
  
    private JTextField jTextFieldRotationX;
    private JTextField jTextFieldRotationY;
    private JTextField jTextFieldRotationZ;
  
    
    // Distance 
    
    private JPanel jPanelDistance;
  
    private JLabel jLabelPointFirst;
    private JTextField jTextFieldPointFirst;
    
    private JLabel jLabelPointSecond;
    private JTextField jTextFieldPointSecond;
    
    private JLabel      jLabelPointDistance;
    private JTextField jTextFieldPointDistance;
    
    private final static String INIT_VAL = "0.0";
    private final static String POINT3D_VAL = "(0.0, 0.0, 0.0)";
    
    /**
     * Creates the GUI for this panel.
     */
    protected void setup() {
        setBorder(new TitledBorder(null, "Object Transformations", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION));
   
        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
    
//        // Position panel
//        jPanelPosition = new javax.swing.JPanel();
//        jLabelPosition = new javax.swing.JLabel();
//        jLabelPosition.setFont(DEFAULT_FONT);
//        jLabelPositionX = new javax.swing.JLabel();
//        jLabelPositionX.setFont(DEFAULT_FONT);
//        jTextFieldPositionX = new javax.swing.JTextField(INIT_VAL);
//        jTextFieldPositionX.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent evt) {
//                translateActionPerformed(evt);
//            }
//        });
//        jLabelPositionY = new javax.swing.JLabel();
//        jLabelPositionY.setFont(DEFAULT_FONT);
//        jTextFieldPositionY = new javax.swing.JTextField(INIT_VAL);
//        jTextFieldPositionY.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent evt) {
//                translateActionPerformed(evt);
//            }
//        });
//        jLabelPositionZ = new javax.swing.JLabel();
//        jLabelPositionZ.setFont(DEFAULT_FONT);
//        jTextFieldPositionZ = new javax.swing.JTextField(INIT_VAL);
//        jTextFieldPositionZ.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent evt) {
//                translateActionPerformed(evt);
//            }
//        });
//    
//        jPanelPosition.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
//    
//        jPanelPosition.setBorder(new javax.swing.border.EtchedBorder());
//        jLabelPosition.setText("Position");
//        jLabelPosition.setMaximumSize(new java.awt.Dimension(70, 20));
//        jLabelPosition.setMinimumSize(new java.awt.Dimension(70, 20));
//        jLabelPosition.setPreferredSize(new java.awt.Dimension(70, 20));
//        jPanelPosition.add(jLabelPosition);
//    
//        jLabelPositionX.setText("X");
//        jPanelPosition.add(jLabelPositionX);
//        jTextFieldPositionX.setPreferredSize(new java.awt.Dimension(40, 20));
//        jPanelPosition.add(jTextFieldPositionX);
//    
//        jLabelPositionY.setText("Y");
//        jPanelPosition.add(jLabelPositionY);
//        jTextFieldPositionY.setPreferredSize(new java.awt.Dimension(40, 20));
//        jPanelPosition.add(jTextFieldPositionY);
//    
//        jLabelPositionZ.setText("Z");
//        jPanelPosition.add(jLabelPositionZ);
//        jTextFieldPositionZ.setPreferredSize(new java.awt.Dimension(40, 20));
//        jPanelPosition.add(jTextFieldPositionZ);
//    
//        topPanel.add(jPanelPosition);
    
///--------------------------------------------------------------------------------//
        // PickPoint panel
//        jPanelPoint = new javax.swing.JPanel();
//        jLabelPoint = new javax.swing.JLabel();
//        jLabelPoint.setFont(DEFAULT_FONT);
//        jLabelPointX = new javax.swing.JLabel();
//        jLabelPointX.setFont(DEFAULT_FONT);
//        jTextFieldPointX = new javax.swing.JTextField(INIT_VAL);
//        jTextFieldPointX.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent evt) {
//                pickPointActionPerformed(evt);
//            }
//        });
//        jLabelPointY = new javax.swing.JLabel();
//        jLabelPointY.setFont(DEFAULT_FONT);
//        jTextFieldPointY = new javax.swing.JTextField(INIT_VAL);
//        jTextFieldPointY.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent evt) {
//                pickPointActionPerformed(evt);
//            }
//        });
//        jLabelPointZ = new javax.swing.JLabel();
//        jLabelPointZ.setFont(DEFAULT_FONT);
//        jTextFieldPointZ = new javax.swing.JTextField(INIT_VAL);
//        jTextFieldPointZ.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent evt) {
//                pickPointActionPerformed(evt);
//            }
//        });
//    
//        jPanelPoint.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
//    
//        jPanelPoint.setBorder(new javax.swing.border.EtchedBorder());
//        jLabelPoint.setText("Point");
//        jLabelPoint.setMaximumSize(new java.awt.Dimension(70, 20));
//        jLabelPoint.setMinimumSize(new java.awt.Dimension(70, 20));
//        jLabelPoint.setPreferredSize(new java.awt.Dimension(70, 20));
//        jPanelPoint.add(jLabelPoint);
//    
//        jLabelPointX.setText("X");
//        jPanelPoint.add(jLabelPointX);
//        jTextFieldPointX.setPreferredSize(new java.awt.Dimension(40, 20));
//        jPanelPoint.add(jTextFieldPointX);
//    
//        jLabelPointY.setText("Y");
//        jPanelPoint.add(jLabelPointY);
//        jTextFieldPointY.setPreferredSize(new java.awt.Dimension(40, 20));
//        jPanelPoint.add(jTextFieldPointY);
//    
//        jLabelPointZ.setText("Z");
//        jPanelPoint.add(jLabelPointZ);
//        jTextFieldPointZ.setPreferredSize(new java.awt.Dimension(40, 20));
//        jPanelPoint.add(jTextFieldPointZ);
//    
//        topPanel.add(jPanelPoint);

//----------------------------------------------------------------------//
        
//        // Scaling panel
//        jPanelScaling = new javax.swing.JPanel();
//        jLabelScaling = new javax.swing.JLabel();
//        jLabelScaling.setFont(DEFAULT_FONT);
//        jLabelScalingX = new javax.swing.JLabel();
//        jLabelScalingX.setFont(DEFAULT_FONT);
//        jTextFieldScalingX = new javax.swing.JTextField(INIT_VAL);
//        jTextFieldScalingX.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent evt) {
//                scaleActionPerformed(evt);
//            }
//        });
//        jLabelScalingY = new javax.swing.JLabel();
//        jLabelScalingY.setFont(DEFAULT_FONT);
//        jTextFieldScalingY = new javax.swing.JTextField(INIT_VAL);
//        jTextFieldScalingY.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent evt) {
//                scaleActionPerformed(evt);
//            }
//        });
//        jLabelScalingZ = new javax.swing.JLabel();
//        jLabelScalingZ.setFont(DEFAULT_FONT);
//        jTextFieldScalingZ = new javax.swing.JTextField(INIT_VAL);
//        jTextFieldScalingZ.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent evt) {
//                scaleActionPerformed(evt);
//            }
//        });
//    
//        jPanelScaling.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
//    
//        jPanelScaling.setBorder(new javax.swing.border.EtchedBorder());
//        jLabelScaling.setText("Scaling");
//        jLabelScaling.setMaximumSize(new java.awt.Dimension(70, 20));
//        jLabelScaling.setMinimumSize(new java.awt.Dimension(70, 20));
//        jLabelScaling.setPreferredSize(new java.awt.Dimension(70, 20));
//        jPanelScaling.add(jLabelScaling);
//    
//        jLabelScalingX.setText("X");
//        jPanelScaling.add(jLabelScalingX);
//        jTextFieldScalingX.setPreferredSize(new java.awt.Dimension(40, 20));
//        jPanelScaling.add(jTextFieldScalingX);
//    
//        jLabelScalingY.setText("Y");
//        jPanelScaling.add(jLabelScalingY);
//        jTextFieldScalingY.setPreferredSize(new java.awt.Dimension(40, 20));
//        jPanelScaling.add(jTextFieldScalingY);
//    
//        jLabelScalingZ.setText("Z");
//        jPanelScaling.add(jLabelScalingZ);
//        jTextFieldScalingZ.setPreferredSize(new java.awt.Dimension(40, 20));
//        jPanelScaling.add(jTextFieldScalingZ);
//    
//        topPanel.add(jPanelScaling);
    
    
        // Rotation panel
        jPanelRotation = new javax.swing.JPanel();
        jLabelRotation = new javax.swing.JLabel();
        jLabelRotation.setFont(DEFAULT_FONT);
        jLabelRotationX = new javax.swing.JLabel();
        jLabelRotationX.setFont(DEFAULT_FONT);
        jTextFieldRotationX = new javax.swing.JTextField(INIT_VAL);
        jTextFieldRotationX.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                rotateActionPerformed(evt);
            }
        });
        jLabelRotationY = new javax.swing.JLabel();
        jLabelRotationY.setFont(DEFAULT_FONT);
        jTextFieldRotationY = new javax.swing.JTextField(INIT_VAL);
        jTextFieldRotationY.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                rotateActionPerformed(evt);
            }
        });
        jLabelRotationZ = new javax.swing.JLabel();
        jLabelRotationZ.setFont(DEFAULT_FONT);
        jTextFieldRotationZ = new javax.swing.JTextField(INIT_VAL);
        jTextFieldRotationZ.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                rotateActionPerformed(evt);
            }
        });
    
        jPanelRotation.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
    
        jPanelRotation.setBorder(new javax.swing.border.EtchedBorder());
        jLabelRotation.setText("Rotation");
        jLabelRotation.setMaximumSize(new java.awt.Dimension(70, 20));
        jLabelRotation.setMinimumSize(new java.awt.Dimension(70, 20));
        jLabelRotation.setPreferredSize(new java.awt.Dimension(70, 20));
        jPanelRotation.add(jLabelRotation);
    
        jLabelRotationX.setText("X");
        jPanelRotation.add(jLabelRotationX);
        jTextFieldRotationX.setPreferredSize(new Dimension(40,20));
        jPanelRotation.add(jTextFieldRotationX);
    
        jLabelRotationY.setText("Y");
        jPanelRotation.add(jLabelRotationY);
        jTextFieldRotationY.setPreferredSize(new Dimension(40,20));
        jPanelRotation.add(jTextFieldRotationY);
    
        jLabelRotationZ.setText("Z");
        jPanelRotation.add(jLabelRotationZ);
        jTextFieldRotationZ.setPreferredSize(new Dimension(40,20));
        jPanelRotation.add(jTextFieldRotationZ);
    
    
        topPanel.add(jPanelRotation);
    
///--------------------------------------------------------------------------------//
        // Distance panel
        jPanelDistance = new javax.swing.JPanel();
        
        jLabelPointFirst = new javax.swing.JLabel();
        jLabelPointFirst.setFont(DEFAULT_FONT);
        jTextFieldPointFirst = new javax.swing.JTextField(POINT3D_VAL);
        jTextFieldPointFirst.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //distanceActionPerformed(evt);
            }
        });

    //--
        jLabelPointSecond = new javax.swing.JLabel();
        jLabelPointSecond.setFont(DEFAULT_FONT);
        jTextFieldPointSecond = new javax.swing.JTextField(POINT3D_VAL);
        jTextFieldPointSecond.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //distanceActionPerformed(evt);
            }
        });
        
      //--
        
        jLabelPointDistance = new javax.swing.JLabel();
        jLabelPointDistance.setFont(DEFAULT_FONT);
        jTextFieldPointDistance = new javax.swing.JTextField(INIT_VAL);
        jTextFieldPointDistance.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //distanceActionPerformed(evt);
            }
        });
        
        //--
        jPanelDistance.setLayout(new java.awt.GridLayout(3, 2));
        jPanelDistance.setBorder(new javax.swing.border.EtchedBorder());
      
        jLabelPointFirst.setText("First");
        jLabelPointFirst.setMaximumSize(new java.awt.Dimension(70, 20));
        jLabelPointFirst.setMinimumSize(new java.awt.Dimension(70, 20));
        jLabelPointFirst.setPreferredSize(new java.awt.Dimension(70, 20));
        jPanelDistance.add(jLabelPointFirst);
    
        jTextFieldPointFirst.setPreferredSize(new java.awt.Dimension(40, 20));
        jPanelDistance.add(jTextFieldPointFirst);
    
        jLabelPointSecond.setText("Second");
        jLabelPointSecond.setMaximumSize(new java.awt.Dimension(70, 20));
        jLabelPointSecond.setMinimumSize(new java.awt.Dimension(70, 20));
        jLabelPointSecond.setPreferredSize(new java.awt.Dimension(70, 20));    
        jPanelDistance.add(jLabelPointSecond);
    
        jTextFieldPointSecond.setPreferredSize(new java.awt.Dimension(40, 20));
        jPanelDistance.add(jTextFieldPointSecond);
    

        jLabelPointDistance.setText("Point Distance");
        jLabelPointDistance.setMaximumSize(new java.awt.Dimension(70, 20));
        jLabelPointDistance.setMinimumSize(new java.awt.Dimension(70, 20));
        jLabelPointDistance.setPreferredSize(new java.awt.Dimension(70, 20));
        jPanelDistance.add(jLabelPointDistance);        

        jTextFieldPointDistance.setPreferredSize(new java.awt.Dimension(40, 20));
        jPanelDistance.add(jTextFieldPointDistance);
        
        topPanel.add(jPanelDistance);
        

//----------------------------------------------------------------------//    
        this.add(topPanel);
    }
  
    private JPanel createTextField(String labelName, JTextField jTextField) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
    
        JTextField textField = jTextField;
        textField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
    
        JLabel label = new JLabel(labelName);
        label.setFont(this.DEFAULT_FONT);
    
        panel.add(textField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(label);
    
        return panel;
    }

    private void translateActionPerformed(ActionEvent evt) {
        try {
            TranslateObjForm form = (TranslateObjForm)useForm("translateObj");
      
            String trans = jTextFieldPositionX.getText();
      
            if (trans != null && trans.trim().length() > 0) {
                float x = Float.parseFloat(trans);
                form.setX(x);
            }
      
            trans = jTextFieldPositionY.getText();
            if (trans != null && trans.trim().length() > 0) {
                float y = Float.parseFloat(trans);
                form.setY(y);
            }
      
            trans = jTextFieldPositionZ.getText();
            if (trans != null && trans.trim().length() > 0) {
                float z = Float.parseFloat(trans);
                form.setZ(z);
            }
            this.performAction("translateObj", form);
        } catch (NumberFormatException e) {
            // log message and continue...
            logger.error("Only numbers allowed.");
        }
    }
  
    private void pickPointActionPerformed(ActionEvent evt) {
        try {
//            PickPointObjForm form = (PickPointObjForm)useForm("pickPointObj");
//      
//            String trans = jTextFieldPointX.getText();
//      
//            if (trans != null && trans.trim().length() > 0) {
//                float x = Float.parseFloat(trans);
//                form.setX(x);
//            }
//      
//            trans = jTextFieldPointY.getText();
//            if (trans != null && trans.trim().length() > 0) {
//                float y = Float.parseFloat(trans);
//                form.setY(y);
//            }
//      
//            trans = jTextFieldPointZ.getText();
//            if (trans != null && trans.trim().length() > 0) {
//                float z = Float.parseFloat(trans);
//                form.setZ(z);
//            }
//            this.performAction("pickPointObj", form);
        } catch (NumberFormatException e) {
            // log message and continue...
            logger.error("Only numbers allowed.");
        }
    }    
    
    private void scaleActionPerformed(ActionEvent evt) {
        try {
            ScaleObjForm form = (ScaleObjForm)useForm("scaleObj");
      
            String scale = jTextFieldScalingX.getText();
            if (scale != null && scale.trim().length() > 0) {
                double x = Double.parseDouble(scale);
                form.setX(x);
            }

            scale = jTextFieldScalingY.getText();
            if (scale != null && scale.trim().length() > 0) {
                double y = Double.parseDouble(scale);
                form.setY(y);
            }

            scale = jTextFieldScalingZ.getText();
            if (scale != null && scale.trim().length() > 0) {
                double z = Double.parseDouble(scale);
                form.setZ(z);
            }
      
            this.performAction("scaleObj", form);
        } catch (NumberFormatException e) {
            // log message and continue...
            logger.error("only numbers allowed");
        }
    }
  
    private void rotateActionPerformed(ActionEvent evt) {
        try {
            RotateObjForm form = (RotateObjForm)useForm("rotateObj");
      
            String rotate = jTextFieldRotationX.getText();
            if (rotate != null && rotate.trim().length() > 0) {
                double x = Double.parseDouble(rotate);
                form.setX(x);
            }
      
            rotate = jTextFieldRotationY.getText();
            if (rotate != null && rotate.trim().length() > 0) {
                double y = Double.parseDouble(rotate);
                form.setY(y);
            }
      
            rotate = jTextFieldRotationZ.getText();
            if (rotate != null && rotate.trim().length() > 0) {
                double z = Double.parseDouble(rotate);
                form.setZ(z);
            }

            this.performAction("rotateObj", form);
        } catch (NumberFormatException e) {
            // log message and continue...
            logger.error("only numbers allowed");
        }
    }
  
    /**
     * Updates some components with specific data from
     * the session object. The data is normaly added in
     * a plugin.
     *
     * @param session A hashtable containing the specific data.
     */  
    protected void update(Session session) {
        Double val = (Double)session.get("TRANS_X");
        if (val != null) {
            jTextFieldPositionX.setText(""+round(val.doubleValue()));
        } else {
            jTextFieldPositionX.setText(INIT_VAL);
        }
    
        val = (Double)session.get("TRANS_Y");
        if (val != null) {
            jTextFieldPositionY.setText(""+round(val.doubleValue()));
        } else {
            jTextFieldPositionY.setText(INIT_VAL);
        }

    
        val = (Double)session.get("TRANS_Z");
        if (val != null) {
            jTextFieldPositionZ.setText(""+round(val.doubleValue()));
        } else {
            jTextFieldPositionZ.setText(INIT_VAL);
        }

//        val = (Double)session.get("POINT_X");
//        if (val != null) {
//            jTextFieldPointX.setText(""+round(val.doubleValue()));
//        } else {
//            jTextFieldPointX.setText(INIT_VAL);
//        }
//
//        val = (Double)session.get("POINT_Y");
//        if (val != null) {
//            jTextFieldPointY.setText(""+round(val.doubleValue()));
//        } else {
//            jTextFieldPointY.setText(INIT_VAL);
//        }
//        
//        val = (Double)session.get("POINT_Z");
//        if (val != null) {
//            jTextFieldPointZ.setText(""+round(val.doubleValue()));
//        } else {
//            jTextFieldPointZ.setText(INIT_VAL);
//        }        

        val = (Double)session.get("SCALE_X");
        if (val != null) {
            jTextFieldScalingX.setText(""+round(val.doubleValue()));
        } else {
            jTextFieldScalingX.setText(INIT_VAL);
        }
    
        val = (Double)session.get("SCALE_Y");
        if (val != null) {
            jTextFieldScalingY.setText(""+round(val.doubleValue()));
        } else {
            jTextFieldScalingY.setText(INIT_VAL);
        }
    
        val = (Double)session.get("SCALE_Z");
        if (val != null) {
            jTextFieldScalingZ.setText(""+round(val.doubleValue()));
        } else {
            jTextFieldScalingZ.setText(INIT_VAL);
        }
    
        // update the field with the rotation angle, the value is already
        // calculated in degrees.
        val = (Double)session.get("ROT_X");
        if (val != null) {
            jTextFieldRotationX.setText(""+round(val.doubleValue()));
        } else {
            jTextFieldRotationX.setText(INIT_VAL);
        }

        val = (Double)session.get("ROT_Y");
        if (val != null) {
            jTextFieldRotationY.setText(""+round(val.doubleValue()));
        } else {
            jTextFieldRotationY.setText(INIT_VAL);
        }
    
        val = (Double)session.get("ROT_Z");
        if (val != null) {
            jTextFieldRotationZ.setText(""+round(val.doubleValue()));
        } else {
            jTextFieldRotationZ.setText(INIT_VAL);
        }
        
        Point3d val1stPt = (Point3d)session.get("POINT3D_1ST");
        Point3d val2ndPt = (Point3d)session.get("POINT3D_2ND");
        
        if (val1stPt != null && val2ndPt != null) {
            jTextFieldPointFirst.setText(val1stPt.toString());
            jTextFieldPointSecond.setText(val2ndPt.toString());
            jTextFieldPointDistance.setText(""+round(val1stPt.distance(val2ndPt)));
        } else {
            jTextFieldPointFirst.setText(POINT3D_VAL);
            jTextFieldPointSecond.setText(POINT3D_VAL);
            jTextFieldPointDistance.setText(INIT_VAL);
        }        
    }
    
    private double round(double r) {
        int decimalPlace = 2;
        BigDecimal bd = new BigDecimal(r);
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        r = bd.doubleValue();
        return r;
    }
    
    public void setVisiblePanel(boolean visible) {
        if(visible) {
            topPanel.add(jPanelDistance);
        } else {
            topPanel.remove(jPanelDistance);
        }
        
    }
    
}
