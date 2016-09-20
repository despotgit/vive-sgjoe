/*
 * iToolBox.java
 *
 * Created on Четвртак, 2006, Мај 11, 17.39
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.gui.logic.interfaces;

import org.sgJoe.tools.interfaces.*;

/**
 * Interface prazan jer niko ne poziva ToolBox
 * @author nikola
 */
public interface iToolBox {
    public void createTool(VirTool Tool);
    public void createMain(VirTool Tool);
    public void setToolRegistry (Object toolRegistry);
    public void setToolPanel (Object toolPanel);
    public Object getToolRegistry ();
    public Object getToolPanel ();
    
    public Object getSGPanel ();    
    public void setSGPanel (Object sgPanel);
    
    
    //Preuzeto iz koda addingToolPanel, da bi se izdvojila logika od GUI-a
    public void setActiveToolList (iToolPanel yourTabbedPanel);
    public int getToolcount (int i);
    public void setToolCount (int i, int value);
    public void incrementToolCount(int i);
    public boolean decrementToolCount(int i);
}
