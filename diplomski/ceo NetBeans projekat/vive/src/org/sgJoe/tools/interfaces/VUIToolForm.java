package org.sgJoe.tools.interfaces;

import org.apache.log4j.Logger;
import org.sgJoe.exception.SGPluginException;
import org.sgJoe.graphics.SceneGraphEditor;
import org.sgJoe.gui.SGPanel;
import org.sgJoe.logic.Session;


/*
 * Descritpion for VUIToolForm.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: April 8, 2006  11:35 AM    $
 */

public abstract class VUIToolForm extends SGPanel 
{

    protected VirTool virToolRef = null;
    private boolean dirty = true;
    
    protected String toolName;
    
    public VUIToolForm(VirTool virToolRef) 
    {
        super();
        this.virToolRef = virToolRef;
    }
    
    /**
     * Creates the GUI for this panel.
     */
    public abstract void setup();
    

    protected void update(Session session) 
    { }

//    public SceneGraphEditor getSGEditor() {
//        return sgEditor;
//    }
//
//    public void setSGEditor(SceneGraphEditor sgEditor) {
//        this.sgEditor = sgEditor;
//    }

    public void onVToolDelete(VTool vTool) 
    {
//        if(vToolRef != null && vToolRef.equals(vTool)) {
//            _onVToolDelete();
//        }        
    }

//    public VTool getVToolRef() {
//        return vToolRef;
//    }
//
//    public void setVToolRef(VTool vToolRef) {
//        this.vToolRef = vToolRef;
//    }

    protected void _onVToolDelete() 
    {
//        this.vToolRef = null;
    }

    public void setToolName(String string) 
    {
        this.toolName = string;
    }

    public boolean isDirty() 
    {
        return dirty;
    }

    public void setDirty(boolean isDirty) 
    {
        this.dirty = isDirty;
    }

}


