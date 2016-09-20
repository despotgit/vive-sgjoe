/*
 * CaliperVUIToolForm.java
 *
 * Created on Èetvrtak, 2006, Maj 4, 12.33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.tools.caliper;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.apache.log4j.Logger;
import org.sgJoe.exception.SGPluginException;
import org.sgJoe.graphics.SceneGraphEditor;
import org.sgJoe.gui.SGPanel;
import org.sgJoe.logic.*;
import org.sgJoe.tools.*;
import org.sgJoe.tools.interfaces.*;


/**
 *
 * @author Lakicevic Milos
 */
public class CaliperVUIToolForm extends VUIToolForm {
    
    /**
     * Creates a new instance of CaliperVUIToolForm
     */
    public CaliperVUIToolForm(VirTool virToolRef) {
        super (virToolRef) ;
    }

    public void setup() {
    }
    
}
