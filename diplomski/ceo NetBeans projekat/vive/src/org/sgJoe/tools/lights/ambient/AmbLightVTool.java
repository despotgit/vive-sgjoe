package org.sgJoe.tools.lights.ambient;

import com.sun.j3d.utils.geometry.*;

import java.util.ArrayList;
import java.util.Hashtable;
import javax.media.j3d.*;
import javax.vecmath.*;

import org.apache.log4j.Logger;
import org.sgJoe.graphics.light.LightFactory;
import org.sgJoe.graphics.light.LightShape;

import org.sgJoe.tools.*;
import org.sgJoe.tools.interfaces.*;
import org.sgJoe.tools.lights.LightVTool;

/*
 * Descritpion for AmbLightVTool.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: May 19, 2006  9:35 PM  $
 */

public class AmbLightVTool extends LightVTool {//extends VTool {
    
    private static Logger logger = Logger.getLogger(AmbLightVTool.class);
    
    
    public AmbLightVTool(VirTool virToolRef) {
        super(virToolRef);
    }

    public void setup() {      
        Vector3f position = new Vector3f(0f, 0f, 0f);
        this.setPosition(position);
          
        AmbientLight ambLight = new AmbientLight();
        ambLight.setColor(LightFactory.DEFAULT_COLOR);
        ambLight.setInfluencingBounds(LightFactory.DEFAULT_BOUNDING_SPHERE);
    
        LightFactory.setLightCapabilities(ambLight);
    
        Switch lightShapeSwitch = new Switch();
        LightFactory.initSwitch(lightShapeSwitch);
        lightShapeSwitch.addChild(LightFactory.createLightShape(LightFactory.AMB_LIGHT_OBJ_FILE));

        BranchGroup bgLight = new BranchGroup();
        VToolFactory.setBGCapabilities(bgLight);        
        
        bgLight.addChild(ambLight);
        bgLight.addChild(lightShapeSwitch);
        
        this.getRotTG().addChild(bgLight);        
        
    }
    
     
    /**
    * Returns a readable light name (for use in GUI).
    *
    * @param lightType One of the four light types.
    */
    public String getLightTypeText() {
        return "Ambient Light";
    }
  
}



