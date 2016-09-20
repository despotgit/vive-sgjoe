/*
 * CaliperSphereVTool.java
 *
 * Created on Ponedeljak, 2006, Maj 15, 12.43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.tools.caliper;

import com.sun.j3d.utils.geometry.*;
import java.util.*;
import javax.vecmath.*;
import org.VIVE.graphics.event.caliper.CaliperEventPublisher;
import org.VIVE.graphics.event.caliper.SGEventCaliperMovingMousePressed;
import org.VIVE.graphics.event.caliper.SGEventStartMoveCaliper;
import org.apache.log4j.Logger;
import org.sgJoe.graphics.event.*;
import org.sgJoe.graphics.event.mouse.*;
import org.sgJoe.tools.VToolFactory;
import org.sgJoe.tools.interfaces.*;
import javax.media.j3d.*;
import java.lang.*;

/**
 *
 * @author Milos Lakicevic
 */
public class CaliperSphereVTool extends VTool implements MouseOverPublisher,MousePressedPublisher,CaliperEventPublisher {
    
    private EventDispatcher dispatcher;
    
    int whichEndpoint;
    
    private Hashtable eventTransparency = new Hashtable();

    //MouseEventPublisher required fields 
    private int X;
    private Point3d localPt;   
    private int Y;
    private Point3d worldPt;
    private double distance;
    private View viewRef;
    
    private Appearance ap = new Appearance();
        
    private Color3f color = new Color3f();
    
    /**
     * Creates a new instance of CaliperSphereVTool
     */    
    public CaliperSphereVTool (VirTool virToolRef, int whichEndpoint) {
        super(virToolRef);
        this.whichEndpoint = whichEndpoint;      
        //register at EventDispatcher
        setPDispatcher(EventDispatcher.getDispatcher());
    }

    public void setup(){
        BranchGroup bg = new BranchGroup();
        VToolFactory.setBGCapabilities(bg);        
        Switch ballsSWG = new Switch(Switch.CHILD_MASK);
        
        ap.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_READ |
                         Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);
        
        ap.setColoringAttributes(new ColoringAttributes(new Color3f(1.0f,0.0f,0.0f),ColoringAttributes.SHADE_FLAT));
        Appearance unvisibleAp = new Appearance();
        unvisibleAp.setTransparencyAttributes(new TransparencyAttributes(TransparencyAttributes.FASTEST,1.0f));
        
        Sphere ball=new Sphere(1.0f,Primitive.GEOMETRY_NOT_SHARED,ap);
        Sphere unvisibleBall = new Sphere(3.0f,Primitive.GEOMETRY_NOT_SHARED, unvisibleAp);
        ballsSWG.addChild(ball);
        ballsSWG.addChild(unvisibleBall);
        bg.addChild(ballsSWG);
        modifyTransform3dForEndPoint();
        this.addChild(bg);
        
        java.util.BitSet visibleNodes = new java.util.BitSet(ballsSWG.numChildren() );
        visibleNodes.set( 0 );
        visibleNodes.set( 1 );
        ballsSWG.setChildMask(visibleNodes);
        
        setPTransparent(true,SGEvent.EVT_MOUSE_PRESSED);
        setPTransparent(true,SGEvent.EVT_MOUSE_OVER);
    }

    public void modifyTransform3dForEndPoint() {
        Vector3d centar;
        Transform3D translate = new Transform3D();
        double radius = 2*((CaliperVirTool) getVirToolRef()).getCaliperWidth();
        
        if (whichEndpoint == ((CaliperVirTool)this.getVirToolRef()).POINT_A)
               centar = ((CaliperVirTool)this.getVirToolRef()).getPointA();
        else
               centar = ((CaliperVirTool)this.getVirToolRef()).getPointB();
        translate.setTranslation(centar);
        translate.setScale(radius);
        this.setTransform(translate);
        
    }


    public void setPDispatcher(EventDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public EventDispatcher getPDispatcher() {
       return this.dispatcher;
    }

    public void onPRegister(Long evtUID) {
    }

    public ArrayList getEventUIDs4Publish() {
        ArrayList evtList = new ArrayList();
        evtList.add(SGEventMouseOver.EVT_MOUSE_PRESSED);
        evtList.add(SGEventMouseOver.EVT_MOUSE_OVER);
        evtList.add(SGEvent.EVT_START_MOVE_CALIPER);
        return evtList;
    }

    public void onPublish(SGEvent event) {
    }

    public SGEvent createEvent(Long evtUID) {
        if(evtUID == SGEvent.EVT_MOUSE_OVER) {           
           if (isPTransparent(SGEvent.EVT_MOUSE_OVER) ) 
             return null;
           else  
             return new SGEventMouseOver(this, X, Y, localPt, worldPt, distance, viewRef);
       
        }else if(evtUID == SGEvent.EVT_MOUSE_PRESSED) {
           if (isPTransparent(SGEvent.EVT_MOUSE_PRESSED) )
             return null;
           else{
             if(((CaliperVirTool)getVirToolRef()).getNumSpheresMoving() != 0 )  
                 return new SGEventCaliperMovingMousePressed(this, X, Y, localPt, worldPt, distance, viewRef);  
             else    
                 return new SGEventMousePressed(this, X, Y, localPt, worldPt, distance, viewRef);
           }
        }else if(evtUID == SGEvent.EVT_START_MOVE_CALIPER){
            return new SGEventStartMoveCaliper(SGEvent.EVT_START_MOVE_CALIPER,this,worldPt); 
        }
          else 
            return null;
    }

    public Long getPublisherUID() {
        return super.getToolUID();
    }

    public boolean isPTransparent(Long evtUID) {
        if(this.eventTransparency.containsKey(evtUID)) {
            Boolean transparent = (Boolean) eventTransparency.get(evtUID);
            return transparent.booleanValue();
        } else {
            return false;
        }
    }

    public void setPTransparent(boolean bTransparent, Long evtUID) {
         eventTransparency.put(evtUID, new Boolean(bTransparent));
    }

    public void setX(int X) {
        this.X = X;
    }

    public void setY(int Y) {
        this.Y = Y;
    }

    public void setLPt(Point3d lPt) {
        this.localPt = lPt;
    }

    public void setWPt(Point3d wPt) {
        this.worldPt = new Point3d( whichEndpoint==CaliperVirTool.POINT_A ? 
                                                             ((CaliperVirTool)this.getVirToolRef()).getPointA()
                                                           : ((CaliperVirTool)this.getVirToolRef()).getPointB());
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setView(View view) {
        this.viewRef = view;
    }

    public String getPublisherName() {
        return getVirToolRef().getInstanceName();
    }
    
     public Color3f getColor() {
        return color;
    }

    public void setColor(Color3f color) {
        this.color = color;
        ap.getColoringAttributes().setColor(this.color);
    }
    
    public void publishEvent(Long evtId){
        setWPt(null);        
        getPDispatcher().publish(this,evtId);
    }
    
    public Point3d getCentar(){
      return (whichEndpoint == CaliperVirTool.POINT_A?
                 new Point3d(((CaliperVirTool)getVirToolRef()).getPointA())
                :new Point3d(((CaliperVirTool)getVirToolRef()).getPointB())  );
    }
 
}
