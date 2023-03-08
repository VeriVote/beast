package edu.kit.kastel.formal.beast.api.codegen.loopbound;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public enum LoopBoundType {
    AMOUNT_VOTERS, AMOUNT_CANDIDATES, AMOUNT_SEATS,
    NECESSARY_AMOUNT_VOTERS, NECESSARY_AMOUNT_CANDIDATES,
    NECESSARY_AMOUNT_SEATS, MANUALLY_ENTERED;

    private static final String EMPTY = "";
    private static final String BLANK = " ";
    private static final String UNDERSCORE = "_";
    private static final String AMOUNT = "AMOUNT_";

    @Override
    public String toString() {
        return this.name()
                .replaceAll(AMOUNT, EMPTY)
                .toLowerCase()
                .replaceAll(UNDERSCORE, BLANK);
    }
}
