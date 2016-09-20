package org.sgJoe.gui;

import javax.swing.ImageIcon;
import java.util.HashMap;

import org.apache.log4j.Logger;


/*
 * ImageIcons are only loaded once and only if they're used.
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: December 2, 2005  11:23 AM $
 */

public class ImagePool {
    
    private static Logger logger = Logger.getLogger(ImagePool.class);
    
    static private ImagePool imagePool = null;
  
    private HashMap imageIconMap = null;
    
    private final static String fileSeparator = System.getProperty("file.separator");
    private final static String parentDir = "..";
  
    /** Private constructor used by instance() method (singleton) */
     private ImagePool() {
            imageIconMap = new HashMap();
    }
  
    /**
     * Creates a new instance of ImagePool
     *
     * @return the ImagePool instance
    */
    static public ImagePool instance() {
        if(imagePool == null) {
            imagePool = new ImagePool();
        }
        return imagePool;
    }
  
    /**
     * Return image. If image not in HashMap put it in and return it.
     * 
     * @param the name of the image as String
     * @return the wanted ImageIcon
     */
    public ImageIcon getImage(String imageName) {
        String imagePath = parentDir + fileSeparator + 
                            parentDir + fileSeparator + 
                                parentDir + fileSeparator +
                                    "images" + fileSeparator + imageName;

        ImageIcon icon = (ImageIcon)imageIconMap.get(imageName);
        if(icon == null) {
            java.net.URL imgURL = ImagePool.class.getResource(imagePath);
            if (imgURL != null) {
                icon = new ImageIcon(imgURL);
                imageIconMap.put(imageName,icon);
            }
        }
        
        return icon;
    }

}
