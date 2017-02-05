//PRE
//symbolicVariables:
// v, w type VOTER
// c, d type CANDIDATE

FOR_ALL_VOTERS(i) : ((v!=i && i != w ) ==> VOTES1(i) == VOTES2(i)) ;
VOTES1(v) == c && VOTES2(w) == d;
VOTES2(v) == d && VOTES2(w) == c;

//POST
ELECT1 == ELECT2;