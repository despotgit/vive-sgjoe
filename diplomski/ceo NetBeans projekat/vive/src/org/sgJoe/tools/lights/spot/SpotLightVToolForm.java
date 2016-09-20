package org.sgJoe.tools.lights.spot;

import org.apache.log4j.Logger;
import org.sgJoe.tools.interfaces.*;
import org.sgJoe.tools.lights.*;


/*
 * Descritpion for SpotLightVToolForm.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: June 4, 2006  9:44 AM  $
 */

public class SpotLightVToolForm extends LightVToolForm {
    
    private static Logger logger = Logger.getLogger(SpotLightVToolForm.class);
    
    private float dirX, dirY, dirZ;
    private float attC, attL, attQ;
    
    private double spreadAngle;
    private double concentration;

    
    public SpotLightVToolForm(VirTool virToolRef) {
        super(virToolRef);
    }
    
    public void setDirX(float dirX) {
        this.dirX = dirX;
    }

    public float getDirX() {
        return dirX;
    }

    public void setDirY(float dirY) {
        this.dirY = dirY;
    }

    public float getDirY() {
        return dirY;
    }

    public void setDirZ(float dirZ) {
        this.dirZ = dirZ;
    }

    public float getDirZ() {
        return dirZ;
    }    
  
    public void setAttC(float attC) {
        this.attC = attC;
    }
  
    public float getAttC() {
        return attC;
    }
  
    public void setAttL(float attL) {
        this.attL = attL;
    }
  
    public float getAttL() {
        return attL;
    }
  
    public void setAttQ(float attQ) {
        this.attQ = attQ;
    }
  
    public float getAttQ() {
        return attQ;
    } 
    
    public void setSpreadAngle(double spreadAngle) {
        this.spreadAngle = spreadAngle;
    }
  
    public double getSpreadAngle() {
        return spreadAngle;
    }
  
    public void setConcentration(double concentration) {
        this.concentration = concentration;
    }
  
    public double getConcentration() {
        return concentration;
    }    
    
}
