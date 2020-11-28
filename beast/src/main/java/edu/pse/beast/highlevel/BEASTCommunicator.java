package edu.pse.beast.highlevel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.pse.beast.booleanexpeditor.booleanexpcodearea.errorfinder.BooleanExpEditorGeneralErrorFinder;
import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.errorhandling.CVariableErrorFinder;
import edu.pse.beast.codearea.errorhandling.CodeError;
import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.highlevel.javafx.ParentTreeItem;
import edu.pse.beast.propertychecker.PropertyChecker;
import edu.pse.beast.propertychecker.Result;

/**
 * The BEASTCommunicator coordinates all the other parts of BEAST and starts and
 * stops the test of an election for the desired properties.
 *
 * @author Jonas Wohnig
 */
public final class BEASTCommunicator {
    /** The Constant BLANK. */
    private static final String BLANK = " ";
    /** The Constant DAYS_UNIT. */
    private static final String DAYS_UNIT = "d";
    /** The Constant HOURS_UNIT. */
    private static final String HOURS_UNIT = "h";
    /** The Constant MINUTES_UNIT. */
    private static final String MINUTES_UNIT = "m";
    /** The Constant SECONDS_UNIT. */
    private static final String SECONDS_UNIT = "s";

    /** The Constant SIXTY_SEVEN. */
    private static final int SIXTY_SEVEN = 67;

    /** The Constant SECONDS_IN_MINUTE. */
    private static final int SECONDS_IN_MINUTE = 60;

    /** The Constant SECONDS_IN_HOUR. */
    private static final int SECONDS_IN_HOUR = 3600;

    /** The Constant SECONDS_IN_DAY. */
    private static final int SECONDS_IN_DAY = 86400;

    /** The Constant NANO_TO_SECONDS. */
    private static final double NANO_TO_SECONDS = 1000000000.0;

    /** The current checkers. */
    private static List<PropertyChecker> currentCheckers =
            new ArrayList<PropertyChecker>();

