#include <stdlib.h>
#include <stdint.h>
#include <assert.h>
#define assume(x) __CPROVER_assume(x)
typedef struct VoteStruct {
    unsigned int votes[V][C];
    unsigned int amtVotes;
} VoteStruct;
typedef struct VoteResultStruct {
    unsigned int result[C];
    unsigned int amtResult;
} VoteResultStruct;
unsigned int nondet_uint();
int nondet_int();
VoteResultStruct voting(VoteStruct voteStruct)
{
    unsigned int votes[V][C];
    for (int i = 0; i < voteStruct.amtVotes; ++i) {
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
            if (votes[i][j] < C) {
                result[votes[i][j]] += (C - j) - 1;
            }
        }
    }
    //end user generated code
    VoteResultStruct resultStruct;
    resultStruct.amtResult == nondet_uint();
    for (int i = 0; i < C; ++i) {
        resultStruct.result[i] = nondet_uint();
        assume(resultStruct.result[i] == result[i]);
    }

    return resultStruct;
}
int main(int argc, char** argv)
{
    VoteStruct votes;
    votes.amtVotes = 2;
    votes.votes[0][0] = 1;
    votes.votes[0][1] = 0;
    votes.votes[0][2] = 1;
    votes.votes[0][3] = 1;
    votes.votes[0][4] = 1;
    votes.votes[0][5] = 0;
    votes.votes[0][6] = 0;
    votes.votes[0][7] = 0;
    votes.votes[0][8] = 1;
    votes.votes[0][9] = 1;
    votes.votes[1][0] = 1;
    votes.votes[1][1] = 1;
    votes.votes[1][2] = 1;
    votes.votes[1][3] = 0;
    votes.votes[1][4] = 1;
    votes.votes[1][5] = 0;
    votes.votes[1][6] = 0;
    votes.votes[1][7] = 0;
    votes.votes[1][8] = 1;
    votes.votes[1][9] = 1;
    VoteResultStruct elect0 = voting(votes);
    assert(0);
    return 0;
}