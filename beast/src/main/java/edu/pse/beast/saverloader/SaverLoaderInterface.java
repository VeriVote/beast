/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.saverloader;

import edu.pse.beast.saverloader.OptionSaverLoader.OptionsSaverLoaderInterface;
import edu.pse.beast.saverloader.StaticSaverLoaders.FormalPropertySaverLoader;
import edu.pse.beast.saverloader.StaticSaverLoaders.SymbolicVarListSaverLoader;

/**
 *
 * @author Justin
 */
public class SaverLoaderInterface {

    private final PropertyListSaverLoader propListSL = new PropertyListSaverLoader();
    private final ProjectSaverLoader projSL = new ProjectSaverLoader();
    private final ElectionDescriptionSaverLoader electionDescrSL = new ElectionDescriptionSaverLoader();
    private final SymbolicVarListSaverLoader symVarListSL = null;
    private final PostAndPrePropertiesDescriptionSaverLoader propDescrSL = new PostAndPrePropertiesDescriptionSaverLoader();
    private final FormalPropertySaverLoader formalPropDescrSL = null;
    private final OptionsSaverLoaderInterface optSLI = null;

    public PropertyListSaverLoader getPropertyListSaverLoader() {
        return propListSL;
    }

    public ProjectSaverLoader getProjectSaverLoader() {
        return projSL;
    }

    public ElectionDescriptionSaverLoader getElectionDescriptionSaverLoader() {
        return electionDescrSL;
    }

    public SymbolicVarListSaverLoader getSymbolicVariableListSaverLoader() {
        return symVarListSL;
    }

    public PostAndPrePropertiesDescriptionSaverLoader getPostAndPrePropertiesDescriptionSaverLoader() {
        return propDescrSL;
    }

    public FormalPropertySaverLoader getFormalPropertiesDescriptionSaverLoader() {
        return formalPropDescrSL;
    }

    public OptionsSaverLoaderInterface getOptionsSaverLoaderInterface() {
        return optSLI;
    }
}
