package edu.pse.beast.propertychecker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.highlevel.javafx.ChildTreeItem;
import edu.pse.beast.highlevel.javafx.ParentTreeItem;
import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.TimeOutNotifier;

/**
 * The Class FactoryController.
 *
 * @author Niels Hanselmann, Lukas Stapelbroek
 */
public final class FactoryController implements Runnable {
    /** The Constant POLLING_INTERVAL. */
    private static final long POLLING_INTERVAL = 1000;

    /** The Constant ERROR_MSG. */
    private static final String ERROR_MSG =
            "Was interrupted while waiting for the last processes "
            + "to finish \n"
            + "The waiting will still continue. To stop the factory "
            + "properly, call \"stopChecking()\" !";

    /**
     * Gives access to the factory controller for the shutdown hook.
     */
    private FactoryController thisObject = this;

    /** The properties to check. */
    private List<ChildTreeItem> propertiesToCheck = new ArrayList<ChildTreeItem>();

    // private final ParameterSource parmSrc;
    /** The notifier. */
    // private final List<Result> results;
    private final TimeOutNotifier notifier;

    // private final List<ParentTreeItem> propertyParents;

    /** The currently running. */
    private List<CheckerFactory> currentlyRunning;

    /** The checker ID. */
    private final String checkerID;

    /** The stopped. */
    private volatile boolean stopped;

    /** The concurrent checker. */
    private final int concurrentChecker;

    /** The parameter. */
    private ElectionCheckParameter parameter;

    /** The elec desc. */
    private final ElectionDescription elecDesc;

    // private final List<ParentTreeItem> propertyParents;

    /** The results. */
    private List<Result> results = new ArrayList<Result>();

    /** The tree items. */
    private List<ChildTreeItem> treeItems = new ArrayList<ChildTreeItem>();

    // /**
    // *
    // * @param electionDescSrc
    // * the source for the election descriptions
    // * @param preAndPostConditionDescrSrc
    // * the properties to be checked
    // * @param parmSrc
    // * the parameter
    // * @param checkerID
    // * the ID of the checker that should be used
    // * @param concurrentChecker
    // * the amount of concurrent checker to be used
    // **/
    //  public FactoryController(ElectionDescriptionSource electionDescSrc,
    //                           PreAndPostConditionsDescriptionSource preAndPostConditionDescrSrc,
    //                           ParameterSource parmSrc, String checkerID,
    //                           int concurrentChecker) {
    //      // add a shutdown hook, so all the checker are stopped properly so they
    //      // do not clog the host pc
    //      Runtime.getRuntime().addShutdownHook(new FactoryEnder());
    //
    //      this.electionDescSrc = electionDescSrc;
    //      this.preAndPostConditionDescrSrc = preAndPostConditionDescrSrc;
    //      // this.parmSrc = parmSrc;
    //      this.checkerID = checkerID;
    //      this.currentlyRunning = new ArrayList<CheckerFactory>(concurrentChecker);
    //
    //      // get a list of result objects that fit for the specified checkerID
    //      this.results = CheckerFactoryFactory.getMatchingResult(checkerID,
    //      preAndPostConditionDescrSrc.getPreAndPostPropertiesDescriptionsCheckAndMargin().size());
    //
    //      // if the user does not specify a specific amount for concurrent
    //      // checkers, we just set it to the thread amount of this computer
    //      if (concurrentChecker <= 0) {
    //          this.concurrentChecker = Runtime.getRuntime().availableProcessors();
    //      } else {
    //          this.concurrentChecker = concurrentChecker;
    //      }
    //      // start the factory controller
    //      new Thread(this, "FactoryController").start();
    //
    //      // if the user wishes for a timeout, we activate it here
    //      if (parmSrc.getParameter().getTimeout().isActive()) {
    //          notifier = new TimeOutNotifier(this,
    //          parmSrc.getParameter().getTimeout().getDuration());
    //      } else {
    //          notifier = null;
    //      }
    // }
    //
    // public FactoryController(File toCheck, ParameterSource parmSrc, String
    //                          checkerID, int concurrentChecker) {
    //     // add a shutdown hook, so all the checkers are stopped properly so they
    //     // do not clog the host pc
    //     Runtime.getRuntime().addShutdownHook(new FactoryEnder());
    //
    //     this.fromFile = true;
    //
    //     // this.parmSrc = parmSrc;
    //     this.checkerID = checkerID;
    //     this.currentlyRunning = new ArrayList<CheckerFactory>(concurrentChecker);
    //
    //     // we do not need these if we start with a file already
    //     this.preAndPostConditionDescrSrc = null;
    //     this.electionDescSrc = null;
    //
    //     // get a list of result objects that fit for the specified checkerID
    //     // because we have no preAndPostConditions we only need ONE result
    //     this.results =
    //     CheckerFactoryFactory.getMatchingUnprocessedResult(checkerID,
    //     preAndPostConditionDescrSrc.getPreAndPostPropertiesDescriptionsCheckAndMargin().size());
    //
    //     preAndPostConditionDescrSrc.referenceResult(this.results);
    //
    //     // if the user does not specify a concrete amount for concurrent
    //     // checkers, we just set it to the thread amount of this pc
    //     if (concurrentChecker <= 0) {
    //         this.concurrentChecker = Runtime.getRuntime().availableProcessors();
    //     } else {
    //         this.concurrentChecker = concurrentChecker;
    //     }
    //
    //     // start the factorycontroller
    //     new Thread(this, "FactoryController").start();
    //
    //     // if the user wishes for a timeout, we activate it here
    //     if (parmSrc.getParameter().getTimeout().isActive()) {
    //         notifier = new TimeOutNotifier(this,
    //         parmSrc.getParameter().getTimeout().getDuration());
    //     } else {
    //         notifier = null;
    //     }
    // }

