def zero():
	print("""
Add person/Report Death
	
	This command will allow to add a person who has joined your vault or was born to one of your vault members.
	Enter their name, gender, mother (if known) and father (if known). There time of birth will be recorded auto-
	matically when you add a person.

	This command will also allow you to report a death. Enter their name and the program will do the rest, including
	reporting who their parents were and their time of death.
""")

def one():
	print("""
Search by name
	
	Enter the name of the person who wish to see more information about. You may also input a part of name to get listing
	of all the members that contain that part of their name. 

	Information provided includes name, gender, mother, father, time of birth. If the person has died, it will also report
	another listing that includes the time of death.

""")

def two():
	print("""
Family Tree by name

	Get the ancestors and descendents of a person by searching their name. Their parents will have the name of their parents
	indented and so on. The same for their descendents.

""")

def three():
	print("""
[ALPHA] Find if two persons are related

	This feature is still in alpha. In the future it will not the relationship between any two persons if present including,
	cousins, uncles, great grandparents and more.

	Currently this feature only checks for direct parents and children and siblings.

""")

def four():
	print("""
Full List

	Prints out the full list of all persons who had been a part of your vault.

""")

def five():
	print("""
List of Dead

	Prints out the full list of all persons who have died in your vault.

""")

def six():
	print("""
List of Alive

	Prints out the full list of all persons who are currently alive in your vault.

""")

def seven():
	print("""
List of Childless

	Prints out the full list of any persons who does not have any children. A sub
	list is also printed out the shows any living persons who do not have any childern.

""")

def eight():
	print("""
List by Birthtime

	Lists the persons of your vault by their birthtime and includes their time of birth. 

""")

def nine():
	print("""
List by Deathtime

	Lists the persons of your vault by their deathtime and includes their time of death. 


""")

def full(command):
	if "1" in command:
		one()
	elif "2" in command:
		two()
	elif "3" in command:
		three()
	elif "4" in command:
		four()
	elif "5" in command:
		five()
	elif "6" in command:
		six()
	elif "7" in command:
		seven()
	elif "8" in command:
		eight()
	elif "9" in command:
		nine()
	else:
		print("""
--------
  Help
--------

If you would like more information about a certain command, type help followed by
the number of the command.

If you would like to exit. Type exit.

""")