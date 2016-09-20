/*
 * CaliperVirTool.java
 *
 * Created on Èetvrtak, 2006, Maj 4, 12.34
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.tools.caliper; 


import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.JPanel;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import org.VIVE.graphics.event.caliper.SGEventStartMoveCaliper;
import org.VIVE.gui.toolOperators.Visibility;
import org.VIVE.gui.toolOperators.VisibilityComponent;
import org.VIVE.gui.toolOperators.interfaces.iVisibility;
import org.VIVE.gui.toolPanels.ToolPanelCaliper;
import org.VIVE.tools.caliper.labels.AngleLabel;
import org.VIVE.tools.caliper.labels.LengthLabel;
import org.apache.log4j.Logger;
import org.sgJoe.graphics.SceneGraphEditor;
import org.sgJoe.graphics.behaviors.BehaviorObserver;
import org.sgJoe.graphics.event.*;
import org.sgJoe.graphics.event.mouse.*;
import org.sgJoe.graphics.event.view.*;
import org.sgJoe.tools.VToolFactory;
import org.sgJoe.tools.decorators.SliderVToolHandle;
import org.sgJoe.tools.interfaces.*;
import javax.media.j3d.*;

/**
 *
 * @author Lakicevic Milos
 */
public class CaliperVirTool extends VirTool implements EventSubscriber, iVisibility{
    
     private static long counter = 0;
    
     private Vector3d pointA = new Vector3d(), 
                      pointB = new Vector3d(),
               
                      tempLocalA = new Vector3d(),
                      tempLocalB = new Vector3d();
     
     
     
     private boolean  movingA = true, 
                      movingB = true;
     
     // VTool references on which caliper is attached 
     private VTool    propVToolA = null, 
                      propVToolB = null;
     
     //caliper states
     public final static int  JUST_CREATED = 0,
                              OVERLAPING_POINTS  = 1,
                              CHANGING_POSITION = 2,
                              POINTS_FIXED =3,
                              MOVE_BY_CALIPER = 4,
                              MOVE_BY_ATHER_TOOL = 5,
                              MOVING_B = 6;
     
     //visibilities
     public static final int STICK_AND_SPHERES = 0,
                             LINE =1,
                             LINE_AND_CROSSES = 2,
                             HIDEN = 3;
     
    private int visibility = STICK_AND_SPHERES;
    
    boolean moved = false;   

    private EventDispatcher dispatcher;
    private static int numSpheresMoving = 0;
    
                
    private int caliperState = JUST_CREATED;
    
    private boolean traceTool = true;
    
    private View viewRef;

    private Point3d worldPt;
    
    //caliper width is referent width for caliper stick(x1) and caliper spheres(x2)
    private double caliperWidth = 0.05f;
    
    private Color3f color = new Color3f(1.0f,0.0f,1.0f);
    
    public static int POINT_A = 0;
    public static int POINT_B = 1;
    
    private javax.swing.JPanel[] caliperOperators;
    
    /**
     * Creates a new instance of CaliperVirTool
     */
    public CaliperVirTool() 
    {
        super();
        //register at EventDispatcher
        setSDispatcher(EventDispatcher.getDispatcher());
        dispatcher.register(this);
        this.setVUIToolFormRef(new ToolPanelCaliper(this));
        
        caliperOperators = new javax.swing.JPanel[1];
        VisibilityComponent[] vis = new VisibilityComponent[4];
        vis[0] = new VisibilityComponent("Stick, spheres",STICK_AND_SPHERES);
        vis[1] = new VisibilityComponent("Line",LINE);
        vis[2] = new VisibilityComponent("Line, Crosshair",LINE_AND_CROSSES);
        vis[3] = new VisibilityComponent("Hiden",HIDEN);
        caliperOperators[0] = new Visibility(vis, this);
    }
    
    SceneGraphEditor editor;
    
    private CaliperSphereVTool sphereA, sphereB;
    
    private CaliperStickVTool stick;
    
    private LengthLabel label;
    
    private AngleLabel angleLabelA, angleLabelB;
    
    private CaliperLineVTool line;
    
    private CaliperCrossVTool crossA, crossB;
    
    public void setup(SceneGraphEditor editor, BehaviorObserver behaviorObserver) {
        super.setup(editor, behaviorObserver);
        this.editor = editor;
        
        getStick().setup();
        getSphereARef().setup();
        getSphereBRef().setup();
        getLabel().setup();
        getAngleLabelA().setup();
        getAngleLabelB().setup();
        getLine().setup();
        getCrossA().setup();
        getCrossB().setup();
        
        setViewRef(editor.getViewInFocus());
        
        setColor(color);
        setVisibilityLevel(HIDEN);
        
    }
    
