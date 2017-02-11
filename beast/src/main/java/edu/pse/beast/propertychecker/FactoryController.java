/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.propertychecker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.highlevel.ElectionDescriptionSource;
import edu.pse.beast.highlevel.ParameterSource;
import edu.pse.beast.highlevel.PostAndPrePropertiesDescriptionSource;
import edu.pse.beast.highlevel.ResultInterface;
import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.TimeOutNotifier;

/**
 *
 * @author Niels & Lukas
 */
public class FactoryController implements Runnable {

    /**
     * gives access to the factorycontroller for the shutdown hook.
     */
    protected FactoryController thisObject = this;
    
    private final ElectionDescriptionSource electionDescSrc;
    private final PostAndPrePropertiesDescriptionSource postAndPrePropDescrSrc;
    private final ParameterSource parmSrc;
    private final List<Result> results;
    private final TimeOutNotifier notifier;

    private final long pollingInterval = 1000;

    private List<CheckerFactory> currentlyRunning;
    private final String checkerID;
    private volatile boolean stopped = false;
    private final int concurrentChecker;


    /**
     * 
     * @param electionDescSrc
     *            the source for the election descriptions
     * @param postAndPrePropDescrSrc
     *            the properties to be checked
     * @param parmSrc
     *            the parameter
     * @param checkerID
     *            the ID of the checker that should be used
     * @param concurrentChecker
     *            the amount of concurrent checker to be used
     */
    public FactoryController(ElectionDescriptionSource electionDescSrc,
            PostAndPrePropertiesDescriptionSource postAndPrePropDescrSrc, ParameterSource parmSrc,
            String checkerID, int concurrentChecker) {

        // add a shutdown hook so all the checker are stopped properly so they
        // don't clog the host pc
        Runtime.getRuntime().addShutdownHook(new FactoryEnder());

        this.electionDescSrc = electionDescSrc;
        this.postAndPrePropDescrSrc = postAndPrePropDescrSrc;
        this.parmSrc = parmSrc;
        this.checkerID = checkerID;
        this.currentlyRunning = new ArrayList<CheckerFactory>(concurrentChecker);

        this.results = CheckerFactoryFactory.getMatchingResult(checkerID,
                postAndPrePropDescrSrc.getPostAndPrePropertiesDescriptions().size());

        for (Iterator<Result> iterator = results.iterator(); iterator.hasNext();) {
            Result result = (Result) iterator.next();
            System.out.println(result.readyToPresent());
        }

        // if the user doesn't specify a concrete amount for concurrent
        // checkers, we just set it to the thread amount of this pc
        if (concurrentChecker <= 0) {
            this.concurrentChecker = Runtime.getRuntime().availableProcessors();
        } else {
            this.concurrentChecker = concurrentChecker;
        }

        // start the factorycontroller
        new Thread(this, "FactoryController").start();

        if (parmSrc.getParameter().getTimeout().isActive()) {
            notifier = new TimeOutNotifier(this, parmSrc.getParameter().getTimeout().getDuration());
        } else {
            notifier = null;
        }
    }

    /**
     * starts the factoryController, so it then starts the needed checker
     */
    @Override
    public void run() {

        List<PostAndPrePropertiesDescription> properties = postAndPrePropDescrSrc
                .getPostAndPrePropertiesDescriptions();

        outerLoop: for (int i = 0; i < properties.size(); i++) {
            innerLoop: while (!stopped) {
                if (currentlyRunning.size() < concurrentChecker) {
                    CheckerFactory factory = CheckerFactoryFactory.getCheckerFactory(checkerID, this,
                            electionDescSrc, properties.get(i), parmSrc, results.get(i));

                    synchronized (this) {
                        currentlyRunning.add(factory);
                    }

                    new Thread(factory, "CheckerFactory Property " + i).start();

                    break innerLoop;
                } else {
                    try {
                        Thread.sleep(pollingInterval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (stopped) {
                break outerLoop;
            }
        }
        while (currentlyRunning.size() > 0) {
            try {
                Thread.sleep(pollingInterval);
            } catch (InterruptedException e) {
                ErrorLogger.log("Was interrupted while waiting for the last processes to finish \n"
                        + "The waiting will still continue. To stop the factory properly, call \"stopChecking()\" !");
            }
        }
        if (notifier != null) {
            // if the notifier thread is still active, we stop it.
            notifier.disable();
        }
    }

    /**
     * tells the controller to stop checking. It stops all currently running
     * Checkers and doesn't start new ones.
     * 
     * @param timeOut
     *            if it is true, the checking was stopped because of a timeout;
     */
    public void stopChecking(boolean timeOut) {

        if (!stopped) {
            this.stopped = true;
            for (Iterator<CheckerFactory> iterator = currentlyRunning.iterator(); iterator.hasNext();) {
                CheckerFactory toStop = (CheckerFactory) iterator.next();
                toStop.stopChecking();
            }

            // set all not finished results to finished, to indicate that they
            // are
            // ready.
            for (Iterator<Result> iterator = results.iterator(); iterator.hasNext();) {
                Result result = (Result) iterator.next();
                if (!result.isFinished()) {
                    result.setFinished();
                    // in case of a timeout set a timeout flag
                    result.setForcefullyStopped();
                    if (timeOut) {
                        result.setTimeoutFlag();
                    }
                }
            }
        }
    }

    /**
     * notifies the factory that one of the started checker factories finished, so a new
     * one could be started.
     * @param finishedFactory the factory that just finished, so it can be removed
     *      from the list of ones to be notified when the checking is stopped forcefully
     */
    public synchronized void notifyThatFinished(CheckerFactory finishedFactory) {
        if (currentlyRunning.size() == 0) {
            ErrorLogger.log("A checker finished when no checker was active.");
        } else {
            synchronized (this) {
                currentlyRunning.remove(finishedFactory);
            }
        }
    }

    /**
     * 
     * @return a NEW list with all the results objects. This list is used
     *         nowhere in the propertychecker, so you can remove parts out of it
     *         as you want.
     */
    public List<ResultInterface> getResults() {
        if (results == null) {

            ErrorLogger.log("Result objects couldn't be created.");
            return null;

        } else {

            List<ResultInterface> toReturn = new ArrayList<ResultInterface>();

            for (Iterator<Result> iterator = results.iterator(); iterator.hasNext();) {
                Result result = (Result) iterator.next();
                toReturn.add(result);
            }

            return toReturn;
        }
    }

    public class FactoryEnder extends Thread {
        @Override
        public void run() {
            thisObject.stopChecking(false);
        }

    }
}
