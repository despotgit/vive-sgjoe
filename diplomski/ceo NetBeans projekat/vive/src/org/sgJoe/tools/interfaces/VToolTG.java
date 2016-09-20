package org.sgJoe.tools.interfaces;

import org.apache.log4j.Logger;


/*
 * Descritpion for VToolTG.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: April 19, 2006  12:46 PM  $
 */

public class VToolTG extends VTool {
    
    private static Logger logger = Logger.getLogger(VToolTG.class);
    
    private VTool vToolRef = null;
    
    public VToolTG() {
        super(null);
    }

    public VToolTG(VTool vToolRef) {
        super(null);
        this.vToolRef = vToolRef;
    }    
    
    public VTool getVToolRef() {
        return vToolRef;
    }

    public void setVToolRef(VTool vToolRef) {
        this.vToolRef = vToolRef;
    }

    public void setup() { }
    
}
