grammar BooleanExpression;

//Parser Rules

property : booleanExp
            | booleanExp LOGICAL_EQUALITY booleanExp
            | booleanExp LOGICAL_AND booleanExp
            | booleanExp LOGICAL_OR booleanExp
            | booleanExp LOGICAL_IMPLICATION booleanExp;

booleanExp : forAllVoters booleanExp
                    | forAllCandidates booleanExp
                    | forAllSeats booleanExp
                    | existsOneVoter booleanExp
                    | existsOneCandidate booleanExp
                    | existsOneSeat booleanExp
                    | relationalNode ;

relationalNode : inequalityRelation
                | equalityRelation
                | lessThanRelation
                | lessOrEqualThanRelation
                | greaterThanRelation
                | greaterOrEqualThanRelation ;

equalityRelation : relationParameter RELATIONAL_EQUALITY relationParameter;
inequalityRelation : relationParameter RELATIONAL_INEQUALITY relationParameter;
lessThanRelation : relationParameter RELATIONAL_LESS relationParameter;
lessOrEqualThanRelation : relationParameter RELATIONAL_LESSOREQUAL relationParameter;
greaterThanRelation : relationParameter RELATIONAL_GREATER relationParameter;
greaterOrEqualThanRelation : relationParameter RELATIONAL_GREATEROREQUAL relationParameter;

relationParameter : SYMBOLIC_VARIABLE | votesVariable | electVariable | constant | sumVotesForCandidate;

constant : VOTERS_CONSTANT | CANDIDATES_CONSTANT | SEATS_CONSTANT ;

forAllVoters : FOR_ALL_VOTERS SYMBOLIC_VARIABLE CLOSING_BRACKET;
forAllCandidates : FOR_ALL_CANDIDATES SYMBOLIC_VARIABLE CLOSING_BRACKET;
forAllSeats : FOR_ALL_SEATS SYMBOLIC_VARIABLE CLOSING_BRACKET;

existsOneVoter : EXISTS_ONE_VOTER SYMBOLIC_VARIABLE CLOSING_BRACKET;
existsOneCandidate : EXISTS_ONE_CANDIDATE SYMBOLIC_VARIABLE CLOSING_BRACKET;
existsOneSeat : EXISTS_ONE_SEAT SYMBOLIC_VARIABLE CLOSING_BRACKET;

sumVotesForCandidate : SUM_VOTES_FOR_CANDIDATE SYMBOLIC_VARIABLE CLOSING_BRACKET;

votesVariable : VOTES OPEN_BRACKET POSITIVE_INTEGER SYMBOLIC_VARIABLE CLOSING_BRACKET;
electVariable : ELECT POSITIVE_INTEGER ;

//Lexer Rules

FOR_ALL_VOTERS : 'FOR_ALL_VOTERS(' ;
FOR_ALL_CANDIDATES : 'FOR_ALL_CANDIDATES(' ;
FOR_ALL_SEATS : 'FOR_ALL_SEATS(' ;

EXISTS_ONE_VOTER : 'EXISTS_ONE_VOTER(' ;
EXISTS_ONE_CANDIDATE : 'EXISTS_ONE_CANDIDATE(' ;
EXISTS_ONE_SEAT : 'EXISTS_ONE_SEAT(' ;

SUM_VOTES_FOR_CANDIDATE : 'SUM_VOTES_FOR_CANDIDATE(' ;

VOTES : 'VOTES';
ELECT : 'ELECT';

SYMBOLIC_VARIABLE : [a-z]+;

VOTERS_CONSTANT : 'V' ;
CANDIDATES_CONSTANT : 'C' ;
SEATS_CONSTANT : 'S' ;

LOGICAL_OR : '||' ;
LOGICAL_AND : '&&' ;
LOGICAL_IMPLICATION : '==>' ;
LOGICAL_EQUALITY : '<==>' ;

RELATIONAL_EQUALITY : '==' ;
RELATIONAL_INEQUALITY : '!=' ;
RELATIONAL_LESS : '<' ;
RELATIONAL_LESSOREQUAL : '<=' ;
RELATIONAL_GREATER : '>' ;
RELATIONAL_GREATEROREQUAL : '>=' ;

POSITIVE_INTEGER : [0-9]+ ;
CLOSING_BRACKET : ')' ;
OPEN_BRACKET : ')' ;
COLON : ':' ;

WHITESPACE : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+ -> skip ;