package edu.kit.kastel.formal.beast.api.codegen.loopbound;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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

    private Map<String, List<LoopBound>> functionNamesToLoopbounds =
            new LinkedHashMap<String, List<LoopBound>>();
    private List<LoopBound> mainLoopbounds = new ArrayList<LoopBound>();
    private Map<String, List<LoopBound>> votingInitLoopbounds =
            new LinkedHashMap<String, List<LoopBound>>();
    private Map<String, List<LoopBound>> votingBackLoopbounds =
            new LinkedHashMap<String, List<LoopBound>>();

    public final void addLoopBound(final LoopBound b) {
        final String funcName = b.getFunctionName();
        if (!functionNamesToLoopbounds.containsKey(funcName)) {
            functionNamesToLoopbounds.put(funcName, new ArrayList<LoopBound>());
        }
        functionNamesToLoopbounds.get(funcName).add(b);
    }

    public final void addFunction(final String name) {
        if (!functionNamesToLoopbounds.containsKey(name)) {
            functionNamesToLoopbounds.put(name, new ArrayList<LoopBound>());
        }
    }

    public final List<LoopBound> getLoopBoundsAsList() {
        final List<LoopBound> loopbounds = new ArrayList<LoopBound>();
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
        for (final Map.Entry<String, List<LoopBound>> e : votingInitLoopbounds.entrySet()) {
            final List<LoopBound> initLbs = e.getValue();
            for (int i = 0; i < initLbs.size(); ++i) {
                initLbs.get(i).setIndex(i);
                initLbs.get(i).setFunctionName(e.getKey());
            }
            final List<LoopBound> extractedLbs = functionNamesToLoopbounds.get(e.getKey());
            final int amtInitLoops = e.getValue().size();
            for (final LoopBound lb : extractedLbs) {
                lb.setFunctionName(e.getKey());
                lb.incrementIndexBy(amtInitLoops);
            }
            final int amtLoopboundsInFunc =
                    e.getValue().size() + extractedLbs.size();
            for (int i = 0; i < votingBackLoopbounds.get(e.getKey()).size(); ++i) {
                final LoopBound lb = votingBackLoopbounds.get(e.getKey()).get(i);
                lb.setIndex(i + amtLoopboundsInFunc);
                lb.setFunctionName(e.getKey());
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
        for (final Map.Entry<String, List<LoopBound>> e : functionNamesToLoopbounds.entrySet()) {
            for (final LoopBound lb : votingInitLoopbounds.get(e.getKey())) {
                created.add(lb.getUnwindString(v, c, s));
            }
            for (final LoopBound lb : e.getValue()) {
                created.add(lb.getUnwindString(v, c, s));
            }
            for (final LoopBound lb : votingBackLoopbounds.get(e.getKey())) {
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
