package edu.pse.beast.api.testrunner.propertycheck.jsonoutput;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CBMCJsonMessage {
    private String type;
    private String msg;

    public CBMCJsonMessage(final String typeString, final String messageString) {
        super();
        this.type = typeString;
        this.msg = messageString;
    }

    public final String getMsg() {
        return msg;
    }

    public final String getType() {
        return type;
    }

    @Override
    public final String toString() {
        return msg;
    }
}
