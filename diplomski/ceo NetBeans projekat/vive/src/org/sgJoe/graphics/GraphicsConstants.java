package org.sgJoe.graphics;

import javax.vecmath.Color3f;


/**
 * This interface defines constants used by the classes of the graphics
 * package.
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:            0.1         $
 * @date     $ Date:  December 3, 2005 9:17 PM  $
 */
public interface GraphicsConstants {
    
    // constants for the four view planes
    public static final int PLANE_PERSPECTIVE = 0,
                            PLANE_XY = 1,
                            PLANE_XZ = 2,
                            PLANE_YZ = 3;
  
    // these constants are used to identify the currently enabled behavior
    public static final int BEHAVIOR_NONE = -1,
                            BEHAVIOR_PICK_ROTATE = 0,
                            BEHAVIOR_PICK_MOVE = 1, // means zoom and translate
                            BEHAVIOR_PICK_SCALE = 2,
                            BEHAVIOR_EXTRUSION = 3,
                            BEHAVIOR_PICK_POINT = 4,
                            BEHAVIOR_PICK_DISTANCE = 5;
    
    // these constants are used to identify currently enabled direction for chosen action
    public static final int DIRECTION_PERSPECTIVE = 0,
                            DIRECTION_XY = 1,
                            DIRECTION_XZ = 2,
                            DIRECTION_YZ = 3,
                            DIRECTION_X = 4,
                            DIRECTION_Y = 5,
                            DIRECTION_Z = 6;
    
    public static final Color3f BACKGROUND_COLOR = new Color3f(0.5f, 0.5f, 0.5f);
  
}
