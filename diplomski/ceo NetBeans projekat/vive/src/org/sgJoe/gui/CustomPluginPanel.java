package org.sgJoe.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.Hashtable;
import java.util.Iterator;
import javax.swing.*;
import javax.swing.border.*;
import org.sgJoe.logic.Tool;
import org.sgJoe.logic.ToolRegistry;
import org.sgJoe.plugin.*;

import org.sgJoe.logic.Session;

import org.apache.log4j.Logger;
import org.sgJoe.tools.interfaces.VTool;
import org.sgJoe.tools.interfaces.VToolForm;
import org.sgJoe.tools.interfaces.VToolPlugin;
import org.sgJoe.tools.interfaces.VUIToolForm;
import org.VIVE.gui.logic.LogicGuiManager;
import org.VIVE.gui.tabbedPanel;


/**
 * CustomPluginPanel configures the panel containing the custom plugins.
 * The corresponding action calls are defined here, as well.
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:            0.1         $
 * @date     $ Date:  December 4, 2005 7:14 PM  $
 */
public class CustomPluginPanel extends SGPanel {
    
    private static Logger logger = Logger.getLogger(CustomPluginPanel.class);
    private tabbedPanel ToolViewPanel = new tabbedPanel(); // Ovaj treba da prima informaciju 
                        //o promeni fokusa na sceni i da promeni svoj sadrzaj i eventualno
                        //ToolOptionPanel.
        //Najverovatnije treba nova komponenta izvedena iz tabedPanel. Za osmisliti.
    
    private ToolRegistry toolRegistryRef = null;
    private LogicGuiManager logicGuiManager;
    
    // lights
    private JButton hideLightSymbolsButton = null;
    private boolean lightSymbolsHidden = false;
    
    // -> private ImagePool imageIconPool = ImagePool.instance();
    // --- nikola --- Konstruktor prima referencu na tabbedPanel gde ce se prikazivati Paneli alata
    // a taj tabbedPanel zna kako da azurira toolOptionPanel. Informacije mu da addingToolPanel
    public CustomPluginPanel(ToolRegistry toolRegistryRef, tabbedPanel toolViewPanel,LogicGuiManager LGM) {
        super();
        this.toolRegistryRef = toolRegistryRef;
        ToolViewPanel = toolViewPanel;
        logicGuiManager = LGM;
    }
    
      
    /**
     * Put everything together
     */
    protected void setup() {
        logger.debug("init CustomPluginComponent");
    
        JPanel pluginPanel = new JPanel();
    
        pluginPanel.setLayout(new java.awt.FlowLayout(FlowLayout.LEFT));
    
        pluginPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
        //pluginPanel.setMinimumSize(new Dimension(270, 80));
        pluginPanel.setPreferredSize(new Dimension(256, 80));
    
        //JTabbedPane customPluginsPane = new JTabbedPane();
        //customPluginsPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        //customPluginsPane.setFont(DEFAULT_FONT);
        //customPluginsPane.setMinimumSize(new java.awt.Dimension(500, 64));
        //customPluginsPane.setPreferredSize(new java.awt.Dimension(500, 64));
    
        // Primitives panel
        // create the buttons and put them into the panel
    
//        JButton spherePluginButton = createButton("sphere.gif", "Insert Sphere");
//        spherePluginButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent evt) {
//                drawSphereActionPerformed(evt);
//            }
//        });
//        
//        JButton cubePluginButton = createButton("cube.gif", "Insert Cube");
//        cubePluginButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent evt) {
//                drawCubeActionPerformed(evt);
//            }
//        });
//    
//        JButton planePluginButton = createButton("plane.gif", "Insert Plane");
//        planePluginButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent evt) {
//                drawPlaneActionPerformed(evt);
//            }
//        });
    
//        JPanel solidsPanel = new JPanel();
//        solidsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 2));
//        solidsPanel.setPreferredSize(new Dimension(42, 42));
//        solidsPanel.add(spherePluginButton);
//        solidsPanel.add(cubePluginButton);
//        solidsPanel.add(planePluginButton);
//    
//        customPluginsPane.addTab("Primitives", solidsPanel);
        
//        // scene graph panel
//        JButton sceneGraphButton = createButton("scenegraph.gif", "Show Scene Graph");
//        sceneGraphButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent evt) {
//                showSceneGraphActionPerformed(evt);
//            }
//        });
//    
//        JPanel sceneGraphPanel = new JPanel();
//        sceneGraphPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 2));
//        sceneGraphPanel.setPreferredSize(new Dimension(42, 42));
//        sceneGraphPanel.add(sceneGraphButton);
//    
//        customPluginsPane.addTab("SceneGraph", sceneGraphPanel);
        // --- Nikola---
        // Ubacio sam tabbedPanel i addingToolPanel.
        // Trebam da ih postavim na ekranu.
        // ToolViewPanel treba da se poveze sa drugim tabbed panelom (ToolOptionPanel)
        JPanel toolBoxPanel = (JPanel) logicGuiManager.getToolBox().getSGPanel();
//                new addingToolPanel(toolRegistryRef, ToolViewPanel, logicGuiManager);
        toolBoxPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        //toolBoxPanel.setPreferredSize(new Dimension(42, 42));
        toolBoxPanel.setMaximumSize(this.getSize());
        
        // creating and populating toolbox with tool
        // --- Nikola --- ovo radi klasa addingToolPanel
//        Iterator toolIt = toolRegistryRef.getIterator();
//        while(toolIt.hasNext()) {
//            Tool tool = (Tool) toolIt.next();     
//            VTool vTool = tool.getVTool();
//            String icon = tool.getIcon();
//            
//            JButton toolButton = createButton(icon, tool.getName());
//            toolButton.setActionCommand(tool.getName());
//            toolButton.addActionListener(new ActionListener() {
//                public void actionPerformed(ActionEvent evt) {
//                    createToolActionPerformed(evt);
//                }
//            });
//            toolBoxPanel.add(toolButton);
//        }
        
        //customPluginsPane.addTab("ToolBox", toolBoxPanel); 
         
        
        //pluginPanel.add(customPluginsPane);
        pluginPanel.add(toolBoxPanel);

        add(pluginPanel);
    
    }
  