    // public static boolean startCheck() {
    //     centralObjectProvider.getResultPresenter().resetResults();
    //     ElectionDescriptionSource electSrc =
    //         centralObjectProvider.getElectionDescriptionSource();
    //     PreAndPostConditionsDescriptionSource preAndPostSrc =
    //         centralObjectProvider.getPreAndPostConditionsSource();
    //     ParameterSource paramSrc = centralObjectProvider.getParameterSrc();
    //     CheckStatusDisplay checkStatusDisplayer =
    //         centralObjectProvider.getCheckStatusDisplay();
    //
    //     // checks if there even are any properties selected for analysis in the
    //     // PreAndPostConditionsSource
    //     if (preAndPostSrc.getPreAndPostConditionsDescriptionsCheck().isEmpty()
    //           && preAndPostSrc.getPreAndPostConditionsDescriptionsMargin().isEmpty()) {
    //         GUIController.setInfoText("no property selected (add string resource"
    //             + " loading later).");
    //         checkStatusDisplayer.displayText("noProperty", false, "");
    //         return false;
    //     }
    //
    //     if (!checkForErrors(centralObjectProvider)) {
    //         // analysis gets started by
    //         // CheckerCommunicator.checkPropertiesForDescription() getting called
    //         checkStatusDisplayer.displayText("startingCheck", true, "");
    //         resultList = centralObjectProvider.getResultCheckerCommunicator()
    //                         .checkPropertiesForDescription(electSrc, preAndPostSrc, paramSrc);
    //
    //         // Thread that checks for new presentable results every 50 milliseconds
    //         Thread waitForResultsThread = new Thread(new Runnable() {
    //             @Override
    //             public void run() {
    //                 // local variables for elapsed time displaying
    //                 String timeString = "";
    //                 long startTime = System.nanoTime();
    //                 long elapsedTime;
    //                 double passedTimeSeconds = 0;
    //
    //                 boolean[] resultPresented =
    //                     new boolean[
    //                         preAndPostSrc
    //                         .getPreAndPostPropertiesDescriptionsCheckAndMargin().size()];
    //
    //                 int numberOfPresentedResults = 0;
    //
    //                 while (numberOfPresentedResults <
    //                     preAndPostSrc
    //                         .getPreAndPostPropertiesDescriptionsCheckAndMargin().size()) {
    //                     elapsedTime = System.nanoTime() - startTime;
    //                     passedTimeSeconds = (double) elapsedTime / NANO_TO_SECONDS;
    //                     timeString = createTimeString(passedTimeSeconds);
    //
    //                     checkStatusDisplayer.displayText(
    //                         "waitingForPropertyResult", true,
    //                         preAndPostSrc
    //                             .getPreAndPostPropertiesDescriptionsCheckAndMargin()
    //                             .get(numberOfPresentedResults).getDescription()
    //                             .getName()
    //                         + "' (" + + timeString + ")");
    //
    //                     try {
    //                         Thread.sleep(50);
    //                     } catch (InterruptedException ex) {
    //                         Logger.getLogger(BEASTCommunicator.class.getName())
    //                             .log(Level.SEVERE, null, ex);
    //                     }
    //                     for (int i = 0; i < resultPresented.length; i++) {
    //                         ResultInterface result = resultList.get(i);
    //                         if (result.readyToPresent() && !resultPresented[i]) {
    //                             ResultPresenter resultPresenter =
    //                                 centralObjectProvider.getResultPresenter();
    //                             resultPresenter.presentResult(result, i);
    //                             resultPresented[i] = true;
    //                             numberOfPresentedResults++;
    //                         }
    //                     }
    //                 }
    //                 resumeReacting(centralObjectProvider);
    //                 checkStatusDisplayer.displayText("analysisEnded", false,
    //                                                  " " + timeString);
    //                 checkStatusDisplayer.signalThatAnalysisEnded();
    //             }
    //         });
    //         waitForResultsThread.start();
    //         return true;
    //     } else {
    //         return false;
    //     }
    // }

    /** The stopped. */
    private static boolean stopped;

    /**
     * Instantiates a new BEAST communicator.
     */
    private BEASTCommunicator() {
    }

    /**
     * Start check NEW.
     *
     * @return true, if successful
     */
    public static synchronized boolean startCheckNEW() {
        stopped = false;
        final GUIController guiController = GUIController.getController();
        final ElectionDescription electionDesc = guiController.getElectionDescription();
        final List<ParentTreeItem> properties = guiController.getProperties();
        final ElectionCheckParameter parameter = guiController.getParameter();
        final boolean result;
        if (!hasProperties(properties)) {
            // Checks if there even are any properties selected for analysis in
            // the PreAndPostConditionsSource
            GUIController.setConsoleText("No property selected!");
            // TODO: (add string resouce loading later)");
            return false;
        } else if (!checkForErrors(electionDesc, properties)) {
            GUIController.setConsoleText("Starting verification ..");
            // TODO load the property checker
            final PropertyChecker checker = new PropertyChecker("CBMC");
            currentCheckers.add(checker);
            // Analysis gets started by
            // CheckerCommunicator.checkPropertiesForDescription()
            final List<Result> results =
                    checker.checkPropertiesForDescription(electionDesc,
                                                          properties,
                                                          parameter);
            if (results != null) {
                // Thread that checks for new presentable results every n
                // milliseconds
                final Thread waitForResultsThread = new Thread(new Runnable() {
                    // DecimalFormat df = new DecimalFormat("#.0");
                    @Override
                    public void run() {
                        // Local variables for elapsed time displaying
                        String timeString = "";
                        final long startTime = System.nanoTime();
                        long elapsedTime;
                        double passedTimeSeconds = 0;
                        long frameTime = System.currentTimeMillis();
                        boolean allDone = false;
                        while (!allDone && !stopped) {
                            elapsedTime = System.nanoTime() - startTime;
                            passedTimeSeconds = elapsedTime / NANO_TO_SECONDS;
                            timeString = createTimeString(passedTimeSeconds);
                            // GUIController.setInfoText("Elapsed time "
                            // + df.format(passedTimeSeconds));
                            try {
                                Thread.sleep(Math.max(0,
                                        SIXTY_SEVEN - (System.currentTimeMillis()
                                                - frameTime)));
                            } catch (InterruptedException ex) {
                                Logger.getLogger(
                                        BEASTCommunicator.class.getName())
                                        .log(Level.SEVERE, null, ex);
                            }
                            frameTime = System.currentTimeMillis();
                            // Check if all results are finished already
                            allDone = true;
                            for (Iterator<Result> iterator = results.iterator();
                                    iterator.hasNext();) {
                                final Result result = iterator.next();
                                allDone = allDone && result.isFinished();
                            }
                        }
                        GUIController.setConsoleText(
                                "Verification ended after " + timeString);
                        GUIController.getController().checkFinished();
                    }
                });
                waitForResultsThread.start();
                result = !stopped;
            } else {
                result = false;
            }
        } else {
            result = false;
        }
        return result;
    }

