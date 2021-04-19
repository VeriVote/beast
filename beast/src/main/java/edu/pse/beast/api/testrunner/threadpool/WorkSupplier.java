package edu.pse.beast.api.testrunner.threadpool;

import java.util.ArrayList;
import java.util.List;

public class WorkSupplier {
	private List<WorkUnit> newWorkUnits = new ArrayList<>();

	public void addWork(WorkUnit wu) {
		synchronized (newWorkUnits) {
			newWorkUnits.add(wu);
		}
	}
	
	public void addWork(List<WorkUnit> wul) {
		synchronized (newWorkUnits) {
			newWorkUnits.addAll(wul);
		}
	}

	private WorkUnit popBack() {
		synchronized (newWorkUnits) {
			WorkUnit unit = newWorkUnits.get(newWorkUnits.size() - 1);
			newWorkUnits.remove(newWorkUnits.size() - 1);
			return unit;
		}
	}

	public WorkUnit getWorkIfAvailable() {
		synchronized (newWorkUnits) {
			if (!newWorkUnits.isEmpty()) {
				return popBack();
			} else {
				return null;
			}
		}
	}

}
