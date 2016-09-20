package org.sgJoe.tools.sample;

import org.apache.log4j.Logger;
import org.sgJoe.exception.SGPluginException;
import org.sgJoe.graphics.SceneGraphEditor;
import org.sgJoe.logic.Session;
import org.sgJoe.plugin.Form;
import org.sgJoe.tools.interfaces.VTool;
import org.sgJoe.tools.interfaces.VToolForm;
import org.sgJoe.tools.interfaces.VToolOperatorsForm;
import org.sgJoe.tools.interfaces.VToolPlugin;
import org.sgJoe.tools.interfaces.VUIToolForm;


/*
 * Descritpion for SampleHandleVToolPlugin.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: April 11, 2006  9:35 AM  $
 */

public class SampleHandleVToolPlugin extends VToolPlugin {
    
    private static Logger logger = Logger.getLogger(SampleHandleVToolPlugin.class);
    
    public SampleHandleVToolPlugin() {
        super(null);
    }

    public VTool createToolInstance(String string) {
        return null;
    }

    public void performAction(Form form, SceneGraphEditor editor, Session session) throws SGPluginException {
    }

    public VToolForm createForm() {
        return null;
    }

    public VUIToolForm createVUIForm() {
        return null;
    }

    public VToolPlugin createPlugin() {
        return null;
    }

    public VToolOperatorsForm createOperatorsForm() {
        return null;
    }
    
}
