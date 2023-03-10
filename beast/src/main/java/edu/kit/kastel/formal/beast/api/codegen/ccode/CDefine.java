package edu.kit.kastel.formal.beast.api.codegen.ccode;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CDefine {
    private static final String BLANK = " ";
    private static final String DEFINE = "#define";

    private String toReplace;
    private String replaceWith;

    public CDefine(final String toReplaceString,
                   final String replaceWithString) {
        this.toReplace = toReplaceString;
        this.replaceWith = replaceWithString;
    }

    public final String generateCode() {
        return DEFINE + BLANK + toReplace + BLANK + replaceWith;
    }
}
