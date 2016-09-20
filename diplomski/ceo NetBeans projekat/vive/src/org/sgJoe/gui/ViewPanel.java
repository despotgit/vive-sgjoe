package org.sgJoe.gui;

import com.sun.j3d.utils.universe.SimpleUniverse;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.media.j3d.Canvas3D;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.apache.log4j.Logger;

import org.sgJoe.graphics.GraphicsConstants;
import org.sgJoe.gui.GuiConstants;
import org.sgJoe.logic.Session;
import org.sgJoe.plugin.CopyViewForm;
import org.sgJoe.plugin.DeleteObjectForm;
import org.sgJoe.plugin.SelectObjectForm;
import org.sgJoe.plugin.SetViewPointForm;

/**
 * The ViewPanel shows the actual scene in 3D. At the same time it is listening
 * to mouse events and sends these to the corresponding actions.
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:            0.1         $
 * @date     $ Date:  December 3, 2005 8:50 PM  $
 */
public class ViewPanel extends SGPanel implements GraphicsConstants, ActionListener, GuiConstants {
    
    private static Logger logger = Logger.getLogger(ViewPanel.class);

    // the four views
    private Canvas3D[] canvas3dArray = new Canvas3D[4];
  
    // These two panels will be switched depending on mode (prespective or 4 views)
    private JPanel fourViewsPanel = null;
    private JPanel perspectivePanel = null;
  
    // Create right-click pop-up menu 
    private JPopupMenu	popupMenu;
    private JMenuItem menuScenegraph = new JMenuItem( "Scene Graph" );
    private JMenuItem menuObject = new JMenuItem( "Object Transformations" );
    private JMenuItem menuDelete = new JMenuItem( "Delete Object" );
    private JMenuItem menuSetViewPoint = new JMenuItem( "Set ViewPoint" );
    private JMenuItem menuCopyView = new JMenuItem ("Copy View");
      
    private String contextMenu = null;
    private SelectObjectForm form = new SelectObjectForm();
    private boolean navigationMode = false;
    
    /**
     * Setup the panel
     */
    public void setup() {
        logger.debug("init ViewPanel");
    
        setLayout(new BorderLayout());
    
        perspectivePanel = new JPanel();
        perspectivePanel.setLayout(new GridLayout(1, 1));
        perspectivePanel.setBorder(new javax.swing.border.EtchedBorder());
    
        fourViewsPanel = new JPanel();
        fourViewsPanel.setLayout(new GridLayout(2, 2, 2 ,2));
        fourViewsPanel.setBorder(new javax.swing.border.EtchedBorder());
    
        add(perspectivePanel, BorderLayout.CENTER);
    
        // get graphics configuration for canvas3D
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
    
        // all canvas have the same configuration
        for (int i = 0; i < 4; i++) {
            canvas3dArray[i] = new Canvas3D(config);
        }
    
        // the mouselistener only reacts if navigation mode is not active
        // this way the popup menu doesn't appear when the user presses the
        // right mouse button while in navigation mode.
        for (int i = 0; i < 4; i++) 
        {
            canvas3dArray[i].addMouseListener(new java.awt.event.MouseAdapter() 
            {
                
                // rewritten to check for user data
                
                public void mouseClicked( MouseEvent e ) 
                {
                    if (!navigationMode && e.getButton() == MouseEvent.BUTTON1) 
                    {
                        int x = e.getX();
                        int y = e.getY();
            
                        SelectObjectForm selForm = (SelectObjectForm)useForm("selectObject");
                        selForm.setX(x);
                        selForm.setY(y);
            
                        performAction("selectObject", selForm);
                    }   
                }
                
                public void mouseReleased(MouseEvent e) {
                    if (!navigationMode && e.isPopupTrigger()) {
                        int x = e.getX();
                        int y = e.getY();
                        form.setX(x);
                        form.setY(y);
                        popupMenu.show(e.getComponent(), x, y);
                    }
                }
            });
        } // end for
    
        // --> fourViewsPanel.add(canvas3dArray[PLANE_PERSPECTIVE]); // perspective
        
        // note that this overrides the former add from the four views panel
        perspectivePanel.add(canvas3dArray[PLANE_PERSPECTIVE]); // perspective
        // --> fourViewsPanel.add(canvas3dArray[PLANE_XZ]); // top
        // --> fourViewsPanel.add(canvas3dArray[PLANE_YZ]); // left
        // --> fourViewsPanel.add(canvas3dArray[PLANE_XY]); // front
    
        menuScenegraph.setFont(DEFAULT_FONT);
        menuObject.setFont(DEFAULT_FONT);
        menuDelete.setFont(DEFAULT_FONT);
        menuSetViewPoint.setFont(DEFAULT_FONT);
        menuCopyView.setFont(DEFAULT_FONT);
        
        menuScenegraph.addActionListener(this);
        menuObject.addActionListener(this);
        menuDelete.addActionListener(this);
        menuSetViewPoint.addActionListener(this);
        menuCopyView.addActionListener(this);
        
        // Create a popup menu
        popupMenu = new JPopupMenu( "Menu" );
        popupMenu.add( menuObject );
        popupMenu.add( menuScenegraph );
        popupMenu.add( menuDelete );
        popupMenu.add( menuSetViewPoint) ;
        popupMenu.add( menuCopyView) ;
    
        add(perspectivePanel, BorderLayout.CENTER);
    
        validate();
    }
  
