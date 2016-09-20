package org.sgJoe.tools.interfaces;

import javax.media.j3d.TransformGroup;
import org.apache.log4j.Logger;
import org.sgJoe.tools.VToolFactory;


/*
 * Descritpion for VToolDecorator.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: April 11, 2006  10:41 AM   $
 */

public abstract class VToolDecorator extends TransformGroup 
{
    
    protected VirTool virToolRef = null;
        
    public VToolDecorator(VirTool virToolRef) 
    {
        super();
        VToolFactory.setTGCapabilities(this);
        
        this.virToolRef = virToolRef;
    }

    public VirTool getVirToolRef() 
    {
        return virToolRef;
    }
 
}
