package org.sgJoe.graphics;


import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.universe.LocaleFactory;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.Viewer;
import com.sun.j3d.utils.universe.ViewingPlatform;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsConfiguration;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;


import javax.media.j3d.*;

import org.apache.log4j.Logger;
import org.sgJoe.graphics.*;
import org.sgJoe.graphics.SGViewingPlatform;



/**
 * Description for SGVirtualUniverse class:
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:            0.1         $
 * @date     $ Date:  February 2, 2006 8:11 PM  $
 */

/**
 * This class sets up a minimal user environment to quickly and easily
 * get a Java 3D program up and running.  This utility class creates
 * all the necessary objects on the "view" side of the scene graph.
 * Specifically, this class creates a locale, a single ViewingPlatform, 
 * and a Viewer object (both with their default values).
 * Many basic Java 3D applications
 * will find that SimpleUniverse provides all necessary functionality
 * needed by their applications. More sophisticated applications
 * may find that they need more control in order to get extra functionality
 * and will not be able to use this class.
 * 
 * @see Viewer
 * @see ViewingPlatform
 */
public class SGUniverse extends VirtualUniverse {
    
    private static Logger logger = Logger.getLogger(SGUniverse.class);

    /**
     * Locale reference needed to create the "view" portion
     * of the scene graph.
     */
    protected Locale          locale;

    /**
     * Viewer reference needed to create the "view" portion
     * of the scene graph.
     */
    protected HashMap        viewerMap = new HashMap();

    /**
     * Creates a locale, a single ViewingPlatform, and
     * and a Viewer object (both with their default values).
     *
     * @see Locale
     * @see Viewer
     * @see ViewingPlatform
     */
    public SGUniverse(Object key) 
    {
        // call main constructor with default values.
        this(key, null, 1, null, null);
    }

    /**
     * Creates a locale, a single ViewingPlatform, and a Viewer object
     * (with default values).  The ViewingPlatform is created with the
     * specified number of TransformGroups.
     *
     * @param numTransforms The number of transforms to be in the
     * MultiTransformGroup object.
     *
     * @see Locale
     * @see Viewer
     * @see ViewingPlatform
     *
     * @since Java 3D 1.2.1
     */
//    public SGVirtualUniverse(int numTransforms) {
//	// call main constructor with default values except numTransforms
//	this(null, numTransforms, null, null);
//    }

    /**
     * Creates a locale, a single ViewingPlatform (with default values), and
     * and a Viewer object.  The Viewer object uses default values for
     * everything but the canvas.
     *
     * @param canvas The canvas to associate with the Viewer object.  Passing
     *  in null will cause this parameter to be ignored and a canvas to be
     *  created by the utility.
     *
     * @see Locale
     * @see Viewer
     * @see ViewingPlatform
     */
    public SGUniverse(Object key, Canvas3D canvas) 
    {
        // call main constructor with default values for everything but
        // the canvas parameter.
        this(key, null, 1, canvas, null);
    }

    public SGUniverse() 
    {
        // call main constructor with default values for everything but
        // the canvas parameter.
        this(null, 1, null);
    }
    
    public void addViewPlatformGraph(Object key, Canvas3D canvas, Transform3D transform) {
        
    }
    /**
     * Creates a locale, a single ViewingPlatform, and a Viewer object
     * The Viewer object uses default values for everything but the canvas.
     * The ViewingPlatform is created with the specified number of
     * TransformGroups.
     *
     * @param canvas The canvas to associate with the Viewer object.  Passing
     * in null will cause this parameter to be ignored and a canvas to be
     * created by the utility.
     * @param numTransforms The number of transforms to be in the
     * MultiTransformGroup object.
     *
     * @see Locale
     * @see Viewer
     * @see ViewingPlatform
     * @see MultiTransformGroup
     *
     * @since Java 3D 1.2.1
     */
//    public SGVirtualUniverse(Canvas3D canvas, int numTransforms) {
//	// call main constructor with default values except canvas
//	// and numTransforms
//	this(null, numTransforms, canvas, null);
//    }

    /**
     * Creates the "view" side of the scene graph.  The passed in parameters
     * override the default values where appropriate.
     *
     * @param origin The origin used to set the origin of the Locale object.
     *  If this object is null, then 0.0 is used.
     * @param numTransforms The number of transforms to be in the
     *  MultiTransformGroup object.
     * @param canvas The canvas to draw into.  If this is null, it is
     *  ignored and a canvas will be created by the utility.
     * @param userConfig The URL to the user's configuration file, used
     *  by the Viewer object.  This is never examined and default values are
     *  always taken.
     *
     * @see Locale
     * @see Viewer
     * @see ViewingPlatform
     * @see MultiTransformGroup
     * @deprecated use ConfiguredUniverse constructors to read a
     *  configuration file
     */
    public SGUniverse(Object key, HiResCoord origin, int numTransforms,
      Canvas3D canvas, URL userConfig) 
    {
          this( key, origin, numTransforms, canvas, userConfig, null );
    }
    
