package edu.pse.beast.gui.errors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ErrorHandler {

    private List<String> errorLog = new ArrayList<>();
    private ErrorGuiController listener;

    private Map<BeastErrorTypes, ErrorMessage> errTypeToMessageType;
    private List<BeastError> exceptions = new ArrayList<>();

    public ErrorHandler(final ErrorMessageLoader messageLoader) {
        errTypeToMessageType = messageLoader.loadMessages();
    }

    public final void setListener(final ErrorGuiController guiListener) {
        this.listener = guiListener;
    }

    public final List<String> getErrorLog() {
        return errorLog;
    }

    public final void logAndDisplayError(final BeastError err) {
        exceptions.add(err);
        // TODO maybe modify the message based on the exception
        final ErrorMessage msg = errTypeToMessageType.get(err.getErrorType());
        ErrorDialogHelper.showErrorDialog(msg);
        err.setErrorMessage(msg);
        listener.handleAddedError(err);
    }
}
