package org.sgJoe.logic;

import java.util.*;

import org.apache.log4j.Logger;

/**
 * Descritpion:
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:            0.1         $
 * @date     $ Date:  November 29, 2005 9:44 PM $
 */
public class ApplicationResources {
    
    private static Logger logger = Logger.getLogger(ApplicationResources.class);
    
    private static final String fileSeparator = System.getProperty("file.separator");
    private static final String parentDir = "..";
    
    
    // instance   (Singleton pattern)
    private static ApplicationResources instance = new ApplicationResources();
  
    // the resource bundle which holds up all the locale-sensitive messages.
    private static ResourceBundle resources;
    
    // due to static natrure we must do static initialization
    static {
        String resourcesPath = "config" + fileSeparator + "ApplicationResources";      
        try  {
            resources = ResourceBundle.getBundle(resourcesPath, Locale.getDefault());
        } catch (MissingResourceException mre) {
            System.err.println("ApplicationResources.properties not found");
        System.exit(0);
        }
    }
  
    /**
     * Creates a new instance of the ApplicationResources. It is keep
     * private to prevent from outside creation (singleton).
     */
    private ApplicationResources() {}
  
    /**
     * Returns an instance of the application resources.
     *
     * @return An instance of application resources.
     */
    public static ApplicationResources instance() {
        return instance;
    }
  
    /**
     * Returns the specific message for a given key. If the
     * message is not found, <code>null</code> will be returned.
     *
     * @param key The key for the resource.
     * @return The message.
     */
    public String getResource(String key) {
        try {
            return resources.getString(key);
        } catch (MissingResourceException mre) {
            logger.error(mre.getMessage());
            return null;
        }
    }
  
}
