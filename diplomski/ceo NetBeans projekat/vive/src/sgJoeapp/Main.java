package sgJoeapp;

import org.sgJoe.setup.SGJoeSetup;

/*
 * Main.java
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:            0.1         $
 * @date     $ Date:  December 8, 2005 5:55 PM  $
 */
public class Main {
    
    /** Creates a new instance of Main */
    public Main() { }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        SGJoeSetup setup = new SGJoeSetup();
        
        setup.run();
    }
    
}
