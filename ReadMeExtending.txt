So you want to extend cbmc:

There are multiple things you might want to extend, change or just modify something, so we will give you a small rundown
of the most important things you might want to change:


- change the checker)
Right now we are using cbmc to check the ElectionDescriptions and properties, but it is really simple to add other checkers
for that.

1) There are multiple abstract classes you have to implement to add another checker
   All these classes are from "edu.pse.beast.propertychecker" :

	- Checker.java (communicates with the program)

	- CheckerFactory.java (creates fitting checkers)

	- Result.java (saves the result and processes it, so it can be diplayed)

	Also, you have to chose an unique checkerID, with which you have to register your CheckerFactory.java at the
	CheckerFactoryFactory.java (have a look at the init() function)

If that is all done, you can simply start BEAST and chose between cbmc, and your newly implemented checker.
	