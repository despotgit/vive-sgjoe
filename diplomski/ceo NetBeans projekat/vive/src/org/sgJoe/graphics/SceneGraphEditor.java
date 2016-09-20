package org.sgJoe.graphics;

import org.apache.log4j.Logger;

import com.sun.j3d.utils.picking.PickResult;

import java.util.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;
import javax.media.j3d.*;
import org.sgJoe.graphics.light.LightFactory;
import org.sgJoe.graphics.primitives.*;
import org.sgJoe.logic.*;
import org.sgJoe.tools.*;
import org.sgJoe.tools.interfaces.*;

/**
 * This class controlles the manipulation of the scene graph. It holds the
 * BaseSceneGraph and is able to add or delete nodes from the scene graph.
 * It also contains some factory methods to create shapes. These shapes are
 * ready to add to the scene graph.
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:            0.1         $
 * @date     $ Date:  December 10, 2005 6:12 PM $
 */
public class SceneGraphEditor {
    
    private static Logger logger = Logger.getLogger(SceneGraphEditor.class);
    
    private BaseSceneGraph baseSceneGraph = null;
    
    private SGObjectRegistry sgObjectRegistry = new SGObjectRegistry();
    
    private SGObjectStack sgObjectStack = new SGObjectStack();    
  
    // Flag to indicate, if this scene graph is editable or not.
    private boolean isEditable = true;

    private ToolRegistry    toolRegistry;
    private VirToolMap      virToolMap;
    private VirToolStack    virToolStack;
    // --> private VToolMap toolInstanceMap;
    // --> private VToolInstanceRepository toolInstanceRepository;
  
    /**
     * Creates a new instance of SceneGraphEditor. This method is keeped private
     * to prevent outside creation (singelton).
     */
    public SceneGraphEditor(BaseSceneGraph baseSceneGraph) {
        this.baseSceneGraph = baseSceneGraph;
        this.baseSceneGraph.setParentEditor(this);
        // --> this.toolInstanceMap = new VToolMap();
        // --> this.toolInstanceRepository = new VToolInstanceRepository(); 
        this.virToolMap = new VirToolMap(); 
        this.virToolStack = new VirToolStack(10); 
        // set the base scene graph in navigation mode
        this.enableEditorMode(isEditable);
    }
    
    /**
     * Returns an instance of the base scene graph.
     *
     * TODO: the BaseSceneGraph should be hidden. It's the SceneGraphEditor's
     * job to manipulate the scene graph.
     *
     * @return The current BaseSceneGraph.
    */
    public BaseSceneGraph getBaseSceneGraph() {
        return this.baseSceneGraph;
    }
    
    /**
     * Deletes the current scene. All children from the objTop are
     * removed. To remove all children, we have to detach the top
     * from the root and to attach it back. For complex scenes, this
     * could lead to a flicker on the screen.
     */
    public void deleteScene() {
        // detach the scene graph from the root for manipulation. This
        // could leas to a short flicker on the screen.
        // TODO: need some sort of double buffer of the scene graph.
        
        sgObjectStack.clear();
        sgObjectRegistry.clear();
        
        baseSceneGraph.detachTopFromRoot();
        baseSceneGraph.getObjTop().removeAllChildren();
        baseSceneGraph.attachTopToRoot();
    }
  
    /**
     * Enable editor mode. That means, 3d objects can be rotated, translated,
     * scaled or added to the scene graph. The orbit behavour is disabled.
     *
     * @param enable <code>true</code> enables the editor mode, <code>false</code>
     *               else.
     */
    public void enableEditorMode(boolean enable) {
        baseSceneGraph.enableOrbitBehaviour(!enable);
        baseSceneGraph.enablePickBehaviour(enable);
        this.isEditable = enable;
    }
  
    /**
     * Sets the orbit navigation mode.
     *
     * @param orbitNav <code>true</code> activates the orbit navigation mode,
     *                 <code>false</code> deactivates the orbit navigation mode.
     */
    public void setNavigationMode(boolean orbitNav) {
        baseSceneGraph.setNavigationMode(orbitNav);
    }
  