    /**
     * The constructor.
     *
     * @param electionDescription
     *            the election description
     * @param parentProperties
     *            the parent properties
     * @param electionCheckParameter
     *            the election check parameter
     * @param checkerIdString
     *            the checker id string
     * @param concurrentCheckers
     *            the concurrent checkers
     */
    public FactoryController(final ElectionDescription electionDescription,
                             final List<ParentTreeItem> parentProperties,
                             final ElectionCheckParameter electionCheckParameter,
                             final String checkerIdString,
                             final int concurrentCheckers) {
        // add a shutdown hook so all the checker are stopped properly so they
        // do not clog the host pc
        Runtime.getRuntime().addShutdownHook(new FactoryEnder());
        this.elecDesc = electionDescription;
        this.parameter = electionCheckParameter;
        this.checkerID = checkerIdString;
        this.currentlyRunning =
                new ArrayList<CheckerFactory>(concurrentCheckers);

        // set the result objects for all the selected children
        for (Iterator<ParentTreeItem> parentIterator =
                parentProperties.iterator();
                parentIterator.hasNext();) {
            final ParentTreeItem parentTreeItem =
                    (ParentTreeItem) parentIterator.next();
            for (Iterator<ChildTreeItem> childIterator =
                    parentTreeItem.getSubItems().iterator();
                    childIterator.hasNext();) {
                final ChildTreeItem child = childIterator.next();
                if (child.isSelected()) {
                    final Result result =
                            CheckerFactoryFactory
                            .getMatchingResult(checkerIdString);
                    result.setProperty(
                            parentTreeItem.getPreAndPostProperties());
                    child.addResult(result);
                    treeItems.add(child);
                    results.add(result);
                }
            }
        }

        // if the user does not specify a concrete amount for concurrent
        // checkers, we just set it to the thread amount of this pc
        if (concurrentCheckers <= 0) {
            this.concurrentChecker = Runtime.getRuntime().availableProcessors();
        } else {
            this.concurrentChecker = concurrentCheckers;
        }
        // start the factory controller
        new Thread(this, "FactoryController").start();

        // if the user wishes for a timeout, we activate it here
        if (electionCheckParameter.getTimeout().isActive()) {
            notifier = new TimeOutNotifier(
                    this,
                    electionCheckParameter.getTimeout().getDuration()
                    );
        } else {
            notifier = null;
        }
    }

