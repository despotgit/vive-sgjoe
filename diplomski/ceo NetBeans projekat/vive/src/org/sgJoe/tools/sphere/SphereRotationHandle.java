package org.sgJoe.tools.sphere;

import java.util.ArrayList;
import java.util.Hashtable;
import javax.vecmath.Point3d;
import org.apache.log4j.Logger;
import org.sgJoe.graphics.event.EventDispatcher;
import org.sgJoe.graphics.event.EventPublisher;
import org.sgJoe.graphics.event.SGEvent;
import org.sgJoe.graphics.event.mouse.MouseDraggedPublisher;
import org.sgJoe.graphics.event.mouse.MouseOverPublisher;
import org.sgJoe.graphics.event.mouse.SGEventMouseDragged;
import org.sgJoe.graphics.event.mouse.SGEventMouseOver;

import org.sgJoe.tools.*;
import org.sgJoe.tools.decorators.*;
import org.sgJoe.tools.interfaces.*;
import com.sun.j3d.utils.geometry.*;
import javax.media.j3d.*;

/*
 * Descritpion for SphereRotationHandle.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: April 29, 2006  10:39 AM   $
 */

public class SphereRotationHandle extends VToolHandle implements MouseOverPublisher, MouseDraggedPublisher {
    
    private static Logger logger = Logger.getLogger(SphereRotationHandle.class);
    
    // eventpublusher 
    private EventDispatcher dispatcher;
    
    private int X;
    private int Y;
    private Point3d localPt;
    private Point3d worldPt;
    private double distance;
    private View viewRef;
    
    private Hashtable eventTransparency = new Hashtable();
    // eventpublusher 
    
    public SphereRotationHandle(VirTool virToolRef, int action) {
        super(virToolRef, action);

        BranchGroup bg = new BranchGroup();
        VToolFactory.setBGCapabilities(bg);
        
        // some geometry for handle
        Appearance app = new Appearance();
        PolygonAttributes polyAttr = new PolygonAttributes();
        polyAttr.setPolygonMode(PolygonAttributes.POLYGON_LINE);
        app.setPolygonAttributes(polyAttr);
        // TransparencyAttributes transAttr = new TransparencyAttributes(TransparencyAttributes.FASTEST,1.0f); 
        // app.setTransparencyAttributes(transAttr);
        
        Sphere sphere = new Sphere(1.0f, Primitive.GEOMETRY_NOT_SHARED, 100);
        sphere.setAppearance(app);
        
        bg.addChild(sphere);

        this.addChild(bg); 
        
        // at this moment 
        this.setPDispatcher(EventDispatcher.getDispatcher());
        this.setPTransparent(true, SGEvent.EVT_MOUSE_OVER);
        this.setPTransparent(true, SGEvent.EVT_MOUSE_DRAGGED);
    }

    public void onMousePressed() {
        this.action = VToolForm.ACT_MOUSE_PRESSED;
    }

    public void onMouseClicked() {
        this.action = VToolForm.ACT_MOUSE_CLICKED;
    }
    
    public void onMouseDragged() {
        this.action = VToolForm.ACT_MOUSE_ROTATE;
    }

    public void onMouseReleased() {
        this.action = VToolForm.ACT_MOUSE_RELEASED;
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
        this.worldPt = wPt;
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
        return dispatcher;
    }
    
    public void onPRegister(Long evtUID) { }

    public void onPublish(SGEvent event) {
    }

    public ArrayList getEventUIDs4Publish() {
        ArrayList evtList = new ArrayList();
        evtList.add(SGEventMouseOver.EVT_MOUSE_OVER);
        evtList.add(SGEventMouseOver.EVT_MOUSE_DRAGGED);
        return evtList;
    }

    public SGEvent createEvent(Long evtUID) {
        if(evtUID == SGEvent.EVT_MOUSE_OVER) {
            return null;
        } else if(evtUID == SGEvent.EVT_MOUSE_DRAGGED) {
            // return new SGEventMouseDragged(this, X, Y, localPt, worldPt, distance, viewRef);
            return null;
        } else {
            return null;
        }
    }
    public Long getPublisherUID() {
        return new Long(-1);
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
    
    public String getPublisherName() {
        return "SphereRotationHandle" + "#" + this.getVirToolRef().getToolUID();
    }
    
}
