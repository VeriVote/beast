package edu.pse.beast.propertychecker;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.ThreadedBufferedReader;

public abstract class Checker implements Runnable {
    private static final int EXIT_ON_SUCCESS = 0;
    private static final int EXIT_ON_INCORRECT_ENVIRONMENT = 10;

    private static final long POLLING_INTERVAL = 1000;

    /**
     * The process that this checker runs.
     */
    private Process process;

    private final int voters;
    private final int candidates;
    private final int seats;
    private final String advanced;
    private final File toCheck;
    private final CheckerFactory parent;

    private final List<String> output = new ArrayList<String>();
    private final List<String> errors = new ArrayList<String>();

    private final Result result;

    private boolean finished = false;
    private boolean success = false;
    private volatile boolean interrupted = false;

    /**
     * This class describes the highest abstract layer of a checker. The task of
     * a checker is to check a vote for the given properties and then return the
     * result.
     *
     * @param voterAmount
     *            the amount of voters to check with
     * @param candAmount
     *            the amount of candidates to check with
     * @param seatAmount
     *            the amount of seats to check with
     * @param advancedOptions
     *            all advanced options that might be applied
     * @param fileToCheck
     *            the path to the file to check
     * @param parentFactory
     *            the parent checkerfactory that has to be notified about
     *            finished checking
     * @param resultVal
     *            the result
     */
    public Checker(final int voterAmount,
                   final int candAmount,
                   final int seatAmount,
                   final String advancedOptions,
                   final File fileToCheck,
                   final CheckerFactory parentFactory,
                   final Result resultVal) {
        this.voters = voterAmount;
        this.candidates = candAmount;
        this.seats = seatAmount;
        this.advanced = advancedOptions;
        this.toCheck = fileToCheck;
        this.parent = parentFactory;
        this.result = resultVal;
        new Thread(this, "Checker").start();
    }

    Process getProcess() {
        return this.process;
    }

    @Override
    public void run() {
        process = createProcess(toCheck, voters, candidates, seats, advanced);
        if (process != null) {
            CountDownLatch latch = new CountDownLatch(2);
            ThreadedBufferedReader outReader =
                    new ThreadedBufferedReader(
                            new BufferedReader(
                                    new InputStreamReader(process.getInputStream())
                                    ),
                            output, latch, true);
            ThreadedBufferedReader errReader =
                    new ThreadedBufferedReader(
                            new BufferedReader(
                                    new InputStreamReader(process.getErrorStream())),
                            errors, latch, false);
            result.setLastTmpResult(output);
            result.setLastTmpError(errors);
            // result
            polling: while (!interrupted || !finished) {
                if (!process.isAlive() && !interrupted) {
                    result.setExitCode(process.exitValue());
                    if (process.exitValue() == EXIT_ON_SUCCESS) {
                        success = true;
                    } else {
                        if (process.exitValue() != EXIT_ON_INCORRECT_ENVIRONMENT) {
                            ErrorLogger.log("Process finished with exitcode: "
                                            + process.exitValue());
                        }
                    }
                    break polling;
                } else if (interrupted) {
                    stopProcess();
                    outReader.stopReading();
                    errReader.stopReading();
                    break polling;
                }
                try {
                    Thread.sleep(POLLING_INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finished = true;
        } else {
            ErrorLogger.log("Process could not be started");
        }
        parent.notifyThatFinished(output, errors);
    }

    /**
     *
     * @return the list that contains the output of the checker
     */
    public List<String> getResultList() {
        return output;
    }

    /**
     *
     * @return the list that contains the errors that were returned by the
     *         checker
     */
    public List<String> getErrorList() {
        return errors;
    }

    /**
     *
     * @return true, if the property is satisfied, false, if it is violated
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     *
     * @return true, when the checking has finished, false else
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Tells the Checker to stop checking and also kill the sub processes.
     */
    public void stopChecking() {
        interrupted = true;
    }

    /**
     * Used to filter out arguments that can be used on some plattforms, but
     * would crash the checker on others.
     *
     * @param toSanitize
     *            the string to be sanitized
     * @return that string, with all occurrences of all forbidden words removed
     */
    protected abstract String sanitizeArguments(String toSanitize);

    /**
     * Creates the process, which is run in a separate thread.
     *
     * @param voterAmount
     *            the amount of voters to check with
     * @param candAmount
     *            the amount of candidates to check with
     * @param seatAmount
     *            the amount of seats to check with
     * @param advancedOptions
     *            all advanced options that might be applied
     * @param fileToCheck
     *            the path to the file to check
     * @return a process that describes the system process that is currently
     *         checking the property
     */
    protected abstract Process createProcess(File fileToCheck, int voterAmount,
                                             int candAmount, int seatAmount,
                                             String advancedOptions);

    /**
     * System specific implementation to stop the process that got started.
     */
    protected abstract void stopProcess();
}
