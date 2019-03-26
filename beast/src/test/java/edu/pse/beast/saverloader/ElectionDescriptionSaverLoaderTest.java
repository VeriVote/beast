//package edu.pse.beast.saverloader;
//
//import static org.junit.Assert.assertEquals;
//
//import java.util.Arrays;
//
//import org.junit.Test;
//
//import edu.pse.beast.celectiondescriptioneditor.electiontemplates.ElectionTemplateHandler;
//import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
//import edu.pse.beast.saverloader.ElectionDescriptionSaverLoader;
//import edu.pse.beast.toolbox.UnifiedNameContainer;
//
///**
// * JUnit Testclass for saverloader.ElectionDescriptionSaverLoader.
// *
// * @author Nikolai Schnell
// */
//public class ElectionDescriptionSaverLoaderTest {
//  @Test
//  public void testLoadFromCreatedSaveString() {
//    ElectionTemplateHandler electionTemplateHandler = new ElectionTemplateHandler();
//    ElectionDescriptionSaverLoader s = new ElectionDescriptionSaverLoader();
//
//    ElectionDescription desc = new ElectionDescription("desc",
//        electionTemplateHandler.getStandardInput().getInputType(),
//        electionTemplateHandler.getStandardResult().getOutputType(), 2);
//    desc.setCode(
//          Arrays.asList(
//              "//line1",
//              "//line2",
//              "unsigned int voting(unsigned int votes["
//                  + UnifiedNameContainer.getVoter() + "]) {",
//              "return 0;",
//              "}"
//          )
//    );
//    String save = s.createSaveString(desc);
//    ElectionDescription loadedDesc = (ElectionDescription) s.createFromSaveString(save);
//    assertEquals("desc", loadedDesc.getName());
//    assertEquals(electionTemplateHandler.getStandardInput().getInputType(),
//        loadedDesc.getContainer().getInputType());
//    assertEquals(electionTemplateHandler.getStandardResult().getOutputType(),
//        loadedDesc.getContainer().getOutputType());
//    assertEquals(desc.getCode().get(0), loadedDesc.getCode().get(0));
//    assertEquals(desc.getCode().get(1), loadedDesc.getCode().get(1));
//    assertEquals(desc.getCode().get(2), loadedDesc.getCode().get(2));
//    assertEquals(desc.getCode().get(3), loadedDesc.getCode().get(3));
//    assertEquals(desc.getCode().get(4), loadedDesc.getCode().get(4));
//  }
//
//  @Test
//  public void testSaveSimpleRealisticDescription() {
//
//    ElectionTemplateHandler electionTemplateHandler = new ElectionTemplateHandler();
//    ElectionDescriptionSaverLoader s = new ElectionDescriptionSaverLoader();
//
//    ElectionDescription desc = new ElectionDescription("desc",
//        electionTemplateHandler.getStandardInput().getInputType(),
//        electionTemplateHandler.getStandardResult().getOutputType(), 2);
//
//    desc.setCode(Arrays.asList(
//        "//Single-choice: Wähler stimmt jeweils für einen Kandidaten",
//        "//Kandidat oder unentschieden: Ein gewählter Kandidat oder unentschieden",
//        "unsigned int voting(unsigned int votes[" + UnifiedNameContainer.getVoter() + "]) {",
//        "return 0;",
//        "} "
//    ));
//    String save = s.createSaveString(desc);
//    ElectionDescription loadedDesc = (ElectionDescription) s.createFromSaveString(save);
//    assertEquals("desc", loadedDesc.getName());
//    assertEquals(electionTemplateHandler.getStandardInput().getInputType(),
//        loadedDesc.getContainer().getInputType());
//    assertEquals(electionTemplateHandler.getStandardResult().getOutputType(),
//        loadedDesc.getContainer().getOutputType());
//    assertEquals(desc.getCode().get(0), loadedDesc.getCode().get(0));
//    assertEquals(desc.getCode().get(1), loadedDesc.getCode().get(1));
//    assertEquals(desc.getCode().get(2), loadedDesc.getCode().get(2));
//    assertEquals(desc.getCode().get(3), loadedDesc.getCode().get(3));
//    assertEquals(desc.getCode().get(4), loadedDesc.getCode().get(4));
//  }
//}