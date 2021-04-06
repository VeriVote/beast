package edu.pse.beast.gui;

import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.gui.elements.PropertyEditorElement;
import javafx.scene.control.TreeView;

public class PreAndPostPropertyEditor {
	private PropertyEditorElement preEditor;
	private PropertyEditorElement postEditor;
	private TreeView<String> variableTreeView;
	private PreAndPostConditionsDescription currentPropDescr;
	
	public PreAndPostPropertyEditor(PropertyEditorElement preEditor,
			PropertyEditorElement postEditor,
			TreeView<String> variableTreeView) {
		this.preEditor = preEditor;
		this.postEditor = postEditor;
		this.variableTreeView = variableTreeView;
	}

	public void loadProperty(PreAndPostConditionsDescription propDescr) {
		preEditor.clear();
		preEditor.insertText(0,
				propDescr.getPreConditionsDescription().getCode());
		postEditor.clear();
		postEditor.insertText(0,
				propDescr.getPostConditionsDescription().getCode());
		this.currentPropDescr = propDescr;
	}

}
