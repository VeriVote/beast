package edu.pse.beast.codeareaJAVAFX;

import java.util.List;

import org.fxmisc.richtext.CodeArea;

import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.toolbox.Tuple;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public abstract class AutoCompletionCodeArea extends CodeArea {
	
	private int start;
	private int end;
	
	public AutoCompletionCodeArea() {
		this.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> ov, Boolean onHidden, Boolean onShown) {
				if (onShown) {
					GUIController.getController().getAutoCompleter().reset();
				}
			}
		});
	}
	
	private Tuple<Integer, Integer> getAbsolutCaretPosition() {
		return new Tuple<Integer, Integer>((int) this.getCaretBounds().get().getMaxX() + 4,
				(int) this.getCaretBounds().get().getMaxY() + 4);
	}

	protected void processAutocompletion(List<String> content, Integer start, Integer end) {
		Tuple<Integer, Integer> position = getAbsolutCaretPosition();

		this.start = start;
		this.end = end;
		
		GUIController.getController().getAutoCompleter().showAutocompletionWindows(position.x, position.y, content,
				this);
	}

	
	public void insertAutoCompletion(String toInsert) {
		replaceText(start, end, toInsert);
	}
}
