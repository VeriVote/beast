package edu.pse.beast.gui.paths;

import java.io.File;

public class PathHandler {
	private String workingDir;
	private String lastLoadedWorkspaceUUID;
	
	private final String lastLoadedConfigFileName = "lastLoadedWorkspace";
	
	private String relPathToSaveFiles = "./saveFiles/";
	
	private String relPathToWorkspaceSaveFiles = "./workspaces/";
	private String relPathToDescrSaveFiles = "./electionDescriptions/";
	private String relPathToPropDescrSaveFiles = "./propertyDescriptions/";
	
	
	public PathHandler() {
		tryload();
	}
	
	private void tryload() {
		workingDir = System.getProperty("user.dir");
		File lastLoadedConfigFile = new File(workingDir + relPathToSaveFiles + lastLoadedConfigFileName);
		if(lastLoadedConfigFile.exists()) {
			
		} else {
			
		}
	}
}
