package org.sgJoe.tools.lights;

import org.apache.log4j.Logger;
import org.sgJoe.tools.interfaces.*;


/*
 * Descritpion for LightVToolOperatorsForm.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: June 1, 2006  6:35 PM  $
 */

public class LightVToolOperatorsForm extends VToolOperatorsForm {
    
    private static Logger logger = Logger.getLogger(LightVToolOperatorsForm.class);
    
    
    public LightVToolOperatorsForm(VirTool virToolRef) {
        
        super(virToolRef);
    }

    public void setup() {
        super.setup();
    }
    
}
