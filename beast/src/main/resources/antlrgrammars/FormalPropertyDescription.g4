grammar FormalPropertyDescription;

booleanExpList : (booleanExp ';')*;

booleanExp : 	binaryRelationExp | quantorExp | notExp | comparisonExp | OpenBracket booleanExp ClosedBracket;

binaryRelationExp : binaryRelationExp BinaryRelationSymbol booleanExp |
					quantorExp BinaryRelationSymbol booleanExp |
					notExp BinaryRelationSymbol booleanExp |
					comparisonExp BinaryRelationSymbol booleanExp					
					; 		

quantorExp : Quantor passSymbVar ':' booleanExp; 	

notExp : '!' booleanExp;

comparisonExp : typeExp ComparisonSymbol typeExp;

typeExp : electExp | voteExp | constantExp | voteSumExp | symbolicVarExp | numberExpression;

numberExpression : Integer;

electExp :  Elect passSymbVar*;

voteExp : Vote passSymbVar*;

constantExp : 'V' | 'C' | 'S';

voteSumExp : 'VOTE_SUM_FOR_CANDIDATE(' passSymbVar;

passSymbVar : OpenBracket symbolicVarExp ClosedBracket;

symbolicVarExp : Identifier;

//Lexer

Vote : 'VOTES' Integer;

Elect : 'ELECT' Integer;

ClosedBracket : ')';

OpenBracket : '(';

Quantor : 	'FOR_ALL_VOTERS' | 'FOR_ALL_CANDIDATES' | 'FOR_ALL_SEATS' |
			'EXISTS_ONE_VOTER' | 'EXISTS_ONE_CANDIDATE' | 'EXISTS_ONE_SEAT';

ComparisonSymbol : '==' | '!=' | '<=' | '>=' | '<' | '>';

BinaryRelationSymbol : '&&' | '||' | '==>' | '<==>';		
			
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
	
fragment
HexadecimalDigit
    :   [0-9a-fA-F]
    ;
	
Whitespace
    :   [ \t]+
        -> skip
    ;

Newline
    :   (   '\r' '\n'?
        |   '\n'
        )
        -> skip
    ;

BlockComment
    :   '/*' .*? '*/'
        -> skip
    ;

LineComment
    :   '//' ~[\r\n]*
        -> skip
    ;