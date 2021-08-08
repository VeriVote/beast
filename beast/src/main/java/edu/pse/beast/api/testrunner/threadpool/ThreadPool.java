package edu.pse.beast.api.testrunner.threadpool;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class ThreadPool {
    private static final String THREAD_NAME_PREFIX = "Runner_";
    private static final int DEFAULT_TIMEOUT = 100;

    private ArrayList<Thread> threads = new ArrayList<Thread>();
    private ArrayList<ThreadPoolRunner> runners = new ArrayList<ThreadPoolRunner>();
    private WorkSupplier workSupplier = new WorkSupplier();

    public ThreadPool(final int numThreads) {
        for (int i = 0; i < numThreads; ++i) {
            final ThreadPoolRunner runner =
                    new ThreadPoolRunner(THREAD_NAME_PREFIX + i, workSupplier);
            final Thread t = new Thread(runner);
            t.start();
            runners.add(runner);
            threads.add(t);
        }
    }

    public final void addWork(final List<WorkUnit> wul) {
        for (final WorkUnit wu : wul) {
            wu.addedToQueue();
        }
        workSupplier.addWork(wul);
    }

    public final void addWork(final WorkUnit wu) {
        wu.addedToQueue();
        workSupplier.addWork(wu);
    }

    public final void waitSync() throws InterruptedException {
    }

    public final void shutdown() throws InterruptedException {
        for (final ThreadPoolRunner runner : runners) {
            runner.shutdown();
        }
        for (final Thread t : threads) {
            t.join(DEFAULT_TIMEOUT);
        }
    }
}
