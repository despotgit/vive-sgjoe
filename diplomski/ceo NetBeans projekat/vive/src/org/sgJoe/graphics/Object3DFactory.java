package org.sgJoe.graphics;

import com.sun.j3d.loaders.Scene;
import com.sun.j3d.utils.picking.PickTool;
import java.util.Enumeration;

import javax.media.j3d.*;
import javax.vecmath.*;
import org.sgJoe.graphics.primitives.*;

import org.apache.log4j.Logger;
import org.sgJoe.graphics.primitives.utils.SGLine3D;
import org.sgJoe.logic.SGObjectInfo;
import org.sgJoe.tools.sample.SampleVTool;
import org.sgJoe.tools.decorators.VToolHandle;
import org.sgJoe.tools.interfaces.VTool;

/**
 * This is a factory class to create 3d object. It also creates different
 * appearances. This is an internal class, so it is keept package private.
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:            0.1         $
 * @date     $ Date:  December 7, 2005 9:44 PM  $
 */
public final class Object3DFactory {
    
    private static Logger logger = Logger.getLogger(Object3DFactory.class);
    
    private static long UID = 0;
    
    private final static String fileSeparator = System.getProperty("file.separator");
                                       
    private final static String OBJECT_SPHERE_FILE = ".\\primitives" + fileSeparator + "sphere.obj";
    private final static String OBJECT_CUBE_FILE = ".\\primitives" + fileSeparator + "cube.obj";
    private final static String OBJECT_PLANE_FILE = ".\\primitives" + fileSeparator + "plane.obj";    
       
    /**
     * Creates a new cube (OBJ file).
     *
     * @return OBJCube The branchgroup containing   the cube.
     */
    public static OBJCube createCube() {
        Scene scene = Utility.loadObjFile(ClassLoader.getSystemClassLoader().getResourceAsStream(OBJECT_CUBE_FILE));
        BranchGroup bg = scene.getSceneGroup();
        Node childNode = bg.getChild(0);
        bg.removeChild(0);
        OBJCube objCube = new OBJCube();
        objCube.addChild(childNode);
        return objCube;
    }
  
    /**
     * Creates a new sphere (OBJ file)
     *
     * @return OBJSphere The BranchGroup with the sphere inside.
     */
    public static OBJSphere createSphere() {
        java.io.InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(OBJECT_SPHERE_FILE);
        Scene scene = Utility.loadObjFile(stream);
        BranchGroup bg = scene.getSceneGroup();
        Node childNode = bg.getChild(0);
        bg.removeChild(0);
        OBJSphere objSphere = new OBJSphere();
        objSphere.addChild(childNode);
        return objSphere;
    }
  
    /**
     * Creates a line
     *
     * @return line The BranchGroup with the line inside.
     */
    public static OBJLine3d createLine3d(Point3d A, Point3d B) {
        SGLine3D line3d = new SGLine3D(A, B);
        OBJLine3d objLine3d = new OBJLine3d();
        objLine3d.addChild(line3d);
        return objLine3d;
    }    
    
    /**
     * Creates a new plane (OBJ file)
     *
     * @return OBJPlane The BranchGroup with the plane inside.
     */
    public static OBJPlane createPlane() {
        Scene scene = Utility.loadObjFile(ClassLoader.getSystemClassLoader().getResourceAsStream(OBJECT_PLANE_FILE));
        BranchGroup bg = scene.getSceneGroup();
        Node childNode = bg.getChild(0);
        bg.removeChild(0);
        OBJPlane objPlane = new OBJPlane();
        objPlane.addChild(childNode);
        return objPlane;
    }

    /**
     * Creates an new BranchGroup object. A Vector3d can be given
     * to position the shape3D elsewhere than the origin.
     */
    public static TransformGroup create3DObject(BranchGroup bg, Vector3d vector) {
        TransformGroup trans = createTransformGroup();
        Transform3D t3d = new Transform3D();
        t3d.setTranslation(vector);
        trans.setTransform(t3d);
        trans.addChild(bg);
        return trans;
    }
    
    
    /**
     * Creates a TransformGroup with the needed capabilities set.
     *
     * @return TransformGroup The specific TransformGroup.
     */
    public static TransformGroup createTransformGroup() {
        TransformGroup trans = new TransformGroup();
        trans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        trans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        trans.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
        
        // need for scene graph traversal
        trans.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
        trans.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
        trans.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
        
        // create SGObjectInfo for this TransformGroup
        SGObjectInfo sgObjInfo = new SGObjectInfo(UID++);
        trans.setUserData(sgObjInfo);
        
        return trans;
    }
        
  
//    public static VToolHandle createVToolHandle(BranchGroup bg, Vector3d vector, String name, VTool vToolRef) {
//        VToolHandle trans = createTG4ToolHandle(name, vToolRef);
//        Transform3D t3d = new Transform3D();
//        t3d.setTranslation(vector);
//        trans.setTransform(t3d);
//        trans.addChild(bg);
//        return trans;
//    }        

//    public static VToolHandle createTG4ToolHandle(String name, VTool vToolRef) {
//        VToolHandle trans = new VToolHandle(name, vToolRef);
//        
//        trans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
//        trans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
//        trans.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
//        
//        // need for scene graph traversal
//        trans.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
//        trans.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
//        trans.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
//        
//        // create SGObjectInfo for this TransformGroup
//        SGObjectInfo sgObjInfo = new SGObjectInfo(UID++);
//        trans.setUserData(sgObjInfo);
//        
//        return trans;
//    }    
    
