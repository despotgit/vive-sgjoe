package org.sgJoe.logic;

import java.io.*;

import org.apache.commons.digester.Digester;
import org.sgJoe.exception.SGException;
import org.xml.sax.SAXException;

import org.apache.log4j.Logger;

/**
 * This class is used to load the application specific configuration.<br>
 * The data is stored in the <code>config.xml</code>. This file should be
 * placed in the classpath.
 *
 * @author   $ Author: Aleksandar Babic             $
 * @version  $ Revision:            0.1             $
 * @date     $ Date:  December 11, 2005 10:42 AM    $
 */

public class ConfigLoader {
    
    private static Logger logger = Logger.getLogger(ConfigLoader.class);
    
    // not so nice, should be configurable or else...
    private final static String currentDir = ".";
    private final static String fileSeparator = System.getProperty("file.separator");
    private final static String CONFIG_FILE = currentDir + fileSeparator +
                                                "config" + fileSeparator + "config.xml";
  
    private Digester digester = null;
  
    private Registry registry = new Registry();
    private ToolRegistry toolRegistry = new ToolRegistry();
    private ScenegraphSettings sgSettings = new ScenegraphSettings();
    private GUISettings guiSettings = new GUISettings();
  
    private ClassLoader loader = ClassLoader.getSystemClassLoader();
  
    /** 
     * Creates a new instance of ModuleLoader 
     */
    public ConfigLoader() 
    {
        logger.debug("create ModuleLoader");
        digester = new Digester();
        digester.push(this);
    }
    
    /**
     * Add a new module to the registry. This method is called by digester.
     *
     * @param name The name of the module.
     * @param plugin The class name of the plugin.
     * @param form The class name of the form.
     */
    public void addModules(String name, String plugin, String form) {
        try {
            Module module = new Module(form, plugin, name);      
            registry.registerModule(module);
        } catch (IllegalArgumentException e) {
            logger.error(e);
            // todo: handle exception
        }
    }
    
    public void addTools(String name, String icon, String virtool, String vtool, String plugin, String form, String gui_form, String operators_form) {
        try {
            Tool tool = new Tool(virtool, vtool, gui_form, form, plugin, operators_form, name, icon);      
            toolRegistry.registerTool(tool);
        } catch (IllegalArgumentException e) {
            logger.error(e);
            // todo: handle exception
        }
    }    
    /**
     * Scenegraph settings. This method is called by digester.
     *
     * @param backClipDistance The back cliip distance.
     * @param boundingRadius The bounding radius.
     * @param dimension The dimension, used ofr grid raster.
     */
    public void setScenegraph(double backClipDistance, double boundingRadius, double dimension) {
        try {
            sgSettings = new ScenegraphSettings(backClipDistance, boundingRadius, dimension);
        } catch (IllegalArgumentException e) {
            logger.error(e);
            // todo: handle exception
        }
    }
  
    /**
     * Sets the gui specific settings.
     *
     */
    public void setGUISettings(String value) {
        return;
    }
    
    /**
     * Returns the registry conatining the loaded modules.
     *
     * @return The registry.
     */
    public Registry getRegistry() {
        return registry;
    }
  
    /**
     * Returns the tool registry conatining the loaded tools.
     *
     * @return The tool registry.
     */
    public ToolRegistry getToolRegistry() {
        return toolRegistry;
    }    
    /**
     * Returns the settings for the scene graph.
     *
     * @return The settings for the scene graph.
     */
    public ScenegraphSettings getScenegraphSettings() {
        return sgSettings;
    }
  
    /**
     * Returns the default settings for the GUI.
     *
     * @return The default settings for the GUI.
     */
    public GUISettings getGUISettings() {
        return this.guiSettings;
    }  
    
    /**
     * Loads the application configurations.
     * 
     * 
     * @throws SGException An exception occured during loading the config file.
     */
    public void loadConfiguration() throws SGException {
        try {
            // just for testing.
            long start = System.currentTimeMillis();

            // process scenegraph
            String[] paramTypes = {"java.lang.Double", "java.lang.Double", "java.lang.Double"};
                             
            digester.addCallMethod("sgjoeapp-config/scenegraph", "setScenegraph", 3, paramTypes);
            digester.addCallParam("sgjoeapp-config/scenegraph/backclipdistance", 0);
            digester.addCallParam("sgjoeapp-config/scenegraph/bounding-radius", 1);
            digester.addCallParam("sgjoeapp-config/scenegraph/grid-dimension", 2);
      
            digester.addCallMethod("sgjoeapp-config/gui", "setGUISettings", 1);
            digester.addCallParam("sgjoeapp-config/gui/manual", 0);
      
            // process modules
            digester.addCallMethod("sgjoeapp-config/modules/module", "addModules", 3);
            digester.addCallParam("sgjoeapp-config/modules/module/name", 0);
            digester.addCallParam("sgjoeapp-config/modules/module/plugin", 1);
            digester.addCallParam("sgjoeapp-config/modules/module/form", 2);

            // process tools
            digester.addCallMethod("sgjoeapp-config/tools/tool", "addTools", 8);
            digester.addCallParam("sgjoeapp-config/tools/tool/name", 0);
            digester.addCallParam("sgjoeapp-config/tools/tool/icon", 1);
            digester.addCallParam("sgjoeapp-config/tools/tool/vir-tool", 2);
            digester.addCallParam("sgjoeapp-config/tools/tool/vtool", 3);
            digester.addCallParam("sgjoeapp-config/tools/tool/plugin", 4);
            digester.addCallParam("sgjoeapp-config/tools/tool/form", 5);            
            digester.addCallParam("sgjoeapp-config/tools/tool/gui-form", 6);            
            digester.addCallParam("sgjoeapp-config/tools/tool/operators-form", 7);
            
            InputStream dataSource = loader.getResourceAsStream(CONFIG_FILE);
      
            digester.parse(dataSource);
            
            long stop = System.currentTimeMillis();
            logger.debug(registry.size() + " modules loaded in " + (stop-start) + " msec.");    
            logger.debug(toolRegistry.size() + " tools loaded in " + (stop-start) + " msec.");    
        } catch (IOException e) {
            logger.error(e);
            throw new SGException(e.getMessage());
        } catch (SAXException e) {
            logger.error(e);
            throw new SGException(e.getMessage());
        }
  }    

}
