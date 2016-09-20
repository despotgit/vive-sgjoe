/*
 * iSpatialField.java
 *
 * Created on July 22, 2007, 11:34 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.sgJoe.tools.interfaces;

import javax.media.j3d.*;
import javax.media.j3d.*;
import javax.vecmath.*;
/**
 *
 * @author Vladimir Despotovic
 */
public interface iSpatialField 
{
  //  public SFieldParameters getSpatialFieldParameters();
    public Texture getSpatialTexture(Point3d upperLeft , Point3d lowerLeft , 
                                     Point3d lowerRight , Point3d upperRight , int Quality);
    public static int LOW=1, 
                      MEDIUM=2, 
                      HIGH=3;   // za Resolution polje

    
}
