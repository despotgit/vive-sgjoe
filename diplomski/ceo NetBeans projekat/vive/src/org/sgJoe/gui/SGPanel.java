package org.sgJoe.gui;

import org.sgJoe.logic.*;
import org.sgJoe.plugin.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.*;

import org.apache.log4j.Logger;

/*
 * Abstract class to be extended by concrete Panels for the GUI.
 * All classes extending from sgJPanel must implement the <code>setup()</code>
 * and the <code>update(Hashtable session)</code> method.
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: December 2, 2005  9:17 AM  $
 */

public abstract class SGPanel extends JPanel implements Constants {
    
    private static Logger logger = Logger.getLogger(SGPanel.class);
    
    private Controller controller = Controller.getInstance();
  
    protected static final Font DEFAULT_FONT = new Font("Dialog", 1, 10);
  
    private Window mainWindow = null;
  
    private JFileChooser chooser;
    private JFileChooser imageFileChooser;
    private SGFileFilter fileFilter = new SGFileFilter();
  
    protected ImagePool imageIconPool = ImagePool.instance();
  
    private ApplicationResources appResources = ApplicationResources.instance();
  
    /**
     * The main setup method to create the GUI panel. It takes as parameter
     * a reference from its parent panel.
     * (TemplatePattern)
     *
     * @param frame The parent frame that created this panel.
     */
    public final void setup(Window mainWindow) {
        this.mainWindow = mainWindow;
        setup();
    }
  
    public final boolean isSetup() {
        return (mainWindow != null);
    }
  
    /**
     * Creates the GUI for this panel. This method should not be called. This
     * is called by <code>setup(Window)</code>.
    */
    protected abstract void setup();
  
    /**
     * Updates the panels with specific data from a plugin. This is
     * normally a message from a plugin for the user. The subclass
     * has to implement the method <code>update</code> to work
     * properly.
    */
    public void updatePanel(Session session) {
        this.update(session);
        this.updateUI();
    }
  
    /**
     * Updates some components with specific data from
     * the session object. The data is normaly added in
     * a plugin.
     *
     * @param session A hashtable containing the specific data.
     */
    protected abstract void update(Session session); 

    /**
     * Reuse an existing form. This is to prevent from an object explosion.
     *
     * @param name The name under which the form is registered in the registry.
     * @return The corresponding form.
     */
    protected final Form useForm(String name) {
        return controller.getFormForName(name);
    }
  
    /**
     * Performs an action and delegates it to the controller. This
     * is just a code beautifier, the programmer who writes new panels
     * (window components) does not need to know anything about the controller.
     *
     * @param form The form with the parameters to delegate to a plugin.
     */
    protected final void performAction(String name, Form form) {
        controller.performAction(name, form);
    }
  
    /**
     * Loads an image icon. All image icons are stored in
     * the directory org/sgJoe/gui/images.
     *
     * @param filename The name of the image to load.
     * @return ImageIcon The loaded image as ImageIcon.
     */
    protected final ImageIcon getImageIcon(String filename) {
        return imageIconPool.getImage(filename);
    }
  
    /**
     * Helper method to create custom plugin buttons
     * @param icon        The icon's file name
     * @param tooltipText String that will be shown as tooltip text.
     * @return JButton    The newly created JButton.
     */
    protected final JButton createButton(String icon, String tooltipText) {
        ImageIcon imgIcon = imageIconPool.getImage(icon);
        Dimension dim = new Dimension(imgIcon.getIconWidth(), imgIcon.getIconHeight());
        JButton jButton = new JButton(imgIcon);
        jButton.setMaximumSize(dim);
        jButton.setMinimumSize(dim);
        jButton.setPreferredSize(dim);
        jButton.setPressedIcon(imageIconPool.getImage("pr_" + icon));
        jButton.setToolTipText(tooltipText);
        jButton.setContentAreaFilled(false);
        jButton.setBorderPainted(false);
        return jButton;
    }

        /**
     * Helper method to create custom plugin buttons
     * @param icon        The icon's file name
     * @param tooltipText String that will be shown as tooltip text.
     * @return JButton    The newly created JButton.
     */
    protected final JRadioButton createRadioButton(String text, boolean selected) {
        JRadioButton jRadioButton = new JRadioButton(text, selected);
        return jRadioButton;
    }
    /**
     * Returns the message for the given key. This can be used for
     * multilanguage support.
     *
     * @param key The key to lookup in the ApplicationResources.properties
     * @return String The value for this resource.
     */
    protected final String getResource(String key) {
        return appResources.getResource(key);
    }
  
    /**
     * Returns a reference to the parent frame.
     *
     * @return Window The parent frame.
     */
    public final Window getParentFrame() {
        return mainWindow;
    }
  
    /**
     * Opens a <code>load</code> file dialog with the specific file filter.
     * The directory root is the current directory of the user.
     *
     * @param extension The file extension
     * @return file The returned file
     */
    protected File loadFile(String extension) {
        String dialogTitle = "sgJoe: OPEN *." + extension + " files.";
        String buttonText = "Open";
        return fileDialog(extension, JFileChooser.OPEN_DIALOG, dialogTitle, buttonText);
    }
  
    /**
     * Opens a <code>save</code> file dialog with the specific file filter.
     * The directory root is the current directory of the user.
     *
     * @param extension The file extension
     * @return file The returned file
     */
    protected File saveFile(String extension) {
        String dialogTitle = "sgJoe: SAVE *." + extension + " files.";
        String buttonText = "Save";
        return fileDialog(extension, JFileChooser.SAVE_DIALOG, dialogTitle, buttonText);
    }
  
    /**
     * Opens a <code>select</code> file dialog with the specific file filter.
     * The directory root is the current directory of the user.
     *
     * @param extension The file extension
     * @return file The returned file
     */
    protected File selectFile(String extension) {
        String dialogTitle = "sgJoe: SELECT *." + extension + " files.";
        String buttonText = "Select";
        return fileDialog(extension, JFileChooser.SAVE_DIALOG, dialogTitle, buttonText);
    }
  
    /**
     * Internal use: opens a file dialog with the specified file filter.
     * The directory root is the current working directory of the user.
     */
    private File fileDialog(String extension, int dialogType, String dialogTitle, String buttonText) {
        if (chooser == null) {
            String currentDir = System.getProperty("user.dir");
            chooser = new JFileChooser(new File(currentDir));
            chooser.setAccessory(new ImagePreview(chooser));
            chooser.setFont(DEFAULT_FONT);
        }

        fileFilter.setFileExtension(extension);
        chooser.addChoosableFileFilter(fileFilter);
        chooser.setDialogTitle(dialogTitle);
        chooser.setDialogType(dialogType);
    
        int returnVal = chooser.showDialog(mainWindow, buttonText);
        // check if user hit OK
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            if (!f.getName().toLowerCase().endsWith("." + extension)) {
                return new File(f.getAbsolutePath()+"."+extension);
            }
            return f;
        } else {
            return null;
        }
  }
  
}
