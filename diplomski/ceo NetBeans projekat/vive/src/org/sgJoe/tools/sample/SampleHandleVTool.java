package org.sgJoe.tools.sample;

import org.apache.log4j.Logger;
import org.sgJoe.tools.interfaces.VTool;
import org.sgJoe.tools.interfaces.VToolTG;


/*
 * Descritpion for SampleHandleVTool.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: April 11, 2006  9:34 AM  $
 */

public class SampleHandleVTool extends VTool {
    
    private static Logger logger = Logger.getLogger(SampleHandleVTool.class);
    
    public SampleHandleVTool() {
        super(null);
    }

    public void setup() {
    }
    
}
