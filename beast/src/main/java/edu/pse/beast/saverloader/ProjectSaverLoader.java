package edu.pse.beast.saverloader;

import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.Project;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.propertylist.Model.PLModel;
import edu.pse.beast.saverloader.StaticSaverLoaders.ElectionCheckParameterSaverLoader;

/**
* Implements SaverLoader methods for creating saveStrings from Project objects and vice versa.
* @author NikolaiLMS
*/
public class ProjectSaverLoader implements SaverLoader {
    private ElectionDescriptionSaverLoader electionDescriptionSaverLoader;
    private PropertyListSaverLoader propertyListSaverLoader;

    /**
     * Constructor
     * Initializes propertyListSaverLoader and electionDescriptionSaverLoader.
     */
    public ProjectSaverLoader() {
        this.propertyListSaverLoader = new PropertyListSaverLoader();
        this.electionDescriptionSaverLoader = new ElectionDescriptionSaverLoader();
    }

    @Override
    public String createSaveString(Object project) throws Exception {
        String name = "<projectName>\n" + ((Project) project).getName()
                + "\n</projectName>\n";
        String electionDescription = "<electionDescription>\n" + electionDescriptionSaverLoader.createSaveString(
                ((Project) project).getElecDescr())
                + "\n</electionDescription>\n";
        String propertyList = "<propertyList>\n" + propertyListSaverLoader.createSaveString(
                ((Project) project).getPropList()) + "\n</propertyList>\n";
        String electionCheckParameter = "<electionCheckParameter>\n" + ElectionCheckParameterSaverLoader.
                createSaveString(((Project) project).getElectionCheckParameter())
                + "\n</electionCheckParameter>\n";
        return name + electionDescription + propertyList + electionCheckParameter;
    }

    @Override
    public Object createFromSaveString(String s) throws Exception {
        String [] split = s.split("\n</projectName>\n");
        String name = split[0].replace("<projectName>\n", "");
        split = split[1].split("\n</electionDescription>\n");
        ElectionDescription electionDescription = ((ElectionDescription) electionDescriptionSaverLoader.
                createFromSaveString(split[0].replace("<electionDescription>\n", "")));
        split = split[1].split("\n</propertyList>\n");
        PLModel propertyList = ((PLModel) propertyListSaverLoader.createFromSaveString(
                split[0].replace("<propertyList>\n", "")));
        split = split[1].split("\n</electionCheckParameter>\n");
        ElectionCheckParameter electionCheckParameter = (ElectionCheckParameter)
                ElectionCheckParameterSaverLoader.createFromSaveString(
                        split[0].replace("<electionCheckParameter>\n", ""));
        return new Project(electionCheckParameter, propertyList, electionDescription, name);
    }

}
