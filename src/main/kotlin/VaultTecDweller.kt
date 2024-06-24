import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Calendar

@Serializable
class VaultTecDweller(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val sex: Sex,
    private val motherID: Int? = null,
    private val fatherID: Int? = null,
    private val timeOfBirth: String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().time)
        .toString(),
    private var alive: Boolean = true,
    private val partner: List<Int> = mutableListOf()
) {
    val name = "$firstName $lastName"

    fun die() {
        alive = false
    }

}

enum class Sex {
    MALE, FEMALE
}