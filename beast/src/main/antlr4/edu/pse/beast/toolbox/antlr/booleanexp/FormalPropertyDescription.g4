grammar FormalPropertyDescription;

booleanExpList
    : booleanExpListElement*
    ;

booleanExpListElement
    :   booleanExp ';'
    ;

booleanExp
    :   quantifierExp
    |   binaryRelationExp
    |   notExp
    |   comparisonExp
    |   OpenBracket booleanExp ClosedBracket
    ;

binaryRelationExp
    :   binaryRelationExp BinaryRelationSymbol booleanExp
    |   quantifierExp BinaryRelationSymbol booleanExp
    |   notExp BinaryRelationSymbol booleanExp
    |   comparisonExp BinaryRelationSymbol booleanExp
    
    |   '(' binaryRelationExp ')' BinaryRelationSymbol booleanExp
    |   '(' quantifierExp ')' BinaryRelationSymbol booleanExp
    |   '(' notExp ')' BinaryRelationSymbol booleanExp
    |   '(' comparisonExp ')' BinaryRelationSymbol booleanExp
    ;


quantifierExp
    :   Quantifier passSymbVar ':' booleanExp
    ;

notExp
    :   '!' booleanExp
    ;

comparisonExp
    :   typeExp ComparisonSymbol typeExp
    ;   

	
typeExp
    :   numberExpression
    |   electionTypeExpression
    ;

numberExpression
    :   '(' numberExpression ')'
    |   numberExpression Mult numberExpression
    |   numberExpression Add numberExpression
    |   voteSumExp
    |   voteSumUniqueExp
    |   constantExp
    |   integer
    ;
    
// any sort of expression which returns a type which is not a number and represents an
// election object. These differ depending on the input and output type of the election.
// eg VOTES1 can be and array of uints, or an array of arrays of unints.
// Thus, things such as VOTES1 == ELECT1 can make sense but doesnt have to.
// These types can only be compared, which leads to a boolean exp. Two types
// can only be compared if they produce lists of same dims with the same underlying
// value, such as VOTES1 == VOTES2    

// Instead of Not Empty we use Empty and combine it with ! which we already have
// Instead of an Empty(Votes) expression I want to see if I can use a comparison with
// a special Empty list

electionTypeExpression
	: intersectExp
	| typeByPosExp
    | electExp
    | voteExp
    | tupleExp
    | permExp
    | emptyList
    | symbolicVarExp
    ;

emptyList
	: EmptyList
	;
    
intersectExp
	: intersectVotes
	| intersectElect
	;
	
intersectVotes
	: Intersect '(' Vote (','Vote)* ')'
	;

intersectElect
	: Intersect '(' Elect (','Elect)* ')'
	;
	
permExp
	: permVoteExp
	| permElectExp
	;
	
permVoteExp
	: Permutation OpenBracket Vote ClosedBracket
	;

permElectExp
	: Permutation OpenBracket Elect ClosedBracket
	;
    
tupleExp
	: voteTupleExp
	| electTupleExp
	;
	
electTupleExp
	: OpenTuple Elect (',' Elect)* CloseTuple
	;
    
voteTupleExp
	: OpenTuple Vote (',' Vote)* CloseTuple
	;

typeByPosExp
    :   voterByPosExp
    |   candByPosExp
    |   seatByPosExp
    ;

voterByPosExp
    :   'VOTER_AT_POS' passPosition
    ;

candByPosExp
    :   'CAND_AT_POS' passPosition
    ;

seatByPosExp
    :   'SEAT_AT_POS' passPosition
    ;

integer
    :   Integer
    ;

electExp
    :   Elect passType*
    ;

voteExp
    :   Vote passType*
    ;

passType
    :   passSymbVar
    |   passByPos
    ;

constantExp
    :   'V'
    |   'C'
    |   'S'
    ;

voteSumExp
    :   Votesum passType
    ;

voteSumUniqueExp
    :   VotesumUnique passType
    ;

passSymbVar
    :   OpenBracket symbolicVarExp ClosedBracket
    ;

passPosition
    :   OpenBracket numberExpression ClosedBracket
    ;

passByPos
    :   OpenBracket typeByPosExp ClosedBracket
    ;

symbolicVarExp
    :   Identifier
    ;

// Lexer

Mult
    :   '*'
    |   '/'
    ;

Add
    :   '+'
    |   '-'
    ;

Intersect : 'CUT';

Vote
    :   'VOTES' Integer
    ;

Elect
    :   'ELECT' Integer
    ;

EmptyList : 'O';

Votesum
    :   'VOTE_SUM_FOR_CANDIDATE' Integer
    ;

VotesumUnique
    :   'VOTE_SUM_FOR_UNIQUE_CANDIDATE' Integer
    ;

ClosedBracket : ')';

OpenBracket : '(';

OpenTuple : '[';

CloseTuple : ']';

Quantifier
    :   'FOR_ALL_VOTERS'
    |   'FOR_ALL_CANDIDATES'
    |   'FOR_ALL_SEATS'
    |   'EXISTS_ONE_VOTER'
    |   'EXISTS_ONE_CANDIDATE'
    |   'EXISTS_ONE_SEAT'
    ;

Permutation : 'PERM';

ComparisonSymbol
    :   '=='
    |   '!='
    |   '<='
    |   '>='
    |   '<'
    |   '>'
    ;

BinaryRelationSymbol
    :   '&&'
    |   '||'
    |   '==>'
    |   '<==>'
    ;

Integer
    :   Digit+
    ;

// Same rules as C

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
    // |   // Other implementation-defined characters ...
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
