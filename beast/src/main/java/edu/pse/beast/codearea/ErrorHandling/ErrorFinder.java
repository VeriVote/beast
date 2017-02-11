/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.ErrorHandling;

import java.util.ArrayList;

/**
 * This interface is implemented by all classes which can find errors in code
 * @author Holger-Desktop
 */
public interface ErrorFinder {
    public ArrayList<CodeError> getErrors();
}
