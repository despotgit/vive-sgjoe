package org.sgJoe.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.ToolTipManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import org.VIVE.gui.logic.LogicGuiManager;
import org.VIVE.gui.tabbedPanel;
import org.VIVE.gui.viewManagerPanel;
import org.apache.log4j.Logger;

import java.awt.*;
import java.util.Hashtable;
import javax.swing.JFrame;
import org.sgJoe.exception.SGException;
import org.sgJoe.gui.CustomPluginPanel;
import org.sgJoe.gui.PrimitiveContext;

import org.sgJoe.logic.*;
import org.sgJoe.tools.sample.SampleVUIToolForm;
import org.sgJoe.tools.interfaces.VToolOperatorsForm;
import org.sgJoe.tools.interfaces.VUIToolForm;


/*
 * This class puts all the panels together to create the application's GUI.
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: December 1, 2005  5:45 PM  $
 */

public class Window extends JFrame {
    
    private static Logger logger = Logger.getLogger(Window.class);
    
    //private final static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final static Dimension DEFAULT_CONTEXT_DIM = new Dimension(280, 600);
    private final static Dimension DEFAULT_CONTEXT_DIM_2 = new Dimension(280, 250);
  
    private QuickBarPanel quickBarPanel = null;
    //private SGGUIToolForm guiToolForm = null;
    private BasePluginPanel basePluginPanel = null;
    private CustomPluginPanel customPluginPanel = null;
    private ViewPanel viewPanel = null;
    private MessagePanel messagePanel = null;
    
    private JPanel vuiToolPanel = null;
  
    // context panels
    // --> private SceneGraphPanel sceneGraphPanel = null;
    private PrimitiveContext primitiveContext = null;
    private JPanel ctxPanel = null;
    
    private Hashtable contextPanels = new Hashtable();
  
    private SGPanel currentContextPanel = null;
    private Container contentPane;    
    
    // gui-tool panel
    private SGPanel sgGUIToolPanel = null;
    
