package org.sgJoe.graphics;

import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickResult;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.HashMap;
import java.util.Iterator;
import javax.media.j3d.*;

import javax.vecmath.*;


import org.apache.log4j.Logger;
import org.sgJoe.graphics.behaviors.*;
import org.sgJoe.graphics.primitives.OBJLine3d;
import org.sgJoe.graphics.primitives.utils.SGCylinderLine3D;
import org.sgJoe.gui.ViewPanel;
import org.sgJoe.logic.Controller;
import org.sgJoe.logic.SGObjectInfo;
import org.sgJoe.logic.ScenegraphSettings;
import org.sgJoe.tools.VToolFactory;

/**
 * This class represents the base scene graph. It contains a background
 * and a lot of behaviours for navigation. Some behaviours are added for
 * object manipulation. The basescene graph contains some elements which 
 * are not accessible like the behaviours, background and special effects
 * like fog.
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:            0.1         $
 * @date     $ Date:  December 6, 2005 6:34 PM  $
 */
public class BaseSceneGraph implements GraphicsConstants {
    
    private static Logger logger = Logger.getLogger(BaseSceneGraph.class);
    
    private SceneGraphEditor parentEditor =  null;
    private BehaviorObserver behaviorObserver = null;
   
    private Controller controller = Controller.getInstance();
    private int viewNumToAdd = 0;
    
    // sgJoe version of SimpleUniverse that allows multiple views
    private SGUniverse sgUniverse = null;
    
    
    // at this moment, during development, i'll handle just 4 view 
    private static Integer VIEW_M = new Integer(0);
    private static Integer VIEW_1 = new Integer(1);
    private static Integer VIEW_2 = new Integer(2);
    private static Integer VIEW_3 = new Integer(3);
    
    // the canvases of the four views
    private HashMap canvas3dMap = new HashMap();

    // the transformgroups of the four views
    // --> private HashMap transformGroupMap = new HashMap();

    // the transformgroups of the four views
    private HashMap orbitMap = new HashMap();
  
    private BranchGroup root = null;
    private BranchGroup objRoot = null;
    private BranchGroup objTop = null;
    private PickCanvas pickCanvas;
  
    private final BoundingSphere DEFAULT_BOUNDING_SPHERE;

    // Each behavior type has four instances, one for each view.
    // Only one behavior of one canvas can be active at a time, otherwise
    // they interfere with each other, resulting in strange occurences.
    // The array order is:
    // 0 perspective
    // 1 XY (front)
    // 2 XZ (top)
    // 3 YZ (left)
  
    //private CustomPickRotateBehavior[] pickRotateBehaviorArray = new CustomPickRotateBehavior[4];
    //private CustomPickSensorBehavior[] pickRotateBehaviorArray = new CustomPickSensorBehavior[4];
    // --> private CustomPickTranslateBehavior[] pickTranslateBehaviorArray = new CustomPickTranslateBehavior[4];  
    private CustomPickSensorBehavior[] pickTranslateBehaviorArray = new CustomPickSensorBehavior[4];  
    private PublishSubscribeBehavior[] publishSubscribeBehaviorArray = new PublishSubscribeBehavior[4];  
    // --> private CustomPickUniversalBehavior[] pickTranslateBehaviorArray = new CustomPickUniversalBehavior[4];  
    private CustomPickZoomBehavior pickZoomBehavior = null;
    // --> private CustomPickScaleBehavior[] pickScaleBehaviorArray = new CustomPickScaleBehavior[4];
    // --> private CustomPickPointBehavior[] pickPointBehaviorArray = new CustomPickPointBehavior[4];
    // --> private CustomPickDistanceBehavior[] pickDistanceBehaviorArray = new CustomPickDistanceBehavior[4];
    
    private int focused = 0; // which canvas is currently focused
    private int newlyFocused = -1; // which canvas is newly focused (switching to
                                   // this canvas as soon as mouse not dragged)
    private int enabledBehaviorType = -1; // which behavior type is enabled (mutually exclusive)
    private boolean navigationEnabled = false; // to determine whether the user is editing of navigation
    private boolean orbitNavigation = true; // to switch between the two different navigation modes
    private boolean draggingMouse = false; // used to disable focus switching when mouse is dragged

    private Background bg;
    
    void setParentEditor(SceneGraphEditor parent) {
        this.parentEditor = parent;
    }
      /** 
       * Creates a new instance of BaseSceneGraph. The scene graph can be configured
       * via the ScenegraphSettings object.
       *
       * @param canvas3dArray An array containing all canvases.
       * @param sgSetting The scene graph settings.
       */
    public BaseSceneGraph(Canvas3D[] canvas3dArray, ScenegraphSettings sgSettings) {
        logger.debug("create baseSceneGraph");
    
        DEFAULT_BOUNDING_SPHERE = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), sgSettings.getBoundingRadius());
    
        canvas3dMap.put(VIEW_M, canvas3dArray[PLANE_PERSPECTIVE]);
        canvas3dMap.put(VIEW_1, canvas3dArray[PLANE_XY]);
        canvas3dMap.put(VIEW_2, canvas3dArray[PLANE_XZ]);
        canvas3dMap.put(VIEW_3, canvas3dArray[PLANE_YZ]);
        
        // setup simple universe
        
        sgUniverse = new SGUniverse();
        sgUniverse.addViewingGraph(VIEW_M, (Canvas3D)canvas3dMap.get(VIEW_M));
//        sgUniverse.addViewingGraph(VIEW_1, (Canvas3D)canvas3dMap.get(VIEW_1));
//        sgUniverse.addViewingGraph(VIEW_2, (Canvas3D)canvas3dMap.get(VIEW_2));
//        sgUniverse.addViewingGraph(VIEW_3, (Canvas3D)canvas3dMap.get(VIEW_3));
        
        // set backClipDistance to see big scenarios
        sgUniverse.getViewer(VIEW_M).getView().setBackClipDistance(sgSettings.getBackClipDistance());
//        sgUniverse.getViewer(VIEW_1).getView().setBackClipDistance(sgSettings.getBackClipDistance());
//        sgUniverse.getViewer(VIEW_2).getView().setBackClipDistance(sgSettings.getBackClipDistance());
//        sgUniverse.getViewer(VIEW_3).getView().setBackClipDistance(sgSettings.getBackClipDistance());
    
        // get locale
        // --> Locale locale = simpleU.getLocale();
        Locale locale = sgUniverse.getLocale();
    
        // get the transformgroup
