from commands import open_json_file

def print_relation(name1,name2,relation):
	print("%s is the %s of %s" % (name2,relation,name1))


def direct(name1,name2):
	data = open_json_file()

	for line in data:
		if name1 in line['name'] and line['alive']:
			if line['mother'] == name2:
				print_relation(name1,name2,"mother")
			if line['father'] == name2:
				print_relation(name1,name2,"father")
		if name2 in line['name'] and line['alive']:
			if line['mother'] == name1 or line['father'] == name1:
				if line['gender'] == 'm':
					print_relation(name1,name2,"son")
				else:
					print_relation(name1,name2,"daughter")


def siblings(name1,name2):
	data = open_json_file()

	for line in data:
		if name1 == line['name'] and line['alive']:
			mother1 = line['mother']
			father1 = line['father']
		if name2 == line['name'] and line['alive']:
			mother2 = line['mother']
			father2 = line['father']
			gender = line['gender']

	sibling = 0

	if mother1 == mother2:
		sibling += 0.5
	if father1 == father2:
		sibling += 0.5

	if sibling == 0.5:
		relation = "half-"
	else:
		relation = ""

	if gender == "m":
		print_relation(name1,name2,relation+"brother")
	else:
		print_relation(name1,name2,relation+"sister")

def married(name1,name2):
	data = open_json_file()

	for line in data:
		if line['alive']:
			if line['mother'] == name1 and line['father'] == name2:
				print_relation(name1,name2,"husband")
			elif line['mother'] == name2 and line['father'] == name1:
				print_relation(name1,name2,"wife")
 
def find(name1,name2):
	data = open_json_file()

	found = []

	for line in data:
		if name1.lower() in line['name'].lower() and line['alive']:
			rname1 = line['name']
			found.append(True)
		if name2.lower() in line['name'].lower() and line['alive']:
			rname2 = line['name']
			found.append(True)
	
	if len(found) == 2:
		direct(rname1,rname2)
		siblings(rname1,rname2)
		married(rname1,rname2)
	else:
		print("Error: At least one of these persons does not exist!")
