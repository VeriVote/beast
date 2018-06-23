package edu.pse.beast.highlevel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder.BooleanExpEditorGeneralErrorFinder;
import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.ErrorHandling.CVariableErrorFinder;
import edu.pse.beast.codearea.ErrorHandling.CodeError;
import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.highlevel.javafx.ChildTreeItem;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.highlevel.javafx.ParentTreeItem;
import edu.pse.beast.propertychecker.PropertyChecker;
import edu.pse.beast.propertychecker.Result;

/**
 * The BEASTCommunicator coordinates all the other parts of BEAST and starts and
 * stops the test of an election for the desired properties.
 *
 * @author Jonas
 */
public class BEASTCommunicator {


	
	
	
	
	
	static List<PropertyChecker> currentCheckers = new ArrayList<PropertyChecker>();
	// public static boolean startCheck() {
	// centralObjectProvider.getResultPresenter().resetResults();
	// ElectionDescriptionSource electSrc =
	// centralObjectProvider.getElectionDescriptionSource();
	// PreAndPostConditionsDescriptionSource preAndPostSrc =
	// centralObjectProvider.getPreAndPostConditionsSource();
	// ParameterSource paramSrc = centralObjectProvider.getParameterSrc();
	// CheckStatusDisplay checkStatusDisplayer =
	// centralObjectProvider.getCheckStatusDisplay();
	//
	// // checks if there even are any properties selected for analysis in the
	// PreAndPostConditionsSource
	// if (preAndPostSrc.getPreAndPostConditionsDescriptionsCheck().isEmpty() &&
	// preAndPostSrc.getPreAndPostConditionsDescriptionsMargin().isEmpty()) {
	// GUIController.setInfoText("no property selected (add string resouce loading
	// later");
	// checkStatusDisplayer.displayText("noProperty", false, "");
	// return false;
	// }
	//
	// if (!checkForErrors(centralObjectProvider)) {
	// // analysis gets started by
	// CheckerCommunicator.checkPropertiesForDescription() getting called
	// checkStatusDisplayer.displayText("startingCheck", true, "");
	// resultList = centralObjectProvider.getResultCheckerCommunicator()
	// .checkPropertiesForDescription(electSrc, preAndPostSrc, paramSrc);
	//
	// // Thread that checks for new presentable results every 50 milliseconds
	// Thread waitForResultsThread = new Thread(new Runnable() {
	// @Override
	// public void run() {
	// // local variables for elapsed time displaying
	// String timeString = "";
	// long startTime = System.nanoTime();
	// long elapsedTime;
	// double passedTimeSeconds = 0;
	//
	// boolean[] resultPresented = new
	// boolean[preAndPostSrc.getPreAndPostPropertiesDescriptionsCheckAndMargin().size()];
	//
	// int numberOfPresentedResults = 0;
	//
	// while (numberOfPresentedResults <
	// preAndPostSrc.getPreAndPostPropertiesDescriptionsCheckAndMargin().size()) {
	// elapsedTime = System.nanoTime() - startTime;
	// passedTimeSeconds = (double) elapsedTime / 1000000000.0;
	// timeString = createTimeString(passedTimeSeconds);
	//
	// checkStatusDisplayer.displayText("waitingForPropertyResult", true,
	// preAndPostSrc.getPreAndPostPropertiesDescriptionsCheckAndMargin().
	// get(numberOfPresentedResults).getDescription().getName() + "' (" + timeString
	// + ")");
	//
	// try {
	// Thread.sleep(50);
	// } catch (InterruptedException ex) {
	// Logger.getLogger(BEASTCommunicator.class.getName()).log(Level.SEVERE, null,
	// ex);
	// }
	// for (int i = 0; i < resultPresented.length; i++) {
	// ResultInterface result = resultList.get(i);
	// if (result.readyToPresent() && !resultPresented[i]) {
	// ResultPresenter resultPresenter = centralObjectProvider.getResultPresenter();
	// resultPresenter.presentResult(result, i);
	// resultPresented[i] = true;
	// numberOfPresentedResults++;
	// }
	// }
	// }
	// resumeReacting(centralObjectProvider);
	// checkStatusDisplayer.displayText("analysisEnded", false,
	// " " + timeString);
	// checkStatusDisplayer.signalThatAnalysisEnded();
	// }
	// });
	// waitForResultsThread.start();
	// return true;
	// } else {
	// return false;
	// }
	// }
	private static boolean stopped;

