package org.sgJoe.plugin;

import org.sgJoe.logic.*;

/**
 * An Form is a JavaBean. Such a bean will have had its properties initialized 
 * from the corresponding request parameters before the corresonding plugin's 
 * execute method is called.
 *
 * @author   $ Author: Aleksandar Babic $
 * @version  $ Revision: 0.1            $
 * @date     $ Date: 29/11/2005 9:44    $
 */
public abstract class Form {

  /**
   * Validate the properties that have been set for this request, and 
   * return an ActionErrors object that encapsulates any validation 
   * errors that have been found. If no errors are found, return null 
   * or an ActionErrors object with no recorded error messages.
   * Any validation error is shown in a popup.
   *
   * @param session Validate arguments from the session if needed.
   * @return ActionErrors The ActionErrors containing the error messages.
   */
  public abstract ActionErrors validate(Session session);
  
  /**
   * Reset all bean properties to their default state. This method is 
   * called before the properties are repopulated by the controller.
   *
   * @param session Validate arguments from the session if needed.
   */
  public abstract void reset(Session session);
  
}
