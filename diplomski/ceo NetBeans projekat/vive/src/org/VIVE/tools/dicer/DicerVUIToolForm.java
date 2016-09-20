/*
 * DicerVUIToolForm.java
 *
 * Created on 28.4.2007.
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.tools.dicer;

import org.apache.log4j.Logger;
import org.sgJoe.tools.interfaces.VUIToolForm;
import org.sgJoe.tools.interfaces.VirTool;

/**
 *
 * @author Vladimir
 */
public class DicerVUIToolForm extends VUIToolForm 
{    
    private static Logger logger = Logger.getLogger(DicerVUIToolForm.class);
    
    /**
     * Creates a new instance of DicerVUIToolForm
     */
    public DicerVUIToolForm(VirTool virToolRef) 
    {
        super (virToolRef);
    }

    public void setup() 
    {
       
    }
    
}
