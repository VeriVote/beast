package edu.pse.beast.types.cbmctypes;

import static edu.pse.beast.toolbox.CCodeHelper.functionCode;
import static edu.pse.beast.toolbox.CCodeHelper.one;
import static edu.pse.beast.toolbox.CCodeHelper.space;
import static edu.pse.beast.toolbox.CCodeHelper.varAssignCode;
import static edu.pse.beast.toolbox.CCodeHelper.zero;

import edu.pse.beast.toolbox.CCodeHelper;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.types.OutputType;

/**
 * The Class CBMCOutputType.
 *
 * @author Lukas Stapelbroek
 */
public abstract class CBMCOutputType extends OutputType {
    /** The Constant VOTES. */
    protected static final String VOTES = "votes";
    /** The Constant ELECT. */
    protected static final String ELECT = "elect";
    /** The Constant ORIG_VOTES_SIZE. */
    private static final String ORIG_VOTES_SIZE = "ORIG_VOTES_SIZE";

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
        code.add(CCodeHelper.INT + space()
                + functionCode("main") + space()
                + CCodeHelper.OPENING_BRACES);
        code.addTab();
        String definition =
                getContainer().getOutputStruct().getStructAccess()
                + varAssignCode(UnifiedNameContainer.getElect() + one(),
                                functionCode(UnifiedNameContainer.getVotingMethod(),
                                             ORIG_VOTES_SIZE,
                                             UnifiedNameContainer.getOrigVotesName()))
                + CCodeHelper.SEMICOLON;
        code.add(definition);
        // Add an assertion that never holds to be able to extract the data.
        code.add(functionCode("assert", zero()) + CCodeHelper.SEMICOLON);
        code.deleteTab();
        code.add(CCodeHelper.CLOSING_BRACES);
        return code;
    }
}
