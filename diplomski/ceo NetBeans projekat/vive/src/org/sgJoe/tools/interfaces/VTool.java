package org.sgJoe.tools.interfaces;

import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;
import org.sgJoe.tools.VToolFactory;

/*
 * Descritpion for VTool.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: April 8, 2006  11:33 AM    $
 */

public abstract class VTool extends TransformGroup {
    
    private VirTool     virToolRef = null;
    
    protected Point3d   lGripPt;
    protected Point3d   vwGripPt;
            
    protected Long      toolUID;
//    protected String    toolName;
    private String    instanceName = null;
//    protected String    toolDescription = "This is abstract class";
    private int action = VToolForm.ACT_NONE;
   
    
    public VTool(VirTool virToolRef) 
    {
        super(); 
        this.virToolRef = virToolRef;
        VToolFactory.setTGCapabilities(this);
        setToolUID(VToolFactory.generateToolUID());
    }
    
    public abstract void setup();
    
//    public String getToolName() {
//        return toolName;
//    }
//    
//    public void setToolName(String toolName) {
//        this.toolName = new String(toolName);
//    }
    
//    public String getToolDescription() {
//        return toolDescription;
//    }
//
//    public void setToolDescription(String toolDescription) {
//        this.toolDescription = toolDescription;
//    }

    public Long getToolUID() {
        return toolUID;
    }

    public void setToolUID(Long toolUID) {
        this.toolUID = toolUID;
    }

    public VirTool getVirToolRef() {
        return virToolRef;
    }

    public void setVirToolRef(VirTool virToolRef) {
        this.virToolRef = virToolRef;
    }

    public Point3d getLGripPt() {
        return lGripPt;
    }

    public void setLGripPt(Point3d lGripPt) {
        this.lGripPt = lGripPt;
    }
    
    public Point3d getVWGripPt() {
        return vwGripPt;
    }

    public void setVWGripPt(Point3d vwGripPt) {
        this.vwGripPt = vwGripPt;
    }    

    public String getInstanceName() {
        return instanceName;
    }
    
    public void setInstanceName(String name) 
    {
        if(name != null) 
        {
            this.instanceName = name;
        } 
        else 
        {
            String shortClassName = this.getClass().getName();
            if(virToolRef != null) 
            {
                shortClassName = shortClassName.substring(this.getClass().getPackage().getName().length() + 1); 
                this.instanceName = virToolRef.getInstanceName() + shortClassName + "#" + VToolFactory.generateToolUID(); 
            } 
            else 
            {
                this.instanceName = shortClassName + "#" + VToolFactory.generateToolUID(); 
            }            
        }        
    }
    public void setAction4OnMousePressed() {
        setAction(VToolForm.ACT_MOUSE_PRESSED);
    }
    
    public void setAction4OnMouseDragged() {
        setAction(VToolForm.ACT_MOUSE_DRAGGED);
    }
    
    public void setAction4OnMouseReleased() {
        setAction(VToolForm.ACT_MOUSE_RELEASED);
    }
    
    public void setAction4OnMouseClicked() {
        setAction(VToolForm.ACT_MOUSE_CLICKED);
    }    

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

}
