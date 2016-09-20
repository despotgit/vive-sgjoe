/*
 * DicerVTool.java
 *
 * Created on tuesday 24th april 2007
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.tools.dicer;

import java.util.ArrayList;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.View;
import javax.vecmath.Point3d;
import org.VIVE.graphics.event.caliper.SGEventMoveTool;
import org.VIVE.graphics.event.caliper.SGEventStartMoveTool;
import org.VIVE.graphics.event.caliper.SGEventStopMoveTool;
import org.apache.log4j.Logger;
import org.sgJoe.graphics.event.EventDispatcher;
import org.sgJoe.graphics.event.SGEvent;
import org.sgJoe.graphics.event.mouse.MouseOverPublisher;
import org.sgJoe.graphics.event.mouse.MousePressedPublisher;
import org.sgJoe.graphics.event.mouse.SGEventMouseOver;
import org.sgJoe.graphics.event.mouse.SGEventMousePressed;
import org.sgJoe.tools.VToolFactory;
import org.sgJoe.tools.interfaces.VTool;
import org.sgJoe.tools.interfaces.VToolForm;
import org.sgJoe.tools.interfaces.VirTool;

import org.VIVE.gui.toolPanels.toolPanelDicer;
/**
 *
 * @author Vladimir
 */

public class DicerVTool extends VTool implements  iDicerConstants,MouseOverPublisher, MousePressedPublisher{
    
    private static Logger logger = Logger.getLogger(DicerVTool.class);
    
    private EventDispatcher dispatcher;
    
    private int X;
    private int Y;
    private Point3d localPt;
    private Point3d worldPt;
    private double distance;
    private View viewRef;
    
    private DicerBox shp;

    protected int action = VToolForm.ACT_NONE;
    
    /**
     * Creates a new instance of DicerVTool
     */
    
    public DicerVTool(VirTool virToolRef) 
    {
        super (virToolRef);
    }

    public void setup() 
    {
        BranchGroup bg = new BranchGroup ();
        VToolFactory.setBGCapabilities(bg);
        toolPanelDicer vui = (toolPanelDicer)getVirToolRef().getVUIToolFormRef();
        shp = new DicerBox( INIT_DICER_LENGTH , INIT_DICER_HEIGHT , INIT_DICER_WIDTH );
        bg.addChild(getShp());
        this.addChild(bg);
        
        this.setPDispatcher(EventDispatcher.getDispatcher());
    }
    
    public Bounds getBounds () 
    {
        return this.getBounds();
    }
    public BoundingSphere getBoundingSphere()
    {
        return this.getBoundingSphere();
    }

    public DicerBox getShp() 
    {
        return shp;
    }
    
    public void setAction4OnMousePressed() 
    {
        setAction(VToolForm.ACT_MOUSE_VTOOL_PRESSED);
    }
    
    public void setAction4OnMouseDragged() 
    {
        setAction(VToolForm.ACT_MOUSE_VTOOL_DRAGGED);
    }
    
    public void setAction4OnMouseReleased() 
    {
        setAction(VToolForm.ACT_MOUSE_VTOOL_RELEASED);
    }
    public void setAction4OnMouseClicked() 
    {
        setAction(VToolForm.ACT_MOUSE_VTOOL_CLICKED);
    }

    public void setX(int X) 
    {
        this.X=X;
    }

    public void setY(int Y) 
    {
        this.Y = Y;
    }

    public void setLPt(Point3d lPt) 
    {
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
        return false;
    }

    public void setPTransparent(boolean bTransparent, Long evtUID) 
    {
    }

}
