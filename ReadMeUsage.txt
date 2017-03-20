Welcome to BEAST (Bounded Election Attribute Structuring Tool) is a tool in which you can check if voting methodes satisfy 
specific Properties.


Before usage, read this a bit, to get a small understanding what bounded model checking is: http://www.cprover.org/cbmc/doc/manual.pdf

___________
Dependencies to use it: 

Linux: you have to have gcc installed.
#Windows: you have to have Visual Studio (including the c++ files) installed#
___________


How to use it:

Start the BEAST.jar, that lays in the folder "/beast/core/"

A window called "Parameter Editor" opens. This window is the main window of BEAST, and if you close it, you close BEAST. 
From here, you can start all other windows that you need.

First of all, if the some options (like language, or font size in Text Areas) aren't fitting you, have a look at the tab
"Project", where you will find the sub option "Options". Have a look in there, you you can see, what optios exist.
If you want other options implemented, open a pull request on our GitHub-page (https://github.com/NikolaiLMS/PSE-Wahlverfahren-Implementierung), 
so maybe someone from our group can implement it for you, or just jump in and implement it yourself.


Now a step by step explanation on how to check a voting type:

1) click on the button "Window" on the menu bar and click "Show/Hide C Editor". Now a window will open.

2) In this so called CEditor, you first have to select the basic type of your voting type. For that, click "File", and then
   "New Election description". Another small window opens, in which you can choose which type of voting you want. After you choose
   the type by selection what you want, give you new Election Description a name, and click "create".

3) Depending on what you choose, the methode head of "voting" might have changed. So have a look at what you voting methode
   has to return, and then just implement whatever you want inside the voting function. You can even implement other functions 
   outside of the voting function, and use it to help you with your voting function. Please notice, that you can't implement
   a main function, because that already exists implicitly. If you want to include header files, you can do this like you know how
   to do it in C. If you want to write your own *.h and *.c files, just place them in the folder "/beast/core/user_includes/" in the 
   program Folder. They get compiled with your program automatically, so you don't have to worry about that.

4) Now, that your voting program is finished, head back to the "Parameter Editor", and mimimize the C-Editor again, if you want to.
   Now, open up the "Property List". Here. click "New". A new Property appeared. Click on the wrench next to it, which opens up the 
   "PropertyEditor". For an in depth explanation of the Boolean_Expression Language you have to use here, click the "?" in the menu bar.
   

5) Now, that the PropertyDescription is done, head back to the "Property List" and check the little box next to "Analyse", to signal to BEAST,
   that you want this property checked.

6) Now, go back to the "Parameter Editor". The last thing to do before you can check your Voting now, is to set the values in the "Parameter Editor".
   Here, you can set the amount of voters, candidates, and seats that are participating in this election.
   Smaller values might cause a problem with cbmc, if your Election Description relies on a bit bigger numbers. But be warned, cbmc can take quite some time
   to finish, and take up a lot of ram doing so. If you want to stop your check after a specific time, if it hasen't finished by then, you can 
   use the Timeout Value. A Timeout of 0 mean, that there is no timeout.

7) Now, that you set the parameters, click on the "play" button in the menu bar of the "Parameter Editor" to start the analysis. If you want to stop it, just
   click the "stop" button.
   
8) When the analysis has finished, you can go check out the "Property List" again. Here, all Properties you specified are now  either green (the property is supported)
   red ( the property is not supported) yellow (the checking was interrupted, and no result is reached) or purple (there was a generic error).
   In case the property is red, click on it to see the votes, and the result of your voting methode with these, for each election you simulated.

9) Now tweak your voting methode, or add more properties, and start all over again.

Have fun
 