	public static synchronized boolean startCheckNEW() {

		stopped = false;
		
		ElectionDescription electionDesc = GUIController.getController().getElectionDescription();
		List<ParentTreeItem> properties = GUIController.getController().getProperties();
		ElectionCheckParameter parameter = GUIController.getController().getParameter();

		// checks if there even are any properties selected for analysis in the
		// PreAndPostConditionsSource
		
		boolean hasProperties = false;
		
		for (Iterator<ParentTreeItem> iterator = properties.iterator(); iterator.hasNext();) {
			ParentTreeItem item = (ParentTreeItem) iterator.next();
			hasProperties = hasProperties || item.isChildSelected();
		}
		
		if (!hasProperties) {
			GUIController.setInfoText("no property selected (add string resouce loading later");
			return false;
		}

		if (!checkForErrors(electionDesc, properties)) {
			
			GUIController.setInfoText("starting Check");
			
			
			//TODO load the propertychecker 
			PropertyChecker checker = new PropertyChecker("CBMC");
			
			currentCheckers.add(checker);
						
			// analysis gets started by CheckerCommunicator.checkPropertiesForDescription()
			List<Result> results = checker.checkPropertiesForDescription(electionDesc, properties, parameter);
			if (results != null) {

				// Thread that checks for new presentable results every n milliseconds
				Thread waitForResultsThread = new Thread(new Runnable() {
					
					DecimalFormat df = new DecimalFormat("#.0");
					
					@Override
					public void run() {
						// local variables for elapsed time displaying
						String timeString = "";
						long startTime = System.nanoTime();
						long elapsedTime;
						double passedTimeSeconds = 0;

						long frameTime = System.currentTimeMillis();

						boolean allDone = false;

						while (!allDone && !stopped) {
							elapsedTime = System.nanoTime() - startTime;
							passedTimeSeconds = (double) elapsedTime / 1000000000.0;
							timeString = createTimeString(passedTimeSeconds);

							//GUIController.setInfoText("elapsed time " + df.format(passedTimeSeconds));

							try {
								Thread.sleep(Math.max(0, 67 - (System.currentTimeMillis() - frameTime)));
							} catch (InterruptedException ex) {
								Logger.getLogger(BEASTCommunicator.class.getName()).log(Level.SEVERE, null, ex);
							}

							frameTime = System.currentTimeMillis();

							//check if all results are finished already
							allDone = true;
							
							for (Iterator<Result> iterator = results.iterator(); iterator.hasNext();) {
								Result result = (Result) iterator.next();
								allDone = allDone && result.isFinished();
							}
						}
						
						GUIController.setInfoText("analysis ended after " + timeString);
						GUIController.getController().checkFinished();
					}
				});
				waitForResultsThread.start();
				return true && !stopped;
			} else {
				return false;
			}
		}
		return false;
	}

	public synchronized static boolean stopCheck() {
		stopped = true;
		
		for (Iterator<PropertyChecker> iterator = currentCheckers.iterator(); iterator.hasNext();) {
			PropertyChecker checker = (PropertyChecker) iterator.next();
			checker.abortChecking();
			
			
			System.out.println("aborting");
		}
		return true;
	}

	/**
     * Checks for errors in the current UserInputs from ElectionDescriptionSource, ParameterSrc and
     * PreAndPostConditionsSource.
     * @param pseCentralObjectProvider CentralObjectProvider instance
     * @return true if errors exist, false otherwise
     */
    public static boolean checkForErrors(ElectionDescription description, List<ParentTreeItem> properties) {
		GUIController.setInfoText("searching for errors");
		GUIController.getController().getBooleanExpEditor().updatePropertyTextAreas(); //save the currently opened property

		List<CodeError> codeErrors = CVariableErrorFinder.findErrors(description.getCode());
		
		
		if (codeErrors.size() != 0) {
			GUIController.getController().getCodeArea().displayErrors(codeErrors);
			return true;
		} 
		
		boolean errorsFound = false;
		
		for (Iterator<ParentTreeItem> iterator = properties.iterator(); iterator.hasNext();) {
			ParentTreeItem parentTreeItem = (ParentTreeItem) iterator.next();
			if (parentTreeItem.isChildSelected()) {
				errorsFound = BooleanExpEditorGeneralErrorFinder.hasErrors(parentTreeItem) || errorsFound;
			}
		}
		
		return errorsFound;
    }

	/**
	 * Creates a String that contains the given time in seconds in a readable
	 * format.
	 * 
	 * @param passedTimeSeconds
	 *            the passed time in seconds as a double
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
}
