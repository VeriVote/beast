package edu.pse.beast.gui.errors;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class BeastError {
    private Exception exception;
    private String other;
    private BeastErrorType errorTypes;
    private ErrorMessage errorMessage;

    public BeastError(final BeastErrorType errorType, final String otherString) {
        this.other = otherString;
        this.errorTypes = errorType;
    }

    public BeastError(final BeastErrorType errorType,
                      final String otherString,
                      final Exception exc) {
        this.other = otherString;
        this.errorTypes = errorType;
        exception = exc;
    }

    public final Exception getException() {
        return exception;
    }

    public final void setException(final Exception exc) {
        this.exception = exc;
    }

    public final String getOther() {
        return other;
    }

    public final void setOther(final String otherString) {
        this.other = otherString;
    }

    public final BeastErrorType getErrorType() {
        return errorTypes;
    }

    public final void setErrorType(final BeastErrorType errorType) {
        this.errorTypes = errorType;
    }

    public final ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public final void setErrorMessage(final ErrorMessage errorMsg) {
        this.errorMessage = errorMsg;
    }

    @Override
    public final String toString() {
        return errorMessage.getTitle();
    }
}
