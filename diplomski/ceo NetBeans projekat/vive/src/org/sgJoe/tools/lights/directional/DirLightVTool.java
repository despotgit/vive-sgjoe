package org.sgJoe.tools.lights.directional;

import javax.media.j3d.DirectionalLight;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Switch;
import javax.vecmath.Vector3f;
import org.apache.log4j.Logger;
import org.sgJoe.graphics.light.LightFactory;
import org.sgJoe.tools.VToolFactory;
import org.sgJoe.tools.interfaces.*;
import org.sgJoe.tools.lights.*;


/*
 * Descritpion for DirLightVTool.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: June 1, 2006  6:13 PM  $
 */

public class DirLightVTool extends LightVTool {
    
    private static Logger logger = Logger.getLogger(DirLightVTool.class);
    
    public DirLightVTool(VirTool virToolRef) {
        super(virToolRef);
    }

    public String getLightTypeText() {
        return "Directional Light";
    }
    
    public void setup() {      
        Vector3f position = new Vector3f(0f, 0f, 0f);
        this.setPosition(position);
          
        DirectionalLight dirLight = new DirectionalLight();
        dirLight.setColor(LightFactory.DEFAULT_COLOR);
        dirLight.setDirection(LightFactory.DEFAULT_DIRECTION);
        dirLight.setInfluencingBounds(LightFactory.DEFAULT_BOUNDING_SPHERE);
    
        LightFactory.setLightCapabilities(dirLight);
    
        Switch lightShapeSwitch = new Switch();
        LightFactory.initSwitch(lightShapeSwitch);
        lightShapeSwitch.addChild(LightFactory.createLightShape(LightFactory.DIR_LIGHT_OBJ_FILE));

        BranchGroup bgLight = new BranchGroup();
        VToolFactory.setBGCapabilities(bgLight);        
        
        bgLight.addChild(dirLight);
        bgLight.addChild(lightShapeSwitch);
        
        this.getRotTG().addChild(bgLight);        
  
    }    

}
