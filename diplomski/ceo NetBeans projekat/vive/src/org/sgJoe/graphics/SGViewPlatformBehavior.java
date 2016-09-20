package org.sgJoe.graphics;

import org.apache.log4j.Logger;


/*
 * Descritpion for SGViewPlatformBehavior.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: February 3, 2006  6:15 PM  $
 */

import javax.vecmath.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.universe.*;

/**
 * Abstract class for ViewPlatformBehaviors.  A ViewPlatformBehavior must
 * be added to the ViewingPlatform with the
 * ViewingPlatform.addViewPlatformBehavior() method.  The ViewPlatformBehavior
 * will operate on the ViewPlatform transform (the TransformGroup return by
 * ViewingPlatform.getViewPlatformTransform()).
 * @since Java 3D 1.2.1
 */
abstract public class SGViewPlatformBehavior extends Behavior {
    
    private static Logger logger = Logger.getLogger(SGViewPlatformBehavior.class);

    /**
     * The ViewingPlatform for this behavior.
     */
    protected SGViewingPlatform vp;

    /**
     * The target TransformGroup for this behavior.
     */
    protected TransformGroup targetTG;

    /**
     * The "home" transform for this behavior.  This is a transform used to
     * position and orient the ViewingPlatform to a known point of interest.
     *
     * @since Java 3D 1.3
     */
    protected Transform3D homeTransform = null;

    /**
     * Sets the ViewingPlatform for this behavior.  This method is called by
     * the ViewingPlatform.  If a sub-calls overrides this method, it must
     * call super.setViewingPlatform(vp).<p>
     * 
     * NOTE: Applications should <i>not</i> call this method.    
     *
     * @param vp the target ViewingPlatform for this behavior
     */
    public void setViewingPlatform(SGViewingPlatform vp) {
	this.vp = vp;
        
        if (vp!=null)
	    targetTG = vp.getViewPlatformTransform();
        else
            targetTG = null;
    }

    /**
     * Returns the ViewingPlatform for this behavior
     * @return the ViewingPlatform for this behavior
     */
    public SGViewingPlatform getViewingPlatform() {
	return vp;
    }

    /**
     * Copies the given Transform3D into the "home" transform, used to
     * position and reorient the ViewingPlatform to a known point of interest.
     * 
     * @param home source transform to be copied
     * @since Java 3D 1.3
     */
    public void setHomeTransform(Transform3D home) {
	if (homeTransform == null)
	    homeTransform = new Transform3D(home);
	else
	    homeTransform.set(home);
    }

    /**
     * Returns the behaviors "home" transform.
     * 
     * @param home transform to be returned
     * @since Java 3D 1.3
     */
    public void getHomeTransform(Transform3D home ) {
        home.set( homeTransform );
    }

    /**
     * Positions and reorients the ViewingPlatform to its "home" transform.
     * @since Java 3D 1.3
     */
    public void goHome() {
	if (targetTG != null && homeTransform != null)
	    targetTG.setTransform(homeTransform);
    }
}

