package org.sgJoe.tools.planar;

import org.apache.log4j.Logger;
import org.sgJoe.logic.Session;
import org.sgJoe.tools.interfaces.*;


/*
 * Descritpion for PlanarVToolForm.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: April 19, 2006  10:26 AM   $
 */

public class PlanarVToolForm extends VToolForm {
    
    private static Logger logger = Logger.getLogger(PlanarVToolForm.class);
    
    public PlanarVToolForm(VirTool virToolRef) {
        super(virToolRef);
    }

}
