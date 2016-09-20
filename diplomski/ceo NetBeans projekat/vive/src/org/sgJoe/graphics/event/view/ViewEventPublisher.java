/*
 * ViewEventPublisher.java
 *
 * Created on June 13, 2006, 9:21 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.sgJoe.graphics.event.view;

import javax.media.j3d.View;
import org.sgJoe.graphics.event.EventPublisher;

/**
 *
 * @author ealebab
 */
public interface ViewEventPublisher extends EventPublisher {

    public void setView(View view);    
    
}
