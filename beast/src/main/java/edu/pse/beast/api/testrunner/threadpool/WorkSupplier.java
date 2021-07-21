package edu.pse.beast.api.testrunner.threadpool;

import java.util.ArrayList;
import java.util.List;

public class WorkSupplier {
    private List<WorkUnit> newWorkUnits = new ArrayList<>();

    public final void addWork(final WorkUnit wu) {
        synchronized (newWorkUnits) {
            newWorkUnits.add(wu);
        }
    }

    public final void addWork(final List<WorkUnit> wul) {
        synchronized (newWorkUnits) {
            newWorkUnits.addAll(wul);
        }
    }

    private WorkUnit popBack() {
        synchronized (newWorkUnits) {
            final WorkUnit unit = newWorkUnits.get(newWorkUnits.size() - 1);
            newWorkUnits.remove(newWorkUnits.size() - 1);
            return unit;
        }
    }

    public final WorkUnit getWorkIfAvailable() {
        synchronized (newWorkUnits) {
            if (!newWorkUnits.isEmpty()) {
                return popBack();
            } else {
                return null;
            }
        }
    }

}
