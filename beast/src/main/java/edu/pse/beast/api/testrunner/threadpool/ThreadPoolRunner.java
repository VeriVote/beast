package edu.pse.beast.api.testrunner.threadpool;

public class ThreadPoolRunner implements Runnable {

    private boolean keepRunning = true;
    private String id;
    private WorkUnit work;
    private WorkSupplier workSupplier;

    public ThreadPoolRunner(String id, WorkSupplier workSupplier) {
        this.id = id;
        this.workSupplier = workSupplier;
    }

    @Override
    public void run() {
        while (keepRunning) {
            work = workSupplier.getWorkIfAvailable();
            if (work != null) {
                work.doWork();
            } else {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void shutdown() {
        work.interrupt();
        keepRunning = false;
    }

}
