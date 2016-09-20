/*
 * CaliperVToolOperatorsForm.java
 *
 * Created on Èetvrtak, 2006, Maj 4, 12.32
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.tools.caliper;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import org.apache.log4j.Logger;
import org.sgJoe.exception.SGPluginException;
import org.sgJoe.gui.SGButtonGroup;
import org.sgJoe.tools.*;
import org.sgJoe.tools.interfaces.VToolOperatorsForm;
import org.sgJoe.tools.interfaces.VToolPlugin;
import org.sgJoe.tools.interfaces.VirTool;

/**
 *
 * @author admin
 */
public class CaliperVToolOperatorsForm extends VToolOperatorsForm{
    
    /**
     * Creates a new instance of CaliperVToolOperatorsForm
     */
    public CaliperVToolOperatorsForm(VirTool virToolRef) {
        super(virToolRef);
    }

    public void setup() {
         
    }
    
}
