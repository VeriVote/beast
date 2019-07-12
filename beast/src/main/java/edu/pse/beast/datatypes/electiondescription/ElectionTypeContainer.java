package edu.pse.beast.datatypes.electiondescription;

import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.types.ComplexType;
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.OutputType;
import edu.pse.beast.types.cbmctypes.cbmcstructs.CBMCStruct;

/**
 * Datatype for the Input and Output of an Election
 *
 * @author Lukas Stapelbroek
 *
 */
public class ElectionTypeContainer { //TODO make this class abstract, move code to "CBMCElectionTypeContainer"
    private final InputType inType;
    private final OutputType outType;
    
    private CBMCStruct inputStruct;
    private CBMCStruct outputStruct;
    
    private UnifiedNameContainer nameContainer = new UnifiedNameContainer();

    /**
     * Constructor
     *
     * @param inType the inType of this election
     * @param outType the outType of this election
     */
    public ElectionTypeContainer(InputType inType, OutputType outType) {
        this.inType = inType;
        
        this.inType.setElectionTypeContainer(this);
        
        this.outType = outType;
        
        this.outType.setElectionTypeContainer(this);
        
        generateStructs();
    }
    
    private void generateStructs() {
    	this.inputStruct = new CBMCStruct(inType);
    	this.outputStruct = new CBMCStruct(outType);
    	
    	if (this.inputStruct.equals(this.outputStruct)) { //they have the same shape
    		this.outputStruct = inputStruct;
    	}
    	
    	inType.setStruct(inputStruct);
    	outType.setStruct(outputStruct);
   	
    }

    /**
     *
     * @return the id of this input typecontainer
     */
    public InputType getInputType() {
        return inType;
    }

    /**
     *
     * @return the id of this output typecontainer
     */
    public OutputType getOutputType() {
        return outType;
    }

	public UnifiedNameContainer getNameContainer(){
		return nameContainer;
	}
	
	public ComplexType getInputStruct() {
		return inputStruct;
	}
	
	public ComplexType getOutputStruct() {
		return outputStruct;
	}
	
	public String getStructDefinitions() {
		String toReturn = "";
		
		toReturn = inputStruct.getStructDefinition(nameContainer) + "\n";
		
		if (!inputStruct.equals(outputStruct)) {
			toReturn = toReturn + outputStruct.getStructDefinition(nameContainer) + "\n";
		}
		
		return toReturn;
	}
}