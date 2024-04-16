package edu.ncsu.github;

public class Timer {
	private boolean isRunning; // To track if the timer is running
	private final Runnable task; // The task to be executed
	private Thread thread; // The thread for executing the task
	private final long interval; // Interval between task executions
	private long startTime; // To store the start time

	public Timer(Runnable task, long interval) {
		this.task = task;
		this.interval = interval;
		isRunning = false;
	}

	// Start the timer
	public synchronized void start() {
		if (!isRunning) {
			isRunning = true;
			startTime = System.currentTimeMillis(); // Record the start time
			thread = new Thread(() -> {
				while (isRunning) {
					try {
						Thread.sleep(interval); // Sleep for the specified interval
						task.run(); // Execute the task
					} catch (InterruptedException e) {
						// Handle interruption
						Thread.currentThread().interrupt();
					}
				}
			});
			thread.start();
		}
	}

	// Stop the timer
	public synchronized void stop() {
		if (isRunning) {
			isRunning = false;
			thread.interrupt(); // Interrupt the thread to stop it
		}
	}

	// Reset the timer
	public synchronized void reset() {
		stop(); // Stop the timer
		startTime = 0; // Reset the start time
	}

	// Get the elapsed time in milliseconds
	public synchronized long getTime() {
		if (isRunning) {
			return System.currentTimeMillis() - startTime; // If timer is running, calculate elapsed time from start time
		} else {
			return 0; // If timer is stopped, return 0
		}
	}
}
