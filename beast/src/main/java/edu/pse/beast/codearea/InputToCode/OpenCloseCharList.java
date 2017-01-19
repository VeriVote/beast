/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.beast.codearea.InputToCode;

import java.util.ArrayList;

/**
 *
 * @author Holger-Desktop
 */
public class OpenCloseCharList {
    private ArrayList<OpenCloseChar> openCloseChars = new ArrayList<OpenCloseChar>();
    private Character[] openChars = {'{', '[', '(', '"'};
    private Character[] closeChars = {'}', ']', ')', '"'};
    
    public OpenCloseCharList() {
        initializeOpenCloseChars();
    }
    
    public boolean isOpenChar(char c) {
        for(int i = 0; i < openChars.length; ++i) {
            if(openCloseChars.get(i).getOpen() == c) return true;
        }
        return false;
    }

    private void initializeOpenCloseChars() {
        for(int i = 0; i < openChars.length; ++i) {
            openCloseChars.add(new OpenCloseChar(openChars[i], closeChars[i]));
        }
    }

}
