package org.sgJoe.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import org.sgJoe.graphics.GraphicsConstants;
import org.sgJoe.plugin.*;

import org.sgJoe.logic.Session;

import org.apache.log4j.Logger;
import org.sgJoe.tools.sample.SampleVToolOperatorsForm;
import org.sgJoe.tools.sample.SampleVUIToolForm;
import org.sgJoe.tools.interfaces.VToolOperatorsForm;
import org.sgJoe.tools.interfaces.VUIToolForm;

/**
 * BasePluginPanel configures the panel containing the basic plugins.
 * The corresponding action calls are defined here, as well.
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:            0.1         $
 * @date     $ Date:  December 4, 2005 11:16 AM $
 */
public class BasePluginPanel extends SGPanel {
    
    private static Logger logger = Logger.getLogger(BasePluginPanel.class);
    
    private boolean navigationMode = false; // used for navigation/selection button
    private boolean oneViewMode = true; // used for view mode switching
  
    private JButton modePluginButton;
    private JButton movePluginButton;
    private JButton rotatePluginButton;
//    private JButton scalePluginButton;
//    private JButton pickPointPluginButton;
//    private JButton distancePluginButton;
    private JButton viewModePluginButton;
    
    private SGButtonGroup buttonGroup;
    
    private JRadioButton xDirectionPluginButton;
    private JRadioButton yDirectionPluginButton;
    private JRadioButton zDirectionPluginButton;
    private JRadioButton perspectiveDirectionPluginButton;    
    private JRadioButton xyDirectionPluginButton;
    private JRadioButton xzDirectionPluginButton;
    private JRadioButton yzDirectionPluginButton;
    
    private JPanel commandPanel;
    private JButton draw;
    private JButton delete;
    private JButton load;
    private FileDialog fileDialog;
    
    private JPanel toolPanel;
  
    // flags to determine current edit mode
    private boolean translateBehaviorActive = false;
    private boolean rotateBehaviorActive = false;
    private boolean scaleBehaviorActive = false;
    private boolean pickPointBehaviorActive = false;
    private boolean distanceBehaviorActive = false;
  
    /**
     * Helper method to create a submenu
     *
     * @param title   The submenu's title String
     * @param buttons ArrayList containing all the buttons for this submenu
     * @return JPanel The newly created submenu.
     */
    
    protected JPanel createSubMenu(String title, ArrayList buttons) 
    {
        JPanel subMenuPanel = new JPanel();
        subMenuPanel.setLayout(new BorderLayout());
        subMenuPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
        subMenuPanel.setMaximumSize(new java.awt.Dimension(160, 120));
        subMenuPanel.setMinimumSize(new java.awt.Dimension(160, 120));
        subMenuPanel.setPreferredSize(new java.awt.Dimension(160, 120));
    
        // create the plugin menu's title bar
        JPanel subMenuTitle = new JPanel();
        subMenuTitle.setLayout(new FlowLayout(FlowLayout.LEFT, 4, 0));            
               
        subMenuTitle.setBorder(new EtchedBorder());
        subMenuTitle.setMaximumSize(new Dimension(80, 20));
        subMenuTitle.setMinimumSize(new Dimension(80, 20));
        subMenuTitle.setPreferredSize(new Dimension(80, 20));
    
        JLabel subMenuTitleLabel = new JLabel(title, SwingConstants.LEFT);
        subMenuTitleLabel.setFont(DEFAULT_FONT);
    
        subMenuTitle.add(subMenuTitleLabel);
    
        // create the panel holding the buttons
        JPanel subMenuButtonPanel = new JPanel();
        subMenuButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
    
        Iterator it = buttons.iterator();
        while(it.hasNext()) 
        {
            // this must be resolved using polymorphism
            Object item = it.next();
            if(item instanceof JButton) 
            {
                JButton button = (JButton)item;
                subMenuButtonPanel.add(button);                
            } 
            else if (item instanceof JRadioButton) 
            {
                JRadioButton radioButton = (JRadioButton)item;
                subMenuButtonPanel.add(radioButton);                
            } 
        }
    
        // add everything to the menu panel
        subMenuPanel.add(subMenuTitle, BorderLayout.NORTH);
        subMenuPanel.add(subMenuButtonPanel, BorderLayout.CENTER);
    
        return subMenuPanel;
    }
  
