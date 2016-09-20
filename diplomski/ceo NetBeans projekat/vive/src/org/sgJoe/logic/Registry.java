package org.sgJoe.logic;

import java.util.*;

import org.apache.log4j.Logger;

/**
 * The registry is a container which stores the modules. The registry is
 * managed by the controller. The registry holds up a hashtable
 * with the modules.
 * <br><br>
 * Registry<br> 
 * <code>key = form-name</code> | <code>value = module-object</code>
 *
 *
 * @author   $ Author: Aleksandar Babic             $
 * @version  $ Revision:            0.1             $
 * @date     $ Date:  November 29, 2005 11:06 PM    $
 */
public class Registry {
    
    private static Logger logger = Logger.getLogger(Registry.class);
    
    private Hashtable registry = new Hashtable();
    
    /**
     * Adds a new module to the registry. The value <code>null</code> is not
     * allowed here, else an IllegalArgumentExceptio is thrown.
     *
     * @param module The module to add.
     * @throws IllegalArgumentException Is thrown if the module is <code>null</code>.
     */
    public void registerModule(Module module) throws IllegalArgumentException {
        if (module == null) {
            throw new IllegalArgumentException("A module can not be 'null'.");
        }
        registry.put(module.getName(), module);
    }
  
    /**
     * Returns the module to the specific form. As argument we put the
     * class object of the form. If the module is not found, <code>null</code>
     * is returned.
     *
     * @param cl The class of the form.
     * @return Module The module.
     */
    public Module getModule(String name) {
        return (Module)registry.get(name);
    }

    /**
     * Returns an enumeration over all availables modules.
     *
     * @return Enumeration over all modules.
     */
    public Enumeration getModules() {
        return registry.elements();
    }
  
    /**
     * Return the number of loaded modules.
     *
     * @return int The number of loaded modules.
     */
    public int size() {
        return registry.size();
    }
    
}
