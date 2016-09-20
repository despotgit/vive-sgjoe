package org.sgJoe.graphics.event;

import org.apache.log4j.Logger;
import org.sgJoe.tools.interfaces.*;


/*
 * Description for ToolSelectedPublisher.java
 *
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: May 10, 2006  12:53 PM  $
 */

public interface ToolSelectedPublisher extends EventPublisher {
    public void setVirToolRef(VirTool virToolRef);
}
