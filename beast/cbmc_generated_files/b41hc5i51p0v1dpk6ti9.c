#include <stdlib.h>
#include <stdint.h>
#include <assert.h>


#define assume(x) __CPROVER_assume(x)
#define INVALID_VOTE 0xFFFFFFFE
#define INVALID_RESULT 0xFFFFFFFE






typedef struct VoteStruct {
unsigned int votes[MAX_VOTERS][MAX_CANDIDATES];
unsigned int amtVotes;
} VoteStruct;

typedef struct VoteResultStruct {
unsigned int result;
unsigned int amtResult;
} VoteResultStruct;



unsigned int nondet_uint();
int nondet_int();


VoteResultStruct voting(VoteStruct voteStruct, unsigned int V, unsigned int C, unsigned int S)
{
 unsigned int votes[MAX_VOTERS][MAX_CANDIDATES];
	for (int i = 0; i < V; ++i) {
 	for (int j = 0; j < C; ++j) {
     	votes[i][j] = voteStruct.votes[i][j];
     }
 }
unsigned int result;
//user generated code
    result = 5;
//end user generated code
	VoteResultStruct resultStruct;
 resultStruct.amtResult = nondet_uint();
 assume(resultStruct.amtResult == 1);
 resultStruct.result = nondet_uint();
	assume(resultStruct.result == result);

return resultStruct;
}


int main(int argc, char ** argv)
{
    //initializing Vote 1
    unsigned int V1 = nondet_uint();
    assume(V1 <= MAX_VOTERS);
    unsigned int S1 = nondet_uint();
    assume(S1 <= MAX_SEATS);
    unsigned int C1 = nondet_uint();
    assume(C1 <= MAX_CANDIDATES);
    VoteStruct votes1;
    votes1.amtVotes = nondet_uint();
    assume(votes1.amtVotes == V1);
    for (int i = 0; i < V1; ++i) {
        for (int j = 0; j < C1; ++j) {
            votes1.votes[i][j] = nondet_uint();
            assume(votes1.votes[i][j] >= 0);
            assume(votes1.votes[i][j] <= C1);
        }
    }
    for (int i = V1; i < MAX_VOTERS; ++i) {
        for (int j = 0; j < MAX_CANDIDATES; ++j) {
            votes1.votes[i][j] = nondet_uint();
            assume(votes1.votes[i][j] == INVALID_VOTE);
        }
    }
    for (int i = 0; i < V1; ++i) {
        unsigned int tmp[MAX_CANDIDATES];
        for (int k = 0; k < C1; ++k) {
            tmp[k] = 0;
        }
        for (int j = 0; j < C1; ++j) {
            for (int k = 0; k < C1; ++k) {
                if (votes1.votes[i][j] == k) {
                    assume(tmp[k] == 0);
                    tmp[k] = 1;
                }
            }
        }
    }

    //initializing Vote 2
    unsigned int V2 = nondet_uint();
    assume(V2 <= MAX_VOTERS);
    unsigned int S2 = nondet_uint();
    assume(S2 <= MAX_SEATS);
    unsigned int C2 = nondet_uint();
    assume(C2 <= MAX_CANDIDATES);
    VoteStruct votes2;
    votes2.amtVotes = nondet_uint();
    assume(votes2.amtVotes == V2);
    for (int i = 0; i < V2; ++i) {
        for (int j = 0; j < C2; ++j) {
            votes2.votes[i][j] = nondet_uint();
            assume(votes2.votes[i][j] >= 0);
            assume(votes2.votes[i][j] <= C2);
        }
    }
    for (int i = V2; i < MAX_VOTERS; ++i) {
        for (int j = 0; j < MAX_CANDIDATES; ++j) {
            votes2.votes[i][j] = nondet_uint();
            assume(votes2.votes[i][j] == INVALID_VOTE);
        }
    }
    for (int i = 0; i < V2; ++i) {
        unsigned int tmp[MAX_CANDIDATES];
        for (int k = 0; k < C2; ++k) {
            tmp[k] = 0;
        }
        for (int j = 0; j < C2; ++j) {
            for (int k = 0; k < C2; ++k) {
                if (votes2.votes[i][j] == k) {
                    assume(tmp[k] == 0);
                    tmp[k] = 1;
                }
            }
        }
    }

{
// VOTES1==VOTES2;
unsigned int voteCompare0 = votes1.amtVotes == votes2.amtVotes;
for (int i = 0; i < votes1.amtVotes; ++i) {
    for (int j = 0; j < MAX_CANDIDATES; ++j) {
        voteCompare0 &= votes1.votes[i][j] == votes2.votes[i][j];
    }
}

assume(voteCompare0);

}

VoteResultStruct result1 = voting(votes1, V1, C1, S1);

VoteResultStruct result2 = voting(votes2, V2, C2, S2);

{
// FALSE;
assert(0);

}

return 0;
}



