package edu.pse.beast.propertychecker;

public class UnprocessedCBMCResult extends CBMCResult {

    public UnprocessedCBMCResult() {
        super(false);
    }
//
//	@Override
//	public void presentTo(ResultPresenterElement presenter) {
//		// we dont have to present this result, because it will get used
//		// internally
//		ErrorLogger.log("There was an attempt to present an UnprocessedResult. This should + " + "never happen");
//	}

    // returns the new Votes as a CBMCWrappedMultiArray
    public CBMCResultWrapperMultiArray getNewVotesWrappedMulti() {
        // determine the elect values
        if (getResult() != null && getElectionDescription() != null) {

            return getElectionDescription().getContainer().getInputType().extractVotesWrappedMulti(getResult(),
                    this.getNumCandidates());

        } else {

            this.setError(
                    "No input could be read from the Checker, please make sure that it is there and working properly");
            return null;
        }
    }

    public String[] getNewResult() {
        // determine the elect values
        if (getResult() != null && getElectionDescription() != null) {

            return getElectionDescription().getContainer().getOutputType().extractResult(getResult());

        } else {
            this.setError(
                    "No input could be read from the Checker, please make sure that it is there and working properly");
            return null;
        }
    }
}
