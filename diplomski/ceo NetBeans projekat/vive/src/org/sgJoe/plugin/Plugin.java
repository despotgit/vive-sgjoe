package org.sgJoe.plugin;

import org.sgJoe.graphics.*;
import org.sgJoe.logic.*;
import org.sgJoe.gui.*;

import org.sgJoe.exception.SGPluginException;

/**
 * In a plugin a specific action can be performed. 
 * In most cases, this is some sort of manipulation on the scenegraph. 
 * This is how plugin (logic) is connected via scenegraph with form (GUI).
 * Input comes from form and must be handled by plugin.
 * Every plugin is executed in it's own thread.
 *
 * @author   $ Author: Aleksandar Babic $
 * @version  $ Revision: 0.1            $
 * @date     $ Date: 29/11/2005 9:32:32 $
 */
 
public abstract class Plugin implements Runnable, Constants {

  private Form form;
  private SceneGraphEditor editor;
  private Session session;
  private PluginObserver pluginObserver = null;
  
  /**
   * Set the form. This is used, since we can set any arguments
   * for the run method (the method run() comes from the interface
   * <code>java.lang.Runnable</code>)
   *
   * @param form The form to set.
   */
  public final void setForm(Form form) {
    this.form = form;
  }
  

  /**
   * Set the scenegraph editor. This is used, since we can set any arguments
   * for the run method (the method run() comes from the interface
   * <code>java.lang.Runnable</code>)
   *
   * @param editor The scenegraph editor to set.
   */
  public final void setScenegraphEditor(SceneGraphEditor editor) {
    this.editor = editor;
  }
  
  /**
   * Set the session for the performAction. This is used, since we can set any arguments
   * for the run method (the method run() comes from the interface
   * <code>java.lang.Runnable</code>)
   *
   * @param session The session to set.
   */
  public final void setSession(Session session) {
    this.session = session;
  }

  /**
   * Add a new PluginObserver for the current Plugin.
   *
   * @param po PluginObserver to add.
   */
  public final void addPluginObserver(PluginObserver po) {
    this.pluginObserver = po;
  }
  
  /**
   * The method performAction runs in it's own thread. It should be called
   * with start().
   */
  public final void run() 
  {
    try 
    {
      if (showUserMessage()) 
      {
        pluginObserver.showPopup();
      }
      
      performAction(form, editor, session);
      pluginObserver.updateGUIComponents(session);
    }
    catch (SGPluginException e) 
    {
      pluginObserver.handleException(e);
    }
    finally 
    {
      if (showUserMessage()) 
      {
        pluginObserver.hidePopup();
      }
    }
  }

  /**
   * Indicates if a popup is shown to the user during the execution of
   * a plugin. This is to indicate the state of the progress, if a
   * plugin takes longer time to execute. This behaviour must be overriden
   * by the subclasses, else no popup is shown to the user.
   *
   * return boolean <code>true</code> if a popup is shown, <code>false</code>
   *                else.
   */
  protected boolean showUserMessage() 
  {
    return false;
  }
  
  /**
     * Process a specific user action. The form contains the necessary
     * arguments which comes from the GUI. The scene graph editor directly
     * take influence to the current scene graph. The session stores
     * additional information, which should be shown in a specific GUI
     * component.
     * 
     * 
     * 
     * @param form The form with the necessary arguments.
     * @param editor A scene graph editor.
     * @param session Stores additional information for the GUI.
     * @throws SGPluginException If an exception occures.
     */
  public abstract void performAction(Form form, SceneGraphEditor editor, Session session) throws SGPluginException;
  
}
