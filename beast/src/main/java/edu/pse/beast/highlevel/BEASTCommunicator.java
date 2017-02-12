package edu.pse.beast.highlevel;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Iterator;
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
        electSrc.stopReacting();
        postAndPreSrc.stopReacting();
        paramSrc.stopReacting();
        checkStatusDisplayer.displayText("searchingForErrors", true, "");
        if (!electSrc.isCorrect()) {
            checkStatusDisplayer.displayText("electionDescriptionErrors", false, "");
            paramSrc.resumeReacting();
            postAndPreSrc.resumeReacting();
        } else if (!postAndPreSrc.isCorrect()) {
            checkStatusDisplayer.displayText("propertyErrors", false, "");
            electSrc.resumeReacting();
            paramSrc.resumeReacting();
            postAndPreSrc.resumeReacting();
        } else if (!paramSrc.isCorrect()) {
            checkStatusDisplayer.displayText("parameterErrors", false, "");
            electSrc.resumeReacting();
            paramSrc.resumeReacting();
            postAndPreSrc.resumeReacting();
        } else {
            resultList = centralObjectProvider.getResultCheckerCommunicator()
                    .checkPropertiesForDescription(electSrc, postAndPreSrc, paramSrc);

            checkStatusDisplayer.displayText("startingCheck", true, "");
            Thread waitForResultsThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String timeString = "";
                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    long startTime = System.nanoTime();
                    long elapsedTime;
                    double passedTimeSeconds = 0;
                    int numberOfPresentedResults = 0;
                    while (numberOfPresentedResults < postAndPreSrc.getPostAndPrePropertiesDescriptions().size()) {
                        elapsedTime = System.nanoTime() - startTime;
                        passedTimeSeconds = (double) elapsedTime / 1000000000.0;
                        if (passedTimeSeconds >= 86400) {
                            int days = (int) passedTimeSeconds/86400;
                            double daysRemainder = passedTimeSeconds % 86400;
                            int hours = (int) daysRemainder/3600;
                            double hoursRemainder = daysRemainder % 3600;
                            int minutes = (int) hoursRemainder/60;
                            double minutesRemainder = hoursRemainder % 60;
                            String seconds = decimalFormat.format(minutesRemainder);
                            timeString = days + "d " + hours + "h " + minutes + "m " + seconds +"s";
                        } else if (passedTimeSeconds >= 3600) {
                            int hours = (int) passedTimeSeconds/3600;
                            double hoursRemainder = passedTimeSeconds % 3600;
                            int minutes = (int) hoursRemainder/60;
                            double minutesRemainder = hoursRemainder % 60;
                            String seconds = decimalFormat.format(minutesRemainder);
                            timeString = hours + "h " + minutes + "m " + seconds +"s";
                        } else if (passedTimeSeconds >= 60) {
                            int minutes = (int) passedTimeSeconds/60;
                            double minutesRemainder = passedTimeSeconds % 60;
                            String seconds = decimalFormat.format(minutesRemainder);
                            timeString = minutes + "min " + seconds +"s";
                        } else {
                            String seconds = decimalFormat.format(passedTimeSeconds);
                            timeString = seconds + "s";
                        }
                        checkStatusDisplayer.displayText("waitingForPropertyResult", true,
                                postAndPreSrc.getPostAndPrePropertiesDescriptions().
                                        get(numberOfPresentedResults).getName() + "' (" + timeString + " passed)");
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(BEASTCommunicator.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if (resultList.size() > 0) {
                            for (Iterator<ResultInterface> iterator = resultList.iterator(); iterator.hasNext();) {
                                ResultInterface result = (ResultInterface) iterator.next();
                                if (result.readyToPresent()) {
                                    ResultPresenter resultPresenter = centralObjectProvider.getResultPresenter();
                                    resultPresenter.presentResult(result);
                                    iterator.remove();
                                    numberOfPresentedResults++;
                                }
                            }
                        }
                    }
                    electSrc.resumeReacting();
                    postAndPreSrc.resumeReacting();
                    paramSrc.resumeReacting();
                    timeString = " " + timeString;
                    checkStatusDisplayer.displayText("analysisEnded", false, timeString);
                }
            });
            waitForResultsThread.start();

        }
    }

    @Override
    public void stopCheck() {
        centralObjectProvider.getResultCheckerCommunicator().abortChecking();
    }

}
