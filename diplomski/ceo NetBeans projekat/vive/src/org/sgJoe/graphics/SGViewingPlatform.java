package org.sgJoe.graphics;

import com.sun.j3d.utils.universe.MultiTransformGroup;
import com.sun.j3d.utils.universe.PlatformGeometry;
import com.sun.j3d.utils.universe.Viewer;
import com.sun.j3d.utils.universe.ViewerAvatar;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.awt.Component;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.behaviors.vp.*;
import com.sun.j3d.internal.J3dUtilsI18N;

import org.apache.log4j.Logger;
import org.sgJoe.tools.VToolFactory;
import org.sgJoe.tools.interfaces.VTool;


/*
 * Descritpion for sgJViewingPlatform.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: February 3, 2006  3:43 PM  $
 */


/**
 * This class is used to set up the "view" side of a Java 3D scene graph.
 * The ViewingPlatform object contains a MultiTransformGroup node to allow
 * for a series of transforms to be linked together.  To this structure
 * the ViewPlatform is added as well as any geometry to associate with this
 * view platform.
 *
 * @see ViewPlatform
 */
public class SGViewingPlatform extends BranchGroup {
    
    private static Logger logger = Logger.getLogger(SGViewingPlatform.class);
    /**
     * Cached ViewPlatform associated with this ViewingPlatform object.
     */
    protected ViewPlatform        viewPlatform;

    /**
     * MultiTransformGroup that holds all TransformGroups between
     * the BranchGroup and the View object.
     */
    protected MultiTransformGroup mtg;

    /**
     * Used to keep track of added geometry.  When geometry
     * is added to the view platform, an addChild to this BranchGroup
     * is performed.
     */
    protected BranchGroup         platformGeometryRoot;

    /**
     * Used to keep track of added geometry.  When geometry
     * is added for an avatar, an addChild to this BranchGroup
     * is performed.
     */
    protected BranchGroup         avatarRoot;

    /**
     * Cached PlatformGeometry object.
     */
    protected PlatformGeometry    platformGeometry = null;

    /**
     * Table of the Viewer objects.
     */
    protected Hashtable		viewerList;

    /**
     * Used to keep track of behaviors.
     *
     * @since Java 3D 1.2.1
     */
    protected BranchGroup behaviors;

    /** 
     * The universe to which this viewing platform is attached
     *
     * @since Java 3D 1.3
     */
    //protected SimpleUniverse universe;
    protected SGUniverse sgUniverse;
    
    protected Transform3D tr3dCheckPoint = null;
    
    public void setCheckPoint(Transform3D tr3d) {
        this.tr3dCheckPoint = tr3d;
    }
    
    public Transform3D getCheckPoint( ) {
        return tr3dCheckPoint;
    }
    
    /**
     * Creates a default ViewingPlatform object.  This consists of a
     * MultiTransfromGroup node with one transform and a ViewPlatform
     * object. The ViewPlatform is positioned at (0.0, 0.0, 0.0).
     */
    public SGViewingPlatform() {
        // Call main constructor with default values.
        this(2);
    }

    /**
     * Creates the ViewingPlatform object.  This consists of a
     * MultiTransfromGroup node with the specified number of transforms
     * (all initialized to the identity transform).
     * and a ViewPlatform object.
     *
     * @param numTransforms The number of transforms the MultiTransformGroup
     *  node should contain.  If this number is less than 1, 1 is assumed.
     */
    public SGViewingPlatform(int numTransforms) {
        viewerList = new Hashtable();

        // Set default capabilities for this node.
        setCapability(Group.ALLOW_LOCAL_TO_VWORLD_READ);
        setCapability(Group.ALLOW_CHILDREN_WRITE);
        setCapability(Group.ALLOW_CHILDREN_EXTEND);
        setCapability(BranchGroup.ALLOW_DETACH);

        // Create MultiTransformGroup node.
        if (numTransforms < 1)
            numTransforms = 1;
        mtg = new MultiTransformGroup(numTransforms);
        

        // Get first transform and add it to the scene graph.
        TransformGroup tg = mtg.getTransformGroup(0);
        // ------------------------------------------------------------------------ //
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tg.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
        tg.setCapability(TransformGroup.ALLOW_LOCAL_TO_VWORLD_READ);
        
        // need for scene graph traversal
        tg.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
        tg.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
        tg.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);

