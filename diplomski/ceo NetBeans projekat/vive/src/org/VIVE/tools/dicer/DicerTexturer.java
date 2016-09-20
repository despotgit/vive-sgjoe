/*
 * DicerTexturer.java
 *
 * Created on July 23, 2007, 3:03 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.VIVE.tools.dicer;

import org.sgJoe.tools.interfaces.iSpatialField;
import javax.media.j3d.*;
import javax.media.j3d.*;
import javax.vecmath.*;

/**
 *
 * @author Vladimir
 */

public class DicerTexturer extends Thread implements iSpatialField
{
    Transform3D transTr = new Transform3D();
    Transform3D rotTr = new Transform3D();
    Transform3D scaleTr = new Transform3D();
    
    DicerVirTool dvt;
    DicerBox boxShape;
    
    int quality = 1;
    int highestQuality = 0;

    Point3d p13; 
    Point3d p14; 
    Point3d p15; 
    Point3d p16;
    Point3d p17;
    Point3d p18; 
    Point3d p19; 
    Point3d p20;
    
    /** Creates a new instance of DicerTexturer */
    public DicerTexturer(DicerVirTool dvirtool) 
    {
        dvt = dvirtool;
        boxShape = ((DicerVTool)dvt.getVToolRef()).getShp();
        
        highestQuality = getHighestQuality();
        dvt.getTransTG().getTransform(transTr); 
        dvt.getRotTG().getTransform(rotTr); 
        dvt.getScaleTG().getTransform(scaleTr);
        
    }
    
    /* This method gets the texture specified by 4 Point3d parameters and quality parameter */
    public Texture getSpatialTexture(Point3d upperLeft , Point3d lowerLeft , 
                                     Point3d lowerRight , Point3d upperRight , 
                                     int Quality)
    {
        // Ovde se obavlja komunikacija sa Main-om ili nekim drugim delom FrameWork-a da bi se dobila
        // tekstura definisana sa ove cetiri tacke    
            
        // mozda:  Texture tex = Main.currentSession.currentSpatialField.getTexture(Point.......); 
        // ili nesto tome slicno...
        
        return null;   // kada bude dovrseno dohvatanje tekstura,onda se ovde vraca ta tekstura,a ne null
    }  
        
    /* Following method returns the 6 textures of given quality(for the dicer with given transformation matrices)
       using method getSpatialTexture */        
    public Texture[] getDicerTextures(  )
    {               
        Texture[] textures = new Texture[6];
        
        /* Ove koordinate za p13-p20 ce biti promenjene kada budem dovrsio kod 
         za inicijalno postavljanje Dicer-a */
        p13 = new Point3d( -1 ,  1 ,  1 ); 
        p14 = new Point3d( -1 , -1 ,  1 ); 
        p15 = new Point3d(  1 , -1 ,  1 ); 
        p16 = new Point3d(  1 ,  1 ,  1 );
        p17 = new Point3d( -1 ,  1 , -1 );
        p18 = new Point3d( -1 , -1 , -1 ); 
        p19 = new Point3d(  1 , -1 , -1 ); 
        p20 = new Point3d(  1 ,  1 , -1 );
        
        calculateVerticesCoords();
                
        
        /* use method getSpatialTexture 6 times to get all of the dicer textures */  
        textures[0] = getSpatialTexture( p13 , p14 , p15 , p16 , quality); //for side 0
        textures[1] = getSpatialTexture( p20 , p19 , p18 , p17 , quality); //for side 1
        textures[2] = getSpatialTexture( p16 , p15 , p19 , p20 , quality); //for side 2
        textures[3] = getSpatialTexture( p17 , p18 , p14 , p13 , quality); //for side 3
        textures[4] = getSpatialTexture( p17 , p13 , p16 , p20 , quality); //for side 4
        textures[5] = getSpatialTexture( p19 , p15 , p14 , p18 , quality); //for side 5
              
        return textures;
    }     
    
    /* Apply textures to each of the dicer's box sides */
    public void run()
    {   
        try
        {
            while(quality <= highestQuality)
            {
                setTexturesToBox();
                quality++;
                if (this.interrupted())  throw new InterruptedException();
            }
        }
        catch (InterruptedException e) 
        {
            System.out.println("Teksturna nit je prekinuta.");
            return;
        }
        
    }
    
    /* Calculates vertices' coordinates using the DicerVirTool to get the info on 
       Transform3Ds and then transforming the original vertices to get their actual 
       current position */
    private void calculateVerticesCoords()
    {
        dvt.getTransTG().getTransform(transTr);            //get current T3Ds
        dvt.getRotTG().getTransform(rotTr); 
        dvt.getScaleTG().getTransform(scaleTr);       

        transform(p13);             // transform the vertices' coordinates to 
        transform(p14);             // their actual values
        transform(p15);
        transform(p16);
        transform(p17);
        transform(p18);
        transform(p19);
        transform(p20);

    }
    
    /* This method transforms the point from its original place to its current,actual place */
    /* in virtual environment  */
    private void transform(Point3d aPoint)
    {
        rotTr.transform(aPoint);            //this is the correct
        scaleTr.transform(aPoint);          //order of applying
        transTr.transform(aPoint);          //the transformations for a point in 3D space
        
    }   
    
    private void resetQuality()
    {
        quality = 1;   // Pretpostavicu za sada da je ovo pocetna vrednost indeksa kvaliteta tekstura
    }
    
    private int getHighestQuality()
    {
        //return FW.getHighestResIndex();        
        return 0;       //za sada return 0
    }
    
    private void setTexturesToBox()
    {
        boolean gotovo = false;                  // kad bude dovrseno dohvatanje tekstura,ukljuciti gotovo na true
        Texture[] textures = getDicerTextures();
        if(gotovo)                               //da bi se ove postavile tamo gde treba da se postave
        {
            boxShape.getShape(0).getAppearance().setTexture(textures[0]);
            boxShape.getShape(1).getAppearance().setTexture(textures[1]);
            boxShape.getShape(2).getAppearance().setTexture(textures[2]);
            boxShape.getShape(3).getAppearance().setTexture(textures[3]);
            boxShape.getShape(4).getAppearance().setTexture(textures[4]);
            boxShape.getShape(5).getAppearance().setTexture(textures[5]);
        }
        
    }
    
    
}