    // /**
    //  * Starts the factoryController, so it then starts the needed checker.
    //  */
    // @Override
    // public void run() {
    //     List<PropertyAndMarginBool> propertiesToCheckAndMargin =
    //         preAndPostConditionDescrSrc
    //             .getPreAndPostPropertiesDescriptionsCheckAndMargin();
    //
    //     outerLoop: for (int i = 0; i < propertiesToCheckAndMargin.size(); i++) {
    //         innerLoop: while (!stopped) {
    //             // if we can start more checkers (we have not used our
    //             // allowed pool completely), we can start a new one
    //             if (currentlyRunning.size() < concurrentChecker) {
    //                 CheckerFactory factory =
    //                     CheckerFactoryFactory.getCheckerFactory(checkerID,
    //                                                             this,
    //                                                             electionDescSrc,
    //                                                             propertiesToCheckAndMargin
    //                                                             .get(i).getDescription(),
    //                                                             parmSrc,
    //                                                             results.get(i),
    //                                                             propertiesToCheckAndMargin
    //                                                             .get(i).getMarginStatus());
    //
    //                 synchronized (this) {
    //                     currentlyRunning.add(factory);
    //                 }
    //
    //                 new Thread(factory, "CheckerFactory Property " + i).start();
    //
    //                 break innerLoop;
    //             } else {
    //                 // ELSE, we try to sleep a bit. It is important that we
    //                 // only sleep if no new checker
    //                 // was started, or else we have to wait when there
    //                 // is more allowed space in our thread pool
    //                 try {
    //                     Thread.sleep(POLLING_INTERVAL);
    //                 } catch (InterruptedException e) {
    //                     e.printStackTrace();
    //                 }
    //             }
    //         }
    //         if (stopped) {
    //             break outerLoop;
    //         }
    //     }
    //
    //     while (currentlyRunning.size() > 0) {
    //         try {
    //             Thread.sleep(POLLING_INTERVAL);
    //         } catch (InterruptedException e) {
    //             ErrorLogger.log("Was interrupted while waiting for the last processes to"
    //                             + " finish \n"
    //                             + "The waiting will still continue. To stop the factory"
    //                             + " properly, call \"stopChecking()\" !");
    //         }
    //     }
    //
    //     // wait for the last running threads to finish
    //     while (currentlyRunning.size() > 0) {
    //         try {
    //             Thread.sleep(POLLING_INTERVAL);
    //         } catch (InterruptedException e) {
    //             ErrorLogger.log("Was interrupted while waiting for the last processes to"
    //                             + "finish \n"
    //                             + "The waiting will still continue. To stop the factory"
    //                             + " properly, call \"stopChecking()\" !");
    //         }
    //     }
    //
    //     if (notifier != null) {
    //         // if the notifier thread is still active, we stop it.
    //         notifier.disable();
    //     }
    // }

