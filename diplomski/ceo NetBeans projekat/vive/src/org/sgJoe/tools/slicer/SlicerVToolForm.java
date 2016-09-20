/*
 * SlicerVToolForm.java
 *
 * Created on Sreda, 2006, Maj 31, 22.46
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.sgJoe.tools.slicer;

import org.apache.log4j.Logger;
import org.sgJoe.logic.Session;
import org.sgJoe.tools.interfaces.VToolForm;
import org.sgJoe.tools.interfaces.VirTool;

/**
 *
 * @author Igor
 */
public class SlicerVToolForm extends VToolForm {
    
    private static Logger logger = Logger.getLogger(SlicerVToolForm.class);
    
    /** Creates a new instance of SlicerVToolForm */
    public SlicerVToolForm(VirTool virToolRef) {
        super (virToolRef);
    }
    
}
