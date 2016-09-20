package org.sgJoe.gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import org.sgJoe.logic.Session;

import org.apache.log4j.Logger;


/**
 * The info bar shows the current message line and displays information
 * about memory usage.
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:            0.1         $
 * @date     $ Date:  December 3, 2005 8:34 PM  $
 */
public class MessagePanel extends SGPanel {
    
    private static Logger logger = Logger.getLogger(MessagePanel.class);
    
    private JLabel message;
    private JLabel memoryInfo;
  
    /**
     * Set the panel up
     */
    protected void setup() {
        logger.debug("init InfoBarPanel");
    
        setBorder(new TitledBorder(null, "message", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, DEFAULT_FONT));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
  
        message = new JLabel();
        message.setFont(DEFAULT_FONT);
        message.setText(this.getResource("panel.message.default.text"));

        add(message);
        add(Box.createHorizontalGlue());

        memoryInfo = new JLabel();
        memoryInfo.setFont(DEFAULT_FONT);

        add(memoryInfo);
    
        Thread t = new Thread() {
            public void run() {
                Runtime runtime = Runtime.getRuntime();
                while (true) { 
                    long freeMem = runtime.freeMemory();
                    long maxMem = runtime.maxMemory();
                    //long totMem = runtime.totalMemory();
                    memoryInfo.setText((freeMem/(1024*1024)) + "MB of max " + (maxMem/(1024*1024)) + " MB");
          
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {}
                }
            }
        };
    
        t.setPriority(Thread.MIN_PRIORITY);
        t.start();
    }
  
    /**
     * Updates some components with specific data from
     * the session object. The data is normaly added in
     * a plugin.
     *
     * @param session A hashtable containing the specific data.
     */  
    protected void update(Session session) {
        String msg = session.getContextDialog();
        if (msg != null) {
            message.setText(msg);
        }
    }
}
