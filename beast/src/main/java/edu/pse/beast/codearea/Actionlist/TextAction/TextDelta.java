/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.Actionlist.TextAction;

/**
 *
 * @author Holger-Desktop
 */
public class TextDelta {
    private int offset;
    private String text;
    
    public TextDelta(int offset, String text) {
        this.offset = offset;
        this.text = text;
    }
    
    public int getOffset() {
        return offset;
    }
    
    public String getText() {
        return text;
    }
    
}
