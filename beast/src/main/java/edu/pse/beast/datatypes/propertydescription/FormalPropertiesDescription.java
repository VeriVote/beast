/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.datatypes.propertydescription;

import edu.pse.beast.toolbox.BooleanExpCodeToASTConverter;

/**
 *
 * @author Niels
 */
public class FormalPropertiesDescription {

    private String code;
    private BooleanExpCodeToASTConverter converter;

    public FormalPropertiesDescription(BooleanExpCodeToASTConverter convertern) {
        this.converter = converter;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
