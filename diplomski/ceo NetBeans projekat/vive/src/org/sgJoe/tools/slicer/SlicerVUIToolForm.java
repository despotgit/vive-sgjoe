/*
 * SlicerVUIToolForm.java
 *
 * Created on Sreda, 2006, Maj 31, 22.47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.sgJoe.tools.slicer;

import org.apache.log4j.Logger;
import org.sgJoe.tools.interfaces.VUIToolForm;
import org.sgJoe.tools.interfaces.VirTool;

/**
 *
 * @author Igor
 */
public class SlicerVUIToolForm extends VUIToolForm 
{    
    private static Logger logger = Logger.getLogger(SlicerVUIToolForm.class);
    
    /**
     * Creates a new instance of SlicerVUIToolForm
     */
    public SlicerVUIToolForm(VirTool virToolRef) 
    {
        super (virToolRef);
    }

    public void setup() 
    {
    }
    
}
