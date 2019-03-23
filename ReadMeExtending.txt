88                                                   
88                                            ,d     
88                                            88     
88,dPPYba,   ,adPPYba, ,adPPYYba, ,adPPYba, MM88MMM  
88P'    "8a a8P_____88 ""     `Y8 I8[    ""   88     
88       d8 8PP""""""" ,adPPPPP88  `"Y8ba,    88     
88b,   ,a8" "8b,   ,aa 88,    ,88 aa    ]8I   88,    
8Y"Ybbd8"'   `"Ybbd8"' `"8bbdP"Y8 `"YbbdP"'   "Y888  

So you want to extend BEAST:

There are multiple things you might want to extend or change, or just modify something, so we will give you a small rundown
of the most important things you might want to change:

- change the checker)
Right now we are using CBMC to check the ElectionDescriptions and properties, but it is really simple to add other checkers
for that.

1) There are multiple abstract classes you have to implement to add another checker
   All these classes are from "edu.pse.beast.propertychecker" :

	- Checker.java (communicates with the program)

	- CheckerFactory.java (creates fitting checkers)

	- Result.java (saves the result and processes it, so it can be diplayed)

	Also, you have to choose a unique checkerID, with which you must register your CheckerFactory.java at
	CheckerFactoryFactory.java (have a look at the function "init()")

If that is all done, you can simply start BEAST and choose between CBMC, and your newly implemented checker.

- add a new feature to the BooleanExpLang)
Adding a new langauge construct to the BooleanExpLang involves several classes. First, let us have a look at how to go from BooleanExpLang to something the checker can work with inside BEAST:
1) BEAST uses ANTLR to lex and parse the input
2) If ANTLR parses the input completely, a class called BooleanExpEditorVariableErrorFinder checks the input for errors which ANTLR cannot find. I use a class called FormalExpErrorFinderTreeListener which performes a depth-first walk over the syntax tree. This latter class is probably where you want to implement the new error checking for your feature.
3) If the BooleanExpEditorVariableErrorFinder cannot find any other errors, the syntax tree is translated into an abstract syntax tree. The latter is a data structure which contains all necessesary information for translation into code which the used checker can handle as easy as possible. This translation happens inside the class FormalPropertySyntaxTreeToAstTranslator. It works very similar to the FormalExpErrorFinderTreeListener, meaning it also performs a depth-first walk over the parse-tree. 
4) The AST is then translated into code which CBMC can work with. This translation happens inside the class CBMCCodeGenerator. This class generates a c-file containing the coting function and a main function. Inside the main function, the code for all supplied pre- and post-conditions is generated. This is performed via CBMCCodeGenerationVisitor. This class is called once for each boolean expression.

So, let us look at how one would approach adding a new feature. Say, for instance, we want to implement a possibility to combine two boolean expressions via NAND. Let us moreover assume that we want to achieve this without using any of the existing binary operators.
First, we must come up with a way of representing this operation. The are two possibilities which come to mind: A function-like call such as "NAND(exp1, exp2)" or something more closely related to its binary structure, such as exp1 NAND exp2. Obviously, one could choose whichever syntax one likes.
After this decision, we need to use ANTLR to generate a new lexer and parser, as well as new base-listeners. To do this, open the file FormalPropertyDescription.g4 located in '/beast/src/main/antlr4/edu/pse/beast/toolbox/antlr/booleanexp' in any text editor. It is a grammar written such that ANTLR4 can generate lexers, parsers, etc. This document will not go into how to write ANTLR4 grammars. However, in our particular case it would suffice to add 'NAND' to the lexer rule called BinaryRelationSymbol. After that, we re-compile the project and the ANTLR4-maven-plugin generates the necessary files automatically.
In our particular case, adding any additional error finding would not be necessary. All which needs to be checked is that both sides are correct boolean expressions, which already happens for the other binary relationships. 
However, for code generation we would need to create a new class inheriting from BinaryRelationshipNode in the package edu.pse.beast.datatypes.booleanexpast.BooleanValuedNodes. Call it NANDNode. In the class FormalPropertySyntaxTreeToAstTranslator, we must now modify the function exitBinaryRelationExp and add an if-branch to generate the NANDNode class.
Finally, we have everything we need for the code generation. In the interface BooleanExpNodeVisitor, add a method called visitNandNode which we call from the getvisited function in NANDnodes. Then, implement this function in CBMCCodeGenerationVisitor. Looking at how other methods in this class work, it would suffice to generate a new variable name, visiting the lhs and rhs nodes, and combine their variable names in the correct manner:

public void visitNand(NANDNode node) {
	String varname = getNewNandVarname();
	variableNames.push(varname);
	node.getLHSBooleanExpNode().getVisited(this);
        node.getRHSBooleanExpNode().getVisited(this);
	String rhsVarName = variableNames.pop();
	String lhsVarName = variableNames.pop();
	code.add(combineForNand(varname, lhsVarName, rhsVarName));
	testIfLast();
}

public String combineForNand(String varname, String lhsVarName, String rhsVarName) {
	String template = "unsigned int VARNAME = !(LHS && RHS)"
	return template.replace("VARNAME", varname)
			.replace("LHS", lhsVarName)
			.replace("RHS", rhsVarName);
}

testIfLast needs to be called after every booleanExpNode visitation. It adds a necessary assume or assert for cbmc.