    // ---Nikola---
    private LogicGuiManager GuiManager;
    private tabbedPanel toolPanel = null;
    private tabbedPanel toolOption = null;
    
    
    private Font DEFAULT_FONT;
    
  
    /** Creates new form Window */
    public Window(ToolRegistry toolRegistryRef, LogicGuiManager LGM) {
        contentPane = this.getContentPane();
    
        // We want to show a confirmation dialog.
        // If the user doesn't want to quit without saving the scene
        // the main window has to stay open.
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    
        setTitle("sgJoe");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                exitForm(evt);
            }
        });
        // ---- Nikola --- u toolPanel ubacim Main alat
        GuiManager = LGM;
        GuiManager.setToolRegistryRef(toolRegistryRef);
        toolPanel = LGM.getActiveToolList();
        toolOption = LGM.getActiveOptionList();
        GuiManager.setActiveToolList(toolPanel);
        GuiManager.setActiveOptionList(toolOption);
    
        initComponents(toolRegistryRef);
    
        // Setting the icon for the application
        ImageIcon icon = ImagePool.instance().getImage("sgjoe.gif");
        setIconImage(icon.getImage());
    
        // Get Displayable Rectangle
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle rect = ge.getMaximumWindowBounds();
        // resize rect because we move the window
        rect.setSize(rect.width + 4, rect.height + 4);
        this.setBounds(rect);
        // Position the window so that window border is not visible
        this.setLocation(-3, -3);
        this.setResizable(false);
        
    }
  
    private void initComponents(ToolRegistry toolRegistryRef) {
        logger.debug("init GUI-components");
    
        // used to make menus and tooltips visible over canvas3d
        // (mixing of light- and heavyweight components)
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);
        ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);
    
        SGMenuBar menuBar = new SGMenuBar();
        this.setJMenuBar(menuBar.createMenuBar());
    
        quickBarPanel = new QuickBarPanel();
        quickBarPanel.setup(this);
    
        basePluginPanel = new BasePluginPanel();
        basePluginPanel.setup(this);
    
    
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
    
        // create the custom plugin panel (tabbed pane)
        // --- nikola --- Ovde treba da napravim taj tabbed panel
        customPluginPanel = new CustomPluginPanel(toolRegistryRef, toolPanel, GuiManager);
        customPluginPanel.setup(this);
        
    
        // the view panel, our window into the scene
        
        JTabbedPane viewTabbedPane = new JTabbedPane();
        viewTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        viewTabbedPane.setFont(DEFAULT_FONT);
        
        viewPanel = new ViewPanel();
        viewPanel.setup(this);
        
        // --> viewPanelTwo = new ViewPanel();
        // --> viewPanelTwo.setup(this);
        
        viewTabbedPane.addTab("view one", viewPanel);
        //viewTabbedPane.addTab("view two", viewPanelTwo);
        
        // -> [centerPanel.add(viewPanel, BorderLayout.CENTER);]
        centerPanel.add(viewTabbedPane, BorderLayout.CENTER);
        contentPane.add(centerPanel, BorderLayout.CENTER);
    
        // ----------------------------------------------------------------------//
        // Context Menu: the panel displaying the scene graph as a tree
        // ----------------------------------------------------------------------//
        ctxPanel = new JPanel();
        ctxPanel.setLayout(new BorderLayout());
        ctxPanel.setPreferredSize(DEFAULT_CONTEXT_DIM);
        
        // --> sceneGraphPanel = new SceneGraphPanel();
        // --> sceneGraphPanel.setup(this);
        // --> sceneGraphPanel.setPreferredSize(DEFAULT_CONTEXT_DIM);
    
        // --> contentPane.add(sceneGraphPanel, BorderLayout.EAST);
        // --> currentContextPanel = sceneGraphPanel;
        
                // crates a place for tool gui panel ...
        vuiToolPanel = this.createToolPanel("Tool GUI");
        
        ctxPanel.add(vuiToolPanel, BorderLayout.CENTER);
        
        primitiveContext = new PrimitiveContext();
        primitiveContext.setup(this);
        primitiveContext.setPreferredSize(DEFAULT_CONTEXT_DIM_2);
        
        ctxPanel.add(primitiveContext, BorderLayout.NORTH);
        // centerPanel --> ctxPanel
        ctxPanel.add(customPluginPanel.getComponent(0), BorderLayout.NORTH);
        
        contentPane.add(ctxPanel, BorderLayout.WEST);
        currentContextPanel = primitiveContext;
    
        // the message panel at the bottom
        messagePanel = new MessagePanel();
        messagePanel.setup(this);
    
        
    
        // setup contentPane
        // --- nikola --- ovo moze da se totalno izbaci
        //contentPane.add(quickBarPanel, BorderLayout.NORTH);
        
        //navigation panel
        // --- nikola --- ovaj panel ide umesto dugmica za kreiranje alata
        // treba mu proslediti informaciju o promeni view-ova pri promeni
        // fokusa alata
        viewManagerPanel VMP = new viewManagerPanel();
        centerPanel.add(VMP,BorderLayout.NORTH);
        //contextPanels.put("ViewManagerPanel", VMP);
        
        // --> contentPane.add(sceneGraphPanel, BorderLayout.EAST);
        contentPane.add(centerPanel, BorderLayout.CENTER);
        contentPane.add(basePluginPanel, BorderLayout.EAST);
        contentPane.add(messagePanel, BorderLayout.SOUTH);
    
        // register all panels to be updated when changes are applied
        contextPanels.put("quickBarPanel", quickBarPanel);
        contextPanels.put("basePluginPanel", basePluginPanel);
        contextPanels.put("customPluginPanel", customPluginPanel);
        contextPanels.put("messagePanel", messagePanel);
                // --- nikola ---
        contextPanels.put("tabbed tool panel", toolPanel);
    
        contextPanels.put("primitiveContext", primitiveContext);
        // --> contextPanels.put("sceneGraphContext", sceneGraphPanel);
     
        contextPanels.put("viewPanel", viewPanel);
        // --> contextPanels.put("viewPanelTwo", viewPanelTwo);
    
        // Causes this Window to be sized to fit the preferred size and layouts of
        // its subcomponents. If the window and/or its owner are not yet displayable,
        // both are made displayable before calculating the preferred size. The
        // Window will be validated after the preferredSize is calculated.
        pack();
        setFullScreenMode();
        
    }
  
    /**
     * Returns the SGPanel of the specific name.
     * 
     * 
     * @param name The name of the panel.
     * @return The corresponding SGPanel.
     */
    public SGPanel getsgJPanel(String name) {
        return (SGPanel)contextPanels.get(name);
    }
  
    /**
     * Helper method to set full screen mode.
     */
    private void setFullScreenMode() {
        // this gives us real full screen mode.
        this.setLocation(-3, -3);
    }
  
    /**
     * Refresh the content of the panels. The session contains information
     * from the executed plugin. This information can be shown in the
     * corresponding panel.
     */
    public void refreshPanels(Session session) {
        Enumeration enumeration = contextPanels.elements();
        while (enumeration.hasMoreElements()) {
            ((SGPanel)enumeration.nextElement()).update(session);
        }
    }
  
    /**
     * Refreshes the given panel with the values from the session.
     *
     * @param session The session with values to refresh.
     * @param panelName The name of the panel to apply the refresh.
     */
    public void refreshPanel(Session session, String panelName) {
        SGPanel p = (SGPanel)contextPanels.get(panelName);
        p.update(session);
    }
  
    /**
     * Updates the current context panel with the values from the session.
     *
     * @param session The session with the values to update.
     */
    public void updateCurrentContext(Session session) {
        currentContextPanel.update(session);
    }
  
    /**
     * Exchanges the current context panel with the panel with the given
     * name.
     *
     * @param contextPanelName The name of the context panel.
     */
    public void exchangeContextPanel(String contextPanelName) throws SGException {
        logger.debug(contextPanelName);
        // if context is null, there must be a bad configuration
        if (contextPanelName == null) {
            throw new SGException("ContextPanel '" + contextPanelName + "' not found.");
        }
    
        // we can have a nullContext, so we don't change the
        // current context panel.
        SGPanel contextPanel = (SGPanel)contextPanels.get(contextPanelName);
        // -_> contentPane.remove(currentContextPanel);
        // --> contentPane.add(contextPanel, BorderLayout.EAST);
        ctxPanel.remove(currentContextPanel);
        ctxPanel.add(contextPanel, BorderLayout.NORTH);        
        contextPanel.validate();
        contextPanel.updateUI();
    
        // Causes this Window to be sized to fit the preferred size and layouts of
        // its subcomponents. If the window and/or its owner are not yet displayable,
        // both are made displayable before calculating the preferred size. The
        // Window will be validated after the preferredSize is calculated.
        pack();
    
        // set full screen mode. This enshures all gui component to be set correctly.
        setFullScreenMode();
    
        // Makes the Window visible. If the Window and/or its owner are not yet
        // displayable, both are made displayable. The Window will be validated
        // prior to being made visible. If the Window is already visible, this
        // will bring the Window to the front.
        show();
    
        // set reference
        currentContextPanel = contextPanel;
    }
  
    
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
        
        // crates a place for tool gui panel ...
        JPanel innerToolPanel = new JPanel();
        innerToolPanel.setName("innerToolPanel");
        
        innerToolPanel.setBorder(new EtchedBorder());
        innerToolPanel.setMaximumSize(new Dimension(80, 400));
        innerToolPanel.setMinimumSize(new Dimension(80, 400));
        innerToolPanel.setPreferredSize(new Dimension(80, 400));        
                // --- Nikola --- ovde sam dodao tabbedToolPanel
        toolPanel.setSize(innerToolPanel.getWidth()-2,innerToolPanel.getHeight()/2);
        toolOption.setSize(innerToolPanel.getWidth()-2,innerToolPanel.getHeight()/2);
        innerToolPanel.setLayout(new GridLayout(2,1));
        innerToolPanel.add(toolPanel);
        innerToolPanel.add(toolOption);
        subMenuPanel.add(subMenuTitle, BorderLayout.NORTH);
        subMenuPanel.add(innerToolPanel, BorderLayout.CENTER);
        
        
        subMenuPanel.add(subMenuTitle, BorderLayout.NORTH);
        subMenuPanel.add(innerToolPanel, BorderLayout.CENTER);

    
        return subMenuPanel;
    }    
    /** Exit the Application */
    private void exitForm(WindowEvent evt) {
        // Controller shutdown
        Controller.getInstance().shutdown();
    }
  
    public void setNavigationMode(boolean navMode) {
        viewPanel.setNavigationMode(navMode);
    }
  
    public void setPerspectiveView() {
        viewPanel.setPerspectiveView();
    }
  
    public void setFourViews(){
        viewPanel.setFourViews();
    }
    
    public void updateToolOperators(VToolOperatorsForm vToolOperatorsForm) {
        basePluginPanel.updateToolOperators(vToolOperatorsForm);
        basePluginPanel.validate();
        basePluginPanel.updateUI();

        setFullScreenMode();
    
        show();
    }
     
    public void removeGUI(VToolOperatorsForm vToolOperatorsForm) {
        basePluginPanel.removeGUI(vToolOperatorsForm);
        basePluginPanel.validate();
        basePluginPanel.updateUI();
   
        //pack();
    
        setFullScreenMode();
    
        show();        
    }    
    
    public void updateGUITool(VUIToolForm sgGUIToolForm) {
// --- Nikola --- Ovo sam iskomentarisao da bi cel stvar proradila...

//        JPanel innerToolPanel = (JPanel) vuiToolPanel.getComponent(1);
//        String name = innerToolPanel.getName();
//        if(innerToolPanel.getComponentCount() != 0) {
//            if(innerToolPanel.getComponent(0).equals(sgGUIToolForm)) {
//                return;
//            }
//            innerToolPanel.remove(0);
//        }
//        sgGUIToolForm.setDirty(false);
//        innerToolPanel.add(sgGUIToolForm);
//        innerToolPanel.validate();
//        innerToolPanel.updateUI();        
//        
//        vuiToolPanel.validate();
//        vuiToolPanel.updateUI();
 
        setFullScreenMode();
    
        show();
    }
     
    public void removeGUI(VUIToolForm sgGUIToolForm) {
        JPanel innerToolPanel = (JPanel) vuiToolPanel.getComponent(1);
        String name = innerToolPanel.getName();
        if(innerToolPanel.getComponentCount() != 0) {
            SampleVUIToolForm component = (SampleVUIToolForm) innerToolPanel.getComponent(0);
            if(component.equals(sgGUIToolForm)) {
                innerToolPanel.remove(0);
            }
            innerToolPanel.validate();
            innerToolPanel.updateUI();
        }
        
        vuiToolPanel.validate();
        vuiToolPanel.updateUI();
        
        setFullScreenMode();
    
        show();        
    }        
}
