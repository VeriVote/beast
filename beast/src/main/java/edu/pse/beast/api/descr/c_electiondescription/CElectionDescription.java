package edu.pse.beast.api.descr.c_electiondescription;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import edu.pse.beast.api.c_parser.ExtractedCLoop;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.descr.c_electiondescription.function.CElectionDescriptionFunction;
import edu.pse.beast.api.descr.c_electiondescription.function.SimpleTypeFunction;
import edu.pse.beast.api.descr.c_electiondescription.function.VotingSigFunction;

/**
 * All data which describes an election description in c code.
 *
 * @author Holger Klein
 *
 */
public class CElectionDescription {
    private static final String VOTING = "voting";

    private List<CElectionDescriptionFunction> functions = new ArrayList<>();
    private Set<String> functionNames = new HashSet<>();
    private VotingSigFunction votingFunction;

    private String name;
    private String uuid;

    private VotingInputTypes inputType;
    private VotingOutputTypes outputType;

    public CElectionDescription(final VotingInputTypes inType,
                                final VotingOutputTypes outType,
                                final String nameString) {
        this.inputType = inType;
        this.outputType = outType;
        this.name = nameString;
        votingFunction = createNewVotingSigFunctionAndAdd(VOTING);
        this.uuid = UUID.randomUUID().toString();
    }

    public CElectionDescription(final String uuidString, final String nameString,
                                final VotingInputTypes inType,
                                final VotingOutputTypes outType) {
        this.inputType = inType;
        this.outputType = outType;
        this.name = nameString;
        votingFunction = createNewVotingSigFunctionAndAdd(VOTING);
        this.uuid = uuidString;
    }

    public void setFunctions(final List<CElectionDescriptionFunction> functionList) {
        this.functions = functionList;
    }

    public void setVotingFunction(final VotingSigFunction votingFunc) {
        this.votingFunction = votingFunc;
    }

    public boolean hasFunctionName(final String nameString) {
        return functionNames.contains(nameString);
    }

    public VotingSigFunction getVotingFunction() {
        return votingFunction;
    }

    public VotingSigFunction createNewVotingSigFunctionAndAdd(final String nameString) {
        final VotingSigFunction created =
                new VotingSigFunction(nameString, inputType, outputType);
        functions.add(created);
        functionNames.add(nameString);
        return created;
    }

    public void removeFunction(final CElectionDescriptionFunction func) {
        functionNames.remove(func.getName());
        functions.remove(func);
    }

    @Override
    public String toString() {
        return name;
    }

    public void setInputType(final VotingInputTypes inType) {
        this.inputType = inType;
        for (final CElectionDescriptionFunction f : functions) {
            if (f.getClass().equals(VotingSigFunction.class)) {
                ((VotingSigFunction) f).setInputType(inType);
            }
        }
    }

    public void setOutputType(final VotingOutputTypes outType) {
        this.outputType = outType;
        for (final CElectionDescriptionFunction f : functions) {
            if (f.getClass().equals(VotingSigFunction.class)) {
                ((VotingSigFunction) f).setOutputType(outType);
            }
        }
    }

    public VotingInputTypes getInputType() {
        return inputType;
    }

    public VotingOutputTypes getOutputType() {
        return outputType;
    }

    public String getName() {
        return name;
    }

    public List<CElectionDescriptionFunction> getFunctions() {
        return functions;
    }

    public String getUuid() {
        return uuid;
    }

    public void setName(final String nameString) {
        this.name = nameString;
    }

    public void addSimpleFunction(final SimpleTypeFunction f) {
        functions.add(f);
        functionNames.add(f.getName());
    }

    public CodeGenLoopBoundHandler generateLoopBoundHandler() {
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
