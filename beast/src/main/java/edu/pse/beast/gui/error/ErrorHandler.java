package edu.pse.beast.gui.error;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class ErrorHandler {

    private List<String> errorLog = new ArrayList<String>();
    private ErrorGUIController listener;

    private Map<BeastErrorType, ErrorMessage> errTypeToMessageType;
    private List<BeastError> exceptions = new ArrayList<BeastError>();

    public ErrorHandler(final ErrorMessageLoader messageLoader) {
        errTypeToMessageType = messageLoader.loadMessages();
    }

    public final void setListener(final ErrorGUIController guiListener) {
        this.listener = guiListener;
    }

    public final List<String> getErrorLog() {
        return errorLog;
    }

    public final void logAndDisplayError(final BeastError err) {
        exceptions.add(err);
        final BeastErrorType type = err.getErrorType();
        // TODO maybe modify the message based on the exception
        final ErrorMessage msg;
        if (errTypeToMessageType.get(type) != null) {
            msg = errTypeToMessageType.get(type);
        } else {
            msg = new ErrorMessage();
        }
        ErrorDialogHelper.showErrorDialog(msg);
        err.setErrorMessage(msg);
        listener.handleAddedError(err);
    }
}
