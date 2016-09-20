package org.sgJoe.logic;

import org.apache.log4j.Logger;
import org.sgJoe.gui.GUIForm;
import org.sgJoe.plugin.Form;
import org.sgJoe.plugin.Plugin;
import org.sgJoe.tools.interfaces.*;

/*
 * Descritpion for Tool.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: April 2, 2006  5:28 PM     $
 */

public class Tool {
    
    private static Logger logger = Logger.getLogger(Tool.class);

    private ClassLoader loader = ClassLoader.getSystemClassLoader();
  
    private final String virToolName;
    private VirTool virTool;
    
    private final String vToolName;
    private VTool vTool;
    
    private final String pluginName;
    private VToolPlugin plugin;
    
    private final String formName;
    private VToolForm form;

    private final String guiFormName;
    private VUIToolForm guiForm;
    
    private final String operatorsFormName;
    private VToolOperatorsForm operatorsForm;
    
    private final String name;     
    private final String icon;

    public Tool(String virToolName, String vToolName, String guiFormName, String formName, String pluginName, String operatorsFormName, String name, String icon) throws IllegalArgumentException {
        if (guiFormName == null || formName == null || pluginName == null || name == null) {
            throw new IllegalArgumentException("'null' not allowed for Form or Plugin.");
        }
        this.virToolName = virToolName;
        this.vToolName = vToolName;
        this.guiFormName = guiFormName; 
        this.formName = formName;
        this.pluginName = pluginName;
        this.operatorsFormName = operatorsFormName;
        this.name = name;
        this.icon = icon;
    
        logger.debug("new module " + name + " added.");
    }
 
    public VTool getVTool() {
        if (vTool == null) {
            try {
                Class vToolClass = loader.loadClass(vToolName);
                vTool = (VTool) vToolClass.newInstance();
            } catch (ClassNotFoundException e) {
                logger.error(e);
            } catch (InstantiationException e) {
                logger.error(e);
            } catch (IllegalAccessException e) {
                logger.error(e);
            }
        }
        
        return vTool;
    }    
    /**
     * Returns the plugin from this module. At the first call, the Plugin class
     * will be loaded.
     *
     * @return The plugin of this module.
     */
    public VToolPlugin getPlugin() {
        if (plugin == null) {
            try {
                Class pluginClass = loader.loadClass(pluginName);
                plugin = (VToolPlugin) pluginClass.newInstance();
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
    

    public VToolForm getForm() {
        if (form == null) {
            try {
                Class formClass = loader.loadClass(formName);
                form = (VToolForm) formClass.newInstance();
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
  
    public VUIToolForm getVUIToolForm() {
        if (guiForm == null) {
            try {
                Class guiFormClass = loader.loadClass(guiFormName);
                guiForm = (VUIToolForm) guiFormClass.newInstance();
                guiForm.setup();
            } catch (ClassNotFoundException e) {
                logger.error(e);
            } catch (InstantiationException e) {
                logger.error(e);
            } catch (IllegalAccessException e) {
                 logger.error(e);
            }
        }
        
        return guiForm;
    }
    
    public VToolOperatorsForm getOperatorsForm() {
        if (operatorsForm == null) {
            try {
                Class operatorsFormClass = loader.loadClass(operatorsFormName);
                operatorsForm = (VToolOperatorsForm) operatorsFormClass.newInstance();
                operatorsForm.setup();
            } catch (ClassNotFoundException e) {
                logger.error(e);
            } catch (InstantiationException e) {
                logger.error(e);
            } catch (IllegalAccessException e) {
                 logger.error(e);
            }
        }
        
        return operatorsForm;
    }    
    public String getName() {
        return name;
    }
    
    public String getIcon() {
        return icon;
    }

    public VirTool getVirTool() {
            VirTool newInstance = null;
            
            try {
                
                Class virToolClass = loader.loadClass(virToolName);
                newInstance = (VirTool) virToolClass.newInstance();
                
            } catch (ClassNotFoundException e) {
                logger.error(e);
            } catch (InstantiationException e) {
                logger.error(e);
            } catch (IllegalAccessException e) {
                logger.error(e);
            }
            return newInstance;
    }
        
}

