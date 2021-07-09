package edu.pse.beast.gui.errors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.pse.beast.gui.BeastGUIController;
import edu.pse.beast.gui.log.LogGuiController;

public class ErrorHandler {

    private List<String> errorLog = new ArrayList<>();
    private LogGuiController listener;

    private Map<Class<? extends Exception>, ErrorMessage> exceptionTypeToMessageType;
    private List<Exception> exceptions = new ArrayList<>();
    
    public ErrorHandler(ErrorMessageLoader messageLoader) {
        exceptionTypeToMessageType = messageLoader.loadMessages();
    }

    public void setListener(LogGuiController listener) {
        this.listener = listener;
    }

    public List<String> getErrorLog() {
        return errorLog;
    }

    public void logAndDisplayError(Exception e) {
        exceptions.add(e);
        
        //TODO maybe modify the message based on the exception
        ErrorMessage msg = exceptionTypeToMessageType.get(e.getClass());
        ErrorDialogHelper.showErrorDialog(msg);
        
    }
}
