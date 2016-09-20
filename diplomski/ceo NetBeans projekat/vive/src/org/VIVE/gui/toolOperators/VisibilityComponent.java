/*
 * VisibilityComponent.java
 *
 * Created on Понедељак, 2006, Јуни 26, 12.17
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.gui.toolOperators;

/**
 *
 * @author nikola
 */
public class VisibilityComponent {
    
    /**
     * Creates a new instance of VisibilityComponent
     */
    private String name;
    private int ID;
    
    public VisibilityComponent(String n, int id) {
        setName(n);
        setID(id);
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    
}
