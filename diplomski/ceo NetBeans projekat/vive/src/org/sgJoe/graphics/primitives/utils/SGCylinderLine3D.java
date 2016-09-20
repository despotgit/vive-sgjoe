package org.sgJoe.graphics.primitives.utils;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Appearance;
import javax.media.j3d.Shape3D;
// --> import javax.media.j3d.TransformGroup;
import javax.media.j3d.Transform3D;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Matrix4d;
import com.sun.j3d.utils.geometry.Cylinder;

    /**
 * Description for SGCylinderLine3D class:
 * This class creates a Cylinder in desired orientation.
 * Provide two endpoints and class will do transformations on 
 * Java3D utility Cylinder class.
 * 
 * This will be done in two steps
 * 1. creating Cylinder object and returning it in BranchGroup
 * 2. creating TransformGroup above BranchGroup
 * 
 * 
 * 
 * @author $ Author: Aleksandar Babic         $
 * @version $ Revision:            0.1         $
 * @date $ Date:  March 18, 2006 8:50 AM    $
 */
    public class SGCylinderLine3D {
    

        private int edges;
  
        private Cylinder cylinder = null;
  
        private BranchGroup OBJCylinderLine3D = null;
  
        private Transform3D transform = null;


  
        /**
        * Constructs a cylinder with 7 edges by default.
        */

        public SGCylinderLine3D() {
            edges = 7;
        }


        /**
        * Sets the resolution (number of edges) of the Cylinder.
        * @param e Number of edges (e.g. 8 would look like a stop sign).
        */
        public void setResolution(int e) {
            edges = e;
        }


      /**
       * Creates a cylinder.
       * @return A BranchGroup containing the cylinder in the desired orientation.
       * @param b coordinates of the base of the cylinder.
       * @param a coordinates of the top of the cylinder.
       * @param radius radius of the cylinder.
       * @param cylApp cylinder Appearance.
       */

        public BranchGroup create (Point3d b, Point3d a, double radius, Appearance cylApp) {

            // cylinder base 
            Vector3d base = new Vector3d();
            base.x = b.x;
            base.y = b.y;
            base.z = b.z;

            // cylinder apex 
            Vector3d apex = new Vector3d();
            apex.x = a.x;
            apex.y = a.y;
            apex.z = a.z;

            // calculate center of object
            Vector3d center = new Vector3d();
            center.x = (apex.x - base.x) / 2.0 + base.x;
            center.y = (apex.y - base.y) / 2.0 + base.y;
            center.z = (apex.z - base.z) / 2.0 + base.z;

            // calculate height of object 
            // unit vector along cylinder axis
            // shows cylinder orientation 
            Vector3d unit = new Vector3d();

            // unit = apex - base;
            unit.sub(apex, base); 

            // calculate the height for cylinder
            double height = unit.length();

            // normalize unit vector
            unit.normalize();

            Cylinder cylinder = createObject(radius, height, edges, cylApp);    

            this.cylinder = cylinder;

            BranchGroup bg = new BranchGroup();
            //bg.setCapability()
            bg.addChild(cylinder);

            this.OBJCylinderLine3D = bg;

            //create TransformGroup

//            TransformGroup tg = new TransformGroup();

//            tg.addChild(bg);

            /* A Java3D cylinder is created lying on the Y axis by default.
               The idea here is to take the desired cylinder's orientation
               and perform a tranformation on it to get it ONTO the Y axis.
               Then this transformation matrix is inverted and used on a
               newly-instantiated Java 3D cylinder. */

            // calculate vectors for rotation matrix
            // rotate object in any orientation, onto Y axis (exception handled below)
            Vector3d uX = new Vector3d();
            Vector3d uY = new Vector3d();
            Vector3d uZ = new Vector3d();
            double magX;
            Transform3D rotateFix = new Transform3D();

            uY = new Vector3d(unit);
            uX.cross(unit, new Vector3d(0, 0, 1));
            magX = uX.length();
            // magX == 0 if object's axis is parallel to Z axis
            if (magX != 0) {
                uX.z = uX.z / magX;
                uX.x = uX.x / magX;
                uX.y = uX.y / magX;
                uZ.cross(uX, uY);
            }
            else {
                // formula doesn't work if object's axis is parallel to Z axis
                // so rotate object onto X axis first, then back to Y at end
                double magZ;
                // (switched z -> y, y -> x, x -> z from code above)
                uX = new Vector3d(unit);
                uZ.cross(unit, new Vector3d(0, 1, 0));
                magZ = uZ.length();
                uZ.x = uZ.x / magZ;
                uZ.y = uZ.y / magZ;
                uZ.z = uZ.z / magZ;
                uY.cross(uZ, uX);
                // rotate object 90 degrees CCW around Z axis--from X onto Y
                rotateFix.rotZ(Math.PI / 2.0);
            }

            // create the rotation matrix
            Transform3D transMatrix = new Transform3D();
            Transform3D rotateMatrix =
                new Transform3D(new Matrix4d(uX.x, uX.y, uX.z, 0,
                                         uY.x, uY.y, uY.z, 0,
                                         uZ.x, uZ.y, uZ.z, 0,
                                         0,  0,  0,  1));
            // invert the matrix; need to rotate it off of the Z axis
            rotateMatrix.invert();
            // rotate the cylinder into correct orientation
            transMatrix.mul(rotateMatrix);
            transMatrix.mul(rotateFix);
            // translate the cylinder away
            transMatrix.setTranslation(center);
            // create the transform group

            this.transform = transMatrix;

//            tg.setTransform(transMatrix);

//            BranchGroup cylBg = new BranchGroup();
//            cylBg.addChild(tg);
            return this.OBJCylinderLine3D;
        }

        private Cylinder createObject(double radius, double height, int edges, Appearance cylApp) {
            return new Cylinder((float) radius, (float) height, Cylinder.GENERATE_NORMALS, edges, 1, cylApp);
        }

        public Cylinder getCylinder() {
            return cylinder;
        }

        public BranchGroup getOBJCylinderLine3D() {
            return OBJCylinderLine3D;
        }

        public Transform3D getTransform() {
            return transform;
        }

}