    public SGUniverse(HiResCoord origin, int numTransforms, URL userConfig) {
          this( origin, numTransforms, userConfig, null );
    }    
    /**
     * Creates the "view" side of the scene graph.  The passed in parameters
     * override the default values where appropriate.
     *
     * @param origin The origin used to set the origin of the Locale object.
     *  If this object is null, then 0.0 is used.
     * @param numTransforms The number of transforms to be in the
     *  MultiTransformGroup object.
     * @param canvas The canvas to draw into.  If this is null, it is
     *  ignored and a canvas will be created by the utility.
     * @param userConfig The URL to the user's configuration file, used
     *  by the Viewer object.  This is never examined and default values are
     *  always taken.
     * @param localeFactory The Locale Factory which will instantiate the
     *  locale(s) for this universe.
     *
     * @see Locale
     * @see Viewer
     * @see ViewingPlatform
     * @see MultiTransformGroup
     * @deprecated use ConfiguredUniverse constructors to read a
     *  configuration file
     */
//    public SGVirtualUniverse(HiResCoord origin, int numTransforms,
//      Canvas3D canvas, URL userConfig, LocaleFactory localeFactory ) {
//	ViewingPlatform vwp;
//
//        createLocale( origin, localeFactory );
//        
//        // Create the ViewingPlatform and Viewer objects, passing
//        // down the appropriate parameters.
//	  vwp = new ViewingPlatform(numTransforms);
//        vwp.setUniverse( this );
//        viewer = new Viewer[1];
//        // viewer[0] = new Viewer(canvas, userConfig);
//        viewer[0] = new Viewer(canvas);
//        viewer[0].setViewingPlatform(vwp);
//
//        // Add the ViewingPlatform to the locale - the scene
//        // graph is now "live".
//        locale.addBranchGraph(vwp);
//    }

    public SGUniverse(Object key, HiResCoord origin, int numTransforms,
                              Canvas3D canvas, URL userConfig, 
                              LocaleFactory localeFactory ) 
    {
	SGViewingPlatform vwp;

        createLocale( origin, localeFactory );
        
        // Create the ViewingPlatform and Viewer objects, passing
        // down the appropriate parameters.
	vwp = new SGViewingPlatform(numTransforms);
        vwp.setUniverse( this );
        
        SGViewer viewer = new SGViewer(canvas);
        viewer.setViewingPlatform(vwp);
        
        viewerMap.put(key, viewer);
        
        // Add the ViewingPlatform to the locale - the scene
        // graph is now "live".
        locale.addBranchGraph(vwp);
    }
    
    public SGUniverse(HiResCoord origin, int numTransforms,
                       URL userConfig, LocaleFactory localeFactory ) {
	createLocale( origin, localeFactory );
    }    
    
    public void addViewingGraph(Object key, Canvas3D canvas) {//, Transform3D transform3d) {
        SGViewingPlatform vwp;
        int numTransforms = 1;
        
        vwp = new SGViewingPlatform(numTransforms);
        vwp.setUniverse( this );
        
        SGViewer viewer = new SGViewer(canvas);
        viewer.setViewingPlatform(vwp);
        viewer.getView().setProjectionPolicy(View.PERSPECTIVE_PROJECTION);
        
        viewerMap.put(key, viewer);
        
        // Add the ViewingPlatform to the locale - the scene
        // graph is now "live".
        locale.addBranchGraph(vwp);        
    }
    /**
     * Creates the "view" side of the scene graph.  The passed in parameters
     * override the default values where appropriate.
     *
     * @param viewingPlatform The viewingPlatform to use to create
     *  the "view" side of the scene graph.
     * @param viewer The viewer object to use to create
     *  the "view" side of the scene graph.
     */
//    public sgJVirtualUniverse(ViewingPlatform viewingPlatform, Viewer viewer) {
//        this( viewingPlatform, viewer, null );
//    }
    
    /**
     * Creates the "view" side of the scene graph.  The passed in parameters
     * override the default values where appropriate.
     *
     * @param viewingPlatform The viewingPlatform to use to create
     *  the "view" side of the scene graph.
     * @param viewer The viewer object to use to create
     *  the "view" side of the scene graph.
     * @param localeFactory The factory used to create the Locale Object
     */
//    public sgJVirtualUniverse(ViewingPlatform viewingPlatform, Viewer viewer,
//			  LocaleFactory localeFactory ) {
//        createLocale( null, localeFactory );
//        viewingPlatform.setUniverse( this );
//        
//        // Assign object references.
//        this.viewer = new Viewer[1];
//        this.viewer[0] = viewer;
//
//        // Add the ViewingPlatform to the Viewer object.
//        this.viewer[0].setViewingPlatform(viewingPlatform);
//
//        // Add the ViewingPlatform to the locale - the scene
//        // graph is now "live".
//        locale.addBranchGraph(viewingPlatform);
//    }
    
//    public sgJVirtualUniverse(LocaleFactory localeFactory) {
//        createLocale( null, localeFactory );
//    }  
    
//    public void addViewBranch(Object key, ViewingPlatform viewingPlatform, Viewer viewer) {
//        viewingPlatform.setUniverse( this );
//        
//        // Add viewer into map
//        viewerMap.put(key, viewer);
//        
//        // Add the ViewingPlatform to the Viewer object.
//        viewer.setViewingPlatform(viewingPlatform);
//
//        // Add the ViewingPlatform to the locale - the scene
//        // graph is now "live".
//        locale.addBranchGraph(viewingPlatform);        
//    }
    /** 
     * Constructor for use by Configured Universe
     */
//    sgJVirtualUniverse( HiResCoord origin, LocaleFactory localeFactory ) {
//        createLocale( origin, localeFactory );
//    }
    
