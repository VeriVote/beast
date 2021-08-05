package edu.pse.beast.api.codegen.loopbounds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CodeGenLoopBoundHandler {
    private static final String EMPTY = "";
    private static final String BLANK = " ";

    private static final String MAIN = "main";
    private static final String UNWIND = "--unwind ";

    private Map<String, List<LoopBound>> functionNamesToLoopbounds = new HashMap<>();
    private List<LoopBound> mainLoopbounds = new ArrayList<>();
    private Map<String, List<LoopBound>> votingInitLoopbounds = new HashMap<>();
    private Map<String, List<LoopBound>> votingBackLoopbounds = new HashMap<>();

    public final void addLoopBound(final LoopBound b) {
        final String funcName = b.getFunctionName();
        if (!functionNamesToLoopbounds.containsKey(funcName)) {
            functionNamesToLoopbounds.put(funcName, new ArrayList<>());
        }
        functionNamesToLoopbounds.get(funcName).add(b);
    }

    public final void addFunction(final String name) {
        if (!functionNamesToLoopbounds.containsKey(name)) {
            functionNamesToLoopbounds.put(name, new ArrayList<>());
        }
    }

    public final List<LoopBound> getLoopBoundsAsList() {
        final List<LoopBound> loopbounds = new ArrayList<>();
        for (final List<LoopBound> lbl : functionNamesToLoopbounds.values()) {
            for (final LoopBound lb : lbl) {
                loopbounds.add(lb);
            }
        }
        return loopbounds;
    }

    public final void pushMainLoopBounds(final List<LoopBound> loopbounds) {
        for (final LoopBound bound : loopbounds) {
            mainLoopbounds.add(bound);
        }
    }

    public final void addVotingInitLoopBounds(final String votingFunctionName,
                                              final List<LoopBound> loopbounds) {
        votingInitLoopbounds.put(votingFunctionName, loopbounds);
    }

    public final void pushVotingLoopBounds(final String votingFunctionName,
                                           final List<LoopBound> loopbounds) {
        votingBackLoopbounds.put(votingFunctionName, loopbounds);
    }

    public final void finishAddedLoopbounds() {
        for (final String k : votingInitLoopbounds.keySet()) {
            final List<LoopBound> initLbs = votingInitLoopbounds.get(k);
            for (int i = 0; i < initLbs.size(); ++i) {
                initLbs.get(i).setIndex(i);
                initLbs.get(i).setFunctionName(k);
            }
            final List<LoopBound> extractedLbs = functionNamesToLoopbounds.get(k);
            final int amtInitLoops = votingInitLoopbounds.get(k).size();
            for (final LoopBound lb : extractedLbs) {
                lb.setFunctionName(k);
                lb.incrementIndexBy(amtInitLoops);
            }
            final int amtLoopboundsInFunc =
                    votingInitLoopbounds.get(k).size() + extractedLbs.size();
            for (int i = 0; i < votingBackLoopbounds.get(k).size(); ++i) {
                final LoopBound lb = votingBackLoopbounds.get(k).get(i);
                lb.setIndex(i + amtLoopboundsInFunc);
                lb.setFunctionName(k);
            }
        }
    }

    public final List<String> generateCBMCStringList(final int v, final int c, final int s) {
        final List<String> created = new ArrayList<String>();
        for (int i = 0; i < mainLoopbounds.size(); ++i) {
            final LoopBound lb = mainLoopbounds.get(i);
            lb.setFunctionName(MAIN);
            lb.setIndex(i);
            created.add(lb.getUnwindString(v, c, s));
        }
        for (final String k : functionNamesToLoopbounds.keySet()) {
            for (final LoopBound lb : votingInitLoopbounds.get(k)) {
                created.add(lb.getUnwindString(v, c, s));
            }
            for (final LoopBound lb : functionNamesToLoopbounds.get(k)) {
                created.add(lb.getUnwindString(v, c, s));
            }
            for (final LoopBound lb : votingBackLoopbounds.get(k)) {
                created.add(lb.getUnwindString(v, c, s));
            }
        }
        final String unwind = UNWIND + (Math.max(Math.max(v, c), s) + 1);
        created.add(unwind);
        return created;
    }

    public final String generateCBMCString(final int v, final int c, final int s) {
        final List<String> args = generateCBMCStringList(v, c, s);
        String commands = EMPTY;
        int i = 0;
        final int len = args.size();
        for (final String arg : args) {
            final String pre = i++ != len ? BLANK : EMPTY;
            commands += pre + arg;
        }
        return commands;
    }
}
