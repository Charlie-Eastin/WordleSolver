package edu.ncsu.github;

/**
 * A simple timer class that executes a task at regular intervals.
 */
public class Timer {

    private final Runnable task;      // The task to be executed
    private final long     interval;  // Interval between task executions
    private boolean        isRunning; // To track if the timer is running
    private Thread         thread;    // The thread for executing the task
    private long           startTime; // To store the start time

    /**
     * Constructor to initialize the timer with a task and interval.
     *
     * @param task
     *            The task to be executed.
     * @param interval
     *            The interval between task executions in milliseconds.
     */
    public Timer ( final Runnable task, final long interval ) {
        this.task = task;
        this.interval = interval;
        isRunning = false;
    }

    /**
     * Start the timer.
     */
    public synchronized void start () {
        if ( !isRunning ) {
            isRunning = true;
            startTime = System.currentTimeMillis(); // Record the start time
            thread = new Thread( () -> {
                while ( isRunning ) {
                    try {
                        Thread.sleep( interval ); // Sleep for the specified
                                                  // interval
                        if (task != null) {
                            System.out.println( "RUNNING TASK" );
                            task.run(); // Execute the task
                        }
                    }
                    catch ( final InterruptedException e ) {
                        // Handle interruption
                        Thread.currentThread().interrupt();
                    }
                }
            } );
            thread.start();
        }
    }

    /**
     * Stop the timer.
     */
    public synchronized void stop () {
        if ( isRunning ) {
            System.out.println("Time taken: " + getTime() + "ms");
            isRunning = false;
            thread.interrupt(); // Interrupt the thread to stop it
        }
    }

    /**
     * Reset the timer.
     */
    public synchronized void reset () {
        stop(); // Stop the timer
        startTime = 0; // Reset the start time
    }

    /**
     * Get the elapsed time in milliseconds.
     *
     * @return The elapsed time since the timer started in milliseconds.
     */
    public synchronized long getTime () {
        if ( isRunning ) {
            return System.currentTimeMillis() - startTime; // If timer is
                                                           // running, calculate
                                                           // elapsed time from
                                                           // start time
        }
        else {
            return 0; // If timer is stopped, return 0
        }
    }
}
