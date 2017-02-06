#include <stdlib.h>
#include <stdint.h>
#include <assert.h>

int nondet_uint();
int nondet_int();

#define assert2(x, y) __CPROVER_assert(x, y)
#define assume(x) __CPROVER_assume(x)

//Code of the user
//Single-choice: W?hler stimmt jeweils f?r einen Kandidaten
//Single-choice: Ein gew?hlter Kandidat oder unentschieden
unsigned int  voting(unsigned int votes[V]) { 
    unsigned int res[C + 1];
    unsigned int i = 0;

    for (i = 0; i <= C; i++) {
        res[i] = 0;
    }
    for (i = 0; i < V; i++) {
        res[votes[i]]++;
    }
    unsigned int max = 0;
    unsigned int elect = 0;
    for (i = 0; i < C; i++) {
        if (max < res[i]) {
            max = res[i];
            elect = i;
        } else {
            if (max == res[i]) {
                elect = C;
            }
        }
    }
    return elect;
} 
int main(int argc, char *argv[]) {
	//Symbolic Variables initialisation
	unsigned int w = nondet_uint();
	assume(0 <= w && w < V);
	unsigned int v = nondet_uint();
	assume(0 <= v && v < V);
	unsigned int d = nondet_uint();
	assume(0 <= d && d < C);
	unsigned int c = nondet_uint();
	assume(0 <= c && c < C);
	
	//voting-array and elect variable initialisation
	unsigned int votes1[V];
	for(unsigned int i = 0; i < V; ++i){
		assume(0 <= votes1[i] < C);
	}
	unsigned int elect1;
	elect1 = voting(votes1);
	unsigned int votes2[V];
	for(unsigned int i = 0; i < V; ++i){
		assume(0 <= votes2[i] < C);
	}
	unsigned int elect2;
	elect2 = voting(votes2);
	
	//preproperties 
	
	unsigned int forAll_0 = 1;
	for(unsigned int i = 0; i < V && forAll_0; i++) {
		unsigned int comparison_0 = 1;
		comparison_0 = v != i;
		unsigned int comparison_1 = 1;
		comparison_1 = i != w;
		unsigned int and_0 = ((comparison_1) && (comparison_0));
		unsigned int comparison_2 = 1;
		comparison_2 = votes1[i] == votes2[i];
		unsigned int implication_0 = (!(comparison_2) || (and_0));
		forAll_0 = implication_0;
	}
	assume(forAll_0);
	unsigned int comparison_3 = 1;
	comparison_3 = votes1[v] == c;
	unsigned int comparison_4 = 1;
	comparison_4 = votes2[w] == d;
	unsigned int and_1 = ((comparison_4) && (comparison_3));
	assume(and_1);
	unsigned int comparison_5 = 1;
	comparison_5 = votes2[v] == d;
	unsigned int comparison_6 = 1;
	comparison_6 = votes2[w] == c;
	unsigned int and_2 = ((comparison_6) && (comparison_5));
	assume(and_2);
	
	//postproperties 
	
	unsigned int comparison_7 = 1;
	comparison_7 = elect1 == elect2;
	assert(comparison_7);
}
