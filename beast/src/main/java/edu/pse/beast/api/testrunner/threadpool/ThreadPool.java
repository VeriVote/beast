package edu.pse.beast.api.testrunner.threadpool;

import java.util.ArrayList;
import java.util.List;

public class ThreadPool {
    private ArrayList<Thread> threads = new ArrayList<>();
    private ArrayList<ThreadPoolRunner> runners = new ArrayList<>();
    private WorkSupplier workSupplier = new WorkSupplier();

    public ThreadPool(int numThreads) {
        for (int i = 0; i < numThreads; ++i) {
            ThreadPoolRunner runner = new ThreadPoolRunner("Runner_" + i,
                    workSupplier);
            Thread t = new Thread(runner);
            t.start();
            runners.add(runner);
            threads.add(t);
        }
    }

    public void addWork(List<WorkUnit> wul) {
        for (WorkUnit wu : wul)
            wu.addedToQueue();
        workSupplier.addWork(wul);
    }

    public void addWork(WorkUnit wu) {
        wu.addedToQueue();
        workSupplier.addWork(wu);
    }

    public void waitSync() throws InterruptedException {

    }

    public void shutdown() throws InterruptedException {
        for (ThreadPoolRunner runner : runners)
            runner.shutdown();
        for (Thread t : threads)
            t.join(100);
    }

}
