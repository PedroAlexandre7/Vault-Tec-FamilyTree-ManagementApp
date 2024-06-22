import json
from datetime import datetime

def open_json_file():
	file = open('Data/vaultDwellers.json')
	data = json.load(file)
	file.close()

	return data


def search(name):
	data = open_json_file()

	found = False

	for line in data:
		if name in line['name'].lower():
			print("%s\n" % line)
			found = True

	if not found:
		print("No person with name '%s' found" % name)

def list_of_dead():
	data = open_json_file()

	print('\nList of Dead:\n')
	for line in data:
		if line['alive'] == False:
			name = line['name']

			print(name)


def list_of_alive():
	data = open_json_file()

	alive = []
	dead = []

	print('\nList of Alive:\n')
	for line in data:
		if not line['alive']:
			dead.append(line['name'])
		if line['alive']:
			alive.append(line['name'])

	for person in alive:
		if person not in dead:
			print(person)


def full_list():
	data = open_json_file()

	print('\nFull List:\n')
	for line in data:
		if line['alive']:
			print(line['name'])

def ancestor(name, dash = "---"):
	data=open_json_file()
	for line in data:
		if name in line['name'] and line['alive']:

			mother = line['mother']
			if mother != "N/A":
				status = "A"

				for line2 in data:
					if mother == line2['name'] and not line2['alive']:
						status = "D"

				print("%s---> Mother: %s (%s)\n" % (dash, mother,status))
				ancestor(mother, (str(dash)+"---"))


			father = line['father']
			if father != "N/A":
				status = "A"

				for line2 in data:
					if father == line2['name'] and not line2['alive']:
						status = "D"

				print("%s---> Father: %s (%s)\n" % (dash, father,status))
				ancestor(father, (str(dash)+"---"))
			
			

def descendants(name, dash = "---"):
	data = open_json_file()

	for line in data:
		if name in line['father'] or name in line['mother']:
			if line['alive']:
				
				status = "A"

				for line2 in data:
					if line['name'] == line2['name'] and not line2['alive']:
						status = "D"


				if line['gender'] == 'm':
					print('%s---> Son: %s (%s)\n' % (dash,line['name'],status))
				else:
					print('%s---> Daughter: %s (%s)\n' % (dash, line['name'],status))
				descendants(line['name'],(str(dash)+"---"))

def family_tree(name):
	data = open_json_file()

	found = False

	for line in data:
		if name in line['name'].lower() and line['alive']:
			found = True

			status = "A"

			for line2 in data:
				if line['name'] == line2['name'] and not line2['alive']:
					status = "D"


			print("\nFamily Tree for %s (%s)\n" % (line['name'],status))

			print("---> Ancestors:\n")
			ancestor(line['name'])

			print("---> Descendants:\n")
			descendants(line['name'])

	if not found:
		print("No person with name '%s' found" % name)

def childless():
	data = open_json_file()

	parents = []
	people = []

	for line in data:
		mother = line['mother']
		father = line['father']

		if mother not in parents:
			parents.append(mother)
		if father not in parents:
			parents.append(father)

		people.append([line['name'],line['alive']])
	

	print('\nList of Childless:\n')

	for i in range(len(people)):
		if people[i][0] not in parents and people[i][1]:
			print(people[i][0])


	print('\nList of Childless and alive:\n')

	dead = []

	for line in data:
		if not line['alive']:
			dead.append(line['name'])

	for i in range(len(people)):
		if people[i][0] not in parents and people[i][1] and people[i][0] not in dead:
			print(people[i][0])

def birthtime():
	data = open_json_file()

	for line in data:
		if line['alive']:
			print("%s - %s" % (line['name'],line['time_of_birth']))

def deathtime():
	data = open_json_file()

	for line in data:
		if not line['alive']:
			print("%s - %s" % (line['name'],line['time_of_death']))


