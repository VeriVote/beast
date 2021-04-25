package edu.pse.beast.gui.ceditor;

import org.fxmisc.richtext.CodeArea;

import edu.pse.beast.gui.CodeAreaChangeListener;

public class CEditorCodeElement extends CodeArea {

	String textOnFocus;
	
	private CodeAreaChangeListener changeListener;
	
	public CEditorCodeElement() {		
		setOnKeyTyped(e -> {
			changeListener.codeChanged(getText());
		});
	}

	public void setChangeListener(CodeAreaChangeListener changeListener) {
		this.changeListener = changeListener;
	}
	
	
}
