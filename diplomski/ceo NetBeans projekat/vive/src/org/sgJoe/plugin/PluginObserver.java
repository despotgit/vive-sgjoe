package org.sgJoe.plugin;

import org.sgJoe.exception.SGPluginException;
import org.sgJoe.logic.Session;

/**
 * This interface is used to observ the plugin execution. The observer
 * should override these methods to controll the execution of a plugin.
 *
 * @author   $ Author: Aleksandar Babic             $
 * @version  $ Revision:            0.1             $
 * @date     $ Date:  November 29, 2005 10:27 PM    $
 */
public interface PluginObserver {
    
    public void updateGUIComponents(Session session);
    public void handleException(SGPluginException e);
  
    public void showPopup();
    public void hidePopup();
    
}
