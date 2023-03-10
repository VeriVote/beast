package edu.kit.kastel.formal.beast.api.runner.propertycheck.process.cbmc;

import java.util.ArrayList;
import java.util.List;

import edu.kit.kastel.formal.beast.api.codegen.cbmc.CodeGenOptions;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CBMCArgumentHelper {
    private static final String EMPTY = "";
    private static final String BLANK = " ";
    private static final String PREPROC_FLAG = "-D ";
    private static final String SET = "=";
    private static final String JSON_UI = "--json-ui ";

    public static void addToArgs(final List<String> argList,
                                 final List<String> toAdd) {
        for (final String a : toAdd) {
            if (a != null && !a.isBlank()) {
                argList.add(a);
            }
        }
    }

    public static String argumentListToString(final List<String> arguments) {
        String argument = EMPTY;
        int a = 0;
        for (final String arg: arguments) {
            if (!argument.isEmpty() && a != arguments.size()) {
                argument += BLANK;
            }
            argument += arg;
            a++;
        }
        return argument;
    }

    public static String getJsonOutputCommand() {
        return BLANK + JSON_UI;
    }

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
        return argumentListToString(getConstCommandList(codeGenOptions, v, c, s));
    }
}
