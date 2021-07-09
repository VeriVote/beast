package edu.pse.beast.api.testrunner.propertycheck.jsonoutput;

public class CBMCJsonMessage {
    String type;
    String msg;

    public CBMCJsonMessage(String type, String msg) {
        super();
        this.type = type;
        this.msg = msg;
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
