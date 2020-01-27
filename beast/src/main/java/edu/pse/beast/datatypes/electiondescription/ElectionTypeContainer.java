package edu.pse.beast.datatypes.electiondescription;

import static edu.pse.beast.toolbox.CCodeHelper.lineBreak;

import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.types.ComplexType;
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.OutputType;
import edu.pse.beast.types.cbmctypes.cbmcstructs.CBMCStruct;

/**
 * Datatype for the Input and Output of an Election.
 *
 * <p>TODO make this class abstract, move code
 *    to "CBMCElectionTypeContainer"
 *
 * @author Lukas Stapelbroek
 *
 */
public class ElectionTypeContainer {
    /** The in type. */
    private final InputType inType;

    /** The out type. */
    private final OutputType outType;

    /** The input struct. */
    private CBMCStruct inputStruct;

    /** The output struct. */
    private CBMCStruct outputStruct;

    /** The name container. */
    private UnifiedNameContainer nameContainer = new UnifiedNameContainer();

    /**
     * Constructor.
     *
     * @param inputType
     *            the inType of this election
     * @param outputType
     *            the outType of this election
     */
    public ElectionTypeContainer(final InputType inputType,
                                 final OutputType outputType) {
        this.inType = inputType;
        this.inType.setElectionTypeContainer(this);
        this.outType = outputType;
        this.outType.setElectionTypeContainer(this);
        generateStructs();
    }

    /**
     * Generate structs.
     */
    private void generateStructs() {
        this.inputStruct = new CBMCStruct(inType);
        this.outputStruct = new CBMCStruct(outType);
        if (this.inputStruct.equals(this.outputStruct)) {
            // They have the same shape.
            this.outputStruct = inputStruct;
        }
        inType.setStruct(inputStruct);
        outType.setStruct(outputStruct);

    }

    /**
     * Gets the input type.
     *
     * @return the id of this input typecontainer
     */
    public InputType getInputType() {
        return inType;
    }

    /**
     * Gets the output type.
     *
     * @return the id of this output typecontainer
     */
    public OutputType getOutputType() {
        return outType;
    }

    /**
     * Gets the name container.
     *
     * @return the name container
     */
    public UnifiedNameContainer getNameContainer() {
        if (this.nameContainer == null) {
            this.nameContainer = new UnifiedNameContainer();
        }
        return nameContainer;
    }

    /**
     * Gets the input struct.
     *
     * @return the input struct
     */
    public ComplexType getInputStruct() {
        return inputStruct;
    }

    /**
     * Gets the output struct.
     *
     * @return the output struct
     */
    public ComplexType getOutputStruct() {
        return outputStruct;
    }

    /**
     * Gets the struct definitions.
     *
     * @return the struct definitions
     */
    public String getStructDefinitions() {
        String toReturn = "";
        toReturn = lineBreak(inputStruct.getStructDefinition());
        if (!inputStruct.equals(outputStruct)) {
            toReturn += lineBreak(outputStruct.getStructDefinition());
        }
        return toReturn;
    }
}
