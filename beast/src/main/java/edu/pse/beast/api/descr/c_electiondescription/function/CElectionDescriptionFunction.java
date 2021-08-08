package edu.pse.beast.api.descr.c_electiondescription.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.pse.beast.api.c_parser.AntlrCLoopParser;
import edu.pse.beast.api.c_parser.ExtractedCLoop;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public abstract class CElectionDescriptionFunction {
    private static final String LINE_BREAK = "\n";

    private String name;
    private List<String> code = new ArrayList<String>();
    private List<ExtractedCLoop> extractedLoops = new ArrayList<ExtractedCLoop>();

    public CElectionDescriptionFunction(final String nameString) {
        this.name = nameString;
    }

    public final String getName() {
        return name;
    }

    public final String getCode() {
        return String.join(LINE_BREAK, code);
    }

    public final void setCode(final String codeString) {
        this.code = Arrays.asList(codeString.split(LINE_BREAK));
    }

    public final void setName(final String nameString) {
        this.name = nameString;
    }

    public final List<String> getCodeAsList() {
        return code;
    }

    public abstract String getDeclCString(CodeGenOptions codeGenOptions);

    @Override
    public final String toString() {
        return name;
    }

    public final List<ExtractedCLoop> getExtractedLoops() {
        return extractedLoops;
    }

    public final void setExtractedLoops(final List<ExtractedCLoop> extractedLoopList) {
        this.extractedLoops = extractedLoopList;
    }

    public final boolean allLoopsDescribed() {
        boolean res;
        if (extractedLoops.isEmpty()) {
            res = AntlrCLoopParser.getAmtLoops(getCode()) == 0;
        } else {
            res = true;
        }
        for (final ExtractedCLoop l : extractedLoops) {
            if (l.getParsedLoopBoundType() == LoopBoundType.MANUALLY_ENTERED
                    && l.getManualInteger() == null) {
                res = false;
            }
        }
        return res;
    }

    public abstract String getReturnText(CodeGenOptions codeGenOptions);
}
