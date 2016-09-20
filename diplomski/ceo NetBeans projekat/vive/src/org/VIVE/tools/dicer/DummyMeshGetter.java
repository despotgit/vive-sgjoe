/*
 * DummyMeshGetter.java
 *
 * Created on September 1, 2007, 12:52 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.tools.dicer;


import com.sun.org.omg.SendingContext.CodeBase;
import javax.media.j3d.*;
import javax.vecmath.*;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.picking.*;
import com.sun.j3d.loaders.vrml97.VrmlLoader;
import com.sun.j3d.loaders.Scene;
import com.tornadolabs.j3dtree.Java3dTree;

import java.util.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.Hashtable;

import java.applet.Applet;
import java.awt.*;
import java.net.*;
import java.io.*;

import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.audioengines.javasound.JavaSoundMixer;
import com.tornadolabs.j3dtree.*;
/**
 *
 * @author Vladimir Despotovic
 */

public class DummyMeshGetter extends Applet
{
    
    /** Creates a new instance of DummyMeshGetter */
    public DummyMeshGetter() 
    {
    }
    
    public BranchGroup getDummyMesh()
    {
        String vrmlFile = null;
	try
	{                    
	    URL codebase = getWorkingDirectory();
            System.out.println("codebase.toExternalFOrm() daje:"+codebase.toExternalForm( ));
	    vrmlFile = codebase.toExternalForm( ) + "/VRML/kamera2.wrl";
        }
	catch( Exception e )
	{
 	   e.printStackTrace();
        }
  	BranchGroup sceneRoot = loadVrmlFile( vrmlFile );
        return sceneRoot;       
    }	    
    private BranchGroup loadVrmlFile( String location )
    	{
		BranchGroup sceneGroup = null;
		Scene scene = null;
		VrmlLoader loader = new VrmlLoader( );
		try
		{
			URL loadUrl = new URL( location );
			try			{
				// load the scene
				scene = loader.load( new URL( location ) );
			} 
			catch ( Exception e )
			{
				System.out.println( "ParsingErrorException loading URL:" + e );
				e.printStackTrace( );
			}
		} 
		catch ( MalformedURLException badUrl )
		{
			// location may be a path name
			try 
			{
				// load the scene
				scene = loader.load( location );
			} 
			catch ( Exception e )
			{
				System.out.println( "Exception loading file from path:" + e );
				e.printStackTrace( );
			}
		}

		if (scene != null)
		{
			// get the scene group
			sceneGroup = scene.getSceneGroup( );

			sceneGroup.setCapability( BranchGroup.ALLOW_BOUNDS_READ );
			sceneGroup.setCapability( BranchGroup.ALLOW_CHILDREN_READ );

			Hashtable namedObjects = scene.getNamedObjects( );
			System.out.println( "*** Named Objects in VRML file: \n" + namedObjects );

			// recursively set the user data here
			// so we can find our objects when they are picked
			java.util.Enumeration enumValues = namedObjects.elements( );
			java.util.Enumeration enumKeys = namedObjects.keys( );

			if( enumValues != null )
			{
				while( enumValues.hasMoreElements( ) != false )
				{
					Object value = enumValues.nextElement( );
					Object key = enumKeys.nextElement( );

					//recursiveSetUserData( value, key );
				}
			}
		}
		return sceneGroup;
	}    
    
    public URL getWorkingDirectory( )
	throws java.net.MalformedURLException
	{
		URL url = null;
		
		try
		{       File file = new File( System.getProperty("user.dir") );
			
			System.out.println( "   " + file.toURL( ) );
			return file.toURL( );		
			}
		catch( Exception e )
		{
		}		
		return getCodeBase( );
	}
}