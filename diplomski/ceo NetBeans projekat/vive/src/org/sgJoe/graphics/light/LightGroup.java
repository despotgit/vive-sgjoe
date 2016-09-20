package org.sgJoe.graphics.light;


import org.apache.log4j.Logger;

import javax.media.j3d.*;
import javax.vecmath.*;

import ch.hta.java3d.objectfile.ObjectFile;

import org.sgJoe.graphics.Utility;
import com.sun.j3d.loaders.Scene;
import java.io.InputStreamReader;

import com.sun.j3d.utils.scenegraph.io.SceneGraphObjectReferenceControl;
import com.sun.j3d.utils.scenegraph.io.SceneGraphIO;

import java.io.IOException;


/**
 * This is a TransformGroup containing a light and a shape.
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: May 19, 2006  11:55 AM  $
 */
public class LightGroup extends TransformGroup implements SceneGraphIO, LightConstants
{
  private static Logger logger = Logger.getLogger(LightGroup.class);
  
  private int lightType;
  
  /**
   * Creates a new LightGroup with the needed capabilities set.
   */
  public LightGroup()
  {
    setCapability(TransformGroup.ALLOW_CHILDREN_READ);
    setCapability(TransformGroup.ENABLE_PICK_REPORTING);
    setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
    setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
  }
  
  /**
   * The method is called before writeSGObject and gives the user the chance 
   * to create references to other Nodes and NodeComponents. References take 
   * the form of a nodeID, of type integer. Every SceneGraphObject is assigned 
   * a unique ID. The user must save the reference information in writeSGObject.
   *
   * @param ref Provides methods to create references to a SceneGraphObject.
   */
  public void createSceneGraphObjectReferences( SceneGraphObjectReferenceControl ref )
  {}
  
  /**
   * Within this method the user should restore references to the SceneGraphObjects 
   * whose nodeID's were created with createSceneGraphObjectReferences This 
   * method is called once the all objects in the scenegraph have been loaded.
   *
   * @param ref Provides methods to resolve references to a SceneGraphObject.
   */
  public void restoreSceneGraphObjectReferences( SceneGraphObjectReferenceControl ref )
  {}
  
  /**
   * This method should store all the local state of the object and any references 
   * to other SceneGraphObjects into out. This is called after data for the parent 
   * SceneGraphObject has been written to the out.
   *
   * @param out The output stream.
   * @throws IOException Exception occured.
   */
  public void writeSceneGraphObject( java.io.DataOutput out ) throws IOException
  {
    out.writeInt(lightType);
  }
  
  /**
   * This is called after the object has been constructed and the superclass 
   * SceneGraphObject data has been read from in. The user should restore all 
   * state infomation written in writeSGObject.
   *
   * @param in The input stream.
   * @throws IOException Exception occured.
   */
  public void readSceneGraphObject( java.io.DataInput in ) throws IOException
  {
    lightType = in.readInt();
  }
  
  /**
   * Flag indicating for children of this object should be saved This method 
   * only has an effect if this is a subclass of Group. If this returns true 
   * then all children of this Group will be saved. If it returns false then 
   * the children are not saved.
   */
  public boolean saveChildren()
  {
    return true;
  }
  
  /**
   * Sets the type of light.
   * <br><br>
   * - DIRECTIONAL_LIGHT
   * - POINT_LIGHT
   * - SPOT_LIGHT
   * - AMBIENT_LIGHT
   * <br><br>
   * The values are defined in the LightConstants.
   *
   * @param lightType The type of light.
   */
  public void setLightType(int lightType)
  {
    this.lightType = lightType;
  }
  
  /**
   * Returns the light type.
   * <br><br>
   * - DIRECTIONAL_LIGHT
   * - POINT_LIGHT
   * - SPOT_LIGHT
   * - AMBIENT_LIGHT
   * <br><br>
   * The values are defined in the LightConstants.
   *
   * @return The light type.
   */
  public int getLightType()
  {
    return lightType;
  }
  
  /**
   * Returns the corresponding light shape. This is depending on
   * the set light type.
   * 
   * @return The light shape.
   */
  public LightShape getLightShape()
  {
    return (LightShape) ((Switch)getChild(1)).getChild(0);
  }
  
  /**
   * Sets the LightGroups position.
   *
   * @param position The new position
   */
  public void setPosition(Vector3f position)
  {
    Transform3D transform = new Transform3D();
    getTransform(transform);
    transform.setTranslation(new Vector3f(0.0f, 0.0f, 0.0f));
    setTransform(transform);
    transform.setTranslation(position);
    setTransform(transform);
  }
  
