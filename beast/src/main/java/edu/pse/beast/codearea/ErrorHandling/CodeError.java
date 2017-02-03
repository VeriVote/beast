/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.ErrorHandling;

import java.util.HashMap;

/**
 *
 * @author Holger-Desktop
 */
public class CodeError {
    private int line;
    private int posInLine;
    private String id;
    private int errorNumber;
    private int startPos;
    private int endPos;
    private HashMap<String, String> extraInfo = new HashMap<>();
    
    public CodeError(int line, int posInLine, String id, int errorNumber, int startPos, int endPos) {
        this.line = line;
        this.posInLine = posInLine;
        this.id = id;
        this.errorNumber = errorNumber;
        this.startPos = startPos;
        this.endPos = endPos;
    }
    
    public void setExtraInfo(String id, String extra) {
        extraInfo.put(id, extra);
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

    public String getMsg() {
        // temporary
        return "";
    }
    
    
}
