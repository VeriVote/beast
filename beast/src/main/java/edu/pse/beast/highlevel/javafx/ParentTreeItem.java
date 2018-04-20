package edu.pse.beast.highlevel.javafx;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder.BooleanExpEditorGeneralErrorFinder;
import edu.pse.beast.codearea.ErrorHandling.CodeError;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.propertychecker.Result;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;

public class ParentTreeItem extends CustomTreeItem {

	private Label propName;
	private CheckBox checkAll = new CheckBox("check all");
	private final List<ChildTreeItem> subItems = new ArrayList<ChildTreeItem>();
	private final List<TreeItem<CustomTreeItem>> childTreeItems = new ArrayList<TreeItem<CustomTreeItem>>();
	private final PreAndPostConditionsDescription propDesc;

	private final TreeItem<CustomTreeItem> treeItemReference;
	private boolean disabled;

	private ChildTreeItem checkItem;
	private ChildTreeItem marginItem;
	private ChildTreeItem testItem;

	ParentTreeItem(PreAndPostConditionsDescription propDesc, boolean isSelected,
			TreeItem<CustomTreeItem> treeItemReference) {

		this.treeItemReference = treeItemReference;
		this.propDesc = propDesc;

		this.setAlignment(Pos.CENTER_LEFT);

		propName = new Label(propDesc.getName());

		this.getChildren().add(propName);
		this.getChildren().add(new Separator(Orientation.VERTICAL));
		this.getChildren().add(checkAll);

		checkItem = new CheckChildTreeItem("Check ", this);
		marginItem = new MarginChildTreeItem("Margin", this);
		testItem = new TestChildTreeItem("Test", this);

		subItems.add(checkItem);
		subItems.add(marginItem);
		subItems.add(testItem);

		checkAll.selectedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {
				checkBoxChanged(newValue);
			}
		});

		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				wasClicked();
			}
		});

		this.setSelected(isSelected);

		this.treeItemReference.setValue(this);

		for (Iterator<ChildTreeItem> iterator = subItems.iterator(); iterator.hasNext();) {
			CustomTreeItem item = (CustomTreeItem) iterator.next();
			childTreeItems.add(new TreeItem<CustomTreeItem>(item));
		}

		this.treeItemReference.getChildren().addAll(childTreeItems);

	}

	private void wasClicked() {
		GUIController.getController().getPreConditionsArea().setPreDescription(propDesc.getPreConditionsDescription());
		GUIController.getController().getPostConditionsArea()
				.setPostDescription(propDesc.getPostConditionsDescription());
		
		GUIController.getController().getMainTabPane().getSelectionModel().select(GUIController.getController().getPropertyTab());
		
		BooleanExpEditorGeneralErrorFinder.hasErrors(this); //checks if there are errors, and if there are
		//displays them in the error tab
		
	}

	private void checkBoxChanged(boolean state) {
		for (Iterator<ChildTreeItem> iterator = subItems.iterator(); iterator.hasNext();) {
			ChildTreeItem childTreeItem = (ChildTreeItem) iterator.next();
			if (!disabled) {
				childTreeItem.setSelected(state);
			}
			if (state) {
				checkAll.setText("uncheck all");
			} else {
				checkAll.setText("check all");
			}
		}
	}

	public boolean isSelected() {
		return checkAll.isSelected();
	}

	public void setSelected(boolean state) {
		checkAll.setSelected(state);
		checkBoxChanged(state);
	}

	public List<ChildTreeItem> getSubItems() {
		return subItems;
	}

	public void childCheckboxChanged() {
		boolean acc = true;

		for (Iterator<ChildTreeItem> iterator = subItems.iterator(); iterator.hasNext();) {
			ChildTreeItem item = (ChildTreeItem) iterator.next();
			acc = acc && item.isSelected();
		}

		if (acc) {
			this.setSelected(acc);
		} else {
			this.disabled = true;
			this.setSelected(false);
			this.disabled = false;
		}
	}

	public ChildTreeItem getCheckItem() {
		return checkItem;
	}

	public ChildTreeItem getMarginItem() {
		return marginItem;
	}

	public void setCheckResult(Result result) {
		checkItem.setResult(result);
	}

	public void setMarginResult(Result result) {
		marginItem.setResult(result);
	}

	public void setCheckStatus(AnalysisStatus status) {
		checkItem.setStatus(status);
	}

	public void setMarginStatus(AnalysisStatus status) {
		marginItem.setStatus(status);
	}
	
	public PreAndPostConditionsDescription getPreAndPostPropertie() {
		return propDesc;
	}

	/**
	 * notifies the parent, that at least one of the childrens 
	 * result changed, so we have have to check all to update 
	 * the GUI
	 */
	public void update() {
		for (Iterator<ChildTreeItem> iterator = subItems.iterator(); iterator.hasNext();) {
			ChildTreeItem child = (ChildTreeItem) iterator.next();
			child.update();
		}
	}
	
	/**
	 * 
	 * @return true, if at least one of the children has to be checked
	 */
	public boolean isChildSelected() {
		boolean selected = false;
		
		for (Iterator<ChildTreeItem> iterator = subItems.iterator(); iterator.hasNext();) {
			ChildTreeItem childItem = (ChildTreeItem) iterator.next();
			selected = selected || childItem.isSelected();
		}
		
		return selected;
	}

	public void addErrors(List<CodeError> combinedErrors) {
		String errorText= "";
		
		
		for (Iterator<CodeError> iterator = combinedErrors.iterator(); iterator.hasNext();) {
			CodeError codeError = (CodeError) iterator.next();
			String error = codeError.getLine() + " : " + codeError.getMsg() + "\n";
			
			errorText = errorText + error;
		}
		
		GUIController.setErrorText(errorText);
		
	}

}