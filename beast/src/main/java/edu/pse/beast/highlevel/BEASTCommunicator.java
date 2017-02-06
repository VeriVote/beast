package edu.pse.beast.highlevel;

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
     * Empty Constructor
     */
    public BEASTCommunicator() {

    }
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
        electSrc.stopReacting();
        postAndPreSrc.stopReacting();
        paramSrc.stopReacting();
        if (!electSrc.isCorrect()) {
            System.err.println("Es bestehen noch Fehler in der Beschreibung des Wahlverfahrens. "
                    + "Bitte korrigieren sie diese, um fortzufahren.");
        } else if (!postAndPreSrc.isCorrect()) {
            System.err.println("Es bestehen noch Fehler in der Beschreibung der zu prüfenden Eigenschaften. "
                    + "Bitte korrigieren sie diese, um fortzufahren.");
        } else if (!paramSrc.isCorrect()) {
            System.err.println("Es bestehen noch Fehler bei den angegebenen Parametern. "
                    + "Bitte korrigieren sie diese, um fortzufahren.");
        } else {

            resultList = centralObjectProvider.getResultCheckerCommunicator()
                    .checkPropertiesForDescription(electSrc, postAndPreSrc, paramSrc);

            if (resultList.isEmpty()) {
                System.out.println("result List is empty");
            }

            while (resultList.size() > 0) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(BEASTCommunicator.class.getName()).log(Level.SEVERE, null, ex);
                }
                //for (ResultInterface result : resultList) {
                for (Iterator<ResultInterface> iterator = resultList.iterator(); iterator.hasNext();) {

                    ResultInterface result = (ResultInterface) iterator.next();
                    if (result.readyToPresent()) {
                        ResultPresenter resultPresenter = centralObjectProvider.getResultPresenter();
                        resultPresenter.presentResult(result);
                        iterator.remove();
                    }
                }
            }
            electSrc.resumeReacting();
            postAndPreSrc.resumeReacting();
            paramSrc.resumeReacting();
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
