
package edu.pse.beast.highlevel;

import java.util.List;
import java.util.Iterator;
/**
 * The BEASTCommunicator coordinates all the other parts of BEAST.
 * @author Jonas
 */
public class BEASTCommunicator implements CheckListener{
    private final CentralObjectProvider centralObjectProvider;
    /**
     * Constructor that takes an CentralObjectProvider that provides access to 
 the other important packages for the BEASTCommunicator.
     * @param centralObjectProvider CentralObjectProvider
     */
    public BEASTCommunicator(CentralObjectProvider centralObjectProvider) {
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
        if(!electSrc.isCorrect()) {
            System.err.println("Es bestehen noch Fehler in der Beschreibung des Wahlverfahrens. "
                    + "Bitte korrigieren sie diese, um fortzufahren.");
        }else if(!postAndPreSrc.isCorrect()) {
            System.err.println("Es bestehen noch Fehler in der Beschreibung der zu prüfenden Eigenschaften. "
                    + "Bitte korrigieren sie diese, um fortzufahren.");
        }else if(!paramSrc.isCorrect()) {
            System.err.println("Es bestehen noch Fehler bei den angegebenen Parametern. "
                    + "Bitte korrigieren sie diese, um fortzufahren.");
        }else {
            List<ResultInterface> result = centralObjectProvider.getResultCheckerCommunicator().checkPropertiesForDescription(electSrc, postAndPreSrc, paramSrc);
            Iterator<ResultInterface> resultIterator = result.iterator();
            while(resultIterator.hasNext()) {
                centralObjectProvider.getResultPresenter().presentResult(resultIterator.next());
            }
        }
    }

    @Override
    public void stopCheck() {
        centralObjectProvider.getResultCheckerCommunicator().abortChecking();
    }
    
}
