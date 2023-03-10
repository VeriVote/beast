package edu.kit.kastel.formal.beast.api.method.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.kit.kastel.formal.beast.api.codegen.cbmc.CodeGenOptions;
import edu.kit.kastel.formal.beast.api.codegen.loopbound.LoopBoundType;
import edu.kit.kastel.formal.beast.api.cparser.AntlrCLoopParser;
import edu.kit.kastel.formal.beast.api.cparser.ExtractedCLoop;

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
        boolean res = !extractedLoops.isEmpty() || AntlrCLoopParser.getAmtLoops(getCode()) == 0;
        for (final ExtractedCLoop l : extractedLoops) {
            res &= l.getParsedLoopBoundType() != LoopBoundType.MANUALLY_ENTERED
                    || l.getManualInteger() != null;
        }
        return res;
    }

    public abstract String getReturnText(CodeGenOptions codeGenOptions);
}
