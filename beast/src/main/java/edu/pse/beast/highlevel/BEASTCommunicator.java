package edu.pse.beast.highlevel;

import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            
            if(resultList.isEmpty() ) {
                System.out.println("result List is empty");
            }

            System.out.println("Now I'm done with checking");
            
            while (resultList.size() > 0) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(BEASTCommunicator.class.getName()).log(Level.SEVERE, null, ex);
                }
                //for (ResultInterface result : resultList) {
                for (Iterator<ResultInterface> iterator = resultList.iterator(); iterator.hasNext();) {
                    ResultInterface result = (ResultInterface) iterator.next();
                    
                    ResultPresenter resultPresenter = centralObjectProvider.getResultPresenter();
                    
                    resultPresenter.presentResult(result);
                    
                    iterator.remove();
                } 
            }
            
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
