package edu.pse.beast.api.codegen.loopbounds;

import java.util.ArrayList;
import java.util.List;

public class LoopBound {
    private static final String LOOP_BOUND_TEMPLATE = " --unwindset FUNC_NAME.IDX:BOUND ";

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

    public static LoopBound codeGenLoopbound(final LoopBoundType type) {
        final LoopBound bound = new LoopBound();
        bound.loopBoundType = type;
        return bound;
    }

    public static List<LoopBound> codeGenLoopboundList(final List<LoopBoundType> types) {
        final List<LoopBound> created = new ArrayList<>();
        for (final LoopBoundType lbt : types) {
            created.add(codeGenLoopbound(lbt));
        }
        return created;
    }

    public String getUnwindString(final int v, final int c, final int s) {
        final int bound;
        switch (loopBoundType) {
        case NECESSARY_LOOP_BOUND_AMT_VOTERS:
            bound = v + 1;
            break;
        case NECESSARY_LOOP_BOUND_AMT_CANDS:
            bound = c + 1;
            break;
        case NECESSARY_LOOP_BOUND_AMT_SEATS:
            bound = s + 1;
            break;
        case MANUALLY_ENTERED_INTEGER:
            bound = manualBoundIfNeeded;
            break;
        default:
            return "";
        }

        final String currentUnwindArgument =
                LOOP_BOUND_TEMPLATE
                .replaceAll("FUNC_NAME", functionName)
                .replaceAll("IDX", String.valueOf(index))
                .replaceAll("BOUND", String.valueOf(bound));
        return currentUnwindArgument;
    }

    public List<LoopBound> getChildren() {
        return children;
    }

    public String getFunctionName() {
        return functionName;
    }

    public LoopBoundType getLoopBoundType() {
        return loopBoundType;
    }

    public int getIndex() {
        return index;
    }

    public int getManualBoundIfNeeded() {
        return manualBoundIfNeeded;
    }

    public void setChildren(final List<LoopBound> loopBoundChildrenList) {
        this.children = loopBoundChildrenList;
    }

    public void setFunctionName(final String funcNameString) {
        this.functionName = funcNameString;
    }

    public void setLoopBoundType(final LoopBoundType typeOfLoopBound) {
        this.loopBoundType = typeOfLoopBound;
    }

    public void setIndex(final int indexNumber) {
        this.index = indexNumber;
    }

    public void setManualBoundIfNeeded(final int manualBound) {
        this.manualBoundIfNeeded = manualBound;
    }

    public void incrementIndexBy(final int amt) {
        index += amt;
    }
}
