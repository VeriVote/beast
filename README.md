# PSE-Wahlverfahren-Implementierung

Welcome to BEAST (Bounded Election Attribute Structuring Tool) is a tool in which you can check if voting methodes satisfy 
specific Properties.

If you want information on how to use or extend BEAST, please read the other two ReadMe files.



What is BEAST?

BEAST is a tool used to check Electiontypes for basic properties that they should have.

For this, we use CBMC, a bounded model checker.

We do this, by making the user descripe his voting routine in c-code, and create the properties out of a "boolean expression language".

We combine these two to one "\*.c" File, which we then send to cbmc to check. 
If we find an assertion Error that way, we extract that data that error holds, and present it to the user, so he can change his election description.

BEAST is written in Java and runs on Linux (tested on: Ubuntu 16, Arch, Mint) and Windows (tested on: 10, 7)
