package org.sgJoe.tools.decorators;

import javax.media.j3d.*;
import javax.vecmath.Point3d;
import org.apache.log4j.Logger;
import org.sgJoe.graphics.primitives.utils.SGCylinderLine3D;
import org.sgJoe.logic.SGObjectInfo;
import org.sgJoe.tools.VToolFactory;
import org.sgJoe.tools.interfaces.*;

/*
 * Descritpion for VToolHandle.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: April 11, 2006  10:46 AM   $
 */

public abstract class VToolHandle extends VToolDecorator {
    
    private static Logger logger = Logger.getLogger(VToolHandle.class); 
    
    protected int action = VToolForm.ACT_NONE;
    private String instanceName = null;
    
    
    public VToolHandle(VirTool virToolRef, int action) 
    {
        super(virToolRef);
  
        String shortClassName = this.getClass().getName();
        shortClassName = shortClassName.substring(this.getClass().getPackage().getName().length() + 1);
        
        this.setInstanceName(virToolRef.getInstanceName() + shortClassName + "#" + VToolFactory.generateToolHandleUID());
        
        this.action = action;
    }
    
    public int getAction() 
    {
        return action;
    }

    public VirTool getVirToolRef() 
    {
        return virToolRef;
    }    
    
    public void setVirToolRef(VirTool virToolRef) 
    {
        this.virToolRef = virToolRef;
    }    
    
    public String getInstanceName() 
    {
        return this.instanceName;
    }
    
    public abstract void onMousePressed();
    
    public abstract void onMouseDragged();
    
    public abstract void onMouseReleased();
    
    public abstract void onMouseClicked();
    
    public void setInstanceName(String instanceName) 
    {
        this.instanceName = instanceName;
    }
}
