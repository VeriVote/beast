# BEAST - Automated Election Verification via Bounded Model Checking

Welcome to BEAST (Bounded Election Attribute Structuring Tool). This is a tool with which you can check whether voting methods satisfy
specific properties (e.g., some fairness properties or that the winner received a majority of the votes).
The background of this work and possible extensions are described in the following paper: https://formal.kastel.kit.edu/biblio/?lang=en&key=BeckertBormerEA2016

If you want more information on how to use or extend BEAST, please read the other two ReadMe files.



## What is BEAST?

BEAST is a tool used to check election types for basic properties that they should have.

For this, we use [CBMC](https://www.cprover.org/cbmc/), a bounded model checker.

We do this by making the user describe their voting routine as a C program and then create the properties in a customized boolean expression language.

We combine these two inputs into one C file, which we then send to CBMC for verification.
If we find an assertion error, we extract that data holding the violation and present it to the user, such that they can change their election description.

BEAST is written in Java and runs on Linux (tested on: Ubuntu 16, Arch, Mint) and Windows (tested on: 10, 7)

## Contributors

This software was designed and implemented during the bachelor course
[Software Engineering Practice](https://formal.kastel.kit.edu/teaching/pse/201617/voting/) by:

* Justin Hecht
* Niels Hanselmann
* Holger Klein
* Nikolai Schnell
* Lukas Stapelbroek
* Jonas Wohnig

For more information, please contact [Michael Kirsten](https://formal.kastel.kit.edu/~kirsten/?lang=en).
