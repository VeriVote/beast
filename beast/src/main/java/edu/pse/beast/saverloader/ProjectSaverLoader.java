//package edu.pse.beast.saverloader;
//
//import java.util.Map;
//
//import edu.pse.beast.datatypes.Project;
//import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
//import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
//import edu.pse.beast.propertylist.Model.PLModel;
//import edu.pse.beast.saverloader.StaticSaverLoaders.ElectionCheckParameterSaverLoader;
//import edu.pse.beast.saverloader.StaticSaverLoaders.SaverLoaderHelper;
//
///**
//* Implements SaverLoader methods for creating saveStrings from Project objects and vice versa.
//* @author NikolaiLMS
//*/
//public class ProjectSaverLoader implements SaverLoader {
//    private ElectionDescriptionSaverLoader electionDescriptionSaverLoader;
//    private PropertyListSaverLoader propertyListSaverLoader;
//
//    /**
//     * Constructor
//     * Initializes propertyListSaverLoader and electionDescriptionSaverLoader.
//     */
//    public ProjectSaverLoader() {
//        this.propertyListSaverLoader = new PropertyListSaverLoader();
//        this.electionDescriptionSaverLoader = new ElectionDescriptionSaverLoader();
//    }
//
//    @Override
//    public String createSaveString(Object obj) {
//        SaverLoaderHelper h = new SaverLoaderHelper();
//        Project proj = (Project) obj;
//        StringBuilder saveString = new StringBuilder();
//        saveString.append(h.getStringForAttr("name", proj.getName()));
//        String electionDescStr = electionDescriptionSaverLoader.createSaveString(proj.getElecDescr());
//        saveString.append(h.getStringForAttr("elecDesc", electionDescStr));
//        String propListStr = propertyListSaverLoader.createSaveString(proj.getPropList());
//        saveString.append(h.getStringForAttr("propList", propListStr));
//        String electionCheckParamStr = ElectionCheckParameterSaverLoader.createSaveString(
//                proj.getElectionCheckParameter());
//        saveString.append(h.getStringForAttr("electionCheckParams", electionCheckParamStr));
//        return saveString.toString();
//    }
//
//    @Override
//    public Object createFromSaveString(String s) throws ArrayIndexOutOfBoundsException {
//        Map<String,String> m = new SaverLoaderHelper().parseSaveString(s);
//        String name = m.get("name");
//        String descStr = m.get("elecDesc");
//        ElectionDescription electionDescription =
//                (ElectionDescription) electionDescriptionSaverLoader.createFromSaveString(descStr);
//        String propListStr = m.get("propList");
//        PLModel propertyList = ((PLModel) propertyListSaverLoader.createFromSaveString(propListStr));
//        String paramStr = m.get("electionCheckParams");
//        ElectionCheckParameter electionCheckParameter = (ElectionCheckParameter)
//                ElectionCheckParameterSaverLoader.createFromSaveString(paramStr);
//        return new Project(electionCheckParameter, propertyList, electionDescription, name);
//    }
//
//}
