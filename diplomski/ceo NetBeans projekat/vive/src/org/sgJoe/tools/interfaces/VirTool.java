package org.sgJoe.tools.interfaces;

import javax.swing.JPanel;
import org.apache.log4j.Logger;

import javax.media.j3d.*;
import org.sgJoe.graphics.*;
import org.sgJoe.graphics.behaviors.*;
import org.sgJoe.tools.*;
import org.sgJoe.tools.decorators.*;


/*
 * Description for VirTool.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: April 17, 2006  4:52 PM    $
 */

public abstract class VirTool {
    
    private static Logger logger = Logger.getLogger(VirTool.class);
    
    protected Long      toolUID = null;
    protected String    toolName = null;
    protected String    toolDescription = null;
    protected String    instanceName = null;
    
    private VTool                 vToolRef = null;
    protected VToolPlugin           vToolPluginRef = null;
    protected VToolForm             vToolFormRef = null;
    protected VToolOperatorsForm    vToolOperatorsFormRef = null;
    protected VUIToolForm           vuiToolFormRef = null;    
    
    protected VToolHandleMap        toolHandleMap = null;
    // --> protected VToolMap              toolMap = null;
    
    protected BranchGroup           toolBaseBG = null;
    protected TransformGroup        toolBaseTG = null;
    protected Switch                toolBaseSWG = null;
    protected SceneGraphEditor      sgEditor   = null;
    private BehaviorObserver        bhvObserver = null;
    
    protected VToolTG trTG = null;

    public VirTool() {     
        createToolHandleMap();
        createBranchGroup();
        createTransformGroup();
        createSwitchGroup();
        
        createTrTG();
        
        getToolBaseBG().addChild(getToolBaseTG());
        
        createToolInstance(instanceName);
        
        // we will assign same UID as for VTool instance itself.....
        //setToolUID(getVToolRef().getToolUID());
        setToolUID(VToolFactory.generateVirToolUID());
        
        createPlugin();
    
        createForm();
    
        createVUIForm();
    
        createOperatorsForm(); 
        
    }
    
    public void createToolHandleMap() {
        this.toolHandleMap = new VToolHandleMap();
    }
    
    public void setup(SceneGraphEditor editor, BehaviorObserver behaviorObserver) {
        this.sgEditor = editor;
        this.bhvObserver = behaviorObserver;
       
        getVToolOperatorsFormRef().setSGEditor(editor);
        getVToolOperatorsFormRef().setup();

        this.sgEditor.getVirToolMap().put(this);        
    }
    
    private void createBranchGroup() {
        setToolBaseBG(new BranchGroup());
        VToolFactory.setBGCapabilities(getToolBaseBG());
    }

    private void createTransformGroup() {
        setToolBaseTG(new VToolTG());
        VToolFactory.setTGCapabilities((VToolTG)toolBaseTG);
    }
    
    private void createSwitchGroup() {
        setToolBaseSWG(new Switch(Switch.CHILD_MASK));
        VToolFactory.setSWGCapabilities(getToolBaseSWG());
    }

    private void createTrTG() {
        trTG = new VToolTG();
        VToolFactory.setTGCapabilities(getTrTG());
    }
    
    public Long getToolUID() {
        return toolUID;
    }

    public void setToolUID(Long toolUID) {
        this.toolUID = toolUID;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public String getToolDescription() {
        return toolDescription;
    }

    public void setToolDescription(String toolDescription) {
        this.toolDescription = toolDescription;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String toolInstanceName) {
        this.instanceName = toolInstanceName;
    }

     public VToolPlugin getVToolPluginRef() {
        return vToolPluginRef;
    }

    public void setVToolPluginRef(VToolPlugin vToolPluginRef) {
        this.vToolPluginRef = vToolPluginRef;
    }

    public VToolForm getVToolFormRef() {
        return vToolFormRef;
    }

    public void setVToolFormRef(VToolForm vToolFormRef) {
        this.vToolFormRef = vToolFormRef;
    }

    public VToolOperatorsForm getVToolOperatorsFormRef() {
        return vToolOperatorsFormRef;
    }

    public void setVToolOperatorsFormRef(VToolOperatorsForm vToolOperatorsFormRef) {
        this.vToolOperatorsFormRef = vToolOperatorsFormRef;
    }

    public VUIToolForm getVUIToolFormRef() {
        return vuiToolFormRef;
    }

    public void setVUIToolFormRef(VUIToolForm vuiToolFormRef) {
        this.vuiToolFormRef = vuiToolFormRef;
    }

    public BranchGroup getToolBaseBG() {
        return toolBaseBG;
    }

    public void setToolBaseBG(BranchGroup toolBaseBG) {
        this.toolBaseBG = toolBaseBG;
    }

    public TransformGroup getToolBaseTG() {
        return toolBaseTG;
    }

    public void setToolBaseTG(TransformGroup toolBaseTG) {
        this.toolBaseTG = toolBaseTG;
    }
    
    public abstract void createToolInstance(String string);
    
    public abstract void createForm();
    
    public abstract void createVUIForm();
    
    public abstract void createPlugin();
    
    public abstract void createOperatorsForm();    

    public abstract void setFocus();

    public VTool getVToolRef() {
        return vToolRef;
    }

    public void setVToolRef(VTool vToolRef) {
        this.vToolRef = vToolRef;
    }
    
    public SceneGraphEditor getSGEditor() {
        return sgEditor;
    }

    public BehaviorObserver getBhvObserver() {
        return bhvObserver;
    }
    
    public Switch getToolBaseSWG() {
        return toolBaseSWG;
    }

    public void setToolBaseSWG(Switch toolBaseSWG) {
        this.toolBaseSWG = toolBaseSWG;
    }    

    public VToolTG getTrTG() {
        return trTG;
    }
    
    // --- nikola ---
    public JPanel getToolPanel(){
        return vuiToolFormRef;
    }

    public JPanel [ ] getToolOperatorPanelList(){
        //return null;
        JPanel [] panel= new JPanel[1];
        panel[0] = vToolOperatorsFormRef;
        return panel;
    }
    
    public VToolHandle getToolHandle(String name) {
        return toolHandleMap.getToolHandle(name);
    }
    
    public void addToolHandle(String name, VToolHandle handle) {
        toolHandleMap.addToolHandle(name, handle);
    } 
    
    // used for creating instance names....
    public abstract long generateUID();
}
