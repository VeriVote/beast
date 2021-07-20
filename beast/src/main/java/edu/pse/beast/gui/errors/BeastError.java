package edu.pse.beast.gui.errors;

public class BeastError {
    private Exception exception;
    private String other;
    private BeastErrorTypes errorTypes;
    private ErrorMessage errorMessage;

    public BeastError(final BeastErrorTypes errorType, final String otherString) {
        this.other = otherString;
        this.errorTypes = errorType;
    }

    public BeastError(final BeastErrorTypes errorType,
                      final String otherString,
                      final Exception exc) {
        this.other = otherString;
        this.errorTypes = errorType;
        exception = exc;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(final Exception exc) {
        this.exception = exc;
    }

    public String getOther() {
        return other;
    }

    public void setOther(final String otherString) {
        this.other = otherString;
    }

    public BeastErrorTypes getErrorType() {
        return errorTypes;
    }

    public void setErrorType(final BeastErrorTypes errorType) {
        this.errorTypes = errorType;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(final ErrorMessage errorMsg) {
        this.errorMessage = errorMsg;
    }

    @Override
    public String toString() {
        return errorMessage.getTitle();
    }
}
