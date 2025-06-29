package info.kgeorgiy.ja.aksenova.iterative;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Class which is used to arrange queue of {@link Task}
 *
 * @author Valeria Aksenova
 */
public class TaskQueue<R> {
    private final Deque<R> queue = new ArrayDeque<>();

    /**
     * Adds a new task to the front of the queue and notifies waiting threads.
     *
     * @param task the task to add to the queue
     */
    public synchronized void add(R task) {
        queue.addFirst(task);
        notify();
    }

    /**
     * Retrieves and returns a task from the end of the queue.
     * If the queue is empty, the method blocks until a new task becomes available.
     *
     * @return the task from the end of the queue
     * @throws InterruptedException if the thread was interrupted while waiting
     */
    public synchronized R get() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        return queue.removeLast();
    }
}
