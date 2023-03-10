package edu.kit.kastel.formal.beast.api.runner.propertycheck.output;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class JSONMessage {
    private String type;
    private String msg;

    public JSONMessage(final String typeString, final String messageString) {
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
