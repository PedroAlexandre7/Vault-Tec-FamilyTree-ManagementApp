import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.text.SimpleDateFormat
import java.util.Calendar

@Serializable
class VaultTecDweller(
    val id: Int,
    private val firstName: String,
    private val lastName: String,
    val sex: Sex,
    private val motherID: Int? = null,
    private val fatherID: Int? = null,
    private val timeOfBirth: String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().time)
        .toString(),
    private var alive: Boolean = true,
    private val partner: List<Int> = mutableListOf()
) {
    private val childreen: List<Int> = mutableListOf()

    @Transient
    private val vault = VaultTecVault.getInstance()
    @Transient
    val name = "$firstName${if (lastName.isNotEmpty()) " $lastName" else ""}"

    val mother: VaultTecDweller? = vault.getDweller(motherID)
    val father: VaultTecDweller? = vault.getDweller(fatherID)

    fun die() {
        alive = false
    }

    fun accept(v: Visitor) {
        if (v.visit(this))
            childreen.forEach {
                VaultTecVault.getInstance().getDweller(it)?.accept(v)
            }
    }



}

enum class Sex {
    MALE, FEMALE
}