//        transformGroupMap.put(VIEW_M, sgUniverse.getViewingPlatform(VIEW_M).getViewPlatformTransform()); 
//        transformGroupMap.put(VIEW_1, sgUniverse.getViewingPlatform(VIEW_1).getViewPlatformTransform()); 
//        transformGroupMap.put(VIEW_2, sgUniverse.getViewingPlatform(VIEW_2).getViewPlatformTransform()); 
//        transformGroupMap.put(VIEW_3, sgUniverse.getViewingPlatform(VIEW_3).getViewPlatformTransform()); 
    
        // add the three additional views
        BranchGroup[] branchGroups = new BranchGroup[4];
    
        orbitSetUp(VIEW_M);
        
        // ---> CustomOrbitBehavior orbit = (CustomOrbitBehavior) orbitMap.get(VIEW_M);
        NavigateStudyPlaneBehavior orbit = (NavigateStudyPlaneBehavior) orbitMap.get(VIEW_M);
        
        // --> orbitSetUp(VIEW_1);
        // --> orbitSetUp(VIEW_2);
        // --> orbitSetUp(VIEW_3);
        
        // --> orbit.stdPrintInternalState();
        // --> System.out.println("-----------------------AFTER CREATION-----------------------");
                
        setDefaultsForOrbit(VIEW_M);
        // --> setDefaultsForOrbit(VIEW_1);
        // --> setDefaultsForOrbit(VIEW_2);
        // --> setDefaultsForOrbit(VIEW_3);
    
        // --> orbit.stdPrintInternalState();
        // --> System.out.println("------------------------AFTER SET DEFAULT-------------------------");
        
        setViewPoint(VIEW_M);
        
        // ----> SGBehaviorState bhvSta = orbit.getBehaviorState();
        // ----> orbit.setUserData(bhvSta);
        
        
        // --> setViewPoint(VIEW_1);
        // --> setViewPoint(VIEW_2);
        // --> setViewPoint(VIEW_3);
        
        // --> orbit.stdPrintInternalState();
        // --> System.out.println("------------------------AFTER SET VIEW-------------------------");
        
        // the root of everything
        root = new BranchGroup();
    
        bg = new Background();
        bg.setApplicationBounds(DEFAULT_BOUNDING_SPHERE);
        bg.setColor(BACKGROUND_COLOR);
        bg.setCapability(Background.ALLOW_COLOR_READ);
        bg.setCapability(Background.ALLOW_COLOR_WRITE);
        root.addChild(bg);
        
        objRoot = new BranchGroup();
        objRoot.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        objRoot.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
        objRoot.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);

        root.addChild(objRoot);
        
        for (int i = 0; i < 4; i++) 
        {
            canvas3dArray[i].addMouseListener(new MouseAdapter() 
            {
                public void mouseEntered(MouseEvent evt) 
                {
                    canvasFocusGained(evt);
                }

                public void mouseReleased(MouseEvent evt) 
                {
                    draggingMouse = false;
                    // switch focus if the mouse got into another canvas
                    // during the dragging motion
                    if (newlyFocused != -1) switchFocus();
                }
            });
      
          // the mouse motion listener is used to control the switching 
          // of the focus. This way, navigation and transformation controls still
          // work, even if the mouse cursor already left the focused canvas.
          // Only after the mouse button has been released, the focus switching
          // takes place.
          canvas3dArray[i].addMouseMotionListener(new MouseMotionAdapter() 
          {
              public void mouseDragged(MouseEvent evt) 
              {
                  draggingMouse = true;
              }
          });
      
          // -> pickRotateBehaviorArray[i] = new CustomPickRotateBehavior(this);
//          pickRotateBehaviorArray[i] = new CustomPickSensorBehavior(this);
//          pickRotateBehaviorArray[i].setSchedulingBounds(DEFAULT_BOUNDING_SPHERE);
//          pickRotateBehaviorArray[i].setEnable(false);
//          pickRotateBehaviorArray[i].setNavigationPlane(i);
//          objRoot.addChild(pickRotateBehaviorArray[i]);
     
          pickTranslateBehaviorArray[i] = new CustomPickSensorBehavior(this);
          // -->pickTranslateBehaviorArray[i] = new CustomPickTranslateBehavior(this);
          // -> pickTranslateBehaviorArray[i] = new CustomPickUniversalBehavior(this);
          pickTranslateBehaviorArray[i].setSchedulingBounds(DEFAULT_BOUNDING_SPHERE);
          pickTranslateBehaviorArray[i].setEnable(false);
          pickTranslateBehaviorArray[i].setNavigationPlane(0);
          objRoot.addChild(pickTranslateBehaviorArray[i]);

          publishSubscribeBehaviorArray[i] = new PublishSubscribeBehavior(this);
          publishSubscribeBehaviorArray[i].setSchedulingBounds(DEFAULT_BOUNDING_SPHERE);
          
          // watch for this ---- active from app strat 
          publishSubscribeBehaviorArray[i].setEnable(false);
          publishSubscribeBehaviorArray[i].setNavigationPlane(0);
          objRoot.addChild(publishSubscribeBehaviorArray[i]);          
          
//          pickScaleBehaviorArray[i] = new CustomPickScaleBehavior(this);
//          pickScaleBehaviorArray[i].setSchedulingBounds(DEFAULT_BOUNDING_SPHERE);
//          pickScaleBehaviorArray[i].setEnable(false);
//          pickScaleBehaviorArray[i].setNavigationPlane(0);
//          objRoot.addChild(pickScaleBehaviorArray[i]);
          
//          pickPointBehaviorArray[i] = new CustomPickPointBehavior(this);
//          pickPointBehaviorArray[i].setSchedulingBounds(DEFAULT_BOUNDING_SPHERE);
//          pickPointBehaviorArray[i].setEnable(false);
//          pickPointBehaviorArray[i].setNavigationPlane(0);
//          objRoot.addChild(pickPointBehaviorArray[i]);          
//
//          pickDistanceBehaviorArray[i] = new CustomPickDistanceBehavior(this);
//          pickDistanceBehaviorArray[i].setSchedulingBounds(DEFAULT_BOUNDING_SPHERE);
//          pickDistanceBehaviorArray[i].setEnable(false);
//          pickDistanceBehaviorArray[i].setNavigationPlane(0);
//          objRoot.addChild(pickDistanceBehaviorArray[i]);
        }
    
        // zoom behavior is for the perspective view only
        pickZoomBehavior = new CustomPickZoomBehavior(this);
        pickZoomBehavior.setSchedulingBounds(DEFAULT_BOUNDING_SPHERE);
        pickZoomBehavior.setEnable(false);
        objRoot.addChild(pickZoomBehavior);
        
        publishSubscribeBehaviorArray[this.PLANE_PERSPECTIVE].setEnable(true);
        
    
        objTop = new BranchGroup();
        VToolFactory.setBGCapabilities(objTop);
        
