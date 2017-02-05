
 

   unsigned int res[C];
    unsigned int i = 0;
    unsigned int j = 0;

	// lower and upper Bound have to be set outside 	   
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
                elect = C;
            }
        }
    }
    return elect;

