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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTextField;

import com.google.common.cache.Weigher;

import edu.pse.beast.codearea.ErrorHandling.ErrorDisplayer;
import edu.pse.beast.codearea.InputToCode.JTextPaneToolbox;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer.ElectionOutputTypeIds;
import edu.pse.beast.electionSimulator.Model.ElectionSimulationModel;
import edu.pse.beast.electionSimulator.Model.RowOfValues;
import edu.pse.beast.electionSimulator.View.ElectionSimulationWindow;
import edu.pse.beast.electionSimulator.programAccess.CompilerAndExecutioner;
import edu.pse.beast.highlevel.BEASTCommunicator;
import edu.pse.beast.highlevel.CentralObjectProvider;
import edu.pse.beast.highlevel.CheckStatusDisplay;
import edu.pse.beast.highlevel.ElectionDescriptionSource;
import edu.pse.beast.highlevel.ParameterSource;
import edu.pse.beast.highlevel.PostAndPrePropertiesDescriptionSource;
import edu.pse.beast.highlevel.ResultInterface;
import edu.pse.beast.highlevel.ResultPresenter;
import edu.pse.beast.propertychecker.PropertyChecker;
import edu.pse.beast.propertychecker.UnprocessedCBMCResult;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.toolbox.ErrorForUserDisplayer;
import edu.pse.beast.toolbox.FileLoader;
import edu.pse.beast.toolbox.FileSaver;
import edu.pse.beast.toolbox.SuperFolderFinder;

