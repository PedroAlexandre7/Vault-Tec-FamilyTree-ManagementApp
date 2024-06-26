import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import java.io.File
import kotlinx.coroutines.*

class VaultTecVault private constructor() {

    companion object {

        @Volatile
        private var instance: VaultTecVault? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: VaultTecVault().also { instance = it }
            }
    }

    private val vaultFile = File("AppData/vault.json")
    private val appDataFile = File("AppData/settings.json")
    private val vaultBackupFile = File("AppData/vaultBackup.json")
    private val appDataBackupFile = File("AppData/settingsBackup.json")

    private val updaterCoroutine = CoroutineScope(Dispatchers.IO)

    private val json = Json {
        encodeDefaults = true
        prettyPrint = true
    }

    @Serializable
    private var iDCounter: Int = 0
    private val vaultDwellers: MutableMap<Int, VaultTecDweller> = getVaultDwellers()

    private fun getVaultDwellers(): MutableMap<Int, VaultTecDweller> {
        var dwellers: List<VaultTecDweller>? = null
        var backupDwellers: List<VaultTecDweller>? = null
        try {
            dwellers = json.decodeFromString<List<VaultTecDweller>>(vaultFile.readText())
        } catch (e: Exception) {
            println(" Error:Something wrong with vault file, proceeding to load backup file...")
        }
        try {
            backupDwellers = json.decodeFromString<List<VaultTecDweller>>(vaultBackupFile.readText())
        } catch (e: Exception) {
            println("Error: Something wrong with vault backup file")
        }

        fun createMap(dwellers: List<VaultTecDweller>): MutableMap<Int, VaultTecDweller> {
            val mutableMap: MutableMap<Int, VaultTecDweller> = mutableMapOf()
            dwellers.forEach { mutableMap[it.id] = it }
            return mutableMap
        }

        if (dwellers != null && backupDwellers != null) {//smt && smt
            return if (dwellers.size >= backupDwellers.size)
                createMap(dwellers)
            else
                createMap(backupDwellers)
        }

        if (dwellers == null && backupDwellers != null) {//null && smt
            return createMap(backupDwellers)
        }
        if (dwellers != null) {//mst && null
            return createMap(dwellers)
        }

        println("Error: Both vault files went bad, every dweller has to be added again... Please contact developer if you're seeing this message!!")
        return mutableMapOf()
    }

    init {
        createFilesAndDirectories()
        getCounter()
        startUpdating()
    }

    private fun createFilesAndDirectories() {
        vaultFile.getParentFile().mkdirs()
        vaultFile.createNewFile()
        appDataFile.createNewFile()
        vaultBackupFile.createNewFile()
        appDataBackupFile.createNewFile()
    }

    private fun getCounter() {
        try {
            val counter = json.decodeFromString<Int>(appDataFile.readText())
            val backupCounter = json.decodeFromString<Int>(appDataFile.readText())
            iDCounter = if (counter > backupCounter) counter else backupCounter
        } catch (e: Exception) {
            println("Error while loading important information... if this is the second time it happens you're going to loose some dwellers. Please contact developer before using the app!!")
            iDCounter = 1000
        }
    }

    private fun startUpdating()  {
        updaterCoroutine.launch { // launch a new coroutine and continue
            while (true) {
                delay(5000)
                saveData()
                delay(1000)
                backupData()
            }
        }
    }

    fun getDwellerId(name: String):Int? {
        vaultDwellers.values.forEach{if (it.name == name) return it.id}
        return null
    }

    fun addDweller(name: String, sex: Sex, motherId: Int? = null, fatherId: Int? = null) {//TODO make mother and father logic (partners and childreen)
        var splitedName = name.split(" ", limit = 2)
        if (splitedName.size==1)
            splitedName = listOf(splitedName[0], "")
        vaultDwellers[iDCounter] = VaultTecDweller(iDCounter++, splitedName[0], splitedName[1], sex, motherId, fatherId)
    }

    fun getDweller(id: Int?): VaultTecDweller?{
        return vaultDwellers[id]
    }

    fun reportDeath(name: String){
        vaultDwellers[getDwellerId(name)]?.die()
    }

    fun saveData() {
        appDataFile.writeText(json.encodeToString(iDCounter))
        vaultFile.writeText(json.encodeToString(vaultDwellers.values.toList()))
    }

    private fun backupData() {
        appDataBackupFile.writeText(json.encodeToString(iDCounter))
        vaultBackupFile.writeText(json.encodeToString(vaultDwellers.values.toList()))
    }

}

suspend fun main() {
    VaultTecFamilyTreeMenu.getInstance().start()

}