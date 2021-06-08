package edu.pse.beast.gui.log;

import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

import edu.pse.beast.gui.ErrorHandler;
import edu.pse.beast.gui.workspace.BeastWorkspace;
import javafx.scene.layout.AnchorPane;

public class LogGuiController {
	private AnchorPane logAnchorPane;
	private ErrorHandler errorhandler;
	private CodeArea logArea = new CodeArea();

	public LogGuiController(AnchorPane logAnchorPane,
			ErrorHandler errorhandler) {
		this.logAnchorPane = logAnchorPane;
		this.errorhandler = errorhandler;
		errorhandler.setListener(this);

		VirtualizedScrollPane<CodeArea> vsp = new VirtualizedScrollPane<CodeArea>(
				logArea);
		logAnchorPane.getChildren().add(vsp);

		AnchorPane.setTopAnchor(vsp, 0d);
		AnchorPane.setBottomAnchor(vsp, 0d);
		AnchorPane.setLeftAnchor(vsp, 0d);
		AnchorPane.setRightAnchor(vsp, 0d);
	}

	public void handleAddedError(String title, String msg) {
		logArea.clear();
		logArea.insertText(0, String.join("\n", errorhandler.getErrorLog()));
	}

}
