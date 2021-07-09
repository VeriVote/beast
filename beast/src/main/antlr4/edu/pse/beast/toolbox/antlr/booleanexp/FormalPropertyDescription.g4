grammar FormalPropertyDescription;

booleanExpList
    : booleanExpListElement*
    ;

booleanExpListElement
    :   booleanExp ';'
    |   falseExp ';'
    ;
    
falseExp
    :   'FALSE'
    ;

booleanExp
    :   quantifierExp
    |   binaryRelationExp
    |   notExp
    |   comparisonExp
    |   isEmptyExp
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
    :   Quantifier passSymbVarByName '.' booleanExp
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
    |   symbolicVarExp
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
    
symbolicVarExp
    :   symbVarByNameExp
    |   symbVarByPosExp
    ;
    
symbVarByNameExp
	: Identifier
	;
	
// any sort of expression which returns a type which is not a number and represents an
// election object. These differ depending on the input and output type of the election.
// eg VOTES1 can be and array of uints, or an array of arrays of unints.
// Thus, things such as VOTES1 == ELECT1 can make sense but doesnt have to.
// These types can only be compared, which leads to a boolean exp. Two types
// can only be compared if they produce lists of same dims with the same underlying
// value, such as VOTES1 == VOTES2    

electionTypeExpression
	: intersectExp
    | electExp
    | voteExp
    | tupleExp
    | permExp
    ;

isEmptyExp
	: IsEmpty OpenBracket electionTypeExpression ClosedBracket
	;
    
intersectExp
	: intersectVotes
	| intersectElect
	;
	
intersectVotes
	: Intersect '(' Vote ',' Vote ')'
	;

intersectElect
	: Intersect '(' Elect ','Elect ')'
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

symbVarByPosExp
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
    :   Elect passSymbVar*
    ;

voteExp
    :   Vote passSymbVar*
    ;    
    
    
voteSumExp
    :   Votesum passSymbVar
    ;

voteSumUniqueExp
    :   VotesumUnique passSymbVar
    ;

passSymbVarByName
    :   OpenBracket symbVarByNameExp ClosedBracket
    ;    

passSymbVar
    :   OpenBracket symbolicVarExp ClosedBracket
    ;    

constantExp
    :   'V' passPosition
    |   'C' passPosition
    |   'S' passPosition
    ;
 
passPosition
    :   OpenBracket numberExpression ClosedBracket
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

IsEmpty : 'EMPTY';

Votesum
    :   'VOTE_SUM_FOR_CANDIDATE' Integer
    ;

VotesumUnique
    :   'VOTE_SUM_FOR_UNIQUE_CANDIDATE' Integer
    ;

ClosedBracket : ')';

OpenBracket : '(';

OpenTuple : '[[';

CloseTuple : ']]';

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
