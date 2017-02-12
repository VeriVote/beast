/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.options;

import javax.swing.JComboBox;

/**
 *
 * @author Holger-Desktop
 */
public class OptionElemComboBox extends JComboBox<String>{
    private OptionElement elem;
    public OptionElemComboBox(OptionElement elem) {
        super();
        this.elem = elem;
    }

    public OptionElement getElem() {
        return elem;
    }
    
}