//        objTop.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
//        objTop.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
//        objTop.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
//        // can detach from its parent
//        objTop.setCapability(BranchGroup.ALLOW_DETACH);
//        objTop.setCapability(BranchGroup.ALLOW_PICKABLE_READ);
//        objTop.setCapability(BranchGroup.ALLOW_PICKABLE_WRITE);

        objRoot.addChild(objTop);

        locale.addBranchGraph(root); // add content branch
    }
  
      /**
       * Adds a behaviour observer for all behaviours in the scene graph.
       *
       * @param behaviourObserver The observer to add.
       */
      public void addBehaviourObserver(BehaviorObserver bo) {
          if(behaviorObserver == null) {
              behaviorObserver = bo;
          }
          for (int i = 0; i < pickTranslateBehaviorArray.length; i++) {
              pickTranslateBehaviorArray[i].addBehaviourObserver(bo);
              publishSubscribeBehaviorArray[i].addBehaviourObserver(bo);
              // --> pickRotateBehaviorArray[i].addBehaviourObserver(bo);
//              pickScaleBehaviorArray[i].addBehaviourObserver(bo);
//              pickPointBehaviorArray[i].addBehaviourObserver(bo);
//              pickDistanceBehaviorArray[i].addBehaviourObserver(bo);
          }
          pickZoomBehavior.addBehaviourObserver(bo);
      }
  
      /**
       * Enables the pick rotate behaviour.
       *
       * @param enable <code>true</code> enables the pick rotate behaviour,
       *               <code>false</code> else.
       */
      public void enablePickRotateBehavior(boolean enable) {
          // pickRotateBehavior.setEnable(enable);
          // --> pickRotateBehaviorArray[focused].setEnable(enable);
          if (enable) {
              logger.debug("ENABLED set to ROTATE  " + focused);
              enabledBehaviorType = BEHAVIOR_PICK_ROTATE;
          } else {
              if (enabledBehaviorType == BEHAVIOR_PICK_ROTATE) enabledBehaviorType = BEHAVIOR_NONE;
          }
      }
  
      /**
       * Enables the pick zoom/translate behaviour.
       *
       * @param enable <code>true</code> enables the pick zoom behaviour,
       *               <code>false</code> else.
       */
      public void enablePickMoveBehavior(boolean enable) {
          pickTranslateBehaviorArray[focused].setEnable(enable);
          pickTranslateBehaviorArray[focused].reset();
        
          if (focused == PLANE_PERSPECTIVE) pickZoomBehavior.setEnable(enable);
          
          if (enable) {
              logger.debug("ENABLED set to MOVE  " + focused);
              enabledBehaviorType = BEHAVIOR_PICK_MOVE;
          } else {
              if (enabledBehaviorType == BEHAVIOR_PICK_MOVE) enabledBehaviorType = BEHAVIOR_NONE;
          }
      }
  
      /**
       * Enables the pick scale behaviour.
       *
       * @param enable <code>true</code> enables the pick scale behaviour,
       *               <code>false</code> else.
       */
      public void enablePickScaleBehavior(boolean enable) {
//          pickScaleBehaviorArray[focused].setEnable(enable);
//          if (enable) {
//              logger.debug("ENABLED set to SCALE  " + focused);
//              enabledBehaviorType = BEHAVIOR_PICK_SCALE;
//          } else {
//              if (enabledBehaviorType == BEHAVIOR_PICK_SCALE) enabledBehaviorType = BEHAVIOR_NONE;
//          }
      }
  
      /**
       * Enables the pick point behaviour.
       *
       * @param enable <code>true</code> enables the pick scale behaviour,
       *               <code>false</code> else.
       */
      public void enablePickPointBehavior(boolean enable) {
//          pickPointBehaviorArray[focused].setEnable(enable);
//          if (enable) {
//              logger.debug("ENABLED set to PICK_POINT  " + focused);
//              enabledBehaviorType = BEHAVIOR_PICK_POINT;
//          } else {
//              if (enabledBehaviorType == BEHAVIOR_PICK_POINT) enabledBehaviorType = BEHAVIOR_NONE;
//          }
      }      
      
      /**
       * Enables the distance behaviour.
       *
       * @param enable <code>true</code> enables the pick scale behaviour,
       *               <code>false</code> else.
       */
      public void enablePickDistanceBehavior(boolean enable) {
//          pickDistanceBehaviorArray[focused].setEnable(enable);
//          if (enable) {
//              logger.debug("ENABLED set to DISTANCE  " + focused);
//              enabledBehaviorType = BEHAVIOR_PICK_DISTANCE;
//          } else {
//              if (enabledBehaviorType == BEHAVIOR_PICK_DISTANCE) enabledBehaviorType = BEHAVIOR_NONE;
//          }
      }      
 
    /**
     * Returns the perspective canvas3D.
     *
     * @return Canvas3D The canvas3d.
     */
//    public Canvas3D getCanvas3D() {
//        return canvas3dArray[PLANE_PERSPECTIVE];
//    }
  
    /**
     * Returns the XZ canvas3d (top).
     *
     * @return Canvas3D The XZ canvas3d.
     */
//    public Canvas3D getCanvasXZ() {
//        return canvas3dArray[PLANE_XZ];
//    }
  
    /**
     * Returns the XY canvas3d (front).
     *
     * @return Canvas3D The XY canvas3d.
     */
//    public Canvas3D getCanvasXY() {
//        return canvas3dArray[PLANE_XY];
//    }
  
    /**
     * Returns the YZ canvas3d (right(?)).
     *
     * @return Canvas3D The YZ canvas3d.
     */
