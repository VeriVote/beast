package edu.pse.beast.api.testrunner.propertycheck.processes.process_handler;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;

public class CBMCArgumentHelper {
    private static final String EMPTY = "";
    private static final String BLANK = " ";
    private static final String PREPROC_FLAG = "-D ";
    private static final String SET = "=";

    public static List<String> getConstCommandList(final CodeGenOptions codeGenOptions,
                                                   final int v, final int c, final int s) {
        final List<String> arguments = new ArrayList<String>();
        arguments.add(PREPROC_FLAG + codeGenOptions.getCbmcAmountMaxVotersVarName() + SET + v);
        arguments.add(PREPROC_FLAG + codeGenOptions.getCbmcAmountMaxCandsVarName() + SET + c);
        arguments.add(PREPROC_FLAG + codeGenOptions.getCbmcAmountMaxSeatsVarName() + SET + s);
        return arguments;
    }

    public static String getConstCommands(final CodeGenOptions codeGenOptions,
                                          final int v, final int c, final int s) {
        final List<String> args = getConstCommandList(codeGenOptions, v, c, s);
        String commands = EMPTY;
        int i = 0;
        final int len = args.size();
        for (final String arg : args) {
            final String pre = i != 0 && i++ != len ? BLANK : EMPTY;
            commands += pre + arg;
        }
        return commands;
    }
}
