package org.sgJoe.plugin;

import javax.media.j3d.*;
import javax.vecmath.Vector3d;
import org.sgJoe.exception.SGPluginException;
import org.sgJoe.graphics.SceneGraphEditor;
import org.sgJoe.logic.*;
import org.sgJoe.tools.*;
import org.sgJoe.tools.interfaces.*;

import org.apache.log4j.Logger;

/*
 * Descritpion for CreateToolPlugin.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: April 2, 2006  3:14 PM     $
 */

public class CreateToolPlugin extends Plugin {
    
    private static Logger logger = Logger.getLogger(CreateToolPlugin.class);
  
    public void performAction(Form form, SceneGraphEditor editor, Session session) throws SGPluginException {
        logger.debug("make instance of selected tool");
        System.out.println("CreateToolPlugin - metoda performAction");
        CreateToolForm ctForm = ((CreateToolForm)form);
  
        String name = ctForm.getToolName();
        String instanceName = ctForm.getToolInstanceName();
                
        Tool tool = editor.getToolRegistry().getTool(name);
        VirTool instance = null;
        if(tool != null) {
            instance = tool.getVirTool();
            instance.setToolName(name);
            instance.setInstanceName(name + "#" + instance.generateUID());
            instance.setup(editor, editor.getBaseSceneGraph().getBehaviorObserver());
            instance.getVToolRef().setInstanceName(null);
            editor.getVirToolMap().put(instance);
        }

        // calc values for tool context panel
        // --> Vector3d translation = new Vector3d();
        // --> Vector3d rotation = new Vector3d();
        // --> Vector3d scale = new Vector3d();
    
        // --> editor.calcObjInfo(vTool, translation, scale, rotation);
      
        // add tool to the scene.
        editor.setCapabilities(instance.getToolBaseBG());
        editor.addChild(instance.getToolBaseBG());
    
//        session.setContextPanelKey(GuiConstants.PRIMITIVE_CONTEXT);        
  }
    
}
