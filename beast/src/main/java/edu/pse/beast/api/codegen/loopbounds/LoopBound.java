package edu.pse.beast.api.codegen.loopbounds;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.NotImplementedException;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class LoopBound {
    private static final String RESOURCES = "/edu/pse/beast/api/codegen/loopbounds/";
    private static final String FILE_KEY = "UNWIND";
    private static final String FILE_ENDING = ".template";

    private static final String FUNC_NAME = "FUNC_NAME";
    private static final String IDX = "IDX";
    private static final String BOUND = "BOUND";

    private static final Map<String, String> TEMPLATES = new LinkedHashMap<String, String>();

    private List<LoopBound> children;
    private String functionName;
    private LoopBoundType loopBoundType;
    private int index;
    private int manualBoundIfNeeded;

    private LoopBound() {
    }

    public LoopBound(final List<LoopBound> childrenList,
                     final String functionNameString,
                     final LoopBoundType typeOfLoopBound,
                     final int indexNumber) {
        this.children = childrenList;
        this.functionName = functionNameString;
        this.loopBoundType = typeOfLoopBound;
        this.index = indexNumber;
    }

    public LoopBound(final List<LoopBound> childrenList,
                     final String functionNameString,
                     final LoopBoundType typeOfLoopBound,
                     final int indexNumber,
                     final int manualBoundValue) {
        this.children = childrenList;
        this.functionName = functionNameString;
        this.loopBoundType = typeOfLoopBound;
        this.index = indexNumber;
        this.manualBoundIfNeeded = manualBoundValue;
    }

    public static final String getTemplate(final String key,
                                           final Class<?> c) {
        assert key != null;
        if (TEMPLATES.isEmpty() || !TEMPLATES.containsKey(key)) {
            final InputStream stream =
                    c.getResourceAsStream(RESOURCES
                            + key.toLowerCase() + FILE_ENDING);
            if (stream == null) {
                throw new NotImplementedException();
            }
            final StringWriter writer = new StringWriter();
            try {
                IOUtils.copy(stream, writer, StandardCharsets.UTF_8);
            } catch (final IOException e) {
                e.printStackTrace();
            }
            TEMPLATES.put(key, writer.toString());
        }
        return TEMPLATES.get(key);
    }

    public static final LoopBound codeGenLoopbound(final LoopBoundType type) {
        final LoopBound bound = new LoopBound();
        bound.loopBoundType = type;
        return bound;
    }

    public static final List<LoopBound> codeGenLoopboundList(final List<LoopBoundType> types) {
        final List<LoopBound> created = new ArrayList<LoopBound>();
        for (final LoopBoundType lbt : types) {
            created.add(codeGenLoopbound(lbt));
        }
        return created;
    }

    public final String getUnwindString(final int v, final int c, final int s) {
        final int bound;
        switch (loopBoundType) {
        case NECESSARY_AMOUNT_VOTERS:
            bound = v + 1;
            break;
        case NECESSARY_AMOUNT_CANDIDATES:
            bound = c + 1;
            break;
        case NECESSARY_AMOUNT_SEATS:
            bound = s + 1;
            break;
        case MANUALLY_ENTERED:
            bound = manualBoundIfNeeded;
            break;
        default:
            return "";
        }
        final String currentUnwindArgument =
                getTemplate(FILE_KEY, this.getClass())
                .replaceAll(FUNC_NAME, functionName)
                .replaceAll(IDX, String.valueOf(index))
                .replaceAll(BOUND, String.valueOf(bound));
        return currentUnwindArgument.trim();
    }

    public final List<LoopBound> getChildren() {
        return children;
    }

    public final String getFunctionName() {
        return functionName;
    }

    public final LoopBoundType getLoopBoundType() {
        return loopBoundType;
    }

    public final int getIndex() {
        return index;
    }

    public final int getManualBoundIfNeeded() {
        return manualBoundIfNeeded;
    }

    public final void setChildren(final List<LoopBound> loopBoundChildrenList) {
        this.children = loopBoundChildrenList;
    }

    public final void setFunctionName(final String funcNameString) {
        this.functionName = funcNameString;
    }

    public final void setLoopBoundType(final LoopBoundType typeOfLoopBound) {
        this.loopBoundType = typeOfLoopBound;
    }

    public final void setIndex(final int indexNumber) {
        this.index = indexNumber;
    }

    public final void setManualBoundIfNeeded(final int manualBound) {
        this.manualBoundIfNeeded = manualBound;
    }

    public final void incrementIndexBy(final int amt) {
        index += amt;
    }
}
