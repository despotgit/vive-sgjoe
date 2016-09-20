package org.sgJoe.gui;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.vecmath.Point3d;
import org.sgJoe.exception.SGException;
import org.sgJoe.graphics.SceneGraphEditor;
import org.sgJoe.graphics.behaviors.BehaviorObserver;
import org.sgJoe.graphics.event.EventDispatcher;
import org.sgJoe.graphics.event.EventSubscriber;
import org.sgJoe.graphics.event.SGEvent;
import org.sgJoe.logic.GUISettings;
import org.VIVE.gui.logic.LogicGuiManager;
import org.sgJoe.logic.Session;
import org.sgJoe.logic.ToolRegistry;
import org.sgJoe.plugin.Constants;

import org.apache.log4j.Logger;
import org.sgJoe.tools.interfaces.VirTool;
import org.sgJoe.tools.sample.SampleVUIToolForm;
import org.sgJoe.tools.interfaces.VToolOperatorsForm;
import org.sgJoe.tools.interfaces.VUIToolForm;

/*
 * WindowManager is responsible for all windows. There is the main window,
 * the shell window and a small popup window. He is also responsible to handle
 * the backcall from the behaviour classes from the base scene graph.
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: 29/11/2005 21:31:00 $
 */

public class WindowManager implements BehaviorObserver, EventSubscriber {
    
    private static Logger logger = Logger.getLogger(WindowManager.class);
    
    private Session session = null;
  
    private Window mainWindow = null;
    private Popup popup = null;
    
    private String previousContext = "";  
    
    private EventDispatcher dispatcher;
        
    private LogicGuiManager logicGuiManager = null;
    
    public WindowManager(GUISettings settings, ToolRegistry toolRegistryRef) {
        logger.debug("init WindowManager");
        
        setLogicGuiManager(new LogicGuiManager(toolRegistryRef));
        
        mainWindow = new Window(toolRegistryRef, logicGuiManager);
        mainWindow.show();
    
        popup = new Popup();
        
        // do self registration with only - singleton dispatcher
        dispatcher = EventDispatcher.getDispatcher();
        dispatcher.register(this);
    }
    
    /**
      * Returns the main window.
      *
      * @return The main window.
     */
    public Window getMainWindow() {
        return mainWindow;
    }
 

    /**
     * Sets the session. This session is used for callbacks from the
     * behaviours.
     *
     * @param session The session to set.
     */
    public void setSession(Session s) {
        this.session = s;
    }
  
    /**
     * Updates all windows with the values from the session. 
     * 
     * @param session The session.
     */
    public void refreshsgJPanels(Session session) {
        try {    
            String key = session.getContextPanelKey();
            if (key != null && !key.equals(previousContext)) {
                mainWindow.exchangeContextPanel(key);
            }
            previousContext = key;
        } catch (SGException e) {
            logger.error(e);
            logger.error("No valid context found in session.");
        }
    }
  
    /**
     * Opens an error message popup window.
     *
     * @param msg The message to show.
     */
    public void showErrorMsg(String msg) {
        JOptionPane.showMessageDialog(mainWindow, msg, "Internal error", JOptionPane.ERROR_MESSAGE);
    }
  
    /**
     * Opens an message popup window.
     *
     * @param msg The message to show.
     */
    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(mainWindow, msg, "Information", JOptionPane.INFORMATION_MESSAGE);
    }
  
    /**
     * Shows a small popup to the user to indicate if the application is
     * executing a plugin.
     */
    public void showPopup() {
        popup.show();
    }
  
    /**
     * Hides the popup.
     */
    public void hidePopup() {
        popup.hide();
    }  
  
    // ----------------------------------------------------------------------- //
    // this is called from the behaviour classes.
    // ----------------------------------------------------------------------- //
  
    /**
     * Returns the BehaviourObserver for this WindowManager.
     *
     * @return BehaviourObserver The BehaviourObserver for this WindowManager.
     */
    public BehaviorObserver getBehaviorObserver() {
        return this;
    }
  
    /**
     * Updates the values for translation, scale and rotation. This is specially
     * used for the behaviours to update some GUI components.
     *
     * @param translation The translation vector.
     * @param scale The scale vector.
     * @param rotation The rotation vector.
     */
    public void update(javax.vecmath.Vector3d translation, javax.vecmath.Vector3d scale, javax.vecmath.Vector3d rotation) {
        session.put("TRANS_X", new Double(translation.x));
        session.put("TRANS_Y", new Double(translation.y));
        session.put("TRANS_Z", new Double(translation.z));
    
        session.put("ROT_X", new Double(Math.toDegrees(rotation.x)));
        session.put("ROT_Y", new Double(Math.toDegrees(rotation.y)));
        session.put("ROT_Z", new Double(Math.toDegrees(rotation.z)));

        session.put("SCALE_X", new Double(scale.x));
        session.put("SCALE_Y", new Double(scale.y));
        session.put("SCALE_Z", new Double(scale.z));
    
        mainWindow.updateCurrentContext(session);
    }    
    
    /**
     * Updates the values for pickpoint. This is specially
     * used for the behaviours to update some GUI components.
     *
     * @param point The Point3d value.
     */
    public void update(javax.vecmath.Point3d point3d) {
        session.put("POINT_X", new Double(point3d.x));
        session.put("POINT_Y", new Double(point3d.y));
        session.put("POINT_Z", new Double(point3d.z));
    
        mainWindow.updateCurrentContext(session);
    }    

    /**
     * Updates the values for two picked point for distance calculation. This is specially
     * used for the behaviours to update some GUI components.
     *
     * @param ptFirst The First Point3d value.
     * @param ptSecond The Second Point3d value.
     */    
    public void update(Point3d ptFirst, Point3d ptSecond) {
        session.put("POINT3D_1ST", new Point3d(ptFirst));
        session.put("POINT3D_2ND", new Point3d(ptSecond));
        
        mainWindow.updateCurrentContext(session);        
    }

    public void update(VUIToolForm sgGUIToolForm) {
        mainWindow.updateGUITool(sgGUIToolForm);
        mainWindow.validate();
    }

    public void update(VToolOperatorsForm vToolOperatorsForm) {
        mainWindow.updateToolOperators(vToolOperatorsForm);
        mainWindow.validate();
    }
    
    public void removeGUI(VUIToolForm sGGUIToolForm) {
        mainWindow.removeGUI(sGGUIToolForm);
        mainWindow.validate();
    }
    
    public void removeGUI(VToolOperatorsForm vToolOperatorsForm) {
        mainWindow.removeGUI(vToolOperatorsForm);
        mainWindow.validate();
    }    

    public void setSDispatcher(EventDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void onSRegister(Long evtUID) {  }

    public ArrayList getEvents() {
        ArrayList evtList = new ArrayList();
        evtList.add(SGEvent.EVT_TOOL_SELECTED);
        return evtList;        
    }

    public void onEvent(SGEvent event) {
        System.out.println(event);
    }

    public void setFocusOnTool(VirTool virTool) {
        getLogicGuiManager().setFocusOnTool(virTool);
    }

    public void toolRemoved(VirTool virTool) {
        getLogicGuiManager().toolRemoved(virTool);
    }

    public LogicGuiManager getLogicGuiManager() {
        return logicGuiManager;
    }

    public void setLogicGuiManager(LogicGuiManager logicGuiManager) {
        this.logicGuiManager = logicGuiManager;
    }

    public void setSGEditor(SceneGraphEditor sceneEditor) {
        this.logicGuiManager.setSGEditor(sceneEditor);
    }
}
