package org.sgJoe.gui;

import javax.swing.*;
import javax.swing.tree.*;

import java.awt.Component;
import java.util.HashMap;

import org.apache.log4j.Logger;

/**
 * This modified TreeCellRenderer displays different icons according to the
 * scene graph components.
 *
 * @author   $ Author: Aleksandar Babic $
 * @version  $ Revision:            0.1 $
 * @date     $ Date:  December 6, 2005 6:07 PM $
 */
public class SceneGraphTreeRenderer extends DefaultTreeCellRenderer {
    
    private static Logger logger = Logger.getLogger(SceneGraphTreeRenderer.class);
    
    // this hashmap maps class names (which are displayed in the scene graph) to
    // their corresponding icons.
    HashMap iconMap = null;
  
    public SceneGraphTreeRenderer(HashMap iconMap) {
      this.iconMap = iconMap;
    }

    public Component getTreeCellRendererComponent(
                        JTree tree,
                        Object value,
                        boolean sel,
                        boolean expanded,
                        boolean leaf,
                        int row,
                        boolean hasFocus) {
          super.getTreeCellRendererComponent(
                        tree, value, sel,
                        expanded, leaf, row,
                        hasFocus);

          // get the icon corresponding to the node name
          DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
          String nodeName = (String)(node.getUserObject());
          Icon icon = (Icon)iconMap.get(nodeName);
          if (icon != null) {
              setIcon(icon);
          }

          return this;
    }
      
}
