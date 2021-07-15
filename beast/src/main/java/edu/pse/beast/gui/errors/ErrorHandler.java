package edu.pse.beast.gui.errors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ErrorHandler {

    private List<String> errorLog = new ArrayList<>();
    private ErrorGuiController listener;

    private Map<BeastErrorTypes, ErrorMessage> errTypeToMessageType;
    private List<BeastError> exceptions = new ArrayList<>();

    public ErrorHandler(ErrorMessageLoader messageLoader) {
        errTypeToMessageType = messageLoader.loadMessages();
    }

    public void setListener(ErrorGuiController listener) {
        this.listener = listener;
    }

    public List<String> getErrorLog() {
        return errorLog;
    }

    public void logAndDisplayError(BeastError err) {
        exceptions.add(err);

        // TODO maybe modify the message based on the exception
        ErrorMessage msg = errTypeToMessageType.get(err.getErrorType());
        ErrorDialogHelper.showErrorDialog(msg);

        err.setErrorMessage(msg);
        listener.handleAddedError(err);
    }
}
