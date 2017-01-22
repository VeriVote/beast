/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.datatypes.propertydescription.electiondescription;

import java.util.List;

/**
 * Datatype for the Description of an Election. Here it is in C-Code
 *
 * @author Niels
 */
public class ElectionDescription {

    private String name;
    private List code;
    private int votingDeclLine;
    private ElectionTypeContainer inputType;
    private ElectionTypeContainer outputType;

    /**
     *
     * @param name name 
     * @param code code
     * @param inputType Type of Input 
     * @param outputType Typue of Output
     * @param votingDeclLine Line of the voting Method
     */
    public ElectionDescription(String name, List code, ElectionTypeContainer inputType, 
            ElectionTypeContainer outputType, int votingDeclLine) {
        this.code = code;
        this.name = name;
        this.inputType = inputType;
        this.outputType = outputType;
        this.votingDeclLine = votingDeclLine;
    }

    /**
     *
     * @return List of Code
     */
    public List getCode() {
        return code;
    }

    /**
     *
     * @param code 
     */
    public void setCode(List code) {
        this.code = code;
    }

    /**
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return line of the voting Method declaration
     */
    public int getVotingDeclLine() {
        return votingDeclLine;
    }

    /**
     *
     * @param votingDeclLine line of the voting Method declaration
     */
    public void setVotingDeclLine(int votingDeclLine) {
        this.votingDeclLine = votingDeclLine;
    }

    /**
     *
     * @return input Type
     */
    public ElectionTypeContainer getInputType() {
        return inputType;
    }

    /**
     *
     * @param inputType 
     */
    public void setInputType(ElectionTypeContainer inputType) {
        this.inputType = inputType;
    }

    /**
     *
     * @return output Type
     */
    public ElectionTypeContainer getOutputType() {
        return outputType;
    }

    /**
     *
     * @param outputType 
     */
    public void setOutputType(ElectionTypeContainer outputType) {
        this.outputType = outputType;
    }

}