    // ----------------------------------------------------------------------- //
    // Primitive types to draw
    // ----------------------------------------------------------------------- //
  
    /**
     * Sends the command to create a cube. Takes an existing form
     * and delegates it to the controller via the performAction method.
     *
     * @param evt A reference from ActionEvent, a wrapper for the object
     *            that caused the event.
     */
    private void drawCubeActionPerformed(ActionEvent evt) {
        drawPrimitive(DrawPrimitiveForm.PRIMITIVE_CUBE);
    }
  
    /**
     * Sends the command to create a sphere. Takes an existing form
     * and delegates it to the controller via the performAction method.
     *
     * @param evt A reference from ActionEvent, a wrapper for the object
     *            that caused the event.
     */
    private void drawSphereActionPerformed(ActionEvent evt) {
        drawPrimitive(DrawPrimitiveForm.PRIMITIVE_SPHERE);
    }
  
    /**
     * Sends the command to create a plane. Takes an existing form
     * and delegates it to the controller via the performAction method.
     *
     * @param evt A reference from ActionEvent, a wrapper for the object
     *            that caused the event.
     */
    private void drawPlaneActionPerformed(ActionEvent evt) {
        drawPrimitive(DrawPrimitiveForm.PRIMITIVE_PLANE);
    }
  
    /**
     * Sends the command to create a 3d line. Takes an existing form
     * and delegates it to the controller via the performAction method.
     *
     * @param evt A reference from ActionEvent, a wrapper for the object
     *            that caused the event.
     */
    private void drawLine3dActionPerformed(ActionEvent evt) {
        drawPrimitive(DrawPrimitiveForm.PRIMITIVE_LINE3D);
    }
    
    /**
     * Draws a primitive.
     *
     * @param primitiveType The type of primitive to draw.
     */
    private void drawPrimitive(int primitiveType) {
        DrawPrimitiveForm form = (DrawPrimitiveForm)useForm("drawPrimitive");
        form.setPrimitive(primitiveType);
        this.performAction("drawPrimitive", form);
    }
   
    private void showSceneGraphActionPerformed(ActionEvent evt) {
        ContextSwitchForm form = (ContextSwitchForm)useForm("contextSwitch");
        form.setContextMenu("sceneGraphContext");
        this.performAction("contextSwitch", form);
    }
     
    private void createToolActionPerformed(ActionEvent evt) {
        CreateToolForm form = (CreateToolForm)useForm("createTool");
        form.setToolName(evt.getActionCommand());
        
        this.performAction("createTool", form);
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
