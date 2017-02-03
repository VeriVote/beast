package edu.pse.beast.toolbox;

import edu.pse.beast.propertychecker.FactoryController;

public class TimeOutNotifier implements Runnable {
    private boolean disabled = false;
    private final FactoryController toInterrupt;
    private final long endTime;
    private final Thread interrupterThread;

    public TimeOutNotifier(FactoryController toInterrupt, long timeOut) {
        this.toInterrupt = toInterrupt;
        this.endTime = System.currentTimeMillis() + timeOut;
        interrupterThread = new Thread(this, "TimeOutNotifier");
        interrupterThread.start();
    }

    @Override
    public void run() {

        while (System.currentTimeMillis() < endTime && !disabled) {
        	
            try {
                Thread.sleep(endTime - System.currentTimeMillis());
            } catch (InterruptedException e) {
                if (!disabled) {
                    ErrorLogger.log("The Timeout interrupter was interrupted. It still continues though");
                }
            }
        }
        if (!disabled) {
            //now stop the checking of the factory controller
            toInterrupt.stopChecking(true);
        }
    }

    public void disable() {
        disabled = true;
        if (interrupterThread.isAlive()) {
            interrupterThread.interrupt();
        }
    }

}
