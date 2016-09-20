package org.sgJoe.setup;

import java.util.Enumeration;
import javax.media.j3d.Canvas3D;
import org.sgJoe.exception.*;
import org.sgJoe.graphics.event.EventLogger;
import org.sgJoe.gui.*;
import org.sgJoe.graphics.*;
import org.sgJoe.logic.*;
import org.sgJoe.plugin.*;
        
import org.apache.log4j.Logger;

/**
 * This class is responsible for the correct initialization of the
 * application. Objects gets instantiated in the correct order, so
 * we can be shure, not to trap in a null pointer exception.
 *
 * @author   $ Author: Aleksandar Babic             $
 * @version  $ Revision:            0.1             $
 * @date     $ Date:  December 11, 2005 10:17 AM    $
 */
public class SGJoeSetup {
    
    private static Logger logger = Logger.getLogger(SGJoeSetup.class);
    
    public void run() {
        try {
           
            logger.debug("show splash screen...");
            
            IntroSplashScreen introScreen = new IntroSplashScreen();
            introScreen.showSplashScreen();

            logger.debug("load configuration...");
            ConfigLoader loader = new ConfigLoader();
            loader.loadConfiguration();
            Registry registry = loader.getRegistry();
            ToolRegistry toolRegistry = loader.getToolRegistry();
            ScenegraphSettings sgSettings = loader.getScenegraphSettings();

            logger.debug("setup controller -> initialize with registry and window manager...");
            Controller controller = Controller.getInstance();
            controller.setRegistry(registry);

// --- nikola ---
// iz nekog razloga ovo nije potrebno
//            LogicGuiManager lGuiManager = new LogicGuiManager();
            
      
            
            logger.debug("initialize window manager...");
            WindowManager wManager = new WindowManager(loader.getGUISettings(), loader.getToolRegistry());            
            
            controller.setWindowManager(wManager);
            logger.debug("create base scene graph and append to scene graph editor...");
            ViewPanel vPanel = (ViewPanel)wManager.getMainWindow().getsgJPanel("viewPanel");
            
            Canvas3D[] c3dArray = vPanel.getCanvas3DArray();
      
            BaseSceneGraph baseSceneGraph = new BaseSceneGraph(c3dArray, sgSettings);
            SceneGraphEditor sceneEditor = new SceneGraphEditor(baseSceneGraph);
            sceneEditor.setToolRegistry(toolRegistry);
            
            wManager.setSGEditor(sceneEditor);
            
            logger.debug("apply scene graph editor to each plugin...");
            Enumeration enumeration = registry.getModules();
            while (enumeration.hasMoreElements()) {
                Module m = (Module)enumeration.nextElement();
                m.getPlugin().setScenegraphEditor(sceneEditor);
            }

            logger.debug("apply observer to the behaviours...");
            baseSceneGraph.addBehaviourObserver(wManager.getBehaviorObserver());

            introScreen.hideSplashScreen();
            introScreen.removeSplashScreen();
            logger.debug("-- setup complete :) :) --");
            
            EventLogger eventLogger = new EventLogger();
      
        } catch (SGException e) {
            logger.error(e);
        }
    }
    
}
