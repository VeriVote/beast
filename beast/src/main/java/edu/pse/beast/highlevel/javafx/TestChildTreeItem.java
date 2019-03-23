package edu.pse.beast.highlevel.javafx;

import edu.pse.beast.propertychecker.Result;
import javafx.scene.control.TreeItem;

public class TestChildTreeItem extends ChildTreeItem {

    TestChildTreeItem(String name, ParentTreeItem parent, TreeItem<CustomTreeItem> treeItemReference) {
        super(name, parent, treeItemReference);
    }

    public TestChildTreeItem(ChildTreeItemValues values, ParentTreeItem parent,
            TreeItem<CustomTreeItem> treeItemReference) {
        super(values, parent, treeItemReference);
    }

    @Override
    public void resetResult(Result result) {
        super.addResult(result);
    }

    @Override
    public AnalysisType getAnalysisType() {
        return AnalysisType.Test;
    }

}