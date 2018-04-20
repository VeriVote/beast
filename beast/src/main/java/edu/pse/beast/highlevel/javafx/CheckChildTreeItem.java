package edu.pse.beast.highlevel.javafx;

import edu.pse.beast.propertychecker.Result;

public class CheckChildTreeItem extends ChildTreeItem {

	CheckChildTreeItem(String name, ParentTreeItem parent) {
		super(name, parent);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void resetResult(Result result) {
		super.setResult(result);
	}

	@Override
	public AnalysisType getAnalysisType() {
		return AnalysisType.Check;
	}

}