    /** 
     *  Create the Locale using the LocaleFactory and HiRes origin,
     *  if specified. 
     */
    private void createLocale( HiResCoord origin, LocaleFactory localeFactory ) {

	if (localeFactory != null) {
	    if (origin != null)
		locale = localeFactory.createLocale(this, origin);
	    else
		locale = localeFactory.createLocale(this);
	}
	else {
	    if (origin != null)
		locale = new Locale(this, origin);
	    else
		locale = new Locale(this);
	}
    }

    /**
     * Returns the Locale object associated with this scene graph.
     *
     * @return The Locale object used in the construction of this scene
     *  graph.
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Returns the Viewer object associated with this scene graph.  
     * SimpleUniverse creates a single Viewer object for use in the
     * scene graph.
     * 
     * @return The Viewer object associated with this scene graph.
     */
    public SGViewer getViewer(Object key) {
        return (SGViewer)(viewerMap.get(key));
    }

    /**
     * Returns the ViewingPlatform object associated with this scene graph.
     *
     * @return The ViewingPlatform object of this scene graph.
     */
    public SGViewingPlatform getViewingPlatform(Object key) {
        return getViewer(key).getViewingPlatform();
    }


    /**
     * Returns the Canvas3D object at the specified index associated with
     * this Java 3D Universe.
     *
     * @param canvasNum The index of the Canvas3D object to retrieve.
     *  If there is no Canvas3D object for the given index, null is returned.
     *
     * @return A reference to the Canvas3D object associated with the
     *  Viewer object.
     */
    public Canvas3D getCanvas(Object key, int canvasNum) {
        Viewer viewer = (Viewer) (viewerMap.get(key));
        if(viewer != null)
            return viewer.getCanvas3D(canvasNum);
        else
            return null;
    }

    /**
     * Used to add Nodes to the geometry side (as opposed to the view side)
     * of the scene graph.  This is a short cut to getting the Locale object
     * and calling that object's addBranchGraph() method.
     *
     * @param bg The BranchGroup to attach to this Universe's Locale.
     */
    public void addBranchGraph(BranchGroup bg) {
        locale.addBranchGraph(bg);
    }

    /**
     * Finds the preferred <code>GraphicsConfiguration</code> object
     * for the system.  This object can then be used to create the
     * Canvas3D objet for this system.
     *
     * @return The best <code>GraphicsConfiguration</code> object for
     *  the system.
     */
    public static GraphicsConfiguration getPreferredConfiguration() {
        GraphicsConfigTemplate3D template = new GraphicsConfigTemplate3D();
        String stereo;

        // Check if the user has set the Java 3D stereo option.
        // Getting the system properties causes appletviewer to fail with a
        //  security exception without a try/catch.

        stereo = (String) java.security.AccessController.doPrivileged(
           new java.security.PrivilegedAction() {
           public Object run() {
               return System.getProperty("j3d.stereo");
           }
        });

        // update template based on properties.
        if (stereo != null) {
            if (stereo.equals("REQUIRED"))
                template.setStereo(template.REQUIRED);
            else if (stereo.equals("PREFERRED"))
                template.setStereo(template.PREFERRED);
        }

        // Return the GraphicsConfiguration that best fits our needs.
        return GraphicsEnvironment.getLocalGraphicsEnvironment().
                getDefaultScreenDevice().getBestConfiguration(template);
    }

    /**
     * Cleanup memory use and reference by SimpleUniverse.
     * Typically it should be invoked by the applet's destroy method.
     */
    public void cleanup() {
	// Get view associated with this sgJVirtualUniverse
        Iterator iT = viewerMap.keySet().iterator();
        
        Viewer viewer = null;
        View view = null;
        
        while(iT.hasNext()) {
            viewer = (Viewer)iT.next();
            view = viewer.getView();
            if(view != null) {
                cleanupCanvases(view);
                removeAllCanvas3Ds(view);
            }
            viewer.setViewingPlatform(null);
        }
        
	removeAllLocales();

	// viewerMap cleanup here to prevent memory leak problem.
        viewerMap.clear();
	Viewer.clearViewerMap();
        //Primitive.clearGeometryCache();
        

    }
    
    protected void cleanupCanvases(View view) {
	// Cleanup all off-screen canvases
	for (int i = view.numCanvas3Ds() - 1; i >= 0; i--) {
	    Canvas3D c = view.getCanvas3D(i);
	    if (c.isOffScreen()) {
		c.setOffScreenBuffer(null);
	    }
	}        
    }
    
    protected void removeAllCanvas3Ds(View view) {
	// Remove all canvases from view
        view.removeAllCanvas3Ds();
    }    
}

