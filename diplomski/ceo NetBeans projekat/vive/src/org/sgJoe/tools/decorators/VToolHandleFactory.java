package org.sgJoe.tools.decorators;

import com.sun.j3d.utils.geometry.ColorCube;
import javax.media.j3d.*;
import javax.vecmath.*;
import org.apache.log4j.Logger;
import org.sgJoe.graphics.Object3DFactory;
import org.sgJoe.graphics.primitives.utils.SGCylinderLine3D;
import org.sgJoe.logic.SGObjectInfo;
import org.sgJoe.tools.interfaces.*;


/*
 * Descritpion for VToolHandleFactory.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: April 11, 2006  11:21 AM  $
 */

public class VToolHandleFactory {
    
    
    private static Logger logger = Logger.getLogger(VToolHandleFactory.class);
    
    public VToolHandleFactory() {
    }
    
//    public static VToolHandle createCylinderHandle(String name, VTool vToolRef, Point3d ptbase, Point3d ptapex, double dradius, Appearance cylApp) {
//        SGCylinderLine3D cylCreator = new SGCylinderLine3D();
//        
//        cylCreator.create(ptbase, ptapex, dradius, cylApp);
//
//        BranchGroup objCylinderLine3D = cylCreator.getOBJCylinderLine3D();
//        VToolHandle objTransGrp = 
//                Object3DFactory.createVToolHandle(objCylinderLine3D, new Vector3d(0.0, 0.0, 0.0), name, vToolRef);
//          
//        //Object3DFactory.setCapabilities(objTransGrp);
//        
//        objTransGrp.setTransform(cylCreator.getTransform());
//
//        SGObjectInfo info = (SGObjectInfo) objTransGrp.getUserData();
//        info.setSGName("CYLINDERHANDLE");
//                   
//        return objTransGrp;
//    }
  
    public static VToolHandle createCubeHandle(String name, VTool vToolRef, Vector3d scaleVector, Vector3d vector) {
        BranchGroup bgCube = new BranchGroup();
        
        ColorCube cube = new ColorCube();
        
        bgCube.addChild(cube);
        
       VToolHandle objTransGrp = null;
//                Object3DFactory.createVToolHandle(bgCube, vector, name, vToolRef);
        
        Transform3D currTrans = new Transform3D();
        Transform3D newTrans = new Transform3D();
        objTransGrp.getTransform(currTrans);
        
        newTrans.setScale(scaleVector);
        
        currTrans.mul(newTrans);
        
        objTransGrp.setTransform(currTrans);
        
        Object3DFactory.setCapabilities(objTransGrp);
                           
        return objTransGrp;
    }    

    
}
