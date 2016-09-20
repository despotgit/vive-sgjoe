/*
 * CaliperVToolHandle.java
 *
 * Created on Èetvrtak, 2006, Maj 4, 15.59
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.tools.caliper;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import org.apache.log4j.Logger;

import com.sun.j3d.utils.geometry.ColorCube;
import javax.vecmath.Vector3d;

import org.sgJoe.tools.*;
import org.sgJoe.tools.decorators.*;
import org.sgJoe.tools.interfaces.*;
import javax.media.j3d.*;

/**
 *
 * @author admin
 */
public class CaliperVToolHandle extends VToolHandle{
    
    /**
     * Creates a new instance of CaliperVToolHandle
     */
    public CaliperVToolHandle(VirTool virToolRef, int action) {
         super(virToolRef, action);
         //TODO: kreiranje tela rucke
    }

    public void onMousePressed() {
    }

    public void onMouseDragged() {
    }

    public void onMouseReleased() {
    }

    public void onMouseClicked() {
    }
    
}
