package org.sgJoe.gui;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JOptionPane;

import org.sgJoe.logic.*;
import org.sgJoe.plugin.*;

import org.apache.log4j.Logger;

/**
 * This singleton class provides actions that are called by other
 * SGPanel classes. It is not a visible GUI element.
 * 
 * 
 * @author $ Author: Aleksandar Babic         $
 * @version $ Revision:            0.1         $
 * @date $ Date:  December 3, 2005 11:36 PM $
 */
public class ActionPool extends SGPanel {
    
    private static Logger logger = Logger.getLogger(ActionPool.class);
    private final static ActionPool instance = new ActionPool();
    private IntroSplashScreen introScreen = new IntroSplashScreen();    
    
    /**
     * Creates a new instance of ActionPool. Keep private to
     * prevent outside creation.
     */
    private ActionPool() {
        logger.debug("create ActionPool");
    }
  
    /**
     * Returns an instance of ActionPool.
     *
     * @return ActionPool An instance of the action pool.
     */
    public static ActionPool getInstance() {
        return instance;
    }
  
    protected void setup() {
    }
  
    /**
     * Resets the scene by deleting its content (after confirmation via
     * popup dialog).
     *
     * @param event A reference from ActionEvent, a wrapper for the object
     *              that caused the event.
    */
    public void deleteSceneActionPerformed(ActionEvent event) {
        String confirmText = "Do you really want to delete the scene?";
        String confirmTitle = "Deletion confirmation dialog";
        int selection = 
                JOptionPane.showConfirmDialog(this, confirmText, confirmTitle, JOptionPane.YES_NO_CANCEL_OPTION);
    
        if (selection == 0) {
            DeleteSceneForm form = (DeleteSceneForm)useForm("deleteScene");
            this.performAction("deleteScene", form);
        }
    }
 
    /**
     * Loads an object file. First the FileDialog will be used to determine
     * the filename, then the file will be loaded.
     *
     * @param event A reference from ActionEvent, a wrapper for the object
     *              that caused the event.
     */
    public void loadObjFileActionPerformed(ActionEvent evt) {
        File file = loadFile(SGFileFilter.FILE_EXTENSION_OBJ);
        if (file == null) {
            return;
        }
        // reuse an existing form.
        OBJLoadForm form = (OBJLoadForm)useForm("oBJLoad");
        form.setFilename(file.getAbsolutePath());
        this.performAction("oBJLoad", form);
    }
  
    /**
     * Exits the application
     *
     * @param event A reference from ActionEvent, a wrapper for the object
     *              that caused the event.
     */
    public void exitActionPerformed(ActionEvent event) {
        // Controller shutdown
        Controller.getInstance().shutdown();
    }

    /**
     * Shows an about popup (the one shown during start up)
     *
     * @param event A reference from ActionEvent, a wrapper for the object
     *              that caused the event.
     */
    public void showAboutScreenActionPerformed(ActionEvent event) {
         introScreen.showSplashScreen();
    }
      
    /**
     * Hides an about popup (the one shown during start up)
     *
     * @param event A reference from ActionEvent, a wrapper for the object
     *              that caused the event.
     */
    public void hideAboutScreenActionPerformed(ActionEvent event) {
        introScreen.hideSplashScreen();
    }
  
    /**
     * Switch the navigation mode to orbit navigation.
     *
     * @param orbitNav <code>true</code> for orbit navigation.
     */
    public void switchNavigationModeActionPerformed(boolean orbitNav) {
        logger.debug("Switch navigation mode.");
        ChangeBehaviourForm form = (ChangeBehaviourForm)useForm("changeBehaviour");
        form.setOrbitMode(orbitNav);
        form.setOperation(OP_SWITCH_NAV_TYPE);
        this.performAction("changeBehaviour", form);
    }
  
    /**
     * Enables the pick mode to pick the entire subgraph stating from the
     * top level TransformGroup.
     *
     * @param pickEntireSubgraph <code>true</code> to enable, <code>false</code>
     *                           else.
     */
  
    public void switchPickingModeActionPerformed(boolean pickEntireSubgraph) {
        logger.debug("Switch picking mode.");
        // set the mode directly
        org.sgJoe.graphics.behaviors.CustomBehavior.pickEntireSubgraph = pickEntireSubgraph;
    }
  
    /**
     * Not used in this class
     */
    protected void update(Session session) { }    
}
