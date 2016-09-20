package org.sgJoe.tools.lights;

import com.sun.j3d.utils.geometry.*;

import java.util.ArrayList;
import java.util.Hashtable;
import javax.media.j3d.*;
import javax.vecmath.*;

import org.apache.log4j.Logger;

import org.sgJoe.tools.*;
import org.sgJoe.tools.interfaces.*;
import org.sgJoe.graphics.light.*;


/*
 * Descritpion for LightVTool.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: June 1, 2006  6:09 PM  $
 */

public abstract class LightVTool extends VTool {
    
    private static Logger logger = Logger.getLogger(LightVTool.class);
    
    private VToolTG rotTG = null;
     
    public LightVTool(VirTool virToolRef) {
        super(virToolRef);
        rotTG = new VToolTG(this);
        VToolFactory.setTGCapabilities(getRotTG());
        this.addChild(getRotTG());
    }
    
    public abstract void setup();
    
    public Bounds getBoundingSphere() {
        return this.getBounds();
    }    

    /**
    * Sets the LightGroups position.
    *
    * @param position The new position
    */
    public void setPosition(Vector3f position) {
        Transform3D transform = new Transform3D();
        getTransform(transform);
        transform.setTranslation(new Vector3f(0.0f, 0.0f, 0.0f));
        this.setTransform(transform);
        transform.setTranslation(position);
        setTransform(transform);
    }
    
    /**
     * Shows/hides the LightShape.
     *
     * @param visible <code>true</code> shows the LightShape,
     *               <code>false</code> hides the LightShape.
     */
     public void setVisible(boolean visible) {
         Switch shapeSwitch = (Switch) getChild(1);
    
         if(visible) {
             shapeSwitch.setWhichChild(Switch.CHILD_ALL);
         } else {
             shapeSwitch.setWhichChild(Switch.CHILD_NONE);
         }
     }
  
      /**
       * Returns the first child of the LightGroup
       * which always has to be a Light
       */
      public Light getLight() {
          VToolTG tg = (VToolTG) getChild(0);
          BranchGroup bg =(BranchGroup) tg.getChild(0);
          Node node = bg.getChild(0);
          return (Light) node;
      }
      
        /**
         * Returns the corresponding light shape. This is depending on
         * the set light type.
         * 
         * @return The light shape.
         */
        public LightShape getLightShape() {
            VToolTG tg = (VToolTG) getChild(0);
            BranchGroup bg =(BranchGroup) tg.getChild(0);
            Node node = bg.getChild(1);         
            return (LightShape) ((Switch) node).getChild(0);
        } 
     
    /**
    * Returns a readable light name (for use in GUI).
    *
    * @param lightType One of the four light types.
    */
    abstract public String getLightTypeText();
  
  /**
   * Get the LightGroups direction.
   */
    public Vector3f getDirection() {
        // get light's rotTG transform group
        Transform3D trans = new Transform3D();
        this.getRotTG().getTransform(trans);
    
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
      public void setDirection(Vector3f direction) {
          Vector3f lightDirection = direction;
          Vector3f oldLightDirection = new Vector3f();
          // Vector that's normal to lightDirection and oldLightDirection
          Vector3f normal = new Vector3f(); 


          // Get the normal vector
          normal.cross(oldLightDirection, lightDirection);

          // Get the angle between the current and the old LightDirection
          float rotAngle = lightDirection.angle(oldLightDirection);

          // Set a new Rotation of rotAngle radians around the axis normal
          AxisAngle4f axisAngle = new AxisAngle4f(normal, rotAngle);

          // Problem when x and y are 0 then light always points to (0/0/-1) also if z > 0
          if(lightDirection.x == 0.0f && lightDirection.y == 0.0f && lightDirection.z > 0.0f) {
              axisAngle = new AxisAngle4f(new Vector3f(0.0f, 1.0f, 0.0f), (float) Math.PI);
              logger.debug("Special Case: " + axisAngle);
          }

          // Make the rotation
         Transform3D transform = new Transform3D();
         this.getRotTG().getTransform(transform);
         transform.setRotation(axisAngle);
         this.getRotTG().setTransform(transform);
      }
  
    /**
    * Get the LightGroups position. rotation must be from 
    * component rotTG
    */
    public Vector3f getPosition() {
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
    private Vector3f mulVec(Matrix3f matrix, Vector3f vector) {
        float x = (matrix.m00 * vector.x) + (matrix.m01 * vector.y) + (matrix.m02 * vector.z);
        float y = (matrix.m10 * vector.x) + (matrix.m11 * vector.y) + (matrix.m12 * vector.z);
        float z = (matrix.m20 * vector.x) + (matrix.m21 * vector.y) + (matrix.m22 * vector.z);
        return new Vector3f(x, y, z);
    }
    
    public VToolTG getRotTG() {
        return rotTG;
    }

    
    public Bounds getBoundingBox() {
        return this.getLightShape().getBounds();
    }
    
}




