package org.sgJoe.tools.decorators;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import javax.media.j3d.Appearance;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Vector3d;
import org.apache.log4j.Logger;
import org.sgJoe.tools.VToolFactory;

import org.sgJoe.tools.interfaces.*;


/*
 * Description for SliderVToolHandle.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: April 22, 2006  10:34 AM   $
 */

public class SliderVToolHandle extends VToolHandle {
    
    private static Logger logger = Logger.getLogger(SliderVToolHandle.class);
    
    protected float dimX = 10.0f;
    protected float dimY = 10.0f;
    protected float dimZ = 0.01f;

    private BranchGroup parentBG = null;
    
    
    public SliderVToolHandle(VirTool virToolRef, int action) {     
        
        super(virToolRef, action);
                
        BranchGroup bg = new BranchGroup();
        VToolFactory.setBGCapabilities(bg);
        
        // some geometry for handle
        Appearance app = new Appearance();
        PolygonAttributes polyAttr = new PolygonAttributes();
        polyAttr.setPolygonMode(PolygonAttributes.POLYGON_LINE);
        app.setPolygonAttributes(polyAttr);
        TransparencyAttributes transpAttr=new TransparencyAttributes(TransparencyAttributes.FASTEST, 1f);
        app.setTransparencyAttributes(transpAttr);
        Box box = new Box(20.0f, 20.0f, 0.01f, Primitive.GEOMETRY_NOT_SHARED, app);
        
        bg.addChild(box);
        
        this.addChild(bg);        
    }

    public void onMousePressed() {
        this.action = VToolForm.ACT_MOUSE_PRESSED;
    }

    public void onMouseDragged() {
        this.action = VToolForm.ACT_MOUSE_TRANSLATE_XY;
    }

    public void onMouseReleased() {
        this.action = VToolForm.ACT_MOUSE_PLANARHANDLE_RELEASED;
    }

    public void onMouseClicked() {
        this.action = VToolForm.ACT_MOUSE_PLANARHANDLE_CLICKED;
    }
    
    public BranchGroup getParentBG() {
        return parentBG;
    }

    public void setParentBG(BranchGroup parentBG) {
        this.parentBG = parentBG;
    }
}
