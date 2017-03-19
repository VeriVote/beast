#include <stdlib.h>
#include <stdint.h>
#include <assert.h>

int nondet_uint();
int nondet_int();

#define assert2(x, y) __CPROVER_assert(x, y)
#define assume(x) __CPROVER_assume(x)

unsigned int voteSumForCandidate(unsigned int arr[V][C], unsigned int candidate) {
	unsigned int sum = 0;
	for(unsigned int i = 0; i < V; ++i) {
		sum += arr[i][candidate];
	}
	return sum;
}
//Code of the user

//Preference: Voter ranks all candidates according to his preference
//Seats per party: Number of seats per party
unsigned int * voting(unsigned int votes[V][C]) { 
        static  int r[S];
        
	
        int i;
        
   for ( i = 0; i < S; ++i) {
      r[i] = 2;
   }
        
        return r;
} 
int main(int argc, char *argv[]) {
	//Symbolic Variables initialisation
	
	//voting-array and elect variable initialisation
	unsigned int votes1[V][C];
	for(unsigned int counter_0 = 0; counter_0 < V; counter_0++){
		for(unsigned int counter_1 = 0; counter_1 < C; counter_1++){
			votes1[counter_0][counter_1] = nondet_uint();
			assume((0 <= votes1[counter_0][counter_1]) && (votes1[counter_0][counter_1] < C));
			for (unsigned int j_prime = 0; j_prime < counter_1; j_prime++) {
				assume (votes1[counter_0][counter_1] != votes1[counter_0][j_prime]);
			}
		}
	}
	unsigned int *elect1 = voting(votes1);
	unsigned int votes2[V][C];
	for(unsigned int counter_0 = 0; counter_0 < V; counter_0++){
		for(unsigned int counter_1 = 0; counter_1 < C; counter_1++){
			votes2[counter_0][counter_1] = nondet_uint();
			assume((0 <= votes2[counter_0][counter_1]) && (votes2[counter_0][counter_1] < C));
			for (unsigned int j_prime = 0; j_prime < counter_1; j_prime++) {
				assume (votes2[counter_0][counter_1] != votes2[counter_0][j_prime]);
			}
		}
	}
	
	
	
	unsigned int *tmp2 = voting(votes2);
	
	unsigned int elect2[S];
	
	for (int i = 0; i < S; i++) {
		elect2[i] = tmp2[i];
	}
	
	//preproperties 
	
	unsigned int forAll_0 = 1;
	for(unsigned int v = 0; v < V && forAll_0; v++) {
		unsigned int comparison_0 = 1;
		for(unsigned int count_0 = 0; count_0 < C && comparison_0; ++count_0) {
			comparison_0 = votes1[v][count_0] == votes2[v][count_0];
		}
		forAll_0 = comparison_0;
	}
	assume(forAll_0);
	
	//postproperties 
	
	unsigned int comparison_1 = 1;
	for(unsigned int count_0 = 0; count_0 < S && comparison_1; ++count_0) {
		comparison_1 = elect1[count_0] != elect2[count_0];
	}
	assert(comparison_1);
}
