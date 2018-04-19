package edu.pse.beast.highlevel;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.pse.beast.highlevel.javafx.ChildTreeItem;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.highlevel.javafx.ParentTreeItem;

/**
 * The BEASTCommunicator coordinates all the other parts of BEAST and starts and
 * stops the test of an election for the desired properties.
 *
 * @author Jonas
 */
public class BEASTCommunicator {

    private static CentralObjectProvider centralObjectProvider;
    private static List<ResultInterface> resultList;

    /**
     * Sets a new CentralObjectProvider which contains the references to the
     * other parts of BEAST.
     *
     * @param centralObjectProvider New CentralObjectProvider which is to be
     * set.
     */
    public void setCentralObjectProvider(CentralObjectProvider centralObjectProvider) {
        BEASTCommunicator.centralObjectProvider = centralObjectProvider;
    }

//    public static boolean startCheck() {
//        centralObjectProvider.getResultPresenter().resetResults();
//        ElectionDescriptionSource electSrc = centralObjectProvider.getElectionDescriptionSource();
//        PreAndPostConditionsDescriptionSource preAndPostSrc = centralObjectProvider.getPreAndPostConditionsSource();
//        ParameterSource paramSrc = centralObjectProvider.getParameterSrc();
//        CheckStatusDisplay checkStatusDisplayer = centralObjectProvider.getCheckStatusDisplay();
//
//        // checks if there even are any properties selected for analysis in the PreAndPostConditionsSource
//        if (preAndPostSrc.getPreAndPostConditionsDescriptionsCheck().isEmpty() && preAndPostSrc.getPreAndPostConditionsDescriptionsMargin().isEmpty()) {
//			GUIController.setInfoText("no property selected (add string resouce loading later");
//            checkStatusDisplayer.displayText("noProperty", false, "");
//            return false;
//        }
//
//        if (!checkForErrors(centralObjectProvider)) {
//            // analysis gets started by CheckerCommunicator.checkPropertiesForDescription() getting called
//            checkStatusDisplayer.displayText("startingCheck", true, "");
//            resultList = centralObjectProvider.getResultCheckerCommunicator()
//                    .checkPropertiesForDescription(electSrc, preAndPostSrc, paramSrc);
//            
//            // Thread that checks for new presentable results every 50 milliseconds
//            Thread waitForResultsThread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    // local variables for elapsed time displaying
//                    String timeString = "";
//                    long startTime = System.nanoTime();
//                    long elapsedTime;
//                    double passedTimeSeconds = 0;
//
//                    boolean[] resultPresented = new boolean[preAndPostSrc.getPreAndPostPropertiesDescriptionsCheckAndMargin().size()];
//
//                    int numberOfPresentedResults = 0;
//
//                    while (numberOfPresentedResults < preAndPostSrc.getPreAndPostPropertiesDescriptionsCheckAndMargin().size()) {
//                        elapsedTime = System.nanoTime() - startTime;
//                        passedTimeSeconds = (double) elapsedTime / 1000000000.0;
//                        timeString = createTimeString(passedTimeSeconds);
//
//                        checkStatusDisplayer.displayText("waitingForPropertyResult", true,
//                                preAndPostSrc.getPreAndPostPropertiesDescriptionsCheckAndMargin().
//                                        get(numberOfPresentedResults).getDescription().getName() + "' (" + timeString + ")");
//
//                        try {
//                            Thread.sleep(50);
//                        } catch (InterruptedException ex) {
//                            Logger.getLogger(BEASTCommunicator.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                        for (int i = 0; i < resultPresented.length; i++) {
//                            ResultInterface result = resultList.get(i);
//                            if (result.readyToPresent() && !resultPresented[i]) {
//                                ResultPresenter resultPresenter = centralObjectProvider.getResultPresenter();
//                                resultPresenter.presentResult(result, i);
//                                resultPresented[i] = true;
//                                numberOfPresentedResults++;
//                            }
//                        }
//                    }
//                    resumeReacting(centralObjectProvider);
//                    checkStatusDisplayer.displayText("analysisEnded", false,
//                            " " + timeString);
//                    checkStatusDisplayer.signalThatAnalysisEnded();
//                }
//            });
//            waitForResultsThread.start();
//            return true;
//        } else {
//        	return false;
//        }
//    }
    
