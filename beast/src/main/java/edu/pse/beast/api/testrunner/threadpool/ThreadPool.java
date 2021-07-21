package edu.pse.beast.api.testrunner.threadpool;

import java.util.ArrayList;
import java.util.List;

public class ThreadPool {
    private ArrayList<Thread> threads = new ArrayList<>();
    private ArrayList<ThreadPoolRunner> runners = new ArrayList<>();
    private WorkSupplier workSupplier = new WorkSupplier();

    public ThreadPool(final int numThreads) {
        for (int i = 0; i < numThreads; ++i) {
            final ThreadPoolRunner runner =
                    new ThreadPoolRunner("Runner_" + i, workSupplier);
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
            t.join(100);
        }
    }
}
