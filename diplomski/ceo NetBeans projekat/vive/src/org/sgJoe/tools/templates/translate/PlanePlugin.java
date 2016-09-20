package org.sgJoe.tools.templates.translate;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import javax.media.j3d.*;

import javax.vecmath.*;

import org.apache.log4j.Logger;
import org.sgJoe.exception.SGPluginException;
import org.sgJoe.graphics.SceneGraphEditor;
import org.sgJoe.graphics.behaviors.BehaviorObserver;
import org.sgJoe.graphics.light.LightShape;
import org.sgJoe.logic.Session;
import org.sgJoe.plugin.Form;
import org.sgJoe.tools.interfaces.*;
import org.sgJoe.tools.templates.PlaneAdapter;
/*
 * Descritpion for PlanePlugin.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: June 17, 2006  11:38 AM  $
 */

public class PlanePlugin extends VToolPlugin {
    
    private static Logger logger = Logger.getLogger(PlanePlugin.class);
   
    protected Vector3d translation = new Vector3d();
    protected Transform3D currXform = new Transform3D();
    protected Transform3D transformX = new Transform3D();    
    protected Transform3D transformY = new Transform3D();
    
    // light related fields
    protected final Color3f grey = new Color3f(0.9f, 0.9f, 0.9f);
    protected final Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
    protected Color3f currentColor = new Color3f();
    
    private static Class c = null;
    private static BeanInfo bi = null;
    private static Method[] mList = null;
    private static MethodDescriptor[] mDescriptorList = null;
    
    protected static Hashtable n2e = null;
    protected static Hashtable e2m = null;
    
