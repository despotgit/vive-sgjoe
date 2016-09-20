/*
 * ScaleTouchHandle.java
 *
 * Created on Sreda, 2006, Maj 31, 22.45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.sgJoe.tools.slicer;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import javax.media.j3d.BranchGroup;
import org.apache.log4j.Logger;
import org.sgJoe.tools.VToolFactory;
import org.sgJoe.tools.decorators.VToolHandle;
import org.sgJoe.tools.interfaces.VToolForm;
import org.sgJoe.tools.interfaces.VirTool;

/**
 *
 * @author Igor
 */
public class ScaleTouchHandle extends VToolHandle 
{    
    private static Logger logger = Logger.getLogger(ScaleTouchHandle.class);
    
    public static final int NONE_ID = 0,
                            UPP_RIGHT_ID = 1,
                            UPP_MID_ID = 2,
                            UPP_LEFT_ID = 3,
                            LEFT_MID_ID = 4,
                            DWN_LEFT_ID = 5,
                            DWN_MID_ID = 6,
                            DWN_RIGHT_ID = 7,
                            RIGHT_MID_ID = 8;
                            
    private int Id = NONE_ID;
    
    private String name;
    
    /**
     * Creates a new instance of ScaleTouchHandle
     */
    public ScaleTouchHandle(VirTool virToolRef, int action, int Id, String name, int shape_Id, float a) 
    {
        super (virToolRef, action);
        
        BranchGroup bg = new BranchGroup();
        VToolFactory.setBGCapabilities(bg);
        VToolFactory.setTGCapabilities(this);
        SFrame shp = new SFrame (a, shape_Id);
//        Box shp = new Box (0.2f, 0.2f, 0.01f, Primitive.GEOMETRY_NOT_SHARED, null);
        bg.addChild(shp);
        this.addChild(bg);
        this.Id = Id;
        this.name = name;
    }

    public void onMousePressed() 
    {
        action = VToolForm.ACT_MOUSE_SCALETOUCHHANDLE_PRESSED;
        SlicerVirTool svt = (SlicerVirTool) virToolRef;
        svt.setHandleId(Id);
//        System.out.print("My name is: " + name + " & my modus is: " + modus + "  ");
    }

    public void onMouseDragged() 
    {
        action = VToolForm.ACT_MOUSE_SCALETOUCHHANDLE_DRAGGED;
    }

    public void onMouseReleased() 
    {
        action = VToolForm.ACT_MOUSE_SCALETOUCHHANDLE_RELEASED;
    }

    public int getId() 
    {
        return Id;
    }

    public void setId(int Id) 
    {
        this.Id = Id;
    }

    public void onMouseClicked() 
    {
    }
    
}
