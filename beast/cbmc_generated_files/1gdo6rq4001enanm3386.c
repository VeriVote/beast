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
unsigned int result[MAX_CANDIDATES];
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
unsigned int result[C];
//user generated code
    unsigned int i = 0;
    unsigned int j = 0;


    for (i = 0; i < C; i++) {
        result[i] = 0;
    }
    for (i = 0; i < V; i++) {
        for (j = 0; j < C; j++) {
                result[votes[i][j]] += (C - j) - 1;
        }
    }
//end user generated code
	VoteResultStruct resultStruct;
	resultStruct.amtResult == nondet_uint();
 assume(resultStruct.amtResult == C);
	for(int i = 0; i < C; ++i) {
 	resultStruct.result[i] = nondet_uint();
		assume(resultStruct.result[i] == result[i]);
	}
	for(int i = C; i < MAX_CANDIDATES; ++i) {
 	resultStruct.result[i] = nondet_uint();
		assume(resultStruct.result[i] == INVALID_RESULT);
	}

return resultStruct;
}


int main(int argc, char ** argv)
{
unsigned int v1 = nondet_uint();

assume(v1 < V1);
assume(v1 < V2);
assume(v1 < V3);
unsigned int v2 = nondet_uint();

assume(v2 < V1);
assume(v2 < V2);
assume(v2 < V3);
unsigned int c = nondet_uint();

assume(c < C1);
assume(c < C2);
assume(c < C3);
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

    //initializing Vote 3
    unsigned int V3 = nondet_uint();
    assume(V3 <= MAX_VOTERS);
    unsigned int S3 = nondet_uint();
    assume(S3 <= MAX_SEATS);
    unsigned int C3 = nondet_uint();
    assume(C3 <= MAX_CANDIDATES);
    VoteStruct votes3;
    votes3.amtVotes = nondet_uint();
    assume(votes3.amtVotes == V3);
    for (int i = 0; i < V3; ++i) {
        for (int j = 0; j < C3; ++j) {
            votes3.votes[i][j] = nondet_uint();
            assume(votes3.votes[i][j] >= 0);
            assume(votes3.votes[i][j] <= C3);
        }
    }
    for (int i = V3; i < MAX_VOTERS; ++i) {
        for (int j = 0; j < MAX_CANDIDATES; ++j) {
            votes3.votes[i][j] = nondet_uint();
            assume(votes3.votes[i][j] == INVALID_VOTE);
        }
    }
    for (int i = 0; i < V3; ++i) {
        unsigned int tmp[MAX_CANDIDATES];
        for (int k = 0; k < C3; ++k) {
            tmp[k] = 0;
        }
        for (int j = 0; j < C3; ++j) {
            for (int k = 0; k < C3; ++k) {
                if (votes3.votes[i][j] == k) {
                    assume(tmp[k] == 0);
                    tmp[k] = 1;
                }
            }
        }
    }

{
// [[VOTES2,VOTES3]]==PERM(VOTES1);
        VoteStruct voteSequence0;
        voteSequence0.amtVotes = nondet_uint();
        assume(voteSequence0.amtVotes == votes2.amtVotes + votes3.amtVotes);
        unsigned int pos = 0;
        for (unsigned int i = 0; i < votes2.amtVotes && i < MAX_VOTERS; ++pos) {
            for (int j = 0; j < MAX_CANDIDATES; ++j) {
                voteSequence0.votes[pos][j] = nondet_uint();
                assume(voteSequence0.votes[pos][j] == votes2.votes[i][j]);
            }
            pos++;
        }
        for (unsigned int i = 0; i < votes3.amtVotes && i < MAX_VOTERS; ++pos) {
            for (int j = 0; j < MAX_CANDIDATES; ++j) {
                voteSequence0.votes[pos][j] = nondet_uint();
                assume(voteSequence0.votes[pos][j] == votes3.votes[i][j]);
            }
            pos++;
        }

    VoteStruct votePermutation1;
    votePermutation1.amtVotes = nondet_uint();
    assume(votePermutation1.amtVotes == votes1.amtVotes);
    unsigned int permutationIndices[MAX_VOTERS];
    for (int i = 0; i < votes1.amtVotes && i < MAX_VOTERS; ++i) {
        permutationIndices[i] = nondet_uint();
        assume(permutationIndices[i] >= 0);
        assume(permutationIndices[i] < votes1.amtVotes);
    }
    for (int i = 0; i < votes1.amtVotes - 1 && i < MAX_VOTERS; ++i) {
        for (int j = i + 1; j < votes1.amtVotes && j < MAX_VOTERS; ++j) {
            assume(permutationIndices[i] != permutationIndices[j]);
        }
    }
    for (int i = 0; i < votes1.amtVotes - 1 && i < MAX_VOTERS; ++i) {
        for (int j = 0; j < MAX_CANDIDATES; ++j) {
            assume(votePermutation1.votes[i][j] == votes1.votes[permutationIndices[i]][j]);
        }
    }
unsigned int voteCompare2 = voteSequence0.amtVotes == votePermutation1.amtVotes;
for (int i = 0; i < voteSequence0.amtVotes; ++i) {
    for (int j = 0; j < MAX_CANDIDATES; ++j) {
        voteCompare2 &= voteSequence0.votes[i][j] == votePermutation1.votes[i][j];
    }
}

assume(voteCompare2);

}

VoteResultStruct result1 = voting(votes1, V1, C1, S1);

VoteResultStruct result2 = voting(votes2, V2, C2, S2);

VoteResultStruct result3 = voting(votes3, V3, C3, S3);

{
// (!EMPTY(CUT(ELECT2,ELECT3)))==>(ELECT1==CUT(ELECT2,ELECT3));
    VoteResultStruct electIntersection0;
    unsigned int count = 0;
    for (int i = 0; i < result2.amtResult; ++i) {
        unsigned int contains = 0;
        for(int j = 0; j < result3.amtResult; ++j) {
            contains |= result2.result[i] == result3.result[j];
        }
        if(contains) {
            electIntersection0.result[count] = nondet_uint();
            assume(electIntersection0.result[count] == result2.result[i]);
            count++;
        }
    }
    electIntersection0.amtResult = nondet_uint();
    assume(electIntersection0.amtResult == count);
    for (int i = count; i < MAX_CANDIDATES; ++i) {
        electIntersection0.result[i] = nondet_uint();
        assume(electIntersection0.result[i] == 3);
    }

unsigned int isEmptyelectIntersection01 = electIntersection0.amtResult == 0;

unsigned int not2 = !isEmptyelectIntersection01;
    VoteResultStruct electIntersection3;
    unsigned int count = 0;
    for (int i = 0; i < result2.amtResult; ++i) {
        unsigned int contains = 0;
        for(int j = 0; j < result3.amtResult; ++j) {
            contains |= result2.result[i] == result3.result[j];
        }
        if(contains) {
            electIntersection3.result[count] = nondet_uint();
            assume(electIntersection3.result[count] == result2.result[i]);
            count++;
        }
    }
    electIntersection3.amtResult = nondet_uint();
    assume(electIntersection3.amtResult == count);
    for (int i = count; i < MAX_CANDIDATES; ++i) {
        electIntersection3.result[i] = nondet_uint();
        assume(electIntersection3.result[i] == 3);
    }

unsigned int electCompare4 = result1.amtResult == electIntersection3.amtResult;
for (int i = 0; i < result1.amtResult; ++i) {
    electCompare4 &= result1.result[i] == electIntersection3.result[i];
}

unsigned int combined5 = !not2 || electCompare4;

assert(combined5);

}

{
// FALSE;
assert(0);

}

return 0;
}



