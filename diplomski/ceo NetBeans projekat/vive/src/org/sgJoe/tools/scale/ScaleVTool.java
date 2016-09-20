package org.sgJoe.tools.scale;

import java.util.Hashtable;
import org.apache.log4j.Logger;


import com.sun.j3d.utils.geometry.*;

import java.util.ArrayList;
import javax.media.j3d.*;
import javax.vecmath.Point3d;

import org.apache.log4j.Logger;
import org.sgJoe.graphics.event.*;
import org.sgJoe.graphics.event.mouse.MouseOverPublisher;
import org.sgJoe.graphics.event.mouse.SGEventMouseOver;

import org.sgJoe.tools.*;
import org.sgJoe.tools.interfaces.*;



/*
 * Descritpion for ScaleVTool.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: May 4, 2006  11:42 PM  $
 */


public class ScaleVTool extends VTool implements MouseOverPublisher {
    
    private static Logger logger = Logger.getLogger(ScaleVTool.class);

    private EventDispatcher dispatcher;
    
    // --> private VToolTG rotTG = null;

    private int X;
    private int Y;
    private Point3d localPt;
    private Point3d worldPt;
    private double distance;
    private View viewRef;
    
    private Hashtable eventTransparency = new Hashtable();
    
    private boolean pTransparent = false;

    public ScaleVTool(VirTool virToolRef) {
        super(virToolRef);
    }

    public void setup() 
    {
        BranchGroup bgCube = new BranchGroup();
        VToolFactory.setBGCapabilities(bgCube);       
        
        //rotTG = new VToolTG();
        //VToolFactory.setTGCapabilities(rotTG);
        
        // some geometry
        ColorCube cube = new ColorCube();
        
        bgCube.addChild(cube);
        
        //rotTG.addChild(bgCube);
        
        this.addChild(bgCube);                
        
        // at this moment 
        this.setPDispatcher(EventDispatcher.getDispatcher());
        this.setPTransparent(true, SGEvent.EVT_MOUSE_OVER);
    }
    
    public Bounds getBoundingSphere() {
        return this.getBounds();
    }    

    public void onPRegister(Long evtUID) {
    }

    public void onPublish(SGEvent event) {
    }

    public ArrayList getEventUIDs4Publish() 
    {
        ArrayList evtList = new ArrayList();
        evtList.add(SGEventMouseOver.EVT_MOUSE_OVER);
        return evtList;
    }

    public SGEvent createEvent(Long evtUID) 
    {        
        return new SGEventMouseOver(this, X, Y, localPt, worldPt, distance, viewRef);
        
    }

    public void setPDispatcher(EventDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public EventDispatcher getPDispatcher() {
        return dispatcher;
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

    public Long getPublisherUID() {
        return super.getToolUID();
    }


    public boolean isPTransparent(Long evtUID) 
    {
        if(this.eventTransparency.containsKey(evtUID)) 
        {
            Boolean transparent = (Boolean) eventTransparency.get(evtUID);
            return transparent.booleanValue();
        } 
        else 
        {
            return false;
        }
    }

    public void setPTransparent(boolean bTransparent, Long evtUID) 
    {
        eventTransparency.put(evtUID, new Boolean(bTransparent));
    }

    public String getPublisherName() 
    {
        return this.getVirToolRef().getInstanceName();
    }

}
