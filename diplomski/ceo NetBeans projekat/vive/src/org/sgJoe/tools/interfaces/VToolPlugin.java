package org.sgJoe.tools.interfaces;

import org.apache.log4j.Logger;
import org.sgJoe.exception.SGPluginException;
import org.sgJoe.graphics.SceneGraphEditor;
import org.sgJoe.graphics.behaviors.BehaviorObserver;
import org.sgJoe.logic.Session;
import org.sgJoe.plugin.Form;
import org.sgJoe.plugin.Plugin;


/*
 * Descritpion for VToolPlugin.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: April 8, 2006  4:10 PM  $
 */

public abstract class VToolPlugin extends Plugin {
    
    protected VirTool virToolRef;
    
    // is this still neccessary
    protected String toolName;
    
    public VToolPlugin(VirTool virToolRef) {
        super();
        this.virToolRef = virToolRef;
    }
    
    public abstract void performAction(Form form, SceneGraphEditor editor, Session session) throws SGPluginException;

    public void setToolName(String string) {
        this.toolName = toolName;
    }
    
    public VirTool getVirToolRef() {
        return virToolRef;
    }
    
   public void OnInstanceDelete(VToolForm form, SceneGraphEditor editor) {
       VirTool instance = form.getVirToolRef();
       BehaviorObserver observer = instance.getBhvObserver();

       editor.removeNode(instance.getToolBaseBG());
       editor.getVirToolStack().removeAll(instance);
       editor.getVirToolMap().remove(instance.getInstanceName());
                
       observer.toolRemoved(instance);       
       observer.removeGUI(instance.getVUIToolFormRef());
       observer.removeGUI(instance.getVToolOperatorsFormRef());       
   }    
}




