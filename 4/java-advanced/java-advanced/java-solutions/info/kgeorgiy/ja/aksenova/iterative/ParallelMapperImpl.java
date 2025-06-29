package info.kgeorgiy.ja.aksenova.iterative;

import info.kgeorgiy.java.advanced.mapper.ParallelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Parallel task assignment support.
 * Implements the {@link ParallelMapper}
 *
 * @author Valeria Aksenova
 */
public class ParallelMapperImpl implements ParallelMapper {
    private final List<Thread> workers;
    private final TaskQueue<Runnable> tasks;

    /**
     * Constructor for tasks mapper.
     * I
     *
     * @param threads amount of threads scheduler is going to use
     */
    public ParallelMapperImpl(int threads) {
        this.tasks = new TaskQueue<>();
        this.workers = new ArrayList<>();
        for (int i = 0; i < threads; i++) {
            Thread worker = new Thread(() -> {
                try {
                    while (!Thread.interrupted()) {
                        tasks.get().run();
                    }
                } catch (InterruptedException ignored) {
                }
            });
            workers.add(worker);
            worker.start();
        }
    }

    /**
     * Maps function {@code f} over specified {@code items}.
     * Mapping for each item is performed in parallel.
     *
     * @throws InterruptedException if calling thread was interrupted
     */
    @Override
    public <T, R> List<R> map(Function<? super T, ? extends R> f, List<? extends T> items) throws InterruptedException {
        int size = items.size();
        final List<Task<R>> resultTasks = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            int index = i;
            Task<R> task = new Task<>();
            resultTasks.add(task);
            tasks.add(() -> {
                try {
                    task.setResult(f.apply(items.get(index)));
                } catch (Exception e) {
                    task.setException(e);
                }
            });
        }
        List<R> results = new ArrayList<>(size);
        for (Task<R> task : resultTasks) {
            results.add(task.getResult());
        }
        return results;
    }


    /**
     * Stops all threads.
     * <p>Easy version: all unfinished mappings are left in undefined state.</p>
     * <p>Hard version: all unfinished mappings should throw exception.</p>
     */

    //note -- good practice
    @Override
    public void close() {
        for (Thread worker : workers) {
            worker.interrupt();
        }
        try {
            IterativeParallelism.joinThreads(workers);
        } catch (InterruptedException ignored) {
        }
    }
}
