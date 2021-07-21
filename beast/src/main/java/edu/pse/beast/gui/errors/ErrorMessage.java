package edu.pse.beast.gui.errors;

public class ErrorMessage {
    private String title;
    private String msg;

    public ErrorMessage(final String titleString, final String messageString) {
        this.title = titleString;
        this.msg = messageString;
    }

    public final String getTitle() {
        return title;
    }

    public final void setTitle(final String titleString) {
        this.title = titleString;
    }

    public final String getMsg() {
        return msg;
    }

    public final void setMsg(final String messageString) {
        this.msg = messageString;
    }
}
