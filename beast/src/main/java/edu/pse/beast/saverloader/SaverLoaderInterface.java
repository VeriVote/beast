//package edu.pse.beast.saverloader;
//
//import edu.pse.beast.saverloader.optionsaverloader.OptionsSaverLoaderInterface;
//import edu.pse.beast.saverloader.staticsaverloaders.FormalPropertySaverLoader;
//import edu.pse.beast.saverloader.staticsaverloaders.SymbolicVarListSaverLoader;
//
///**
// *
// * @author Justin Hecht
// */
//public class SaverLoaderInterface {
//
//    private final PropertyListSaverLoader propListSL = new PropertyListSaverLoader();
//    private final ProjectSaverLoader projSL = new ProjectSaverLoader();
//    private final ElectionDescriptionSaverLoader electionDescrSL =
//        new ElectionDescriptionSaverLoader();
//    private final SymbolicVarListSaverLoader symVarListSL = null;
//    private final PreAndPostConditionsDescriptionSaverLoader propDescrSL = new
//            PreAndPostConditionsDescriptionSaverLoader();
//    private final FormalPropertySaverLoader formalPropDescrSL = null;
//    private final OptionsSaverLoaderInterface optSLI = null;
//
//    /**
//     * Getter for PropertyListSaverLoader
//     * @return the PropertyListSaverLoader
//     */
//    public PropertyListSaverLoader getPropertyListSaverLoader() {
//        return propListSL;
//    }
//
//    /**
//     * Getter for ProjectSaverLoader
//     * @return the ProjectSaverLoader
//     */
//    public ProjectSaverLoader getProjectSaverLoader() {
//        return projSL;
//    }
//
//    /**
//     * Getter for ElectionDescriptionSaverLoader
//     * @return the ElectionDescriptionSaverLoader
//     */
//    public ElectionDescriptionSaverLoader getElectionDescriptionSaverLoader() {
//        return electionDescrSL;
//    }
//
//    /**
//     * Getter for SymbolicVarListSaverLoader
//     * @return the SymbolicVarListSaverLoader
//     */
//    public SymbolicVarListSaverLoader getSymbolicVariableListSaverLoader() {
//        return symVarListSL;
//    }
//
//    /**
//     * Getter for PreAndPostConditionsDescriptionSaverLoader
//     * @return the PreAndPostConditionsDescriptionSaverLoader
//     */
//    public PreAndPostConditionsDescriptionSaverLoader
//            getPreAndPostConditionsDescriptionSaverLoader() {
//        return propDescrSL;
//    }
//
//    /**
//     * Getter for FormalPropertySaverLoader
//     * @return the FormalPropertySaverLoader
//     */
//    public FormalPropertySaverLoader getFormalPropertiesDescriptionSaverLoader() {
//        return formalPropDescrSL;
//    }
//
//    /**
//     * Getter for OptionsSaverLoaderInterface
//     * @return the OptionsSaverLoaderInterface
//     */
//    public OptionsSaverLoaderInterface getOptionsSaverLoaderInterface() {
//        return optSLI;
//    }
//}