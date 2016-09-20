package org.sgJoe.tools.templates.translate.sphere;


import javax.media.j3d.BranchGroup;
import org.apache.log4j.Logger;
import org.sgJoe.tools.templates.translate.*;


/*
 * Descritpion for VirPlaneSphereTool.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: June 19, 2006  11:33 AM  $
 */

public class VirPlaneSphereTool extends VirPlaneTool {
    
    private static Logger logger = Logger.getLogger(VirPlaneSphereTool.class);
    
    private static long counter = 0;
    
    public void createToolInstance(String string) {
        this.setVToolRef(new PlaneSphereTool(this));            
        BranchGroup bg = new BranchGroup();
        bg.addChild(this.getVToolRef());
        this.getToolBaseSWG().addChild(bg);
        this.toolBaseTG.addChild(getToolBaseSWG());        
    }   
    
    public long generateUID() {
        return ++counter;
    }
    
}
