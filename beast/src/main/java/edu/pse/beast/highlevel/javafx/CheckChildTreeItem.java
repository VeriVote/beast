package edu.pse.beast.highlevel.javafx;

import edu.pse.beast.propertychecker.Result;

public class CheckChildTreeItem extends ChildTreeItem {

	CheckChildTreeItem(ChildTreeItemValues values, ParentTreeItem parent) {
		super(values, parent);
	}

	CheckChildTreeItem(String name, ParentTreeItem parent) {
		super(name, parent);
	}

	@Override
	public void resetResult(Result result) {
		super.addResult(result);
	}

	@Override
	public AnalysisType getAnalysisType() {
		return AnalysisType.Check;
	}

}
