package org.sgJoe.graphics.behaviors;

import javax.media.j3d.*;

import com.sun.j3d.utils.picking.PickResult;

import org.apache.log4j.Logger;


/*
 * Superclass for all custom behaviors
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: December 5, 2005  4:41 PM  $
 */

public abstract class CustomBehavior extends Behavior 
{
    
    protected BehaviorObserver behaviorObserver = null;
    public static boolean pickEntireSubgraph = true;
  
    public CustomBehavior() 
    { 
    }
  
    /**
     * Add a behaviour observer. This is used to make backcalls. This is
     * indented if a specific behaviour changed a 3d object form the scene
     * and the new position, rotation angle and scale factor has to be updated.
     *
     * @param behaviourObserver The behaviour observer to add.
     */
    public void addBehaviourObserver(BehaviorObserver bo) 
    {
        behaviorObserver = bo;
    }
  
    /**
     * This method returns the TransformGroup that is closest to the scene
     * graph's top, beginning from the node found in the PickResult.
     * This is used to transform several objects that are children of one
     * TransformGroup.
     * 
     * @param pickResult the PickResult that needs to be examined
     * @return TransformGroup the last TransformGroup in the scene graph path
     */
    protected TransformGroup getLastTransformGroup(PickResult pickResult) 
    {
        
        TransformGroup transformGroup = null;
    
        if (pickEntireSubgraph) 
        {
            // get the topmost TransformGroup
        
            SceneGraphPath sgp = pickResult.getSceneGraphPath();
            for (int i = sgp.nodeCount() - 1; i >= 0; i--) 
            {
                Node pNode = sgp.getNode(i);

                if (pNode instanceof TransformGroup) 
                {
                    transformGroup = (TransformGroup)pNode;
                }
            }
        } 
        else 
        { 
            // get the closest TransformGroup
    
            transformGroup = (TransformGroup)pickResult.getNode(PickResult.TRANSFORM_GROUP);
        }
    
        return transformGroup;
    }
    
    protected TransformGroup getLastTransformGroup(PickResult pickResult, boolean entireSubGraph) 
    {
        //System.out.println("Ushli u CustomBehavior davnih dana.");
        TransformGroup transformGroup = null;
    
        if (entireSubGraph) 
        {
            // get the topmost TransformGroup
        
            SceneGraphPath sgp = pickResult.getSceneGraphPath();
            for (int i = sgp.nodeCount() - 1; i >= 0; i--) 
            {
                Node pNode = sgp.getNode(i);

                if (pNode instanceof TransformGroup) 
                {
                    transformGroup = (TransformGroup)pNode;
                }
            }
        } 
        else 
        { 
            // get the closest TransformGroup
    
            transformGroup = (TransformGroup)pickResult.getNode(PickResult.TRANSFORM_GROUP);
        }
    
        return transformGroup;        
    }
}
