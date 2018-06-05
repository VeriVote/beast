package edu.pse.beast.highlevel.javafx;

import edu.pse.beast.propertychecker.Result;

public class TestChildTreeItem extends ChildTreeItem {

	TestChildTreeItem(String name, ParentTreeItem parent) {
		super(name, parent);
	}

	public TestChildTreeItem(ChildTreeItemValues values, ParentTreeItem parent) {
		super(values, parent);
	}

	@Override
	public void resetResult(Result result) {
		super.setResult(result);
	}

	@Override
	public AnalysisType getAnalysisType() {
		return AnalysisType.Test;
	}
	
}