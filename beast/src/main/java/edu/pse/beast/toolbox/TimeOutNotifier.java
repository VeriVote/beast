package edu.pse.beast.toolbox;

import edu.pse.beast.propertychecker.FactoryController;

/**
 * The Class TimeOutNotifier.
 *
 * @author Lukas Stapelbroek
 */
public class TimeOutNotifier implements Runnable {

    /** The disabled. */
    private boolean disabled = false;

    /** The to interrupt. */
    private final FactoryController toInterrupt;

    /** The end time. */
    private final long endTime;

    /** The interrupter thread. */
    private final Thread interrupterThread;

    /**
     * This class is used for notifying FactoryController that a set amount of
     * time has passed.
     *
     * @param toInterruptController
     *            the FactoryController to interrupt
     * @param timeOut
     *            the time to wait before sending the interrupt message
     */
    public TimeOutNotifier(final FactoryController toInterruptController,
                           final long timeOut) {
        this.toInterrupt = toInterruptController;
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
                    ErrorLogger.log("The Timeout interrupter was interrupted. "
                                    + "It still continues though");
                }
            }
        }
        if (!disabled) {
            // now stop the checking of the factory controller
            toInterrupt.stopChecking(true);
        }
    }

    /**
     * Disables the notifier so it does not interrupt later by accident.
     */
    public void disable() {
        disabled = true;
        if (interrupterThread.isAlive()) {
            interrupterThread.interrupt();
        }
    }
}
