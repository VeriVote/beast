/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.ErrorHandling;

/**
 *
 * @author Holger-Desktop
 */
public class CodeError {
    private int line;
    private int posInLine;
    private String id;
    private int errorNumber;
    
    public CodeError(int line, int posInLine, String id, int errorNumber) {
        this.line = line;
        this.posInLine = posInLine;
        this.id = id;
        this.errorNumber = errorNumber;
    }

    public String getId() {
        return id;
    }

    public int getErrorNumber() {
        return errorNumber;
    }

    public int getLine() {
        return line;
    }

    public int getPosInLine() {
        return posInLine;
    }
    
    
}
