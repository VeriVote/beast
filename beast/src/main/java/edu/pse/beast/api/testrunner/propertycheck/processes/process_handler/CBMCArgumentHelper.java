package edu.pse.beast.api.testrunner.propertycheck.processes.process_handler;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;

public class CBMCArgumentHelper {
    private static final String PREPROC_FLAG = " -D ";
    private static final String SET = "=";

    public static String getConstCommands(final CodeGenOptions codeGenOptions,
                                          final int v, final int c, final int s) {
        return PREPROC_FLAG + codeGenOptions.getCbmcAmountMaxVotersVarName() + SET + v
                + PREPROC_FLAG + codeGenOptions.getCbmcAmountMaxCandsVarName() + SET + c
                + PREPROC_FLAG + codeGenOptions.getCbmcAmountMaxSeatsVarName() + SET + s;
    }
}
