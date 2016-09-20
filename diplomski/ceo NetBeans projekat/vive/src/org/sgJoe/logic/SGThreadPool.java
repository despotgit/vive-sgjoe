package org.sgJoe.logic;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

/**
 * Tasks that wish to get run by the thread pool must implement the
 * <tt>java.lang.Runnable</tt> interface. Such tasks are placed in
 * the pool via the <tt>add()</tt> method. 
 * <p>
 * Usage:
 * <pre>
 * // Create a pool with 5 threads.
 * ThreadPool pool = new ThreadPool(5);
 * Start the pool
 * pool.start();
 * // Add an entry to be "run" by the pool.
 * pool.add(new Worker());
 *
 * // The following object will be "run" by the thread pool
 * // beginning in the run() method.
 * class Worker implements Runnable {
 *	public void run() {
 * 		. . .
 *	}
 * }
 * </pre>
 *
 * @author   $ Author: Aleksandar Babic         $
 * @version  $ Revision:            0.1         $
 * @date     $ Date:  December 1, 2005 12:01 AM $
 */
public class SGThreadPool {
    
    private static Logger logger = Logger.getLogger(SGThreadPool.class);
    
    private Thread[] pool;
    private SGJWorkQueue workQueue;
    public static final int DEFAULT_SIZE = 3;
    private volatile boolean shouldRun = true;
    private boolean started = false;

  
    /**
      * Create a default size thread pool.
      */
    public SGThreadPool() {
	this(DEFAULT_SIZE);
    }

    /**
      * Create a thread pool.
      * @arg int size - The number of threads initially created.
      */
    public SGThreadPool(int size) 
    {
        if (size < 1) 
        {
            throw new IllegalArgumentException();
        }

	workQueue = new SGJWorkQueue();
	pool = new WorkerThread[size];
    }

    /**
      * Starts the thread pool running. Each thread in the pool waits
      * for work to be added using the add() method.
      */
    public void startPool() 
    {
        if (!started) 
        {
            started = true;
            for (int i = 0; i < pool.length; i++) 
            {
                pool[i] = new WorkerThread("JBThread_" + new Integer(i).toString());
                pool[i].start();
            }
	}
    }

    /**
      * Stop the pool.
      */
    public void stopPool() {
        shouldRun = false;
    }

    /**
      * Add work to the queue.
      * @arg Runnable task - the task that is to be run.
      */
    public void add(Runnable task) {
        workQueue.addWork(task);
    }

    /**
      * inner class that does all the work
      */
    private class WorkerThread extends Thread 
    {
        private WorkerThread(String name) 
        {
            setName(name);
	}

	public void run() 
        {
            while (shouldRun) 
            {
                try 
                {
                    Runnable work  = (Runnable)workQueue.getWork();
                    // Causes doRun.run() to be executed asynchronously on the AWT event 
                    // dispatching thread. This will happen after all pending AWT events 
                    // have been processed. This method should be used when an application 
                    // thread needs to update the GUI.
                    SwingUtilities.invokeLater(work);
		} 
                catch (InterruptedException ie) 
                {
                    logger.error(ie);
                    shouldRun = false;
		}
            }
        }
    }
    
}
