grammar FormalPropertyDescription;

//Parser

booleanExpList : (booleanExp ';')*; //an arbitrary amount of booleanexp, seperated by ;

booleanExp : 	quantorExp |
				binaryRelationExp |
				notExp |
				comparisonExp;
				

binaryRelationExp : logicalAndExp |
					logicalOrExp |
					implicationExp |
					equivalencyExp;
					
quantorExp : 	forAllExp |
				existsOneExp;
				
comparisonExp : typeExp Comparison typeExp;				
				
forAllExp : 	ForAll '(' Identifier ')' ':' booleanExp;
				
existsOneExp : 	ExistsOne '(' Identifier ')' ':' booleanExp;
				
logicalAndExp : booleanExp '&&' booleanExp;
logicalOrExp : booleanExp '||' booleanExp;
implicationExp : booleanExp '==>' booleanExp;
equivalencyExp : booleanExp '<==>' booleanExp;

notExp : '!' booleanExp;				

typeExp : 	symbolicVarExp |	
			electExp |
			voteExp |
			constantExp |
			votesumForCandExp;
			
symbolicVarExp : Identifier;

electExp : Elect Integer accesElement*;

voteExp : Vote Integer accesElement*;

constantExp : Constant;

votesumForCandExp : VOTESUM '(' Identifier ')';

accesElement : '[' Integer ']';

//Lexer

ForAll : 'FOR_ALL_VOTERS' | 'FOR_ALL_CANDIDATES' | 'FOR_ALL_SEATS';

ExistsOne : 'EXISTS_ONE_VOTER' | 'EXISTS_ONE_CANDIDATE' | 'EXISTS_ONE_SEAT';

Comparison : '==' | '!=' | '>=' | '<=' | '<' | '>';

Elect : 'ELECT';

Vote : 'VOTE';

Integer : Digit+;

// same rules as C
Identifier
    :   IdentifierNondigit
        (   IdentifierNondigit
        |   Digit
        )*
    ;

fragment
IdentifierNondigit
    :   Nondigit
    |   UniversalCharacterName
    //|   // other implementation-defined characters...
    ;

fragment
Nondigit
    :   [a-zA-Z_]
    ;

fragment
Digit
    :   [0-9]
    ;

fragment
UniversalCharacterName
    :   '\\u' HexQuad
    |   '\\U' HexQuad HexQuad
    ;

fragment
HexQuad
    :   HexadecimalDigit HexadecimalDigit HexadecimalDigit HexadecimalDigit
    ;