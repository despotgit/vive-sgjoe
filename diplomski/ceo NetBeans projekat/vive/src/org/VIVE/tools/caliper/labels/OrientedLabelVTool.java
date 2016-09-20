/*
 * OrientedLabelVTool.java
 *
 * Created on June 8, 2006, 7:26 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.tools.caliper.labels;

import java.lang.*;
import java.awt.Font;
import javax.media.j3d.*;
import javax.vecmath.*;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import org.sgJoe.graphics.event.*;
import org.sgJoe.tools.VToolFactory;
import org.sgJoe.tools.interfaces.*;

/**
 *
 * @author Milos Lakicevic
 */
public abstract class OrientedLabelVTool extends VTool{
    
    protected Text3D textGeometry;
    
    protected Transform3D trans = new Transform3D();
    
    /**
     * Creates a new instance of OrientedLabelVTool
     */
    public OrientedLabelVTool(VirTool virToolRef) {
         super(virToolRef);   
    }

 

    public void setup() {
        
         BranchGroup bg = new BranchGroup();
         VToolFactory.setBGCapabilities(bg);      
        
         ColoringAttributes textColor = new ColoringAttributes();
         textColor.setColor(0.0f, 1.0f, 0.0f);
         
         
         Appearance textAppear = new Appearance();
         textAppear.setColoringAttributes(textColor);
 
         Font3D font3D = new Font3D(new Font("Monospaced", Font.BOLD,1),
                                    new FontExtrusion());
         textGeometry = new Text3D(font3D, new String(""));
         textGeometry.setCapability(Text3D.ALLOW_POSITION_WRITE);
         textGeometry.setCapability(Text3D.ALLOW_POSITION_READ);
         textGeometry.setCapability(Text3D.ALLOW_STRING_READ);
         textGeometry.setCapability(Text3D.ALLOW_STRING_WRITE);
                  
         textGeometry.setAlignment(Text3D.ALIGN_CENTER);
         
         OrientedShape3D textShape = new OrientedShape3D();
         
         textShape.setGeometry(textGeometry);
         textShape.setAppearance(textAppear);
      
         bg.addChild(textShape);         
         this.addChild(bg);
         
         this.updateLabel();

    }
    
    

    public String getText() {
        return textGeometry.getString();
    }

    public void setText(String text) {
        textGeometry.setString(text);
    }

    abstract void updateLabel(); 
    
    
}
