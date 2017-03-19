package edu.pse.beast.highlevel;

import java.text.DecimalFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The BEASTCommunicator coordinates all the other parts of BEAST and starts and
 * stops the test of an election for the desired properties.
 *
 * @author Jonas
 */
public class BEASTCommunicator implements CheckListener {

    private CentralObjectProvider centralObjectProvider;
    private List<ResultInterface> resultList;

    /**
     * Sets a new CentralObjectProvider which contains the references to the
     * other parts of BEAST.
     *
     * @param centralObjectProvider New CentralObjectProvider which is to be
     * set.
     */
    public void setCentralObjectProvider(CentralObjectProvider centralObjectProvider) {
        this.centralObjectProvider = centralObjectProvider;
    }

    @Override
    public void startCheck() {
        centralObjectProvider.getResultPresenter().resetResults();
        ElectionDescriptionSource electSrc = centralObjectProvider.getElectionDescriptionSource();
        PostAndPrePropertiesDescriptionSource postAndPreSrc = centralObjectProvider.getPostAndPrePropertiesSource();
        ParameterSource paramSrc = centralObjectProvider.getParameterSrc();
        CheckStatusDisplay checkStatusDisplayer = centralObjectProvider.getCheckStatusDisplay();

        // checks if there even are any properties selected for analysis in the PostAndPrePropertiesSource
        if (postAndPreSrc.getPostAndPrePropertiesDescriptions().isEmpty()) {
            checkStatusDisplayer.displayText("noProperty", false, "");
            return;
        }

        if (!checkForErrors(centralObjectProvider)) {
            // analysis gets started by CheckerCommunicator.checkPropertiesForDescription() getting called
            checkStatusDisplayer.displayText("startingCheck", true, "");
            resultList = centralObjectProvider.getResultCheckerCommunicator()
                    .checkPropertiesForDescription(electSrc, postAndPreSrc, paramSrc);

            // Thread that checks for new presentable results every 50 milliseconds
            Thread waitForResultsThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    // local variables for elapsed time displaying
                    String timeString = "";
                    long startTime = System.nanoTime();
                    long elapsedTime;
                    double passedTimeSeconds = 0;

                    boolean[] resultPresented = new boolean[postAndPreSrc.getPostAndPrePropertiesDescriptions().size()];

                    int numberOfPresentedResults = 0;

                    while (numberOfPresentedResults < postAndPreSrc.getPostAndPrePropertiesDescriptions().size()) {
                        elapsedTime = System.nanoTime() - startTime;
                        passedTimeSeconds = (double) elapsedTime / 1000000000.0;
                        timeString = createTimeString(passedTimeSeconds);

                        checkStatusDisplayer.displayText("waitingForPropertyResult", true,
                                postAndPreSrc.getPostAndPrePropertiesDescriptions().
                                        get(numberOfPresentedResults).getName() + "' (" + timeString + ")");

                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(BEASTCommunicator.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        for (int i = 0; i < resultPresented.length; i++) {
                            ResultInterface result = resultList.get(i);
                            if (result.readyToPresent() && !resultPresented[i]) {
                                ResultPresenter resultPresenter = centralObjectProvider.getResultPresenter();
                                resultPresenter.presentResult(result, i);
                                resultPresented[i] = true;
                                numberOfPresentedResults++;
                            }
                        }
                    }
                    resumeReacting(centralObjectProvider);
                    checkStatusDisplayer.displayText("analysisEnded", false,
                            " " + timeString);
                }
            });
            waitForResultsThread.start();
        }
    }

    @Override
    public void stopCheck() {
        boolean stoppedAtATimeItIsAllowed;
        stoppedAtATimeItIsAllowed = centralObjectProvider.getResultCheckerCommunicator().abortChecking();
        if (!stoppedAtATimeItIsAllowed) {
            CheckStatusDisplay checkStatusDisplayer = centralObjectProvider.getCheckStatusDisplay();
            checkStatusDisplayer.displayText("falseStop", false, "");
        }
    }

    /**
     * Checks for errors in the current UserInputs from ElectionDescriptionSource, ParameterSrc and
     * PostAndPrePropertiesSource.
     * @param pseCentralObjectProvider CentralObjectProvider instance
     * @return true if errors exist, false otherwise
     */
    private boolean checkForErrors(CentralObjectProvider pseCentralObjectProvider) {
        stopReacting(centralObjectProvider);
        CheckStatusDisplay checkStatusDisplayer = pseCentralObjectProvider.getCheckStatusDisplay();
        checkStatusDisplayer.displayText("searchingForErrors", true, "");
        if (!pseCentralObjectProvider.getElectionDescriptionSource().isCorrect()) {
            checkStatusDisplayer.displayText("electionDescriptionErrors", false, "");
            resumeReacting(centralObjectProvider);
            return true;
        } else if (!pseCentralObjectProvider.getPostAndPrePropertiesSource().isCorrect()) {
            checkStatusDisplayer.displayText("propertyErrors", false, "");
            resumeReacting(centralObjectProvider);
            return true;
        } else if (!pseCentralObjectProvider.getParameterSrc().isCorrect()) {
            checkStatusDisplayer.displayText("parameterErrors", false, "");
            resumeReacting(centralObjectProvider);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Makes the GUIs stop reacting.
     * @param pseCentralObjectProvider CentralObjectProvider instance
     */
    private void stopReacting(CentralObjectProvider pseCentralObjectProvider) {
        pseCentralObjectProvider.getElectionDescriptionSource().stopReacting();
        pseCentralObjectProvider.getPostAndPrePropertiesSource().stopReacting();
        pseCentralObjectProvider.getParameterSrc().stopReacting();
    }

    /**
     * Makes the GUIs resume reacting.
     * @param pseCentralObjectProvider CentralObjectProvider instance
     */
    private void resumeReacting(CentralObjectProvider pseCentralObjectProvider) {
        pseCentralObjectProvider.getElectionDescriptionSource().resumeReacting();
        pseCentralObjectProvider.getPostAndPrePropertiesSource().resumeReacting();
        pseCentralObjectProvider.getParameterSrc().resumeReacting();
    }

    /**
     * Creates a String that contains the given time in seconds in a readable format.
     * @param passedTimeSeconds the passed time in seconds as a double
     */
    private String createTimeString(double passedTimeSeconds) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String timeString = "";
        if (passedTimeLongerThanDay(passedTimeSeconds)) {
            timeString = createTimeStringLongerThanDay(passedTimeSeconds, decimalFormat);
        } else if (passedTimeLongerThanHour(passedTimeSeconds)) {
            timeString = createTimeStringLongerThanHour(passedTimeSeconds, decimalFormat);
        } else if (passedTimeLongerThanMinute(passedTimeSeconds)) {
            timeString = createTimeStringLongerThanMinute(passedTimeSeconds, decimalFormat);
        } else {
            String seconds = decimalFormat.format(passedTimeSeconds);
            timeString = seconds + "s";
        }
        return timeString;
    }

    private String createTimeStringLongerThanMinute(double passedTimeSeconds, DecimalFormat decimalFormat) {
        String timeString;
        int minutes = (int) passedTimeSeconds / 60;
        double minutesRemainder = passedTimeSeconds % 60;
        String seconds = decimalFormat.format(minutesRemainder);
        timeString = minutes + "min " + seconds + "s";
        return timeString;
    }

    private String createTimeStringLongerThanHour(double passedTimeSeconds, DecimalFormat decimalFormat) {
        String timeString;
        int hours = (int) passedTimeSeconds / 3600;
        double hoursRemainder = passedTimeSeconds % 3600;
        int minutes = (int) hoursRemainder / 60;
        double minutesRemainder = hoursRemainder % 60;
        String seconds = decimalFormat.format(minutesRemainder);
        timeString = hours + "h " + minutes + "m " + seconds + "s";
        return timeString;
    }

    private String createTimeStringLongerThanDay(double passedTimeSeconds, DecimalFormat decimalFormat) {
        String timeString;
        int days = (int) passedTimeSeconds / 86400;
        double daysRemainder = passedTimeSeconds % 86400;
        int hours = (int) daysRemainder / 3600;
        double hoursRemainder = daysRemainder % 3600;
        int minutes = (int) hoursRemainder / 60;
        double minutesRemainder = hoursRemainder % 60;
        String seconds = decimalFormat.format(minutesRemainder);
        timeString = days + "d " + hours + "h " + minutes + "m " + seconds + "s";
        return timeString;
    }

    private boolean passedTimeLongerThanDay(double passedTimeSeconds) {
        return passedTimeSeconds >= 86400;
    }

    private boolean passedTimeLongerThanHour(double passedTimeSeconds) {
        return passedTimeSeconds >= 3600;
    }

    private boolean passedTimeLongerThanMinute(double passedTimeSeconds) {
        return passedTimeSeconds >= 60;
    }
}
