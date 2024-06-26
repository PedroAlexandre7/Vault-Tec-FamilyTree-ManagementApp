import kotlinx.coroutines.delay
import kotlin.system.exitProcess

class VaultTecFamilyTreeMenu constructor() {

    companion object {

        @Volatile
        private var instance: VaultTecFamilyTreeMenu? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: VaultTecFamilyTreeMenu().also { instance = it }
            }
    }

    private val timeBetweenText: Long = 1000
    private val vault = VaultTecVault.getInstance()

    private val options: Map<Int, String> = linkedMapOf(
        Pair(1, "Add dweller"),
        Pair(2, "Report Death"),
        Pair(3, "Search by name"),
        Pair(4, "Family tree by name"),
        Pair(0, "Exit Menu")
    )


    suspend fun start() {
        println("------------------------------------------")
        println("Vault-Tec Family Tree Management Menu v0.3")
        println("------------------------------------------\n")

        suspend fun mainLoop() {
            while (true) {
                val opt = commandPrompt()

                when (opt) {
                    0 -> return
                    1 -> addDweller()
                    2 -> reportDeath()
                }
            }
        }
        mainLoop()

        vault.saveData()
    }

    private suspend fun commandPrompt(): Int {
        println("--------")
        println("Commands")
        println("--------\n")

        options.forEach { println("${it.key}. ${it.value}") }
        println()

        val input = readlnOrNull()?.toIntOrNull()

        if (input == null) {
            println("Please enter a number...\n")
            delay(timeBetweenText)
            return commandPrompt()
        }
        if (input < 0 || input > options.size) {
            println("Insert a valid number...\n")
            delay(timeBetweenText)
            return commandPrompt()
        }
        println()
        return input
    }

    private fun exit() {
        exitProcess(0)
    }

    private fun answeredYes(acceptEnterAsYes: Boolean = false): Boolean {//0 don't accept enter, 1 for yes, 2 for no
        val input = readln().lowercase()
        if (input == "y" || input == "yes" || (acceptEnterAsYes && input.isEmpty()))
            return true
        else if (input == "n" || input == "no")
            return false
        return answeredYes()

    }
    private suspend fun getName(): String {
        print("\tName: ")
        val name = readlnOrNull()
        if (name.isNullOrEmpty()) {
            println("\tInvalid name...")
            delay(timeBetweenText)
            return getName()
        }
        return name
    }

    private suspend fun addDweller() {
        println("-----------")
        println("Add Dweller")
        println("-----------\n")

        suspend fun getNameAvoidDuplicate(): String {
            val name = getName()
            if (vault.getDwellerId(name) != null) {
                print("\tRepeated name, do you wan't to change dweller's name (y/n)? ")
                if (answeredYes()) getNameAvoidDuplicate()
            }
            return name
        }

        suspend fun getSex(): Sex {
            print("\tSex (m for male) (f for female): ")
            val sex: String? = readlnOrNull()
            if (sex.isNullOrEmpty() || (sex != "m" && sex != "f" && sex != "male" && sex != "female")) {
                println("\tInvalid sex...")
                delay(timeBetweenText)
                return getSex()
            }
            return if (sex == "m" || sex == "male")
                Sex.MALE
            else
                Sex.FEMALE
        }

        suspend fun getParent(sex: Sex): Int? {
            print("\t${if (sex == Sex.FEMALE) "Mother" else "Father"} (leave empty if unknown): ")
            val parentName: String? = readlnOrNull()
            if (parentName == null) {
                println("\tInvalid name...")
                delay(timeBetweenText)
                return getParent(sex)
            }

            if (parentName.isEmpty()) return null

            val parentId = vault.getDwellerId(parentName)

            if (parentId == null) {
                print("\tDweller not found, write dweller's name again (y/n)? ")
                return if (answeredYes()) getParent(sex)
                else null
            }
            val parent = vault.getDweller(parentId)!!
            if (parent.sex != sex) {
                println("\tDweller $parent cannot be the ${if (sex == Sex.FEMALE) "mother" else "father"}...")
                delay(timeBetweenText)
                return getParent(sex)
            }
            return parentId
        }

        val name = getNameAvoidDuplicate()
        val sex = getSex()
        val motherId = getParent(Sex.FEMALE)
        val fatherId = getParent(Sex.MALE)

        print(
            "\tAdd $sex dweller $name${
                if (motherId != null || fatherId != null)
                    " ${if (sex == Sex.MALE) "son" else "daughter"} of ${
                        if (fatherId != null)
                            vault.getDweller(fatherId)!!.name + if (motherId != null) " and ${vault.getDweller(motherId)!!.name}" else ""
                        else
                            vault.getDweller(motherId)!!.name
                    }"
                else ""
            } (y/n)? "
        )
        if (answeredYes(true))
            vault.addDweller(name, sex, motherId, fatherId)
        println()
    }

    private suspend fun reportDeath() {
        val dweller = vault.getDweller(vault.getDwellerId(getName()))!!
        print(
            "\tAre you sure ${dweller.sex} dweller ${dweller.name}${
                if (dweller.mother != null || dweller.father != null)
                    " ${if (dweller.sex == Sex.MALE) "son" else "daughter"} of ${
                        if (dweller.father != null)
                            dweller.father.name + if (dweller.mother != null) " and ${dweller.mother.name}" else ""
                        else
                            dweller.mother!!.name
                    }"
                else ""
            } (y/n)? "
        )
    }


}