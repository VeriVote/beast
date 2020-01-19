package edu.pse.beast.types.cbmctypes;

import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.types.OutputType;

/**
 * The Class CBMCOutputType.
 *
 * @author Lukas Stapelbroek
 */
public abstract class CBMCOutputType extends OutputType {

    /**
     * The constructor.
     *
     * @param unsigned
     *            the unsigned
     * @param dataType
     *            the data type
     * @param dimensions
     *            the dimensions
     * @param sizeOfDimensions
     *            the size of dimensions
     */
    public CBMCOutputType(final boolean unsigned, final DataType dataType,
                          final int dimensions,
                          final String[] sizeOfDimensions) {
        super(unsigned, dataType, dimensions, sizeOfDimensions);
    }

    @Override
    public final CodeArrayListBeautifier addMarginMainTest(final CodeArrayListBeautifier code,
                                                           final int voteNumber) {
        code.add("int main() {");
        code.addTab();
        String definition = getContainer().getOutputStruct().getStructAccess()
                + " " + UnifiedNameContainer.getElect() + "1 = "
                + UnifiedNameContainer.getVotingMethod() + "( ORIG_VOTES_SIZE, "
                + UnifiedNameContainer.getOrigVotesName() + ");";
        code.add(definition);
        // add an assertion that never holds to be able to extract the data
        code.add("assert(0);");
        code.deleteTab();
        code.add("}");
        return code;
    }
}
