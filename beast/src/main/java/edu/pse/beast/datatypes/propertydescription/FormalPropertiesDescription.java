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
     * Sets the code of this property. Replaces the previous code.
     * @param code the code of the FormalPropertyDescription
     */
    public void setCode(String code) {
        this.code = code;
    }

    public List<String> getCodeAsList() {
        String[] split = code.split("\n");
        return Arrays.asList(split);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((code == null)
                        ? 0 : code.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null
                || getClass() != obj.getClass()) {
            return false;
        }
        final FormalPropertiesDescription other
              = (FormalPropertiesDescription) obj;
        if (code == null) {
            if (other.code != null) {
                return false;
            }
        } else if (!code.equals(other.code)) {
            return false;
        }
        return true;
    }
}
