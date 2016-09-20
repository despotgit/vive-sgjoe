package org.sgJoe.graphics.behaviors;

import javax.vecmath.*;
import org.sgJoe.tools.sample.SampleVUIToolForm;
import org.sgJoe.tools.interfaces.*;

/*
 * This interface is an observer for the behaviours. The behaviours run
 * in their own thread. To communicate with other threads, use this
 * BehaviourObserver for backcalls.
 *
 * In next revision I'll try to trigger behaviuors through external custom events.
 *
 * @author   $ Author: Aleksandar Babic             $
 * @version  $ Revision:             0.1            $
 * @date     $ Date: December 1, 2005 11:14 AM      $
 */

public interface BehaviorObserver {
    
    /**
      * Updates the values for translation, scale and rotation. This is specially
      * used for the behaviours to update some GUI components.
      *
      * @param translation The translation vector.
      * @param scale The scale vector.
      * @param rotation The rotation vector.
      */
    
    public void update(Vector3d translation, Vector3d scale, Vector3d rotation);
    
    public void update(Point3d point3d);
    
    public void update(Point3d ptFirst, Point3d ptSecond);
    
    public void update (VUIToolForm vuiToolForm);
    
    public void update (VToolOperatorsForm vToolOperatorsForm);

    public void removeGUI(VUIToolForm sGGUIToolForm);
    
    public void removeGUI(VToolOperatorsForm vToolOperatorsForm);
    
    // specially designed for LogicGuiManager
    public void setFocusOnTool(VirTool virTool);
    public void toolRemoved (VirTool virTool);
}
