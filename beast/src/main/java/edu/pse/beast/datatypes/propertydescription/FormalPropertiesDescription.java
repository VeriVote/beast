package edu.pse.beast.datatypes.propertydescription;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Holger
 */
public class FormalPropertiesDescription {

    private String code;

    /**
     * @param code the code of the FormalPropertyDescription
     */
    public FormalPropertiesDescription(String code) {
        this.code = code;
    }

    /**
     *
     * @return the code of the FormalPropertyDescription
     */
    public String getCode() {
        return code;
    }

    /**
     *
     * @param code the code of the FormalPropertyDescription
     */
    public void setCode(String code) {
        this.code = code;
    }

    public List<String> getCodeAsList() {
        String[] split = code.split("\n");

        return Arrays.asList(split);
    }
}