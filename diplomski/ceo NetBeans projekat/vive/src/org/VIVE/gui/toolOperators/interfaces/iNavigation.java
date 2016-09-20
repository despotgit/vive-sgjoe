/*
 * iNavigation.java
 *
 * Created on Понедељак, 2006, Јуни 19, 9.30
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.gui.toolOperators.interfaces;

/**
 *
 * @author nikola
 */
public interface iNavigation {

    public void moveTool(int constMove);
    public void setInitSize();
    public void setInitRot();
    public void setInitTrans();
    
    public void setPositionSizeRotation(int PSR);
    public void setScale(int scale);

}
