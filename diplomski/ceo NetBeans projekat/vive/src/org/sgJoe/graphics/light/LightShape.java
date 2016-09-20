package org.sgJoe.graphics.light;


import javax.media.j3d.*;
import org.apache.log4j.Logger;

import com.sun.j3d.utils.scenegraph.io.SceneGraphObjectReferenceControl;
import com.sun.j3d.utils.scenegraph.io.SceneGraphIO;

import java.io.IOException;

/**
 * Light shape defines a visible sysmbol of a specific light type. 
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: May 19, 2006  11:55 AM  $
 */
public class LightShape extends Shape3D implements SceneGraphIO
{
  private Logger logger = Logger.getLogger(LightShape.class);
  
  /**
   * The method is called before writeSGObject and gives the user the chance 
   * to create references to other Nodes and NodeComponents. References take 
   * the form of a nodeID, of type integer. Every SceneGraphObject is assigned 
   * a unique ID. The user must save the reference information in writeSGObject.
   *
   * @param ref Provides methods to create references to a SceneGraphObject.
   */
  public void createSceneGraphObjectReferences( SceneGraphObjectReferenceControl ref )
  {}
  
  /**
   * Within this method the user should restore references to the SceneGraphObjects 
   * whose nodeID's were created with createSceneGraphObjectReferences This 
   * method is called once the all objects in the scenegraph have been loaded.
   *
   * @param ref Provides methods to resolve references to a SceneGraphObject.
   */
  public void restoreSceneGraphObjectReferences( SceneGraphObjectReferenceControl ref )
  {}
  
  /**
   * This method should store all the local state of the object and any references 
   * to other SceneGraphObjects into out. This is called after data for the parent 
   * SceneGraphObject has been written to the out.
   *
   * @param out The output stream.
   * @throws IOException Exception occured.
   */
  public void writeSceneGraphObject( java.io.DataOutput out ) throws IOException
  {}
  
  /**
   * This is called after the object has been constructed and the superclass 
   * SceneGraphObject data has been read from in. The user should restore all 
   * state infomation written in writeSGObject.
   *
   * @param in The input stream.
   * @throws IOException Exception occured.
   */
  public void readSceneGraphObject( java.io.DataInput in ) throws IOException
  {}
  
  /**
   * Flag indicating for children of this object should be saved This method 
   * only has an effect if this is a subclass of Group. If this returns true 
   * then all children of this Group will be saved. If it returns false then 
   * the children are not saved.
   */
  public boolean saveChildren()
  {
    return true;
  }

  /**
   * Creates a new light shape. For this shape, no capabilities are set.
   */
  public LightShape()
  {
  }
  
  /** 
   * Creates a new instance of LightShape. All needed capabilities are set.
   */
  public LightShape(Shape3D shape)
  {
    super(shape.getGeometry(), shape.getAppearance());
    setCapability(Shape3D.ALLOW_APPEARANCE_READ);
    setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
    setCapability(Shape3D.ALLOW_GEOMETRY_READ);
    setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
    setCapability(Shape3D.ALLOW_PICKABLE_WRITE);
    setCapability(Shape3D.ALLOW_PICKABLE_READ);
  }
  
  /**
   *  Has to be overridden so that cloning works for user defined class
   */
  public Node cloneNode(boolean forceDuplicate)
  {
    LightShape ls = new LightShape();
    ls.duplicateNode(this, forceDuplicate);
    return ls;
  }
  
}
