package edu.pse.beast.types.cbmctypes;

import java.util.Iterator;
import java.util.List;

import edu.pse.beast.electionSimulator.NewElectionSimulation;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.types.InputType;

public abstract class CBMCInputType extends InputType {

	@Override
	protected void getHelper() {
		super.helper = new CbmcHelpMethods();
	}
	
	@Override
	public void addCheckerSpecificHeaders(CodeArrayListBeautifier code) {
		// add the headers CBMC needs;
		code.add("#include <stdlib.h>");
		code.add("#include <stdint.h>");
		code.add("#include <assert.h>");
		code.add("");
		code.add("unsigned int nondet_uint();");
		code.add("int nondet_int();");
		code.add("");
		code.add("#define assert2(x, y) __CPROVER_assert(x, y)");
		code.add("#define assume(x) __CPROVER_assume(x)");
		code.add("");
	}
	
	public String getVoteDescriptionString(List<List<String>> origVotes) {
		
		String votesString = "";
		
		int voterIndex = 0;
		
		for (Iterator<List<String>> iterator = origVotes.iterator(); iterator.hasNext();) { //iterate over the voters
			List<String> list = (List<String>) iterator.next();
			String oneVoter = "";
			
			try {
				oneVoter = GUIController.getController().getElectionSimulation().getVoterName(voterIndex);
			} catch (Exception e) {
				oneVoter = "" + voterIndex;
			}
			
			oneVoter = oneVoter + ": ";
			
			voterIndex++;
			
			int partyIndex = 0;
			
			for (Iterator<String> iterator2 = list.iterator(); iterator2.hasNext();) { //iterate over the candidates
				String voteAmount = (String) iterator2.next();
				
				
				try {
					oneVoter = oneVoter + GUIController.getController().getElectionSimulation().getPartyName(partyIndex);
				} catch (Exception e) {
					oneVoter = "" + partyIndex;
				}
				
				oneVoter = oneVoter + ": " + voteAmount + ", ";
				
				partyIndex++;
				
			}
			
			votesString = votesString + oneVoter + "\n";
		}
		
		return votesString;
	}
}
