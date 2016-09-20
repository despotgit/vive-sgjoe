package org.sgJoe.gui;

import java.io.File;
import javax.swing.filechooser.FileFilter;

import org.apache.log4j.Logger;


/*
 * A file filter for sgJoe's file dialogs. Supported extensions are:
 * obj - for object files
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: December 2, 2005  10:52 AM $
 */

public class SGFileFilter extends FileFilter {
    
    private static Logger logger = Logger.getLogger(SGFileFilter.class);
    
    public final static String FILE_EXTENSION_OBJ = "obj";
    public final static String FILE_EXTENSION_GIF = "gif";
    
    private String currentFileExtension = FILE_EXTENSION_OBJ;    
    
    public void setFileExtension(String extension) {
        this.currentFileExtension = extension;
    }
  
    /**
     * Whether the given file is accepted by this filter.
     */
    public boolean accept(File file) {
        boolean accepted = false;
    
        if (file.isDirectory()) {
            return true;
        }
    
        String filename = file.getName();
        int periodeIndex = filename.lastIndexOf('.');
    
        if (periodeIndex > 0 && periodeIndex < filename.length()-1) {
            String extension = filename.substring(periodeIndex+1).toLowerCase();
            if (extension.equals(currentFileExtension)) {
                accepted = true;
            }
        }
        
        return accepted;
    }  

    /**
     * The description of this filter. For example: "JPG and GIF Images"
     */
    public String getDescription() {
        return "*." + currentFileExtension + " files";
    }  
      
}
