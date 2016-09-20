package org.sgJoe.plugin;

import org.sgJoe.exception.SGPluginException;
import org.sgJoe.graphics.SceneGraphEditor;
import org.sgJoe.logic.Session;

import org.apache.log4j.Logger;

/**
 * This class change the behaviour of the 3d objects from the scene. You
 * can either scale, rotate or translate objects, or you can navigate
 * with the camera.
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:            0.1         $
 * @date     $ Date:  December 22, 2005 4:50 PM $
 */
public class ChangeBehaviourPlugin extends Plugin {
    
    private static Logger logger = Logger.getLogger(ChangeBehaviourPlugin.class);
    
    private int prevBehaviourType = -1;
    private int loopCount = 0;
    private boolean hasBehaviour = false;
  
  /**
     * Process a specific user action. The form contains the necessary
     * arguments which comes from the GUI. The scene graph editor directly
     * take influence to the current scene graph. The session stores
     * additional information, which should be shown in a specific GUI
     * component.
     * 
     * 
     * 
     * @param form The form with the necessary arguments.
     * @param editor A scene graph editor.
     * @param session Stores additional information for the GUI.
     * @throws SGPluginException If an exception occures.
     */
    public void performAction(Form form, SceneGraphEditor editor, Session session) throws SGPluginException {
        int operation = ((ChangeBehaviourForm)form).getOperation();
    
        // switch between navigation/editing mode
        if (operation == OP_SWITCH_NAV_MODE) {
            int behaviourType = ((ChangeBehaviourForm)form).getBehaviourType();

            // reset loopCount to 0
            loopCount = 0;
            String message = switchBehaviour(behaviourType, editor);
            session.setContextDialog(message);
        }
        // switch between orbit/1st person navigation type
        else if (operation == OP_SWITCH_NAV_TYPE) {
            boolean orbitNav = ((ChangeBehaviourForm)form).getOrbitMode();
            editor.setNavigationMode(orbitNav);
            session.setContextDialog("SWITCHING NAVIGATION TYPE!");
      
            hasBehaviour = false;
        }
        
        session.put("hasBehaviour", new Boolean(hasBehaviour));
    }
  
    /**
     * Internal use: switch the behaviour. If the <code>DISABLE_BEHAVIOUR_NAVIGATION_MODE</code>
     * is choosen, we have to set the previous state again. This is done in
     * an recursive way. For not to fall in an endless loop, we set some loopCounter
     * to ensure to loop not more than two times.
     *
     * @param behaviourType The type of behaviour to change.
     * @param editor The scene graph editor.
     */
    private String switchBehaviour(int behaviourType, SceneGraphEditor editor) {
        switch (behaviourType) {
            
            case ChangeBehaviourForm.ENABLE_BEHAVIOUR_MOUSE_PICK_ROTATE:
                editor.enableMousePickRotateBehaviour(true);
                editor.enableMousePickScaleBehaviour(false);
                editor.enableMousePickTranslateBehaviour(false);
                editor.enableMousePickPointBehavior(false);
                editor.enableMousePickDistanceBehavior(false);
                editor.enableEditorMode(true);
                prevBehaviourType = behaviourType;
                hasBehaviour = true;
                return "enable mouse pick rotate.";
                
            case ChangeBehaviourForm.ENABLE_BEHAVIOUR_MOUSE_PICK_TRANSLATE:
                editor.enableMousePickTranslateBehaviour(true);
                editor.enableMousePickScaleBehaviour(false);
                editor.enableMousePickRotateBehaviour(false);
                editor.enableMousePickPointBehavior(false);
                editor.enableMousePickDistanceBehavior(false);
                //editor.enableVertexDragBehaviour(false);
                editor.enableEditorMode(true);
                prevBehaviourType = behaviourType;
                hasBehaviour = true;
                return "enable mouse pick translate.";
                
          case ChangeBehaviourForm.ENABLE_BEHAVIOUR_MOUSE_PICK_SCALE:
                editor.enableMousePickScaleBehaviour(true);
                editor.enableMousePickTranslateBehaviour(false);
                editor.enableMousePickRotateBehaviour(false);
                editor.enableMousePickPointBehavior(false);
                editor.enableMousePickDistanceBehavior(false);
                editor.enableEditorMode(true);
                prevBehaviourType = behaviourType;
                hasBehaviour = true;
                return "enable mouse pick scale.";
                
          case ChangeBehaviourForm.ENABLE_BEHAVIOUR_MOUSE_PICK_POINT:
                editor.enableMousePickPointBehavior(true);
                editor.enableMousePickScaleBehaviour(false);
                editor.enableMousePickTranslateBehaviour(false);
                editor.enableMousePickRotateBehaviour(false);
                editor.enableMousePickDistanceBehavior(false);
                editor.enableEditorMode(true);
                prevBehaviourType = behaviourType;
                hasBehaviour = true;
                return "enable mouse pick point.";                
                
          case ChangeBehaviourForm.ENABLE_BEHAVIOUR_DISTANCE_METER:
                editor.enableMousePickPointBehavior(false);
                editor.enableMousePickScaleBehaviour(false);
                editor.enableMousePickTranslateBehaviour(false);
                editor.enableMousePickRotateBehaviour(false);
                editor.enableMousePickDistanceBehavior(true);
                editor.enableEditorMode(true);
                prevBehaviourType = behaviourType;
                hasBehaviour = true;
                return "enable mouse distance meter.";                
                          
            case ChangeBehaviourForm.ENABLE_BEHAVIOUR_NAVIGATION_MODE:
                editor.enableMousePickScaleBehaviour(false);
                editor.enableMousePickTranslateBehaviour(false);
                editor.enableMousePickRotateBehaviour(false);
                editor.enableMousePickPointBehavior(false);
                editor.enableMousePickDistanceBehavior(false);
                editor.enableEditorMode(false);
                hasBehaviour = false;
                return "enable navigation mode.";
                
            case ChangeBehaviourForm.DISABLE_BEHAVIOUR_NAVIGATION_MODE:
                editor.enableEditorMode(true);
                if (loopCount++ != 2) {
                    logger.debug("loop down...");
                    switchBehaviour(prevBehaviourType, editor);
                }
                // --> editor.enableEditorMode(true);
                return "disable navigation mode";
          
            case ChangeBehaviourForm.DISABLE_BEHAVIOUR_MOUSE_PICK_ROTATE:    
            case ChangeBehaviourForm.DISABLE_BEHAVIOUR_MOUSE_PICK_SCALE:
            case ChangeBehaviourForm.DISABLE_BEHAVIOUR_MOUSE_PICK_TRANSLATE:
            case ChangeBehaviourForm.DISABLE_BEHAVIOUR_MOUSE_PICK_POINT:
            case ChangeBehaviourForm.DISABLE_BEHAVIOUR_DISTANCE_METER:                
                editor.enableMousePickScaleBehaviour(false);
                editor.enableMousePickTranslateBehaviour(false);
                editor.enableMousePickRotateBehaviour(false);
                editor.enableMousePickPointBehavior(false);
                editor.enableMousePickDistanceBehavior(false);
                editor.enableEditorMode(true);
                prevBehaviourType = behaviourType;
                hasBehaviour = false;
                return "disable mouse pick scale.";
                
            default:
                // do nothing
                return new String();
        }
      }
    
}
