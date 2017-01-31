package edu.pse.beast.parametereditor;

import edu.pse.beast.stringresource.StringResourceLoader;
import edu.pse.beast.toolbox.ActionIdAndListener;
import edu.pse.beast.toolbox.ImageResourceProvider;
import edu.pse.beast.toolbox.ToolbarHandler;
import javax.swing.JToolBar;
/**
 *
 * @author Jonas
 */
public class ParameterEditorToolbarHandler extends ToolbarHandler {
    private ParameterEditorWindow window;
	
	public ParameterEditorToolbarHandler(ImageResourceProvider imageRes, StringResourceLoader stringRes,
			ActionIdAndListener[] actionIdsAndListener, JToolBar toolbar, ParameterEditorWindow window) {
		super(imageRes, stringRes, actionIdsAndListener, toolbar);
		this.window = window;
	}
}
