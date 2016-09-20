package org.sgJoe.graphics.light;

import java.awt.GraphicsConfiguration;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.behaviors.mouse.*;
import com.sun.j3d.utils.behaviors.keyboard.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.picking.behaviors.PickTranslateBehavior;
import com.sun.j3d.utils.picking.behaviors.PickRotateBehavior;
import com.sun.j3d.utils.picking.behaviors.PickZoomBehavior;
import org.apache.log4j.Logger;
import javax.media.j3d.Light.*;
import javax.media.j3d.BoundingSphere;
import ch.hta.java3d.objectfile.ObjectFile;
import org.sgJoe.graphics.Utility;
import com.sun.j3d.loaders.Scene;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import org.sgJoe.plugin.*;
import org.sgJoe.graphics.light.LightShape;

/**
 * This is a factory class to create lights.
 * This is an internal class, so it is kept package private.
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: May 19, 2006  11:55 AM  $
 */
public final class LightFactory implements LightConstants
{
  private static Logger logger = Logger.getLogger(LightFactory.class);
 
  private static ClassLoader loader = ClassLoader.getSystemClassLoader();
  
  /**
   * Set the Lights default capabilities
   */
  private static void setDefaultCapabilities(Light light)
  {
    light.setCapability(Light.ALLOW_COLOR_READ);
    light.setCapability(Light.ALLOW_COLOR_WRITE);
    light.setCapability(Light.ALLOW_STATE_READ);
    light.setCapability(Light.ALLOW_STATE_WRITE);
    light.setCapability(Light.ALLOW_PICKABLE_READ);
    light.setCapability(Light.ALLOW_PICKABLE_WRITE);
  }
  
  /**
   * Inits the switch that is used to show/hide the LightShape
   * @param the switch
   */
  public static void initSwitch(Switch lightSwitch)
  {
    lightSwitch.setCapability(Switch.ALLOW_CHILDREN_READ);
    lightSwitch.setCapability(Switch.ALLOW_SWITCH_READ);
    lightSwitch.setCapability(Switch.ALLOW_SWITCH_WRITE);
    lightSwitch.setWhichChild(Switch.CHILD_ALL);
  }
  
  /**
   * Set the DirectionalLights capabilities
   */
  public static void setLightCapabilities(DirectionalLight light)
  {
    setDefaultCapabilities(light);
    
    light.setCapability(DirectionalLight.ALLOW_DIRECTION_READ);
    light.setCapability(DirectionalLight.ALLOW_DIRECTION_WRITE);
  }
  
  /**
   * Set the SpotLights capabilities
   */
  public static void setLightCapabilities(SpotLight light)
  {
    setDefaultCapabilities(light);
    
    light.setCapability(SpotLight.ALLOW_DIRECTION_READ);
    light.setCapability(SpotLight.ALLOW_DIRECTION_WRITE);
    light.setCapability(SpotLight.ALLOW_POSITION_READ);
    light.setCapability(SpotLight.ALLOW_POSITION_WRITE);
    light.setCapability(SpotLight.ALLOW_ATTENUATION_READ);
    light.setCapability(SpotLight.ALLOW_ATTENUATION_WRITE);
    light.setCapability(SpotLight.ALLOW_SPREAD_ANGLE_READ);
    light.setCapability(SpotLight.ALLOW_SPREAD_ANGLE_WRITE);
    light.setCapability(SpotLight.ALLOW_CONCENTRATION_READ);
    light.setCapability(SpotLight.ALLOW_CONCENTRATION_WRITE);
  }
  
  /**
   * Set the PointLights capabilities
   */
  public static void setLightCapabilities(PointLight light)
  {
    setDefaultCapabilities(light);
    
    light.setCapability(PointLight.ALLOW_POSITION_READ);
    light.setCapability(SpotLight.ALLOW_POSITION_WRITE);
    light.setCapability(SpotLight.ALLOW_ATTENUATION_READ);
    light.setCapability(SpotLight.ALLOW_ATTENUATION_WRITE);
    light.setCapability(SpotLight.ALLOW_SPREAD_ANGLE_READ);
    light.setCapability(SpotLight.ALLOW_SPREAD_ANGLE_WRITE);
  }
  
  /**
   * Set the AmbientLights capabilities
   */
  public static void setLightCapabilities(AmbientLight light) {
    setDefaultCapabilities(light);
  }
  
  /**
   * Creates a new Shape3D
   * @param the name of the objectFile to load
   * @return the newly created Shape3D
   */
  public static Shape3D createLightShape(String objectFile)
  {
    Scene scene = Utility.loadObjFile(loader.getResourceAsStream(objectFile));
    BranchGroup loadedObject = scene.getSceneGroup();
    Node node = loadedObject.getChild(0);
    loadedObject.removeChild(node);
    
    Shape3D shape = (Shape3D) node;    
    shape.setCapability(Shape3D.ALLOW_APPEARANCE_READ);
    shape.setCapability(Shape3D.ALLOW_GEOMETRY_READ);
    
    LightShape lightShape = new LightShape(shape);
    
    Appearance lightShapeApp = lightShape.getAppearance();
    lightShapeApp.setCapability(Appearance.ALLOW_RENDERING_ATTRIBUTES_READ);
    lightShapeApp.setCapability(Appearance.ALLOW_POLYGON_ATTRIBUTES_READ);
    lightShapeApp.setCapability(Appearance.ALLOW_POLYGON_ATTRIBUTES_WRITE);
    lightShapeApp.setCapability(Appearance.ALLOW_MATERIAL_READ);
    lightShapeApp.setCapability(Appearance.ALLOW_MATERIAL_WRITE);
    
    Color3f black = new Color3f(0.0f,  0.0f, 0.0f);
    Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
    Color3f grey = new Color3f(0.9f,  0.9f, 0.9f);
    
    Material mat = new Material(black, grey, black, white, 64.0f);
    mat.setCapability(Material.ALLOW_COMPONENT_READ);
    mat.setCapability(Material.ALLOW_COMPONENT_WRITE);
    lightShapeApp.setMaterial(mat);
    
    lightShape.setCapability(Shape3D.ALLOW_APPEARANCE_READ);
    lightShape.setAppearance(lightShapeApp);
    
    return lightShape;
  }
}
