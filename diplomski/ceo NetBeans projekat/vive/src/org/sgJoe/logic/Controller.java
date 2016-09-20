package org.sgJoe.logic;

import org.sgJoe.exception.*;
import org.sgJoe.gui.*;
import org.sgJoe.plugin.*;

import java.util.Hashtable;

import org.apache.log4j.Logger;

/**
 * This class represents the core of the application. The controller
 * communicates with the Plugins and the GUI. Since the GUI part does
 * not know anything about how to execute a plugin, it's the controller's
 * job to decide which plugin to execute.
 * <br><br>
 * For each plugin there exists an corresponding form. The plugin and the
 * form represents a GUI module. All modules are stored in the registry.
 * They are here to offer some system wide features (lights, textures etc.)
 * <br><br>
 * The controller maintains a thread pool. Every plugins is executed in it's
 * own thread (I'll see if it is going to stay this way).
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision: 0.1             $
 * @date     $ Date: 29/11/2005 10:11:11 $
 */
public class Controller implements Constants, PluginObserver {
    
    private Logger logger = Logger.getLogger(Controller.class);
    
    /**
     * Singleton instance. One contorller per application.
     * Later may be more that one -> could this be start of 3D app server ???
     */
    private static Controller instance = new Controller();
  
    private WindowManager wManager = null;
  
    private SGThreadPool threadPool = new SGThreadPool();
  
    private Registry registry = new Registry();
    private Session session = new Session();
  
    /**
     *  Creates a new instance of Controller. Keep private to
     *  prevent outside creation. The thread pool is startet.
     */
    private Controller() {
        threadPool.startPool();
    }
  
    /**
     * Shuts down the application and stops the thread pool.
     */
    public void shutdown() 
    {
        Window mainWindow = wManager.getMainWindow();
        String confirmText = " Do you really want to quit?\n";
        String confirmTitle = " Closing sgJoe framework";
  
        int selection = 
                javax.swing.JOptionPane.showConfirmDialog(mainWindow, confirmText, confirmTitle, javax.swing.JOptionPane.YES_NO_OPTION);
    
        if (selection == javax.swing.JOptionPane.YES_OPTION) 
        {
            mainWindow.dispose();
            threadPool.stopPool();
            System.exit(0);
        }
    }
  
    /**
     * Returns an instance of the controller.
     *
     * @return Controller An instance of the controller.
     */
    public static Controller getInstance() {
        return instance;
    }
  
    /**
     * Sets the registry. It may be externaly populated.
     *
     * @param regi The registry to apply to this controller.
     */
    public void setRegistry(Registry registry) {
        this.registry = registry;
    }
  
    /**
     * Sets the window manager.
     *
     * @param The window manager.
     */
    public void setWindowManager(WindowManager wManager) {
        this.wManager = wManager;
        this.wManager.setSession(session);
    }
    
    /**
     * Returns reference to the window manager.
     *
     * @return Current window manager.
     */
    public WindowManager getWindowManager() {
        return wManager;
    }    
  
    /**
     * Returns the module which contains the corresponding form class. If the
     * Form is not found, <code>null</code> is returned.
     *
     * @param cl The form class.
     * @return The corresponding module to this form class.
     */
    public Form getFormForName(String name) 
    {
        Module m = registry.getModule(name);
        if (m != null) 
        {
            return m.getForm();
        }
        return null;
    }
  
    /**
     * Performs a action. By means of the form, the controller can deside
     * which plugin is executed. Before a form is executed it validates
     * it's content.
     *
     * @param form The form to execute by the plugin.
     */
    public void performAction(String name, Form form) 
    {
    
        // first validate the form
        ActionErrors errors = form.validate(session);
        if (errors != null && errors.isSet) 
        {
            logger.error("validation failed: " + errors.getMessage());
            wManager.showMessage(errors.getMessage());
            errors = null;
            return;
        }
    
        // get the registered plugin from the cache
        Plugin plugin = registry.getModule(name).getPlugin();
        if (plugin == null) 
        {
            logger.error("plugin not found exception, check if plugin was correctly registered.");
            wManager.showErrorMsg("An internal error occured. \n(PluginNotFoundException)");
            return;
        }
    
        // assign to the plugin all needed information to be executed by a thread.
        plugin.setForm(form);
        
        // session object gives the context for forms to operate on.
        // it may also serve as sync object for threads.
        plugin.setSession(session);
        
        // apply reference from the controller, so the plugin can inform the
        // controller, when all work is done and the GUI components get updated.
        plugin.addPluginObserver(this);
    
        // assign the plugin and execute it.
        threadPool.add(plugin);
    }
  
    // ----------------------------------------------------------------------- //
    // Implements methods from PluginObserver. These methods defines the
    // what happend during the plugin execution.
    // ----------------------------------------------------------------------- //
  
    /**
     * Handle the exceuption occurend during plugin execution.
     * 
     * 
     * @param SGPluginException The exection thrown by the current plugin.
     */
    public void handleException(SGPluginException e) {
        logger.error(e);
        wManager.showErrorMsg("Plugin not properly executed.\n" + e.getMessage() + "\n(PluginException)");
    }
  
    /**
     * Update the GUI components. This is done after the execution of the current plugin.
     *
     * @param s The Session containing some informtaion for GUI components.
     */
    public void updateGUIComponents(Session s) {
        wManager.refreshsgJPanels(s);
    }
  
    /**
     * Show popup to the user.
     */
    public void showPopup() {
        wManager.showPopup();
    }
  
    /**
     * Hides the popup.
     */
    public void hidePopup() {
        wManager.hidePopup();
    }
}