        tg.setCapability(TransformGroup.ALLOW_BOUNDS_READ);
        tg.setCapability(TransformGroup.ALLOW_BOUNDS_READ);
        // ------------------------------------------------------------------------ //
        addChild(tg);

        // Create ViewPlatform and add it to the last transform in the
        // MultiTransformGroup node.
        tg = mtg.getTransformGroup(numTransforms - 1);
        viewPlatform = new ViewPlatform();
        viewPlatform.setCapability(ViewPlatform.ALLOW_POLICY_READ);
        viewPlatform.setCapability(ViewPlatform.ALLOW_POLICY_WRITE);
        viewPlatform.setCapability(ViewPlatform.ALLOW_LOCAL_TO_VWORLD_READ);
        tg.addChild(viewPlatform);

        // Set capabilities to allow for changes when live.
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        // Initialize the avatarRoot BranchGroup node and add it to the
        // last transform in the MultiTransformGroup node.
        avatarRoot = new BranchGroup();
        avatarRoot.setCapability(Group.ALLOW_CHILDREN_READ);
        avatarRoot.setCapability(Group.ALLOW_CHILDREN_WRITE);
        avatarRoot.setCapability(Group.ALLOW_CHILDREN_EXTEND);
        tg.addChild(avatarRoot);

        // Initialize the platformGeometry BranchGroup node and add it to the
        // last transform in the MultiTransformGroup node.
        platformGeometryRoot = new BranchGroup();
        platformGeometryRoot.setCapability(Group.ALLOW_CHILDREN_READ);
        platformGeometryRoot.setCapability(Group.ALLOW_CHILDREN_WRITE);
        platformGeometryRoot.setCapability(Group.ALLOW_CHILDREN_EXTEND);
        tg.addChild(platformGeometryRoot);
    }

    /**
     * Sets the ViewPlatform node for this ViewingPlatform object.
     *
     * @param vp The ViewPlatform node to associate with this ViewingPlatform
     *  object.
     */
    public void setViewPlatform(ViewPlatform vp) {
	TransformGroup tg = getViewPlatformTransform();
	tg.removeChild(viewPlatform);
	tg.addChild(vp);
        viewPlatform = vp;
        // Assign this to all Viewers.
        Enumeration e = viewerList.keys();

        while (e.hasMoreElements())
            ((SGViewer)e.nextElement()).setViewingPlatform(this);
    }

    /**
     * Returns the ViewPlatform node for this ViewingPlatform object.
     *
     * @return The ViewPlatform node associated with this ViewingPlatform
     *  object.
     */
    public ViewPlatform getViewPlatform() {
        return viewPlatform;
    }

    /**
     * Assigns the geometry to associate with the ViewingPlatform.
     * PlatformGeometry is used to hold any geometry to be associated
     * with the ViewingPlatform.  If the ViewingPlatform is to be the
     * inside of a car, for instance, than the PlatformGeometry could be
     * the dashboard of the car.
     * 
     * @param pg The geometry to be associated with this ViewingPlatform.
     *  Passing in null has the effect of deleting any geometry associated
     *  with this ViewingPlatform.
     */
    public void setPlatformGeometry(PlatformGeometry pg) {
        // Just return if trying to set the same PlatformGeometry object.
        if (platformGeometry == pg)
            return;

        // If the PlatformGeometry is null, will be removing any geometry
        // already present.
        
        if (pg == null) {
            if (platformGeometryRoot.numChildren() != 0)
                platformGeometryRoot.removeChild(0);
        }
        else {

            // See if there is an old PlatformGeometry to replace.
            if (platformGeometryRoot.numChildren() != 0)
                platformGeometryRoot.setChild(pg, 0);
            else {
                platformGeometryRoot.addChild(pg);
            }
        }
        platformGeometry = pg;
    }

    /**
      * Returns the PlatformGeometry associated with this ViewingPlatform
      *
      * @return The PlatformGeometry associated with this ViewingPlatform
      */
    public PlatformGeometry getPlatformGeometry() {
	return platformGeometry;
    }

    /**
     * Returns the MultitransformGroup object for this
     * ViewingPlatform object.
     *
     * @return The MultitransformGroup object.
     */
    public MultiTransformGroup getMultiTransformGroup() {
        return mtg;
    }

    /**
     * Returns a reference to the "bottom most" transform in the
     * MultiTransformGroup that is above the ViewPlatform node.
     *
     * @return The TransformGroup that is immediately above the
     *  ViewPlatform object.
     */
    public TransformGroup getViewPlatformTransform() {
        return mtg.getTransformGroup(mtg.getNumTransforms() - 1);
    }

    /**
     * Sets the nominal viewing distance in the ViewPlatform transform based
     * on the current field of view.  If the ViewAttachPolicy is not the
     * default of View.NOMINAL_HEAD, then this method has no effect.<p>
     * 
     * The ViewPlatform is moved back along Z so that objects at the origin
     * spanning the normalized X range of -1.0 to +1.0 can be fully viewed
     * across the width of the window.  This is done by setting a translation
     * of 1/(tan(fieldOfView/2)) in the ViewPlatform transform.<p>
     *
     * If there is no Viewer object associated with this ViewingPlatform
     * object the default field of view of PI/4.0 is used.<p>
     * 
     * NOTE: Support for multiple Viewer objects is not available.  If
     * multiple viewers are attached to this ViewingPlatform than a
     * RuntimeException will be thrown.
     */
