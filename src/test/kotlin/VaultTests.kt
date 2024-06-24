import kotlinx.coroutines.delay

class VaultTests {
    suspend fun idk(){
        val vault = VaultTecVault()
        vault.addDweller("Anthony Hopkins", Sex.MALE)
        vault.addDweller("Anthony Hopkins", Sex.MALE, null, null)
        vault.saveData()
        delay(7500)
        println("ended")
    }
}