package edu.pse.beast.highlevel.javafx;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.pse.beast.codearea.errorhandling.CodeError;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.propertychecker.Result;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TreeItem;

public class ParentTreeItem extends CustomTreeItem {

	private Label propName;
	private CheckBox checkAll = new CheckBox("check all");
	
	private final List<ChildTreeItem> subItems = new ArrayList<ChildTreeItem>();
	private final List<TreeItem<CustomTreeItem>> childTreeItems = new ArrayList<TreeItem<CustomTreeItem>>();
	private final PreAndPostConditionsDescription propDesc;

	private boolean disabled;
	private boolean initialized = false;

	private ChildTreeItem checkItem;
	private ChildTreeItem marginItem;
	private ChildTreeItem testItem;
	private int counter;

	ParentTreeItem(PreAndPostConditionsDescription propDesc, boolean isSelected,
			TreeItem<CustomTreeItem> treeItemReference, boolean createChildren) {

		this.setTreeItemReference(treeItemReference);
		
		this.propDesc = propDesc;

		this.setAlignment(Pos.CENTER_LEFT);

		propName = new Label(propDesc.getName());

		this.getChildren().add(propName);
		this.getChildren().add(new Separator(Orientation.VERTICAL));
		this.getChildren().add(checkAll);
	
		checkItem = new CheckChildTreeItem("Verification ", this, new TreeItem<CustomTreeItem>());
		marginItem = new MarginChildTreeItem("Margin", this, new TreeItem<CustomTreeItem>());
		testItem = new TestChildTreeItem("Test", this, new TreeItem<CustomTreeItem>());

		if (createChildren) {
			subItems.add(checkItem);
			subItems.add(marginItem);
			subItems.add(testItem);

			addChildrenToStage();
		} else {
			subItems.add(null);
			subItems.add(null);
			subItems.add(null);
		}

		checkAll.selectedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {
				checkBoxChanged(newValue);
			}
		});

		this.setSelected(isSelected);

		this.getTreeItemReference().setValue(this);
	}

	public void addChildrenToStage() {
		for (Iterator<ChildTreeItem> iterator = subItems.iterator(); iterator.hasNext();) {
			CustomTreeItem item = (CustomTreeItem) iterator.next();
			//TreeItem<CustomTreeItem> reference = new TreeItem<CustomTreeItem>(item);
			
			
			
			childTreeItems.add(item.getTreeItemReference());
		}

		this.getTreeItemReference().getChildren().addAll(childTreeItems);

		initialized = true;
	}

	public void wasClicked(boolean bringToFront) {
		GUIController.getController().setCurrentPropertyDescription(this, bringToFront);
	}

	private void checkBoxChanged(boolean state) {
		if (initialized) {
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
		checkItem.addResult(result);
	}

	public void setMarginResult(Result result) {
		marginItem.addResult(result);
	}

	public void setCheckStatus(AnalysisStatus status) {
		//checkItem.setStatus(status);
	}

	public void setMarginStatus(AnalysisStatus status) {
		//marginItem.setStatus(status);
	}

	public PreAndPostConditionsDescription getPreAndPostProperties() {
		return propDesc;
	}

	/**
	 * notifies the parent, that at least one of the children's result changed, so we
	 * have have to check all to update the GUI
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
		String errorText = "";

		for (Iterator<CodeError> iterator = combinedErrors.iterator(); iterator.hasNext();) {
			CodeError codeError = (CodeError) iterator.next();
			String error = codeError.getLine() + " : " + codeError.getMsg() + "\n";

			errorText = errorText + error;
		}

		GUIController.setErrorText(errorText);

	}

	public void setText(String text) {
		propName.setText(text);
	}

	public String getText() {
		return propName.getText();
	}

	public void addChild(ChildTreeItemValues values, int index) {

		ChildTreeItem child = null;
		
		switch (index) {
		case 0:
			child = new CheckChildTreeItem(values, this, new TreeItem<CustomTreeItem>());
			break;
		case 1:
			child = new MarginChildTreeItem(values, this, new TreeItem<CustomTreeItem>());
			break;
		case 2:
			child = new TestChildTreeItem(values, this, new TreeItem<CustomTreeItem>());
			break;
		default:
			counter--;
			break;
		}

		subItems.set(index, child);
		
		counter++;
		
		if(counter == 3) {
			addChildrenToStage();
		}
	}
	
	public int getCounter() {
		return counter;
	}

}