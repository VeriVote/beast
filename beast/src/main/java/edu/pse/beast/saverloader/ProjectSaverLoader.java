package edu.pse.beast.saverloader;

import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.Project;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.propertylist.Model.PLModel;

/**
*
* @author Justin
*/
public class ProjectSaverLoader implements SaverLoader{
    private ElectionCheckParameterSaverLoader electionCheckParameterSaverLoader;
    private ElectionDescriptionSaverLoader electionDescriptionSaverLoader;
    private PropertyListSaverLoader propertyListSaverLoader;

    public ProjectSaverLoader () {
        this.electionCheckParameterSaverLoader = new ElectionCheckParameterSaverLoader();
        this.propertyListSaverLoader = new PropertyListSaverLoader();
        this.electionDescriptionSaverLoader = new ElectionDescriptionSaverLoader();
    }
    public String createSaveString(Object project) throws Exception{
        String electionDescription = "<electionDescription>\n" + electionDescriptionSaverLoader.createSaveString(
                ((Project) project).getElecDescr())
                + "\n</electionDescription>\n";
        String propertyList = "<propertyList>\n" + propertyListSaverLoader.createSaveString(
                ((Project) project).getPropList()) + "\n</propertyList>\n";
        String electionCheckParameter = "<electionCheckParameter>\n" + electionCheckParameterSaverLoader.createSaveString(
                ((Project) project).getElectionCheckParameter())
                + "\n</electionCheckParameter>\n";
        return electionDescription + propertyList + electionCheckParameter;
    }

    public Object createFromSaveString(String s) throws Exception {
        String [] split = s.split("\n</electionDescription>\n");
        ElectionDescription electionDescription = ((ElectionDescription) electionDescriptionSaverLoader.
                createFromSaveString(split[0].replace("<electionDescription>\n", "")));
        split = split[1].split("\n</propertyList>\n");
        PLModel propertyList = ((PLModel ) propertyListSaverLoader.createFromSaveString(split[0].replace("<propertyList>\n", "")));
        split = split[1].split("\n</electionCheckParameter>\n");
        ElectionCheckParameter electionCheckParameter = (ElectionCheckParameter)
                electionCheckParameterSaverLoader.createFromSaveString(split[0].replace("<electionCheckParameter>\n", ""));
        return new Project(electionCheckParameter, propertyList, electionDescription);
    }

}