    /**
     * Returns <code>true</code> if this scene graph is editable, else
     * <code>false</code>. A scene graph is editable, if the orbit behaviour
     * is disabled and pick behaviour is enabled.
     *
     * @return <code>true</code> if the scene graph is editable,
     *         else <code>false</code>
     */
    public boolean isEditable() {
        return this.isEditable;
    }
  
    /**
     * Enable or disbale the mouse pick rotate behaviour. This is not the same
     * like <code>enableEditorMode(boolean)</code>. With this method, we can enable
     * a specific mouse behaviour.
     *
     * @param enable <code>true</code> to enable the behaviour, <code>false</code>
     *               else.
     */
    public void enableMousePickRotateBehaviour(boolean enable) {
        baseSceneGraph.enablePickRotateBehavior(enable);
    }
  
    /**
     * Enable or disbale the mouse pick rotate behaviour. This is not the same
     * like <code>setEditorMode(boolean)</code>. With this method, we can enable
     * a specific mouse behaviour.
     *
     * @param enable <code>true</code> to enable the behaviour, <code>false</code>
     *               else.
     */
    public void enableMousePickTranslateBehaviour(boolean enable) {
        baseSceneGraph.enablePickMoveBehavior(enable);
    }
  
    /**
     * Enable or disable the mouse pick scale behaviour. This is not the same
     * like <code>setEditorMode(boolean)</code>. With this method, we can enable
     * a specific mouse behaviour.
     *
     * @param enable <code>true</code> to enable the behaviour, <code>false</code>
     *               else.
     */
    public void enableMousePickScaleBehaviour(boolean enable) {
        baseSceneGraph.enablePickScaleBehavior(enable);
    }
    
    /**
     * Enable or disbale the mouse pick point behaviour. This is not the same
     * like <code>setEditorMode(boolean)</code>. With this method, we can enable
     * a specific mouse behaviour.
     *
     * @param enable <code>true</code> to enable the behaviour, <code>false</code>
     *               else.
     */
    public void enableMousePickPointBehavior(boolean enable) {
        baseSceneGraph.enablePickPointBehavior(enable);
    }    
  
    /**
     * Enable or disbale the mouse distance meter behaviour. This is not the same
     * like <code>setEditorMode(boolean)</code>. With this method, we can enable
     * a specific mouse behaviour.
     *
     * @param enable <code>true</code> to enable the behaviour, <code>false</code>
     *               else.
     */
    public void enableMousePickDistanceBehavior(boolean enable) {
        baseSceneGraph.enablePickDistanceBehavior(enable);
    }    
    /**
     * Removes a node from the scene graph. To do this properly, the
     * top of the tree will be detached from the scene graph. Then the
     * node will be removed and then attached to the root of the scene
     * graph. This cause that the image will flicker. To avoid this,
     * we have to double buffer the scene graph.
     *
     * @param node The node to remove from the scene graph.
     */
    public void removeNode(Node node) 
    {
        if (baseSceneGraph.getObjTop().isCompiled() || baseSceneGraph.getObjTop().isLive()) 
        {
          logger.debug("scene graph is compiled or live, detache top from root");
          baseSceneGraph.detachTopFromRoot();
        }
        _removeNode(node);
        baseSceneGraph.attachTopToRoot();
    }

