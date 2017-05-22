from datetime import datetime
import json

class Person():
	def __init__(self, name, gender, mother='N/A', father='N/A', alive=True, time_of_birth=datetime.now()):
		self.name = name
		self.gender = gender
		self.mother = mother
		self.father = father
		self.alive = alive
		self.time_of_birth = time_of_birth

	def born(self, name, gender, mother='N/A', father='N/A', alive=True, time_of_birth=datetime.now()):
		time_of_birth = datetime.now()

		print("\n%s was born on %s to %s and %s" % (name,time_of_birth,mother,father))

		dic = {'name':name,'gender':gender,'mother':mother,'father':father,'alive':alive,'time_of_birth':str(time_of_birth)}
		info = json.dumps(dic)

		file = open('fallout.json', 'r')
		lines = file.readlines()
		file.close()

		file = open('fallout.json', 'w')
		for l in lines:
			if l != "]":
				file.write(l)

		file.write(",%s\n]" % info)
		file.close()

	def die(self):
		return datetime.now()

def add():
	state = input("""
(0) - New person was born or joined vault
(1) - Person has died\n\n
""")
	if state == '0':
		name = input("Name?: ")
		gender = input("Gender (m for male) (f for female): ")
		mother = input("Mother? (leave empty if unknown): ")
		father= input("Father? (leave empty if unknown): ")
		if mother == "" and father == "":
			person = Person(name,gender)
			person.born(name,gender)
		else:
			person = Person(name,gender,mother,father)
			person.born(name,gender,mother,father)
	elif state == '1':
		name = input("Name?: ")
		file = open('fallout.json')
		data = json.load(file)
		for line in data:
			if line['name'] == name:
				gender = line['gender']
				mother = line['mother']
				father = line['father']
				time_of_birth = line['time_of_birth']

				file.close()

				person = Person(name,gender,mother,father,False,time_of_birth)
				time_of_death = person.die()

				if gender == "m":
					pronoun = 'His'
				else:
					pronoun = 'Her'

				print("\n%s has died on %s. %s parents were %s and %s" %(name,time_of_death,pronoun,mother,father))

				dic = {'name':name,'gender':gender,'mother':mother,'father':father,'alive':False,'time_of_birth':str(time_of_birth),'time_of_death':str(time_of_death)}
				info = json.dumps(dic)

				file = open('fallout.json', 'r')
				lines = file.readlines()
				file.close()

				file = open('fallout.json', 'w')
				for l in lines:
					if l != "]":
						file.write(l)

				file.write(",%s\n]" % info)
				file.close()
	elif state.lower() == "exit":
		return
	else:
		print("Error: Unkown Command\n")