    public void createToolInstance(String string) {
        
        this.setStick(new CaliperStickVTool(this));
        this.setSphereARef(new CaliperSphereVTool(this, POINT_A));
        this.setSphereBRef(new CaliperSphereVTool(this, POINT_B));
        this.setLabel(new LengthLabel(this));
        this.setAngleLabelA(new AngleLabel(this, POINT_A));
        this.setAngleLabelB(new AngleLabel(this, POINT_B));
        this.setLine(new CaliperLineVTool(this));
        this.setCrossA(new CaliperCrossVTool(this,POINT_A));
        this.setCrossB(new CaliperCrossVTool(this,POINT_B));
        BranchGroup bg;
        
        bg = new BranchGroup();
        bg.addChild(this.getSphereARef());
        this.getToolBaseSWG().addChild(bg);
        
        bg = new BranchGroup();
        bg.addChild(this.getStick());
        this.getToolBaseSWG().addChild(bg);
        
        bg = new BranchGroup();
        bg.addChild(this.getSphereBRef());
        this.getToolBaseSWG().addChild(bg);
        
        bg = new BranchGroup();
        bg.addChild(this.getLabel());
        this.getToolBaseSWG().addChild(bg);
        
        bg = new BranchGroup();
        bg.addChild(this.getAngleLabelA());
        this.getToolBaseSWG().addChild(bg);
        
        bg = new BranchGroup();
        bg.addChild(this.getAngleLabelB());
        this.getToolBaseSWG().addChild(bg);
        
        bg = new BranchGroup();
        bg.addChild(this.getLine());
        this.getToolBaseSWG().addChild(bg);
        
        bg = new BranchGroup();
        bg.addChild(this.getCrossA());
        this.getToolBaseSWG().addChild(bg);
        
        bg = new BranchGroup();
        bg.addChild(this.getCrossB());
        this.getToolBaseSWG().addChild(bg);
        
        
        
        setVToolRef(stick);
        
        this.toolBaseTG.addChild(getToolBaseSWG());
  
                             
    }

    public void createForm() {
        vToolFormRef = new CaliperVToolForm(this);
    }

    public void createVUIForm() {
        vuiToolFormRef = new CaliperVUIToolForm(this);
        vuiToolFormRef.setup();
    }

    public void createPlugin() {
        vToolPluginRef = new CaliperVToolPlugin(this);
    }

    public void createOperatorsForm() {
        vToolOperatorsFormRef = new CaliperVToolOperatorsForm(this);
    }
    
    public void setPointA(Point3d a){
       pointA.x = a.x;
       pointA.y = a.y;
       pointA.z = a.z;
       this.updateCaliper();
    }
    
    public void setPointA(Vector3d a){
       pointA.x = a.x;
       pointA.y = a.y;
       pointA.z = a.z;
       this.updateCaliper();
    }
    
    public Vector3d getPointA(){
       return new Vector3d(pointA);
    }
    
    public void setPointB(Point3d b){
       pointB.x = b.x;
       pointB.y = b.y;
       pointB.z = b.z;
       this.updateCaliper();
    }
    
    public void setPointB(Vector3d b){
       pointB.x = b.x;
       pointB.y = b.y;
       pointB.z = b.z;
       this.updateCaliper();
    }
    
    public Vector3d getPointB(){
       return new Vector3d(pointB);
    }
    
    public CaliperSphereVTool getSphereARef(){
        return sphereA;
    }
    public CaliperSphereVTool getSphereBRef(){
        return sphereB;
    }
    public void setSphereARef(CaliperSphereVTool sphereA){
        this.sphereA = sphereA;
    }
    public void setSphereBRef(CaliperSphereVTool sphereB){
        this.sphereB = sphereB;
    }
    
    public int getCaliperState() {
      return caliperState;
    }

    public void setCaliperState(int caliperState) {
        this.caliperState = caliperState;
    }

    void updateCaliper() {
        //calculate distance from ViewPlatform
        Transform3D trans = new Transform3D();
        getViewRef().getViewPlatform().getLocalToVworld(trans);
        trans.invert();
        //centar = A + (B-A)/2
        Vector3d centar = new Vector3d();
        centar.sub(getPointB(),getPointA());
        centar.scale(0.5d);
        centar.add(getPointA());
        trans.transform(centar);
        Vector3d tr = new Vector3d();
        trans.get(tr);
        centar.add(tr);
        
        // "distance" is distance from view to centar of caliper
        double distance = Math.abs(centar.z);
        this.setCaliperWidth(0.003f*distance);
        
        getStick().modifyTransform3dForAB();
        getSphereARef().modifyTransform3dForEndPoint();
        getSphereBRef().modifyTransform3dForEndPoint();
        getLabel().updateLabel();
        getAngleLabelA().updateLabel();
        getAngleLabelB().updateLabel();
        getLine().modifyGeometryForAB();
        getCrossA().modifyTransform3DForAB();
        getCrossB().modifyTransform3DForAB();
        
    }

