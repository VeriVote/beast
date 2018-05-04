package edu.pse.beast.SaverLoader;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.electioncheckparameter.TimeOut;
import edu.pse.beast.saverloader.StaticSaverLoaders.ElectionCheckParameterSaverLoader;

/**
 * JUnit Testclass for saverloader.StaticSaverLoaders.ElectionCheckParameterSaverLoader.
 * @author NikolaiLMS
 */
public class ElectionCheckParameterSaverLoaderTest {
    private static ElectionCheckParameter electionCheckParameter;

    @BeforeClass
    public static void setUpClass() {
        electionCheckParameter = new ElectionCheckParameter(Arrays.asList(new Integer[]{1, 2}),
                Arrays.asList(new Integer[]{1, 2}), Arrays.asList(new Integer[]{1, 2}), 1,1,1, new TimeOut(TimeUnit.HOURS,
                (long) 3.2),4, "-- unwind 6");
    }

    /**
     * Tests the ElectionCheckParameterSaverLoader by creating a saveString from a
     * ElectionCheckParameter object, then recreating that object from the saveString and checking its integrity.
     */
    @Test
    public void testCreateFromSaveString() throws Exception {
        String saveString = ElectionCheckParameterSaverLoader.createSaveString(electionCheckParameter);

        ElectionCheckParameter recreatedElectionCheckParameter = (ElectionCheckParameter)
                ElectionCheckParameterSaverLoader.createFromSaveString(saveString);

        assert (recreatedElectionCheckParameter.getAmountCandidates().get(0).equals(1));
        assert (recreatedElectionCheckParameter.getAmountCandidates().get(1).equals(2));
        assert (recreatedElectionCheckParameter.getAmountSeats().get(0).equals(1));
        assert (recreatedElectionCheckParameter.getAmountSeats().get(1).equals(2));
        assert (recreatedElectionCheckParameter.getAmountVoters().get(0).equals(1));
        assert (recreatedElectionCheckParameter.getAmountVoters().get(1).equals(2));
        assert (recreatedElectionCheckParameter.getArgument().equals("-- unwind 6"));
        assert (recreatedElectionCheckParameter.getProcesses() == 4);

        TimeOut recreatedTimeOut = recreatedElectionCheckParameter.getTimeout();
        assert (recreatedTimeOut.getDuration() == 10800000);
        assert (recreatedTimeOut.getOrigUnit().equals(TimeUnit.HOURS));
    }
}