    /**
     * Internal used by removeNode(...). This method climbs up the scene graph
     * to find the group with more than one child. At this position we can
     * safely remove the subtree.
     */
    private void _removeNode(Node node) {
        
        if(node == null) {
            return;
        }
        Node parent = node.getParent();
        
        if(parent instanceof TransformGroup) {
            // get object info
            TransformGroup TG = (TransformGroup) parent;
            SGObjectInfo info = (SGObjectInfo) TG.getUserData();
            if(info != null) {
                sgObjectStack.removeUID(info.getSGUID());
                sgObjectRegistry.removeObjectTG(info.getSGUID());
            }

            // deal with tools
            if( !(parent instanceof VToolTG) && parent instanceof VTool) {
                VTool vTool = (VTool) parent;
//                String toolName = vTool.getToolName();
//                VirTool instance = virToolMap.get(vTool.getToolUID());
//                VToolForm toolForm = instance.getVToolFormRef();
//                VUIToolForm guiForm = instance.getVUIToolFormRef();
//                guiForm.onVToolDelete(vTool);
//                toolForm.onVToolDelete(vTool);
//                
//                virToolMap.remove(vTool.getToolUID());  
            }
        }
        int count = 0;
        // if we have no more parent, we hit the root.
        if(parent == null) {
            return;
        }
        if (parent.equals(baseSceneGraph.getObjTop())) {
          ((Group)parent).removeChild(node);
        } else {
          count = _countChild(parent);
          // if we have less than one child, we search for more.
          // if we have more than one child, we detach it from graph.
          if (count <= 1) {
            // recurse
            _removeNode(parent);
          } else {
            if (parent instanceof Group)
            {
              ((Group)parent).removeChild(node);
            }
          }
        }
      }
  
    /**
     * Internal use: counts the child nodes of the scene graph. We count
     * only leafes.
     */
    private int _countChild(Node parent) {
        int count = 0;

        if (parent instanceof Group && !(parent instanceof Primitive)) {
            Group g = (Group)parent;
            Enumeration enumeration = g.getAllChildren();
            while (enumeration.hasMoreElements()) {
                count += _countChild((Node)enumeration.nextElement());
            }
        } else if (parent instanceof Shape3D || parent instanceof ColorCube) {
            count++;
        }
        return count;
    }
  
    /**
     * Add a new child to the top of the scene graph.
     *
     * @param node The node to add to the scene graph.
     */
    public void addChild(Node node) {    
        BranchGroup bg = new BranchGroup();
        VToolFactory.setBGCapabilities(bg);
//        bg.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
//        bg.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        bg.addChild(node);

//        if((node instanceof org.sgJoe.tools.interfaces.VTool) &&
//                (node instanceof org.sgJoe.tools.decorators.VToolHandle)) {
//            sgObjectRegistry.putObjectTG((TransformGroup) node);
//        }
        
        
        // this is some hack: there is a problem, when we insert a
        // new object when we aren't in editor mode. we have to check
        // and explicitly enable editor mode. Wired, but it's like
        // that. lives is not always as fun as it seems.
        if (isEditable) {
            baseSceneGraph.getObjTop().addChild(bg);
        } else {
            this.enableEditorMode(true);
            baseSceneGraph.getObjTop().addChild(bg);
            this.enableEditorMode(false);
        }
    }
  
    /**
     * Sets the capabilities needed to manipulate an shape3d. It also sets
     * the needed capabilities for Groups. The method recurse down the scene
     * graph and sets the capabilities to all nodes.
     *
     * @param node The node to set the capabilities.
     */
    public void setCapabilities(Node node) {
        Object3DFactory.setCapabilities(node);
    }
  
