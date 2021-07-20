package edu.pse.beast.gui.errors;

public class ErrorMessage {
    private String title;
    private String msg;

    public ErrorMessage(final String titleString, final String messageString) {
        this.title = titleString;
        this.msg = messageString;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String titleString) {
        this.title = titleString;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(final String messageString) {
        this.msg = messageString;
    }
}
