package edu.kit.kastel.formal.beast.api.method.function;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public enum CelectionDescriptionFunctionType {
    VOTING, SIMPLE;

    private static final String BLANK = " ";
    private static final String UNDERSCORE = "_";

    @Override
    public String toString() {
        return this.name()
                .toLowerCase()
                .replaceAll(UNDERSCORE, BLANK);
    }

    public String toNiceString() {
        final String lowerCaseString = toString();
        final StringBuilder niceString = new StringBuilder();
        boolean space = true;
        for (int i = 0; i < lowerCaseString.length(); i++) {
            final char c =
                    space && Character.isLetter(lowerCaseString.charAt(i))
                    ? Character.toUpperCase(lowerCaseString.charAt(i))
                            : lowerCaseString.charAt(i);
            niceString.append(Character.toString(c));
            space = Character.isSpaceChar(c);
        }
        return niceString.toString();
    }
}
