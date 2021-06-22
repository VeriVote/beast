package edu.pse.beast.gui.paths;

import java.io.File;

public class PathHandler {
	private String baseDir;
	private String lastLoadedWorkspaceUUID;
	
	private final String lastLoadedConfigFileName = "lastLoadedWorkspace";
	
	private String relPathToSaveFiles = "./saveFiles/";
	
	private String relPathToWorkspaceSaveFiles = "./workspaces/";
	private String relPathToDescrSaveFiles = "./electionDescriptions/";
	private String relPathToPropDescrSaveFiles = "./propertyDescriptions/";
	
	public PathHandler() {
		tryload();
	}
	
	public File getBaseDir() {
		return new File(baseDir);
	}
	
	public File getElectionDescrDir() {
		return new File(baseDir + relPathToSaveFiles + relPathToDescrSaveFiles);
	}
	
	public File getPropDescrDir() {
		return new File(baseDir + relPathToSaveFiles + relPathToPropDescrSaveFiles);

	}
	
	private void tryload() {
		baseDir = System.getProperty("user.dir");
		File lastLoadedConfigFile = new File(baseDir + relPathToSaveFiles + lastLoadedConfigFileName);

		if(lastLoadedConfigFile.exists()) {
			
		} else {
			
		}
	}

	
}