//    public void setNominalViewingTransform() {
//	if (viewPlatform.getViewAttachPolicy() == View.NOMINAL_HEAD) {
//	    double fieldOfView;
//
//	    if (viewerList.size() == 0) {
//		// No Viewer associated with this ViewingPlatform, so use the
//		// default field of view value to move the ViewingPlatform.
//		fieldOfView = Math.PI/4.0;
//	    }
//	    else {
//		if (viewerList.size() > 1) {
//		    throw new RuntimeException
//			(J3dUtilsI18N.getString("ViewingPlatform0"));
//		}
//
//		Viewer viewer = (Viewer)viewerList.keys().nextElement();
//		View view = viewer.getView();
//		fieldOfView = view.getFieldOfView();
//	    }
//
//	    Transform3D t3d = new Transform3D();
//	    double viewDistance = 1.0/Math.tan(fieldOfView/2.0);
//	    t3d.set(new Vector3d(0.0, 0.0, viewDistance));
//	    getViewPlatformTransform().setTransform(t3d);
//	}
//    }

    /**
     * Returns the avatarRoot child number of the ViewerAvatar object.
     * All the children of the avatarRoot are compared with the passed
     * in ViewerAvatar.  If a match is found, the index is returned.
     *
     * @param avatar The ViewerAvatar object to look for in the avatarRoot's
     *  child nodes.
     * @return The index of the child that corresponds to the ViewerAvatar.
     *  If the avatarRoot does not contain the ViewerAvatar -1 is returned.
     */
    private int findAvatarChild(ViewerAvatar avatar) {
        // Search the avatarRoot for the ViewerAvatar associated with
        // with the Viewer object
        for (int i = 0; i < avatarRoot.numChildren(); i++) {
            if (((ViewerAvatar)avatarRoot.getChild(i)) == avatar)
                return i;
        }

        // Should never get here.
        System.err.println("ViewingPlatform.findAvatarChild:Child not found.");
        return -1;
    }

    /**
     * Adds the ViewerAvatar to the scene graph.  An avatar (geometry)
     * can be associated with a Viewer object and displayed by Java 3D.
     *
     * @param viewer The viewer object to associate with this avatar.
     * @param avatar The avatar to add to the scene graph.  Passing in
     *  null removes any currently assigned avatar.
     */
    void setAvatar(SGViewer viewer, ViewerAvatar avatar) {
        Object oldAvatar = viewerList.get(viewer);

        // A position of -1 means the avatar is not a child of the avatarRoot.
        int avatarPosition = -1;

        // Because "null" cannot be used in a put the avatarRoot object
        // is used to signify that there is no ViewerAvatar associated
        // with this Viewer.
        if (oldAvatar != avatarRoot)
            avatarPosition = findAvatarChild((ViewerAvatar)oldAvatar);

        // If the avatar is null, will be removing any geometry already present.
        if (avatar == null) {
            if (avatarPosition != -1) {
                avatarRoot.removeChild(avatarPosition);

                // Reset hashtable entry - avatarRoot == null.
                viewerList.put(viewer, avatarRoot);
            }
        }
        else {
            // see if there is an old ViewerAvater to replace
            if (avatarPosition != -1)
                avatarRoot.setChild(avatar, avatarPosition);
            else
                avatarRoot.addChild(avatar);

            // Update hashtable with new avatar.
            viewerList.put(viewer, avatar);
        }
    }

    /**
     * When a ViewingPlatform is set by a Viewer, the ViewingPlatform
     * needs to be informed, via a call to this method.  This will add
     * the Viewer to the ViewingPlatform's viewerList for use when
     * things such as the PlatformGeometry are changed and all Viewer
     * scene graphs need to be modified.
     */
    void addViewer(SGViewer viewer) {
        viewerList.put(viewer, avatarRoot);
    }

    /*
     * Cleanup when Viewer set another ViewingPlatform
     */
