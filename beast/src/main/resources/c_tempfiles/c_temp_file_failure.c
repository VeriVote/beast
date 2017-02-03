#include <stdlib.h>
#include <stdint.h>
#include <assert.h>

int nondet_uint();

#define assert2(x, y) __CPROVER_assert(x, y)
#define assume(x) __CPROVER_assume(x)

#ifndef V
#define V 3
#endif

#ifndef C
#define C 3
#endif

unsigned int voting(unsigned int votes[V]) {
    unsigned int res[C + 1];
    unsigned int i = 0;

    for (i = 0; i < V; i++) {
        assume (0 < votes[i] && votes[i] <= C);
    }

    for (i = 0; i <= C; i++) {
        res[i] = 0;
    }
    for (i = 0; i < V; i++) {
        res[votes[i]]++;
    }
    unsigned int max = 0;
    unsigned int elect = 0;
    for (i = 1; i <= C; i++) {
        if (max < res[i]) {
            max = res[i];
            elect = i;
        } else {
            if (max == res[i]) {
                elect = 0;
            }
        }
    }
    return elect;
}

void anonymity(unsigned int votes1[V], unsigned int votes2[V],
	       unsigned int c, unsigned int d,
	       unsigned int v, unsigned int w) {
    assume (0 < c && c <= C);
    assume (0 < d && d <= C);
    assume (0 <= v && v < V);
    assume (0 <= w && w < V);
    unsigned int i = 0;

    for (i = 0; i < V; i++) {
        if (i != v && i != w) {
            assume (votes1[i] == votes2[i]);
        }
    }

    assume (votes1[v] == c && votes1[w] == d);
    assume (votes2[v] == d && votes2[w] == c);

    unsigned int elect1 = voting(votes1);
    unsigned int elect2 = voting(votes2);

    assert (elect1 != elect2);
}

int main(int argc, char *argv[]) {
    unsigned int i = 0;
    unsigned int v1[V];
    unsigned int v2[V];
    for (i = 0; i < V; i++) {
        v1[i] = nondet_uint();
	v2[i] = nondet_uint();
    }

    unsigned int candidate1 = nondet_uint();
    unsigned int candidate2 = nondet_uint();
    unsigned int voter1     = nondet_uint();
    unsigned int voter2     = nondet_uint();

    anonymity(v1, v2, candidate1, candidate2, voter1, voter2);
    return 0;
}