    static{
        
        n2e = new Hashtable();
        n2e.put("OnInstanceDelete", new Long(VToolForm.ACT_ON_INSTANCE_DELETE));
        n2e.put("OnMouseTranslateXY", new Long(VToolForm.ACT_MOUSE_TRANSLATE_XY));
        n2e.put("OnMousePressedEvent", new Long(VToolForm.ACT_MOUSE_PRESSED));
        n2e.put("OnMouseReleasedEvent", new Long(VToolForm.ACT_MOUSE_RELEASED));
        n2e.put("OnMousePlanarHandlePressed", new Long(VToolForm.ACT_MOUSE_PLANAR_HANDLE_PRESSED));
        n2e.put("OnMousePlanarHandleClicked", new Long(VToolForm.ACT_MOUSE_PLANAR_HANDLE_CLICKED));
        n2e.put("OnMousePlanarHandleReleased", new Long(VToolForm.ACT_MOUSE_PLANAR_HANDLE_RELEASED));
        n2e.put("OnMouseDraggedHitOtherTool", new Long(VToolForm.ACT_MOUSE_DRAGGED_HIT_OTHER_TOOL));
        e2m = new Hashtable();
        
                
        try {
            c = Class.forName("org.sgJoe.tools.templates.translate.PlanePlugin");    
            bi = Introspector.getBeanInfo(c);
        }
        catch (java.lang.ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (java.beans.IntrospectionException e) {
            e.printStackTrace();
        }    
        
        mDescriptorList = bi.getMethodDescriptors();
        mList = new Method[mDescriptorList.length];
        String mthName = null;
        
        for (int i = 0; i < mDescriptorList.length; i++) {
            mList[i] = mDescriptorList[i].getMethod();
            mthName = mList[i].getName();
            if(n2e.get(mthName) != null) {
                Long evt = (Long) n2e.get(mthName);
                e2m.put(evt, mList[i]);
            }
        } 
    
    }

    
    public PlanePlugin(VirTool virToolRef) {
        super(virToolRef);
    }

   public void performAction(Form form, SceneGraphEditor editor, Session session) throws SGPluginException {
        PlaneForm toolForm = (PlaneForm) form;
               
        int action = toolForm.getAction();
        
        VirPlaneTool instance = (VirPlaneTool) toolForm.getVirToolRef();
        VTool vTool = instance.getVToolRef();
        
        if(e2m.get(new Long(action)) != null) {
                Object[] params = new Object[2];
                params[0] = toolForm;
                params[1] = editor;

                Method method = (Method) e2m.get(new Long(action));

                try {
                    method.invoke(this, params); 
                } catch (IllegalArgumentException ex) {
                    ex.printStackTrace();
                } catch (InvocationTargetException ex) {
                    ex.printStackTrace();
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                }            
        } else {
            OnDefault(action);
        }
        
    }
   
   public void OnMousePressedEvent(VToolForm form, SceneGraphEditor editor) {
        
        VirPlaneTool instance = (VirPlaneTool) form.getVirToolRef();
        PlaneAdapter tool = (PlaneAdapter) instance.getVToolRef();
        
        if(form.getLGripPt().z < 0.01) {
            return;
        }
        
        Transform3D temp = new Transform3D();
        Transform3D curr = new Transform3D();

        instance.getVToolRef().setLGripPt(null);

        java.util.BitSet visibleNodes = 
                new java.util.BitSet(instance.getToolBaseSWG().numChildren());

        visibleNodes.set(0);

        instance.getPlaneHandle().alignPosition();
        instance.getPlaneHandle().alignDirection();
        PlaneAdapter adapter = (PlaneAdapter) instance.getVToolRef();
        instance.getPlaneHandle().setRelativePosition(adapter.getPosition4Plane());

        visibleNodes.set(1);

        instance.getToolBaseSWG().setChildMask(visibleNodes); 

        BehaviorObserver observer = instance.getBhvObserver();

        PlaneGUIForm guiform = (PlaneGUIForm) instance.getVUIToolFormRef();
        PlaneOperatorsForm operatorsForm = (PlaneOperatorsForm) instance.getVToolOperatorsFormRef();

        observer.update(guiform);
        observer.update(operatorsForm);       
   }
   
   public void OnMouseReleasedEvent(VToolForm form, SceneGraphEditor editor) {
       VirTool instance = form.getVirToolRef();
       VTool vTool = instance.getVToolRef();
       
       vTool.setLGripPt(null);
       vTool.setVWGripPt(null);
        
       java.util.BitSet visibleNodes = new java.util.BitSet(instance.getToolBaseSWG().numChildren());
       visibleNodes.set(0);
       instance.getToolBaseSWG().setChildMask(visibleNodes);       
   }
   
   public void OnMousePlanarHandlePressed(VToolForm form, SceneGraphEditor editor) {
       // nothing
   }
   
   public void OnMousePlanarHandleReleased(VToolForm form, SceneGraphEditor editor) {
       OnMouseReleasedEvent(form, editor);
   }
   
   public void OnMousePlanarHandleClicked(VToolForm form, SceneGraphEditor editor) {
       OnMouseReleasedEvent(form, editor);
   }   
   
   public void OnMouseDraggedHitOtherTool(VToolForm form, SceneGraphEditor editor) {
       OnMouseReleasedEvent(form, editor);
   }   
   
   
   public void OnDefault(int action) {
       System.out.println("[ " + action + " ] NO SUCH ACTION !!!!");
   }
   
   public void OnMouseTranslateXY(VToolForm form, SceneGraphEditor editor) {
       VirTool instance = form.getVirToolRef();
       VTool vTool = instance.getVToolRef();

       Point3d newVWPt = form.getVWGripPt();
       Point3d oldVWPt = vTool.getVWGripPt();
            
       if(newVWPt != null && oldVWPt != null) {
           translation = new Vector3d();
           transformX = new Transform3D();
                
           translation.sub(newVWPt, oldVWPt);
                
           vTool.getTransform(currXform);
           transformX.set(translation);
           currXform.mul(transformX, currXform);  
           vTool.setTransform(currXform);                    
               
           BehaviorObserver observer = vTool.getVirToolRef().getBhvObserver();
                
           PlaneGUIForm guiform = (PlaneGUIForm) instance.getVUIToolFormRef();
           PlaneOperatorsForm operatorsForm = (PlaneOperatorsForm) instance.getVToolOperatorsFormRef();                

           observer.update(guiform);
           observer.update(operatorsForm);  
                
       } else if(newVWPt == null) {
           vTool.setVWGripPt(null);
       }
            
       vTool.setVWGripPt(newVWPt);                  
   }
}