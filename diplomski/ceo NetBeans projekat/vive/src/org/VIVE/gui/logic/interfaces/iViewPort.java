/*
 * iViewPort.java
 *
 * Created on Четвртак, 2006, Мај 11, 17.39
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.gui.logic.interfaces;

/**
 *
 * @author nikola
 */
public interface iViewPort {
    public static final int EMPTY = 0, J3D_VIEW_PORT = 1, TOOL_VIEW_PORT = 2;
    public int getMaxNoViewPorts ();
    public int getNoViewPorts();
    public void addViewPort(int Type);
    
    public Object getSGPanel ();    
    public void setSGPanel (Object sgPanel);
}