    public CaliperStickVTool getStick() {
        return stick;
    }

    public void setStick(CaliperStickVTool stick) {
        this.stick = stick;
    }

    public boolean isMovingA() {
        return movingA;
    }

    public void setMovingA(boolean movingA) {
        this.movingA = movingA;
    }

    public boolean isMovingB() {
        return movingB;
    }

    public void setMovingB(boolean movingB) {
        this.movingB = movingB;
    }

    public Vector3d getTempLocalA() {
        return new Vector3d(tempLocalA);
    }

    public void setTempLocalA(Vector3d a) {
       tempLocalA.x = a.x;
       tempLocalA.y = a.y;
       tempLocalA.z = a.z;
    }

    public Vector3d getTempLocalB() {
      return new Vector3d(tempLocalB);
    }

    public void setTempLocalB(Vector3d b) {
       tempLocalB.x = b.x;
       tempLocalB.y = b.y;
       tempLocalB.z = b.z;
    }

    public boolean isTraceTool() {
        return traceTool;
    }

    public void setTraceTool(boolean traceTool) {
        this.traceTool = traceTool;
    }

    public VTool getPropVToolA() {
        return propVToolA;
    }

    public void setPropVToolA(VTool parentVToolA) {
          this.propVToolA = parentVToolA;
    }

    public VTool getPropVToolB() {
        return propVToolB;
    }

    public void setPropVToolB(VTool parentVToolB) {
        this.propVToolB = parentVToolB;
    }

    public void setFocus() {
    }

    public static int getNumSpheresMoving() {
        return numSpheresMoving;
    }

    public static void setNumSpheresMoving(int aNumSpheresMoving) {
        numSpheresMoving = aNumSpheresMoving;
    }
    
    public static void incNumSpheresMoving(){
       numSpheresMoving++;
    }
    
    public static void decNumSpheresMoving(){
      if(numSpheresMoving>0) numSpheresMoving--; 
    }

    public LengthLabel getLabel() {
        return label;
    }

    public void setLabel(LengthLabel label) {
        this.label = label;
    }

    public double getCaliperWidth() {
        return caliperWidth;
    }

    public void setCaliperWidth(double caliperWidth) {
        this.caliperWidth = caliperWidth;
    }

