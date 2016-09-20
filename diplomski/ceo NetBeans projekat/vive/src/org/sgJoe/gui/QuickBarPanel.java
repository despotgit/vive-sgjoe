package org.sgJoe.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.sgJoe.logic.Session;

import org.apache.log4j.Logger;

/**
 * The QuickBar enables quick access to sgJoe's common functions like
 * loading/saving files and resetting the scene.
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:            0.1         $
 * @date     $ Date:  December 3, 2005 11:05 PM $
 */
public class QuickBarPanel extends SGPanel {
    
    private static Logger logger = Logger.getLogger(QuickBarPanel.class);
    
    private FileDialog fileDialog;
    private ActionPool actionPool = ActionPool.getInstance();
    private ImagePool imageIconPool = ImagePool.instance();
  
    /**
     * Helper method to create quickbar buttons
     *
     * @param icon        The icon's file name
     * @param tooltipText String that will be shown as tooltip text.
     * @return JButton    The newly created JButton.
     */
    private JButton createQuickbarButton(String icon, String tooltipText) { 
        JButton jButton = new JButton(imageIconPool.getImage(icon));
        jButton.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton.setMinimumSize(new java.awt.Dimension(32, 32));
        jButton.setPreferredSize(new java.awt.Dimension(32, 32));
        jButton.setToolTipText(tooltipText);
        jButton.setBorderPainted(false);
        return jButton;
    }
  
    /**
     * Creates the GUI for this panel.
     */
    protected void setup() {
        logger.debug("init QuickBarPanel");
    
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
    
        JToolBar quickBar = new JToolBar();
        quickBar.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
    
        JButton newSceneButton = createQuickbarButton("newscene.gif", "New Scene");
        newSceneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                actionPool.deleteSceneActionPerformed(event);
            }
        });
        
        JButton loadObjFileButton = createQuickbarButton("loadobj.gif", "Load OBJ File");
        loadObjFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                actionPool.loadObjFileActionPerformed(event);
            }
        });

        quickBar.add(newSceneButton);
        quickBar.addSeparator();
    
        quickBar.add(loadObjFileButton);
        quickBar.addSeparator();

        this.add(quickBar);
    }
  
    /**
     * Updates some components with specific data from
     * the session object. The data is normaly added in
     * a plugin.
     *
     * @param session A hashtable containing the specific data.
     */
    protected void update(Session session) { }
    
}