    public static boolean startCheckNEW() {
    	
    	for (Iterator<ParentTreeItem> iterator = GUIController.getController().getProperties().iterator(); iterator.hasNext();) {
    		ParentTreeItem parentItem = (ParentTreeItem) iterator.next();
			for (Iterator<ChildTreeItem> childIterator = parentItem.getSubItems().iterator(); childIterator.hasNext();) {
				ChildTreeItem child = (ChildTreeItem) childIterator.next();
				child.resetResult();
			}
		}
    	
    	
        centralObjectProvider.getResultPresenter().resetResults();
        ElectionDescriptionSource electSrc = centralObjectProvider.getElectionDescriptionSource();
        PreAndPostConditionsDescriptionSource preAndPostSrc = centralObjectProvider.getPreAndPostConditionsSource();
        ParameterSource paramSrc = centralObjectProvider.getParameterSrc();
        CheckStatusDisplay checkStatusDisplayer = centralObjectProvider.getCheckStatusDisplay();

                
        // checks if there even are any properties selected for analysis in the PreAndPostConditionsSource
        if(GUIController.getController().getProperties().size() == 0)  {
        	GUIController.setInfoText("no property selected (add string resouce loading later");
            return false;
        }

        if (!checkForErrors(centralObjectProvider)) {
            // analysis gets started by CheckerCommunicator.checkPropertiesForDescription() getting called
            checkStatusDisplayer.displayText("startingCheck", true, "");
            resultList = centralObjectProvider.getResultCheckerCommunicator()
                    .checkPropertiesForDescription(electSrc, preAndPostSrc, parameter);
            
            
            resultList = centralObjectProvider.getResultCheckerCommunicator()
            		.checkPropertiesForDescription(GUIController.getController().getElectionDescription(), GUIController.getController().getProperties(), GUIController.getController().getParameter());
            
            
            // Thread that checks for new presentable results every 50 milliseconds
            Thread waitForResultsThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    // local variables for elapsed time displaying
                    String timeString = "";                   long startTime = System.nanoTime();
                    long elapsedTime;
                    double passedTimeSeconds = 0;

                    boolean[] resultPresented = new boolean[preAndPostSrc.getPreAndPostPropertiesDescriptionsCheckAndMargin().size()];

                    int numberOfPresentedResults = 0;

                    while (numberOfPresentedResults < preAndPostSrc.getPreAndPostPropertiesDescriptionsCheckAndMargin().size()) {
                        elapsedTime = System.nanoTime() - startTime;
                        passedTimeSeconds = (double) elapsedTime / 1000000000.0;
                        timeString = createTimeString(passedTimeSeconds);

                        checkStatusDisplayer.displayText("waitingForPropertyResult", true,
                                preAndPostSrc.getPreAndPostPropertiesDescriptionsCheckAndMargin().
                                        get(numberOfPresentedResults).getDescription().getName() + "' (" + timeString + ")");

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
                    checkStatusDisplayer.signalThatAnalysisEnded();
                }
            });
            waitForResultsThread.start();
            return true;
        } else {
        	return false;
        }
    }

    public static boolean stopCheck() {
        boolean stoppedAtATimeItIsAllowed = centralObjectProvider.getResultCheckerCommunicator().abortChecking();
        if (!stoppedAtATimeItIsAllowed) {
            CheckStatusDisplay checkStatusDisplayer = centralObjectProvider.getCheckStatusDisplay();
            checkStatusDisplayer.displayText("falseStop", false, "");
            return false;
        }
        return true;
    }

    /**
     * Checks for errors in the current UserInputs from ElectionDescriptionSource, ParameterSrc and
     * PreAndPostConditionsSource.
     * @param pseCentralObjectProvider CentralObjectProvider instance
     * @return true if errors exist, false otherwise
     */
    public static boolean checkForErrors(CentralObjectProvider pseCentralObjectProvider) {
        stopReacting(pseCentralObjectProvider);
        CheckStatusDisplay checkStatusDisplayer = pseCentralObjectProvider.getCheckStatusDisplay();
        checkStatusDisplayer.displayText("searchingForErrors", true, "");
        if (!pseCentralObjectProvider.getElectionDescriptionSource().isCorrect()) {
            checkStatusDisplayer.displayText("electionDescriptionErrors", false, "");
            resumeReacting(pseCentralObjectProvider);
            return true;
        } else if (!pseCentralObjectProvider.getPreAndPostConditionsSource().isCorrect()) {
            checkStatusDisplayer.displayText("propertyErrors", false, "");
            resumeReacting(pseCentralObjectProvider);
            return true;
        } else if (!pseCentralObjectProvider.getParameterSrc().isCorrect()) {
            checkStatusDisplayer.displayText("parameterErrors", false, "");
            resumeReacting(pseCentralObjectProvider);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Makes the GUIs stop reacting.
     * @param pseCentralObjectProvider CentralObjectProvider instance
     */
    public static void stopReacting(CentralObjectProvider pseCentralObjectProvider) {
        pseCentralObjectProvider.getElectionDescriptionSource().stopReacting();
        pseCentralObjectProvider.getPreAndPostConditionsSource().stopReacting();
        pseCentralObjectProvider.getParameterSrc().stopReacting();
    }

    /**
     * Makes the GUIs resume reacting.
     * @param pseCentralObjectProvider CentralObjectProvider instance
     */
    public static void resumeReacting(CentralObjectProvider pseCentralObjectProvider) {
        pseCentralObjectProvider.getElectionDescriptionSource().resumeReacting();
        pseCentralObjectProvider.getPreAndPostConditionsSource().resumeReacting();
        pseCentralObjectProvider.getParameterSrc().resumeReacting();
    }

    /**
     * Creates a String that contains the given time in seconds in a readable format.
     * @param passedTimeSeconds the passed time in seconds as a double
     */
    private static String createTimeString(double passedTimeSeconds) {
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

    private static String createTimeStringLongerThanMinute(double passedTimeSeconds, DecimalFormat decimalFormat) {
        String timeString;
        int minutes = (int) passedTimeSeconds / 60;
        double minutesRemainder = passedTimeSeconds % 60;
        String seconds = decimalFormat.format(minutesRemainder);
        timeString = minutes + "min " + seconds + "s";
        return timeString;
    }

    private static String createTimeStringLongerThanHour(double passedTimeSeconds, DecimalFormat decimalFormat) {
        String timeString;
        int hours = (int) passedTimeSeconds / 3600;
        double hoursRemainder = passedTimeSeconds % 3600;
        int minutes = (int) hoursRemainder / 60;
        double minutesRemainder = hoursRemainder % 60;
        String seconds = decimalFormat.format(minutesRemainder);
        timeString = hours + "h " + minutes + "m " + seconds + "s";
        return timeString;
    }

    private static String createTimeStringLongerThanDay(double passedTimeSeconds, DecimalFormat decimalFormat) {
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

    private static boolean passedTimeLongerThanDay(double passedTimeSeconds) {
        return passedTimeSeconds >= 86400;
    }

    private static boolean passedTimeLongerThanHour(double passedTimeSeconds) {
        return passedTimeSeconds >= 3600;
    }

    private static boolean passedTimeLongerThanMinute(double passedTimeSeconds) {
        return passedTimeSeconds >= 60;
    }

	public static CentralObjectProvider getCentralObjectProvider() {
		return centralObjectProvider;
	}
}
