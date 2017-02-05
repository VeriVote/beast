package edu.pse.beast.parametereditor;

import edu.pse.beast.stringresource.StringResourceLoader;
import edu.pse.beast.toolbox.ActionIdAndListener;
import edu.pse.beast.toolbox.ImageResourceProvider;
import edu.pse.beast.toolbox.ToolbarHandler;
import javax.swing.JToolBar;
/**
 * The ParameterEditorToolbarHandler creates the toolbar for the ParameterEditor.
 * @author Jonas
 */
public class ParameterEditorToolbarHandler extends ToolbarHandler {
    private final ParameterEditorWindow window;
        /**
         * Creates the toolbar and applies it to the ParameterEditorWindow
         * @param imageRes resource for the toolbar icons
         * @param stringRes resource for the tooltip Strings
         * @param actionIdsAndListener array of ActionIdAndListeners
         * @param toolbar toolbar of the ParameterEditorWindow
         * @param window ParameterEditorWindow
         */
        public ParameterEditorToolbarHandler(ImageResourceProvider imageRes, StringResourceLoader stringRes,
            ActionIdAndListener[] actionIdsAndListener, JToolBar toolbar, ParameterEditorWindow window) {
                super(imageRes, stringRes, actionIdsAndListener, toolbar);
                this.window = window;
        }
}
