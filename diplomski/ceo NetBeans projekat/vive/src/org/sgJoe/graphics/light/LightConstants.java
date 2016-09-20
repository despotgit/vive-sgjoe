package org.sgJoe.graphics.light;

import javax.media.j3d.*;
import javax.vecmath.*;

/**
 * This interface defines constants and default values
 * used by the classes of the light package.
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: May 19, 2006  11:55 AM  $
 */
public interface LightConstants
{
  public static final int DIRECTIONAL_LIGHT = 0;
  public static final int POINT_LIGHT = 1;
  public static final int SPOT_LIGHT = 2;
  public static final int AMBIENT_LIGHT = 3;
  
  public static final String  DIR_LIGHT_OBJ_FILE = "./primitives/dirLight.obj";
  public static final String  POINT_LIGHT_OBJ_FILE = "./primitives/pointLight.obj";
  public static final String  SPOT_LIGHT_OBJ_FILE = "./primitives/spotLight.obj";
  public static final String  AMB_LIGHT_OBJ_FILE = "./primitives/ambLight.obj";
  
  public final double DEFAULT_BOUNDING_RADIUS = 1000.0d;
  public final BoundingSphere DEFAULT_BOUNDING_SPHERE = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), DEFAULT_BOUNDING_RADIUS);
  public final Color3f DEFAULT_COLOR = new Color3f(1.0f, 1.0f, 1.0f);
  public final Vector3f DEFAULT_DIRECTION = new Vector3f(0.0f, 0.0f, -1.0f);
  public final float DEFAULT_SPREAD_ANGLE = (float) Math.toRadians(60);
  public final float DEFAULT_CONCENTRATION = 1.0f;
}
