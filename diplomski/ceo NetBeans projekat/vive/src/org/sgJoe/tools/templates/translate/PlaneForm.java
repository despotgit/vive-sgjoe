package org.sgJoe.tools.templates.translate;

import org.apache.log4j.Logger;
import org.sgJoe.tools.interfaces.*;


/*
 * Descritpion for PlaneForm.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: June 17, 2006  9:11 AM  $
 */

public class PlaneForm extends VToolForm {
    
    private static Logger logger = Logger.getLogger(PlaneForm.class);
    
    private float posX, posY, posZ;
        
    public PlaneForm(VirTool virToolRef) {
        super(virToolRef);
    }    

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public float getPosZ() {
        return posZ;
    }

    public void setPosZ(float posZ) {
        this.posZ = posZ;
    }

}
