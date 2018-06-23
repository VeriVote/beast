package edu.pse.beast.highlevel.javafx;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;

public abstract class CustomTreeItem extends HBox {

	private TreeItem<CustomTreeItem> reference;
	
	public void setTreeItemReference(TreeItem<CustomTreeItem> reference) {
		this.reference = reference;
	}
	
	public TreeItem<CustomTreeItem> getTreeItemReference() {
		return reference;
	}

}