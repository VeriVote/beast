package edu.pse.beast.api.codegen.booleanExpAst;

import java.util.HashMap;
import java.util.Map;

import edu.pse.beast.api.codegen.helperfunctions.HelperFunction;
import edu.pse.beast.datatypes.booleanexpast.BooleanExpListNode;

public class BooleanExpASTData {
	private BooleanExpListNode topAstNode;
	private int highestElect;
	private int highestVote;
	
	public void setHighestElect(int highestElect) {
		this.highestElect = highestElect;
	}

	public int getHighestElect() {
		return highestElect;
	}

	public void setHighestVote(int highestVote) {
		this.highestVote = highestVote;
	}

	public int getHighestVote() {
		return highestVote;
	}

	public void setTopAstNode(BooleanExpListNode topAstNode) {
		this.topAstNode = topAstNode;
	}

	public BooleanExpListNode getTopAstNode() {
		return topAstNode;
	}

}