    // Starts the factoryController, so it then starts the needed checker.
    // TODO: this is just a threadpool, fix that
    @Override
    public void run() {
        outerLoop: for (int i = 0; i < results.size(); i++) {
            innerLoop: while (!stopped) {
                // if we can start more checkers (we have not used our
                // allowed pool completely), we can start a new one
                if (currentlyRunning.size() < concurrentChecker) {
                    // CheckerFactory factory =
                    // CheckerFactoryFactory.getCheckerFactory(checkerID,
                    // this,
                    // electionDescSrc,
                    // propertiesToCheck.get(i).getDescription(), parameter,
                    // results.get(i),
                    // propertiesToCheckAndMargin.get(i).getMarginStatus());

                    final CheckerFactory factory =
                            CheckerFactoryFactory
                            .getCheckerFactory(checkerID, this, elecDesc,
                                               results.get(i), parameter);
                    synchronized (this) {
                        currentlyRunning.add(factory);
                    }
                    new Thread(factory, "CheckerFactory Property " + i).start();
                    break innerLoop;
                } else {
                    // ELSE, we try to sleep a bit. It is important that we
                    // only sleep if no new checker
                    // was started, or else we have to wait when there
                    // is more allowed space in our thread pool
                    try {
                        Thread.sleep(POLLING_INTERVAL);
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
                Thread.sleep(POLLING_INTERVAL);
            } catch (InterruptedException e) {
                ErrorLogger.log(ERROR_MSG);
            }
        }

        // wait for the last running threads to finish
        while (currentlyRunning.size() > 0) {
            try {
                Thread.sleep(POLLING_INTERVAL);
            } catch (InterruptedException e) {
                ErrorLogger.log(ERROR_MSG);
            }
        }

        if (notifier != null) {
            // if the notifier thread is still active, we stop it.
            notifier.disable();
        }
    }

    /**
     * Tells the controller to stop checking. It stops all currently running
     * Checkers and does not start new ones.
     *
     * @param timeOut
     *            if it is true, the checking was stopped because of a timeout;
     */
    public synchronized void stopChecking(final boolean timeOut) {
        if (!stopped) {
            this.stopped = true;
            // send a signal to all currently running Checkers so they will stop
            for (Iterator<CheckerFactory> iterator =
                    currentlyRunning.iterator();
                    iterator.hasNext();) {
                final CheckerFactory toStop = iterator.next();
                toStop.stopChecking();
            }
            // set all not finished results to finished, to indicate that they
            // are
            // ready to be presented
            for (Iterator<Result> iterator = results.iterator();
                    iterator.hasNext();) {
                final Result result = (Result) iterator.next();
                if (!result.isFinished()) {
                    result.setTimeoutFlag();
                }
            }
        }
    }

    /**
     * Notifies the factory that one of the started checker factories finished,
     * so a new one could be started.
     *
     * @param finishedFactory
     *            the factory that just finished, so it can be removed from the
     *            list of ones to be notified when the checking is stopped
     *            forcefully
     */
    public synchronized void notifyThatFinished(final CheckerFactory finishedFactory) {
        if (currentlyRunning.size() == 0) {
            ErrorLogger.log("A checker finished when no checker was active.");
        } else {
            synchronized (this) {
                currentlyRunning.remove(finishedFactory);
            }
        }
    }

    // /**
    // *
    // * @return a NEW list with all the results objects. This list is used
    // * nowhere in the property checker, so you can remove parts out of it
    // * as you want.
    // **/
    // public List<ResultInterface> getResults() {
    //     if (results == null) {
    //         ErrorLogger.log("Result objects could not be created.");
    //         return null;
    //     } else {
    //         List<ResultInterface> toReturn = new ArrayList<ResultInterface>();
    //         for (Iterator<Result> iterator = results.iterator(); iterator.hasNext();) {
    //             Result result = (Result) iterator.next();
    //             toReturn.add(result);
    //         }
    //         return toReturn;
    //     }
    // }

    // public List<UnprocessedCBMCResult> getUnprocessedResults() {
    //     if (results == null) {
    //         ErrorLogger.log("Result objects could not be created.");
    //         return null;
    //     } else {
    //         List<UnprocessedCBMCResult> toReturn =
    //             new ArrayList<UnprocessedCBMCResult>();
    //
    //         for (Iterator<Result> iterator = results.iterator(); iterator.hasNext();) {
    //             UnprocessedCBMCResult result = (UnprocessedCBMCResult) iterator.next();
    //             toReturn.add(result);
    //         }
    //         return toReturn;
    //     }
    // }

    /**
     * Gets the results.
     *
     * @return the results
     */
    public List<Result> getResults() {
        return results;
    }

    /**
     * Gets the properties to check.
     *
     * @return the properties to check
     */
    public List<ChildTreeItem> getPropertiesToCheck() {
        return propertiesToCheck;
    }

    /**
     * Sets the properties to check.
     *
     * @param propsToCheck
     *            the new properties to check
     */
    public void setPropertiesToCheck(final List<ChildTreeItem> propsToCheck) {
        this.propertiesToCheck = propsToCheck;
    }

    /**
     * This Class is there for the shutDownHook. It is used, so if the program
     * has a chance of cleaning up, it still has a chance of messaging all
     * checkers to stop running.
     *
     * @author Lukas Stapelbroek
     *
     */
    public class FactoryEnder extends Thread {

        /**
         * Run.
         */
        @Override
        public void run() {
            thisObject.stopChecking(false);
        }
    }
}
