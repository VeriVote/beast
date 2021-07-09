package edu.pse.beast.gui.errors;

public class BeastError {
    private Exception exception;
    private String other;
    private BeastErrorTypes errorType;
    private ErrorMessage errorMessage;
    
    
    public BeastError(BeastErrorTypes errorType, String other) {
        this.other = other;
        this.errorType = errorType;
    }
    
    public BeastError(BeastErrorTypes errorType, String other, Exception e) {
        this.other = other;
        this.errorType = errorType;
        exception = e;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public BeastErrorTypes getErrorType() {
        return errorType;
    }

    public void setErrorType(BeastErrorTypes errorType) {
        this.errorType = errorType;
    }
    
    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
    
    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    @Override
    public String toString() {
        return errorMessage.getTitle();
    }

}
