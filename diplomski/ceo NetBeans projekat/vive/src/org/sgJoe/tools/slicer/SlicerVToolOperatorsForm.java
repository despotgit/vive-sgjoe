/*
 * SlicerVToolOperatorsForm.java
 *
 * Created on Sreda, 2006, Maj 31, 22.47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.sgJoe.tools.slicer;

import org.apache.log4j.Logger;
import org.sgJoe.tools.interfaces.VToolOperatorsForm;
import org.sgJoe.tools.interfaces.VirTool;

/**
 *
 * @author Igor
 */
public class SlicerVToolOperatorsForm extends VToolOperatorsForm
{
    
    private static Logger logger = Logger.getLogger(SlicerVToolOperatorsForm.class);
    
    /** Creates a new instance of SlicerVToolOperatorsForm */
    public SlicerVToolOperatorsForm(VirTool virToolRef) 
    {
        super (virToolRef);
    }

    public void setup() 
    {
        super.setup();
    }
    
}