    public void setSDispatcher(EventDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void onSRegister(Long evtUID) {
    }

   
    public View getViewRef() {
        return viewRef;
    }

    public void setViewRef(View viewRef) {
        this.viewRef = viewRef;
    }

    public Color3f getColor() {
        return color;
    }

    public void setColor(Color3f color) {        
        this.color = color;
        getStick().setColor(color);
        getSphereARef().setColor(color);
        getSphereBRef().setColor(color);
        getLine().setColor(color);
        getCrossA().setColor(color);
        getCrossB().setColor(color);
    }

      
    public void onEventInJustCreated(SGEvent event)
    {
          if (event.getUID().longValue() == SGEvent.EVT_MOUSE_OVER.longValue())
          {    
                    
                     setVisibilityLevel(STICK_AND_SPHERES);
                     
                     setMovingA(true);
                     setMovingB(true);
                     incNumSpheresMoving();
                     incNumSpheresMoving();
                 
                     setPointA( ((SGEventMouse)event).getWorldPt() );
                     setPointB( ((SGEventMouse)event).getWorldPt() );
                     setPropVToolA((VTool)event.getPublisherRef());
                     setPropVToolB((VTool)event.getPublisherRef());
                     
                     setCaliperState(CaliperVirTool.OVERLAPING_POINTS);                     
          }
    }
    
    public void onEventInOverlapingPoints(SGEvent event)
    {
                     
         if (event.getUID().longValue() ==SGEvent.EVT_MOUSE_OVER.longValue())
         { 
          
                setPointA( ((SGEventMouse)event).getWorldPt() );
                setPointB( ((SGEventMouse)event).getWorldPt() );
                setPropVToolA((VTool)event.getPublisherRef());
                setPropVToolB((VTool)event.getPublisherRef());
                     
         }
         else if (event.getUID().longValue() == SGEvent.EVT_MOUSE_PRESSED.longValue() 
                   ||
                   event.getUID().longValue() == SGEvent.EVT_CALIPER_MOVING_MOUSE_PRESSED.longValue())
         {             
                                                                            
                setMovingA(false);
                setMovingB(true);
                decNumSpheresMoving();
                     
                setPointA( ((SGEventMouse)event).getWorldPt() );
                setPointB( ((SGEventMouse)event).getWorldPt() );             
                setPropVToolA((VTool)event.getPublisherRef());
                setPropVToolB((VTool)event.getPublisherRef());
                     
                setCaliperState(CaliperVirTool.MOVING_B);
          }     
    }
    
    public void onEventInMovingB(SGEvent event)
    {
        
          if (event.getUID().longValue() ==SGEvent.EVT_MOUSE_OVER.longValue())
          {  
                      
                setPropVToolB((VTool)event.getPublisherRef()); 
                setPointB( ((SGEventMouse)event).getWorldPt() );
                moved = true;
                          
          }else if ((event.getUID().longValue() == SGEvent.EVT_MOUSE_PRESSED.longValue()
                     ||
                     event.getUID().longValue() == SGEvent.EVT_CALIPER_MOVING_MOUSE_PRESSED.longValue())
                     &&  moved)
         {
                 getSphereARef().setPTransparent(false,SGEvent.EVT_MOUSE_PRESSED);
                 getSphereARef().setPTransparent(false,SGEvent.EVT_MOUSE_OVER); 
                 getSphereBRef().setPTransparent(false,SGEvent.EVT_MOUSE_PRESSED);
                 getSphereBRef().setPTransparent(false,SGEvent.EVT_MOUSE_OVER);
                 getStick().setPTransparent(false,SGEvent.EVT_MOUSE_PRESSED);
                 getStick().setPTransparent(false,SGEvent.EVT_MOUSE_OVER);
                          
                 decNumSpheresMoving();
                 setMovingB(false);
                          
                 setCaliperState(CaliperVirTool.POINTS_FIXED);
           }
    }
    
    public void onEventInChangingPosition(SGEvent event)
    {
         
        if (isMovingA())
        {                                                        
               if (event.getUID().longValue() == SGEvent.EVT_MOUSE_OVER.longValue())
               {  
                       
                       //if prop tool  is not caliper in moving state update prop tool
                  if ( ! (getPropVToolA() instanceof CaliperSphereVTool
                           && ((CaliperVirTool)getPropVToolA().getVirToolRef()). getCaliperState() == MOVE_BY_CALIPER))
                                setPropVToolA((VTool)event.getPublisherRef());
                       
                       setPointA( ((SGEventMouse)event).getWorldPt() );
                       getStick().publishEvent(SGEvent.EVT_MOVE_TOOL);
                       moved = true;
                       
                   }else if (moved && (event.getUID().longValue() ==SGEvent.EVT_CALIPER_MOVING_MOUSE_PRESSED.longValue() 
                             ||
                             event.getUID().longValue() ==SGEvent.EVT_MOUSE_PRESSED.longValue()) )
                   {
                      
                      getSphereARef().setPTransparent(false,SGEvent.EVT_MOUSE_PRESSED);
                      getSphereARef().setPTransparent(false,SGEvent.EVT_MOUSE_OVER);
                      getStick().setPTransparent(false,SGEvent.EVT_MOUSE_PRESSED);
                      getStick().setPTransparent(false,SGEvent.EVT_MOUSE_OVER);
                      
                      setMovingA(false);
                      decNumSpheresMoving();
                      getStick().publishEvent(SGEvent.EVT_STOP_MOVE_TOOL);
                      setCaliperState(POINTS_FIXED);
                      
                   }
                  }   
                 
     if (isMovingB())
     {
                   
                   if (event.getUID().longValue() ==SGEvent.EVT_MOUSE_OVER.longValue() )
                   {  
                      
                      //if prop tool  is not caliper in moving state
                      if ( ! (getPropVToolB() instanceof CaliperSphereVTool
                               && ((CaliperVirTool)getPropVToolB().getVirToolRef()). getCaliperState() == MOVE_BY_CALIPER)) 
                              setPropVToolB((VTool)event.getPublisherRef()); 
                      
                      setPointB( ((SGEventMouse)event).getWorldPt() );
                      getStick().publishEvent(SGEvent.EVT_MOVE_TOOL);
                      moved = true;
                      
                   }else if (moved && (event.getUID().longValue() == SGEvent.EVT_CALIPER_MOVING_MOUSE_PRESSED.longValue()
                             ||
                             event.getUID().longValue() ==SGEvent.EVT_MOUSE_PRESSED.longValue() ) )
                   {
                      
                      getSphereBRef().setPTransparent(false,SGEvent.EVT_MOUSE_PRESSED);
                      getSphereBRef().setPTransparent(false,SGEvent.EVT_MOUSE_OVER);
                      getStick().setPTransparent(false,SGEvent.EVT_MOUSE_PRESSED);
                      getStick().setPTransparent(false,SGEvent.EVT_MOUSE_OVER);
                      setMovingB(false);
                      decNumSpheresMoving();
                      
                      getStick().publishEvent(SGEvent.EVT_STOP_MOVE_TOOL);
                      setCaliperState(POINTS_FIXED);
                      
                   }
                  } 
    }
    
    public void onEventInMoveByAtherTool(SGEvent event){
        if (isMovingA()){  
                   // during modifying ather tool -> modify caliper
                   if (event.getUID().longValue() == SGEvent.EVT_MOVE_TOOL.longValue() 
                       && isProp(POINT_A, (VTool)event.getPublisherRef() )){  
                      
                      Transform3D trUpper = new Transform3D(),
                                  trTool = new Transform3D();
                                              
                      ( (VTool)(event.getPublisherRef()) ).getLocalToVworld(trUpper);
                      ( (VTool)(event.getPublisherRef()) ).getTransform(trTool);                      
                      trUpper.mul(trTool);
                      Vector3d point = getTempLocalA(),
                               trans = new Vector3d();                    
                      trUpper.transform(point);
                      
                      trUpper.get(trans);
                      point.add(trans);
                      
                      setPointA(point);
                      getStick().publishEvent(SGEvent.EVT_MOVE_TOOL);
                      
                   // when stop modifying ather tool -> stop modifying caliper
                   }else if (event.getUID().longValue() == SGEvent.EVT_STOP_MOVE_TOOL.longValue()){
                      
                      setMovingA(false);
                      getStick().publishEvent(SGEvent.EVT_STOP_MOVE_TOOL);
                      setCaliperState(POINTS_FIXED);  
                      
                   }else if (event.getUID().longValue() == SGEvent.EVT_START_MOVE_TOOL.longValue()
                        && this.traceTool && isProp(POINT_B, (VTool)event.getPublisherRef() ) ){    
                                                        
                            Vector3d point = getPointB(),
                                     trans = new Vector3d();
                            Transform3D trUpper = new Transform3D(),
                                        trTool =  new Transform3D();                                              
                            ( (VTool)(event.getPublisherRef()) ).getLocalToVworld(trUpper);
                            ( (VTool)(event.getPublisherRef()) ).getTransform(trTool);                      
                            trUpper.mul(trTool);
                            trUpper.invert();
                            trUpper.transform(point);
                            trUpper.get(trans);
                            point.add(trans);
                            setTempLocalB(point);
                            
                            setMovingB(true);
                            setCaliperState(MOVE_BY_ATHER_TOOL);
                                
                        } 
      }   // moving A
                 
       if (isMovingB())
       {          
                   
                   if (event.getUID().longValue() ==SGEvent.EVT_MOVE_TOOL.longValue()
                       && isProp(POINT_B, (VTool)event.getPublisherRef() ))
                   {  
                      
                      Transform3D trUpper = new Transform3D(),
                                  trTool = new Transform3D();
                                              
                      ( (VTool)(event.getPublisherRef()) ).getLocalToVworld(trUpper);
                      ( (VTool)(event.getPublisherRef()) ).getTransform(trTool);                      
                      trUpper.mul(trTool);
                      Vector3d point = getTempLocalB(),
                               trans = new Vector3d();                    
                      trUpper.transform(point);
                      trUpper.get(trans);
                      point.add(trans);
                      
                      setPointB(point);
                      getStick().publishEvent(SGEvent.EVT_MOVE_TOOL);
                   
                    }else if (event.getUID().longValue() ==SGEvent.EVT_STOP_MOVE_TOOL.longValue()){
                      
                      setMovingB(false);                 
                      getStick().publishEvent(SGEvent.EVT_STOP_MOVE_TOOL);
                      setCaliperState(POINTS_FIXED); 
                      
                    }else if (event.getUID().longValue() == SGEvent.EVT_START_MOVE_TOOL.longValue()
                        && this.traceTool && isProp(POINT_A, (VTool)event.getPublisherRef() ) ){
                       
                            Vector3d point = getPointA(),
                                     trans = new Vector3d();
                            Transform3D trUpper = new Transform3D(),
                                        trTool =  new Transform3D();                                              
                            ( (VTool)(event.getPublisherRef()) ).getLocalToVworld(trUpper);
                            ( (VTool)(event.getPublisherRef()) ).getTransform(trTool);                      
                            trUpper.mul(trTool);
                            trUpper.invert();
                            trUpper.transform(point);                            
                            trUpper.get(trans);
                            point.add(trans);
                            
                            setTempLocalA(point);
                            
                            setMovingA(true);
                            
                        } 
         }  //moving B      
                
    }
    
    public void onEventInPointsFixed(SGEvent event){
        
        if (event.getUID().longValue() == SGEvent.EVT_START_MOVE_TOOL.longValue()
                        && this.traceTool )
                    {
                        // if sender is prop VTool for A endpoint 
                        if( isProp(POINT_A, (VTool)event.getPublisherRef() ) ){
                       
                            Vector3d point = getPointA(),
                                     trans = new Vector3d();
                            Transform3D trUpper = new Transform3D(),
                                        trTool =  new Transform3D();                                              
                            ( (VTool)(event.getPublisherRef()) ).getLocalToVworld(trUpper);
                            ( (VTool)(event.getPublisherRef()) ).getTransform(trTool);                      
                            trUpper.mul(trTool);
                            trUpper.invert();
                            trUpper.transform(point);                            
                            trUpper.get(trans);
                            point.add(trans);
                            
                            setTempLocalA(point);
                            
                            setMovingA(true);
                            
                            setCaliperState(MOVE_BY_ATHER_TOOL);
                            
                            
                        } 
                        if(isProp(POINT_B, (VTool)event.getPublisherRef() ) ){    
                                                        
                            Vector3d point = getPointB(),
                                     trans = new Vector3d();
                            Transform3D trUpper = new Transform3D(),
                                        trTool =  new Transform3D();                                              
                            ( (VTool)(event.getPublisherRef()) ).getLocalToVworld(trUpper);
                            ( (VTool)(event.getPublisherRef()) ).getTransform(trTool);                      
                            trUpper.mul(trTool);
                            trUpper.invert();
                            trUpper.transform(point);
                            trUpper.get(trans);
                            point.add(trans);
                            setTempLocalB(point);
                            
                            setMovingB(true);
                            setCaliperState(MOVE_BY_ATHER_TOOL);
                                
                        } 
                        
                        if( isProp(POINT_A, (VTool)event.getPublisherRef() ) ||
                            isProp(POINT_B, (VTool)event.getPublisherRef() ))
                                  getStick().publishEvent(SGEvent.EVT_START_MOVE_TOOL);           
                        //   EVT_START_MOVE_TOOL
                        
      }else if (event.getUID().longValue() == SGEvent.EVT_MOUSE_PRESSED.longValue()){
                        
                        // if this ball send EVT_MOUSE_PRESSED then publish EVT_START_MOVE_CALIPER 
                        if( event.getPublisherRef() == getSphereARef() ){                      
                            
                            getSphereARef().publishEvent(SGEvent.EVT_START_MOVE_CALIPER);  
                          }
                          
                          if( event.getPublisherRef() == getSphereBRef() ){                      
                            
                            getSphereBRef().publishEvent(SGEvent.EVT_START_MOVE_CALIPER); 
                          }  
                        
                    }       //EVT_MOUSE_PRESSED    
                    
                    
        else if (event.getUID().longValue() == SGEvent.EVT_START_MOVE_CALIPER.longValue()){
                        
                        //if point A should follow mouse movement
                        if( shouldBallMove(POINT_A, (SGEventStartMoveCaliper) event) )                         
                        {                            
                             getSphereARef().setPTransparent(true,SGEvent.EVT_MOUSE_PRESSED);
                             getSphereARef().setPTransparent(true,SGEvent.EVT_MOUSE_OVER);
                             getStick().setPTransparent(true,SGEvent.EVT_MOUSE_PRESSED);
                             getStick().setPTransparent(true,SGEvent.EVT_MOUSE_OVER);
                             incNumSpheresMoving();
                             setMovingA(true);
                             moved = false;
                             
                             getStick().publishEvent(SGEvent.EVT_START_MOVE_TOOL);
                             setCaliperState(MOVE_BY_CALIPER);
                                                                                   
                        }
                        
                        //if point B should follow mouse movement
                        if(shouldBallMove(POINT_B, (SGEventStartMoveCaliper) event))  
                        {
                             
                             getSphereBRef().setPTransparent(true,SGEvent.EVT_MOUSE_PRESSED);
                             getSphereBRef().setPTransparent(true,SGEvent.EVT_MOUSE_OVER);
                             getStick().setPTransparent(true,SGEvent.EVT_MOUSE_PRESSED);
                             getStick().setPTransparent(true,SGEvent.EVT_MOUSE_OVER);
                             incNumSpheresMoving(); 
                             setMovingB(true);
                             moved = false;
                             
                             getStick().publishEvent(SGEvent.EVT_START_MOVE_TOOL);
                             setCaliperState(MOVE_BY_CALIPER);
                           
                             
                        }
                    };  //  EVT_START_MOVE_CALIPER
                    
    
    }
    public void onEvent(SGEvent event) 
    {       
          if(event.getUID().longValue()==SGEvent.EVT_VIEW.longValue())
          {
            setViewRef(((SGEventView)event).getViewRef());
            updateCaliper();
            return;
          } 
          
       /* 
        
        * Scenario 1:
        * kada se klikne na kuglicu (i someCalipeMove = false )ona salje svim kaliperima poruku EVT_MOVE_CALIPER
        * Oni kaliperi kojima se poklope koordinate centra neke od kuglica i ako je kaliper prethodno bio 
        * aktivan ili mu je ukljucen "Trace Tool"
        * prelaze u MOVE_BY_CALIPER rezim i setuju im se odgovarajuce boolean promenljive moveA i/ili 
        * moveB zavisno od toga da li se te tacke poklapaju sa centrom kliknute kuglice.
        * Posle toga kaliperi u stanju MOVE_BY_CALIPER reaguju na EVT_MOUSE_OVER koji salje neki drugi alat (slicer..kaliper)
        * i apdejtuje koordinate.
        * Na kraju, kada se desi EVT_MOUSE_PRESSED iznad kalipera (i someCalipeMove = true ), svim kaliperima koji su u stanju 
        * MOVE_BY_CALIPER se salje EVT_STOP_MOVE i tada se svi kaliperi koji su bili u stanju MOVE_BY_CALIPER 
        * prebacuju u FIXED_POINTS
        *
        * Scenario 2:
        * Desi se kretanje drugog alata (slicer, dicer npr). Tada taj drugi alat ispaljuje EVT_START_MOVE_TOOL.
        * Tada svi caliperi koji su prikaceni za taj alat menjaju stanje u MOVE_BY_ATHER_TOOL .
        * Na mouseDrag drugi alat(alat roditelj) salje event-e EVT_MOVE_TOOL 
        * koji poseduju i Transform3D alata koji se pomera. Caliperi reaguju na to tako sto primene taj transform3D 
        * na svoje lokalne koordinate. 
        * Kada se zavrsi drag drugog alata tj. kada se desi MOUSE_PRESSED, 
        * on ispaljuje dogadjaj EVT_STOP_MOVE_TOOL i tada se svi kaliperi koji su bili 
        * u stanju kretanja (MOVE_BY_ATHER_TOOL) zaustavljaju i menja im se stanje u FIXED_POINTS, a someCaliperMove dobija vrednost false.
        *
        *
        *
        */
       
       switch (caliperState){
            
           case JUST_CREATED:      
                onEventInJustCreated(event);                          
                break;
               
           case OVERLAPING_POINTS:
                onEventInOverlapingPoints(event);      
                break;
                   
           case MOVING_B:                
                onEventInMovingB(event);                   
                break;        
            
           case CHANGING_POSITION:
           case MOVE_BY_CALIPER:  
                onEventInChangingPosition(event); 
                break;
                   
           case CaliperVirTool.MOVE_BY_ATHER_TOOL:
                onEventInMoveByAtherTool(event);                  
                break;
           
           case POINTS_FIXED:
                onEventInPointsFixed(event);                    
                break;
       }   //switch
       
      
    }
      
     public ArrayList getEvents() {
        ArrayList evtList = new ArrayList();
        evtList.add(SGEvent.EVT_MOUSE_OVER);
        evtList.add(SGEvent.EVT_MOUSE_PRESSED);
        evtList.add(SGEvent.EVT_START_MOVE_TOOL);
        evtList.add(SGEvent.EVT_STOP_MOVE_TOOL);
        evtList.add(SGEvent.EVT_MOVE_TOOL);
        evtList.add(SGEvent.EVT_START_MOVE_CALIPER);
        evtList.add(SGEvent.EVT_VIEW); 
        
        return evtList;       
    }
    
    private boolean isProp(int whichPoint, VTool prop){
       VTool act = (whichPoint==POINT_A ? getSphereARef():
                                         getSphereBRef() );   
               
       while(act instanceof CaliperSphereVTool 
               && // && act.propVTool != prop
              !( ( ((CaliperSphereVTool)act).whichEndpoint ==POINT_A && ((CaliperVirTool)act.getVirToolRef()).getPropVToolA() == prop)
                  ||
                 ( ((CaliperSphereVTool)act).whichEndpoint ==POINT_B && ((CaliperVirTool)act.getVirToolRef()).getPropVToolB() == prop) 
                )
             ) 
                   act = ( ((CaliperSphereVTool)act).whichEndpoint ==POINT_A ?
                            ((CaliperVirTool)act.getVirToolRef()).getPropVToolA()
                           :((CaliperVirTool)act.getVirToolRef()).getPropVToolB());
       
       return ( act!= null &&
               act instanceof CaliperSphereVTool  &&               
               prop == (((CaliperSphereVTool)act).whichEndpoint ==POINT_A ?
                               ((CaliperVirTool)act.getVirToolRef()).getPropVToolA()
                               :((CaliperVirTool)act.getVirToolRef()).getPropVToolB()));
    } 
    
    private boolean shouldBallMove(int whichBall, SGEventStartMoveCaliper evt){
       // loptica treba da se krece ako je zadnji aktivan alat 
       //ili ako postoji putanja od nje do zadnjeg aktivnog alata ko ji na kojoj su svi trace Object
       //ili ako zadnji aktivan alat nije kaliper i na nju je kliknuto
        
        /*
         * if zadnji aktivan alat kaliper
         *   if (thisBall.toolRef == lastTool)
         *        return true
         *   else{
         *       act = thisBall;
         *       while( act instance of Sphere && act.toolRef!= lastTool && act.toolRef.traceObject)
         *                act = act.upperBall;
         *       return (act!=null && act instanceof sphere && act.toolRef ==lastTool &&  act.toolRef.traceObject
         *   }
         * else 
         *   return true 
         */
        //graph.getParentEditor().getVirToolStack().push(instance);
        
        //if (this.get)
        
        VirTool lastTool = editor.getVirToolStack().peek(0);
        VTool act = (whichBall==POINT_A? getSphereARef(): getSphereBRef());
            
        if( ! ((CaliperSphereVTool)act).getCentar().equals(evt.getWorldPt()) )
             return false;
        
        if (lastTool instanceof CaliperVirTool){
            
           if (act.getVirToolRef() == lastTool)                
              return true;
           else{
              while(act instanceof CaliperSphereVTool && act.getVirToolRef() != lastTool && ((CaliperVirTool)act.getVirToolRef()).traceTool)
                  act = (((CaliperSphereVTool)act).whichEndpoint == POINT_A ? 
                                ((CaliperVirTool)act.getVirToolRef()).getPropVToolA()
                               :((CaliperVirTool)act.getVirToolRef()).getPropVToolB());
              
              return ( act != null 
                       && act instanceof CaliperSphereVTool
                       && ((CaliperVirTool)act.getVirToolRef()).traceTool);
           }
        }else return false;
         
    }

  
    public AngleLabel getAngleLabelA() {
        return angleLabelA;
    }

    public void setAngleLabelA(AngleLabel angleLabelA) {
        this.angleLabelA = angleLabelA;
    }

    public AngleLabel getAngleLabelB() {
        return angleLabelB;
    }

    public void setAngleLabelB(AngleLabel angleLabelB) {
        this.angleLabelB = angleLabelB;
    }
    
    public Vector3d getDirection(){
       Vector3d direction = new Vector3d();
       direction.sub(getPointB(),getPointA());
       return direction;
    }

    public CaliperLineVTool getLine() {
        return line;
    }

    public void setLine(CaliperLineVTool line) {
        this.line = line;
    }

    public CaliperCrossVTool getCrossA() {
        return crossA;
    }

    public void setCrossA(CaliperCrossVTool crossA) {
        this.crossA = crossA;
    }

    public CaliperCrossVTool getCrossB() {
        return crossB;
    }

    public void setCrossB(CaliperCrossVTool crossB) {
        this.crossB = crossB;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibilityLevel(int visibility) {
        this.visibility = visibility;
        
        java.util.BitSet visibleNodes = new java.util.BitSet(getToolBaseSWG().numChildren());
                     
        
        switch(visibility){
            case STICK_AND_SPHERES:
                visibleNodes.set(0,true);    // stick
                visibleNodes.set(1,true);    //ball A
                visibleNodes.set(2,true);    //ball B
                visibleNodes.set(3,true);    //length label
                visibleNodes.set(4,true);    //angle label A
                visibleNodes.set(5,true);    //angle label B
                visibleNodes.set(6,false);    //line
                visibleNodes.set(7,false);    //cross A
                visibleNodes.set(8,false);    //cross B
                break;
            case LINE:
                visibleNodes.set(0,false);    // stick
                visibleNodes.set(1,false);    //ball A
                visibleNodes.set(2,false);    //ball B
                visibleNodes.set(3,true);    //length label
                visibleNodes.set(4,true);    //angle label A
                visibleNodes.set(5,true);    //angle label B
                visibleNodes.set(6,true);    //line
                visibleNodes.set(7,false);    //cross A
                visibleNodes.set(8,false);    //cross B
                break;
            case LINE_AND_CROSSES:
                 visibleNodes.set(0,false);    // stick
                visibleNodes.set(1,false);    //ball A
                visibleNodes.set(2,false);    //ball B
                visibleNodes.set(3,true);    //length label
                visibleNodes.set(4,true);    //angle label A
                visibleNodes.set(5,true);    //angle label B
                visibleNodes.set(6,true);    //line
                visibleNodes.set(7,true);    //cross A
                visibleNodes.set(8,true);    //cross B
                break;
            case HIDEN:
                visibleNodes.set(0,false);    // stick
                visibleNodes.set(1,false);    //ball A
                visibleNodes.set(2,false);    //ball B
                visibleNodes.set(3,false);    //length label
                visibleNodes.set(4,false);    //angle label A
                visibleNodes.set(5,false);    //angle label B
                visibleNodes.set(6,false);    //line
                visibleNodes.set(7,false);    //cross A
                visibleNodes.set(8,false);    //cross B
                break;
        }  
                
        getToolBaseSWG().setChildMask(visibleNodes); 
    }

    public long generateUID() {
         return ++counter;
    }
    
    public JPanel[] getToolOperatorPanelList(){
        return caliperOperators;
    }
    
    
}
