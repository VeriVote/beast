/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.ErrorHandling;

import java.util.ArrayList;

/**
 *
 * @author Holger-Desktop
 */
public class ErrorFinderList {
    private ArrayList<ErrorFinder> errorFinderList = new ArrayList<>();
    
    public ArrayList<CodeError>  getErrors() {
        ArrayList<CodeError> created = new ArrayList<>();
        for(int i = 0; i < errorFinderList.size() && created.size() == 0; ++i) {
            created.addAll(errorFinderList.get(i).getErrors());
        }
        return created;
    }
    
    public void add(ErrorFinder errorFinder) {
        errorFinderList.add(errorFinder);
    }
}