//    public Canvas3D getCanvasYZ() {
//        return canvas3dArray[PLANE_YZ];
//    }
  
    /**
     * Returns the object root. Remeber that all nodes are added to the
     * objTop.
     *
     * @return BranchGroup Returns the obj root.
     */
    public BranchGroup getObjRoot() {
        return objRoot;
    }
  
    /**
     * Returns the object top, this is where all the generated nodes
     * are added and manipulated. The object top is directly added
     * to the object root. If we manipulate the scene graph, we have
     * to detach the object top from the object root. This guarantes
     * that the scene graph is no more live.
     *
     * @return BranchGroup Returns the obj top.
     */
    public BranchGroup getObjTop() {
        return objTop;
    }
  
    /**
     * Detach the object top from the scene graph. This method has to be
     * called before every manipulation on the scene graph. If the scene
     * graph is detached from the object root, the scene graph is no more
     * visible to the client.
     */
    public void detachTopFromRoot() {
        synchronized(objTop) {
            objTop.detach();
        }
    }
  
    /**
     * Attaches the scene graph to the object root, so it will come to
     * live. This has to be done after a manipulation on the scene graph.
     * This makes the scene graph visible to the client.
     */
    public void attachTopToRoot() {
        objRoot.addChild(objTop);
    }
  
    /**
     * Sets the top of the scene graph new. This means, the old
     * scene get deleted and the new scene is shown. After this,
     * the old scene can not be accessed again.
     *
     * @param newObjTop The top of the new scene graph to set.
     */
    public void setNewObjTop(BranchGroup newObjTop) {
      // set capabilities for the reference
      newObjTop.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
      newObjTop.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
      newObjTop.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
      // can detach from it's parent
      newObjTop.setCapability(BranchGroup.ALLOW_DETACH);
      newObjTop.setCapability(BranchGroup.ALLOW_PICKABLE_READ);
      newObjTop.setCapability(BranchGroup.ALLOW_PICKABLE_WRITE);
  
      // detach old from scene graph
      objTop.detach();
      // add new one to scene graph
      objRoot.addChild(newObjTop);
      // set reference new
      objTop = newObjTop;
    }
  
    /**
     * Sets the orbit navigation mode.
     *
     * @param orbitNav <code>true</code> activates the orbit navigation mode,
     *                 <code>false</code> deactivates the orbit navigation mode.
     */
    public void setNavigationMode(boolean orbitNav) {
        orbitNavigation = orbitNav;

        if (navigationEnabled) {
            // ---> CustomOrbitBehavior orbit = null;
            // ---> orbit = (CustomOrbitBehavior)orbitMap.get(VIEW_M);
            // ---> orbit.setEnable(orbitNavigation);
            // ---> orbit = (CustomOrbitBehavior)orbitMap.get(VIEW_1);
            // ---> orbit.setEnable(orbitNavigation);
            // ---> orbit = (CustomOrbitBehavior)orbitMap.get(VIEW_2);
            // ---> orbit.setEnable(orbitNavigation);              
            // ---> orbit = (CustomOrbitBehavior)orbitMap.get(VIEW_3);
            // ---> orbit.setEnable(orbitNavigation);          
            
            NavigateStudyPlaneBehavior orbit = null;
            orbit = (NavigateStudyPlaneBehavior)orbitMap.get(VIEW_M);
            orbit.setEnable(orbitNavigation);
            orbit = (NavigateStudyPlaneBehavior)orbitMap.get(VIEW_1);
            orbit.setEnable(orbitNavigation);
            orbit = (NavigateStudyPlaneBehavior)orbitMap.get(VIEW_2);
            orbit.setEnable(orbitNavigation);              
            orbit = (NavigateStudyPlaneBehavior)orbitMap.get(VIEW_3);
            orbit.setEnable(orbitNavigation);                        

            logger.debug("First Person navigation not yet implemented !!!");
        }
          
        // if user changed from first person back to orbit, reset view
        if (orbitNavigation) {
            resetViewPlatform();
        }
    }
  
      /**
       * Enables the orbit behaviour for this scene graph.
       *
       * @param enable <code>true</code> enables the orbit behaviour,
       *               <code>false</code> else.
       */
      public void enableOrbitBehaviour(boolean enable) 
      {
          navigationEnabled = enable;

            // check which navigation mode is on
            if (orbitNavigation) 
            {
              // ---> CustomOrbitBehavior orbit = null;
              NavigateStudyPlaneBehavior orbit = null;
              Object key = null;
              Iterator iT = this.orbitMap.keySet().iterator();
              while(iT.hasNext()) 
              {
                  key = iT.next();
                  // ---> orbit = (CustomOrbitBehavior) orbitMap.get(key);
                  orbit = (NavigateStudyPlaneBehavior) orbitMap.get(key);
                  orbit.setEnable(enable);
              }
              
            } else {
              logger.debug("First Person navigation not yet implemented !!!");
            }

            if (focused != PLANE_PERSPECTIVE) { 
                // null pointer for perspective focus
                logger.debug("First Person navigation not yet implemented !!!");
            }
      }

      /**
       * Enables the pick behaviour for this scene graph and all subgraphs
       * included.
       *
       * @param enable <code>true</code> enables the pick behaviour,
       *               <code>false</code> else.
       */
      public void enablePickBehaviour(boolean enable) 
      {
          // we have to check if we have some children, else we trap
          // in a null-pointer-exception :-(
          if (objTop.numChildren() > 0) 
          {
              // we can just catch the exception and all works fine. seems to be
              // some sort from the java3d
              try 
              {
                  objTop.setPickable(enable);
              } catch (java.lang.IndexOutOfBoundsException e) {
                  logger.error(e);
              }
          }
      }
  
      /**
       * Resets the view back to its original position.
       */
      public void resetViewPlatform() {
          
          //setDefaultsForOrbit(new Integer(focused));
          Integer key = new Integer(focused);
          
          Transform3D tr3d = sgUniverse.getViewingPlatform(key).getCheckPoint();
          
          sgUniverse.getViewingPlatform(key).getViewPlatformTransform().setTransform(tr3d);
          
          // ----- new approach -----
          CustomOrbitBehavior orbit = (CustomOrbitBehavior) orbitMap.get(key);
          SGBehaviorState prevState = (SGBehaviorState) orbit.getUserData();
          orbit.setBehaviorSate(prevState);
          
          
//          if(focused == 0) {
//              // Matrix to rotate the x axis
//              Matrix3d rotXMatrix = new Matrix3d();
//              rotXMatrix.setIdentity();
//              rotXMatrix.rotX(- Math.PI / 8.0);
//
//              // Matrix to rotate the y axis
//              Matrix3d rotYMatrix = new Matrix3d();
//              rotYMatrix.setIdentity();
//              rotYMatrix.rotY(Math.PI / 4.0);
//
//              orbit.setFocusPoint(new Vector3f(0.0f, 0.0f, 0.0f));
//              orbit.setFocusDistance(20);
//
//              orbit.setXAngle(- Math.PI / 8.0);
//              orbit.setYAngle(Math.PI / 4.0);              
//            
//              // Vector to translate to the screen center
//              Vector3d translation = new Vector3d(13.0, 7.654, 13.0);
//
//              // The Transform3D object for our view
//              Transform3D viewTransform = new Transform3D();
//
//              // Combine the rotations and the translation
//              Matrix4d m1 = new Matrix4d();
//              m1.setIdentity();
//              m1.setRotation(rotXMatrix);
//              Matrix4d m2 = new Matrix4d();
//              m2.setIdentity();
//              m2.setRotation(rotYMatrix);
//              m2.setTranslation(translation);
//              viewTransform.mul(new Transform3D(m2), new Transform3D(m1));            
//              
//              tgArray[PLANE_PERSPECTIVE].setTransform(viewTransform);
//          } else 
//          if(focused == 1) {
//              orbitXY.setFocusPoint(new Vector3f(0.0f, 0.0f, 0.0f));
//              orbitXY.setFocusDistance(20);
//              
//              Transform3D viewXYTransform = new Transform3D();
//              Vector3f viewLocation = new Vector3f(0.0f, 0.0f, 20.0f);
//              viewXYTransform.set(viewLocation);
//              tgArray[PLANE_XY].setTransform(viewXYTransform);
//          } else if(focused == 2) {
//              Point3d center = new Point3d(0, 0, 0);
//              Vector3d up = new Vector3d(0, 0, 1);              
//              orbitXZ.setFocusPoint(new Vector3f(0.0f, 0.0f, 0.0f));
//              orbitXZ.setFocusDistance(20);
//              
//              Transform3D viewXZTransform = new Transform3D();
//              Vector3f  viewLocation = new Vector3f(0.0f, 20.0f, 0.0f);
//              Point3d v = new Point3d(viewLocation.x, viewLocation.y, viewLocation.z);
//              viewXZTransform.lookAt(v, center, up);
//              viewXZTransform.invert();
//              // turn the view direction 90 degrees down
//              viewXZTransform.setRotation(new AxisAngle4f(-1.0f, 0.0f, 0.0f, (float)Math.PI / 2.0f));                                           
//              
//              tgArray[PLANE_XZ].setTransform(viewXZTransform);
//          }
      }
  
      /**
       * Returns the closest intersection from the current scene.
       *
       * @param x Position on the screen.
       * @param y Position on the screen.
       * @return PickResult A PickResult containing the object from the 3d scene.
       */
      public PickResult getClosestIntersection(int x, int y) 
      {
          // TODO: Check if viable
          Canvas3D canvas3d = (Canvas3D) canvas3dMap.get(new Integer(focused));
          PickCanvas pickCanvas = new PickCanvas(canvas3d, objTop); 
          pickCanvas.setMode(PickCanvas.GEOMETRY_INTERSECT_INFO);
          pickCanvas.setTolerance(0.0f);
          pickCanvas.setShapeLocation(x, y);

          PickResult pickResult = pickCanvas.pickClosest();
          return pickResult;
      }
  
      
      public PickResult[] getAllSorted(int x, int y) {
          // TODO: Check if viable
          Canvas3D canvas3d = (Canvas3D) canvas3dMap.get(new Integer(focused));
          PickCanvas pickCanvas = new PickCanvas(canvas3d, objTop); 
          pickCanvas.setMode(PickCanvas.GEOMETRY_INTERSECT_INFO);
          pickCanvas.setTolerance(0.0f);
          pickCanvas.setShapeLocation(x, y);

          PickResult[] pickResult = pickCanvas.pickAllSorted();
          return pickResult;
      }      
      /**
       * helper method responsible to create the four views
       * the viewLocation defines the visual depth of the scene and when objects
       * will disappear behind the viewer. Since the three views are non-perspective,
       * this has no influence on the field of view. To alter the "panoramic" view
       * (horizontal as well as vertical), the viewplatform has to be scaled.
       */
