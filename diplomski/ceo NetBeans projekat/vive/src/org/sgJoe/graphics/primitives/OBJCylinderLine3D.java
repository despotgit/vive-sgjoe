package org.sgJoe.graphics.primitives;

import javax.media.j3d.BranchGroup;
import org.apache.log4j.Logger;
import org.sgJoe.graphics.primitives.utils.SGCylinderLine3D;


/*
 * Descritpion for OBJCylinderLine3D.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: March 19, 2006  5:04 PM    $
 */

public class OBJCylinderLine3D extends BranchGroup  {
    
    private static Logger logger = Logger.getLogger(OBJCylinderLine3D.class);
    
    private SGCylinderLine3D creator = null;
    
    public OBJCylinderLine3D() {
        creator = new SGCylinderLine3D();
    }
    
    
}
