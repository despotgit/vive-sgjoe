package org.sgJoe.tools.main;

import org.apache.log4j.Logger;

import org.sgJoe.tools.interfaces.*;

/*
 * Descritpion for MainVTool.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: May 22, 2006  5:37 PM  $
 */

public class MainVTool extends VTool {
    
    private static Logger logger = Logger.getLogger(MainVTool.class);
    
    public MainVTool(VirTool virToolRef) {
        super(virToolRef);
    }
    
    public void setup() {
        
    }    

    public VToolTG getRotTG() {
        return null;
    }
}