//      private BranchGroup createViewplatformGraph(int plane) {
//          
//          Canvas3D canvas = canvas3dArray[plane];
//          View view;
//          BranchGroup vpRoot;
//          ViewPlatform vp;
//          PhysicalBody body = new PhysicalBody();
//          PhysicalEnvironment environment = new PhysicalEnvironment();
//          Vector3d up = null;
//          Point3d center;
//          Point3d v;
//
//          view = new View();
//          view.addCanvas3D(canvas);
//          view.setPhysicalBody(body);
//          view.setPhysicalEnvironment(environment);
//          view.setProjectionPolicy(View.PARALLEL_PROJECTION);
//
//          vpRoot = new BranchGroup();
//          vpRoot.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
//
//          Transform3D transform = createPlaneTransform(plane);
//
//          vp = new ViewPlatform();
//          // note that perspective never calls this method,
//          // thus tgArray[PLANE_PERSPECTIVE] has to be set separately
//          tgArray[plane] = new TransformGroup(transform);
//          tgArray[plane].setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
//          tgArray[plane].setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
//          tgArray[plane].addChild(vp);
//          vpRoot.addChild(tgArray[plane]);
//          view.attachViewPlatform(vp);
//          return vpRoot;
//      }
  
      /**
       * Creates a plane transform3d. This trasform3d is used to make
       * operations in either the xy, xz or yz - plane. Permited values
       * are:
       * <br><br>
       * <code>PLANE_XY</code><br>
       * <code>PLANE_XZ</code><br>
       * <code>PLANE_YZ</code><br><br>
       * These values are defined in GraphicConstants.
       *
       * @param plane The type of transform3d to create.
       */
      private Transform3D createPlaneTransform(int plane) {
        Transform3D transform = new Transform3D();
        Vector3f viewLocation = null;
        Point3d center = new Point3d(0, 0, 0);
        Vector3d up = new Vector3d(0, 0, 1);
        Point3d v;

        switch (plane) {
          case PLANE_XY:
            viewLocation = new Vector3f(0.0f, 0.0f, 40.0f);
            transform.set(viewLocation);
            break;

          case PLANE_XZ:
            viewLocation = new Vector3f(0.0f, 40.0f, 0.0f);
            v = new Point3d(viewLocation.x, viewLocation.y, viewLocation.z);
            transform.lookAt(v, center, up);
            transform.invert();
            // turn the view direction 90 degrees down
            transform.setRotation(new AxisAngle4f(-1.0f, 0.0f, 0.0f, (float)Math.PI / 2.0f));
            break;

          case PLANE_YZ:
            viewLocation = new Vector3f(-40.0f, 0.0f, 0.0f);
            v = new Point3d(viewLocation.x, viewLocation.y, viewLocation.z);
            transform.lookAt(v, center, up);
            transform.invert();
            // turn the view 90 degrees to the left
            transform.setRotation(new AxisAngle4f(0.0f, -1.0f, 0.0f, (float)Math.PI / 2.0f));
            break; 
        }

        transform.setScale(8.0d); // increase field of view
        return transform;
      }

  
      /**
       * If a behavior type is enabled, do the following:<br>
       * - disable all behaviors of this type<br>
       * - enable the behavior type for the focused canvas
       * <br>
       * @param evt The current mouse event.
       */
      private void canvasFocusGained(MouseEvent evt) 
      {
          // get the focused canvas
            Canvas3D focusedCanvas3D = (Canvas3D)evt.getSource();

            for (int i = 0; i < 4; i++) 
            {
                Canvas3D canvas3d = (Canvas3D) canvas3dMap.get(new Integer(i));
                if (focusedCanvas3D.equals(canvas3d)) 
                {
                    newlyFocused = i;
                }
            }

            // only switch the focus if mouse is not being dragged
            if (!draggingMouse) 
            {
                switchFocus();
            }
      }    
      
      /**
       * Switch focus.
       */
      private void switchFocus() {
          // disable all behaviors of the formerly focused canvas (sledgehammer method)
          // --> pickRotateBehaviorArray[focused].reset();
          // --> pickRotateBehaviorArray[focused].setEnable(false);
          
          pickZoomBehavior.reset();
          pickZoomBehavior.setEnable(false);
        
          pickTranslateBehaviorArray[focused].reset();
          pickTranslateBehaviorArray[focused].setEnable(false);
        
          publishSubscribeBehaviorArray[focused].reset();
          publishSubscribeBehaviorArray[focused].setEnable(false);
          
//          pickScaleBehaviorArray[focused].setEnable(false);
//          
//          pickPointBehaviorArray[focused].reset();
//          pickPointBehaviorArray[focused].setEnable(false);
//
//          pickDistanceBehaviorArray[focused].reset();
//          pickDistanceBehaviorArray[focused].setEnable(false);
          
          if ((focused != PLANE_PERSPECTIVE) && (navigationEnabled)) {
              logger.debug("First Person navigation not yet implemented !!!");
          }

          focused = newlyFocused;
          newlyFocused = -1;

          if ((navigationEnabled) && (focused != PLANE_PERSPECTIVE)) {
              logger.debug("First Person navigation not yet implemented !!!");
          }

          // always enable publish-subscribe mechanism
          publishSubscribeBehaviorArray[focused].setEnable(true);
          publishSubscribeBehaviorArray[focused].reset();          
          // enable behavior for focused canvas (still sledgehammer method)
          if (enabledBehaviorType != BEHAVIOR_NONE) {
              switch(enabledBehaviorType) {
                case BEHAVIOR_PICK_ROTATE:
                  // --> pickRotateBehaviorArray[focused].setEnable(true);
                  break;
                case BEHAVIOR_PICK_MOVE:
                  pickTranslateBehaviorArray[focused].setEnable(true);
                  pickTranslateBehaviorArray[focused].reset();
                  if (focused == PLANE_PERSPECTIVE) pickZoomBehavior.setEnable(true);
                  break;
//                case BEHAVIOR_PICK_SCALE:
//                  pickScaleBehaviorArray[focused].setEnable(true);
//                  break;
//                case BEHAVIOR_PICK_POINT:
//                  pickPointBehaviorArray[focused].setEnable(true);
//                  break;
//                case BEHAVIOR_PICK_DISTANCE:
//                  pickDistanceBehaviorArray[focused].setEnable(true);
//                  break;                  
             }
          }
      }

      public View getViewInFocus() {
          Integer canvasKey = new Integer(this.focused);
          Canvas3D canvasInFocus = (Canvas3D) this.canvas3dMap.get(canvasKey);
          return canvasInFocus.getView();
      }
      
      public void setDefaultForViewPlatform(Object key) {
              // Matrix to rotate the x axis
//              Matrix3d rotXMatrix = new Matrix3d();
//              rotXMatrix.setIdentity();
//              rotXMatrix.rotX(- Math.PI / 8.0);
//
//              // Matrix to rotate the y axis
//              Matrix3d rotYMatrix = new Matrix3d();
//              rotYMatrix.setIdentity();
//              rotYMatrix.rotY(Math.PI / 4.0);
//
//              orbit.setFocusPoint(new Vector3f(0.0f, 0.0f, 0.0f));
//              orbit.setFocusDistance(20);
//
//              orbit.setXAngle(- Math.PI / 8.0);
//              orbit.setYAngle(Math.PI / 4.0);
//
//              orbitXY.setFocusPoint(new Vector3f(0.0f, 0.0f, 0.0f));
//              orbitXY.setFocusDistance(20);
//              

//              orbitXY.setXAngle(- Math.PI / 8.0);
//              orbitXY.setYAngle(Math.PI / 4.0);
          
//              // Vector to translate to the screen center
//              Vector3d translationPer = new Vector3d(13.0, 7.654, 13.0);
//
//              // The Transform3D object for our view
//              Transform3D viewTransformPer = new Transform3D();

//              // Combine the rotations and the translation
//              Matrix4d m1 = new Matrix4d();
//              m1.setIdentity();
//              m1.setRotation(rotXMatrix);
//              Matrix4d m2 = new Matrix4d();
//              m2.setIdentity();
//              m2.setRotation(rotYMatrix);
//              m2.setTranslation(translationPer);
//              viewTransformPer.mul(new Transform3D(m2), new Transform3D(m1));

              // Transform the view
              // TransformGroup viewTransformGroup = simpleU.getViewingPlatform().getViewPlatformTransform();
//              tgArray[PLANE_PERSPECTIVE].setTransform(viewTransformPer);
          
//              Transform3D viewXYTransform = new Transform3D();
//              Vector3f viewLocation = new Vector3f(0.0f, 0.0f, 20.0f);
//              viewXYTransform.set(viewLocation);
//              tgArray[PLANE_XY].setTransform(viewXYTransform);      
//              
//              Point3d center = new Point3d(0, 0, 0);
//              Vector3d up = new Vector3d(0, 0, 1);              
//              
//              orbitXZ.setFocusPoint(new Vector3f(0.0f, 0.0f, 0.0f));
//              orbitXZ.setFocusDistance(20);
//              
//              orbitXZ.setXAngle(- Math.PI / 8.0);
//              orbitXZ.setYAngle(Math.PI / 4.0);
//              
//              Transform3D viewXZTransform = new Transform3D();
//              Vector3d  viewLocationXZ = new Vector3d(0.0f, 20.0f, 0.0f);
//              //Point3d v = new Point3d(viewLocationXZ.x, viewLocationXZ.y, viewLocationXZ.z);
//              //viewXZTransform.lookAt(v, center, up);
//              //viewXZTransform.invert();
//              // turn the view direction 90 degrees down
//              viewXYTransform.set(viewLocationXZ);
//              //viewXZTransform.setRotation(new AxisAngle4f(-1.0f, 0.0f, 0.0f, (float)Math.PI / 2.0f));                                           
//                           
//              
//              tgArray[PLANE_XZ].setTransform(viewXZTransform);       
//
//          // Transform XZ view (top)
//          for (int i = 3; i < 4; i++) {
//              tgArray[i].setTransform(createPlaneTransform(i));
//          }          
      }
      
      public void orbitSetUpCopy(Object orgKey, Object newKey) 
      {          
          // ---> CustomOrbitBehavior orbitNew = null;
          NavigateStudyPlaneBehavior orbitNew = null;
          SGViewingPlatform viewingPlatformNew = sgUniverse.getViewingPlatform(newKey);
          Canvas3D canvas3dNew = (Canvas3D) canvas3dMap.get(newKey);
            
          if(orgKey == null) 
          {
              orbitNew = new NavigateStudyPlaneBehavior(canvas3dNew);//new CustomOrbitBehavior(canvas3dNew);
              orbitNew.setView(canvas3dNew.getView());
          } else {
              // ---> CustomOrbitBehavior orbitOrg = (CustomOrbitBehavior) orbitMap.get(orgKey);
              // ---> orbitNew = orbitOrg.createNewBehaviourObjectAndCopyInternalState(canvas3dNew);
          }
        
          orbitNew.importTransformGroup(viewingPlatformNew.getViewPlatformTransform());
          orbitNew.setSchedulingBounds(DEFAULT_BOUNDING_SPHERE);
          BranchGroup orbitBG = new BranchGroup();
          orbitBG.addChild(orbitNew);
          viewingPlatformNew.addChild(orbitBG);
          orbitNew.setEnable(false);
        
          orbitMap.put(newKey, orbitNew);
      }
      
      public void orbitSetUp(Object newKey) {
          orbitSetUpCopy(null, newKey);
      }      
      
      
      public void setDefaultsForOrbit(Object key) {
          // Matrix to rotate the x axis
          Matrix3d rotXMatrix = new Matrix3d();
          rotXMatrix.setIdentity();
          //rotXMatrix.rotX(- Math.PI / 8.0);
          rotXMatrix.rotX(0.0);

          // Matrix to rotate the y axis
          Matrix3d rotYMatrix = new Matrix3d();
          rotYMatrix.setIdentity();
          // rotYMatrix.rotY(Math.PI / 4.0);
          rotYMatrix.rotY(0.0);

          // ---> CustomOrbitBehavior orbit = (CustomOrbitBehavior) orbitMap.get(key);
          NavigateStudyPlaneBehavior orbit = (NavigateStudyPlaneBehavior) orbitMap.get(key);
          orbit.setFocusPoint(new Vector3f(0.0f, 0.0f, 0.0f));
          orbit.setFocusDistance(20);

          orbit.setXAngle(0.0);
          orbit.setYAngle(0.0);
          
          //Vector to translate to the screen center
          Vector3d translation = new Vector3d(0.0, 0.0, 20.0);
          //Vector3d translation = new Vector3d(0.0, 0.0, -20);

          // The Transform3D object for our view
          Transform3D viewTransform = new Transform3D();

          // Combine the rotations and the translation
          Matrix4d m1 = new Matrix4d();
          m1.setIdentity();
          m1.setRotation(rotXMatrix);
          Matrix4d m2 = new Matrix4d();
          m2.setIdentity();
          m2.setRotation(rotYMatrix);
          m2.setTranslation(translation);
          viewTransform.mul(new Transform3D(m2), new Transform3D(m1));

          // Transform the view
          sgUniverse.getViewingPlatform(key).getViewPlatformTransform().setTransform(viewTransform);
                        
      }
      
      public void setViewPoint() {
          System.out.println("BaseSceneGraph.setVP");
          Integer key = new Integer(focused);
          Transform3D tr3d = new Transform3D();
          sgUniverse.getViewingPlatform(key).getViewPlatformTransform().getTransform(tr3d);
          sgUniverse.getViewingPlatform(key).setCheckPoint(tr3d);
          
          // ----- new apporach -----
          // ---> CustomOrbitBehavior orbit = (CustomOrbitBehavior) orbitMap.get(key);
          NavigateStudyPlaneBehavior orbit = (NavigateStudyPlaneBehavior) orbitMap.get(key);
          SGBehaviorState bhvSta = orbit.getBehaviorState();
          orbit.setUserData(bhvSta);
      }
      
      public void setViewPoint(Object key) {
          System.out.println("BaseSceneGraph.setVP");
          Transform3D tr3d = new Transform3D();
          sgUniverse.getViewingPlatform(key).getViewPlatformTransform().getTransform(tr3d);
          sgUniverse.getViewingPlatform(key).setCheckPoint(tr3d);
          
          // ----- new apporach -----
          // ---> CustomOrbitBehavior orbit = (CustomOrbitBehavior) orbitMap.get(key);
          NavigateStudyPlaneBehavior orbit = (NavigateStudyPlaneBehavior) orbitMap.get(key);
          SGBehaviorState bhvSta = orbit.getBehaviorState();
          orbit.setUserData(bhvSta);          
      }
      
      /**
       * Copy View in focus.
       */
      public void copyView() 
      {
          ViewPanel vPanel = (ViewPanel)controller.getWindowManager().getMainWindow().getsgJPanel("viewPanel");
          ++viewNumToAdd;
          if(viewNumToAdd > 3) 
          {
              // currently we can add only 4 views
              return;
          } else {
              Integer focusedKey = new Integer(focused);
              View focusedView = sgUniverse.getViewer(focusedKey).getView();
              Integer viewToAddKey = new Integer(viewNumToAdd);
              Transform3D tr3d = new Transform3D();
              sgUniverse.addViewingGraph(viewToAddKey, (Canvas3D)canvas3dMap.get(viewToAddKey));              
              sgUniverse.getViewingPlatform(focusedKey).getViewPlatformTransform().getTransform(tr3d);
              sgUniverse.getViewer(viewToAddKey).getView().setBackClipDistance(focusedView.getBackClipDistance());
              
              sgUniverse.getViewingPlatform(viewToAddKey).getViewPlatformTransform().setTransform(tr3d);
              
              //setDefaultsForOrbit(viewToAddKey);              
              
              // --> orbitSetUpCopy(focusedKey, viewToAddKey);
              orbitSetUpCopy(null, viewToAddKey);
              
              setViewPoint(viewToAddKey);
//              CustomOrbitBehavior orbit = (CustomOrbitBehavior) orbitMap.get(viewToAddKey);
//              CustomOrbitBehavior fromOrbit = (CustomOrbitBehavior) orbitMap.get(focusedKey);
              
              NavigateStudyPlaneBehavior orbit = (NavigateStudyPlaneBehavior) orbitMap.get(viewToAddKey);
              NavigateStudyPlaneBehavior fromOrbit = (NavigateStudyPlaneBehavior) orbitMap.get(focusedKey);
              
              orbit.copyStateFrom(fromOrbit);
              
//              System.out.println("-------------------CREATED VIEW---------------");
//              orbit.stdPrintInternalState();
              
              vPanel.addView(viewNumToAdd);
          }
          
      }     
      
      public void changeFunctionDirection(int direction) {
          pickTranslateBehaviorArray[0].setDirection(direction);
          pickTranslateBehaviorArray[1].setDirection(direction);
          pickTranslateBehaviorArray[2].setDirection(direction);
          pickTranslateBehaviorArray[3].setDirection(direction);
          
//          pickScaleBehaviorArray[0].setDirection(direction);
//          pickScaleBehaviorArray[1].setDirection(direction);
//          pickScaleBehaviorArray[2].setDirection(direction);
//          pickScaleBehaviorArray[3].setDirection(direction);
          
          // --> pickRotateBehaviorArray[0].setDirection(direction);
          // --> pickRotateBehaviorArray[1].setDirection(direction);
          // --> pickRotateBehaviorArray[2].setDirection(direction);
          // --> pickRotateBehaviorArray[3].setDirection(direction);   
          
          // does nothing for pickpoint 
//          pickPointBehaviorArray[0].setDirection(direction);
//          pickPointBehaviorArray[1].setDirection(direction);
//          pickPointBehaviorArray[2].setDirection(direction);
//          pickPointBehaviorArray[3].setDirection(direction);          
          
          // does nothing for distance 
//          pickDistanceBehaviorArray[0].setDirection(direction);
//          pickDistanceBehaviorArray[1].setDirection(direction);
//          pickDistanceBehaviorArray[2].setDirection(direction);
//          pickDistanceBehaviorArray[3].setDirection(direction);          
      }
      
    public void createLine3d(Point3d A, Point3d B, Vector3d vector) {
        OBJLine3d objLine3d = Object3DFactory.createLine3d(A, B);
        TransformGroup objTransGrp = Object3DFactory.create3DObject(objLine3d, vector);
        SGObjectInfo info = (SGObjectInfo) objTransGrp.getUserData();
        info.setSGName("CYLINDERLINE3D");
          
        // add primitive to the scene.
        getParentEditor().setCapabilities(objTransGrp);
        getParentEditor().addChild(objTransGrp);
        
        System.out.println("[CREATE]" + info);
    }        
    
    public void createCylinderLine3D(Point3d A, Point3d B, double radius, Vector3d vector) {
        SGCylinderLine3D creator = new SGCylinderLine3D();
        Appearance cylApp = new Appearance();
        ColoringAttributes ca = new ColoringAttributes();
        ca.setColor(new Color3f(1, 0, 0));
        cylApp.setColoringAttributes(ca);
        creator.create(A, B, radius, cylApp);
        BranchGroup objCylinderLine3D = creator.getOBJCylinderLine3D();
        TransformGroup objTransGrp = Object3DFactory.create3DObject(objCylinderLine3D, vector);
          
        // add primitive to the scene.
        getParentEditor().setCapabilities(objTransGrp);
        
        objTransGrp.setTransform(creator.getTransform());

        SGObjectInfo info = (SGObjectInfo) objTransGrp.getUserData();
        info.setSGName("CYLINDERLINE3D");
        
        getParentEditor().addChild(objTransGrp);
             
//        System.out.println("[CREATE]" + info);
    }    
    
      public void pushUID(Long sgUID) {
          getParentEditor().pushUID(sgUID);
      }
      
      public Long popUID() {
          return getParentEditor().popUID();
      } 
      
      public Long peekUID() {
          return getParentEditor().peekUID();
      }      
      
      public void removeUID(Long sgUID) {
          getParentEditor().removeUID(sgUID);
      }     

    public SceneGraphEditor getParentEditor() {
        return parentEditor;
    }
    
    public BehaviorObserver getBehaviorObserver() {
        return behaviorObserver;
    }
}
