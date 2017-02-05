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