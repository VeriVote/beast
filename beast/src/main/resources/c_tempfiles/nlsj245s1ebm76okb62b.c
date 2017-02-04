#include <stdlib.h>
#include <stdint.h>
#include <assert.h>

int nondet_uint();
int nondet_int();

#define assert2(x, y) __CPROVER_assert(x, y)
#define assume(x) __CPROVER_assume(x)

int main(int argc, char *argv[]) {
	//Symbolic Variables initialisation
	unsigned int u = nondet_uint();
	assume(0 <= u && u < V);
	unsigned int w = nondet_uint();
	assume(0 <= w && w < V);
	
	//voting-array and elect variable initialisation
	unsigned int votes1[V][C];
	for(unsigned int i = 0; i < V; ++i){
		for(unsigned int j = 0; j < C; ++j){
			assume(0 <= votes1[i][j] < 1);
		}
	}
	unsigned int elect1;
	elect1 = voting(votes1);
	unsigned int votes2[V][C];
	for(unsigned int i = 0; i < V; ++i){
		for(unsigned int j = 0; j < C; ++j){
			assume(0 <= votes2[i][j] < 1);
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
		unsigned int comparison_2 = 1;
		for(unsigned int count_0 = 0; count_0 < V && comparison_2; ++count_0) {
			comparison_2 = votes1[i][count_0] == votes2[i][count_0];
		}
		unsigned int implication_0 = (!(comparison_2) || (comparison_1));
		unsigned int and_0 = ((implication_0) && (comparison_0));
		forAll_0 = and_0;
	}
	assume(forAll_0);
	
	//postproperties 
	
	unsigned int comparison_3 = 1;
	comparison_3 = elect1 == elect2;
	assert(comparison_3);
}
//Code of the user
votingcode
abalsdf
