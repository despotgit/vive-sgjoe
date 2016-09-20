/*
 * AngleLabel.java
 *
 * Created on June 18, 2006, 6:54 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.tools.caliper.labels;

import javax.media.j3d.*;
import javax.vecmath.*;
import org.VIVE.tools.caliper.CaliperSphereVTool;
import org.VIVE.tools.caliper.CaliperStickVTool;
import org.VIVE.tools.caliper.CaliperVirTool;
import org.sgJoe.tools.interfaces.*;

/**
 *
 * @author Milos Lakicevic
 */
public class AngleLabel extends OrientedLabelVTool 
{
    private int whichEndpoint;
    /** Creates a new instance of AngleLabel */
    public AngleLabel(VirTool virTool, int whichEndpoint) 
    {
        super(virTool);
        this.whichEndpoint = whichEndpoint;
    }

    public void updateLabel() 
    {
        Vector3d centar;
        CaliperVirTool toolRef = (CaliperVirTool)this.getVirToolRef();
        Transform3D translate = new Transform3D();
        
        double textHeight = 9 * ((CaliperVirTool) getVirToolRef()).getCaliperWidth();
        
        if (whichEndpoint == ((CaliperVirTool)this.getVirToolRef()).POINT_A)
               centar = new Vector3d(((CaliperVirTool)this.getVirToolRef()).getPointA());
        else
               centar = new Vector3d(((CaliperVirTool)this.getVirToolRef()).getPointB());
                        
        textGeometry.setPosition(new Point3f(0.0f, -(float)textHeight/2.0f,2.0f*(float)textHeight));
        
        translate.setTranslation(centar);
        translate.setScale(textHeight);
        this.setTransform(translate);
        
        Vector3d firstDirection,secDirection;
        double angle;
        
        if (whichEndpoint == toolRef.POINT_A && (toolRef.getPropVToolA() instanceof CaliperSphereVTool
                                              || toolRef.getPropVToolA() instanceof CaliperStickVTool))
        {            
            firstDirection = toolRef.getDirection();         
            secDirection = ((CaliperVirTool)toolRef.getPropVToolA().getVirToolRef()).getDirection();
            angle = firstDirection.angle(secDirection);
            angle*=1800/Math.PI;
            long a =  Math.round(angle);
            angle = a / 10.0;
            setText(Double.toString(angle)+"d");
            
        }else if (whichEndpoint == toolRef.POINT_B && (toolRef.getPropVToolB() instanceof CaliperSphereVTool
                                                    || toolRef.getPropVToolB() instanceof CaliperStickVTool))
        
        {            
            firstDirection = toolRef.getDirection();         
            secDirection = ((CaliperVirTool)toolRef.getPropVToolB().getVirToolRef()).getDirection();
            angle = firstDirection.angle(secDirection);
            angle*=1800/Math.PI;
            long a =  Math.round(angle);
            angle = a / 10.0;
            setText(Double.toString(angle)+"d");
        }else 
            setText("");
        
        
        
    }
    
}
