package edu.pse.beast.propertychecker;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.BooleanExpressionNode;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.PermutationExpContext;

public class PermutationExpNode extends BooleanExpressionNode{
	
		public final PermutationExpContext permutationExpContext;
		public final String voteOutput;

		public PermutationExpNode(PermutationExpContext permutationExpContext, String voteOutput) {
			this.permutationExpContext = permutationExpContext;
			this.voteOutput = voteOutput;
		}

		@Override
		public void getVisited(BooleanExpNodeVisitor visitor) {
			visitor.visitPermutationExpNode(this);
		}

		@Override
		public String getTreeString(int depth) {
			System.out.println("might add treestring");
			return "";
		}
	}

