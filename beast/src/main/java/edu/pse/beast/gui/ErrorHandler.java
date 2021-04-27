package edu.pse.beast.gui;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.gui.log.LogGuiController;

public class ErrorHandler {
	
	private BeastGUIController guiController; 
	private List<String> errorLog = new ArrayList<>();
	private LogGuiController listener;

	public ErrorHandler(BeastGUIController guiController) {
		this.guiController = guiController;
	}
	
	public void logAndDisplayError(String title, String msg) {
		ErrorDialogHelper.showErrorDialog(title, msg);
		errorLog.add(title + ":\n" + msg);
		listener.handleAddedError(title, msg);
	}
	
	public void setListener(LogGuiController listener) {
		this.listener = listener;
	}

	public List<String> getErrorLog() {
		return errorLog;
	}
}
