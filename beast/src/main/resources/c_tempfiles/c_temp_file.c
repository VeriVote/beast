#include <stdlib.h>
#include <stdint.h>
#include <assert.h>int nondet_uint();
#define assert2(x, y) __CPROVER_assert(x, y)
#define assume(x) __CPROVER_assume(x)#ifndef V
#define V 3
#endif

#ifndef C
#define C 3
 #endif

#ifndef S
#define S 3
 #endif

unsigned int voting(unsigned int voters[V], unsigned int candidates[C], unsigned int seats[S]) {
return 0;
}
void test1() {
unsigned int c;
assume(0 <= c <= C);
unsigned int v;
assume(0 <= v <= C);
unsigned int votes1[V];
unsigned int votes2[V];
for(unsigned int i = 0; i < V; ++i) {
votes1[i] = nondet_uint();
}
for(unsigned int i = 0; i < V; ++i) {
votes2[i] = nondet_uint();
}
unsigned int forall_0 = 1;
for(unsigned int v = 0; v < V && forall_0; ++v) {
unsigned int existsOne_1 = 0;
for(unsigned int c = 0; c < C && !existsOne_1; ++c) {
unsigned int compare_2 = 1;
compare_2 = c == votes2[v];
unsigned int compare_3 = 1;
compare_3 = calculateVoteSum(c) >= 3;
unsigned int compare_4 = 1;
compare_4 = c < 2;
unsigned int impl_5 = !compare_3 || RHS;
unsigned int and_6 = compare_2 && RHS;
existsOne_1 = and_6;
}
forall_0 = existsOne_1;
}
assume(forall_0);
unsigned int compare_7 = 1;
for(unsigned int count_8 = 0; count_8 < V && compare_7; ++count_8) {
compare_7 = votes2[count_8] == votes1[count_8];
}
assert(compare_7);
}
int main() {
test1();
return 0;
}
