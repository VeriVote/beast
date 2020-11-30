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
    /** The Constant A_FEW_HOURS. */
    private static final long A_FEW_HOURS = (long) 3.2;

    /** The Constant NUM_PROCESSES. */
    private static final int NUM_PROCESSES = 4;

    /** The Constant THREE_HOURS_IN_MILISECONDS. */
    private static final int THREE_HOURS_IN_MILISECONDS = 10800000;

    /** Argument for six loop unwindings. */
    private static final String SIX_UNWINDINGS = "--unwind 6";

    /** The instance. */
    private static ElectionCheckParameter electionCheckParameter;

    @BeforeClass
    public static void setUpClass() {
        electionCheckParameter
            = new ElectionCheckParameter(
                Arrays.asList(new Integer[] {1, 2}),
                              Arrays.asList(new Integer[] {1, 2}),
                              Arrays.asList(new Integer[] {1, 2}),
                              1, 1, 1, new TimeOut(TimeUnit.HOURS, A_FEW_HOURS),
                              NUM_PROCESSES, SIX_UNWINDINGS);
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
        assertEquals((int)recreatedElectionCheckParameter.getRangeofCandidates().get(0), 1);
        assertEquals((int)recreatedElectionCheckParameter.getRangeofCandidates().get(1), 2);
        assertEquals((int)recreatedElectionCheckParameter.getRangeOfSeats().get(0), 1);
        assertEquals((int)recreatedElectionCheckParameter.getRangeOfSeats().get(1), 2);
        assertEquals((int)recreatedElectionCheckParameter.getRangeOfVoters().get(0), 1);
        assertEquals((int)recreatedElectionCheckParameter.getRangeOfVoters().get(1), 2);
        assertEquals(recreatedElectionCheckParameter.getArgument(), SIX_UNWINDINGS);
        assertEquals((int)recreatedElectionCheckParameter.getProcesses(), NUM_PROCESSES);
        final TimeOut recreatedTimeOut = recreatedElectionCheckParameter.getTimeout();
        assertEquals(recreatedTimeOut.getDuration(), THREE_HOURS_IN_MILISECONDS);
        assertEquals(recreatedTimeOut.getOrigUnit(), TimeUnit.HOURS);
    }
}