public class ElectionSimulation
        implements Runnable, ActionListener, ComponentListener, AdjustmentListener, MouseWheelListener {

    private final String pathToTempFolder = "/core/c_tempfiles/";

    private ElectionTypeContainer inputContainer;

    private ElectionTypeContainer outputContainer;

    private CentralObjectProvider centralObjectProvider;

    private boolean react = false;

    private boolean running = false;
    
    private final long SLEEPINTERVAL = 1000; // sleep interval in milliseconds

    private enum Modes {
        compileAndRun, searchMinDiffAndShow;
    }

    private ElectionSimulationWindow view;
    private ElectionSimulationModel model;

    public ElectionSimulation(CentralObjectProvider centralObjectProvider) {
        this(centralObjectProvider, new StringLoaderInterface("de"));
    }

    public ElectionSimulation(CentralObjectProvider centralObjectProvider, StringLoaderInterface sli) {
        this.inputContainer = centralObjectProvider.getElectionDescriptionSource().getElectionDescription()
                .getInputType();
        this.outputContainer = centralObjectProvider.getElectionDescriptionSource().getElectionDescription()
                .getOutputType();
        this.model = new ElectionSimulationModel(inputContainer);
        this.view = new ElectionSimulationWindow(sli, inputContainer, this, model);
        this.centralObjectProvider = centralObjectProvider;
        this.start();
        react = true;
    }

    public void changeContainer(ElectionTypeContainer container) {
        model.changeContainer(container);
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
                model.getRows().add(new RowOfValues(model, inputContainer, model.getAmountCandidates(),
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

            System.out.println("stop");

            running = false;
            centralObjectProvider.getResultCheckerCommunicator().abortChecking();
            react = true;

        } else {
            react = false;
            running = true;

            Thread marginComputer = new Thread(new Runnable() {

                public void run() {
                    if (!BEASTCommunicator.checkForErrors(centralObjectProvider)) {

                        // ElectionDescriptionSource electSrc =
                        // centralObjectProvider.getElectionDescriptionSource();
                        // PostAndPrePropertiesDescriptionSource postAndPreSrc =
                        // centralObjectProvider.getPostAndPrePropertiesSource();
                        // ParameterSource paramSrc =
                        // centralObjectProvider.getParameterSrc();
                        // CheckStatusDisplay checkStatusDisplayer =
                        // centralObjectProvider.getCheckStatusDisplay();

                        int[][] votingData = new int[model.getAmountVoters()][model.getAmountCandidates()];

                        // lies die Daten in ein 2d array
                        for (int i = 0; i < model.getAmountVoters(); i++) {
                            for (int j = 0; j < model.getAmountCandidates(); j++) {
                                votingData[i][j] = model.getRows().get(i).getValues().get(j);
                            }
                        }

                        List<String> codeLines = generateRunnableCode(votingData);

                        List<Integer> winnerResults = CompilerAndExecutioner.compileAndRun(codeLines);
                        // here we now have the winner(s) of the computation

                        // here we now make a binary search tree for the margin
                        // value

                        int left = 0;
                        int right = votingData.length; // how many votes we have

                        while (left < right) {
                            // calculate the margin to check
                            int margin = left + Math.round((float) (right - left) / 2);

                            System.out.println("left: " + left);
                            
                            // generate the code for the margin
                            if (running) {
                                List<String> boundedCheckingCodeLines = generateMarginComputationCode(votingData,
                                        margin, winnerResults);

                                // create a temporary file, in which the code
                                // gets
                                // saved
                                String absolutePath = SuperFolderFinder.getSuperFolder() + pathToTempFolder;
                                String pathToCBMCFile = absolutePath + FileLoader.getNewUniqueName(absolutePath);

                                File cbmcFile = new File(pathToCBMCFile + ".c");
                                FileSaver.writeStringLinesToFile(boundedCheckingCodeLines, cbmcFile);

                                UnprocessedCBMCResult marginResult = centralObjectProvider.getResultCheckerCommunicator()
                                        .checkFile(cbmcFile, centralObjectProvider.getParameterSrc());

                                while (!marginResult.isFinished()) {
                                    try {
                                        Thread.sleep(SLEEPINTERVAL);
                                    } catch (InterruptedException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }

                                System.out.println("finished for margin " + margin + " result: " + marginResult.checkAssertionSuccess());
                                
                                if (marginResult.isValid()) {
                                    if(marginResult.checkAssertionSuccess()) {
                                        left = margin + 1;
                                        margin = margin +1;
                                    } else {
                                        right = margin;
                                    }
                                } else {
                                    //we were forcefully stop TODO do something to tell the user
                                }
                            }
                            
                            System.out.println("left: " + left + " right " + right);

                        }
                    } else { // TODO make this like the other error check, so it
                             // would
                        // open the c window
                        ErrorForUserDisplayer.displayError("There are still errors in the code, please fix them!");
                        running = false;
                        react = true;
                    }
                }
            }, "Margin-Computation-Thread");
            marginComputer.start();

        }
    }

    // public void test() {
    // ElectionDescriptionSource electSrc =
    // centralObjectProvider.getElectionDescriptionSource();
    // PostAndPrePropertiesDescriptionSource postAndPreSrc =
    // centralObjectProvider.getPostAndPrePropertiesSource();
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
    // boolean[postAndPreSrc.getPostAndPrePropertiesDescriptions().size()];
    //
    // int numberOfPresentedResults = 0;
    //
    // while (numberOfPresentedResults <
    // postAndPreSrc.getPostAndPrePropertiesDescriptions().size()) {
    // elapsedTime = System.nanoTime() - startTime;
    // passedTimeSeconds = (double) elapsedTime / 1000000000.0;
    // timeString = createTimeString(passedTimeSeconds);
    //
    // checkStatusDisplayer.displayText("waitingForPropertyResult", true,
    // postAndPreSrc.getPostAndPrePropertiesDescriptions().get(numberOfPresentedResults)
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

    private List<String> generateRunnableCode(int[][] votingData) {
        // if we vote for seats, we get the result as an array
        boolean multiOut = outputContainer.getOutputId() == ElectionOutputTypeIds.CAND_PER_SEAT;

        // this list will hold all the code in for later
        List<String> code = new ArrayList<String>();

        code.add("#include <stdio.h>");
        code.add("#ifndef V\n #define V " + votingData.length + "\n #endif");
        code.add("#ifndef C\n #define C " + votingData[0].length + "\n #endif");
        code.add("#ifndef S\n #define S " + votingData[0].length + "\n #endif");

        // add the array "ORIG_RESULTS" to the code. It contains the voting data
        code.addAll(getVotingResultCode(votingData));

        // add the code the user wrote
        code.addAll(centralObjectProvider.getElectionDescriptionSource().getElectionDescription().getCode());

        code.add("int main() {");
        switch (inputContainer.getInputID()) {
        case SINGLE_CHOICE:
            if (multiOut) {
                code.add("int *winner = voting(ORIG_VOTES);"); // call the
                                                               // voting method
                code.add("");
                code.add("fprintf(stdout, \"winner: =\"");
                code.add("for(int j = 0; j < C - 1; j++) {");
                code.add("fprintf(stdout, \"%d,\", array[i]);");
                code.add("}");
                code.add("fprintf(stdout, \"%d\", array[C - 1]);"); // add the
                                                                    // last line
                                                                    // without a
                                                                    // comma
            } else {
                code.add("int winner = voting(ORIG_VOTES);"); // we just have a
                                                              // single int as
                                                              // the winner
                code.add("fprintf(stdout, \"winner: = %d\", winner);");
            }
            break;
        case APPROVAL:
            if (multiOut) {
                code.add("int *winner = voting(ORIG_VOTES);"); // call the
                                                               // voting method
                code.add("");
                code.add("fprintf(stdout, \"winner: =\"");
                code.add("for(int j = 0; j < C - 1; j++) {");
                code.add("fprintf(stdout, \"%d,\", array[i]);");
                code.add("}");
                code.add("fprintf(stdout, \"%d\", array[C - 1]);"); // add the
                                                                    // last line
                                                                    // without a
                                                                    // comma
            } else {
                code.add("int winner = voting(ORIG_VOTES);"); // we just have a
                                                              // single int as
                                                              // the winner
                code.add("fprintf(stdout, \"winner: = %d\", winner);");
            }
            break;
        case WEIGHTED_APPROVAL:
            if (multiOut) {
                code.add("int *winner = voting(ORIG_VOTES);"); // call the
                                                               // voting method
                code.add("");
                code.add("fprintf(stdout, \"winner: =\"");
                code.add("for(int j = 0; j < C - 1; j++) {");
                code.add("fprintf(stdout, \"%d,\", array[i]);");
                code.add("}");
                code.add("fprintf(stdout, \"%d\", array[C - 1]);"); // add the
                                                                    // last line
                                                                    // without a
                                                                    // comma
            } else {
                code.add("int winner = voting(ORIG_VOTES);"); // we just have a
                                                              // single int as
                                                              // the winner
                code.add("fprintf(stdout, \"winner: = %d\", winner);");
            }
            break;
        case PREFERENCE:
            if (multiOut) {
                code.add("int *winner = voting(ORIG_VOTES);"); // call the
                                                               // voting method
                code.add("");
                code.add("fprintf(stdout, \"winner: =\"");
                code.add("for(int j = 0; j < C - 1; j++) {");
                code.add("fprintf(stdout, \"%d,\", array[i]);");
                code.add("}");
                code.add("fprintf(stdout, \"%d\", array[C - 1]);"); // add the
                                                                    // last line
                                                                    // without a
                                                                    // comma
            } else {
                code.add("int winner = voting(ORIG_VOTES);"); // we just have a
                                                              // single int as
                                                              // the winner
                code.add("fprintf(stdout, \"winner: = %d\", winner);");
            }
            break;

        default:
            ErrorForUserDisplayer.displayError(
                    "the current input type was not found. Please extend the methode \"generateRunnableCode\" in the class ElectionSimulation ");
            break;
        }

        code.add("}");

        return code;
    }

    private List<String> generateMarginComputationCode(int[][] votingData, int margin, List<Integer> origResult) {
        // if we vote for seats, we get the result as an array
        boolean multiOut = (outputContainer.getOutputId() == ElectionOutputTypeIds.CAND_PER_SEAT);

        // this list will hold all the code in for later
        List<String> code = new ArrayList<String>();

        // add the headers CBMC needs;
        code.add("#include <stdlib.h>");
        code.add("#include <stdint.h>");
        code.add("#include <assert.h>");
        code.add("");
        code.add("unsigned int nondet_uint();");
        code.add("int nondet_int();");
        code.add("");
        code.add("#define assert2(x, y) __CPROVER_assert(x, y)");
        code.add("#define assume(x) __CPROVER_assume(x)");
        code.add("");

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
        // code.add("int new_votes[V], diff[V], total_diff, pos_diff;");

        code.add("int new_votes[V], total_diff;");

        switch (inputContainer.getInputID()) {
        case SINGLE_CHOICE:
            code.add("for (int i = 0; i < V; i++) {"); // go over all voters
            code.add("int changed = nondet_int();"); // determine, if we want to
                                                     // changed votes for this
                                                     // voter
            code.add("assume(0 <= changed);");
            code.add("assume(changed <= 1);");
            code.add("if(changed) {");
            code.add("total_diff++;"); // if we changed the vote, we keep track
                                       // of it
            code.add("new_votes[i] = !ORIG_VOTES;"); // flip the vote (0 -> 1 |
                                                     // 1 -> 0)
            code.add("} else {");
            code.add("new_votes[i] = ORIG_VOTES[i];");
            code.add("}");
            code.add("}");
            code.add("assume(total_diff <= MARGIN);"); // no more changes than
                                                       // margin allows
            if (multiOut) {
                code.add("int *result = voting(new_votes);");
                code.add("for (int i = 0; i < S; i++) {"); // iterate over all
                                                           // candidates / seats
                code.add("assert(result[i] == ORIG_RESULT[i];");
                code.add("}"); // end of the for loop
            } else {
                code.add("int result = voting(new_votes);");
                code.add("assert(result == ORIG_RESULT);");
            }
            code.add("}"); // end of the function
            break;
        case APPROVAL:
            code.add("for (int i = 0; i < V; i++) {"); // go over all voters
            code.add("for (int j = 0; i < C; i++) {"); // go over all candidates
            code.add("int changed = nondet_int();"); // determine, if we want to
                                                     // changed votes for this
                                                     // voter - candidate pair
            code.add("assume(0 <= changed);");
            code.add("assume(changed <= 1);");
            code.add("if(changed) {");
            code.add("total_diff++;"); // if we changed the vote, we keep track
                                       // of it
            code.add("new_votes[i] = !ORIG_VOTES;"); // flip the vote (0 -> 1 |
                                                     // 1 -> 0)
            code.add("} else {");
            code.add("new_votes[i] = ORIG_VOTES[i];");
            code.add("}");
            code.add("}");
            code.add("}"); // end of the double for loop
            code.add("assume(total_diff <= MARGIN);"); // no more changes than
                                                       // margin allows
            if (multiOut) {
                code.add("int *result = voting(new_votes);");
                code.add("for (int i = 0; i < S; i++) {"); // iterate over all
                                                           // candidates / seats
                code.add("assert(result[i] == ORIG_RESULT[i];");
                code.add("}"); // end of the for loop
            } else {
                code.add("int result = voting(new_votes);");
                code.add("assert(result == ORIG_RESULT);");
            }
            code.add("}"); // end of the function
            break;
        case WEIGHTED_APPROVAL:
            code.add("for (int i = 0; i < V; i++) {"); // go over all voters
            code.add("for (int j = 0; i < C; i++) {"); // go over all candidates
            code.add("int changed = nondet_int();"); // determine, if we want to
                                                     // changed votes for this
                                                     // voter - candidate pair
            code.add("assume(0 <= changed);");
            code.add("assume(changed <= 1);");
            code.add("if(changed) {");
            code.add("total_diff++;"); // if we changed the vote, we keep track
                                       // of it
            code.add("new_votes[i] = nondet_int();");
            code.add("assume(new_votes[i] != ORIG_VOTES);"); // set the vote to
                                                             // (0-100), but
                                                             // different from
                                                             // original
            code.add("assume(0 <= new_votes[i]);");
            code.add("assume(new_votes[i] <= 100);");
            code.add("} else {");
            code.add("new_votes[i] = ORIG_VOTES[i];");
            code.add("}");
            code.add("}");
            code.add("}"); // end of the double for loop
            code.add("assume(total_diff <= MARGIN);"); // no more changes than
                                                       // margin allows
            if (multiOut) {
                code.add("int *result = voting(new_votes);");
                code.add("for (int i = 0; i < S; i++) {"); // iterate over all
                                                           // candidates / seats
                code.add("assert(result[i] == ORIG_RESULT[i];");
                code.add("}"); // end of the for loop
            } else {
                code.add("int result = voting(new_votes);");
                code.add("assert(result == ORIG_RESULT);");
            }
            code.add("}"); // end of the function
            break;
        case PREFERENCE:
            code.add("for (int i = 0; i < V; i++) {"); // go over all voters
            code.add("for (int j = 0; i < C; i++) {"); // go over all candidates
            code.add("int changed = nondet_int();"); // determine, if we want to
                                                     // changed votes for this
                                                     // voter - candidate pair
            code.add("assume(0 <= changed);");
            code.add("assume(changed <= 1);");
            code.add("if(changed) {");
            code.add("total_diff++;"); // if we changed the vote, we keep track
                                       // of it
            code.add("new_votes[i] = nondet_int();");
            code.add("assume(new_votes[i] != ORIG_VOTES);"); // set the vote to
                                                             // (0-100), but
                                                             // different from
                                                             // original
            code.add("assume(0 <= new_votes[i]);");
            code.add("assume(new_votes[i] <= 100);");
            code.add("} else {");
            code.add("new_votes[i] = ORIG_VOTES[i];");
            code.add("}");
            code.add("}");
            code.add("}"); // end of the double for loop
            code.add("assume(total_diff <= MARGIN);"); // no more changes than
                                                       // margin allows
            if (multiOut) {
                code.add("int *result = voting(new_votes);");
                code.add("for (int i = 0; i < S; i++) {"); // iterate over all
                                                           // candidates / seats
                code.add("assert(result[i] == ORIG_RESULT[i];");
                code.add("}"); // end of the for loop
            } else {
                code.add("int result = voting(new_votes);");
                code.add("assert(result == ORIG_RESULT);");
            }
            code.add("}"); // end of the function
            break;

        default:
            ErrorForUserDisplayer.displayError(
                    "the current input type was not found. Please extend the methode \"generateMarginComputationCode\" in the class ElectionSimulation ");
            break;
        }

        // not used lines ( I think) //TODO if they really aren't needed, delete
        // them
        // code.add("new_votes[i] = ORIG_VOTES[i] + diff[i];");
        // code.add("if (0 < diff[i]) pos_diff += diff[i];");
        // code.add("total_diff += diff[i];");
        // code.add("}");
        // code.add("__CPROVER_assume (pos_diff ≤ MARGIN);");
        // code.add("__CPROVER_assume (total_diff == 0);");
        // code.add("int *result = voting(new_votes);");
        // code.add("assert (equals(result, ORIG_RESULT));");
        // code.add("}");

        // add the main methode
        code.add("int main() {");
        code.add("verify();");
        code.add("}");

        return code;
    }

    private List<String> getVotingResultCode(int[][] votingData) {

        // compile the input data as an integer array for c
        // and save the lines in this list

        boolean twoDim = false;

        switch (inputContainer.getInputID()) {
        case SINGLE_CHOICE:
            twoDim = false;
            break;
        case APPROVAL:
            twoDim = true;
            break;
        case WEIGHTED_APPROVAL:
            twoDim = true;
            break;
        case PREFERENCE:
            twoDim = true;
            break;

        default:
            ErrorForUserDisplayer.displayError(
                    "the current input type was not found. Please extend the methode \"getVotingResultCode\" in the class ElectionSimulation ");
            break;
        }

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
                                     // voted" we have to add an offset of one
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
}
