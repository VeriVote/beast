grammar FormalPropertyDescription;

booleanExpList : booleanExpListElement*;

booleanExpListElement : booleanExp ';';

booleanExp : 	quantorExp | binaryRelationExp | notExp | comparisonExp | OpenBracket booleanExp ClosedBracket;

binaryRelationExp : binaryRelationExp BinaryRelationSymbol booleanExp |
					quantorExp BinaryRelationSymbol booleanExp |
					notExp BinaryRelationSymbol booleanExp |
					comparisonExp BinaryRelationSymbol booleanExp |	
			
					'(' binaryRelationExp ')' BinaryRelationSymbol booleanExp |
					'(' quantorExp ')' BinaryRelationSymbol booleanExp |
					'(' notExp ')' BinaryRelationSymbol booleanExp |
					'(' comparisonExp ')' BinaryRelationSymbol booleanExp; 		

quantorExp : Quantor passSymbVar ':' booleanExp; 	

notExp : '!' booleanExp;

comparisonExp : typeExp ComparisonSymbol typeExp;

typeExp : electExp | voteExp | numberExpression | symbolicVarExp | typeByPosExp;

numberExpression : 	'(' numberExpression ')' |
			numberExpression Mult numberExpression |
			numberExpression Add numberExpression |			
			voteSumExp |
			constantExp |
			integer;

/*binaryNumberExp :	'(' binaryNumberExp ')' Add numberExpression |
			'(' binaryNumberExp ')' Mult numberExpression |
			'(' voteSumExp ')' Add numberExpression |
			'(' voteSumExp ')' Mult numberExpression |				
			'(' constantExp ')' Add numberExpression |
			'(' constantExp ')' Mult numberExpression |
			'(' integer ')' Add numberExpression |
			'(' integer ')' Mult numberExpression |
			binaryNumberExp Add numberExpression |
			binaryNumberExp Mult numberExpression |
			voteSumExp Add numberExpression |
			voteSumExp Mult numberExpression |				
			constantExp Add numberExpression |
			constantExp Mult numberExpression |
			integer Add numberExpression |
			integer Mult numberExpression;*/

typeByPosExp : voterByPosExp | candByPosExp | seatByPosExp;

voterByPosExp : VoterByPos passPosition;

candByPosExp : CandByPos passPosition;

seatByPosExp : SeatByPos passPosition;

integer : Integer;

electExp :  Elect passSymbVar*;

voteExp : Vote passSymbVar*;

constantExp : 'V' | 'C' | 'S';

voteSumExp : Votesum passSymbVar;

passSymbVar : OpenBracket symbolicVarExp ClosedBracket;

passPosition : OpenBracket numberExpression ClosedBracket;

symbolicVarExp : Identifier;

//Lexer

VoterByPos : 'VOTER_BY_POS' Integer;

CandByPos : 'CAND_BY_POS' Integer;

SeatByPos : 'SEAT_BY_POS' Integer;

Mult : '*'|'/';

Add : '+'|'-';

Vote : 'VOTES' Integer;

Elect : 'ELECT' Integer;

Votesum : 'VOTE_SUM_FOR_CANDIDATE' Integer;

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
