package org.sgJoe.tools.lights.spot;

import javax.media.j3d.*;
import javax.vecmath.Vector3f;
import org.apache.log4j.Logger;
import org.sgJoe.graphics.light.LightFactory;
import org.sgJoe.tools.VToolFactory;
import org.sgJoe.tools.interfaces.*;
import org.sgJoe.tools.lights.*;
/*
 * Descritpion for SpotLightVTool.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: June 4, 2006  9:37 AM  $
 */

public class SpotLightVTool extends LightVTool {
    
    private static Logger logger = Logger.getLogger(SpotLightVTool.class);
    
    public SpotLightVTool(VirTool virToolRef) {
        super(virToolRef);
    }

    public String getLightTypeText() {
        return "Spot Light";
    }
    
    public void setup() {      
        Vector3f position = new Vector3f(0f, 0f, 0f);
        this.setPosition(position);
          
        SpotLight spotLight = new SpotLight();
        spotLight.setColor(LightFactory.DEFAULT_COLOR);
        spotLight.setDirection(LightFactory.DEFAULT_DIRECTION);
        spotLight.setSpreadAngle(LightFactory.DEFAULT_SPREAD_ANGLE);
        spotLight.setConcentration(LightFactory.DEFAULT_CONCENTRATION);        
        spotLight.setInfluencingBounds(LightFactory.DEFAULT_BOUNDING_SPHERE);
    
        LightFactory.setLightCapabilities(spotLight);
    
        Switch lightShapeSwitch = new Switch();
        LightFactory.initSwitch(lightShapeSwitch);
        lightShapeSwitch.addChild(LightFactory.createLightShape(LightFactory.SPOT_LIGHT_OBJ_FILE));

        BranchGroup bgLight = new BranchGroup();
        VToolFactory.setBGCapabilities(bgLight);        
        
        bgLight.addChild(spotLight);
        bgLight.addChild(lightShapeSwitch);
        
        this.getRotTG().addChild(bgLight);   
    
    }    

}

