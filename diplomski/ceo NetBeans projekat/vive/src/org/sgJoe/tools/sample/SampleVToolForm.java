package org.sgJoe.tools.sample;

import org.apache.log4j.Logger;
import org.sgJoe.graphics.behaviors.BehaviorObserver;
import org.sgJoe.logic.ActionErrors;
import org.sgJoe.logic.Session;
import org.sgJoe.plugin.Form;
import org.sgJoe.tools.*;
import org.sgJoe.tools.interfaces.VToolForm;
import org.sgJoe.tools.interfaces.VirTool;


/*
 * Descritpion for SGToolForm.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: April 2, 2006  9:28 PM     $
 */

public class SampleVToolForm extends VToolForm {
    
    private static Logger logger = Logger.getLogger(SampleVToolForm.class);
    
    public static final int ACT_CHANGE_COORDINATE_SYSTEM = 300;
    
    private String text = "";
    private int coordinateSystem = SampleVTool.COORD_SYS_VIEW;

    public SampleVToolForm(VirTool virToolRef) { 
        super(virToolRef);
    }

//    public void reset(Session session) {
//        toolAction = ACT_NONE;        
//        prevX = prevY = currX = currY = 0;
//        text = "";        
//        viewSource = null;
//    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void _onVToolDelete() {
        text = "";
    }

    public int getCoordinateSystem() {
        return coordinateSystem;
    }

    public void setCoordinateSystem(int coordinateSystem) {
        this.coordinateSystem = coordinateSystem;
    }
}