    /**
     * Returns an array of the 4 canvas3d views.
     *
     * @return An array of canvas3d.
     */
    public Canvas3D[] getCanvas3DArray() {
        return canvas3dArray;
    }
  
    public void actionPerformed( ActionEvent event ) {
        
        if(event.getSource() == menuObject) {
            contextMenu = PRIMITIVE_CONTEXT;
            form.setContextMenu(contextMenu);
            performAction("selectObject", form);
        } else if(event.getSource() == menuScenegraph){
            contextMenu = SCENEGRAPH_CONTEXT;
            form.setContextMenu(contextMenu);
            performAction("selectObject", form);
        } else if(event.getSource() == menuDelete) {
            contextMenu = CURRENT_CONTEXT;
            DeleteObjectForm deleteObjectForm = (DeleteObjectForm)useForm("deleteObject");
            deleteObjectForm.setX(form.getX());
            deleteObjectForm.setY(form.getY());
            performAction("deleteObject", deleteObjectForm);
        } else if(event.getSource() == menuSetViewPoint) {
            //contextMenu = SETVIEWPOINT_CONTEXT;
            SetViewPointForm setViewPointForm = (SetViewPointForm)useForm("setViewPoint");
            performAction("setViewPoint", setViewPointForm);            
        } else if(event.getSource() == menuCopyView) {
            //contextMenu = COPYVIEW_CONTEXT;
            CopyViewForm copyViewForm = (CopyViewForm) useForm("copyView");
            performAction("copyView", copyViewForm);            
        }
  
    }
  
    public void setFourViews() 
    {
        if(this.getComponent(0).equals(perspectivePanel)) 
        {
            remove(perspectivePanel); // first clean up the old layout
            perspectivePanel.remove(0); // away with the canvas3d    
        }         

        fourViewsPanel.add(canvas3dArray[PLANE_PERSPECTIVE], 0); // and into the first panel
        add(fourViewsPanel, BorderLayout.CENTER); // add the four views panel
        validate();
    }
  
    public void setPerspectiveView() {
        remove(fourViewsPanel); // first clean up the old layout
        fourViewsPanel.remove(0); // away with the canvas3d
        perspectivePanel.add(canvas3dArray[PLANE_PERSPECTIVE], 0); // and into the perspective panel
        add(perspectivePanel, BorderLayout.CENTER); // add the perspective panel
        validate();
    }
    
    public void addView(int viewNum) 
    {
        if(this.getComponent(0).equals(perspectivePanel)) 
        {
            // do this only the first time
            remove(perspectivePanel); // first clean up the old layout
            perspectivePanel.remove(0); // away with the canvas3d    
            fourViewsPanel.add(canvas3dArray[PLANE_PERSPECTIVE], 0); // and into the first panel
            add(fourViewsPanel, BorderLayout.CENTER); // add the four views panel            
        } 
        
        fourViewsPanel.add(canvas3dArray[viewNum]);
       
        validate();        
    }
  
    /**
    * Set the navigation mode
     *
     * @param navMode true if navigation mode is active.
     */
    public void setNavigationMode(boolean navMode) {
        navigationMode = navMode;
        logger.debug("navigationMode: " + navigationMode);
    }
  
    /**
     * Updates some components with specific data from
     * the session object. The data is normaly added in
     * a plugin.
     *
     * @param session A hashtable containing the specific data.
     */
    protected void update(Session session){ }
}
