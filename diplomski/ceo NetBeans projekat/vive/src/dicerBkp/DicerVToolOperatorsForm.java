/*
 * DicerVToolOperatorsForm.java
 *
 * Created on 28.4.2007.
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package dicerBkp;

import org.apache.log4j.Logger;
import org.sgJoe.tools.interfaces.VToolOperatorsForm;
import org.sgJoe.tools.interfaces.VirTool;

/**
 *
 * @author Vladimir
 */
public class DicerVToolOperatorsForm extends VToolOperatorsForm
{
    
    private static Logger logger = Logger.getLogger(DicerVToolOperatorsForm.class);
    
    /** Creates a new instance of DicerVToolOperatorsForm */
    public DicerVToolOperatorsForm(VirTool virToolRef) 
    {
        super (virToolRef);
    }

    public void setup() 
    {
        super.setup();
    }
    
}