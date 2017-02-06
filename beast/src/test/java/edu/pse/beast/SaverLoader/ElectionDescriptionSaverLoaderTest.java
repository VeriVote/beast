package edu.pse.beast.SaverLoader;

import edu.pse.beast.celectiondescriptioneditor.ElectionTemplates.ElectionTemplateHandler;
import edu.pse.beast.datatypes.descofvoting.ElectionDescription;
import edu.pse.beast.datatypes.descofvoting.ElectionTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeRep;
import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
import edu.pse.beast.saverloader.ElectionDescriptionSaverLoader;
import edu.pse.beast.stringresource.StringLoaderInterface;
import org.junit.*;

import java.util.ArrayList;

/**
 * @author NikolaiLMS
 */
public class ElectionDescriptionSaverLoaderTest {
    public ElectionDescriptionSaverLoaderTest() {
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
     * Test of createSaveString method, of class ElectionDescription.
     */
    @Test
    public void testCreateSaveString() throws Exception {
        ElectionTemplateHandler electionTemplateHandler = new ElectionTemplateHandler();
        ElectionTypeContainer inputType = electionTemplateHandler.getById("list_of_integer_vals_per_voter");
        ElectionTypeContainer outputType = electionTemplateHandler.getById("one_candidate_or_zero");

        ElectionDescription electionDescription = new ElectionDescription("testDescription", inputType, outputType, 2);
        ArrayList<String> code = new ArrayList<String>();
        code.add("sdfgokdffg\n");
        code.add("sdkofgdfg\n");
        electionDescription.setCode(code);

        System.out.println(ElectionDescriptionSaverLoader.createSaveString(electionDescription));
    }

    /**
     * Test of createFromSaveString method, of class ElectionDescription.
     */
    @Test
    public void testCreateFromSaveString() throws Exception {
        ElectionTemplateHandler electionTemplateHandler = new ElectionTemplateHandler();
        ElectionTypeContainer inputType = electionTemplateHandler.getById("list_of_integer_vals_per_voter");
        ElectionTypeContainer outputType = electionTemplateHandler.getById("one_candidate_or_zero");

        ElectionDescription electionDescription = new ElectionDescription("testDescription", inputType, outputType, 2);
        ArrayList<String> code = new ArrayList<String>();
        code.add("sdfgokdffg\n");
        code.add("sdkofgdfg\n");
        electionDescription.setCode(code);
        ElectionDescription createdElectionDescription =
                ElectionDescriptionSaverLoader.createFromSaveString(ElectionDescriptionSaverLoader.createSaveString(electionDescription));
        System.out.println(electionDescription.getName());
        System.out.println(createdElectionDescription.getVotingDeclLine());
        System.out.println(createdElectionDescription.getCode());
        System.out.println(createdElectionDescription.getInputType().getType().getAccesTypeIfList().toString());
        System.out.println(createdElectionDescription.getOutputType().getType().getInternalType().toString());
        long e = 4;
        System.out.println(e);
    }
}
