package edu.pse.beast.gui;

import org.fxmisc.richtext.CodeArea;

import edu.pse.beast.api.electiondescription.CElectionDescription;

public class CElectionEditor extends CodeArea {
	private CElectionDescription descr;

	public void loadDescription(CElectionDescription descr) {
		this.descr = descr;
	}
}
