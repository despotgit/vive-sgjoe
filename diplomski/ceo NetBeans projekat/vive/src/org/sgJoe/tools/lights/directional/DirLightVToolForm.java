package org.sgJoe.tools.lights.directional;

import org.apache.log4j.Logger;
import org.sgJoe.graphics.light.*;
import org.sgJoe.tools.interfaces.*;
import org.sgJoe.tools.lights.LightVToolForm;


/*
 * Descritpion for DirLightVToolForm.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: June 1, 2006  6:26 PM  $
 */

public class DirLightVToolForm extends LightVToolForm {
    
    private static Logger logger = Logger.getLogger(DirLightVToolForm.class);
    
    private float dirX, dirY, dirZ;
    
    public DirLightVToolForm(VirTool virToolRef) {
        super(virToolRef);
    }

      public void setDirX(float dirX)
      {
        this.dirX = dirX;
      }
  
      public float getDirX()
      {
        return dirX;
      }

      public void setDirY(float dirY)
      {
        this.dirY = dirY;
      }

      public float getDirY()
      {
        return dirY;
      }

      public void setDirZ(float dirZ)
      {
        this.dirZ = dirZ;
      }

      public float getDirZ()
      {
        return dirZ;
      }
}
