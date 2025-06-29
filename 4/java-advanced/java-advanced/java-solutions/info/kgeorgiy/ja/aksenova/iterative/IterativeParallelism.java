package info.kgeorgiy.ja.aksenova.iterative;

import info.kgeorgiy.java.advanced.iterative.ScalarIP;
import info.kgeorgiy.java.advanced.mapper.ParallelMapper;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;

/**
 * Scalar iterative parallelism support.
 * Implements the {@link ScalarIP}
 *
 * @author Valeria Aksenova
 */
public class IterativeParallelism implements ScalarIP {
    private final ParallelMapper mapper;

    /**
     * Default constructor.
     */
    public IterativeParallelism() {
        this(null);
    }

    /**
     * Constructor initializes mapper task scheduler.
     *
     * @param mapper task scheduler.
     */
    public IterativeParallelism(ParallelMapper mapper) {
        this.mapper = mapper;
    }

    private <T, R> R performOperation(int threads, List<T> list, Function<List<T>, R> function, Function<List<R>, R> finishing) throws InterruptedException {
        if (list == null || list.isEmpty()) {
            throw new NoSuchElementException("Empty list");
        }
        threads = Math.min(threads, list.size());
        int chunk = list.size() / threads;
        int remainder = list.size() % threads;
        final List<List<T>> chunks = new ArrayList<>(threads);
        int offset = 0;
        for (int i = 0; i < threads; i++) {
            int currentSize = chunk + (i < remainder ? 1 : 0);
            chunks.add(list.subList(offset, offset + currentSize));
            offset += currentSize;
        }
        if (mapper != null) {
            return finishing.apply(mapper.map(function, chunks));
        }

        List<Thread> workers = new ArrayList<>(threads);
        List<R> results = new ArrayList<>(threads);
        for (int i = 0; i < threads; i++) {
            results.add(null);
        }
        for (int i = 0; i < threads; i++) {
            int finalI = i;
            Thread thread = new Thread(() -> results.set(finalI, function.apply(chunks.get(finalI))));
            workers.add(thread);
            thread.start();
        }
        joinThreads(workers);
        return finishing.apply(results);
    }

    /**
     * Joins threads.
     *
     * @param workers list of threads.
     * @throws InterruptedException if joining failed.
     */
    public static void joinThreads(List<Thread> workers) throws InterruptedException {
        InterruptedException exception = null;
        for (Thread thread : workers) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                exception = e;
            }
        }
        if (exception != null) {
            throw exception;
        }
    }

    /**
     * Returns index of the first maximum.
     *
     * @param threads    number of concurrent threads.
     * @param values     values to find maximum in.
     * @param comparator value comparator.
     * @param <T>        value type.
     * @return index of the first maximum in given values.
     * @throws InterruptedException             if executing thread was interrupted.
     * @throws java.util.NoSuchElementException if no values are given.
     */
    @Override
    public <T> int argMax(int threads, List<T> values, Comparator<? super T> comparator) throws InterruptedException {
        return performOperation(threads, values, chunk -> chunk.stream().max(comparator).map(values::indexOf).orElse(-1), i -> i.stream().max(Comparator.comparing(values::get, comparator)).orElse(-1));
    }

    /**
     * Returns index of the first minimum.
     *
     * @param threads    number of concurrent threads.
     * @param values     values to find minimum in.
     * @param comparator value comparator.
     * @param <T>        value type.
     * @return index of the first minimum in given values.
     * @throws InterruptedException             if executing thread was interrupted.
     * @throws java.util.NoSuchElementException if no values are given.
     */
    @Override
    public <T> int argMin(int threads, List<T> values, Comparator<? super T> comparator) throws InterruptedException {
        return argMax(threads, values, comparator.reversed());
    }


    /**
     * Returns the index of the first value satisfying a predicate.
     *
     * @param threads   number of concurrent threads.
     * @param values    values to test.
     * @param predicate test predicate.
     * @param <T>       value type.
     * @return index of the first value satisfying the predicate, or {@code -1}, if there are none.
     * @throws InterruptedException if executing thread was interrupted.
     */
    @Override
    public <T> int indexOf(int threads, List<T> values, Predicate<? super T> predicate) throws InterruptedException {
        return performOperation(threads, values, sublist -> IntStream.range(0, sublist.size()).filter(i -> predicate.test(sublist.get(i))).map(i -> values.indexOf(sublist.get(i))).findFirst().orElse(-1), i -> i.stream().filter(index -> index != -1).min(Integer::compareTo).orElse(-1));
    }

    /**
     * Returns the index of the last value satisfying a predicate.
     *
     * @param threads   number of concurrent threads.
     * @param values    values to test.
     * @param predicate test predicate.
     * @param <T>       value type.
     * @return index of the last value satisfying the predicate, or {@code -1}, if there are none.
     * @throws InterruptedException if executing thread was interrupted.
     */
    @Override
    public <T> int lastIndexOf(int threads, List<T> values, Predicate<? super T> predicate) throws InterruptedException {
        int i = indexOf(threads, values.reversed(), predicate);
        if (i != -1) {
            i = values.size() - i - 1;
        }
        return i;
    }

    /**
     * Returns sum of the indices of the values satisfying a predicate.
     *
     * @param threads   number of concurrent threads.
     * @param values    values to test.
     * @param predicate test predicate.
     * @param <T>       value type.
     * @return sum of the indices of values satisfying the predicate.
     * @throws InterruptedException if executing thread was interrupted.
     */
    @Override
    public <T> long sumIndices(int threads, List<? extends T> values, Predicate<? super T> predicate) throws InterruptedException {
        return performOperation(threads, IntStream.range(0, values.size()).boxed().toList(), chunk -> {
            long sum = 0;
            for (int i : chunk) {
                if (predicate.test(values.get(i))) {
                    sum += i;
                }
            }
            return sum;
        }, results -> results.stream().mapToLong(Long::longValue).sum());
    }
}
