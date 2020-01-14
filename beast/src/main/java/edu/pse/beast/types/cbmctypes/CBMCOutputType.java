package edu.pse.beast.types.cbmctypes;

import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.types.OutputType;

public abstract class CBMCOutputType extends OutputType {
    public CBMCOutputType(boolean unsigned, DataType dataType, int dimensions,
                          String[] sizeOfDimensions) {
        super(unsigned, dataType, dimensions, sizeOfDimensions);
    }

    @Override
    public CodeArrayListBeautifier addMarginMainTest(CodeArrayListBeautifier code,
                                                     int voteNumber) {
        code.add("int main() {");
        code.addTab();
        String definition = getContainer().getOutputStruct().getStructAccess()
                + " " + UnifiedNameContainer.getElect() + "1 = "
                + UnifiedNameContainer.getVotingMethod()
                + "( ORIG_VOTES_SIZE, "
                + UnifiedNameContainer.getOrigVotesName() + ");";
        code.add(definition);
        code.add("assert(0);"); // add an assertion that never holds to be able
                                // to extract the data
        code.deleteTab();
        code.add("}");
        return code;
    }
}
