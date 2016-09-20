/*
 * iUpdateGUICaliper.java
 *
 * Created on Понедељак, 2006, Јуни 19, 13.43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.gui.toolPanels.interfaces;

/**
 * Interfejs implementira tool Panel
 * Ove metode poziva alat
 * @author nikola
 */
public interface iUpdateGUICaliper {
    //pri svakoj promeni koordinata neke od tacaka
    public void UpdatePointAPosition (int x, int y, int z);
    public void UpdatePointBPosition (int x, int y, int z);

    //prilikom lokovanja tacke
    public void setLockPointA (boolean value);
    public void setLockPointB (boolean value);
}
