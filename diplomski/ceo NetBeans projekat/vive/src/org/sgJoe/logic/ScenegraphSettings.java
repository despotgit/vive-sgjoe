package org.sgJoe.logic;

import org.apache.log4j.Logger;

/**
 * This class stores the settings for the scenegraph.
 * Seeting may be found in XML files.
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:            0.1         $
 * @date     $ Date:  December 7, 2005 6:46 PM  $
 */
public class ScenegraphSettings {
    
    private static Logger logger = Logger.getLogger(ScenegraphSettings.class);

    private double gridDimension = 10.0d;

    private double backClipDistance = 10000.0d;
    private double boundingRadius = 10000.0d;
  
    public ScenegraphSettings() { }
  
    /**
     * Creates a new instance of ScenegraphSettings.
     *
     * @param backClipDistance the backClipDistance
     * @param boundingRadius the boundingRadius
     * @param dimension the grid dimension
     */
      public ScenegraphSettings(double backClipDistance, double boundingRadius, double dimension) {
          if (backClipDistance < 0.0d) {
              logger.error("backclip distance too small");
          } else {
              this.backClipDistance = backClipDistance;
          }
          
          if (boundingRadius < 0.0d) {
              logger.error("bounding radius too small");
          } else {
              this.boundingRadius = boundingRadius;
          }
          
          if (dimension < 1) {
              logger.error("grid dimension too small");
          } else {
              this.gridDimension = dimension;
          }
      }
  
      /**
       * Returns the backClipDistance.
       *
       * @return The backClipDistance.
       */
      public double getBackClipDistance() {
          return backClipDistance;
      }

      /**
       * Returns the boundingRadius.
       *
       * @return The boundingRadius.
       */
      public double getBoundingRadius() {
          return boundingRadius;
      }
      
}
