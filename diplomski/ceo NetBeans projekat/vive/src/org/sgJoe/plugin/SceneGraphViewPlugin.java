package org.sgJoe.plugin;

import javax.swing.tree.*;
import javax.media.j3d.*;
import org.sgJoe.logic.*;

import java.util.Enumeration;
import javax.swing.JTree;
import org.sgJoe.exception.SGPluginException;
import org.sgJoe.graphics.SceneGraphEditor;

import org.apache.log4j.Logger;
/**
 * This plugin shows the scene graph to the user in an easy to understand
 * manner. The used view component here is JTree and DefaultMutableTree from
 * the swing classes.
 *
 * @author   $ Author: Aleksandar Babic             $
 * @version  $ Revision:            0.1             $
 * @date     $ Date:  December 22, 2005 12:52 PM    $
 */
public class SceneGraphViewPlugin extends Plugin {
    
    private static Logger logger = Logger.getLogger(SceneGraphViewPlugin.class);
    
    private DefaultMutableTreeNode jtreeTop = null;
  
    private ApplicationResources appResources = ApplicationResources.instance();
  
    /**
     * Process a specific user action. The form contains the necessary
     * arguments which comes from the GUI. The scene graph editor directly
     * take influence to the current scene graph. The session stores
     * additional information, which should ne shown in a specific GUI
     * component.
     * 
     * 
     * 
     * @param form The form with the necessary arguments.
     * @param editor A scene graph editor.
     * @param session Stores additional information for the GUI.
     * @throws SGPluginException If an exception occures.
     */
    public void performAction(Form form, SceneGraphEditor editor, Session session) throws SGPluginException {
        SceneGraphViewForm sceneGraphForm = (SceneGraphViewForm)form;
        JTree tree = null;
    
        switch(sceneGraphForm.getOperation()) {
            case OP_TREE_RELOAD:
                jtreeTop = new DefaultMutableTreeNode("Scene Graph");
        
            // get root from scene graph
            BranchGroup top = editor.getSceneGraphTop();        
            _travers(top, jtreeTop);
        
            // save the jTree in the user session, so we can access it again
            // in the corresponding panel.
            session.put("SCENE_GRAPH_VIEW", jtreeTop.getNextNode());
        
            // set a message for the info panel
            session.setContextDialog("Generate scene graph view");
            session.setContextPanelKey("sceneGraphContext");
            break;
            
        case OP_TREE_EXPAND:
            tree = sceneGraphForm.getTree();
            if(tree != null) {
                expandAll(tree);
            }
            break;
            
        case OP_TREE_COLLAPSE:
            tree = sceneGraphForm.getTree();
            if(tree != null) {
                collapseAll(tree);
            }
            break;
        }
    }
  
  /**
   * Expand the tree.
   *
   * @param tree The tree to expand.
   */
    private void expandAll(JTree tree) {
        int row = 0;
        while (row < tree.getRowCount()) {
            tree.expandRow(row);
            row++;
        }
    }
  
  /**
   * Expand all to last.
   *
   * @param tree The tree to expand.
   */
    private void expandAll2Last(JTree tree) {
      // expand to the last leaf from the root
        DefaultMutableTreeNode  root;
        root = (DefaultMutableTreeNode) tree.getModel().getRoot();
        tree.scrollPathToVisible(new TreePath(root.getLastLeaf().getPath()));
    }
  
  /**
   * Collapse the tree.
   *
   * @param tree The tree to collapse.
   */
    private void collapseAll(JTree tree) {
        int row = tree.getRowCount() - 1;
        while (row >= 0) {
            tree.collapseRow(row);
            row--;
        }
    }
  
  /**
   * Internal use: Traverses the scene graph and adds each found node to
   * the JTree.
   *
   * @param parent The parent node.
   * @param treeTop The parent element of the <code>DefaultMutableTreeNode</code>.
   */
    private void _travers(Node parent, DefaultMutableTreeNode treeTop) {
        // replace the class name with a shorter class name.
        String className = appResources.getResource(parent.getClass().getName());
        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(className);
        treeTop.add(treeNode);
    
        // if we have a shape3D node, try to determine the appearance and the
        // geometrie.
        if (parent instanceof Shape3D) {
            Shape3D shape = (Shape3D)parent;
            // check for appearance
            Appearance app = shape.getAppearance();
            if (app != null) {
                className = appResources.getResource(app.getClass().getName());
                if (className != null) {
                    treeNode.add(new DefaultMutableTreeNode(className));
                } else {
                    treeNode.add(new DefaultMutableTreeNode(app.getClass().getName()));
                }
            }
            // check for geometrie
            Enumeration enumeration = shape.getAllGeometries();
            while (enumeration.hasMoreElements()) {
                Geometry geo = (Geometry)enumeration.nextElement();
                 if (geo != null) {
                    className = appResources.getResource(geo.getClass().getName());
                    if (className != null) {
                        treeNode.add(new DefaultMutableTreeNode(className));
                    } else {
                        treeNode.add(new DefaultMutableTreeNode(geo.getClass().getName()));
                    }
                 }
            } // end while
        }
    
        // if node is of typ Group, recurse down the tree. visit all it's children
        if (parent instanceof Group) {
            Enumeration enumeration = ((Group)parent).getAllChildren();
            while (enumeration.hasMoreElements()) {
                Node child = (Node)enumeration.nextElement();
                _travers(child, treeNode);
            }
        }
    }
    
}
