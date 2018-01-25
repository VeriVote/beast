package edu.pse.beast.types.cbmctypes.outputtypes;

import java.util.List;

import edu.pse.beast.propertychecker.CBMCResultWrapperLong;
import edu.pse.beast.propertychecker.CBMCResultWrapperSingleArray;
import edu.pse.beast.types.cbmctypes.CBMCOutputType;

public class SingleCandidate extends CBMCOutputType{

	@Override
	public String getOutputString() {
		return "unsigned int";
	}

	@Override
	public String getOutputIDinFile() {
		return "CAND_OR_UNDEF";
	}

	@Override
	public boolean isOutputOneCandidate() {
		return true;
	}

	@Override
	public Long[] extractResult(List<String> toExtract) {
		Long[] tmpArray = { super.helper.readLongs("new_result", toExtract).get(0).getValue() };
		return tmpArray;
	}

	@Override
	public List<CBMCResultWrapperSingleArray> readSeatList(List<String> toExtract) {
		return null;
	}

	@Override
	public List<CBMCResultWrapperLong> readElect(List<String> toExtract) {
		return super.helper.readLongs("elect", toExtract);
	}

}
