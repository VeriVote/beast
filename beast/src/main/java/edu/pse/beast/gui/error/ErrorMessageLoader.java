package edu.pse.beast.gui.error;

import java.util.Map;

/**
 * This class can be extended to load error messages from a file.
 *
 * @author Holger Klein
 *
 */
public class ErrorMessageLoader {
    private static final String NOT_IMPLEMENTED = "Not implemented";
    private static final String CANNOT_REMOVE_FUNCTION = "Cannot remove the voting function";
    private static final String TRIED_RUNNING_NOT_IMPLEMENTED =
            "tried to run code that has not been implemented yet";
    private static final String TRIED_REMOVING = "Tried removing voting function";

    public final Map<BeastErrorType, ErrorMessage> loadMessages() {
        return Map.of(BeastErrorType.NOT_IMPLEMENTED_CODE_PATH,
                new ErrorMessage(NOT_IMPLEMENTED,
                        TRIED_RUNNING_NOT_IMPLEMENTED),
                BeastErrorType.CANNOT_REMOVE_VOTING_FUNCTION,
                new ErrorMessage(TRIED_REMOVING,
                        CANNOT_REMOVE_FUNCTION));
    }
}
