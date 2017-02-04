#include <stdlib.h>
#include <stdint.h>
#include <assert.h>

int nondet_uint();
int nondet_int();

#define assert2(x, y) __CPROVER_assert(x, y)
#define assume(x) __CPROVER_assume(x)

#ifndef V
#define V 3
#endif

#ifndef C
#define C 3
#endif


unsigned int voting(unsigned int votes[V][C]) {
    unsigned int res[C];
    unsigned int i = 0;
    unsigned int j = 0;
    //assert (0 < V);
    //assert (0 < C);
    for (i = 0; i < V; i++) {
        for (j = 0; j < C; j++) {
            assume (0 <= votes[i][j]);
            assume (votes[i][j] <= 1);
        }
    }
    for (i = 0; i < C; i++) {
        res[i] = 0;
    }
    for (i = 0; i < V; i++) {
        for (j = 0; j < C; j++) {
            res[j] = res[j] + votes[i][j];
        }
    }
    unsigned int max = 0;
    unsigned int elect = 0;
    for (i = 0; i < C; i++) {
        if (max < res[i]) {
            max = res[i];
            elect = i+1;
        } else {
            if (max == res[i]) {
                elect = 0;
            }
        }
    }
    return elect;
}


int main(int argc, char *argv[]) {
	//Symbolic Variables initialisation
	unsigned int v = nondet_uint();
	assume(0 <= v && v < V);
	unsigned int u = nondet_uint();
	assume(0 <= u && u < V);
	unsigned int w = nondet_uint();
	assume(0 <= w && w < V);
	
	//voting-array and elect variable initialisation
	unsigned int votes1[V][C];
	for(unsigned int i = 0; i < V; ++i){
		for(unsigned int j = 0; j < C; ++j){
		    votes1[i][j] = nondet_int();
			assume(0 <= votes1[i][j] < V);
		}
	}
	unsigned int elect1;
	elect1 = voting(votes1);
	unsigned int votes2[V][C];
	for(unsigned int i = 0; i < V; ++i){
		for(unsigned int j = 0; j < C; ++j){
		votes2[i][j] = nondet_int();
			assume(0 <= votes2[i][j] < V);
		}
	}
	unsigned int elect2;
	elect2 = voting(votes2);
	
	//preproperties 
	
	unsigned int forAll_0 = 1;
	for(unsigned int i = 0; i < V && forAll_0; i++) {
		unsigned int comparison_0 = 1;
		comparison_0 = i != u;
		unsigned int comparison_1 = 1;
		comparison_1 = i != w;
		unsigned int and_0 = ((comparison_1) && (comparison_0));
		unsigned int comparison_2 = 1;
		for(unsigned int count_0 = 0; count_0 < V && comparison_2; ++count_0) {
			comparison_2 = votes1[i][count_0] == votes2[i][count_0];
		}
		unsigned int implication_0 = (!(comparison_2) || (and_0));
		forAll_0 = implication_0;
	}
	forAll_0 = 1;
	assume(forAll_0);
	
	//postproperties 
	
	unsigned int comparison_3 = 1;
	comparison_3 = elect1 == elect2;
	comparison_3 = 0;
	assert(comparison_3);
}
