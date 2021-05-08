#include <stdint.h>

unsigned int nondet_uint();

// macros
#define assert(x, y) __CPROVER_assert(x, y)
#define assume(x) __CPROVER_assume(x)

typedef struct ResultArr {
	unsigned int result[C];
	unsigned int amt;
} ResultArr;

typedef struct VotesArr {
	unsigned int votes[V];
	unsigned int amt;
} VotesArr;

ResultArr voting(VotesArr votes) {
	ResultArr res = { 0 };

	for (unsigned int i = 0; i < votes.amt; ++i) {
		res.result[votes.votes[i]]++;;
	}

	return res;
}

int main() {
	VotesArr votes1;
	votes1.amt = nondet_uint();
	assume(votes1.amt <= V);
	for (unsigned int i = 0; i < V; ++i) {
		votes1.votes[i] = nondet_uint();
		assume(votes1.votes[i] < C);
	}
	assert(0, "");
	ResultArr res = voting(votes1);

	

	return 1;
}