    /**
     * Returns the top of the scene graph. This is the place in the
     * scene graph where all custome 3D objects are added.
     *
     * @return BranchGroup The top of the scene graph.
     */
    public BranchGroup getSceneGraphTop() {
        return baseSceneGraph.getObjTop();
    }  
      
  
    /**
     * Creates a new 3d object. It takes a vector3D as argument to
     * translate the object to a specific position.
     *
     * @param vector The translate vector.
     * @return TransformGroup The 3d object.
     */
    public TransformGroup createBox(Vector3d vector) {
      
      OBJCube objCube = Object3DFactory.createCube();
      TransformGroup cubeTG = Object3DFactory.create3DObject(objCube, vector);

      Transform3D sphereTr = new Transform3D();
           
      OBJSphere objSphereX = Object3DFactory.createSphere();
      TransformGroup sphereTGX = Object3DFactory.create3DObject(objSphereX, new Vector3d(1.5, 0.0, 0.0));
      sphereTGX.getTransform(sphereTr);
      sphereTr.setScale(0.5);      
      sphereTGX.setTransform(sphereTr);
      
      //sphereTr = new Transform3D();
      //sphereTr.setScale(0.5);
      OBJSphere objSphereY = Object3DFactory.createSphere();
      TransformGroup sphereTGY = Object3DFactory.create3DObject(objSphereY, new Vector3d(0.0, 1.5, 0.0));
      sphereTGY.getTransform(sphereTr);
      sphereTr.setScale(0.5);      
      sphereTGY.setTransform(sphereTr);
      
      //sphereTr = new Transform3D();
      //sphereTr.setScale(0.5);      
      OBJSphere objSphereZ = Object3DFactory.createSphere();
      TransformGroup sphereTGZ = Object3DFactory.create3DObject(objSphereZ, new Vector3d(0.0, 0.0, 1.5));
      sphereTGZ.getTransform(sphereTr);
      sphereTr.setScale(0.5);      
      sphereTGZ.setTransform(sphereTr);
      
      BranchGroup objComplex = new BranchGroup();
      
      objComplex.addChild(cubeTG);
//      objComplex.addChild(sphereTGX);
//      objComplex.addChild(sphereTGY);
//      objComplex.addChild(sphereTGZ);
      
      TransformGroup complexTG = Object3DFactory.create3DObject(objComplex, new Vector3d(0.0, 0.0, 0.0));

//      TGAssociation tgAssocTranslate = new TGAssociation(sphereTGX, complexTG, "TRANSLATE");
//      SGObjectInfo sphereXinfo = (SGObjectInfo) sphereTGX.getUserData();
//      sphereXinfo.registerTGAssociation(tgAssocTranslate);
//      sphereXinfo.setActuator(true);
//      sphereXinfo.setActuatorType(SGObjectInfo.ACT_TRANSLATE);
//      
//      TGAssociation tgAssocScale = new TGAssociation(sphereTGZ, complexTG, "SCALE");
//      SGObjectInfo sphereZinfo = (SGObjectInfo) sphereTGZ.getUserData();
//      sphereZinfo.registerTGAssociation(tgAssocScale);
//      sphereZinfo.setActuator(true);
//      sphereZinfo.setActuatorType(SGObjectInfo.ACT_SCALE);
//      
//      TGAssociation tgAssocRotate = new TGAssociation(sphereTGZ, complexTG, "SCALE");
//      SGObjectInfo sphereYinfo = (SGObjectInfo) sphereTGY.getUserData();
//      sphereYinfo.registerTGAssociation(tgAssocRotate);
//      sphereYinfo.setActuator(true);
//      sphereYinfo.setActuatorType(SGObjectInfo.ACT_ROTATE);
      
      
      //SGObjectInfo info = (SGObjectInfo) cubeTG.getUserData();
      //info.setSGName("OBJCUBE");
      SGObjectInfo info = (SGObjectInfo) complexTG.getUserData();
      info.setSGName("OBJCOMPLEX");
      
      //return cubeTG;
      
      return complexTG;
    }
  
    /**
     * Creates a new 3d object. It takes a vector3D as argument to
     * translate the object to a specific position.
     *
     * @param vector The translate vector.
     * @return TransformGroup The 3d object.
     */
    public TransformGroup createSphere(Vector3d vector) {
        // we will add plane sto that we can see what is happening with 
        // global and local transaltions
        OBJPlane objPlane = Object3DFactory.createPlane();
        TransformGroup planeTG = Object3DFactory.create3DObject(objPlane, new Vector3d(0.0, 0.0, 0.0));
        
        OBJSphere objSphere = Object3DFactory.createSphere();
        TransformGroup sphereTG = Object3DFactory.create3DObject(objSphere, new Vector3d(0.0, 2.0, 0.0));
        Transform3D sphereTr = new Transform3D();
        sphereTG.getTransform(sphereTr);
        sphereTr.setScale(0.5);      
        sphereTG.setTransform(sphereTr);        
        
        planeTG.addChild(sphereTG);
        
        return planeTG;
    }
  
    public TransformGroup createLine3d(Point3d A, Point3d B, Vector3d vector) {
        OBJLine3d objLine3d = Object3DFactory.createLine3d(A, B);
        return Object3DFactory.create3DObject(objLine3d, vector);
    }  
    /**
     * Creates a new plane. It takes a vector3D as argument to
     * translate the object to a specific position.
     *
     * @param vector The translate vector.
     * @return TransformGroup The 3d object.
     */
    public TransformGroup createPlane(Vector3d vector) {
        OBJPlane objPlane = Object3DFactory.createPlane();
        return Object3DFactory.create3DObject(objPlane, vector);
    }  
      
