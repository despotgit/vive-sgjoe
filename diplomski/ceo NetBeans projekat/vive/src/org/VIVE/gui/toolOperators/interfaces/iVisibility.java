/*
 * iVisibility.java
 *
 * Created on Понедељак, 2006, Јуни 19, 9.29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.gui.toolOperators.interfaces;

/**
 *
 * @author nikola
 */
public interface iVisibility {
    
    /**
     * Ovaj interfejs treba da implemantira klasa koja podrzava 
     * visibility operator. Metodu zove odgovarajuci GUI kontroler
     * kod koga je alat prijavljen.
     * Nivoi od 0 do BrojNivoa-1, prosledjuju se pri konstrukciji OperatorPanela
     *
     */
    public void setVisibilityLevel(int level);
    
}
