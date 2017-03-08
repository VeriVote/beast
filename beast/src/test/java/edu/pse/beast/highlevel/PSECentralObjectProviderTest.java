package edu.pse.beast.highlevel;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jonas
 */
public class PSECentralObjectProviderTest {
    
    
    /**
     * Test the creation of all objects by the class PSECentralObjectProvider.
     */
    @Test
    public void testGetElectionDescriptionSource() {
        System.out.println("createObjects - PSECentralObjectProvider");
        PSECentralObjectProvider instance = new PSECentralObjectProvider(new BEASTCommunicator());
        ElectionDescriptionSource failResultEDS = null;
        ElectionDescriptionSource resultEDS = instance.getElectionDescriptionSource();
        assertNotEquals(failResultEDS, resultEDS);
        PostAndPrePropertiesDescriptionSource failResultPNP = null;
        PostAndPrePropertiesDescriptionSource resultPNP = instance.getPostAndPrePropertiesSource();
        assertNotEquals(failResultPNP, resultPNP);
        ResultCheckerCommunicator failResultRCC = null;
        ResultCheckerCommunicator resultRCC = instance.getResultCheckerCommunicator();
        assertNotEquals(failResultRCC, resultRCC);
        ParameterSource failResultPS = null;
        ParameterSource resultPS = instance.getParameterSrc();
        assertNotEquals(failResultPS, resultPS);
        ResultPresenter failResultRP = null;
        ResultPresenter resultRP = instance.getResultPresenter();
        assertNotEquals(failResultRP, resultRP);
        MainNotifier failResultMN = null;
        MainNotifier resultMN = instance.getMainNotifier();
        assertNotEquals(failResultMN, resultMN);
        CheckStatusDisplay failResultCSD = null;
        CheckStatusDisplay resultCSD = instance.getCheckStatusDisplay();
        assertNotEquals(failResultCSD, resultCSD);
    }
    
}
