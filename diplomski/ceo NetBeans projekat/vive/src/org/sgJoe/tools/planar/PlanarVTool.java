package org.sgJoe.tools.planar;

import com.sun.j3d.utils.geometry.*;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.vecmath.Point3d;
import org.VIVE.graphics.event.caliper.SGEventMoveTool;
import org.VIVE.graphics.event.caliper.SGEventStartMoveTool;
import org.VIVE.graphics.event.caliper.SGEventStopMoveTool;
import org.apache.log4j.Logger;
import org.sgJoe.graphics.event.EventDispatcher;
import org.sgJoe.graphics.event.SGEvent;
import org.sgJoe.graphics.event.mouse.*;
import org.sgJoe.tools.VToolFactory;

import org.sgJoe.tools.interfaces.*;
import javax.media.j3d.*;

/*
 * Descritpion for PlanarVTool.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: April 19, 2006  9:44 AM    $
 */


public class PlanarVTool extends VTool  implements MouseOverPublisher, MousePressedPublisher{
    
    private static Logger logger = Logger.getLogger(PlanarVTool.class);
    
    private EventDispatcher dispatcher;
    
    private int X;
    private int Y;
    private Point3d localPt;
    private Point3d worldPt;
    private double distance;
    private View viewRef;
    
    private Hashtable eventTransparency = new Hashtable();
    
    private boolean pTransparent = false;
    
    public PlanarVTool(VirTool virToolRef) {
        super(virToolRef);
    }
    
    public void setup() {
        BranchGroup bgCube = new BranchGroup();
        VToolFactory.setBGCapabilities(bgCube);        
        
        // some geometry
        ColorCube cube = new ColorCube();
        bgCube.addChild(cube);
        
        this.addChild(bgCube);                
        
        this.setPDispatcher(EventDispatcher.getDispatcher());
    }
    
    public Bounds getBoundingBox() 
    {
        BranchGroup bg =(BranchGroup) getChild(0);
        Node node = bg.getChild(0);
        return node.getBounds();
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

    public void setWPt(Point3d wPt) 
    {
        this.worldPt = wPt;
    }

    public void setDistance(double distance) 
    {
        this.distance = distance;
    }

    public void setView(View view) 
    {
        this.viewRef = view;
    }

    public void setPDispatcher(EventDispatcher dispatcher) 
    {
         this.dispatcher = dispatcher;
    }

    public EventDispatcher getPDispatcher() 
    {
         return dispatcher;
    }

    public void onPRegister(Long evtUID) 
    {
    }

    public ArrayList getEventUIDs4Publish() 
    {
        ArrayList evtList = new ArrayList();
        evtList.add(SGEventMouseOver.EVT_MOUSE_OVER);
        return evtList;
    }

    public void onPublish(SGEvent event) 
    {
    }

    public SGEvent createEvent(Long evtUID) 
    {
        if(evtUID == SGEvent.EVT_MOUSE_OVER) 
        {           
             return new SGEventMouseOver(this, X, Y, localPt, worldPt, distance, viewRef);
       
        }
         else if(evtUID == SGEvent.EVT_MOUSE_PRESSED) 
        {        
            return new SGEventMousePressed(this, X, Y, localPt, worldPt, distance, viewRef);
           
        }
        else if(evtUID == SGEvent.EVT_START_MOVE_TOOL) 
        {        
            return new SGEventStartMoveTool(this,SGEvent.EVT_START_MOVE_TOOL);
           
        }
        else if(evtUID == SGEvent.EVT_MOVE_TOOL)
        {        
            return new SGEventMoveTool(this,SGEvent.EVT_MOVE_TOOL);
           
        }
        else if(evtUID == SGEvent.EVT_STOP_MOVE_TOOL) 
        {        
            return new SGEventStopMoveTool(this,SGEvent.EVT_STOP_MOVE_TOOL);  
        } 
        else 
        {
            return null;
        }
    }

    public Long getPublisherUID() 
    {
        return super.getToolUID();
    }

    public String getPublisherName() 
    {
        return this.getVirToolRef().getInstanceName();
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
}

