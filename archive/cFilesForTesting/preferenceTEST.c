
    // V x C matrix of the according candidates for each preference of the according voter (votes[i][j] = 0 if not ranked)
    // (i.e. voter v ranks candidate c on preference rank p translates to "votes[v-1][p] == c")
    //assert (0 < C);
    //assert (0 < V);
    unsigned int r[S];
    unsigned int res = C;

    unsigned int count[C+1];
    unsigned int cc = C; // number of concurring candidates (C minus eliminated candidates)
    unsigned int weakest = 0;
    unsigned int choose = 0;
    unsigned int e = 0;
    unsigned int i = 0;
    unsigned int j = 0;
    unsigned int k = 0;
    unsigned int l = 0;
    unsigned int t = 0;

    for (i = 0; i < S; i++) {
        r[i] = C;
        // all Seats are ties at the start
    }

    unsigned int quota = 0;
    if (V % 2 != 0) {
        quota = (V - 1) / (S + 1);
    } else {
        quota = V / (S + 1);
    }
    unsigned int min = quota;
    while (res == C && 0 < cc && e < S && (S - e) < cc) {
        for (i = 0; i < C; i++) {
            count[i] = 0;
        }
        for (i = 0; i < V; i++) {
            for (j = 0; j < C; j++) {
                if (votes[i][1] == j) {
                    count[j]++;
                }
            }
        }
        for (i = 0; i < C && res == C; i++) {
            if (quota < count[i]) {
                res = i;
            }
        }
        if (res != C) {
            r[e] = res;
            e++;
            for (t = 0; t <= quota; t++) {
                i = 0;
                while (votes[i][1] != res) {
                    i++;
                }
                for (j = 1; j <= C; j++) {
                    votes[i][j] = 0;
                }
            }
            for (j = 0; j < V; j++) {
                for (k = 1; k <= C; k++) {
                    if (votes[j][k] == res) {
                        for (l = k; l < C; l++) {
                            votes[j][l] = votes[j][l + 1];
                        }
                        votes[j][C] = 0;
                    }
                }
            }
            res = C;
            cc--;
        } else {
            min = quota;
            weakest = 0;
            for (i = 1; i <= C; i++) {
                if (count[i] < min && count[i] != 0) { // count[i] != 0 makes sure that there are first preferences for i
                    min = count[i];
                    weakest = 1;
                } else if (count[i] == min) {
                    weakest++;
                }
            }
            choose = nondet_uint();
            assume (0 < choose && choose <= weakest); // randomly eliminate a weakest candidate

            weakest = 0;
            for (i = 0; i < C; i++) {
                if (count[i] == min) {
                    weakest++;
                }
                if (count[i] == min && weakest == choose) { // eliminate candidate i
                    for (j = 0; j < V; j++) {
                        for (k = 0; k < C; k++) {
                            if (votes[j][k] == i) {
                                for (l = k; l < C; l++) {
                                    votes[j][l] = votes[j][l + 1];
                                }
                                votes[j][C] = 0;
                            }
                        }
                    }
                    cc--;
                }
            }
        }
    }
    if (e < S - 1) {
        for (i = e; i < S && 0 < cc; i++) {
            res = 0;
            for (k = 1; k <= C && res == 0; k++) {
                for (j = 0; j < V && res == 0; j++) {
                    if (votes[j][1] == k) {
                        res = k;
                    }
                }
            }
            r[i] = res;
            for (j = 0; j < V; j++) {
                for (k = 1; k <= C; k++) {
                    if (votes[j][k] == res) {
                        for (l = k; l < C; l++) {
                            votes[j][l] = votes[j][l + 1];
                        }
                        votes[j][C] = 0;
                    }
                }
            }
            cc--;
        }
    }
    return r[0];
