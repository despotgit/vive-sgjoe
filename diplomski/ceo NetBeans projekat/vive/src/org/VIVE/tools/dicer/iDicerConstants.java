/*
 * DicerConstants.java
 *
 * Created on May 1, 2007, 12:21 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.tools.dicer;

import javax.media.j3d.*;
import javax.vecmath.*;

/**
 *
 * @author Vladimir
 */
public interface iDicerConstants 
{
    public Color3f cornerColor = new Color3f(0.85f , 0.85f , 0.85f);
    public Color3f cornerColor2 = new Color3f(0.5f , 0.5f , 0.5f);     //this is an alternative to distinction lines,maybe it is better to use slightly different colors for sides of a corner/edge
    public Color3f edgeColor = new Color3f(0.95f , 0.95f , 0.95f);
    public Color3f faceColor = new Color3f(0.8f , 1.0f , 1.0f);
    public Color3f distinctionColor = new Color3f(0.7f , 0.7f , 0.7f);
    public Color3f wireframeColor = new Color3f( 0.0f, 1.0f , 0.0f);
     
    public static final float INIT_DICER_LENGTH = 1.0f;        //Initial dicer dimensions
    public static final float INIT_DICER_HEIGHT = 1.0f;        //actually,these dimensions 
    public static final float INIT_DICER_WIDTH = 1.0f;         //are doubled(cause is:Box constructor)
    
    public static final float INIT_EDGE_LENGTH = 1.8f;         //Initial handles' dimensions               
    public static final float INIT_EDGE_WIDTH = 0.15f;         //(these are real)
    public static final float INIT_CORNER_DIM = 0.3f; 
    
    public static final double ERV = 1.075;                    //edge referent value
    public static final double CRV = 1.15;                     //corner referent value
    
    public final double vpDistance = 4.0d;                     //distance from viewer to the dicer
    
    public static final int MODE_NONE = -1,                    //nothing 4 now
          
          DICER_TRANSLATION = 10,  
         
          DICER_TRANSLATING0 = 0,             //0 means frontal translation
          DICER_TRANSLATING1 = 1,             //1 means back translation
          DICER_TRANSLATING2 = 2,             //2 means right translation 
          DICER_TRANSLATING3 = 3,             //3 means left translation
          DICER_TRANSLATING4 = 4,             //4 means top translation
          DICER_TRANSLATING5 = 5,             //5 means bottom translation
    
          DICER_SCALING = 20,  
            
          DICER_SCALING0 = 6,
          DICER_SCALING1 = 7,
          DICER_SCALING2 = 8,
          DICER_SCALING3 = 9,
          DICER_SCALING4 = 10,
          DICER_SCALING5 = 11;    
                  
          
}
