/*
 * CaliperVToolPlugin.java
 *
 * Created on Èetvrtak, 2006, Maj 4, 12.32
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.tools.caliper;

import com.sun.j3d.utils.geometry.Box;
import org.apache.log4j.Logger;

import com.sun.j3d.utils.geometry.ColorCube;
import javax.media.j3d.*;
import javax.vecmath.*;

import org.sgJoe.exception.SGPluginException;
import org.sgJoe.graphics.*;
import org.sgJoe.graphics.*;
import org.sgJoe.graphics.behaviors.*;
import org.sgJoe.logic.Session;
import org.sgJoe.plugin.*;
import org.sgJoe.tools.*;
import org.sgJoe.tools.decorators.*;
import org.sgJoe.tools.interfaces.*;

/**
 *
 * @author admin
 */
public class CaliperVToolPlugin extends VToolPlugin{
    
    private static Logger logger = Logger.getLogger(CaliperVToolPlugin.class);
     
    /**
     * Creates a new instance of CaliperVToolPlugin
     */
    public CaliperVToolPlugin(VirTool virToolRef) {
        super(virToolRef);
    }

    public void performAction(Form form, SceneGraphEditor editor, Session session) throws SGPluginException {
    }
    
}
