/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.beast.codearea.InputToCode;

import javax.swing.JTextPane;

/**
 *
 * @author Holger-Desktop
 */
public class OpenCloseChar {
    private final char open;
    private final char close;
    
    public OpenCloseChar(char open, char close) {
        this.open = open;
        this.close = close;
    }
    
    public char getOpen() {
        return this.open;
    }
    
    public char getClose() {
        return this.close;
    }
}
