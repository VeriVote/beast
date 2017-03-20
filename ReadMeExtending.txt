88                                                   
88                                            ,d     
88                                            88     
88,dPPYba,   ,adPPYba, ,adPPYYba, ,adPPYba, MM88MMM  
88P'    "8a a8P_____88 ""     `Y8 I8[    ""   88     
88       d8 8PP""""""" ,adPPPPP88  `"Y8ba,    88     
88b,   ,a8" "8b,   ,aa 88,    ,88 aa    ]8I   88,    
8Y"Ybbd8"'   `"Ybbd8"' `"8bbdP"Y8 `"YbbdP"'   "Y888  

So you want to extend BEAST:

There are multiple things you might want to extend, change or just modify something, so we will give you a small rundown
of the most important things you might want to change:

- change the checker)
Right now we are using cbmc to check the ElectionDescriptions and properties, but it is really simple to add other checkers
for that.

1) There are multiple abstract classes you have to implement to add another checker
   All these classes are from "edu.pse.beast.propertychecker" :

	- Checker.java (communicates with the program)

	- CheckerFactory.java (creates fitting checkers)

	- Result.java (saves the result and processes it, so it can be diplayed)

	Also, you have to chose an unique checkerID, with which you have to register your CheckerFactory.java at the
	CheckerFactoryFactory.java (have a look at the init() function)

If that is all done, you can simply start BEAST and chose between cbmc, and your newly implemented checker.

- add a new feature to the BooleanExpLang)
Adding a new langauge construct to the BooleanExpLang involves several classes. First, let's have a look a how going from BooleanExpLang to something the checker can work with works inside BEAST:
1) BEAST uses ANTLR to lex and parse the input
2) If ANTLR parses the input completely, a class called BooleanExpEditorVariableErrorFinder checks the input for errors which ANTLR can't find. I uses a class called FormalExpErrorFinderTreeListener which performes a depth-first walk over the syntax tree. This latter class is probably where you might want to implement the new error checking for your feature.
3) If the BooleanExpEditorVariableErrorFinder can't find any other errors, the Syntax tree is translated into an abstract syntax tree. The latter is a data structure which contains all necessesary information to make translation into Code which the used Checker can work with as easy as possible. This translation happens inside the class FormalPropertySyntaxTreeToAstTranslator. It works very similar to the FormalExpErrorFinderTreeListener, meaning it two performs a depth-first walk over the parse-tree. 
4) The AST is then translated into Code CBMC can work with. This translation happens inside CBMCCodeGenerator. This class generates a c-file containing the coting function and a main function. Inside the main function, the code for all supplied post and pre properties is generated. This is performed CBMCCodeGenerationVisitor. This class is called once for each boolean Expression

So lets go through how one would go about adding a feature. Say one wanted to implement a possibility to combine two boolean expression via a NAND. Let's also assume that this is to be achieved without using any of the existing binary operators.
First, one had to come up with a way of representing this operation. The are 2 which come to mind: A function-like call such as "NAND(exp1, exp2)" or something more closely relating to its binary structure, such as exp1 NAND exp2. Obviously, one could choose whatever syntax one likes.
After this decision, one needs to use ANTLR to generate a new lexer and parser, as well as new base-listeners. To do this, open the file FormalPropertyDescription.g4 located in /beast/core/antlrgrammars in any text editor. It is a grammar written in a way ANTLR4 can use to generate lexers, parsers, etc. This document won't go into how to write ANTLR4 grammars. However, in our particular case it would suffice to add 'NAND' to the Lexer Rule called BinaryRelationSymbol. After that, use ANTLR4 to generate the necessary files and use them to replace the ones in toolbox/antlr/booleanexp.
In our particular case, adding any additional error finding wouldn't be necessary. All that needs to be checked is that both sides are correct boolean Expressions, which already happens for the other binary relationships. 
However, for Code generation we would have to create a new class inheriting from BinaryRelationshipNode in package edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes. Call it NANDNode. In the class FormalPropertySyntaxTreeToAstTranslator we now have to modify the function exitBinaryRelationExp, adding an if branch to generate the NANDNode class. 
Finally, we have everything we need for code generation. In the interface BooleanExpNodeVisitor, add a method called visitNandNode which you call from the NANDnodes getvisited function. Then, implement this function in CBMCCodeGenerationVisitor. Looking at how other methods in this class work, it would suffice to generate a new variable name, visiting the lhs and rhs nodes, and combining their variable names in the correct manner:

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
