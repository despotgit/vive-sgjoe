package org.sgJoe.gui;


import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import org.sgJoe.plugin.*;

import java.util.HashMap;
import javax.swing.border.TitledBorder;
import java.awt.FlowLayout;
import org.sgJoe.gui.SceneGraphTreeRenderer;
import org.sgJoe.logic.Session;


import org.apache.log4j.Logger;

/**
 * This panel displays the scene graph of the current scene.
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:            0.1         $
 * @date     $ Date:  December 4, 2005 10:16 PM $
 */
public class SceneGraphPanel extends SGPanel {
    
    private static Logger logger = Logger.getLogger(SceneGraphPanel.class);
    
    private DefaultMutableTreeNode top = null;
    private JTree tree = null;
    private JScrollPane treeView = null;
    private JSplitPane splitPane = null;
  
    private JButton reload = null;
    private JButton expand = null;
  
    private boolean expanded = false;    
    
    
    protected void setup() {
        setBorder(new TitledBorder(null, "Scene Graph", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    
        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
    
        // Reload button
        reload = new JButton();
        reload.setFont(DEFAULT_FONT);
        reload.setText("Update");
        reload.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent evt) {
                reloadActionPerformed(evt);
            }
        });
        buttonPanel.add(reload);
    
        // Expand button
        expand = new JButton();
        expand.setFont(DEFAULT_FONT);
        expand.setText("Expand");
        expand.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            expandActionPerformed(evt);
          }
        });
        buttonPanel.add(expand);

        splitPane.setTopComponent(buttonPanel);
    
        top = new DefaultMutableTreeNode(GuiConstants.GRAPH_SCENE_GRAPH);
        // Create a tree that allows one selection at a time.
        tree = new JTree(top);
    
        ImageIcon bGIcon = imageIconPool.getImage("graphBG.gif");
        ImageIcon tGIcon = imageIconPool.getImage("graphTG.gif");
        ImageIcon s3DIcon = imageIconPool.getImage("graphS3D.gif");
    
        HashMap iconMap = new HashMap();
        iconMap.put(GuiConstants.GRAPH_BRANCH_GROUP, imageIconPool.getImage("graphBG.gif"));
        iconMap.put(GuiConstants.GRAPH_TRANSFORM_GROUP, imageIconPool.getImage("graphTG.gif"));
        iconMap.put(GuiConstants.GRAPH_SHAPE_3D, imageIconPool.getImage("graphS3D.gif"));
        iconMap.put(GuiConstants.GRAPH_APPEARANCE, imageIconPool.getImage("graphApp.gif"));
        iconMap.put(GuiConstants.GRAPH_TRIANGLE_STRIP_ARRAY, imageIconPool.getImage("graphTSA.gif"));
        iconMap.put(GuiConstants.GRAPH_TRIANGLE_FAN_ARRAY, imageIconPool.getImage("graphTFA.gif"));
        iconMap.put(GuiConstants.GRAPH_TRIANGLE_ARRAY, imageIconPool.getImage("graphTA.gif"));
        iconMap.put(GuiConstants.GRAPH_SPHERE, imageIconPool.getImage("graphsphere.gif"));
        iconMap.put(GuiConstants.GRAPH_CUBE, imageIconPool.getImage("graphcube.gif"));
        iconMap.put(GuiConstants.GRAPH_PLANE, imageIconPool.getImage("graphplane.gif"));
        iconMap.put(GuiConstants.GRAPH_SCENE_GRAPH, imageIconPool.getImage("graphsymbol.gif"));
        iconMap.put(GuiConstants.GRAPH_SWITCH, imageIconPool.getImage("graphswitch.gif"));
    
        DefaultTreeCellRenderer renderer = new SceneGraphTreeRenderer(iconMap);
        tree.setCellRenderer(renderer);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    
        // Create the scroll pane and add the tree to it. 
        treeView = new JScrollPane(tree);

        splitPane.setBottomComponent(treeView);

        add(splitPane);
    }
    
  
      private void reloadActionPerformed(ActionEvent evt) {
        expanded = true;
        expand.setText("Expand");
        SceneGraphViewForm form = (SceneGraphViewForm)useForm("sceneGraphView");
        form.setOperation(Constants.OP_TREE_RELOAD);
        this.performAction("sceneGraphView", form);
      }
      
      private void expandActionPerformed(ActionEvent evt) {
        SceneGraphViewForm form = (SceneGraphViewForm)useForm("sceneGraphView");
        if(expanded) {
          form.setOperation(Constants.OP_TREE_EXPAND);
          expand.setText("Collapse");
          expanded = false;
        } else {
          form.setOperation(Constants.OP_TREE_COLLAPSE);
          expand.setText("Expand");
          expanded = true;
        }
        form.setTree(tree);
        this.performAction("sceneGraphView", form);
      }
  
      /**
       * Updates some components with specific data from
       * the session object. The data is normaly added in
       * a plugin.
       *
       * @param session A hashtable containing the specific data.
       */
      protected void update(Session session) {
          DefaultMutableTreeNode tmpNode = (DefaultMutableTreeNode) session.remove("SCENE_GRAPH_VIEW");

          if (tmpNode != null) {
              synchronized(top) {
            top.removeAllChildren();
            top.add(tmpNode);
            tree.updateUI();
          }
        }
      }    
}
