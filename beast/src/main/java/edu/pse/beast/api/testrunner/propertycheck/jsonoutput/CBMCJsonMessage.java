package edu.pse.beast.api.testrunner.propertycheck.jsonoutput;

public class CBMCJsonMessage {
    private String type;
    private String msg;

    public CBMCJsonMessage(final String typeString, final String messageString) {
        super();
        this.type = typeString;
        this.msg = messageString;
    }

    public String getMsg() {
        return msg;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return msg;
    }
}
