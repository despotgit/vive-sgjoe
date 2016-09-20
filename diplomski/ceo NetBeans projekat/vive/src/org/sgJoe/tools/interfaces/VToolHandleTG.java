package org.sgJoe.tools.interfaces;

import org.apache.log4j.Logger;
import org.sgJoe.tools.decorators.*;


/*
 * Descritpion for VToolHandleTG.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: May 24, 2006  9:00 PM  $
 */

public class VToolHandleTG extends VToolHandle {
    
    private static Logger logger = Logger.getLogger(VToolHandleTG.class);

    private VToolHandle vToolHandleRef = null;
    
    public VToolHandleTG(VToolHandle vToolHandleRef) {
        super(vToolHandleRef.getVirToolRef(), vToolHandleRef.getAction());
        this.vToolHandleRef = vToolHandleRef;
    }

    public int getAction() {
        return vToolHandleRef.getAction();
    }

    public VirTool getVirToolRef() {
        return vToolHandleRef.getVirToolRef();
    }    
    
    public void setVirToolRef(VirTool virToolRef) {
        this.vToolHandleRef.setVirToolRef(virToolRef);
    }    
    
    public VToolHandle getVToolHandleRef() {
        return vToolHandleRef;
    }

    public void onMousePressed() {
        this.vToolHandleRef.onMousePressed();
    }

    public void onMouseClicked() {
        this.vToolHandleRef.onMouseClicked();
    }
        
    public void onMouseDragged() {
        this.vToolHandleRef.onMouseDragged();
    }

    public void onMouseReleased() {
        this.vToolHandleRef.onMouseReleased();
    }

}

