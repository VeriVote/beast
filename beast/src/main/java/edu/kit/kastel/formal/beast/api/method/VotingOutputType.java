package edu.kit.kastel.formal.beast.api.method;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public enum VotingOutputType {
    CANDIDATE_LIST, PARLIAMENT, PARLIAMENT_STACK, SINGLE_CANDIDATE;

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
        String niceString = "";
        boolean space = true;
        for (int i = 0; i < lowerCaseString.length(); i++) {
            final char c =
                    space && Character.isLetter(lowerCaseString.charAt(i))
                    ? Character.toUpperCase(lowerCaseString.charAt(i))
                            : lowerCaseString.charAt(i);
            niceString += Character.toString(c);
            space = Character.isSpaceChar(c);
        }
        return niceString;
    }
}
