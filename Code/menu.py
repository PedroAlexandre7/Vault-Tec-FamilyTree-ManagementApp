import json
import sys
import time

import commands
import add
import relation
import HELP

print("\n-------------------------------------------")
print("Fallout Shelter Family Tree Program v.0.76")
print("-------------------------------------------\n\n")
print("Select from the commands by selecting the number in parenthesis")
print("Type exit to exit the program")
print("Enter help for more information\n")

while True:
	command = input("""
---------
Commands
---------

(0) - Add person/Report Death
(1) - Search by name
(2) - Family Tree by name 
(3) - [ALPHA] Find if two persons are related
(4) - Full List
(5) - List of Dead
(6) - List of Alive
(7) - List of Childless
(8) - List by Birthtime
(9) - List by Deathtime\n\n""")
	print("")

	if command == "0":
		add.add()

	elif command == "1":
		name = input("Name: ")
		print("")

		name = name.lower()

		commands.search(name)

	elif command == "2":
		name = input("Name: ")
		print("")

		name = name.lower()

		commands.family_tree(name)

	elif command == "3":
		name = input("1st person: ")
		name2 = input("2nd person: ")
		print("")

		relation.find(name,name2)

	elif command == "4":
		commands.full_list()

	elif command == "5":
		commands.list_of_dead()

	elif command == "6":
		commands.list_of_alive()

	elif command == '7':
		commands.childless()

	elif command == '8':
		commands.birthtime()

	elif command == '9':
		commands.deathtime()

	elif command.lower() == "exit":
		sys.exit()

	elif "help" in command.lower():
		HELP.full(command)
	else:
		print("Error: Unkown command; try again\n")

	time.sleep(1)