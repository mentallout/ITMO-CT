package info.kgeorgiy.ja.aksenova.iterative;

/**
 * Task support for parallel function execution.
 *
 * @author Valeria Aksenova
 */
public class Task<R> {
    private R result = null;
    private boolean isCompleted = false;
    private Exception exception = null;

    /**
     * Saves result for each task.
     * Calls {@link Object#notifyAll()} to notify all threads waiting for the result.
     *
     * @param result the result of the task execution
     */
    public synchronized void setResult(R result) {
        this.result = result;
        this.isCompleted = true;
        notifyAll();
    }

    /**
     * Sets exception if task failed to execute.
     * Calls {@link Object#notifyAll()} to notify all threads waiting for the result.
     *
     * @param exception the exception that occurred during task execution
     */
    public synchronized void setException(Exception exception) {
        this.exception = exception;
        this.isCompleted = true;
        notifyAll();
    }

    /**
     * Returns the result of the task execution.
     *
     * @return the result of the task execution
     * @throws InterruptedException if the current thread was interrupted while waiting for the result
     */
    public synchronized R getResult() throws InterruptedException {
        while (!isCompleted) {
            wait();
        }
        if (exception != null) {
            throw (InterruptedException) exception;
        }
        return result;
    }
}
