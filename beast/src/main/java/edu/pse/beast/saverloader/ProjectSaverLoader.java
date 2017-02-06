package edu.pse.beast.saverloader;

import edu.pse.beast.celectiondescriptioneditor.ElectionTemplates.ElectionTemplateHandler;
import edu.pse.beast.datatypes.ElectionCheckParameter;
import edu.pse.beast.datatypes.Project;
import edu.pse.beast.datatypes.TimeOut;
import edu.pse.beast.datatypes.descofvoting.ElectionDescription;
import edu.pse.beast.datatypes.descofvoting.ElectionTypeContainer;
import edu.pse.beast.propertylist.Model.PLModel;
import edu.pse.beast.propertylist.PropertyList;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
*
* @author Justin
*/
public class ProjectSaverLoader {

    public static String createSaveString(Project project) {
        String electionDescription = "<electionDescription>\n" + ElectionDescriptionSaverLoader.createSaveString(project.getElecDescr())
                + "\n</electionDescription>\n";
        String propertyList = "<propertyList>\n" + PropertyListSaverLoader.createSaveString(project.getPropList()) + "\n</propertyList>\n";
        String electionCheckParameter = "<electionCheckParameter>\n" + ElectionCheckParameterSaverLoader.createSaveString(project.getElectionCheckParameter())
                + "\n</electionCheckParameter>\n";
        return electionDescription + propertyList + electionCheckParameter;
    }

    public static Project createFromSaveString(String s) throws Exception {
        String [] split = s.split("\n</electionDescription>\n");
        ElectionDescription electionDescription = ElectionDescriptionSaverLoader.createFromSaveString(split[0].replace("<electionDescription>\n", ""));
        split = split[1].split("\n</propertyList>\n");
        PLModel propertyList = PropertyListSaverLoader.createFromSaveString(split[0].replace("<propertyList>\n", ""));
        split = split[1].split("\n</electionCheckParameter>\n");
        ElectionCheckParameter electionCheckParameter = ElectionCheckParameterSaverLoader.createFromSaveString(split[0].replace("<electionCheckParameter>\n", ""));
        return new Project(electionCheckParameter, propertyList, electionDescription);
    }

}
