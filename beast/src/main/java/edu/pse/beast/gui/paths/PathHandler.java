package edu.pse.beast.gui.paths;

import java.io.File;

public class PathHandler {
	private String baseDir;
	
	private String relPathToSaveFiles = "./saveFiles/";
	
	private String relPathToWorkspaceSaveFiles = "./workspaces/";
	private String relPathToDescrSaveFiles = "./electionDescriptions/";
	private String relPathToPropDescrSaveFiles = "./propertyDescriptions/";
	
	private final String relPathToOptionsFile = "./.beastoptions";
	
	public PathHandler() {
		tryload();
	}
	
	public File getBaseDir() {
		return new File(baseDir);
	}
	
	public File getWorkspaceDir() {
		return new File(baseDir + relPathToSaveFiles + relPathToWorkspaceSaveFiles);
	}
	
	public File getElectionDescrDir() {
		return new File(baseDir + relPathToSaveFiles + relPathToDescrSaveFiles);
	}
	
	public File getPropDescrDir() {
		return new File(baseDir + relPathToSaveFiles + relPathToPropDescrSaveFiles);
	}
	
	public File getOptionsFile() {
		return new File(baseDir + relPathToOptionsFile);
	}
	
	private void tryload() {
		baseDir = System.getProperty("user.dir");
		if(!getWorkspaceDir().exists()) {
			getWorkspaceDir().mkdirs();
		}
		if(!getElectionDescrDir().exists()) {
			getElectionDescrDir().mkdirs();
		}
		if(!getPropDescrDir().exists()) {
			getPropDescrDir().mkdirs();
		}
	}	
}
