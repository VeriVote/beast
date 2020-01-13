package edu.pse.beast.saverloader;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.electioncheckparameter.TimeOut;
import edu.pse.beast.saverloader.staticsaverloaders.ElectionCheckParameterSaverLoader;

/**
 * JUnit Testclass for
 * saverloader.StaticSaverLoaders.ElectionCheckParameterSaverLoader.
 *
 * @author Nikolai Schnell
 */
public class ElectionCheckParameterSaverLoaderTest {
    private static ElectionCheckParameter electionCheckParameter;

    @BeforeClass
    public static void setUpClass() {
        electionCheckParameter
            = new ElectionCheckParameter(
                Arrays.asList(new Integer[] {1, 2}),
                              Arrays.asList(new Integer[] {1, 2}),
                              Arrays.asList(new Integer[] {1, 2}),
                              1, 1, 1, new TimeOut(TimeUnit.HOURS, (long) 3.2), 4, "--unwind 6");
    }

    /**
     * Tests the ElectionCheckParameterSaverLoader by creating a saveString from a
     * ElectionCheckParameter object, then recreating that object from the
     * saveString and checking its integrity.
     *
     * @throws Exception when String creation does not work
     */
    @Test
    public void testCreateFromSaveString() throws Exception {
        final String saveString
            = ElectionCheckParameterSaverLoader.createSaveString(electionCheckParameter);
        final ElectionCheckParameter recreatedElectionCheckParameter
            = (ElectionCheckParameter)
                ElectionCheckParameterSaverLoader.createFromSaveString(saveString);
        assertEquals((int)recreatedElectionCheckParameter.getAmountCandidates().get(0), 1);
        assertEquals((int)recreatedElectionCheckParameter.getAmountCandidates().get(1), 2);
        assertEquals((int)recreatedElectionCheckParameter.getAmountSeats().get(0), 1);
        assertEquals((int)recreatedElectionCheckParameter.getAmountSeats().get(1), 2);
        assertEquals((int)recreatedElectionCheckParameter.getAmountVoters().get(0), 1);
        assertEquals((int)recreatedElectionCheckParameter.getAmountVoters().get(1), 2);
        assertEquals(recreatedElectionCheckParameter.getArgument(), "--unwind 6");
        assertEquals((int)recreatedElectionCheckParameter.getProcesses(), 4);
        final TimeOut recreatedTimeOut = recreatedElectionCheckParameter.getTimeout();
        assertEquals(recreatedTimeOut.getDuration(), 10800000);
        assertEquals(recreatedTimeOut.getOrigUnit(), TimeUnit.HOURS);
    }
}