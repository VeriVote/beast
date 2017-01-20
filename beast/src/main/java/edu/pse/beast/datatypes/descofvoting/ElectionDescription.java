package edu.pse.beast.datatypes.descofvoting;

/**
 * 
 * @author Lukas
 *
 */
public class ElectionDescription {
    private String name;
    private String code;
    private ElectionTypeContainer inputType;
    private ElectionTypeContainer outputType;
    private int votingDeclLine;
    
    /**
     * 
     * @param name the name of the description
     * @param inputType the input type
     * @param outputType the output type
     * @param votingDeclLine the votingDeclerationLine
     */
    public ElectionDescription(String name, ElectionTypeContainer inputType,
            ElectionTypeContainer outputType, int votingDeclLine) {
        this.name = name;
        this.outputType = outputType;
        this.inputType = inputType;
        this.votingDeclLine = votingDeclLine;
    }
    
    /**
     * 
     * @return code of this description;
     */
    public String getCode() {
        return code;
    }
    
    /**
     * 
     * @return the name of the Description
     */
    public String getName() {
        return name;
    }
    
    /**
     * 
     * @return the votingDescriptionLine of this description
     */
    public int getVotingDeclLine() {
        return votingDeclLine;
    }
    
    /**
     * 
     * @return the outputType of this description
     */
    public ElectionTypeContainer getOutputType() {
        return outputType;
    }
    
    /**
     * 
     * @return the inputType of this description
     */
    public ElectionTypeContainer getInputType() {
        return inputType;
    }
    
    /**
     * 
     * @param code of this description
     */
    public void setCode(String code) {
        this.code = code;
    }
    
    /**
     * 
     * @param name of this description
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 
     * @param votingDeclLine of this description
     */
    public void setVotingDeclLine(int votingDeclLine) {
        this.votingDeclLine = votingDeclLine;
    }
    
    /**
     * 
     * @param outputType of this description
     */
    public void setOutputType(ElectionTypeContainer outputType) {
        this.outputType = outputType;
    }
    
    /**
     * 
     * @param inputType of this description
     */
    public void setInputType(ElectionTypeContainer inputType) {
        this.inputType = inputType;
    }
}
