package org.sgJoe.gui;

import java.awt.Font;

import java.awt.event.*;
import javax.swing.*;

import org.apache.log4j.Logger;


/*
 * This class creates the GUI's menu bar when createMenuBar is called.
 * Although it does extend SGPanel, SGMenuBar is not exactly a panel, but
 * it makes use of the superclass methods.
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: December 5, 2005  9:13 AM  $
 */

public class SGMenuBar {
    
    private static Logger logger = Logger.getLogger(SGMenuBar.class);
  
    protected static final Font MENU_FONT = new Font("Dialog", 0, 11);
    private ActionPool actionPool = ActionPool.getInstance();    
    
    /** create the menu bar */
    public JMenuBar createMenuBar() {
        
        JMenuBar menuBar = new JMenuBar();
        menuBar.setFont(MENU_FONT);
    
        // Menu File
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('f');
        fileMenu.setFont(MENU_FONT);
    
        // Entries in Menu File
        JMenuItem newMenuItem = new JMenuItem("New");
        newMenuItem.setMnemonic('n');
        newMenuItem.setFont(MENU_FONT);
        // add the action
        newMenuItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent evt) {
            actionPool.deleteSceneActionPerformed(evt);
        }
        });
    
        JMenuItem openObjectFileMenuItem = new JMenuItem("Open OBJ File...");
        openObjectFileMenuItem.setMnemonic('o');
        openObjectFileMenuItem.setFont(MENU_FONT);
    
        openObjectFileMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                actionPool.loadObjFileActionPerformed(evt);
            }
        });


        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setMnemonic('x');
        exitMenuItem.setFont(MENU_FONT);
        // add the action
        exitMenuItem.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            actionPool.exitActionPerformed(evt);
          }
        });
    
        fileMenu.add(newMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(openObjectFileMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);
    
        menuBar.add(fileMenu);
    
        // Menu Misc
        JMenu miscMenu = new JMenu("Misc");
        miscMenu.setMnemonic('m');
        miscMenu.setFont(MENU_FONT);
    
        // Entries in Menu Help
   
        // navigation modes
        ButtonGroup group = new ButtonGroup();
        JRadioButtonMenuItem jrOrbitMenuItem = new JRadioButtonMenuItem("Orbit Navigation");
        jrOrbitMenuItem.setSelected(true);
        jrOrbitMenuItem.setMnemonic('o');
        jrOrbitMenuItem.setFont(MENU_FONT);
        jrOrbitMenuItem.addItemListener(new ItemListener() {
          public void itemStateChanged(ItemEvent evt)
          {
            boolean orbitNav = (evt.getStateChange() == evt.SELECTED);
            actionPool.switchNavigationModeActionPerformed(orbitNav);
          }
        });
        group.add(jrOrbitMenuItem);

        // picking modes
        ButtonGroup group2 = new ButtonGroup();
        JRadioButtonMenuItem jrPickAllMenuItem = new JRadioButtonMenuItem("Pick Entire Subgraph");
        jrPickAllMenuItem.setSelected(true);
        jrPickAllMenuItem.setMnemonic('p');
        jrPickAllMenuItem.setFont(MENU_FONT);
        jrPickAllMenuItem.addItemListener(new ItemListener() {
          public void itemStateChanged(ItemEvent evt)
          {
            boolean pickMode = (evt.getStateChange() == evt.SELECTED);
            actionPool.switchPickingModeActionPerformed(pickMode);
          }
        });
        group2.add(jrPickAllMenuItem);

        JRadioButtonMenuItem jrPickClosestMenuItem = new JRadioButtonMenuItem("Pick Closest Object");
        jrPickClosestMenuItem.setMnemonic('c');
        jrPickClosestMenuItem.setFont(MENU_FONT);
        group2.add(jrPickClosestMenuItem);
    
        miscMenu.addSeparator();
        miscMenu.add(jrOrbitMenuItem);
        miscMenu.addSeparator();
        miscMenu.add(jrPickAllMenuItem);
        miscMenu.add(jrPickClosestMenuItem);
    
        menuBar.add(miscMenu);  
    
        // Menu Help
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('h');
        helpMenu.setFont(MENU_FONT);

        // Entries in Menu Help
        JMenuItem aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.setMnemonic('a');
        aboutMenuItem.setFont(MENU_FONT);
        // add the action
        aboutMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                actionPool.showAboutScreenActionPerformed(evt);
            }
        });

        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);
    
        return menuBar;   
    }
}
