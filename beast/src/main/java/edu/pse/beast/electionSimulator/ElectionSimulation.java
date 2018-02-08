package edu.pse.beast.electionSimulator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTextField;

import edu.pse.beast.datatypes.electiondescription.ElectionDescriptionChangeListener;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.electionSimulator.Model.ElectionSimulationModel;
import edu.pse.beast.electionSimulator.Model.RowOfValues;
import edu.pse.beast.electionSimulator.View.ElectionSimulationWindow;
import edu.pse.beast.electionSimulator.programAccess.CompilerAndExecutioner;
import edu.pse.beast.highlevel.BEASTCommunicator;
import edu.pse.beast.highlevel.PSECentralObjectProvider;
import edu.pse.beast.propertychecker.UnprocessedCBMCResult;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.toolbox.FileLoader;
import edu.pse.beast.toolbox.FileSaver;
import edu.pse.beast.toolbox.SuperFolderFinder;
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.OutputType;

public class ElectionSimulation implements Runnable, ActionListener, ComponentListener, AdjustmentListener,
		MouseWheelListener, ElectionDescriptionChangeListener {

	private final String pathToTempFolder = "/core/c_tempfiles/";

	private static ElectionTypeContainer container;

	private PSECentralObjectProvider centralObjectProvider;

	private boolean react = false;

	private boolean running = false;

	private boolean isOpen = false;

	private static boolean initiated = false;

	private StringLoaderInterface sli;

	private static Modes currentMode = Modes.searchMinDiffAndShowCBMC;

	private final long SLEEPINTERVAL = 1000; // sleep interval in milliseconds

	public enum Modes {
		compileAndRun, compileAndRunCBMC, searchMinDiffAndShowCandCBMC, searchMinDiffAndShowCBMC;
	}

	private static ElectionSimulationWindow view;
	private static ElectionSimulationModel model;

	public ElectionSimulation() {
		this(new StringLoaderInterface("en"));
	}

	public ElectionSimulation(StringLoaderInterface sli) {
		this.sli = sli;
	}

	public void init(PSECentralObjectProvider centralObjectProvider) {
		this.centralObjectProvider = centralObjectProvider;
		this.container = centralObjectProvider.getElectionDescriptionSource().getElectionDescription()
				.getContainer();
		this.model = new ElectionSimulationModel(container);
		this.view = new ElectionSimulationWindow(sli, container, this, model);

		centralObjectProvider.getCElectionEditor().addListener(this);

		this.start();
		react = true;

		initiated = true;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent event) {
		if (react) {
			int amount = event.getWheelRotation();
			if (amount > 0) {
				model.setVerticalOffset(Math.min(model.getVerticalOffset() + amount * 5,
						(model.getAmountVoters() - 1) * model.getElementHeight() * 2));
				view.update();
			} else {
				model.setVerticalOffset(Math.max(model.getVerticalOffset() + amount * 5, 0));
				view.update();
			}
		}
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		if (react) {
			if (e.getSource() == view.horizontalScroll) {
				model.setHorizontalOffset(view.horizontalScroll.getValue());
			}

			if (e.getSource() == view.verticalScroll) {
				model.setVerticalOffset(view.verticalScroll.getValue());
			}
			view.update();
		}
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		if (react) {
			model.setFieldsPerWidth((int) ((view.getWidth() - 2 * (model.getBorderMarginSmall()))
					/ (1.25 * model.getWidthMultiplier())));
			model.setFieldsPerHeight((int) ((view.getHeight() - 2 * (model.getBorderMarginSmall()))
					/ (1.25 * model.getHeightMultiplier())));

			updateRows();
			view.update();
		}
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (react) { // if the main plane should react to inputs, these
						// conditions get checked
			if (e.getSource() == view.addCandidate) {
				model.setAmountCandidates(model.getAmountCandidates() + 1);
				for (Iterator<RowOfValues> iterator = model.getRows().iterator(); iterator.hasNext();) {
					RowOfValues row = (RowOfValues) iterator.next();
					row.addColumn();
					row.repaint();
				}
				model.getCandidates().add(new JTextField("C" + model.getAmountCandidates()));
			} else if (e.getSource() == view.removeCandidate) {
				if (model.getAmountCandidates() > 1) {
					model.setAmountCandidates(model.getAmountCandidates() - 1);
					for (Iterator<RowOfValues> iterator = model.getRows().iterator(); iterator.hasNext();) {
						RowOfValues row = (RowOfValues) iterator.next();
						row.removeColumn();
					}
					view.remove(model.getCandidates().get(model.getCandidates().size() - 1));
					model.getCandidates().remove(model.getCandidates().size() - 1);
					model.setHorizontalOffset(Math.min(model.getHorizontalOffset(),
							(model.getAmountCandidates() - 1) * model.getElementWidth() * 2));
				}
			} else if (e.getSource() == view.addVoter) {
				model.setAmountVoters(model.getAmountVoters() + 1);
				model.getRows().add(new RowOfValues(model, container, model.getAmountCandidates(),
						model.getElementWidth(), model.getElementHeight(), model.getWidthMultiplier()));
				for (Iterator<RowOfValues> iterator = model.getRows().iterator(); iterator.hasNext();) {
					RowOfValues row = (RowOfValues) iterator.next();
					row.repaint();
				}
				model.getVoters().add(new JTextField("V" + model.getAmountVoters()));

			} else if (e.getSource() == view.removeVoter) {
				if (model.getAmountVoters() > 1) {
					model.setAmountVoters(model.getAmountVoters() - 1);
					view.remove(model.getRows().get(model.getRows().size() - 1));
					model.getRows().remove(model.getRows().size() - 1);
					view.remove(model.getVoters().get(model.getVoters().size() - 1));
					model.getVoters().remove(model.getVoters().size() - 1);
					model.setVerticalOffset(Math.min(model.getVerticalOffset(),
							(model.getAmountVoters() - 1) * model.getElementHeight() * 2));
				}
			}
			view.update();
		}
	}

	public synchronized void updateRows() {
		if (react) {
			for (Iterator<RowOfValues> iterator = model.getRows().iterator(); iterator.hasNext();) {
				RowOfValues row = (RowOfValues) iterator.next();
				row.update();
			}
		}
	}

	@Override
	public void inputChanged(InputType input) {
		electionTypeChanged();
	}

	@Override
	public void outputChanged(OutputType output) {
		electionTypeChanged();
	}

	/**
	 * gets called when one of the changed listener gets called here, we update the
	 * models which are used
	 */
	private void electionTypeChanged() {
		this.container = centralObjectProvider.getElectionDescriptionSource().getElectionDescription()
				.getContainer();
		model.changeContainer(container);
	}

	/**
	 * Displays the view.
	 */
	public void start() {
		java.awt.EventQueue.invokeLater(this);
	}

	// TODO
	/**
	 * resets all fields and returns the view back to its original state
	 */
	public void reset() {
		// TODO Auto-generated method stub

	}

	public void save() {
		// TODO Auto-generated method stub

	}

	public void saveAs() {
		// TODO Auto-generated method stub

	}

	public void loadData() {
		// TODO Auto-generated method stub

	}

	public void undo() {
		// TODO Auto-generated method stub

	}

	public void redo() {
		// TODO Auto-generated method stub

	}

	public void startStop() {
		if (running) {
			running = false;
			centralObjectProvider.getResultCheckerCommunicator().abortChecking();
			centralObjectProvider.getCheckStatusDisplay().displayText("", false, "");
			BEASTCommunicator.resumeReacting(centralObjectProvider);
			view.startStopButton.setText("start");
			react = true;

		} else {
			react = false;
			running = true;
			view.startStopButton.setText("stop");

			Thread marginComputer = new Thread(new Runnable() {

				public void run() {
					if (!BEASTCommunicator.checkForErrors(centralObjectProvider)) {

						// BEASTCommunicator.stopReacting(centralObjectProvider);
						//
						// centralObjectProvider.getCheckStatusDisplay().displayText("startingCheck",
						// true, "");
						//
						// int[][] votingData = new
						// int[model.getAmountVoters()][model.getAmountCandidates()];
						//
						// // read the data in a 2d array
						// for (int i = 0; i < model.getAmountVoters(); i++) {
						// for (int j = 0; j < model.getAmountCandidates(); j++) {
						// votingData[i][j] = model.getRows().get(i).getValues().get(j);
						// }
						// }

						// set the values for the vote
						// centralObjectProvider.getParameterEditor().setVoterAmount(model.getAmountVoters());
						// centralObjectProvider.getParameterEditor().setCandidateAmount(model.getAmountCandidates());
						// centralObjectProvider.getParameterEditor().setSeatAmount(model.getAmountCandidates());

						// List<String> codeLines = generateRunnableCode(votingData);

						List<Integer> winnerResults = CompilerAndExecutioner.compileAndRun(null);
						// here we now have the winner(s) of the computation

						// here we now make a binary search tree for the margin
						// value

						int left = 0;
						int right = 0; // how many votes we
										// have
						int margin = 0;

						UnprocessedCBMCResult finalMarginResult = null;

						while ((left < right) && running) {
							// calculate the margin to check
							margin = (int) (left + Math.floor((float) (right - left) / 2));

							// generate the code for the margin
							List<String> boundedCheckingCodeLines = generateMarginComputationCode(null, margin,
									winnerResults);

							// create a temporary file, in which the code
							// gets
							// saved
							String absolutePath = SuperFolderFinder.getSuperFolder() + pathToTempFolder;
							String pathToCBMCFile = absolutePath + FileLoader.getNewUniqueName(absolutePath);

							File cbmcFile = new File(pathToCBMCFile + ".c");
							FileSaver.writeStringLinesToFile(boundedCheckingCodeLines, cbmcFile);

							UnprocessedCBMCResult marginResult = centralObjectProvider.getResultCheckerCommunicator()
									.checkFile(cbmcFile, centralObjectProvider.getElectionDescriptionSource()
											.getElectionDescription(), centralObjectProvider.getParameterSrc());

							while (!marginResult.isFinished()) {
								try {
									Thread.sleep(SLEEPINTERVAL);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

							System.out.println("finished for margin " + margin + " result: "
									+ marginResult.checkAssertionSuccess());

							if (marginResult.isValid()) {
								if (marginResult.checkAssertionSuccess()) {
									left = margin + 1;
									margin = margin + 1;
								} else {
									right = margin;
									finalMarginResult = marginResult;
								}
							} else {
								// we were forcefully stoped TODO do something
								// to
								// tell the user
							}
						}

						System.out.println("final margin: " + margin);

						if (finalMarginResult == null) {
							System.err.println("error, no margin result");
						} else {
							if (container.getOutputType().isOutputOneCandidate()) {
								System.out.println("winner before: " + winnerResults.get(0));
							} else {

								String tmpAcc = "";

								for (Iterator<Integer> iterator = winnerResults.iterator(); iterator.hasNext();) {
									Integer integer = (Integer) iterator.next();
									tmpAcc = integer + ", " + tmpAcc;
								}

								System.out.println("winners before: " + tmpAcc);
							}

							System.out.println("result_array: ");

							String[][] new_votes_output = finalMarginResult.getNewVotesWrappedMulti().getArray();

							if (new_votes_output == null || new_votes_output[0] == null) {

								System.err.println("Achtung, du hast einen fehler gemacht");
							} else {

								for (int i = 0; i < new_votes_output.length; i++) {
									for (int j = 0; j < new_votes_output[0].length; j++) {
										System.out.print(new_votes_output[i][j] + " | ");
									}
									System.out.println();
									System.out.println("_________________");
								}

								if (container.getOutputType().isOutputOneCandidate()) {
									System.out.println("new winner: " + finalMarginResult.getNewResult()[0]);
								} else {

									String[] tmpArray = finalMarginResult.getNewResult();

									String tmpAcc = "";

									for (int i = 0; i < tmpArray.length; i++) {
										tmpAcc = tmpArray[i] + ", " + tmpAcc;
									}

									System.out.println("new winners: " + tmpAcc);
								}

							}
						}

						centralObjectProvider.getCheckStatusDisplay().displayText("", false, "");

					} else {
						centralObjectProvider.getCheckStatusDisplay().displayText("", false, "");
					}
					BEASTCommunicator.resumeReacting(centralObjectProvider);
					running = false;
					react = true;
					view.startStopButton.setText("start");
				}
			}, "Margin-Computation-Thread");
			marginComputer.start();

		}

	}

	// public void test() {
	// ElectionDescriptionSource electSrc =
	// centralObjectProvider.getElectionDescriptionSource();
	// PreAndPostConditionsDescriptionSource preAndPostSrc =
	// centralObjectProvider.getPreAndPostConditionsSource();
	// ParameterSource paramSrc = centralObjectProvider.getParameterSrc();
	// CheckStatusDisplay checkStatusDisplayer =
	// centralObjectProvider.getCheckStatusDisplay();
	//
	// if (!BEASTCommunicator.checkForErrors(centralObjectProvider)) {
	// // analysis gets started by
	// // CheckerCommunicator.checkPropertiesForDescription() getting
	// // called
	// checkStatusDisplayer.displayText("compiling", true, "");
	//
	// electionDescription.getCode();
	//
	// CompilerAndExecutioner.compileAndRun(centralObjectProvider,
	// valuesToPass);
	//
	// // Thread that checks for new presentable results every 50
	// // milliseconds
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
	// boolean[preAndPostSrc.getPreAndPostConditionsDescriptions().size()];
	//
	// int numberOfPresentedResults = 0;
	//
	// while (numberOfPresentedResults <
	// postAndPreSrc.getPreAndPostConditionsDescriptions().size()) {
	// elapsedTime = System.nanoTime() - startTime;
	// passedTimeSeconds = (double) elapsedTime / 1000000000.0;
	// timeString = createTimeString(passedTimeSeconds);
	//
	// checkStatusDisplayer.displayText("waitingForPropertyResult", true,
	// preAndPostSrc.getPreAndPostConditionsDescriptions().get(numberOfPresentedResults)
	// .getName() + "' (" + timeString + ")");
	//
	// try {
	// Thread.sleep(50);
	// } catch (InterruptedException ex) {
	// Logger.getLogger(BEASTCommunicator.class.getName()).log(Level.SEVERE,
	// null, ex);
	// }
	// for (int i = 0; i < resultPresented.length; i++) {
	// ResultInterface result = resultList.get(i);
	// if (result.readyToPresent() && !resultPresented[i]) {
	// ResultPresenter resultPresenter =
	// centralObjectProvider.getResultPresenter();
	// resultPresenter.presentResult(result, i);
	// resultPresented[i] = true;
	// numberOfPresentedResults++;
	// }
	// }
	// }
	// CentralObresumeReacting(centralObjectProvider);
	// checkStatusDisplayer.displayText("analysisEnded", false, " " +
	// timeString);
	// checkStatusDisplayer.signalThatAnalysisEnded();
	// }
	// });
	// waitForResultsThread.start();
	// }
	//
	// }

	// private List<String> generateRunnableCode(int[][] votingData) {
	// // if we vote for seats, we get the result as an array
	// boolean multiOut = outputContainer.getOutputID() ==
	// ElectionOutputTypeIds.CAND_PER_SEAT;
	//
	// // this list will hold all the code in for later
	// List<String> code = new ArrayList<String>();
	//
	// code.add("#include <stdio.h>");
	// code.add("#ifndef V\n #define V " + votingData.length + "\n #endif");
	// code.add("#ifndef C\n #define C " + votingData[0].length + "\n #endif");
	// code.add("#ifndef S\n #define S " + votingData[0].length + "\n #endif");
	//
	// // add the array "ORIG_RESULTS" to the code. It contains the voting data
	// code.addAll(getVotingResultCode(votingData));
	//
	// // add the code the user wrote
	// code.addAll(centralObjectProvider.getElectionDescriptionSource().getElectionDescription().getCode());
	//
	// code.add("int main() {");
	// switch (inputContainer.getInputID()) {
	// case SINGLE_CHOICE:
	// if (multiOut) {
	// code.add("int *winner = voting(ORIG_VOTES);"); // call the
	// // voting method
	// code.add("");
	// code.add("fprintf(stdout, \"winner: =\");");
	// code.add("for(int j = 0; j < C - 1; j++) {");
	// code.add("fprintf(stdout, \"%d,\", winner[j]);");
	// code.add("}");
	// code.add("fprintf(stdout, \"%d\", winner[C - 1]);"); // add the
	// // last
	// // line
	// // without
	// // a
	// // comma
	// } else {
	// code.add("int winner = voting(ORIG_VOTES);"); // we just have a
	// // single int as
	// // the winner
	// code.add("fprintf(stdout, \"winner: = %d\", winner);");
	// }
	// break;
	// case APPROVAL:
	// if (multiOut) {
	// code.add("int *winner = voting(ORIG_VOTES);"); // call the
	// // voting method
	// code.add("");
	// code.add("fprintf(stdout, \"winner: =\");");
	// code.add("for(int j = 0; j < C - 1; j++) {");
	// code.add("fprintf(stdout, \"%d,\", winner[j]);");
	// code.add("}");
	// code.add("fprintf(stdout, \"%d\", winner[C - 1]);"); // add the
	// // last
	// // line
	// // without
	// // a
	// // comma
	// } else {
	// code.add("int winner = voting(ORIG_VOTES);"); // we just have a
	// // single int as
	// // the winner
	// code.add("fprintf(stdout, \"winner: = %d\", winner);");
	// }
	// break;
	// case WEIGHTED_APPROVAL:
	// if (multiOut) {
	// code.add("int *winner = voting(ORIG_VOTES);"); // call the
	// // voting method
	// code.add("");
	// code.add("fprintf(stdout, \"winner: =\");");
	// code.add("for(int j = 0; j < C - 1; j++) {");
	// code.add("fprintf(stdout, \"%d,\", winner[j]);");
	// code.add("}");
	// code.add("fprintf(stdout, \"%d\", winner[C - 1]);"); // add the
	// // last
	// // line
	// // without
	// // a
	// // comma
	// } else {
	// code.add("int winner = voting(ORIG_VOTES);"); // we just have a
	// // single int as
	// // the winner
	// code.add("fprintf(stdout, \"winner: = %d\", winner);");
	// }
	// break;
	// case PREFERENCE:
	// if (multiOut) {
	// code.add("int *winner = voting(ORIG_VOTES);"); // call the
	// // voting method
	// code.add("");
	// code.add("fprintf(stdout, \"winner: =\");");
	// code.add("for(int j = 0; j < C - 1; j++) {");
	// code.add("fprintf(stdout, \"%d,\", winner[j]);");
	// code.add("}");
	// code.add("fprintf(stdout, \"%d\", winner[C - 1]);"); // add the
	// // last
	// // line
	// // without
	// // a
	// // comma
	// } else {
	// code.add("int winner = voting(ORIG_VOTES);"); // we just have a
	// // single int as
	// // the winner
	// code.add("fprintf(stdout, \"winner: = %d\", winner);");
	// }
	// break;
	//
	// default:
	// ErrorForUserDisplayer.displayError(
	// "the current input type was not found. Please extend the methode
	// \"generateRunnableCode\" in the class ElectionSimulation ");
	// break;
	// }
	//
	// code.add("}");
	//
	// return code;
	// }

	private List<String> generateMarginComputationCode(int[][] votingData, int margin, List<Integer> origResult) {
		// if we vote for seats, we get the result as an array
		
		boolean multiOut = !container.getOutputType().isOutputOneCandidate();		
		
		// this list will hold all the code in for later
		List<String> code = new ArrayList<String>();

		code = container.getInputType().addCheckerSpecificHeaders(code);

		// define some pre processor values
		code.add("#ifndef V\n #define V " + votingData.length + "\n #endif");
		code.add("#ifndef C\n #define C " + votingData[0].length + "\n #endif");
		code.add("#ifndef S\n #define S " + votingData[0].length + "\n #endif");
		
		// we add the margin which will get computed by the model checker
		
		code.add("#ifndef MARGIN\n #define MARGIN " + margin + "\n #endif");
		
		// we also add the original result, which is calculated by compiling the
		// program and running it

		if (multiOut) {
			code.addAll(getLastResultCode(origResult));
		} else {
			code.addAll(getLastResultCode(origResult.get(0)));
		}

		// add the array "ORIG_RESULTS" to the code. It contains the voting data
		code.addAll(getVotingResultCode(votingData));

		// add the code the user wrote (e.g the election function)
		code.addAll(centralObjectProvider.getElectionDescriptionSource().getElectionDescription().getCode());

		// add the verify methode:
		// taken and adjusted from the paper:
		// https://formal.iti.kit.edu/~beckert/pub/evoteid2016.pdf
		
		code.add("void verify() {");
		code.add("int total_diff = 0;");

		code = container.getInputType().addVerifyMethod(code, multiOut);
		

		// not used lines ( I think) //TODO if they really aren't needed, delete
		// them
		// code.add("new_votes1[i] = ORIG_VOTES[i] + diff[i];");
		// code.add("if (0 < diff[i]) pos_diff += diff[i];");
		// code.add("total_diff += diff[i];");
		// code.add("}");
		// code.add("__CPROVER_assume (pos_diff ≤ MARGIN);");
		// code.add("__CPROVER_assume (total_diff == 0);");
		// code.add("int *result = voting(new_votes1);");
		// code.add("assert (equals(result, ORIG_RESULT));");
		// code.add("}");

		// add the main methode
		code.add("int main() {");
		code.add("\tverify();");
		code.add("}");

		return code;
	}

	private List<String> getVotingResultCode(int[][] votingData) {

		// compile the input data as an integer array for c
		// and save the lines in this list
		
		boolean twoDim = container.getInputType().isTwoDim();

		List<String> dataAsArray = new ArrayList<String>();

		// first create the declaration of the array:
		String declaration = "";

		if (twoDim) {
			declaration = "int ORIG_VOTES[" + votingData.length + "][" + votingData[0].length + "] = {";
		} else {
			declaration = "int ORIG_VOTES[" + votingData.length + "] = {";
		}
		dataAsArray.add(declaration);

		if (twoDim) { // we have a two dimensional variable from the get go
			for (int i = 0; i < votingData.length; i++) {
				String tmp = "";
				for (int j = 0; j < votingData[i].length; j++) {
					if (j < (votingData[i].length - 1)) {
						tmp = tmp + votingData[i][j] + ",";
					} else {
						tmp = tmp + votingData[i][j];
					}
				}

				tmp = "{" + tmp + "}";
				if (i < votingData.length - 1) {
					dataAsArray.add(tmp + ",");
				} else {
					dataAsArray.add(tmp); // the last entry doesn't need a
											// trailing comma
				}
			}
		} else { // we have to map the two dimensional array to an one
					// dimensional one
			for (int i = 0; i < votingData.length; i++) {
				int tmp = 0; // saves what this voter voted for
				for (int j = 0; j < votingData[i].length; j++) {
					if (votingData[i][j] != 0) {
						tmp = j + 1; // because "0" is reserved for "hasn't
										// voted" we have to add an offset of
										// one
					}
				}
				if (i < votingData.length - 1) {
					dataAsArray.add(tmp + ",");
				} else {
					dataAsArray.add("" + tmp);
				}
			}
		}

		dataAsArray.add("};"); // close the array declaration

		return dataAsArray;
	}

	private List<String> getLastResultCode(List<Integer> origResult) {

		List<String> dataAsArray = new ArrayList<String>();

		// first create the declaration of the array:
		String declaration = "";

		declaration = "int ORIG_RESULT[" + origResult.size() + "] = {";

		dataAsArray.add(declaration);

		String tmp = ""; // saves the amount of votes this seat got
		for (int i = 0; i < origResult.size(); i++) {
			if (i < origResult.size() - 1) {
				tmp = tmp + origResult.get(i) + ",";
			} else {
				tmp = tmp + origResult.get(i);
			}
		}
		dataAsArray.add(tmp);

		dataAsArray.add("};");

		return dataAsArray;
	}

	private List<String> getLastResultCode(int origResult) {

		List<String> dataAsArray = new ArrayList<String>();

		// first create the declaration of the array:
		String declaration = "";

		declaration = "int ORIG_RESULT = " + origResult + ";";

		dataAsArray.add(declaration);

		return dataAsArray;
	}

	public void open() {
		if (!initiated) {
			init((PSECentralObjectProvider) BEASTCommunicator.getCentralObjectProvider());
		}

		isOpen = true;
		view.setVisible(true);
	}

	public void close() {
		isOpen = false;
		view.setVisible(false);
	}

	public boolean isOpen() {
		return isOpen;
	}

	// TODO schöner machen, vielleicht das enum mit der combobox verbinden, damit es
	// automatisch geht
	public void setMode(String selectedItem) {

		switch (selectedItem) {
		case "outcome (c)":
			currentMode = Modes.compileAndRun;
			break;

		case "outcome  (cbmc)":
			currentMode = Modes.compileAndRunCBMC;
			break;

		case "outcome (c) + margin(cbmc)":
			currentMode = Modes.searchMinDiffAndShowCandCBMC;
			break;

		case "outcome (cbmc) + margin(cbmc)":
			currentMode = Modes.searchMinDiffAndShowCBMC;
			break;
		default:
			break;
		}
	}

	public static String[][] getVotingData() {
		String[][] votingData = {{"0"}, {"0"}};;
		if (initiated) {
			votingData = new String[model.getAmountVoters()][model.getAmountCandidates()];

			// read the data in a 2d array
			for (int i = 0; i < model.getAmountVoters(); i++) {
				for (int j = 0; j < model.getAmountCandidates(); j++) {
					votingData[i][j] = model.getRows().get(i).getValues().get(j);
				}
			}
		}

		return votingData;
	}

	public static int getNumVoters() {
		if (!initiated) {
			return 1;
		} else {
			return model.getAmountVoters();
		}
	}

	public static int getNumCandidates() {
		if (!initiated) {
			return 1;
		} else {
			return model.getAmountCandidates();
		}

	}

	public static int getNumSeats() {
		if (!initiated) {
			return 1;
		} else {
			return model.getAmountSeats();
		}
	}

	public static Modes getMode() {
		return currentMode;
	}

	public static List<List<String>> getVotingDataListofList() {
		List<List<String>> toReturn = new ArrayList<List<String>>();

		String[][] data = getVotingData();

		for (int i = 0; i < data.length; i++) {
			List<String> tmp = new ArrayList<String>();
			for (int j = 0; j < data[0].length; j++) {
				tmp.add(data[i][j]);
			}
			toReturn.add(tmp);
		}

		return toReturn;
	}
}
