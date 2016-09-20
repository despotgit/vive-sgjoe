package org.sgJoe.logic;

import org.sgJoe.plugin.*;

import org.apache.log4j.Logger;


/**
 * A module is a unit which exists of a form and a plugin. A plugin is
 * always associated to a form and vice versa.
 *
 * @author   $ Author: Aleksandar Babic             $
 * @version  $ Revision:            0.1             $
 * @date     $ Date:  November 29, 2005 11:15 PM    $
 */
public class Module {
    
    private static Logger logger = Logger.getLogger(Module.class);
    
    private ClassLoader loader = ClassLoader.getSystemClassLoader();
  
    private final String pluginName;
    private Plugin plugin;
    
    private final String formName;
    private Form form;
  
    private final String name;    
    
    /**
      * Creates a new instance of Module taking as paramtere a form and the
      * corresponding plugin. If the form or plugin is <code>null</code> an
      * IllegalArgumentException is thrown.
      *
      * @param form The form
      * @param plugin The plugin
      * @throws IllegalArgumentException Exception is thrown, if form or plugin 
      *                                  is <code>null</code>
      */
    public Module(String formName, String pluginName, String name) throws IllegalArgumentException {
        if (formName == null || pluginName == null || name == null) {
            throw new IllegalArgumentException("'null' not allowed for Form or Plugin.");
        }
        this.formName = formName;
        this.pluginName = pluginName;
        this.name = name;
    
        logger.debug("new module " + name + " added.");
    }
 
    /**
     * Returns the plugin from this module. At the first call, the Plugin class
     * will be loaded.
     *
     * @return The plugin of this module.
     */
    public Plugin getPlugin() {
        if (plugin == null) {
            try {
                Class pluginClass = loader.loadClass(pluginName);
                plugin = (Plugin) pluginClass.newInstance();
            } catch (ClassNotFoundException e) {
                logger.error(e);
            } catch (InstantiationException e) {
                logger.error(e);
            } catch (IllegalAccessException e) {
                logger.error(e);
            }
        }
        
        return plugin;
    }
    
    /**
      * Returns the form of this module. At the first call, the Plugin class
      * will be loaded.
      *
      * @return The form of this module.
      */
    public Form getForm() {
        if (form == null) {
            try {
                Class formClass = loader.loadClass(formName);
                form = (Form) formClass.newInstance();
            } catch (ClassNotFoundException e) {
                logger.error(e);
            } catch (InstantiationException e) {
                logger.error(e);
            } catch (IllegalAccessException e) {
                 logger.error(e);
            }
        }
        
        return form;
    }
  
    /**
      * Returns the name of this module.
      *
      * @return The name of the module.
      */
    public String getName() {
        return name;
    }      
}
