//package edu.pse.beast.saverloader;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.concurrent.TimeUnit;
//
//import org.junit.Assert;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import edu.pse.beast.celectiondescriptioneditor.electiontemplates.ElectionTemplateHandler;
//import edu.pse.beast.datatypes.Project;
//import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
//import edu.pse.beast.datatypes.electioncheckparameter.TimeOut;
//import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
//import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
//import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
//import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
//import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
//import edu.pse.beast.propertylist.model.PLModel;
//import edu.pse.beast.propertylist.model.PropertyItem;
//import edu.pse.beast.saverloader.ProjectSaverLoader;
//import edu.pse.beast.types.InternalTypeContainer;
//import edu.pse.beast.types.InternalTypeRep;
//import edu.pse.beast.types.cbmctypes.inputplugins.WeightedApproval;
//import edu.pse.beast.types.cbmctypes.outputtypes.SingleCandidate;
//
///**
// * JUnit Testclass for saverloader.ProjectSaverLoader.
// * @author Nikolai Schnell
// */
//public class ProjectSaverLoaderTest {
//    private static Project project;
//    private static ProjectSaverLoader projectSaverLoader;
//
//    @BeforeClass
//    public static void setUpClass() {
//        projectSaverLoader = new ProjectSaverLoader();
//        ElectionTemplateHandler electionTemplateHandler = new ElectionTemplateHandler();
//        ElectionDescription electionDescription =
//            new ElectionDescription("testDescription",
//                                    new WeightedApproval(),
//                                    new SingleCandidate(),
//                                    2);
//        ArrayList<String> code = new ArrayList<String>();
//        code.add("sdfgokdffg");
//        code.add("sdkofgdfg");
//        electionDescription.setCode(code);
//
//        FormalPropertiesDescription pre =
//            new FormalPropertiesDescription("CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD");
//        FormalPropertiesDescription post =
//            new FormalPropertiesDescription("CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD");
//        SymbolicVariableList list = new SymbolicVariableList();
//        list.addSymbolicVariable("voter1", new InternalTypeContainer(InternalTypeRep.VOTER));
//        list.addSymbolicVariable("voter2", new InternalTypeContainer(InternalTypeRep.VOTER));
//        list.addSymbolicVariable("candidate",
//                                 new InternalTypeContainer(InternalTypeRep.CANDIDATE));
//        list.addSymbolicVariable("seat", new InternalTypeContainer(InternalTypeRep.SEAT));
//        PreAndPostConditionsDescription description1 =
//            new PreAndPostConditionsDescription("description1", pre, post, list);
//        PreAndPostConditionsDescription description2 =
//            new PreAndPostConditionsDescription("description2", pre, post, list);
//
//        PLModel plModel = new PLModel();
//        plModel.initialize();
//        PropertyItem propertyItem = new PropertyItem(description1, true, true);
//        PropertyItem propertyItem2 = new PropertyItem(description2, false, false);
//        plModel.addDescription(propertyItem);
//        plModel.addDescription(propertyItem2);
//        ElectionCheckParameter electionCheckParameter =
//            new ElectionCheckParameter(
//                Arrays.asList(new Integer[]{1, 2}),
//                Arrays.asList(new Integer[]{1, 2}),
//                Arrays.asList(new Integer[]{1, 2}),
//                new TimeOut(TimeUnit.HOURS, (long) 3.2),
//                4,
//                "-- unwind 6");
//        project =
//            new Project(electionCheckParameter, plModel,
//                        electionDescription, "TestProject");
//    }
//
//    /**
//     * Tests the ProjectSaverLoader by creating a saveString from a Project object,
//     * then recreating that object from the saveString and checking its integrity.
//     */
//    @Test
//    public void testCreateFromSaveString() throws Exception {
//        Project recreatedProject = (Project) projectSaverLoader.createFromSaveString(
//                projectSaverLoader.createSaveString(project));
//
//        ElectionDescription recreatedElectionDescription = recreatedProject.getElecDescr();
//        assertEquals(recreatedElectionDescription.getName(), "testDescription");
//        assertEquals(recreatedElectionDescription.getCode().get(0), "sdfgokdffg");
//        assertEquals(recreatedElectionDescription.getCode().get(1), "sdkofgdfg");
//        assertEquals(recreatedElectionDescription.getVotingDeclLine(), 2);
//
//        ElectionCheckParameter recreatedElectionCheckParameter =
//            project.getElectionCheckParameter();
//        assertEquals((int)recreatedElectionCheckParameter.getAmountCandidates().get(0), 1);
//        assertEquals((int)recreatedElectionCheckParameter.getAmountCandidates().get(1), 2);
//        assertEquals((int)recreatedElectionCheckParameter.getAmountSeats().get(0), 1);
//        assertEquals((int)recreatedElectionCheckParameter.getAmountSeats().get(1), 2);
//        assertEquals((int)recreatedElectionCheckParameter.getAmountVoters().get(0), 1);
//        assertEquals((int)recreatedElectionCheckParameter.getAmountVoters().get(1), 2);
//        assertEquals(recreatedElectionCheckParameter.getArgument(), "-- unwind 6");
//        assertEquals(recreatedElectionCheckParameter.getProcesses(), 4);
//
//        TimeOut recreatedTimeOut = recreatedElectionCheckParameter.getTimeout();
//        assertEquals(recreatedTimeOut.getDuration(), 10800000);
//        assertEquals(recreatedTimeOut.getOrigUnit(), TimeUnit.HOURS);
//
//        PLModel recreatedPLModel = recreatedProject.getPropList();
//        assertEquals(recreatedPLModel.getPropertyList().get(0)
//                         .getDescription().getName(), "description1");
//        assertEquals(recreatedPLModel.getPropertyList().get(0)
//                .getDescription().getPostConditionsDescription().getCode(),
//                "CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD");
//        assertEquals(recreatedPLModel.getPropertyList().get(0)
//                .getDescription().getPreConditionsDescription().getCode(),
//                "CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD");
//        assertEquals(recreatedPLModel.getPropertyList().get(0)
//                .getDescription().getSymbolicVariableList().get(0).getId(), "voter1");
//        assertEquals(recreatedPLModel.getPropertyList().get(0)
//                .getDescription().getSymbolicVariableList().get(0)
//                .getInternalTypeContainer().getInternalType(), InternalTypeRep.VOTER);
//        assertEquals(recreatedPLModel.getPropertyList().get(0)
//                .getDescription().getSymbolicVariableList().get(1).getId(), "voter2");
//        assertEquals(recreatedPLModel.getPropertyList().get(0)
//                .getDescription().getSymbolicVariableList().get(1)
//                .getInternalTypeContainer().getInternalType(), InternalTypeRep.VOTER);
//        assertEquals(recreatedPLModel.getPropertyList().get(0)
//                .getDescription().getSymbolicVariableList().get(2).getId(), "candidate");
//        assertEquals(recreatedPLModel.getPropertyList().get(0)
//                .getDescription().getSymbolicVariableList().get(2)
//                .getInternalTypeContainer().getInternalType(), InternalTypeRep.CANDIDATE);
//        assertEquals(recreatedPLModel.getPropertyList().get(0)
//                .getDescription().getSymbolicVariableList().get(3).getId(), "seat");
//        assertEquals(recreatedPLModel.getPropertyList().get(0)
//                .getDescription().getSymbolicVariableList().get(3)
//                .getInternalTypeContainer().getInternalType(), InternalTypeRep.SEAT);
//        assertTrue(recreatedPLModel.getPropertyList().get(0).getTestStatus());
//
//        assertEquals(recreatedPLModel.getPropertyList().get(1)
//                .getDescription().getName(), "description2");
//        assertEquals(recreatedPLModel.getPropertyList().get(1)
//                .getDescription().getPostConditionsDescription().getCode(),
//                "CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD");
//        assertEquals(recreatedPLModel.getPropertyList().get(1)
//                .getDescription().getPreConditionsDescription().getCode(),
//                "CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD");
//        assertEquals(recreatedPLModel.getPropertyList().get(1)
//                .getDescription().getSymbolicVariableList().get(0).getId(), "voter1");
//        assertEquals(recreatedPLModel.getPropertyList().get(1)
//                .getDescription().getSymbolicVariableList().get(0)
//                .getInternalTypeContainer().getInternalType(), InternalTypeRep.VOTER);
//        assertEquals(recreatedPLModel.getPropertyList().get(1)
//                .getDescription().getSymbolicVariableList().get(1).getId(), "voter2");
//        assertEquals(recreatedPLModel.getPropertyList().get(1)
//                .getDescription().getSymbolicVariableList().get(1)
//                .getInternalTypeContainer().getInternalType(), InternalTypeRep.VOTER);
//        assertEquals(recreatedPLModel.getPropertyList().get(1)
//                .getDescription().getSymbolicVariableList().get(2).getId(), "candidate");
//        assertEquals(recreatedPLModel.getPropertyList().get(1)
//                .getDescription().getSymbolicVariableList().get(2)
//                .getInternalTypeContainer().getInternalType(), InternalTypeRep.CANDIDATE);
//        assertEquals(recreatedPLModel.getPropertyList().get(1)
//                .getDescription().getSymbolicVariableList().get(3).getId(), "seat");
//        assertEquals(recreatedPLModel.getPropertyList().get(1)
//                .getDescription().getSymbolicVariableList().get(3)
//                .getInternalTypeContainer().getInternalType(), InternalTypeRep.SEAT);
//        assertFalse(recreatedPLModel.getPropertyList().get(1).getTestStatus());
//    }
//}