    protected JPanel createSubMenuBoxLayout(String title, ArrayList buttons) {
        JPanel subMenuPanel = new JPanel();
        subMenuPanel.setLayout(new BorderLayout());
        subMenuPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
        subMenuPanel.setMaximumSize(new java.awt.Dimension(160, 200));
        subMenuPanel.setMinimumSize(new java.awt.Dimension(160, 200));
        subMenuPanel.setPreferredSize(new java.awt.Dimension(160, 200));
    
        // create the plugin menu's title bar
        JPanel subMenuTitle = new JPanel();
        subMenuTitle.setLayout(new FlowLayout(FlowLayout.LEFT, 4, 0));            
               
        subMenuTitle.setBorder(new EtchedBorder());
        subMenuTitle.setMaximumSize(new Dimension(80, 20));
        subMenuTitle.setMinimumSize(new Dimension(80, 20));
        subMenuTitle.setPreferredSize(new Dimension(80, 20));
    
        JLabel subMenuTitleLabel = new JLabel(title, SwingConstants.LEFT);
        subMenuTitleLabel.setFont(DEFAULT_FONT);
    
        subMenuTitle.add(subMenuTitleLabel);
    
        // create the panel holding the buttons
        JPanel subMenuButtonPanel = new JPanel();
        subMenuButtonPanel.setLayout(new BoxLayout(subMenuButtonPanel, BoxLayout.Y_AXIS));
        //subMenuButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
    
        Iterator it = buttons.iterator();
        while(it.hasNext()) {
            // this must be resolved using polymorphism
            Object item = it.next();
            if(item instanceof JButton) {
                JButton button = (JButton)item;
                subMenuButtonPanel.add(button);                
            } else if (item instanceof JRadioButton) {
                JRadioButton radioButton = (JRadioButton)item;
                subMenuButtonPanel.add(radioButton);                
            } 
        }
    
        // add everything to the menu panel
        subMenuPanel.add(subMenuTitle, BorderLayout.NORTH);
        subMenuPanel.add(subMenuButtonPanel, BorderLayout.CENTER);
    
        return subMenuPanel;
    }    
  
    /**
     * Put everything together
     */
    protected void setup() {
        logger.debug("init BasePluginComponent");
    
        setLayout(new javax.swing.BoxLayout(this, BoxLayout.Y_AXIS));
    
        setMaximumSize(new java.awt.Dimension(100, 100));
        setMinimumSize(new java.awt.Dimension(100, 100));
        setPreferredSize(new java.awt.Dimension(100, 100));
    
        // create the buttons, define their actions, and put them into an array list
        modePluginButton = createButton("select.gif", "Switch Navigation/Edit Mode");
        modePluginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                switchModeActionPerformed(event);
            }
        });
    
//        JButton resetPluginButton = createButton("reset.gif", "Reset View");
//        resetPluginButton.addActionListener(new ActionListener() {
//                public void actionPerformed(ActionEvent evt) {
//                    resetActionPerformed(evt);
//                }
//        });
    
        JButton deletePluginButton = createButton("erase.gif", "Delete Object");
        deletePluginButton.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });
    
        movePluginButton = createButton("move.gif", "Move Object");
        movePluginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                moveActionPerformed(event);
            }
        });
    
        rotatePluginButton = createButton("rotate.gif", "Rotate Object"); 
        rotatePluginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                rotateActionPerformed(event);
            }
        });
//    
//        scalePluginButton = createButton("scale.gif", "Scale Object");
//        scalePluginButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent event) {
//                scaleActionPerformed(event);
//            }
//        });
//        
//        pickPointPluginButton = createButton("picktool.gif", "Pick Point");
//        pickPointPluginButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent event) {
//                pickPointActionPerformed(event);
//            }
//        });        
//           
//        distancePluginButton = createButton("distance.gif", "Distance Meter");
//        distancePluginButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent event) {
//                distanceActionPerformed(event);
//            }
//        });
        
//        viewModePluginButton = createButton("view4.gif", "Switch View Mode");
//        viewModePluginButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent event) {
//                switchViewModeActionPerformed(event);
//            }
//        });
            
        ArrayList buttonList = new ArrayList();
    
        buttonList.add(modePluginButton);
        buttonList.add(deletePluginButton);
        buttonList.add(movePluginButton);
        buttonList.add(rotatePluginButton);
