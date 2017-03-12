package edu.pse.beast.SaverLoader;

import edu.pse.beast.celectiondescriptioneditor.ElectionTemplates.ElectionTemplateHandler;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.saverloader.ElectionDescriptionSaverLoader;
import edu.pse.beast.saverloader.StaticSaverLoaders.SaverLoaderHelper;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * JUnit Testclass for saverloader.ElectionDescriptionSaverLoader.
 * @author NikolaiLMS
 */
public class ElectionDescriptionSaverLoaderTest {
    @Test
    public void testLoadFromCreatedSaveString() {
        ElectionTemplateHandler electionTemplateHandler = new ElectionTemplateHandler();
        ElectionDescriptionSaverLoader s = new ElectionDescriptionSaverLoader();

        ElectionDescription desc = new ElectionDescription(
                "desc",
                electionTemplateHandler.getStandardInput(),
                electionTemplateHandler.getStandardResult(),
                2);
        desc.setCode(Arrays.asList(
                "//line1",
                "//line2",
                "unsigned int voting(unsigned int votes[V]) {",
                "return 0;",
                "}"));
        String save = s.createSaveString(desc);
        ElectionDescription loadedDesc = (ElectionDescription) s.createFromSaveString(save);
        assertEquals("desc", loadedDesc.getName());
        assertEquals(electionTemplateHandler.getStandardInput().getId(), loadedDesc.getInputType().getId());
        assertEquals(electionTemplateHandler.getStandardResult().getId(), loadedDesc.getOutputType().getId());
        assertEquals(desc.getCode().get(0), loadedDesc.getCode().get(0));
        assertEquals(desc.getCode().get(1), loadedDesc.getCode().get(1));
        assertEquals(desc.getCode().get(2), loadedDesc.getCode().get(2));
        assertEquals(desc.getCode().get(3), loadedDesc.getCode().get(3));
        assertEquals(desc.getCode().get(4), loadedDesc.getCode().get(4));
    }
}