    /**
     * Returns the 3d objects at the specific coordinates.
     */
//    public PickRay createPickRay(int x, int y) {
//        return Object3DFactory.createPickRay(baseSceneGraph.getCanvas3D(), x, y);
//    }

    /**
     * Returns the closest intersection from the current scene.
     *
     * @param x Position on the screen.
     * @param y Position on the screen.
     * @return PickResult A PickResult containing the object from the 3d scene.
     */
    public PickResult getClosestIntersection(int x, int y) {
        return baseSceneGraph.getClosestIntersection(x, y);
    }

    /**
     * Reset the current view to its initial position.
     */
    public void resetViewPlatform() {
        baseSceneGraph.resetViewPlatform();
    }

    /**
     * Mark ViewPoint for the current (in focus) view.
     */
    public void setViewPoint() {
        baseSceneGraph.setViewPoint();
    }    
    
    /**
     * Copy View in focus.
     */
    public void copyView() {
        baseSceneGraph.copyView();
    }    
    
    /**
     * Copy View in focus.
     */
    public void changeFunctionDirection(int direction) {
        baseSceneGraph.changeFunctionDirection(direction);
    }
    
    /**
     * Calculates the translation, scale and rotation angles from the
     * given TransformGroup.
     *
     * @param transGrp The TransformGroup.
     * @param translation The translation vector. The found values are copied
     *                    in this vector.
     * @param scale The scale vector. The found values are copied
     *                    in this vector.
     * @param rotation The rotation angles. The found values are copied
     *                    in this vector.
     */
    public void calcObjInfo(TransformGroup transGrp, 
                            Vector3d translation, 
                            Vector3d scale, 
                            Vector3d rotation)
    {
        Transform3D trans = new Transform3D();
        transGrp.getTransform(trans);

        // get translation
        trans.get(translation);
        // get rotation
        Vector3d tmp = getRotationAngle(trans);
        rotation.set(Math.toDegrees(tmp.x), Math.toDegrees(tmp.y), Math.toDegrees(tmp.z));
        // get scale
        trans.getScale(scale);
      }
  
      /**
       * Get the rotation angles of the current transform3D. The returned
       * angles are in radians.
       *
       * @param t3d The current Transform3D from where to grab the angles.
       * @return Vector3d A vector containing the axes angles.
       */
      public Vector3d getRotationAngle(Transform3D t3d) {
        return Object3DFactory.getRotationAngle(t3d);
      }      
      
      public void pushUID(Long sgUID) {
          sgObjectStack.pushUID(sgUID);
      }
      
      public Long popUID() {
          return sgObjectStack.popUID();
      } 
      
      public Long peekUID() {
          return sgObjectStack.peekUID();
      }      
      
      public void removeUID(Long sgUID) {
          sgObjectStack.removeUID(sgUID);
      }      

    public void setToolRegistry(ToolRegistry toolRegistry) {
        this.toolRegistry = toolRegistry;
    }

    public ToolRegistry getToolRegistry() {
        return toolRegistry;
    }

//    public VToolMap getToolInstanceMap() {
//        return toolInstanceMap;
//    }
//
//    public void setToolInstanceMap(VToolMap toolInstanceMap) {
//        this.toolInstanceMap = toolInstanceMap;
//    }

//    public VToolInstanceRepository getToolInstanceRepository() {
//        return toolInstanceRepository;
//    }

//    public void setToolInstanceRepository(VToolInstanceRepository toolInstanceRepository) {
//        this.toolInstanceRepository = toolInstanceRepository;
//    }
    
    public View getViewInFocus() {
        return this.baseSceneGraph.getViewInFocus();
    }
    
    public VirToolMap getVirToolMap() {
        return virToolMap;
    }
    
    public VirToolStack getVirToolStack() {
        return virToolStack;
  }
  
}
