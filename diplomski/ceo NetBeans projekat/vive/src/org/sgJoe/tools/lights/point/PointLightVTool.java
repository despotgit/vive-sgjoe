package org.sgJoe.tools.lights.point;

import javax.media.j3d.PointLight;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Switch;
import javax.vecmath.Vector3f;
import org.apache.log4j.Logger;
import org.sgJoe.graphics.light.LightFactory;
import org.sgJoe.tools.VToolFactory;
import org.sgJoe.tools.interfaces.*;
import org.sgJoe.tools.lights.*;
        
/*
 * Descritpion for PointLightVTool.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: June 3, 2006  9:48 PM  $
 */

public class PointLightVTool extends LightVTool {
    
    private static Logger logger = Logger.getLogger(PointLightVTool.class);
    
    public PointLightVTool(VirTool virToolRef) {
        super(virToolRef);
    }

    public String getLightTypeText() {
        return "Point Light";
    }
    
    public void setup() {      
        Vector3f position = new Vector3f(0f, 0f, 0f);
        this.setPosition(position);
          
        PointLight ptLight = new PointLight();
        ptLight.setColor(LightFactory.DEFAULT_COLOR);
        ptLight.setInfluencingBounds(LightFactory.DEFAULT_BOUNDING_SPHERE);
    
        LightFactory.setLightCapabilities(ptLight);
    
        Switch lightShapeSwitch = new Switch();
        LightFactory.initSwitch(lightShapeSwitch);
        lightShapeSwitch.addChild(LightFactory.createLightShape(LightFactory.POINT_LIGHT_OBJ_FILE));

        BranchGroup bgLight = new BranchGroup();
        VToolFactory.setBGCapabilities(bgLight);        
        
        bgLight.addChild(ptLight);
        bgLight.addChild(lightShapeSwitch);
        
        this.getRotTG().addChild(bgLight);        
  
    }    

}

