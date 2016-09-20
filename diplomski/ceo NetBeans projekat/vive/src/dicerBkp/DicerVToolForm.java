/*
 * DicerVToolForm.java
 *
 * Created on 28.4.2007.
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package dicerBkp;

import org.apache.log4j.Logger;
import org.sgJoe.logic.Session;
import org.sgJoe.tools.interfaces.VToolForm;
import org.sgJoe.tools.interfaces.VirTool;

/**
 *
 * @author Vladimir
 */
public class DicerVToolForm extends VToolForm {
    
    private static Logger logger = Logger.getLogger(DicerVToolForm.class);
    
    /** Creates a new instance of DicerVToolForm */
    public DicerVToolForm(VirTool virToolRef) {
        super (virToolRef);
    }
    
}
