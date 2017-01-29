/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.InputToCode;

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
        for(int i = 0; i < openCloseChars.size(); ++i) {
            if(openCloseChars.get(i).getOpen() == c) return true;
        }
        return false;
    }
    
    public OpenCloseChar getOpenCloseChar(char c) {
        for(int i = 0; i < openCloseChars.size(); ++i) {
            if(openCloseChars.get(i).getOpen() == c || openCloseChars.get(i).getClose() == c) return openCloseChars.get(i);
        }
        return null;
    }
        
    private void initializeOpenCloseChars() {
        for(int i = 0; i < openChars.length; ++i) {
            openCloseChars.add(new OpenCloseChar(openChars[i], closeChars[i]));
        }
    }    

    public boolean isCloseChar(char c) {
        for(int i = 0; i < openCloseChars.size(); ++i) {
            if(openCloseChars.get(i).getClose()== c) return true;
        }
        return false;
    }
    
    
    

}