    /**
     * Stop check.
     *
     * @return true, if successful
     */
    public static synchronized boolean stopCheck() {
        stopped = true;
        for (Iterator<PropertyChecker> iterator =
                currentCheckers.iterator();
                iterator.hasNext();) {
            final PropertyChecker checker = iterator.next();
            checker.abortChecking();
            System.out.println("Aborting.");
        }
        return true;
    }

    /**
     * Checks for errors in the current UserInputs from
     * ElectionDescriptionSource, ParameterSrc and PreAndPostConditionsSource.
     *
     * @param description
     *            the election description
     * @param properties
     *            the properties list
     * @return true if errors exist, false otherwise
     */
    public static boolean checkForErrors(final ElectionDescription description,
                                         final List<ParentTreeItem> properties) {
        GUIController.setConsoleText("Searching for errors.");
        // Save the currently opened property
        GUIController.getController().getBooleanExpEditor()
                .savePropertyTextAreasIntoDescription();
        final List<CodeError> codeErrors = CVariableErrorFinder
                .findErrors(description.getComplexCodeAndSetHeaderLoopBounds(), description);
        if (codeErrors.size() != 0) {
            GUIController.getController().getCodeArea()
                    .displayErrors(codeErrors);
            return true;
        }
        boolean errorsFound = false;
        for (Iterator<ParentTreeItem> iterator = properties.iterator();
                iterator.hasNext();) {
            final ParentTreeItem parentTreeItem = iterator.next();
            if (parentTreeItem.isChildSelected()) {
                errorsFound |= BooleanExpEditorGeneralErrorFinder
                        .hasErrors(parentTreeItem);
            }
        }
        return errorsFound;
    }

    /**
     * Checks for properties.
     *
     * @param properties
     *            the properties
     * @return true, if successful
     */
    private static boolean hasProperties(final List<ParentTreeItem> properties) {
        boolean hasProperties = false;
        for (Iterator<ParentTreeItem> iterator = properties.iterator();
                iterator.hasNext();) {
            final ParentTreeItem item = iterator.next();
            hasProperties = hasProperties || item.isChildSelected();
        }
        return hasProperties;
    }

    /**
     * Creates a String that contains the given time in seconds in a readable
     * format.
     *
     * @param passedTimeSeconds
     *            the passed time in seconds as a double
     * @return the string
     */
    private static String createTimeString(final double passedTimeSeconds) {
        final DecimalFormat decimalFormat = new DecimalFormat("#.##");
        final String timeString;
        if (passedTimeLongerThanDay(passedTimeSeconds)) {
            timeString = createTimeStringLongerThanDay(passedTimeSeconds,
                                                       decimalFormat);
        } else if (passedTimeLongerThanHour(passedTimeSeconds)) {
            timeString = createTimeStringLongerThanHour(passedTimeSeconds,
                                                        decimalFormat);
        } else if (passedTimeLongerThanMinute(passedTimeSeconds)) {
            timeString = createTimeStringLongerThanMinute(passedTimeSeconds,
                                                          decimalFormat);
        } else {
            final String seconds = decimalFormat.format(passedTimeSeconds);
            timeString = seconds + SECONDS_UNIT;
        }
        return timeString;
    }

