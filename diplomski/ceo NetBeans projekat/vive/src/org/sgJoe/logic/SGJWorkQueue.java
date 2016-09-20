package org.sgJoe.logic;

import java.util.LinkedList;

import org.apache.log4j.Logger;

/**
 * The worker queue is a linked list where runnable obejcts are stored.
 *
 * @author   $ Author: Aleksandar Babic             $
 * @version  $ Revision:            0.1             $
 * @date     $ Date:  November 30, 2005 11:47 PM    $
 */
public class SGJWorkQueue {
    
    private static Logger logger = Logger.getLogger(SGJWorkQueue.class);
    
    private LinkedList work;

    /**
      * Init new worker queue
      */
    public SGJWorkQueue() {
        work = new LinkedList();
    }

    /**
      * Appends a new task to the queue. This method is synchronized.
      *
      * @param task The task to append to the queue.
      */
    public synchronized void addWork(Runnable task) 
    {
        work.add(task);
	notify();
    }

    /**
      * Returns a Runnable from the queue.
      *
      * @return Object The Runnable from the queue.
      * @throws InterruptedException Exception occured.
      */
    public synchronized Object getWork() throws InterruptedException 
    {
	while (work.isEmpty()) 
        {
            try 
            {
                wait();
            } catch (InterruptedException ie) {
                throw ie;
            }
	}

	return work.remove(0);	
    }
    
}
