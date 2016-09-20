/*
 * LengthLabel.java
 *
 * Created on June 9, 2006, 6:17 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.tools.caliper.labels;

import javax.vecmath.*;
import org.VIVE.tools.caliper.CaliperVirTool;
import org.sgJoe.tools.interfaces.*;

/**
 *
 * @author Milos Lakicevic
 */
public class LengthLabel extends OrientedLabelVTool{
    
    /**
     * Creates a new instance of LengthLabel
     */
    public LengthLabel(VirTool virTool) {
        super(virTool);
    }
    
    public void updateLabel() {
        Vector3d base = ((CaliperVirTool)this.getVirToolRef()).getPointA(), 
                 apex = ((CaliperVirTool)this.getVirToolRef()).getPointB();
        
        double textHeight = 9 * ((CaliperVirTool) getVirToolRef()).getCaliperWidth();
        
        Vector3d center = new Vector3d();
            center.x = (apex.x - base.x) / 2.0 + base.x;
            center.y = (apex.y - base.y) / 2.0 + base.y;
            center.z = (apex.z - base.z) / 2.0 + base.z;
        
        trans.setScale(textHeight);    
        trans.setTranslation(center);    
        this.setTransform(trans);
        
        textGeometry.setPosition(new Point3f(0.0f, -(float)textHeight/2.0f,2.0f*(float)textHeight));
        
        Vector3d difer = new Vector3d(apex);
        difer.sub(base);
        double longitude =difer.length();
        longitude*=10;
        long l=  Math.round(longitude);
        longitude = l / 10.0;
        if(longitude!=0.0d)
           this.setText(Double.toString(longitude));
        else
           this.setText("");
    }
    
}