  /**
   * Shows/hides the LightShape.
   *
   * @param visible <code>true</code> shows the LightShape,
   *               <code>false</code> hides the LightShape.
   */
  public void setVisible(boolean visible)
  {
    Switch shapeSwitch = (Switch) getChild(1);
    
    if(visible)
    {
      shapeSwitch.setWhichChild(Switch.CHILD_ALL);
    }
    else
    {
      shapeSwitch.setWhichChild(Switch.CHILD_NONE);
    }
  }
  
  /**
   * Returns the first child of the LightGroup
   * which always has to be a Light
   */
  public Light getLight()
  {
    return (Light) getChild(0);
  }
  
  /**
   * Returns a readable light name (for use in GUI).
   *
   * @param lightType One of the four light types.
   */
  public String getLightTypeText(int lightType)
  {
    String lightTypeText = null;
    switch(lightType)
    {
      case DIRECTIONAL_LIGHT:
        lightTypeText = "Directional Light";
        break;
      case POINT_LIGHT:
        lightTypeText = "Point Light";
        break;
      case SPOT_LIGHT:
        lightTypeText = "Spot Light";
        break;
      case AMBIENT_LIGHT:
        lightTypeText = "Ambient Light";
        break;
    }
    return lightTypeText;
  }
  
  /**
   * Get the LightGroups direction.
   */
  public Vector3f getDirection()
  {
    // get light's transform group
    Transform3D trans = new Transform3D();
    getTransform(trans);
    
    // get the rotation matrix and the position vector
    Matrix3f rotation = new Matrix3f();
    Vector3f position = new Vector3f();
    trans.get(rotation, position);
    
    // calculate light's direction
    Vector3f direction = new Vector3f();
    direction = mulVec(rotation,new Vector3f(0.0f, 0.0f, -1.0f));
    
    return direction;
  }
  
  /**
   * Sets the LightGroups direction.
   *
   * @param direction The new direction
   */
  public void setDirection(Vector3f direction)
  {
    Vector3f lightDirection = direction;
    Vector3f oldLightDirection = new Vector3f();
    Vector3f normal = new Vector3f(); // Vector that's normal to lightDirection and oldLightDirection
    
    if(lightType == DIRECTIONAL_LIGHT)
    {
      DirectionalLight dirLight = (DirectionalLight) getLight();
      dirLight.getDirection(oldLightDirection);
    }
    else if(lightType == SPOT_LIGHT)
    {
      SpotLight spotLight = (SpotLight) getLight();
      spotLight.getDirection(oldLightDirection);
    }
    
    // Get the normal vector
    normal.cross(oldLightDirection,lightDirection);
    
    // Get the angle between the current and the old LightDirection
    float rotAngle = lightDirection.angle(oldLightDirection);
    
    // Set a new Rotation of rotAngle radians around the axis normal
    AxisAngle4f axisAngle = new AxisAngle4f(normal,rotAngle);
    
    // Problem when x and y are 0 then light always points to (0/0/-1) also if z > 0
    if(lightDirection.x == 0.0f && lightDirection.y == 0.0f && lightDirection.z > 0.0f)
    {
      axisAngle = new AxisAngle4f(new Vector3f(0.0f, 1.0f, 0.0f), (float) Math.PI);
      logger.debug("Special Case: " + axisAngle);
    }
    
    // Make the rotation
    Transform3D transform = new Transform3D();
    getTransform(transform);
    transform.setRotation(axisAngle);
    setTransform(transform);
  }
  
  /**
   * Get the LightGroups position.
   */
  public Vector3f getPosition()
  {
    // get light's transform group
    Transform3D trans = new Transform3D();
    getTransform(trans);
    
    // get the rotation matrix and the position vector
    Matrix3f rotation = new Matrix3f();
    Vector3f position = new Vector3f();
    trans.get(rotation, position);
    
    return position;
  }
  
  /**
   * Helper method to multiply a vector by a matrix
   */
  private Vector3f mulVec(Matrix3f matrix, Vector3f vector)
  {
    float x = (matrix.m00 * vector.x) + (matrix.m01 * vector.y) + (matrix.m02 * vector.z);
    float y = (matrix.m10 * vector.x) + (matrix.m11 * vector.y) + (matrix.m12 * vector.z);
    float z = (matrix.m20 * vector.x) + (matrix.m21 * vector.y) + (matrix.m22 * vector.z);
    return new Vector3f(x, y, z);
  }
  
  /**
   *  Has to be overridden so that cloning works for user defined class
   */
  public Node cloneNode(boolean forceDuplicate)
  {
    LightGroup lg = new LightGroup();
    lg.duplicateNode(this, forceDuplicate);
    return lg;
  }
}

