/*
 * PlaneAdapter.java
 *
 * Created on June 17, 2006, 2:57 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.sgJoe.tools.templates;

import javax.vecmath.*;
import org.sgJoe.tools.interfaces.*;

/**
 *
 * @author ealebab
 */
public interface PlaneAdapter {
    
    public VToolTG getRotTG();
    
    public Vector3f getPosition4Plane();
    
}
