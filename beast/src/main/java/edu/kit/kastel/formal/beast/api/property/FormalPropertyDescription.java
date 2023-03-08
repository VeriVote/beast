package edu.kit.kastel.formal.beast.api.property;

import java.util.Arrays;
import java.util.List;

/**
 * The class FormalPropertiesDescription.
 *
 * @author Holger Klein
 */
public final class FormalPropertyDescription {
    private static final String LINE_BREAK = "\n";

    /** The code. */
    private String code;

    /**
     * Instantiates a new formal properties description.
     *
     * @param codeString the code of the FormalPropertyDescription
     */
    public FormalPropertyDescription(final String codeString) {
        this.code = codeString;
    }

    /**
     * Instantiates a new formal properties description with empty code string.
     */
    public FormalPropertyDescription() {
        this.code = "";
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
     *
     * @param codeString the code of the FormalPropertyDescription
     */
    public void setCode(final String codeString) {
        this.code = codeString;
    }

    /**
     * Sets the code of this property to the empty string.
     */
    public void setCode() {
        this.code = "";
    }

    /**
     * Gets the code as list.
     *
     * @return the code as list
     */
    public List<String> getCodeAsList() {
        final String[] split = code.split(LINE_BREAK);
        return Arrays.asList(split);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final FormalPropertyDescription other = (FormalPropertyDescription) obj;
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
