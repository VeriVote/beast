package edu.pse.beast.highlevel;

import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * The BEASTCommunicator coordinates all the other parts of BEAST.
 *
 * @author Jonas
 */
public class BEASTCommunicator implements CheckListener {

    private CentralObjectProvider centralObjectProvider;
    private List<ResultInterface> resultList;

    /**
     * Constructor that takes an CentralObjectProvider that provides access to
     * the other important packages for the BEASTCommunicator.
     *
     * @param centralObjectProvider CentralObjectProvider
     */
    public BEASTCommunicator() {

    }

    public void setCentralObjectProvider(CentralObjectProvider centralObjectProvider) {
        this.centralObjectProvider = centralObjectProvider;
    }

    @Override
    public void startCheck() {
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

            resultList = centralObjectProvider.getResultCheckerCommunicator().checkPropertiesForDescription(electSrc, postAndPreSrc, paramSrc);
            Iterator<ResultInterface> resultIterator = resultList.iterator();
            LinkedList<Boolean> resultsReady = new LinkedList<>();
            // for(int i=0; i< resultList.)
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
        Iterator<ResultInterface> resultIterator = resultList.iterator();
        while (resultIterator.hasNext()) {
            ResultInterface currentResult = resultIterator.next();
            if (currentResult.readyToPresent()) {
                centralObjectProvider.getResultPresenter().presentResult(currentResult);
            }
        }
        resultList = null;
    }

}
