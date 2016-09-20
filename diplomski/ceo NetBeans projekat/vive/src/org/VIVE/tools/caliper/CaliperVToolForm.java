/*
 * CaliperVToolForm.java
 *
 * Created on Èetvrtak, 2006, Maj 4, 12.31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.tools.caliper;

import org.apache.log4j.Logger;
import org.sgJoe.graphics.behaviors.BehaviorObserver;
import org.sgJoe.logic.ActionErrors;
import org.sgJoe.logic.Session;
import org.sgJoe.plugin.Form;
import org.sgJoe.tools.*;
import org.sgJoe.tools.interfaces.VToolForm;
import org.sgJoe.tools.interfaces.VirTool;


/**
 *
 * @author admin
 */
public class CaliperVToolForm extends VToolForm{
    
    /**
     * Creates a new instance of CaliperVToolForm
     */
    public CaliperVToolForm(VirTool virToolRef) 
    {
        super(virToolRef);
    }
    
}
