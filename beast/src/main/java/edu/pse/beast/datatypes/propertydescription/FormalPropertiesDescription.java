package edu.pse.beast.datatypes.propertydescription;

import java.util.Arrays;
import java.util.List;

/**
 * The Class FormalPropertiesDescription.
 *
 * @author Holger
 */
public class FormalPropertiesDescription {

    /** The code. */
    private String code;

    /**
     * Instantiates a new formal properties description.
     *
     * @param codeString the code of the FormalPropertyDescription
     */
    public FormalPropertiesDescription(final String codeString) {
        this.code = codeString;
    }

    /**
     * Gets the code.
     *
     * @return the code of the FormalPropertyDescription
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the code of this property. Replaces the previous code.
     * @param codeString the code of the FormalPropertyDescription
     */
    public void setCode(final String codeString) {
        this.code = codeString;
    }

    /**
     * Gets the code as list.
     *
     * @return the code as list
     */
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
    public boolean equals(final Object obj) {
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
