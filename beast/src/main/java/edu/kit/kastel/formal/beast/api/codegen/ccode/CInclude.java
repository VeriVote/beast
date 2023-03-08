package edu.kit.kastel.formal.beast.api.codegen.ccode;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CInclude {
    private static final String BLANK = " ";
    private static final String ANGLE_LEFT = "<";
    private static final String ANGLE_RIGHT = ">";
    private static final String INCLUDE = "#include";

    private String filePath;

    public CInclude(final String filePathString) {
        this.filePath = filePathString;
    }

    public final String generateCode() {
        return INCLUDE + BLANK + ANGLE_LEFT + filePath + ANGLE_RIGHT;
    }
}
