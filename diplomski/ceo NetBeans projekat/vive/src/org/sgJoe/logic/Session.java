package org.sgJoe.logic;

import java.util.Hashtable;
import org.sgJoe.plugin.Constants;

import org.apache.log4j.Logger;

/**
 * The session is used to store intermediate results. This gives the
 * ability to use results from one plugin in other plugins or GUI 
 * components.
 * Results are shared across plugins within application/session.
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision: 0.1             $
 * @date     $ Date: 29/11/2005 09:50:50 $
 */
public class Session extends Hashtable implements Constants {
    
    private static Logger logger = Logger.getLogger(Session.class);

    /**
     * Sets a message which will be shown in the message panel from the gui.
     *
     * @param msg The message for the gui.
     */
    public void setContextDialog(String msg) {
        this.put(CONTEXT_DIALOG, msg);
    }
  
    /**
     * Returns the message from the context. The message is still
     * saved in the session. Explicit casting to string is a must.
     *
     * @return The message for the context dialog.
     */
    public String getContextDialog() {
        return (String)this.get(CONTEXT_DIALOG);
    }
  
    /**
     * Sets the key for a specific context panel. Context panels can
     * be exchanged. Every context panel is stored and retreived by a specific
     * key. This method gives the ability to inform the windows manager
     * which context panel should be exchanged.
     *
     * @param key The context panel key.
     */
    public void setContextPanelKey(String key) {
        this.put(CONTEXT_PANEL, key);
    }
  
    /**
     * Return the name of the context panel. The window manager will exchange
     * the current context panel depending on which key he will get from this
     * mehod.
     *
     * @return The context panel key.
     */
    public String getContextPanelKey() {
        return (String)this.get(CONTEXT_PANEL);
    }
  
    /**
     * Removes the name of the context panel from this session.
     *
     * @return The context panel key.
     */
    public String removeContextPanelKey() {
        return (String)this.remove(CONTEXT_PANEL);
    }

}
