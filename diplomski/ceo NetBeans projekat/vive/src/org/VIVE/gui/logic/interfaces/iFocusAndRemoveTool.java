/*
 * iFocusAndRemoveTool.java
 *
 * Created on June 19, 2006, 7:34 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.gui.logic.interfaces;

import org.sgJoe.tools.interfaces.VirTool;

/**
 *
 * @author ealebab
 */
public interface iFocusAndRemoveTool {
    //Ovu zoves kada alat dobija fokus
    public void setFocusOnTool (VirTool virTool);
    //Ovu zoves kada se izbacuje alat sa scene
    public void onToolRemoved (VirTool virTool);
}
