/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.saverloader;

/**
 *
 * @author Niels
 */
public class SaverLoaderInterface {
	
    private final PropertyListSaverLoader propListSL = null;
    private final ProjectSaverLoader projSL = null;
    private final ElectionDescriptionSaverLoader electionDescrSL = null;
    private final SymbolicVariableListSaverLoader symVarListSL = null;
    private final PostAndPrePropertiesDescriptionSaverLoader propDescrSL = null;
    private final FormalPropertiesDescriptionSaverLoader formalPropDescrSL = null;
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

	public SymbolicVariableListSaverLoader getSymbolicVariableListSaverLoader() {
		return symVarListSL;
	}

	public PostAndPrePropertiesDescriptionSaverLoader getPostAndPrePropertiesDescriptionSaverLoader() {
		return propDescrSL;
	}

	public FormalPropertiesDescriptionSaverLoader getFormalPropertiesDescriptionSaverLoader() {
		return formalPropDescrSL;
	}

	public OptionsSaverLoaderInterface getOptionsSaverLoaderInterface() {
		return optSLI;
	}
    
}
