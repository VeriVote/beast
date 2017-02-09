package edu.pse.beast.SaverLoader;

import edu.pse.beast.celectiondescriptioneditor.ElectionTemplates.ElectionTemplateHandler;
import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.Project;
import edu.pse.beast.datatypes.electioncheckparameter.TimeOut;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeRep;
import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
import edu.pse.beast.propertylist.Model.PLModel;
import edu.pse.beast.propertylist.Model.PropertyItem;
import edu.pse.beast.saverloader.ProjectSaverLoader;
import org.junit.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author NikolaiLMS
 */
public class ProjectSaverLoaderTest {

    public ProjectSaverLoaderTest() {
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
     * Test of createSaveString method, of class Project.
     */
    @Test
    public void testCreateSaveString() throws Exception {
        ProjectSaverLoader projectSaverLoader = new ProjectSaverLoader();
        ElectionTemplateHandler electionTemplateHandler = new ElectionTemplateHandler();
        ElectionTypeContainer inputType = electionTemplateHandler.getById("list_of_integer_vals_per_voter");
        ElectionTypeContainer outputType = electionTemplateHandler.getById("one_candidate_or_zero");

        ElectionDescription electionDescription = new ElectionDescription("testDescription", inputType, outputType, 2);
        ArrayList<String> code = new ArrayList<String>();
        code.add("sdfgokdffg\n");
        code.add("sdkofgdfg\n");
        electionDescription.setCode(code);

        FormalPropertiesDescription pre = new FormalPropertiesDescription("CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD");
        FormalPropertiesDescription post = new FormalPropertiesDescription("CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD");
        SymbolicVariableList list = new SymbolicVariableList();
        list.addSymbolicVariable("voter1", new InternalTypeContainer(InternalTypeRep.VOTER));
        list.addSymbolicVariable("voter2", new InternalTypeContainer(InternalTypeRep.VOTER));
        list.addSymbolicVariable("cand", new InternalTypeContainer(InternalTypeRep.CANDIDATE));
        list.addSymbolicVariable("s", new InternalTypeContainer(InternalTypeRep.SEAT));
        PostAndPrePropertiesDescription description = new PostAndPrePropertiesDescription("postAndPre", pre, post, list);

        PLModel plModel = new PLModel();
        plModel.initialize();
        PropertyItem propertyItem = new PropertyItem(description, true);
        PropertyItem propertyItem2 = new PropertyItem(description, false);
        plModel.addDescription(propertyItem);
        plModel.addDescription(propertyItem2);
        ElectionCheckParameter electionCheckParameter = new ElectionCheckParameter(Arrays.asList(new Integer[]{1, 2}),
                Arrays.asList(new Integer[]{1, 2}), Arrays.asList(new Integer[]{1, 2}), new TimeOut(TimeUnit.HOURS, (long) 3.2)
                ,4, "-- unwind 6");
        Project project = new Project(electionCheckParameter, plModel, electionDescription, "TestProject");
        System.out.println(projectSaverLoader.createSaveString(project));
    }

    /**
     * Test of createFromSaveString method, of class Project.
     */
    @Test
    public void testCreateFromSaveString() throws Exception {
        ProjectSaverLoader projectSaverLoader = new ProjectSaverLoader();
        ElectionTemplateHandler electionTemplateHandler = new ElectionTemplateHandler();
        ElectionTypeContainer inputType = electionTemplateHandler.getById("list_of_integer_vals_per_voter");
        ElectionTypeContainer outputType = electionTemplateHandler.getById("one_candidate_or_zero");

        ElectionDescription electionDescription = new ElectionDescription("testDescription", inputType, outputType, 2);
        ArrayList<String> code = new ArrayList<String>();
        code.add("sdfgokdffg\n");
        code.add("sdkofgdfg\n");
        electionDescription.setCode(code);

        FormalPropertiesDescription pre = new FormalPropertiesDescription("CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD");
        FormalPropertiesDescription post = new FormalPropertiesDescription("CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD");
        SymbolicVariableList list = new SymbolicVariableList();
        list.addSymbolicVariable("voter1", new InternalTypeContainer(InternalTypeRep.VOTER));
        list.addSymbolicVariable("voter2", new InternalTypeContainer(InternalTypeRep.VOTER));
        list.addSymbolicVariable("cand", new InternalTypeContainer(InternalTypeRep.CANDIDATE));
        list.addSymbolicVariable("s", new InternalTypeContainer(InternalTypeRep.SEAT));
        PostAndPrePropertiesDescription description = new PostAndPrePropertiesDescription("postAndPre", pre, post, list);

        PLModel plModel = new PLModel();
        plModel.initialize();
        PropertyItem propertyItem = new PropertyItem(description, true);
        PropertyItem propertyItem2 = new PropertyItem(description, false);
        plModel.addDescription(propertyItem);
        plModel.addDescription(propertyItem2);
        ElectionCheckParameter electionCheckParameter = new ElectionCheckParameter(Arrays.asList(new Integer[]{1, 2}),
                Arrays.asList(new Integer[]{1, 2}), Arrays.asList(new Integer[]{1, 2}), new TimeOut(TimeUnit.HOURS, (long) 3.2)
                ,4, "-- unwind 6");
        Project project = new Project(electionCheckParameter, plModel, electionDescription, "TestProject");

        Project projec1 = (Project) projectSaverLoader.createFromSaveString(projectSaverLoader.createSaveString(project));
        System.out.println("PROJEC 1 NAME: " + projec1.getName());
        System.out.println(projec1.getElecDescr().getName());
        System.out.println(projec1.getElecDescr().getInputType().getType().getAccesTypeIfList());
        System.out.println(projec1.getPropList().getPropertyList().get(0).getDescription().getPrePropertiesDescription().getCode());
    }
}
