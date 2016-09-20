/*
 * DicerVPT3DObtainer.java
 *
 * Created on August 6, 2007, 6:49 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.tools.dicer;

import javax.media.j3d.*;
import javax.media.j3d.*;
import javax.vecmath.*;

/**
 *
 * @author Vladimir
 */
public class DicerVPT3DObtainer implements iDicerConstants
{
    Transform3D transTr = new Transform3D();
    Transform3D rotTr = new Transform3D();
    Transform3D scaleTr = new Transform3D();
    
    Transform3D hlp = new Transform3D();
    
    DicerVirTool dvt;
        
    /**
     * Creates a new instance of DicerVPT3DObtainer
     */
    public DicerVPT3DObtainer(DicerVirTool dvtool) 
    {
        dvt = dvtool;
        
        dvt.getTransTG().getTransform(transTr); 
        dvt.getRotTG().getTransform(rotTr); 
        dvt.getScaleTG().getTransform(scaleTr);
        
    }
    
    /* Following method returns the Transform3Ds onto which the ViewingPlatforms for a */
    /* specific Dicer will be attached,considering that VP looks in the -Z direction of its*/
    /* Transform3D */
    public Transform3D[] getVPTransform3Ds()
    {
        Transform3D[] t3ds = new Transform3D[6];
        
        /* all these T3Ds are modified in regard to Dicer's coordinate system,whose (0,0,0) is  */ 
        /* in the middle of every Dicer */
        /* T3D0 should be translated in +z direction */
        /* T3D1 should be rotated around y by PI degrees and then translated in -z direction */
        /* T3D2 should be rotated around y by PI/2 degrees and then translated in +x direction */
        /* T3D3 should be rotated around y by -PI/2 degrees and then translated in -x direction */
        /* T3D4 should be rotated around x by -PI/2 degrees and then translated in +y direction */
        /* T3D5 should be rotated around x by PI/2 degrees and then translated in -y direction */
        
        hlp.set(new Vector3d(0,0,vpDistance));                  /* t3d0 */
        t3ds[0].mul( getCumul(), hlp);
        
        hlp.rotY(Math.PI);
        hlp.setTranslation(new Vector3d(0,0,-vpDistance));      /* t3d1 */
        t3ds[1].mul(getCumul(),hlp);
        
        hlp.rotY(Math.PI/2);
        hlp.setTranslation(new Vector3d(vpDistance,0,0));       /* t3d2 */
        t3ds[2].mul(getCumul(),hlp);
        
        hlp.rotY(-Math.PI/2);
        hlp.setTranslation(new Vector3d(-vpDistance,0,0));       /* t3d3 */
        t3ds[3].mul(getCumul(),hlp);
        
        hlp.rotX(-Math.PI/2);
        hlp.setTranslation(new Vector3d(0,vpDistance,0));       /* t3d4 */
        t3ds[4].mul(getCumul(),hlp);
        
        hlp.rotX(Math.PI/2);
        hlp.setTranslation(new Vector3d(0,-vpDistance,0));       /* t3d5 */
        t3ds[5].mul(getCumul(),hlp);
        
        
        
        return t3ds;
    }
    
    /* following returns Transform3D for the Dicer as cumulative 3D transformation */
    private Transform3D getCumul()   
    {
        Transform3D tmp = new Transform3D();
        tmp.set(rotTr);
        tmp.mul(scaleTr);
        tmp.mul(transTr);
        
        return tmp;
    }
     
}
