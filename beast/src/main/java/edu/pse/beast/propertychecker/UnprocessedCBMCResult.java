package edu.pse.beast.propertychecker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.pse.beast.datatypes.FailureExample;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.highlevel.ResultPresenterElement;
import edu.pse.beast.toolbox.ErrorForUserDisplayer;
import edu.pse.beast.toolbox.ErrorLogger;

public class UnprocessedCBMCResult extends CBMCResult {
	
	@Override
	public void presentTo(ResultPresenterElement presenter) {
		// we dont have to present this result, because it will get used
		//internally
		ErrorLogger.log("There was an attempt to present an UnprocessedResult. This should + "
				+ "never happen");
	}
	
	public CBMCResultWrapperMultiArray getNewVotes() {
		// determine the elect values
				if (getResult() != null && getElectionDescription() != null) {

					//this list saves which values the voters will have
					List<CBMCResultWrapperMultiArray> new_votes_multi = null;
					
					List<CBMCResultWrapperSingleArray> new_votes_single = null;
					
					boolean isSingle = false;

					// it is voting for seats, and not for candidates
					if (!getElectionDescription().getOutputType().getResultTypeSeats()) {

						switch (getElectionDescription().getInputType().getInputID()) {

						// get the fitting type and extract the values out of it,
						// because we
						// know the format of the values
						// for each specific type
						case APPROVAL:

							new_votes_multi = readTwoDimVar("new_votes", getResult());

							break;
						case PREFERENCE:

							new_votes_multi = readTwoDimVar("new_votes", getResult());

							break;
						case SINGLE_CHOICE:

							new_votes_single = readOneDimVar("new_votes", getResult());

							isSingle = true;
							
							break;
						case WEIGHTED_APPROVAL:

							new_votes_multi = readTwoDimVar("new_votes", getResult());

							break;
						default:
							ErrorForUserDisplayer
									.displayError("This votingtype you are using hasn't been implemented yet to be displayed. "
											+ "Please do so in the class CBMC_Result");
							this.setError("This votingtype hasn't been implemented yet please do so in the class CBMC_Result");
							return null;
						}
					}

					if(isSingle) {
						List<CBMCResultWrapperMultiArray> toReturn = new ArrayList<CBMCResultWrapperMultiArray>();
						
						for (Iterator<CBMCResultWrapperSingleArray> iterator = new_votes_single.iterator(); iterator.hasNext();) {
							CBMCResultWrapperSingleArray cbmcResultWrapperSingleArray = (CBMCResultWrapperSingleArray) iterator
									.next();
							toReturn.add(cbmcResultWrapperSingleArray.wrapInTwoDim(1, "new_votes", this.getNumCandidates()));
						}
						
						return toReturn.get(0);
					} else {
						return new_votes_multi.get(0);
					}
					
					
				} else {
					this.setError(
							"No input could be read from the Checker, please make sure that it is there and working properly");
					return null;
				}
		
		
	}
	
}