//        buttonList.add(scalePluginButton);
//        buttonList.add(pickPointPluginButton);
//        buttonList.add(distancePluginButton);
         
        // create the submenu
        JPanel navTransMenu = createSubMenu("Base Functions", buttonList);
    
        // add the menu to the base plugin panel (this)
        add(navTransMenu);
    
//        buttonList = new ArrayList();
//        buttonList.add(resetPluginButton);
//        buttonList.add(viewModePluginButton);
        
//        JPanel viewModeMenu = createSubMenu("View", buttonList);
//        add(viewModeMenu);        
    
//        ArrayList radioButtonList = new ArrayList();
//        
//        xDirectionPluginButton = this.createRadioButton("X", false);
//        xDirectionPluginButton.setActionCommand("X");
//        xDirectionPluginButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent event) {
//                changeDirectionActionPerformed(event);
//            }
//        });  
//        
//        yDirectionPluginButton = this.createRadioButton("Y", false);
//        yDirectionPluginButton.setActionCommand("Y");
//        yDirectionPluginButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent event) {
//                changeDirectionActionPerformed(event);
//            }
//        });        
//        
//        zDirectionPluginButton = this.createRadioButton("Z", false);
//        zDirectionPluginButton.setActionCommand("Z");
//        zDirectionPluginButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent event) {
//                changeDirectionActionPerformed(event);
//            }
//        });
//        
//        xyDirectionPluginButton = this.createRadioButton("XY", false);
//        xyDirectionPluginButton.setActionCommand("XY");
//        xyDirectionPluginButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent event) {
//                changeDirectionActionPerformed(event);
//            }
//        });        
//        
//        xzDirectionPluginButton = this.createRadioButton("XZ", false);
//        xzDirectionPluginButton.setActionCommand("XZ");
//        xzDirectionPluginButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent event) {
//                changeDirectionActionPerformed(event);
//            }
//        });        
//        
//        yzDirectionPluginButton = this.createRadioButton("YZ", false);
//        yzDirectionPluginButton.setActionCommand("YZ");
//        yzDirectionPluginButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent event) {
//                changeDirectionActionPerformed(event);
//            }
//        });        
//        
//        perspectiveDirectionPluginButton = this.createRadioButton("PERSPECT", true);
//        perspectiveDirectionPluginButton.setActionCommand("PERSPECT");
//        perspectiveDirectionPluginButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent event) {
//                changeDirectionActionPerformed(event);
//            }
//        });        
//        
//        radioButtonList.add(perspectiveDirectionPluginButton);
//        radioButtonList.add(xyDirectionPluginButton);
//        radioButtonList.add(xzDirectionPluginButton);
//        radioButtonList.add(yzDirectionPluginButton);        
//        radioButtonList.add(xDirectionPluginButton);
//        radioButtonList.add(yDirectionPluginButton);
//        radioButtonList.add(zDirectionPluginButton);
//        
//        buttonGroup = new SGButtonGroup();
//        
//        AbstractButton[] absButtonArray =  new AbstractButton[radioButtonList.size()]; 
//        
//        for(int i = 0; i < radioButtonList.size(); i++) {
//            absButtonArray[i] = (AbstractButton) radioButtonList.get(i);
//        }
//        
//        buttonGroup.add(absButtonArray);
//        
//        JPanel funcDirectionMenu = createSubMenuBoxLayout("Directions", radioButtonList);
//        add(funcDirectionMenu);
        
        toolPanel = createToolPanel("Tool Operators");
        
        //add(toolPanel);
        
    }
  
  
    /**
     * Switches between editor or navigation mode. Takes an existing form
     * and delegates it to the controller via the performAction method.
     *
     * @param event A reference from ActionEvent, a wrapper for the object
     *              which caused the event.
     */
    private void switchModeActionPerformed(ActionEvent evt) 
    {
        ChangeBehaviourForm form = (ChangeBehaviourForm)useForm("changeBehaviour");

        if (navigationMode) 
        {
            form.setBehaviourType(form.DISABLE_BEHAVIOUR_NAVIGATION_MODE);
            modePluginButton.setIcon(getImageIcon("select.gif"));
            modePluginButton.setPressedIcon(getImageIcon("pr_select.gif"));
            navigationMode = false;
        }
        else 
        {
            // enable navigation, prev was disabled
            form.setBehaviourType(form.ENABLE_BEHAVIOUR_NAVIGATION_MODE);
            modePluginButton.setIcon(getImageIcon("navigate.gif"));
            modePluginButton.setPressedIcon(getImageIcon("pr_navigate.gif"));
            navigationMode = true;
        }
        
        getParentFrame().setNavigationMode(navigationMode);

        form.setOperation(OP_SWITCH_NAV_MODE);
        this.performAction("changeBehaviour", form);
    }
  
    /**
     * Highlights the move button and calls the corresponding action via
     * controller to enable a translate behavior.
     *
     * @param event A reference from ActionEvent, a wrapper for the object
     *              which caused the event.
     */
    private void moveActionPerformed(ActionEvent evt) 
    {
        ChangeBehaviourForm form = (ChangeBehaviourForm)useForm("changeBehaviour");

        // first switch to editor mode, if necessary
        if (navigationMode) 
        {
            modePluginButton.setIcon(getImageIcon("select.gif"));
            modePluginButton.setPressedIcon(getImageIcon("pr_select.gif"));
            navigationMode = false;
            getParentFrame().setNavigationMode(navigationMode);
        }

        // if the move button is already active, make it inactive and disable
        // mouse pick translation
        if (translateBehaviorActive) 
        {
            form.setBehaviourType(form.DISABLE_BEHAVIOUR_MOUSE_PICK_TRANSLATE);
            movePluginButton.setIcon(getImageIcon("move.gif"));
            translateBehaviorActive = false;
        } 
        else 
        { // activate mouse pick translation
            form.setBehaviourType(form.ENABLE_BEHAVIOUR_MOUSE_PICK_TRANSLATE);
            movePluginButton.setIcon(getImageIcon("ac_move.gif"));
//            rotatePluginButton.setIcon(getImageIcon("rotate.gif"));
//            scalePluginButton.setIcon(getImageIcon("scale.gif"));
//            pickPointPluginButton.setIcon(getImageIcon("picktool.gif"));
//            distancePluginButton.setIcon(getImageIcon("distance.gif"));
            rotateBehaviorActive = false;
            scaleBehaviorActive = false;  
            pickPointBehaviorActive = false;
            distanceBehaviorActive = false;
            translateBehaviorActive = true;
        }
           
        form.setOperation(OP_SWITCH_NAV_MODE);
        this.performAction("changeBehaviour", form);
    }
  
    /**
     * Highlights the rotate button and calls the corresponding action via
     * controller to enable a rotation behavior.
     *
     * @param event A reference from ActionEvent, a wrapper for the object
     *              which caused the event.
     */
    private void rotateActionPerformed(ActionEvent evt) 
    {
        ChangeBehaviourForm form = (ChangeBehaviourForm)useForm("changeBehaviour");
        // first switch to editor mode, if necessary
        if (navigationMode) 
        {
            modePluginButton.setIcon(getImageIcon("select.gif"));
            modePluginButton.setPressedIcon(getImageIcon("pr_select.gif"));
            navigationMode = false;
            getParentFrame().setNavigationMode(navigationMode);
        }

        // if the rotate button is already active, make it inactive and disable
        // mouse pick rotation
        if (rotateBehaviorActive) 
        {
            form.setBehaviourType(form.DISABLE_BEHAVIOUR_MOUSE_PICK_ROTATE);
//            rotatePluginButton.setIcon(getImageIcon("rotate.gif"));
            rotateBehaviorActive = false;
        } 
        else 
        { // activate mouse pick rotation
            form.setBehaviourType(form.ENABLE_BEHAVIOUR_MOUSE_PICK_ROTATE);
            movePluginButton.setIcon(getImageIcon("move.gif"));
//            rotatePluginButton.setIcon(getImageIcon("ac_rotate.gif"));
//            scalePluginButton.setIcon(getImageIcon("scale.gif"));
//            pickPointPluginButton.setIcon(getImageIcon("picktool.gif"));
//            distancePluginButton.setIcon(getImageIcon("distance.gif"));
            translateBehaviorActive = false;
            scaleBehaviorActive = false;
            pickPointBehaviorActive = false;
            distanceBehaviorActive = false;
            rotateBehaviorActive = true;
        }
        
        form.setOperation(OP_SWITCH_NAV_MODE);
        this.performAction("changeBehaviour", form);
    }
  
    /**
     * Highlights the scale button and calls the corresponding action via
     * controller to enable a scaling behavior
     *
     * @param event A reference from ActionEvent, a wrapper for the object
     *              which caused the event.
     */
    private void scaleActionPerformed(ActionEvent evt) 
    {
        ChangeBehaviourForm form = (ChangeBehaviourForm)useForm("changeBehaviour");

        // first switch to editor mode, if necessary
        if (navigationMode) 
        {
            modePluginButton.setIcon(getImageIcon("select.gif"));
            modePluginButton.setPressedIcon(getImageIcon("pr_select.gif"));
            navigationMode = false;
            getParentFrame().setNavigationMode(navigationMode);
        }

        // if the scale button is already active, make it inactive and disable
        // mouse pick scaling
        if (scaleBehaviorActive) 
        {
            form.setBehaviourType(form.DISABLE_BEHAVIOUR_MOUSE_PICK_SCALE);
//            scalePluginButton.setIcon(getImageIcon("scale.gif"));
            scaleBehaviorActive = false;
        } 
        else 
        { // activate mouse pick scaling
            form.setBehaviourType(form.ENABLE_BEHAVIOUR_MOUSE_PICK_SCALE);
            movePluginButton.setIcon(getImageIcon("move.gif"));
//            rotatePluginButton.setIcon(getImageIcon("rotate.gif"));
//            scalePluginButton.setIcon(getImageIcon("ac_scale.gif"));
//            pickPointPluginButton.setIcon(getImageIcon("picktool.gif"));
//            distancePluginButton.setIcon(getImageIcon("distance.gif"));
            translateBehaviorActive = false;
            rotateBehaviorActive = false;
            pickPointBehaviorActive = false;
            distanceBehaviorActive = false;
            scaleBehaviorActive = true;
        }
        
        form.setOperation(OP_SWITCH_NAV_MODE);
        this.performAction("changeBehaviour", form);
    }
  
    /**
     * Calls the corresponding delete action
     *
     * @param event A reference from ActionEvent, a wrapper for the object
     *              that caused the event.
     */
    private void deleteActionPerformed(ActionEvent evt) 
    {
        DeleteObjectForm form = (DeleteObjectForm)useForm("deleteObject");
        this.performAction("deleteObject", form);
    }
  
    /**
     * Calls the corresponding reset action
     *
     * @param event A reference from ActionEvent, a wrapper for the object
     *              that caused the event.
     */
    private void resetActionPerformed(ActionEvent evt) {
        ResetViewForm form = (ResetViewForm)useForm("resetView");
        this.performAction("resetView", form);
    }
  
    private void switchViewModeActionPerformed(ActionEvent evt) 
    {
        Window window = getParentFrame();

        if (oneViewMode) 
        {
            viewModePluginButton.setIcon(getImageIcon("view1.gif"));
            viewModePluginButton.setPressedIcon(getImageIcon("pr_view1.gif"));
            oneViewMode = false;
            window.setFourViews(); 
        } 
        else 
        {
            viewModePluginButton.setIcon(getImageIcon("view4.gif"));
            viewModePluginButton.setPressedIcon(getImageIcon("pr_view4.gif"));
            oneViewMode = true;
            window.setPerspectiveView();
        }
    }
    
    private void changeDirectionActionPerformed(ActionEvent event) 
    {
        JRadioButton jrb = (JRadioButton) event.getSource();
        boolean bSelected = jrb.isSelected();
        String rbName = jrb.getText();
        
        buttonGroup.setSelected(jrb, bSelected);
        
        System.out.println(rbName + " -> selected ->" + bSelected);
        int newDirection = -1;
        
        ChangeFunctionDirectionForm form = (ChangeFunctionDirectionForm)useForm("changeFunctionDirection");
        
        if(rbName.equalsIgnoreCase("PERSPECT")) 
        {
            newDirection = GraphicsConstants.DIRECTION_PERSPECTIVE;
        } else if (rbName.equalsIgnoreCase("XY")) {
            newDirection = GraphicsConstants.DIRECTION_XY;
        } else if (rbName.equalsIgnoreCase("XZ")) {
            newDirection = GraphicsConstants.DIRECTION_XZ;
        } else if (rbName.equalsIgnoreCase("YZ")) {
            newDirection = GraphicsConstants.DIRECTION_YZ;
        } else if (rbName.equalsIgnoreCase("X")) {
            newDirection = GraphicsConstants.DIRECTION_X;
        } else if (rbName.equalsIgnoreCase("Y")) {
            newDirection = GraphicsConstants.DIRECTION_Y;
        } else if (rbName.equalsIgnoreCase("Z")) {
            newDirection = GraphicsConstants.DIRECTION_Z;
        }
        
        form.setDirection(newDirection);
        
        this.performAction("changeFunctionDirection", form);
    }
    
    /**
     * Highlights the pickpopint button and calls the corresponding action via
     * controller to enable a picking point behavior.
     *
     * @param event A reference from ActionEvent, a wrapper for the object
     *              which caused the event.
     */    
    private void pickPointActionPerformed(ActionEvent evt) 
    {
        // --> System.out.println("Performed PickPoint Action");
        ChangeBehaviourForm form = (ChangeBehaviourForm)useForm("changeBehaviour");

        // first switch to editor mode, if necessary
        if (navigationMode) 
        {
            modePluginButton.setIcon(getImageIcon("select.gif"));
            modePluginButton.setPressedIcon(getImageIcon("pr_select.gif"));
            navigationMode = false;
            getParentFrame().setNavigationMode(navigationMode);
        }

        // if the pickpoint button is already active, make it inactive and disable
        // mouse pickpoint
        if (pickPointBehaviorActive) 
        {
            form.setBehaviourType(form.DISABLE_BEHAVIOUR_MOUSE_PICK_POINT);
//            pickPointPluginButton.setIcon(getImageIcon("picktool.gif"));
            pickPointBehaviorActive = false;
        } 
        else 
        { // activate mouse pick scaling
            form.setBehaviourType(form.ENABLE_BEHAVIOUR_MOUSE_PICK_POINT);
            movePluginButton.setIcon(getImageIcon("move.gif"));
//            rotatePluginButton.setIcon(getImageIcon("rotate.gif"));
//            scalePluginButton.setIcon(getImageIcon("scale.gif"));
//            pickPointPluginButton.setIcon(getImageIcon("ac_picktool.gif"));
//            distancePluginButton.setIcon(getImageIcon("distance.gif"));
            translateBehaviorActive = false;
            rotateBehaviorActive = false;
            scaleBehaviorActive = false;
            distanceBehaviorActive = false;
            pickPointBehaviorActive = true;
        }
        
        form.setOperation(OP_SWITCH_NAV_MODE);
        this.performAction("changeBehaviour", form);        
    } 
    
    /**
     * Highlights the distance meter button and calls the corresponding action via
     * controller to enable a picking point behavior.
     *
     * @param event A reference from ActionEvent, a wrapper for the object
     *              which caused the event.
     */    
    private void distanceActionPerformed(ActionEvent evt) 
    {
        System.out.println("Performed DistanceMeter Action");
        
        Container myWnd = this.getParentFrame();
        
        // here we can call to show distance GUI part
        
        ChangeBehaviourForm form = (ChangeBehaviourForm)useForm("changeBehaviour");

        // first switch to editor mode, if necessary
        if (navigationMode) 
        {
            modePluginButton.setIcon(getImageIcon("select.gif"));
            modePluginButton.setPressedIcon(getImageIcon("pr_select.gif"));
            navigationMode = false;
            getParentFrame().setNavigationMode(navigationMode);
        }

        // if the distance button is already active, make it inactive and disable
        // mouse pickpoint
        if (distanceBehaviorActive) {
            form.setBehaviourType(form.DISABLE_BEHAVIOUR_DISTANCE_METER);
//            distancePluginButton.setIcon(getImageIcon("distance.gif"));
            distanceBehaviorActive = false;
        } else { // activate mouse pick scaling
            form.setBehaviourType(form.ENABLE_BEHAVIOUR_DISTANCE_METER);
            movePluginButton.setIcon(getImageIcon("move.gif"));
//            rotatePluginButton.setIcon(getImageIcon("rotate.gif"));
//            scalePluginButton.setIcon(getImageIcon("scale.gif"));
//            pickPointPluginButton.setIcon(getImageIcon("picktool.gif"));
//            distancePluginButton.setIcon(getImageIcon("ac_distance.gif"));
            translateBehaviorActive = false;
            rotateBehaviorActive = false;
            scaleBehaviorActive = false;
            pickPointBehaviorActive = false;
            distanceBehaviorActive = true;
        }
        
        form.setOperation(OP_SWITCH_NAV_MODE);
        this.performAction("changeBehaviour", form);        
    }
    
    /**
     * Updates some components with specific data from
     * the session object. The data is normaly added in
     * a plugin.
     *
     * @param session A hashtable containing the specific data.
     */
    protected void update(Session session) { }  

    private JPanel createToolPanel(String title) {
        JPanel subMenuPanel = new JPanel();
        subMenuPanel.setLayout(new BorderLayout());
        subMenuPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
        subMenuPanel.setMaximumSize(new java.awt.Dimension(160, 400));
        subMenuPanel.setMinimumSize(new java.awt.Dimension(160, 120));
        subMenuPanel.setPreferredSize(new java.awt.Dimension(160, 400));
    
        // create the plugin menu's title bar
        JPanel subMenuTitle = new JPanel();
        subMenuTitle.setLayout(new FlowLayout(FlowLayout.LEFT, 4, 0));            
               
        subMenuTitle.setBorder(new EtchedBorder());
        subMenuTitle.setMaximumSize(new Dimension(80, 20));
        subMenuTitle.setMinimumSize(new Dimension(80, 20));
        subMenuTitle.setPreferredSize(new Dimension(80, 20));
    
        JLabel subMenuTitleLabel = new JLabel(title, SwingConstants.LEFT);
        subMenuTitleLabel.setFont(DEFAULT_FONT);
    
        subMenuTitle.add(subMenuTitleLabel);
        
        // crates a place for tool operators panel ...
        JPanel innerToolPanel = new JPanel();
        innerToolPanel.setName("innerToolPanel");
        
        innerToolPanel.setBorder(new EtchedBorder());
        
//        SampleVToolOperatorsForm innerToolPanel = new SampleVToolOperatorsForm();
//        innerToolPanel.setup();
        innerToolPanel.setName("innerToolPanel");        
        innerToolPanel.setMaximumSize(new Dimension(80, 400));
        innerToolPanel.setMinimumSize(new Dimension(80, 400));
        innerToolPanel.setPreferredSize(new Dimension(80, 400));  
        
        subMenuPanel.add(subMenuTitle, BorderLayout.NORTH);
        subMenuPanel.add(innerToolPanel, BorderLayout.CENTER);

    
        return subMenuPanel;
    }
    
    public void updateToolOperators(VToolOperatorsForm vToolOperatorsForm) 
    {
        JPanel innerToolPanel = (JPanel) toolPanel.getComponent(1);
        String name = innerToolPanel.getName();
        if(innerToolPanel.getComponentCount() != 0) 
        {
            if(innerToolPanel.getComponent(0).equals(vToolOperatorsForm)) 
            {
                return;
            }
            innerToolPanel.remove(0);
        }
        vToolOperatorsForm.setDirty(false);
        innerToolPanel.add(vToolOperatorsForm);
        vToolOperatorsForm.setVisible(true);
        this.setVisible(true);
        this.validate();
        this.updateUI();
    }
    
    public void removeGUI(VToolOperatorsForm vToolOperatorsForm) 
    {
        
        JPanel innerToolPanel = (JPanel) toolPanel.getComponent(1);
        String name = innerToolPanel.getName();
        if(innerToolPanel.getComponentCount() != 0) 
        {
            VToolOperatorsForm component = (VToolOperatorsForm) innerToolPanel.getComponent(0);
            if(component.equals(vToolOperatorsForm)) 
            {
                innerToolPanel.remove(0);
            }
            innerToolPanel.validate();
            innerToolPanel.updateUI();
        }
        vToolOperatorsForm.setVisible(false);
        this.setVisible(true);
        this.validate();   
        this.updateUI();
    }    
        
}
