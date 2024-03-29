package edu.kit.kastel.formal.beast.api.method;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import edu.kit.kastel.formal.beast.api.codegen.loopbound.CodeGenLoopBoundHandler;
import edu.kit.kastel.formal.beast.api.cparser.ExtractedCLoop;
import edu.kit.kastel.formal.beast.api.method.function.CElectionDescriptionFunction;
import edu.kit.kastel.formal.beast.api.method.function.SimpleTypeFunction;
import edu.kit.kastel.formal.beast.api.method.function.VotingSigFunction;

/**
 * All data which describes an election description in c code.
 *
 * @author Holger Klein
 *
 */
public class CElectionDescription {
    private static final String VOTING = "voting";

    private List<CElectionDescriptionFunction> functions =
            new ArrayList<CElectionDescriptionFunction>();
    private Set<String> functionNames = new LinkedHashSet<String>();
    private VotingSigFunction votingFunction;

    private String name;
    private String uuid;

    private VotingInputType inputType;
    private VotingOutputType outputType;

    public CElectionDescription(final VotingInputType inType,
                                final VotingOutputType outType,
                                final String nameString) {
        this.inputType = inType;
        this.outputType = outType;
        this.name = nameString;
        votingFunction = createNewVotingSigFunctionAndAdd(VOTING);
        this.uuid = UUID.randomUUID().toString();
    }

    public CElectionDescription(final String uuidString, final String nameString,
                                final VotingInputType inType,
                                final VotingOutputType outType) {
        this.inputType = inType;
        this.outputType = outType;
        this.name = nameString;
        votingFunction = createNewVotingSigFunctionAndAdd(VOTING);
        this.uuid = uuidString;
    }

    public final void setFunctions(final List<CElectionDescriptionFunction> functionList) {
        this.functions = functionList;
    }

    public final void setVotingFunction(final VotingSigFunction votingFunc) {
        this.votingFunction = votingFunc;
    }

    public final boolean hasFunctionName(final String nameString) {
        return functionNames.contains(nameString);
    }

    public final VotingSigFunction getVotingFunction() {
        return votingFunction;
    }

    public final VotingSigFunction createNewVotingSigFunctionAndAdd(final String nameString) {
        final VotingSigFunction created =
                new VotingSigFunction(nameString, inputType, outputType);
        functions.add(created);
        functionNames.add(nameString);
        return created;
    }

    public final void removeFunction(final CElectionDescriptionFunction func) {
        functionNames.remove(func.getName());
        functions.remove(func);
    }

    @Override
    public final String toString() {
        return name;
    }

    public final void setInputType(final VotingInputType inType) {
        this.inputType = inType;
        for (final CElectionDescriptionFunction f : functions) {
            if (f.getClass().equals(VotingSigFunction.class)) {
                ((VotingSigFunction) f).setInputType(inType);
            }
        }
    }

    public final void setOutputType(final VotingOutputType outType) {
        this.outputType = outType;
        for (final CElectionDescriptionFunction f : functions) {
            if (f.getClass().equals(VotingSigFunction.class)) {
                ((VotingSigFunction) f).setOutputType(outType);
            }
        }
    }

    public final VotingInputType getInputType() {
        return inputType;
    }

    public final VotingOutputType getOutputType() {
        return outputType;
    }

    public final String getName() {
        return name;
    }

    public final List<CElectionDescriptionFunction> getFunctions() {
        return functions;
    }

    public final String getUuid() {
        return uuid;
    }

    public final void setName(final String nameString) {
        this.name = nameString;
    }

    public final void addSimpleFunction(final SimpleTypeFunction f) {
        functions.add(f);
        functionNames.add(f.getName());
    }

    public final CodeGenLoopBoundHandler generateLoopBoundHandler() {
        final CodeGenLoopBoundHandler boundHandler = new CodeGenLoopBoundHandler();
        for (final CElectionDescriptionFunction f : functions) {
            final List<ExtractedCLoop> loops = f.getExtractedLoops();
            boundHandler.addFunction(f.getName());
            for (final ExtractedCLoop l : loops) {
                boundHandler.addLoopBound(l.generateLoopBound());
            }
        }
        return boundHandler;
    }
}
