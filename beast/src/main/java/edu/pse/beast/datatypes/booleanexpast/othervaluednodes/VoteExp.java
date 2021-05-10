package edu.pse.beast.datatypes.booleanexpast.othervaluednodes;

import java.util.List;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;
import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.types.InOutType;

/**
 * The Class VoteExp.
 *
 * @author Lukas Stapelbroek
 */
public final class VoteExp extends AccessValueNode {
    /**
     * Instantiates a new vote exp.
     *
     * @param type
     *            the internal type
     * @param accessingVars
     *            accessing variables
     * @param count
     *            the count of this vote
     */
	
	private List<SymbolicCBMCVar> accessingCBMCVars;
	private String voteNumber;
	
    public VoteExp(final InOutType type, final TypeExpression[] accessingVars,
                   final int count) {
        super(type, accessingVars, count);
    }
    
    public VoteExp(List<SymbolicCBMCVar> accessingCBMCVars, String voteNumber) {
		super(null, null, 0);
		this.accessingCBMCVars = accessingCBMCVars;
		this.voteNumber = voteNumber;
		
	}

    public List<SymbolicCBMCVar> getAccessingCBMCVars() {
		return accessingCBMCVars;
	}

    public String getVoteNumber() {
		return voteNumber;
	}
    
	@Override
    public void getVisited(final BooleanExpNodeVisitor visitor) {
        visitor.visitVoteExp(this);
    }

    @Override
    public String getTreeString(final int depth) {
    	String acc = "";
    	for(SymbolicCBMCVar exp : accessingCBMCVars) {
    		acc += "[]";
    	}
        return "Vote" + count + acc;
    }

	@Override
	public void getVisited(BooleanAstVisitor visitor) {
		visitor.visitVoteExpNode(this);		
	}
}
