/*
 * CaliperStickVTool.java
 *
 * Created on Èetvrtak, 2006, Maj 4, 12.30
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.tools.caliper;

import com.sun.j3d.utils.geometry.*;
import java.util.ArrayList;
import java.util.*;
import javax.vecmath.*;
import org.VIVE.graphics.event.caliper.*;
import org.apache.log4j.Logger;
import org.sgJoe.graphics.event.EventDispatcher;
import org.sgJoe.graphics.event.SGEvent;
import org.sgJoe.graphics.event.mouse.*;
import org.sgJoe.tools.VToolFactory;

import org.sgJoe.tools.interfaces.*;
import javax.media.j3d.*;
import java.lang.*;

/**
 *
 * @author Lakicevic Milos
 */
public class CaliperStickVTool extends VTool implements  MouseOverPublisher,MousePressedPublisher{
    
    private static Logger logger = Logger.getLogger(CaliperStickVTool.class);

    private EventDispatcher dispatcher;
    
    private Hashtable eventTransparency = new Hashtable();

    //MouseEventPublisher required fields 
    private int X;
    private Point3d localPt;   
    private int Y;
    private Point3d worldPt;
    private double distance;
    private View viewRef; 
    
    private Color3f color =  new Color3f();
  
    private Appearance ap = new Appearance();

    /**
     * Creates a new instance of CaliperStickVTool
     */
    public CaliperStickVTool(VirTool virToolRef) {
        super(virToolRef);
        
        setPDispatcher(EventDispatcher.getDispatcher());
    }
    
    public void setup(){
        BranchGroup bg = new BranchGroup();
        VToolFactory.setBGCapabilities(bg);    
        
        ColoringAttributes ca = new ColoringAttributes(new Color3f(1.0f,0.0f,0.0f),ColoringAttributes.SHADE_FLAT);
        ap.setColoringAttributes(ca);
        ap.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_READ |
                         Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);
        
        Cylinder stick=new Cylinder(1.0f,1.0f,Primitive.GEOMETRY_NOT_SHARED,ap);
        
        bg.addChild(stick);
        this.modifyTransform3dForAB();
        this.addChild(bg);
        
        setPTransparent(true,SGEvent.EVT_MOUSE_PRESSED);
        setPTransparent(true,SGEvent.EVT_MOUSE_OVER);
    }
    
    public void modifyTransform3dForAB(){
                 
         /* A Java3D cylinder is created lying on the Y axis by default.
               The idea here is to take the desired cylinder's orientation
               and perform a tranformation on it to get it ONTO the Y axis.
               Then this transformation matrix is inverted and used on a
               newly-instantiated Java 3D cylinder. */        
        
        Vector3d base = ((CaliperVirTool)this.getVirToolRef()).getPointA(), 
                 apex = ((CaliperVirTool)this.getVirToolRef()).getPointB();
         
            double stickWidth = ((CaliperVirTool) getVirToolRef()).getCaliperWidth();
            
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
            
            Vector3d unitY = new Vector3d(0,1,0),
                     cr    = new Vector3d();
            double angle;
            
            cr.cross(unitY,unit);
            angle = unitY.angle(unit);
            
            AxisAngle4d vectAlfa = new AxisAngle4d(cr,angle);
            Transform3D transMatrix = new Transform3D();
            transMatrix.setRotation(vectAlfa);
            transMatrix .setTranslation(center);
            
            Transform3D scaleMatrix = new Transform3D();
            Vector3d scale = new Vector3d(stickWidth,height,stickWidth);
            scaleMatrix.setScale(scale);
            transMatrix.mul(scaleMatrix);
            this.setTransform(transMatrix);                              
    }
    /*this class implements mouse publisher interfaces 
      only to be able to propagate mouse events through stick to ather tool*/
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
        Point3d tch = new Point3d(wPt);
        Transform3D trans = new Transform3D(),
                    inverted = new Transform3D();
        this.getTransform(trans);
        inverted.invert(trans);
        inverted.transform(tch);
        tch.x = 0.0d;
        tch.z = 0.0d;
        trans.transform(tch);
        this.worldPt = tch;        
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setView(View view) {
        this.viewRef = view;
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
        evtList.add(SGEvent.EVT_START_MOVE_TOOL);
        evtList.add(SGEvent.EVT_MOVE_TOOL);
        evtList.add(SGEvent.EVT_STOP_MOVE_TOOL);
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
           else              
             return new SGEventMousePressed(this, X, Y, localPt, worldPt, distance, viewRef);
     
        }else if(evtUID == SGEvent.EVT_START_MOVE_TOOL) {
        
            return new SGEventStartMoveTool(this,SGEvent.EVT_START_MOVE_TOOL);
           
        }else if(evtUID == SGEvent.EVT_MOVE_TOOL) {
        
            return new SGEventMoveTool(this,SGEvent.EVT_MOVE_TOOL);
           
        }else if(evtUID == SGEvent.EVT_STOP_MOVE_TOOL) {
        
            return new SGEventStopMoveTool(this,SGEvent.EVT_STOP_MOVE_TOOL);
           
        
        
        } else {
            return null;
        }
    }

    public Long getPublisherUID() {
        return super.getToolUID();
    }

    public String getPublisherName() {
        return getVirToolRef().getInstanceName();
    }

    public boolean isPTransparent(Long evtUID) {
        
        if(this.eventTransparency.containsKey(evtUID)) {
        
            Boolean transparent = (Boolean) eventTransparency.get(evtUID);
            return transparent.booleanValue();
            
        } else             
            return false;            
    }

    public void setPTransparent(boolean bTransparent, Long evtUID) {
         eventTransparency.put(evtUID, new Boolean(bTransparent));
    }

    public Color3f getColor() {
        return color;
    }

    public void setColor(Color3f color) {
        this.color = color;
        ap.getColoringAttributes().setColor(this.color);
    }

    public void publishEvent(Long evtId){
        getPDispatcher().publish(this,evtId);
    }
}
