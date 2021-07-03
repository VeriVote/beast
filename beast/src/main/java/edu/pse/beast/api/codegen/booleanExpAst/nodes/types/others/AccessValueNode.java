package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.others;

import java.util.Arrays;

/**
 * The Class AccessValueNode.
 *
 * @author Holger Klein
 */
public abstract class AccessValueNode extends TypeExpression {
	/** The accessing vars. */
	protected final TypeExpression[] accessingVars;

	/** The count. */
	protected final int count;

	/**
	 * Instantiates a new access value node.
	 *
	 * @param type               the type
	 * @param accessingVariables the accessing variables
	 * @param countVal           the count val
	 */
	protected AccessValueNode(final TypeExpression[] accessingVariables,
			final int countVal) {

		this.accessingVars = accessingVariables;
		this.count = countVal;
	}

	/**
	 * Gets the accessing vars.
	 *
	 * @return the accessing vars
	 */
	public TypeExpression[] getAccessingVars() {
		return accessingVars;
	}

	/**
	 * Gets the count.
	 *
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

}
