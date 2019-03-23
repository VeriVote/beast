grammar FormalPropertyDescription;

booleanExpList : booleanExpListElement*;

booleanExpListElement : booleanExp ';' |

//new part 0
						votingListChangeExp ';'|
						votingTupelChangeExp ';' |
						candidateListChangeExp ';';
//end new part 0

//new part 1
votingListChangeExp : Vote ValueAssign votingListChangeContent;

votingListChangeContent : concatenationExp | permutationExp; //expressions that make changes to the voting lists used

votingTupelChangeExp: tuple ValueAssign splitExp;

candidateListChangeExp: Elect ValueAssign intersectExp;

//end new part1

booleanExp : 	quantifierExp | notEmptyExp | intersectExp | binaryRelationExp | notExp | comparisonExp | OpenBracket booleanExp ClosedBracket;

binaryRelationExp : binaryRelationExp BinaryRelationSymbol booleanExp |
					quantifierExp BinaryRelationSymbol booleanExp |
					notExp BinaryRelationSymbol booleanExp |
					comparisonExp BinaryRelationSymbol booleanExp |

					notEmptyExp BinaryRelationSymbol booleanExp |
					intersectExp BinaryRelationSymbol booleanExp |
//					concatenationExp BinaryRelationSymbol booleanExp|



					'(' binaryRelationExp ')' BinaryRelationSymbol booleanExp |
					'(' quantifierExp ')' BinaryRelationSymbol booleanExp |
					'(' notExp ')' BinaryRelationSymbol booleanExp |
					'(' comparisonExp ')' BinaryRelationSymbol booleanExp |

					'(' notEmptyExp ')' BinaryRelationSymbol booleanExp |
					'(' intersectExp ')' BinaryRelationSymbol booleanExp
					;

//new part 2

addedContentExp : notEmptyExp | intersectExp;

notEmptyExp :		NotEmpty '(' notEmptyContent ')';


notEmptyContent	: Elect | intersectExp;

voteEquivalents : Vote | permutationExp | concatenationExp; //all types that are equivalent to "Vote" (e.g the function returns "Vote")

concatenationExp :	'(' voteEquivalents Concatenate voteEquivalents ')'
					| Vote Concatenate voteEquivalents
					| permutationExp Concatenate voteEquivalents;

splitExp : 	Split '(' voteEquivalents ')';

permutationExp: Permutation '(' voteEquivalents ')';

intersectExp: Intersect '(' intersectContent ',' intersectContent ')';

intersectContent : Elect | intersectExp;

tuple :	'(' Vote tupleContent ')';

tupleContent : ',' Vote | ',' Vote tupleContent;

//end new part 2

quantifierExp : Quantifier passSymbVar ':' booleanExp;


notExp : '!' booleanExp;

comparisonExp : typeExp ComparisonSymbol typeExp;

typeExp : electExp | voteExp | numberExpression | symbolicVarExp | typeByPosExp | intersectExp;

numberExpression : 	'(' numberExpression ')' |
			numberExpression Mult numberExpression |
			numberExpression Add numberExpression |
			voteSumExp |
			voteSumUniqueExp |
			constantExp |
			integer;


typeByPosExp : voterByPosExp | candByPosExp | seatByPosExp;

voterByPosExp : 'VOTER_AT_POS' passPosition;

candByPosExp : 'CAND_AT_POS' passPosition;

seatByPosExp : 'SEAT_AT_POS' passPosition;

integer : Integer;

electExp :  Elect passType*;

voteExp : Vote passType*;

passType : passSymbVar|passByPos;

constantExp : 'V' | 'C' | 'S';

voteSumExp : Votesum passType;

voteSumUniqueExp : VotesumUnique passType;

passSymbVar : OpenBracket symbolicVarExp ClosedBracket;

passPosition : OpenBracket numberExpression ClosedBracket;

passByPos : OpenBracket typeByPosExp ClosedBracket;

symbolicVarExp : Identifier;

//Lexer

Mult : '*'|'/';

Add : '+'|'-';

Concatenate : '++';

Intersect : 'INTERSECT';

Vote : 'VOTES' Integer;

Elect : 'ELECT' Integer;

NotEmpty: 'NOTEMPTY';

Votesum : 'VOTE_SUM_FOR_CANDIDATE' Integer;

VotesumUnique : 'VOTE_SUM_FOR_UNIQUE_CANDIDATE' Integer;

ClosedBracket : ')';

OpenBracket : '(';

Quantifier : 'FOR_ALL_VOTERS' | 'FOR_ALL_CANDIDATES' | 'FOR_ALL_SEATS' |
			'EXISTS_ONE_VOTER' | 'EXISTS_ONE_CANDIDATE' | 'EXISTS_ONE_SEAT';

//new part 3
Split :		'SPLIT';

Permutation : 'PERM';

ValueAssign : '<-';
//end new part 3

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
