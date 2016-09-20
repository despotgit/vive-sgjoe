package org.sgJoe.gui;

import java.awt.Window;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;



import org.apache.log4j.Logger;

/**
 * This splash screen pops up during the startup phase of the application 
 * It is a JButton object with gif on it
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:            0.1         $
 * @date     $ Date:  December 4, 2005 12:16 AM $
 */
public class IntroSplashScreen implements ActionListener {
    
    private static Logger logger = Logger.getLogger(IntroSplashScreen.class);
    
    private ActionListener ke;
    private Window about;
    private ImagePool imageIconPool = ImagePool.instance();

  
    public IntroSplashScreen() {
        about = new Window(new Frame());
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = screen.width;
        int y = screen.height;
          
        ImageIcon imgIcon = imageIconPool.getImage("intro.jpg");
            
        JButton button = new JButton(imgIcon);
        button.setBorderPainted(false);
        button.addActionListener(this);
        about.add(button);
        about.setSize(imgIcon.getIconWidth(), imgIcon.getIconHeight());
        about.setLocation(x/2-imgIcon.getIconWidth()/2, y/2-imgIcon.getIconHeight()/2);
    }
  
    public void showSplashScreen() {
        about.toFront();
        about.setVisible(true);
    }
  
    public void hideSplashScreen() {
        about.setVisible(false);
    }
  
    public void removeSplashScreen() {
        about.dispose();
    }
  
    public void actionPerformed(ActionEvent e) {
        about.setVisible(false);
    }
    
}