    /**
     * Creates the time string longer than minute.
     *
     * @param passedTimeSeconds
     *            the passed time seconds
     * @param decimalFormat
     *            the decimal format
     * @return the string
     */
    private static String createTimeStringLongerThanMinute(final double passedTimeSeconds,
                                                           final DecimalFormat decimalFormat) {
        final int minutes = (int) passedTimeSeconds / SECONDS_IN_MINUTE;
        final double minutesRemainder = passedTimeSeconds % SECONDS_IN_MINUTE;
        final String seconds = decimalFormat.format(minutesRemainder);
        return minutes + MINUTES_UNIT + BLANK + seconds + SECONDS_UNIT;
    }

    /**
     * Creates the time string longer than hour.
     *
     * @param passedTimeSeconds
     *            the passed time seconds
     * @param decimalFormat
     *            the decimal format
     * @return the string
     */
    private static String createTimeStringLongerThanHour(final double passedTimeSeconds,
                                                         final DecimalFormat decimalFormat) {
        final int hours = (int) passedTimeSeconds / SECONDS_IN_HOUR;
        final double hoursRemainder = passedTimeSeconds % SECONDS_IN_HOUR;
        final int minutes = (int) hoursRemainder / SECONDS_IN_MINUTE;
        final double minutesRemainder = hoursRemainder % SECONDS_IN_MINUTE;
        final String seconds = decimalFormat.format(minutesRemainder);
        return hours + HOURS_UNIT + BLANK + minutes + MINUTES_UNIT
               + BLANK + seconds + SECONDS_UNIT;
    }

    /**
     * Creates the time string longer than day.
     *
     * @param passedTimeSeconds
     *            the passed time seconds
     * @param decimalFormat
     *            the decimal format
     * @return the string
     */
    private static String createTimeStringLongerThanDay(final double passedTimeSeconds,
                                                        final DecimalFormat decimalFormat) {
        final int days = (int) passedTimeSeconds / SECONDS_IN_DAY;
        final double daysRemainder = passedTimeSeconds % SECONDS_IN_DAY;
        final int hours = (int) daysRemainder / SECONDS_IN_HOUR;
        final double hoursRemainder = daysRemainder % SECONDS_IN_HOUR;
        final int minutes = (int) hoursRemainder / SECONDS_IN_MINUTE;
        final double minutesRemainder = hoursRemainder % SECONDS_IN_MINUTE;
        final String seconds = decimalFormat.format(minutesRemainder);
        return days + DAYS_UNIT + BLANK + hours + HOURS_UNIT + BLANK
               + minutes + MINUTES_UNIT + BLANK + seconds + SECONDS_UNIT;
    }

    /**
     * Passed time longer than day.
     *
     * @param passedTimeSeconds
     *            the passed time seconds
     * @return true, if successful
     */
    private static boolean passedTimeLongerThanDay(final double passedTimeSeconds) {
        return passedTimeSeconds >= SECONDS_IN_DAY;
    }

    /**
     * Passed time longer than hour.
     *
     * @param passedTimeSeconds
     *            the passed time seconds
     * @return true, if successful
     */
    private static boolean passedTimeLongerThanHour(final double passedTimeSeconds) {
        return passedTimeSeconds >= SECONDS_IN_HOUR;
    }

    /**
     * Passed time longer than minute.
     *
     * @param passedTimeSeconds
     *            the passed time seconds
     * @return true, if successful
     */
    private static boolean passedTimeLongerThanMinute(final double passedTimeSeconds) {
        return passedTimeSeconds >= SECONDS_IN_MINUTE;
    }
}
