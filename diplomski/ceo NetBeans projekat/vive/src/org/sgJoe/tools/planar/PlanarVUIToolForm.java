package org.sgJoe.tools.planar;

import org.apache.log4j.Logger;
import org.sgJoe.tools.interfaces.*;


/*
 * Descritpion for PlanarVUIToolForm.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: April 19, 2006  10:28 AM   $
 */

public class PlanarVUIToolForm extends VUIToolForm{
    
    private static Logger logger = Logger.getLogger(PlanarVUIToolForm.class);
    
    public PlanarVUIToolForm(VirTool virToolRef) {
        super(virToolRef);
    }

    public void setup() {
    }
    
}
