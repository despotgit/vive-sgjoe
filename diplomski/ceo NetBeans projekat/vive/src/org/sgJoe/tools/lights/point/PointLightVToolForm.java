package org.sgJoe.tools.lights.point;

import org.apache.log4j.Logger;
import org.sgJoe.tools.interfaces.*;
import org.sgJoe.tools.lights.*;


/*
 * Descritpion for PointLightVToolForm.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: June 3, 2006  10:06 PM  $
 */


public class PointLightVToolForm extends LightVToolForm {
    
    private static Logger logger = Logger.getLogger(PointLightVToolForm.class);
    
    private float attC, attL, attQ;
    
    public PointLightVToolForm(VirTool virToolRef) {
        super(virToolRef);
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
}
