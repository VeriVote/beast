package edu.pse.beast.gui.error;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class ErrorMessage {
    private static final String UNKNOWN_ERROR_TITLE = "Unknown Error";
    private static final String UNKNOWN_MESSAGE_STRING = "This should not happen.";
    private String title;
    private String msg;

    public ErrorMessage(final String titleString, final String messageString) {
        this.title = titleString;
        this.msg = messageString;
    }

    public ErrorMessage() {
        this.title = UNKNOWN_ERROR_TITLE;
        this.msg = UNKNOWN_MESSAGE_STRING;
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