    /**
     * Returns a pickRay from the x, y position at the screen in the z
     * direction. This is helpfull to find pickable objects in the
     * 3D space.
     *
     * @param canvas The canvas3d.
     * @param x The x position on the screen.
     * @param y The y position on the screen.
     * @return PickRay A pickRay containing the path from the x,y position to the
     *                 background.
     */
    public static PickRay createPickRay(Canvas3D canvas, int x, int y) {
        Point3d eye_pos = new Point3d();
        Point3d mouse_pos = new Point3d();
    
        canvas.getCenterEyeInImagePlate(eye_pos);
        canvas.getPixelLocationInImagePlate(x, y, mouse_pos);
    
        Transform3D motion = new Transform3D();
        canvas.getImagePlateToVworld(motion);
        motion.transform(eye_pos);
        motion.transform(mouse_pos);
    
        Vector3d direction = new Vector3d(mouse_pos);
        direction.sub(eye_pos);
    
        return new PickRay(eye_pos, direction);
    }
  
    /**
     * Sets the needed capabilities for the current node. The method will
     * travers the tree down from the given node and sets capabilities for
     * the shapes and groups.
     *
     * @param parent The node to set the capabilities.
     */
    public static void setCapabilities(Node parent) {
        if (parent instanceof Shape3D) {
            Shape3D s3d = (Shape3D)parent;
            // conditional set
            if(!s3d.isCompiled() && !s3d.isLive()) {
            PickTool.setCapabilities(parent, PickTool.INTERSECT_FULL);
            parent.setCapability(Shape3D.ALLOW_APPEARANCE_READ);
            parent.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
            parent.setCapability(Shape3D.ALLOW_GEOMETRY_READ);
            parent.setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
            parent.setCapability(Shape3D.ALLOW_BOUNDS_READ);
            parent.setCapability(Shape3D.ALLOW_BOUNDS_WRITE);
            Appearance app = s3d.getAppearance();
            if (app != null) {
                app.setCapability(Appearance.ALLOW_POLYGON_ATTRIBUTES_READ);
                app.setCapability(Appearance.ALLOW_POLYGON_ATTRIBUTES_WRITE);
        
                app.setCapability(Appearance.ALLOW_MATERIAL_READ);
                app.setCapability(Appearance.ALLOW_MATERIAL_WRITE);
        
                app.setCapability(Appearance.ALLOW_RENDERING_ATTRIBUTES_READ);
                app.setCapability(Appearance.ALLOW_RENDERING_ATTRIBUTES_WRITE);
        
                app.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_READ);
                app.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);
        
                app.setCapability(Appearance.ALLOW_TEXTURE_ATTRIBUTES_READ);
                app.setCapability(Appearance.ALLOW_TEXTURE_ATTRIBUTES_WRITE);
        
                PolygonAttributes pAttr = app.getPolygonAttributes();
                if (pAttr == null) {
                    pAttr = new PolygonAttributes();
                }
                pAttr.setCapability(PolygonAttributes.ALLOW_MODE_READ);
                pAttr.setCapability(PolygonAttributes.ALLOW_MODE_WRITE);
            }
            Geometry geom = s3d.getGeometry();
            if(geom instanceof GeometryArray) {
                geom.setCapability(GeometryArray.ALLOW_COUNT_READ);
                geom.setCapability(GeometryArray.ALLOW_FORMAT_READ);
                geom.setCapability(GeometryArray.ALLOW_COORDINATE_READ);
            }
            }
            
        } else if (parent instanceof Group) {
            if(parent instanceof BranchGroup) {
                parent.setCapability(BranchGroup.ALLOW_DETACH);
            }
            parent.setCapability(Group.ALLOW_CHILDREN_EXTEND);
            parent.setCapability(Group.ALLOW_CHILDREN_READ);
            parent.setCapability(Group.ALLOW_CHILDREN_WRITE);
      
            parent.setCapability(Group.ENABLE_PICK_REPORTING);
            parent.setCapability(Group.ALLOW_BOUNDS_READ);
            parent.setCapability(Group.ALLOW_BOUNDS_WRITE);
            
            parent.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
            parent.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
      
            Enumeration enumeration = ((Group)parent).getAllChildren();
            while (enumeration.hasMoreElements()) {
                Node child = (Node)enumeration.nextElement();
                setCapabilities(child);
            }
        }
    }
  
    /**
     * Get the rotation angles of the current transform3D. The returned
     * angles are in radians.
     *
     * @param t3d The current Transform3D from where to grab the angles.
     * @return Vector3d A vector containing the axes angles.
     */
    public static Vector3d getRotationAngle(Transform3D t3d) {
        Quat4d q1 = new Quat4d();
    
        t3d.get(q1);
    
        double sqw = q1.w * q1.w;
        double sqx = q1.x * q1.x;
        double sqy = q1.y * q1.y;
        double sqz = q1.z * q1.z;
    
        double heading = Math.atan2(2.0 * (q1.x*q1.y + q1.z*q1.w), (sqx - sqy - sqz + sqw));
        double bank = Math.atan2(2.0 * (q1.y*q1.z + q1.x*q1.w), (-sqx - sqy + sqz + sqw));
        double attitude = Math.asin(-2.0 * (q1.x*q1.z - q1.y*q1.w));
        
        return new Vector3d(bank, attitude, heading);
    }    
}
