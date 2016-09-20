package org.sgJoe.tools.lights;

import javax.vecmath.Color3f;
import org.apache.log4j.Logger;
import org.sgJoe.tools.interfaces.*;

/*
 * Descritpion for LightVToolForm.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: June 1, 2006  6:22 PM  $
 */


public class LightVToolForm extends VToolForm {
    
    private static Logger logger = Logger.getLogger(LightVToolForm.class);
    
    private float posX, posY, posZ;
    private boolean lightEnabled = true;

    private Color3f color;
       
    public LightVToolForm(VirTool virToolRef) {
        super(virToolRef);
    }    

    public Color3f getColor() {
        return color;
    }

    public void setColor(Color3f color) {
        this.color = color;
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

    public boolean isLightEnabled() {
        return lightEnabled;
    }

    public void setLightEnabled(boolean lightEnabled) {
        this.lightEnabled = lightEnabled;
    }
    
}

