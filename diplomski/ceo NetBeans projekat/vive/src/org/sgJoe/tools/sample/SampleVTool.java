package org.sgJoe.tools.sample;

import com.sun.j3d.utils.geometry.ColorCube;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.TransformGroup;
import org.apache.log4j.Logger;
import org.sgJoe.logic.Controller;
import org.sgJoe.tools.*;
import org.sgJoe.tools.interfaces.*;


/*
 * Descritpion for SampleVTool.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: April 3, 2006  10:10 AM    $
 */

public class SampleVTool extends VTool {
    
    private static Logger logger = Logger.getLogger(SampleVTool.class);
    
    public static final int COORD_SYS_WORLD = 0,
                            COORD_SYS_VIEW  = 1;
    
    private int coordinateSystem = COORD_SYS_VIEW;
    
    private String text = "";
    
    public SampleVTool(VirTool virToolRef) {
        super(virToolRef);
        
        BranchGroup bgCube = new BranchGroup();
        VToolFactory.setBGCapabilities(bgCube);        
        
        // some geometry
        ColorCube cube = new ColorCube();
        bgCube.addChild(cube);
        
        this.addChild(bgCube);
    }
    
    public void setup() {
        
    }
    
    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }    

    public int getCoordinateSystem() {
        return coordinateSystem;
    }

    public void setCoordinateSystem(int coordinateSystem) {
        this.coordinateSystem = coordinateSystem;
    }

    public VToolTG getRotTG() {
        return null;
    }
}
