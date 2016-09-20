package org.sgJoe.gui;

import javax.swing.border.*;
import javax.swing.*;
import java.awt.*;
import java.lang.*;

import org.apache.log4j.Logger;

/*
 * Descritpion for Popup.java
 *
 *
 * @author   $ Author: Aleksandar Babic             $
 * @version  $ Revision:             0.1            $
 * @date     $ Date: December 1, 2005  11:28 AM     $
 */

public class Popup {
    
    private static Logger logger = Logger.getLogger(Popup.class);

    private Frame popup = new Frame();
    private Label msgLabel = new Label();
    private Thread animThread = null;
    private String[] msg = new String[4];
  
    public Popup() {
        msg[0] = "processing";
        msg[1] = "processing.";
        msg[2] = "processing..";
        msg[3] = "processing...";
    
        msgLabel.setBackground(Color.LIGHT_GRAY);
        msgLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        msgLabel.setText("processing...");
    
        Panel p = new Panel();
        p.setLayout(new BorderLayout());
        p.add(msgLabel);
        p.validate();
    
        popup.add(p);
    
        popup.setSize(200, 100);
        popup.pack();
        
        center();
    
        animThread = new Thread() {
        
            public void run() {
                int counter = 0;
                boolean isRunning = true;
                while (isRunning) {
                    msgLabel.setText(msg[counter++]);
                    if (counter == msg.length) counter = 0;
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) { 
                        isRunning = false; /* noting... */ 
                    }
                }
            }
        };
        
        animThread.setPriority(Thread.MIN_PRIORITY);
    }   
    private void center() {
         Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
         Dimension frameSize = popup.getSize();
         int x = (screenSize.width / 2) - (frameSize.width / 2);
         int y = (screenSize.height / 2) - (frameSize.height / 2);
         popup.setLocation(x, y);
    }
  
    public void show() {
        popup.show();
        
        if (!animThread.isAlive()) {
            animThread.start();
        }
    }
  
    public void hide() {
        popup.hide();
        
        if (!animThread.interrupted()) {
            animThread.interrupt();
        }
    }    
    
}
