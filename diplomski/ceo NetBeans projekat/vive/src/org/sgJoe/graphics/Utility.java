package org.sgJoe.graphics;

import java.util.Enumeration;

import com.sun.j3d.loaders.*;
import com.sun.j3d.loaders.objectfile.*;
import javax.media.j3d.*;

import org.apache.log4j.Logger;

/**
 * Utility class with different responsabilities.
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:            0.1         $
 * @date     $ Date:  December 8, 2005 4:58 PM  $
 */
public class Utility {
    
    private static Logger logger = Logger.getLogger(Utility.class);
  
    /**
     * Loads an Object file
     * @param filename The filename as a String
     *        flags All the desired flags
     *        creaseAngle The desired creaseAngle as double
     * @return The scene containing the Object
     */
    public static Scene loadObjFile(String filename, int flags, double creaseAngle) {
        ObjectFile f = new ObjectFile(flags, (float)(creaseAngle * Math.PI / 180.0));
        Scene scene = null;
        try {
            scene = f.load(filename);
            BranchGroup bg = scene.getSceneGroup();
            Enumeration enumeration = bg.getAllChildren();
            while(enumeration.hasMoreElements()) {
                Node node = (Node) enumeration.nextElement();
                if(node instanceof Shape3D) {
                    Shape3D shape = (Shape3D) node;
                    shape.setCapability(Shape3D.ALLOW_GEOMETRY_READ);
                    shape.setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
                    Geometry geom = shape.getGeometry();
                    if(geom instanceof GeometryArray) {
                        geom.setCapability(GeometryArray.ALLOW_COUNT_READ);
                        geom.setCapability(GeometryArray.ALLOW_FORMAT_READ);
                        geom.setCapability(GeometryArray.ALLOW_COORDINATE_READ);
                    }
                }
            }
        } catch (java.io.FileNotFoundException e) {
            logger.error(e);
        } catch (com.sun.j3d.loaders.ParsingErrorException e) {
            logger.error(e);
        }
        return scene;
    }
  
    /**
     * Loads an Object file
     *
     * @param InputStream of the file
     *
     * @return The scene containing the Object
     */
    public static Scene loadObjFile(java.io.InputStream stream) {
        int flags = ObjectFile.TRIANGULATE;
        double creaseAngle = 60.0;
    
        ObjectFile f = new ObjectFile(flags, (float)(creaseAngle * Math.PI / 180.0));
        Scene scene = null;
        try {
            scene = f.load(new java.io.InputStreamReader(stream));
            BranchGroup bg = scene.getSceneGroup();
            Enumeration enumeration = bg.getAllChildren();
            while(enumeration.hasMoreElements()) {
                Node node = (Node) enumeration.nextElement();
                if(node instanceof Shape3D) {
                    Shape3D shape = (Shape3D) node;
                    shape.setCapability(Shape3D.ALLOW_GEOMETRY_READ);
                    shape.setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
                    Geometry geom = shape.getGeometry();
                    if(geom instanceof GeometryArray) {
                        geom.setCapability(GeometryArray.ALLOW_COUNT_READ);
                        geom.setCapability(GeometryArray.ALLOW_FORMAT_READ);
                        geom.setCapability(GeometryArray.ALLOW_COORDINATE_READ);
                    }
                }
            }
        } catch (java.io.FileNotFoundException e) {
            logger.error(e);
        } catch (com.sun.j3d.loaders.ParsingErrorException e) {
            logger.error(e);
        }
        return scene;
    }

}
