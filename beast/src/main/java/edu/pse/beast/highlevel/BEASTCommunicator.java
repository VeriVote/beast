package edu.pse.beast.highlevel;

import edu.pse.beast.parametereditor.ParameterEditor;
import edu.pse.beast.toolbox.SuperFolderFinder;

import javax.swing.*;
import java.awt.*;
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
    private Object[] options = {"OK"};

    private CentralObjectProvider centralObjectProvider;
    private List<ResultInterface> resultList;

    /**
     * Sets a new CentralObjectProvider which contains the references to the
     * other parts of BEAST.
     * @param centralObjectProvider New CentralObjectProvider which is to be set.
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
            return;
        } else if (!postAndPreSrc.isCorrect()) {
            checkStatusDisplayer.displayText("propertyErrors", false, "");
            return;
        } else if (!paramSrc.isCorrect()) {
            checkStatusDisplayer.displayText("parameterErrors", false, "");
            return;
        } else {

        resultList = centralObjectProvider.getResultCheckerCommunicator()
                .checkPropertiesForDescription(electSrc, postAndPreSrc, paramSrc);

        checkStatusDisplayer.displayText("startingCheck", true, "");

        Thread waitForResultsThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int numberOfPresentedResults = 0;
                while (numberOfPresentedResults < postAndPreSrc.getPostAndPrePropertiesDescriptions().size()) {
                    checkStatusDisplayer.displayText("waitingForPropertyResult", true,
                            postAndPreSrc.getPostAndPrePropertiesDescriptions().get(numberOfPresentedResults).getName()
                     + "'");
                    ;
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(BEASTCommunicator.class.getName()).log(Level.SEVERE, null, ex);
                    } if (resultList.size() > 0) {
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
                checkStatusDisplayer.displayText("", false, "");
            }
        });
        waitForResultsThread.start();
        }
    }

    @Override
    public void stopCheck() {
        ElectionDescriptionSource electSrc = centralObjectProvider.getElectionDescriptionSource();
        PostAndPrePropertiesDescriptionSource postAndPreSrc = centralObjectProvider.getPostAndPrePropertiesSource();
        ParameterSource paramSrc = centralObjectProvider.getParameterSrc();

        electSrc.resumeReacting();
        postAndPreSrc.resumeReacting();
        paramSrc.resumeReacting();

        centralObjectProvider.getResultCheckerCommunicator().abortChecking();
    }

}
