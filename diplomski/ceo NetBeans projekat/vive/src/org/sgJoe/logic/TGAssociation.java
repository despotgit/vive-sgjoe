package org.sgJoe.logic;

import javax.media.j3d.TransformGroup;
import org.apache.log4j.Logger;


/*
 * Descritpion for TGAssociation.java
 *
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:             0.1        $
 * @date     $ Date: March 27, 2006  9:04 AM    $
 */

public class TGAssociation {
    
    private static Logger logger = Logger.getLogger(TGAssociation.class);
    
    public static final int TGA_UNIDIRECTIONAL = 0, // (from 1st to 2nd)
                            TGA_BIDIRECTIONAL = 1;  // (in both directions)
                        
    
    private TransformGroup tg1st = null;
    private TransformGroup tg2nd = null;
    
    // unidirectional by default
    private int direction = 0;
    
    private String identifier = null;
   
    
    public TGAssociation(TransformGroup _tg1st, TransformGroup _tg2nd, String _identifier) throws IllegalArgumentException {
        TGAssociation(_tg1st, _tg2nd, _identifier, 0);
    }

    private void TGAssociation(TransformGroup _tg1st, TransformGroup _tg2nd, String _identifier, int _direction) throws IllegalArgumentException {
        if(_tg1st == null || _tg2nd == null) {
            throw new IllegalArgumentException("tg1st nor tg2nd can not be 'null'.");
        }
        if(_identifier == null) {
            throw new IllegalArgumentException("identifier can not be 'null'.");
        }        
        if(_direction < 0 || _direction > 1) {
            throw new IllegalArgumentException("direction must be 0 or 1.");
        }
        
        tg1st = _tg1st;
        tg2nd = _tg2nd;
        setIdentifier(_identifier);
        direction = _direction;
    }     

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    
    public TransformGroup get2ndTG() {
        return tg2nd;
    }

}