//    void removeViewer(sgJViewer viewer) {
//	viewerList.remove(viewer);
//    }
    
    /**
     * Adds a new ViewPlatformBehavior to the ViewingPlatform
     */
    void addViewPlatformBehavior(SGViewPlatformBehavior behavior) {
	behavior.setViewingPlatform(this);
 	if (behaviors == null) {
 	    behaviors = new BranchGroup();
 	    behaviors.setCapability(BranchGroup.ALLOW_DETACH);
	    behaviors.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
 	}
 	// otherwise detach the BranchGroup so we can add to it
	else {
	    behaviors.detach();
	}
	behaviors.addChild(behavior);
	this.addChild(behaviors);
    }

    /**
     * Sets the ViewPlatformBehavior which will operate on the ViewPlatform
     * transform (the TransformGroup returned by
     * ViewingPlatform.getViewPlatformTransform()). The ViewPlatformBehavior
     * may be set after the ViewingPlatform is setLive().
     * If a behavior is already present, it will be detached and it's
     * setViewingPlatform method will be called with a parameter of null.
     * @param behavior The ViewPlatformBehavior to add to the ViewingPlatform.
     * null will remove the ViewingPlatform behavior.
     * @since Java 3D 1.2.1
     */
    public void setViewPlatformBehavior(SGViewPlatformBehavior behavior) 
    {
	if (behaviors != null) 
        {
	    removeViewPlatformBehavior((SGViewPlatformBehavior)behaviors.getChild(0));
	}
	if (behavior != null) 
        {
	    addViewPlatformBehavior(behavior);
	}
    }

    /**
     * Removes the specified ViewPlatformBehavior
     */
    void removeViewPlatformBehavior(SGViewPlatformBehavior behavior) {
	// remove from the behaviors branch group
	if (behaviors != null) {
	    behaviors.detach();
	    for (int i = 0; i < behaviors.numChildren(); i++) {
		if (behaviors.getChild(i) == behavior) {
	            behavior.setViewingPlatform( null );
		    behaviors.removeChild(i);
		    break;
		}
	    }
	    if (behaviors.numChildren() == 0) behaviors = null;
	    else this.addChild(behaviors);
	}
    }
    
    /**
     * Returns the number of ViewPlatformBehaviors on the ViewingPlatform
     */
//    int getViewPlatformBehaviorCount() {
//	return behaviors.numChildren();
//    }
    
    /**
     * Returns the ViewPlatformBehavior at the specified index
     */
//    ViewPlatformBehavior getViewPlatformBehavior(int index) {
//	return (ViewPlatformBehavior)behaviors.getChild(index);
//    }

    /**
     * Returns the ViewPlatformBehavior
     * @return the ViewPlatformBehavior for the ViewingPlatform.
     * Returns null if there is no ViewPlatformBehavior set.
     * @since Java 3D 1.2.1
     */
//    public ViewPlatformBehavior getViewPlatformBehavior() {
//	if (behaviors == null) {
//	    return null;
//	}
//	return getViewPlatformBehavior(0);
//    }
    
    /**
     * Returns the Viewers attached to this ViewingPlatform
     *
     * @return the Viewers attached to this viewing platform
     * @since Java 3D 1.3
     */
//    public Viewer[] getViewers() {
//	if (viewerList.size() == 0) return null;
//        return (Viewer[])viewerList.keySet().toArray( new Viewer[0] );
//    }
    
    /**
     * Returns the Universe to which this ViewingPlatform is attached
     *
     * @return the Universe to which this ViewingPlatform is attached
     * @since Java 3D 1.3
     */
    public SGUniverse getUniverse() {
        return sgUniverse;
    }
    
    /**
     * Sets the Universe to which this ViewingPlatform is attached
     *
     * @param universe the Universe to which this ViewingPlatform is attached
     * @since Java 3D 1.3
     */
    public void setUniverse( SGUniverse universe ) {
        this.sgUniverse = universe;
    }
}
