package edu.pse.beast.api.testrunner.threadpool;

public class ThreadPoolRunner implements Runnable {

	private boolean keepRunning = true;
	private WorkSupplier workSupplier;
	private String id;
	private WorkUnit work;

	public ThreadPoolRunner(String id) {
		this.id = id;
	}

	public void setWorkSupplier(WorkSupplier workSupplier) {
		this.workSupplier = workSupplier;
	}

	@Override
	public void run() {
		while (keepRunning) {
			if (workSupplier == null) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				work = workSupplier.getWorkUnit();
				if (work != null)
					work.doWork();
				else {
					if (workSupplier.isFinished()) {
						workSupplier = null;
					}
				}
			}
		}		
	}

	public void shutdown() {
		work.interrupt();
		keepRunning = false;
	}

}
