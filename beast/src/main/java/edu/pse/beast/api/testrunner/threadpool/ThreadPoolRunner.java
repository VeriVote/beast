package edu.pse.beast.api.testrunner.threadpool;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class ThreadPoolRunner implements Runnable {
    private static final int DEFAULT_WATING_TIME = 200;

    private boolean keepRunning = true;
    private String id;
    private WorkUnit work;
    private WorkSupplier workSupplier;

    public ThreadPoolRunner(final String idString, final WorkSupplier workSupp) {
        this.id = idString;
        this.workSupplier = workSupp;
    }

    @Override
    public final void run() {
        while (keepRunning) {
            work = workSupplier.getWorkIfAvailable();
            if (work != null) {
                work.doWork();
            } else {
                try {
                    Thread.sleep(DEFAULT_WATING_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public final void shutdown() {
        work.interrupt();
        keepRunning = false;
    }

}
