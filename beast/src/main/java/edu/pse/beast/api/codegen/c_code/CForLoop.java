package edu.pse.beast.api.codegen.c_code;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CForLoop {
    private CTypeNameBrackets counterVariables;
    private String stopCondition;
    private String doAfter;

    private List<String> code = new ArrayList<>();

    public CForLoop(final CTypeNameBrackets counterVar,
                    final String stopCondString,
                    final String doAfterString) {
        this.counterVariables = counterVar;
        this.stopCondition = stopCondString;
        this.doAfter = doAfterString;
    }
}
