/*
 * DicerScaleHandle.java
 *
 * Created on Sreda, 2006, Maj 31, 22.45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package dicerBkp;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import javax.media.j3d.BranchGroup;
import org.VIVE.tools.dicerJunk.cornerWF;
import org.VIVE.tools.dicerJunk.edgeWF;
import org.apache.log4j.Logger;
import org.sgJoe.tools.VToolFactory;
import org.sgJoe.tools.decorators.VToolHandle;
import org.sgJoe.tools.interfaces.VToolForm;
import org.sgJoe.tools.interfaces.VirTool;

/**
 *
 * @author Vladimir Despotovic
 */
public class DicerScaleHandle extends VToolHandle 
{    
    private static Logger logger = Logger.getLogger(DicerScaleHandle.class);
    
    public static final int NONE_ID = 0,
            //edges:
            
                            FRONT_UP_ID = 1,
                            FRONT_DOWN_ID = 2,
                            FRONT_LEFT_ID = 3,
                            FRONT_RIGHT_ID = 4,
                            
                            BACK_UP_ID = 5,
                            BACK_DOWN_ID = 6,
                            BACK_LEFT_ID = 7,
                            BACK_RIGHT_ID = 8,
            
                            SIDE_UPPER_LEFT_ID=9,
                            SIDE_LOWER_LEFT_ID=10,
                            SIDE_LOWER_RIGHT_ID=11,
                            SIDE_UPPER_RIGHT_ID=12,
            
            //corners:
            
                            FRONT_UPPER_LEFT_ID = 13,
                            FRONT_LOWER_LEFT_ID=14,
                            FRONT_LOWER_RIGHT_ID=15,
                            FRONT_UPPER_RIGHT_ID=16,
                            
                            BACK_UPPER_LEFT_ID = 17,
                            BACK_LOWER_LEFT_ID=18,
                            BACK_LOWER_RIGHT_ID=19,
                            BACK_UPPER_RIGHT_ID=20;                  
            
           
    private int Id = NONE_ID;
    
    private String name;
    
    /**
     * Creates a new instance of DicerScaleHandle
     */
    
    // corner handle constructor1(for filled corner)    
    public DicerScaleHandle(VirTool virToolRef, int action,int Id, String name, float a) 
    {
        super (virToolRef, action);
        
        BranchGroup bg = new BranchGroup();
        VToolFactory.setBGCapabilities(bg);
        VToolFactory.setTGCapabilities(this);
        corner cosak=new corner(a);
        
        bg.addChild(cosak);
        this.addChild(bg);
        this.Id = Id;    //  This variable marks the position of the handle in the Dicer
        this.name = name;
    }
    
    //corner handle constructor2(for wireframed corner)
    public DicerScaleHandle(VirTool virToolRef, int action,int Id, String name, float a,boolean justLines) 
    {
        super (virToolRef, action);
        
        BranchGroup bg = new BranchGroup();
        VToolFactory.setBGCapabilities(bg);
        VToolFactory.setTGCapabilities(this);
        cornerWF cosak=new cornerWF(a,justLines);
        
        bg.addChild(cosak);
        this.addChild(bg);
        this.Id = Id;    //  This variable marks the position of the handle in the Dicer
        this.name = name;
    }
    
    
    // edge handle constructor1(for filled edge)    
    public DicerScaleHandle(VirTool virToolRef,int action,int Id,String name,float a,float b)
    {
        super(virToolRef,action);
        BranchGroup bg = new BranchGroup();
        VToolFactory.setBGCapabilities(bg);
        VToolFactory.setTGCapabilities(this);
        edge handl=new edge(a,b);
        
        bg.addChild(handl);
        this.addChild(bg);
        this.Id = Id;             // The position of the handle in Dicer as integer
        this.name = name;        
    }    
    
    // edge handle constructor2(for wireframed edge)    
    public DicerScaleHandle(VirTool virToolRef,int action,int Id,String name,float a,float b,boolean justLines)
    {
        super(virToolRef,action);
        BranchGroup bg = new BranchGroup();
        VToolFactory.setBGCapabilities(bg);
        VToolFactory.setTGCapabilities(this);
        edgeWF handl=new edgeWF(a,b,justLines);
        
        bg.addChild(handl);
        this.addChild(bg);
        this.Id = Id;             // The position of the handle in Dicer as integer
        this.name = name;        
    }    
    
    
    public void onMousePressed() 
    {
        action = VToolForm.ACT_MOUSE_SCALETOUCHHANDLE_PRESSED;
        DicerVirTool dvt = (DicerVirTool) virToolRef;
        dvt.setHandleId(Id);                             //Now dVirTool knows which handle's been pressed
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
