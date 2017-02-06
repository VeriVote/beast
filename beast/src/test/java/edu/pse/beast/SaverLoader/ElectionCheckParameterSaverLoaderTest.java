package edu.pse.beast.SaverLoader;

import edu.pse.beast.celectiondescriptioneditor.ElectionTemplates.ElectionTemplateHandler;
import edu.pse.beast.datatypes.ElectionCheckParameter;
import edu.pse.beast.datatypes.TimeOut;
import edu.pse.beast.datatypes.descofvoting.ElectionDescription;
import edu.pse.beast.datatypes.descofvoting.ElectionTypeContainer;
import edu.pse.beast.saverloader.ElectionCheckParameterSaverLoader;
import edu.pse.beast.saverloader.ElectionDescriptionSaverLoader;
import org.junit.*;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author NikolaiLMS
 */
public class ElectionCheckParameterSaverLoaderTest {
    public ElectionCheckParameterSaverLoaderTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of createSaveString method, of class ElectionCheckParameter.
     */
    @Test
    public void testCreateSaveString() {
        ElectionCheckParameter electionCheckParameter = new ElectionCheckParameter(Arrays.asList(new Integer[]{1, 2}),
                Arrays.asList(new Integer[]{1, 2}), Arrays.asList(new Integer[]{1, 2}), new TimeOut(TimeUnit.HOURS, (long) 3.2)
        ,4, "-- unwind 6");

        System.out.println(ElectionCheckParameterSaverLoader.createSaveString(electionCheckParameter));
    }

    /**
     * Test of createFromSaveString method, of class ElectionCheckParameter.
     */
    @Test
    public void testCreateFromSaveString() throws Exception {
        ElectionCheckParameter electionCheckParameter = new ElectionCheckParameter(Arrays.asList(new Integer[]{1, 2}),
                Arrays.asList(new Integer[]{1, 2}), Arrays.asList(new Integer[]{1, 2}), new TimeOut(TimeUnit.HOURS, (long) 3.2)
                ,4, "-- unwind 6");
        ElectionCheckParameter electionCheckParameter1;

            electionCheckParameter1 =
                    ElectionCheckParameterSaverLoader.createFromSaveString(ElectionCheckParameterSaverLoader.createSaveString(electionCheckParameter));

        System.out.println(electionCheckParameter1.getAmountCandidates());
        System.out.println(electionCheckParameter1.getAmountSeats());
        System.out.println(electionCheckParameter1.getAmountVoters());
        System.out.println(electionCheckParameter1.getArgument());
        System.out.println(electionCheckParameter1.getProcesses());
        System.out.println(electionCheckParameter1.getTimeout().getDuration());
        System.out.println(electionCheckParameter1.getTimeout().getOrigUnit().toString());
    }
}
